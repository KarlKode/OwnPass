package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import android.util.Log;
import ch.ethz.inf.vs.android.gamarc.ownpass.Password;
import ch.ethz.inf.vs.android.gamarc.ownpass.PasswordUpdateRequest;
import ch.ethz.inf.vs.android.gamarc.ownpass.Server;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordAdd extends AsyncTask<Password, Void, String> {
    private Server server;
    protected String authorizationString;

    public PasswordAdd(Server s, String authorizationString) {
        this.server = server;
        this.authorizationString = authorizationString;
    }

    protected String getUrl() {
        return server.getUrl() + "/passwords";
    }

    @Override
    protected String doInBackground(Password... params) {

        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(getUrl());

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

    protected String createBody(Password pw) {
        String title = pw.getTitle();
        String url = pw.getUrl();
        String username = pw.getUsernameBase64();
        String password = pw.getPasswordBase64();
        // TODO: Urlencode
        return String.format("title=%s&url=%s&username=%s&password=%s", title, url, username, password);
    }

    @Override
    protected void onPostExecute(String response) {
        // handle response;
        try {
            JSONObject oneObject = new JSONObject(response);
            String login = oneObject.getString("user_id");
            if (login.equals("foo")) ;
            //TODO remove passwordToUpdate
        } catch (JSONException e) {
            Log.e(PasswordUpdateRequest.class.toString(), "Failed to download file: " + e.getMessage());
        }

    }
}
