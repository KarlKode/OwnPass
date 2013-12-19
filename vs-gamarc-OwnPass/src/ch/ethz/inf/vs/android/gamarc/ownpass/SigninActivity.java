package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import ch.ethz.inf.vs.android.gamarc.ownpass.rest.UserPasswords;

import java.util.ArrayList;
import java.util.List;

public class SigninActivity extends Activity {

    public static int LONG_PRESS_TIME = 500;
    Dialog addServerDialog;
    Dialog editServerDialog;
    EditText serverName;
    EditText serverUrl;
    EditText serverLogin;
    EditText serverPassword;
    Button saveDialogBtn;
    Button cancelDialogBtn;
    Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

        database = new Database(this);
        List<Server> servers = new ArrayList<Server>();//testing
        Server testServer1 = new Server(1,"http://marcg.ch:5000/", "marc2", "login", "password");

        servers.add(testServer1); //testing
        Server testServer2 = new Server(2,"http://marcg.ch:5000/", "marc", "login2", "password2");
        servers.add(testServer2); //testing

        //Add Server entries to the listview
        ListView lvServer = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<Server> serverArrayAdapter = new ArrayAdapter<Server>(this,
                android.R.layout.simple_list_item_1, servers);
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

                // Create intent to switch to the SensorActivity
               Intent intent = new Intent(getApplicationContext(), PasswordManagerActivity.class);
                // Pass the sensor as an extra to the SensorActivity
               // intent.putExtra(EXTRA_SENSOR, sensorWrapper.getSensor().toString());
                startActivity(intent);
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

        // Debug stuff
        Server marcgTest = new Server(0, "testserver", "http://marcg.ch:5000/", "test", "foo");
        UserPasswords r = new UserPasswords(marcgTest);
        r.execute();

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

    private void addNewServer(){
        //TODO call dialog with dialog_signin.xml
        //http://developer.android.com/guide/topics/ui/dialogs.html

        addServerDialog = new Dialog(SigninActivity.this);
        addServerDialog.setContentView(R.layout.dialog_signin);
        serverName = (EditText)addServerDialog.findViewById(R.id.servername);
        serverUrl = (EditText)addServerDialog.findViewById(R.id.url);
        serverLogin = (EditText)addServerDialog.findViewById(R.id.username);
        serverPassword = (EditText)addServerDialog.findViewById(R.id.password);

        saveDialogBtn = (Button)addServerDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button)addServerDialog.findViewById(R.id.canbtn);
        addServerDialog.setTitle("Add new Server");

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Create Databaseentry

                addServerDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                addServerDialog.dismiss();
            }
        });
        addServerDialog.show();
    }
    
    private void editServer(Server server){
        editServerDialog = new Dialog(SigninActivity.this);
        editServerDialog.setContentView(R.layout.dialog_signin);
        serverName = (EditText)editServerDialog.findViewById(R.id.servername);
        serverUrl = (EditText)editServerDialog.findViewById(R.id.url);
        serverLogin = (EditText)editServerDialog.findViewById(R.id.username);
        serverPassword = (EditText)editServerDialog.findViewById(R.id.password);

        saveDialogBtn = (Button)editServerDialog.findViewById(R.id.savebtn);
        cancelDialogBtn = (Button)editServerDialog.findViewById(R.id.canbtn);
        editServerDialog.setTitle("Edit Server");

        serverName.setText(server.getName(), TextView.BufferType.EDITABLE);
        serverUrl.setText(server.getUrl(), TextView.BufferType.EDITABLE);
        serverLogin.setText(server.getUsername(), TextView.BufferType.EDITABLE);

        saveDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Update Databaseentry

                editServerDialog.dismiss();
            }
        });
        cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editServerDialog.dismiss();
            }
        });
        editServerDialog.show();
    }




}
