package com.staraid.mysql.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 数据库配置
 */
@Configuration
@Log4j2
public class DatabaseConfig {
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @PostConstruct
    public void initializeDatabase() {

        // Extract the file path from the JDBC URL
        String dbFilePath = dataSourceUrl.replace("jdbc:sqlite:", "").trim();
        File dbFile = new File(dbFilePath);

        if (!dbFile.exists()) {
            log.info("数据库文件不存在，开始初始化...");
            try {
                // Create the database file
                if (dbFile.getParentFile() != null) {
                    dbFile.getParentFile().mkdirs();
                }
                dbFile.createNewFile();
                // Populate the database with initial schema
                Resource initSchema = new ClassPathResource("db.sql");
                DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);

                // Create a DataSource
                DataSource dataSource = createDataSource(dataSourceUrl);

                // Execute the script
                DatabasePopulatorUtils.execute(databasePopulator, dataSource);

                log.info("数据库初始化完成！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("初始化已完成！");
        }
    }

    private DataSource createDataSource(String url) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName("org.sqlite.JDBC");
        return dataSource;
    }
}
