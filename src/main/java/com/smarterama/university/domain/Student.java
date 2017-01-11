package com.smarterama.university.domain;

import com.smarterama.university.dao.Identified;
import com.smarterama.university.dao.StudentDAO;
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
public class Student implements DomainObject {
    private static Logger logger = LoggerFactory.getLogger(Student.class);
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private int course;
    private boolean isSubsidized;
    private Group group;

    @Autowired
    private StudentDAO studentDAO;


    public Student() {
    }

    public Student(String firstName, String lastName, String address, int course, boolean isSubsidized) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.course = course;
        this.isSubsidized = isSubsidized;
    }

    public void setFieldsFromRequest(Map<String, String[]> parameterMap) {
        this.firstName = parameterMap.get("first_name")[0];
        this.lastName = parameterMap.get("last_name")[0];
        this.address = parameterMap.get("address")[0];
        this.course = Integer.parseInt(parameterMap.get("course")[0]);
        this.isSubsidized = "on".equals(parameterMap.get("first_name")[0]);
        this.id = parameterMap.get("id") != null ? Integer.parseInt(parameterMap.get("id")[0]) : -1;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public boolean isSubsidized() {
        return isSubsidized;
    }

    public void setSubsidized(boolean subsidized) {
        isSubsidized = subsidized;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public int persist() throws PersistenceException {
        return studentDAO.persist(this);
    }

    public int update() throws PersistenceException {
        return studentDAO.update(this);
    }

    public int delete() throws PersistenceException {
        return studentDAO.delete(this);
    }

    public Student retrieve() throws PersistenceException {
        Student readStudent = studentDAO.read(id);
        group = readStudent.getGroup();
        firstName = readStudent.getFirstName();
        lastName = readStudent.getLastName();
        course = readStudent.getCourse();
        isSubsidized = readStudent.isSubsidized();
        address = readStudent.getAddress();
        return this;
    }

    public List<Student> getAll() throws PersistenceException {
        List<Student> studentsList = null;
        try {
            studentsList = studentDAO.findAll();
        } catch (PersistenceException e) {
            logger.error("Error getting students list", e);
            throw e;
        }
        return studentsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return course == student.course &&
                isSubsidized == student.isSubsidized &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(address, student.address) &&
                Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, course, isSubsidized, group);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentDAO=" + studentDAO +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", course=" + course +
                ", isSubsidized=" + isSubsidized +
                ", group=" + group +
                '}';
    }
}
