package com.smarterama.university.servlets;

import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/room")
public class RoomServlet extends HttpServlet {
    private static final String INSERT_UPDATE_JSP = "/WEB-INF/views/create/room.jsp";
    private static final String INDEX_JSP = "/WEB-INF/views/indexes/rooms.jsp";
    private static final String REDIRECT_ADDRESS = "/university/rooms";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        if (action.equalsIgnoreCase("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("id", id);
        }
        getServletContext().getRequestDispatcher(INSERT_UPDATE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : -1;
        try {
            if (request.getParameter("room_number") != null) {
                Room room = new Room(request.getParameterMap());

                if (id != -1) {
                    room.setId(id);
                    room.update();
                } else {
                    room.persist();
                }

            } else {
                Room room = new Room();
                room.setId(id);
                room.delete();
            }

            response.sendRedirect(REDIRECT_ADDRESS);
        } catch (PersistenceException | NumberFormatException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }
}
