package com.smarterama.university.domain;

import com.smarterama.university.dao.GenericDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configurable(autowire = Autowire.BY_TYPE)
@Entity
@Table(name = "rooms")
public class Room {
    private static Logger logger = LoggerFactory.getLogger(Room.class);

    @Id @Column @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private int capacity;

    @Column
    private int roomNumber;

    @Transient
    @Autowired
    private GenericDAO<Room, Integer> roomDAO;


    public Room(int capacity, int roomNumber) {
        this.capacity = capacity;
        this.roomNumber = roomNumber;
    }

    public Room() {
    }


    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
        this.capacity = Integer.parseInt(parameterMap.get("capacity")[0]);
        this.roomNumber = Integer.parseInt(parameterMap.get("room_number")[0]);
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Transactional
    public void persist() throws PersistenceException {
        roomDAO.save(this);
    }

    @Transactional
    public void delete() throws PersistenceException {
        roomDAO.delete(this);
    }

    @Transactional
    public Room retrieve() throws PersistenceException {
        Room readRoom = roomDAO.get(Room.class, id);
        roomNumber = readRoom.getRoomNumber();
        capacity = readRoom.getCapacity();
        return this;
    }

    @Transactional
    public List<Room> getAll() throws PersistenceException {
        List<Room> roomsList = null;
        try {
            roomsList = roomDAO.getAll(Room.class);
        } catch (HibernateException e) {
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
