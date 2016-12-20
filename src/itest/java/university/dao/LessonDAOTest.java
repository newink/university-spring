package university.dao;

import com.smarterama.university.dao.*;
import com.smarterama.university.domain.*;
import org.junit.Before;
import org.junit.Test;
import university.DBUtil;

import java.sql.Connection;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class LessonDAOTest {
    private LessonDAO lessonDAO;
    private Lesson testedLesson;

    @Before
    public void setUp() throws Exception {
        Connection connection = DBUtil.getTestConnection();
        DBUtil.createDatabase(connection);
        lessonDAO = new LessonDAO();
        RoomDAO roomDAO = new RoomDAO();
        GroupDAO groupDAO = new GroupDAO();
        LecturerDAO lecturerDAO = new LecturerDAO();
        DisciplineDAO disciplineDAO = new DisciplineDAO();

        Discipline discipline = new Discipline("Math", Discipline.TestType.EXAM);
        Room room = new Room(12, 1234);
        Lecturer lecturer = new Lecturer("Clark", "Kent", "email", Lecturer.Degree.MASTER);
        Group group = new Group(23);

        discipline.setId(1);
        room.setId(1);
        lecturer.setId(1);
        group.setId(1);

        roomDAO.persist(room);
        groupDAO.persist(group);
        lecturerDAO.persist(lecturer);
        disciplineDAO.persist(discipline);

        testedLesson = new Lesson();
        testedLesson.setGroup(group);
        testedLesson.setId(1);
        testedLesson.setDiscipline(discipline);
        testedLesson.setRoom(room);
        testedLesson.setLecturer(lecturer);
        testedLesson.setStartDate(new GregorianCalendar(2016, 11, 2, 16, 20).getTime());
        testedLesson.setFinishDate(new GregorianCalendar(2016, 11, 2, 17, 0).getTime());
    }

    @Test
    public void testCreateRead() throws Exception {
        lessonDAO.persist(testedLesson);
        Lesson actualLesson = lessonDAO.read(1);

        assertNotNull(actualLesson);
    }

    @Test
    public void testUpdate() throws Exception {
        Date date = new GregorianCalendar(2016, 11, 2, 15, 30).getTime();
        lessonDAO.persist(testedLesson);
        testedLesson.setStartDate(new GregorianCalendar(2016, 11, 2, 15, 30).getTime());
        lessonDAO.update(testedLesson);
        Lesson actualLesson = lessonDAO.read(1);

        assertEquals(date.getTime(), actualLesson.getStartDate().getTime());
    }

    @Test
    public void testDelete() throws Exception {
        lessonDAO.persist(testedLesson);
        lessonDAO.delete(testedLesson);
        Lesson actualLesson = lessonDAO.read(1);

        assertNull(actualLesson);
    }
}
