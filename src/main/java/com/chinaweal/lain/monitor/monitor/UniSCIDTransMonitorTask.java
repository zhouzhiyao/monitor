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
 * ͳһ������ô��빲�����ݴ���ļ������
 * ������ݣ��������ˣ����ɿ⡢����⣩�������Ƿ�һ��
 */
public class UniSCIDTransMonitorTask extends MonitorTask implements Job {

    public static final String TASK_NAME = "uniSCIDTransMonitorTask";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        MonitorTaskModel taskModel = (MonitorTaskModel) MonitorConfig.TASK.get(TASK_NAME);

        String sourceTye = taskModel.getTye();    //��ص�Դ
        String[] multiTye = sourceTye.split(";"); //���ݸ���Ϣ��ȡ�����Դ�����ݿ⣬IP������ֶΣ�SQL����
        ArrayList apple = MonitorBO.getSourceByTye(multiTye[0]);
        ArrayList banana = MonitorBO.getSourceByTye(multiTye[1]);
        Entry[] result = compare(allAreaCount(apple), allAreaCount(banana));
        String mailContent = mailContent(result);

        sendMail(taskModel, mailContent);

        //��¼���μ��������������
        monitorTaskLog();
    }

    /**
     * ���������ȵ�������бȽϣ����ȽϽ������ͬ�����ݽṹ�洢
     *
     * @param apple
     * @param banana
     * @return
     */
    public Entry[] compare(Entry[] apple, Entry[] banana) {
        Entry[] result = new Entry[apple.length];
        for (int i = 0; i < result.length; i++) {   //�Խ��������г�ʼ����Ĭ�϶�����·�쳣״̬����ѯ�ɹ���д��ֵ
            result[i] = new Entry();
        }
        for (int i = 0; i < result.length; i++) {
            //����һ����·��ͨ�����ν����Ч�����ٽ��бȶ�
            if (apple[i].getSum() == -1 || banana[i].getSum() == -1) {
                result[i].setRecord(apple[i].getRecord() + "/" + banana[i].getRecord());
                continue;
            }
            result[i].setSum(apple[i].getSum() - banana[i].getSum());
        }
        return result;
    }

    /**
     * ��AreaUtils.ArrayList��ģ������¼��ѯ�����
     * ��Ҫ��Ϊ�˺���ȶԷ��㣬Դ�����������ģ���ģ�������������
     *
     * @param sourceList
     * @return
     */
    public Entry[] allAreaCount(ArrayList sourceList) {
        Entry[] allAreasResult = new Entry[AreaUtils.AREA_LIST.size()];
        for (int i = 0; i < allAreasResult.length; i++) {   //�Խ��������г�ʼ����Ĭ�϶�����·�쳣״̬����ѯ�ɹ���д��ֵ
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
            } catch (Throwable throwable) {   //ͳ�ƹ����г����쳣���϶�Ϊ��·��ͨ����¼��ʱʹ�õ���·��Ϣ
                allAreasResult[index].setRecord(url + "?name=" + name + "&password=" + password);
            }
        }
        return allAreasResult;
    }

    /**
     * ���ݽ������ƴ���ʼ�����
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
            disconnectedArea = "��·�쳣������" + "\n" + disconnectedArea;

        if (exceptionArea.length() > 0)
            exceptionArea = "���ݴ����쳣������" + "\n" + exceptionArea;

        return disconnectedArea + exceptionArea;
    }


    /**
     * �����ݽṹ���ڼ�¼ �ȽϽ����
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
