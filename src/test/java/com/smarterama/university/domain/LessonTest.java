package com.smarterama.university.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class LessonTest {
    private Lesson testedLesson;

    @Before
    public void setUp() {
        Room room = new Room();
        room.setCapacity(30);
        room.setRoomNumber(112);

        Discipline discipline = new Discipline();
        discipline.setName("Math");
        discipline.setFinalExamType("EXAM");

        Group group = new Group();
        group.setGroupNumber(12);

        Lecturer lecturer = new Lecturer();
        lecturer.setDegree("BACHELOR");
        lecturer.setEmail("lecturer@email.com");
        lecturer.setFirstName("Richard");
        lecturer.setLastName("Clarkson");

        testedLesson = new Lesson();
        testedLesson.setDiscipline(discipline);
        testedLesson.setGroup(group);
        testedLesson.setRoom(room);
        testedLesson.setLecturer(lecturer);

        Calendar calendar = new GregorianCalendar(2016, 11, 1, 16, 30);
        testedLesson.setStartDate(calendar.getTime());
        calendar.set(2016, 11, 1, 17, 30);
        testedLesson.setFinishDate(calendar.getTime());
    }

    @Test
    public void calculateLessonDurationTestHourDuration() {
        long minutesInHour = 60;
        assertEquals(minutesInHour, testedLesson.calculateLessonDuration());
    }

    @Test
    public void goingAtTestMiddleLesson() {
        Calendar calendar = new GregorianCalendar(2016, 11, 1, 17, 0);
        Date middleLessonTime = calendar.getTime();
        assertTrue(testedLesson.goingAt(middleLessonTime));
    }

    @Test
    public void goingAtTestBeforeLesson() {
        Calendar calendar = new GregorianCalendar(2016, 11, 1, 15, 0);
        Date beforeLessonTime = calendar.getTime();
        assertFalse(testedLesson.goingAt(beforeLessonTime));
    }

    @Test
    public void goingAtTestAfterLesson() {
        Calendar calendar = new GregorianCalendar(2016, 11, 1, 17, 30, 30);
        Date afterLessonTime = calendar.getTime();
        assertFalse(testedLesson.goingAt(afterLessonTime));
    }
}
