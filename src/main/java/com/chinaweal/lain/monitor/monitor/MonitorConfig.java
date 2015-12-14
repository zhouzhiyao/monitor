package com.chinaweal.lain.monitor.monitor;

import com.chinaweal.lain.monitor.db.DBUtils;
import com.chinaweal.lain.monitor.model.MonitorTaskModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 * <p/>
 * 所有系统基础数据都在这里进行初始化
 */
public class MonitorConfig {

    //任务名称与监控类的对应关系
    public static Map monitorTaskClazz = new HashMap();

    static {
        monitorTaskClazz.put(UniSCIDTransMonitorTask.TASK_NAME, "com.chinaweal.lain.monitor.monitor.UniSCIDTransMonitorTask");
        monitorTaskClazz.put(ConnectedDetectTask.TASK_NAME, "com.chinaweal.lain.monitor.monitor.ConnectedDetectTask");
    }


    /**
     * 监控任务的初始化
     */
    static {
        monitorTaskInit();
    }

    public static final Map TASK = new HashMap();   //key(监控任务名称) - MonitorTaskModel

    public static void monitorTaskInit() {
        String SQL = "SELECT * FROM taskConfig";
        DBUtils db = new DBUtils(MonitorConstant.url, MonitorConstant.name, MonitorConstant.password);
        List taskList = db.executeQuery(SQL);
        for (int i = 0; i < taskList.size(); i++) {
            Map taskMap = (Map) taskList.get(i);
            MonitorTaskModel model = new MonitorTaskModel();
            model.setTaskName((String) taskMap.get("taskName"));
            model.setTye((String) taskMap.get("tye"));
            model.setPeriod((String) taskMap.get("period"));
            model.setMailSendTo((String) taskMap.get("mailSendTo"));
            model.setMailCcTo((String) taskMap.get("mailCcTo"));
            TASK.put(model.getTaskName(), model);
        }

        return;
    }
}
