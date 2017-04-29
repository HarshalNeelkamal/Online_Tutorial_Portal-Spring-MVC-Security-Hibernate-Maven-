<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
		<a href="customLoginForm">SignIn</a>
	</div>
</div>
<h1 id="app_name">Get Your Code...!!</h1>
<form:form action="addUser" modelAttribute="appUser" method="post" id="form_css_ref" onsubmit="fieldBlur()">
<table>
	<tr>
		<td align="left" width="20%"></td>
		<td align="left" width="40%"><form:input path="id" type="hidden" value="0" /></td>
		
	</tr>
	<tr>
		<td align="left" width="20%"></td>
		<td align="left" width="40%"><form:input path="username" class="input_block" placeholder="username"/></td>
		<td align="left"><form:errors path="username" cssClass="signuperror"/> </td>
	</tr>
	<tr>
		<td align="left" width="20%"></td>
		<td align="left" width="40%"><form:input onkeyup="fieldBlur()" type="password" path="password" id="pass" class="input_block" placeholder="password"/></td>
		<td align="left"><form:errors path="password" cssClass="signuperror"/> </td>
	</tr>
	<tr>
		<td align="left" width="20%"></td>
		<td align="left" width="40%"><input onkeyup="fieldBlur()" type="password" id="re_pass" class="input_block" placeholder="re-enter password" /></td>
		<td align="left"><h4 id="pass_match_error"></h4></td>
	</tr>
	<tr>
		<td align="left" width="20%"></td>
		<td align="left" width="40%"><form:input path="emailId" class="input_block" placeholder="Email Id"/></td>
		<td align="left"><form:errors path="emailId" cssClass="signuperror"/> </td>
	</tr>
	<tr>
        <td></td>
        <td align="center"><input id="submit_button" type="submit" value="submit" disabled="disabled"/></td>
    </tr>
</table>
</form:form>
<c:if test="${message != null}">
	<h3 class="signuperror">${message}</h3>
</c:if>
<script type="text/javascript">
var submit = document.getElementById("submit_button");
var pass_match_error = document.getElementById("pass_match_error");
pass_match_error.style.color = 'red';
pass_match_error.innerHTML = '';
submit.style.opacity = 0.5;
function fieldBlur() {
	var pass = document.getElementById("pass");
	var re_pass = document.getElementById("re_pass");
	if(pass.value == re_pass.value && pass.value !== ''){
		submit.disabled = false;
		submit.style.opacity = '1.0';
		pass_match_error.innerHTML = '';
	}
	else{
		submit.style.opacity = '0.5';
		submit.disabled = true;
		pass_match_error.innerHTML = '...passwords dont match';
	}
}
function whenFocus(){
	submit.style.opacity = '0.5';
	submit.disabled = true;
	pass_match_error.innerHTML = '...passwords dont match';
}
</script>
</body>
</html>