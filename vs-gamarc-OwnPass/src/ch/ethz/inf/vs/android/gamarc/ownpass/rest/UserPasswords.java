package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import ch.ethz.inf.vs.android.gamarc.ownpass.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class UserPasswords extends AsyncTask<Void, Void, String> {
    private UserPasswordCallback callback;
    private Server server;
    private String authorizationString;
    private Database db;
    private Exception exception;

    public UserPasswords(UserPasswordCallback callback, Server server, Database d) {
        this.callback = callback;
        this.server = server;
        db = d;
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
                exception = null;
                return null;
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            exception = e;
        } catch (IOException e) {
            e.printStackTrace();
            exception = e;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (response == null) {
            callback.onError(exception);
            return;
        }

        List<Password> passwords = new ArrayList <Password>();
        JSONArray pws;
        try {
            pws = new JSONArray(response);
            for (int i = 0; i < pws.length(); i++) {
                JSONObject oneObject = pws.getJSONObject(i);
                // Pulling items from the array
                int id = oneObject.getInt("id");
                String pw = oneObject.getString("password");
                String url = oneObject.getString("url");
                String name = oneObject.getString("username");
                String title = oneObject.getString("title");

                passwords.add(new Password(server, id, url, title, name, pw));
            }
           
        } catch (JSONException e) {
            Log.e(UserPasswords.class.toString(), "Failed to download file");
        }

        callback.onSuccess(passwords);
    }

    public String getAuthorization() {
        return authorizationString;
    }
}
