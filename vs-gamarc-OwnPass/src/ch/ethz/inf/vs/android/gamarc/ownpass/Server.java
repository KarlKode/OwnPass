package ch.ethz.inf.vs.android.gamarc.ownpass;

public class Server {
	private int SERVER_ID;
	private String LOGIN;
	private String PASSWORD;
	private String SERVER_NAME;
	private String SERVER_URL;
	
	public int getId(){
		return this.SERVER_ID;
	}
	
	public String getUrl(){
		return this.SERVER_URL;
	}
	
	public String getName(){
		return this.SERVER_NAME;
	}
	
	public void setID(int id){
		 this.SERVER_ID = id;
	}
	
	public String getEncryptedLogin(){
		return this.LOGIN;
	}
	
	public String getEncryptedPW(){
		return this.PASSWORD;
	}
	
	public Server(int Server_id, String url, String Server_name, String login, String password){
		SERVER_ID = Server_id;
		SERVER_URL = url;
		SERVER_NAME = Server_name;
		PASSWORD = password;
		LOGIN = login;	
	}

}
