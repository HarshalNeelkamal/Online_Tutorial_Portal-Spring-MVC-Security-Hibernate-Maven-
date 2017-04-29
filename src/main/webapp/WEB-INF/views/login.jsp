<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/LogSign.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">

</head>
<body>
<div id="NavigationItem">
	<div id="buttonHolder">
		<a href="UserSignup">SignUp</a>
	</div>
</div>

<h1 id="app_name">Get Your Code...!!</h1>

<div id="messageHolder">
<c:if test="${not empty error}">
	<div class="error">${error}</div>
</c:if>
<c:if test="${not empty logout}">
	<div class="logout">${logout}</div>
</c:if>
</div>
<form action="performAuthenticatation" method="post" id="form_css_ref">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<table>
		<tr>
			<td><input type="text" name="j_username" class="input_block" placeholder="username" /></td>
		</tr>
		<tr>
			<td><input type="password" name="j_password" class="input_block" placeholder="password" /></td>
		</tr>
		<tr>
			<td align="center"><input type="submit" value="login"/></td>
		</tr>
	</table>
</form>
</body>
</html>