package com.smarterama.university.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class GenericDAO<T, PK extends Serializable> {
    
    @Autowired
    private SessionFactory sessionFactory;

    public void save(T o){
        getSession().save(o);
    }

    public void delete(Object object){
        getSession().delete(object);
    }

    public T get(Class<T> type, PK id){
        return getSession().get(type, id);
    }

    public void saveOrUpdate(T o){
        getSession().saveOrUpdate(o);
    }

    public List<T> getAll(final Class<T> type) {
        return getSession().createQuery("from " + type.getName()).getResultList();
    }
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
