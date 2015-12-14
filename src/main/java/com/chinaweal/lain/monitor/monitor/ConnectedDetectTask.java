package com.chinaweal.lain.monitor.monitor;

import com.chinaweal.lain.monitor.model.MonitorTaskModel;
import com.chinaweal.lain.monitor.model.SourceConfigModel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-12
 */
public class ConnectedDetectTask extends MonitorTask implements Job {

    public static final String TASK_NAME = "connectedDetectTask";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MonitorTaskModel taskModel = (MonitorTaskModel) MonitorConfig.TASK.get(TASK_NAME);
        String sourceTye = taskModel.getTye();    //��ص�Դ
        String[] multiTye = sourceTye.split(";"); //���ݸ���Ϣ��ȡ�����Դ�����ݿ⣬IP������ֶΣ�SQL����
        String resultInfo = "";
        for (int i = 0; i < multiTye.length; i++) {
            String tye = multiTye[i];
            ArrayList sourceList = MonitorBO.getSourceByTye(tye);
            resultInfo += detect(sourceList);
        }

        sendMail(taskModel, resultInfo);

    }

    /**
     * ̽��IP�Ƿ���Ч
     *
     * @param sourceList
     */
    public String detect(ArrayList sourceList) {
        String detectedResult = "";
        for (int i = 0; i < sourceList.size(); i++) {
            SourceConfigModel source = (SourceConfigModel) sourceList.get(i);
            String url = source.getProtocol() + "://" + source.getIp() + ":" + source.getPort() + source.getTarget();
            if (!isURLConnected(url)) {
                detectedResult += source.getArea() + " " + url + "\n";
            }
        }
        //ƴ�ռ������ı���
        if (sourceList.size() > 0 && detectedResult.length() > 0)
            detectedResult = ((SourceConfigModel) sourceList.get(0)).getDiscription() + "�����µط��޷����ʣ�" + detectedResult + "\n";
        return detectedResult;
    }

    private boolean isURLConnected(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            if (con.getResponseCode() == 200) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
