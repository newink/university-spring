package com.smarterama.university.servlets;

import com.smarterama.university.domain.Lecturer;
import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/lecturers")
public class LecturerServlet extends HttpServlet {
    private Lecturer serviceLecturer;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceLecturer = new Lecturer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Lecturer> lecturerList = null;
        String message = "";

        try {
            lecturerList = serviceLecturer.getAll();
        } catch (PersistenceException e) {
            message = "Error: " + e.getMessage();
        }
        request.getSession().setAttribute("lecturers", lecturerList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/lecturers.jsp").forward(request, response);
    }
}
