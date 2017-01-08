package com.smarterama.university.domain;

import com.smarterama.university.dao.Identified;
import com.smarterama.university.dao.LessonDAO;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configurable(autowire = Autowire.BY_TYPE)
public class Lesson implements Identified {
    private static Logger logger = LoggerFactory.getLogger(Lesson.class);
    private int id;
    private Room room;
    private Lecturer lecturer;
    private Group group;
    private Discipline discipline;
    private Date startDate;
    private Date finishDate;

    @Autowired
    private LessonDAO lessonDAO;


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int persist() throws PersistenceException {
        return lessonDAO.persist(this);
    }

    public int update() throws PersistenceException {
        return lessonDAO.update(this);
    }

    public int delete() throws PersistenceException {
        return lessonDAO.delete(this);
    }

    public Lesson retrieve() throws PersistenceException {
        Lesson readLesson = lessonDAO.read(id);
        discipline = readLesson.getDiscipline();
        group = readLesson.getGroup();
        lecturer = readLesson.getLecturer();
        room = readLesson.getRoom();
        startDate = readLesson.getStartDate();
        finishDate = readLesson.getFinishDate();
        return this;
    }

    public List<Lesson> getAll() throws PersistenceException {
        List<Lesson> lessonsList = null;
        try {
            lessonsList = lessonDAO.findAll();
        } catch (PersistenceException e) {
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
