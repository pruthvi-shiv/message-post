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
		String postTitle = request.getParameter("postTitle");
		pageContext.setAttribute("postTitle", postTitle);
		String postAuthor = request.getParameter("postAuthor");
		pageContext.setAttribute("postAuthor", postAuthor);
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
			pageContext.setAttribute("user", user);
	%>

	<p>
		Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.)
	</p>

    <h1> Post already exists for your name, click the below link to update the post</h1>
	
	<a href="postPage?postTitle=${fn:escapeXml(postTitle)}&postAuthor=${fn:escapeXml(postAuthor)}">
	 ${fn:escapeXml(postTitle)}</a>
	 by:
	 ${fn:escapeXml(postAuthor)}
	<p></p>

	<%
		}
	%>

</body>
</html>
