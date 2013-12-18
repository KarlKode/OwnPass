package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;


public class JsonSender extends AsyncTask <String, Void, String>{

	private final Integer USER_ID;
	private Server server;
	private Password password;
	
	public JsonSender(int UserID){
		USER_ID = UserID;
	}
	
	public void setServer(Server s){
		server = s;
		password = null;
	}
	
	public void setPassword(Password p){
		server = null;
		password = p;
	}

	@Override
	protected String doInBackground(String... params) { //Url
		String json = createJson(server, password);
		String url = (String)params[0]; //TODO manipulate?
		return put(url, json);
	}

	private JSONObject writePasswordObject(Password pw){
		JSONObject object = new JSONObject();
		  try {
		    object.put("id", pw.getSiteId());
		    object.put("password", pw.getEncryptedPW());
		    object.put("title", pw.getSiteName());
		    object.put("url", pw.getUrl());
		    object.put("user_id", USER_ID);
		    object.put("username", pw.getEncryptedLogin());
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		return object;
	}
	
	private JSONObject writeUserObject(Server s){
		JSONObject object = new JSONObject();
		  try {
		    object.put("user_id", USER_ID);
		    object.put("password", s.getEncryptedPW());
		    object.put("username", s.getEncryptedLogin());
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		return object;
	}

	
	private String createJson(Server s, Password pw){

		String json = null;
		if(s == null && pw!= null){
			json = writePasswordObject(pw).toString();
		}else if(s != null && pw == null){
			json = writeUserObject(s).toString();
		} else throw new RuntimeException("createJson can not be started");
		
		return json;
	}
	
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }   
	 
	 private static String put(String url, String json){
     InputStream inputStream = null;
     String result = "";
     try {

         HttpClient httpclient = new DefaultHttpClient();
         HttpPut httpPut = new HttpPut(url);
         StringEntity se = new StringEntity(json);
         httpPut.setEntity(se);
 
         httpPut.setHeader("Accept", "application/json");
         httpPut.setHeader("Content-type", "application/json");

         HttpResponse httpResponse = httpclient.execute(httpPut);
         inputStream = httpResponse.getEntity().getContent();

         if(inputStream != null)
             result = convertInputStreamToString(inputStream);
         else
             result = "Did not work!";

     } catch (Exception e) {
         Log.d("InputStream", e.getLocalizedMessage());
     }

     return result;
 }
	 
}
