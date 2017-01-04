package com.smarterama.university.dao;

import com.smarterama.university.exceptions.PersistenceException;

import java.sql.Connection;

public interface ConnectionFactory {
    public Connection getConnection() throws PersistenceException;
}
