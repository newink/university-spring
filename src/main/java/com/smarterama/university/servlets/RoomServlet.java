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

@WebServlet(value = "/room")
public class RoomServlet extends HttpServlet {
    private static final String INSERT_UPDATE_JSP = "/WEB-INF/views/create/room.jsp";
    private static final String REDIRECT_ADDRESS = "/university/rooms";

    @Autowired
    private Room room;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        if (action.equalsIgnoreCase("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            room.setId(id);
            try {
                request.setAttribute("room", room.retrieve());
                request.setAttribute("id", id);
            } catch (PersistenceException e) {
                String error = "Error: " + e.getMessage();
                request.setAttribute("error", error);
                getServletContext().getRequestDispatcher(INSERT_UPDATE_JSP).forward(request, response);
            }
        }
        getServletContext().getRequestDispatcher(INSERT_UPDATE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            room.setFieldsFromRequest(request.getParameterMap());
            if (room.getId() != -1) {
                room.update();
            } else {
                room.persist();
            }
            response.sendRedirect(REDIRECT_ADDRESS);
        } catch (PersistenceException | NumberFormatException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(INSERT_UPDATE_JSP).forward(request, response);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
}
