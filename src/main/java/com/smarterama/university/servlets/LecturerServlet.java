package com.smarterama.university.servlets;

import com.smarterama.university.domain.Discipline;
import com.smarterama.university.domain.Lecturer;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/lecturer")
public class LecturerServlet extends HttpServlet {
    private static final String INSERT_UPDATE_JSP = "/WEB-INF/views/create/lecturer.jsp";
    private static final String INDEX_JSP = "/WEB-INF/views/indexes/lecturers.jsp";
    private static final String REDIRECT_ADDRESS = "/university/lecturers";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        List<Discipline> disciplineList = null;
        try {
            disciplineList = new Discipline().getAll();
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
        request.setAttribute("disciplines", disciplineList);
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
            if (request.getParameter("first_name") != null) {
                Lecturer lecturer = new Lecturer(request.getParameterMap());
                String[] disciplineStrings = request.getParameterValues("disciplines");

                for (String disciplineString : disciplineStrings) {
                    int disciplineId = Integer.parseInt(disciplineString);
                    Discipline discipline = new Discipline();
                    discipline.setId(disciplineId);
                    discipline.retrieve();
                    lecturer.addDiscipline(discipline);
                }

                if (id != -1) {
                    lecturer.setId(id);
                    lecturer.update();
                } else {
                    lecturer.persist();
                }

            } else {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(id);
                lecturer.delete();
            }

            response.sendRedirect(REDIRECT_ADDRESS);
        } catch (PersistenceException | NumberFormatException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
    }
}
