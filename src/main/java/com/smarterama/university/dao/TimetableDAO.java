package com.smarterama.university.dao;

import com.smarterama.university.domain.Group;
import com.smarterama.university.domain.Lecturer;
import com.smarterama.university.domain.Lesson;
import com.smarterama.university.domain.Room;

import java.util.Date;
import java.util.List;

public class TimetableDAO extends GenericDAO<Lesson, Integer> {
    public List<Lesson> collectLessons(Group group, Date date) {
        return getSession().createQuery("from Lesson where group = :group and DATE(startDate) = :date")
                .setParameter("group", group).setParameter("date", date).getResultList();
    }

    public List<Lesson> collectLessons(Lecturer lecturer, Date date) {
        return getSession().createQuery("from Lesson where lecturer = :lecturer and DATE(startDate) = :date")
                .setParameter("lecturer", lecturer).setParameter("date", date).getResultList();
    }

    public List<Lesson> collectLessons(Group group, Date startDate, Date finishDate) {
        return getSession().createQuery("from Lesson where group = :group and startDate >= :startDate and finishDate <= :finishDate")
                .setParameter("group", group).setParameter("startDate", startDate).setParameter("finishDate", finishDate)
                .getResultList();
    }

    public List<Lesson> collectLessons(Lecturer lecturer, Date startDate, Date finishDate) {
        return getSession().createQuery("from Lesson where lecturer = :lecturer and startDate >= :startDate and finishDate <= :finishDate")
                .setParameter("lecturer", lecturer).setParameter("startDate", startDate).setParameter("finishDate", finishDate)
                .getResultList();
    }

    public List<Lesson> collectLessons(Room room) {
        return getSession().createQuery("from Lesson where room = :room").setParameter("room", room).getResultList();
    }

    public List<Lesson> collectLessons(Date startDate, Date finishDate) {
        return getSession().createQuery("from Lesson where startDate >= :startDate and finishDate <= :finishDate")
                .setParameter("startDate", startDate).setParameter("finishDate", finishDate)
                .getResultList();
    }
}
