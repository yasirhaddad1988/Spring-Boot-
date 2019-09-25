<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/header_footerCSS.css" />
<script src="js/download.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DocumentUploader</title>
</head>
<body>
	<%@ include file="loggedInHeader.jsp"%>
	<h1>Logged In ${ user.email }</h1>
	<form method="POST" action="/upload" enctype="multipart/form-data">
		<input type="file" name="file" /><br />
		<br /> <input type="submit" value="Submit" />
	</form>
	<h2>${ maxUploads }</h2>
	<h1>Downloads</h1>
	<c:forEach items="${ documents }" var="document">
	<div style="display: flex;">
		<form method="POST" action="/uploads/{fileName:.+}" id="download" style="width: 30%" >
		<button onclick="download()" >${document.docName}</button>
		<input type="hidden" name="docName" value="${document.docName}"/>
		<input type="submit" style="visibility: hidden"/>
		</form>
		<form method="POST" action="deleteFile" id="delete" style="width: 30%">
		<button onclick="deleteFile()">Delete ${document.docName}</button>
		<input type="hidden" name="docName" value="${document.docName}"/>
		<input type="submit" style="visibility: hidden"/>
		</form>
		</div>
	</c:forEach>
	<!--  
	<c:forEach items="${ documents }" var="document">
		<form method="POST" action="deleteFile" id="delete" style="float:left">
		<button onclick="deleteFile()" style="width: 30%">Delete ${document.docName}</button>
		<input type="hidden" name="docName" value="${document.docName}"/>
		<input type="submit" style="visibility: hidden"/>
		</form>
	</c:forEach> -->
	
	<h1>${ advertisement }</h1>
	<%@ include file="footer.jsp"%>
	
</body>
</html>