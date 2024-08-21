package com.staraid.mysql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 数据源绑定
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName; // 确保 SQLite 驱动配置



    /**
     * 创建 SQLite 数据源
     * @return 配置好的 SQLite 数据源
     */
    @Bean(name = "localDataSource")
    @Primary  // 标注为主数据源
    public DataSource localDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceUrl);
        return dataSource;
    }
}