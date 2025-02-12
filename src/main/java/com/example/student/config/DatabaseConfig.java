package com.example.student.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final String CONFIG_FILE = "database.properties";
    private static String url;
    private static String username;
    private static String password;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.error("Unable to find " + CONFIG_FILE);
                throw new RuntimeException("Unable to find " + CONFIG_FILE);
            }
            props.load(input);

            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");


            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (IOException e) {
            logger.error("Error loading database configuration", e);
            throw new RuntimeException("Error loading database configuration", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            if (url == null || username == null || password == null) {
                throw new RuntimeException("Missing required database configuration");
            }
            Connection conn = DriverManager.getConnection(url, username, password);
            logger.debug("Database connection established");
            return conn;
        } catch (SQLException e) {
            logger.error("Error establishing database connection", e);
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.debug("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }
}