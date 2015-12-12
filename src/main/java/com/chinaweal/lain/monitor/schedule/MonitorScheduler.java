package com.chinaweal.lain.monitor.schedule;

import com.chinaweal.lain.monitor.model.MonitorTaskModel;
import com.chinaweal.lain.monitor.monitor.MonitorConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-10
 */
public class MonitorScheduler {

    public static void doMonitor() {
        try {
            org.quartz.Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            //定时任务的初始化
            Set monitorConfigSet = MonitorConfig.TASK.keySet();
            Iterator iterator = monitorConfigSet.iterator();
            while (iterator.hasNext()) {
                String taskName = iterator.next().toString();
                MonitorTaskModel monitorTaskModel = (MonitorTaskModel) MonitorConfig.TASK.get(taskName);
                Class monitorTaskClazz = Class.forName(MonitorConfig.monitorTaskClazz.get(taskName).toString());
                addMonitorJob(scheduler, monitorTaskClazz, monitorTaskModel.getPeriod());   //添加所有定时任务
            }
            scheduler.start();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 添加监控任务
     *
     * @param monitorScheduler
     * @param monitorClazz
     * @param cronExpression
     * @throws SchedulerException
     */
    public static void addMonitorJob(Scheduler monitorScheduler, Class monitorClazz, String cronExpression)
            throws SchedulerException {
        JobDetail monitorJob = JobBuilder.newJob(monitorClazz).build();

        //监控周期
        CronScheduleBuilder taskSchedule = CronScheduleBuilder.cronSchedule(cronExpression);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(taskSchedule)
                .build();

        monitorScheduler.scheduleJob(monitorJob, trigger);
    }


}
