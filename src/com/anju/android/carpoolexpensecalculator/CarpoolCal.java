package com.anju.android.carpoolexpensecalculator;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CarpoolCal extends Activity {	
	//Get all the widgets	
	private EditText txtMiles;
	private EditText txtPeople;
	private EditText txtGasPrice;
	private EditText txtCarMileage;
	private EditText txtToll;	
	private Button btnCalculate;
	private Button btnReset;
	private Button btnSave;
	private Button btnDelete;
	private Button btnSearch;	
	private TextView txtTotalGasBurnt;
	private TextView txtTotalCommuteExpense;
	private TextView txtIndividual;	
	double tollCharge = 0.00;
	double totalMiles = 0.00;
	int totalPeople = 0;
	double gasPrice = 0.00;
	double carMileage = 0.00;
	
	FeedReaderDbHelper mDbHelper;
	long newRowId = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_cal);
        mDbHelper = new FeedReaderDbHelper(getBaseContext());
        
        //Access all the values entered by the user and ids of other widgets
        txtMiles = (EditText) findViewById(R.id.txtMiles);
        txtPeople = (EditText) findViewById(R.id.txtPeople);
        txtGasPrice = (EditText) findViewById(R.id.txtGasPrice);
        txtCarMileage = (EditText) findViewById(R.id.txtCarMileage);
        txtToll = (EditText) findViewById(R.id.txtTollCharges);
        
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCalculate.setEnabled(false);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        
        txtTotalGasBurnt = (TextView) findViewById(R.id.txtTotalGasExpense);
        txtTotalCommuteExpense = (TextView) findViewById(R.id.txtTotalExpense);
        txtIndividual = (TextView) findViewById(R.id.txtExpensePerPerson);
        
        OnKeyListener mKeyListener = new OnKeyListener(){
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event){
				// TODO Auto-generated method stub
				switch (v.getId()){
				case R.id.txtMiles:
				case R.id.txtPeople:
				case R.id.txtGasPrice:
				case R.id.txtCarMileage:
					btnCalculate.setEnabled(txtMiles.getText().length()>0
											&& txtPeople.getText().length()>0
											&& txtGasPrice.getText().length()>0
											&& txtCarMileage.getText().length()>0);
					break;
				}
				return false;
		   }
        };
        txtMiles.setOnKeyListener(mKeyListener);
        txtPeople.setOnKeyListener(mKeyListener);
        txtGasPrice.setOnKeyListener(mKeyListener);
        txtCarMileage.setOnKeyListener(mKeyListener);
        txtToll.setOnKeyListener(mKeyListener);
        OnClickListener mClickListener = new OnClickListener(){
        	@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				if(v.getId()==R.id.btnCalculate){
					calculate();
				}
				else if(v.getId()==R.id.btnReset){
					reset();
				}
				else if(v.getId()==R.id.btnSave){
					newRowId = saveToDb();
				}
				else if(v.getId()==R.id.btnDelete){
					deleteFromDb(newRowId);
				}
				else if(v.getId()==R.id.btnSearch){
					searchDb(v);
				}
			}
		};
        btnCalculate.setOnClickListener(mClickListener);
        btnReset.setOnClickListener(mClickListener);
        btnSave.setOnClickListener(mClickListener);
        btnDelete.setOnClickListener(mClickListener);
        btnSearch.setOnClickListener(mClickListener);
    }

    protected void searchDb(View view) {
		// TODO Auto-generated method stub
    	Intent intent = new Intent(this, CarpoolRecord.class);
    	startActivity(intent);
	}

	protected void deleteFromDb(long newRowId) {
		// TODO Auto-generated method stub
    	SQLiteDatabase db = mDbHelper.getWritableDatabase();
    	String selection =  FeedReaderContract.FeedEntry._ID + " LIKE ?";
    	String[] selelectionArgs = { String.valueOf(newRowId) };
    	int result = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selelectionArgs);
    	System.out.println("Rows deleted::"+result);
    }

	protected long saveToDb() {
		// TODO Auto-generated method stub
		// Gets the data repository in write mode		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, Calendar.getInstance().toString());
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PEOPLE, Integer.parseInt(txtPeople.getText().toString()));
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MILES, Double.parseDouble(txtMiles.getText().toString()));
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_EXPENSE, Double.parseDouble(txtIndividual.getText().toString()));		
		newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
		if(newRowId == -1){
				System.out.println("Row did not get inserted");
		}
		else{
			System.out.println("Row inserted::"+newRowId);
		}
		return newRowId;
	}

	protected void reset() {
		// TODO Auto-generated method stub
    	txtMiles.setText("");
    	txtPeople.setText("");
    	txtGasPrice.setText("");
    	txtCarMileage.setText("");
    	txtToll.setText("");    	
    	txtTotalGasBurnt.setText("");
    	txtTotalCommuteExpense.setText("");
    	txtIndividual.setText("");
    	txtMiles.requestFocus();
    	btnCalculate.setEnabled(false);    	
	}

	protected void calculate(){
		// TODO Auto-generated method stub		
		boolean isError = false;			
		totalMiles = Double.parseDouble(txtMiles.getText().toString());
		totalPeople = Integer.parseInt(txtPeople.getText().toString());
		gasPrice = Double.parseDouble(txtGasPrice.getText().toString());
		carMileage = Double.parseDouble(txtCarMileage.getText().toString());
		if(txtToll.getText().length()>0){
		tollCharge = Double.parseDouble(txtToll.getText().toString());
		}			
		if(totalMiles == 0.0){
			showErrorAlert("Enter valid Miles",txtMiles.getId());
			isError = true;
		}
		if(totalPeople < 1){
			showErrorAlert("Enter valid number of People", txtPeople.getId());
		}
		if(gasPrice == 0.0){
			showErrorAlert("Enter valid Gas Price", txtGasPrice.getId());
			isError = true;
		}
		if(carMileage == 0.0){
			showErrorAlert("Enter valid Mileage", txtCarMileage.getId());
			isError = true;
		}
		DecimalFormat df = new DecimalFormat("###.##");
		if(!isError){		
		Double totalGasExpense = Double.parseDouble(df.format((totalMiles/carMileage)*gasPrice));		
		Double totalCommuteExpense = totalGasExpense + tollCharge;		
		Double indivCommuteExpense = Double.parseDouble(df.format(totalCommuteExpense/totalPeople));		
		txtTotalGasBurnt.setText(totalGasExpense.toString());
		txtTotalCommuteExpense.setText(totalCommuteExpense.toString());
		txtIndividual.setText(indivCommuteExpense.toString());
		}		
	}

	private void showErrorAlert(String errorMessage, final int fieldId){
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setTitle("Error")
		.setMessage(errorMessage).setNeutralButton("Close", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				// TODO Auto-generated method stub
				findViewById(fieldId).requestFocus();
			}
		}).show();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_carpool_cal, menu);
        return true;
    }
}
