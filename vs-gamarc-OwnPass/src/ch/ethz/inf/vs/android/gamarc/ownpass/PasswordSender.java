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

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import java.net.URLEncoder;

public class PasswordSender extends AsyncTask<Password, Void, String>{
	private static Database database;
	
    protected static String URL;
    protected static String authorizationString;
    private static String SERVER_NAME;
    private ArrayList<Password> pToUp;
	
    public PasswordSender(Server s, String authorization, Database database){
    	SERVER_NAME = s.getName();
    	URL = s.getUrl()+"/passwords";
    	authorizationString = authorization;
    	this.database = database;
    }
    
	@Override
	protected String doInBackground(Password... params) { 
		
		String result = "";
		 try {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPut httpPut = new HttpPut(URL);
			 
			 httpPut.setHeader("Content-type", "application/json");
			 httpPut.setHeader("Authorization", authorizationString);
			 
			String json = createBody(params[0]);
			 StringEntity se = new StringEntity(json);
			 httpPut.setEntity(se);
			 
			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 result = httpclient.execute(httpPut, responseHandler); 
		 } catch (Exception e) {
			 Log.d("InputStream", e.getLocalizedMessage());
		 }
		 return result;
	}

	protected String createBody(Password pw){
		  String title = pw.getSiteName();
		  String url = pw.getUrl();
		  String username = pw.getEncryptedLogin();
		  String password = pw.getEncryptedPW();
		return String.format("title=%s&url=%s&username=%s&password=%s", title, url, username, password);
	}

	 @Override
	 protected void onPostExecute(String response) {
	     // handle response;
		 try {
				JSONObject oneObject = new JSONObject(response);
			    String login = oneObject.getString("user_id");
			    if(login.equals(SERVER_NAME));
			    	//TODO remove passwordToUpdate
			} catch (JSONException e) {
				Log.e(PasswordUpdateRequest.class.toString(), "Failed to download file: " + e.getMessage());
			}

	}
}