package com.smarterama.university.domain;

import com.smarterama.university.dao.LessonDAO;
import com.smarterama.university.exceptions.PersistenceException;

import java.util.Date;
import java.util.List;

public class Timetable {
    private LessonDAO lessonDAO;

    public Timetable() {

    }

    public List<Lesson> collectDailyTimetable(Group group, Date date) throws PersistenceException {
        return getDAO().collectLessons(group, date);
    }

    public List<Lesson> collectDailyTimetable(Lecturer lecturer, Date date) throws PersistenceException {
        return getDAO().collectLessons(lecturer, date);
    }

    public List<Lesson> collectTimetable(Date startDate, Date finishDate) throws PersistenceException {
        return getDAO().collectLessons(startDate, finishDate);
    }

    public List<Lesson> collectTimetable(Group group, Date startDate, Date finishDate) throws PersistenceException {
        return getDAO().collectLessons(group, startDate, finishDate);
    }

    public List<Lesson> collectTimetable(Lecturer lecturer, Date startDate, Date finishDate) throws PersistenceException {
        return getDAO().collectLessons(lecturer, startDate, finishDate);
    }

    public boolean checkRoomAvailable(Date date, Room room) throws PersistenceException {
        for (Lesson lesson : getDAO().collectLessons(room)) {
            if (lesson.goingAt(date) && lesson.getRoom().equals(room)) {
                return false;
            }
        }
        return true;
    }

    private LessonDAO getDAO() {
        if (lessonDAO == null) {
            lessonDAO = new LessonDAO();
        }
        return lessonDAO;
    }
}
