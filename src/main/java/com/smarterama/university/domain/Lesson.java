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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configurable(autowire = Autowire.BY_TYPE)
@Entity
@Table(name = "lessons")
public class Lesson implements DomainObject {

    private static Logger logger = LoggerFactory.getLogger(Lesson.class);

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    @Transient
    @Autowired
    private GenericDAO<Lesson, Integer> lessonDAO;


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @JsonManagedReference
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public long calculateLessonDuration() {
        return TimeUnit.MILLISECONDS.toMinutes(finishDate.getTime() - startDate.getTime());
    }

    public boolean goingAt(Date date) {
        return startDate.before(date) && finishDate.after(date);
    }

    public boolean goingAtDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(startDate).equals(dateFormat.format(date));
    }

    public boolean goingBetweenDays(Date betweenStartDate, Date betweenFinishDate) {
        return startDate.after(betweenStartDate) && startDate.before(betweenFinishDate);
    }

    @Transactional
    public void persist() throws PersistenceException {
        lessonDAO.saveOrUpdate(this);
    }

    @Transactional
    public void update() throws PersistenceException {
        lessonDAO.saveOrUpdate(this);
    }

    @Transactional
    public void delete() throws PersistenceException {
        lessonDAO.delete(this);
    }


    @Transactional(readOnly = true)
    public Lesson retrieve() throws PersistenceException {
        Lesson readLesson = lessonDAO.get(Lesson.class, id);
        return readLesson;
    }


    @Transactional(readOnly = true)
    public List<Lesson> collectAll() throws PersistenceException {
        List<Lesson> lessonsList = null;
        try {
            lessonsList = lessonDAO.getAll(Lesson.class);
        } catch (HibernateException e) {
            logger.error("Error getting lessons list", e);
            throw e;
        }
        return lessonsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(room, lesson.room) &&
                Objects.equals(lecturer, lesson.lecturer) &&
                Objects.equals(group, lesson.group) &&
                Objects.equals(discipline, lesson.discipline) &&
                Objects.equals(startDate, lesson.startDate) &&
                Objects.equals(finishDate, lesson.finishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, lecturer, group, discipline, startDate, finishDate);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonDAO=" + lessonDAO +
                ", id=" + id +
                ", room=" + room +
                ", lecturer=" + lecturer +
                ", group=" + group +
                ", discipline=" + discipline +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                '}';
    }
}
