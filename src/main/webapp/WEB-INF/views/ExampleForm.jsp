<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
<form:form action="addExample" modelAttribute="ExampleModel" method="post">
	<table>
		<tr>
			<td>title:</td>
			<td><form:input type="text" path="title"/></td>
			<td align="right"><form:errors path="title" cssClass="error"/> </td>
		</tr>
		<tr>
			<td>content:</td>
			<td><form:textarea type="text" path="content" id="descriptionInput"/></td>
			<td align="right"><form:errors path="content" cssClass="error"/> </td>
		</tr>
		<tr>
			<td></td>
			<td><input type="Submit"/> </td>
		</tr>		
	</table>
	<form:input type="hidden" path="user_id" value="${userId}"/>
</form:form>
</body>
</html>