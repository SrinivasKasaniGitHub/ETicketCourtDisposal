package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.mtpv.eticketcourt.service.ServiceHelper;


public class ChangePassword extends Activity {

	EditText old_pswd, new_pswd, otp_received;
	Button cancel, ok;
	final int PROGRESS_DIALOG = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_change_password);
		this.setFinishOnTouchOutside(false);
		
		old_pswd = (EditText) findViewById(R.id.old_password);
		new_pswd = (EditText) findViewById(R.id.new_password);
		otp_received = (EditText) findViewById(R.id.otp_number);
		
		
		cancel = (Button) findViewById(R.id.cancel_dialog);
		ok = (Button) findViewById(R.id.ok_dialog);
		
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// finish();
				if (old_pswd.getText().toString().trim().equals("")) {
					showToast("Please enter Previous Password");

				} else if (new_pswd.getText().toString().trim().equals("")) {

					showToast("Please enter New Password");
				} else if (otp_received.getText().toString().trim().equals("")) {

					showToast("Please enter Your OTP");
				} else {
					new Async_submitted_changedPSWD().execute();
				}

				// showToast("Your Password Changed Successully!!!");
			}
		});

	}

	public class Async_submitted_changedPSWD extends
            AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				SharedPreferences sharedPreference = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				String pid_code = sharedPreference.getString("PID_CODE", "");
				String security_code = sharedPreference.getString("PASS_WORD",
						"");
				String contact_no = sharedPreference.getString("OFF_PHONE_NO",
						"");
				String otp_no = "" + otp_received.getText().toString().trim();
				String new_password = "" + new_pswd.getText().toString().trim();

				String otpflg = "N";

				if (otp_no.trim().equals(Settings.changepwdotpresp.trim())) {
					otpflg = "Y";
				}
				ServiceHelper.updateChange_PWD("" + pid_code, ""
						+ security_code, "" + contact_no, "" + otpflg, ""
						+ new_password);
				Log.i("ServiceH", ""
						+ ServiceHelper.changePSWDconfirm.toString().trim());
				// if(ServiceHelper.changePSWDconfirm!=null &&
				// ServiceHelper.changePSWDconfirm.trim().length()==1){

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			if ("Y".equals(ServiceHelper.changePSWDconfirm.toString().trim())) {
				showToast("Your Password Changed Successully!!!");
				finish();
			} else {
				showToast("New Password Update Failed.Please try Again !!");
			}

		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {

		case PROGRESS_DIALOG:
			ProgressDialog pd = ProgressDialog.show(this, "", "", true);
			pd.setContentView(R.layout.custom_progress_dialog);
			pd.setCancelable(false);

			return pd;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	private void showToast(String msg) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "" + msg,
		// Toast.LENGTH_SHORT).show();
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();

		ViewGroup group = (ViewGroup) toast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(24);

		toastView.setBackgroundResource(R.drawable.toast_background);
		toast.show();

	}
}
