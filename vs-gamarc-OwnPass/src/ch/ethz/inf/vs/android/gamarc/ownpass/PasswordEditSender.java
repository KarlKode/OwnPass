package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.util.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class PasswordEditSender extends PasswordSender {
    private Server server;

    public PasswordEditSender(Server server, String authorizationString) {
        super(server, authorizationString);
    }

    @Override
    protected String doInBackground(Password... params) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(super.getUrl() + "/" + params[0].getServerId());

            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", super.authorizationString);

            String json = createBody(params[0]);
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            result = httpclient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

}
