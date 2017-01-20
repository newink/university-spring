package com.smarterama.university.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smarterama.university.dao.GenericDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configurable(autowire = Autowire.BY_TYPE)
@Entity
@Table(name = "disciplines")
public class Discipline implements DomainObject {
    private static Logger logger = LoggerFactory.getLogger(Discipline.class);

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private TestType finalExamType;

    @Transient
    @JsonIgnore
    @Autowired
    private GenericDAO<Discipline, Integer> disciplineDAO;


    public Discipline(String name, TestType finalExamType) {
        this.name = name;
        this.finalExamType = finalExamType;
    }

    public Discipline() {
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestType getFinalExamType() {
        return finalExamType;
    }

    public void setFinalExamType(TestType finalExamType) {
        this.finalExamType = finalExamType;
    }

    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
        this.name = parameterMap.get("name")[0];
        this.finalExamType = TestType.valueOf(parameterMap.get("test_type")[0].toUpperCase());
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : null;
    }

    @Transactional
    public void persist() throws PersistenceException {
        disciplineDAO.saveOrUpdate(this);
    }

    @Transactional
    public void delete() throws PersistenceException {
        disciplineDAO.delete(this);
    }

    @Transactional(readOnly = true)
    public Discipline retrieve() throws PersistenceException {
        Discipline readDiscipline = disciplineDAO.get(Discipline.class, id);
        name = readDiscipline.getName();
        finalExamType = readDiscipline.getFinalExamType();
        return this;
    }

    @Transactional(readOnly = true)
    public List<Discipline> collectAll() throws PersistenceException {
        List<Discipline> disciplineList = null;
        try {
            disciplineList = disciplineDAO.getAll(Discipline.class);
        } catch (HibernateException e) {
            logger.error("Error getting disciplines list", e);
            throw e;
        }
        return disciplineList;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "disciplineDAO=" + disciplineDAO +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", finalExamType=" + finalExamType +
                '}';
    }

    public enum TestType {
        TEST, EXAM, ESSAY
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
