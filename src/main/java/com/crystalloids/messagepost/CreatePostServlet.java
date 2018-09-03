package com.crystalloids.messagepost;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreatePostServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

// Process the HTTP POST of the form
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.sendRedirect("/createpost.jsp?");

	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String postTitle = req.getParameter("postTitle");
		resp.sendRedirect("/messagepost.jsp?postTitle=" + postTitle);

	}
	

}
