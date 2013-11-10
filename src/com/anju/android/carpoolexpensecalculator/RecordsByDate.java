package com.anju.android.carpoolexpensecalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TableLayout;

public class RecordsByDate extends Activity {
	FeedReaderDbHelper mDbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_records_by_date);
        displayAllRecords();
    }

    private void displayAllRecords() {
		// TODO Auto-generated method stub
    	List<Record> allRecords = getRecords();
    	//Iterator<Record> it = allRecords.iterator();
    	//while(it.hasNext()){
    		System.out.println("Number of records::"+allRecords.size());
    	//}
//    	TableLayout table = (TableLayout) RecordsByDate.this.findViewById(R.id.HeaderTable);
//    	table.removeAllViews();
//		for(Record record : allRecords){
//			 
//		}
	}

	public List<Record> getRecords() {
		// TODO Auto-generated method stub
    	List<Record> recordList = new ArrayList<Record>();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, new String[]{FeedReaderContract.FeedEntry.COLUMN_NAME_DATE,FeedReaderContract.FeedEntry.COLUMN_NAME_PEOPLE,
				FeedReaderContract.FeedEntry.COLUMN_NAME_MILES,FeedReaderContract.FeedEntry.COLUMN_NAME_EXPENSE}, null, null, null, null, FeedReaderContract.FeedEntry.COLUMN_NAME_DATE);
	    if(cursor.moveToFirst()){
	    	Record record = new Record();
	    	record.set_id(Integer.parseInt(cursor.getString(0)));
	    	record.setDate(cursor.getString(1));
	    	record.setExpense(Double.parseDouble(cursor.getString(2)));
	    	record.setMiles(Double.parseDouble(cursor.getString(3)));
	    	record.setPeople(Integer.parseInt(cursor.getString(4)));
	    	recordList.add(record);
	    }while (cursor.moveToNext());
	    return recordList;
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_records_by_date, menu);
        return true;
    }
}
