<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Lecturers Index</h1>
        <a href="lecturer">Add new lecturer</a>
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
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lecturers}" var="lecturer">
                        <tr>
                            <td><c:out value="${lecturer.id}"/></td>
                            <td><c:out value="${lecturer.firstName}"/></td>
                            <td><c:out value="${lecturer.lastName}"/></td>
                            <td><c:out value="${lecturer.email}"/></td>
                            <td><c:out value="${lecturer.degree}"/></td>
                            <td><a href="/university/lecturer?action=update&id=${lecturer.id}"><i
                                    class="fa fa-pencil"></i></a></td>
                            <td>
                                <form method="post" action="/university/delete" class="inline">
                                    <input type="hidden" name="id" value="${lecturer.id}">
                                    <input type="hidden" name="entity" value="lecturer">
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
