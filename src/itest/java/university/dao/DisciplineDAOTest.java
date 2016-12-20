package university.dao;

import com.smarterama.university.dao.DisciplineDAO;
import com.smarterama.university.domain.Discipline;
import org.junit.Before;
import org.junit.Test;
import university.DBUtil;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DisciplineDAOTest {
    private DisciplineDAO disciplineDAO;
    private Discipline testedDiscipline;

    @Before
    public void setUp() throws Exception {
        Connection connection = DBUtil.getTestConnection();
        disciplineDAO = new DisciplineDAO();
        DBUtil.createDatabase(connection);
        testedDiscipline = new Discipline("Math", Discipline.TestType.TEST);
        testedDiscipline.setId(1);
    }

    @Test
    public void testReadCreate() throws Exception {
        disciplineDAO.persist(testedDiscipline);
        Discipline actualDiscipline = disciplineDAO.read(1);

        assertEquals(testedDiscipline.getFinalExamType(), actualDiscipline.getFinalExamType());
        assertEquals(testedDiscipline.getName(), actualDiscipline.getName());
    }

    @Test
    public void testReadNotExists() throws Exception {
        assertNull(disciplineDAO.read(30));
    }

    @Test
    public void testUpdate() throws Exception {
        disciplineDAO.persist(testedDiscipline);
        testedDiscipline.setName("Physics");
        disciplineDAO.update(testedDiscipline);
        Discipline changedDiscipline = disciplineDAO.read(1);

        assertNotNull(changedDiscipline);
        assertEquals("Physics", changedDiscipline.getName());
    }

    @Test
    public void testUpdateNotExists() throws Exception {
        assertEquals(0, disciplineDAO.update(testedDiscipline));
    }

    @Test
    public void testDelete() throws Exception {
        disciplineDAO.persist(testedDiscipline);
        disciplineDAO.delete(testedDiscipline);

        assertNull(disciplineDAO.read(1));
    }

    @Test
    public void testDeleteNotExists() throws Exception {
        assertEquals(0, disciplineDAO.delete(testedDiscipline));
    }
}
