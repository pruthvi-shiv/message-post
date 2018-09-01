<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<%-- //[START imports]--%>
<%@ page import="com.crystalloids.messagepost.Mpost"%>
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

	<form action="/sign" method="post">
		<div>
			<textarea name="content" rows="3" cols="60">Insert the post content</textarea>
		</div>
		<div>
			<textarea name="postTitle" rows="1" cols="25">Provide a title</textarea>
		</div>
		<div>
			<input type="submit" value="Submit Post" />
		</div>
		<input type="Hidden" name="postTitle"
			value="${fn:escapeXml(postTitle)}">
		<input type="Hidden" name="postAuthor"
			value="${fn:escapeXml(postAuthor)}">	
	</form> 

	<%
		}
	%>
	

</body>
</html>
