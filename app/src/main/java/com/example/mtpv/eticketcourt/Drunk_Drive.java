package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.ServiceHelper;
import com.example.mtpv.eticketcourt.service.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("DefaultLocale")
public class Drunk_Drive extends Activity implements OnClickListener, LocationListener {
	@SuppressWarnings("unused")
	private String HALLOWEEN_ORANGE = "#FF7F27";

	public static boolean rtaFlG = false, licFLG = false, adhrFLG = false;

	public static LinearLayout vehicle_type;
	public static CheckBox police_vehcle;
	public static CheckBox govt_vehcle;
	public static String is_govt_police = "";

	EditText et_regcid;// AP09
	EditText et_vchl_num;// CC
	EditText et_last_num;// 3014
	public static EditText et_driver_lcnce_num;

	/* PENDING CHALLAN DETAILS */
	TextView tv_regno_pchallan;
	TextView tv_eTicktvNo;
	TextView tv_offenceddate;
	TextView tv_offencetime;
	TextView tv_pointname;
	TextView tv_psname;
	TextView tv_offencedesc;
	TextView tv_amnt;

	/* LICENCE DETAILS */
	TextView tv_licnce_ownername;
	TextView tv_lcnce_father_name;
	TextView tv_lcnce_phone_number;
	TextView tv_lcnce_address, h_no, vill, village, mdl, dist_pin;
	TextView dl_no;
	ImageView dl_img;

	public static String[] offender_remarks_resp_master;

	/* AADHAR DETAILS */
	public static EditText et_aadharnumber;
	TextView tv_aadhar_header;
	TextView tv_aadhar_user_name;
	TextView tv_aadhar_care_off;
	public static TextView tv_aadhar_address;
	TextView tv_aadhar_mobile_number;
	TextView tv_aadhar_gender;
	public static TextView tv_aadhar_dob;
	TextView tv_aadhar_uid;
	TextView tv_aadhar_eid;
	ImageView img_aadhar_image;
	LinearLayout ll_aadhar_layout;

	RelativeLayout rl_rta_details_layout;
	RelativeLayout rl_lcnce_Details;

	TextView tv_vhle_no;
	TextView tv_owner_name;
	TextView tv_address;
	TextView tv_city;
	TextView tv_maker_name;
	TextView tv_maker_class;
	TextView tv_color;
	TextView tv_engine_no;
	TextView tv_chasis_no;

	public static TextView tv_vehicle_details;// to show vehile details fopund
												// or not
	TextView tv_licence_details;// to show licence details fopund or not

	Button btn_get_details;// RTA DETAILS
	Button btn_get_pending_details;
	Button btn_cancel;
	Button btn_generate_dd_Case;
	Button btn_wheler_code;

	ImageButton ibtn_capture;
	WebView wv_img_captured;

	Utils utils;

	String NETWORK_TXT = "";
	String imei_send = "";
	String simid_send = "";

	final int PROGRESS_DIALOG = 0;
	final int WHEELER_CODE = 1;
	final int FAKE_NUMBERPLATE_DIALOG = 2;
	String fake_plate_details;

	TelephonyManager telephonyManager;

	LocationManager m_locationlistner;
	android.location.Location location;
	double latitude = 0.0;
	double longitude = 0.0;
	String provider = "";

	/* to pass to generate challan */
	public static String details_regncid = "";
	public static String details_regncid_name = "";
	public static String details_regn_last_num = "";
	public static String details_owner_dl_no = "";
	public static String details_driver_dl_no = "";

	public static String[] rta_details_master, Wheeler_check;
	public static String[] licene_details_master;
	// public static String licence_status = "1";
	public static String licence_status = "";

	public static String completeVehicle_num_send = "", regncode_send = "", regnName_send = "", vehicle_num_send = "",
			fake_veh_chasisNo = "";

	public static String aadhaar;
	public static String licence_no;

	// http://www.meraevents.com/event/velocity-2015-carnival
	DBHelper db;
	Cursor c_whlr;
	Cursor c_occptn;
	Cursor c_qlfctn;
	Cursor c_vchle_cat;
	Cursor c_vhcle_main;

	private static final int CAMERA_REQUEST = 1888;

	public static String picturePath = "";

	Bitmap bt;
	Bitmap bitmap;
	String base64_str = "";
	FileOutputStream fo;
	WebviewLoader webviewloader;

	int edt_regncid_spotchallanMAX_LENGTH = 4;
	int edt_regncidname_spotchallanLENGTH = 4;
	int edt_regncid_lastnum_spotchallanMAX_LENGTH = 4;

	String[] wheeler_code_arr, wheeler_name_arr;// wheeler code details
	/* WHELLER DETAILS W_CODE & W_NAME */
	String wheler_code = "Select Wheeler";

	int selected_wheller_code = -1;

	public static String whlr_code_send = "";

	public static byte[] ba = null;

	public static RadioGroup radioGrp_regNo_EngnNo_Chasis;

	public static RadioButton radioGroupButton_regNo, radioGroupButton_engineNo, radioGroupButton_chasisNo;

	public static LinearLayout ll_mainsub_root, ll_engineNo, ll_chasisNo, ll_regno;

	public static EditText et_engineNo, et_chasisNo;

	public static String RegNo_EngNo_ChasisNo = "";

	public static boolean EngneFLG = false, regNoFLG = false, chasisFLG = false, veh_HisFLG = false;

	public static int isitTr = 1;

	TextView regnoText;
	String[] wheeler_code_arr_spot, wheeler_name_arr_spot;
	public static String pidCode = null, pidName = null, psCd = null, psName = null, cadre_code = null,
			cadre_name = null, pass_word = null, off_phone_no = null, current_version = null, rta_data_flg = null,
			dl_data_flg = null, aadhaar_data_flg = null, otp_no_flg = null, cashless_flg = null, mobileNo_flg = null;

	public static ImageView offender_image;

	public static String image_data_tosend = null;

	byte[] byteArray;

	static String date;

	public static String Current_Date;

	/************************ QR CODE **********************************/

	public static int WHITE = 0xFFFFFFFF;
	public static int BLACK = 0xFF000000;
	public final static int width = 500;

	private Calendar cal;

	@SuppressWarnings("unused")
	private int day;

	@SuppressWarnings("unused")
	private int month;

	private int year;

	ImageView qr_code;

	public static String owner_image_data = null;

