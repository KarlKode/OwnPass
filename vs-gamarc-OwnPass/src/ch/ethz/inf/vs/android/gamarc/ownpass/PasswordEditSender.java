package ch.ethz.inf.vs.android.gamarc.ownpass;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import org.apache.http.client.methods.HttpPost;;

public class PasswordEditSender extends PasswordSender{

	public PasswordEditSender(Server s, String authorization, Database database) {
		super(s, authorization, database);
	}
	
	@Override
	protected String doInBackground(Password... params) {
		String result = "";
		 try {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPost httpPost = new HttpPost(URL+"/"+params[0].getSiteId());
			 
			 httpPost.setHeader("Content-type", "application/json");
			 httpPost.setHeader("Authorization", authorizationString);
			 
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
