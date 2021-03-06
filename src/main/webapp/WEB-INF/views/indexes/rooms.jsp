<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Rooms Index</h1>
        <a href="room">Add new room</a>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <table class="table">
            <thead>
            <tr>
                <th>Room Id</th>
                <th>Room Number</th>
                <th>Room Capacity</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${rooms}" var="room">
                <tr>
                    <td><c:out value="${room.id}"/></td>
                    <td><c:out value="${room.roomNumber}"/></td>
                    <td><c:out value="${room.capacity}"/></td>
                    <td><a href="/university/room?action=update&id=${room.id}"><i class="fa fa-pencil"></i></a>
                    </td>
                    <td>
                        <form method="post" action="/university/delete" class="inline">
                            <input type="hidden" name="id" value="${room.id}">
                            <input type="hidden" name="entity" value="room">
                            <button type="submit" class="link-button">
                                <i class="fa fa-times"></i>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
