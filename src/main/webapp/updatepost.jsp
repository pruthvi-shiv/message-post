<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.Date"%>

<%-- //[START imports]--%>
<%--@ page import="com.crystalloids.messagepost.Mpost"--%>
<%@ page import="com.crystalloids.messagepost.RestUtils"%>
<%@ page import="org.json.JSONException"%>
<%@ page import="org.json.JSONObject"%>
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
		pageContext.setAttribute("user", user);
	%>

	<p>
		Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.)
	</p>

	<%
		JSONObject jPost = new JSONObject();
		jPost = RestUtils.apiGet(postAuthor, postTitle);
		if (jPost.length() == 0) {
	%>
	<p>Something went wrong.</p>
	<%
		} else {
			pageContext.setAttribute("post_title", jPost.get("title"));
			pageContext.setAttribute("post_author", jPost.get("authorEmail"));
			pageContext.setAttribute("post_content", jPost.get("content"));
			pageContext.setAttribute("post_createdDate", jPost.get("date"));
	%>
	<b>${fn:escapeXml(post_title)}</b> by: ${fn:escapeXml(post_author)}
	<p></p>
	<p>${fn:escapeXml(post_content)}
	<p>
	<p>Created on : ${fn:escapeXml(post_createdDate)}
	<p>

		<%
			}
		%>

		<%
			Boolean isUserAuthor = pageContext.getAttribute("user").toString()
					.equals(pageContext.getAttribute("post_author").toString());

			if (isUserAuthor) {
		%>
	
	<form action="/mainPost" method="post">
		<div>
			<textarea name="content" rows="3" cols="60">${fn:escapeXml(post_content)}</textarea>
		</div>
		<div>
			<input type="submit" value="Submit Post" />
		</div>
		<input type="Hidden" name="postTitle" value="${fn:escapeXml(postTitle)}" /> 
		<input type="Hidden" name="postAuthor" value="${fn:escapeXml(postAuthor)}" /> 
		<input type="Hidden" name="isUpdate" value="true" />
	</form>

	<%
		}
	%>

	<div class="container">
		<form action="welcome.jsp">
			<input type="submit" value="Home" />
		</form>
	</div>

</body>
</html>
