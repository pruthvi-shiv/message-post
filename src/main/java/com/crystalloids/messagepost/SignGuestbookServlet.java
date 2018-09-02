package com.crystalloids.messagepost;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.ArrayList;
import java.util.Date;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.crystalloids.messagepost.Mpost;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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

		if (!isUpdate) {
			checkPost = Mpost.getMpost(title, userEmail);

			if (checkPost != null) {
				System.out.println("Cant create the post with same title");
				resp.sendRedirect("/duplicatepost.jsp?postTitle=" + title + "&postAuthor=" + userEmail);
			}

		}

		else {

			try {
				apiPost(userEmail, content, title);
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

	private void apiPost(String author, String content, String title)
			throws ClientProtocolException, IOException, ParseException, JSONException

	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ArrayList<NameValuePair> postParameters;
		postParameters = new ArrayList<NameValuePair>();
		String url = "https://crystalloids-assignment-pruth.appspot.com/_ah/api/echo/v1/createPost";
		postParameters.add(new BasicNameValuePair("author", author));
		postParameters.add(new BasicNameValuePair("content", content));
		postParameters.add(new BasicNameValuePair("title", title));
		HttpPost httpPost = new HttpPost(url);

		System.out.println(url);

		httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

		CloseableHttpResponse response1 = httpclient.execute(httpPost);
		try {
			System.out.println(response1.getStatusLine());
		} finally {
			response1.close();
		}

	}

}
