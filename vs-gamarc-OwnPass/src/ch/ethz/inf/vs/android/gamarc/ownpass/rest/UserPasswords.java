package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import android.util.Base64;
import ch.ethz.inf.vs.android.gamarc.ownpass.Server;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UserPasswords extends AsyncTask<Void, Void, String> {
    private Server server;
    private String authorizationString;

    public UserPasswords(Server server) {
        this.server = server;
        authorizationString = "Basic " + Base64.encodeToString((server.getUsername() + ":" + server.getPassword())
                .getBytes(), Base64.NO_WRAP);
    }

    protected String getUrl() {
        try {
            return server.getUrl() + "users/" + URLEncoder.encode(server.getUsername(), "UTF-8") + "/passwords";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String doInBackground(Void... params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet;
        httpGet = new HttpGet(getUrl());
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", authorizationString);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (response == null) {
            System.out.println("foo");
            return;
        }
        System.out.println(response);
    }

    public String getAuthorization() {
        return authorizationString;
    }
}
