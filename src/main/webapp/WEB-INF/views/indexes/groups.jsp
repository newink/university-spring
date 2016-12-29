<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp" />
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Groups Index</h1>
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert-warning">${error}</div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Group Id</th>
                        <th>Number</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${groups}" var="group">
                        <tr>
                            <td><c:out value="${group.id}" /></td>
                            <td><c:out value="${group.groupNumber}" /></td>
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
