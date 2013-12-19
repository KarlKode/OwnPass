package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import ch.ethz.inf.vs.android.gamarc.ownpass.Password;
import ch.ethz.inf.vs.android.gamarc.ownpass.Server;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PasswordAdd extends AsyncTask<Password, Void, String> {
    protected String authorizationString;
    private Server server;

    public PasswordAdd(Server server, String authorizationString) {
        this.server = server;
        this.authorizationString = authorizationString;
    }

    protected String getUrl() {
        return server.getUrl() + "passwords";
    }

    @Override
    protected String doInBackground(Password... params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut;
        httpPut = new HttpPut(getUrl());
        httpPut.setHeader("Content-Type", "application/json");
        httpPut.setHeader("Authorization", authorizationString);

        httpPut.setEntity(getBodyEntity(params[0]));

        try {
            HttpResponse response = httpClient.execute(httpPut);
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

    private HttpEntity getBodyEntity(Password pwd) {
        try {
            String title = URLEncoder.encode(pwd.getTitle(), "UTF-8");
            String url = URLEncoder.encode(pwd.getUrl(), "UTF-8");
            String username = URLEncoder.encode(pwd.getUsernameBase64(), "UTF-8");
            String password = URLEncoder.encode(pwd.getPasswordBase64(), "UTF-8");
            return new StringEntity(
                    String.format("title=%s&url=%s&username=%s&password=%s", title, url, username, password));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        // TODO
    }
}
