package ch.ethz.inf.vs.android.gamarc.ownpass;

import java.util.ArrayList;

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
    private ArrayList<Password> pToUp;
	
    public PasswordSender(Server s, String authorization){
    	SERVER_NAME = s.getName();
    	URL = s.getUrl()+"/passwords";
    	authorizationString = authorization;
    }
    
    public void execute(ArrayList<Password> pToUp){
    	this.pToUp =pToUp;
    	for(Password p : pToUp){
    		this.execute(p);

    		//TODO wait -> get responded
//   		 if(responded){
//   	    	 pToUp.remove(p)
//   	     }
    	}
    	
    	//TODO callback
    }
    
	@Override
	protected String doInBackground(Password... params) { 
		
		String result = "";
		 try {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPut httpPut = new HttpPut(URL);
			 
			 httpPut.setHeader("Content-type", "application/json");
			 httpPut.setHeader("Authorization", authorizationString);
			 
			String json = writePasswordObject(params[0]).toString();
			 StringEntity se = new StringEntity(json);
			 httpPut.setEntity(se);
			 
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 result = httpclient.execute(httpPut, responseHandler); 
		 } catch (Exception e) {
			 Log.d("InputStream", e.getLocalizedMessage());
		 }
		 return result;
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

	 @Override
	 protected void onPostExecute(String response) {
	     // handle response;
   		 boolean responded = false;
		 try {
				JSONObject oneObject = new JSONObject(response);
			    String login = oneObject.getString("user_id");
			    if(login.equals(SERVER_NAME))
			    	responded = true;
			} catch (JSONException e) {
				Log.e(PasswordUpdateRequest.class.toString(), "Failed to download file: " + e.getMessage());
			}
		 
		 //TODO callback -> return responded
		 
	}
	 
	 
	 
}
