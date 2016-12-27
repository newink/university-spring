package com.smarterama.university.dao;

import com.smarterama.university.domain.Discipline;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class    DisciplineDAO extends AbstractJDBCDao<Discipline> {

    private static Logger logger = LoggerFactory.getLogger(DisciplineDAO.class);

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM disciplines";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE disciplines SET name = ?, test_type = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM disciplines WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO disciplines (name, test_type) VALUES (?, ?);";
    }

    @Override
    protected String getTableName() {
        return "disciplines";
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Discipline object) throws PersistenceException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getFinalExamType().name());
        } catch (SQLException e) {
            logger.error("Error preparing discipline update/insert statement", e);
            throw new PersistenceException("Problem preparing update statement", e);
        }
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Discipline object) throws PersistenceException {
        prepareUpdateStatement(statement, object);
    }

    @Override
    protected List<Discipline> parseResultSet(ResultSet resultSet) throws PersistenceException {
        List<Discipline> List = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Discipline addedDiscipline = new Discipline();
                addedDiscipline.setId(resultSet.getInt("id"));
                addedDiscipline.setFinalExamType(resultSet.getString("test_type"));
                addedDiscipline.setName(resultSet.getString("name"));
                List.add(addedDiscipline);
            }
        } catch (SQLException e) {
            logger.error("Error parsing discipline result set", e);
            throw new PersistenceException("Problem parsing result set", e);
        }
        return List;
    }
}
