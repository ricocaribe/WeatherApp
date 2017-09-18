package com.jmricop.weatherapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecentSearchesDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recent_searches.db";
    private static final String SEARCHES_TABLE_NAME = "searches";
    private static final String SEARCH_COLUMN_NAME = "name";

    public RecentSearchesDB(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SEARCHES_TABLE_NAME + " (id integer primary key autoincrement, name text unique)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SEARCHES_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertSearch (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SEARCH_COLUMN_NAME, name);
        db.insertWithOnConflict(SEARCHES_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        return true;
    }


    public String[] getRecentSearches() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SEARCHES_TABLE_NAME, null );
        String[] recentSearches = new String[res.getCount()];

        res.moveToLast();

        int index = 0;
        while(!res.isBeforeFirst()){
            recentSearches[index] = res.getString(res.getColumnIndex(SEARCH_COLUMN_NAME));
            res.moveToPrevious();
            index++;
        }
        res.close();
        return recentSearches;
    }
}