	public static byte[] owner_imageByteArray = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.drunkdrive);

		date = (DateFormat.format("dd/MM/yyyy hh:mm:ss", new java.util.Date()).toString());

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		Calendar c1 = Calendar.getInstance();
		int mSec = c1.get(Calendar.MILLISECOND);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String strdate1 = sdf1.format(c1.getTime());
		date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		// id_date.setText(strdate1);
		Current_Date = strdate1;

		LoadUIComponents();

		// licence_status = "1";
		licence_status = "";

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		pidCode = sharedPreferences.getString("PID_CODE", "");
		pidName = sharedPreferences.getString("PID_NAME", "");
		psCd = sharedPreferences.getString("PS_CODE", "");
		psName = sharedPreferences.getString("PS_NAME", "");
		cadre_code = sharedPreferences.getString("CADRE_CODE", "");
		cadre_name = sharedPreferences.getString("CADRE_NAME", "");
		pass_word = sharedPreferences.getString("PASS_WORD", "");
		off_phone_no = sharedPreferences.getString("OFF_PHONE_NO", "");
		current_version = sharedPreferences.getString("CURRENT_VERSION", "");
		rta_data_flg = sharedPreferences.getString("RTA_DATA_FLAG", "");
		dl_data_flg = sharedPreferences.getString("DL_DATA_FLAG", "");
		aadhaar_data_flg = sharedPreferences.getString("AADHAAR_DATA_FLAG", "");
		otp_no_flg = sharedPreferences.getString("OTP_NO_FLAG", "");
		cashless_flg = sharedPreferences.getString("CASHLESS_FLAG", "");
		mobileNo_flg = sharedPreferences.getString("MOBILE_NO_FLAG", "");


		rl_rta_details_layout.setVisibility(View.GONE);
		rl_lcnce_Details.setVisibility(View.GONE);

		if (android.os.Build.VERSION.SDK_INT > 11) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		/* TO GET SIM ID */
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei_send = telephonyManager.getDeviceId();// TO GET IMEI NUMBER

		if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
			simid_send = "" + telephonyManager.getSimSerialNumber();
		} else {
			simid_send = "";
		}

		db = new DBHelper(getApplicationContext());
		/* TO GET WHEELER CODE DETAILS */
		try {
			db.open();
			// WHEELER CODE
			c_whlr = DBHelper.db.rawQuery("select * from " + DBHelper.wheelercode_table, null);
			if (c_whlr.getCount() == 0) {
				Log.i("WHEELER DB DETAILS", "0");
			} else {

				wheeler_code_arr_spot = new String[c_whlr.getCount()];
				wheeler_name_arr_spot = new String[c_whlr.getCount()];

				int count = 0;
				while (c_whlr.moveToNext()) {
					wheeler_code_arr_spot[count] = c_whlr.getString(1);
					wheeler_name_arr_spot[count] = c_whlr.getString(2);
					count++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			c_whlr.close();
			db.close();
		}
		c_whlr.close();
		db.close();

		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup_licence);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

				case R.id.radioGroupButton_licenceyes:
					// TODO Something
					// Exits
					licence_status = "";
					licence_status = "1";
					et_driver_lcnce_num.setEnabled(true);
					break;

				case R.id.radioGroupButton_licenceno:
					// TODO Something
					// Does Not Exits
					licence_status = "";
					licence_status = "0";
					et_driver_lcnce_num.setText("");
					et_driver_lcnce_num.setEnabled(false);
					break;
				}
			}
		});

		/* TO GET WHEELER CODE DETAILS */
		try {
			db.open();
			// WHEELER CODE
			c_whlr = DBHelper.db.rawQuery("select * from " + DBHelper.wheelercode_table, null);
			if (c_whlr.getCount() == 0) {
				Log.i("WHEELER DB DETAILS", "0");
			} else {

				wheeler_code_arr = new String[c_whlr.getCount()];
				wheeler_name_arr = new String[c_whlr.getCount()];

				int count = 0;
				while (c_whlr.moveToNext()) {
					wheeler_code_arr[count] = c_whlr.getString(1);
					wheeler_name_arr[count] = c_whlr.getString(2);
					count++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			c_whlr.close();
			db.close();
		}
		c_whlr.close();
		db.close();

	}

	private void LoadUIComponents() {
		// TODO Auto-generated method stub

		// hardcode
		et_regcid = (EditText) findViewById(R.id.edt_regncid_rtadetails_xml);
		et_vchl_num = (EditText) findViewById(R.id.edt_regncidname_rtadetails_xml);
		et_last_num = (EditText) findViewById(R.id.edt_regncid_lastnum_rtadetails_xml);
		//et_regcid.setText("AP09");
		//et_vchl_num.setText("CC");
		//et_last_num.setText("3014");

		offender_image = (ImageView) findViewById(R.id.offender_image);
		offender_image.setVisibility(View.GONE);
		et_regcid.requestFocus();

		et_regcid.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (et_regcid.getText().toString().length() == edt_regncid_spotchallanMAX_LENGTH) {
					et_vchl_num.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		et_vchl_num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (et_vchl_num.getText().toString().length() == edt_regncidname_spotchallanLENGTH) {
					et_last_num.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		et_last_num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (et_last_num.getText().toString().length() == edt_regncid_lastnum_spotchallanMAX_LENGTH) {
					et_driver_lcnce_num.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		vehicle_type = (LinearLayout) findViewById(R.id.vehicle_type);
		police_vehcle = (CheckBox) findViewById(R.id.police_vehcle);
		govt_vehcle = (CheckBox) findViewById(R.id.govt_vehcle);

		police_vehcle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (police_vehcle.isChecked() == true && govt_vehcle.isChecked() == true) {
					police_vehcle.setChecked(true);
					govt_vehcle.setChecked(false);
				}
			}
		});

		govt_vehcle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (police_vehcle.isChecked() == true && govt_vehcle.isChecked() == true) {
					govt_vehcle.setChecked(true);
					police_vehcle.setChecked(false);
				}
			}
		});

		// hardcode
		et_driver_lcnce_num = (EditText) findViewById(R.id.edt_driverdlno_rtadetails_xml);
		//et_driver_lcnce_num.setText("7461WGL1998OD");
		btn_get_details = (Button) findViewById(R.id.btngetrtadetails_rtadetails_xml);
		btn_get_pending_details = (Button) findViewById(R.id.btn_pendingchallans_rtadetails_xml);
		btn_cancel = (Button) findViewById(R.id.btncancel_rta_details_xml);
		btn_generate_dd_Case = (Button) findViewById(R.id.btngeneratechallan_rta_details_xml);
		btn_wheler_code = (Button) findViewById(R.id.btn_whlr_code_dd_xml);

		ibtn_capture = (ImageButton) findViewById(R.id.imgv_camera_capture_rta_details_xml);
		// wv_img_captured = (WebView)
		// findViewById(R.id.webView_rtadetails_xml);
		// wv_img_captured.setVisibility(View.GONE);

		rl_rta_details_layout = (RelativeLayout) findViewById(R.id.rl_detailsresponse_rtadetails_xml);
		rl_lcnce_Details = (RelativeLayout) findViewById(R.id.rl_licences_rtadetails_xml);

		tv_vhle_no = (TextView) findViewById(R.id.tvregno_rtadetails_xml);
		tv_owner_name = (TextView) findViewById(R.id.tvownername_rtadetails_xml);
		tv_address = (TextView) findViewById(R.id.tv_addr_rtadetails_xml);
		// tv_city = (TextView) findViewById(R.id.tv_city_rtadetails_xml);
		tv_maker_name = (TextView) findViewById(R.id.tv_makername_rtadetails_xml);
		// tv_maker_class = (TextView)
		// findViewById(R.id.tv_makerclass_rtadetails_xml);
		// tv_color = (TextView) findViewById(R.id.tv_color_rtadetails_xml);
		tv_engine_no = (TextView) findViewById(R.id.tv_engineno_rtadetails_xml);
		tv_chasis_no = (TextView) findViewById(R.id.tv_chasis_rtadetails_xml);

		/* LICENCE DETAILS */
		tv_licnce_ownername = (TextView) findViewById(R.id.tvlcnceownername_rtadetails_xml);
		tv_lcnce_father_name = (TextView) findViewById(R.id.tvlcnce_fname_rtadetails_xml);
		tv_lcnce_phone_number = (TextView) findViewById(R.id.tv_lcnce_mobnum_rtadetails_xml);
		tv_lcnce_address = (TextView) findViewById(R.id.tv_lcnce_Address_rtadetails_xml);
		dl_img = (ImageView) findViewById(R.id.dl_img);
		dl_no = (TextView) findViewById(R.id.dl_no);

		/* AADHAR DETAILS */
		// hardcode
		et_aadharnumber = (EditText) findViewById(R.id.edt_aadharno_rtadetails_xml);
		//et_aadharnumber.setText("322847907255");
		ll_aadhar_layout = (LinearLayout) findViewById(R.id.ll_aadhardetails_rtadetails_xml);
		tv_aadhar_header = (TextView) findViewById(R.id.tvheader_adhar_rtadetails_xml);
		tv_aadhar_user_name = (TextView) findViewById(R.id.tvaadharname_rtadetails_xml);
		tv_aadhar_care_off = (TextView) findViewById(R.id.tvcareof_rtadetails_xml);
		tv_aadhar_address = (TextView) findViewById(R.id.tvaddress_rtadetails_xml);
		tv_aadhar_mobile_number = (TextView) findViewById(R.id.tvmobilenumber_rtadetails_xml);
		tv_aadhar_gender = (TextView) findViewById(R.id.tvgender_rtadetails_xml);
		tv_aadhar_dob = (TextView) findViewById(R.id.tvdob_rtadetails_xml);
		tv_aadhar_uid = (TextView) findViewById(R.id.tvuid_rtadetails_xml);
		// tv_aadhar_eid = (TextView) findViewById(R.id.tveid_rtadetails_xml);
		qr_code = (ImageView) findViewById(R.id.qr_code);
		img_aadhar_image = (ImageView) findViewById(R.id.imgv_aadhar_photo_rtadetails_xml);
		ll_aadhar_layout.setVisibility(View.GONE);
		tv_aadhar_header.setVisibility(View.GONE);

		/* TO SHOW vehicel DETAILS AND LICENCE DETAILS FOUND OR NOT */
		tv_vehicle_details = (TextView) findViewById(R.id.textView_regdetails_header);
		tv_licence_details = (TextView) findViewById(R.id.textView_licence_header);

		btn_wheler_code.setOnClickListener(this);
		ibtn_capture.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_generate_dd_Case.setOnClickListener(this);
		btn_get_details.setOnClickListener(this);// RTA DETAILS
		btn_get_pending_details.setOnClickListener(this);

		utils = new Utils();
		NETWORK_TXT = getResources().getString(R.string.newtork_txt);

		rta_details_master = new String[0];
		licene_details_master = new String[0];

		ll_mainsub_root = (LinearLayout) findViewById(R.id.ll_mainsub_root);

	}

	public Boolean isOnline() {
		ConnectivityManager conManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
		return nwInfo != null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btngetrtadetails_rtadetails_xml:

			Fake_NO_Dialog.fake_action = null;
			tv_vehicle_details.setText("");
			tv_licence_details.setText("");

			VerhoeffCheckDigit ver = new VerhoeffCheckDigit();

			if (et_regcid.getText().toString().trim().equals("")) {
				// utils.showError(et_regcid, "Enter Registration Code");
				et_regcid.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));
				et_regcid.requestFocus();

			} else if (et_last_num.getText().toString().trim().equals("")) {
				// utils.showError(et_last_num, "");
				et_last_num.setError(Html.fromHtml("<font color='black'>Enter Vehicle Number</font>"));
				et_last_num.requestFocus();

			} else {
				if (isOnline()) {
					vehicle_num_send = "";
					vehicle_num_send = ("" + (et_regcid.getText().toString().trim().toUpperCase()) + ""
							+ (et_vchl_num.getText().toString().trim().toUpperCase()) + ""
							+ (et_last_num.getText().toString().trim().toUpperCase()));
					Log.i("**VEHCILE**", "" + vehicle_num_send);

					Dashboard.rta_details_request_from = "RTACLASS";
					completeVehicle_num_send = ("" + et_regcid.getText().toString() + ""
							+ et_vchl_num.getText().toString() + "" + et_last_num.getText().toString());
					new Async_getRTADetails().execute();

					if (isOnline()) {
						if (!et_driver_lcnce_num.getText().toString().trim().equals("")) {
							Dashboard.licence_details_request_from = "RTACLASS";
							new Async_getLicenceDetails().execute();
						}
					}

					/* TO GET AADHAR DETAILS */
					ll_aadhar_layout.setVisibility(View.GONE);
					tv_aadhar_header.setVisibility(View.GONE);
					img_aadhar_image.setImageResource(R.drawable.photo);

					if (et_aadharnumber.getText() != null && et_aadharnumber.getText().toString().trim().length() >= 1
							&& (!ver.isValid(et_aadharnumber.getText().toString()))) {
						showToast("Please Enter Valid Adhaar Number");
						et_aadharnumber
								.setError(Html.fromHtml("<font color='black'>Please Enter Valid Adhaar Number</font>"));
					} else if ((et_aadharnumber.getText().toString().trim().length() == 12)
							|| (et_aadharnumber.getText().toString().trim().length() == 28)) {
						if (isOnline()) {
							new Async_getAadharDetails().execute();
						}
					}
				} else {
					showToast("" + NETWORK_TXT);
				}
			}
			break;

		case R.id.btn_pendingchallans_rtadetails_xml:

			if (et_regcid.getText().toString().trim().equals("")) {
				// utils.showError(et_regcid, "Enter Registration Code");
				et_regcid.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));

			}
			// NOT MANDATORY
			// else if (et_vchl_num.getText().toString().trim().equals("")) {
			// utils.showError(et_vchl_num, "Enter Registration Series");
			// }
			else if (et_last_num.getText().toString().trim().equals("")) {
				// utils.showError(et_last_num, "Enter Vehicle Number");
				et_last_num.setError(Html.fromHtml("<font color='black'>Enter Vehicle Number</font>"));
			} else {
				if (isOnline()) {
					vehicle_num_send = "";
					vehicle_num_send = ("" + (et_regcid.getText().toString().trim().toUpperCase()) + ""
							+ (et_vchl_num.getText().toString().trim().toUpperCase()) + ""
							+ (et_last_num.getText().toString().trim().toUpperCase()));
					Log.i("**VEHCILE NUD***", "" + vehicle_num_send);
					new Async_getPendingChallans().execute();
				} else {
					showToast("" + NETWORK_TXT);
				}
			}
			break;

		case R.id.btncancel_rta_details_xml:
			/*
			 * In API level 11 or greater : use Code : if(Build.VERSION.SDK_INT
			 * >= 11)
			 */
			Intent launch = new Intent(Drunk_Drive.this, Dashboard.class);
			launch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(launch);
			break;

		/* TO MOVE TO GENERATE MOBILE TICKET */
		case R.id.btngeneratechallan_rta_details_xml:

			if (et_regcid.getText().toString().trim().equals("")) {
				et_regcid.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));
				et_regcid.requestFocus();

			} else if (et_last_num.getText().toString().trim().equals("")) {
				// utils.showError(et_last_num, "Enter Vehicle Number");
				et_last_num.setError(Html.fromHtml("<font color='black'>Enter Vehicle Number</font>"));
			} else if (btn_wheler_code.getText().toString().trim()
					.equals("" + getResources().getString(R.string.select_wheeler_code))) {
				showToast("Select wheeler code");
			}

			else {
				details_regncid = et_regcid.getText().toString().trim();
				details_regncid_name = et_vchl_num.getText().toString().trim();
				details_regn_last_num = et_last_num.getText().toString().trim();
				details_driver_dl_no = et_driver_lcnce_num.getText().toString().trim();

				try {
					db.open();

					c_whlr = DBHelper.db.rawQuery("select * from " + DBHelper.wheelercode_table, null);

					if ((c_whlr.getCount() > 0)) {

						licence_status = "";
						Log.i("IMAGE FROM RTVE", "" + picturePath);

						if (Fake_NO_Dialog.fake_action == "not fake") {

							SharedPreferences sharedPreference = PreferenceManager
									.getDefaultSharedPreferences(getApplicationContext());
							SharedPreferences.Editor edit = sharedPreference.edit();
							edit.putString("IMAGE", "" + image_data_tosend);
							edit.commit();
							startActivity(new Intent(this, GenerateDrunkDriveCase.class));

						} else if (Fake_NO_Dialog.fake_action == null) {

							SharedPreferences sharedPreference = PreferenceManager
									.getDefaultSharedPreferences(getApplicationContext());
							SharedPreferences.Editor edit = sharedPreference.edit();
							edit.putString("IMAGE", "" + image_data_tosend);
							edit.commit();
							startActivity(new Intent(this, GenerateDrunkDriveCase.class));

						} else if (Fake_NO_Dialog.fake_action == "fake") {

							TextView title = new TextView(Drunk_Drive.this);
							title.setText("Hyderabad E-Ticket");
							title.setBackgroundColor(Color.RED);
							title.setGravity(Gravity.CENTER);
							title.setTextColor(Color.WHITE);
							title.setTextSize(26);
							title.setTypeface(title.getTypeface(), Typeface.BOLD);
							title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
									R.drawable.dialog_logo, 0);
							title.setPadding(20, 0, 20, 0);
							title.setHeight(70);

							String otp_message = "\n It's a Fake Vehicle !!!\n";

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Drunk_Drive.this,
									AlertDialog.THEME_HOLO_LIGHT);
							alertDialogBuilder.setCustomTitle(title);
							alertDialogBuilder.setIcon(R.drawable.dialog_logo);
							alertDialogBuilder.setMessage(otp_message);
							alertDialogBuilder.setCancelable(false);
							alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								}
							});

							AlertDialog alertDialog = alertDialogBuilder.create();
							alertDialog.show();
							alertDialog.getWindow().getAttributes();

							TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
							textView.setTextSize(28);
							textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
							textView.setGravity(Gravity.CENTER);

							Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
							btn1.setTextSize(22);
							btn1.setTextColor(Color.WHITE);
							btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
							btn1.setBackgroundColor(Color.RED);
						}
					} else {
						showToast("" + Utils.MASTER_ELSE_TEXT);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c_whlr.close();
				db.close();
			}
			break;

		case R.id.imgv_camera_capture_rta_details_xml:
			/*
			 * if (isDeviceSupportCamera()) {
			 *
			 * Intent cameraIntent = new
			 * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			 * startActivityForResult(cameraIntent, CAMERA_REQUEST); } else {
			 * showToast("Sorry! Your device doesn't support camera"); }
			 */
			selectImage();
			break;

		case R.id.btn_whlr_code_dd_xml:
			showDialog(WHEELER_CODE);
			break;

		default:
			break;
		}
	}

	protected void selectImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		startActivityForResult(intent, 1);

	}

	public class Async_getRTADetails extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			regncode_send = "" + et_regcid.getText().toString().trim().toUpperCase();
			regnName_send = "" + et_vchl_num.getText().toString().toUpperCase();
			vehicle_num_send = "" + et_last_num.getText().toString().trim().toUpperCase();

			aadhaar = et_aadharnumber.getText().toString().trim();
			licence_no = et_driver_lcnce_num.getText().toString().trim();

			completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + "" + vehicle_num_send);

			ServiceHelper.getRTADetails("" + completeVehicle_num_send);
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

			// new Async_getOffenderRemarks().execute();

			if ((!ServiceHelper.rta_data.equals("0")) && (rta_details_master.length > 1)) {
				rl_rta_details_layout.setVisibility(View.VISIBLE);

				rtaFlG = true;
				licFLG = false;
				adhrFLG = false;

				tv_vhle_no.setText("" + completeVehicle_num_send);
				tv_owner_name.setText("" + rta_details_master[1]);
				tv_address.setText("" + rta_details_master[2] + "\t" + rta_details_master[3]);
				tv_maker_name.setText("" + rta_details_master[4] + "\t" + rta_details_master[6]);
				tv_engine_no.setText("" + rta_details_master[7]);
				tv_chasis_no.setText("" + rta_details_master[8]);

				Log.i("FAKE DETAILS", "" + rta_details_master[10]);

				tv_vehicle_details.setText("VEHICLE DETAILS");

				rta_details_master = new String[0];

				rta_details_master = ServiceHelper.rta_data.split("!");

				Wheeler_check = rta_details_master[0].split(":");
				String Wheeler_Enable_check = Wheeler_check[1].toString();

				if (!Wheeler_Enable_check.equalsIgnoreCase("NA")) {
					btn_wheler_code.setClickable(false);
				} else {
					btn_wheler_code.setClickable(true);
				}

				/******** DIRECT DISPLAY WHEELER CODE FROM SERVICE ************/

				/*
				 * AP09CC3014 -->0 WHEELER :4! -->0 CHERUKUPALLY SUNEETHA! -->1
				 * 1-8-726/41 G-1,SHANTI NILAYAM,ACHAIAH NAGAR NEW NALLAKUNTA
				 * HYD! --->2 HYD! -->3 MARUTI SUZUKI INDIA LTD.,! -->4 MARUTI
				 * ALTO K10 VXI BS IV! -->5 M F RED! -->6 K10BN4121940! -->7
				 * MA3EADE1S00114983HA! -->8 4! -->9 NA! -->10 !-->11
				 */

				// Log.i("Response wheeler :::", ""+rta_details_master[0]);
				if (rta_details_master != null && rta_details_master.length > 0 && (!rta_details_master[0].equals("NA"))
						&& rta_details_master[0].split("\\:")[1] != null
						&& !"NA".equalsIgnoreCase(rta_details_master[0].split("\\:")[1].trim())) {
					// whlr_code_send = rta_details_master[0].split("\\:")[1];
					whlr_code_send = rta_details_master[9];
					Log.i("whlr_code_::::", whlr_code_send);
					if (whlr_code_send != null) {
						btn_wheler_code.setText("" + whlr_code_send);
						Log.i("whlr_con::::", "Called");
					} else {
						btn_wheler_code.setClickable(true);
					}
				} else {
					// showToast("DD Toast !!!");
				}
				// ********DIRECT DISPLAY WHEELER CODE FROM SERVICE************

				/*
				 * if ((!rta_details_master[10].toString().trim().equals("NA")))
				 * { showDialog(FAKE_NUMBERPLATE_DIALOG);
				 *
				 * }
				 */
				new Async_getOffenderRemarks().execute();

			} else {
				tv_vehicle_details.setText("VEHICLE DETAILS NOT FOUND!");
				rl_rta_details_layout.setVisibility(View.GONE);
				rtaFlG = false;
				// showToast("No Details Found !");
			}

			if ((et_driver_lcnce_num.getText().toString().trim().equals(""))
					&& (et_aadharnumber.getText().toString().trim().equals(""))) {

				String otp_message = "\n Please enter driver Licence Number / Aadhar Number \n";

				TextView title3 = new TextView(Drunk_Drive.this);
				title3.setText("ALERT");
				title3.setBackgroundColor(Color.RED);
				title3.setGravity(Gravity.CENTER);
				title3.setTextColor(Color.WHITE);
				title3.setTextSize(26);
				title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
				title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
				title3.setPadding(20, 0, 20, 0);
				title3.setHeight(70);

				AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(Drunk_Drive.this,
						AlertDialog.THEME_HOLO_LIGHT);
				alertDialog_Builder.setCustomTitle(title3);
				alertDialog_Builder.setIcon(R.drawable.dialog_logo);
				alertDialog_Builder.setMessage(otp_message);
				alertDialog_Builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
				alertDialog_Builder.setCancelable(false);
				AlertDialog alert_Dialog = alertDialog_Builder.create();
				alert_Dialog.show();

				alert_Dialog.getWindow().getAttributes();

				TextView textView2 = (TextView) alert_Dialog.findViewById(android.R.id.message);
				textView2.setTextSize(28);
				textView2.setGravity(Gravity.CENTER);
				textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

				Button btn2 = alert_Dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				btn2.setTextSize(22);
				btn2.setTextColor(Color.WHITE);
				btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
				btn2.setBackgroundColor(Color.RED);
			}
			if ((et_aadharnumber.getText().toString().trim().length() == 12)
					|| (et_aadharnumber.getText().toString().trim().length() == 28)) {
				new Async_getAadharDetails().execute();
			}
		}
	}

	public class Async_getOffenderRemarks extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			ServiceHelper.getOffenderRemarks(completeVehicle_num_send,
					"" + et_driver_lcnce_num.getText().toString().trim(),
					"" + et_aadharnumber.getText().toString().trim());
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
			if (ServiceHelper.offender_remarks != null) {
				Drunk_Drive.offender_remarks_resp_master = new String[0];

				Drunk_Drive.offender_remarks_resp_master = ServiceHelper.offender_remarks.split("!");
				Log.i("**getRTADe*", "" + Drunk_Drive.offender_remarks_resp_master.length);

				if ((!offender_remarks_resp_master[10].toString().trim().equals("NA"))) {
					showDialog(FAKE_NUMBERPLATE_DIALOG);
				}
			} else {
				showToast("Error");
			}
		}
	}

	public class Async_getAadharDetails extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHelper.getAadharDetails("" + et_aadharnumber.getText().toString().trim(), "");
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

			if (ServiceHelper.aadhar_details.length > 0) {
				ll_aadhar_layout.setVisibility(View.VISIBLE);
				tv_aadhar_header.setVisibility(View.VISIBLE);

				adhrFLG = true;
				// licFLG = false ;
				rtaFlG = false;

				tv_aadhar_header.setText("AADHAR DETAILS");
				tv_aadhar_user_name.setText("" + ServiceHelper.aadhar_details[0].trim().toUpperCase());
				tv_aadhar_care_off.setText("" + (!ServiceHelper.aadhar_details[1].equalsIgnoreCase("0")
						? ServiceHelper.aadhar_details[1] : "NA").trim().toUpperCase());
				tv_aadhar_address.setText(""
						+ (!ServiceHelper.aadhar_details[2].equalsIgnoreCase("0") ? ServiceHelper.aadhar_details[2]
								: "")
						+ ","
						+ (!ServiceHelper.aadhar_details[3].equalsIgnoreCase("0")
								? ServiceHelper.aadhar_details[3] + "," : "")
						+ (!ServiceHelper.aadhar_details[4].equalsIgnoreCase("0")
								? ServiceHelper.aadhar_details[4] + "," : "")
						+ (!ServiceHelper.aadhar_details[5].equalsIgnoreCase("0")
								? ServiceHelper.aadhar_details[5] + "," : "")
						+ (!ServiceHelper.aadhar_details[6].equalsIgnoreCase("0")
								? ServiceHelper.aadhar_details[6] + "," : "")
						+ (!ServiceHelper.aadhar_details[7].equalsIgnoreCase("0")
								? ServiceHelper.aadhar_details[7] + "," : "").trim().toUpperCase());

				String Compleate_addr = "" + tv_aadhar_address;

				tv_aadhar_mobile_number.setText("" + (!ServiceHelper.aadhar_details[8].equalsIgnoreCase("0")
						? ServiceHelper.aadhar_details[8] : "NA").trim().toUpperCase());
				tv_aadhar_gender.setText("" + (!ServiceHelper.aadhar_details[9].equalsIgnoreCase("0")
						? ServiceHelper.aadhar_details[9] : "NA").trim().toUpperCase());

				/*
				 * tv_aadhar_dob .setText("" +
				 * (!ServiceHelper.aadhar_details[10] .equalsIgnoreCase("0") ?
				 * ServiceHelper.aadhar_details[10] : "NA"));
				 */

				String date_birth = ServiceHelper.aadhar_details[10];
				String[] split_dob = date_birth.split("\\/");
				String service_year = "" + split_dob[2];

				int final_age = year - Integer.parseInt(service_year);
				Log.i("final_age ::::::::", "" + final_age);

				tv_aadhar_dob.setText("" + final_age);

				tv_aadhar_uid.setText("" + (!ServiceHelper.aadhar_details[11].equalsIgnoreCase("0")
						? ServiceHelper.aadhar_details[11] : "NA"));
				/*
				 * tv_aadhar_eid .setText("" +
				 * (!ServiceHelper.aadhar_details[12] .equalsIgnoreCase("0") ?
				 * ServiceHelper.aadhar_details[12] : "NA"));
				 */

				if (ServiceHelper.aadhar_details[13].toString().trim().equals("0")) {

				} else if (ServiceHelper.aadhar_details[13] != null) {
					byte[] decodestring = Base64.decode("" + ServiceHelper.aadhar_details[13].toString().trim(),
							Base64.DEFAULT);
					Bitmap decocebyte = BitmapFactory.decodeByteArray(decodestring, 0, decodestring.length);
					img_aadhar_image.setImageBitmap(decocebyte);
				}

				else {
					ServiceHelper.aadhar_details[13] = null;
				}
				if (ServiceHelper.aadhar_details[13] == null) {
					img_aadhar_image.setImageResource(R.drawable.camera);
					// img_aadhar_image.setClickable(true);
				}

				String Qrdata = "AADHAAAR NUMBER:" + " " + ServiceHelper.aadhar_details[11] + "\n" + "NAME:" + " "
						+ ServiceHelper.aadhar_details[0] + "\n" + "FATHER NAME:" + " "
						+ ServiceHelper.aadhar_details[1] + "\n" + "AGE:" + " " + final_age + "\n" + "GENDER:" + " "
						+ ServiceHelper.aadhar_details[9] + "\n" + "ADDRESS:" + " " + Compleate_addr;
				try {
					Bitmap bitmap = encodeAsBitmap(Qrdata);
					qr_code.setImageBitmap(bitmap);
				} catch (WriterException e) {
					e.printStackTrace();
				}

				/*
				 * if
				 * (((et_driver_lcnce_num.getText().toString().trim().equals(""
				 * ))||(et_aadharnumber.getText().toString().trim().equals("")))
				 * || (!rta_details_master[10].toString().trim() .equals("NA")))
				 * { showDialog(FAKE_NUMBERPLATE_DIALOG); }
				 */

				// image data arr[13]
			} else if (ServiceHelper.aadhar_details.length == 0) {
				tv_aadhar_header.setVisibility(View.VISIBLE);
				tv_aadhar_header.setText("AADHAR DETAILS NOT FOUND!");
				ll_aadhar_layout.setVisibility(View.GONE);
				adhrFLG = false;
			}
		}
	}

	/* LICENCE DETAILS */
	public class Async_getLicenceDetails extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHelper.getLicenceDetails("" + et_driver_lcnce_num.getText().toString().trim());
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
			// showToast("" + licene_details_master.length);

			dl_no.setText("" + et_driver_lcnce_num.getText().toString().trim());

			if ((!ServiceHelper.license_data.equals("0")) && (SpotChallan.licence_details_spot_master.length > 0)) {

				rl_lcnce_Details.setVisibility(View.VISIBLE);
				Log.i("ServiceHelpe::", "" + ServiceHelper.license_data);
				Drunk_Drive.licFLG = true;
				adhrFLG = false;
				rtaFlG = false;
				tv_licnce_ownername.setText("" + SpotChallan.licence_details_spot_master[0]);
				tv_lcnce_father_name.setText("" + SpotChallan.licence_details_spot_master[1]);
				tv_lcnce_phone_number.setText("" + SpotChallan.licence_details_spot_master[2]);
				tv_lcnce_address.setText("" + SpotChallan.licence_details_spot_master[4]);

				/*
				 * owner_image_data = ""+licene_details_master[5]; if
				 * (""+licene_details_master[5] !=null ) { byte[] decodestring =
				 * Base64.decode("" + licene_details_master[5].toString()
				 * .trim(), Base64.DEFAULT); Bitmap decocebyte =
				 * BitmapFactory.decodeByteArray( decodestring, 0,
				 * decodestring.length); dl_img.setImageBitmap(decocebyte); }
				 * else if (""+licene_details_master[5] ==null) {
				 *
				 * dl_img.setImageResource(R.drawable.empty_profile_img);
				 *
				 * }
				 */

				owner_image_data = "" + SpotChallan.licence_details_spot_master[5];

				if (owner_image_data != null && owner_image_data.trim().length() > 100) {
					owner_imageByteArray = Base64.decode(owner_image_data.getBytes(), 1);
					Log.i("Image 2 byte[]", "" + Base64.decode(owner_image_data.trim().getBytes(), 1));
					Bitmap bmp = BitmapFactory.decodeByteArray(owner_imageByteArray, 0, owner_imageByteArray.length);
					dl_img.setImageBitmap(bmp);

					/*
					 * ByteArrayOutputStream bytes = new
					 * ByteArrayOutputStream();
					 * bmp.compress(Bitmap.CompressFormat.JPEG,10, bytes);
					 *
					 * byteArray = bytes.toByteArray();
					 *
					 * imgString4 = Base64.encodeToString(byteArray,
					 * Base64.NO_WRAP);
					 */
				} else if (owner_image_data == null && owner_image_data.trim().length() == 0) {
					dl_img.setImageResource(R.drawable.empty_profile_img);
				}
				tv_licence_details.setText("LICENCE DETAILS");
				Log.i("LICENCE DETAILS FOUND", "" + licFLG);

			} else {
				Drunk_Drive.licFLG = false;
				Log.i("NO LICED", "" + licFLG);
				tv_licence_details.setText("LICENCE DETAILS NOT FOUND!");
				rl_lcnce_Details.setVisibility(View.GONE);

				// showToast("No Details Found !");
			}
			/*
			 * Log.i("Async_getLicenceDetails FD", "" + rta_details_master[9]);
			 * Log.i("Async_getLicenceDetails FD", "" + rta_details_master[10]);
			 *
			 * if ((!rta_details_master[10].toString().trim().equals("NA"))) {
			 * Log.i("FD", "YES"); showDialog(FAKE_NUMBERPLATE_DIALOG); }
			 */
		}
	}

	/* TO GET PENDING CHALLANS BY VECHILE NUMBER */
	public class Async_getPendingChallans extends AsyncTask<Void, Void, String> {

		@SuppressWarnings("unused")
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SharedPreferences sharedPreference2 = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			String psCd = sharedPreference2.getString("PS_CODE", "");
			String psName = sharedPreference2.getString("PS_NAME", "");
			String pidCd = sharedPreference2.getString("PID_CODE", "");
			String pidName = sharedPreference2.getString("PID_NAME", "");
			String cadre = sharedPreference2.getString("CADRE_NAME", "");
			String cadreCd = sharedPreference2.getString("CADRE_CODE", "");
			String pswd = sharedPreference2.getString("PASS_WORD", "");

			ServiceHelper.getpendingChallansByRegNo("" + completeVehicle_num_send, "" + "", "" + "", "" + pidCd,
					"" + pidName, "" + pswd, "" + simid_send, "" + imei_send, "" + latitude, "" + longitude, "", "23");
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

			if ((!ServiceHelper.Opdata_Chalana.equals("0")) && (ServiceHelper.pending_challans_master.length > 0)) {
				//startActivity(new Intent(getApplicationContext(), PendingChallans.class));
			} else if (ServiceHelper.Opdata_Chalana.equals("0")) {
				showToast("No Pending Challans");
			} else {
				showToast("Try Again!");
			}
			if ((!rta_details_master[10].toString().trim().equals("NA"))) {
				Log.i("FD", "YES");
				showDialog(FAKE_NUMBERPLATE_DIALOG);
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

		case WHEELER_CODE:
			TextView title = new TextView(this);
			title.setText("Select Wheeler");
			title.setBackgroundColor(Color.parseColor("#007300"));
			title.setGravity(Gravity.CENTER);
			title.setTextColor(Color.WHITE);
			title.setTextSize(26);
			title.setTypeface(title.getTypeface(), Typeface.BOLD);
			title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
			title.setPadding(20, 0, 20, 0);
			title.setHeight(70);

			AlertDialog.Builder ad_whle_code_name = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			ad_whle_code_name.setCustomTitle(title);//
			ad_whle_code_name.setSingleChoiceItems((wheeler_name_arr), selected_wheller_code,

					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							/*
							 * selected_wheller_code = which;
							 * Log.i("selected_wheller_code ::::",
							 * ""+selected_wheller_code);
							 * btn_wheler_code.setText(""+
							 * wheeler_name_arr[which]);
							 * Log.i("wheeler_name_arr_spot[which] ::::",
							 * ""+wheeler_name_arr[which]);
							 *
							 * removeDialog(WHEELER_CODE);
							 *
							 * whlr_code_send = wheeler_name_arr[which];
							 */

							selected_wheller_code = which;
							Log.i("selected_whe::", "" + selected_wheller_code);
							btn_wheler_code.setText("" + wheeler_name_arr_spot[which]);
							Log.i("wheelerh] ::::", "" + wheeler_name_arr_spot[which]);
							removeDialog(WHEELER_CODE);
							whlr_code_send = wheeler_code_arr_spot[which];
							Log.i("****whlr_code_send***", "" + whlr_code_send);
						}
					});
			Dialog dg_whle_code_name = ad_whle_code_name.create();
			return dg_whle_code_name;

		case FAKE_NUMBERPLATE_DIALOG:

			String massge = null;
			try {
				massge = "\n" + offender_remarks_resp_master[10] + "\n";

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

				massge = "OFFENDER REMARKS NOT FOUND PLEASE TRY AGAIN";

			}

			TextView title2 = new TextView(this);
			title2.setText("REMARKS");
			title2.setBackgroundColor(Color.RED);
			title2.setGravity(Gravity.CENTER);
			title2.setTextColor(Color.WHITE);
			title2.setTextSize(26);
			title2.setTypeface(title2.getTypeface(), Typeface.BOLD);
			title2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
			title2.setPadding(20, 0, 20, 0);
			title2.setHeight(70);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			alertDialogBuilder.setCustomTitle(title2);
			alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			alertDialogBuilder.setMessage(massge);
			alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					removeDialog(FAKE_NUMBERPLATE_DIALOG);
					if (offender_remarks_resp_master[10].contains("FAKE NO")) {
						Log.i("FAke  ::::", "Yes it's Fake");
						Log.i("fake_veh_chasisNo  ::::", fake_veh_chasisNo);
						fake_veh_chasisNo = offender_remarks_resp_master[8]
								.substring(offender_remarks_resp_master[8].length() - 5);
						Log.i("Spot ** :::", "" + fake_veh_chasisNo);
						Intent intent = new Intent(getApplicationContext(), Fake_NO_Dialog.class);
						intent.putExtra("Flagkey", "D");
						startActivity(intent);
					}
				}
			});
			alertDialogBuilder.setCancelable(false);
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

			alertDialog.getWindow().getAttributes();

			TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
			textView.setTextSize(28);
			textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			textView.setGravity(Gravity.CENTER);

			Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			btn1.setTextSize(22);
			btn1.setTextColor(Color.WHITE);
			btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			btn1.setBackgroundColor(Color.RED);
			return alertDialog;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	Bitmap encodeAsBitmap(String str) throws WriterException {

		BitMatrix result;

		try {
			result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, width, null);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}

		int w = result.getWidth();
		int h = result.getHeight();
		int[] pixels = new int[w * h];
		for (int y = 0; y < h; y++) {
			int offset = y * w;
			for (int x = 0; x < w; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
		return bitmap;
	}

	/* ACTIVITYRESULT FOR ACCESSING IMAGES FROM GALLERY AND CAMERA */
	@SuppressLint("SetJavaScriptEnabled")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			if (requestCode == CAMERA_REQUEST) {

				Bitmap photo = (Bitmap) data.getExtras().get("data");

				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				Random randomGenerator = new Random();
				int num = randomGenerator.nextInt(100);
				String newimagename = num + ".jpg";
				File f = new File(Environment.getExternalStorageDirectory() + File.separator + newimagename);

				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					fo = new FileOutputStream(f.getAbsoluteFile());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					fo.write(bytes.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!picturePath.equals("")) {
					picturePath = "";
				}

				picturePath = f.getAbsolutePath();
				Log.i("pic path", picturePath);
				webviewloader = new WebviewLoader();
				wv_img_captured.setBackgroundColor(0x00000000);
				wv_img_captured.setHorizontalScrollBarEnabled(true);
				wv_img_captured.setVerticalScrollBarEnabled(true);
				WebSettings webSettings = wv_img_captured.getSettings();
				wv_img_captured.setInitialScale(50);
				webSettings.setJavaScriptEnabled(true);
				wv_img_captured.getSettings().setLoadWithOverviewMode(true);
				wv_img_captured.getSettings().setUseWideViewPort(true);
				wv_img_captured.getSettings().setBuiltInZoomControls(true);
				wv_img_captured.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
				webviewloader.DisplayImage("file://" + picturePath, wv_img_captured);
				Log.i("pic path", picturePath);
				wv_img_captured.setVisibility(View.VISIBLE);
				// bitmap = BitmapFactory.decodeFile(picturePath);
				// byte[] bytearray = null;
				// try
				// {
				// InputStream is = new FileInputStream(picturePath);
				// if(picturePath != null)
				// try{
				// bytearray = streamToBytes(is);
				// baNew = bytearray;
				// }finally{
				// is.close();
				// }
				// }catch (Exception e)
				// {
				// e.printStackTrace();
				// }

				// if (bt != null) {
				// bt.recycle();
				// bt = null;
				// }
				// ByteArrayOutputStream bao = new ByteArrayOutputStream(1000);
				// // bt = Bitmap.createScaledBitmap(bitmap, 512, 384, true);
				// bt = Bitmap.createScaledBitmap(decodeUri(picturePath), 100,
				// 100, true);
				// bt.compress(Bitmap.CompressFormat.JPEG, 100, bao);
				//
				// ba = null;
				// ba = bao.toByteArray();
				// Log.i("byte array", "" + ba);
				// // baNew = bao.toByteArray();
				// base64_str = "";
				// base64_str = Base64.encodeToString(ba, Base64.DEFAULT);
				// bt.recycle();

			} else if (requestCode == 1) {

				File f = new File(Environment.getExternalStorageDirectory().toString());

				for (File temp : f.listFiles()) {

					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}

				try {
					String current_date = Drunk_Drive.date;
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

					String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Hyd-E Ticket"
							+ File.separator + "Drunk&Drive" + File.separator + current_date;
					File camerapath = new File(path);

					if (!camerapath.exists()) {
						camerapath.mkdirs();
					}
					f.delete();
					OutputStream outFile = null;
					File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

					try {
						Log.i("Camera Path :::", "" + file.getAbsolutePath());

						outFile = new FileOutputStream(file);
						Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
						Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																	// bitmap to
																	// dwaw into

						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setTextSize(80);
						paint.setTextAlign(Paint.Align.CENTER);

						int xPos = (canvas.getWidth() / 2);
						int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

						canvas.drawText("Date & Time: " + Current_Date, xPos, yPos + 300, paint);
						canvas.drawText("Lat :" + latitude, xPos, yPos + 400, paint);
						canvas.drawText("Long :" + longitude, xPos, yPos + 500, paint);

						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
						outFile.flush();
						outFile.close();
						new SingleMediaScanner(this, file);

					} catch (FileNotFoundException e) {
						e.printStackTrace();

					} catch (IOException e) {
						e.printStackTrace();

					} catch (Exception e) {
						e.printStackTrace();
					}

					Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
					Canvas canvas = new Canvas(mutableBitmap); // bmp is the
																// bitmap to
																// dwaw into
					Paint paint = new Paint();
					paint.setColor(Color.RED);
					paint.setTextSize(80);
					paint.setTextAlign(Paint.Align.CENTER);
					int xPos = (canvas.getWidth() / 2);
					int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

					canvas.drawText("Date & Time: " + Current_Date, xPos, yPos + 300, paint);
					canvas.drawText("Lat :" + latitude, xPos, yPos + 400, paint);
					canvas.drawText("Long :" + longitude, xPos, yPos + 500, paint);

					offender_image.setImageBitmap(mutableBitmap);

					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
					byteArray = bytes.toByteArray();
					image_data_tosend = Base64.encodeToString(byteArray, Base64.NO_WRAP);
					offender_image.setVisibility(View.VISIBLE);
					Log.i("image_data_tosend ::", "" + image_data_tosend);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				picturePath = "";
				picturePath = null;
			}
		}
	}

	// decodes image and scales it to reduce memory consumption
	@SuppressWarnings("unused")
	private Bitmap decodeUri(String selectedImage) {

		try {
			// Decode image size
			// fm_ll.setBackgroundResource(0);
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(selectedImage, o);
			// The new size we want to scale to
			final int REQUIRED_SIZE = 400;
			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
					break;
				}
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
				Log.i("Scale bule Values", " " + scale);
			}
			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Runtime.getRuntime().gc();
			return BitmapFactory.decodeFile(selectedImage, o2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void LocationAndIMEIValues() {
		// TODO Auto-generated method stub
		m_locationlistner = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = m_locationlistner.getBestProvider(criteria, false);
		m_locationlistner.requestLocationUpdates(provider, 0, 0, this);
		location = m_locationlistner.getLastKnownLocation(provider);
		// onLocationChanged(location);
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			// speed = location.getSpeed();
		} else {
			latitude = 0.0;
			longitude = 0.0;
		}
	}

	private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device

			return false;
		}
	}

	public void showToast(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();

		ViewGroup group = (ViewGroup) toast.getView();
		TextView messageTextView = (TextView) group.getChildAt(0);
		messageTextView.setTextSize(24);

		toastView.setBackgroundResource(R.drawable.toast_background);
		toast.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

	}

	@Override
	public void onBackPressed() {
		showToast("Please Click on Back Button to go back");
	}
}
