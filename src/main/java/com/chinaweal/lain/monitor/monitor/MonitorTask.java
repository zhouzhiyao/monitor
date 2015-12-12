package com.chinaweal.lain.monitor.monitor;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 */
public interface MonitorTask {
    /**
     *  1、获取监控结果；
     *  2、监控结果处理，比如对比，判断容错范围等
     *  3、然后根据结果决定是否需要触发监控通知
     *
     *  地域、库
     */

    /**
     * 监控任务的初始化
     */
    public void init();

    /**
     * 形成监控结果的文字信息，用于通知
     *
     * @return
     */
}
