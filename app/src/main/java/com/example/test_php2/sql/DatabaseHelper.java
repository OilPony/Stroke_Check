package com.example.test_php2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test_php2.model.User;

/**
 * Created by delaroy on 3/27/17.
 */
public class DatabaseHelper  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UserManager.db";

    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String TABLE_RECENT = "recent";
    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_NAME = "user_name";

    private static final String TABLE_NGROK = "ng";
    private static final String COLUMN_NG_ID = "ng_id";
    private static final String COLUMN_NG_PATH = "ng_path";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;



//    private static final String TABLE_RECENT = "recent";
//    private static final String COLUMN_ID = "user_id";
//    private static final String COLUMN_NAME = "user_name";

    private String CREATE_RECENT_TABLE = "CREATE TABLE " + TABLE_RECENT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT" + ")";

    private String DROP_RECENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECENT;


    private String CREATE_NGROK_TABLE = "CREATE TABLE " + TABLE_NGROK + "("
            + COLUMN_NG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NG_PATH + " TEXT" + ")";

    private String DROP_NGROK_TABLE = "DROP TABLE IF EXISTS " + TABLE_NGROK;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RECENT_TABLE);
        db.execSQL(CREATE_NGROK_TABLE);
    }



    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_RECENT_TABLE);
        db.execSQL(DROP_NGROK_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void add(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        db.insert(TABLE_RECENT, null, values);
        db.close();
    }

    public void addNg(String ng){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NG_PATH, ng);
        db.insert(TABLE_NGROK, null, values);
        db.close();
    }

    public String getName(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME
                + " FROM " + TABLE_RECENT  , null);
//        cursor.moveToPosition(1);
        cursor.moveToLast();
        String cr = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        return cr;
    }

    public String getNg(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NG_PATH
                + " FROM " + TABLE_NGROK  , null);
        cursor.moveToLast();
        String cr = cursor.getString(cursor.getColumnIndex(COLUMN_NG_PATH));
        return cr;
    }


    public void updatePassword(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, password);
        db.update(TABLE_USER, values, COLUMN_USER_NAME+" = ?",new String[] { name });
        db.close();
    }

    public boolean checkUser(String name){
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser(String name, String password){
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = { name, password };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }
}
