package com.smarterama.university.domain;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Group> groups;
    private List<Student> students;
    private List<Room> rooms;
    private List<Lecturer> lecturers;

    public University() {
        groups = new ArrayList<>();
        students = new ArrayList<>();
        rooms = new ArrayList<>();
        lecturers = new ArrayList<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public boolean removeGroup(Group group) {
        return groups.remove(group);
    }

    public Group getByGroupNumber(int number) {
        for (Group group : groups) {
            if (group.getGroupNumber() == number) {
                return group;
            }
        }
        return null;
    }

    public List<Group> findAllGroups() {
        return groups;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public List<Student> findStudentsByLastName(String lastName) {
        List<Student> found = new ArrayList<>();
        for (Student student : students) {
            if (student.getLastName().equals(lastName)) {
                found.add(student);
            }
        }
        return found;
    }

    public List<Student> findAllStudents() {
        return students;
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
    }

    public void removeLecturer(Lecturer lecturer) {
        lecturers.remove(lecturer);
    }

    public List<Lecturer> findLecturersByLastName(String lastName) {
        List<Lecturer> found = new ArrayList<>();
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getLastName().equals(lastName)) {
                found.add(lecturer);
            }
        }
        return found;
    }

    public List<Lecturer> findAllLecturers() {
        return lecturers;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public Room getRoomByNumber(int number) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == number) {
                return room;
            }
        }
        return null;
    }

    public List<Room> findAllRooms() {
        return rooms;
    }
}
