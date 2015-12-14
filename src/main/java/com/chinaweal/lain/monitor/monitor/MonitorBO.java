package com.chinaweal.lain.monitor.monitor;

import com.chinaweal.lain.monitor.db.DBUtils;
import com.chinaweal.lain.monitor.model.SourceConfigModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-14
 */
public class MonitorBO {
    /**
     * 统计值，只有一个结果
     *
     * @param url
     * @param name
     * @param password
     * @param SQL
     * @return
     * @throws Throwable
     */
    public static long singleCount(String url, String name, String password, String SQL) throws Throwable {
        Connection con = null;
        Statement stat = null;
        ResultSet resultSet = null;
        long sum = 0;
        try {
            DBUtils db = new DBUtils(url, name, password);
            con = db.getConnectionFromSybase();
            stat = con.createStatement();
            resultSet = stat.executeQuery(SQL);
            if (resultSet.next())
                sum = resultSet.getLong(1);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stat != null) stat.close();
                if (con != null) con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return sum;
    }

    public static ArrayList getSourceByTye(String tye) {
        String SQL = "select * from sourceConfig where tye = '" + tye + "'";
        List list = new DBUtils(MonitorConstant.url, MonitorConstant.name, MonitorConstant.password)
                .executeQuery(SQL);
        ArrayList result = new ArrayList();
        SourceConfigModel sourceConfigModel = new SourceConfigModel();
        for (int i = 0; i < list.size(); i++) {
            Map sourceConfigMap = (HashMap) list.get(i);
            sourceConfigModel.setTye(sourceConfigMap.get("tye").toString());
            sourceConfigModel.setDiscription(sourceConfigMap.get("discription").toString());
            sourceConfigModel.setArea(sourceConfigMap.get("area").toString());
            sourceConfigModel.setProtocol(sourceConfigMap.get("protocol").toString());
            sourceConfigModel.setIp(sourceConfigMap.get("ip").toString());
            sourceConfigModel.setPort(sourceConfigMap.get("port").toString());
            sourceConfigModel.setUserName(sourceConfigMap.get("username").toString());
            sourceConfigModel.setPassword(sourceConfigMap.get("password").toString());
            result.add(sourceConfigModel);
        }
        return result;
    }
}
