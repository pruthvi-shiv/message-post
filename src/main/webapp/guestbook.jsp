<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<%-- //[START imports]--%>
<%@ page import="com.crystalloids.messagepost.Mpost"%>
<%-- //[END imports]--%>

 <%@ page import="java.util.List"%>
<%--<%@ page import="org.json.JSONObject" %>
<%@ page import="org.apache.http.HttpEntity"%>
<%@ page import="org.apache.http.HttpResponse"%>
<%@ page import="org.apache.http.client.ClientProtocolException"%>
<%@ page import="org.apache.http.client.ResponseHandler"%>
<%@ page import="org.apache.http.client.methods.HttpGet"%>
<%@ page import="org.apache.http.impl.client.CloseableHttpClient"%>
<%@ page import="org.apache.http.client.methods.CloseableHttpResponse"%>
<%@ page import="org.apache.http.impl.client.HttpClients"%>
<%@ page import="org.apache.http.util.EntityUtils"%> --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- Only place where all the posts are fetched instead of using the REST API --%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body>

	<%-- from there --%>

	<%
		String postTitle = request.getParameter("postTitle");
		if (postTitle == null) {
			postTitle = "Default";
		}
		pageContext.setAttribute("postTitle", postTitle);
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

	<%
			Mpost post = new Mpost();
			List<Mpost> posts = post.getMposts();

			if (posts.isEmpty()) {
	%>
	<p>No posts by users yet.</p>
	<%
		} else {
	%>
	<p>Post by users</p>
	<%
		for (Mpost vpost : posts) {
					pageContext.setAttribute("postTitle", vpost.getTitle());
					String author;
					author = vpost.getAuthorEmail();
					pageContext.setAttribute("postAuthor", author);
	%>
	<a href="postPage?postTitle=${fn:escapeXml(postTitle)}&postAuthor=${fn:escapeXml(postAuthor)}">
	 ${fn:escapeXml(postTitle)}</a>
	 by:
	 ${fn:escapeXml(postAuthor)}
	<p></p>

	<%
		}
			}
	%>

	<form action="/createPost" method="post">
		<div>
			<input type="submit" value="Create New Post" />
		</div>
 		<input type="Hidden" name="createPost"
			value=true> 
	</form> 

	<%

		}
	%>

</body>
</html>
