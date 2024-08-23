package com.staraid.mysql.service.impl;

import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.staraid.mysql.service.ERDiagramService;
import com.staraid.mysql.utils.Result;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ERDiagramServiceImpl implements ERDiagramService {

    private final DynamicDataSourceConfig dynamicDataSourceConfig;

    public ERDiagramServiceImpl(DynamicDataSourceConfig dynamicDataSourceConfig) {
        this.dynamicDataSourceConfig = dynamicDataSourceConfig;
    }

    @Override
    public Result getTableDiagram(Long dbId,String dbName){
        Map<String, Object> re = new HashMap<>();
        try {
            re.put("tables", getTables(dynamicDataSourceConfig.getDataSource(String.valueOf(dbId)).getConnection(),dbName));
            re.put("relationships", getRelationships(dynamicDataSourceConfig.getDataSource(String.valueOf(dbId)).getConnection(),dbName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Result.success(re);
    }

    public Map<String, List<Map<String, Object>>> getTables(Connection connection, String databaseName) {
        Map<String, List<Map<String, Object>>> tablesInfo = new HashMap<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tablesResultSet = metaData.getTables(databaseName, null, "%", new String[]{"TABLE"});

            while (tablesResultSet.next()) {
                String tableName = tablesResultSet.getString("TABLE_NAME");
                List<Map<String, Object>> columns = new ArrayList<>();

                // 获取列信息
                ResultSet columnsResultSet = metaData.getColumns(databaseName, null, tableName, "%");
                while (columnsResultSet.next()) {
                    Map<String, Object> columnInfo = new HashMap<>();
                    String columnName = columnsResultSet.getString("COLUMN_NAME");
                    String columnType = columnsResultSet.getString("TYPE_NAME");

                    columnInfo.put("name", columnName);
                    columnInfo.put("type", columnType);
                    columnInfo.put("primary", false); // 默认不是主键

                    columns.add(columnInfo);
                }

                // 获取主键信息
                ResultSet primaryKeysResultSet = metaData.getPrimaryKeys(databaseName, null, tableName);
                while (primaryKeysResultSet.next()) {
                    String primaryKeyColumn = primaryKeysResultSet.getString("COLUMN_NAME");

                    // 更新对应列的primary属性
                    for (Map<String, Object> column : columns) {
                        if (column.get("name").equals(primaryKeyColumn)) {
                            column.put("primary", true);
                        }
                    }
                }

                tablesInfo.put(tableName, columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tablesInfo;
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
