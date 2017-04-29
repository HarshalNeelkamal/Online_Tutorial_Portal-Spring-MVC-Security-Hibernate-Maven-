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
	function deleteExample(id) {
		id = id.replace("deletion-","");
		var retVal = confirm("Do you want to delete this example ?");
	    if( retVal == true ){
	    	$(location).attr('href', "deleteExample?id="+id);
	    }
	    else{
			
	    }
	}
</script>
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/MyExTu.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">
</head>
<body>
<sec:authentication var="user_name_sec" property="principal" />
<div id="dialog-confirm" title="Delete this Example?">
  <p id="dialog_message" style="display: none"><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>These items will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
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
<!-- <h1>my Examples</h1>  -->
<div id="example_table_holder">
	<table >
	<c:if test="${fn:length(example_list) > 0}">
		<c:forEach var="e" items="${example_list}"> 
			<tr>
				<td onclick="clicked(this.id)" id="title-${e.example_id}"><h2 class="title_h">${e.title}</h2></td>
				<td><a onclick="deleteExample(this.id)" id = "deletion-${e.example_id}">[ Delete ]</a><a href="editExample?id=${e.example_id}">[ Edit ]</a><td>
			</tr>
			<tr>
				<td style="display: none;" id='${e.example_id}'><p class="content_h">${e.content}</p></td>
				<td></td>
			</tr>
		</c:forEach>
	</c:if>
	</table>
</div>
<button onclick="window.location.href='exampleForm'" class="floating_button"><h1>+</h1></button>
</body>
</html>