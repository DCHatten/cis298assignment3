package edu.kvcc.cis298.cis298assignment3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.kvcc.cis298.cis298assignment3.database.WineDbSchema.WineTable;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WineListHelper extends SQLiteOpenHelper {

    //Version number that can be used to trigger a call to onUpgrade.
    //If when the application starts, the existing database version number
    //does not match the version number in the code, the onUpgrade method
    //will be called.
    private static final int VERSION = 1;
    //Constant for the database name
    private static final String DATABASE_NAME = "wineBase.db";

    public WineListHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //onCreate method that will be called to create the database
    //if it does not exist
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + WineTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                WineTable.Cols.UUID + ", " +
                WineTable.Cols.DESCRIPTION + ", " +
                WineTable.Cols.CASESIZE + ", " +
                WineTable.Cols.PRICE + ", " +
                WineTable.Cols.ACTIVE +
                ")"
        );
    }

    //onUpgrade method that will be called if the version number
    //listed above does not match the version number of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Going to leave this blank. We won't do work here.
    }
}
