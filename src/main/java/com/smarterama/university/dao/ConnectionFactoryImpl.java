package com.smarterama.university.dao;

import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactoryImpl.class);
    private String user;
    private String password;
    private String url;
    private String driver;

    public ConnectionFactoryImpl() {
        Properties databaseProperties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
            databaseProperties.load(inputStream);
        } catch (IOException e) {
            logger.error("Can't load properties file!", e);
        }

        user = databaseProperties.getProperty("user");
        password = databaseProperties.getProperty("password");
        url = databaseProperties.getProperty("url");
        driver = databaseProperties.getProperty("driver");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("No lib in class path!", e);
        }
    }

    @Override
    public Connection getConnection() throws PersistenceException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Error getting connection", e);
            throw new PersistenceException("Error getting connection", e);
        }
        return connection;
    }
}
