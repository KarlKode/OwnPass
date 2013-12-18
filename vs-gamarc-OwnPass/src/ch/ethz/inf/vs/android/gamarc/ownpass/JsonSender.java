package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class JsonSender extends AsyncTask <String, Void, String>{

	private final Integer USER_ID;
	
	public JsonSender(int UserID){
		USER_ID = UserID;
		
		
		
	}
	

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	

	private JSONObject writePasswordObject(Password pw){
		JSONObject object = new JSONObject();
		  try {
		    object.put("id", pw.getSiteId());
		    object.put("password", pw.getEncryptedPW());
		    //TODO object.put("title", );
		    object.put("url", pw.getUrl());
		    object.put("user_id", USER_ID);
		    object.put("username", pw.getEncryptedLogin());
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		return object;
	}
	
	private JSONObject writeUserObject(String username, String pw){
		JSONObject object = new JSONObject();
		  try {
		    object.put("user_id", USER_ID);
		    object.put("password", pw);
		    object.put("username", username);
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		return object;
	}
	
	 
}
