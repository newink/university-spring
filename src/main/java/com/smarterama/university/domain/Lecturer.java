package com.smarterama.university.domain;

import com.smarterama.university.dao.Identified;
import com.smarterama.university.dao.LecturerDAO;
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
public class Lecturer implements Identified {
    private static Logger logger = LoggerFactory.getLogger(Lecturer.class);
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Degree degree;
    private List<Discipline> disciplines;

    @Autowired
    private LecturerDAO lecturerDAO;


    public Lecturer(String firstName, String lastName, String email, Degree degree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.degree = degree;
        this.disciplines = new ArrayList<>();
    }

    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
        this.firstName = parameterMap.get("first_name")[0];
        this.lastName = parameterMap.get("last_name")[0];
        this.email = parameterMap.get("email")[0];
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : -1;
        this.degree = Degree.valueOf(parameterMap.get("degree")[0].toUpperCase());
        this.disciplines = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lecturer() {
        this.disciplines = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = Degree.valueOf(degree.toUpperCase());
    }

    public void addDiscipline(Discipline discipline) {
        disciplines.add(discipline);
    }

    public void removeDiscipline(Discipline discipline) {
        disciplines.remove(discipline);
    }

    public boolean hasDiscipline(Discipline discipline) {
        return disciplines.contains(discipline);
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public enum Degree {
        ASSOCIATE, BACHELOR, PROFESSIONAL, MASTER
    }

    public int persist() throws PersistenceException {
        return lecturerDAO.persist(this);
    }

    public int update() throws PersistenceException {
        return lecturerDAO.update(this);
    }

    public int delete() throws PersistenceException {
        return lecturerDAO.delete(this);
    }

    public Lecturer retrieve() throws PersistenceException {
        Lecturer readLecturer = lecturerDAO.read(id);
        firstName = readLecturer.getFirstName();
        lastName = readLecturer.getLastName();
        email = readLecturer.getEmail();
        degree = readLecturer.getDegree();
        disciplines.addAll(readLecturer.getDisciplines());
        return this;
    }

    public List<Lecturer> getAll() throws PersistenceException {
        List<Lecturer> lecturersList = null;
        try {
            lecturersList = lecturerDAO.findAll();
        } catch (PersistenceException e) {
            logger.error("Error getting lecturers list", e);
            throw e;
        }
        return lecturersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(firstName, lecturer.firstName) &&
                Objects.equals(lastName, lecturer.lastName) &&
                Objects.equals(email, lecturer.email) &&
                degree == lecturer.degree;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, degree);
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "lecturerDAO=" + lecturerDAO +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", degree=" + degree +
                ", disciplines=" + disciplines +
                '}';
    }
}
