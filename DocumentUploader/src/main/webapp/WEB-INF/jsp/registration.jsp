<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/header_footerCSS.css" />
<link rel="stylesheet" href="css/validation.css" />
<title>Register</title>
</head>
<body>
	<%@ include file="header.jsp"  %>
	<div class="container">
		<h1>${ badRegister }</h1>
		<form class="gridForm" action="confirmAccount" method="post">
			<label>Enter the user name:</label>
			<s:bind path="user.name">
				<input type="text" name="${status.expression}" value="${status.value}" required> <br>
			</s:bind>
			<label>Enter the user email:</label>
			<s:bind path="user.email">
				<input type="text" name="${status.expression}" value="${status.value}" required> <br>
			</s:bind>
			<label for="psw">Enter the user password:</label>
			<s:bind path="user.password">
				<input type="password" id="psw" name="${status.expression}" value="${status.value}" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{5,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 5 or more characters" required> <br>
			</s:bind>
			<div id="message">
			<h3>Password must contain the following:</h3>
			<p id="letter" class="invalid">A <b>lowercase</b> letter</p>
			<p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
			<p id="number" class="invalid">A <b>number</b></p>
			<p id="length" class="invalid">Minimum <b>5 characters</b></p>
			</div>
			<label>Confirm password:</label>
			<input type="password" name="cpassword" required> <br>
			<label>Enter the security question:</label>
			<s:bind path="user.question">
				<input type="text" name="${status.expression}" value="${status.value}" required> <br>
			</s:bind>
			<label>Enter the security answer:</label>
			<s:bind path="user.answer">
				<input type="text" name="${status.expression}" value="${status.value}" required> <br>
			</s:bind>
			<label>Enter the account name:</label>
			<s:bind path="account.accountName">
				<input type="text" name="${status.expression}" value="${status.value}" required> <br>
			</s:bind>
			<select id="service" name="service" required> 
    			<option value="Bronze">Bronze</option>
    			<option value="Silver">Silver</option>
   				<option value="Gold">Gold</option>
    			<option value="Unlimited">Unlimited</option> 
    			<option value="Enterprise">Enterprise</option> 
			</select>
			<input type="hidden" name="_service" value="1"/> 
			<input type="submit" value="Submit" >
		</form>
	</div>
	<script src="js/password.js"></script>
	<%@ include file="footer.jsp"  %>
</body>
</html>