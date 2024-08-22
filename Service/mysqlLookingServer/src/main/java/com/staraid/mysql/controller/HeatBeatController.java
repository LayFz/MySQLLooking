package com.staraid.mysql.controller;


import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.staraid.mysql.pojo.DatabaseStatus;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HeatBeatController {
    private final DynamicDataSourceConfig dataSourceConfig;

    public HeatBeatController(DynamicDataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @GetMapping("/database-statuses")
    public List<DatabaseStatus> getDatabaseStatuses() {
        List<DatabaseStatus> statuses = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : dataSourceConfig.getDataSources().entrySet()) {
            String key = (String) entry.getKey();
            HikariDataSource dataSource = (HikariDataSource) entry.getValue();
            String status = checkConnectionStatus(dataSource);
            statuses.add(new DatabaseStatus(key, status));
        }

        return statuses;
    }

    private String checkConnectionStatus(HikariDataSource dataSource) {
        // 测试连接
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeQuery("SELECT 1");
            return "online";
        } catch (SQLException e) {
            return "offline";
        }
    }

}
