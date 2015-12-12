package com.chinaweal.lain.monitor.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class MonitorUtils {
    /**
     * Æ½´ÕsybaseÔ´µÄurl
     *
     * @param ip
     * @param port
     * @param dbName
     * @return
     */
    public static String genSybaseURL(String ip, String port, String dbName) {
        return "jdbc:sybase:Tds:" + ip + ":" + port + "/" + dbName + "?charset=cp936";
    }
}
