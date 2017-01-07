package com.smarterama.university.dao;

import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDAO extends AbstractJDBCDao<Room> {

    private static final Logger logger = LoggerFactory.getLogger(RoomDAO.class);

    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM rooms";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE rooms SET room_number = ?, capacity = ? WHERE id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM rooms WHERE id = ?;";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO rooms (room_number, capacity) VALUES (?, ?);";
    }

    @Override
    protected String getTableName() {
        return "rooms";
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Room room) throws PersistenceException {
        prepareInsertStatement(statement, room);
        try {
            statement.setInt(3, room.getId());
        } catch (SQLException e) {
            logger.error("Error preparing rooms update/insert statement", e);
            throw new PersistenceException("Problem preparing rooms update statement", e);
        }
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Room room) throws PersistenceException {
        try {
            statement.setInt(1, room.getRoomNumber());
            statement.setInt(2, room.getCapacity());
        } catch (SQLException e) {
            logger.error("Error preparing rooms update/insert statement", e);
            throw new PersistenceException("Problem preparing rooms update statement", e);
        }
    }

    @Override
    protected List<Room> parseResultSet(ResultSet resultSet) throws PersistenceException {
        List<Room> roomList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt("id"));
                room.setCapacity(resultSet.getInt("capacity"));
                room.setRoomNumber(resultSet.getInt("room_number"));
                roomList.add(room);
            }
        } catch (SQLException e) {
            logger.error("Error parsing rooms result set", e);
            throw new PersistenceException("Error parsing rooms result set", e);
        }
        return roomList;
    }
}
