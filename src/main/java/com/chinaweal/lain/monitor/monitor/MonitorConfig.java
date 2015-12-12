package com.chinaweal.lain.monitor.monitor;

import com.chinaweal.lain.monitor.db.DBUtils;
import com.chinaweal.lain.monitor.model.DataSourceConfigModel;
import com.chinaweal.lain.monitor.model.MonitorTaskModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class MonitorConfig {

    //后面移到配置文件datasource.xml
    private static final String url = "jdbc:sybase:Tds:10.1.1.39:7220/aicbiz?charset=cp936";
    private static final String name = "sa";
    private static final String password = "sb0525";

    public static final Map DATASOURCES = new HashMap();    //key(tye_area) - value(dataSourceConfigModel)
    public static final Map TASK = new HashMap();   //key(监控任务名称) - MonitorTaskModel

    static {
        dataSourceConfigInit();
        monitorTaskInit();
    }

    /**
     * 被监控数据源的初始化
     */
    public static void dataSourceConfigInit() {
        DBUtils db = new DBUtils(url, name, password);
        String SQL = "SELECT * FROM dataSourceConfig";
        List dataSourceList = db.executeQuery(SQL);
        for (int i = 0; i < dataSourceList.size(); i++) {
            Map dataSourceMap = (Map) dataSourceList.get(i);
            DataSourceConfigModel dataSourceConfigModel = new DataSourceConfigModel();
            dataSourceConfigModel.setTye((String) dataSourceMap.get("tye"));
            dataSourceConfigModel.setDiscription((String) dataSourceMap.get("discription"));
            dataSourceConfigModel.setDbName((String) dataSourceMap.get("dbName"));
            dataSourceConfigModel.setArea((String) dataSourceMap.get("area"));
            dataSourceConfigModel.setIp((String) dataSourceMap.get("ip"));
            dataSourceConfigModel.setPort((String) dataSourceMap.get("port"));
            dataSourceConfigModel.setUserName((String) dataSourceMap.get("username"));
            dataSourceConfigModel.setPassword((String) dataSourceMap.get("password"));
            String key = dataSourceConfigModel.getTye() + "_" + dataSourceConfigModel.getArea();
            DATASOURCES.put(key, dataSourceConfigModel);
        }
        return;
    }

    /**
     * 监控任务的初始化
     */
    public static void monitorTaskInit() {
        DBUtils db = new DBUtils(url, name, password);
        String SQL = "SELECT * FROM taskConfig";
        List taskList = db.executeQuery(SQL);
        for (int i = 0; i < taskList.size(); i++) {
            Map taskMap = (Map) taskList.get(i);
            MonitorTaskModel model = new MonitorTaskModel();
            model.setTaskName((String) taskMap.get("taskname"));
            model.setTye((String) taskMap.get("tye"));
            model.setPeriod((String) taskMap.get("period"));
            model.setMailSendTo((String) taskMap.get("mailSendTo"));
            model.setMailCcTo((String) taskMap.get("mailCcTo"));
            TASK.put(model.getTaskName(), model);
        }

        return;
    }

    //任务名称与监控类的对应关系
    public static Map monitorTaskClazz = new HashMap();

    static {
        monitorTaskClazz.put(UniSCIDTransMonitorTask.TASK_NAME, "com.chinaweal.lain.monitor.monitor.UniSCIDTransMonitorTask");
    }


}
