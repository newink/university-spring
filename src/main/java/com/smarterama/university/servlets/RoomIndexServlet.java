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
public class RoomIndexServlet extends HttpServlet {
    private Room serviceRoom;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceRoom = new Room();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> roomList = null;
        String message = "";

        try {
            roomList = serviceRoom.getAll();
        } catch (PersistenceException e) {
            message = "Error: " + e.getMessage();
        }
        request.getSession().setAttribute("rooms", roomList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/indexes/rooms.jsp").forward(request, response);
    }
}
