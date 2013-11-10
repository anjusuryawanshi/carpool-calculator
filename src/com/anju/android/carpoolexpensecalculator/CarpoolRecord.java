package com.anju.android.carpoolexpensecalculator;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.content.Intent;

public class CarpoolRecord extends Activity {
	
	private EditText startDate;
	private EditText endDate;
	private Button btnDisplay;
	private int mYearStart;
	private int mMonthStart;
	private int mDayStart;
	
	private int mYearEnd;
	private int mMonthEnd;
	private int mDayEnd;
	
	static final int DATE_DIALOG_ID1 = 998;
	
	static final int DATE_DIALOG_ID2 = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_record);
        
        startDate = (EditText) findViewById(R.id.txtStartDate);
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					showDialog(DATE_DIALOG_ID1);
				}
			}
		});
        
        
        final Calendar c = Calendar.getInstance();
        mYearStart = c.get(Calendar.YEAR);
        mMonthStart = c.get(Calendar.MONTH);
        mDayStart = c.get(Calendar.DAY_OF_MONTH);
        
        updateStartDate();
        
        endDate = (EditText) findViewById(R.id.txtEndDate);
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
				showDialog(DATE_DIALOG_ID2);}
			}
		});
        
        final Calendar c1 = Calendar.getInstance();
        mYearEnd = c1.get(Calendar.YEAR);
        mMonthEnd = c1.get(Calendar.MONTH);
        mDayEnd = c1.get(Calendar.DAY_OF_MONTH);
        updateEndDate();
        
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        OnClickListener mClickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.btnDisplay){
					DisplayByDate();
				}
				
			}
        	
        };
        btnDisplay.setOnClickListener(mClickListener);
                
    }

    protected void DisplayByDate() {
		// TODO Auto-generated method stub
    	Intent intent = new Intent(this, RecordsByDate.class);
    	startActivity(intent);
	}

	private void updateStartDate() {
		// TODO Auto-generated method stub
    	startDate.setText(new StringBuilder().append(mMonthStart + 1).append("-")
    			.append(mDayStart).append("-")
    			.append(mYearStart).append(" "));
    	
    	
    
	}
    private void updateEndDate(){
    	endDate.setText(new StringBuilder().append(mMonthEnd + 1).append("-")
    			.append(mDayEnd).append("-")
    			.append(mYearEnd).append(" "));
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			mYearStart = year;
			mMonthStart = monthOfYear;
			mDayStart = dayOfMonth;
			updateStartDate();
			
		}
	};
private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			mYearEnd = year;
			mMonthEnd = monthOfYear;
			mDayEnd = dayOfMonth;
			updateEndDate();
			
		}
	};
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID1:
            return new DatePickerDialog(this,
                        mDateSetListener1,
                        mYearStart, mMonthStart, mDayStart);
        case DATE_DIALOG_ID2:
            return new DatePickerDialog(this,
                        mDateSetListener2,
                        mYearEnd, mMonthEnd, mDayEnd);
        }
        return null;
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_carpool_record, menu);
        return true;
    }
}
