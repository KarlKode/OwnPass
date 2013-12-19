package ch.ethz.inf.vs.android.gamarc.ownpass;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.client.methods.HttpDelete;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordDeleteSender extends AsyncTask<Password, Void, String>{
	private static Database database;
	
    protected static String URL;
    protected static String authorizationString;
    private static String SERVER_NAME;
    private ArrayList<Password> pToUp;
	
    public PasswordDeleteSender(Server s, String authorization, Database database){
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
			 HttpDelete httpDelete = new HttpDelete(URL+"/"+params[0].getSiteId());
			 
			 httpDelete.setHeader("Content-type", "application/json");
			 httpDelete.setHeader("Authorization", authorizationString);

			 ResponseHandler<String> responseHandler = new BasicResponseHandler();
			 result = httpclient.execute(httpDelete, responseHandler); 
		 } catch (Exception e) {
			 Log.d("InputStream", e.getLocalizedMessage());
		 }
		 return result;
	}
	
	 @Override
	 protected void onPostExecute(String response) {
	     // handle response;
		 try {
				JSONObject oneObject = new JSONObject(response);
			    String login = oneObject.getString("status");
			    if(login.equals("password_deleted"));
			    	//TODO remove passwordToUpdate
			} catch (JSONException e) {
				Log.e(PasswordUpdateRequest.class.toString(), "Failed to download file: " + e.getMessage());
			}
	}

}