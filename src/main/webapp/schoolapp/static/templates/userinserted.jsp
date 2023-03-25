<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Successfully inserted a User</title>
</head>
<body>
<h1>User inserted successfully</h1>
<div>
    <p>${requestScope.insertedUser.username}</p>
    <p>${requestScope.insertedUser.password}</p>
    <p></p>
</div>

<div>
    <a href="${pageContext.request.contextPath}/schoolapp/static/templates/usersmenu.jsp">Return</a>
</div>

</body>
</html>