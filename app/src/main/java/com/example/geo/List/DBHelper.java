package com.example.geo.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private DBHelper DBHelper;
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Track.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TrackContract.TrackEntry.TABLE_NAME + " (" +
                TrackContract.TrackEntry.COLUMN_ID + " INTEGER NOT NULL," +
                TrackContract.TrackEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                TrackContract.TrackEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TrackContract.TrackEntry.COLUMN_WEB + " TEXT NOT NULL, " +
                TrackContract.TrackEntry.COLUMN_CATEGORY + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " +  TrackContract.TrackEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //---opens the database---
    public DBHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

        //Drop User Table if exist

        db1.execSQL(DROP_BENEFICIARY_TABLE);

        // Create tables again
        onCreate(db1);

    }


    //Method to create beneficiary records

    public void addItem(Trackable trackable) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( TrackContract.TrackEntry.COLUMN_ID, trackable.getId());
        values.put( TrackContract.TrackEntry.COLUMN_NAME, trackable.getName());
        values.put( TrackContract.TrackEntry.COLUMN_DESCRIPTION, trackable.getDesc());
        values.put( TrackContract.TrackEntry.COLUMN_WEB, trackable.getWebsite());
        values.put( TrackContract.TrackEntry.COLUMN_CATEGORY, trackable.getCategory());

        db.insert(TrackContract.TrackEntry.TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                TrackContract.TrackEntry.COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = TrackContract.TrackEntry.COLUMN_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TrackContract.TrackEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }





    public List<Trackable> getAllItem() {
        // array of columns to fetch
        String[] columns = {
                TrackContract.TrackEntry.COLUMN_ID,
                TrackContract.TrackEntry.COLUMN_NAME,
                TrackContract.TrackEntry.COLUMN_DESCRIPTION,
                TrackContract.TrackEntry.COLUMN_WEB,
                TrackContract.TrackEntry.COLUMN_CATEGORY
        };
        // sorting orders
        String sortOrder =
                TrackContract.TrackEntry.COLUMN_NAME + " ASC";
        List<Trackable>trackableList = new ArrayList<Trackable>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query( TrackContract.TrackEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Trackable trackable = new Trackable();
                trackable.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex( TrackContract.TrackEntry.COLUMN_ID))));
                trackable.setName(cursor.getString(cursor.getColumnIndex( TrackContract.TrackEntry.COLUMN_NAME)));
                trackable.setDesc(cursor.getString(cursor.getColumnIndex( TrackContract.TrackEntry.COLUMN_DESCRIPTION)));
                trackable.setWebsite(cursor.getString(cursor.getColumnIndex( TrackContract.TrackEntry.COLUMN_WEB)));
                trackable.setCategory(cursor.getString(cursor.getColumnIndex( TrackContract.TrackEntry.COLUMN_CATEGORY)));
                // Adding user record to list
                trackableList.add(trackable);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return trackableList;
    }

}
