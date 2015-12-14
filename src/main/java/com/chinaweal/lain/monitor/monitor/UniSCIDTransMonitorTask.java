package com.chinaweal.lain.monitor.monitor;


import com.chinaweal.lain.monitor.model.MonitorTaskModel;
import com.chinaweal.lain.monitor.model.SourceConfigModel;
import com.chinaweal.lain.monitor.utils.AreaUtils;
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
public class UniSCIDTransMonitorTask extends MonitorTask implements Job {

    public static final String TASK_NAME = "uniSCIDTransMonitorTask";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        MonitorTaskModel taskModel = (MonitorTaskModel) MonitorConfig.TASK.get(TASK_NAME);

        String sourceTye = taskModel.getTye();    //监控的源
        String[] multiTye = sourceTye.split(";"); //根据该信息获取被监控源（数据库，IP，监控手段（SQL））
        ArrayList apple = MonitorBO.getSourceByTye(multiTye[0]);
        ArrayList banana = MonitorBO.getSourceByTye(multiTye[1]);
        Entry[] result = compare(allAreaCount(apple), allAreaCount(banana));
        String mailContent = mailContent(result);

        sendMail(taskModel, mailContent);

        //记录本次监控任务的运行情况
        monitorTaskLog();
    }

    /**
     * 对两个长度的数组进行比较，将比较结果以相同的数据结构存储
     *
     * @param apple
     * @param banana
     * @return
     */
    public Entry[] compare(Entry[] apple, Entry[] banana) {
        Entry[] result = new Entry[apple.length];
        for (int i = 0; i < result.length; i++) {   //对结果数组进行初始化，默认都是链路异常状态，查询成功后写入值
            result[i] = new Entry();
        }
        for (int i = 0; i < result.length; i++) {
            //其中一个链路不通，本次结果无效，不再进行比对
            if (apple[i].getSum() == -1 || banana[i].getSum() == -1) {
                result[i].setRecord(apple[i].getRecord() + "/" + banana[i].getRecord());
                continue;
            }
            result[i].setSum(apple[i].getSum() - banana[i].getSum());
        }
        return result;
    }

    /**
     * 以AreaUtils.ArrayList的模板结果记录查询结果集
     * 主要是为了后面比对方便，源结果集是无序的，而模板结果集是有序的
     *
     * @param sourceList
     * @return
     */
    public Entry[] allAreaCount(ArrayList sourceList) {
        Entry[] allAreasResult = new Entry[AreaUtils.AREA_LIST.size()];
        for (int i = 0; i < allAreasResult.length; i++) {   //对结果数组进行初始化，默认都是链路异常状态，查询成功后写入值
            allAreasResult[i] = new Entry();
        }

        for (int i = 0; i < sourceList.size(); i++) {
            SourceConfigModel sourceModel = (SourceConfigModel) sourceList.get(i);
            String area = sourceModel.getArea();
            String tye = sourceModel.getTye();
            String name = sourceModel.getUserName();
            String password = sourceModel.getPassword();
            String url = MonitorUtils.genSybaseURL(sourceModel.getIp(), sourceModel.getPort(), sourceModel.getTarget());
            int index = AreaUtils.areaIndex(area);
            try {
                long sum = MonitorBO.singleCount(url, name, password, MonitorSQL.getDBMonitorSQL(tye));
                allAreasResult[index].setSum(sum);
            } catch (Throwable throwable) {   //统计过程中出现异常，认定为链路不通，记录当时使用的链路信息
                allAreasResult[index].setRecord(url + "?name=" + name + "&password=" + password);
            }
        }
        return allAreasResult;
    }

    /**
     * 根据结果集，拼凑邮件内容
     *
     * @param result
     * @return
     */
    public String mailContent(Entry[] result) {
        String disconnectedArea = "";
        String exceptionArea = "";
        for (int i = 0; i < result.length; i++) {
            if (result[i].getSum() == -1) {
                disconnectedArea += AreaUtils.cnName(i) + " " + result[i].getRecord() + "\n";
            } else {
                exceptionArea += AreaUtils.cnName(i) + " " + result[i].getSum() + "\n";
            }
        }
        if (disconnectedArea.length() > 0)
            disconnectedArea = "链路异常地区：" + "\n" + disconnectedArea;

        if (exceptionArea.length() > 0)
            exceptionArea = "数据传输异常地区：" + "\n" + exceptionArea;

        return disconnectedArea + exceptionArea;
    }


    /**
     * 该数据结构用于记录 比较结果集
     */
    class Entry {
        private long sum;
        private String record;

        Entry() {
            this.sum = -1;
            this.record = "";
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }

        public String getRecord() {
            return record;
        }

        public void setRecord(String record) {
            this.record = record;
        }
    }


    public static void main(String[] args) throws Throwable {
    }
}
