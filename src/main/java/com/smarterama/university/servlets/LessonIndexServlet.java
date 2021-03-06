package com.smarterama.university.servlets;

import com.smarterama.university.domain.Lesson;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/lessons")
public class LessonIndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Lesson> lessonList = null;
        String message = "";

        try {
            lessonList = new Lesson().collectAll();
        } catch (PersistenceException e) {
            message = "Error: " + e.getMessage();
        }
        request.getSession().setAttribute("lessons", lessonList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/indexes/lessons.jsp").forward(request, response);
    }
}
