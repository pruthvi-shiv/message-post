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

public class SignGuestbookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

// Process the HTTP POST of the form
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Mpost mpost;
		Mpost checkPost;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); // Find out who the user is.
		String title = req.getParameter("postTitle") == null ? "Default" : req.getParameter("postTitle");
		String content = req.getParameter("content");
		Boolean isUpdate = req.getParameter("isUpdate") == null ? false : true;
		String userEmail = user.getEmail();

		System.out.println("Here ");
		System.out.println(userEmail);
		System.out.println(title);
		System.out.println(isUpdate);

		if (!isUpdate) {
			checkPost = Mpost.getMpost(title, userEmail);

			if (checkPost != null) {
				System.out.println("Cant create the post with same title");
				resp.sendRedirect("/duplicatepost.jsp?postTitle=" + title + "&postAuthor=" + userEmail);
			}
	    
			else {

		mpost = new Mpost(title, content, userEmail);
		mpost.save();

		resp.sendRedirect("/messagepost.jsp?postTitle=" + title + "&postAuthor=" + userEmail);
		
			}

	}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//	    Mpost post = Mpost.getMpost(postTitle) ; 
//	    String postContent = post.getContent();
//	    String postAuthor = post.getAuthorEmail();
//	    Date postDate = post.getDate();

		String postTitle = req.getParameter("postTitle");
		String postAuthor = req.getParameter("postAuthor");
		String method = req.getParameter("method");

		Boolean updatePost = method != null && method.equals("update");

		if (updatePost) {
			resp.sendRedirect("/updatepost.jsp?postTitle=" + postTitle + "&postAuthor=" + postAuthor);
		}

		else {

			resp.sendRedirect("/messagepost.jsp?postTitle=" + postTitle + "&postAuthor=" + postAuthor);

		}

	}
}
