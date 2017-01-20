package com.smarterama.university.domain;

import com.smarterama.university.exceptions.PersistenceException;

import java.util.List;

public interface DomainObject {
    void setId(Integer id);

    int getId();

    void persist() throws PersistenceException;

    void delete() throws PersistenceException;

    DomainObject retrieve() throws PersistenceException;

    List<? extends DomainObject> collectAll() throws PersistenceException;
}
