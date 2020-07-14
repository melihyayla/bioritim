package com.melih.tabactionbar;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sqllite_database";//database adý

    private static final String TABLE_NAME = "userlist";
    private static String USER_NAME = "username";
    private static String USER_ID = "id";
    private static String USER_SURNAME = "surname";
    private static String USER_DOB = "dateofbirth";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_NAME + " TEXT,"
                + USER_SURNAME + " TEXT,"
                + USER_DOB + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void deleteUser(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, USER_ID + " = ?",
                new String[] { String.valueOf(id) });

        Log.i("DB-DELETE", "Deleted");

        db.close();
    }

    public void addUser(String username, String surname,String dateofbirth) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(USER_SURNAME, surname);
        values.put(USER_DOB, dateofbirth);

        db.insert(TABLE_NAME, null, values);
        Log.i("DB-ADD", "Added:" + username + " " + surname + " " + dateofbirth);
        db.close();
    }


    public HashMap<String, String> userDetail(int id){

        HashMap<String,String> users = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            users.put(USER_NAME, cursor.getString(1));
            users.put(USER_SURNAME, cursor.getString(2));
            users.put(USER_DOB, cursor.getString(3));

        }
        cursor.close();
        db.close();

        return users;
    }

    public  ArrayList<HashMap<String, String>> activeUsers(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> userlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                userlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();

        return userlist;
    }

    public void editUser(String username, String surname ,String dateofbirth ,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(USER_SURNAME, surname);
        values.put(USER_DOB, dateofbirth);

        db.update(TABLE_NAME, values, USER_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int getRowCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        return rowCount;
    }


    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}
