package com.smarterama.university.domain;
import com.smarterama.university.exceptions.PersistenceException;

import java.util.List;

public interface DomainObject {
    void setId(int id);

    int getId();

    int persist() throws PersistenceException;

    int update() throws PersistenceException;

    int delete() throws PersistenceException;

    DomainObject retrieve() throws PersistenceException;

    List<? extends DomainObject> getAll() throws PersistenceException;
}
