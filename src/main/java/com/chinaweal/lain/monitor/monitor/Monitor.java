package com.chinaweal.lain.monitor.monitor;

import com.chinaweal.lain.monitor.schedule.MonitorScheduler;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class Monitor {
    public static void monitor() {
        MonitorScheduler.doMonitor();
    }

    public static void monitorTaskInit() {
//        new MonitorConfig();
    }


    public static void main(String[] args) throws Throwable {
        monitor();
    }
}
