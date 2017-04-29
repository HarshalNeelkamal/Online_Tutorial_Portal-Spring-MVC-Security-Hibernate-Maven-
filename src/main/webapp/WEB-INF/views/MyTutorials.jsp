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
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/MyExTu.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/FatScroll.css">
<link rel="stylesheet" type="text/css" href="../getyourcode/resources/css/TopNav.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"/></script>
<script type="text/javascript">
var previousId = 0;
var presentTutId;
if( ${present_tutorial_Id} !== -1){
	presentTutId = ${present_tutorial_Id};
}
var previousTutorialId = "td-0";
	function  clicked(objRef) {
		if(typeof(newTitleId) == "undefined"){
			var newTitleId = "0";
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
	function loadComplete(){	
		if(${present_tutorial_Id} !== -1){
			$("#last_row_in_topic_table").show();
			$('#tutorial_delete_tr').show();
			changeSelectedTutorial(presentTutId);
			<c:forEach var="tuto" items="${tutorial_list}" end="0">
		    	document.getElementById("add_topic").href = 'topicForm?tut_id='+${tuto.tut_id};
			</c:forEach>
		if(presentTutId !== null){
			$.getJSON("topicLoad.do",
					{tut_id : presentTutId},
					function (data){
						displayTopics(data);
					});
		}
		}else{
			$("#last_row_in_topic_table").hide();
			$('#tutorial_delete_tr').hide();
		}
	}
	function deleteTopic(top_id,tut_id){
		
		var retVal = confirm("Do you want to delete this Topic ?");
	    if( retVal == true ){
	    	$.getJSON("deleteTopic.do",
					{tutId : tut_id , top_id : top_id },
					function (data){
						displayTopics(data);
				});
	    }
	    else{
			
	    }
		
	}
	function newTutorialClicked(tutId) {
		presentTutId = tutId;
		presentTutId = presentTutId.replace("tutorial-","");
		changeSelectedTutorial(presentTutId);
		<c:forEach var="tuto" items="${tutorial_list}" end="0">
    		document.getElementById("add_topic").href = 'topicForm?tut_id='+presentTutId;
    		$('#last_row_in_topic_table').show();
    		$('#tutorial_delete_tr').show();
		</c:forEach>
	
		if(presentTutId !== null){
			$.getJSON("topicLoad.do",
						{tut_id : presentTutId},
						function (data){
							displayTopics(data);
						});
			}
	}
	function displayTopics(data){
		var tableString = "";
		for (var i in data) {
			tableString += "<tr class=\"removable\"><td onclick=\"clicked(this.id)\" id=\"title-"+data[i].topic_id+"\"><h2 class=\"title_h\">"+data[i].topic_title+"</h2></td><td><a onclick = \"deleteTopic("+data[i].topic_id+","+presentTutId+")\">[ Delete ]</a><a href = \"editTopic?topicId="+data[i].topic_id+"&tutorialId="+presentTutId+"\">[ Edit ]</a></td></tr><tr class=\"removable\"><td style=\"display: none;\" id=\'"+data[i].topic_id+"\'><p class=\"content_h\">"+data[i].topic_content+"</p></td><td></td></tr>";
		}
	$(".removable").remove();
	$("#last_row_in_topic_table").before(tableString);
	}
	function changeSelectedTutorial(tutId) {
		var clickedId = "td-"+tutId;
		if (typeof(document.getElementById(previousTutorialId)) !== "undefined" && document.getElementById(previousTutorialId) && previousTutorialId !== clickedId){
				document.getElementById(previousTutorialId).style.backgroundColor  = 'white';
				document.getElementById("tutorial-"+previousTutorialId.replace("td-","")).style.color  = 'black';
		}
		document.getElementById(clickedId).style.backgroundColor  = 'black';
		document.getElementById("tutorial-"+tutId).style.color  = 'white';
		previousTutorialId = clickedId;
	}
	function deleteTutorialClicked() {
		
		var retVal = confirm("Do you want to delete this Tutorial ?");
		if( retVal == true ){
			$('#last_row_in_topic_table').hide();
			$('#tutorial_delete_tr').hide();
			$.getJSON("deleteTutorial.do",
					{tutId : presentTutId},
					function (data){
						var idToRemove = "td-"+presentTutId;
						$("#"+idToRemove).remove();
						$(".removable").remove();
						var forComp;
						<c:forEach var="tuto" items="${tutorial_list}" end="0">
							forComp = ${tuto.tut_id}; 
						</c:forEach>
						if(forComp !== presentTutId && presentTutId !== null){
							forComp = "tutorial-"+forComp;
							newTutorialClicked(forComp);
						}
				});
	    }
	    else{
			
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
<div class="topic_area">
<table >
	<c:forEach var="et" items="${tutorial_list}">
	<tr>
	<c:if test="${not empty et.tut_title}">
		<td id="td-${et.tut_id}"><a id ="tutorial-${et.tut_id}" onclick="newTutorialClicked(this.id)">${et.tut_title}</a></td>
	</c:if>
	</tr>
	</c:forEach>
	<tr>
		<td id="add_td"><button onclick="window.location.href='tutorialForm'" class="floating_button_tut"><h1>+</h1></button></td>
	</tr>
</table>
</div>
<div id="topic_table_holder">
	<table id="topic_table" class="removableParent">
	<tr id="last_row_in_topic_table" class="unremovable">
		<td class="unremovable" colspan="2"><a id="add_topic" class="unremovable">Add Topic +</a></td>
	</tr>
	<tr id="tutorial_delete_tr" class="unremovable">
		<td class="unremovable" colspan="2"><a id="delete_tutorial" onclick="deleteTutorialClicked()" class="unremovable" style="font-size: 20px; color: red; float: left; text-align: left; margin-left: 10px;">Delete Tutorial</a></td>
	</tr>  
	</table>
</div>
<script type="text/javascript">
loadComplete();
</script>
</body>
</html>