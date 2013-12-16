package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PasswordDataBaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "passwords.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_Passwords = null;
	private static final String ID = null;
	private static final String Username = null;
	private static final String Website = null;
	private static final String Password = null;

	public PasswordDataBaseHelper(Context context, String name, CursorFactory factory, int version) {
		 super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_Passwords + "(" + ID
	      + " integer primary key, "+ Website + " text not null, "+ Username + " text not null, "+ Password  +" text not null);";

	
	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(PasswordDataBaseHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_Passwords);
	    onCreate(db);
	  }

}
