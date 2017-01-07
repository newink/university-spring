package com.smarterama.university.servlets;

import com.smarterama.university.domain.*;
import com.smarterama.university.exceptions.PersistenceException;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final String INDEX_JSP = "/WEB-INF/views/indexes/%ss.jsp";
    private static final String REDIRECT_ADDRESS = "/university/%ss";

    @Autowired
    private Student student;
    @Autowired
    private Group group;
    @Autowired
    private Room room;
    @Autowired
    private Discipline discipline;
    @Autowired
    private Lecturer lecturer;
    @Autowired
    private Lesson lesson;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entityName = request.getParameter("entity");
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            if ("student".equals(entityName)) {
                student.setId(id);
                student.delete();
            }
            if ("group".equals(entityName)) {
                group.setId(id);
                group.delete();
            }
            if ("room".equals(entityName)) {
                room.setId(id);
                room.delete();
            }
            if ("discipline".equals(entityName)) {
                discipline.setId(id);
                discipline.delete();
            }
            if ("lecturer".equals(entityName)) {
                lecturer.setId(id);
                lecturer.delete();
            }
            if ("lesson".equals(entityName)){
                lesson.setId(id);
                lesson.delete();
            }
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(String.format(INDEX_JSP, entityName)).forward(request, response);
        }
        response.sendRedirect(String.format(REDIRECT_ADDRESS, entityName));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
}
