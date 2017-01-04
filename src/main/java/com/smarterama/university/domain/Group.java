package com.smarterama.university.domain;

import com.smarterama.university.dao.GroupDAO;
import com.smarterama.university.dao.Identified;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Group implements Identified {
    private static Logger logger = LoggerFactory.getLogger(Group.class);
    private GroupDAO groupDAO;
    private int id;
    private int groupNumber;

    public Group(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Group() {
    }

    public Group(Map<String, String[]> parameterMap) {
        this.groupNumber = Integer.parseInt(parameterMap.get("group_number")[0]);
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : -1;
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

    public int persist() throws PersistenceException {
        return getDAO().persist(this);
    }

    public int update() throws PersistenceException {
        return getDAO().update(this);
    }

    public int delete() throws PersistenceException {
        return getDAO().delete(this);
    }

    public Group retrieve() throws PersistenceException {
        Group readGroup = getDAO().read(id);
        groupNumber = readGroup.getGroupNumber();
        return this;
    }

    public List<Group> getAll() throws PersistenceException {
        List<Group> groupList = null;
        try {
            groupList = getDAO().findAll();
        } catch (PersistenceException e) {
            logger.error("Error getting groups list", e);
            throw e;
        }
        return groupList;
    }

    private GroupDAO getDAO() {
        if (groupDAO == null) {
            groupDAO = new GroupDAO();
        }
        return groupDAO;
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
