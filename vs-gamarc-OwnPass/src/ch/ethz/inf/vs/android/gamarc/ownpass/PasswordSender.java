package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class PasswordSender extends AsyncTask<Password, Void, String>{
	
    private static String URL;
    private static String authorizationString;
    private static String SERVER_NAME;
	
    public PasswordSender(Server s, String authorization){
    	SERVER_NAME = s.getName();
    	URL = s.getUrl()+"/passwords";
    	authorizationString = authorization;
    }

	@Override
	protected String doInBackground(Password... params) { 
		String json = writePasswordObject(params[0]).toString();
		return put(URL, json, authorizationString);
	}

	private JSONObject writePasswordObject(Password pw){
		JSONObject object = new JSONObject();
		  try {
		    object.put("id", pw.getSiteId());
		    object.put("password", pw.getEncryptedPW());
		    object.put("title", pw.getSiteName());
		    object.put("url", pw.getUrl());
		    object.put("user_id", SERVER_NAME);
		    object.put("username", pw.getEncryptedLogin());
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		return object;
	}

	 private static String put(String url, String json, String authorization){
		 String result = "";
		 try {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPut httpPut = new HttpPut(url);
			 StringEntity se = new StringEntity(json);
			 httpPut.setEntity(se);

			 httpPut.setHeader("Content-type", "application/json");
			 httpPut.setHeader("Authorization", authorization);
			 
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 result = httpclient.execute(httpPut, responseHandler); 
		 } catch (Exception e) {
			 Log.d("InputStream", e.getLocalizedMessage());
		 }
		 return result;
	 }
	
	 @Override
	 protected void onPostExecute(String response) {
	     super.onPostExecute(response);
	     //TODO  handle response;
	}
}
