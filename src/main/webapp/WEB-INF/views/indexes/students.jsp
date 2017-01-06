<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Students Index</h1>
        <a href="student">Add new student</a>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
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
                    <td><c:out value="${student.id}"/></td>
                    <td><c:out value="${student.firstName}"/></td>
                    <td><c:out value="${student.lastName}"/></td>
                    <td><c:out value="${student.address}"/></td>
                    <td><c:out value="${student.course}"/></td>
                    <td><c:out value="${student.group.groupNumber}"/></td>
                    <td><a href="/university/student?action=update&id=${student.id}"><i
                            class="fa fa-pencil"></i></a></td>
                    <td>
                        <form method="post" action="/university/delete" class="inline">
                            <input type="hidden" name="id" value="${student.id}">
                            <input type="hidden" name="entity" value="student">
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
