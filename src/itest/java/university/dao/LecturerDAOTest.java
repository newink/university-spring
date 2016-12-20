package university.dao;

import com.smarterama.university.dao.DisciplineDAO;
import com.smarterama.university.dao.LecturerDAO;
import com.smarterama.university.domain.Discipline;
import com.smarterama.university.domain.Lecturer;
import com.smarterama.university.exceptions.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import university.DBUtil;

import java.sql.Connection;

import static org.junit.Assert.*;

public class LecturerDAOTest {
    private LecturerDAO lecturerDAO;
    private DisciplineDAO disciplineDAO;
    private Lecturer testedLecturer;
    private Discipline discipline;

    @Before
    public void setUp() throws Exception {
        Connection connection = DBUtil.getTestConnection();
        lecturerDAO = new LecturerDAO();
        disciplineDAO = new DisciplineDAO();
        DBUtil.createDatabase(connection);
        testedLecturer = new Lecturer("Clark", "Kent", "email", Lecturer.Degree.MASTER);
        testedLecturer.setId(1);
        discipline = new Discipline("Math", Discipline.TestType.TEST);
        discipline.setId(1);
        disciplineDAO.persist(discipline);
        testedLecturer.addDiscipline(discipline);
    }

    @Test
    public void testReadCreate() throws Exception {
        disciplineDAO.persist(discipline);
        lecturerDAO.persist(testedLecturer);
        Lecturer actualLecturer = lecturerDAO.read(1);
        Discipline actualDiscipline = actualLecturer.getDisciplines().get(0);
        Discipline expectedDiscipline = testedLecturer.getDisciplines().get(0);

        assertNotNull(actualLecturer);
        assertEquals(testedLecturer.getFirstName(), actualLecturer.getFirstName());
        assertEquals(testedLecturer.getDegree(), actualLecturer.getDegree());
        assertEquals(expectedDiscipline.getName(), actualDiscipline.getName());
    }

    @Test
    public void testReadNotExists() throws Exception {
        assertNull(lecturerDAO.read(30));
    }

    @Test
    public void testUpdate() throws Exception {
        lecturerDAO.persist(testedLecturer);
        testedLecturer.setFirstName("Mables");
        lecturerDAO.update(testedLecturer);
        Lecturer changedLecturer = lecturerDAO.read(1);

        assertNotNull(changedLecturer);
        assertEquals("Mables", changedLecturer.getFirstName());
    }

    @Test(expected = PersistenceException.class)
    public void testUpdateNotExists() throws Exception {
        assertEquals(0, lecturerDAO.update(testedLecturer));
    }

    @Test
    public void testUpdateAddMoreDisciplines() throws Exception {
        Discipline secondDiscipline = new Discipline("Physics", Discipline.TestType.EXAM);
        secondDiscipline.setId(2);
        disciplineDAO.persist(secondDiscipline);

        lecturerDAO.persist(testedLecturer);
        testedLecturer.addDiscipline(secondDiscipline);
        lecturerDAO.update(testedLecturer);
        Lecturer changedLecturer = lecturerDAO.read(1);
        Discipline addedDiscipline = changedLecturer.getDisciplines().get(1);

        assertNotNull(changedLecturer);
        assertEquals(2, changedLecturer.getDisciplines().size());
        assertEquals(addedDiscipline.getName(), secondDiscipline.getName());
    }

    @Test
    public void testUpdateRemoveDiscipline() throws Exception {
        Discipline secondDiscipline = new Discipline("Physics", Discipline.TestType.EXAM);
        secondDiscipline.setId(2);
        disciplineDAO.persist(secondDiscipline);

        testedLecturer.addDiscipline(secondDiscipline);
        lecturerDAO.persist(testedLecturer);
        testedLecturer.removeDiscipline(secondDiscipline);
        lecturerDAO.update(testedLecturer);
        Lecturer changedLecturer = lecturerDAO.read(1);

        assertEquals(1, changedLecturer.getDisciplines().size());
    }

    @Test
    public void testDelete() throws Exception {
        lecturerDAO.persist(testedLecturer);
        lecturerDAO.delete(testedLecturer);

        assertNull(lecturerDAO.read(1));
    }

    @Test
    public void testDeleteNotExists() throws Exception {
        assertEquals(0, lecturerDAO.delete(testedLecturer));
    }
}
