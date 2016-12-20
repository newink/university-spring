package com.smarterama.university.dao;

import com.smarterama.university.exceptions.PersistenceException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    public Connection getConnection() throws PersistenceException;
}
