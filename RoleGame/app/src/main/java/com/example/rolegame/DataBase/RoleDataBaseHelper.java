package com.example.rolegame.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class RoleDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Roles.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_role";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "role_name";
    private static final String COLUMN_TEAM = "role_team";
    private static final String COLUMN_ABILITY = "role_abilities";
    private static final String COLUMN_MECHANICS = "role_mechanics";
    private static final String COLUMN_IMAGE = "role_image";
    private static final String COLUMN_DESCRIPTION = "role_description";
    private static final String COLUMN_CHOSEN = "is_chosen";

    public RoleDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Creates the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TEAM + " TEXT, " +
                COLUMN_ABILITY + " TEXT, " +
                COLUMN_MECHANICS + " TEXT, " +
                COLUMN_IMAGE + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CHOSEN + " BOOL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //this function adds a new row to the table
    public void addRole(String name, int team, String ability, String mechanic ,int image , boolean isChosen)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TEAM, team);
        cv.put(COLUMN_ABILITY, ability);
        cv.put(COLUMN_MECHANICS, mechanic);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_DESCRIPTION,"no description");
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

    //updates the column IsChosen in the chosen row
    public void updateIsChosen (String id, boolean isChosen)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CHOSEN, isChosen);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {id});
    }

    //Updates the data in the description row.
    public void updateDescription (String id, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {id});
    }

    //Updates the data in the mechanics row.
    public void updateMechanics (String id, String Mechanics)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MECHANICS, Mechanics);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {id});
    }
}
