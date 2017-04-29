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
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/UserList.css">
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../../getyourcode/resources/css/TopNav.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
<script type="text/javascript">
function logoutRequest() {
	document.getElementById("logOutForm").submit();
}
function UserSearchRequest(key){
	$.getJSON("getUsers.do",
			{key:key},
			function (data){
				var appandable = "";
				for(var i in data){
					var appendString = "";
					if(data[i].enabled == true){
						appendString = "[block]";
					}else{
						appendString = "[Un-block]";
					}
						appandable = appandable + "<tr class='removable'><td class = \"SearchDesignTd\"><div><h3 id=\'"+data[i].id+"\' onclick=\"User_Clicked(this.id)\">USER_NAME: "+data[i].username+"</h3><h4>EMAIL_ID: "+data[i].emailId+"</h4></div><h5 onclick = \"blockUser(this.id)\" class = \"block\" id=\'"+data[i].id+"\'>"+appendString+"</h5><h5 onclick = \"deleteUser(this.id)\" class = \"block\" id=\'del-"+data[i].id+"\'> [delete] </h5></td></tr>";
				}
				$(".removable").remove();
				$("#dummy_row_in_table").before(appandable);
			});
}
function User_Clicked(id) {
	$(location).attr('href', "../authorsProfile?authors_user_id="+id);
}
function choiceChanged() {
	$key = $('#search_key').val();
	if($key == ""){
		$key = " ";
	}
	UserSearchRequest($key);
}
function blockUser(id) {
	$(location).attr('href', "toggleStatus?id="+id);
}
function  deleteUser(id) {
	var retVal = confirm("Do you want to delete this User ?");
    if( retVal == true ){
    	$(location).attr('href', "deleteUser?user_id="+id.replace("del-",""));
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
<div class="searchArea">
<div class="searchBar">
<input type="text" class="searchField" id="search_key"/>
<input type="submit" value="search" onclick="choiceChanged()"/>
</div>
</div>
<div>
<table id="searchResultsTable">
<tr id="dummy_row_in_table"></tr>
</table>
</div>

<script type="text/javascript">
UserSearchRequest("");
</script>
</body>
</html>