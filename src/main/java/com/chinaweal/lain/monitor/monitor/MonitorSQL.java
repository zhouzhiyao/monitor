package com.chinaweal.lain.monitor.monitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-12
 */
public class MonitorSQL {
    private static String uniSCIDTransMonitorTask_officialSQL = "select count(1) from entityNOBond e, marketEntity m where m.entityno = e.entityno";
    private static String uniSCIDTransMonitorTask_shareSQL = "select  count(1) as AICRSD from certinfo where isvalid ='1'";

    private static Map DBMonitorSQL= new HashMap();
    static {
        DBMonitorSQL.put("official_db", uniSCIDTransMonitorTask_officialSQL);
        DBMonitorSQL.put("share_db", uniSCIDTransMonitorTask_shareSQL);
    }

    public static String getDBMonitorSQL(String dbTye){
        return DBMonitorSQL.get(dbTye).toString();
    }
}
