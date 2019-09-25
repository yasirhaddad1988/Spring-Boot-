<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/header_footerCSS.css" />
<title>Registration Confirmation</title>
</head>
<body>
	<%@ include file="header.jsp"  %>
	<h1>Account creation successful</h1>
	<p>${ user.name } registered with email: ${ user.email } and user role: ${ user.role }</p>
	<p>Your account name is ${ account.accountName } with service level ${ account.serviceLevel }</p>
	<%@ include file="footer.jsp"  %>
</body>
</html>