package com.smarterama.university.domain;

import com.smarterama.university.dao.Identified;
import com.smarterama.university.dao.StudentDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Student implements Identified {
    private static Logger logger = LoggerFactory.getLogger(Student.class);
    private StudentDAO studentDAO;
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private int course;
    private boolean isSubsidized;
    private Group group;

    public Student() {
    }

    public Student(String firstName, String lastName, String address, int course, boolean isSubsidized) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.course = course;
        this.isSubsidized = isSubsidized;
    }

    public Student(Map<String, String[]> parameterMap) {
        this.firstName = parameterMap.get("first_name")[0];
        this.lastName = parameterMap.get("last_name")[0];
        this.address = parameterMap.get("address")[0];
        this.course = Integer.parseInt(parameterMap.get("course")[0]);
        this.isSubsidized = "on".equals(parameterMap.get("first_name")[0]);
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
        return getDAO().persist(this);
    }

    public int update() throws PersistenceException {
        return getDAO().update(this);
    }

    public int delete() throws PersistenceException {
        return getDAO().delete(this);
    }

    public Student retrieve() throws PersistenceException {
        Student readStudent = getDAO().read(id);
        group = readStudent.getGroup();
        firstName = readStudent.getFirstName();
        lastName = readStudent.getLastName();
        isSubsidized = readStudent.isSubsidized();
        address = readStudent.getAddress();
        return this;
    }

    public List<Student> getAll() throws PersistenceException {
        List<Student> studentsList = null;
        try {
            studentsList = getDAO().findAll();
        } catch (PersistenceException e) {
            logger.error("Error getting students list", e);
            throw e;
        }
        return studentsList;
    }

    private StudentDAO getDAO() {
        if (studentDAO == null) {
            studentDAO = new StudentDAO();
        }
        return studentDAO;
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
