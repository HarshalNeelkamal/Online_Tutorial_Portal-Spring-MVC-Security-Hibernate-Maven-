<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
<script type="text/javascript">
function loadingOver() {
	document.getElementById("signup_login_form").submit();
}
</script>
</head>
<body>
<form action="performAuthenticatation" method="post" id="signup_login_form" onload="javascript:loadingOver()">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<table>
		<tr>
			<td></td><td><input type="hidden" name="j_username" value="${j_username}"/></td>
		</tr>
		<tr>
			<td></td><td><input type="hidden" name="j_password" value="${j_password}"/></td>
		</tr>
		<tr>
			<td></td><td><input type="hidden" value="login"/></td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	loadingOver();
</script>
</body>
</html>
