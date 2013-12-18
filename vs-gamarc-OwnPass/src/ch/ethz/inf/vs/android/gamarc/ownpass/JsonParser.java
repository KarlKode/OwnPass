	package ch.ethz.inf.vs.android.gamarc.ownpass;

	import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

	import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

	import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

	import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser extends AsyncTask <String, Void, String> {

		private final Integer USER_ID;
		private JSONObject users;
		private JSONArray passwords;
		
		public JsonParser(int UserID){
			USER_ID = UserID;	
		}

		@Override
		protected String doInBackground(String... params) {//URL, Login, Password
		    StringBuilder builder = new StringBuilder();
		    HttpClient client = new DefaultHttpClient();

		    HttpGet httpGet = new HttpGet(params[0]);
			httpGet.setHeader(params[1], params[2]);
		    try {
		      HttpResponse response = client.execute(httpGet);
		      StatusLine statusLine = response.getStatusLine();
		      int statusCode = statusLine.getStatusCode();
		      if (statusCode == 200) {
		        HttpEntity entity = response.getEntity();
		        InputStream content = entity.getContent();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		        String line;
		        while ((line = reader.readLine()) != null) {
		          builder.append(line);
		        }
		      } else {
		        Log.e(JsonParser.class.toString(), "Failed to download file");
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
			try {
				passwords = new JSONArray(result);
			} catch (JSONException e) {
				try{
					users = new JSONObject(result);
				}catch(JSONException e2){
					e.printStackTrace();
				}
			}
		}
		
		public ArrayList<Password> getPasswords(){
			ArrayList<Password> pws = new ArrayList<Password>();
			
			for (int i=0; i < passwords.length(); i++)
			{
			    try {
			        JSONObject oneObject = passwords.getJSONObject(i);
			        // Pulling items from the array
			        int id = oneObject.getInt("id");
			        String pw = oneObject.getString("password");
			        String url = oneObject.getString("url");
			        int uid = oneObject.getInt("user_id");
			        String name = oneObject.getString("username");
			        String title = oneObject.getString("title");
			        
			        pws.add(new Password(uid, id, url, title, name, pw));
			    } catch (JSONException e) {
			    	e.printStackTrace();
			    }
			}
			return pws;
		}
		
		private Server evalServer(){
			try {
		        JSONObject oneObject = new JSONObject();
		        // Pulling items from the array
		        int id = oneObject.getInt("id");
		        String pw = oneObject.getString("password");
		        String name = oneObject.getString("username");
		        
		        return new Server(id, null, null, name, pw);
		    } catch (JSONException e) {
		    	e.printStackTrace();
		    }
			return null;
		}
		
		public boolean isConnected(Server s){
			boolean res = false;
			try{
				this.execute(s.getUrl(), s.getEncryptedLogin(), s.getEncryptedPW());
				Server sRet = evalServer();
				if(sRet.getId() == s.getId())
					if(sRet.getEncryptedLogin().equals(s.getEncryptedLogin()))
						if(sRet.getEncryptedPW().equals(s.getEncryptedPW()))
							res = true;
			}catch(Exception e){}
			return res;
		}
		
//		public boolean isConnected(){
//      ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
//          NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//          if (networkInfo != null && networkInfo.isConnected()) 
//              return true;
//          else
//              return false;    
//  }
}
