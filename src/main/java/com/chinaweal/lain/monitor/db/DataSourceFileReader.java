package com.chinaweal.lain.monitor.db;


import com.chinaweal.lain.monitor.model.DataSourceConfigModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 */
public class DataSourceFileReader {
    private String dataSourceFilePath;

    public DataSourceFileReader(String dataSourceFilePath) {
        this.dataSourceFilePath = dataSourceFilePath;
    }

    /**
     * 解析dataSource的配置文件
     *
     * @return 以HashMap存储解析结果：地域名 - DataSourceConfigModel
     * @throws IOException
     */
    public Map read() throws IOException {
        File dataSourceFile = new File(dataSourceFilePath);
        SAXReader reader = new SAXReader();
        Map result = new HashMap();
        try {
            Document doc = reader.read(dataSourceFile);
            Node rootNode = doc.selectSingleNode("/datasources");
            List dataSourceNodeList = rootNode.selectNodes("/datasource");
            Iterator dataSourceIterator = dataSourceNodeList.iterator();
            while (dataSourceIterator.hasNext()) {
                Node node = (Node) dataSourceIterator.next();
                String area = node.getText();
                DataSourceConfigModel model = new DataSourceConfigModel();
//                model.setUrl(node.valueOf("@url"));

                String name = node.selectSingleNode("/property/@name").getText();
                String password = node.selectSingleNode("/property/@password").getText();

                model.setUserName(name);
                model.setPassword(password);

                result.put(area, model);

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }
}
