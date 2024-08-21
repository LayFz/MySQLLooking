package com.staraid.mysql.config;

import com.staraid.mysql.holder.DatabaseContextHolder;
import com.staraid.mysql.pojo.Database;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Component
public class DynamicDataSourceConfig extends AbstractRoutingDataSource {

    private final Map<Object, Object> dataSources = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDataSourceKey();
    }

    @Override
    public void afterPropertiesSet() {
        setTargetDataSources(dataSources);
        super.afterPropertiesSet();
    }

    public DataSource getDataSource(String key) {
        return (DataSource) dataSources.get(key);
    }

    // 加锁操作，避免用户的行为
    public synchronized String addDataSource(String key, Database config) {
        // 如果数据源已存在，直接返回
        if (hasDataSource(key)) {
            return "EXISTS"; // 表示已存在，不再重复创建
        }
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:"+config.getDbType()+"://"+config.getDbUrl()+":"+config.getDbPort()+"?serverTimezone=UTC");
        dataSource.setUsername(config.getDbName());
        dataSource.setPassword(config.getDbPassword());
        dataSource.setDriverClassName(config.getDbDriver());
        dataSource.setConnectionTimeout(3000);// 超时时间3s

        try {
            // 测试连接
            try (Connection connection = dataSource.getConnection()) {
                if (connection != null && connection.isValid(5)) {
                    System.out.println("Connection successful for key: " + key);
                } else {
                    System.err.println("Failed to connect for key: " + key);
                }
            }
        } catch (SQLException e) {
            // 判断错误类型
            if ("08S01".equals(e.getSQLState())) { // 通常用于网络错误
                return "NET_WORK_BAD";
            } else if ("28000".equals(e.getSQLState())) { // 身份验证错误
                return "UN_AUTH";
            } else {
                System.err.println("Failed to connect for key: " + key + ", Error: " + e.getMessage());
            }
        }

        // 将数据源添加到管理集合中
        dataSources.put(key, dataSource);
        afterPropertiesSet();  // 刷新数据源
        return "SUCCESS";
    }

    public boolean hasDataSource(String key) {
        return dataSources.containsKey(key);
    }

    public synchronized void removeDataSource(String key) {
        if (dataSources.containsKey(key)) {
            HikariDataSource dataSource = (HikariDataSource) dataSources.get(key);
            dataSource.close();  // 关闭连接池
            dataSources.remove(key);  // 从Map中移除
            afterPropertiesSet();  // 刷新数据源
        }
    }






}
