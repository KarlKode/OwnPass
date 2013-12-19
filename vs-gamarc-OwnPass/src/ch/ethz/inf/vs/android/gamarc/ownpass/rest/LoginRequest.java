package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import android.util.Base64;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class LoginRequest extends AsyncTask<String, Void, Void> {
    public static final String BASE_URL = "http://127.0.0.1/users/%s/passwords";
    private String response;

    @Override
    protected Void doInBackground(String... params) { // Username, hashed password
        String username = params[0];
        String password = params[1];
        String url = String.format(BASE_URL, username);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");

        String authorizationString = "Basic " + Base64.encodeToString((username + ":" + password)
        		.getBytes(), Base64.NO_CLOSE);
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
       //TODO  print response;
    }
}
