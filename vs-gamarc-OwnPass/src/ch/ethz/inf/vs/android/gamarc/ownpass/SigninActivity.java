package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SigninActivity extends Activity {

    public static int LONG_PRESS_TIME = 500;
    ServerDataBaseHelper sHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

        //TODO create server manager object
        List<Server> servers = sHelper.get_All_Server();

        //Add Server entries to the listview
        ListView lvServer = (ListView) findViewById(R.id.list_view);


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

    private void addNewServer(){
        //TODO call dialog with dialog_signin.xml
        //http://developer.android.com/guide/topics/ui/dialogs.html
    }




}
