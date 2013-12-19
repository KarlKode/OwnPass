package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class UserCreator extends AsyncTask<String, Void, String>{
	private static final String BASE_AUTHORIZATION = "username=%s&password=%s";
	private static Server serv;
	
	@Override
	protected String doInBackground(String... params) { //0)Url not Base_URL; 1)username 2)login_name 3)password
		String url = params[0]+"/users"; 
		String json = createUserObject(params[1], params[0], params[1]).toString();
		String authorization = createAuthorization(params[2], params[3]);	
		return put(url, json, authorization);
	}
	
	private String createAuthorization(String username, String password) {
		String base = String.format(BASE_AUTHORIZATION, username, password);
        return "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_CLOSE);
	}

//	private JSONObject createUserObject(String userid, String url, String username){
//		String login = RandomStringUtils.randomAlphanumeric(10);
//		String pw = RandomStringUtils.randomAlphanumeric(20);
//		login = Base64.encodeToString(login.getBytes(), Base64.DEFAULT);
//		pw = Base64.encodeToString(login.getBytes(), Base64.DEFAULT);
//		
//		serv = new Server(0, url, username, login, pw); //server_id is set later from database
//		
//		JSONObject object = new JSONObject();
//		  try {
//		    object.put("user_id", userid);
//		    object.put("password", pw);
//		    object.put("username", login);
//		  } catch (JSONException e) {
//		    e.printStackTrace();
//		  }
//		return object;
//	}

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
//			 serv = null;
		 }
		 return result;
	 }
	 
	 public Server getCreatedServer(){
		 return serv;
	 }
	 
	 @Override
	 protected void onPostExecute(String response) {
	     super.onPostExecute(response);
	     //TODO  handle response;
	}
}
