package com.smarterama.university.servlets;

import com.smarterama.university.domain.*;
import com.smarterama.university.exceptions.PersistenceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/lesson")
public class LessonServlet extends HttpServlet {
    private static final String INSERT_UPDATE_JSP = "/WEB-INF/views/create/lesson.jsp";
    private static final String INDEX_JSP = "/WEB-INF/views/indexes/lessons.jsp";
    private static final String REDIRECT_ADDRESS = "/university/lessons";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        List<Discipline> disciplineList = null;
        List<Room> roomList = null;
        List<Group> groupList = null;
        List<Lecturer> lecturerList = null;
        try {
            disciplineList = new Discipline().getAll();
            roomList = new Room().getAll();
            groupList = new Group().getAll();
            lecturerList = new Lecturer().getAll();
        } catch (PersistenceException e) {
            String error = "Error: " + e.getMessage();
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher(INDEX_JSP).forward(request, response);
        }
        request.setAttribute("disciplines", disciplineList);
        request.setAttribute("rooms", roomList);
        request.setAttribute("groups", groupList);
        request.setAttribute("lecturers", lecturerList);
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
            if (request.getParameter("room") != null) {
                String startDateString = request.getParameter("start_date");
                String finishDateString = request.getParameter("finish_date");

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate;
                Date finishDate;
                try {
                    startDate = format.parse(startDateString);
                    finishDate = format.parse(finishDateString);
                } catch (ParseException e) {
                    throw new ServletException(e);
                }

                Lesson lesson = new Lesson();
                lesson.setStartDate(startDate);
                lesson.setFinishDate(finishDate);
                Room room = new Room();
                room.setId(Integer.parseInt(request.getParameter("room")));
                lesson.setRoom(room.retrieve());

                Lecturer lecturer = new Lecturer();
                lecturer.setId(Integer.parseInt(request.getParameter("lecturer")));
                lesson.setLecturer(lecturer.retrieve());

                Group group = new Group();
                group.setId(Integer.parseInt(request.getParameter("group")));
                lesson.setGroup(group.retrieve());

                Discipline discipline = new Discipline();
                discipline.setId(Integer.parseInt(request.getParameter("discipline")));
                lesson.setDiscipline(discipline.retrieve());

                if (id != -1) {
                    lesson.setId(id);
                    lesson.update();
                } else {
                    lesson.persist();
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
