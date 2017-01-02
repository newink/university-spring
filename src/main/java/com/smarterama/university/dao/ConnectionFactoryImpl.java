package com.smarterama.university.dao;

import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactoryImpl.class);
    private DataSource dataSource;

    public ConnectionFactoryImpl() {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
        } catch (NamingException e) {
            logger.error("Can not resolve datasource: ", e);
        }
    }

    @Override
    public Connection getConnection() throws PersistenceException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error getting connection", e);
            throw new PersistenceException("Error getting connection", e);
        }
        return connection;
    }
}
