<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.Date"%>

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
		pageContext.setAttribute("postTitle", postAuthor);
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
		Mpost vpost = Mpost.getMpost(postTitle, postAuthor);
		if (vpost.getTitle().isEmpty()) {
	%>
	<p>Something went wrong.</p>
	<%
		} else {
			pageContext.setAttribute("post_title", vpost.getTitle());
			pageContext.setAttribute("post_author", vpost.getAuthorEmail());
			pageContext.setAttribute("post_content", vpost.getContent());
			pageContext.setAttribute("post_createdDate", vpost.getDate());
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


		
	<form action="/sign" method="post">
		<div>
			<textarea name="content" rows="3" cols="60">${fn:escapeXml(post_content)}</textarea>
		</div>
		<div>
			<input type="submit" value="Submit Post" />
		</div>
		<input type="Hidden" name="postTitle"
			value="${fn:escapeXml(postTitle)}">
		<input type="Hidden" name="postAuthor"
			value="${fn:escapeXml(postAuthor)}">	
		<input type="Hidden" name="isUpdate" value="true">	
	</form> 

	<%
		}
	%>
	
	<div class="container">
		<form action="guestbook.jsp">
			<input type="submit" value="Home" />
		</form>
	</div>

</body>
</html>
