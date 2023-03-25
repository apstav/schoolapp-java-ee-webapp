<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Νέα Στοιχεία Χρήστη</h1>
<p>teacher ${requestScope.user.username}</p>
<p>teacher ${requestScope.user.password}</p>
<a href="${pageContext.request.contextPath}/schoolapp/static/templates/usersmenu.jsp">Επιστροφή</a>

</body>
</html>