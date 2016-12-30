<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp" />
<body>
<div class="container">
    <div class="col-md-5">
        <h1>New Lesson</h1>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/lesson" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <fieldset class="form-group">
                <label for="room">Room</label>
                <select id="room" name="room">
                    <c:forEach items="${rooms}" var="room">
                        <option value="${room.id}"><c:out value="${room.roomNumber}" /></option>
                    </c:forEach>
                </select>
            </fieldset>
            <fieldset class="form-group">
                <label for="lecturer">Lecturer</label>
                <select id="lecturer" name="lecturer">
                    <c:forEach items="${lecturers}" var="lecturer">
                        <option value="${lecturer.id}"><c:out value="${lecturer.firstName} ${lecturer.lastName}" /></option>
                    </c:forEach>
                </select>
            </fieldset>
            <fieldset class="form-group">
                <label for="group">Group</label>
                <select id="group" name="group">
                    <c:forEach items="${groups}" var="group">
                        <option value="${group.id}"><c:out value="${group.groupNumber}" /></option>
                    </c:forEach>
                </select>
            </fieldset>
            <fieldset class="form-group">
                <label for="discipline">Discipline</label>
                <select id="discipline" name="discipline">
                    <c:forEach items="${disciplines}" var="discipline">
                        <option value="${discipline.id}"><c:out value="${discipline.name}" /></option>
                    </c:forEach>
                </select>
            </fieldset>
            <fieldset class="form-group">
                <label for="start_date">Start Date</label>
                <input required type="date" class="form-control" id="start_date" name="start_date">
            </fieldset>
            <fieldset class="form-group">
                <label for="finish_date">Finish Date</label>
                <input required type="date" class="form-control" id="finish_date" name="finish_date">
            </fieldset>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
