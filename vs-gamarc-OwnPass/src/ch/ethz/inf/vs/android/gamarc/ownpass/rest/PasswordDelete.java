package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import android.util.Log;
import ch.ethz.inf.vs.android.gamarc.ownpass.Password;
import ch.ethz.inf.vs.android.gamarc.ownpass.PasswordUpdateRequest;
import ch.ethz.inf.vs.android.gamarc.ownpass.Server;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PasswordDelete extends AsyncTask<Password, Void, String> {

    private Server server;
    private String authorizationString;

    public PasswordDelete(Server server, String authorizationString) {
        this.server = server;
        this.authorizationString = authorizationString;
    }

    private String getUrl(Password password) {
        return server.getUrl() + "passwords/" + password.getServerId();
    }

    @Override
    protected String doInBackground(Password... params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete;
        httpDelete = new HttpDelete(getUrl(params[0]));
        httpDelete.setHeader("Content-Type", "application/json");
        httpDelete.setHeader("Authorization", authorizationString);

        try {
            HttpResponse response = httpClient.execute(httpDelete);
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
        // TODO: Handle response
    }

}