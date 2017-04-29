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
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/Reports.css">
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/TopNav.css">
<script type="text/javascript">
function logoutRequest() {
	document.getElementById("logOutForm").submit();
}
function sectionClicked(section_type) {
	var list = section_type.split("-");
	var section = list[0];
	var type = list[1];
	if(type == "example"){
		$(location).attr('href', "../authorsEx?ex_id="+section);
	}else{
		$(location).attr('href', "../authorsTut?tut_id="+section);
	}
}
function markSolved(id) {
	var retVal = confirm("Have you took the necessary steps to resolve the case; Marking case as Resolved will cleare the trace of this report.\n Are you sure you wanna Continue?");
    if( retVal == true ){
    	$(location).attr('href', "resolveReport?report_id="+id);
    }
    else{
		
    }
}
</script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
</head>
<body>

<sec:authentication var="user_name_sec" property="principal" />
<div id="NavigationItem">
	<form action="../userLoggOut" id="logOutForm" method="post"> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	<div class="NameHolder">
		<h3 class="userNameTag">Hello ${user_name_sec.username}</h3>
		<div class="dropDown">
			<a href="javascript:logoutRequest()">Logout</a>
		</div>
	</div>
	<div class="leftButtonHolder">
		<a href="../myTutorials">My tutorial</a>
		<a href="../myExamples">My Example</a>
		<a href="../userHome">Home</a>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a href="../admin/subAdmins">Sub-Admins</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
			<a href="viewInbox">Inbox</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
			<a href="viewUsers">UsersList</a>
		</sec:authorize>
	</div>
</div>

<div class="main_div">
<div class="table_holder">
<table>
<tr>
	<td id="Table_title"><h2>REPORTED CASES:</h2></td>
</tr>
<c:forEach var="report" items="${reports}">
	<tr>
		<td class="report_title_td"><h3 id="block_info"> &#x21F4 AGAINST USER_ID: <a href="../authorsProfile?authors_user_id=${report.reportersId}">${report.reportersId}</a> | TITLE: ${report.title}</h3><h4><a onclick="sectionClicked(this.id)" id="${report.sectionId}-${report.type}" >(${report.type})</a></h4></td>
	</tr>
	<tr>
		<td class="report_message_td"><p>${report.message}</p><div class="buttonHolder"><h3 onclick="markSolved(this.id)" id="${report.report_id}">Mark as Solved</h3></div></td>
	</tr>
</c:forEach>
</table>
</div>
</div>
</body>
</html>