package com.achs.voilence_protector.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private CountriesDBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new CountriesDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(CountriesDBHelper.SUBJECT, name);
        contentValue.put(CountriesDBHelper.DESC, desc);
        database.insert(CountriesDBHelper.TABLE_NAME, null, contentValue);
    }


    public int getContactCount() {
        String countQuery = "SELECT * FROM " + CountriesDBHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);

// return count
        return cursor.getCount();

    }

    public Cursor fetch() {
        String[] columns = new String[] { CountriesDBHelper._ID, CountriesDBHelper.SUBJECT, CountriesDBHelper.DESC };
        Cursor cursor = database.query(CountriesDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CountriesDBHelper.SUBJECT, name);
        contentValues.put(CountriesDBHelper.DESC, desc);
        int i = database.update(CountriesDBHelper.TABLE_NAME, contentValues, CountriesDBHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(CountriesDBHelper.TABLE_NAME, CountriesDBHelper._ID + "=" + _id, null);
    }

}

