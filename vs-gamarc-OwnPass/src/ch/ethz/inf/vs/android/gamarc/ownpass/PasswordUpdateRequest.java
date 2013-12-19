package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class PasswordUpdateRequest extends AsyncTask<String, Void, String> {
    private ArrayList<Password> passwords = new ArrayList<Password>();
    private String authorization;
    private Server server;

    public PasswordUpdateRequest(Server server, String authorization) {
        this.server = server;
        this.authorization = authorization;
    }

    private String getUrl() {
        return server.getUrl() + "/passwords";
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(getUrl());

        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", authorization);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        JSONArray pws;
        try {
            pws = new JSONArray(result);
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
            Log.e(PasswordUpdateRequest.class.toString(), "Failed to download file");
        }

        //TODO callback
    }

    public ArrayList<Password> getPasswords() {
        return passwords;
    }
}
