package com.staraid.mysql.controller;


import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.staraid.mysql.pojo.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * e-r图controller
 *
 */
@RestController
@RequestMapping("/api/er-diagram")
public class ERDiagramController {
    // 这个方法需要连接数据库并提取表结构信息

    private final DynamicDataSourceConfig dynamicDataSourceConfig;

    @Autowired
    public ERDiagramController(DynamicDataSourceConfig dynamicDataSourceConfig) {
        this.dynamicDataSourceConfig = dynamicDataSourceConfig;
    }
    @GetMapping("/data")
    public Map<String, Object> getERDiagramData() throws SQLException {
        // 实现获取数据库结构的逻辑
        // 例如返回表和字段的关系数据
        Map<String, Object> re = new HashMap<>();
        re.put("tables", getTables(dynamicDataSourceConfig.getDataSource("2").getConnection(),"etst"));
        re.put("relationships", getRelationships(dynamicDataSourceConfig.getDataSource("2").getConnection(),"etst"));

        return re;
    }

    public List<String> getTables(Connection connection, String databaseName) {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(databaseName, null, "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }


    public List<Map<String, Object>> getRelationships(Connection connection, String databaseName) {
        List<Map<String, Object>> relationships = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(databaseName, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                ResultSet foreignKeys = metaData.getImportedKeys(databaseName, null, tableName);
                while (foreignKeys.next()) {
                    Map<String, Object> relationship = new HashMap<>();
                    relationship.put("PKTABLE_NAME", foreignKeys.getString("PKTABLE_NAME"));
                    relationship.put("PKCOLUMN_NAME", foreignKeys.getString("PKCOLUMN_NAME"));
                    relationship.put("FKTABLE_NAME", foreignKeys.getString("FKTABLE_NAME"));
                    relationship.put("FKCOLUMN_NAME", foreignKeys.getString("FKCOLUMN_NAME"));
                    relationships.add(relationship);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relationships;
    }
}
