package com.crystalloids.messagepost;


import java.util.ArrayList;
import java.io.IOException;


import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class RestUtils {

	private static final String base_url = "https://crystalloids-assignment-pruth.appspot.com/_ah/api/echo/v1/";

	public static void apiPost(String author, String content, String title)
			throws ClientProtocolException, IOException, ParseException, JSONException

	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ArrayList<NameValuePair> postParameters;
		postParameters = new ArrayList<NameValuePair>();
		String url = base_url + "createPost";
		postParameters.add(new BasicNameValuePair("author", author));
		postParameters.add(new BasicNameValuePair("content", content));
		postParameters.add(new BasicNameValuePair("title", title));
		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

		CloseableHttpResponse response1 = httpclient.execute(httpPost);
		try {
//			System.out.println(response1.getStatusLine());
		} finally {
			response1.close();
		}

	}
	
	public static JSONObject apiGet(String author, String title)
			throws ClientProtocolException, IOException, ParseException, JSONException

	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ArrayList<NameValuePair> postParameters;
		postParameters = new ArrayList<NameValuePair>();
		String url = base_url + "getPost";
		postParameters.add(new BasicNameValuePair("author", author));
		postParameters.add(new BasicNameValuePair("title", title));
		HttpGet httpGet = new HttpGet(url +"?"+URLEncodedUtils.format(postParameters, "utf-8"));
		JSONObject result = new JSONObject();

		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		try {
			
			String status = response1.getStatusLine().toString(); 
			
		    HttpEntity entity1 = response1.getEntity();
		    
		    System.out.println(status);
		    
	        if (status.equals( "HTTP/1.1 200 OK")) {
	        	result = new JSONObject(EntityUtils.toString(entity1));      	
		} EntityUtils.consume(entity1);
		}
	        
	        finally {
			//response1.close();
	        //do nothing
		}
		
		return (result);

	}
	
	public static JSONObject apiGetall()
			throws ClientProtocolException, IOException, ParseException, JSONException

	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = base_url + "getPosts";
		HttpGet httpGet = new HttpGet(url);
		JSONObject result = new JSONObject();

		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		try {
			
			String status = response1.getStatusLine().toString(); 
			
		    HttpEntity entity1 = response1.getEntity();
		    
	        if (status.equals( "HTTP/1.1 200 OK")) {
	        	result = new JSONObject(EntityUtils.toString(entity1));    
		} EntityUtils.consume(entity1);
		}
	        
	        finally {
			//response1.close();
	        //do nothing
		}
		
		return (result);

	}

}
