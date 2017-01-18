<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="col-md-5">
        <h1>New Room</h1>
        <c:if test="${not empty error}">
        <p class="bg-warning"><c:out value="${error}"/><p>
        </c:if>
        <form action="/university/discipline" method="post">
            <c:if test="${not empty id}">
                <input type="hidden" name="id" value="${id}">
            </c:if>
            <fieldset class="form-group">
                <label for="name">Discipline Name</label>
                <input required type="text" class="form-control" id="name" name="name" value="${discipline.name}">
            </fieldset>
            <fieldset class="form-group">
                <label for="test_type">Final Exam Type</label>
                <select id="test_type" name="test_type">
                    <option>Exam</option>
                    <option>Test</option>
                    <option>Essay</option>
                </select>
            </fieldset>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
