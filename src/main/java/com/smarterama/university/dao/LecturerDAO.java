package com.smarterama.university.dao;

import com.smarterama.university.domain.Discipline;
import com.smarterama.university.domain.Lecturer;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LecturerDAO extends AbstractJDBCDao<Lecturer> {

    private static Logger logger = LoggerFactory.getLogger(LecturerDAO.class);

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM lecturers";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE lecturers SET first_name = ?, last_name = ?, email = ?, degree = ? WHERE id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM lecturers WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO lecturers (first_name, last_name, email, degree) VALUES (?, ?, ?, ?);";
    }

    @Override
    protected String getTableName() {
        return "lecturers";
    }

    @Override
    public int persist(Lecturer lecturer) throws PersistenceException {
        String query = getInsertQuery();
        String deleteQuery = "DELETE FROM lecturers_disciplines WHERE lecturer_id = ?;";
        String disciplinesQuery = "INSERT INTO lecturers_disciplines (lecturer_id, discipline_id)" +
                " VALUES (?, ?);";
        int count;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
             PreparedStatement disciplineStatement = connection.prepareStatement(disciplinesQuery)) {

            prepareInsertStatement(statement, lecturer);
            count = statement.executeUpdate();

            deleteStatement.setInt(1, lecturer.getId());
            deleteStatement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                lecturer.setId(generatedKeys.getInt(1));
            }
            deleteStatement.close();

            connection.setAutoCommit(false);
            for (Discipline discipline : lecturer.getDisciplines()) {
                disciplineStatement.setInt(1, lecturer.getId());
                disciplineStatement.setInt(2, discipline.getId());
                disciplineStatement.addBatch();
            }
            disciplineStatement.executeBatch();
            connection.setAutoCommit(true);
            disciplineStatement.close();
        } catch (SQLException e) {
            logger.error("Error creating lecturer record: Cause: {}, Object parameters: {}", e, lecturer);
            throw new PersistenceException("Error creating lecturer record", e);
        }
        return count;
    }

    @Override
    public int update(Lecturer lecturer) throws PersistenceException {
        String query = getUpdateQuery();
        String deleteQuery = "DELETE FROM lecturers_disciplines WHERE lecturer_id = ?;";
        String disciplinesQuery = "INSERT INTO lecturers_disciplines (lecturer_id, discipline_id)" +
                " VALUES (?, ?);";
        int count;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
             PreparedStatement disciplineStatement = connection.prepareStatement(disciplinesQuery)) {

            prepareUpdateStatement(statement, lecturer);
            count = statement.executeUpdate();

            deleteStatement.setInt(1, lecturer.getId());
            deleteStatement.executeUpdate();
            deleteStatement.close();

            for (Discipline discipline : lecturer.getDisciplines()) {
                disciplineStatement.setInt(1, lecturer.getId());
                disciplineStatement.setInt(2, discipline.getId());
                disciplineStatement.addBatch();
            }
            disciplineStatement.executeBatch();
            disciplineStatement.close();
        } catch (SQLException e) {
            logger.error("Error updating lecturer record: Cause: {}, Object parameters: {}", e, lecturer);
            throw new PersistenceException("Error updating lecturer record", e);
        }
        return count;
    }

    @Override
    public Lecturer read(int key) throws PersistenceException {
        List<Lecturer> lecturerList = new ArrayList<>();
        String query = String.format("%s WHERE %s.id = ?", getSelectQuery(), getTableName());
        String disciplinesQuery = "SELECT * FROM disciplines INNER JOIN lecturers_disciplines " +
                "ON lecturers_disciplines.discipline_id = disciplines.id " +
                "WHERE lecturers_disciplines.lecturer_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement disciplinesStatement = connection.prepareStatement(disciplinesQuery);) {

            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lecturer addedLecturer = new Lecturer();
                addedLecturer.setId(resultSet.getInt("id"));
                addedLecturer.setDegree(resultSet.getString("degree"));
                addedLecturer.setEmail(resultSet.getString("email"));
                addedLecturer.setFirstName(resultSet.getString("first_name"));
                addedLecturer.setLastName(resultSet.getString("last_name"));


                disciplinesStatement.setInt(1, addedLecturer.getId());
                ResultSet disciplinesResultSet = disciplinesStatement.executeQuery();
                while (disciplinesResultSet.next()) {
                    String testType = disciplinesResultSet.getString("test_type");
                    Discipline discipline = new Discipline(disciplinesResultSet.getString("name"), Discipline.TestType.valueOf(testType));
                    addedLecturer.addDiscipline(discipline);
                }
                disciplinesResultSet.close();
                disciplinesStatement.close();
                lecturerList.add(addedLecturer);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lecturer record: Cause: {}, Key: {}", e, key);
            throw new PersistenceException("Error getting lecturer record", e);
        }
        if (lecturerList.isEmpty()) {
            return null;
        }
        return lecturerList.get(0);
    }

    @Override
    public List<Lecturer> findAll() throws PersistenceException {
        List<Lecturer> lecturerList = new ArrayList<>();
        String query = getSelectQuery();
        String disciplinesQuery = "SELECT * FROM disciplines INNER JOIN lecturers_disciplines " +
                "ON lecturers_disciplines.discipline_id = disciplines.id " +
                "WHERE lecturers_disciplines.lecturer_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lecturer addedLecturer = new Lecturer();
                addedLecturer.setId(resultSet.getInt("id"));
                addedLecturer.setDegree(resultSet.getString("degree"));
                addedLecturer.setEmail(resultSet.getString("email"));
                addedLecturer.setFirstName(resultSet.getString("first_name"));
                addedLecturer.setLastName(resultSet.getString("last_name"));


                PreparedStatement disciplinesStatement = connection.prepareStatement(disciplinesQuery);
                disciplinesStatement.setInt(1, addedLecturer.getId());
                ResultSet disciplinesResultSet = disciplinesStatement.executeQuery();
                while (disciplinesResultSet.next()) {
                    String testType = disciplinesResultSet.getString("test_type");
                    Discipline discipline = new Discipline(disciplinesResultSet.getString("name"), Discipline.TestType.valueOf(testType));
                    addedLecturer.addDiscipline(discipline);
                }
                disciplinesResultSet.close();
                disciplinesStatement.close();
                lecturerList.add(addedLecturer);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lecturer record", e);
            throw new PersistenceException("Error getting lecturer record", e);
        }
        if (lecturerList.isEmpty()) {
            return null;
        }
        return lecturerList;
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Lecturer lecturer) throws PersistenceException {
        prepareInsertStatement(statement, lecturer);
        try {
            statement.setInt(5, lecturer.getId());
        } catch (SQLException e) {
            logger.error("Error preparing lecturer insert statement", e);
            throw new PersistenceException("Error preparing lecturer insert statement", e);
        }
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Lecturer lecturer) throws PersistenceException {
        try {
            statement.setString(1, lecturer.getFirstName());
            statement.setString(2, lecturer.getLastName());
            statement.setString(3, lecturer.getEmail());
            statement.setString(4, lecturer.getDegree().name());
        } catch (SQLException e) {
            logger.error("Error preparing lecturer update statement", e);
            throw new PersistenceException("Error preparing lecturer update statement", e);
        }
    }

    @Override
    protected List<Lecturer> parseResultSet(ResultSet resultSet) throws PersistenceException {
        return null;
    }
}
