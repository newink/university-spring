package university.dao;

import com.smarterama.university.dao.GroupDAO;
import com.smarterama.university.dao.StudentDAO;
import com.smarterama.university.domain.Group;
import com.smarterama.university.domain.Student;
import org.junit.Before;
import org.junit.Test;
import university.DBUtil;

import java.sql.Connection;

import static org.junit.Assert.*;

public class StudentDAOTest {
    private StudentDAO studentDAO;
    private Student testedStudent;

    @Before
    public void setUp() throws Exception {
        Connection connection = DBUtil.getTestConnection();
        DBUtil.createDatabase(connection);
        studentDAO = new StudentDAO();
        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group(124);
        group.setId(1);
        groupDAO.persist(group);
        testedStudent = new Student("Clark", "Kent", "Tyumen", 3, true);
        testedStudent.setId(1);
        testedStudent.setGroup(group);
    }

    @Test
    public void testReadCreate() throws Exception {
        studentDAO.persist(testedStudent);
        Student actualStudent = studentDAO.read(1);

        assertNotNull(actualStudent);
        assertEquals(testedStudent.getFirstName(), actualStudent.getFirstName());
    }

    @Test
    public void testUpdate() throws Exception {
        studentDAO.persist(testedStudent);
        testedStudent.setLastName("Monson");
        studentDAO.update(testedStudent);
        Student actualStudent = studentDAO.read(1);

        assertEquals("Monson", actualStudent.getLastName());
    }

    @Test
    public void testDelete() throws Exception {
        studentDAO.persist(testedStudent);
        studentDAO.delete(testedStudent);
        Student actualStudent = studentDAO.read(1);

        assertNull(actualStudent);
    }
}
