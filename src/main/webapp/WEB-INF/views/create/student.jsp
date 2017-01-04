<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="col-md-5">
        <h1>New Student</h1>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/student" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <fieldset class="form-group">
                <label for="first_name">First Name</label>
                <input required type="text" class="form-control" id="first_name" name="first_name"
                       value="${student.firstName}">
            </fieldset>
            <fieldset class="form-group">
                <label for="last_name">Last Name</label>
                <input required type="text" class="form-control" id="last_name" name="last_name"
                       value="${student.lastName}">
            </fieldset>
            <fieldset class="form-group">
                <label for="address">Address</label>
                <input required type="text" class="form-control" id="address" name="address"
                       value="${student.address}">
            </fieldset>
            <fieldset class="form-group">
                <label for="course">Course</label>
                <input required type="number" class="form-control" id="course" name="course" value="${student.course}">
            </fieldset>
            <fieldset class="form-group">
                <label for="group_id">Group Id</label>
                <select id="group_id" name="group_id">
                    <c:forEach items="${groups}" var="group">
                        <option value="${group.id}">${group.groupNumber}</option>
                    </c:forEach>
                </select>
            </fieldset>
            <fieldset class="form-group">
                <label for="subsidized">Subsidized</label>
                <input type="checkbox" class="form-control" id="subsidized" name="subsidized">
            </fieldset>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
