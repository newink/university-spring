package com.smarterama.university.servlets;

import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/rooms")
public class RoomIndexServlet extends HttpServlet {

    @Autowired
    private Room room;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> roomList = null;
        String message = "";

        try {
            roomList = room.getAll();
        } catch (PersistenceException e) {
            message = "Error: " + e.getMessage();
        }
        request.getSession().setAttribute("rooms", roomList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/indexes/rooms.jsp").forward(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
}
