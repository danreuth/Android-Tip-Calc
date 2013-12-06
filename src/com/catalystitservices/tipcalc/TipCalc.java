package com.catalystitservices.tipcalc;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class TipCalc extends Activity {

	
	// Constants used when saving and restoring
	     
		    private static final String TOTAL_BILL = "TOTAL_BILL";
		    private static final String CURRENT_TIP = "CURRENT_TIP";
		    private static final String BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP";
		     
		    private double billBeforeTip; // Users bill before tip
		    private double tipAmount; // Tip amount
		    private double finalBill; // Bill plus Tip
	
		    private EditText billBeforeTipET;
		    private EditText tipAmountET;
		    private EditText finalBillET;
		    
		    private SeekBar tipSeekBar;
		    
		    private int[] checklistValues = new int[12];

		    CheckBox friendlyCheckBox;
		    CheckBox specialsCheckBox;
		    CheckBox opinionCheckBox;
		    
		    RadioGroup availableRadioGroup;
		    RadioButton availableBadRadio;
		    RadioButton availableOKRadio;
		    RadioButton availableGoodRadio;

		    Spinner problemsSpinner;
		    
		    Button startChronometerButton;
		    Button pauseChronometerButton;
		    Button resetChronometerButton;
		    
		    Chronometer timeWaitingChronometer;
		    
		    long secondsYouWaited = 0;
		    
		    TextView timeWaitingTextView;






		    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);
		Debug.out("In onCreate");
		
		if(savedInstanceState == null){
			billBeforeTip = 0.0;
			tipAmount = .15;
			finalBill = 0.0;
				             
		} else {
			billBeforeTip = savedInstanceState.getDouble(BILL_WITHOUT_TIP);
			tipAmount = savedInstanceState.getDouble(CURRENT_TIP);
			finalBill = savedInstanceState.getDouble(TOTAL_BILL);
		}
		
		billBeforeTipET = (EditText) findViewById(R.id.billEditText);
	    tipAmountET = (EditText) findViewById(R.id.tipEditText);
	    finalBillET = (EditText) findViewById(R.id.finalBillEditText);
	    
	    billBeforeTipET.addTextChangedListener(billBeforeTipListener);
	    tipAmountET.addTextChangedListener(tipAmountListener);
	   
	    tipSeekBar = (SeekBar) findViewById(R.id.changeTipSeekBar);
	    tipSeekBar.setOnSeekBarChangeListener(tipSeekBarListener);
	    
	    friendlyCheckBox = (CheckBox) findViewById(R.id.friendlyCheckBox); 
	    specialsCheckBox = (CheckBox) findViewById(R.id.specialsCheckBox); 
	    opinionCheckBox = (CheckBox) findViewById(R.id.opinionCheckBox);
	    
	  //  setUpIntroCheckBoxes();
	    
	    availableBadRadio = (RadioButton) findViewById(R.id.availableBadRadio);
	    availableOKRadio = (RadioButton) findViewById(R.id.availableOkRadio);
	    availableGoodRadio = (RadioButton) findViewById(R.id.availableGoodRadio);
	    
	    availableRadioGroup = (RadioGroup) findViewById(R.id.availableRadioGroup);
	    
	 //   addChangeListenerToRadios();
	    
	    problemsSpinner = (Spinner) findViewById(R.id.problemsSpinner);
	    problemsSpinner.setPrompt("Problem Solving");

	  //  addItemSelectedListenerToSpinner();
	    
	    startChronometerButton = (Button) findViewById(R.id.startChronometerButton);
	    pauseChronometerButton = (Button) findViewById(R.id.pauseChronometerButton);
	    resetChronometerButton = (Button) findViewById(R.id.resetChronometerButton);
	   
	  //  setButtonOnClickListeners();
	    
	    timeWaitingChronometer = (Chronometer) findViewById(R.id.timeWaitingChronometer);
	    timeWaitingTextView = (TextView) findViewById(R.id.timeWaitingTextView);
	    


	    
	}

	private TextWatcher tipAmountListener = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			try {
				tipAmount= Double.parseDouble(s.toString());
				if(tipAmount > 1) {
					tipAmount = 1.0;
					tipAmountET.setText("1.0");
				}
			}
			
			catch(Exception e) {
				Debug.out("In catch tip");
				tipAmount = 0.0;
				
			}
			
			updateTipAndFinalBill();
			
		}
		
	};
	
	private TextWatcher billBeforeTipListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			try {
				billBeforeTip = Double.parseDouble(s.toString());
			}
			
			catch(Exception e) {
				Debug.out("In catch");
				billBeforeTip = 0.0;
			}
			
			updateTipAndFinalBill();
		}
		
	};
		
	private void updateTipAndFinalBill() {
		
		double finalBill = billBeforeTip + (billBeforeTip * tipAmount);
		finalBillET.setText(String.format("%.02f", finalBill));
	}
	
	protected void onSaveInstanceState(Bundle outState){
		
		super.onSaveInstanceState(outState);
		outState.putDouble(TOTAL_BILL, finalBill);
		outState.putDouble(CURRENT_TIP, tipAmount);
		outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);
			         
	}
		
	 private OnSeekBarChangeListener tipSeekBarListener = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			
			tipAmount = (tipSeekBar.getProgress()) * .01;
			tipAmountET.setText(String.format("%.02f", tipAmount));
			updateTipAndFinalBill();

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		 
	 };
		
		 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calc, menu);
		return true;
	}

}
