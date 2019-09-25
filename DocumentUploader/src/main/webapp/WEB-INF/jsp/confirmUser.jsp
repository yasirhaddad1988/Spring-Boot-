<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/header_footerCSS.css" />
<title>New User Confirmed</title>
</head>
<body>
	<%@ include file="loggedInHeader.jsp"%> <br>
	<h1>User creation Successful</h1>
	<p>${ user.name } created with email: ${ user.email } under the account: ${ account.accountName }</p> <br> <br>
	<%@ include file="footer.jsp"%>
</body>
</html>