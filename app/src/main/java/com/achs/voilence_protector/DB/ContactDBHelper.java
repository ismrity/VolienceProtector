package com.achs.voilence_protector.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.achs.voilence_protector.util.Contact;

import java.util.ArrayList;

public class ContactDBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contactSave";
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_PHONE + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
// Creating tables again
        onCreate(db);
    }

    public long addContact(String name, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE, mobile);

// Inserting Row
        long val = db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return val;
    }

    public int getContactCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

// return count
        return cursor.getCount();

    }



    public ArrayList<Contact> getAllContactString() {
        ArrayList<Contact> contactList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                contactList.add(new Contact(name,phone));


            } while (cursor.moveToNext());
        }

// return contact list
        return contactList;
    }

    public ArrayList<Contact> getPhoneContact() {
        ArrayList<Contact> phoneList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String phone = cursor.getString(2);
                phoneList.add(new Contact(phone));

            } while (cursor.moveToNext());
        }

// return contact list
        return phoneList;
    }

    public ArrayList<Contact> getUser() {
        ArrayList<Contact> userList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String user = cursor.getString(1);
                userList.add(new Contact(user));

            } while (cursor.moveToNext());
        }

// return contact list
        return userList;
    }

}
