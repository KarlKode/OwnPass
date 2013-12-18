package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.os.AsyncTask;
import android.util.Base64;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import ch.ethz.inf.vs.android.gamarc.ownpass.Server;

import java.io.IOException;

public class LoginRequest extends AsyncTask<Void, Void, Void> {
	private DefaultHttpClient httpClient = new DefaultHttpClient();
	private ResponseHandler<String> responseHandler = new BasicResponseHandler();
	private HttpGet httpGet;
	
    private static String URL = "/users/%s";
    private static String authorizationString;
    private String response;
    
    public LoginRequest(Server s){
      
    	String username = new String(Base64.decode(s.getEncryptedLogin(), Base64.DEFAULT));
        String password = new String(Base64.decode(s.getEncryptedPW(), Base64.DEFAULT));
        
        URL = s.getUrl()+URL+username;
        authorizationString = "Basic " + Base64.encodeToString((username + ":" + password)
        		.getBytes(), Base64.NO_CLOSE);
        
        httpGet = new HttpGet(URL);
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", authorizationString);
     }

    @Override
    protected Void doInBackground(Void... params) {//no parameters
    	response = null;
        try {
            response = httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("response :" + response);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       
        
        //TODO  handle response;
        
        
    }
    
    public String getAuthorization(){
    	return authorizationString;
    }
    
    public String getBaseUrl(){
    	return URL;
    }
    
	public boolean isConnected(){
		boolean res = false;
		this.execute();
		if(response != null)	
			res = true;
		return res;
	}
    
}
