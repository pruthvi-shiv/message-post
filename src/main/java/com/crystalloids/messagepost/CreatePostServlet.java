package com.crystalloids.messagepost;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.Date;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.crystalloids.messagepost.Mpost;

public class CreatePostServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

// Process the HTTP POST of the form
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Mpost mpost;
		resp.sendRedirect("/createpost.jsp?");

	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//	    Mpost post;

		String postTitle = req.getParameter("postTitle");
		String authorEmail = req.getParameter("authorEmail");

		resp.sendRedirect("/messagepost.jsp?postTitle=" + postTitle);

		Mpost post = Mpost.getMpost(postTitle, authorEmail);
		String postContent = post.getContent();
		String postAuthor = post.getAuthorEmail();
		Date postDate = post.getDate();

	}
}
