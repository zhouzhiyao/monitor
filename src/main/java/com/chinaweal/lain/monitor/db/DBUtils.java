package com.chinaweal.lain.monitor.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 */
public class DBUtils {
    private String url;
    private String userName;
    private String password;

    public DBUtils(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }


    public Connection getConnectionFromSybase()
            throws SQLException {
        Connection conn = null;
        try {
            //数据库驱动器类   //oracle.jdbc.OracleDriver
            Class.forName("com.sybase.jdbc2.jdbc.SybXADataSource");

            conn = DriverManager.getConnection(url, userName, password); //建立连接

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public List executeQuery(String sql) {
        List result = new ArrayList();
        Connection con = null;
        Statement stat = null;
        ResultSet resultSet = null;
        try {
            con = getConnectionFromSybase();
            stat = con.createStatement();
            resultSet = stat.executeQuery(sql);
            result = dataPackaging(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (resultSet != null) resultSet.close();
                if (stat != null) stat.close();
                if (con != null) con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stat != null) stat.close();
                if (con != null) con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }


    private List dataPackaging(ResultSet resultSet) throws SQLException {
        List result = new ArrayList();
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        while (resultSet.next()) {
            result.add(mapRow(resultSet, rsMetaData));
        }
        return result;
    }

    private Map mapRow(ResultSet resultSet, ResultSetMetaData rsMetaData) throws SQLException {
        HashMap record = new HashMap();
        int columns = rsMetaData.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            String columnName = rsMetaData.getColumnLabel(i);
            Object obj = resultSet.getObject(i);
            record.put(columnName, obj);
        }
        return record;
    }
}
