package com.staraid.mysql;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executors;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CertiTest {
    @Test
    void contextLoads() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "root");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306?serverTimezone=UTC", properties)) {
            // 设置超时时间为5秒
            connection.setNetworkTimeout(Executors.newSingleThreadExecutor(), 5000);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.err.println("Failed to connect, Error: " + e.getMessage());
        }
    }


}
