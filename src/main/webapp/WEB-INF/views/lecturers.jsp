<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="partials/_header.jsp" />
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Lecturers Index</h1>
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert-warning">${error}</div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Lecturer Id</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Degree</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lecturers}" var="lecturer">
                        <tr>
                            <td><c:out value="${lecturer.id}" /></td>
                            <td><c:out value="${lecturer.firstName}" /></td>
                            <td><c:out value="${lecturer.lastName}" /></td>
                            <td><c:out value="${lecturer.email}" /></td>
                            <td><c:out value="${lecturer.degree}" /></td>
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
