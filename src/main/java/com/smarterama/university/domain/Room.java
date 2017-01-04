package com.smarterama.university.domain;

import com.smarterama.university.dao.Identified;
import com.smarterama.university.dao.RoomDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Room implements Identified {
    private static Logger logger = LoggerFactory.getLogger(Room.class);
    private RoomDAO roomDAO;
    private int id;
    private int capacity;
    private int roomNumber;

    public Room(int capacity, int roomNumber) {
        this.capacity = capacity;
        this.roomNumber = roomNumber;
    }

    public Room() {
    }

    public Room(Map<String, String[]> parameterMap) {
        this.capacity = Integer.parseInt(parameterMap.get("capacity")[0]);
        this.roomNumber = Integer.parseInt(parameterMap.get("room_number")[0]);
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : -1;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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

    public Room retrieve() throws PersistenceException {
        Room readRoom = getDAO().read(id);
        roomNumber = readRoom.getRoomNumber();
        capacity = readRoom.getCapacity();
        return this;
    }

    public List<Room> getAll() throws PersistenceException {
        List<Room> roomsList = null;
        try {
            roomsList = getDAO().findAll();
        } catch (PersistenceException e) {
            logger.error("Error getting rooms list", e);
            throw e;
        }
        return roomsList;
    }

    private RoomDAO getDAO() {
        if (roomDAO == null) {
            roomDAO = new RoomDAO();
        }
        return roomDAO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return capacity == room.capacity &&
                roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, roomNumber);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomDAO=" + roomDAO +
                ", id=" + id +
                ", capacity=" + capacity +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
