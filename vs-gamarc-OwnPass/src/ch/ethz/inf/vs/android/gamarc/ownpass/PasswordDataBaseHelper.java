package ch.ethz.inf.vs.android.gamarc.ownpass;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PasswordDataBaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "passwords.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_Passwords = null;
	private static final String SERVER_ID = null;
	private static final String SITE_ID = null;
	private static final String SITEURL = null;
	private static final String SITENAME = null;
	private static final String LOGIN = null;
	private static final String PASSWORD = null;

	public PasswordDataBaseHelper(Context context) {
		 super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_Passwords + "(" + SERVER_ID
	      + " integer, "+ SITE_ID+ " integer primary key autoincrement, " +SITEURL + " text not null, "+ SITENAME + " text not null, "+ LOGIN + " text not null, "+ PASSWORD  +" text not null);";

	
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
	  
	  public void Add_Passw(Password passw){
		  SQLiteDatabase db = this.getWritableDatabase();
		  ContentValues values =new ContentValues();
		  //values.put(SERVER_ID, passw.getServerId());
		  values.put(SITENAME, passw.getSiteName());
		  values.put(SITEURL, passw.getUrl());
		  values.put(LOGIN, passw.getEncryptedLogin());
		  values.put(PASSWORD, passw.getEncryptedPW());
		  //insert
		  db.insert(TABLE_Passwords, null, values);
		  db.close();
	  }
	  
	  private ArrayList<Password> passwList = new ArrayList<Password>(); 
	  
	  public ArrayList<Password> get_All_Passwords(Server s){
		  int ServerID = s.getId();
		  SQLiteDatabase db = this.getWritableDatabase();
		  try{
			  passwList.clear();
			  Cursor cursor = db.query(TABLE_Passwords, new String[] {SITE_ID, SITENAME, SITEURL, LOGIN, PASSWORD}, 
					  SERVER_ID + " = ?", new String [] {String.valueOf(ServerID)}, null, null, null, null);
			  if(cursor != null)
				 if( cursor.moveToFirst()){
					 do{
						 Password pw = new Password(ServerID, 
								 cursor.getInt(1), cursor.getString(2), cursor.getString(3), 
								 cursor.getString(4), cursor.getString(5));
						 
						 passwList.add(pw);
					 }while(cursor.moveToNext());
				 }
			  cursor.close();
			  db.close();
			  return passwList;
		  }catch (Exception e){
			  Log.e("all_passwords", ""+e);                                                                                                       
		  }
		  return passwList;
	  }
	  
	  public ArrayList<Password> search(Server s, String text){
		  ArrayList<Password> searchList = new ArrayList<Password>(); 
		  for(Password pw : searchList){
			  if(pw.getSiteName().contains(text) || pw.getUrl().contains(text)){
				  searchList.add(pw);
			  }
		  }
		  return searchList;
	  }
	  
	  public void deletePW(Password pw){ //TODO passw auf allen Servern löschen oder nur auf einem?
		  SQLiteDatabase db = this.getWritableDatabase();
		  db.delete(TABLE_Passwords, SITE_ID + " = "+ pw.getSiteId(), null); 
		  db.close();
	  }
	  
	// Updating single contact
	  public int Update_Pw(Password pw) {
		  SQLiteDatabase db = this.getWritableDatabase();

		  ContentValues values = new ContentValues();

		  values.put(SERVER_ID, pw.getServerId());
		  values.put(SITENAME, pw.getSiteName());
		  values.put(SITEURL, pw.getUrl());
		  values.put(LOGIN, pw.getEncryptedLogin());
		  values.put(PASSWORD, pw.getEncryptedPW());

		  // updating row

		  return db.update(TABLE_Passwords, values,  SITE_ID + " = " + pw.getSiteId(),
				  	null);
	  }
	  
}
