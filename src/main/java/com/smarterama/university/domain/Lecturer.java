package com.smarterama.university.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smarterama.university.dao.GenericDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configurable(autowire = Autowire.BY_TYPE)
@Entity
@Table(name = "lecturers")
public class Lecturer implements DomainObject {

    private static Logger logger = LoggerFactory.getLogger(Lecturer.class);

    @Id @Column @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "lecturers_disciplines",
            joinColumns = { @JoinColumn(name = "lecturer_id") },
            inverseJoinColumns = { @JoinColumn(name = "discipline_id") })
    private List<Discipline> disciplines;

    @Transient
    @Autowired
    private GenericDAO<Lecturer, Integer> lecturerDAO;


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
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : null;
        this.degree = Degree.valueOf(parameterMap.get("degree")[0].toUpperCase());
        this.disciplines = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setDegree(Degree degree) {
        this.degree = degree;
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

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public enum Degree {
        ASSOCIATE, BACHELOR, PROFESSIONAL, MASTER
    }

    @Transactional
    public void persist() throws PersistenceException {
        lecturerDAO.saveOrUpdate(this);
    }

    @Transactional
    public void delete() throws PersistenceException {
        lecturerDAO.delete(this);
    }


    @Transactional(readOnly = true)
    public Lecturer retrieve() throws PersistenceException {
        Lecturer readLecturer = lecturerDAO.get(Lecturer.class, id);
        firstName = readLecturer.getFirstName();
        lastName = readLecturer.getLastName();
        email = readLecturer.getEmail();
        degree = readLecturer.getDegree();
        disciplines.addAll(readLecturer.getDisciplines());
        return this;
    }


    @Transactional(readOnly = true)
    public List<Lecturer> collectAll() throws PersistenceException {
        List<Lecturer> lecturersList = null;
        try {
            lecturersList = lecturerDAO.getAll(Lecturer.class);
        } catch (HibernateException e) {
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
