package com.mubeen.vanesa.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mubeen on 16/03/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static String DB_Name = "vanesa";
    private static String Table_name = "users";
    private static int database_version = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT= "created_at";

    public SQLiteHandler(Context context) {
        super(context, DB_Name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + Table_name + "( " +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_EMAIL + " TEXT UNIQUE, " +
                KEY_UID + " TEXT, " +
                KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public void addUserToDB(String name, String email, String Uid, String created_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_EMAIL,email);
        values.put(KEY_UID,Uid);
        values.put(KEY_CREATED_AT,created_at);
        long id = db.insert(Table_name,null,values);
        db.close();

        Log.d(TAG,"New user inserted in sqLite" + id);

    }

    public HashMap<String,String> getuserDetails(){

        HashMap<String,String> user = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_name;

        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if(cursor.getCount() >0)
        {
            user.put("name",cursor.getString(1));
            user.put("email",cursor.getString(2));
            user.put("uid",cursor.getString(3));
            user.put("created_at",cursor.getString(4));
        }
        cursor.close();
        db.close();
        Log.d(TAG,"Fetching user from SQlite:" + user.toString());
        return user;
    }

    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_name,null,null);
        db.close();
        Log.d(TAG, "All users deleted from SQLite");
    }

}
