package com.smarterama.university.domain;

import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;
import java.util.List;

@Configurable(autowire = Autowire.BY_TYPE)
public class Timetable {
//
//    @Autowired
//    private LessonDAO lessonDAO;
//
//    public Timetable() {
//
//    }
//
//    public List<Lesson> collectDailyTimetable(Group group, Date date) throws PersistenceException {
//        return lessonDAO.collectLessons(group, date);
//    }
//
//    public List<Lesson> collectDailyTimetable(Lecturer lecturer, Date date) throws PersistenceException {
//        return lessonDAO.collectLessons(lecturer, date);
//    }
//
//    public List<Lesson> collectTimetable(Date startDate, Date finishDate) throws PersistenceException {
//        return lessonDAO.collectLessons(startDate, finishDate);
//    }
//
//    public List<Lesson> collectTimetable(Group group, Date startDate, Date finishDate) throws PersistenceException {
//        return lessonDAO.collectLessons(group, startDate, finishDate);
//    }
//
//    public List<Lesson> collectTimetable(Lecturer lecturer, Date startDate, Date finishDate) throws PersistenceException {
//        return lessonDAO.collectLessons(lecturer, startDate, finishDate);
//    }
//
//    public boolean checkRoomAvailable(Date date, Room room) throws PersistenceException {
//        for (Lesson lesson : lessonDAO.collectLessons(room)) {
//            if (lesson.goingAt(date) && lesson.getRoom().equals(room)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
