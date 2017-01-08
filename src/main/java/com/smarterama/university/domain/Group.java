package com.smarterama.university.domain;

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
@Table(name = "groups")
public class Group {
    private static Logger logger = LoggerFactory.getLogger(Group.class);

    @Id @Column @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private int groupNumber;

    @Transient
    @Autowired
    private GenericDAO<Group, Integer> groupDAO;


    public Group(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Group() {
    }

    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
        this.groupNumber = Integer.parseInt(parameterMap.get("group_number")[0]);
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Transactional
    public void persist() throws PersistenceException {
        groupDAO.saveOrUpdate(this);
    }

    @Transactional
    public void delete() throws PersistenceException {
        groupDAO.delete(this);
    }

    @Transactional
    public Group retrieve() throws PersistenceException {
        Group readGroup = groupDAO.get(Group.class, id);
        groupNumber = readGroup.getGroupNumber();
        return this;
    }

    @Transactional
    public List<Group> getAll() throws PersistenceException {
        List<Group> groupList = null;
        try {
            groupList = groupDAO.getAll(Group.class);
        } catch (HibernateException e) {
            logger.error("Error getting groups list", e);
            throw e;
        }
        return groupList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupNumber == group.groupNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupDAO=" + groupDAO +
                ", id=" + id +
                ", groupNumber=" + groupNumber +
                '}';
    }
}
