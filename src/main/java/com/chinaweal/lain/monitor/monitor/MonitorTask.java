package com.chinaweal.lain.monitor.monitor;

import com.chinaweal.lain.monitor.communicate.Mail;
import com.chinaweal.lain.monitor.model.MonitorTaskModel;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 */
public class MonitorTask {

    /**
     * 根据监控任务配置信息，将监控结果以邮件发送
     *
     * @param taskModel
     * @param content
     */
    public void sendMail(MonitorTaskModel taskModel, String content) {
        String[] sendTo = taskModel.getMailSendTo().toString().split(";");
        String[] ccTo = taskModel.getMailCcTo().toString().split(";");

        //发送将监控结果信息，针对不同的通知方式做扩展
        Mail mail = new Mail(sendTo, ccTo, taskModel.getTaskName(), content);
        mail.sendMail();
    }


    /**
     * 记录监控任务运行情况
     */
    public void monitorTaskLog(){

    }
}
