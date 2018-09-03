<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<%-- //[START imports]--%>
<%--@ page import="com.crystalloids.messagepost.Mpost"--%>
<%-- //[END imports]--%>

<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body>

	<%-- from there --%>

	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
	%>

	<p>
		Hello! <a
			href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
			in</a> to create and view posts.
	</p>

	<%
		} else {
			pageContext.setAttribute("postAuthor", user);
	%>

	<p>
		Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.)
	</p>

	<script type="text/javascript">
		function validateForm() {
			var a = document.forms["Form"]["postTitle"].value;
			var b = document.forms["Form"]["content"].value;
			if (a == null || a == "", b == null || b == "") {
				alert("Please Fill All Required Field");
				return false;
			}
		}
	</script>
	<form name = "Form" action="/mainPost" method="post" onsubmit="return validateForm()">
		<div>
			<label for="title">Title</label> <input type="text" id="fname"
				name="postTitle" placeholder="Post Title.."> <label
				for="content">Content</label> <input style="height: 150px;"
				type="text" id="lname" name="content" placeholder="Post Content..">
			<input type="submit" value="Submit Post" />
		</div>
		<input type="Hidden" name="postTitle"
			value="${fn:escapeXml(postTitle)}"> <input type="Hidden"
			name="postAuthor" value="${fn:escapeXml(postAuthor)}">
	</form>

	<%
		}
	%>


</body>
</html>
