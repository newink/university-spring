<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <input type="hidden" name="action" value="create">
            <fieldset class="form-group">
                <label for="first_name">First Name</label>
                <input required type="text" class="form-control" id="first_name" name="first_name">
            </fieldset>
            <fieldset class="form-group">
                <label for="last_name">Last Name</label>
                <input required type="text" class="form-control" id="last_name" name="last_name">
            </fieldset>
            <fieldset class="form-group">
                <label for="address">Address</label>
                <input required type="text" class="form-control" id="address" name="address">
            </fieldset>
            <fieldset class="form-group">
                <label for="course">Course</label>
                <input required type="number" class="form-control" id="course" name="course">
            </fieldset>
            <fieldset class="form-group">
                <label for="group_id">Group Id</label>
                <input required type="number" class="form-control" id="group_id" name="group_id">
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
