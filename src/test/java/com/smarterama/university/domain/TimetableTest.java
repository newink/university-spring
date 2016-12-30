package com.smarterama.university.domain;

import com.smarterama.university.DBUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class TimetableTest {

    private Timetable testedTimetable;
    private Group mathGroup;
    private Group physicsGroup;
    private Lecturer mathLecturer;
    private Lecturer physicsLecturer;
    private Lesson mathLesson;
    private Lesson physicsLesson;
    private Lesson thermodynamicsLesson;
    private Lesson algebraLesson;
    private Room mathRoom;
    private Room physicsRoom;

    private Calendar rangeStart = new GregorianCalendar(2016, 11, 1, 0, 0);
    private Calendar rangeFinish = new GregorianCalendar(2016, 11, 4, 23, 59);

    @Before
    public void setUp() throws Exception {
        DBUtil.createDatabase(DBUtil.getTestConnection());

        mathRoom = new Room();
        mathRoom.setCapacity(30);
        mathRoom.setRoomNumber(112);
        mathRoom.setId(1);
        mathRoom.persist();

        physicsRoom = new Room();
        physicsRoom.setCapacity(40);
        physicsRoom.setRoomNumber(140);
        physicsRoom.setId(2);
        physicsRoom.persist();

        Discipline mathDiscipline = new Discipline();
        mathDiscipline.setName("Math");
        mathDiscipline.setFinalExamType("EXAM");
        mathDiscipline.setId(1);
        mathDiscipline.persist();
        Discipline physicsDiscipline = new Discipline();
        physicsDiscipline.setName("Physics");
        physicsDiscipline.setFinalExamType("TEST");
        physicsDiscipline.setId(2);
        physicsDiscipline.persist();

        mathGroup = new Group();
        mathGroup.setGroupNumber(12);
        mathGroup.setId(1);
        mathGroup.persist();
        physicsGroup = new Group();
        physicsGroup.setGroupNumber(20);
        physicsGroup.setId(2);
        physicsGroup.persist();

        mathLecturer = new Lecturer();
        mathLecturer.setDegree("BACHELOR");
        mathLecturer.setEmail("lecturer1@email.com");
        mathLecturer.setFirstName("Jeremy");
        mathLecturer.setLastName("Clarkson");
        mathLecturer.setId(1);
        mathLecturer.persist();

        physicsLecturer = new Lecturer();
        physicsLecturer.setDegree("BACHELOR");
        physicsLecturer.setEmail("lecturer2@email.com");
        physicsLecturer.setFirstName("Richard");
        physicsLecturer.setLastName("Hammond");
        physicsLecturer.setId(2);
        physicsLecturer.persist();

        mathLesson = new Lesson();
        mathLesson.setId(1);
        mathLesson.setDiscipline(physicsDiscipline);
        mathLesson.setGroup(mathGroup);
        mathLesson.setRoom(mathRoom);
        mathLesson.setLecturer(mathLecturer);


        Calendar calendar = new GregorianCalendar(2016, 11, 1, 16, 30);
        mathLesson.setStartDate(calendar.getTime());
        calendar.set(2016, 11, 1, 17, 30);
        mathLesson.setFinishDate(calendar.getTime());

        physicsLesson = new Lesson();
        physicsLesson.setId(2);
        physicsLesson.setDiscipline(physicsDiscipline);
        physicsLesson.setGroup(physicsGroup);
        physicsLesson.setRoom(physicsRoom);
        physicsLesson.setLecturer(physicsLecturer);

        calendar.set(2016, 11, 2, 16, 50);
        physicsLesson.setStartDate(calendar.getTime());
        calendar.set(2016, 11, 2, 17, 50);
        physicsLesson.setFinishDate(calendar.getTime());

        thermodynamicsLesson = new Lesson();
        thermodynamicsLesson.setId(3);
        thermodynamicsLesson.setDiscipline(physicsDiscipline);
        thermodynamicsLesson.setGroup(mathGroup);
        thermodynamicsLesson.setRoom(physicsRoom);
        thermodynamicsLesson.setLecturer(physicsLecturer);

        calendar.set(2016, 11, 3, 16, 50);
        thermodynamicsLesson.setStartDate(calendar.getTime());
        calendar.set(2016, 11, 3, 17, 50);
        thermodynamicsLesson.setFinishDate(calendar.getTime());

        algebraLesson = new Lesson();
        algebraLesson.setId(4);
        algebraLesson.setDiscipline(physicsDiscipline);
        algebraLesson.setGroup(physicsGroup);
        algebraLesson.setRoom(mathRoom);
        algebraLesson.setLecturer(mathLecturer);

        calendar.set(2016, 11, 4, 16, 50);
        algebraLesson.setStartDate(calendar.getTime());
        calendar.set(2016, 11, 4, 17, 50);
        algebraLesson.setFinishDate(calendar.getTime());

        testedTimetable = new Timetable();
        mathLesson.persist();
        physicsLesson.persist();
        algebraLesson.persist();
        thermodynamicsLesson.persist();
    }

    @Test
    public void getDailyTimetableTestGroup() throws Exception {
        Calendar calendar = new GregorianCalendar(2016, 11, 1);
        List<Lesson> timetable = testedTimetable.collectDailyTimetable(mathGroup, calendar.getTime());
        Lesson actualLesson = timetable.get(0);
        assertTrue(mathGroup.equals(actualLesson.getGroup()));
        assertEquals(1, timetable.size());
    }

    @Test
    public void getDailyTimetableTestLecturer() throws Exception {
        Calendar calendar = new GregorianCalendar(2016, 11, 2);
        List<Lesson> timetable = testedTimetable.collectDailyTimetable(physicsLecturer, calendar.getTime());
        Lesson actualLesson = timetable.get(0);
        assertTrue(physicsLecturer.equals(actualLesson.getLecturer()));
        assertEquals(1, timetable.size());
    }

    @Test
    public void getTimetableTestFull() throws Exception {
        List<Lesson> timetable = testedTimetable.collectTimetable(rangeStart.getTime(), rangeFinish.getTime());
        assertEquals(4, timetable.size());
    }

    @Test
    public void getTimetableTestGroupDateRange() throws Exception {
        List<Lesson> timetable = testedTimetable.collectTimetable(physicsGroup, rangeStart.getTime(), rangeFinish.getTime());
        assertEquals(2, timetable.size());
        for (Lesson lesson : timetable) {
            assertTrue(physicsGroup.equals(lesson.getGroup()));
        }
    }

    @Test
    public void getTimetableTestLecturerDateRange() throws Exception {
        List<Lesson> timetable = testedTimetable.collectTimetable(physicsLecturer, rangeStart.getTime(), rangeFinish.getTime());
        assertEquals(2, timetable.size());
        for (Lesson lesson : timetable) {
            assertTrue(physicsLecturer.equals(lesson.getLecturer()));
        }
    }

    @Test
    public void checkRoomAvailableTestNotAvailable() throws Exception {
        Calendar busyRoomTime = new GregorianCalendar(2016, 11, 1, 16, 40);
        assertFalse(testedTimetable.checkRoomAvailable(busyRoomTime.getTime(), mathRoom));
    }

    @Test
    public void checkRoomAvailableTestAvailable() throws Exception {
        Calendar busyRoomTime = new GregorianCalendar(2016, 11, 1, 16, 40);
        assertTrue(testedTimetable.checkRoomAvailable(busyRoomTime.getTime(), physicsRoom));
    }
}
