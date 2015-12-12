package com.chinaweal.lain.monitor.monitor;


import com.chinaweal.lain.monitor.communicate.Mail;
import com.chinaweal.lain.monitor.db.DBUtils;
import com.chinaweal.lain.monitor.model.DataSourceConfigModel;
import com.chinaweal.lain.monitor.model.MonitorTaskModel;
import com.chinaweal.lain.monitor.utils.AreaUtils;
import com.chinaweal.lain.monitor.utils.HtmlTemplateUtils;
import com.chinaweal.lain.monitor.utils.MonitorUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 * <p/>
 * 统一社会信用代码共享数据传输的监控任务
 * 监控内容：传输两端（生成库、共享库）的数量是否一致
 */
public class UniSCIDTransMonitorTask implements Job {

    public static final String TASK_NAME = "uniSCIDTransMonitorTask";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long[] result = compare();
        String resultInfo = resultInfo(result);

        MonitorTaskModel taskModel = (MonitorTaskModel) MonitorConfig.TASK.get(TASK_NAME);
        String[] sendTo = taskModel.getMailSendTo().toString().split(";");
        String[] ccTo = taskModel.getMailCcTo().toString().split(";");

        //发送将监控结果信息，针对不同的通知方式做扩展
        new Mail(sendTo, ccTo, taskModel.getTaskName(), resultInfo).sendMail();

        //记录本次监控任务的运行情况
        monitorTaskLog();
    }

    /**
     * 非工作时间监控的任务：
     * 周一至周五
     * 早上七点，中午一点，晚上六点
     */


    /**
     * 两个目标的对比，将结果不一样的地域记录下来
     * 数组方式进行比对
     *
     * @return long[]
     */
    public long[] compare() {
        long[] official = getAllAreasResult(MonitorSQL.getUniSCIDTransMonitorTask_officialSQL(), "official");
        long[] share = getAllAreasResult(MonitorSQL.getUniSCIDTransMonitorTask_shareSQL(), "share");
        long[] result = new long[AreaUtils.AREA_LIST.size()];
        for (int i = 0; i < AreaUtils.AREA_LIST.size(); i++) {
            //链路不通，本次结果无效，不再进行比对
            if (official[i] == -1 || share[i] == -1) {
                result[i] = -1;
                continue;
            }
            result[i] = official[i] - share[i];
        }
        return result;
    }

    /**
     * 对选定的地域进行对比
     * 考虑数组为空的情况，以及链路不通的处理情况
     *
     * @param SQL
     * @param tye
     * @return
     */
    public long[] getAllAreasResult(String SQL, String tye) {

        long[] allAreasResult = new long[AreaUtils.AREA_LIST.size()];

        for (int i = 0; i < AreaUtils.AREA_LIST.size(); i++) {
            String area = AreaUtils.AREA_LIST.get(i).toString();
            String key = tye + "_" + area;
            DataSourceConfigModel model = (DataSourceConfigModel) MonitorConfig.DATASOURCES.get(key);
            if (model == null) {
                allAreasResult[AreaUtils.areaIndex(area)] = -1;
                continue;
            }
            String url = MonitorUtils.genSybaseURL(model.getIp(), model.getPort(), model.getDbName());
            String userName = model.getUserName();
            String password = model.getPassword();
            DBUtils db = new DBUtils(url, userName, password);
            long areaSum = 0;
            try {
                areaSum = db.getSumResult(SQL);
            } catch (Throwable throwable) {
                allAreasResult[AreaUtils.areaIndex(area)] = -1; //-1表示在查询过程中出现问题，如链路不通
//                throwable.printStackTrace();
            }
            allAreasResult[AreaUtils.areaIndex(area)] = areaSum;
            System.out.println(key + " checking ...");
        }
        return allAreasResult;
    }


    /**
     * 形成监控结果文字，提供给通讯工具使用
     *
     * @return
     */
    public String resultInfo(long[] result) {
        ArrayList disconnectedArea = new ArrayList();
        ArrayList exceptionArea = new ArrayList();
        String resultInfo = "";
        try {
            for (int i = 0; i < result.length; i++) {
                if (result[i] == 0)
                    continue;
                if (result[i] == -1) {
                    disconnectedArea.add(AreaUtils.AREA_LIST.get(i));
                    continue;
                }
                exceptionArea.add(AreaUtils.AREA_LIST.get(i) + " -> " + result[i]);
            }
        } catch (Exception e) {
            //记录异常信息并邮件发送
            e.printStackTrace();
        }

        if (exceptionArea.size() > 0 || disconnectedArea.size() > 0) {
            resultInfo += HtmlTemplateUtils.getHead();
        }

        if (exceptionArea.size() > 0) {
            String line = HtmlTemplateUtils.getLine().replace("@item", "数据传输异常地区：");
            String exceptionStr = "";
            for (int i = 0; i < exceptionArea.size(); i++) {
                if (i == exceptionArea.size() - 1) {
                    exceptionStr += exceptionArea.get(i);
                    break;
                }
                exceptionStr += exceptionArea.get(i) + ", ";
            }
            line = line.replace("@content", exceptionStr);
            resultInfo += line;
        }

        if (disconnectedArea.size() > 0) {
            String line = HtmlTemplateUtils.getLine().replace("@item", "链路异常地区：");
            String disconnectedStr = "";
            for (int i = 0; i < disconnectedArea.size(); i++) {
                if (i == disconnectedArea.size() - 1) {
                    disconnectedStr += disconnectedArea.get(i);
                    break;
                }
                disconnectedStr += disconnectedArea.get(i) + ", ";
            }
            line = line.replace("@content", disconnectedStr);
            resultInfo += line;
        }

        if (exceptionArea.size() > 0 || disconnectedArea.size() > 0) {
            resultInfo += HtmlTemplateUtils.getEnd();
        }

        return resultInfo;
    }


    /**
     * 记录本次监控任务运行情况
     */
    private void monitorTaskLog() {
        //Log the task running info!
    }


    public static void main(String[] args) throws Throwable {
    }
}
