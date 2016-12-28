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

@WebServlet(value = "/student")
public class StudentServlet extends HttpServlet {
    private Student serviceStudent;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        try {
            switch (action) {
                case "delete": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    serviceStudent.setId(id);
                    serviceStudent.delete();
                    response.sendRedirect("/university/students");
                    break;
                }
                case "update": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    serviceStudent.setId(id);
                    serviceStudent.retrieve();
                    request.getSession().setAttribute("id", id);
                    request.getSession().setAttribute("updatedStudent", serviceStudent);
                    getServletContext().getRequestDispatcher("/WEB-INF/views/update/student.jsp").forward(request, response);
                    break;
                }
                default: {
                    getServletContext().getRequestDispatcher("/WEB-INF/views/create/student.jsp").forward(request, response);
                }
            }
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.getSession().setAttribute("error", error);
            getServletContext().getRequestDispatcher("/WEB-INF/views/indexes/student.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String address = request.getParameter("address");
        int course = Integer.parseInt(request.getParameter("course"));
        int groupId = Integer.parseInt(request.getParameter("group_id"));
        boolean subsidized = "on".equals(request.getParameter("subsidized"));

        try {
            Student student = new Student(firstName, lastName, address, course, subsidized);
            Group group = new Group();
            group.setId(groupId);

            student.setGroup(group.retrieve());
            switch (action) {
                case "create": {
                    student.persist();
                    break;
                }
                case "update": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    student.setId(id);
                    student.update();
                    break;
                }
            }
            response.sendRedirect("/university/students");
        } catch (PersistenceException | NumberFormatException e) {
            String error = "Error: " + e.getMessage();
            request.getSession().setAttribute("error", error);
            getServletContext().getRequestDispatcher("/WEB-INF/views/create/student.jsp").forward(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        serviceStudent = new Student();
    }
}
