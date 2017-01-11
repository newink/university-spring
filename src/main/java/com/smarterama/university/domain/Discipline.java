package com.smarterama.university.domain;

import com.smarterama.university.dao.DisciplineDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configurable(autowire = Autowire.BY_TYPE)
public class Discipline implements DomainObject {

    private static Logger logger = LoggerFactory.getLogger(Discipline.class);
    private int id;
    private String name;
    private TestType finalExamType;

    @Autowired
    private DisciplineDAO disciplineDAO;


    public Discipline(String name, TestType finalExamType) {
        this.name = name;
        this.finalExamType = finalExamType;
    }

    public Discipline() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setFinalExamType(String finalExamType) {
        this.finalExamType = TestType.valueOf(finalExamType.toUpperCase());
    }

    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
        this.name = parameterMap.get("name")[0];
        this.finalExamType = TestType.valueOf(parameterMap.get("test_type")[0].toUpperCase());
    }

    public int persist() throws PersistenceException {
        return disciplineDAO.persist(this);
    }

    public int update() throws PersistenceException {
        return disciplineDAO.update(this);
    }

    public int delete() throws PersistenceException {
        return disciplineDAO.delete(this);
    }

    public Discipline retrieve() throws PersistenceException {
        Discipline readDiscipline = disciplineDAO.read(id);
        name = readDiscipline.getName();
        finalExamType = readDiscipline.getFinalExamType();
        return this;
    }

    public List<Discipline> getAll() throws PersistenceException {
        List<Discipline> disciplineList = null;
        try {
            disciplineList = disciplineDAO.findAll();
        } catch (PersistenceException e) {
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
