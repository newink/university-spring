<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/_header.jsp" />
<body>
    <div class="container">
        <div class="jumbotron">
            <h1>Rooms Index</h1>
            <c:choose>
                <c:when test="${not empty error}">
                    <div class="alert-warning">${error}</div>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Room Id</th>
                            <th>Room Number</th>
                            <th>Room Capacity</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${rooms}" var="room">
                            <tr>
                                <td><c:out value="${room.id}" /></td>
                                <td><c:out value="${room.roomNumber}" /></td>
                                <td><c:out value="${room.capacity}" /></td>
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
