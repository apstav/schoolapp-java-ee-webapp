<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>Teacher: ${requestScope.teacher.id} ${requestScope.teacher.firstname} ${requestScope.teacher.lastname}
		was deleted</p>
	<a href="${pageContext.request.contextPath}/schoolapp/static/templates/teachersmenu.jsp">Επιστροφή</a>
</body>
</html>
