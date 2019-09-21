package com.achs.voilence_protector.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SosDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SOS";
    // Contact2 table name
    private static final String TABLE_CONTACT = "contact";
    private static final String TABLE_PROFILE = "profile";
    // contact2 Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public static final String KEY_PR_ID="id";
    public static final String KEY_PR_NAME="name";
    public static final String KEY_PR_PHONE="phone";

    // Reflection of class to call its instance
    public SosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // For example: String concationation
        /*String a = "Smriti"+" Duwadi";
        String query = "CREATE TABLE USER (ID INTEGER, USERNAME TEXT)";*/

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
        + KEY_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
// Creating tables again
        onCreate(db);
    }

    // Adding new contact
    public void addContact(String Name, String Phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name); // Contact Name
        values.put(KEY_PHONE, Phone); //  Phone Number
// Inserting Row
        db.insert(TABLE_CONTACT, null, values);
        db.close(); // Closing database connection
    }
}