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
     * ���ݼ������������Ϣ������ؽ�����ʼ�����
     *
     * @param taskModel
     * @param content
     */
    public void sendMail(MonitorTaskModel taskModel, String content) {
        String[] sendTo = taskModel.getMailSendTo().toString().split(";");
        String[] ccTo = taskModel.getMailCcTo().toString().split(";");

        //���ͽ���ؽ����Ϣ����Բ�ͬ��֪ͨ��ʽ����չ
        Mail mail = new Mail(sendTo, ccTo, taskModel.getTaskName(), content);
        mail.sendMail();
    }


    /**
     * ��¼��������������
     */
    public void monitorTaskLog(){

    }
}
