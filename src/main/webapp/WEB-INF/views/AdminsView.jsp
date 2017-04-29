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

<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/AdminView.css">
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/TopNav.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
<script type="text/javascript">
function logoutRequest() {
	document.getElementById("logOutForm").submit();
}
function createSubAdmin() {
	
}
function subAdminsProfile(sa_id){
	$(location).attr('href', "../authorsProfile?authors_user_id="+sa_id);
}
function sub_admin_deleteReq(id) {
	var retVal = confirm("Do you want to delete this sub-Admin ?");
    if( retVal == true ){
    	$(location).attr('href', "deleteSubAdm?sub_adm_id="+id.replace("del-",""));
    }
    else{
		
    }
}
</script>
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
			<a href="subAdmins">Sub-Admins</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
			<a href="../sub_Admin/viewInbox">Inbox</a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_SUB_ADMIN')">
			<a href="../sub_Admin/viewUsers">UsersList</a>
		</sec:authorize>
	</div>
</div>
<div class="only_table_holder">
	<table >
	<c:if test="${fn:length(subAdminsList) > 0}">
		<c:forEach var="sa" items="${subAdminsList}"> 
			<c:if test="${sa.username != user_name_sec.username}">
			<tr>
				<td onclick="subAdminsProfile(this.id)" id="${sa.id}"><h2 class="title_h">${sa.username}</h2></td>
				<td class="row_buttons"><a onclick="sub_admin_deleteReq(this.id)" id="del-${sa.id}">[ Delete ]</a></td>
			</tr>
			</c:if>
		</c:forEach>
	</c:if>
	</table>
	<div><a href="subAdminForm" id="create">Create new Sub Admin</a></div>
</div>
</body>
</html>