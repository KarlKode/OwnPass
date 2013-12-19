package ch.ethz.inf.vs.android.gamarc.ownpass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "servers.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_SERVER = "servers";

    private static final String FIELD_SERVER_ID = "id";
    private static final String FIELD_SERVER_NAME = "name";
    private static final String FIELD_SERVER_URL = "url";
    private static final String FIELD_SERVER_USERNAME = "username";
    private static final String FIELD_SERVER_PASSWORD = "password";
    private static final String QUERY_CREATE_SERVER = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL" +
            ")", TABLE_SERVER, FIELD_SERVER_ID, FIELD_SERVER_NAME, FIELD_SERVER_URL, FIELD_SERVER_USERNAME,
            FIELD_SERVER_PASSWORD);

    private static final String TABLE_PASSWORD = "passwords";
    private static final String FIELD_PASSWORD_ID = "id";
    private static final String FIELD_PASSWORD_SERVER_ID = "server_id";
    private static final String FIELD_PASSWORD_SERVER_PASSWORD_ID = "server_password_id";
    private static final String FIELD_PASSWORD_TITLE = "title";
    private static final String FIELD_PASSWORD_URL = "url";
    private static final String FIELD_PASSWORD_USERNAME = "username";
    private static final String FIELD_PASSWORD_PASSWORD = "password";
    private static final String QUERY_CREATE_PASSWORD = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER KEY," +
                    "%s INTEGER KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s BLOB NOT NULL," +
                    "%s BLOB NOT NULL" +
            ")", TABLE_PASSWORD, FIELD_PASSWORD_ID, FIELD_PASSWORD_SERVER_ID, FIELD_PASSWORD_SERVER_PASSWORD_ID,
            FIELD_PASSWORD_TITLE, FIELD_PASSWORD_URL, FIELD_PASSWORD_USERNAME, FIELD_PASSWORD_PASSWORD);

    /* TODO */
    private static final String TABLE_UPDATE = "updates";
    private static final String FIELD_UPDATE_ID = "id";
    private static final String FIELD_UPDATE_TYPE = "type";
    private static final String FIELD_UPDATE_PASSWORD_ID = "password_id";
    private static final String FIELD_UPDATE_PASSWORD_NAME = "name";
    private static final String FIELD_UPDATE_PASSWORD_URL = "url";
    private static final String FIELD_UPDATE_PASSWORD_USERNAME = "username";
    private static final String FIELD_UPDATE_PASSWORD_PASSWORD = "password";
    private static final String QUERY_CREATE_UPDATES ="";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(QUERY_CREATE_SERVER);
        database.execSQL(QUERY_CREATE_PASSWORD);
        System.out.println(QUERY_CREATE_PASSWORD);
        // TODO Updates
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);
        // TODO Updates
        onCreate(database);
    }

    // Server stuff
    public Server getServer(long id) {
        SQLiteDatabase database = getReadableDatabase();

        // Query for the server with the given id
        Server server = null;
        Cursor cursor = database.query(TABLE_SERVER,
                new String[] {FIELD_SERVER_NAME, FIELD_SERVER_URL, FIELD_SERVER_USERNAME, FIELD_SERVER_PASSWORD},
                String.format("%s=?", FIELD_SERVER_ID),
                new String[] {String.valueOf(id)},
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_NAME));
            String url = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_URL));
            String username = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_PASSWORD));
            server = new Server(id, name, url, username, password);
        }

        // Cleanup
        cursor.close();
        database.close();

        return server;
    }

    public List<Server> getServers() {
        SQLiteDatabase database = getReadableDatabase();

        // Query all servers
        ArrayList<Server> servers = new ArrayList<Server>();
        Cursor cursor = database.query(TABLE_SERVER,
                new String[] {FIELD_SERVER_ID, FIELD_SERVER_NAME, FIELD_SERVER_URL, FIELD_SERVER_USERNAME,
                        FIELD_SERVER_PASSWORD},
                null,
                null,
                null,
                null,
                FIELD_SERVER_ID);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(FIELD_SERVER_ID));
                String name = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_NAME));
                String url = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_URL));
                String username = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(FIELD_SERVER_PASSWORD));
                servers.add(new Server(id, name, url, username, password));
            } while (cursor.moveToNext());
        }

        // Cleanup
        cursor.close();
        database.close();

        return servers;
    }

    public void addServer(Server server) {
        SQLiteDatabase database = getWritableDatabase();

        // Insert a new server with the given values
        ContentValues newServer = new ContentValues();
        newServer.put(FIELD_SERVER_NAME, server.getName());
        newServer.put(FIELD_SERVER_URL, server.getUrl());
        newServer.put(FIELD_SERVER_USERNAME, server.getUsername());
        newServer.put(FIELD_SERVER_PASSWORD, server.getPassword());

        long newServerId = database.insert(TABLE_SERVER, null, newServer);
        if (newServerId == -1) {
            // TODO error
        } else {
            server.setId(newServerId);
        }

        // Cleanup
        database.close();
    }

    public void updateServer(long id, String name, String url, String username, String password) {
        SQLiteDatabase database = getWritableDatabase();

        // Update the server with the given id and set the new values
        ContentValues updatedServer = new ContentValues();
        updatedServer.put(FIELD_SERVER_ID, id);
        updatedServer.put(FIELD_SERVER_NAME, name);
        updatedServer.put(FIELD_SERVER_URL, url);
        updatedServer.put(FIELD_SERVER_USERNAME, username);
        updatedServer.put(FIELD_SERVER_PASSWORD, password);

        database.update(TABLE_SERVER, updatedServer, String.format("%s=?", FIELD_SERVER_ID), new String[] {String.valueOf(id)});

        // Cleanup
        database.close();
    }

    public void removeServer(long id) {
        SQLiteDatabase database = getWritableDatabase();

        // Remove server with the given id
        database.delete(TABLE_SERVER, String.format("%s=?", FIELD_SERVER_ID), new String[]{String.valueOf(id)});

        // Cleanup
        database.close();
    }

    // Password stuff
    public Password getPassword(long serverId, long passwordId) {
        return null;
    }

    public List<Password> getPasswords(long serverId) {
        SQLiteDatabase database = getReadableDatabase();

        // Query all passwords
        ArrayList<Password> passwords = new ArrayList<Password>();
        Cursor cursor = database.query(TABLE_PASSWORD,
                new String[] {FIELD_PASSWORD_ID, FIELD_PASSWORD_SERVER_ID, FIELD_PASSWORD_SERVER_PASSWORD_ID,
                        FIELD_PASSWORD_TITLE, FIELD_PASSWORD_URL, FIELD_PASSWORD_USERNAME, FIELD_SERVER_PASSWORD},
                null,
                null,
                null,
                null,
                FIELD_PASSWORD_ID);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(FIELD_PASSWORD_ID));
                Server server = getServer(cursor.getLong(cursor.getColumnIndex(FIELD_PASSWORD_SERVER_ID)));
                long server_id = cursor.getLong(cursor.getColumnIndex(FIELD_PASSWORD_SERVER_PASSWORD_ID));
                String title = cursor.getString(cursor.getColumnIndex(FIELD_PASSWORD_TITLE));
                String url = cursor.getString(cursor.getColumnIndex(FIELD_PASSWORD_URL));
                byte[] username = cursor.getBlob(cursor.getColumnIndex(FIELD_PASSWORD_USERNAME));
                byte[] password = cursor.getBlob(cursor.getColumnIndex(FIELD_PASSWORD_PASSWORD));
                passwords.add(new Password(id, server, server_id, title, url, username, password));
            } while (cursor.moveToNext());
        }

        // Cleanup
        cursor.close();
        database.close();

        return passwords;
    }

    public void addPassword(Password password) {
    	SQLiteDatabase database = getWritableDatabase();
    
    	ContentValues newPassword = new ContentValues();
        newPassword.put(FIELD_PASSWORD_SERVER_ID, password.getServer().getId());
        newPassword.put(FIELD_PASSWORD_SERVER_PASSWORD_ID, password.getServerId());		
        newPassword.put(FIELD_PASSWORD_TITLE, password.getTitle());
        newPassword.put(FIELD_PASSWORD_URL, password.getUrl());
        newPassword.put(FIELD_PASSWORD_USERNAME, password.getUsername());
        newPassword.put(FIELD_SERVER_PASSWORD, password.getPassword());

        long newServerId = database.insert(TABLE_PASSWORD, null, newPassword);
        if (newServerId == -1) {
            // TODO error
        } else {
            password.setId(newServerId);
        }

        // Cleanup
        database.close();
    }

    public void updatePassword(Password password) {
    	SQLiteDatabase database = getWritableDatabase();
    	
        // Update the server with the given id and set the new values
        ContentValues updatedPassword = new ContentValues();
        updatedPassword.put(FIELD_PASSWORD_SERVER_ID, password.getServer().getId());
        updatedPassword.put(FIELD_PASSWORD_SERVER_PASSWORD_ID, password.getServerId());		
        updatedPassword.put(FIELD_PASSWORD_TITLE, password.getTitle());
        updatedPassword.put(FIELD_PASSWORD_URL, password.getUrl());
        updatedPassword.put(FIELD_PASSWORD_USERNAME, password.getUsername());
        updatedPassword.put(FIELD_SERVER_PASSWORD, password.getPassword());

        database.update(TABLE_SERVER, updatedPassword, String.format("%s=?", FIELD_SERVER_ID), new String[] {String.valueOf(password.getId())});

        // Cleanup
        database.close();
    }

    public void removePassword(Password password) {
        SQLiteDatabase database = getWritableDatabase();

        // Remove server with the given id
        database.delete(TABLE_PASSWORD, String.format("%s=?", FIELD_SERVER_ID), new String[]{String.valueOf(password.getId())});

        // Cleanup
        database.close();
    }

//	public void clearPasswords() {
//		SQLiteDatabase database = getWritableDatabase();
//        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);
//        database.execSQL(QUERY_CREATE_PASSWORD);
//	}
}
