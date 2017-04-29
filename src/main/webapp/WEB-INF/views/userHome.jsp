<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/UserHome.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
<script type="text/javascript">
function logoutRequest() {
	document.getElementById("logOutForm").submit();
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
function UserSearchRequest(key){
	$.getJSON("searchResults.do",
			{key:key},
			function (data){
				var appandable = "";
				for(var i in data){
					appandable = appandable + "<tr class='removable'><td id=\'"+data[i].tut_id+"\' onclick=\"tutorial_Clicked(this.id)\" class = \"SearchDesignTd\"><h2>"+data[i].tut_title+"</h2></td></tr>";
				}
				$(".removable").remove();
				$("#dummy_row_in_table").before(appandable);
			});
}
function ExampleSearchRequest(key){
	$.getJSON("searchExampleResults.do",
			{key:key},
			function (data){
				var appandable = "";
				for(var i in data){
					appandable = appandable + "<tr class='removable'><td id=\'"+data[i].example_id+"\' onclick=\"Example_Clicked(this.id)\" class = \"SearchDesignTd\"><h2>"+data[i].title+"</h2></td></tr>";
				}
				$(".removable").remove();
				$("#dummy_row_in_table").before(appandable);
			});
}
function tutorial_Clicked(tut_id) {
	$(location).attr('href', "authorsTut?tut_id="+tut_id);
}
function Example_Clicked(ex_id) {
	$(location).attr('href', "authorsEx?ex_id="+ex_id);
}
function choiceChanged() {
	$selection = $('#searchDropDown :selected').text();
	$key = $('#search_key').val();
	if($key == ""){
		$key = " ";
	}
	if($selection == 'Example'){
		ExampleSearchRequest($key);
	}else if($selection == 'Tutorial'){
		UserSearchRequest($key);
	}
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
<div class="mainBody">
<div class="personalArea">
<table>
	<tr>
		<th><h3>About Me</h3></th>
	</tr>
	<tr>
	<c:if test="${not empty aboutUser}">
		<td><p style="word-break: break-word; white-space: pre-wrap;">${aboutUser}</p></td>
	</c:if>
	<c:if test="${empty aboutUser}">
		<td class="about_td">tell us something about you like you hobbies, your technical areas of interests, your expertise, tour coding experience and your professional experience so that surfing users can know if they want to follow your material.<br/></td>
	</c:if>
	</tr>
	<tr>
		<th><h3>Contact info</h3></th>
	</tr>
	<c:if test="${not empty userEmail}">
		<tr>
			<td><p style="word-break: break-word; white-space: pre-wrap;">${userEmail}</p></td>
		</tr>
	</c:if>
	
	<c:if test="${not empty UserLinkedIn}">
		<tr>
			<td><p style="word-break: break-word; white-space: pre-wrap;">${UserLinkedIn}</p></td>
		</tr>
	</c:if>
	<c:if test="${not empty UserOther}">
		<tr>
			<td><p style="word-break: break-word; white-space: pre-wrap;">${UserOther}</p></td>
		</tr>
	</c:if>
	<c:if test="${empty userEmail && empty UserLinkedIn && empty UserOther }">
		<tr>
			<td class="contact_info">you might wanna drop your contact details like your LinkedIn profile URL so that people who wish to approach you can connect.<br/></td>
		</tr>
	</c:if>
	<tr>
		<td><p><a onclick="write_contact_info()">Edit</a></p></td>
	</tr>
</table>
</div>
<div class="searchArea">
<div class="searchBar">
<select id="searchDropDown" onchange="choiceChanged()">
  <option value="Tutorial">Tutorial</option>
  <option value="Example">Example</option>
</select>
<input type="text" class="searchField" id="search_key"/>
<input type="submit" value="search" onclick="choiceChanged()"/>
</div>
<table id="searchResultsTable">
<tr id="dummy_row_in_table"></tr>
</table>
</div>
<div class="backDrop">
</div>
<div id="contact_div" style="display: none;" class="fillup_div"> 
<form:form action="saveUserDetails" modelAttribute="UserDetails" method="post">
	<table>
		<tr>
			<td class="close_button"><h2 onclick="close_floating_field()">x</h2></td>
		</tr>
		<tr>
			<td class="floating_form_header"><h2>Write Something About Your Self:</h2></td>
		</tr>
		<tr>
			<td><form:textarea type="textarea" path="about" id="floating_text" /></td>
		</tr>
		<tr>
			<td align="right"><form:errors path="about" cssClass="error"/> </td>
		</tr>
		<tr>
			<td class="floating_form_header"><h2>please Drop your contact Info:</h2></td>
		</tr>
		<tr>
			<td><form:input type="text" placeholder="Email Id" value="${userEmail}" path="email"/></td>
		</tr>
		<tr>
			<td align="right"><form:errors path="email" cssClass="error"/> </td>
		</tr>
		<tr>
			<td><form:input type="text" placeholder="LinkedIn Profile Url" value="${UserLinkedIn}" path="linkedIn"/></td>
		</tr>
		<tr>
			<td align="right"><form:errors path="linkedIn" cssClass="error"/> </td>
		</tr>
		<tr>
			<td><form:input type="text" placeholder="Others" value="${UserOther}" path="other"/></td>
		</tr>
		<tr>
			<td align="right"><form:errors path="other" cssClass="error"/> </td>
		</tr>
		<tr>
			<td><input type="submit" value="Apply"></td>
		</tr>
	</table>
</form:form>
</div>
</div>
<script type="text/javascript">
choiceChanged();
</script>
</body>
</html>