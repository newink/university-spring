package com.smarterama.university.servlets;

import com.smarterama.university.domain.Discipline;
import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/disciplines")
public class DisciplineServlet extends HttpServlet {
    private Discipline serviceDiscipline;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceDiscipline = new Discipline();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Discipline> disciplineList = null;
        String message = "";

        try {
            disciplineList = serviceDiscipline.getAll();
        } catch (PersistenceException e) {
            message = "Error: " + e.getMessage();
        }
        request.getSession().setAttribute("disciplines", disciplineList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/disciplines.jsp").forward(request, response);
    }
}
