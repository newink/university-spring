<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp" />
<body>
<div class="container">
    <div class="col-md-5">
        <h1>New Group</h1>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/group" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <fieldset class="form-group">
                <label for="group_number">Group Number</label>
                <input required type="number" class="form-control" id="group_number" name="group_number">
            </fieldset>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
