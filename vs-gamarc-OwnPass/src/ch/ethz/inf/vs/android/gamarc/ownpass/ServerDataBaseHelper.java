package ch.ethz.inf.vs.android.gamarc.ownpass;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	  
	  
	  public void Add_Server(Password passw){
		  SQLiteDatabase db = this.getWritableDatabase();
		  ContentValues values =new ContentValues();
		  //values.put(SERVER_ID, passw.getServerId());
		  values.put(SERVER_NAME, passw.getSiteName());
		  values.put(SERVER_URL, passw.getUrl());
		  values.put(LOGIN, passw.getEncryptedLogin());
		  values.put(PASSWORD, passw.getEncryptedPW());
		  //insert
		  db.insert(TABLE_SERVER, null, values);
		  db.close();
	  }
	  
	  ArrayList<Server> sList = new ArrayList<Server>(); 
	  
	  public ArrayList<Server> get_All_Server(){
		  SQLiteDatabase db = this.getWritableDatabase();
		  try{
			  sList.clear();
			
			// Select All Query
			  String selectQuery = "SELECT * FROM " + TABLE_SERVER;
			  
			  Cursor cursor = db.rawQuery(selectQuery, null);

			 if( cursor.moveToFirst()){
					 do{
						 Server s = new Server(cursor.getInt(1), cursor.getString(2), 
								 cursor.getString(3), cursor.getString(4), cursor.getString(5));
						 
						 sList.add(s);
					 }while(cursor.moveToNext());
			 }
			  cursor.close();
			  db.close();
			  return sList;
		  }catch (Exception e){
			  Log.e("all_passwords", ""+e);                                                                                                       
		  }
		  return sList;
	  }
	  
	  public void deleteServer(Server s){ //TODO passw auf allen Servern löschen oder nur auf einem?
		  SQLiteDatabase db = this.getWritableDatabase();
		  db.delete(TABLE_SERVER, SERVER_ID + " = "+ s.getId(), null); 
		  db.close();
	  }
	  
	// Updating single contact
	  public int Update_Server(Server s) {
		  SQLiteDatabase db = this.getWritableDatabase();

		  ContentValues values = new ContentValues();

		  values.put(SERVER_ID, s.getId());
		  values.put(SERVER_NAME, s.getName());
		  values.put(SERVER_URL, s.getUrl());
		  values.put(LOGIN, s.getEncryptedLogin());
		  values.put(PASSWORD, s.getEncryptedPW());

		  // updating row

		  return db.update(TABLE_SERVER, values,  SERVER_ID + " = " + s.getId(),
				  	null);
	  }
	  
}
