package com.smarterama.university.servlets;

import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/rooms")
public class RoomServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(RoomServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Room> allRooms = null;
        String message = "";
        Room serviceRoom = new Room();
        try {
            allRooms = serviceRoom.getAll();
        } catch (PersistenceException e) {
            logger.error("Error retrieving rooms list from database!", e);
            message = "Error: " + e.getMessage();
        }
        req.getSession().setAttribute("rooms", allRooms);
        req.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/rooms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
