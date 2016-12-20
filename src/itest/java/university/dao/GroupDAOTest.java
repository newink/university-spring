package university.dao;

import com.smarterama.university.dao.GroupDAO;
import com.smarterama.university.domain.Group;
import org.junit.Before;
import org.junit.Test;
import university.DBUtil;

import java.sql.Connection;

import static org.junit.Assert.*;

public class GroupDAOTest {
    private GroupDAO groupDAO;
    private Group testedGroup;

    @Before
    public void setUp() throws Exception{
        Connection connection = DBUtil.getTestConnection();
        groupDAO = new GroupDAO();
        testedGroup = new Group(422);
        testedGroup.setId(1);
        DBUtil.createDatabase(connection);
    }

    @Test
    public void testCreateRead() throws Exception {
        groupDAO.persist(testedGroup);
        Group actualGroup = groupDAO.read(1);

        assertNotNull(actualGroup);
        assertEquals(testedGroup.getGroupNumber(), actualGroup.getGroupNumber());
    }

    @Test
    public void testReadNotExists() throws Exception {
        assertNull(groupDAO.read(30));
    }

    @Test
    public void testUpdate() throws Exception {
        groupDAO.persist(testedGroup);
        testedGroup.setGroupNumber(3450);
        groupDAO.update(testedGroup);
        Group changedGroup = groupDAO.read(1);

        assertNotNull(changedGroup);
        assertEquals(3450, changedGroup.getGroupNumber());
    }

    @Test
    public void testUpdateNotExists() throws Exception {
        assertEquals(0, groupDAO.update(testedGroup));
    }

    @Test
    public void testDelete() throws Exception {
        groupDAO.persist(testedGroup);
        groupDAO.delete(testedGroup);

        assertNull(groupDAO.read(1));
    }

    @Test
    public void testDeleteNotExists() throws Exception {
        assertEquals(0, groupDAO.delete(testedGroup));
    }
}
