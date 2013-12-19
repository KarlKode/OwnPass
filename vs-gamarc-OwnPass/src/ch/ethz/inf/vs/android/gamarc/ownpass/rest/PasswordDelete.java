package ch.ethz.inf.vs.android.gamarc.ownpass.rest;

import android.os.AsyncTask;
import android.util.Log;
import ch.ethz.inf.vs.android.gamarc.ownpass.Password;
import ch.ethz.inf.vs.android.gamarc.ownpass.PasswordUpdateRequest;
import ch.ethz.inf.vs.android.gamarc.ownpass.Server;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PasswordDelete extends AsyncTask<Password, Void, String> {

    private Server server;
    private String authorizationString;
    private ArrayList<Password> pToUp;

    public PasswordDelete(Server server, String authorizationString) {
        this.server = server;
        this.authorizationString = authorizationString;
    }

    private String getUrl() {
        return server.getUrl() + "/passwords";
    }

    @Override
    protected String doInBackground(Password... params) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(getUrl() + "/" + params[0].getServerId());

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
            if (login.equals("password_deleted")) ;
            //TODO remove passwordToUpdate
        } catch (JSONException e) {
            Log.e(PasswordUpdateRequest.class.toString(), "Failed to download file: " + e.getMessage());
        }
    }

}