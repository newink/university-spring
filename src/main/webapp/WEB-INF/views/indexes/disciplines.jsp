<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp" />
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Disciplines Index</h1>
        <a href="discipline">Add new discipline</a>
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert-warning">${error}</div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Discipline Id</th>
                        <th>Name</th>
                        <th>Final Exam Type</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${disciplines}" var="discipline">
                        <tr>
                            <td><c:out value="${discipline.id}" /></td>
                            <td><c:out value="${discipline.name}" /></td>
                            <td><c:out value="${discipline.finalExamType}" /></td>
                            <td><a href="/university/discipline?action=update&id=${discipline.id}"><i class="fa fa-pencil"></i></a></td>
                            <td>
                                <form method="post" action="/university/discipline" class="inline">
                                    <input type="hidden" name="id" value="${discipline.id}">
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
