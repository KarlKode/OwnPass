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
import java.util.List;

import ch.ethz.inf.vs.android.gamarc.ownpass.rest.PasswordAdd;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.PasswordDelete;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.PasswordEdit;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.UserPasswords;

public class PasswordManagerActivity extends Activity{

    Dialog addPwDialog;
    Dialog editPwDialog;
    Dialog showPwDialog;
    EditText title;
    EditText username;
    EditText url;
    EditText password;
    Button delDialogBtn;
    Button cancelDialogBtn;
    Button saveDialogBtn;

	private Database db;
	private UserPasswords upass;
	private PasswordAdd padd;
	private PasswordDelete pdel;
	private PasswordEdit pedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_manager);

        List<Password> passwordList = new ArrayList<Password>();

        Server server = new Server(1,"o", "b", "c", "t");

        byte[] b = {1,2,6,3};
        Password pass = new Password(server, 14,"title","url",b ,b);
        passwordList.add(pass);
        
        db = new Database(this);
        upass = new UserPasswords(server, db); 
        String authorizationString = upass.getAuthorization();
        padd = new PasswordAdd(server, authorizationString);
        pdel = new PasswordDelete(server, authorizationString);
        pedit = new PasswordEdit(server, authorizationString);
        passwordList = getPasswords(server);

        //Add Server entries to the listview
        ListView lvPass = (ListView) findViewById(R.id.list_view_pwd);
        ArrayAdapter<Password> passwordArrayAdapter = new ArrayAdapter<Password>(this,
                android.R.layout.simple_list_item_1, passwordList);
        lvPass.setAdapter(passwordArrayAdapter);


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

        Button addPasswordButton = (Button) findViewById(R.id.activity_manager_button);
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

        addPwDialog = new Dialog(PasswordManagerActivity.this);
        addPwDialog.setContentView(R.layout.dialog_add_pwd);
        title = (EditText)addPwDialog.findViewById(R.id.activity_manager_button);
        url = (EditText)addPwDialog.findViewById(R.id.url);
        username = (EditText)addPwDialog.findViewById(R.id.username);
        password = (EditText)addPwDialog.findViewById(R.id.password);

        saveDialogBtn = (Button)addPwDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button)addPwDialog.findViewById(R.id.canbtn);
        addPwDialog.setTitle("Add new Password");



        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Create Databaseentry

                addPwDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPwDialog.dismiss();
            }
        });
        addPwDialog.show();

    }

    private void editPassword(Password pw){

        editPwDialog = new Dialog(PasswordManagerActivity.this);
        editPwDialog.setContentView(R.layout.dialog_edit_pwd);
        title = (EditText)editPwDialog.findViewById(R.id.servername);
        url = (EditText)editPwDialog.findViewById(R.id.url);
        username = (EditText)editPwDialog.findViewById(R.id.username);
        password = (EditText)editPwDialog.findViewById(R.id.password);

        saveDialogBtn = (Button)editPwDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button)editPwDialog.findViewById(R.id.canbtn);
        delDialogBtn = (Button)editPwDialog.findViewById(R.id.list_view_pwd);

        editPwDialog.setTitle("Edit Password");

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Create Databaseentry

                editPwDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                editPwDialog.dismiss();
            }
        });
        editPwDialog.show();

    }

    private void showPassword(Password pw){
<<<<<<< HEAD

        showPwDialog = new Dialog(PasswordManagerActivity.this);
        showPwDialog.setContentView(R.layout.dialog_show_pwd);
        title = (EditText)showPwDialog.findViewById(R.id.servername);
        url = (EditText)showPwDialog.findViewById(R.id.url);
        username = (EditText)showPwDialog.findViewById(R.id.username);
        password = (EditText)showPwDialog.findViewById(R.id.password);

        saveDialogBtn = (Button)showPwDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button)showPwDialog.findViewById(R.id.canbtn);
        showPwDialog.setTitle(pw.getTitle());

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Create Databaseentry

                showPwDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                showPwDialog.dismiss();
            }
        });
        showPwDialog.show();

    }


	private void addPasswords(){

	}
	
	private void update(){
		
=======
    	
    }
 
	
	public void update(){
		upass.execute();
>>>>>>> cfd3848e2dc0cfe11fc4a6fcad2d284bf49a6b22
	}
	
	private void save(Password pw){
		db.addPassword(pw);
		
	}
	
	private void delete(Password pw){
		db.removePassword(pw);
	}
	

    private List<Password>getPasswords(Server server){
    	return db.getPasswords(server.getId());
    }
}
