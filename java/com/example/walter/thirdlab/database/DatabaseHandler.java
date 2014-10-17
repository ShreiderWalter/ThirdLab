package com.example.walter.thirdlab.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.walter.thirdlab.core.PlanetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walter on 29.09.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;

    private final static String DATABASE_NAME = "solar_system_rating";
    private final static String TABLE_RATING = "contacts";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String RATE = "rate";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RATING + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + RATE + " INT"  + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        onCreate(db);
    }

    public void addPlanetItem(PlanetItem student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, student.getName());
        contentValues.put(RATE, student.getCurrentRate());
        db.insert(TABLE_RATING, null, contentValues);
        db.close();
    }

    public PlanetItem getPlanetItem(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RATING, new String[]{
                KEY_ID, KEY_NAME, RATE }, KEY_NAME + "=?",
                new String[]{name}, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        PlanetItem student = new PlanetItem(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getInt(2));
        return student;
    }

    public List<PlanetItem> getAllPlanetItems(){
        List<PlanetItem> studentsList = new ArrayList<PlanetItem>();
        String selectQuery = "SELECT * FROM " + TABLE_RATING;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                PlanetItem student = new PlanetItem();
                student.setId(cursor.getInt(0));
                student.setName(cursor.getString(1));
                student.setCurrentRate(cursor.getInt(2));
                studentsList.add(student);
            } while (cursor.moveToNext());
        }
        return studentsList;
    }

    public int updatePlanetItem(PlanetItem student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, student.getName());
        contentValues.put(RATE, student.getCurrentRate());

        return db.update(TABLE_RATING, contentValues, KEY_ID + " =?", new String[]{String.valueOf(student.getId())});
    }

    public void deletePlanetItem(PlanetItem student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RATING, KEY_ID + " =?", new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public int getPlanetItemsCount(){
        String countQuery = "SELECT * FROM " + TABLE_RATING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
