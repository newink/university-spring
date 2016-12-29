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
public class LecturerIndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Lecturer> lecturerList = null;
        String message = "";

        try {
            lecturerList = new Lecturer().getAll();
        } catch (PersistenceException e) {
            message = "Error: " + e.getMessage();
        }
        request.getSession().setAttribute("lecturers", lecturerList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/indexes/lecturers.jsp").forward(request, response);
    }
}
