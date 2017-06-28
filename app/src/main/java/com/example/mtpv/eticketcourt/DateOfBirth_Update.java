package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateOfBirth_Update extends Activity {

	@SuppressWarnings("unused")
	private DatePicker datePicker;
	private Calendar calendar;
	private int year, month, day;
	public static Button ok_btn, dob_input;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_date_of_birth__update);
		this.setFinishOnTouchOutside(false);

		dob_input = (Button) findViewById(R.id.dob_input);
		ok_btn = (Button) findViewById(R.id.ok_dialog);

		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);

		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		showDate(year, month, day);

		ok_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		dob_input.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(999);
			}
		});
	}

	/*
	 * @SuppressWarnings("deprecation") public void setDate(View view) {
	 * showDialog(999); Toast.makeText(getApplicationContext(), "ca",
	 * Toast.LENGTH_SHORT) .show(); }
	 */

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		
		case 999:
			DatePickerDialog dp_offence_date = new DatePickerDialog(this,
					myDateListener, year, month, day);

			dp_offence_date.getDatePicker().setMaxDate(
					System.currentTimeMillis());
			return dp_offence_date;
			
		default:
			break;
			
		}
		return super.onCreateDialog(id);
	}

	private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			// arg1 = year
			// arg2 = month
			// arg3 = day
			showDate(arg1, arg2, arg3);
		}
	};

	@SuppressLint("SimpleDateFormat")
	private void showDate(int year, int month, int day) {

		dob_input.setText(new StringBuilder().append(day).append("-")
				.append(month).append("-").append(year));

		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
		@SuppressWarnings("deprecation")
        String present_date_toSend = date_format.format(new Date(year - 1900,
				month, day));

		dob_input.setText(present_date_toSend);
		Log.i("dob_input :::", "" + dob_input.getText().toString());
	}
}


