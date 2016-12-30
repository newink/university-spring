<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp" />
<body>
<div class="container">
    <div class="col-md-5">
        <h1>New Lecturer</h1>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/lecturer" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <fieldset class="form-group">
                <label for="first_name">First Name</label>
                <input required type="text" class="form-control" id="first_name" name="first_name">
            </fieldset>
            <fieldset class="form-group">
                <label for="last_name">Last Name</label>
                <input required type="text" class="form-control" id="last_name" name="last_name">
            </fieldset>
            <fieldset class="form-group">
                <label for="email">Email</label>
                <input required type="text" class="form-control" id="email" name="email">
            </fieldset>
            <fieldset class="form-group">
                <label for="degree">Degree</label>
                <select id="degree" name="degree">
                    <option>Associate</option>
                    <option>Bachelor</option>
                    <option>Professional</option>
                    <option>Master</option>
                </select>
            </fieldset>
            <fieldset class="form-group">
                <label for="disciplines">Disciplines</label>
                <select id="disciplines" name="disciplines" multiple>
                    <c:forEach items="${disciplines}" var="discipline">
                        <option value="${discipline.id}"><c:out value="${discipline.name}" /></option>
                    </c:forEach>
                </select>
            </fieldset>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
