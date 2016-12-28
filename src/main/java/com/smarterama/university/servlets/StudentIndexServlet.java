package com.smarterama.university.servlets;

import com.smarterama.university.domain.Group;
import com.smarterama.university.domain.Student;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.List;

@WebServlet(value = "/students")
public class StudentIndexServlet extends HttpServlet {
    private Student serviceStudent;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceStudent = new Student();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> studentList = null;
        String message = "";

        try {
            studentList = serviceStudent.getAll();
        } catch (PersistenceException e) {
            message = "Error " + e.getMessage();
        }
        request.getSession().setAttribute("students", studentList);
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/WEB-INF/views/indexes/students.jsp").forward(request, response);
    }
}
