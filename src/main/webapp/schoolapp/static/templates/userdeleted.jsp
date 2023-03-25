<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>User delete</title>
</head>
<body>
<p>User: ${requestScope.user.id} ${requestScope.user.username} ${requestScope.user.password}
    was deleted</p>
<a href="${pageContext.request.contextPath}/schoolapp/static/templates/usersmenu.jsp">Επιστροφή</a>
</body>
</html>