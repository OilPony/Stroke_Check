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
    private static final String DATABASE_NAME = "StrokeDB";
    private static final String TABLE_NAME = "Strokes";
    private static final String KEY_ID = "id";
    private static final String KEY_SM1 = "sm1";
    private static final String KEY_SM2 = "sm2";
    private static final String[] COLUMNS = { KEY_ID, KEY_SM1, KEY_SM2};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Strokes ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "sm1 TEXT, "
                + "sm2 TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Stroke stroke) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(stroke.getId()) });
        db.close();
    }

    public Stroke getStroke(int id) {
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

        Stroke stroke = new Stroke();
        stroke.setId(Integer.parseInt(cursor.getString(0)));
        stroke.setSm1(cursor.getInt(1));
        stroke.setSm2(cursor.getInt(2));

        return stroke;
    }

    public List<Stroke> allStrokes() {

        List<Stroke> strokes = new LinkedList<Stroke>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Stroke stroke = null;

        if (cursor.moveToFirst()) {
            do {
                stroke = new Stroke();
                stroke.setId(Integer.parseInt(cursor.getString(0)));
                stroke.setSm1(cursor.getInt(1));
                stroke.setSm2(cursor.getInt(2));
                strokes.add(stroke);
            } while (cursor.moveToNext());
        }

        return strokes;
    }

    public void addStroke(Stroke stroke) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SM1, stroke.getSm1());
        values.put(KEY_SM2, stroke.getSm2());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updatePlayer(Stroke stroke) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SM1, stroke.getSm1());
        values.put(KEY_SM2, stroke.getSm2());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(stroke.getId()) });

        db.close();

        return i;
    }

}