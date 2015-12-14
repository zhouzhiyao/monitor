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
        String sourceTye = taskModel.getTye();    //监控的源
        String[] multiTye = sourceTye.split(";"); //根据该信息获取被监控源（数据库，IP，监控手段（SQL））
        String resultInfo = "";
        for (int i = 0; i < multiTye.length; i++) {
            String tye = multiTye[i];
            ArrayList sourceList = MonitorBO.getSourceByTye(tye);
            resultInfo += detect(sourceList);
        }

        sendMail(taskModel, resultInfo);

    }

    /**
     * 探测IP是否有效
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
        //拼凑监控事项的标题
        if (sourceList.size() > 0 && detectedResult.length() > 0)
            detectedResult = ((SourceConfigModel) sourceList.get(0)).getDiscription() + "，以下地方无法访问：" + detectedResult + "\n";
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
