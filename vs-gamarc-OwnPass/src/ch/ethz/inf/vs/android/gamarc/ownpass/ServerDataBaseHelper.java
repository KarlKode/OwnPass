package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ServerDataBaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "servers.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_SERVER = null;
	private static final String SERVER_ID = null;
	private static final String LOGIN = null;
	private static final String PASSWORD = null;
	private static final String SERVER_NAME = null;
	private static final String SERVER_URL = null;

	public ServerDataBaseHelper(Context context, String name, CursorFactory factory, int version) {
		 super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_SERVER + "(" + SERVER_ID
	      + " integer primary key autoincrement, "+ SERVER_NAME + " text not null, "+ SERVER_URL + " text not null, "+ LOGIN + " text not null, "+ PASSWORD  +" text not null);";

	
	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(PasswordDataBaseHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER);
	    onCreate(db);
	  }

}
