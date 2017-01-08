package com.smarterama.university.domain;

import com.smarterama.university.dao.Identified;
import com.smarterama.university.dao.RoomDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configurable(autowire = Autowire.BY_TYPE)
public class Room implements Identified {
    private static Logger logger = LoggerFactory.getLogger(Room.class);
    private int id;
    private int capacity;
    private int roomNumber;

    @Autowired
    private RoomDAO roomDAO;


    public Room(int capacity, int roomNumber) {
        this.capacity = capacity;
        this.roomNumber = roomNumber;
    }

    public Room() {
    }


    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
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
        return roomDAO.persist(this);
    }

    public int update() throws PersistenceException {
        return roomDAO.update(this);
    }

    public int delete() throws PersistenceException {
        return roomDAO.delete(this);
    }

    public Room retrieve() throws PersistenceException {
        Room readRoom = roomDAO.read(id);
        roomNumber = readRoom.getRoomNumber();
        capacity = readRoom.getCapacity();
        return this;
    }

    public List<Room> getAll() throws PersistenceException {
        List<Room> roomsList = null;
        try {
            roomsList = roomDAO.findAll();
        } catch (PersistenceException e) {
            logger.error("Error getting rooms list", e);
            throw e;
        }
        return roomsList;
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
