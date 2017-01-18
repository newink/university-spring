<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="col-md-5">
        <h1>New Room</h1>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/room" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <fieldset class="form-group">
                <label for="room_number">Room Number</label>
                <input required type="number" class="form-control" id="room_number" name="room_number"
                       value="${room.roomNumber}">
            </fieldset>
            <fieldset class="form-group">
                <label for="capacity">Capacity</label>
                <input required type="number" class="form-control" id="capacity" name="capacity" value="${room.capacity}">
            </fieldset>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
