<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../partials/header.jsp"/>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Groups Index</h1>
        <a href="group">Add new group</a>
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
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${groups}" var="group">
                        <tr>
                            <td><c:out value="${group.id}"/></td>
                            <td><c:out value="${group.groupNumber}"/></td>
                            <td><a href="/university/group?action=update&id=${group.id}"><i
                                    class="fa fa-pencil"></i></a></td>
                            <td>
                                <form method="post" action="/university/delete" class="inline">
                                    <input type="hidden" name="id" value="${group.id}">
                                    <input type="hidden" name="entity" value="group">
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
