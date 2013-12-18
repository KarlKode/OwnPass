package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.os.AsyncTask;
import android.util.Base64;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import ch.ethz.inf.vs.android.gamarc.ownpass.Server;

import java.io.IOException;

public class LoginRequest extends AsyncTask<String, Void, Void> {
    private static String URL = "users/%s/passwords";
    private static String authorizationString;
    private String response;
    
    public LoginRequest(Server s){
        URL = s.getUrl()+URL;
    	String username = s.getEncryptedLogin();
        String password = s.getEncryptedPW();
        
        URL = String.format(URL, username);
        
        authorizationString = "Basic " + Base64.encodeToString((username + ":" + password)
        		.getBytes(), Base64.NO_CLOSE);
    }

    @Override
    protected Void doInBackground(String... params) { // Username, hashed password


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Content-Type", "application/json");


        httpGet.setHeader("Authorization", authorizationString);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            response = httpClient.execute(httpGet, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("response :" + response);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       //TODO  handle response;
    }
}
