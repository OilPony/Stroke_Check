package com.example.test_php2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper2  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "avg.db";


    private static final String SMILE_TABLE = "smile";
    private static final String ARM_TABLE = "arm";
    private static final String SOUND_TABLE = "sound";

    private static final String COL_ID = "_id";
    private static final String COL_NAME = "name";
    private static final String COL_DIST = "dist";


    private static final String SQL_CREATE_TABLE_SMILE
            = "CREATE TABLE " + SMILE_TABLE + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT,"
            + "dist DOUBLE)";

    private static final String SQL_CREATE_TABLE_ARM
            = "CREATE TABLE " + ARM_TABLE + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT,"
            + "dist DOUBLE)";

    private static final String SQL_CREATE_TABLE_SOUND
            = "CREATE TABLE " + SOUND_TABLE + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT,"
            + "dist DOUBLE)";



    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL("DROP TABLE IF EXISTS " + SMILE_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + ARM_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + SOUND_TABLE);
//        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SMILE);
        ContentValues sm = new ContentValues();
        sm.put(COL_DIST, 2.18);
        sm.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_SMILE, null, sm);

        sm = new ContentValues();
        sm.put(COL_DIST, 2.15);
        sm.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_SMILE, null, sm);

        sm = new ContentValues();
        sm.put(COL_DIST, 0.05);
        sm.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_SMILE, null, sm);

        sm = new ContentValues();
        sm.put(COL_DIST, 7.48);
        sm.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_SMILE, null, sm);

        sm = new ContentValues();
        sm.put(COL_DIST, 2.00);
        sm.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_SMILE, null, sm);
        //-------------------------------------------------------------

        db.execSQL(SQL_CREATE_TABLE_ARM);
        ContentValues am = new ContentValues();
        am.put(COL_DIST, 35.00);
        am.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_ARM, null, am);

        am = new ContentValues();
        am.put(COL_DIST, 30.00);
        am.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_ARM, null, am);

        am = new ContentValues();
        am.put(COL_DIST, 32.00);
        am.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_ARM, null, am);

        am = new ContentValues();
        am.put(COL_DIST, 33.00);
        am.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_ARM, null, am);

        am = new ContentValues();
        am.put(COL_DIST, 36.00);
        am.put(COL_NAME, "yuriyuripps");
        db.insert(SQL_CREATE_TABLE_ARM, null,am);
        //-------------------------------------------------------------

        db.execSQL(SQL_CREATE_TABLE_SOUND);
        ContentValues cv = new ContentValues();
        cv.put(COL_DIST, 123.25);
        cv.put(COL_NAME, "yuriyuripps");
        db.insert(SOUND_TABLE, null, cv);

        cv = new ContentValues();
        cv.put(COL_DIST, 124.25);
        cv.put(COL_NAME, "yuriyuripps");
        db.insert(SOUND_TABLE, null, cv);

        cv = new ContentValues();
        cv.put(COL_DIST, 128.21);
        cv.put(COL_NAME, "yuriyuripps");
        db.insert(SOUND_TABLE, null, cv);

        cv = new ContentValues();
        cv.put(COL_DIST, 132.24);
        cv.put(COL_NAME, "yuriyuripps");
        db.insert(SOUND_TABLE, null, cv);

        cv = new ContentValues();
        cv.put(COL_DIST, 129.54);
        cv.put(COL_NAME, "yuriyuripps");
        db.insert(SOUND_TABLE, null, cv);







    }



    public void updateDistRc(double dist , String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DIST, dist);
        db.insert(SOUND_TABLE, null, values);
        db.close();

    }

    public void updateDistSm(double dist , String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DIST, dist);
        values.put(COL_NAME, name);
        db.insert(SMILE_TABLE, null, values);
        db.close();


    }

    public void updateDistArm(double dist , String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DIST, dist);
        db.insert(ARM_TABLE, null, values);
        db.close();
    }

    public boolean checkRc(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        name = "'" + name + "'";
        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + SOUND_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();



            if (cursorCount > 3) {
                return true;
            } else {
                return false;
            }
    }

    public boolean checkArm(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        name = "'" + name + "'";
        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + ARM_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

            if (cursorCount > 3) {
                return true;
            } else {
                return false;
            }


    }

    public boolean checkSm(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        name = "'" + name + "'";
        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + SMILE_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();


            if (cursorCount > 3) {
                return true;
            } else {
                return false;
            }




    }

    public double avgSound(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        name = "'" + name + "'";
        double sum = 0;
        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + SOUND_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        for(int i =0; i<cursor.getCount(); ++i){
            cursor.moveToPosition(i);
            double cr = cursor.getDouble(cursor.getColumnIndex(COL_DIST));

            sum += cr;

        }

        cursor.close();
        db.close();

        return sum/(cursor.getCount());
    }

    public double avgSmile(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        name = "'" + name + "'";
        double sum = 0;
        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + SMILE_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        for(int i =0; i<cursor.getCount(); ++i){
            cursor.moveToPosition(i);
            double cr = cursor.getDouble(cursor.getColumnIndex(COL_DIST));

            sum += cr;

        }

        cursor.close();
        db.close();

        return sum/cursor.getColumnCount();
    }

    public double avgArm(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        name = "'" + name + "'";
        double sum = 0;
        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + ARM_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        for(int i =0; i<cursor.getCount(); ++i){
            cursor.moveToPosition(i);
            double cr = cursor.getDouble(cursor.getColumnIndex(COL_DIST));

            sum += cr;

        }



        return sum/cursor.getCount();
    }

    public String check(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        name = "'" + name + "'";

        Cursor cursor = db.rawQuery("SELECT " + COL_DIST
                + " FROM " + SOUND_TABLE + " WHERE " + COL_NAME + "= " + name , null);
        String cursorCount = String.valueOf(cursor.getCount());
        cursor.close();
        db.close();

        return cursorCount;




    }


}
