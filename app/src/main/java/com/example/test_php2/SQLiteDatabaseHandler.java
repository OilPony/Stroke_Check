package com.example.test_php2;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StrokeDatabase";
    private static final String TABLE_NAME = "Smiles";
    private static final String KEY_ID = "id";
    private static final String KEY_SM1 = "sm1";
    private static final String[] COLUMNS = { KEY_ID, KEY_SM1};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Smiles ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "sm1 TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Smiles smile) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(smile.getId()) });
        db.close();
    }

    public Smiles getSmile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Smiles smile = new Smiles();
        smile.setId(Integer.parseInt(cursor.getString(0)));
        smile.setSm1(cursor.getInt(1));

        return smile;
    }

    public List<Smiles> allSmiles() {

        List<Smiles> smiles = new LinkedList<Smiles>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Smiles smile = null;

        if (cursor.moveToFirst()) {
            do {
                smile = new Smiles();
                smile.setId(Integer.parseInt(cursor.getString(0)));
                smile.setSm1(cursor.getInt(1));
                smiles.add(smile);
            } while (cursor.moveToNext());
        }

        return smiles;
    }

    public void addSmile(Smiles smile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SM1, smile.getSm1());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updatePlayer(Smiles smile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SM1, smile.getSm1());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(smile.getId()) });

        db.close();

        return i;
    }

}