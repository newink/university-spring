package com.smarterama.university.dao;

import com.smarterama.university.domain.*;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class LessonDAO extends AbstractJDBCDao<Lesson> {

    private static Logger logger = LoggerFactory.getLogger(LessonDAO.class);

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM lessons INNER JOIN rooms on lessons.room_id = rooms.id " +
                "INNER JOIN lecturers ON lessons.lecturer_id = lecturers.id INNER JOIN groups ON lessons.group_id = groups.id " +
                "INNER JOIN disciplines ON lessons.discipline_id = disciplines.id";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE lessons " +
                "SET room_id = ?, lecturer_id = ?, group_id = ?, discipline_id = ?, start_time = ?, finish_time = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM lessons WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO lessons (room_id, lecturer_id, group_id, discipline_id, start_time, finish_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getTableName() {
        return "lessons";
    }

    public List<Lesson> collectLessons(Group group, Date date) throws PersistenceException {
        List<Lesson> resultList;
        String query = "SELECT * FROM lessons " +
                "INNER JOIN rooms ON lessons.room_id = rooms.id " +
                "INNER JOIN groups ON group_id = groups.id " +
                "INNER JOIN lecturers ON lecturer_id = lecturers.id " +
                "INNER JOIN disciplines ON discipline_id = disciplines.id " +
                "WHERE group_id = ? AND DATE(start_time) = DATE(?)";
        try  (Connection connection = connectionFactory.getConnection();
              PreparedStatement statement  = connection.prepareStatement(query))  {

            statement.setInt(1, group.getId());
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lesson record from DB", e);
            throw new PersistenceException("Error getting lesson record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    public List<Lesson> collectLessons(Lecturer lecturer, Date date) throws PersistenceException {
        List<Lesson> resultList;
        String query = "SELECT * FROM lessons " +
                "INNER JOIN rooms ON lessons.room_id = rooms.id " +
                "INNER JOIN groups ON group_id = groups.id " +
                "INNER JOIN lecturers ON lecturer_id = lecturers.id " +
                "INNER JOIN disciplines ON discipline_id = disciplines.id " +
                "WHERE lecturer_id = ? AND DATE(start_time) = DATE(?)";
        try  (Connection connection = connectionFactory.getConnection();
              PreparedStatement statement  = connection.prepareStatement(query))  {

            statement.setInt(1, lecturer.getId());
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lesson record from DB", e);
            throw new PersistenceException("Error getting lesson record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    public List<Lesson> collectLessons(Room room) throws PersistenceException {
        List<Lesson> resultList;
        String query = "SELECT * FROM lessons " +
                "INNER JOIN rooms ON lessons.room_id = rooms.id " +
                "INNER JOIN groups ON group_id = groups.id " +
                "INNER JOIN lecturers ON lecturer_id = lecturers.id " +
                "INNER JOIN disciplines ON discipline_id = disciplines.id " +
                "WHERE room_id = ?";
        try  (Connection connection = connectionFactory.getConnection();
              PreparedStatement statement  = connection.prepareStatement(query))  {

            statement.setInt(1, room.getId());
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lesson record from DB", e);
            throw new PersistenceException("Error getting lesson record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    public List<Lesson> collectLessons(Group group, Date startDate, Date finishDate) throws PersistenceException {
        List<Lesson> resultList;
        String query = "SELECT * FROM lessons " +
                "INNER JOIN rooms ON lessons.room_id = rooms.id " +
                "INNER JOIN groups ON group_id = groups.id " +
                "INNER JOIN lecturers ON lecturer_id = lecturers.id " +
                "INNER JOIN disciplines ON discipline_id = disciplines.id " +
                "WHERE group_id = ? AND start_time >= ? AND start_time <= ?";
        try  (Connection connection = connectionFactory.getConnection();
              PreparedStatement statement  = connection.prepareStatement(query))  {

            statement.setInt(1, group.getId());
            statement.setTimestamp(2, new Timestamp(startDate.getTime()));
            statement.setTimestamp(3, new Timestamp(finishDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lesson record from DB", e);
            throw new PersistenceException("Error getting lesson record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    public List<Lesson> collectLessons(Lecturer lecturer, Date startDate, Date finishDate) throws PersistenceException {
        List<Lesson> resultList;
        String query = "SELECT * FROM lessons " +
                "INNER JOIN rooms ON lessons.room_id = rooms.id " +
                "INNER JOIN groups ON group_id = groups.id " +
                "INNER JOIN lecturers ON lecturer_id = lecturers.id " +
                "INNER JOIN disciplines ON discipline_id = disciplines.id " +
                "WHERE lecturer_id = ? AND start_time >= ? AND start_time <= ?";
        try  (Connection connection = connectionFactory.getConnection();
              PreparedStatement statement  = connection.prepareStatement(query))  {

            statement.setInt(1, lecturer.getId());
            statement.setTimestamp(2, new Timestamp(startDate.getTime()));
            statement.setTimestamp(3, new Timestamp(finishDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lesson record from DB", e);
            throw new PersistenceException("Error getting lesson record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    public List<Lesson> collectLessons(Date startDate, Date finishDate) throws PersistenceException {
        List<Lesson> resultList;
        String query = "SELECT * FROM lessons " +
                "INNER JOIN rooms ON lessons.room_id = rooms.id " +
                "INNER JOIN groups ON group_id = groups.id " +
                "INNER JOIN lecturers ON lecturer_id = lecturers.id " +
                "INNER JOIN disciplines ON discipline_id = disciplines.id " +
                "WHERE start_time >= ? AND start_time <= ?";
        try  (Connection connection = connectionFactory.getConnection();
              PreparedStatement statement  = connection.prepareStatement(query))  {

            statement.setTimestamp(1, new Timestamp(startDate.getTime()));
            statement.setTimestamp(2, new Timestamp(finishDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultList = parseResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            logger.error("Error getting lesson record from DB", e);
            throw new PersistenceException("Error getting lesson record from DB", e);
        }
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList;
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Lesson lesson) throws PersistenceException {
        try {
            statement.setInt(1, lesson.getRoom().getId());
            statement.setInt(2, lesson.getLecturer().getId());
            statement.setInt(3, lesson.getGroup().getId());
            statement.setInt(4, lesson.getDiscipline().getId());
            statement.setTimestamp(5, new Timestamp(lesson.getStartDate().getTime()));
            statement.setTimestamp(6, new Timestamp(lesson.getFinishDate().getTime()));
        } catch (SQLException e) {
            logger.error("Error preparing lesson update statement", e);
            throw new PersistenceException("Error preparing lesson update statement", e);
        }
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Lesson lesson) throws PersistenceException {
        prepareUpdateStatement(statement, lesson);
    }

    @Override
    protected List<Lesson> parseResultSet(ResultSet resultSet) throws PersistenceException {
        List<Lesson> lessonList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Group group = new Group(resultSet.getInt("group_number"));
                group.setId(resultSet.getInt("group_id"));

                Room room = new Room(resultSet.getInt("capacity"), resultSet.getInt("room_number"));
                room.setId(resultSet.getInt("room_id"));

                Discipline discipline = new Discipline(resultSet.getString("name"), Discipline.TestType.valueOf(resultSet.getString("test_type")));
                discipline.setId(resultSet.getInt("discipline_id"));

                Lecturer lecturer = new Lecturer();
                lecturer.setId(resultSet.getInt("lecturer_id"));
                lecturer.setFirstName(resultSet.getString("first_name"));
                lecturer.setLastName(resultSet.getString("last_name"));
                lecturer.setEmail(resultSet.getString("email"));
                lecturer.setDegree(resultSet.getString("degree"));

                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setStartDate(resultSet.getTimestamp("start_time"));
                lesson.setFinishDate(resultSet.getTimestamp("finish_time"));
                lesson.setGroup(group);
                lesson.setRoom(room);
                lesson.setDiscipline(discipline);
                lesson.setLecturer(lecturer);
                lessonList.add(lesson);
            }
        } catch (SQLException e) {
            logger.error("Error parsing lessons result set", e);
            throw new PersistenceException("Error parsing lessons result set", e);
        }
        return lessonList;
    }
}
