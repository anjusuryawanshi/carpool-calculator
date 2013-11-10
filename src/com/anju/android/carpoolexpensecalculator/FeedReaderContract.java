package com.anju.android.carpoolexpensecalculator;

import android.provider.BaseColumns;

public class FeedReaderContract {
	
	public static abstract class FeedEntry implements BaseColumns {
	    public static final String TABLE_NAME = "carpool";
	    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
	    public static final String COLUMN_NAME_DATE = "date";
	    public static final String COLUMN_NAME_PEOPLE = "people";
	    public static final String COLUMN_NAME_MILES = "miles";
	    public static final String COLUMN_NAME_EXPENSE = "expensePerPerson";
			   
	}
	
	// Prevents the FeedReaderContract class from being instantiated.
	private FeedReaderContract() {}
	
	public static final String SQL_CREATE_ENTRIES =
		"CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + "(" +
	     FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		 FeedReaderContract.FeedEntry.COLUMN_NAME_DATE + " TEXT" + "," +
		 FeedReaderContract.FeedEntry.COLUMN_NAME_PEOPLE + " INTEGER" + "," +
		 FeedReaderContract.FeedEntry.COLUMN_NAME_MILES + " REAL" + "," +
		 FeedReaderContract.FeedEntry.COLUMN_NAME_EXPENSE + " REAL" + " );"; 
	
	public static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
	
	public static final String SQL_SELECT_ALL = "SELECT "+FeedReaderContract.FeedEntry.COLUMN_NAME_DATE+", "+FeedReaderContract.FeedEntry.COLUMN_NAME_PEOPLE+","+
			FeedReaderContract.FeedEntry.COLUMN_NAME_MILES+", "+FeedReaderContract.FeedEntry.COLUMN_NAME_EXPENSE+" FROM "+FeedReaderContract.FeedEntry.TABLE_NAME;
			

}
