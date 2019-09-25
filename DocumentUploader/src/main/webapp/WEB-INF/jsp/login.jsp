<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html >
<html>
<head>
<link rel="stylesheet" href="css/header_footerCSS.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.css" />
<link rel="stylesheet" href="css/login.css" />
<title>Log In</title>
</head>
<body>
	<%@ include file="header.jsp"  %>
	
	<sf:form action="documentManagement" method="post"
		modelAttribute="user">
		<sf:label path="email">Email: </sf:label>
		<sf:input path="email" type="text" />
		<br>
		<sf:label path="password">Password: </sf:label>
		<sf:input path="password" type="password" />
		<br>
		<input type="submit" value="Log In" >
	</sf:form>
	<p class="badCreds">${ badCreds }</p>
	
	<%@ include file="footer.jsp"  %>
</body>
</html>