<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users found</title>
</head>
<body>
<div>
    <table>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Password</th>
            <th>Delete</th>
            <th>Update</th>
        </tr>
        <c:forEach var = "user" items = "${requestScope.users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td><a href="${pageContext.request.contextPath}/schoolapp/user-delete?id=${user.id}&username=${user.username}&password=${user.password}">Delete</a></td>
                <td><a href="${pageContext.request.contextPath}/schoolapp/static/templates/userupdate.jsp?id=${user.id}&username=${user.username}&password=${user.password}">Update</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div>
    <c:if test="${requestScope.deleteAPIError}">
        <p>Something went wrong in Delete</p>
    </c:if>
</div>

<div>
    <c:if test="${requestScope.updateAPIError}">
        <p>Something went wrong in Update</p>
    </c:if>
</div>

</body>
</html>