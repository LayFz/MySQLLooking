package com.staraid.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.*;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class SQLiteDatabaseTest {


    @Test
    public void testDatabaseConnection() {
// SQLite connection string
        String url = "jdbc:sqlite:./MySQLLooking.db";

        // SQL statement for creating a new table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " email text NOT NULL\n"
                + ");";

        // SQL statement for inserting data
        String insertSQL = "INSERT INTO users(name, email) VALUES('John Doe', 'john.doe@example.com');";

        // SQL statement for selecting data
        String selectSQL = "SELECT id, name, email FROM users;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Create table
            stmt.execute(createTableSQL);

            // Insert data
            stmt.execute(insertSQL);

            // Select data
            ResultSet rs = stmt.executeQuery(selectSQL);

            // Loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
