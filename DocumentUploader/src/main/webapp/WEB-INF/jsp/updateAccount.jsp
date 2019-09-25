<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/header_footerCSS.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Account</title>
</head>
<body>
	<%@ include file="loggedInHeader.jsp"%>
	<br>
	<h1>${ badRegister }</h1>
	<br>
	<h1>Update Account Information</h1>
	<form class="gridForm" action="confirmAccountUpdate" method="post">
		<label>Enter new user name:</label> 
		<input type="text" name="name"><br> 
		<label>Enter new user email:</label> 
		<input type="text" name="email"> <br> 
		<label>Enter current user password:</label> 
		<input type="password" name="currentPassword" required> <br>  
		<label>Enter new user password:</label> 
		<input type="password" name="password"> <br> 
		<label>Confirm new user password:</label> 
		<input type="password" name="cpassword"> <br> 
		<label>Enter new security question:</label> 
		<input type="text" name="question"> <br>
		<label>Enter new security answer:</label> 
		<input type="text" name="answer"> <br> 
		<label>Enter new account name:</label>
		<input type="text" name="accountName"> <br>
		<select id="service" name="service"> 
    		<option value="Bronze">Bronze</option>
    		<option value="Silver">Silver</option>
   			<option value="Gold">Gold</option>
    		<option value="Unlimited">Unlimited</option> 
    		<option value="Enterprise">Enterprise</option> 
		</select>
		<input type="hidden" name="_service" value="1"/> 
		<input type="submit" value="Submit">
	</form> <br>
	<a href="createUser"><button>Add User</button></a>

	<br>
	<br>

	<%@ include file="footer.jsp"%>
</body>
</html>