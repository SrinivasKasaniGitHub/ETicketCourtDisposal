package com.example.mtpv.eticketcourt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ExtraPassengers extends Activity {

	public static EditText passngrCount_input;
	Button cancel_dialog, ok_dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_extra_passengers);
		this.setFinishOnTouchOutside(false);
		passngrCount_input = (EditText) findViewById(R.id.passngrCount_input);

		ok_dialog = (Button) findViewById(R.id.ok_dialog);

		ok_dialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (passngrCount_input.getText().toString().trim() == null
						|| passngrCount_input.getText().toString().trim().length() == 0) {
					showToast("Please Enter Passengers Count");
				} else {
					SpotChallan.extraPassengers = passngrCount_input.getText().toString() != null
							? passngrCount_input.getText().toString() : "1";

					Log.i("extraPassengers****************** ::", SpotChallan.extraPassengers);
					finish();
				}
			}
		});

	}

	private void showToast(String msg) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "" + msg,
		// Toast.LENGTH_SHORT).show();
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();

		ViewGroup group = (ViewGroup) toast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(24);

		toastView.setBackgroundResource(R.drawable.toast_background);
		toast.show();
	}
}
