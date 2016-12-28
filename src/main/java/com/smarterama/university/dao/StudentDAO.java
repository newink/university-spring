package com.smarterama.university.dao;

import com.smarterama.university.domain.Group;
import com.smarterama.university.domain.Student;
import com.smarterama.university.exceptions.PersistenceException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentDAO extends AbstractJDBCDao<Student> {
    private static final Logger logger = LoggerFactory.getLogger(StudentDAO.class);

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM students INNER JOIN groups ON students.group_id = groups.id";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE students SET first_name = ?, last_name = ?, course = ?, address = ?, subsidized = ?, group_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM students WHERE id = ?";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO students (first_name, last_name, course, address, subsidized, group_id) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
    }

    @Override
    protected String getTableName() {
        return "students";
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Student student) throws PersistenceException {
        prepareInsertStatement(statement, student);
        try {
            statement.setInt(7, student.getId());
        } catch (SQLException e) {
            logger.error("Error preparing student update/insert statement", e);
            throw new PersistenceException("Error preparing student update statement", e);
        }
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Student student) throws PersistenceException {
        try {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getCourse());
            statement.setString(4, student.getAddress());
            statement.setBoolean(5, student.isSubsidized());
            statement.setInt(6, student.getGroup().getId());
        } catch (SQLException e) {
            logger.error("Error preparing student update/insert statement", e);
            throw new PersistenceException("Error preparing student update statement", e);
        }
    }

    @Override
    protected List<Student> parseResultSet(ResultSet resultSet) throws PersistenceException {
        List<Student> studentsList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Student student = new Student();
                Group group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setGroupNumber(resultSet.getInt("group_number"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setGroup(group);
                student.setId(resultSet.getInt("id"));
                student.setCourse(resultSet.getInt("course"));
                student.setAddress(resultSet.getString("address"));
                student.setSubsidized(resultSet.getBoolean("subsidized"));
                studentsList.add(student);
            }
        } catch (SQLException e) {
            logger.error("Error parsing student result set", e);
            throw new PersistenceException("Error parsing students result set", e);
        }
        return studentsList;
    }
}
