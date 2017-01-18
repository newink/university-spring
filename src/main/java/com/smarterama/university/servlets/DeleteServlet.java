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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entityName = request.getParameter("entity");
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            DomainObject domainObject = getObject(entityName);
            domainObject.setId(id);
            domainObject.delete();
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(String.format(INDEX_JSP, entityName)).forward(request, response);
        }
        response.sendRedirect(String.format(REDIRECT_ADDRESS, entityName));
    }

    private DomainObject getObject(String name) {
        switch (name) {
            case "student": return new Student();
            case "group": return new Group();
            case "room": return new Room();
            case "discipline": return new Discipline();
            case "lecturer": return new Lecturer();
            case "lesson": return new Lesson();
            default: return null;
        }
    }
}
