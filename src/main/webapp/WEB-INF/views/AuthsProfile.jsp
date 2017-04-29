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
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/AuthorsProfile.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
<script type="text/javascript">
function tutorial_Clicked(tut_id) {
	$(location).attr('href', "authorsTut?tut_id="+tut_id);
}
function logoutRequest() {
	document.getElementById("logOutForm").submit();
}
function Example_Clicked(ex_id) {
	$(location).attr('href', "authorsEx?ex_id="+ex_id);
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
<div class="authsInfo">
<c:if test="${not empty error}">
	<h1 style="color: red;">${error}</h1>
</c:if>
	<div class="aboutAuthor">
		<p>${author.details.about}</p>
	</div>
<div class="contactDetails">
	<c:if test="${author.details.email != null && author.details.email != ''}">
		<h3>Email: ${author.details.email}</h3>
	</c:if>
	<c:if test="${author.details.linkedIn != null && author.details.linkedIn != ''}">
		<h3>LinkedIn: ${author.details.linkedIn}</h3>
	</c:if>
	<c:if test="${author.details.other != null && author.details.other != ''}">
		<h3>Others: ${author.details.other}</h3>
	</c:if>
</div>
</div>
<div class="tutorialsTable">
<table>
	<tr>
		<td><h3>TUTORIALS</h3></td>
	</tr>
	<c:forEach var= 't' items="${tutorial}" >
		<tr>
			<td class="title_rows" id="${t.tut_id}" onclick="tutorial_Clicked(this.id)">${t.tut_title}</td>
		</tr>
	</c:forEach>
</table>
</div>
<div class="examplesTable">
<table>
	<tr>
		<td><h3>EXAMPLES</h3></td>
	</tr>
	<c:forEach var= 'e' items="${example}" >
		<tr>
			<td class="title_rows" id="${e.example_id}" onclick="Example_Clicked(this.id)">${e.title}</td>
		</tr>
	</c:forEach>
</table>
</div>
</body>
</html>