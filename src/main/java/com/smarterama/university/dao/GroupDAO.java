package com.smarterama.university.dao;

import com.smarterama.university.domain.Group;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractJDBCDao<Group> {

    private static Logger logger = LoggerFactory.getLogger(GroupDAO.class);

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM groups";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE groups SET group_number = ? WHERE id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM groups WHERE id = ?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO groups (group_number) VALUES (?)";
    }

    @Override
    protected String getTableName() {
        return "groups";
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Group group) throws PersistenceException {
        prepareInsertStatement(statement, group);
        try {
            statement.setInt(2, group.getId());
        } catch (SQLException e) {
            logger.error("Error preparing groups update/insert statement", e);
            throw new PersistenceException("Error preparing groups insert statement", e);
        }
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Group group) throws PersistenceException {
        try {
            statement.setInt(1, group.getGroupNumber());
        } catch (SQLException e) {
            logger.error("Error preparing groups update/insert statement", e);
            throw new PersistenceException("Error preparing groups update statement", e);
        }
    }

    @Override
    protected List<Group> parseResultSet(ResultSet resultSet) throws PersistenceException {
        List<Group> groupList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Group group = new Group();
                int id = resultSet.getInt("id");
                group.setId(id);
                group.setGroupNumber(resultSet.getInt("group_number"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            logger.error("Error parsing groups result set", e);
            throw new PersistenceException("Problem parsing groups result set", e);
        }
        return groupList;
    }
}
