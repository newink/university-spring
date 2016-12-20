package com.smarterama.university.dao;

import com.smarterama.university.exceptions.PersistenceException;

import java.util.List;

public interface GenericDAO<T> {
    public int persist(T object) throws PersistenceException;

    public T read(int key) throws PersistenceException;

    public int update(T object) throws PersistenceException;

    public int delete(T object) throws PersistenceException;

    public List<T> findAll() throws PersistenceException;
}
