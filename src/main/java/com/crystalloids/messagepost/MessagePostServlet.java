package com.crystalloids.messagepost;

import com.crystalloids.messagepost.RestUtils;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.ParseException;

public class MessagePostServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// Process the HTTP POST of the form
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); // Find out who the user is.
		String title = req.getParameter("postTitle") == null ? "Default" : req.getParameter("postTitle");
		String content = req.getParameter("content");
		Boolean isUpdate = req.getParameter("isUpdate") == null ? false : true;
		String userEmail = user.getEmail();
		JSONObject j = new JSONObject();
		

		if (!isUpdate) {
			
			try {
				j = RestUtils.apiGet(userEmail, title);
				
			} catch (ParseException e) {
//				e.printStackTrace();
			} catch (JSONException e) {
//				e.printStackTrace();
			}

			if (j.length() > 0) {
				resp.sendRedirect("/duplicatepost.jsp?postTitle=" + title + "&postAuthor=" + userEmail);
			}
			
			else {
				try {
					RestUtils.apiPost(userEmail, content, title);
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

				resp.sendRedirect("/messagepost.jsp?postTitle=" + title + "&postAuthor=" + userEmail);
			}

		}

		else {

			try {
				RestUtils.apiPost(userEmail, content, title);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			resp.sendRedirect("/messagepost.jsp?postTitle=" + title + "&postAuthor=" + userEmail);
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
