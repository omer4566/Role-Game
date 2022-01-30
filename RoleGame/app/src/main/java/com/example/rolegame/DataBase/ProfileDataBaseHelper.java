package com.example.rolegame.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ProfileDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Profiles.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_profile";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "profile_name";
    private static final String COLUMN_IMAGE = "profile_image";
    private static final String COLUMN_CHOSEN = "profile_isChosen";

    public ProfileDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Creates the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_CHOSEN + " BOOL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Deletes a row from the table
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete context", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    //this function adds a new row to the table
    public void addProfile(String name, String imgURI, boolean isChosen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_IMAGE, imgURI);
        cv.put(COLUMN_CHOSEN, isChosen);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    //this function allows to you read the table and get the contents inside.
    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //updates the column IsChosen in the chosen row
    public void updateIsChosen (String id, boolean isChosen)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CHOSEN, isChosen);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }
    }

    //Updates the data in the chosen row.
    public void updateData(String id ,String name, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_IMAGE, image);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }
    }
}
