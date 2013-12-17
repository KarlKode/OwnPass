package ch.ethz.inf.vs.android.gamarc.ownpass;

public class Password {
	private final int SERVER_ID;
	private int SITE_ID;
	private final String SITEURL;
	private final String SITENAME;
	private final String LOGIN;
	private final String PASSWORD;
	
	//static int autoIncCounter;
	
	public int getServerId(){
		return this.SERVER_ID;
	}
	
	public int getSiteId(){
		return this.SITE_ID;
	}
	
	public String getUrl(){
		return this.SITEURL;
	}
	
	public String getSiteName(){
		return this.SITENAME;
	}
	
	public String getEncryptedLogin(){
		return this.LOGIN;
	}
	
	public String getEncryptedPW(){
		return this.PASSWORD;
	}
	
	public Password(int Server_id, int Site_id, String url, String Site_name, String login, String password){
		SERVER_ID = Server_id;
		SITE_ID = Site_id;
		SITEURL = url;
		SITENAME = Site_name;
		PASSWORD = password;
		LOGIN = login;	
	}
	
//	public Password(int Server_id, String url, String Site_name, String login, String password){
//		SERVER_ID = Server_id;
//		//SITE_ID = autoIncCounter ++;
//		SITEURL = url;
//		SITENAME = Site_name;
//		PASSWORD = password;
//		LOGIN = login;	
//	}
}
