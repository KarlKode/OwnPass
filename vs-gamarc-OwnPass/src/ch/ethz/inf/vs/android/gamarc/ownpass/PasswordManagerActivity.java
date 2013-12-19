package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.PasswordAdd;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.PasswordDelete;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.PasswordEdit;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.UserPasswords;

import java.util.ArrayList;
import java.util.List;

public class PasswordManagerActivity extends Activity implements UserPasswordCallback {

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
    private static Database db;
    private UserPasswords upass;
    private PasswordAdd padd;
    private PasswordDelete pdel;
    private PasswordEdit pedit;
    private Server server;
    private Encryption encryption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_manager);

        List<Password> passwordList = new ArrayList<Password>();
        
        db = new Database(this);
        Intent intent = getIntent();
        server = db.getServer(intent.getLongExtra(SigninActivity.EXTRA_SERVER_ID, 0));

        upass = new UserPasswords(this, server, db);
        String authorizationString = upass.getAuthorization();
        padd = new PasswordAdd(server, authorizationString);
        pdel = new PasswordDelete(server, authorizationString);
        pedit = new PasswordEdit(server, authorizationString);
        Toast.makeText(this, "loading passwords", Toast.LENGTH_LONG).show();
        update();
        passwordList = getPasswords(server);


        encryption = new Encryption(server);
        

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

    private void addNewPassword() {

        addPwDialog = new Dialog(PasswordManagerActivity.this);
        addPwDialog.setContentView(R.layout.dialog_add_pwd);
        title = (EditText) addPwDialog.findViewById(R.id.servername);
        url = (EditText) addPwDialog.findViewById(R.id.url);
        username = (EditText) addPwDialog.findViewById(R.id.username);
        password = (EditText) addPwDialog.findViewById(R.id.password);

        saveDialogBtn = (Button) addPwDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button) addPwDialog.findViewById(R.id.canbtn);
        addPwDialog.setTitle("Add new Password");



        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String t = title.getText().toString();
                String ur = url.getText().toString();
                String us = username.getText().toString();
                String p = password.getText().toString();
                save(t, ur, us, p);

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

    private void editPassword(final Password pw) {
        editPwDialog = new Dialog(PasswordManagerActivity.this);
        editPwDialog.setContentView(R.layout.dialog_edit_pwd);
        title = (EditText) editPwDialog.findViewById(R.id.servername);
        url = (EditText) editPwDialog.findViewById(R.id.url);
        username = (EditText) editPwDialog.findViewById(R.id.username);
        password = (EditText) editPwDialog.findViewById(R.id.password);

        saveDialogBtn = (Button) editPwDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button) editPwDialog.findViewById(R.id.canbtn);
        delDialogBtn = (Button) editPwDialog.findViewById(R.id.list_view_pwd);

        title.setText(pw.getTitle(), TextView.BufferType.EDITABLE);
        url.setText(pw.getUrl(), TextView.BufferType.EDITABLE);
        username.setText(encryption.decrypt(pw.getUsername()), TextView.BufferType.EDITABLE);

        editPwDialog.setTitle("Edit Password");

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = title.getText().toString();
                String ur = url.getText().toString();
                String us = username.getText().toString();
                String pw = password.getText().toString();
                save(name, ur, us, pw);

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

        delDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(pw);
                editPwDialog.dismiss();
            }
        });


        editPwDialog.show();

    }

    private void showPassword(Password pw) {
        showPwDialog = new Dialog(PasswordManagerActivity.this);
        showPwDialog.setContentView(R.layout.dialog_show_pwd);
        TextView title = (TextView) showPwDialog.findViewById(R.id.servername);
        TextView url = (TextView) showPwDialog.findViewById(R.id.url);
        TextView username = (TextView) showPwDialog.findViewById(R.id.username);
        TextView password = (TextView) showPwDialog.findViewById(R.id.password);

        title.setText(pw.getTitle());
        url.setText(pw.getUrl());
        username.setText(encryption.decrypt(pw.getUsername()));
        password.setText(encryption.decrypt(pw.getPassword()));


        cancelDialogBtn = (Button) showPwDialog.findViewById(R.id.back_button_showdialog);
        showPwDialog.setTitle(pw.getTitle());


        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                showPwDialog.dismiss();
            }
        });
        showPwDialog.show();

    }

    private void save(String title, String url, String username, String password) {
    	Encryption crypto = new Encryption(server);
    	Password pw = new Password(server, server.getId(), title, url, crypto.encrypt(username),  crypto.encrypt(password));
        db.addPassword(pw);

    }

    private void delete(Password pw) {
        db.removePassword(pw);
    }

    private List<Password> getPasswords(Server server) {
        return db.getPasswords(server.getId());
    }
    
    public void update() {
        upass.execute();
    }
    @Override
    public void onSuccess(List<Password> passwordList) {
        for(Password p: passwordList) {
        	db.addPassword(p);
        }
    }
    @Override
    public void onError(Exception e) {
        if (e == null) {
            onSuccess(null);
        }

        Toast.makeText(this, "Could not update passwords", Toast.LENGTH_LONG).show();
    }
}
