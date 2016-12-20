package university.dao;

import com.smarterama.university.dao.RoomDAO;
import com.smarterama.university.domain.Room;
import org.junit.Before;
import org.junit.Test;
import university.DBUtil;

import java.sql.Connection;

import static org.junit.Assert.*;

public class RoomDAOTest {
    private RoomDAO roomDAO;
    private Room testedRoom;

    @Before
    public void setUp() throws Exception {
        Connection connection = DBUtil.getTestConnection();
        roomDAO = new RoomDAO();
        DBUtil.createDatabase(connection);
        testedRoom = new Room(12, 100);
        testedRoom.setId(1);
    }

    @Test
    public void testReadCreate() throws Exception {
        roomDAO.persist(testedRoom);
        Room actualRoom = roomDAO.read(1);

        assertNotNull(actualRoom);
        assertEquals(testedRoom.getCapacity(), actualRoom.getCapacity());
        assertEquals(testedRoom.getRoomNumber(), actualRoom.getRoomNumber());
    }

    @Test
    public void testReadNotExists() throws Exception {
        assertNull(roomDAO.read(30));
    }

    @Test
    public void testUpdate() throws Exception {
        roomDAO.persist(testedRoom);
        testedRoom.setRoomNumber(3450);
        roomDAO.update(testedRoom);
        Room changedRoom = roomDAO.read(1);

        assertNotNull(changedRoom);
        assertEquals(3450, changedRoom.getRoomNumber());
    }

    @Test
    public void testUpdateNotExists() throws Exception {
        assertEquals(0, roomDAO.update(testedRoom));
    }

    @Test
    public void testDelete() throws Exception {
        roomDAO.persist(testedRoom);
        roomDAO.delete(testedRoom);

        assertNull(roomDAO.read(1));
    }

    @Test
    public void testDeleteNotExists() throws Exception {
        assertEquals(0, roomDAO.delete(testedRoom));
    }
}
