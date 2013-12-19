package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.UserPasswords;

import java.util.List;

public class SigninActivity extends Activity implements UserPasswordCallback {
    public static String EXTRA_SERVER_ID = "SERVER_ID";

    Dialog addServerDialog;
    Dialog editServerDialog;
    EditText serverName;
    EditText serverUrl;
    EditText serverLogin;
    EditText serverPassword;
    Button saveDialogBtn;
    Button cancelDialogBtn;
    Button delDialogBtn;
    Database database;
    ArrayAdapter<Server> serverArrayAdapter;
    Server serverToConnect = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        database = new Database(this);

        //Add Server entries to the listview
        ListView lvServer = (ListView) findViewById(R.id.list_view);
        serverArrayAdapter = new ArrayAdapter<Server>(this,
                android.R.layout.simple_list_item_1, database.getServers());
        lvServer.setAdapter(serverArrayAdapter);

        //start OnCLickListener to the server ListView
        lvServer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked ServerWrapper
                Server server = (Server) parent.getItemAtPosition(position);
                if (server == null) {
                    return;
                }

                if (serverToConnect != null) {
                    // TODO display error
                    return;
                }

                serverToConnect = server;
                UserPasswords userPasswords = new UserPasswords(SigninActivity.this, server, database);
                userPasswords.execute();
            }
        });

        lvServer.setLongClickable(true);
        lvServer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                Server server = (Server) parent.getItemAtPosition(position);
                editServer(server);
                return true;
            }
        });

        //start OnClickListerner for the button
        Button addServerButton = (Button) findViewById(R.id.activity_signin_button);
        addServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addNewServer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

<<<<<<< HEAD
    private void addNewServer(){
        //TODO call dialog with dialog_addServer_signinver_signin.xml
        //http://developer.android.com/guide/topics/ui/dialogs.html

        addServerDialog = new Dialog(SigninActivity.this);
        addServerDialog.setContentView(R.layout.dialog_addServer_signin);
        serverName = (EditText)addServerDialog.findViewById(R.id.servername);
        serverUrl = (EditText)addServerDialog.findViewById(R.id.url);
        serverLogin = (EditText)addServerDialog.findViewById(R.id.username);
        serverPassword = (EditText)addServerDialog.findViewById(R.id.password);
=======
    private void addNewServer() {
        //TODO call dialog with dialog_signin.xml
        //http://developer.android.com/guide/topics/ui/dialogs.html

        addServerDialog = new Dialog(SigninActivity.this);
        addServerDialog.setContentView(R.layout.dialog_signin);
        serverName = (EditText) addServerDialog.findViewById(R.id.servername);
        serverUrl = (EditText) addServerDialog.findViewById(R.id.url);
        serverLogin = (EditText) addServerDialog.findViewById(R.id.username);
        serverPassword = (EditText) addServerDialog.findViewById(R.id.password);
>>>>>>> cfd3848e2dc0cfe11fc4a6fcad2d284bf49a6b22

        saveDialogBtn = (Button) addServerDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button) addServerDialog.findViewById(R.id.canbtn);
        addServerDialog.setTitle("Add new Server");

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the server to the database
                String name = serverName.getText().toString();
                String url = serverUrl.getText().toString();
                String username = serverLogin.getText().toString();
                String password = serverLogin.getText().toString();
                database.addServer(new Server(name, url, username, password));

                updateServers();

                addServerDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServerDialog.dismiss();
            }
        });
        addServerDialog.show();
    }

    private void editServer(final Server server) {
        editServerDialog = new Dialog(SigninActivity.this);
<<<<<<< HEAD
        editServerDialog.setContentView(R.layout.dialog_addServer_signin);
        serverName = (EditText)editServerDialog.findViewById(R.id.servername);
        serverUrl = (EditText)editServerDialog.findViewById(R.id.url);
        serverLogin = (EditText)editServerDialog.findViewById(R.id.username);
        serverPassword = (EditText)editServerDialog.findViewById(R.id.password);
=======
        editServerDialog.setContentView(R.layout.dialog_signin);
        serverName = (EditText) editServerDialog.findViewById(R.id.servername);
        serverUrl = (EditText) editServerDialog.findViewById(R.id.url);
        serverLogin = (EditText) editServerDialog.findViewById(R.id.username);
        serverPassword = (EditText) editServerDialog.findViewById(R.id.password);
>>>>>>> cfd3848e2dc0cfe11fc4a6fcad2d284bf49a6b22

        saveDialogBtn = (Button) editServerDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button) editServerDialog.findViewById(R.id.canbtn);
        delDialogBtn = (Button) editServerDialog.findViewById(R.id.delbtn);

        editServerDialog.setTitle("Edit Server");

        serverName.setText(server.getName(), TextView.BufferType.EDITABLE);
        serverUrl.setText(server.getUrl(), TextView.BufferType.EDITABLE);
        serverLogin.setText(server.getUsername(), TextView.BufferType.EDITABLE);

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the server entry in the database
                String name = serverName.getText().toString();
                String url = serverUrl.getText().toString();
                String username = serverLogin.getText().toString();
                String password = serverLogin.getText().toString();
                database.updateServer(server.getId(), name, url, username, password);

                updateServers();

                editServerDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editServerDialog.dismiss();
            }
        });

        delDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the server from the database
                database.removeServer(server.getId());

                updateServers();

                editServerDialog.dismiss();
            }
        });

        editServerDialog.show();
    }

    private void updateServers() {
        serverArrayAdapter.clear();
        serverArrayAdapter.addAll(database.getServers());
    }

    @Override
    public void onSuccess(List<Password> passwordList) {
        if (serverToConnect == null) {
            return;
        }

        // Create intent to switch to the PasswordManagerActivity
        Intent intent = new Intent(getApplicationContext(), PasswordManagerActivity.class);

        // Pass the server as an extra to the PasswordManagerActivity
        intent.putExtra(EXTRA_SERVER_ID, serverToConnect.getId());

        // Start the activity
        startActivity(intent);

        serverToConnect = null;
    }

    @Override
    public void onError(Exception e) {
        if (serverToConnect == null) {
            return;
        }

        if (e == null) {
            onSuccess(null);
        }

        Toast.makeText(this, "Could not log in", Toast.LENGTH_LONG).show();
        serverToConnect = null;
    }
}
