package com.smarterama.university.servlets;

import com.smarterama.university.domain.*;
import com.smarterama.university.exceptions.PersistenceException;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            if ("student".equals(entityName)) {
                Student student = new Student();
                student.setId(id);
                student.delete();
            }
            if ("group".equals(entityName)) {
                Group group = new Group();
                group.setId(id);
                group.delete();
            }
            if ("room".equals(entityName)) {
                Room room = new Room();
                room.setId(id);
                room.delete();
            }
            if ("discipline".equals(entityName)) {
                Discipline discipline = new Discipline();
                discipline.setId(id);
                discipline.delete();
            }
            if ("lecturer".equals(entityName)) {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(id);
                lecturer.delete();
            }
            if ("lesson".equals(entityName)){
                Lecturer lecturer = new Lecturer();
                lecturer.setId(id);
                lecturer.delete();
            }
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(String.format(INDEX_JSP, entityName)).forward(request, response);
        }
        response.sendRedirect(String.format(REDIRECT_ADDRESS, entityName));
    }
}
