package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class PasswordManagerActivity extends Activity{

    Dialog addPwDialog;
    Dialog editPwDialog;
    Dialog showPwDialog;
    EditText title;
    EditText username;
    EditText url;
    EditText password;
	private Database db;

	public PasswordManagerActivity(Database db){
		this.db = db;
	}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_manager);

        ArrayList<Password> passwordList = new ArrayList<Password>();

        Server server = new Server(1,"o", "b", "c", "t");
//        Byte[] b = new Byte[];
//        Password pass = new Password(server, 14,"title","url",; ,new Byte[]);
//        passwordList.add(pass);
        db = new Database(this);
        passwordList = getPasswords(server);

        //Add Server entries to the listview
        ListView lvPass = (ListView) findViewById(R.id.list_view_pwd);
        ArrayAdapter<Password> serverArrayAdapter = new ArrayAdapter<Password>(this,
                android.R.layout.simple_list_item_1, passwordList);
        lvPass.setAdapter(serverArrayAdapter);


        //start OnCLickListener to the password ListView
        lvPass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked Password
                Password pw = (Password) parent.getItemAtPosition(position);
                if (password == null) {
                    return;
                }

                showPassword(pw);
            }
        });


        lvPass.setLongClickable(true);
        lvPass.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                Password pw = (Password) parent.getItemAtPosition(position);
                editPassword(pw);
                return true;
            }
        });

        //start OnClickListerner for the button

        Button addPasswordButton = (Button) findViewById(R.id.activity_signin_button);
        addPasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addNewPassword();


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }



    private void addNewPassword(){

    }

    private void editPassword(Password pw){

    }

    private void showPassword(Password pw){

    }
    
    /*
	public void addPasswords(){
		
	}
	
	public void update(){
		
	} */
	
	private void save(Password pw){
		
	}
	
	private void delete(Password pw){
		
	}
	
	private void change(Password pw){
		
	}

    private ArrayList<Password>getPasswords(Server server){
    	
        return null;
    }
}
