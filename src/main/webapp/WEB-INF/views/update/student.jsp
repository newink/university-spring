<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/_header.jsp" />
<body>
<div class="container">
    <div class="col-md-5">
        <c:if test="${not empty error}">
            <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/student" method="post">
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="action" value="update">
            <fieldset class="form-group">
                <label for="first_name">First Name</label>
                <input required type="text" class="form-control" id="first_name" name="first_name" value="${updatedStudent.firstName}">
            </fieldset>
            <fieldset class="form-group">
                <label for="last_name">Last Name</label>
                <input required type="text" class="form-control" id="last_name" name="last_name" value="${updatedStudent.lastName}">
            </fieldset>
            <fieldset class="form-group">
                <label for="address">Address</label>
                <input required type="text" class="form-control" id="address" name="address" value="${updatedStudent.address}">
            </fieldset>
            <fieldset class="form-group">
                <label for="course">Course</label>
                <input required type="number" class="form-control" id="course" name="course" value="${updatedStudent.course}">
            </fieldset>
            <fieldset class="form-group">
                <label for="group_id">Group Id</label>
                <input required type="number" class="form-control" id="group_id" name="group_id" value="${updatedStudent.group.id}">
            </fieldset>
            <fieldset class="form-group">
                <label for="subsidized">Subsidized</label>
                <input type="checkbox" class="form-control" id="subsidized" name="subsidized">
            </fieldset>
            <button type="submit" class="btn btn-default">Update</button>
        </form>
    </div>
</div>
</body>
</html>
