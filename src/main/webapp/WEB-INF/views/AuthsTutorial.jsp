<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/AuthorsTut.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
<script type="text/javascript">
var previousId = 0;
function  clicked(objRef) {
	if(typeof(newTitleId) == "undefined"){
		var newTitleId = "0";
		//var previousId = "0";
	}
	newTitleId = objRef;
	newTitleId = newTitleId.replace("title-","");
	if(document.getElementById(newTitleId).style.display == 'none'){
		document.getElementById(newTitleId).style.display = 'block';
	}else{
		document.getElementById(newTitleId).style.display = 'none';
	}
	if (typeof(document.getElementById(previousId)) !== "undefined" && document.getElementById(previousId) && previousId !== newTitleId){
			document.getElementById(previousId).style.display = 'none';
	}
	previousId = newTitleId;
}
function logoutRequest() {
	document.getElementById("logOutForm").submit();
}
function showAuthorsProfile() {
	var id = ${tutorial.user_id}
	$(location).attr('href', "authorsProfile?authors_user_id="+id);
}
function deleteTutorial() {
	var retVal = confirm("Do you want to delete this Tutorial ?");
	if( retVal == true ){
		var id = ${tutorial.tut_id}
    	$(location).attr('href', "sub_Admin/deleteTutorial?tut_id="+id);    
    }
    else{
		
    }
}
function close_floating_field() {
	$("#contact_div").hide();
	$("#about_div").hide();
	$(".backDrop").hide();
}
function write_contact_info(){
	$("#contact_div").show();
	$(".backDrop").show();
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
<div class="backDrop">
</div>
<div id="contact_div" style="display: none;" class="fillup_div"> 
<form:form action="ReportAuthor" modelAttribute="Reports" method="post">
	<table>
		<tr>
			<td class="close_button"><h2 onclick="close_floating_field()">x</h2></td>
		</tr>
		<tr>
			<td class="floating_form_header"><h2>please Give a suitable title:</h2></td>
		</tr>
		<tr>
			<td><form:input type="text" placeholder="Title" path="title"/></td>
		</tr>
		<tr>
			<td align="right"><form:errors path="title" cssClass="error"/> </td>
		</tr>
		<tr>
			<td class="floating_form_header"><h2>Please Explain us what you found:</h2></td>
		</tr>
		<tr>
			<td><form:textarea type="textarea" path="message" id="floating_text" /></td>
		</tr>
		<tr>
			<td align="right"><form:errors path="message" cssClass="error"/> </td>
		</tr>
		<tr>
			<td><form:input type="hidden" value="tutorial" path="type"/></td>
		</tr>
		<tr>
			<td><form:input type="hidden" value="${tutorial.user_id}" path="reportersId"/></td>
		</tr>
		<tr>
			<td><form:input type="hidden" value="${user_name_sec.username}" path="repoteesUserName"/></td>
		</tr>
		<tr>
			<td><form:input type="hidden" value="${tutorial.tut_id}" path="sectionId"/></td>
		</tr>
		<tr>
			<td><input type="submit" value="Submit Report"></td>
		</tr>
	</table>
</form:form>
</div>
<c:if test="${empty tutorial}">
	<h1>No data Available, this tutorial was probably deleted</h1>
</c:if>
<c:if test="${not empty tutorial}">
<div class="main_Body">
<div id="tut_title"><h2>${tutorial.tut_title}</h2></div>
<div class="only_table_holder">
	<table >
	<c:if test="${fn:length(tutorial.topics) > 0}">
		<c:forEach var="t" items="${tutorial.topics}"> 
			<tr>
				<td onclick="clicked(this.id)" id="title-${t.topic_id}"><h2 class="title_h">${t.topic_title}</h2></td>
			</tr>
			<tr>
				<td style="display: none;" id='${t.topic_id}'><p class="content_h">${t.topic_content}</p></td>
				<td></td>
			</tr>
		</c:forEach>
	</c:if>
	</table>
	<div><a onclick="showAuthorsProfile()" id="authorsProfile">view Authors Profile</a></div>
</div>
</div>
<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
	<div class="deleteButton"><a onclick="deleteTutorial()">[Delete Tutorial]</a></div>
</sec:authorize>
<div class="deleteButton"><a onclick="write_contact_info()">[Report Tutorial]</a></div>
</c:if>
</body>
</html>