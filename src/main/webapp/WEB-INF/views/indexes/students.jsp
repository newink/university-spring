<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/_header.jsp" />
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Students Index</h1>
        <a href="student">Add new student</a>
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert-warning">${error}</div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Student Id</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Address</th>
                        <th>Course</th>
                        <th>Group Number</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${students}" var="student">
                        <tr>
                            <td><c:out value="${student.id}" /></td>
                            <td><c:out value="${student.firstName}" /></td>
                            <td><c:out value="${student.lastName}" /></td>
                            <td><c:out value="${student.address}" /></td>
                            <td><c:out value="${student.course}" /></td>
                            <td><c:out value="${student.group.groupNumber}" /></td>
                            <td><a href="/university/student?action=update&id=${student.id}"><i class="fa fa-pencil"></i></a></td>
                            <td><a href="/university/student?action=delete&id=${student.id}"><i class="fa fa-times"></i></a></td>
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
