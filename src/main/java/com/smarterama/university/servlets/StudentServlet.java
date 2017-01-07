package com.smarterama.university.servlets;

import com.smarterama.university.domain.Group;
import com.smarterama.university.domain.Student;
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

@WebServlet(value = "/student")
public class StudentServlet extends HttpServlet {
    private static final String INSERT_UPDATE_JSP = "/WEB-INF/views/create/student.jsp";
    private static final String REDIRECT_ADDRESS = "/university/students";

    @Autowired
    private Student student;
    @Autowired
    private Group group;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        List<Group> groupList = null;
        try {
            groupList = group.getAll();
            request.setAttribute("groups", groupList);
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(INSERT_UPDATE_JSP).forward(request, response);
        }
        if (action.equalsIgnoreCase("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            student.setId(id);
            try {
                request.setAttribute("student", student.retrieve());
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
            student.setFieldsFromRequest(request.getParameterMap());
            group.setId(Integer.parseInt(request.getParameter("group_id")));
            student.setGroup(group.retrieve());

            if (student.getId() != -1) {
                student.update();
            } else {
                student.persist();
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
