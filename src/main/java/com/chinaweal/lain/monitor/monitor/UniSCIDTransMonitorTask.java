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
 * ͳһ������ô��빲�����ݴ���ļ������
 * ������ݣ��������ˣ����ɿ⡢����⣩�������Ƿ�һ��
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

        //���ͽ���ؽ����Ϣ����Բ�ͬ��֪ͨ��ʽ����չ
        new Mail(sendTo, ccTo, taskModel.getTaskName(), resultInfo).sendMail();

        //��¼���μ��������������
        monitorTaskLog();
    }

    /**
     * �ǹ���ʱ���ص�����
     * ��һ������
     * �����ߵ㣬����һ�㣬��������
     */


    /**
     * ����Ŀ��ĶԱȣ��������һ���ĵ����¼����
     * ���鷽ʽ���бȶ�
     *
     * @return long[]
     */
    public long[] compare() {
        long[] official = getAllAreasResult(MonitorSQL.getUniSCIDTransMonitorTask_officialSQL(), "official");
        long[] share = getAllAreasResult(MonitorSQL.getUniSCIDTransMonitorTask_shareSQL(), "share");
        long[] result = new long[AreaUtils.AREA_LIST.size()];
        for (int i = 0; i < AreaUtils.AREA_LIST.size(); i++) {
            //��·��ͨ�����ν����Ч�����ٽ��бȶ�
            if (official[i] == -1 || share[i] == -1) {
                result[i] = -1;
                continue;
            }
            result[i] = official[i] - share[i];
        }
        return result;
    }

    /**
     * ��ѡ���ĵ�����жԱ�
     * ��������Ϊ�յ�������Լ���·��ͨ�Ĵ������
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
                allAreasResult[AreaUtils.areaIndex(area)] = -1; //-1��ʾ�ڲ�ѯ�����г������⣬����·��ͨ
//                throwable.printStackTrace();
            }
            allAreasResult[AreaUtils.areaIndex(area)] = areaSum;
            System.out.println(key + " checking ...");
        }
        return allAreasResult;
    }


    /**
     * �γɼ�ؽ�����֣��ṩ��ͨѶ����ʹ��
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
            //��¼�쳣��Ϣ���ʼ�����
            e.printStackTrace();
        }

        if (exceptionArea.size() > 0 || disconnectedArea.size() > 0) {
            resultInfo += HtmlTemplateUtils.getHead();
        }

        if (exceptionArea.size() > 0) {
            String line = HtmlTemplateUtils.getLine().replace("@item", "���ݴ����쳣������");
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
            String line = HtmlTemplateUtils.getLine().replace("@item", "��·�쳣������");
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
     * ��¼���μ�������������
     */
    private void monitorTaskLog() {
        //Log the task running info!
    }


    public static void main(String[] args) throws Throwable {
    }
}
