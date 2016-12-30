<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp" />
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Lessons Index</h1>
        <a href="lesson">Add new lesson</a>
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert-warning">${error}</div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Lesson Id</th>
                        <th>Room Number</th>
                        <th>Lecturer Name</th>
                        <th>Group Number</th>
                        <th>Discipline Name</th>
                        <th>Start Date</th>
                        <th>Finish Date</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lessons}" var="lesson">
                        <tr>
                            <td><c:out value="${lesson.id}" /></td>
                            <td><c:out value="${lesson.room.roomNumber}" /></td>
                            <td><c:out value="${lesson.lecturer.firstName} ${lesson.lecturer.lastName}" /></td>
                            <td><c:out value="${lesson.group.groupNumber}" /></td>
                            <td><c:out value="${lesson.discipline.name}" /></td>
                            <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lesson.startDate}" /></td>
                            <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lesson.finishDate}" /></td>
                            <td><a href="/university/lesson?action=update&id=${lesson.id}"><i class="fa fa-pencil"></i></a></td>
                            <td>
                                <form method="post" action="/university/lesson" class="inline">
                                    <input type="hidden" name="id" value="${lesson.id}">
                                    <button type="submit" class="link-button">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
