<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/header_footerCSS.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Creation</title>
</head>
<body>
	<%@ include file="loggedInHeader.jsp"%>
	<br>
	<h1>${ badRegister }</h1>
	<br>
	<h1>New User Information</h1>
	<form class="gridForm" action="confirmUser" method="post">
		<label>Enter the user name:</label> 
		<input type="text" name="name" required><br> 
		<label>Enter the user email:</label> 
		<input type="text" name="email" required> <br> 
		<label>Enter the user password:</label> 
		<input type="password" name="password" required> <br> 
		<label>Confirm the user password:</label> 
		<input type="password" name="cpassword" required> <br> 
		<label>Enter the security question:</label> 
		<input type="text" name="question" required> <br>
		<label>Enter the security answer:</label> 
		<input type="text" name="answer" required> <br> 
		<input type="submit" value="Submit">
	</form>

	<br>
	<br>

	<%@ include file="footer.jsp"%>
</body>
</html>