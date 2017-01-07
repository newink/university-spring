package com.smarterama.university.dao;

import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public abstract class AbstractJDBCDao<T extends Identified> implements GenericDAO<T> {
    protected DataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(AbstractJDBCDao.class);

    protected abstract String getSelectQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getInsertQuery();

    protected abstract String getTableName();

    protected abstract void prepareUpdateStatement(PreparedStatement statement, T object) throws PersistenceException;

    protected abstract void prepareInsertStatement(PreparedStatement statement, T object) throws PersistenceException;

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws PersistenceException;


    @Override
    public int persist(T object) throws PersistenceException {
        String query = getInsertQuery();
        int count;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            prepareInsertStatement(statement, object);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving DB record: Cause: {}, Object parameters: {}", e, object);
            throw new PersistenceException("Error saving DB record", e);
        }
        return count;
    }

    @Override
    public T read(int key) throws PersistenceException {
        List<T> resultList;
        String query = String.format("%s WHERE %s.id = ?", getSelectQuery(), getTableName());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting DB record: Cause: {}, Key: {}", e, key);
            throw new PersistenceException("Error getting record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public int update(T object) throws PersistenceException {
        String query = getUpdateQuery();
        int count;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            prepareUpdateStatement(statement, object);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating DB record: Cause: {}, Object parameters: {}", e, object);
            throw new PersistenceException("Error updating DB record", e);
        }
        return count;
    }

    @Override
    public int delete(T object) throws PersistenceException {
        String query = getDeleteQuery();
        int count;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, object.getId());
            count = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting DB record: Cause: {}, Object parameters: {}", e, object);
            throw new PersistenceException("Problem deleting DB record", e);
        }
        return count;
    }

    @Override
    public List<T> findAll() throws PersistenceException {
        List<T> resultList;
        String query = getSelectQuery();
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting all records from DB", e);
            throw new PersistenceException("Problem getting all records from table", e);
        }
        return resultList;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
