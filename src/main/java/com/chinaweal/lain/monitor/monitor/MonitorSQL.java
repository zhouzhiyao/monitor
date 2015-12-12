package com.chinaweal.lain.monitor.monitor;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-12
 */
public class MonitorSQL {
    private static String uniSCIDTransMonitorTask_officialSQL = "select count(1) from entityNOBond e, marketEntity m where m.entityno = e.entityno";
    private static String uniSCIDTransMonitorTask_shareSQL = "select  count(1) as AICRSD from certinfo where isvalid ='1'";

    /**
     * ���������ͳһ����������
     *
     * @return
     */
    public static String getUniSCIDTransMonitorTask_officialSQL() {
        return uniSCIDTransMonitorTask_officialSQL;
    }

    /**
     * ��ع���⴫�������
     *
     * @return
     */
    public static String getUniSCIDTransMonitorTask_shareSQL() {
        return uniSCIDTransMonitorTask_shareSQL;
    }
}
