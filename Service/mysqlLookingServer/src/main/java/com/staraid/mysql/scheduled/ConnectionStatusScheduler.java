package com.staraid.mysql.scheduled;

import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Component
public class ConnectionStatusScheduler {
    private final DynamicDataSourceConfig dataSourceConfig;

    public ConnectionStatusScheduler(DynamicDataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Scheduled(fixedRate = 12000) // 每60秒检查一次
    public void checkConnectionStatuses() {
        for (Map.Entry<Object, Object> entry : dataSourceConfig.getDataSources().entrySet()) {
            HikariDataSource dataSource = (HikariDataSource) entry.getValue();
            String status = checkConnectionStatus(dataSource);
            System.out.println("Database " + entry.getKey() + " is " + status);
        }
    }

    private String checkConnectionStatus(HikariDataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeQuery("SELECT 1");
            return "online";
        } catch (SQLException e) {
            return "offline";
        }
    }
}
