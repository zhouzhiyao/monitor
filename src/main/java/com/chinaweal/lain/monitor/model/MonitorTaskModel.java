package com.chinaweal.lain.monitor.model;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class MonitorTaskModel {
    private String taskName;
    private String tye;
    private String period;
    private String mailSendTo;
    private String mailCcTo;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTye() {
        return tye;
    }

    public void setTye(String tye) {
        this.tye = tye;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMailSendTo() {
        return mailSendTo;
    }

    public void setMailSendTo(String mailSendTo) {
        this.mailSendTo = mailSendTo;
    }

    public String getMailCcTo() {
        return mailCcTo;
    }

    public void setMailCcTo(String mailCcTo) {
        this.mailCcTo = mailCcTo;
    }
}
