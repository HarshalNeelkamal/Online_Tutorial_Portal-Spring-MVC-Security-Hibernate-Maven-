<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/MyExTu.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/SlimScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">
<script type="text/javascript">
function formValidation() {
    if (document.forms["EditExampleForm"]["title"].value !== ""){
    	document.getElementById("error").innerHTML = "";
    	document.getElementById("submit").disabled = false;
    } else{
    	document.getElementById("error").innerHTML = "title required";
    	document.getElementById("submit").disabled = true;
    }
}
function logoutRequest() {
	document.getElementById("logOutForm").submit();
}
</script>
</head>
<body>
<sec:authentication var="user_name_sec" property="principal" />

<div id="NavigationItem">
	<form action="userLoggOut" id="logOutForm" method="post"> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	<div class="NameHolder">
		<h3 class="userNameTag">Hello ${user_name_sec.username}</h3>
		<div class="dropDown">
			<a href="javascript:logoutRequest()">Logout</a>
		</div>
	</div>
	<div class="leftButtonHolder">
		<a href="myTutorials">My tutorial</a>
		<a href="myExamples">My Example</a>
		<a href="userHome">Home</a>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a href="admin/subAdmins">Sub-Admins</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
			<a href="sub_Admin/viewInbox">Inbox</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
			<a href="sub_Admin/viewUsers">UsersList</a>
		</sec:authorize>
	</div>
</div>
<c:if test="${not empty Error}">
<h1>${Error}</h1>
</c:if>
<c:if test="${empty Error}">
<div>
<form action="editExampleAttempt" method="post" id="EditExampleForm">
<table id="edit_table"> 
<tr>
<td><h3 class="EditFormError" id="error"></h3></td>
</tr>
<tr>
<td><input onkeyup="formValidation()" type="text" value="${title}" name="title" id="title"/></td>
</tr>
<tr>
<td><textarea name="content" id="content" rows=10 cols=100 >${content}</textarea><!-- <input type="text" name="content" id="content" size="40" value="${content}"/> --></td>
</tr>
<tr>
<td><input type="submit" value="submit" id="submit"/></td>
</tr>
<tr>
<td><input type="hidden" value="${id}" name="id" /></td>
</tr>
</table>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</div>
</c:if>

</body>
</html>