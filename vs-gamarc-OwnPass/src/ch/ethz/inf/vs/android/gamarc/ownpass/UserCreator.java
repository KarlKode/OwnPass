package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

//import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class UserCreator extends AsyncTask<String, Void, String>{
	private static final String BASE_AUTHORIZATION = "username=%s&password=%s";
	private static Server serv;
	private static String URL;
	
	@Override
	protected String doInBackground(String... params) { //0)Url not Base_URL; 1)username 2)login_name 3)password
		URL = params[0]; 
		
		String authorization = createAuthorization(params[2], params[3]);	
		
		 String result = "";
		 try {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPut httpPut = new HttpPut(URL + "/users");

			 httpPut.setHeader("Content-type", "application/json");
			 httpPut.setHeader("Authorization", authorization);
			 
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 result = httpclient.execute(httpPut, responseHandler); 
		 } catch (Exception e) {
			 Log.d("InputStream", e.getLocalizedMessage());
		 }
		 return result;
	}
	
	private String createAuthorization(String username, String password) {
		String base = String.format(BASE_AUTHORIZATION, username, password);
        return "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_CLOSE);
	}
	 
	 public Server getCreatedServer(){
		 return serv;
	 }
	 
	 @Override
	 protected void onPostExecute(String response) {
	     super.onPostExecute(response);

	     try {
			JSONObject oneObject = new JSONObject(response);
		    String pw = oneObject.getString("password");
		    String uid = oneObject.getString("user_id");
		    String login = oneObject.getString("username");
			
		    serv = new Server(0, URL , uid, login, pw); //server_id is set later from database
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	}
}
