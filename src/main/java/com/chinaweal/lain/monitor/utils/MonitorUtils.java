package com.chinaweal.lain.monitor.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class MonitorUtils {
    /**
     * Æ´½ÓsybaseÔ´µÄurl
     *
     * @param ip
     * @param port
     * @param dbName
     * @return
     */
    public static String genSybaseURL(String ip, String port, String dbName) {
        return "jdbc:sybase:Tds:" + ip + ":" + port + "/" + dbName + "?charset=cp936";
    }

    public static boolean isURLConnected(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            if (con.getResponseCode() == 200) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
