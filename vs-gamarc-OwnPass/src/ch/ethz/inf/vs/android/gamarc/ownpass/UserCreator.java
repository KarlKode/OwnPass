package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Base64;
import org.apache.commons.lang3.RandomStringUtils;

public class UserCreator extends AsyncTask<String, Void, String>{
	private final String BASE_AUTHORIZATION = "username=%s&password=%s";
	private Server serv;
	
	@Override
	protected String doInBackground(String... params) { //0)Url not Base_URL; 1)username 2)login_name 3)password
		String url = params[0]+"/users"; 
		String json = createUserObject(params[1]).toString();
		String authorization = createAuthorization(params[2], params[3]);	
		return put(url, json, authorization);
	}
	
	
	
	private String createAuthorization(String string, String string2) {
		
		
		return null;
	}



	private JSONObject createUserObject(String userid){
		String login = RandomStringUtils.randomAlphanumeric(10);
		String pw = RandomStringUtils.randomAlphanumeric(20);
		login = Base64.encodeToString(login.getBytes(), Base64.DEFAULT);
		pw = Base64.encodeToString(login.getBytes(), Base64.DEFAULT);
		
		JSONObject object = new JSONObject();
		  try {
		    object.put("user_id", userid);
		    object.put("password", pw);
		    object.put("username", login);
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		return object;
	}

}
