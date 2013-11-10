package com.anju.android.carpoolexpensecalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FeedReaderDbHelper  extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "carpool.db";
	private static final int DATABASE_VERSION = 2;

	 
	 
	public FeedReaderDbHelper (Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
		 }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(FeedReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
