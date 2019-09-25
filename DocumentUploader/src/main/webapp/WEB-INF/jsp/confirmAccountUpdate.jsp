<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/header_footerCSS.css" />
<title>Confirm Update</title>
</head>
<body>
<%@ include file="loggedInHeader.jsp"%>
<h1>Account update successful</h1>
	<p>${ user.name } updated with email: ${ user.email } and user role: ${ user.role }</p>
	<p>${ account.accountName } updated to service level: ${ account.serviceLevel }</p>
	


<%@ include file="footer.jsp"%>
</body>
</html>