package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

public class PasswordManagerActivity extends Activity{
//	private Database db;
//	
//	public PasswordManagerActivity(Database db){
//		this.db = db;
//	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_manager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
	
	public void displayPasswords(){
		
	}
	
	public void addPasswords(){
		
	}
	
	public void update(){
		
	}
	
	public void save(Password pw){
		
	}
	
	public void delete(Password pw){
		
	}
	
	public void change(Password pw){
		
	}
}
