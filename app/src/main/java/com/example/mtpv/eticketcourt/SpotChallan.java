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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.PidSecEncrypt;
import com.example.mtpv.eticketcourt.service.ServiceHelper;
import com.example.mtpv.eticketcourt.service.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;


@SuppressWarnings("deprecation")
@SuppressLint({ "DefaultLocale", "WorldReadableFiles", "SetJavaScriptEnabled", "SimpleDateFormat", "InflateParams" })
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SpotChallan extends Activity
		implements OnClickListener, LocationListener, android.widget.CompoundButton.OnCheckedChangeListener {

	public static String btn_otp_flag = "0";

	public static String helemt_rc_value;
	public static String helemt_adhar_value;
	public static String helemt_dl_value;

	int edt_regncid_spotchallanMAX_LENGTH = 4;
	int edt_regncidname_spotchallanLENGTH = 4;
	int edt_regncid_lastnum_spotchallanMAX_LENGTH = 4;

	public static Double total = 0.0;
	TextView star;

	public static CheckBox check;
	public static String imgSelected = "0";
	Context context;
	int selected_ocuptn = -1;

	public static String[] temp_violations_ids;
	public static String violation_code;

	public static LinearLayout vehicle_type;
	public static CheckBox police_vehcle;
	public static CheckBox govt_vehcle;
	public static String is_govt_police = "9", extraPassengers = "1";

	public static boolean passngerFLG = false, llrFLG = false, spotPamentFLG = false;

	public static String aadhaar;
	public static String licence_no;

	public static String otp_number = "", driver_mobileNo = "", minor_valueCode = "";

	public static EditText et_regcid_spot;// AP09
	public static EditText et_vchl_num_spot;// CC
	public static EditText et_last_num_spot;// 3014
	public static EditText et_driver_lcnce_num_spot;
	public static EditText et_payment_card_pin;
	public static EditText et_id_proof_spot;
	public static EditText et_driver_contact_spot;
	public static EditText et_remarks_spot;
	public static EditText et_aadharnumber_spot;
	public static EditText et_confirm_otp;
	public static EditText et_drivername_iOD;
	public static EditText et_driverFatherName_iOD;
	public static EditText et_driver_address_iOD;
	public static EditText et_driver_city_iOD;

	public static TextView tv_spot_or_vhclehistory_header;// VECHICLE HIS OR
	// SPOT
	public static TextView tv_vehicle_details_header_spot;
	public static TextView tv_licence_details_header_spot;
	public static TextView tv_spotChallanTwo_header;

	/* RTA COMPLETE DETAILS */
	public static TextView tv_vhle_no_spot;
	public static TextView tv_owner_name_spot;
	public static TextView tv_address_spot;
	public static TextView tv_city_spot;
	public static TextView tv_maker_name_spot;
	public static TextView tv_maker_class_spot;
	public static TextView tv_color_spot;
	public static TextView tv_engine_no_spot;
	public static TextView tv_chasis_no_spot;

	/* LICENCE DETAILS */
	public static ImageView licence_image;
	public static TextView tv_licnce_ownername_spot;
	public static TextView tv_lcnce_father_name_spot;
	public static TextView tv_lcnce_phone_number_spot;
	public static TextView tv_lcnce_address_spot;
	public static TextView dl_no;

	/* PENDING CHALLANS DETAILS */
	public static TextView tv_total_pending_challans;
	public static TextView tv_toal_amount_pending_challans;
	public static TextView selected_pendingamnt_spotchallan;
	public static TextView tv_grand_total_spot;

	/* AADHAR public static TextView */
	public static TextView tv_aadhar_header;
	public static TextView tv_aadhar_user_name;
	public static TextView tv_aadhar_care_off;
	public static TextView tv_aadhar_address;
	public static TextView tv_aadhar_mobile_number;
	public static TextView tv_aadhar_gender;
	public static TextView tv_aadhar_dob;
	public static TextView tv_aadhar_uid;
	public static TextView tv_aadhar_eid;
	public static TextView tv_violation_amnt;

	ImageView img_aadhar_image, qr_code;

	public static boolean vHisFlg = false, radioBtnFlg = false, spotchargshtFLG = false, otherFLG = false,
			relsedocFLG = false, dobFLG = false;

	public static String DLvalidFLG = "VALID";
	public static Button btn_send_otp_to_mobile;

	public static Button btn_wheller_code;
	public static Button btn_violation;
	public static Button btn_get_details_spot;
	public static Button btn_first_tosecond_spot;
	public static Button btn_first_cancel_spot;
	public static Button btn_id_proof_spot;
	public static Button btn_move_to_first;
	public static Button btn_final_submit;
	public static Button btn_cnfirm_otp;

	byte[] byteArray;

	static String date;

	public static String Current_Date;
	public static String final_image_data_tosend = null;

	public static ImageView offender_image;

	ImageButton ibtn_camera;
	ImageButton ibtn_gallery, images;

	RelativeLayout rl_rta_details_layout;
	RelativeLayout rl_card_details;// visa,master,maestro
	RelativeLayout rl_detained_items;// RC:PERMIT:LICENCE:VECHICLE:NONE

	LinearLayout rl_licence_details_layout;
	LinearLayout ll_pending_challans;
	LinearLayout ll_cash_or_card;
	LinearLayout ll_aadhar_layout;
	LinearLayout ll_camera_gallery;
	LinearLayout ll_detained_items_root;// under spot yes or no
	LinearLayout ll_spot_payment_root;// is_it_spot : yes / no
	LinearLayout ll_confrim_otp;
	LinearLayout ll_is_owner_driver;
	LinearLayout ll_drivertype_rgbtn;

	TelephonyManager telephonyManager;

	Utils utils;

	LocationManager m_locationlistner;

	android.location.Location location;

	public static double latitude = 0.0;
	public static double longitude = 0.0;
	public static double total_amount = 0;
	public static double grand_total = 0;// max violation amount

	DBHelper db;
	Cursor c_whlr, c_occptn;

	String[] violationPoints_offnce_code, violationPoints_wheeler_cd, violationPoints_penality_points;

	String provider = "";
	public static String completeVehicle_num_send = "", regncode_send = "", regnName_send = "", vehicle_num_send = "",
			fake_veh_chasisNo = "";// complete vno to send to
	// service
	String NETWORK_TXT = "";
	String imei_send = "";
	String simid_send = "";
	String macAddress = "";
	String wheler_code = "Select Wheeler";
	public static String whlr_code_send = "";
	String cam_imag = "";

	public static String[] rta_details_spot_master, offender_remarks_resp_master, helmet_remarks, Wheeler_check,
			licence_details_spot_master;
	String[] wheeler_code_arr_spot, wheeler_name_arr_spot;// wheeler code
	// details
	// to get values
	// after
	// secondary split
	// i.e,"@"
	// ArrayList<String>
	// violation_list;// to
	// bind to violation to
	// dialog
	ArrayList<String> violation_list;// to bind to violation to dialog
	ArrayList<String> violation_description;
	ArrayList<String> violation_section;
	ArrayList<String> violation_offence_Code;
	ArrayList<String> violation_min_amount;
	ArrayList<String> violation_max_amount;
	ArrayList<String> violation_avg_amount;
	ArrayList<String> violation_positions;
	ArrayList<String> violation_rg_ids;
	ArrayList<String> violation_checked_violations;
	// ArrayList<Boolean> violation_checked_items_status;
	LinkedHashMap<String, String> check_map;
	HashMap<String, String> check_all_ids;
	HashMap<String, String> vioCodeDescMap;

	int j = 1;// for checked violations

	int selected_wheller_code = -1;
	int selected_violation_list = -1;
	int selected_id_proof = -1;

	int val_detained_items;// DETAINED ITEMS
	int val_spot_payment;// SPOT PAYMENT
	public static final int PROGRESS_DIALOG = 0;
	final int WHEELER_CODE = 1;
	final int VIOLATIONS_DIALOG = 2;
	final int SECOND_SPOTSCREEN_DIALOG = 3;
	final int ID_PROOF_DIALOG = 4;
	final int DYNAMIC_VIOLATIONS = 5;
	final int FAKE_NUMBERPLATE_DIALOG = 6;
	final int OTP_CNFRMTN_DIALOG = 7;
	final int OCCUPATION_DIALOG = 10;

	StringBuffer message = new StringBuffer();

	/* GPS DETAILS */
	/* GPS VALUES */
	// flag for GPS status
	boolean isGPSEnabled = false;

	public static String aadhr_Points, DL_Points, aadhr_point_frm_json, dl_point_frm_json, aadhaar_offence_year,
			dl_offence_year;

	// flag for network status
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	ArrayList<Boolean> detained_items_status;
	ArrayList<String> releasedDetained_items_list;// to bind all values
	StringBuffer releasedDetained_items_list_toSend;
	// vehicle
	// history

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	RadioGroup radiogrp_release_detained_items;
	RadioButton radiobutton_release_yes_no, radioGroupButton_isOwner, radioGroupButton_isDriver;
	RadioButton radioGroupButton_detaineditems_yes, radioGroupButton_detaineditems_no;

	/* SPOT SECOND SCREEN START */
	RadioGroup radiogrp_spot_payment;
	RadioGroup radiogrp_cash_or_card;
	public static RadioButton radiogrp_spot_payment_yes_no, radioGroupButton_spotpayment0,
			radioGroupButton_spotpayment1, radioGroupButton_spotpaymentNo, radioGroupButton_spotpaymentYes,
			radioGroupButton_cashcard1, radioGroupButton_cashcard0;
	public static boolean cardFLG = false;
	LinearLayout ll_detained_itemlist_layout;
	LinearLayout ll_grand_total;
	LinearLayout[] ll;
	CheckBox[] cb;

	CheckBox chck_detainedItems_rc;
	CheckBox chck_detainedItems_vhcle;
	CheckBox chck_detainedItems_licence;
	CheckBox chck_detainedItems_permit;
	CheckBox chck_detainedItems_none;
	static StringBuilder sb_detained_items;

	TextView textView_header_spot_challan_xml;

	/* SPOT SECOND SCREEN END */

	/* FOR IMAGES FROM GALLER OR CAMERA */
	int RESULT_LOAD_IMAGE = 0;
	@SuppressWarnings("unused")
	private static final int CAMERA_REQUEST = 1888;
	WebviewLoader webviewloader;
	String picturePath = "";
	WebView wv_generate;
	FileOutputStream fo;

	String[] id_proof_arr = { "Email ID", "Pancard Number", "Passport Number", "VoterId Number" };

	String[] id_proof_hints_arr = { "Enter Email ID", "Enter Pancard Number", "Enter Passport Number",
			"Enter VoterId Number" };

	/* VALUES TO PUSH TO SERVICE */
	String MODULE_CODE_FIX = "0";
	String CHALLAN_NUM_FIX = "";
	String SERVICE_CODE_FIX = "";
	String ONLINE_MODE_FIX = "0";

	/* DETAILS TO PUSH TO SERVICE FOR MOBILE SPOT PAYMENT */
	String imgEvidence = "0";// if image then "1" else "0"

	/* DATE & TIME START */
	SimpleDateFormat date_format, date_format2;
	Calendar calendar;
	int present_date;
	int present_month;
	int present_year;

	int present_hour;
	int present_minutes;

	String present_date_toSend = "";
	StringBuffer present_time_toSend;

	/* DATE & TIME END */

	String bookedPScode_send_from_settings;
	String bookedPSname_send_from_settings;
	String point_code_send_from_settings;
	String point_name_send_from_settings;
	String exact_location_send_from_settings;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	String emailId_to_send = "";
	String pancard_to_send = "";
	String passport_to_send = "";
	String VoterId_to_send = "";
	String is_it_spot_send = "0";// yes-1 else No-0
	String licStatus_send = "";// if lic num entered

	StringBuffer violations_details_send;
	StringBuffer violation_desc_append;// after selecting the violations to
	// append to btn_violations

	public static StringBuffer sb_selected_penlist_send;

	public static ArrayList<String> sb_selected_penlist;
	public static ArrayList<String> sb_selected_penlist_positions;

	/* FTP UPLOAD DETAILS */
	FTPClient client;
	String[] FTP_HOST_PORT_SPOT;
	String ftp_host_spot = "";

	/* DYNAMIC LAYOUTS START */
	LinearLayout ll_dynamic_violations_root_static;
	LinearLayout[] ll_dynamic_vltns;
	Spinner[] spinner_violation;

	// String[] spinner_selectors;
	TextView[] tv_dynamic_vltn_name;
	CheckBox[] check_dynamic_vltn;
	RadioGroup[] rg_dynamic_vltn;
	RadioButton[] rbtn_dynamic_vltn;

	/* DYNAMIC LAYOUTS END */
	LinearLayout ll_extra_people;// no. of people
	EditText et_extra_people;// no. of people

	public static String otp_status = "";
	public static String otp_msg = "";
	public static String otpValue = "";
	public static String vStatusConfirmationYN = "", ll_validationString = "";

	RadioGroup rg_isOwner_isDriver;
	RadioButton rb_isOwner_isDriver;

	RadioGroup nationality_status;
	RadioButton rb_indian, rb_nri;

	LinearLayout passport_layout;
	public static EditText et_passport;
	public static String citizen_status = "indian";

	public static RadioGroup radioGrp_regNo_EngnNo_Chasis;
	public static RadioButton radioGroupButton_regNo, radioGroupButton_engineNo, radioGroupButton_chasisNo;
	public static LinearLayout ll_mainsub_root, ll_engineNo, ll_chasisNo, ll_regno;
	public static EditText et_engineNo, et_chasisNo;

	public static String RegNo_EngNo_ChasisNo = "";
	public static boolean EngneFLG = false, regNoFLG = false, chasisFLG = false, veh_HisFLG = false;
	public static int isitTr = 1;

	public static Button btn_select_profession;
	public static EditText edt_prfession_name, edt_prfession_Address, edt_email_ID;

	String ocuptn_title = "Select Occupation";
	String[] occup_code_arr, occup_name_arr;
	LinearLayout proffession_layout;
	String violation_code_value;

	public static String profession_code = "";

	VerhoeffCheckDigit ver = new VerhoeffCheckDigit();

	public static String Signature, reqString = "", pending_challans_amount;
	public static String merchantId = "800026";
	public static String merchantKey = "57Y5LEJFUK88CCDSB89D6JI4";
	public static int INVOKE_LASTMILE_PAY = 20;

	@SuppressWarnings("unused")
	private boolean isValidEmaillId(String email) {

		return Pattern
				.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
						+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
						+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
						+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
				.matcher(email).matches();
	}

	public static String Compleate_address;

	SharedPreferences sharedpreference;
	SharedPreferences.Editor editors;

	public static String pidCode = null, pidName = null, psCd = null, psName = null, cadre_code = null,
			cadre_name = null, pass_word = null, off_phone_no = null, current_version = null, rta_data_flg = null,
			dl_data_flg = null, aadhaar_data_flg = null, otp_no_flg = null, cashless_flg = null, mobileNo_flg = null;
	CountDownTimer newtimer;

	public static int WHITE = 0xFFFFFFFF;
	public static int BLACK = 0xFF000000;
	public final static int width = 500;

	@SuppressWarnings("unused")
	private Calendar cal;
	@SuppressWarnings("unused")
	private int day;
	@SuppressWarnings("unused")
	private int month;
	@SuppressWarnings("unused")
	private int year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.spot_challan);

		imgSelected = "0";

		sb_selected_penlist_positions = new ArrayList<String>();
		sb_selected_penlist_send = new StringBuffer("");

		// dateNTimeDialog();

		final_image_data_tosend = null;

		newtimer = new CountDownTimer(1000000000, 50) {

			public void onTick(long millisUntilFinished) {
				date = (DateFormat.format("dd/MM/yyyy hh:mm:ss", new java.util.Date()).toString());
				Calendar c1 = Calendar.getInstance();
				@SuppressWarnings("unused")
				int mSec = c1.get(Calendar.MILLISECOND);
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String strdate1 = sdf1.format(c1.getTime());
				date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
				// id_date.setText(strdate1);
				Current_Date = strdate1;
			}

			public void onFinish() {
			}
		};
		newtimer.start();

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

		LoadUIcomponents();

		getDateAndTime();

		total_amount = 0;
		grand_total = 0;
		imgEvidence = "0";// if image then "1" else "0"
		licStatus_send = "";// if lic num entered

		violation_list = new ArrayList<String>();
		violation_description = new ArrayList<String>();
		violation_section = new ArrayList<String>();
		violation_positions = new ArrayList<String>();
		violation_max_amount = new ArrayList<String>();
		violation_avg_amount = new ArrayList<String>();
		violation_min_amount = new ArrayList<String>();
		violation_offence_Code = new ArrayList<String>();
		violation_checked_violations = new ArrayList<String>();
		violation_rg_ids = new ArrayList<String>();
		// violation_checked_items_status = new ArrayList<Boolean>();

		utils = new Utils();
		NETWORK_TXT = getResources().getString(R.string.newtork_txt);

		/* TO GET WHEELER CODE DETAILS */
		db = new DBHelper(this);
		try {
			db.open();
			// WHEELER CODE
			c_whlr = DBHelper.db.rawQuery("select * from " + DBHelper.wheelercode_table, null);
			if (c_whlr.getCount() == 0) {
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

		/* TO GET OCCUPATION CODE DETAILS */
		try {
			db.open();
			// WHEELER CODE
			c_occptn = DBHelper.db.rawQuery("select * from " + DBHelper.occupation_table, null);
			if (c_occptn.getCount() == 0) {
			} else {

				occup_code_arr = new String[c_occptn.getCount()];
				occup_name_arr = new String[c_occptn.getCount()];

				int count = 0;
				while (c_occptn.moveToNext()) {
					occup_code_arr[count] = c_occptn.getString(1);
					occup_name_arr[count] = c_occptn.getString(2);
					count++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			c_occptn.close();
			db.close();
		}
		c_occptn.close();
		db.close();

		preferences = getSharedPreferences("preferences", MODE_WORLD_READABLE);
		editor = preferences.edit();

		/* PREFERENCES */
		/*
		 * preferences = getSharedPreferences("preferences",
		 * MODE_WORLD_READABLE); editor = preferences.edit(); FOR CHECKING FTP
		 * DETAILS ftp_host_spot = preferences.getString("ftpurl", "host"); if
		 * (!ftp_host_spot.equals("host")) { FTP_HOST_PORT_SPOT =
		 * ftp_host_spot.split("\\:"); Log.i("DYNAMIC FTP DETAILS", "" +
		 * FTP_HOST_PORT_SPOT[0] + "\nPort : " + FTP_HOST_PORT_SPOT[1]); }
		 */
	}

	@SuppressWarnings("unused")
	private void dateNTimeDialog() {
		// TODO Auto-generated method stub

		/******* data and time alert start **********/
		TextView title = new TextView(SpotChallan.this);
		title.setText("ALERT");
		title.setBackgroundColor(Color.RED);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(26);
		title.setTypeface(title.getTypeface(), Typeface.BOLD);
		title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
		title.setPadding(20, 0, 20, 0);
		title.setHeight(70);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
				AlertDialog.THEME_HOLO_LIGHT);
		alertDialogBuilder.setCustomTitle(title);
		alertDialogBuilder.setIcon(R.drawable.dialog_logo);
		alertDialogBuilder.setMessage("\n Please set your device Date & Time properly !!\n");
		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
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
		/******* data and time alert end **********/
	}

	private void LoadUIcomponents() {
		// TODO Auto-generated method stub
		// hardcode
		et_regcid_spot = (EditText) findViewById(R.id.edt_regncid_spotchallan_xml);
		// et_regcid_spot.setText("AP29");
		et_vchl_num_spot = (EditText) findViewById(R.id.edt_regncidname_spotchallan_xml);
		// et_vchl_num_spot.setText("BS");
		et_last_num_spot = (EditText) findViewById(R.id.edt_regncid_lastnum_spotchallan_xml);
		// et_last_num_spot.setText("7402");

		offender_image = (ImageView) findViewById(R.id.offender_image);
		offender_image.setVisibility(View.GONE);

		et_regcid_spot.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (et_regcid_spot.getText().toString().length() == edt_regncid_spotchallanMAX_LENGTH) {
					et_vchl_num_spot.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		et_vchl_num_spot.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (et_vchl_num_spot.getText().toString().length() == edt_regncidname_spotchallanLENGTH) {
					et_last_num_spot.requestFocus();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		et_last_num_spot.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (et_last_num_spot.getText().toString().length() == edt_regncid_lastnum_spotchallanMAX_LENGTH) {
					et_driver_lcnce_num_spot.requestFocus();
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

		radioGroupButton_isOwner = (RadioButton) findViewById(R.id.radioGroupButton_isOwner);
		radioGroupButton_isDriver = (RadioButton) findViewById(R.id.radioGroupButton_isDriver);

		// hardcode
		et_driver_lcnce_num_spot = (EditText) findViewById(R.id.edt_driverdlno_spotchallan_xml);
		// et_driver_lcnce_num_spot.setText("7461WGL1998OD");
		et_aadharnumber_spot = (EditText) findViewById(R.id.edt_aadharno_spotchallan_xml);
		// et_aadharnumber_spot.setText("322847907255");

		et_drivername_iOD = (EditText) findViewById(R.id.edt_driverdname_isOD);
		et_driverFatherName_iOD = (EditText) findViewById(R.id.edt_driverfathername_isOD);
		et_driver_address_iOD = (EditText) findViewById(R.id.edt_address_isOD);
		et_driver_city_iOD = (EditText) findViewById(R.id.edt_city_isOD);

		btn_wheller_code = (Button) findViewById(R.id.btn_whlr_code_spotchallan_xml);
		btn_violation = (Button) findViewById(R.id.btn_violation_spotchallan_xml);
		btn_get_details_spot = (Button) findViewById(R.id.btngetrtadetails_spotchallan_xml);

		btn_first_tosecond_spot = (Button) findViewById(R.id.btn_next_spotchallan_xml);
		btn_first_cancel_spot = (Button) findViewById(R.id.btn_cancel_spotchallan_xml);

		tv_spot_or_vhclehistory_header = (TextView) findViewById(R.id.textView_header_spot_challan_xml);

		/* TO SHOW vehicel DETAILS AND LICENCE DETAILS FOUND OR NOT */
		tv_vehicle_details_header_spot = (TextView) findViewById(R.id.textView_regdetails_header_spotchallan_xml);
		tv_licence_details_header_spot = (TextView) findViewById(R.id.textView_licence_header_spotchallan_xml);

		/* RTA COMPLETE DETAILS */
		tv_vhle_no_spot = (TextView) findViewById(R.id.tvregno_spotchallan_xml);
		tv_owner_name_spot = (TextView) findViewById(R.id.tvownername_spotchallan_xml);
		tv_address_spot = (TextView) findViewById(R.id.tv_addr_spotchallan_xml);
		// tv_city_spot = (TextView) findViewById(R.id.tv_city_spotchallan_xml);
		tv_maker_name_spot = (TextView) findViewById(R.id.tv_makername_spotchallan_xml);
		// tv_maker_class_spot = (TextView)
		// findViewById(R.id.tv_makerclass_spotchallan_xml);
		// tv_color_spot = (TextView)
		// findViewById(R.id.tv_color_spotchallan_xml);
		tv_engine_no_spot = (TextView) findViewById(R.id.tv_engineno_spotchallan_xml);
		tv_chasis_no_spot = (TextView) findViewById(R.id.tv_chasis_spotchallan_xml);

		/* AADHRA DETAILS */
		ll_aadhar_layout = (LinearLayout) findViewById(R.id.ll_aadhardetails_spot_challan_xml);
		tv_aadhar_header = (TextView) findViewById(R.id.tvadhardetails_header_label_spotchallan_xml);
		tv_aadhar_user_name = (TextView) findViewById(R.id.tvaadharname_spotchallan_xml);
		tv_aadhar_care_off = (TextView) findViewById(R.id.tvcareof_spotchallan_xml);
		tv_aadhar_address = (TextView) findViewById(R.id.tvaddress_spotchallan_xml);
		tv_aadhar_mobile_number = (TextView) findViewById(R.id.tvmobilenumber_spotchallan_xml);
		tv_aadhar_gender = (TextView) findViewById(R.id.tvgender_spotchallan_xml);
		tv_aadhar_dob = (TextView) findViewById(R.id.tvdob_spotchallan_xml);
		tv_aadhar_uid = (TextView) findViewById(R.id.tvuid_spotchallan_xml);
		// tv_aadhar_eid = (TextView) findViewById(R.id.tveid_spotchallan_xml);
		img_aadhar_image = (ImageView) findViewById(R.id.imgv_aadhar_photo_spotchallan_xml);
		qr_code = (ImageView) findViewById(R.id.qr_code);
		ll_aadhar_layout.setVisibility(View.GONE);
		tv_aadhar_header.setVisibility(View.GONE);

		/* LICENCE COMPLETE DETAILS */
		licence_image = (ImageView) findViewById(R.id.imgv_licence_spotchallan_xml);
		tv_licnce_ownername_spot = (TextView) findViewById(R.id.tvlcnceownername_spotchallan_xml);
		tv_lcnce_father_name_spot = (TextView) findViewById(R.id.tvlcnce_fname_spotchallan_xml);
		tv_lcnce_phone_number_spot = (TextView) findViewById(R.id.tv_lcnce_mobnum_spotchallan_xml);
		tv_lcnce_address_spot = (TextView) findViewById(R.id.tv_lcnce_Address_spotchallan_xml);
		dl_no = (TextView) findViewById(R.id.dl_no);

		rl_rta_details_layout = (RelativeLayout) findViewById(R.id.rl_detailsresponse_spotchallan_xml);
		rl_licence_details_layout = (LinearLayout) findViewById(R.id.rl_licences_spotchallan_xml);
		ll_pending_challans = (LinearLayout) findViewById(R.id.ll_pendingchallans_spot_xml);
		ll_grand_total = (LinearLayout) findViewById(R.id.ll_4_spot_challan_xml);

		/* RADIO BUTTONS FOR NATIONALITY STATUS */
		nationality_status = (RadioGroup) findViewById(R.id.nationality_status);
		rb_indian = (RadioButton) findViewById(R.id.rb_indian);
		rb_nri = (RadioButton) findViewById(R.id.rb_nri);

		passport_layout = (LinearLayout) findViewById(R.id.passport_layout);
		et_passport = (EditText) findViewById(R.id.et_passport);

		/* PENDING CHALLANS */
		tv_total_pending_challans = (TextView) findViewById(R.id.tv_pendingchallans_total_spotchallan_xml);
		tv_toal_amount_pending_challans = (TextView) findViewById(R.id.tv_pendingamount_spotchallan_xml);
		tv_grand_total_spot = (TextView) findViewById(R.id.tv_grand_totalamnt_spotchallan_xml);
		tv_violation_amnt = (TextView) findViewById(R.id.tv_violtaionamnt_spotchallan_xml);
		tv_grand_total_spot.setText("Rs . " + total_amount);

		/* CAMERA & GALLERY */
		ibtn_camera = (ImageButton) findViewById(R.id.imgv_camera_capture_spotchallan_xml);
		ibtn_gallery = (ImageButton) findViewById(R.id.imgv_gallery_spotchallan_xml);

		//wv_generate = (WebView) findViewById(R.id.webView_image_spotchallan_xml);

		star = (TextView) findViewById(R.id.star);

		/* VEHICLE HISTORY */
		ll_camera_gallery = (LinearLayout) findViewById(R.id.ll_main_root_spotchallan_xml);

		rg_isOwner_isDriver = (RadioGroup) findViewById(R.id.radioGroup_isOwner_isDriver);
		ll_is_owner_driver = (LinearLayout) findViewById(R.id.ll_isOwner_isDriver);
		ll_drivertype_rgbtn = (LinearLayout) findViewById(R.id.ll_drivertype_spot_xml);

		/* CLICK LISTENERS */
		btn_wheller_code.setOnClickListener(this);
		btn_violation.setOnClickListener(this);
		btn_get_details_spot.setOnClickListener(this);
		ll_pending_challans.setOnClickListener(this);
		btn_first_tosecond_spot.setOnClickListener(this);
		btn_first_cancel_spot.setOnClickListener(this);

		ibtn_camera.setOnClickListener(this);
		ibtn_gallery.setOnClickListener(this);

		/* TO HIDE HEADER FOR DETAILS */
		tv_vehicle_details_header_spot.setVisibility(View.GONE);
		tv_licence_details_header_spot.setVisibility(View.GONE);
		rl_rta_details_layout.setVisibility(View.GONE);
		// rl_licence_details_layout.setVisibility(View.GONE);

		/* NO OF PEOPLE LABEL */
		ll_extra_people = (LinearLayout) findViewById(R.id.ll_extraviolations_spotchallan_xml);
		et_extra_people = (EditText) findViewById(R.id.edt_extarviolat_spotchallan_xml);
		ll_extra_people.setVisibility(View.GONE);

		Dashboard.rta_details_request_from = "";
		ServiceHelper.pending_challans_details = new String[0][0];
		ServiceHelper.pending_challans_master = new String[0];

		// releasedDetained_items_list_toSend.remove(releasedDetained_items_list_toSend);
		ll_regno = (LinearLayout) findViewById(R.id.ll_regno);
		ll_engineNo = (LinearLayout) findViewById(R.id.ll_engineNo);
		ll_chasisNo = (LinearLayout) findViewById(R.id.ll_chasisNo);

		radioGrp_regNo_EngnNo_Chasis = (RadioGroup) findViewById(R.id.radioGrp_regNo_EngnNo_Chasis);

		radioGroupButton_regNo = (RadioButton) findViewById(R.id.radioGroupButton_regNo);
		radioGroupButton_engineNo = (RadioButton) findViewById(R.id.radioGroupButton_engineNo);
		radioGroupButton_chasisNo = (RadioButton) findViewById(R.id.radioGroupButton_chasisNo);

		et_engineNo = (EditText) findViewById(R.id.et_engineNo);
		et_chasisNo = (EditText) findViewById(R.id.et_chasisNo);

		ll_engineNo.setVisibility(View.GONE);
		ll_regno.setVisibility(View.VISIBLE);
		ll_chasisNo.setVisibility(View.GONE);

		radioGrp_regNo_EngnNo_Chasis.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radioGroupButton_regNo) {

					regNoFLG = true;
					EngneFLG = false;
					chasisFLG = false;
					isitTr = 1;

					ll_engineNo.setVisibility(View.GONE);
					ll_regno.setVisibility(View.VISIBLE);
					ll_chasisNo.setVisibility(View.GONE);

					et_regcid_spot.setText("");
					et_vchl_num_spot.setText("");
					et_last_num_spot.setText("");
					et_regcid_spot.requestFocus();
					et_driver_lcnce_num_spot.setText("");
					et_aadharnumber_spot.setText("");

					btn_wheller_code.setText("" + getString(R.string.select_wheeler_code));

					Log.i("RegNo_EngNo_ChasisNo 1", "" + regNoFLG);

				} else if (checkedId == R.id.radioGroupButton_engineNo) {

					EngneFLG = true;
					regNoFLG = false;
					chasisFLG = false;
					isitTr = 2;

					ll_engineNo.setVisibility(View.VISIBLE);
					et_engineNo.setText("");
					et_engineNo.requestFocus();

					ll_regno.setVisibility(View.GONE);
					ll_chasisNo.setVisibility(View.GONE);
					et_regcid_spot.setText("");
					et_vchl_num_spot.setText("");
					et_last_num_spot.setText("");

					et_driver_lcnce_num_spot.setText("");
					et_aadharnumber_spot.setText("");

					btn_wheller_code.setText("" + getString(R.string.select_wheeler_code));
					Log.i("RegNo_EngNo_ChasisNo 2", "" + EngneFLG);

				} else if (checkedId == R.id.radioGroupButton_chasisNo) {
					chasisFLG = true;
					EngneFLG = false;
					regNoFLG = false;
					isitTr = 3;

					ll_engineNo.setVisibility(View.GONE);
					ll_regno.setVisibility(View.GONE);
					ll_chasisNo.setVisibility(View.VISIBLE);

					et_chasisNo.setText("");
					et_regcid_spot.setText("");
					et_vchl_num_spot.setText("");
					et_last_num_spot.setText("");
					et_regcid_spot.requestFocus();

					et_driver_lcnce_num_spot.setText("");
					et_aadharnumber_spot.setText("");

					btn_wheller_code.setText("" + getString(R.string.select_wheeler_code));
					Log.i("RegNo_EngNo_ChasisNo 3", "" + chasisFLG);
				}
			}
		});

		Log.i("SPOT RESPONSE CLASS", "" + Dashboard.check_vhleHistory_or_Spot);

		if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
				|| (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

			btn_wheller_code.setVisibility(View.GONE);
			btn_violation.setVisibility(View.GONE);
			ll_grand_total.setVisibility(View.GONE);
			ll_camera_gallery.setVisibility(View.GONE);
			ll_is_owner_driver.setVisibility(View.VISIBLE);
			ll_drivertype_rgbtn.setVisibility(View.GONE);

			star.setVisibility(View.GONE);

			if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
				tv_spot_or_vhclehistory_header.setText("" + getResources().getString(R.string.vehicle_history));
				et_regcid_spot.requestFocus();

			} else if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
				tv_spot_or_vhclehistory_header
						.setText("" + getResources().getString(R.string.release_documents_one_line));
				et_regcid_spot.requestFocus();
				passport_layout.setVisibility(View.GONE);
				nationality_status.setVisibility(View.VISIBLE);
				rb_indian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						citizen_status = "";
						passport_layout.setVisibility(View.GONE);
						nationality_status.setVisibility(View.VISIBLE);
					}
				});

				rb_nri.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						passport_layout.setVisibility(View.VISIBLE);
						nationality_status.setVisibility(View.VISIBLE);
						citizen_status = et_passport.getText().toString().trim();
					}
				});
			}
			/* FOR SELECTED PENDING LIST POSITIONS */

		} else if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {

			btn_wheller_code.setVisibility(View.VISIBLE);
			btn_violation.setVisibility(View.VISIBLE);
			ll_grand_total.setVisibility(View.VISIBLE);
			ll_camera_gallery.setVisibility(View.VISIBLE);

			tv_spot_or_vhclehistory_header.setText("" + getResources().getString(R.string.spot_challan));

			sb_selected_penlist_positions = new ArrayList<String>();
			sb_selected_penlist_send = new StringBuffer("");

		} else if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {

			star.setVisibility(View.GONE);
			btn_wheller_code.setVisibility(View.VISIBLE);
			btn_violation.setVisibility(View.VISIBLE);
			ll_grand_total.setVisibility(View.VISIBLE);
			ll_camera_gallery.setVisibility(View.VISIBLE);

			tv_spot_or_vhclehistory_header.setText("" + getResources().getString(R.string.towing_one_line));

			sb_selected_penlist_positions = new ArrayList<String>();
			sb_selected_penlist_send = new StringBuffer("");
		}

		// ap12v9152
		else if ((Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory"))
				|| (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments"))) {
			btn_wheller_code.setVisibility(View.GONE);
			btn_violation.setVisibility(View.GONE);
			ll_grand_total.setVisibility(View.GONE);
			ll_camera_gallery.setVisibility(View.GONE);
			ll_is_owner_driver.setVisibility(View.VISIBLE);
			ll_drivertype_rgbtn.setVisibility(View.GONE);

			star.setVisibility(View.GONE);

			if (Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
				tv_spot_or_vhclehistory_header.setText("" + getResources().getString(R.string.vehicle_history));
				et_regcid_spot.requestFocus();

			} else if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")) {
				tv_spot_or_vhclehistory_header
						.setText("" + getResources().getString(R.string.release_documents_one_line));
				et_regcid_spot.requestFocus();
				passport_layout.setVisibility(View.GONE);
				nationality_status.setVisibility(View.VISIBLE);
				rb_indian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						citizen_status = "";
						passport_layout.setVisibility(View.GONE);
						nationality_status.setVisibility(View.VISIBLE);
					}
				});

				rb_nri.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						passport_layout.setVisibility(View.VISIBLE);
						nationality_status.setVisibility(View.VISIBLE);
						citizen_status = et_passport.getText().toString().trim();
					}
				});
			}
			/* FOR SELECTED PENDING LIST POSITIONS */

		}
	}

	public Boolean isOnline() {
		ConnectivityManager conManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
		return nwInfo != null;
	}

	@SuppressWarnings({ "unused" })
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btngetrtadetails_spotchallan_xml:
			// enabling default values
			ll_drivertype_rgbtn.setVisibility(View.GONE);
			ll_is_owner_driver.setVisibility(View.GONE);
			radioGroupButton_isDriver.setChecked(true);

			tv_vehicle_details_header_spot.setText("");
			tv_licence_details_header_spot.setText("");

			// initial status of vehicle history and fake number plate details
			VehicleHistoryPendingChallans.total_amount_selected_challans = 0.0;
			Fake_NO_Dialog.fake_action = null;

			tv_total_pending_challans.setText("0");
			tv_toal_amount_pending_challans.setText("0");
			tv_violation_amnt.setText("Rs . " + grand_total + "");
			tv_grand_total_spot.setText("");

			// FOR DISPLAYING TOTAL CALCULATED AMOUNT
			tv_grand_total_spot.setText("");
			tv_violation_amnt.setText("");

			regncode_send = "";// AP09
			regnName_send = "";// CC
			vehicle_num_send = "";// 3014

			// START DRIVER DETAILS MANUAL ENTRIES
			et_drivername_iOD.setEnabled(true);
			et_drivername_iOD.setClickable(true);

			et_driverFatherName_iOD.setEnabled(true);
			et_driverFatherName_iOD.setClickable(true);

			et_driver_address_iOD.setEnabled(true);
			et_driver_address_iOD.setClickable(true);

			et_driver_city_iOD.setEnabled(true);
			et_driver_city_iOD.setClickable(true);

			police_vehcle.setChecked(false);
			govt_vehcle.setChecked(false);
			// DRIVER DETAILS MANUAL ENTRIES END

			regncode_send = "" + et_regcid_spot.getText().toString().trim().toUpperCase();
			regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
			vehicle_num_send = "" + et_last_num_spot.getText().toString().trim().toUpperCase();

			// EXAMPLE AP09CC3014
			completeVehicle_num_send = "";
			completeVehicle_num_send = ("" + et_regcid_spot.getText().toString() + ""
					+ et_vchl_num_spot.getText().toString() + "" + et_last_num_spot.getText().toString());
			Log.i("**VE**", "" + completeVehicle_num_send);

			getLocation();
			total_amount = 0;

			btn_wheller_code.setText(getString(R.string.select_wheeler_code));
			btn_violation.setText(getString(R.string.select_violation));

			if (isitTr == 1 || regNoFLG == true) {
				Log.i("**isitTr 1***", "" + isitTr);

				if (et_regcid_spot.getText().toString().equals("")) {
					et_regcid_spot.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));
					et_regcid_spot.requestFocus();

				} else if (et_last_num_spot.getText().toString().equals("")) {
					et_last_num_spot.setError(Html.fromHtml("<font color='black'>Enter Vehicle Code</font>"));
					et_last_num_spot.requestFocus();

				} else if (!et_regcid_spot.getText().toString().equals("")
						&& !et_last_num_spot.getText().toString().equals("")) {
					// //new Async_checkAadhaarExists().execute();
					if ("N".equals(rta_data_flg.trim().toUpperCase())) {
						// IF NO DATA FOUND FILL THE DETAILS MANUALLY
						ll_is_owner_driver.setVisibility(View.VISIBLE);
					} else {
						// IF DATA FOUND THEN ASYNC TASK WILL BE CALLED
						// new Async_getRTADetails().execute();

						Async_getRTADetails rta_task = new Async_getRTADetails();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
							rta_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
						} else {
							rta_task.execute();
						}

						if (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
							Async_getLicenceDetails dl_task = new Async_getLicenceDetails();
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
								Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
								dl_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
							} else {
								dl_task.execute();
							}

							if (et_aadharnumber_spot.getText() != null
									&& et_aadharnumber_spot.getText().toString().trim().length() >= 1
									&& (!ver.isValid(et_aadharnumber_spot.getText().toString()))) {
								showToast("Please Enter Valid Adhaar Number");
								et_aadharnumber_spot.setError(
										Html.fromHtml("<font color='black'>Please Enter Valid Adhaar Number</font>"));
							} else if ((et_aadharnumber_spot.getText().toString().trim().length() == 12)
									|| (et_aadharnumber_spot.getText().toString().trim().length() == 28)) {
								if (isOnline()) {
									Async_getAadharDetails adhar_task = new Async_getAadharDetails();
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
										Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
										adhar_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
									} else {
										adhar_task.execute();
									}
								}
							}
						}

						Async_getPendingChallans pending_task = new Async_getPendingChallans();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
							pending_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
						} else {
							pending_task.execute();
						}

					}

					// if (et_driver_lcnce_num_spot.getText() != null
					// &&
					// et_driver_lcnce_num_spot.getText().toString().trim().length()
					// > 2) {
					// Dashboard.licence_details_request_from = "SPOT";
					// if ("Y".equals(dl_data_flg.trim().toUpperCase())) {
					// //new Async_getLicenceDetails().execute();
					// }
					// }

					/*
					 * if (et_aadharnumber_spot.getText() != null &&
					 * et_aadharnumber_spot.getText().toString().trim().length()
					 * < 12 &&
					 * et_aadharnumber_spot.getText().toString().trim().length()
					 * > 0) {
					 * showToast("Please Enter 12 digit Valid Aadhaar Number !!"
					 * ); et_aadharnumber_spot .setError(Html.
					 * fromHtml("<font color='black'>Enter  12 digit Aadhaar Number</font>"
					 * )); et_aadharnumber_spot.requestFocus(); }
					 */

					/*
					 * if (et_aadharnumber_spot.getText() != null &&
					 * et_aadharnumber_spot.getText().toString().trim().length()
					 * == 12 &&
					 * !ver.isValid(et_aadharnumber_spot.getText().toString().
					 * trim())) { TextView title = new TextView(this);
					 * title.setText("ALERT");
					 * title.setBackgroundColor(Color.RED);
					 * title.setGravity(Gravity.CENTER);
					 * title.setTextColor(Color.WHITE); title.setTextSize(26);
					 * title.setTypeface(title.getTypeface(), Typeface.BOLD);
					 * title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
					 * dialog_logo, 0, R.drawable.dialog_logo, 0);
					 * title.setPadding(20, 0, 20, 0); title.setHeight(70);
					 *
					 * AlertDialog.Builder alertDialogBuilder = new
					 * AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
					 * alertDialogBuilder.setCustomTitle(title);
					 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
					 * alertDialogBuilder
					 * .setMessage("\n It's a Invalid Aadhar, \nPlease Enter Valid Aadhar Number !! \n"
					 * ); alertDialogBuilder.setPositiveButton("Ok", new
					 * DialogInterface.OnClickListener() {
					 *
					 * @Override public void onClick(DialogInterface arg0, int
					 * arg1) { // et_aadharnumber_spot.setText("");
					 * et_aadharnumber_spot.setError(Html
					 * .fromHtml("<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"
					 * )); et_aadharnumber_spot.requestFocus(); } });
					 * alertDialogBuilder.setCancelable(false); AlertDialog
					 * alertDialog = alertDialogBuilder.create();
					 * alertDialog.show();
					 *
					 * alertDialog.getWindow().getAttributes();
					 *
					 * TextView textView = (TextView)
					 * alertDialog.findViewById(android.R.id.message);
					 * textView.setTextSize(28);
					 * textView.setTypeface(textView.getTypeface(),
					 * Typeface.BOLD); textView.setGravity(Gravity.CENTER);
					 *
					 * Button btn1 =
					 * alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
					 * btn1.setTextSize(22); btn1.setTextColor(Color.WHITE);
					 * btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
					 * btn1.setBackgroundColor(Color.RED);
					 *
					 * }
					 */ /*
						 * else if (et_aadharnumber_spot.getText().toString() !=
						 * null &&
						 * et_aadharnumber_spot.getText().toString().length() ==
						 * 12 &&
						 * ver.isValid(et_aadharnumber_spot.getText().toString()
						 * )) { if
						 * ("Y".equals(aadhaar_data_flg.trim().toUpperCase())) {
						 * new Async_getAadharDetails().execute(); } }
						 * ll_aadhar_layout.setVisibility(View.GONE);
						 * tv_aadhar_header.setVisibility(View.GONE); }
						 */

				}
			}
			/*
			 * else if (isitTr == 2 || EngneFLG == true) {
			 *
			 * if (!et_engineNo.getText().toString().equals("")) { // //new
			 * Async_checkAadhaarExists().execute();
			 *
			 * if (et_aadharnumber_spot.getText().toString().length() == 0) {
			 * new Async_getRTADetails().execute();
			 *
			 * } else if
			 * (et_aadharnumber_spot.getText().toString().trim().length() > 1 &&
			 * et_aadharnumber_spot.getText().toString().length() == 12 &&
			 * ver.isValid(et_aadharnumber_spot.getText().toString())) { new
			 * Async_getRTADetails().execute();
			 *
			 * } else if
			 * (et_aadharnumber_spot.getText().toString().trim().length() > 1 &&
			 * et_aadharnumber_spot.getText().toString().length() == 12 &&
			 * !ver.isValid(et_aadharnumber_spot.getText().toString())) {
			 * TextView title = new TextView(this); title.setText("ALERT");
			 * title.setBackgroundColor(Color.RED);
			 * title.setGravity(Gravity.CENTER);
			 * title.setTextColor(Color.WHITE); title.setTextSize(26);
			 * title.setTypeface(title.getTypeface(), Typeface.BOLD);
			 * title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
			 * dialog_logo, 0, R.drawable.dialog_logo, 0); title.setPadding(20,
			 * 0, 20, 0); title.setHeight(70);
			 *
			 * AlertDialog.Builder alertDialogBuilder = new
			 * AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			 * alertDialogBuilder.setCustomTitle(title);
			 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			 * alertDialogBuilder
			 * .setMessage("\n It's a Invalid Aadhar, \nPlease Enter Valid Aadhar Number !! \n"
			 * ); alertDialogBuilder.setPositiveButton("Ok", new
			 * DialogInterface.OnClickListener() {
			 *
			 * @Override public void onClick(DialogInterface arg0, int arg1) {
			 * // et_aadharnumber_spot.setText(""); } });
			 * alertDialogBuilder.setCancelable(false); AlertDialog alertDialog
			 * = alertDialogBuilder.create(); alertDialog.show();
			 *
			 * alertDialog.getWindow().getAttributes();
			 *
			 * TextView textView = (TextView)
			 * alertDialog.findViewById(android.R.id.message);
			 * textView.setTextSize(28);
			 * textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			 * textView.setGravity(Gravity.CENTER);
			 *
			 * Button btn1 =
			 * alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			 * btn1.setTextSize(22); btn1.setTextColor(Color.WHITE);
			 * btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			 * btn1.setBackgroundColor(Color.RED);
			 *
			 * } else if (et_engineNo.getText().toString().equals("")) {
			 * Toast.makeText(getApplicationContext(), (Html.
			 * fromHtml("<font color='black'>Enter Proper Vehicle Number</font>"
			 * )), Toast.LENGTH_LONG).show(); } }
			 *
			 * if (isOnline()) { rl_rta_details_layout.setVisibility(View.GONE);
			 * Dashboard.rta_details_request_from = "SPOT";
			 *
			 * if (et_aadharnumber_spot.getText().toString().trim() != null &&
			 * et_aadharnumber_spot.getText().toString().trim().length() > 1 &&
			 * et_aadharnumber_spot.getText().toString().length() != 12) {
			 *
			 * TextView title = new TextView(this); title.setText("ALERT");
			 * title.setBackgroundColor(Color.RED);
			 * title.setGravity(Gravity.CENTER);
			 * title.setTextColor(Color.WHITE); title.setTextSize(26);
			 * title.setTypeface(title.getTypeface(), Typeface.BOLD);
			 * title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
			 * dialog_logo, 0, R.drawable.dialog_logo, 0); title.setPadding(20,
			 * 0, 20, 0); title.setHeight(70);
			 *
			 * AlertDialog.Builder alertDialogBuilder = new
			 * AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			 * alertDialogBuilder.setCustomTitle(title);
			 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			 * alertDialogBuilder.
			 * setMessage("\n Aadhaar Number Length must be 12 digits!! \n");
			 * alertDialogBuilder.setPositiveButton("Ok", new
			 * DialogInterface.OnClickListener() {
			 *
			 * @Override public void onClick(DialogInterface arg0, int arg1) {
			 * // et_aadharnumber_spot.setText(""); } });
			 * alertDialogBuilder.setCancelable(false); AlertDialog alertDialog
			 * = alertDialogBuilder.create(); alertDialog.show();
			 *
			 * alertDialog.getWindow().getAttributes();
			 *
			 * TextView textView = (TextView)
			 * alertDialog.findViewById(android.R.id.message);
			 * textView.setTextSize(28);
			 * textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			 * textView.setGravity(Gravity.CENTER);
			 *
			 * Button btn1 =
			 * alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			 * btn1.setTextSize(22); btn1.setTextColor(Color.WHITE);
			 * btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			 * btn1.setBackgroundColor(Color.RED);
			 *
			 * } else if
			 * (et_aadharnumber_spot.getText().toString().trim().length() == 12
			 * && (ver.isValid(et_aadharnumber_spot.getText().toString()))) {
			 * new Async_getAadharDetails().execute(); }
			 *
			 * if (et_driver_lcnce_num_spot.getText().toString().length() != 0)
			 * { Dashboard.licence_details_request_from = "SPOT"; new
			 * Async_getLicenceDetails().execute(); }
			 * ll_aadhar_layout.setVisibility(View.GONE);
			 * tv_aadhar_header.setVisibility(View.GONE); } else { showToast(""
			 * + NETWORK_TXT); }
			 *
			 * } else if (isitTr == 3 || chasisFLG == true) {
			 *
			 * if (!et_chasisNo.getText().toString().equals("")) { // //new
			 * Async_checkAadhaarExists().execute();
			 *
			 * if (et_aadharnumber_spot.getText().toString().length() == 0) {
			 * new Async_getRTADetails().execute();
			 *
			 * } else if
			 * (et_aadharnumber_spot.getText().toString().trim().length() > 1 &&
			 * et_aadharnumber_spot.getText().toString().length() == 12 &&
			 * ver.isValid(et_aadharnumber_spot.getText().toString())) { new
			 * Async_getRTADetails().execute();
			 *
			 * } else if
			 * (et_aadharnumber_spot.getText().toString().trim().length() > 1 &&
			 * et_aadharnumber_spot.getText().toString().length() == 12 &&
			 * !ver.isValid(et_aadharnumber_spot.getText().toString())) {
			 *
			 * TextView title = new TextView(this); title.setText("ALERT");
			 * title.setBackgroundColor(Color.RED);
			 * title.setGravity(Gravity.CENTER);
			 * title.setTextColor(Color.WHITE); title.setTextSize(26);
			 * title.setTypeface(title.getTypeface(), Typeface.BOLD);
			 * title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
			 * dialog_logo, 0, R.drawable.dialog_logo, 0); title.setPadding(20,
			 * 0, 20, 0); title.setHeight(70);
			 *
			 * AlertDialog.Builder alertDialogBuilder = new
			 * AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			 * alertDialogBuilder.setCustomTitle(title);
			 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			 * alertDialogBuilder
			 * .setMessage("\n It's a Invalid Aadhar, \nPlease Enter Valid Aadhar Number !! \n"
			 * ); alertDialogBuilder.setPositiveButton("Ok", new
			 * DialogInterface.OnClickListener() {
			 *
			 * @Override public void onClick(DialogInterface arg0, int arg1) {
			 * // et_aadharnumber_spot.setText(""); } });
			 * alertDialogBuilder.setCancelable(false); AlertDialog alertDialog
			 * = alertDialogBuilder.create(); alertDialog.show();
			 *
			 * alertDialog.getWindow().getAttributes();
			 *
			 * TextView textView = (TextView)
			 * alertDialog.findViewById(android.R.id.message);
			 * textView.setTextSize(28);
			 * textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			 * textView.setGravity(Gravity.CENTER);
			 *
			 * Button btn1 =
			 * alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			 * btn1.setTextSize(22); btn1.setTextColor(Color.WHITE);
			 * btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			 * btn1.setBackgroundColor(Color.RED);
			 *
			 * } else if (et_chasisNo.getText().toString().equals("")) {
			 * Toast.makeText(getApplicationContext(), (Html.
			 * fromHtml("<font color='black'>Enter Proper Vehicle Number</font>"
			 * )), Toast.LENGTH_LONG).show(); } }
			 *
			 * if (isOnline()) { rl_rta_details_layout.setVisibility(View.GONE);
			 * Dashboard.rta_details_request_from = "SPOT";
			 *
			 * if (et_aadharnumber_spot.getText().toString().trim() != null &&
			 * et_aadharnumber_spot.getText().toString().trim().length() > 1 &&
			 * et_aadharnumber_spot.getText().toString().length() != 12) {
			 *
			 * TextView title = new TextView(this); title.setText("ALERT");
			 * title.setBackgroundColor(Color.RED);
			 * title.setGravity(Gravity.CENTER);
			 * title.setTextColor(Color.WHITE); title.setTextSize(26);
			 * title.setTypeface(title.getTypeface(), Typeface.BOLD);
			 * title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
			 * dialog_logo, 0, R.drawable.dialog_logo, 0); title.setPadding(20,
			 * 0, 20, 0); title.setHeight(70);
			 *
			 * AlertDialog.Builder alertDialogBuilder = new
			 * AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			 * alertDialogBuilder.setCustomTitle(title);
			 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			 * alertDialogBuilder.
			 * setMessage("\n Aadhaar Number Length must be 12 digits!! \n");
			 * alertDialogBuilder.setPositiveButton("Ok", new
			 * DialogInterface.OnClickListener() {
			 *
			 * @Override public void onClick(DialogInterface arg0, int arg1) {
			 * // et_aadharnumber_spot.setText(""); } });
			 * alertDialogBuilder.setCancelable(false); AlertDialog alertDialog
			 * = alertDialogBuilder.create(); alertDialog.show();
			 *
			 * alertDialog.getWindow().getAttributes();
			 *
			 * TextView textView = (TextView)
			 * alertDialog.findViewById(android.R.id.message);
			 * textView.setTextSize(28);
			 * textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			 * textView.setGravity(Gravity.CENTER);
			 *
			 * Button btn1 =
			 * alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			 * btn1.setTextSize(22); btn1.setTextColor(Color.WHITE);
			 * btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			 * btn1.setBackgroundColor(Color.RED);
			 *
			 * } else if
			 * (et_aadharnumber_spot.getText().toString().trim().length() == 12
			 * && (ver.isValid(et_aadharnumber_spot.getText().toString()))) {
			 * new Async_getAadharDetails().execute(); } if
			 * (et_driver_lcnce_num_spot.getText().toString().length() != 0) {
			 * Dashboard.licence_details_request_from = "SPOT"; new
			 * Async_getLicenceDetails().execute(); }
			 * ll_aadhar_layout.setVisibility(View.GONE);
			 * tv_aadhar_header.setVisibility(View.GONE);
			 *
			 * } else { showToast("" + NETWORK_TXT); } }
			 */
			break;

		case R.id.ll_pendingchallans_spot_xml:
			if (ServiceHelper.pending_challans_details.length > 0) {
				Log.i("TEST :::", "Testing");
				sb_selected_penlist = new ArrayList<String>();
				sb_selected_penlist.clear();
				sb_selected_penlist_positions.clear();
				Log.i("**sb_sel*", "" + sb_selected_penlist_positions.size());
				sb_selected_penlist_send.delete(0, sb_selected_penlist_send.length());

				if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					cardFLG = false;
					startActivity(new Intent(getApplicationContext(), VehicleHistoryPendingChallans.class));
				} else {
					startActivity(new Intent(getApplicationContext(), VehicleHistoryPendingChallans.class));
				}
			} else {
				showToast("No Pending Challans!");
			}
			break;

		case R.id.btn_whlr_code_spotchallan_xml:
			int rg_iOD = rg_isOwner_isDriver.getCheckedRadioButtonId();

			/*------is_driver/is_owner only for spot_challan : 29-01-2015------*/
			if (et_regcid_spot.getText().toString().equals("") || et_last_num_spot.getText().toString().equals("")) {
				showToast("Please Enter Proper Vehicle Number");

			} else {
				if (isOnline()) {
					SpotChallan.tv_grand_total_spot.setText("");
					// SpotChallan.selected_pendingamnt_spotchallan.setText("");
					SpotChallan.tv_violation_amnt.setText("");
					grand_total = 0;
					// total_amount = 0.0;
					showDialog(WHEELER_CODE);
				} else {
					showToast("No Internet Connection");
				}

			}
			break;

		case R.id.btn_violation_spotchallan_xml:
			/* VIOLATIONS NORMAL DIALOG START */
			// if ((!btn_wheller_code.getText().toString().trim()
			// .equals("" + getString(R.string.select_wheeler_code)))
			// && (violation_offence_Code.size() > 0)) {
			//
			// /* TO CLEAR THE CHECKED & UNC-CHECKED POEISITONS */
			// violation_positions.removeAll(violation_positions);
			// grand_total = 0.0;
			// // total_amount = 0.0;
			// showDialog(VIOLATIONS_DIALOG);
			// } else {
			// showToast("Select Wheeler Code");
			// }
			/* VIOLATIONS NORMAL DIALOG END */

			extraPassengers = "1";
			tv_grand_total_spot.setText("");
			tv_violation_amnt.setText("");
			VehicleHistoryPendingChallans.total_amount_selected_challans = 0.0;

			if (isOnline()) {

				if ((!btn_wheller_code.getText().toString().trim().equals("" + getString(R.string.select_wheeler_code)))
						&& (violation_offence_Code.size() > 0)) {

					/* TO CLEAR THE CHECKED & UNC-CHECKED POEISITONS */
					violation_positions.removeAll(violation_positions);
					violation_rg_ids.removeAll(violation_rg_ids);
					violation_checked_violations.removeAll(violation_checked_violations);
					grand_total = 0.0;

					/* CHECK MAP INTITALISATION */
					check_map = new LinkedHashMap<String, String>();
					check_all_ids = new HashMap<String, String>();
					check_all_ids.clear();

					vioCodeDescMap = new HashMap<String, String>();
					vioCodeDescMap.clear();

					violations_details_send = new StringBuffer();
					violations_details_send.delete(0, violations_details_send.length());

					/* TO APPEND THE SLECTED VILATIONS TO BUTTON */
					violation_desc_append = new StringBuffer();
					violation_desc_append.delete(0, violation_desc_append.length());

					removeDialog(DYNAMIC_VIOLATIONS);
					showDialog(DYNAMIC_VIOLATIONS);

				} else {
					showToast("Select Wheeler Code");
				}
			} else {
				showToast("No Internet Connection");
			}
			break;

		case R.id.btn_cancel_spotchallan_xml:
			SharedPreferences shared_pc = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

			String cadre_code = shared_pc.getString("CADRE_CODE", "");

			if (cadre_code != null && Integer.parseInt(cadre_code) <= 13) {

				startActivity(new Intent(getApplicationContext(), Dashboard.class));
			} else {
				startActivity(new Intent(getApplicationContext(), Dashboard_PC.class));
			}
			this.finish();
			break;

		case R.id.btn_next_spotchallan_xml:
			Log.i("SPOT NEXT CLICK", "" + Dashboard.check_vhleHistory_or_Spot);

			if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				Log.i("-------TOWING----------", "------ENTERED------");
				star.setVisibility(View.GONE);
				if (et_regcid_spot.getText().toString().trim().equals("")) {
					et_regcid_spot.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));
				} else if (et_last_num_spot.getText().toString().trim().equals("")) {
					et_last_num_spot.setError(Html.fromHtml("<font color='black'>Enter Vehicle Code</font>"));
				} else if (btn_wheller_code.getText().toString().trim()
						.equals("" + getString(R.string.select_wheeler_code))) {
					showToast("Select Wheeler Code");
				} else {
					if (isOnline()) {

						Log.i("FIRST TO SEND CLICK", "" + Dashboard.check_vhleHistory_or_Spot);
						if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
							Log.i("41 CP ACT 1 :::", "Entered ");

							if ((btn_violation.getText().toString()
									.equals("" + getResources().getString(R.string.select_violation)))) {
								licence_details_spot_master = new String[0];
								otp_msg = "Please select violation";
								removeDialog(OTP_CNFRMTN_DIALOG);
								showDialog(OTP_CNFRMTN_DIALOG);
							} else if (!btn_violation.getText().toString()
									.equals("" + getResources().getString(R.string.select_violation))) {
								Log.i("temp_violations_ids :::", "Entered ");
								temp_violations_ids = new String[violation_checked_violations.size()];
								int status = 0;
								for (int j = 0; j < violation_checked_violations.size(); j++) {
									Log.i("violations_ids :::", "Entered ");
									Log.i("temp_v:", " " + temp_violations_ids[j]);
									temp_violations_ids[j] = violation_checked_violations.get(j);
									if (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")
											|| et_driver_lcnce_num_spot.getText().toString().trim().equals("")
											|| !et_aadharnumber_spot.getText().toString().trim().equals("")
											|| et_aadharnumber_spot.getText().toString().trim().equals("")) {
										status = 0;
										Log.i("DOESNOT CONTAINS", "" + temp_violations_ids[j]);
										completeVehicle_num_send = "";// AP09CC3014
										regncode_send = "";// AP09
										regnName_send = "";
										vehicle_num_send = "";// 3014

										regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
										regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
										vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();
										completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
												+ vehicle_num_send);
										Log.i("**VEHCIL***", "" + completeVehicle_num_send);

										getDateAndTime();
										int pos = 0;
										Log.i("**VE***", "" + violation_positions.size());

										if (Fake_NO_Dialog.fake_action == "not fake") {
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else {
												new Async_getDetainedItems().execute();
											}
										} else if (Fake_NO_Dialog.fake_action == null) {
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else {
												new Async_getDetainedItems().execute();
											}
										} else if (Fake_NO_Dialog.fake_action == "fake") {
											TextView title = new TextView(SpotChallan.this);
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

											String otp_message = "\n It's a Fake Vehicle !!! \n";

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialogBuilder.setCustomTitle(title);
											alertDialogBuilder.setIcon(R.drawable.dialog_logo);
											alertDialogBuilder.setMessage(otp_message);
											alertDialogBuilder.setCancelable(false);
											alertDialogBuilder.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO
															// Auto-generated
															// method stub
														}
													});

											AlertDialog alertDialog = alertDialogBuilder.create();
											alertDialog.show();

											alertDialog.getWindow().getAttributes();

											TextView textView = (TextView) alertDialog
													.findViewById(android.R.id.message);
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
										status = 1;
									}
								}
								break;

							} else {
								completeVehicle_num_send = "";// AP09CC3014
								regncode_send = "";// AP09
								regnName_send = "";
								vehicle_num_send = "";// 3014

								regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
								regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
								vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

								completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
										+ vehicle_num_send);
								Log.i("**VE*", "" + completeVehicle_num_send);

								getDateAndTime();
								int pos = 0;
								Log.i("**VEH***", "" + violation_positions.size());
								if (Fake_NO_Dialog.fake_action == "not fake") {
									if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() != 12) {
										et_aadharnumber_spot.setError(Html.fromHtml(
												"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() == 12
											&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
										et_aadharnumber_spot.setError(
												Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else {
										new Async_getDetainedItems().execute();
									}
								} else if (Fake_NO_Dialog.fake_action == null) {
									if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() != 12) {
										et_aadharnumber_spot.setError(Html.fromHtml(
												"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() == 12
											&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
										et_aadharnumber_spot.setError(
												Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else {
										new Async_getDetainedItems().execute();
									}
								} else if (Fake_NO_Dialog.fake_action == "fake") {

									TextView title = new TextView(SpotChallan.this);
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

									String otp_message = "\n It's a Fake Vehicle !!! \n";

									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
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
							}
						} else {
							completeVehicle_num_send = "";// AP09CC3014
							regncode_send = "";// AP09
							regnName_send = "";
							vehicle_num_send = "";// 3014

							regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
							regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
							vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

							completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
									+ vehicle_num_send);
							Log.i("**VEHCILE N*", "" + completeVehicle_num_send);

							getDateAndTime();
							int pos = 0;
							Log.i("**VEHCIL***", "" + violation_positions.size());

							if (Fake_NO_Dialog.fake_action == "not fake") {
								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else {
									new Async_getDetainedItems().execute();
								}
							} else if (Fake_NO_Dialog.fake_action == null) {
								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else {
									new Async_getDetainedItems().execute();
								}
							} else if (Fake_NO_Dialog.fake_action == "fake") {

								TextView title = new TextView(SpotChallan.this);
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

								String otp_message = "\n It's a Fake Vehicle !!! \n";

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
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
						}
					} else {
						showToast("" + NETWORK_TXT);
					}
				}
			}

			if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
					|| (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

				if (et_regcid_spot.getText().toString().trim().equals("")) {
					et_regcid_spot.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));
					showToast("Enter Registration Code");

				}

				else {
					if (isOnline()) {
						Log.i("VHCLE", "" + ServiceHelper.pending_challans_details.length);

						if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
								&& (ServiceHelper.pending_challans_details.length > 0)) {
							violations_details_send = new StringBuffer("");
							violations_details_send.delete(0, violations_details_send.length());

							completeVehicle_num_send = "";// AP09CC3014
							regncode_send = "";// AP09
							regnName_send = "";
							vehicle_num_send = "";// 3014

							regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
							regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
							vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

							completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
									+ vehicle_num_send);
							Log.i("**VEHCI**", "" + completeVehicle_num_send);

							getDateAndTime();
							int pos = 0;
							Log.i("**VEHC***", "" + violation_positions.size());

							if (Fake_NO_Dialog.fake_action == "not fake") {

								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();

								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();

								} else if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
										&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
									otp_msg = "Please enter driver Licence Number or Aadhar Number";
									removeDialog(OTP_CNFRMTN_DIALOG);
									showDialog(OTP_CNFRMTN_DIALOG);

								} /*
									 * else if (VehicleHistoryPendingChallans.
									 * total_amount_selected_challans == 0.0) {
									 * otp_msg =
									 * "Please Select Pending Challan";
									 * removeDialog(OTP_CNFRMTN_DIALOG);
									 * showDialog(OTP_CNFRMTN_DIALOG); }
									 */else {
									new Async_getDetainedItems().execute();
								}
							} else if (Fake_NO_Dialog.fake_action == null) {
								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
										&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
									otp_msg = "Please enter driver Licence Number or Aadhar Number";
									removeDialog(OTP_CNFRMTN_DIALOG);
									showDialog(OTP_CNFRMTN_DIALOG);

								} /*
									 * else if (VehicleHistoryPendingChallans.
									 * total_amount_selected_challans == 0.0) {
									 * otp_msg =
									 * "Please Select Pending Challan";
									 * removeDialog(OTP_CNFRMTN_DIALOG);
									 * showDialog(OTP_CNFRMTN_DIALOG); }
									 */else {
									new Async_getDetainedItems().execute();
								}
							} else if (Fake_NO_Dialog.fake_action == "fake") {
								TextView title = new TextView(SpotChallan.this);
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

								String otp_message = "\n It's a Fake Vehicle !!! \n";

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
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

						} else if ((Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

							String threeWheeler = tv_vhle_no_spot.getText().toString().trim();

							if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
									&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
								otp_msg = "Please enter driver Licence Number or Aadhar Number";
								removeDialog(OTP_CNFRMTN_DIALOG);
								showDialog(OTP_CNFRMTN_DIALOG);

							} else {
								violations_details_send = new StringBuffer("");
								violations_details_send.delete(0, violations_details_send.length());

								completeVehicle_num_send = "";// AP09CC3014
								regncode_send = "";// AP09
								regnName_send = "";
								vehicle_num_send = "";// 3014

								regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
								regnName_send = "" + et_vchl_num_spot.getText().toString().trim().toUpperCase();
								vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

								completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
										+ vehicle_num_send);
								Log.i("", "" + completeVehicle_num_send);

								getDateAndTime();
								int pos = 0;
								Log.i("**VE", "" + violation_positions.size());

								if (Fake_NO_Dialog.fake_action == "not fake") {

									if (rb_indian.isChecked() == true && passport_layout.getVisibility() == View.GONE) {

										if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
												&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
											otp_msg = "Please enter driver Licence Number or Aadhar Number";
											removeDialog(OTP_CNFRMTN_DIALOG);
											showDialog(OTP_CNFRMTN_DIALOG);
										} else {
											new Async_getDetainedItems().execute();
										}

									} else if (rb_nri.isChecked() == true
											&& passport_layout.getVisibility() == View.VISIBLE) {
										if (rb_nri.isChecked() == true
												&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
											et_driver_lcnce_num_spot.setError(
													Html.fromHtml("<font color='black'>Enter DL Number</font>"));
											et_driver_lcnce_num_spot.requestFocus();
										} else if (rb_nri.isChecked() == true
												&& et_passport.getText().toString().trim().equals("")) {
											et_passport.setError(
													Html.fromHtml("<font color='black'>Enter Passport Number</font>"));
											et_passport.requestFocus();
										} else {
											new Async_getDetainedItems().execute();
										}
									}
								} else if (Fake_NO_Dialog.fake_action == null) {
									if (rb_indian.isChecked() == true && passport_layout.getVisibility() == View.GONE) {
										if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
												&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
											otp_msg = "Please enter driver Licence Number or Aadhar Number";
											removeDialog(OTP_CNFRMTN_DIALOG);
											showDialog(OTP_CNFRMTN_DIALOG);
										} else {
											new Async_getDetainedItems().execute();
										}
									} else if (rb_nri.isChecked() == true
											&& passport_layout.getVisibility() == View.VISIBLE) {
										if (rb_nri.isChecked() == true
												&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
											et_driver_lcnce_num_spot.setError(
													Html.fromHtml("<font color='black'>Enter DL Number</font>"));
											et_driver_lcnce_num_spot.requestFocus();
										} else if (rb_nri.isChecked() == true
												&& et_passport.getText().toString().trim().equals("")) {
											et_passport.setError(
													Html.fromHtml("<font color='black'>Enter Passport Number</font>"));
											et_passport.requestFocus();
										} else {
											new Async_getDetainedItems().execute();
										}
									}
								} else if (Fake_NO_Dialog.fake_action == "fake") {

									TextView title = new TextView(SpotChallan.this);
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

									String otp_message = "\n It's a Fake Vehicle !!! \n";

									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
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

							}

						} else {
							showToast("" + getResources().getString(R.string.no_pending_challans));
						}
					} else {
						showToast("" + NETWORK_TXT);
					}
				}

			} else if ((Dashboard.check_vhleHistory_or_Spot.equals("spot"))) {

				Log.i("-------SPOT----------", "------ENTERED------");

				Log.i("violations_details_send", "" + violations_details_send);

				// 64@500@500@W/o Driving Licence ( S 181/177 )!
				String dlCheck = "0";
				if (violations_details_send != null && violations_details_send.toString().trim().length() > 2) {
					System.out.println(">>>>>>>>1");
					String[] selvio = violations_details_send.toString().trim().split("\\!");

					if (selvio != null && selvio.length > 0) {
						for (String vio : selvio) {

							String[] violations = vio.split("\\@");
							Log.i("Ofense_code", violations[0]);
							if (64 == Integer.parseInt(violations[0])) {
								// et_regcid_spot.setError(Html.fromHtml("<font
								// color='black'>Please select Driver
								// Photo</font>")); //00
								dlCheck = "1";

							}
						}
					}
				}

				// validate offence code64 w/o DL
				if ("0".equals(imgSelected) && "1".equals(dlCheck)) {
					showToast("Please Take Driver's Photo !");
				} else if (et_regcid_spot.getText().toString().trim().equals("")) {
					System.out.println(">>>>>>>>2");
					et_regcid_spot.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));

				} else if (et_last_num_spot.getText().toString().trim().equals("")) {
					System.out.println(">>>>>>>>3");
					et_last_num_spot.setError(Html.fromHtml("<font color='black'>Enter Vehicle Code</font>"));

				} else if (btn_wheller_code.getText().toString().trim()
						.equals("" + getString(R.string.select_wheeler_code))) {
					System.out.println(">>>>>>>>4");
					showToast("Select Wheeler Code");

				} else if (et_drivername_iOD.getText().toString().trim().equals("")
						&& !Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
					System.out.println(">>>>>>>>5");
					TextView title = new TextView(this);
					title.setText("ALERT");
					title.setBackgroundColor(Color.RED);
					title.setGravity(Gravity.CENTER);
					title.setTextColor(Color.WHITE);
					title.setTextSize(26);
					title.setTypeface(title.getTypeface(), Typeface.BOLD);
					title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
					title.setPadding(20, 0, 20, 0);
					title.setHeight(70);

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,
							AlertDialog.THEME_HOLO_LIGHT);
					alertDialogBuilder.setCustomTitle(title);
					alertDialogBuilder.setIcon(R.drawable.dialog_logo);
					alertDialogBuilder.setMessage("\n Please Enter Driver Name...! \n");
					alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						}
					});
					alertDialogBuilder.setCancelable(false);
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					alertDialog.getWindow().getAttributes();

					TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
					textView.setTextSize(28);
					textView.setTypeface(title.getTypeface(), Typeface.BOLD);
					textView.setGravity(Gravity.CENTER);

					Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
					btn1.setTextSize(22);
					btn1.setTextColor(Color.WHITE);
					btn1.setTypeface(title.getTypeface(), Typeface.BOLD);
					btn1.setBackgroundColor(Color.RED);

				} else {

					if (isOnline()) {

						Log.i("FIRST TO SEND CLICK", "" + Dashboard.check_vhleHistory_or_Spot);
						Log.i("here :::::", "" + Dashboard.check_vhleHistory_or_Spot);

						if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {

							if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
									&& (btn_violation.getText().toString()
											.equals("" + getResources().getString(R.string.select_violation)))) {

								licence_details_spot_master = new String[0];
								otp_msg = "\n Please select  violation -without driving license \n";
								removeDialog(OTP_CNFRMTN_DIALOG);
								showDialog(OTP_CNFRMTN_DIALOG);

							} else if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
									&& (!btn_violation.getText().toString()
											.equals("" + getResources().getString(R.string.select_violation)))) {

								temp_violations_ids = new String[violation_checked_violations.size()];
								int status = 0;

								for (int j = 0; j < violation_checked_violations.size(); j++) {

									temp_violations_ids[j] = violation_checked_violations.get(j);

									if (temp_violations_ids[j].equals("30")) {

										violation_code = "30";
										spotPamentFLG = false;
										status = 0;
										Log.i("DOES CONTAINS", "" + temp_violations_ids[j]);
										completeVehicle_num_send = "";// AP09CC3014
										regncode_send = "";// AP09
										regnName_send = "";
										vehicle_num_send = "";// 3014

										regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
										regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
										vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

										completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
												+ vehicle_num_send);
										Log.i("**VEHCILE ", "" + completeVehicle_num_send);

										getDateAndTime();
										int pos = 0;
										Log.i("**VEHC", "" + violation_positions.size());

										if (Fake_NO_Dialog.fake_action == "not fake") {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} /*
												 * else if
												 * (picturePath.equals("")) {
												 * showToast("Please Capture Image"
												 * ); }
												 */else {
												new Async_getDetainedItems().execute();
											}
										} else if (Fake_NO_Dialog.fake_action == null) {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} /*
												 * else if
												 * (picturePath.equals("")) {
												 * showToast("Please Capture Image"
												 * ); }
												 */else {
												new Async_getDetainedItems().execute();
											}
										} else if (Fake_NO_Dialog.fake_action == "fake") {

											TextView title = new TextView(SpotChallan.this);
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

											String otp_message = "\n It's a Fake Vehicle !!! \n";

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialogBuilder.setCustomTitle(title);
											alertDialogBuilder.setIcon(R.drawable.dialog_logo);
											alertDialogBuilder.setMessage(otp_message);
											alertDialogBuilder.setCancelable(false);
											alertDialogBuilder.setPositiveButton("Ok",

													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO
															// Auto-generated
															// method stub
														}
													});

											AlertDialog alertDialog = alertDialogBuilder.create();
											alertDialog.show();
											alertDialog.getWindow().getAttributes();

											TextView textView = (TextView) alertDialog
													.findViewById(android.R.id.message);
											textView.setTextSize(28);
											textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
											textView.setGravity(Gravity.CENTER);

											Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
											btn1.setTextSize(22);
											btn1.setTextColor(Color.WHITE);
											btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
											btn1.setBackgroundColor(Color.RED);

										}
										break;

									} else if (temp_violations_ids[j].equals("64")
											|| temp_violations_ids[j].equals("123")
											|| temp_violations_ids[j].equals("6")
											|| ServiceHelper.pending_challans_details.length >= 10) {

										if (temp_violations_ids[j].equals("64")) {
											violation_code = "64";
										} else if (temp_violations_ids[j].equals("123")) {
											violation_code = "123";
										} else if (temp_violations_ids[j].equals("6")) {
											violation_code = "6";
										}

										// radioGroupButton_spotpayment0.setClickable(false);
										spotPamentFLG = true;

										status = 0;
										Log.i("DOES CONTAINS", "" + temp_violations_ids[j]);
										completeVehicle_num_send = "";// AP09CC3014
										regncode_send = "";// AP09
										regnName_send = "";
										vehicle_num_send = "";// 3014

										regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
										regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
										vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

										completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
												+ vehicle_num_send);
										Log.i("**VEHCI***", "" + completeVehicle_num_send);

										getDateAndTime();
										int pos = 0;
										Log.i("**VEHCZE***", "" + violation_positions.size());

										if (Fake_NO_Dialog.fake_action == "not fake") {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else { new
											 * Async_getDetainedItems().execute(
											 * ); }
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} /*
												 * else if
												 * (picturePath.equals("")) {
												 * showToast("Please Capture Image"
												 * ); }
												 */else {
												if (DLvalidFLG.equals("VALID")) {
													new Async_getDetainedItems().execute();
													Log.i("**What 1***", "" + DLvalidFLG);
												} else {

													Log.i("**What 2***", "" + DLvalidFLG);
													TextView title3 = new TextView(SpotChallan.this);
													title3.setText("ALERT");
													title3.setBackgroundColor(Color.RED);
													title3.setGravity(Gravity.CENTER);
													title3.setTextColor(Color.WHITE);
													title3.setTextSize(26);
													title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
													title3.setCompoundDrawablesWithIntrinsicBounds(
															R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
													title3.setPadding(20, 0, 20, 0);
													title3.setHeight(70);

													AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
															SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
													alertDialog_Builder.setCustomTitle(title3);
													alertDialog_Builder.setIcon(R.drawable.dialog_logo);
													alertDialog_Builder.setMessage(
															"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
													alertDialog_Builder.setPositiveButton("Ok",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface arg0, int arg1) {
																}
															});

													alertDialog_Builder.setCancelable(false);
													AlertDialog alert_Dialog = alertDialog_Builder.create();
													alert_Dialog.show();

													alert_Dialog.getWindow().getAttributes();

													TextView textView2 = (TextView) alert_Dialog
															.findViewById(android.R.id.message);
													textView2.setTextSize(28);
													textView2.setGravity(Gravity.CENTER);
													textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

													Button btn2 = alert_Dialog
															.getButton(DialogInterface.BUTTON_POSITIVE);
													btn2.setTextSize(22);
													btn2.setTextColor(Color.WHITE);
													btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
													btn2.setBackgroundColor(Color.RED);

												}
											}
										} else if (Fake_NO_Dialog.fake_action == null) {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else { new
											 * Async_getDetainedItems().execute(
											 * ); }
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} /*
												 * else if
												 * (picturePath.equals("")) {
												 * showToast("Please Capture Image"
												 * ); }
												 */else {
												if (DLvalidFLG.equals("VALID")) {
													new Async_getDetainedItems().execute();
												} else {

													TextView title3 = new TextView(SpotChallan.this);
													title3.setText("ALERT");
													title3.setBackgroundColor(Color.RED);
													title3.setGravity(Gravity.CENTER);
													title3.setTextColor(Color.WHITE);
													title3.setTextSize(26);
													title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
													title3.setCompoundDrawablesWithIntrinsicBounds(
															R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
													title3.setPadding(20, 0, 20, 0);
													title3.setHeight(70);

													AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
															SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
													alertDialog_Builder.setCustomTitle(title3);
													alertDialog_Builder.setIcon(R.drawable.dialog_logo);
													alertDialog_Builder.setMessage(
															"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
													alertDialog_Builder.setPositiveButton("Ok",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface arg0, int arg1) {
																}
															});
													alertDialog_Builder.setCancelable(false);
													AlertDialog alert_Dialog = alertDialog_Builder.create();
													alert_Dialog.show();

													alert_Dialog.getWindow().getAttributes();

													TextView textView2 = (TextView) alert_Dialog
															.findViewById(android.R.id.message);
													textView2.setTextSize(28);
													textView2.setGravity(Gravity.CENTER);
													textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

													Button btn2 = alert_Dialog
															.getButton(DialogInterface.BUTTON_POSITIVE);
													btn2.setTextSize(22);
													btn2.setTextColor(Color.WHITE);
													btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
													btn2.setBackgroundColor(Color.RED);

												}
											}
										} else if (Fake_NO_Dialog.fake_action == "fake") {

											TextView title = new TextView(SpotChallan.this);
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

											String otp_message = "\n It's a Fake Vehicle !!! \n";

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialogBuilder.setCustomTitle(title);
											alertDialogBuilder.setIcon(R.drawable.dialog_logo);
											alertDialogBuilder.setMessage(otp_message);
											alertDialogBuilder.setCancelable(false);
											alertDialogBuilder.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO
															// Auto-generated
															// method stub
														}
													});

											AlertDialog alertDialog = alertDialogBuilder.create();
											alertDialog.show();

											alertDialog.getWindow().getAttributes();

											TextView textView = (TextView) alertDialog
													.findViewById(android.R.id.message);
											textView.setTextSize(28);
											textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
											textView.setGravity(Gravity.CENTER);

											Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
											btn1.setTextSize(22);
											btn1.setTextColor(Color.WHITE);
											btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
											btn1.setBackgroundColor(Color.RED);

										}
										break;

									} else if (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {

									} else {
										status = 1;
										spotPamentFLG = false;
									}
								}

								if (status == 1) {
									/*
									 * if ((et_driver_lcnce_num_spot.getText().
									 * toString().trim().equals("")) &&
									 * (btn_violation.getText().toString().
									 * equals(""+
									 * getResources().getString(R.string.
									 * select_violation)))) {
									 */
									licence_details_spot_master = new String[0];
									otp_msg = "Please select  violation -without driving license";
									removeDialog(OTP_CNFRMTN_DIALOG);
									showDialog(OTP_CNFRMTN_DIALOG);
									// }

								}
								break;

							} else if ((!et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
									&& (btn_violation.getText().toString()
											.equals("" + getResources().getString(R.string.select_violation)))) {
								licence_details_spot_master = new String[0];
								otp_msg = "Please select violation";
								removeDialog(OTP_CNFRMTN_DIALOG);
								showDialog(OTP_CNFRMTN_DIALOG);

							} else if (!btn_violation.getText().toString()
									.equals("" + getResources().getString(R.string.select_violation))) {
								String[] temp_violations_ids = new String[violation_checked_violations.size()];
								int status = 0;
								for (int j = 0; j < violation_checked_violations.size(); j++) {

									temp_violations_ids[j] = violation_checked_violations.get(j);

									if (temp_violations_ids[j].equals("64") || temp_violations_ids[j].equals("123")
											|| temp_violations_ids[j].equals("6")
											|| ServiceHelper.pending_challans_details.length >= 10) {

										if (temp_violations_ids[j].equals("64")) {
											violation_code = "64";
										} else if (temp_violations_ids[j].equals("123")) {
											violation_code = "123";
										} else if (temp_violations_ids[j].equals("6")) {
											violation_code = "6";
										}

										spotPamentFLG = true;
										status = 0;
										Log.i("DOES CONTAINS", "" + temp_violations_ids[j]);
										completeVehicle_num_send = "";// AP09CC3014
										regncode_send = "";// AP09
										regnName_send = "";
										vehicle_num_send = "";// 3014

										regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
										regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
										vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

										completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
												+ vehicle_num_send);
										Log.i("**VEHCILED***", "" + completeVehicle_num_send);

										getDateAndTime();
										int pos = 0;
										Log.i("**VEHE***", "" + violation_positions.size());

										if (Fake_NO_Dialog.fake_action == "not fake") {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else { new
											 * Async_getDetainedItems().execute(
											 * ); }
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else {

												if (DLvalidFLG.equals("VALID")) {
													new Async_getDetainedItems().execute();
												} else {

													TextView title3 = new TextView(SpotChallan.this);
													title3.setText("ALERT");
													title3.setBackgroundColor(Color.RED);
													title3.setGravity(Gravity.CENTER);
													title3.setTextColor(Color.WHITE);
													title3.setTextSize(26);
													title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
													title3.setCompoundDrawablesWithIntrinsicBounds(
															R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
													title3.setPadding(20, 0, 20, 0);
													title3.setHeight(70);

													AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
															SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
													alertDialog_Builder.setCustomTitle(title3);
													alertDialog_Builder.setIcon(R.drawable.dialog_logo);
													alertDialog_Builder.setMessage(
															"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
													alertDialog_Builder.setPositiveButton("Ok",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface arg0, int arg1) {
																}
															});
													alertDialog_Builder.setCancelable(false);
													AlertDialog alert_Dialog = alertDialog_Builder.create();
													alert_Dialog.show();

													alert_Dialog.getWindow().getAttributes();

													TextView textView2 = (TextView) alert_Dialog
															.findViewById(android.R.id.message);
													textView2.setTextSize(28);
													textView2.setGravity(Gravity.CENTER);
													textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

													Button btn2 = alert_Dialog
															.getButton(DialogInterface.BUTTON_POSITIVE);
													btn2.setTextSize(22);
													btn2.setTextColor(Color.WHITE);
													btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
													btn2.setBackgroundColor(Color.RED);

												}

											}
										} else if (Fake_NO_Dialog.fake_action == null) {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else { new
											 * Async_getDetainedItems().execute(
											 * ); }
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else {
												if (DLvalidFLG.equals("VALID")) {
													new Async_getDetainedItems().execute();
												} else {

													TextView title3 = new TextView(SpotChallan.this);
													title3.setText("ALERT");
													title3.setBackgroundColor(Color.RED);
													title3.setGravity(Gravity.CENTER);
													title3.setTextColor(Color.WHITE);
													title3.setTextSize(26);
													title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
													title3.setCompoundDrawablesWithIntrinsicBounds(
															R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
													title3.setPadding(20, 0, 20, 0);
													title3.setHeight(70);

													AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
															SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
													alertDialog_Builder.setCustomTitle(title3);
													alertDialog_Builder.setIcon(R.drawable.dialog_logo);
													alertDialog_Builder.setMessage(
															"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
													alertDialog_Builder.setPositiveButton("Ok",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface arg0, int arg1) {
																}
															});
													alertDialog_Builder.setCancelable(false);
													AlertDialog alert_Dialog = alertDialog_Builder.create();
													alert_Dialog.show();

													alert_Dialog.getWindow().getAttributes();

													TextView textView2 = (TextView) alert_Dialog
															.findViewById(android.R.id.message);
													textView2.setTextSize(28);
													textView2.setGravity(Gravity.CENTER);
													textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

													Button btn2 = alert_Dialog
															.getButton(DialogInterface.BUTTON_POSITIVE);
													btn2.setTextSize(22);
													btn2.setTextColor(Color.WHITE);
													btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
													btn2.setBackgroundColor(Color.RED);
												}
											}
										} else if (Fake_NO_Dialog.fake_action == "fake") {

											TextView title = new TextView(SpotChallan.this);
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

											String otp_message = "\n It's a Fake Vehicle !!! \n";

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialogBuilder.setCustomTitle(title);
											alertDialogBuilder.setIcon(R.drawable.dialog_logo);
											alertDialogBuilder.setMessage(otp_message);
											alertDialogBuilder.setCancelable(false);
											alertDialogBuilder.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO
															// Auto-generated
															// method stub
														}
													});

											AlertDialog alertDialog = alertDialogBuilder.create();
											alertDialog.show();

											alertDialog.getWindow().getAttributes();

											TextView textView = (TextView) alertDialog
													.findViewById(android.R.id.message);
											textView.setTextSize(28);
											textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
											textView.setGravity(Gravity.CENTER);

											Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
											btn1.setTextSize(22);
											btn1.setTextColor(Color.WHITE);
											btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
											btn1.setBackgroundColor(Color.RED);

										}
										break;
									} else if (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {

										spotPamentFLG = false;
										status = 0;
										Log.i("DOESNOT CONTAINS", "" + temp_violations_ids[j]);
										completeVehicle_num_send = "";// AP09CC3014
										regncode_send = "";// AP09
										regnName_send = "";
										vehicle_num_send = "";// 3014

										regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
										regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
										vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();
										completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
												+ vehicle_num_send);
										Log.i("**VE**", "" + completeVehicle_num_send);

										getDateAndTime();
										int pos = 0;
										Log.i("**V***", "" + violation_positions.size());

										if (Fake_NO_Dialog.fake_action == "not fake") {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else { new
											 * Async_getDetainedItems().execute(
											 * ); }
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else {

												if (DLvalidFLG.equals("VALID")) {
													new Async_getDetainedItems().execute();
												} else {

													TextView title3 = new TextView(SpotChallan.this);
													title3.setText("ALERT");
													title3.setBackgroundColor(Color.RED);
													title3.setGravity(Gravity.CENTER);
													title3.setTextColor(Color.WHITE);
													title3.setTextSize(26);
													title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
													title3.setCompoundDrawablesWithIntrinsicBounds(
															R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
													title3.setPadding(20, 0, 20, 0);
													title3.setHeight(70);

													AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
															SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
													alertDialog_Builder.setCustomTitle(title3);
													alertDialog_Builder.setIcon(R.drawable.dialog_logo);
													alertDialog_Builder.setMessage(
															"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
													alertDialog_Builder.setPositiveButton("Ok",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface arg0, int arg1) {
																}
															});
													alertDialog_Builder.setCancelable(false);
													AlertDialog alert_Dialog = alertDialog_Builder.create();
													alert_Dialog.show();

													alert_Dialog.getWindow().getAttributes();

													TextView textView2 = (TextView) alert_Dialog
															.findViewById(android.R.id.message);
													textView2.setTextSize(28);
													textView2.setGravity(Gravity.CENTER);
													textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

													Button btn2 = alert_Dialog
															.getButton(DialogInterface.BUTTON_POSITIVE);
													btn2.setTextSize(22);
													btn2.setTextColor(Color.WHITE);
													btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
													btn2.setBackgroundColor(Color.RED);

												}
											}
										} else if (Fake_NO_Dialog.fake_action == null) {
											/*
											 * if
											 * (et_aadharnumber_spot.getText().
											 * toString().trim().equals("")) {
											 * et_aadharnumber_spot.setError(
											 * Html.
											 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
											 * ));
											 * et_aadharnumber_spot.requestFocus
											 * (); }else { new
											 * Async_getDetainedItems().execute(
											 * ); }
											 */
											if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() != 12) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
													&& et_aadharnumber_spot.getText().toString().length() == 12
													&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
												et_aadharnumber_spot.setError(Html.fromHtml(
														"<font color='black'>Enter Valid Aadhaar Number</font>"));
												et_aadharnumber_spot.requestFocus();
											} else {

												if (DLvalidFLG.equals("VALID")) {
													new Async_getDetainedItems().execute();
												} else {

													TextView title3 = new TextView(SpotChallan.this);
													title3.setText("ALERT");
													title3.setBackgroundColor(Color.RED);
													title3.setGravity(Gravity.CENTER);
													title3.setTextColor(Color.WHITE);
													title3.setTextSize(26);
													title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
													title3.setCompoundDrawablesWithIntrinsicBounds(
															R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
													title3.setPadding(20, 0, 20, 0);
													title3.setHeight(70);

													AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
															SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
													alertDialog_Builder.setCustomTitle(title3);
													alertDialog_Builder.setIcon(R.drawable.dialog_logo);
													alertDialog_Builder.setMessage(
															"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
													alertDialog_Builder.setPositiveButton("Ok",
															new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface arg0, int arg1) {
																}
															});
													alertDialog_Builder.setCancelable(false);
													AlertDialog alert_Dialog = alertDialog_Builder.create();
													alert_Dialog.show();

													alert_Dialog.getWindow().getAttributes();

													TextView textView2 = (TextView) alert_Dialog
															.findViewById(android.R.id.message);
													textView2.setTextSize(28);
													textView2.setGravity(Gravity.CENTER);
													textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

													Button btn2 = alert_Dialog
															.getButton(DialogInterface.BUTTON_POSITIVE);
													btn2.setTextSize(22);
													btn2.setTextColor(Color.WHITE);
													btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
													btn2.setBackgroundColor(Color.RED);

												}
											}
										} else if (Fake_NO_Dialog.fake_action == "fake") {

											TextView title = new TextView(SpotChallan.this);
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

											String otp_message = "\n It's a Fake Vehicle !!! \n";

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialogBuilder.setCustomTitle(title);
											alertDialogBuilder.setIcon(R.drawable.dialog_logo);
											alertDialogBuilder.setMessage(otp_message);
											alertDialogBuilder.setCancelable(false);
											alertDialogBuilder.setPositiveButton("Ok",

													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO
															// Auto-generated
															// method stub
														}
													});

											AlertDialog alertDialog = alertDialogBuilder.create();
											alertDialog.show();
											alertDialog.getWindow().getAttributes();

											TextView textView = (TextView) alertDialog
													.findViewById(android.R.id.message);
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
										status = 1;
										spotPamentFLG = false;
									}
								}

								if (status == 1) {
									licence_details_spot_master = new String[0];
									otp_msg = "Please select  violation -without driving license";
									removeDialog(OTP_CNFRMTN_DIALOG);
									showDialog(OTP_CNFRMTN_DIALOG);
								}
								break;
								/* 19_APRIL_2017 */
							} else {
								completeVehicle_num_send = "";// AP09CC3014
								regncode_send = "";// AP09
								regnName_send = "";
								vehicle_num_send = "";// 3014

								regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
								regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
								vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

								completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
										+ vehicle_num_send);
								Log.i("*", "" + completeVehicle_num_send);

								getDateAndTime();
								int pos = 0;
								Log.i("**VE", "" + violation_positions.size());

								if (Fake_NO_Dialog.fake_action == "not fake") {
									/*
									 * if
									 * (et_aadharnumber_spot.getText().toString(
									 * ).trim().equals("")) {
									 * et_aadharnumber_spot.setError(Html.
									 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
									 * )); et_aadharnumber_spot.requestFocus();
									 * }else { new
									 * Async_getDetainedItems().execute(); }
									 */
									if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() != 12) {
										et_aadharnumber_spot.setError(Html.fromHtml(
												"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() == 12
											&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
										et_aadharnumber_spot.setError(
												Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else {
										if (DLvalidFLG.equals("VALID")) {
											new Async_getDetainedItems().execute();
										} else {

											TextView title3 = new TextView(SpotChallan.this);
											title3.setText("ALERT");
											title3.setBackgroundColor(Color.RED);
											title3.setGravity(Gravity.CENTER);
											title3.setTextColor(Color.WHITE);
											title3.setTextSize(26);
											title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
											title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
													R.drawable.dialog_logo, 0);
											title3.setPadding(20, 0, 20, 0);
											title3.setHeight(70);

											AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialog_Builder.setCustomTitle(title3);
											alertDialog_Builder.setIcon(R.drawable.dialog_logo);
											alertDialog_Builder.setMessage(
													"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
											alertDialog_Builder.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(DialogInterface arg0, int arg1) {
														}
													});
											alertDialog_Builder.setCancelable(false);
											AlertDialog alert_Dialog = alertDialog_Builder.create();
											alert_Dialog.show();
											alert_Dialog.getWindow().getAttributes();

											TextView textView2 = (TextView) alert_Dialog
													.findViewById(android.R.id.message);
											textView2.setTextSize(28);
											textView2.setGravity(Gravity.CENTER);
											textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

											Button btn2 = alert_Dialog.getButton(DialogInterface.BUTTON_POSITIVE);
											btn2.setTextSize(22);
											btn2.setTextColor(Color.WHITE);
											btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
											btn2.setBackgroundColor(Color.RED);

										}

									}
								} else if (Fake_NO_Dialog.fake_action == null) {
									/*
									 * if
									 * (et_aadharnumber_spot.getText().toString(
									 * ).trim().equals("")) {
									 * et_aadharnumber_spot.setError(Html.
									 * fromHtml("<font color='black'>Please Enter Adhaar Number</font>"
									 * )); et_aadharnumber_spot.requestFocus();
									 * }else { new
									 * Async_getDetainedItems().execute(); }
									 */
									if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() != 12) {
										et_aadharnumber_spot.setError(Html.fromHtml(
												"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
											&& et_aadharnumber_spot.getText().toString().length() == 12
											&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
										et_aadharnumber_spot.setError(
												Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
										et_aadharnumber_spot.requestFocus();
									} else {

										if (DLvalidFLG.equals("VALID")) {
											new Async_getDetainedItems().execute();
										} else {

											TextView title3 = new TextView(SpotChallan.this);
											title3.setText("ALERT");
											title3.setBackgroundColor(Color.RED);
											title3.setGravity(Gravity.CENTER);
											title3.setTextColor(Color.WHITE);
											title3.setTextSize(26);
											title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
											title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
													R.drawable.dialog_logo, 0);
											title3.setPadding(20, 0, 20, 0);
											title3.setHeight(70);

											AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialog_Builder.setCustomTitle(title3);
											alertDialog_Builder.setIcon(R.drawable.dialog_logo);
											alertDialog_Builder.setMessage(
													"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
											alertDialog_Builder.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(DialogInterface arg0, int arg1) {
														}
													});
											alertDialog_Builder.setCancelable(false);
											AlertDialog alert_Dialog = alertDialog_Builder.create();
											alert_Dialog.show();
											alert_Dialog.getWindow().getAttributes();

											TextView textView2 = (TextView) alert_Dialog
													.findViewById(android.R.id.message);
											textView2.setTextSize(28);
											textView2.setGravity(Gravity.CENTER);
											textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);

											Button btn2 = alert_Dialog.getButton(DialogInterface.BUTTON_POSITIVE);
											btn2.setTextSize(22);
											btn2.setTextColor(Color.WHITE);
											btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
											btn2.setBackgroundColor(Color.RED);

										}
									}
								} else if (Fake_NO_Dialog.fake_action == "fake") {

									TextView title = new TextView(SpotChallan.this);
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

									String otp_message = "\n It's a Fake Vehicle !!! \n";

									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
											AlertDialog.THEME_HOLO_LIGHT);
									alertDialogBuilder.setCustomTitle(title);
									alertDialogBuilder.setIcon(R.drawable.dialog_logo);
									alertDialogBuilder.setMessage(otp_message);
									alertDialogBuilder.setCancelable(false);
									alertDialogBuilder.setPositiveButton("Ok",

											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													// TODO Auto-generated
													// method stub
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
							}
						} else {
							completeVehicle_num_send = "";// AP09CC3014
							regncode_send = "";// AP09
							regnName_send = "";
							vehicle_num_send = "";// 3014

							regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
							regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
							vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

							completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
									+ vehicle_num_send);
							Log.i("**VE*", "" + completeVehicle_num_send);

							getDateAndTime();
							int pos = 0;
							Log.i("**VEHCI*", "" + violation_positions.size());

							if (Fake_NO_Dialog.fake_action == "not fake") {
								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else {

									if (DLvalidFLG.equals("VALID")) {
										new Async_getDetainedItems().execute();
									} else {

										TextView title3 = new TextView(SpotChallan.this);
										title3.setText("ALERT");
										title3.setBackgroundColor(Color.RED);
										title3.setGravity(Gravity.CENTER);
										title3.setTextColor(Color.WHITE);
										title3.setTextSize(26);
										title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
										title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
												R.drawable.dialog_logo, 0);
										title3.setPadding(20, 0, 20, 0);
										title3.setHeight(70);

										AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
												SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
										alertDialog_Builder.setCustomTitle(title3);
										alertDialog_Builder.setIcon(R.drawable.dialog_logo);
										alertDialog_Builder.setMessage(
												"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
										alertDialog_Builder.setPositiveButton("Ok",
												new DialogInterface.OnClickListener() {
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

								}
							} else if (Fake_NO_Dialog.fake_action == null) {
								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else {

									if (DLvalidFLG.equals("VALID")) {
										new Async_getDetainedItems().execute();
									} else {

										TextView title3 = new TextView(SpotChallan.this);
										title3.setText("ALERT");
										title3.setBackgroundColor(Color.RED);
										title3.setGravity(Gravity.CENTER);
										title3.setTextColor(Color.WHITE);
										title3.setTextSize(26);
										title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
										title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
												R.drawable.dialog_logo, 0);
										title3.setPadding(20, 0, 20, 0);
										title3.setHeight(70);

										AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(
												SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
										alertDialog_Builder.setCustomTitle(title3);
										alertDialog_Builder.setIcon(R.drawable.dialog_logo);
										alertDialog_Builder.setMessage(
												"\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
										alertDialog_Builder.setPositiveButton("Ok",
												new DialogInterface.OnClickListener() {

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
								}
							} else if (Fake_NO_Dialog.fake_action == "fake") {

								TextView title = new TextView(SpotChallan.this);
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

								String otp_message = "\n It's a Fake Vehicle !!! \n";

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
										AlertDialog.THEME_HOLO_LIGHT);
								alertDialogBuilder.setCustomTitle(title);
								alertDialogBuilder.setIcon(R.drawable.dialog_logo);
								alertDialogBuilder.setMessage(otp_message);
								alertDialogBuilder.setCancelable(false);
								alertDialogBuilder.setPositiveButton("Ok",

										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method
												// stub
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
						}
					} else {
						showToast("" + NETWORK_TXT);
					}
				}
			}

			else if ((Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory"))
					|| (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

				if (et_regcid_spot.getText().toString().trim().equals("")) {
					et_regcid_spot.setError(Html.fromHtml("<font color='black'>Enter Registration Code</font>"));
					showToast("Enter Registration Code");

				}
				/*
				 * else if
				 * (et_last_num_spot.getText().toString().trim().equals("")) {
				 * et_last_num_spot.setError(Html.
				 * fromHtml("<font color='black'>Enter Vehicle Number</font>"));
				 * showToast("Enter Vehicle Code"); }
				 */
				else {
					if (isOnline()) {
						Log.i("VHCLE HS", "" + ServiceHelper.pending_challans_details.length);

						if ((Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory"))
								&& (ServiceHelper.pending_challans_details.length > 0)) {
							violations_details_send = new StringBuffer("");
							violations_details_send.delete(0, violations_details_send.length());

							completeVehicle_num_send = "";// AP09CC3014
							regncode_send = "";// AP09
							regnName_send = "";
							vehicle_num_send = "";// 3014

							regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
							regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
							vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

							completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
									+ vehicle_num_send);
							Log.i("**VEH**", "" + completeVehicle_num_send);

							getDateAndTime();
							int pos = 0;
							Log.i("**VEHCI**", "" + violation_positions.size());

							if (Fake_NO_Dialog.fake_action == "not fake") {

								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();

								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();

								} else if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
										&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
									otp_msg = "Please enter driver Licence Number or Aadhar Number";
									removeDialog(OTP_CNFRMTN_DIALOG);
									showDialog(OTP_CNFRMTN_DIALOG);

								} /*
									 * else if (VehicleHistoryPendingChallans.
									 * total_amount_selected_challans == 0.0) {
									 * otp_msg =
									 * "Please Select Pending Challan";
									 * removeDialog(OTP_CNFRMTN_DIALOG);
									 * showDialog(OTP_CNFRMTN_DIALOG); }
									 */else {
									new Async_getDetainedItems().execute();
								}
							} else if (Fake_NO_Dialog.fake_action == null) {
								if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() != 12) {
									et_aadharnumber_spot.setError(Html.fromHtml(
											"<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
										&& et_aadharnumber_spot.getText().toString().length() == 12
										&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
									et_aadharnumber_spot.setError(
											Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
									et_aadharnumber_spot.requestFocus();
								} else if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
										&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
									otp_msg = "Please enter driver Licence Number or Aadhar Number";
									removeDialog(OTP_CNFRMTN_DIALOG);
									showDialog(OTP_CNFRMTN_DIALOG);

								} /*
									 * else if (VehicleHistoryPendingChallans.
									 * total_amount_selected_challans == 0.0) {
									 * otp_msg =
									 * "Please Select Pending Challan";
									 * removeDialog(OTP_CNFRMTN_DIALOG);
									 * showDialog(OTP_CNFRMTN_DIALOG); }
									 */else {
									new Async_getDetainedItems().execute();
								}
							} else if (Fake_NO_Dialog.fake_action == "fake") {
								TextView title = new TextView(SpotChallan.this);
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

								String otp_message = "\n It's a Fake Vehicle !!! \n";

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
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

						} else if ((Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

							String threeWheeler = tv_vhle_no_spot.getText().toString().trim();

							if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
									&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
								otp_msg = "Please enter driver Licence Number or Aadhar Number";
								removeDialog(OTP_CNFRMTN_DIALOG);
								showDialog(OTP_CNFRMTN_DIALOG);

							} else {
								violations_details_send = new StringBuffer("");
								violations_details_send.delete(0, violations_details_send.length());

								completeVehicle_num_send = "";// AP09CC3014
								regncode_send = "";// AP09
								regnName_send = "";
								vehicle_num_send = "";// 3014

								regncode_send = et_regcid_spot.getText().toString().trim().toUpperCase();
								regnName_send = "" + et_vchl_num_spot.getText().toString().trim().toUpperCase();
								vehicle_num_send = et_last_num_spot.getText().toString().trim().toUpperCase();

								completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + ""
										+ vehicle_num_send);
								Log.i("**VEHCIL*", "" + completeVehicle_num_send);

								getDateAndTime();
								int pos = 0;
								Log.i("**VEH*", "" + violation_positions.size());

								if (Fake_NO_Dialog.fake_action == "not fake") {

									if (rb_indian.isChecked() == true && passport_layout.getVisibility() == View.GONE) {

										if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
												&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
											otp_msg = "Please enter driver Licence Number or Aadhar Number";
											removeDialog(OTP_CNFRMTN_DIALOG);
											showDialog(OTP_CNFRMTN_DIALOG);
										} else {
											new Async_getDetainedItems().execute();
										}

									} else if (rb_nri.isChecked() == true
											&& passport_layout.getVisibility() == View.VISIBLE) {
										if (rb_nri.isChecked() == true
												&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
											et_driver_lcnce_num_spot.setError(
													Html.fromHtml("<font color='black'>Enter DL Number</font>"));
											et_driver_lcnce_num_spot.requestFocus();
										} else if (rb_nri.isChecked() == true
												&& et_passport.getText().toString().trim().equals("")) {
											et_passport.setError(
													Html.fromHtml("<font color='black'>Enter Passport Number</font>"));
											et_passport.requestFocus();
										} else {
											new Async_getDetainedItems().execute();
										}
									}
								} else if (Fake_NO_Dialog.fake_action == null) {
									if (rb_indian.isChecked() == true && passport_layout.getVisibility() == View.GONE) {
										if ((et_driver_lcnce_num_spot.getText().toString().trim().equals(""))
												&& (et_aadharnumber_spot.getText().toString().trim().equals(""))) {
											otp_msg = "Please enter driver Licence Number or Aadhar Number";
											removeDialog(OTP_CNFRMTN_DIALOG);
											showDialog(OTP_CNFRMTN_DIALOG);
										} else {
											new Async_getDetainedItems().execute();
										}
									} else if (rb_nri.isChecked() == true
											&& passport_layout.getVisibility() == View.VISIBLE) {
										if (rb_nri.isChecked() == true
												&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
											et_driver_lcnce_num_spot.setError(
													Html.fromHtml("<font color='black'>Enter DL Number</font>"));
											et_driver_lcnce_num_spot.requestFocus();
										} else if (rb_nri.isChecked() == true
												&& et_passport.getText().toString().trim().equals("")) {
											et_passport.setError(
													Html.fromHtml("<font color='black'>Enter Passport Number</font>"));
											et_passport.requestFocus();
										} else {
											new Async_getDetainedItems().execute();
										}
									}
								} else if (Fake_NO_Dialog.fake_action == "fake") {

									TextView title = new TextView(SpotChallan.this);
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

									String otp_message = "\n It's a Fake Vehicle !!! \n";

									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
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

							}

						} else {
							showToast("" + getResources().getString(R.string.no_pending_challans));
						}
					} else {
						showToast("" + NETWORK_TXT);
					}
				}

			}

			else {
				showToast("" + getResources().getString(R.string.no_pending_challans));
			}
			break;

		case R.id.imgv_camera_capture_spotchallan_xml:
			/*
			 * cam_imag = ""; cam_imag = "camera"; if (isDeviceSupportCamera())
			 * {
			 *
			 * Intent cameraIntent = new
			 * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			 * startActivityForResult(cameraIntent, CAMERA_REQUEST);
			 *
			 * LARGE IMAGE START Intent intent = new
			 * Intent("android.media.action.IMAGE_CAPTURE"); File file = new
			 * File(Environment.getExternalStorageDirectory() + File.separator +
			 * "image.jpg"); intent.putExtra(MediaStore.EXTRA_OUTPUT,
			 * Uri.fromFile(file)); startActivityForResult(intent,
			 * CAMERA_REQUEST); LARGE IMAGE END } else {
			 * showToast("Sorry! Your device doesn't support camera"); }
			 */
			selectImagefrom_Camera();
			break;

		case R.id.imgv_gallery_spotchallan_xml:

			/*
			 * cam_imag = ""; cam_imag = "browse";
			 *
			 * Intent in_gallery = new Intent( Intent.ACTION_PICK,
			 * android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			 * startActivityForResult(in_gallery, RESULT_LOAD_IMAGE);
			 */

			/*
			 * Intent gallery_intent = new
			 * Intent(Intent.ACTION_PICK,android.provider
			 * .MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			 * startActivityForResult(gallery_intent, 2);
			 */

			selectImagefrom_Gallery();
			break;

		case R.id.btn_select_idproff_spotchallantwo_xml:
			showDialog(ID_PROOF_DIALOG);
			break;

		case R.id.btn_select_profession:
			showDialog(OCCUPATION_DIALOG);
			break;

		default:
			break;
		}
	}

	private void selectImagefrom_Gallery() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 2);
	}

	private void selectImagefrom_Camera() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		startActivityForResult(intent, 1);
		imgSelected = "1";
	}

	/* ACTIVITYRESULT FOR ACCESSING IMAGES FROM GALLERY AND CAMERA */
	/*
	 * protected void onActivityResult(int requestCode, int resultCode, Intent
	 * data) { super.onActivityResult(requestCode, resultCode, data);
	 *
	 * if (resultCode == RESULT_OK) { if (requestCode == 1) { File f = new
	 * File(Environment.getExternalStorageDirectory().toString()); for (File
	 * temp : f.listFiles()) { if (temp.getName().equals("temp.jpg")) { f =
	 * temp; break; } } try { Bitmap bitmap; BitmapFactory.Options bitmapOptions
	 * = new BitmapFactory.Options();
	 *
	 * bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
	 *
	 * offender_image.setImageBitmap(bitmap);
	 * offender_image.setVisibility(View.VISIBLE);
	 *
	 * String path = android.os.Environment.getExternalStorageDirectory() +
	 * File.separator + "E-Ticket" + File.separator + Current_Date; f.delete();
	 * OutputStream outFile = null; File file = new File(path,
	 * String.valueOf(System.currentTimeMillis()) + ".jpg"); try { outFile = new
	 * FileOutputStream(file); bitmap.compress(Bitmap.CompressFormat.JPEG, 85,
	 * outFile); outFile.flush(); outFile.close(); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); } }
	 * catch (Exception e) { e.printStackTrace(); } } else if (requestCode == 2)
	 * { Uri selectedImage = data.getData(); String[] filePath = {
	 * MediaStore.Images.Media.DATA }; Cursor c =
	 * getContentResolver().query(selectedImage,filePath, null, null, null);
	 * c.moveToFirst(); int columnIndex = c.getColumnIndex(filePath[0]); String
	 * picturePath = c.getString(columnIndex); c.close(); Bitmap thumbnail =
	 * (BitmapFactory.decodeFile(picturePath));
	 * Log.w("path of image from gallery......******************.........",
	 * picturePath+""); offender_image.setImageBitmap(thumbnail);
	 * offender_image.setVisibility(View.VISIBLE); } } }
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			@SuppressWarnings("unused")
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			String picturePath = "";
			if (requestCode == 1) {

				File f = new File(Environment.getExternalStorageDirectory().toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					String current_date = SpotChallan.date;
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

					String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "E-Ticket"
							+ File.separator + current_date;
					File camerapath = new File(path);

					if (!camerapath.exists()) {
						camerapath.mkdirs();
					}
					f.delete();
					OutputStream outFile = null;
					File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

					try {
						// Log.i("Camera Path :::",""+file.getAbsolutePath());
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

						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, outFile);
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

					if (bitmap != null) {
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
						offender_image.setVisibility(View.VISIBLE);
						offender_image.setImageBitmap(mutableBitmap);

						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
						mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);

						byteArray = bytes.toByteArray();
						final_image_data_tosend = Base64.encodeToString(byteArray, Base64.NO_WRAP);
						offender_image.setVisibility(View.VISIBLE);
						// Log.i("imgString 2::", ""+imgString);

					} else if (bitmap == null) {
						showToast("Image Cannot be Loaded !");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (requestCode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				picturePath = c.getString(columnIndex);
				c.close();

				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				Log.w("path of *.........", picturePath + "");

				if (thumbnail != null) {
					Bitmap mutableBitmap = thumbnail.copy(Bitmap.Config.ARGB_8888, true);
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
					final_image_data_tosend = Base64.encodeToString(byteArray, Base64.NO_WRAP);
					offender_image.setVisibility(View.VISIBLE);
				} else if (thumbnail == null) {
					showToast("Image Cannot be Loaded !");
				}
			}
		}
	}

	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST
		// QUALITY
		// MATCH

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize, Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		int inSampleSize = 1;
		if (height > reqHeight) {
			inSampleSize = Math.round((float) height / (float) reqHeight);
		}

		int expectedWidth = width / inSampleSize;
		if (expectedWidth > reqWidth) {
			// if(Math.round((float)width / (float)reqWidth) > inSampleSize) //
			// If bigger SampSize..
			inSampleSize = Math.round((float) width / (float) reqWidth);
		}

		options.inSampleSize = inSampleSize;
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/* TO GET RTA DETAILS */
	public class Async_getRTADetails extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			message = new StringBuffer();

			regncode_send = "" + et_regcid_spot.getText().toString().trim().toUpperCase();
			regnName_send = "" + et_vchl_num_spot.getText().toString().toUpperCase();
			vehicle_num_send = "" + et_last_num_spot.getText().toString().trim().toUpperCase();

			aadhaar = et_aadharnumber_spot.getText().toString().trim();
			licence_no = et_driver_lcnce_num_spot.getText().toString().trim();

			completeVehicle_num_send = ("" + regncode_send + "" + regnName_send + "" + vehicle_num_send);

			/*
			 * if (!(ServiceHelper.onlinebuff==null)) { ServiceHelper.onlinebuff
			 * = new StringBuffer(""); ServiceHelper.onlinebuff.delete(0,
			 * ServiceHelper.onlinebuff.length()); //v }
			 */

			ServiceHelper.getRTADetails("" + completeVehicle_num_send);
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			tv_vehicle_details_header_spot.setVisibility(View.VISIBLE);
			// rl_licence_details_layout.setVisibility(View.GONE);

			if (ServiceHelper.rta_data.toString().trim().equals("1")) {
				showToast("Invalid Login ID");
			} else if (ServiceHelper.rta_data.toString().trim().equals("2")) {
				showToast("Invalid Password");
			} else if (ServiceHelper.rta_data.toString().trim().equals("3")) {
				showToast("Unautherized Device");
			} else if (ServiceHelper.rta_data.toString().trim().equals("4")) {
				showToast("Error, Please Contact E Challan Team at 040-27852721");
			} else if ((!ServiceHelper.rta_data.equals("0"))) {
				// new Async_getPendingChallans().execute();

				Async_getOffenderRemarks offender_task = new Async_getOffenderRemarks();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
					/*
					 * try { Thread.sleep(5000); } catch (InterruptedException
					 * e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */
					offender_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					offender_task.execute();
				}

				SpotChallan.rta_details_spot_master = new String[0];

				SpotChallan.rta_details_spot_master = ServiceHelper.rta_data.split("!");

				SpotChallan.Wheeler_check = rta_details_spot_master[0].split(":");

				String Wheeler_Enable_check = SpotChallan.Wheeler_check[1].toString();

				if (!Wheeler_Enable_check.equalsIgnoreCase("NA")) {
					btn_wheller_code.setClickable(false);
				} else {
					btn_wheller_code.setClickable(true);
				}

				Log.i("**getRTADetails SPOT***", "" + ServiceHelper.rta_data);

				Log.i("jnsdj", "" + SpotChallan.rta_details_spot_master.length);
				// start to display details as card system
				rl_rta_details_layout.setVisibility(View.VISIBLE);
				Log.i("RegnNo ::::", "" + rta_details_spot_master[0]); // --->0
				tv_vehicle_details_header_spot.setText("VEHICLE DETAILS");
				tv_vhle_no_spot.setText("" + completeVehicle_num_send);
				tv_owner_name_spot.setText("" + rta_details_spot_master[1]);
				tv_address_spot.setText("" + rta_details_spot_master[2] + ", " + rta_details_spot_master[3]);
				// tv_city_spot.setText("" + rta_details_spot_master[3]);
				tv_maker_name_spot.setText("" + rta_details_spot_master[4] + ", " + rta_details_spot_master[5] + ", "
						+ rta_details_spot_master[6]);
				// tv_maker_class_spot.setText("" + rta_details_spot_master[5]);
				// tv_color_spot.setText("" + rta_details_spot_master[6]);
				tv_engine_no_spot.setText("" + rta_details_spot_master[7]);
				tv_chasis_no_spot.setText("" + rta_details_spot_master[8]);
				// end to display details as card system

				// ********************Dynamic Wheeler code
				// Assignment***************************
				Log.i("Response wheeler :::", "" + rta_details_spot_master[0]);
				if (rta_details_spot_master[0].equals("NA")) {
					tv_vehicle_details_header_spot.setText("VEHICLE DETAILS NOT FOUND!");
					rl_rta_details_layout.setVisibility(View.GONE);

				} else if (rta_details_spot_master != null && rta_details_spot_master[9] != null
						&& !"NA".equals(rta_details_spot_master[9])) {

					/*
					 * else if (rta_details_spot_master != null &&
					 * rta_details_spot_master[0].split("\\:")[1] != null &&
					 * !"NA".equals(rta_details_spot_master[0].split("\\:")[1].
					 * trim())) {
					 */
					// whlr_code_send =
					// rta_details_spot_master[0].split("\\:")[1];// 33
					whlr_code_send = rta_details_spot_master[9];// WHEELER CODE
					Log.i("whlr_co:::", whlr_code_send);
					if (whlr_code_send != null) {
						btn_wheller_code.setText("" + whlr_code_send);
						Log.i("whlr_c::", "Called");
						new Async_getViolations().execute();
					} else {
						btn_wheller_code.setClickable(true);
					}
				}

				// ********************Dynamic Wheeler code
				// Assignment***************************

				/*
				 * radioGroupButton_isDriver.setChecked(true); if
				 * (radioGroupButton_isDriver.isChecked() == true) {
				 * et_drivername_iOD.setText("");
				 * et_driverFatherName_iOD.setText("");
				 * et_driver_address_iOD.setText("");
				 * et_driver_city_iOD.setText("");
				 *
				 * et_drivername_iOD.setEnabled(true);
				 * et_drivername_iOD.setClickable(true);
				 *
				 * et_driverFatherName_iOD.setEnabled(true);
				 * et_driverFatherName_iOD.setClickable(true);
				 *
				 * et_driver_address_iOD.setEnabled(true);
				 * et_driver_address_iOD.setClickable(true);
				 *
				 * et_driver_city_iOD.setEnabled(true);
				 * et_driver_city_iOD.setClickable(true);
				 *
				 * }
				 *
				 * radioGroupButton_isDriver.setOnClickListener(new
				 * OnClickListener() {
				 *
				 * @Override public void onClick(View v) { // TODO
				 * Auto-generated method stub et_drivername_iOD.setText("");
				 * et_driverFatherName_iOD.setText("");
				 * et_driver_address_iOD.setText("");
				 * et_driver_city_iOD.setText("");
				 *
				 * et_drivername_iOD.setEnabled(true);
				 * et_drivername_iOD.setClickable(true);
				 *
				 * et_driverFatherName_iOD.setEnabled(true);
				 * et_driverFatherName_iOD.setClickable(true);
				 *
				 * et_driver_address_iOD.setEnabled(true);
				 * et_driver_address_iOD.setClickable(true);
				 *
				 * et_driver_city_iOD.setEnabled(true);
				 * et_driver_city_iOD.setClickable(true); } });
				 */

				/*
				 * radioGroupButton_isOwner.setOnClickListener(new
				 * OnClickListener() {
				 *
				 * @Override public void onClick(View v) { // TODO
				 * Auto-generated method stub if (rta_details_spot_master.length
				 * > 0) { et_drivername_iOD.setText("" +
				 * rta_details_spot_master[1]);
				 * et_driverFatherName_iOD.setText("");
				 * et_driver_address_iOD.setText("" +
				 * rta_details_spot_master[2]); et_driver_city_iOD.setText("" +
				 * rta_details_spot_master[3]);
				 *
				 * if (et_drivername_iOD.getText().toString().trim().length() >
				 * 0) { et_drivername_iOD.setEnabled(false);
				 * et_drivername_iOD.setClickable(false); } else if
				 * (et_drivername_iOD.getText().toString().trim().length() == 0)
				 * { et_drivername_iOD.setEnabled(true);
				 * et_drivername_iOD.setClickable(true); }
				 *
				 * if
				 * (et_driverFatherName_iOD.getText().toString().trim().length()
				 * > 0) { et_driverFatherName_iOD.setEnabled(false);
				 * et_driverFatherName_iOD.setClickable(false); } else if
				 * (et_driverFatherName_iOD.getText().toString().trim().length()
				 * == 0) { et_driverFatherName_iOD.setEnabled(true);
				 * et_driverFatherName_iOD.setClickable(true); }
				 *
				 * if
				 * (et_driver_address_iOD.getText().toString().trim().length() >
				 * 0) { et_driver_address_iOD.setEnabled(false);
				 * et_driver_address_iOD.setClickable(false); } else if
				 * (et_driver_address_iOD.getText().toString().trim().length()
				 * == 0) { et_driver_address_iOD.setEnabled(true);
				 * et_driver_address_iOD.setClickable(true); }
				 *
				 * if (et_driver_city_iOD.getText().toString().trim().length() >
				 * 0) { et_driver_city_iOD.setEnabled(false);
				 * et_driver_city_iOD.setClickable(false); } else if
				 * (et_driver_city_iOD.getText().toString().trim().length() ==
				 * 0) { et_driver_city_iOD.setEnabled(true);
				 * et_driver_city_iOD.setClickable(true); } } } });
				 */

				/*------is_owner/is_driver:only for spot challan : 29-01-2015----------*/
				if ((Dashboard.check_vhleHistory_or_Spot.equals("spot"))) {
					if (et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
						licence_details_spot_master = new String[0];
						otp_msg = "Please Enter driving license number (or)\nAdd violation - without driving license/\n Add violation-Without Carrying DL";
						removeDialog(OTP_CNFRMTN_DIALOG);
						showDialog(OTP_CNFRMTN_DIALOG);
					}
					ll_drivertype_rgbtn.setVisibility(View.GONE);
					ll_is_owner_driver.setVisibility(View.VISIBLE);

				} else if ((Dashboard.check_vhleHistory_or_Spot.equals("towing"))
						|| (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
						|| (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

					if (et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
						licence_details_spot_master = new String[0];
						if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
								|| (Dashboard.check_vhleHistory_or_Spot.equals("towing"))
								|| (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))) {
						} else {
							otp_msg = "Please Enter driving license number (or)\nAdd violation - without driving license/\n Add violation-Without Carrying DL";
							removeDialog(OTP_CNFRMTN_DIALOG);
							showDialog(OTP_CNFRMTN_DIALOG);
						}
					}
					ll_drivertype_rgbtn.setVisibility(View.GONE);
					ll_is_owner_driver.setVisibility(View.VISIBLE);

					/*
					 * if
					 * ((!rta_details_spot_master[10].toString().trim().equals
					 * ("NA"))) { showDialog(FAKE_NUMBERPLATE_DIALOG); }
					 */
				} else {
					ll_drivertype_rgbtn.setVisibility(View.GONE);
					ll_is_owner_driver.setVisibility(View.VISIBLE);
				}
			} else {
				if ((Dashboard.check_vhleHistory_or_Spot.equals("spot"))) {

				} else {
					et_drivername_iOD.setText("");
					et_driverFatherName_iOD.setText("");
					et_driver_address_iOD.setText("");
					et_driver_city_iOD.setText("");

					et_drivername_iOD.setEnabled(true);
					et_drivername_iOD.setClickable(true);

					et_driverFatherName_iOD.setEnabled(true);
					et_driverFatherName_iOD.setClickable(true);

					et_driver_address_iOD.setEnabled(true);
					et_driver_address_iOD.setClickable(true);

					et_driver_city_iOD.setEnabled(true);
					et_driver_city_iOD.setClickable(true);

					ll_drivertype_rgbtn.setVisibility(View.GONE);
					ll_is_owner_driver.setVisibility(View.VISIBLE);
				}

				tv_vehicle_details_header_spot.setText("VEHICLE DETAILS NOT FOUND!");
				rl_rta_details_layout.setVisibility(View.GONE);
				// showToast("No Details Found !");
			}
		}
	}

	/* LICENCE DETAILS */
	public class Async_getLicenceDetails extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// public String getAADHARData(String uid,String eid,String
			// imei,String simNo,String gpsLattitude,String gpsLongitude);
			ServiceHelper.getLicenceDetails("" + et_driver_lcnce_num_spot.getText().toString().trim());
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@SuppressWarnings("unused")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			// showToast("" + licene_details_master.length);
			String threeWheeler = tv_vhle_no_spot.getText().toString().trim();
			tv_licence_details_header_spot.setVisibility(View.VISIBLE);

			ll_validationString = ServiceHelper.license_data;

			System.out.println("License Details:" + ll_validationString);

			if (ServiceHelper.license_data != null) {
				Log.i("licence::", "" + licence_details_spot_master);

				if (ServiceHelper.license_data.toString().trim().equals("0")) {
					tv_licence_details_header_spot.setText("DRIVING LICENCE DETAILS NOT FOUND");
					// rl_licence_details_layout.setVisibility(View.GONE);

					if (et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() == 12
							&& ver.isValid(et_aadharnumber_spot.getText().toString())) {
						// new Async_getAadharDetails().execute();
					} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() != 12
							&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
						et_aadharnumber_spot.setError(
								Html.fromHtml("<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
						et_aadharnumber_spot.requestFocus();
					}

				} else if (ServiceHelper.license_data.toString().trim().equals("1")) {
					showToast("Invalid Login ID");
				} else if (ServiceHelper.license_data.toString().trim().equals("2")) {
					showToast("Invalid Password");
				} else if (ServiceHelper.license_data.toString().trim().equals("3")) {
					showToast("Unautherized Device");
				} else if (ServiceHelper.license_data.toString().trim().equals("4")) {
					showToast("Error, Please Contact E Challan Team at 040-27852721");
				} else if ((!ServiceHelper.license_data.equals("0")) && (licence_details_spot_master.length > 0)) {

					rl_licence_details_layout.setVisibility(View.VISIBLE);

					tv_licnce_ownername_spot.setText("" + licence_details_spot_master[0]);
					tv_lcnce_father_name_spot.setText("" + licence_details_spot_master[1]);
					tv_lcnce_phone_number_spot.setText("" + licence_details_spot_master[2]);
					tv_lcnce_address_spot.setText("" + licence_details_spot_master[4]);

					et_drivername_iOD.setText("" + licence_details_spot_master[0]);
					et_driverFatherName_iOD.setText("" + licence_details_spot_master[1]);
					et_driver_city_iOD.setText("" + licence_details_spot_master[2]);
					et_driver_address_iOD.setText("" + licence_details_spot_master[4]);

					/******* start driving license image display ******/
					if (licence_details_spot_master[5].toString().trim().equals("0")) {
						licence_image.setImageResource(R.drawable.photo);
					} else {
						byte[] decodestring = Base64.decode("" + licence_details_spot_master[5].toString().trim(),
								Base64.DEFAULT);
						Bitmap decocebyte = BitmapFactory.decodeByteArray(decodestring, 0, decodestring.length);
						licence_image.setImageBitmap(decocebyte);
					}
					/******* end driving license image display ******/

					dl_no.setText("" + et_driver_lcnce_num_spot.getText().toString().trim());
					tv_licence_details_header_spot.setText("LICENCE DETAILS");

					Log.i("DL flag ::::", "" + licence_details_spot_master[6]);
					DLvalidFLG = "" + licence_details_spot_master[6];
					Log.i("DLvalidFLG ::::", "" + DLvalidFLG);
					/*
					 * if
					 * (licence_details_spot_master[5].toString().trim().equals
					 * ("0")) {
					 *
					 * licence_image.setImageResource(R.drawable.photo);
					 *
					 * } else { byte[] decodestring = Base64.decode(""+
					 * licence_details_spot_master[5].toString().trim(),
					 * Base64.DEFAULT); Bitmap decocebyte =
					 * BitmapFactory.decodeByteArray(decodestring, 0,
					 * decodestring.length);
					 * licence_image.setImageBitmap(decocebyte); }
					 */

					if (licence_details_spot_master.length != 0) {
						et_drivername_iOD.setText("" + licence_details_spot_master[0].toUpperCase());
						et_driverFatherName_iOD.setText("" + licence_details_spot_master[1].toUpperCase());
						et_driver_address_iOD.setText("" + licence_details_spot_master[4].toUpperCase());
						et_driver_city_iOD.setText("");

						if (et_drivername_iOD.getText().toString().trim().length() > 0) {
							et_drivername_iOD.setEnabled(false);
							et_drivername_iOD.setClickable(false);
						} else if (et_drivername_iOD.getText().toString().trim().length() == 0) {
							et_drivername_iOD.setEnabled(true);
							et_drivername_iOD.setClickable(true);
						}

						if (et_driverFatherName_iOD.getText().toString().trim().length() > 0) {
							et_driverFatherName_iOD.setEnabled(false);
							et_driverFatherName_iOD.setClickable(false);
						} else if (et_driverFatherName_iOD.getText().toString().trim().length() == 0) {
							et_driverFatherName_iOD.setEnabled(true);
							et_driverFatherName_iOD.setClickable(true);
						}

						if (et_driver_address_iOD.getText().toString().trim().length() > 0) {
							et_driver_address_iOD.setEnabled(false);
							et_driver_address_iOD.setClickable(false);
						} else if (et_driver_address_iOD.getText().toString().trim().length() == 0) {
							et_driver_address_iOD.setEnabled(true);
							et_driver_address_iOD.setClickable(true);
						}

						if (et_driver_city_iOD.getText().toString().trim().length() > 0) {
							et_driver_city_iOD.setEnabled(false);
							et_driver_city_iOD.setClickable(false);
						} else if (et_driver_city_iOD.getText().toString().trim().length() == 0) {
							et_driver_city_iOD.setEnabled(true);
							et_driver_city_iOD.setClickable(true);
						}
					}

					if (DLvalidFLG.equals("VALID")) {

					} else if (DLvalidFLG.equals("INVALID")) {
						TextView title3 = new TextView(SpotChallan.this);
						title3.setText("ALERT");
						title3.setBackgroundColor(Color.RED);
						title3.setGravity(Gravity.CENTER);
						title3.setTextColor(Color.WHITE);
						title3.setTextSize(26);
						title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
						title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
								R.drawable.dialog_logo, 0);
						title3.setPadding(20, 0, 20, 0);
						title3.setHeight(70);

						AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(SpotChallan.this,
								AlertDialog.THEME_HOLO_LIGHT);
						alertDialog_Builder.setCustomTitle(title3);
						alertDialog_Builder.setIcon(R.drawable.dialog_logo);
						alertDialog_Builder
								.setMessage("\n Driving Licence has been Expired\n Please Add Without DL Violation\n ");
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

					/*
					 * if(et_aadharnumber_spot.getText().toString().trim().
					 * length ()>1 &&
					 * et_aadharnumber_spot.getText().toString().length()==12 &&
					 * ver.isValid(et_aadharnumber_spot.getText().toString())){
					 * new Async_getAadharDetails().execute(); }else
					 */
					if (et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() == 12
							&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
						et_aadharnumber_spot.setError(
								Html.fromHtml("<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
						et_aadharnumber_spot.requestFocus();
					}
				}
			} else {
				tv_licence_details_header_spot.setText("DRIVING LICENCE DETAILS NOT FOUND");
				// rl_licence_details_layout.setVisibility(View.GONE);
			}
		}
	}

	public class Async_checkAadhaarExists extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// checkAadhar_Ticket(String unit_Code,String regn_No,String
			// eng_No,String chasis_No)
			ServiceHelper.checkAadhar_Ticket("" + Dashboard.UNIT_CODE, "" + completeVehicle_num_send, "", "");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			if (ServiceHelper.aadhaarDetailsCheck_resp != null) {

				if (ServiceHelper.aadhaarDetailsCheck_resp.equals("NOTEXIST")) {
					new Async_getVioPoints().execute();
					if (et_aadharnumber_spot.getText().toString().length() == 0) {
						// new Async_getRTADetails().execute();
					} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() == 12
							&& ver.isValid(et_aadharnumber_spot.getText().toString())) {

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								// new Async_getRTADetails().execute();
							}
						});

					} else if (et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() == 12
							&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
						TextView title = new TextView(SpotChallan.this);
						title.setText("ALERT");
						title.setBackgroundColor(Color.RED);
						title.setGravity(Gravity.CENTER);
						title.setTextColor(Color.WHITE);
						title.setTextSize(26);
						title.setTypeface(title.getTypeface(), Typeface.BOLD);
						title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo,
								0);
						title.setPadding(20, 0, 20, 0);
						title.setHeight(70);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
								AlertDialog.THEME_HOLO_LIGHT);
						alertDialogBuilder.setCustomTitle(title);
						alertDialogBuilder.setIcon(R.drawable.dialog_logo);
						alertDialogBuilder
								.setMessage("\n It's a Invalid Aadhar, \nPlease Enter Valid Aadhar Number !!\n");
						alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// et_aadharnumber_spot.setText("");
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

					} else if (et_regcid_spot.getText().toString().equals("")
							|| et_last_num_spot.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(),
								(Html.fromHtml("<font color='black'>Enter Proper Vehicle Number</font>")),
								Toast.LENGTH_LONG).show();
					}

					if (isOnline()) {
						rl_rta_details_layout.setVisibility(View.GONE);
						Dashboard.rta_details_request_from = "SPOT";

						if (et_aadharnumber_spot.getText().toString().trim() != null
								&& et_aadharnumber_spot.getText().toString().trim().length() > 1
								&& et_aadharnumber_spot.getText().toString().length() != 12) {
							TextView title = new TextView(SpotChallan.this);
							title.setText("ALERT");
							title.setBackgroundColor(Color.RED);
							title.setGravity(Gravity.CENTER);
							title.setTextColor(Color.WHITE);
							title.setTextSize(26);
							title.setTypeface(title.getTypeface(), Typeface.BOLD);
							title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
									R.drawable.dialog_logo, 0);
							title.setPadding(20, 0, 20, 0);
							title.setHeight(70);

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
									AlertDialog.THEME_HOLO_LIGHT);
							alertDialogBuilder.setCustomTitle(title);
							alertDialogBuilder.setIcon(R.drawable.dialog_logo);
							alertDialogBuilder.setMessage("\n Aadhaar Number Length must be 12 digits!! \n");
							alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// et_aadharnumber_spot.setText("");
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

						} else if (et_aadharnumber_spot.getText().toString().trim().length() == 12
								&& (ver.isValid(et_aadharnumber_spot.getText().toString()))) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									// new Async_getAadharDetails().execute();
								}
							});
						}

						if (et_driver_lcnce_num_spot.getText().toString().length() != 0) {
							Dashboard.licence_details_request_from = "SPOT";
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									// new Async_getLicenceDetails().execute();
								}
							});
						}

						/*
						 * if (et_aadharnumber_spot.getText().toString().trim().
						 * length ()==12 &&
						 * (ver.isValid(et_aadharnumber_spot.getText().
						 * toString())) &&
						 * et_driver_lcnce_num_spot.getText().toString
						 * ().length()!=0 ) { new
						 * Async_getVioPoints().execute(); }
						 */
						ll_aadhar_layout.setVisibility(View.GONE);
						tv_aadhar_header.setVisibility(View.GONE);
					}
				} else if (ServiceHelper.aadhaarDetailsCheck_resp.equals("EXIST")) {
					TextView title = new TextView(SpotChallan.this);
					title.setText("Hyderabad E-Ticket");
					title.setBackgroundColor(Color.RED);
					title.setGravity(Gravity.CENTER);
					title.setTextColor(Color.WHITE);
					title.setTextSize(26);
					title.setTypeface(title.getTypeface(), Typeface.BOLD);
					title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
					title.setPadding(20, 0, 20, 0);
					title.setHeight(70);

					String otp_message = "\n On this Vehicle \n " + "Without Aadhaar No Ticket is in Pending, "
							+ "\n Kindly Update Aadhaar then, \n" + "Generate a New Ticket....! \n";

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
							AlertDialog.THEME_HOLO_LIGHT);
					alertDialogBuilder.setCustomTitle(title);
					alertDialogBuilder.setIcon(R.drawable.dialog_logo);
					alertDialogBuilder.setMessage(otp_message);
					alertDialogBuilder.setCancelable(false);
					alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(SpotChallan.this, SpotChallan.class);
							startActivity(intent);
						}
					});

					alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

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

					Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
					btn.setTextSize(22);
					btn.setTextColor(Color.WHITE);
					btn.setTypeface(btn.getTypeface(), Typeface.BOLD);
					btn.setBackgroundColor(Color.RED);

					Button btn2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
					btn2.setTextSize(22);
					btn2.setTextColor(Color.WHITE);
					btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
					btn2.setBackgroundColor(Color.RED);
				}
			} else {
				showToast("Error!");
			}
		}
	}

	// Async_getVioPoints
	public class Async_getVioPoints extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// public String getViolationsPoints(String unitCode,String
			// aadharNo,String drivingLicenseNo);
			Log.i("Async:", "**Called**");
			ServiceHelper.getViolations_Points("" + Dashboard.UNIT_CODE,
					"" + et_aadharnumber_spot.getText().toString().trim(),
					"" + et_driver_lcnce_num_spot.getText().toString().trim());
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			if (ServiceHelper.points_resp != null) {

				if (ServiceHelper.points_resp.length() > 0 && !ServiceHelper.points_resp.equals("NA")) {
					// new Async_getRTADetails().execute();

					try {
						String resp_json = ServiceHelper.points_resp;
						JSONObject jsonRootObject = new JSONObject(resp_json); // array
						// method
						JSONArray jsonArray = jsonRootObject.optJSONArray("POINTS_TABLE"); // array
						// object
						// {"POINTS_TABLE":[{"AADHAR_POINTS":311},{"DL_POINTS":209}]}

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);

							if (i == 0) {
								aadhr_point_frm_json = jsonObject.optString("AADHAR_POINTS").toString();
								aadhr_Points = "" + aadhr_point_frm_json.trim();
								aadhaar_offence_year = jsonObject.optString("OFFENCE_YEAR").toString();
								Log.i("i :::", "" + i);
								Log.i("aadhr_:", "" + aadhr_point_frm_json);
								Log.i("aa::", "" + aadhaar_offence_year);

							} else if (i == 1) {
								dl_point_frm_json = jsonObject.optString("DL_POINTS").toString();
								DL_Points = "" + dl_point_frm_json.trim();

								dl_offence_year = jsonObject.optString("OFFENCE_YEAR").toString();

								Log.i("i :::", "" + i);
								Log.i("dl_point_frm_json :::", "" + dl_point_frm_json);
								Log.i("dl_offence_year :::", "" + dl_offence_year);
							}
						}
						/*
						 * Log.i("aadhr_Points :::", ""+aadhr_Points);
						 * Log.i("DL_Points :::", ""+DL_Points);
						 */
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			} else {
				showToast("Error!");
			}
		}
	}

	/* TO GET PENDING CHALLANS BY VECHILE NUMBER */
	public class Async_getPendingChallans extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String pidCd = sharedPreference.getString("PID_CODE", "");
			String pidName = sharedPreference.getString("PID_NAME", "");
			String pswd = sharedPreference.getString("PASS_WORD", ""); // PASS_WORD

			ServiceHelper.getpendingChallansByRegNo("" + completeVehicle_num_send, "" + "", "" + "", "" + pidCd,
					"" + pidName, "" + pswd, "" + simid_send, "" + imei_send, "" + latitude, "" + longitude, "", "23");
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@SuppressWarnings("unused")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			Log.i("PENDING CHALLANS", "" + ServiceHelper.pending_challans_master.length);
			if (ServiceHelper.Opdata_Chalana != null) {

				if ((!ServiceHelper.Opdata_Chalana.equals("0")) && (ServiceHelper.pending_challans_master.length > 0)) {
					// startActivity(new Intent(getApplicationContext(),
					// PendingChallans.class));
					for (int i = 0; i < ServiceHelper.pending_challans_details.length; i++) {
						total_amount = total_amount
								+ (Double.parseDouble(ServiceHelper.pending_challans_details[i][7].toString().trim()));
					}
					tv_total_pending_challans.setText("" + ServiceHelper.pending_challans_details.length);
					tv_toal_amount_pending_challans.setText("" + total_amount);
					// grand_total : max violation amount
					Double total = 0.0;
					Log.i("Asynns", "" + total_amount);
					total = total_amount + grand_total;
					// tv_grand_total_spot.setText("Rs . " + total);vx
					SpotChallan.tv_grand_total_spot.setText("");
				} else if (ServiceHelper.Opdata_Chalana.equals("0")) {
					total_amount = 0;// violation amount
					showToast("No Pending Challans");
					tv_total_pending_challans.setText("0");
					tv_toal_amount_pending_challans.setText("0");
					SpotChallan.tv_grand_total_spot.setText("");
					// tv_grand_total_spot.setText("Rs . " + grand_total);
				}
			} else {
				total_amount = 0;
				showToast("Try Again!");
				SpotChallan.tv_grand_total_spot.setText("");
				// tv_grand_total_spot.setText("Rs . " + grand_total);
			}
		}
	}

	/* TO GET VIOLATIONS */
	public class Async_getOffenderRemarks extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {

			/*
			 * if
			 * (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")
			 * ) { ServiceHelper.onlinebuff =
			 * ServiceHelper.onlinebuff.append("2" + "!" + "NA" + "^"); }
			 */

			ServiceHelper.getOffenderRemarks(completeVehicle_num_send,
					"" + et_driver_lcnce_num_spot.getText().toString().trim(),
					"" + et_aadharnumber_spot.getText().toString().trim());
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			if (ServiceHelper.offender_remarks != null) {

				SpotChallan.offender_remarks_resp_master = new String[0];
				SpotChallan.offender_remarks_resp_master = ServiceHelper.offender_remarks.split("!");
				SpotChallan.helmet_remarks = SpotChallan.offender_remarks_resp_master[10].split("\\$");

				// RC:5
				// @DL:0
				// @ADR:0

				try {

					StringTokenizer helmet_token = new StringTokenizer(helmet_remarks[1].toString(), "@");

					String first_rc = helmet_token.nextToken();
					String second_dl = helmet_token.nextToken();
					String third_aadhar = helmet_token.nextToken();

					StringTokenizer helmet_rc = new StringTokenizer(first_rc, ":");
					String helemt_rc_name = helmet_rc.nextToken();
					helemt_rc_value = helmet_rc.nextToken();

					StringTokenizer helmet_dl = new StringTokenizer(second_dl, ":");
					String helemt_dl_name = helmet_dl.nextToken();
					helemt_dl_value = helmet_dl.nextToken();

					StringTokenizer helmet_adhar = new StringTokenizer(third_aadhar, ":");
					String helemt_adhar_name = helmet_adhar.nextToken();
					helemt_adhar_value = helmet_adhar.nextToken();

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					helemt_rc_value = "0";
					helemt_dl_value = "0";
					helemt_adhar_value = "0";
				}

				// Added on 26_May_2017 Manohar and Vinay
				if ((helemt_rc_value.contains("5") || Integer.parseInt(helemt_rc_value) >= 5)
						&& (helemt_dl_value.contains("3") || Integer.parseInt(helemt_dl_value) >= 3)
						&& (helemt_adhar_value.contains("3") || Integer.parseInt(helemt_adhar_value) >= 3)) {
					ShowMessage(
							"\nThis Vehicle Involved 5 or More Without Helmet cases under this Vehicle No, DL Number and Aadhaar No. "
									+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
				} else {

					if (helemt_rc_value.contains("5") || Integer.parseInt(helemt_rc_value) >= 5) {
						ShowMessage("\nThis Vehicle Involved 5 or More Without Helmet cases under this Vehicle No. "
								+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
					} else if (helemt_dl_value.contains("3") || Integer.parseInt(helemt_dl_value) >= 3) {
						ShowMessage(
								"\nThis Vehicle Involved 2 or More Without Helmet cases under this Driving License No. "
										+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
					} else if (helemt_adhar_value.contains("3") || Integer.parseInt(helemt_adhar_value) >= 3) {
						ShowMessage("\nThis Vehicle Involved 2 or More Without Helmet cases under this Aadhaar No. "
								+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
					}
				}
				Log.i("**getRTADetail", "" + SpotChallan.offender_remarks_resp_master.length);
				if (offender_remarks_resp_master[1].toString().trim().equals("NA")) {
					showToast("No Vehicle Remarks Found !");
				} else if ((!offender_remarks_resp_master[10].toString().trim().equals("NA"))) {
					showDialog(FAKE_NUMBERPLATE_DIALOG);
				}
			} else {
				showToast("Error!");
			}
		}
	}

	/* TO GET VIOLATIONS */
	public class Async_getViolations extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			Log.i("Async_ge::::::", whlr_code_send);
			ServiceHelper.getViolationDetails("" + whlr_code_send);
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			if (ServiceHelper.Opdata_Chalana != null) {
				if (ServiceHelper.Opdata_Chalana.toString().trim().equals("1")) {
					showToast("Invalid Login ID");
				} else if (ServiceHelper.Opdata_Chalana.toString().trim().equals("2")) {
					showToast("Invalid Password");
				} else if (ServiceHelper.Opdata_Chalana.toString().trim().equals("3")) {
					showToast("Unautherized Device");
				} else if (ServiceHelper.Opdata_Chalana.toString().trim().equals("4")) {
					showToast("Error, Please Contact E Challan Team at 040-27852721");
				} else if (ServiceHelper.violation_detailed_views.length > 0) {
					violation_list.removeAll(violation_list);
					violation_offence_Code.removeAll(violation_offence_Code);
					violation_section.removeAll(violation_section);
					violation_description.removeAll(violation_description);
					violation_min_amount.removeAll(violation_min_amount);
					violation_avg_amount.removeAll(violation_avg_amount);
					violation_max_amount.removeAll(violation_max_amount);
					violation_rg_ids.remove(violation_rg_ids);

					for (int i = 0; i < ServiceHelper.violation_detailed_views.length; i++) {

						violation_list.add("" + ServiceHelper.violation_detailed_views[i][2].toString().trim() + "("
								+ ServiceHelper.violation_detailed_views[i][1].toString().trim() + ") " + " Rs:"
								+ ServiceHelper.violation_detailed_views[i][4].toString().trim());
						violation_offence_Code.add("" + ServiceHelper.violation_detailed_views[i][0].toString().trim());
						violation_section.add("" + ServiceHelper.violation_detailed_views[i][1].toString().trim());
						violation_description.add("" + ServiceHelper.violation_detailed_views[i][2].toString().trim());
						violation_min_amount.add("" + ServiceHelper.violation_detailed_views[i][3].toString().trim());
						violation_max_amount.add("" + ServiceHelper.violation_detailed_views[i][4].toString().trim());
						violation_avg_amount.add("" + ServiceHelper.violation_detailed_views[i][5].toString().trim());
					}
				}
			} else {
				showToast("Error!");
			}
		}
	}

	/* TO GET DETAINED ITEMS LIST */

	public class Async_getDetainedItems extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			Log.i("vhle", "" + completeVehicle_num_send);
			Log.i("UNIT_CODE", "" + Dashboard.UNIT_CODE);
			ServiceHelper.getDetainedItemsList("" + completeVehicle_num_send, "" + Dashboard.UNIT_CODE);
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			Log.i("Async_getD", "" + ServiceHelper.detained_items_list_details.length);

			detained_items_status = new ArrayList<Boolean>();
			detained_items_status.clear();
			releasedDetained_items_list = new ArrayList<String>();
			releasedDetained_items_list.clear();

			sb_detained_items = new StringBuilder("");
			sb_detained_items.delete(0, sb_detained_items.length());
			boolean minorFlg = false;
			for (int i = 0; i < ServiceHelper.detained_items_list_details.length; i++) {
				/*
				 * releasedDetained_items_list.add("" +
				 * ServiceHelper.detained_items_list_details[i][0] + ":" +
				 * ServiceHelper.detained_items_list_details[i][1] + ":" +
				 * ServiceHelper.detained_items_list_details[i][2]);
				 */

				releasedDetained_items_list.add("" + ServiceHelper.detained_items_list_details[i][0] + ":"
						+ ServiceHelper.detained_items_list_details[i][1] + ":"
						+ ServiceHelper.detained_items_list_details[i][2] + ":"
						+ ServiceHelper.detained_items_list_details[i][3] + ":"
						+ ServiceHelper.detained_items_list_details[i][4] + ":"
						+ ServiceHelper.detained_items_list_details[i][5] + ":"
						+ ServiceHelper.detained_items_list_details[i][6] + ":"
						+ ServiceHelper.detained_items_list_details[i][7]);

				/*
				 * Log.i("ServiceHelper[0] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][0]);
				 * Log.i("ServiceHelper[1] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][1]);
				 * Log.i("ServiceHelper[2] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][2]);
				 * Log.i("ServiceHelper[3] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][3]);
				 * Log.i("ServiceHelper[4] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][4]);
				 * Log.i("ServiceHelper[5] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][5]);
				 * Log.i("ServiceHelper[6] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][6]);
				 * Log.i("ServiceHelper[7] :::", ""+
				 * ServiceHelper.detained_items_list_details[i][7]);
				 */

				// driver_mobileNo =
				// ServiceHelper.detained_items_list_details[i][5];
				minor_valueCode = ServiceHelper.detained_items_list_details[i][7];
				/*
				 * 07-26 15:04:22.653: I/**getDetainedItemsList**(24509): 02 ===
				 * detain item code 07-26 15:04:22.653:
				 * I/**getDetainedItemsList**(24509): VEHICLE === detained item
				 * 07-26 15:04:22.653: I/**getDetainedItemsList**(24509):
				 * HYD00TD161000074 == Ticket no 07-26 15:04:22.653:
				 * I/**getDetainedItemsList**(24509): null == Licence Number
				 * 07-26 15:04:22.653: I/**getDetainedItemsList**(24509): null
				 * == Aadhaar NUmber 07-26 15:04:22.653:
				 * I/**getDetainedItemsList**(24509): 9494345434 ==Contact NO
				 * 07-26 15:04:22.653: I/**getDetainedItemsList**(24509): KIRAN
				 * == Driver Name
				 */
				if ("1".equals(minor_valueCode)) {
					minorFlg = true;
				}
			}

			if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")
					&& (ServiceHelper.detained_items_list_details.length > 0)) {

				Log.i("Minor Flag be::", "" + minorFlg);
				if (minorFlg) {
					Log.i("Minor Fla:", "" + minorFlg);
					if (rb_indian.isChecked() == true && et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() == 12
							&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
						et_aadharnumber_spot
								.setError(Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
						et_aadharnumber_spot.requestFocus();
					} else if (rb_indian.isChecked() == true
							&& et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& ver.isValid(et_aadharnumber_spot.getText().toString())) {
						removeDialog(SECOND_SPOTSCREEN_DIALOG);
						showDialog(SECOND_SPOTSCREEN_DIALOG);
					} else {
						removeDialog(SECOND_SPOTSCREEN_DIALOG);
						showDialog(SECOND_SPOTSCREEN_DIALOG);
					}
				} else if (!minorFlg) {
					Log.i("Minor Fla:", "" + minorFlg);
					if (((rb_indian.isChecked() == true && et_aadharnumber_spot.getText().toString().trim().equals(""))
							&& et_driver_lcnce_num_spot.getText().toString().trim().equals(""))) {
						otp_msg = "Please enter driver Licence Number or Aadhar Number";
						removeDialog(OTP_CNFRMTN_DIALOG);
						showDialog(OTP_CNFRMTN_DIALOG);
					} else if (((rb_indian.isChecked() == true
							&& et_aadharnumber_spot.getText().toString().trim().length() > 1)
							&& et_aadharnumber_spot.getText().toString().length() != 12)) {
						et_aadharnumber_spot.setError(
								Html.fromHtml("<font color='black'>Enter Valid 12 digit Aadhaar Number</font>"));
						et_aadharnumber_spot.requestFocus();
					} else if (rb_indian.isChecked() == true && passport_layout.getVisibility() == View.GONE
							&& et_aadharnumber_spot.getText().toString().trim().length() > 1
							&& et_aadharnumber_spot.getText().toString().length() == 12
							&& !ver.isValid(et_aadharnumber_spot.getText().toString())) {
						et_aadharnumber_spot
								.setError(Html.fromHtml("<font color='black'>Enter Valid Aadhaar Number</font>"));
						et_aadharnumber_spot.requestFocus();
					} /*
						 * else if (rb_indian.isChecked() == true &&
						 * passport_layout.getVisibility()== View.GONE &&
						 * (et_driver_lcnce_num_spot
						 * .getText().toString().trim().equals(""))) {
						 * et_driver_lcnce_num_spot.setError(Html.fromHtml(
						 * "<font color='black'>Enter DL Number</font>"));
						 * et_driver_lcnce_num_spot.requestFocus(); }
						 */else if (rb_indian.isChecked() == true && passport_layout.getVisibility() == View.GONE
							&& !et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
						removeDialog(SECOND_SPOTSCREEN_DIALOG);
						showDialog(SECOND_SPOTSCREEN_DIALOG);
					} else if (rb_nri.isChecked() == true && passport_layout.getVisibility() == View.VISIBLE
							&& !et_passport.getText().toString().trim().equals("")) {
						removeDialog(SECOND_SPOTSCREEN_DIALOG);
						showDialog(SECOND_SPOTSCREEN_DIALOG);
					} else {
						removeDialog(SECOND_SPOTSCREEN_DIALOG);
						showDialog(SECOND_SPOTSCREEN_DIALOG);
					}
				}
				// End of Rlease Documents
			} else if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
				removeDialog(SECOND_SPOTSCREEN_DIALOG);
				showDialog(SECOND_SPOTSCREEN_DIALOG);
			} else if (Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {

				removeDialog(SECOND_SPOTSCREEN_DIALOG);
				showDialog(SECOND_SPOTSCREEN_DIALOG);

			} else {
				if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")
						|| (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))) {
					showToast("No Detained Items Found To Be Release");
				} else if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")
						|| (Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory"))) {
					showToast("No Detained Items Found To Be Release");

				}

				else if ((Dashboard.check_vhleHistory_or_Spot.equals("spot"))
						|| (Dashboard.check_vhleHistory_or_Spot.equals("towing"))) {
					removeDialog(SECOND_SPOTSCREEN_DIALOG);
					showDialog(SECOND_SPOTSCREEN_DIALOG);
				} /*
					 * else if ((Dashboard.check_vhleHistory_or_Spot.equals(
					 * "specialdrive" ))) {
					 * removeDialog(SECOND_SPOTSCREEN_DIALOG);
					 * showDialog(SECOND_SPOTSCREEN_DIALOG); }
					 */
			}
		}
	}
	// 16_MAR_2017

	public class Async_getAadharDetails extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("**Async_ge", "Entered");

			if (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
				ServiceHelper.onlinebuff = ServiceHelper.onlinebuff.append("3" + "!" + "NA" + "^");
			}

			ServiceHelper.getAadharDetails("" + et_aadharnumber_spot.getText().toString().trim(), "");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			if (ServiceHelper.aadhar_details.length > 0) {
				ll_aadhar_layout.setVisibility(View.VISIBLE);
				tv_aadhar_header.setVisibility(View.VISIBLE);

				/*
				 * Log.i("ServiceHelper[0] :::", ""+
				 * ServiceHelper.aadhar_details[0]);
				 * Log.i("ServiceHelper[1] :::", ""+
				 * ServiceHelper.aadhar_details[1]);
				 * Log.i("ServiceHelper[2] :::", ""+
				 * ServiceHelper.aadhar_details[2]);
				 * Log.i("ServiceHelper[3] :::", ""+
				 * ServiceHelper.aadhar_details[3]);
				 * Log.i("ServiceHelper[4] :::", ""+
				 * ServiceHelper.aadhar_details[4]);
				 * Log.i("ServiceHelper[5] :::", ""+
				 * ServiceHelper.aadhar_details[5]);
				 * Log.i("ServiceHelper[6] :::", ""+
				 * ServiceHelper.aadhar_details[6]);
				 * Log.i("ServiceHelper[7] :::", ""+
				 * ServiceHelper.aadhar_details[7]);
				 * Log.i("ServiceHelper[8] :::", ""+
				 * ServiceHelper.aadhar_details[8]);
				 * Log.i("ServiceHelper[9] :::", ""+
				 * ServiceHelper.aadhar_details[9]);
				 * Log.i("ServiceHelper[10] :::", ""+
				 * ServiceHelper.aadhar_details[10]);
				 * Log.i("ServiceHelper[11] :::", ""+
				 * ServiceHelper.aadhar_details[11]);
				 * Log.i("ServiceHelper[12] :::", ""+
				 * ServiceHelper.aadhar_details[12]);
				 */
				String date_birth = "";
				tv_aadhar_header.setText("AADHAR DETAILS");
				if (!"NA".equals(ServiceHelper.aadhar_data)) {
					tv_aadhar_user_name.setText("" + ServiceHelper.aadhar_details[0] != null
							? ServiceHelper.aadhar_details[0].trim().toUpperCase() : "");
					tv_aadhar_care_off.setText("" + (!ServiceHelper.aadhar_details[1].equalsIgnoreCase("0")
							? ServiceHelper.aadhar_details[1].trim().toUpperCase() : "NA"));
					tv_aadhar_address.setText(""
							+ (!ServiceHelper.aadhar_details[2].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[2].trim().toUpperCase() : "")
							+ ", "
							+ (!ServiceHelper.aadhar_details[3].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[3].trim().toUpperCase() + ", " : "")
							+ (!ServiceHelper.aadhar_details[4].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[4].trim().toUpperCase() + ", " : "")
							+ (!ServiceHelper.aadhar_details[5].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[5].trim().toUpperCase() + ", " : "")
							+ (!ServiceHelper.aadhar_details[6].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[6].trim().toUpperCase() + ", " : "")
							+ (!ServiceHelper.aadhar_details[7].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[7].trim().toUpperCase() + ", " : ""));
					tv_aadhar_mobile_number.setText("" + (!ServiceHelper.aadhar_details[8].equalsIgnoreCase("0")
							? ServiceHelper.aadhar_details[8].trim().toUpperCase() : "NA"));
					tv_aadhar_gender.setText("" + (!ServiceHelper.aadhar_details[9].equalsIgnoreCase("0")
							? ServiceHelper.aadhar_details[9].trim().toUpperCase() : "NA"));
					date_birth = "" + ServiceHelper.aadhar_details[10];
				}
				String[] split_dob = date_birth.split("\\/");
				String service_year = "" + split_dob[2];

				int final_age = present_year - Integer.parseInt(service_year);
				Log.i("final_age ::::::::", "" + final_age);
				tv_aadhar_dob.setText("" + final_age);
				tv_aadhar_uid.setText("" + (!ServiceHelper.aadhar_details[11].equalsIgnoreCase("0")
						? ServiceHelper.aadhar_details[11].trim().toUpperCase() : "NA"));
				// tv_aadhar_eid.setText(""+
				// (!ServiceHelper.aadhar_details[12].equalsIgnoreCase("0") ?
				// ServiceHelper.aadhar_details[12].trim().toUpperCase():
				// "NA"));

				if (ServiceHelper.aadhar_details.length > 0
						&& et_driver_lcnce_num_spot.getText().toString().equals("")) {

					et_drivername_iOD.setText("" + ServiceHelper.aadhar_details[0] != null
							? ServiceHelper.aadhar_details[0].trim().toUpperCase() : "");
					et_driverFatherName_iOD.setText("" + (!ServiceHelper.aadhar_details[1].equalsIgnoreCase("0")
							? ServiceHelper.aadhar_details[1].trim().toUpperCase() : "NA"));
					et_driver_address_iOD.setText(""
							+ (!ServiceHelper.aadhar_details[2].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[2].trim().toUpperCase() : "")
							+ ","
							+ (!ServiceHelper.aadhar_details[3].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[3].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[4].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[4].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[5].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[5].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[6].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[6].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[7].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[7].trim().toUpperCase() + "" : ""));
					Compleate_address = "" + ""
							+ (!ServiceHelper.aadhar_details[2].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[2].trim().toUpperCase() : "")
							+ ","
							+ (!ServiceHelper.aadhar_details[3].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[3].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[4].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[4].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[5].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[5].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[6].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[6].trim().toUpperCase() + "," : "")
							+ (!ServiceHelper.aadhar_details[7].equalsIgnoreCase("0")
									? ServiceHelper.aadhar_details[7].trim().toUpperCase() + "" : "");
					et_driver_city_iOD.setText((!ServiceHelper.aadhar_details[6].equalsIgnoreCase("0")
							? ServiceHelper.aadhar_details[6].trim().toUpperCase() + "" : ""));

					if (et_drivername_iOD.getText().toString().trim().length() > 0) {
						et_drivername_iOD.setEnabled(false);
						et_drivername_iOD.setClickable(false);
					} else if (et_drivername_iOD.getText().toString().trim().length() == 0) {
						et_drivername_iOD.setEnabled(true);
						et_drivername_iOD.setClickable(true);
					}

					if (et_driverFatherName_iOD.getText().toString().trim().length() > 0) {
						et_driverFatherName_iOD.setEnabled(false);
						et_driverFatherName_iOD.setClickable(false);
					} else if (et_driverFatherName_iOD.getText().toString().trim().length() == 0) {
						et_driverFatherName_iOD.setEnabled(true);
						et_driverFatherName_iOD.setClickable(true);
					}

					if (et_driver_address_iOD.getText().toString().trim().length() > 0) {
						et_driver_address_iOD.setEnabled(false);
						et_driver_address_iOD.setClickable(false);
					} else if (et_driver_address_iOD.getText().toString().trim().length() == 0) {
						et_driver_address_iOD.setEnabled(true);
						et_driver_address_iOD.setClickable(true);
					}

					if (et_driver_city_iOD.getText().toString().trim().length() > 0) {
						et_driver_city_iOD.setEnabled(false);
						et_driver_city_iOD.setClickable(false);
					} else if (et_driver_city_iOD.getText().toString().trim().length() == 0) {
						et_driver_city_iOD.setEnabled(true);
						et_driver_city_iOD.setClickable(true);
					}
				}
				if (ServiceHelper.aadhar_details[13].toString().trim().equals("0")) {
					img_aadhar_image.setImageResource(R.drawable.photo);
				} else {
					byte[] decodestring = Base64.decode("" + ServiceHelper.aadhar_details[13].toString().trim(),
							Base64.DEFAULT);
					Bitmap decocebyte = BitmapFactory.decodeByteArray(decodestring, 0, decodestring.length);
					img_aadhar_image.setImageBitmap(decocebyte);
				}

				String Qrdata = "AADHAAAR NUMBER:" + " " + et_aadharnumber_spot.getText().toString().trim() + "\n"
						+ "NAME:" + " " + ServiceHelper.aadhar_details[0] + "\n" + "FATHER NAME:" + " "
						+ ServiceHelper.aadhar_details[1] + "\n" + "AGE:" + " " + final_age + "\n" + "GENDER:" + " "
						+ ServiceHelper.aadhar_details[9] + "\n" + "ADDRESS:" + " " + "" + Compleate_address;
				try {
					Bitmap bitmap = encodeAsBitmap(Qrdata);
					qr_code.setImageBitmap(bitmap);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else if (ServiceHelper.aadhar_details.length == 0) {

				tv_aadhar_header.setVisibility(View.VISIBLE);
				tv_aadhar_header.setText("AADHAR DETAILS NOT FOUND!");
				ll_aadhar_layout.setVisibility(View.GONE);
				et_drivername_iOD.setText("");
				et_driverFatherName_iOD.setText("");
				et_driver_address_iOD.setText("");
				et_driver_city_iOD.setText("");

				et_drivername_iOD.setEnabled(true);
				et_drivername_iOD.setClickable(true);

				et_driverFatherName_iOD.setEnabled(true);
				et_driverFatherName_iOD.setClickable(true);

				et_driver_address_iOD.setEnabled(true);
				et_driver_address_iOD.setClickable(true);

				et_driver_city_iOD.setEnabled(true);
				et_driver_city_iOD.setClickable(true);

			} else if (ServiceHelper.Opdata_Chalana.equals(0)) {

				tv_aadhar_header.setVisibility(View.VISIBLE);
				tv_aadhar_header.setText("AADHAR DETAILS NOT FOUND!");
				ll_aadhar_layout.setVisibility(View.GONE);
				et_drivername_iOD.setText("");
				et_driverFatherName_iOD.setText("");
				et_driver_address_iOD.setText("");
				et_driver_city_iOD.setText("");

				et_drivername_iOD.setEnabled(true);
				et_drivername_iOD.setClickable(true);

				et_driverFatherName_iOD.setEnabled(true);
				et_driverFatherName_iOD.setClickable(true);

				et_driver_address_iOD.setEnabled(true);
				et_driver_address_iOD.setClickable(true);

				et_driver_city_iOD.setEnabled(true);
				et_driver_city_iOD.setClickable(true);
			}
		}
	}

	@SuppressWarnings("unused")
	@SuppressLint({ "ResourceAsColor", "CutPasteId" })
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
			ad_whle_code_name.setSingleChoiceItems((wheeler_name_arr_spot), selected_wheller_code,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							selected_wheller_code = which;
							Log.i("selected::", "" + selected_wheller_code);
							btn_wheller_code.setText("" + wheeler_name_arr_spot[which]);
							Log.i("wheeler_nam::::", "" + wheeler_name_arr_spot[which]);

							removeDialog(WHEELER_CODE);

							whlr_code_send = wheeler_code_arr_spot[which];
							Log.i("****whlr_code_send***", "" + whlr_code_send);

							btn_violation.setText("" + getResources().getString(R.string.select_violation));

							if (isOnline()) {
								// total_amount = 0;
								new Async_getViolations().execute();
							} else {
								showToast("" + NETWORK_TXT);
							}
						}
					});
			Dialog dg_whle_code_name = ad_whle_code_name.create();
			return dg_whle_code_name;

		case VIOLATIONS_DIALOG:
			TextView title5 = new TextView(this);
			title5.setText("Select Violations");
			title5.setBackgroundColor(Color.parseColor("#007300"));
			title5.setGravity(Gravity.CENTER);
			title5.setTextColor(Color.WHITE);
			title5.setTextSize(26);
			title5.setTypeface(title5.getTypeface(), Typeface.BOLD);
			title5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
			title5.setPadding(20, 0, 20, 0);
			title5.setHeight(70);

			TextView title6 = new TextView(this);
			title6.setTextSize(22);
			title6.setText("Ok");
			title6.setTextColor(Color.WHITE);
			title6.setTypeface(title6.getTypeface(), Typeface.BOLD);
			title6.setBackgroundColor(Color.RED);

			AlertDialog.Builder ad_violations = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			ad_violations.setCustomTitle(title5);// "Select Violations"
			ad_violations.setMultiChoiceItems(violation_list.toArray(new String[violation_list.size()]), null,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								Log.i("CHEKCED : ", "" + violation_offence_Code.get(which));
								violation_positions.add("" + which);
								// violation_checked_items_status.add(true);
							} else if (!isChecked) {
								Log.i("REMOVED : ", "" + violation_offence_Code.get(which));
								violation_positions.remove("" + which);
								// violation_checked_items_status.remove(w);
							}
						}
					});

			ad_violations.setPositiveButton("" + title6, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String desc = "";
					for (int i = 0; i < violation_positions.size(); i++) {
						// showToast(violation_offence_Code.get(Integer
						// .parseInt(violation_positions.get(i))));

						if (i == 0) {
							desc = violation_description.get(Integer.parseInt(violation_positions.get(i))).toString()
									.trim();
						} else {
							desc = desc + "," + violation_description.get(Integer.parseInt(violation_positions.get(i)))
									.toString().trim();
						}
						grand_total = grand_total + Integer
								.parseInt(violation_max_amount.get(Integer.parseInt(violation_positions.get(i))));
					}
					if (!desc.equals("")) {
						btn_violation.setText("" + desc);
					} else {
						btn_violation.setText("" + getResources().getString(R.string.select_violation));
					}
					total = 0.0;
					total = total_amount + grand_total;
					tv_grand_total_spot.setText("Rs . " + total);
					removeDialog(VIOLATIONS_DIALOG);

				}
			});
			Dialog dg_violation = ad_violations.create();
			return dg_violation;

		case SECOND_SPOTSCREEN_DIALOG:
			if (police_vehcle.isChecked()) {
				is_govt_police = "2";
				Log.i("is_govt_poli::", "" + is_govt_police);
			} else if (govt_vehcle.isChecked()) {
				is_govt_police = "4";
				Log.i("is_govt_::", "" + is_govt_police);
			}
			otp_status = "send";

			Dialog dg_scond = new Dialog(this, android.R.style.Theme_Black_NoTitleBar);
			dg_scond.setContentView(R.layout.spot_challan_two);

			/* IS IT SPOT DETAILS */
			ll_spot_payment_root = (LinearLayout) dg_scond.findViewById(R.id.ll_is_spotpayment_spotchallan_two_xml);
			ll_spot_payment_root.setVisibility(View.VISIBLE);

			radiogrp_spot_payment = (RadioGroup) dg_scond.findViewById(R.id.radioGroup_spot_payment);
			radioGroupButton_spotpaymentYes = (RadioButton) dg_scond.findViewById(R.id.radioGroupButton_spotpayment0); // Yes
			radioGroupButton_spotpaymentNo = (RadioButton) dg_scond.findViewById(R.id.radioGroupButton_spotpayment1); // No

			/* RADIO BUTTON FOR CASH OR CARD */
			ll_cash_or_card = (LinearLayout) dg_scond.findViewById(R.id.ll_cashorcard_spotchallan_two_xml);
			rl_card_details = (RelativeLayout) dg_scond.findViewById(R.id.rl_card_details);// visa,master,maestro

			radiogrp_cash_or_card = (RadioGroup) dg_scond.findViewById(R.id.radioGroup_cash_card);
			radioGroupButton_cashcard1 = (RadioButton) dg_scond.findViewById(R.id.radioGroupButton_cashcard1);// Card
			radioGroupButton_cashcard0 = (RadioButton) dg_scond.findViewById(R.id.radioGroupButton_cashcard0);// Cash

			/* RELEASED DETAINED ITEMS RADIOBUTTONS WITH DYNAMIC LATOYT */
			ll_detained_items_root = (LinearLayout) dg_scond
					.findViewById(R.id.ll_detaineditems_root_spotchallan_two_xml);
			/* RADIO BUTTON FOR DETAINED ITEMS */
			radiogrp_release_detained_items = (RadioGroup) dg_scond.findViewById(R.id.radioGroup_detaineditems);
			/* DEFAULTY ENABLING RELEASED DETAINED ITEMS TO YES */
			radioGroupButton_detaineditems_yes = (RadioButton) dg_scond
					.findViewById(R.id.radioGroupButton_detaineditems_yes);
			radioGroupButton_detaineditems_no = (RadioButton) dg_scond
					.findViewById(R.id.radioGroupButton_detaineditems_no);

			/* DISPLAYS DETAINED ITEMS */
			ll_detained_itemlist_layout = (LinearLayout) dg_scond
					.findViewById(R.id.ll_detaineditems_spotchallan_two_xml);// DYNAMIC
			rl_detained_items = (RelativeLayout) dg_scond.findViewById(R.id.rl_detaineditems_spotchallantwo_xml);// rc,vchle.lcnce,prmt,none
			/* DETAINED ITEMS START */
			chck_detainedItems_rc = (CheckBox) dg_scond.findViewById(R.id.checkBox_dt_rc_spotchallantwo_xml);
			chck_detainedItems_vhcle = (CheckBox) dg_scond.findViewById(R.id.checkBox_dt_vchle_spotchallantwo_xml);
			chck_detainedItems_licence = (CheckBox) dg_scond.findViewById(R.id.checkBox_dt_lcns_spotchallantwo_xml);
			chck_detainedItems_permit = (CheckBox) dg_scond.findViewById(R.id.checkBox_dt_permit_spotchallantwo_xml);
			chck_detainedItems_none = (CheckBox) dg_scond.findViewById(R.id.checkBox_dt_none_spotchallantwo_xml);

			/* HEADER TEXT */
			textView_header_spot_challan_xml = (TextView) dg_scond
					.findViewById(R.id.checkBox_dt_none_spotchallantwo_xml);
			tv_spotChallanTwo_header = (TextView) dg_scond.findViewById(R.id.textView_header_spot_challan_xml);

			/* MOBILE OTP */
			et_driver_contact_spot = (EditText) dg_scond.findViewById(R.id.edt_drvr_cnctno_spotchallantwo_xml);
			btn_send_otp_to_mobile = (Button) dg_scond.findViewById(R.id.btn_sendOTPtoMobile_spotchallantwo_xml);
			btn_send_otp_to_mobile.setVisibility(View.GONE);

			/*
			 * if ("Y".equals(otp_no_flg.trim().toUpperCase())) {
			 * btn_send_otp_to_mobile.setVisibility(View.VISIBLE); } else if
			 * ("N".equals(otp_no_flg.trim().toUpperCase())) {
			 * btn_send_otp_to_mobile.setVisibility(View.GONE); }
			 */

			/* ID PROOF */
			btn_id_proof_spot = (Button) dg_scond.findViewById(R.id.btn_select_idproff_spotchallantwo_xml);
			et_id_proof_spot = (EditText) dg_scond.findViewById(R.id.edt_idproff_spotchallantwo_xml);
			et_remarks_spot = (EditText) dg_scond.findViewById(R.id.edt_remarks_spotchallantwo_xml);

			btn_move_to_first = (Button) dg_scond.findViewById(R.id.btn_cancel_spotchallantwo_xml);
			btn_final_submit = (Button) dg_scond.findViewById(R.id.btn_finalsubmit_spotchallantwo_xml);

			/* PROFESSION LAYOUT */
			proffession_layout = (LinearLayout) dg_scond.findViewById(R.id.proffession_layout);

			radiogrp_spot_payment.setClickable(true);
			radiogrp_cash_or_card.setClickable(true);

			radioGroupButton_spotpaymentNo.setClickable(false);
			radioGroupButton_spotpaymentYes.setClickable(true);

			btn_id_proof_spot.setOnClickListener(this);
			chck_detainedItems_rc.setOnCheckedChangeListener(this);
			chck_detainedItems_vhcle.setOnCheckedChangeListener(this);
			chck_detainedItems_licence.setOnCheckedChangeListener(this);
			chck_detainedItems_permit.setOnCheckedChangeListener(this);
			chck_detainedItems_none.setOnCheckedChangeListener(this);

			ll_cash_or_card.setVisibility(View.GONE);
			rl_card_details.setVisibility(View.GONE);
			ll_detained_items_root.setVisibility(View.GONE);

			cb = new CheckBox[0];
			if (cb.length > 0) {
				for (int i = 0; i < cb.length; i++) {
					cb[i].setChecked(false);
					// cb[i].setEnabled(false);
				}
			}

			if (spotPamentFLG) {
				radioGroupButton_spotpaymentYes.setClickable(false);
			} else {
				radioGroupButton_spotpaymentNo.setClickable(true);
			}

			/*** KIRAN CODING START ***/
			if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				Log.i("towing  :::::", "****towing Condition Entered*****");

				rl_detained_items.setVisibility(View.VISIBLE);
				tv_spotChallanTwo_header.setText("" + getResources().getString(R.string.towing_one_line));

				Log.i("spot  :::::", "****spot Condition Entered*****");
				String vehicle_split = et_regcid_spot.getText().toString().trim().substring(0, 2);
				// ***3-WHEELER CONDITION****//*
				String three_wheeler = btn_wheller_code.getText().toString().trim();

				proffession_layout.setVisibility(View.GONE);

				if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
					radioGroupButton_spotpaymentYes.setEnabled(false);

					if (et_aadharnumber_spot.getText().toString().trim().equals("")
							&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
						radioGroupButton_spotpaymentNo.setEnabled(false);
						radioGroupButton_spotpaymentNo.setChecked(true);
					} else if (!et_last_num_spot.getText().toString().trim().equals("")) {
						radioGroupButton_spotpaymentYes.setEnabled(false);
						radioGroupButton_spotpaymentYes.setClickable(false);
						radioGroupButton_spotpaymentNo.setEnabled(false);
						radioGroupButton_spotpaymentNo.setChecked(true);
					} else {
						radioGroupButton_spotpaymentYes.setClickable(true);
						radioGroupButton_spotpaymentNo.setEnabled(false);
						radioGroupButton_spotpaymentNo.setChecked(true);
					}

					Log.i("Log 1  :::::", "****Entered*****");

					if (radioGroupButton_detaineditems_no.isChecked()) {
						Log.i("Log 2  :::::", "****Entered*****");
						rl_detained_items.setVisibility(View.VISIBLE);
						ll_detained_items_root.setVisibility(View.GONE);
						ll_detained_itemlist_layout.setVisibility(View.GONE);
						ll_cash_or_card.setVisibility(View.GONE);
					} else {
						Log.i("Log 3  :::::", "****Entered*****");
						rl_detained_items.setVisibility(View.VISIBLE);
						ll_detained_items_root.setVisibility(View.VISIBLE);
						ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
						ll_cash_or_card.setVisibility(View.VISIBLE);
					}

				} else if (!vehicle_split.equals("AP") || !vehicle_split.equals("TS")) {
					Log.i("Log 4  :::::", "****Entered*****");
					radioGroupButton_spotpaymentYes.setChecked(true);
					radioGroupButton_spotpaymentNo.setEnabled(true);

					rl_detained_items.setVisibility(View.GONE);
					ll_detained_items_root.setVisibility(View.VISIBLE);
					ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
					ll_cash_or_card.setVisibility(View.VISIBLE);

					for (int i = 0; i < cb.length; i++) {
						cb[i].setChecked(false);
						cb[i].setEnabled(false);
					}
					sb_detained_items.append("");
					setCheckedValues(false, "donotedit");
					chck_detainedItems_none.setChecked(true);
				}

				/*
				 * if (et_driver_lcnce_num_spot.getText().toString().equals("")
				 * &&( three_wheeler.contains("3") ||
				 * three_wheeler.contains("3-WHEELER"))) {
				 * Log.i("is_it_spot_send Value ::::::",is_it_spot_send);
				 * sb_detained_items.delete(0, sb_detained_items.length());
				 * //sb_detained_items.append("02:VEHICLE@");
				 * sb_detained_items.append("donotedit");//donotedit
				 * chck_detainedItems_vhcle.setChecked(false);
				 *
				 * chck_detainedItems_licence.setClickable(false);
				 * chck_detainedItems_none.setClickable(true);
				 * chck_detainedItems_permit.setClickable(false);
				 * chck_detainedItems_rc.setClickable(false);
				 * chck_detainedItems_vhcle.setClickable(false); }else
				 */
				if (et_driver_lcnce_num_spot.getText().toString().equals("")) {
					sb_detained_items.delete(0, sb_detained_items.length());
					if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
						sb_detained_items.append("02:VEHICLE@");
						chck_detainedItems_vhcle.setChecked(true);
					} else {
						sb_detained_items.append("donotedit");
						chck_detainedItems_none.setChecked(true);
					}

				}

				else if (et_aadharnumber_spot.getText().toString().equals("")) {
					sb_detained_items.delete(0, sb_detained_items.length());
					if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
						sb_detained_items.append("02:VEHICLE@");
						chck_detainedItems_vhcle.setChecked(true);
					} else {
						sb_detained_items.append("donotedit");
						chck_detainedItems_none.setChecked(true);
					}

					chck_detainedItems_none.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (chck_detainedItems_none.isChecked()) {
								TextView title = new TextView(SpotChallan.this);
								title.setText("ALERT");
								title.setBackgroundColor(Color.RED);
								title.setGravity(Gravity.CENTER);
								title.setTextColor(Color.WHITE);
								title.setTextSize(26);
								title.setTypeface(title.getTypeface(), Typeface.BOLD);
								title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
										R.drawable.dialog_logo, 0);
								title.setPadding(20, 0, 20, 0);
								title.setHeight(70);

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
										AlertDialog.THEME_HOLO_LIGHT);
								alertDialogBuilder.setCustomTitle(title);
								alertDialogBuilder.setIcon(R.drawable.dialog_logo);
								alertDialogBuilder.setMessage("\nKindly detain any item !!!\n");
								alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										// et_aadharnumber_spot.setText("");
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
							} else {
								showToast("Please detain an item !");
							}
						}
					});

				} else {
					// --------DEFAULT NONE : 05-02-2016-------
					sb_detained_items.append("");
					setCheckedValues(false, "donotedit");
					chck_detainedItems_none.setChecked(true);
					// ------------------------------------------

				}

			} else if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
				Log.i("spot  :::::", "****spot Condition Entered*****");

				Log.i("spot  :::::", "****spot Condition Entered*****");
				Log.i("length", "" + ServiceHelper.pending_challans_details.length);

				if (violation_checked_violations.contains("64") || violation_checked_violations.contains("6")
						|| ServiceHelper.pending_challans_details.length >= 10) {

					btn_send_otp_to_mobile.setVisibility(View.VISIBLE);

					btn_otp_flag = "1";

				} else {
					btn_send_otp_to_mobile.setVisibility(View.GONE);
					Log.i("GONE", "==" + violation_checked_violations);
				}

				String vehicle_split = et_regcid_spot.getText().toString().trim().substring(0, 2);
				// ***3-WHEELER CONDITION****//*
				String three_wheeler = btn_wheller_code.getText().toString().trim();

				proffession_layout.setVisibility(View.GONE);

				if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
					radioGroupButton_spotpaymentYes.setEnabled(false);
					radioGroupButton_spotpaymentYes.setClickable(false);

					if (et_aadharnumber_spot.getText().toString().trim().equals("")
							&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {
						radioGroupButton_spotpaymentNo.setEnabled(false);
						radioGroupButton_spotpaymentNo.setChecked(true);
					} else {
						Log.i("FLG Log 1  :::::", "****Entered*****");
						radioGroupButton_spotpaymentYes.setEnabled(true);
						radioGroupButton_spotpaymentNo.setChecked(true);
						radioGroupButton_spotpaymentYes.setClickable(true);
					}
					Log.i("Log 1  :::::", "****Entered*****");

					if (radioGroupButton_detaineditems_no.isChecked()) {
						Log.i("Log 2  :::::", "****Entered*****");

						rl_detained_items.setVisibility(View.VISIBLE);
						ll_detained_items_root.setVisibility(View.GONE);
						ll_detained_itemlist_layout.setVisibility(View.GONE);
						ll_cash_or_card.setVisibility(View.GONE);

					} else {
						Log.i("Log 3  :::::", "****Entered*****");
						rl_detained_items.setVisibility(View.VISIBLE);
						ll_detained_items_root.setVisibility(View.VISIBLE);
						ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
						ll_cash_or_card.setVisibility(View.VISIBLE);
					}

				} else if (!vehicle_split.equals("AP") || !vehicle_split.equals("TS")) {
					Log.i("Log 4  :::::", "****Entered*****");
					radioGroupButton_spotpaymentYes.setChecked(true);
					radioGroupButton_spotpaymentNo.setEnabled(true);

					rl_detained_items.setVisibility(View.GONE);
					ll_detained_items_root.setVisibility(View.VISIBLE);
					ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
					ll_cash_or_card.setVisibility(View.VISIBLE);

					for (int i = 0; i < cb.length; i++) {
						cb[i].setChecked(false);
						cb[i].setEnabled(false);
					}
					sb_detained_items.append("");
					setCheckedValues(false, "donotedit");
					chck_detainedItems_none.setChecked(true);
				}

				/*
				 * if (et_driver_lcnce_num_spot.getText().toString().equals("")
				 * &&( three_wheeler.contains("3") ||
				 * three_wheeler.contains("3-WHEELER"))) {
				 * Log.i("is_it_spot_send Value ::::::",is_it_spot_send);
				 * sb_detained_items.delete(0, sb_detained_items.length());
				 * //sb_detained_items.append("02:VEHICLE@");
				 * sb_detained_items.append("donotedit");//donotedit
				 * chck_detainedItems_vhcle.setChecked(false);
				 *
				 * chck_detainedItems_licence.setClickable(false);
				 * chck_detainedItems_none.setClickable(true);
				 * chck_detainedItems_permit.setClickable(false);
				 * chck_detainedItems_rc.setClickable(false);
				 * chck_detainedItems_vhcle.setClickable(false); }else
				 */

				if (et_driver_lcnce_num_spot.getText().toString().equals("")) {

					sb_detained_items.delete(0, sb_detained_items.length());

					if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
						sb_detained_items.append("02:VEHICLE@");
						chck_detainedItems_vhcle.setChecked(true);

					} else {
						sb_detained_items.append("donotedit");
						chck_detainedItems_none.setChecked(true);
					}

					chck_detainedItems_none.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							showToast("Please Detain an Item");
						}
					});

				} else if (violation_checked_violations.contains("64") || violation_checked_violations.contains("123")
						|| violation_checked_violations.contains("6")
						|| ServiceHelper.pending_challans_details.length >= 10) {
					// --------DEFAULT NONE : 05-02-2016-------
					sb_detained_items.append("02:VEHICLE@");
					chck_detainedItems_vhcle.setChecked(true);

				}

				// Added on 26_May_2017 Manohar and Vinay
				else if (violation_checked_violations.contains("01")) {

					if ((helemt_rc_value.contains("4") || Integer.parseInt(helemt_rc_value) >= 4)
							&& (helemt_dl_value.contains("2") || Integer.parseInt(helemt_dl_value) >= 2)
							&& (helemt_adhar_value.contains("2") || Integer.parseInt(helemt_adhar_value) >= 2)) {
						ShowMessage(
								"\nThis Vehicle Involved 5 or More Without Helmet cases under this Vehicle No, DL Number and Aadhaar No. "
										+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
						sb_detained_items.append("02:VEHICLE@");
						chck_detainedItems_vhcle.setChecked(true);

					} else {

						if (helemt_rc_value.contains("4") || Integer.parseInt(helemt_rc_value) >= 4) {
							ShowMessage("\nThis Vehicle Involved 5 or More Without Helmet cases under this Vehicle No. "
									+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
							sb_detained_items.append("02:VEHICLE@");
							chck_detainedItems_vhcle.setChecked(true);
						} else if (helemt_dl_value.contains("2") || Integer.parseInt(helemt_dl_value) >= 2) {
							ShowMessage(
									"\nThis Vehicle Involved 2 or More Without Helmet cases under this Driving License No. "
											+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
							sb_detained_items.append("02:VEHICLE@");
							chck_detainedItems_vhcle.setChecked(true);
						}

						else if (helemt_adhar_value.contains("2") || Integer.parseInt(helemt_adhar_value) >= 2) {
							ShowMessage("\nThis Vehicle Involved 2 or More Without Helmet cases under this Aadhaar No. "
									+ "Please Detain the Vehicle and Ask them to Attend Counselling at Traffic Training Institute(TTI)\n");
							sb_detained_items.append("02:VEHICLE@");
							chck_detainedItems_vhcle.setChecked(true);
						}
					}
				} else {
					sb_detained_items.append("");
					setCheckedValues(false, "donotedit");
					chck_detainedItems_none.setChecked(true);

				}
				// ------------------------------------------

			} else if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
				Log.i("vehiclehistory  :::::", "****vehiclehistory Condition Entered*****");

				rl_detained_items.setVisibility(View.VISIBLE);

				btn_send_otp_to_mobile.setVisibility(View.VISIBLE);

				btn_otp_flag = "1";

				tv_spotChallanTwo_header.setText("" + getResources().getString(R.string.vehicle_history));

				ll_cash_or_card.setVisibility(View.VISIBLE);
				ll_detained_items_root.setVisibility(View.VISIBLE);

				proffession_layout.setVisibility(View.GONE);

				is_it_spot_send = "1";

				String vehicle_split = et_regcid_spot.getText().toString().trim().substring(0, 2);
				if (radioGroupButton_spotpaymentNo.isChecked()) {
					Log.i("vehicle_split :::", "" + vehicle_split);
					if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
						Log.i("vehicle_split 2 :::", "" + vehicle_split);
						radioGroupButton_spotpaymentYes.setEnabled(true);
						radioGroupButton_spotpaymentNo.setChecked(true);
						// radioGroupButton_spotpaymentYes.setChecked(true);
						if (radioGroupButton_detaineditems_no.isChecked()) {
							Log.i("Log 2  :::::", "****Entered*****");

							sb_detained_items.append("");
							setCheckedValues(false, "donotedit");
							chck_detainedItems_none.setChecked(true);

							rl_detained_items.setVisibility(View.VISIBLE);
							ll_detained_items_root.setVisibility(View.GONE);
							ll_detained_itemlist_layout.setVisibility(View.GONE);
							ll_cash_or_card.setVisibility(View.GONE);
						} else {
							Log.i("Log 3  :::::", "****Entered*****");
							rl_detained_items.setVisibility(View.VISIBLE);
							ll_detained_items_root.setVisibility(View.VISIBLE);
							ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
							ll_cash_or_card.setVisibility(View.VISIBLE);
						}

					} else {
						radioGroupButton_spotpaymentYes.setEnabled(true);
						radioGroupButton_spotpaymentYes.setChecked(true);

						rl_detained_items.setVisibility(View.VISIBLE);
						ll_detained_items_root.setVisibility(View.VISIBLE);

						ll_detained_itemlist_layout.setVisibility(View.VISIBLE);

						sb_detained_items.append("");
						setCheckedValues(false, "donotedit");
						chck_detainedItems_none.setChecked(true);
					}
				}
				if (cb.length > 0) {
					for (int i = 0; i < cb.length; i++) {
						cb[i].setEnabled(true);
					}
				}

			} else if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
				Log.i("releasedocuments  :::::", "****releasedocuments Condition Entered*****");
				if (driver_mobileNo != null) {
					et_driver_contact_spot.setText(driver_mobileNo);
				} else {
					et_driver_contact_spot.setText("");
				}

				rl_detained_items.setVisibility(View.GONE);
				tv_spotChallanTwo_header.setText("" + getResources().getString(R.string.release_documents_one_line));

				radioGroupButton_spotpaymentYes.setChecked(true);
				radioGroupButton_detaineditems_yes.setChecked(true);

				if (cb.length > 0) {
					for (int i = 0; i < cb.length; i++) {
						cb[i].setEnabled(true);
					}
				}

				/*----------SAID BY MADHUSUDHAN : 15-02-2016---STARTED------------*/
				ll_spot_payment_root.setVisibility(View.GONE);
				ll_cash_or_card.setVisibility(View.GONE);
				rl_card_details.setVisibility(View.GONE);
				// et_payment_card_pin.setVisibility(View.GONE);
				// et_payment_card_pin.setText("");
				is_it_spot_send = "1";// for is it spot : YES
				/*----------SAID BY MADHUSUDHAN : 15-02-2016---ENDED------------*/

				Log.i("Releasen", "" + ServiceHelper.detained_items_list_details.length);
				if (ServiceHelper.detained_items_list_details.length > 0) {
					ll_detained_items_root.setVisibility(View.VISIBLE);
					ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
				} else {
					ll_detained_items_root.setVisibility(View.GONE);
					ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
				}
			} else if (Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {

				Log.i("vehiclehistory  :::::", "****vehiclehistory Condition Entered*****");

				rl_detained_items.setVisibility(View.VISIBLE);
				tv_spotChallanTwo_header.setText("" + getResources().getString(R.string.vehicle_history));

				ll_cash_or_card.setVisibility(View.VISIBLE);
				ll_detained_items_root.setVisibility(View.VISIBLE);

				proffession_layout.setVisibility(View.GONE);

				is_it_spot_send = "1";

				String vehicle_split = et_regcid_spot.getText().toString().trim().substring(0, 2);
				if (radioGroupButton_spotpaymentNo.isChecked()) {
					Log.i("vehicle_split :::", "" + vehicle_split);
					if (vehicle_split.equals("AP") || vehicle_split.equals("TS")) {
						Log.i("vehicle_split 2 :::", "" + vehicle_split);
						radioGroupButton_spotpaymentYes.setEnabled(true);
						radioGroupButton_spotpaymentNo.setChecked(true);
						// radioGroupButton_spotpaymentYes.setChecked(true);
						if (radioGroupButton_detaineditems_no.isChecked()) {
							Log.i("Log 2  :::::", "****Entered*****");

							sb_detained_items.append("");
							setCheckedValues(false, "donotedit");
							chck_detainedItems_none.setChecked(true);

							rl_detained_items.setVisibility(View.VISIBLE);
							ll_detained_items_root.setVisibility(View.GONE);
							ll_detained_itemlist_layout.setVisibility(View.GONE);
							ll_cash_or_card.setVisibility(View.GONE);
						} else {
							Log.i("Log 3  :::::", "****Entered*****");
							rl_detained_items.setVisibility(View.VISIBLE);
							ll_detained_items_root.setVisibility(View.VISIBLE);
							ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
							ll_cash_or_card.setVisibility(View.VISIBLE);
						}

					} else {
						radioGroupButton_spotpaymentYes.setEnabled(true);
						radioGroupButton_spotpaymentYes.setChecked(true);

						rl_detained_items.setVisibility(View.VISIBLE);
						ll_detained_items_root.setVisibility(View.VISIBLE);

						ll_detained_itemlist_layout.setVisibility(View.VISIBLE);

						sb_detained_items.append("");
						setCheckedValues(false, "donotedit");
						chck_detainedItems_none.setChecked(true);
					}
				}
				if (cb.length > 0) {
					for (int i = 0; i < cb.length; i++) {
						cb[i].setEnabled(true);
					}
				}

			}

			if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
					|| (Dashboard.check_vhleHistory_or_Spot.equals("spot")
							|| (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")))) {

				if ((ServiceHelper.detained_items_list_details.length > 0)
						&& (!ServiceHelper.Opdata_Chalana.equals("0"))) {

					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					StringBuffer myId = new StringBuffer();

					params.setMargins(0, 0, 0, 10);
					myId.delete(0, myId.length());
					cb = new CheckBox[ServiceHelper.detained_items_list_details.length];

					ll = new LinearLayout[ServiceHelper.detained_items_list_details.length];
					Log.i("LL LENGHT", "" + ll.length);

					for (int i = 0; i < (ServiceHelper.detained_items_list_details.length); i++) {
						ll[i] = new LinearLayout(getApplicationContext());
						ll[i].setId(i);
						ll[i].setLayoutParams(params);
						ll[i].setOrientation(LinearLayout.HORIZONTAL);

						cb[i] = new CheckBox(getApplicationContext());
						android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
						int identifier = getResources().getIdentifier(
								getApplicationContext().getPackageName() + ":drawable/custom_chec_box", null, null);
						cb[i].setText("   " + ServiceHelper.detained_items_list_details[i][2].toString().trim()
								+ "       " + ServiceHelper.detained_items_list_details[i][1].toString().trim());
						cb[i].setButtonDrawable(identifier);
						cb[i].setTextAppearance(getApplicationContext(), R.style.navi_text_style);
						cb[i].setId(i);
						// cb[i].setOnClickListener(onRadioButtonClick(cb[i].getId()));

						ll[i].addView(cb[i]);
						detained_items_status.add(true);

						cb[i].setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								check = (CheckBox) v;

								if (detained_items_status.get(check.getId()) == true) {
									detained_items_status.set(check.getId(), false);
								} else if (detained_items_status.get(check.getId()) == false) {
									detained_items_status.set(check.getId(), true);
								}
							}
						});

						ll_detained_itemlist_layout.addView(ll[i]);
					}
				}
			}

			/***** BUTTON EVENTS *******/
			btn_send_otp_to_mobile.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// TODO Auto-generated method stub
					String tempContactNumber = et_driver_contact_spot.getText().toString();

					if (tempContactNumber.equals("")) {
						et_driver_contact_spot.setError(
								Html.fromHtml("<font color='black'>Enter mobile number to send OTP!!</font>"));
						et_driver_contact_spot.requestFocus();

					} else if (tempContactNumber.trim() != null && tempContactNumber.trim().length() > 1
							&& tempContactNumber.trim().length() != 10) {
						et_driver_contact_spot
								.setError(Html.fromHtml("<font color='black'>Enter Valid mobile number!!</font>"));
						et_driver_contact_spot.requestFocus();

					} else if (tempContactNumber.length() == 10) {
						if ((tempContactNumber.charAt(0) == '7') || (tempContactNumber.charAt(0) == '8')
								|| (tempContactNumber.charAt(0) == '9')) {
							if (isOnline()) {
								otp_status = "send";
								new Async_sendOTP_to_mobile().execute();
							} else {
								showToast("Please check your network connection!");
							}
						} else {
							et_driver_contact_spot
									.setError(Html.fromHtml("<font color='black'>Check Contact No.!!</font>"));
							et_driver_contact_spot.requestFocus();
						}

					} else if (tempContactNumber.length() == 11) {
						if (tempContactNumber.charAt(0) == '0') {
							if (isOnline()) {
								otp_status = "send";
								new Async_sendOTP_to_mobile().execute();
							} else {
								showToast("Please check your network connection!");
							}
						} else {
							et_driver_contact_spot
									.setError(Html.fromHtml("<font color='black'>Check Contact No.!!</font>"));
							et_driver_contact_spot.requestFocus();
						}

					}
				}
			});

			radioGroupButton_detaineditems_yes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int i = 0; i < cb.length; i++) {
						cb[i].setEnabled(true);
					}
				}
			});

			radioGroupButton_detaineditems_no.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					is_it_spot_send = "0";
					for (int i = 0; i < cb.length; i++) {
						cb[i].setChecked(false);
						cb[i].setEnabled(false);
					}
				}
			});

			/* RADIO BUTTON FOR SPOT PAYMENT */
			radioGroupButton_spotpaymentYes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					is_it_spot_send = "1";
					ll_cash_or_card.setVisibility(View.VISIBLE);
					// et_payment_card_pin.setVisibility(View.GONE);
					Log.i("SPOT YES_NO G", "" + ServiceHelper.detained_items_list_details.length);
					Log.i("SPOT YES_NOROM", "" + Dashboard.check_vhleHistory_or_Spot);

					if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
							|| (Dashboard.check_vhleHistory_or_Spot.equals("spot"))
							|| (Dashboard.check_vhleHistory_or_Spot.equals("towing"))) {

						if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
								|| (Dashboard.check_vhleHistory_or_Spot.equals("spot"))) {

							rl_detained_items.setVisibility(View.GONE);
							ll_detained_items_root.setVisibility(View.VISIBLE);
							ll_detained_itemlist_layout.setVisibility(View.VISIBLE);
							sb_detained_items.append("");
							setCheckedValues(false, "donotedit");
							chck_detainedItems_none.setChecked(true);
						}
						if ((ServiceHelper.detained_items_list_details.length > 0)) {
							ll_detained_items_root.setVisibility(View.VISIBLE);
							if (cb.length > 0) {
								for (int i = 0; i < cb.length; i++) {
									cb[i].setEnabled(false);
								}
							}
						} else {
							ll_detained_items_root.setVisibility(View.VISIBLE);
							// ll_detained_itemlist_layout.setVisibility(View.GONE);
						}
					}
				}
			});

			radioGroupButton_spotpaymentNo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					is_it_spot_send = "0";
					ll_cash_or_card.setVisibility(View.GONE);
					// et_payment_card_pin.setVisibility(View.GONE);
					// ll_detained_items_root.setVisibility(View.GONE);

					if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
							|| (Dashboard.check_vhleHistory_or_Spot.equals("spot"))) {

						String vehicle_split = et_regcid_spot.getText().toString().trim().substring(0, 2);
						if (radioGroupButton_spotpaymentNo.isChecked()) {
							Log.i("radio Btn NO case :::", "" + vehicle_split);

							rl_detained_items.setVisibility(View.VISIBLE);
							ll_detained_items_root.setVisibility(View.GONE);
							ll_cash_or_card.setVisibility(View.GONE);
							ll_detained_itemlist_layout.setVisibility(View.GONE);
						}
					}
					if ((Dashboard.check_vhleHistory_or_Spot.equals("chargesheet"))) {
						Log.i("Chargesheet ue ::::", is_it_spot_send);
						is_it_spot_send = "1";
					}
				}
			});

			radioGroupButton_cashcard0.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rl_card_details.setVisibility(View.GONE);
					// et_payment_card_pin.setVisibility(View.GONE);
					cardFLG = false;
				}
			});

			radioGroupButton_cashcard1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rl_card_details.setVisibility(View.GONE);
					// et_payment_card_pin.setVisibility(View.GONE);
					cardFLG = true;
				}
			});
			/*** KIRAN CODING END ***/

			/*
			 * if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
			 * btn_send_otp_to_mobile.setVisibility(View.GONE);
			 * et_confirm_otp.setText("");
			 * ll_confrim_otp.setVisibility(View.GONE);
			 *
			 * }else if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
			 * String vehicle_split = et_regcid_spot.getText().toString().trim()
			 * .substring(0,2);
			 *
			 * Log.i("vehicle_split  ::::", ""+vehicle_split);
			 *
			 * if
			 * ((et_aadharnumber_spot.getText().toString().trim().equals(""))&&
			 * (vehicle_split.equals("AP") || vehicle_split.equals("TS"))) {
			 * radiogrp_cash_or_card.setClickable(false);
			 * //radioGroupButton_cashcard0.setClickable(false); } }else if
			 * (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
			 * rl_detained_items.setVisibility(View.VISIBLE);
			 * tv_spotChallanTwo_header.setText(""+
			 * getResources().getString(R.string.vehicle_history));
			 *
			 * } else if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
			 * Log.i("violation_code_value :::", ""+violation_code_value); if
			 * (violation_code_value.equals("64") ||
			 * btn_violation.getText().toString().contains("W/o Driving Licence"
			 * )) { proffession_layout.setVisibility(View.GONE);
			 * btn_select_profession.setOnClickListener(this);
			 * rl_detained_items.setVisibility(View.VISIBLE);
			 * tv_spotChallanTwo_header.setText("" +
			 * getResources().getString(R.string.spot_challan));
			 *
			 * }else{ proffession_layout.setVisibility(View.GONE);
			 * btn_select_profession.setOnClickListener(this);
			 * rl_detained_items.setVisibility(View.VISIBLE);
			 * tv_spotChallanTwo_header.setText("" +
			 * getResources().getString(R.string.spot_challan)); } }else if
			 * (Dashboard.check_vhleHistory_or_Spot.equals("specialdrive")) {
			 *
			 * rl_detained_items.setVisibility(View.VISIBLE);
			 * tv_spotChallanTwo_header.setText("" +
			 * getResources().getString(R.string.special_drive));
			 *
			 * } else if (Dashboard.check_vhleHistory_or_Spot.equals("towing"))
			 * {
			 *
			 * rl_detained_items.setVisibility(View.VISIBLE);
			 * tv_spotChallanTwo_header.setText("" +
			 * getResources().getString(R.string.towing_one_line));
			 *
			 * } else if
			 * (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))
			 * {
			 *
			 * if (driver_mobileNo !=null) {
			 * et_driver_contact_spot.setText(driver_mobileNo); }else {
			 * et_driver_contact_spot.setText(""); }
			 *
			 * rl_detained_items.setVisibility(View.GONE);
			 * tv_spotChallanTwo_header.setText("" +
			 * getResources().getString(R.string.release_documents_one_line));
			 *
			 * radioGroupButton_spotpaymentYes.setChecked(true);
			 * radioGroupButton_detaineditems_yes.setChecked(true);
			 *
			 * if (cb.length > 0) { for (int i = 0; i < cb.length; i++) {
			 * cb[i].setEnabled(true); } }
			 *
			 * ----------SAID BY MADHUSUDHAN : 15-02-2016---STARTED------------
			 * ll_spot_payment_root.setVisibility(View.GONE);
			 * ll_cash_or_card.setVisibility(View.GONE);
			 * rl_card_details.setVisibility(View.GONE);
			 * //et_payment_card_pin.setVisibility(View.GONE);
			 * //et_payment_card_pin.setText(""); is_it_spot_send = "1";//for is
			 * it spot : YES ----------SAID BY MADHUSUDHAN :
			 * 15-02-2016---ENDED------------
			 *
			 * Log.i("Released Detained Items Len", ""+
			 * ServiceHelper.detained_items_list_details.length); if
			 * (ServiceHelper.detained_items_list_details.length > 0) {
			 * ll_detained_items_root.setVisibility(View.VISIBLE);
			 * ll_detained_itemlist_layout.setVisibility(View.VISIBLE); } else {
			 * ll_detained_items_root.setVisibility(View.GONE);
			 * ll_detained_itemlist_layout.setVisibility(View.VISIBLE); } }
			 *
			 * if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) { String
			 * three_wheeler = btn_wheller_code.getText().toString().trim();
			 * Log.i("three_wheeler :::", ""+three_wheeler); if
			 * (et_driver_lcnce_num_spot.getText().toString().equals("") &&(
			 * three_wheeler.contains("3") ||
			 * three_wheeler.contains("3-WHEELER"))) {
			 * Log.i("is_it_spot_send Value ::::::",is_it_spot_send);
			 * sb_detained_items.delete(0, sb_detained_items.length());
			 * sb_detained_items.append("02:VEHICLE@");
			 * chck_detainedItems_vhcle.setChecked(true);
			 *
			 * chck_detainedItems_licence.setClickable(false);
			 * chck_detainedItems_none.setClickable(false);
			 * chck_detainedItems_permit.setClickable(false);
			 * chck_detainedItems_rc.setClickable(false);
			 * chck_detainedItems_vhcle.setClickable(false); }else if
			 * (et_driver_lcnce_num_spot.getText().toString().equals("")) {
			 * sb_detained_items.delete(0, sb_detained_items.length());
			 * sb_detained_items.append("02:VEHICLE@");
			 * chck_detainedItems_vhcle.setChecked(true); }else if
			 * (et_aadharnumber_spot.getText().toString().equals("")) {
			 * sb_detained_items.delete(0, sb_detained_items.length());
			 * sb_detained_items.append("02:VEHICLE@");
			 * chck_detainedItems_vhcle.setChecked(true);
			 *
			 * chck_detainedItems_none.setOnClickListener(new OnClickListener()
			 * {
			 *
			 * @Override public void onClick(View arg0) { // TODO Auto-generated
			 * method stub
			 *
			 * TextView title = new TextView(SpotChallan.this);
			 * title.setText("ALERT"); title.setBackgroundColor(Color.RED);
			 * title.setGravity(Gravity.CENTER);
			 * title.setTextColor(Color.WHITE); title.setTextSize(26);
			 * title.setTypeface(title.getTypeface(), Typeface.BOLD);
			 * title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
			 * dialog_logo, 0, R.drawable.dialog_logo, 0); title.setPadding(20,
			 * 0, 20, 0); title.setHeight(70);
			 *
			 * AlertDialog.Builder alertDialogBuilder = new
			 * AlertDialog.Builder(SpotChallan.this,
			 * AlertDialog.THEME_HOLO_LIGHT);
			 * alertDialogBuilder.setCustomTitle(title);
			 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			 * alertDialogBuilder.
			 * setMessage("\n If No Aadhar Number then\n Kindly Detain Any Item !!!\n"
			 * ); alertDialogBuilder.setPositiveButton( "Ok", new
			 * DialogInterface.OnClickListener() {
			 *
			 * @Override public void onClick(DialogInterface arg0, int arg1) {
			 * // et_aadharnumber_spot.setText(""); } });
			 * alertDialogBuilder.setCancelable(false); AlertDialog alertDialog
			 * = alertDialogBuilder.create(); alertDialog.show();
			 * alertDialog.getWindow().getAttributes();
			 *
			 * TextView textView = (TextView)
			 * alertDialog.findViewById(android.R.id.message);
			 * textView.setTextSize(28);
			 * textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			 * textView.setGravity(Gravity.CENTER);
			 *
			 * Button btn1 =
			 * alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			 * btn1.setTextSize(22); btn1.setTextColor(Color.WHITE);
			 * btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			 * btn1.setBackgroundColor(Color.RED);
			 *
			 * } });
			 *
			 * } else { --------DEFAULT NONE : 05-02-2016-------
			 * sb_detained_items.append(""); setCheckedValues(false,
			 * "donotedit"); chck_detainedItems_none.setChecked(true);
			 * ------------------------------------------ } }
			 *
			 *
			 * if
			 * ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
			 * || (Dashboard.check_vhleHistory_or_Spot.equals("spot") ||
			 * (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")))
			 * ) {
			 *
			 * if ((ServiceHelper.detained_items_list_details.length > 0) &&
			 * (!ServiceHelper.Opdata_Chalana.equals("0"))) {
			 *
			 * LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			 * LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			 * StringBuffer myId = new StringBuffer();
			 *
			 * params.setMargins(0, 0, 0, 10); myId.delete(0, myId.length()); cb
			 * = new CheckBox[ServiceHelper.detained_items_list_details.length];
			 *
			 * ll = new
			 * LinearLayout[ServiceHelper.detained_items_list_details.length];
			 * Log.i("LL LENGHT", "" + ll.length);
			 *
			 * for (int i = 0; i <
			 * (ServiceHelper.detained_items_list_details.length); i++) { ll[i]
			 * = new LinearLayout(getApplicationContext()); ll[i].setId(i);
			 * ll[i].setLayoutParams(params);
			 * ll[i].setOrientation(LinearLayout.HORIZONTAL);
			 *
			 * cb[i] = new CheckBox(getApplicationContext());
			 * android.widget.LinearLayout.LayoutParams params1 = new
			 * android.widget.LinearLayout.LayoutParams(
			 * LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f); int
			 * identifier = getResources().getIdentifier(
			 * getApplicationContext().getPackageName()+
			 * ":drawable/custom_chec_box", null, null); cb[i].setText("   " +
			 * ServiceHelper.detained_items_list_details[i][2]
			 * .toString().trim() + "       " +
			 * ServiceHelper.detained_items_list_details[i][1].toString().trim()
			 * ); cb[i].setButtonDrawable(identifier);
			 * cb[i].setTextAppearance(getApplicationContext(),
			 * R.style.navi_text_style); cb[i].setId(i); //
			 * cb[i].setOnClickListener(onRadioButtonClick(cb[i].getId()));
			 *
			 * ll[i].addView(cb[i]); detained_items_status.add(true);
			 *
			 * cb[i].setOnClickListener(new OnClickListener() {
			 *
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub check = (CheckBox) v;
			 *
			 * if (detained_items_status.get(check.getId()) == true) {
			 * detained_items_status.set(check.getId(),false); } else if
			 * (detained_items_status.get(check.getId()) == false) {
			 * detained_items_status.set(check.getId(),true); } } });
			 * ll_detained_itemlist_layout.addView(ll[i]); } } }
			 *
			 *
			 *
			 * btn_send_otp_to_mobile.setOnClickListener(new OnClickListener() {
			 *
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub String tempContactNumber =
			 * et_driver_contact_spot.getText().toString();
			 *
			 * if (tempContactNumber.equals("")) {
			 * et_driver_contact_spot.setError(Html.
			 * fromHtml("<font color='black'>Enter mobile number to send OTP!!</font>"
			 * )); et_driver_contact_spot.requestFocus(); } else if
			 * (tempContactNumber.trim()!=null &&
			 * tempContactNumber.trim().length()>1 &&
			 * tempContactNumber.trim().length()!=10) {
			 * et_driver_contact_spot.setError(Html.
			 * fromHtml("<font color='black'>Enter Valid mobile number!!</font>"
			 * )); et_driver_contact_spot.requestFocus(); } else if
			 * (tempContactNumber.length() == 10) { if
			 * ((tempContactNumber.charAt(0) == '7') ||
			 * (tempContactNumber.charAt(0) == '8') ||
			 * (tempContactNumber.charAt(0) == '9')) { if (isOnline()) {
			 * otp_status = "send"; new Async_sendOTP_to_mobile().execute();
			 *
			 * } else { showToast("Please check your network connection!"); } }
			 * else { et_driver_contact_spot.setError(Html.
			 * fromHtml("<font color='black'>Check Contact No.!!</font>"));
			 * et_driver_contact_spot.requestFocus(); } } else if
			 * (tempContactNumber.length() == 11) { if
			 * (tempContactNumber.charAt(0) == '0') { if (isOnline()) {
			 * otp_status = "send"; new Async_sendOTP_to_mobile().execute(); }
			 * else { showToast("Please check your network connection!"); } }
			 * else { et_driver_contact_spot.setError(Html.
			 * fromHtml("<font color='black'>Check Contact No.!!</font>"));
			 * et_driver_contact_spot.requestFocus(); } } } });
			 *
			 *
			 *
			 *
			 * radiogrp_release_detained_items.setOnCheckedChangeListener(new
			 * OnCheckedChangeListener() {
			 *
			 * @Override public void onCheckedChanged(RadioGroup group, int
			 * checkedId) { switch (checkedId) { case
			 * R.id.radioGroupButton_detaineditems_yes: for (int i = 0; i <
			 * cb.length; i++) { cb[i].setEnabled(true); } break; case
			 * R.id.radioGroupButton_detaineditems_no: for (int i = 0; i <
			 * cb.length; i++) { cb[i].setChecked(false);
			 * cb[i].setEnabled(false); } break; } } });
			 *
			 * RADIO BUTTON FOR SPOT PAYMENT
			 * radiogrp_spot_payment.setOnCheckedChangeListener(new
			 * OnCheckedChangeListener() {
			 *
			 * @Override public void onCheckedChanged(RadioGroup group, int
			 * checkedId) { // TODO Auto-generated method stub switch
			 * (checkedId) { case R.id.radioGroupButton_spotpayment0:// yes
			 * is_it_spot_send = "1";
			 * ll_cash_or_card.setVisibility(View.VISIBLE);
			 * //et_payment_card_pin.setVisibility(View.GONE);
			 *
			 *//**
				 * IF NO RELEASED DETAINED ITEMS ARE THERE HIDE THE RADIO
				 * BUTTONS LIKE DETAINED ITEMS
				 *//*
				 *
				 * Log.i("SPOT YES_NO RELSD DETNED LENG",""+
				 * ServiceHelper.detained_items_list_details.length);
				 * Log.i("SPOT YES_NO RELSD DETNED FROM", ""+
				 * Dashboard.check_vhleHistory_or_Spot);
				 *
				 * if
				 * ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"
				 * )) || (Dashboard.check_vhleHistory_or_Spot.equals("spot")) ||
				 * (Dashboard.check_vhleHistory_or_Spot.equals("towing"))) {
				 *
				 * if ((ServiceHelper.detained_items_list_details.length > 0)) {
				 *
				 * ll_detained_items_root.setVisibility(View.VISIBLE); if
				 * (cb.length > 0) { for (int i = 0; i < cb.length; i++) {
				 * cb[i].setEnabled(false); } }
				 *
				 * } else {
				 *
				 * ll_detained_items_root.setVisibility(View.GONE); } } break;
				 * case R.id.radioGroupButton_spotpayment1:// no is_it_spot_send
				 * = "0"; ll_cash_or_card.setVisibility(View.GONE);
				 * //et_payment_card_pin.setVisibility(View.GONE);
				 * ll_detained_items_root.setVisibility(View.GONE); if
				 * ((Dashboard.check_vhleHistory_or_Spot.equals("chargesheet")))
				 * { Log.i("Chargesheet is_it_spot Value ::::",
				 * is_it_spot_send); is_it_spot_send = "1"; }
				 *
				 * break; } } });
				 *
				 *
				 * radiogrp_cash_or_card.setOnCheckedChangeListener(new
				 * OnCheckedChangeListener() {
				 *
				 * @Override public void onCheckedChanged(RadioGroup group, int
				 * checkedId) { // TODO Auto-generated method stub switch
				 * (checkedId) { case R.id.radioGroupButton_cashcard0:// cash
				 * rl_card_details.setVisibility(View.GONE);
				 * //et_payment_card_pin.setVisibility(View.GONE); cardFLG =
				 * false ; break; case R.id.radioGroupButton_cashcard1:// card
				 * rl_card_details.setVisibility(View.GONE);
				 * //et_payment_card_pin.setVisibility(View.GONE);
				 *
				 * cardFLG = true ; //new Async_taskPaymentGateWay().execute();
				 *
				 * break; } } });
				 */
			// 21_MAR_2017
			/*************** FINAL SUBMIT DETAILS *****************/
			btn_final_submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					releasedDetained_items_list_toSend = new StringBuffer("");
					releasedDetained_items_list_toSend.delete(0, releasedDetained_items_list_toSend.length());

					/* THIS IS FOR DETAINED ITEMS CHECK BOX LIST */
					if (cb.length > 0) {
						for (int i = 0; i < cb.length; i++) {
							if (cb[i].isChecked()) {
								releasedDetained_items_list_toSend
										.append("" + releasedDetained_items_list.get(i).toString());
								releasedDetained_items_list_toSend.append("@");
							}
						}
					}
					if (releasedDetained_items_list_toSend == null
							&& releasedDetained_items_list_toSend.toString().trim().equalsIgnoreCase("")) {
						releasedDetained_items_list_toSend.append("");
						Log.i("empty_releasedd",
								"" + releasedDetained_items_list_toSend.toString());
					}

					/*
					 * START THIS IS TO APPEND THE SELECTED CHECK BOXES IN TO
					 * THE DETAINED ITEMS
					 */
					sb_detained_items.delete(0, sb_detained_items.length());
					if (chck_detainedItems_rc.isChecked()) {
						sb_detained_items.append("01:RC@");
					}

					if (chck_detainedItems_vhcle.isChecked()) {
						sb_detained_items.append("02:VEHICLE@");
					}

					if (chck_detainedItems_licence.isChecked()) {
						sb_detained_items.append("03:LICENCE@");
					}

					if (chck_detainedItems_permit.isChecked()) {
						sb_detained_items.append("04:PERMIT@");
					}

					if (chck_detainedItems_none.isChecked()) {
						sb_detained_items.append("");
					}
					/*
					 * END THIS IS TO APPEND THE SELECTED CHECK BOXES IN TO THE
					 * DETAINED ITEMS
					 */

					/* TO GET CARD DETAILS START */
					if (btn_id_proof_spot.getText().toString().trim().equals("" + id_proof_arr[0])) {
						emailId_to_send = "" + et_id_proof_spot.getText().toString().trim();
						pancard_to_send = "";
						passport_to_send = "";
						VoterId_to_send = "";
					} else if (btn_id_proof_spot.getText().toString().trim().equals("" + id_proof_arr[1])) {
						emailId_to_send = "";
						pancard_to_send = "" + et_id_proof_spot.getText().toString().trim();
						passport_to_send = "";
						VoterId_to_send = "";
					} else if (btn_id_proof_spot.getText().toString().trim().equals("" + id_proof_arr[2])) {
						emailId_to_send = "";
						pancard_to_send = "";
						passport_to_send = "" + et_id_proof_spot.getText().toString().trim();
						VoterId_to_send = "";
					} else if (btn_id_proof_spot.getText().toString().trim().equals("" + id_proof_arr[3])) {
						emailId_to_send = "";
						pancard_to_send = "";
						passport_to_send = "";
						VoterId_to_send = "" + et_id_proof_spot.getText().toString().trim();
					}
					/* TO GET CARD DETAILS START */
					/* TO GET PRESENT DATE & TIME TO SEND TO SERVICE START */
					// getDateAndTime();
					/* TO GET PRESENT DATE & TIME TO SEND TO SERVICE END */

					bookedPScode_send_from_settings = preferences.getString("psname_code", "0");
					bookedPSname_send_from_settings = preferences.getString("psname_name", "psname");
					point_code_send_from_settings = preferences.getString("point_code", "0");
					point_name_send_from_settings = preferences.getString("point_name", "pointname");
					exact_location_send_from_settings = preferences.getString("exact_location", "location");

					Log.i("***FROM THE : **", "" + Dashboard.check_vhleHistory_or_Spot);

					/*---------DATA TO SERVICE : SPOT & TOWING START------------*/
					if ((Dashboard.check_vhleHistory_or_Spot.equals("spot"))) {
						Log.i("vehicle::", "" + sb_selected_penlist_send);
						if (sb_selected_penlist_positions.size() > 0) {
							for (int i = 0; i < sb_selected_penlist_positions.size(); i++) {
								sb_selected_penlist_send.append("" + sb_selected_penlist
										.get(Integer.parseInt(sb_selected_penlist_positions.get(i))));
								Log.i("sb_selected_penli:::", "" + sb_selected_penlist_send);
							}
						}
						if (sb_selected_penlist_send == null && sb_selected_penlist_send.toString().equals("")) {
							sb_selected_penlist_send.append("");
						}
						/*---- FOR TOWING CONCT NUM IS OPTIONLA : 25-06-2015---*/
						if ((!chck_detainedItems_rc.isChecked()) && (!chck_detainedItems_vhcle.isChecked())
								&& (!chck_detainedItems_licence.isChecked()) && (!chck_detainedItems_permit.isChecked())
								&& (!chck_detainedItems_none.isChecked())) {
							showToast("Check Detained Items");

						}

						else if (!Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
							Log.i("vehiclehi::", "" + sb_selected_penlist_send);

							String tempContactNumber = et_driver_contact_spot.getText().toString().trim();
							if (et_driver_contact_spot.getText().toString().trim().equals("")) {
								// et_driver_contact_spot.setError("Enter driver
								// contact number");
								et_driver_contact_spot.setError(
										Html.fromHtml("<font color='black'>Enter driver contact number!!</font>"));
								et_driver_contact_spot.requestFocus();
							} else if (tempContactNumber.trim() != null && tempContactNumber.trim().length() > 1
									&& tempContactNumber.trim().length() != 10) {
								// et_driver_contact_spot.setError("Enter Valid
								// mobile number!!");
								et_driver_contact_spot.setError(
										Html.fromHtml("<font color='black'>Enter Valid mobile number!!!!</font>"));
								et_driver_contact_spot.requestFocus();

							} else if (btn_otp_flag.equalsIgnoreCase("1") && otp_status != "verify") {

								otpAlert();

								sb_selected_penlist_send.delete(0, sb_selected_penlist_send.length());

							} else if (tempContactNumber.length() == 10) {
								if ((tempContactNumber.charAt(0) == '7') || (tempContactNumber.charAt(0) == '8')
										|| (tempContactNumber.charAt(0) == '9')) {
									if (isOnline()) {
										Log.i("----CALLING-----", "----SPOT_PAYMENT-----");
										/* to call mobileSpotChallanPayment */
										if (police_vehcle.isChecked()) {
											is_govt_police = "2";
										} else if (govt_vehcle.isChecked()) {
											is_govt_police = "4";
										}

										String vehicle_split2 = et_regcid_spot.getText().toString().trim().substring(0,
												2);

										if ((vehicle_split2.equals("AP") || vehicle_split2.equals("TS"))
												&& chck_detainedItems_none.isChecked()
												&& radioGroupButton_spotpaymentNo.isChecked()
												&& et_driver_lcnce_num_spot.getText().toString().trim().equals("")) {

											TextView title = new TextView(SpotChallan.this);
											title.setText("ALERT");
											title.setBackgroundColor(Color.RED);
											title.setGravity(Gravity.CENTER);
											title.setTextColor(Color.WHITE);
											title.setTextSize(26);
											title.setTypeface(title.getTypeface(), Typeface.BOLD);
											title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
													R.drawable.dialog_logo, 0);
											title.setPadding(20, 0, 20, 0);
											title.setHeight(70);

											AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
													SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
											alertDialogBuilder.setCustomTitle(title);
											alertDialogBuilder.setIcon(R.drawable.dialog_logo);
											alertDialogBuilder.setMessage("\nPlease kindly Detain Any Item !!!\n");
											alertDialogBuilder.setPositiveButton("Ok",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(DialogInterface arg0, int arg1) {
															// et_aadharnumber_spot.setText("");
														}
													});
											alertDialogBuilder.setCancelable(false);
											AlertDialog alertDialog = alertDialogBuilder.create();
											alertDialog.show();
											alertDialog.getWindow().getAttributes();

											TextView textView = (TextView) alertDialog
													.findViewById(android.R.id.message);
											textView.setTextSize(28);
											textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
											textView.setGravity(Gravity.CENTER);

											Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
											btn1.setTextSize(22);
											btn1.setTextColor(Color.WHITE);
											btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
											btn1.setBackgroundColor(Color.RED);
										} else {
											new Async_spot_challan().execute();
										}
										// }
										// }

										// if
										// ("Y".equals(otp_no_flg.trim().toUpperCase())
										// &&
										// (!Dashboard.check_vhleHistory_or_Spot.equals("towing"))
										// && otp_status != "verify") {
										// showToast("Please Verify OTP");
										// } else {
										// new Async_spot_challan().execute();
										// }

									} else {
										showToast("" + NETWORK_TXT);
									}
								} else {
									et_driver_contact_spot.setError("Check Contact No.");
								}
							} else if (tempContactNumber.length() == 11) {
								if (tempContactNumber.charAt(0) == '0') {
									if (isOnline()) {
										Log.i("----CALLING-----", "----SPOT_PAYMENT-----");
										/* to call mobileSpotChallanPayment */
										if (police_vehcle.isChecked()) {
											is_govt_police = "2";
										} else if (govt_vehcle.isChecked()) {
											is_govt_police = "4";
										}
										// new Async_spot_challan().execute();

										if ("Y".equals(otp_no_flg.trim())
												&& (!Dashboard.check_vhleHistory_or_Spot.equals("towing"))
												&& otp_status != "verify") {
											showToast("Please Verify OTP");
										} else {
											new Async_spot_challan().execute();
										}
									} else {
										showToast("" + NETWORK_TXT);
									}
								} else {
									et_driver_contact_spot.setError("Check Contact No.");
								}
							}

						} else {
							if (isOnline()) {
								/* to call mobileSpotChallanPayment */
								Log.i("----CALLING-----", "----OTHERS-----");

								et_drivername_iOD.setText("");
								et_driverFatherName_iOD.setText("");
								et_driver_address_iOD.setText("");
								et_driver_city_iOD.setText("");

								et_drivername_iOD.setEnabled(true);
								et_drivername_iOD.setClickable(true);

								et_driverFatherName_iOD.setEnabled(true);
								et_driverFatherName_iOD.setClickable(true);

								et_driver_address_iOD.setEnabled(true);
								et_driver_address_iOD.setClickable(true);

								et_driver_city_iOD.setEnabled(true);
								et_driver_city_iOD.setClickable(true);

								if ("Y".equals(otp_no_flg.trim().toUpperCase())
										&& (!Dashboard.check_vhleHistory_or_Spot.equals("towing"))
										&& otp_status != "verify") {
									// showToast("Please Verify OTP");
									TextView title = new TextView(SpotChallan.this);
									title.setText("ALERT");
									title.setBackgroundColor(Color.RED);
									title.setGravity(Gravity.CENTER);
									title.setTextColor(Color.WHITE);
									title.setTextSize(26);
									title.setTypeface(title.getTypeface(), Typeface.BOLD);
									title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0,
											R.drawable.dialog_logo, 0);
									title.setPadding(20, 0, 20, 0);
									title.setHeight(70);

									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
											AlertDialog.THEME_HOLO_LIGHT);
									alertDialogBuilder.setCustomTitle(title);
									alertDialogBuilder.setIcon(R.drawable.dialog_logo);
									alertDialogBuilder.setMessage(
											"\n Please Click on Send OTP Button to Verify Offender Mobile Number!!\n");
									alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface arg0, int arg1) {
											// et_aadharnumber_spot.setText("");
											// et_driver_contact_spot.setError(Html.fromHtml("<font
											// color='black'>Please Click on
											// Send OTP Button!!!!</font>"));
											et_driver_contact_spot.requestFocus();
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
								} else {
									new Async_spot_challan().execute();
								}
								// new Async_spot_challan().execute();
							} else {
								showToast("" + NETWORK_TXT);
							}
						}
					}
					if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {

						btn_send_otp_to_mobile.setVisibility(View.GONE);

						if (sb_selected_penlist_positions.size() > 0) {
							for (int i = 0; i < sb_selected_penlist_positions.size(); i++) {
								sb_selected_penlist_send.append("" + sb_selected_penlist
										.get(Integer.parseInt(sb_selected_penlist_positions.get(i))));
							}
						}

						if (sb_selected_penlist_send == null && sb_selected_penlist_send.toString().equals("")) {
							sb_selected_penlist_send.append("");
						}

						releasedDetained_items_list_toSend = new StringBuffer("");
						releasedDetained_items_list_toSend.delete(0, releasedDetained_items_list_toSend.length());

						if (cb.length > 0) {

							for (int i = 0; i < cb.length; i++) {
								if (cb[i].isChecked()) {
									releasedDetained_items_list_toSend
											.append("" + releasedDetained_items_list.get(i).toString());
									releasedDetained_items_list_toSend.append("@");
								}
							}
						}
						if (releasedDetained_items_list_toSend == null
								&& releasedDetained_items_list_toSend.toString().trim().equalsIgnoreCase("")) {

							releasedDetained_items_list_toSend.append("");
							Log.i("EMPTY releasedDetand",
									"" + releasedDetained_items_list_toSend.toString());
						}
						/*---- FOR TOWING CONCT NUM IS OPTIONLA : 25-06-2015---*/
						if ((!chck_detainedItems_rc.isChecked()) && (!chck_detainedItems_vhcle.isChecked())
								&& (!chck_detainedItems_licence.isChecked()) && (!chck_detainedItems_permit.isChecked())
								&& (!chck_detainedItems_none.isChecked())) {
							showToast("Check Detained Items");

						} else {
							if (isOnline()) {
								/* to call mobileSpotChallanPayment */
								Log.i("----CALLING-----", "----OTHERS-----");

								et_drivername_iOD.setText("");
								et_driverFatherName_iOD.setText("");
								et_driver_address_iOD.setText("");
								et_driver_city_iOD.setText("");

								et_drivername_iOD.setEnabled(true);
								et_drivername_iOD.setClickable(true);

								et_driverFatherName_iOD.setEnabled(true);
								et_driverFatherName_iOD.setClickable(true);

								et_driver_address_iOD.setEnabled(true);
								et_driver_address_iOD.setClickable(true);

								et_driver_city_iOD.setEnabled(true);
								et_driver_city_iOD.setClickable(true);

								new Async_spot_challan().execute();
							} else {
								showToast("" + NETWORK_TXT);
							}
						}
						/*---------DATA TO SERVICE : SPOT & TOWING END------------*/

					} else if ((Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory"))
							|| (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"))) {
						Log.i("vehiclehis:", "" + sb_selected_penlist_send);
						if (sb_selected_penlist_positions.size() > 0) {
							for (int i = 0; i < sb_selected_penlist_positions.size(); i++) {
								sb_selected_penlist_send.append("" + sb_selected_penlist
										.get(Integer.parseInt(sb_selected_penlist_positions.get(i))));
								// Log.i("Released Items :::", ""+
								// sb_selected_penlist_send);
								Log.i("Released Items :::", "" + sb_selected_penlist_send);
							}
						} else if (sb_selected_penlist_send == null && sb_selected_penlist_send.toString().equals("")) {
							sb_selected_penlist_send.append("");
						}

						/*
						 * * DETAINED ITEMS ARE DISABLED FOR VECHICLE RELEASE &
						 * * ENABLED FOR VEHICLE HISTORY *
						 */
						if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
							if (!radioGroupButton_detaineditems_yes.isChecked() && (!chck_detainedItems_rc.isChecked())
									&& (!chck_detainedItems_vhcle.isChecked())
									&& (!chck_detainedItems_licence.isChecked())
									&& (!chck_detainedItems_permit.isChecked())
									&& (!chck_detainedItems_none.isChecked())) {
								showToast("Check Detained Items");
								Log.i("vehiclehistory 2:::", "****ENTERED********");

							} else if (radioGroupButton_detaineditems_no.isChecked()) {
								// chck_detainedItems_none.setChecked(true);
								Log.i("vehiclehistory 3:::", "****ENTERED********");

								String tempContactNumber = et_driver_contact_spot.getText().toString().trim();
								if (et_driver_contact_spot.getText().toString().trim().equals("")) {
									et_driver_contact_spot.setError("Enter driver contact number");

								} else if (btn_otp_flag.equalsIgnoreCase("1") && otp_status != "verify") {
									otpAlert();

									sb_selected_penlist_send.delete(0, sb_selected_penlist_send.length());

								} else if (tempContactNumber.trim() != null && tempContactNumber.trim().length() > 1
										&& tempContactNumber.trim().length() != 10) {
									// showToast("Enter correct mobile
									// number!!");
									et_driver_contact_spot.setError("Enter Valid mobile number!!");
								} else if (tempContactNumber.length() == 10) {
									if ((tempContactNumber.charAt(0) == '7') || (tempContactNumber.charAt(0) == '8')
											|| (tempContactNumber.charAt(0) == '9')) {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								} else if (tempContactNumber.length() == 11) {
									if (tempContactNumber.charAt(0) == '0') {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								}

							} else {
								Log.i("vehiclehistory 4:::", "****ENTERED********");
								String tempContactNumber = et_driver_contact_spot.getText().toString().trim();
								if (et_driver_contact_spot.getText().toString().trim().equals("")) {
									et_driver_contact_spot.setError("Enter driver contact number");
								} else if (tempContactNumber.trim() != null && tempContactNumber.trim().length() > 1
										&& tempContactNumber.trim().length() != 10) {
									// showToast("Enter correct mobile
									// number!!");
									et_driver_contact_spot.setError("Enter Valid mobile number!!");
								} else if (tempContactNumber.length() == 10) {
									if ((tempContactNumber.charAt(0) == '7') || (tempContactNumber.charAt(0) == '8')
											|| (tempContactNumber.charAt(0) == '9')) {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								} else if (tempContactNumber.length() == 11) {
									if (tempContactNumber.charAt(0) == '0') {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								}
							}

						} else {
							if (et_driver_contact_spot.getText().toString().trim().equals("")) {
								et_driver_contact_spot.setError("Enter driver contact number");
							} else if (et_driver_contact_spot.getText().toString().trim().length() < 10) {
								et_driver_contact_spot.setError("Check contact number");
							} else {
								if (isOnline()) {
									/* to call mobileSpotChallanPayment */
									if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
										citizen_status = et_passport.getText().toString().trim();
									}
									if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
										showToast("Please Verify OTP");
									} else {
										new Async_vhcleHistory().execute();
									}
									// new Async_vhcleHistory().execute();
								} else {
									showToast("" + NETWORK_TXT);
								}
							}
						}
					} else if ((Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory"))
							|| (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments"))) {

						Log.i("vehicleh::", "" + sb_selected_penlist_send);
						if (sb_selected_penlist_positions.size() > 0) {
							for (int i = 0; i < sb_selected_penlist_positions.size(); i++) {
								sb_selected_penlist_send.append("" + sb_selected_penlist
										.get(Integer.parseInt(sb_selected_penlist_positions.get(i))));
								// Log.i("Released Items :::", ""+
								// sb_selected_penlist_send);
								Log.i("Released Items :::", "" + sb_selected_penlist_send);
							}
						} else if (sb_selected_penlist_send == null && sb_selected_penlist_send.toString().equals("")) {
							sb_selected_penlist_send.append("");
						}

						/*
						 * * DETAINED ITEMS ARE DISABLED FOR VECHICLE RELEASE &
						 * * ENABLED FOR VEHICLE HISTORY *
						 */
						if (Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
							if (!radioGroupButton_detaineditems_yes.isChecked() && (!chck_detainedItems_rc.isChecked())
									&& (!chck_detainedItems_vhcle.isChecked())
									&& (!chck_detainedItems_licence.isChecked())
									&& (!chck_detainedItems_permit.isChecked())
									&& (!chck_detainedItems_none.isChecked())) {
								showToast("Check Detained Items");
								Log.i("vehiclehistory 2:::", "****ENTERED********");

							} else if (radioGroupButton_detaineditems_no.isChecked()) {
								// chck_detainedItems_none.setChecked(true);
								Log.i("vehiclehistory 3:::", "****ENTERED********");

								String tempContactNumber = et_driver_contact_spot.getText().toString().trim();
								if (et_driver_contact_spot.getText().toString().trim().equals("")) {
									et_driver_contact_spot.setError("Enter driver contact number");
								} else if (tempContactNumber.trim() != null && tempContactNumber.trim().length() > 1
										&& tempContactNumber.trim().length() != 10) {
									// showToast("Enter correct mobile
									// number!!");
									et_driver_contact_spot.setError("Enter Valid mobile number!!");
								} else if (tempContactNumber.length() == 10) {
									if ((tempContactNumber.charAt(0) == '7') || (tempContactNumber.charAt(0) == '8')
											|| (tempContactNumber.charAt(0) == '9')) {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								} else if (tempContactNumber.length() == 11) {
									if (tempContactNumber.charAt(0) == '0') {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								}

							} else {
								Log.i("vehiclehistory 4:::", "****ENTERED********");
								String tempContactNumber = et_driver_contact_spot.getText().toString().trim();
								if (et_driver_contact_spot.getText().toString().trim().equals("")) {
									et_driver_contact_spot.setError("Enter driver contact number");
								} else if (tempContactNumber.trim() != null && tempContactNumber.trim().length() > 1
										&& tempContactNumber.trim().length() != 10) {
									// showToast("Enter correct mobile
									// number!!");
									et_driver_contact_spot.setError("Enter Valid mobile number!!");
								} else if (tempContactNumber.length() == 10) {
									if ((tempContactNumber.charAt(0) == '7') || (tempContactNumber.charAt(0) == '8')
											|| (tempContactNumber.charAt(0) == '9')) {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								} else if (tempContactNumber.length() == 11) {
									if (tempContactNumber.charAt(0) == '0') {
										if (isOnline()) {
											/*
											 * to call mobileSpotChallanPayment
											 */
											if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")) {
												citizen_status = et_passport.getText().toString().trim();
											}
											if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
												showToast("Please Verify OTP");
											} else {
												new Async_vhcleHistory().execute();
											}
											// new
											// Async_vhcleHistory().execute();
										} else {
											showToast("" + NETWORK_TXT);
										}
									} else {
										et_driver_contact_spot.setError("Check Contact No.");
									}
								}
							}

						} else {
							if (et_driver_contact_spot.getText().toString().trim().equals("")) {
								et_driver_contact_spot.setError("Enter driver contact number");
							} else if (et_driver_contact_spot.getText().toString().trim().length() < 10) {
								et_driver_contact_spot.setError("Check contact number");
							} else {
								if (isOnline()) {
									/* to call mobileSpotChallanPayment */
									if (Dashboard_PC.check_vhleHistory_or_Spot.equals("releasedocuments")) {
										citizen_status = et_passport.getText().toString().trim();
									}
									if ("Y".equals(otp_no_flg.trim().toUpperCase()) && otp_status != "verify") {
										showToast("Please Verify OTP");
									} else {
										new Async_vhcleHistory().execute();
									}
									// new Async_vhcleHistory().execute();
								} else {
									showToast("" + NETWORK_TXT);
								}
							}
						}

					}

				}
			});

			btn_move_to_first.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_otp_flag = "0";
					removeDialog(SECOND_SPOTSCREEN_DIALOG);
				}
			});
			return dg_scond;

		case OCCUPATION_DIALOG:
			AlertDialog.Builder ad_ocuptn = new AlertDialog.Builder(this);
			ad_ocuptn.setTitle("" + ocuptn_title);
			ad_ocuptn.setSingleChoiceItems(occup_name_arr, selected_ocuptn, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					selected_ocuptn = which;
					btn_select_profession.setText("" + occup_name_arr[which].toString().trim());
					// showToast("" + occup_code_arr[which]);
					removeDialog(OCCUPATION_DIALOG);

					if (btn_select_profession.getText().toString().contains("PRIVATE EMPLOYEE")) {
						profession_code = "6";
						edt_prfession_name.setHint("Enter Name of Company");
						edt_prfession_Address.setHint("Enter Address of Company");
						edt_email_ID.setHint("Enter Email ID of Company");
						removeDialog(OCCUPATION_DIALOG);

					} else if (btn_select_profession.getText().toString().contains("BUSINESS")) {
						profession_code = "3";
						edt_prfession_name.setHint("Enter Name of Business");
						edt_prfession_Address.setHint("Enter Address of Business");
						edt_email_ID.setHint("Enter Email ID of Bussiness");
						removeDialog(OCCUPATION_DIALOG);

					} else if (btn_select_profession.getText().toString().contains("SELF EMPLOYEE")) {
						profession_code = "18";
						edt_prfession_name.setHint("Enter Name of Employment");
						edt_prfession_Address.setHint("Enter Address of Employment ");
						edt_email_ID.setHint("Enter Email ID of Employment");
						removeDialog(OCCUPATION_DIALOG);

					} else if (btn_select_profession.getText().toString().contains("GOVT EMPLOYEE")) {
						profession_code = "7";
						edt_prfession_name.setHint("Enter Name of Department");
						edt_prfession_Address.setHint("Enter Address of Department ");
						edt_email_ID.setHint("Enter Email ID of Department");
						removeDialog(OCCUPATION_DIALOG);

					} else if (btn_select_profession.getText().toString().contains("STUDENT")) {
						profession_code = "1";
						edt_prfession_name.setHint("Enter Name of College");
						edt_prfession_Address.setHint("Enter Address of College");
						edt_email_ID.setHint("Enter Email ID of College");
						removeDialog(OCCUPATION_DIALOG);

					} else if (btn_select_profession.getText().toString().contains("OTHERS")) {
						profession_code = "11";
						edt_prfession_name.setHint("Enter Name of Profession");
						edt_prfession_Address.setHint("Enter Address of Profession");
						edt_email_ID.setHint("Enter Email ID of Profession");
						removeDialog(OCCUPATION_DIALOG);

					} else {
						profession_code = "";
						edt_prfession_name.setHint("Enter Name of Profession");
						edt_prfession_Address.setHint("Enter Address of Profession");
						edt_email_ID.setHint("Enter Email ID of Profession");
						removeDialog(OCCUPATION_DIALOG);
					}
				}
			});
			Dialog dg_ocuptn = ad_ocuptn.create();
			return dg_ocuptn;

		case ID_PROOF_DIALOG:
			TextView title4 = new TextView(this);
			title4.setText("Select ID Proof");
			title4.setBackgroundColor(Color.parseColor("#007300"));
			title4.setGravity(Gravity.CENTER);
			title4.setTextColor(Color.WHITE);
			title4.setTextSize(26);
			title4.setTypeface(title4.getTypeface(), Typeface.BOLD);
			title4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
			title4.setPadding(20, 0, 20, 0);
			title4.setHeight(70);

			AlertDialog.Builder ad_proof = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			ad_proof.setCustomTitle(title4);
			ad_proof.setSingleChoiceItems(id_proof_arr, selected_id_proof, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					selected_id_proof = which;
					btn_id_proof_spot.setText("" + id_proof_arr[which]);
					et_id_proof_spot.setHint("" + id_proof_hints_arr[which]);
					/*
					 * selected_id_proof = which; btn_id_proof_spot.setText("" +
					 * id_proof_arr[which]); et_id_proof_spot.setHint("" +
					 * id_proof_hints_arr[which]);
					 */
					if (btn_id_proof_spot.getText().toString().equals("Email ID")) {
						et_id_proof_spot.setFilters(new InputFilter[] {});
						et_id_proof_spot
								.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
										| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
						et_id_proof_spot.requestFocus();
					}
					if (btn_id_proof_spot.getText().toString().equals("Aadhar Number")) {
						int maxLength = 12;
						et_id_proof_spot.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
						et_id_proof_spot.setInputType(InputType.TYPE_CLASS_NUMBER);
						et_id_proof_spot.requestFocus();
					}
					if (btn_id_proof_spot.getText().toString().equals("Pancard Number")) {
						et_id_proof_spot.requestFocus();
						et_id_proof_spot.setFilters(new InputFilter[] {});
						et_id_proof_spot.setInputType(InputType.TYPE_CLASS_TEXT);
						et_id_proof_spot.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

					} else if (btn_id_proof_spot.getText().toString().equals("Passport Number")) {
						et_id_proof_spot.requestFocus();
						et_id_proof_spot.setFilters(new InputFilter[] {});
						et_id_proof_spot.setInputType(InputType.TYPE_CLASS_TEXT);
						et_id_proof_spot.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

					} else if (btn_id_proof_spot.getText().toString().equals("VoterId Number")) {
						et_id_proof_spot.requestFocus();
						et_id_proof_spot.setFilters(new InputFilter[] {});
						et_id_proof_spot.setInputType(InputType.TYPE_CLASS_TEXT);
						et_id_proof_spot.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					}
					removeDialog(ID_PROOF_DIALOG);
				}
			});
			Dialog dg_proof = ad_proof.create();
			return dg_proof;

		case DYNAMIC_VIOLATIONS:

			Dialog dg_dynmic_violtns = new Dialog(this, android.R.style.Theme_Black_NoTitleBar);
			dg_dynmic_violtns.setContentView(R.layout.dynamic_violations);

			TextView tv_sub_header = (TextView) dg_dynmic_violtns.findViewById(R.id.textView_header_spot_challan_xml);
			TextView tv_title = (TextView) dg_dynmic_violtns.findViewById(R.id.textView_title_header_dynmicvltns_xml);
			tv_title.setText("" + getResources().getString(R.string.select_violation));
			ll_dynamic_violations_root_static = (LinearLayout) dg_dynmic_violtns
					.findViewById(R.id.ll_dynamic_violations_xml);

			if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
				tv_sub_header.setText("" + getResources().getString(R.string.spot_challan));

			} else if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				tv_sub_header.setText("" + getResources().getString(R.string.towing_one_line));

			} /*
				 * else if
				 * (Dashboard.check_vhleHistory_or_Spot.equals("specialdrive"))
				 * { tv_sub_header.setText(""+
				 * getResources().getString(R.string.special_drive)); }
				 */

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);

			/* DYNAMIC LAYOUTS START */
			if (violation_offence_Code.size() > 0) {

				ll_dynamic_vltns = new LinearLayout[violation_offence_Code.size()];
				spinner_violation = new Spinner[ServiceHelper.violation_detailed_views.length];
				tv_dynamic_vltn_name = new TextView[ServiceHelper.violation_detailed_views.length];
				check_dynamic_vltn = new CheckBox[violation_offence_Code.size()];

				Log.i("Binding SIZE", "" + violation_offence_Code.size());

				for (int i = 0; i < violation_offence_Code.size(); i++) {
					Log.i("vio det view lendght ", "" + ServiceHelper.violation_detailed_views.length);
					String[] spinner_selectors = new String[3];

					/* TO SHOW IT IN SPINNER DROPDOWN */
					spinner_selectors[0] = "MIN :" + ServiceHelper.violation_detailed_views[i][3];
					spinner_selectors[1] = "AVG :" + ServiceHelper.violation_detailed_views[i][5];
					spinner_selectors[2] = "MAX :" + ServiceHelper.violation_detailed_views[i][4];

					Log.i("J", "" + j);

					ll_dynamic_vltns[i] = new LinearLayout(getApplicationContext());
					ll_dynamic_vltns[i].setOrientation(LinearLayout.HORIZONTAL);
					ll_dynamic_vltns[i].setLayoutParams(params);

					spinner_violation[i] = new Spinner(getApplicationContext());
					tv_dynamic_vltn_name[i] = new TextView(getApplicationContext());
					check_dynamic_vltn[i] = new CheckBox(getApplicationContext());

					LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(90,
							android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
					sp_params.setMargins(0, 15, 5, 15);

					spinner_violation[i].setId(Integer.parseInt(violation_offence_Code.get(i)));
					spinner_violation[i].setGravity(Gravity.CENTER_VERTICAL);

					ArrayAdapter<String> ap_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
							android.R.id.text1, spinner_selectors);
					ap_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner_violation[i].setAdapter(ap_adapter);

					// spinner_violation[i].setAdapter(new MyAdapter(this,
					// R.layout.spinner_item, spinner_selectors));

					spinner_violation[i].setBackgroundColor(R.drawable.navi_blue_btn_style);
					spinner_violation[i].setPopupBackgroundResource(R.drawable.navi_blue_btn_style);
					spinner_violation[i].setLayoutParams(sp_params);

					ll_dynamic_vltns[i].addView(spinner_violation[i]);
					Log.i("SPINNER IDS", "" + spinner_violation[i].getId());

					vioCodeDescMap.put("" + spinner_violation[i].getId(), ServiceHelper.violation_detailed_views[i][2]
							+ " ( " + ServiceHelper.violation_detailed_views[i][1] + " ) ");

					/* CHECKBOX START */
					int identifier = getResources().getIdentifier(
							getApplicationContext().getPackageName() + ":drawable/custom_chec_box", null, null);
					LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.FILL_PARENT);
					params1.weight = 1.0f;

					check_dynamic_vltn[i].setText("  " + ServiceHelper.violation_detailed_views[i][2] + " ( "
							+ ServiceHelper.violation_detailed_views[i][1] + " ) ");
					check_dynamic_vltn[i].setTextAppearance(getApplicationContext(), R.style.navi_text_style);

					check_dynamic_vltn[i].setId(Integer.parseInt(violation_offence_Code.get(i)));

					Log.i("CHECK ID AFTR", "" + check_dynamic_vltn[i].getId());

					check_dynamic_vltn[i].setButtonDrawable(identifier);

					check_dynamic_vltn[i].setLayoutParams(params1);

					/*// default 41(CP) enable for towing
					if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
						check_dynamic_vltn[0].setChecked(true);
						violation_checked_violations.add("" +check_dynamic_vltn[0]);
					}*/

					ll_dynamic_vltns[i].addView(check_dynamic_vltn[i]);
					
					/* CHECKBOX END */

					check_all_ids.put("" + i, "" + spinner_violation[i].getId());
					//
					// /* RADIO GROUP END */
					//
					ll_dynamic_violations_root_static.addView(ll_dynamic_vltns[i]);

					/* DYNAMIC RADIO BUTTONS CLICK EVENT END */
					/*----------------------------------------------------------*/

					check_dynamic_vltn[i].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							check = (CheckBox) v;

							// check.append("75");

							if (!et_driver_lcnce_num_spot.getText().toString().equalsIgnoreCase("")
									|| !et_driver_lcnce_num_spot.getText().toString().equalsIgnoreCase(null)) {

								if (et_driver_lcnce_num_spot.getText().length() >= 3) {

									if (check.getId() == 64 || check.getId() == 123) {
										ShowMessageDL(
												"\nWith out DL Section is not allowed when Offender had Driving License !\n");
									}

								}

							}

							if (check.isChecked()) {
								Log.i("checked val", "" + check.getId());
								violation_checked_violations.add("" + check.getId());
								if ((check.getId() == 7 || check.getId() == 07) || (check.getId() == 9)
										|| (check.getId() == 49)) {
									Intent extra_pasnger = new Intent(getApplicationContext(), ExtraPassengers.class);
									startActivity(extra_pasnger);
									Log.i("extraPasse ::", SpotChallan.extraPassengers);
								}
							} else {
								Log.i("check removed" + "", "" + check.getId());
								// }
								violation_checked_violations.remove("" + check.getId());
							}
							Log.i("violation_che", "" + violation_checked_violations.size());
						}
					});
				}
			} else {
				removeDialog(DYNAMIC_VIOLATIONS);
			}

			dg_dynmic_violtns.setOnKeyListener(new Dialog.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub

					if (KeyEvent.KEYCODE_BACK == keyCode) {

						for (String key : violation_checked_violations) {// 77,01,02
							// getting key for offence code from all
							for (Entry<String, String> entry : check_all_ids.entrySet()) {// all
								// key:index
								// ;
								// value
								// :
								if (entry.getValue().equals(key)) {
									Log.i(" offence code",
											"" + key + "--index --" + (Integer.parseInt(entry.getKey())));
									String selectedId = spinner_violation[Integer.parseInt(entry.getKey())]
											.getSelectedItem().toString();

									Log.i("SPINNER selectedId", "" + selectedId.substring(5, selectedId.length()));
									DBHelper db1 = new DBHelper(SpotChallan.this);
									try {
										Log.i("SETTINGS_TABLE ", "Entered");
										String query = "select * from " + DBHelper.violationPointsTable + "  ";
										db1.open();
										Cursor cursor = DBHelper.db.rawQuery(query, null);
										boolean validPid = false;

										if (cursor.moveToFirst()) {
											validPid = true;

											do {
												String offence_vptable = cursor.getString(1);
												String wheelerCOde = cursor.getString(2);
												String penalityPoints = cursor.getString(3);

												Log.i("1 Offence COde:", offence_vptable);
												Log.i("2 Wheeler Type:", wheelerCOde);
												Log.i("3 Penality Point:", penalityPoints);

												Log.i("key  :", key);
												Log.i("wheeler cd :", whlr_code_send);

												if (!et_driver_lcnce_num_spot.getText().toString().trim().equals("")
														&& offence_vptable.equals(key)
														&& wheelerCOde.equals(whlr_code_send) && !dobFLG) {

													dobFLG = true;
													Intent dob = new Intent(SpotChallan.this, DateOfBirth_Update.class);
													startActivity(dob);
												}
											} while (cursor.moveToNext());
										}

									} catch (Exception e) {
										e.printStackTrace();
										// showToast(e.getMessage());
									} finally {
										db1.close();
									}

									if (("7".equals(key) || "07".equals(key))
											|| ("9".equals(key) || "09".equals(key))) {
										passngerFLG = true;

										Log.i("Integer.parseInt(ext:",
												extraPassengers + " is :" + Integer.parseInt(extraPassengers));
										Log.i("Integer.parseI:", extraPassengers + " is :"
												+ (Integer.parseInt(selectedId.substring(5, selectedId.length()))));
										Log.i("grand_total ::::", "" + grand_total);

										/*
										 * grand_total = grand_total +
										 * ((Integer.parseInt(selectedId.
										 * substring(5, selectedId.length()))
										 * Integer.parseInt(extraPassengers)));
										 */

										grand_total = grand_total
												+ ((Integer.parseInt(selectedId.substring(5, selectedId.length()))))
												+ (Integer.parseInt(extraPassengers) * 100);

										Log.i("Grand  ::", grand_total + "");

										// FOR DISPLAYING TOTAL CALCULATED
										// AMOUNT
										// SpotChallan.total = 0.0;
										// SpotChallan.total = grand_total +
										// total_amount;
										Log.i("VehicleHis:",
												"" + VehicleHistoryPendingChallans.total_amount_selected_challans);

										total = 0.0;
										total = grand_total
												+ VehicleHistoryPendingChallans.total_amount_selected_challans;
										tv_violation_amnt.setText("Rs . " + grand_total);
										tv_grand_total_spot.setText("Rs . " + total);

									} else {
										passngerFLG = false;

										grand_total = grand_total
												+ (Integer.parseInt(selectedId.substring(5, selectedId.length())));// total
										Log.i("grand_:",
												"Value OF g " + grand_total);

										// FOR DISPLAYING TOTAL CALCULATED
										// AMOUNT
										total = 0.0;
										total = grand_total
												+ VehicleHistoryPendingChallans.total_amount_selected_challans;
										Log.i("total Value o:", "Value OF total::: " + total);

										tv_violation_amnt.setText("Rs . " + grand_total);
										tv_grand_total_spot.setText("Rs . " + total);
									}

									/* framing violation buffer */

									violations_details_send.append(
											key.trim() + "@" + selectedId.substring(5, selectedId.length()).trim() + "@"
													+ selectedId.substring(5, selectedId.length()).trim() + "@");

									for (String offenceCodes : vioCodeDescMap.keySet()) {
										if (offenceCodes.trim().equals(key.trim())) {
											violations_details_send
													.append("" + vioCodeDescMap.get(offenceCodes.trim()).trim());

											violation_desc_append
													.append("" + vioCodeDescMap.get(offenceCodes.trim()).trim());
											violation_desc_append.append(",");
										}
									}
									violations_details_send.append("!");
									btn_violation.setText(violation_desc_append);
									Log.i("violatient ", violations_details_send.toString().trim());
								}
							}
						}
						removeDialog(DYNAMIC_VIOLATIONS);
						/* NO OF PEOPLE CALCULATING */
						removeDialog(DYNAMIC_VIOLATIONS);

						/*---------TO ENABLE EDITEXT WHEN EXTRA PASSENGERS : 07 IS SELECTED---------*/
						int status = 0;
						for (int i = 0; i < violation_checked_violations.size(); i++) {
							Log.i("FINAL ", "" + violation_checked_violations.get(i));
							violation_code_value = violation_checked_violations.get(i);
							// String ccc =
							// ""+violationPoints_offnce_code[count];
							if ((violation_checked_violations.get(i).toString().equals("7"))) {
								status = 1;
							}
						}
						if (status == 1) {
							ll_extra_people.setVisibility(View.GONE);
						} else {
							ll_extra_people.setVisibility(View.GONE);
						}
					}
					return true;
				}
			});
			/* DYNAMIC LAYOUTS END */
			return dg_dynmic_violtns;

		/******************************
		 * REMARKS DIALOG
		 ********************************/
		case FAKE_NUMBERPLATE_DIALOG:

			// AP09BR7265 theft
			// AP21J4081 Fake
			message = new StringBuffer();

			Log.i("*****1 :::", "" + "A :" + aadhr_point_frm_json + "D :" + dl_point_frm_json);

			if (aadhr_point_frm_json != null && dl_point_frm_json != null && !aadhr_point_frm_json.equals("NA")
					&& !dl_point_frm_json.equals("NA")) {

				Log.i("*****1 :::", "" + "A :" + aadhr_point_frm_json + "D :" + dl_point_frm_json);
				message.append("VIOLATION BASED POINTS \n");
				message.append("------------------------------\n");
				message.append("AADHAR POINTS \t: " + aadhr_point_frm_json + "\n");
				message.append("OFFENCE YEAR \t: " + aadhaar_offence_year + "\n\n");
				message.append("DL POINTS \t: " + dl_point_frm_json + "\n");
				message.append("OFFENCE YEAR \t: " + dl_offence_year + "\n\n");
				message.append("" + rta_details_spot_master[10]);

			} else if (aadhr_point_frm_json != null && !aadhr_point_frm_json.equals("NA")) {
				Log.i("*****2 :::", "" + aadhr_point_frm_json);
				message.append("VIOLATION BASED POINTS \n");
				message.append("------------------------------\n");
				message.append("AADHAR POINTS \t: " + aadhr_point_frm_json + "\n");
				message.append("OFFENCE YEAR \t: " + aadhaar_offence_year + "\n\n");
				message.append("" + rta_details_spot_master[10]);

			} else if (dl_point_frm_json != null && !dl_point_frm_json.equals("NA")) {
				// message.append("VIOLATION BASED POINTS \n");
				Log.i("*****3 :::", "" + dl_point_frm_json);
				message.append("VIOLATION BASED POINTS \n");
				message.append("------------------------------\n");
				message.append("DL POINTS \t: " + dl_point_frm_json + "\n");
				message.append("OFFENCE YEAR \t: " + dl_offence_year + "\n\n");
				message.append("" + rta_details_spot_master[10]);

			} else {
				Log.i("*****4 :::", "" + aadhr_point_frm_json + dl_point_frm_json);
				message.append("" + offender_remarks_resp_master[10]);
			}
			// message.append(""+rta_details_spot_master[10]);

			Log.i("rta_detar[1]::::", "" + rta_details_spot_master[1]);
			Log.i("rta_detar[2]::::", "" + rta_details_spot_master[2]);
			Log.i("rta_detailr[3]::::", "" + rta_details_spot_master[3]);
			Log.i("rta_detair[4]::::", "" + rta_details_spot_master[4]);
			Log.i("rta_detair[5]::::", "" + rta_details_spot_master[5]);
			Log.i("rta_detr[6]::::", "" + rta_details_spot_master[6]);
			Log.i("rta_deer[7]::::", "" + rta_details_spot_master[7]);
			Log.i("rta_detaier[8]::::", "" + rta_details_spot_master[8]);
			Log.i("rta_detaiter[9]::::", "" + rta_details_spot_master[9]);

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
			alertDialogBuilder.setMessage(message);
			alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					removeDialog(FAKE_NUMBERPLATE_DIALOG);
					/*
					 * if (ServiceHelper.offender_remarks!=null) {
					 * 
					 * Log.i("fake_veh_chasisNo  ::::", fake_veh_chasisNo);
					 * 
					 * fake_veh_chasisNo = ( rta_details_spot_master.length>0 &&
					 * rta_details_spot_master[8]!=null &&
					 * !"".equals(rta_details_spot_master[8].trim()))?
					 * rta_details_spot_master[8].substring(
					 * rta_details_spot_master[8].length()-5):"";
					 * 
					 * Log.i("Spot ***fake_veh_chasisNo :::",
					 * ""+fake_veh_chasisNo); Intent intent = new
					 * Intent(getApplicationContext(), Fake_NO_Dialog.class);
					 * startActivity(intent); }
					 */
					if (offender_remarks_resp_master[10] != null
							&& offender_remarks_resp_master[10].contains("FAKE NO")) {

						Log.i("FAke  ::::", "Yes it's Fake");
						Log.i("fake_veh_chasisNo  ::::", fake_veh_chasisNo);
						fake_veh_chasisNo = (offender_remarks_resp_master.length > 0
								&& offender_remarks_resp_master[8] != null
								&& !"".equals(offender_remarks_resp_master[8].trim()))
										? offender_remarks_resp_master[8]
												.substring(offender_remarks_resp_master[8].length() - 5)
										: "";
						Log.i("Spot ***fake_v:", "" + fake_veh_chasisNo);
						Intent intent = new Intent(getApplicationContext(), Fake_NO_Dialog.class);
						intent.putExtra("Flagkey", "S");
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

		case OTP_CNFRMTN_DIALOG:

			String otp_message = "\n" + otp_msg + "\n";

			TextView title3 = new TextView(this);
			title3.setText("ALERT");
			title3.setBackgroundColor(Color.RED);
			title3.setGravity(Gravity.CENTER);
			title3.setTextColor(Color.WHITE);
			title3.setTextSize(26);
			title3.setTypeface(title3.getTypeface(), Typeface.BOLD);
			title3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
			title3.setPadding(20, 0, 20, 0);
			title3.setHeight(70);

			AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			alertDialog_Builder.setCustomTitle(title3);
			alertDialog_Builder.setIcon(R.drawable.dialog_logo);
			alertDialog_Builder.setMessage(otp_message);
			alertDialog_Builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					removeDialog(OTP_CNFRMTN_DIALOG);
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
			return alert_Dialog;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	// private OnClickListener onRadioButtonClick(int ids) {
	// // TODO Auto-generated method stub
	//
	// switch (ids) {
	// case value:
	//
	// break;
	//
	// default:
	// break;
	// }
	// return null;
	// }

	/*
	 * public String sendOTP(String regn_no,String mobileNo,String date); public
	 * String verifyOTP(String regn_no,String mobileNo,String date, String
	 * otp,String verify_status);
	 */

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

	/*---------------SEND OTP TO MOBILE start---------*/
	public class Async_sendOTP_to_mobile extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("OTP Status", "" + otp_status);
			otp_status = "send";

			date_format2 = new SimpleDateFormat("dd-MMM-yyyy");
			present_date_toSend = date_format2.format(new Date(present_year - 1900, present_month, present_date));

			ServiceHelper.sendOTPtoMobile(completeVehicle_num_send, et_driver_contact_spot.getText().toString().trim(),
					"" + getDate().toUpperCase());
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			Log.i("OTP Status in Post", "" + otp_status);
			otp_msg = "";
			otpValue = "";
			if (otp_status.equals("send")) {
				if (ServiceHelper.Opdata_Chalana.toLowerCase().equals("na")) {
					// ll_confrim_otp.setVisibility(View.GONE);
					// et_confirm_otp.setText("");
				} else {
					showToast("OTP is sent to your mobile number");
					otpValue = "" + ServiceHelper.Opdata_Chalana;
					Log.i("****OTP VALUE******", "" + ServiceHelper.Opdata_Chalana);
					// ll_confrim_otp.setVisibility(View.GONE);
					// et_confirm_otp.setText("");

					Intent dialogbox = new Intent(getApplicationContext(), OTP_input.class);
					dialogbox.putExtra("regNO", completeVehicle_num_send);
					dialogbox.putExtra("MobileNo", et_driver_contact_spot.getText().toString().trim());
					dialogbox.putExtra("otp_date", "" + getDate().toUpperCase());
					dialogbox.putExtra("OTP_value", otpValue);

					startActivity(dialogbox);
				}
			}
		}
	}

	public class Async_VerifyOTP extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("OTP Status", "" + otp_status);
			otp_status = "verify";

			/*
			 * ServiceHelper.verifyOTP(ServiceHelper.
			 * VERIFY_OTP_FROM_MOBILE_METHOD_NAME, "regn_no", "mobileNo",
			 * "date", "otp", "verify_status", "" + completeVehicle_num_send,
			 * ""+ et_driver_contact_spot.getText().toString(), "" +
			 * getDate().toUpperCase(), ""+ otp_number, ""+
			 * vStatusConfirmationYN);
			 */

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			Log.i("OTP Status in Post", "" + otp_status);

			if (otp_status.equals("verify")) {

				if ((ServiceHelper.Opdata_Chalana.toLowerCase().equals("0"))
						|| (ServiceHelper.Opdata_Chalana.toLowerCase().equals("na"))) {

					otp_msg = "Entered OTP is wrong,\nPlease Click on SendOTP once Again ";
					otp_status = "send";
					removeDialog(OTP_CNFRMTN_DIALOG);
					showDialog(OTP_CNFRMTN_DIALOG);
					otp_popup();

				} else {
					otp_msg = "OTP is verified";
					removeDialog(OTP_CNFRMTN_DIALOG);
					showDialog(OTP_CNFRMTN_DIALOG);
				}
			}
		}
	}

	public static String getDate() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		System.out.println(today.month);
		return today.monthDay + "-" + getMonthName(today.month) + "-" + today.year;
	}

	public void otp_popup() {
		// TODO Auto-generated method stub
		LayoutInflater li = LayoutInflater.from(getApplicationContext());
		View promptsView = li.inflate(R.layout.activity_otp_input, null);

		final EditText userInput = (EditText) promptsView.findViewById(R.id.otp_input);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
				AlertDialog.THEME_HOLO_LIGHT);
		// alertDialogBuilder.setCustomTitle(title);
		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setIcon(R.drawable.dialog_logo);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (userInput.getText().toString().equals("")) {
					showToast("Please Enter OTP");
				} else {

					Log.i("UserInput ::::", "" + userInput.getText().toString());
					Log.i("otpValue ::::", "" + otpValue);

					if (userInput.getText().toString().equals("" + otpValue)) {
						otp_number = userInput.getText().toString();
						vStatusConfirmationYN = "Y";
						if (isOnline()) {
							Log.i("***OTP CONFIRMA", "" + vStatusConfirmationYN);
							otp_status = "verify";
							new Async_VerifyOTP().execute();
						} else {
							showToast("Please check your network connection!");
						}
					} else {
						vStatusConfirmationYN = "N";
						if (isOnline()) {
							Log.i("***OTP COED", "" + vStatusConfirmationYN);
							otp_status = "verify";
							new Async_VerifyOTP().execute();
						} else {
							showToast("Please check your network connection!");
						}
					}
				}
				/*
				 * else if (userInput.getText().toString().equals("" +
				 * otpValue)) { otp_number = userInput.getText().toString();
				 * 
				 * vStatusConfirmationYN = "Y"; if (isOnline()) {
				 * Log.i("***OTP CONFIRMATION ENTERED", "" +
				 * vStatusConfirmationYN); otp_status = "verify"; new
				 * Async_sendOTP_to_mobile().execute(); } else {
				 * showToast("Please check your network connection!"); } } else
				 * { vStatusConfirmationYN = "N"; if (isOnline()) {
				 * Log.i("***OTP CONFIRMATION ENTERED", "" +
				 * vStatusConfirmationYN); otp_status = "verify"; new
				 * Async_sendOTP_to_mobile().execute(); } else {
				 * showToast("Please check your network connection!"); } }
				 */
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		alertDialog.getWindow().getAttributes();

		Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
		btn.setTextSize(22);
		btn.setTextColor(Color.WHITE);
		btn.setTypeface(btn.getTypeface(), Typeface.BOLD);
		btn.setBackgroundColor(Color.RED);
	}

	public static String getMonthName(int month) {

		switch (month + 1) {

		case 1:
			return "Jan";

		case 2:
			return "Feb";

		case 3:
			return "Mar";

		case 4:
			return "Apr";

		case 5:
			return "May";

		case 6:
			return "Jun";

		case 7:
			return "Jul";

		case 8:
			return "Aug";

		case 9:
			return "Sep";

		case 10:
			return "Oct";

		case 11:
			return "Nov";

		case 12:
			return "Dec";
		}

		return "";
	}

	/*---------------------------------------------------------------*/

	public class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context ctx, int txtViewResourceId, String[] objects) {
			super(ctx, txtViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.spinner_item, parent, false);
			// TextView main_text = (TextView)
			// mySpinner.findViewById(R.id.text1);
			// main_text.setText("" + spinner_selectors[position]);
			return mySpinner;
		}
	}

	/* DETAINED ITEMS LISTENERS */
	protected void getDateAndTime() {
		// TODO Auto-generated method stub
		calendar = Calendar.getInstance();

		present_date = calendar.get(Calendar.DAY_OF_MONTH);
		present_month = calendar.get(Calendar.MONTH);
		present_year = calendar.get(Calendar.YEAR);

		date_format = new SimpleDateFormat("dd-MMM-yyyy");//

		present_date_toSend = date_format.format(new Date(present_year - 1900, present_month, present_date));
		Log.i("**PRESENT DATE****", "" + present_date_toSend.toUpperCase());

		/* TIME START */

		present_hour = calendar.get(Calendar.HOUR_OF_DAY);
		present_minutes = calendar.get(Calendar.MINUTE);

		present_time_toSend = new StringBuffer();
		present_time_toSend.delete(0, present_time_toSend.length());

		if (present_hour < 10) {
			present_time_toSend.append("0").append(present_hour);
		} else {
			present_time_toSend.append(present_hour);
		}
		present_time_toSend.append(":");

		if (present_minutes < 10) {
			present_time_toSend.append("0").append(present_minutes);
		} else {
			present_time_toSend.append(present_minutes);
		}
		/* TIME END */
		Log.i("**PRESENT TIME****", "" + present_time_toSend.toString().trim().toUpperCase());
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {

		case R.id.checkBox_dt_rc_spotchallantwo_xml:
			if (isChecked) {
				chck_detainedItems_rc.setChecked(true);
			} else {
				chck_detainedItems_rc.setChecked(false);
			}
			break;

		case R.id.checkBox_dt_vchle_spotchallantwo_xml:
			if (isChecked) {
				chck_detainedItems_vhcle.setChecked(true);
			} else {
				chck_detainedItems_vhcle.setChecked(false);
			}
			break;

		case R.id.checkBox_dt_lcns_spotchallantwo_xml:
			if (isChecked) {
				chck_detainedItems_licence.setChecked(true);
			} else {
				chck_detainedItems_licence.setChecked(false);
			}
			break;

		case R.id.checkBox_dt_permit_spotchallantwo_xml:
			if (isChecked) {
				chck_detainedItems_permit.setChecked(true);
			} else {
				chck_detainedItems_permit.setChecked(false);
			}
			break;

		case R.id.checkBox_dt_none_spotchallantwo_xml:
			if (isChecked) {
				setCheckedValues(false, "donotedit");
				chck_detainedItems_none.setChecked(true);
			} else {
				setCheckedValues(true, "canedit");
				chck_detainedItems_none.setChecked(false);
			}
			break;
		}
	}

	/* TO SET NON-EDITABLE AND TO CLEAR */
	private void setCheckedValues(boolean b_val, String edit_val) {
		// TODO Auto-generated method stub

		chck_detainedItems_rc.setEnabled(b_val);
		chck_detainedItems_vhcle.setEnabled(b_val);
		chck_detainedItems_licence.setEnabled(b_val);
		chck_detainedItems_permit.setEnabled(b_val);

		if (edit_val.equals("donotedit")) {
			chck_detainedItems_rc.setChecked(b_val);
			chck_detainedItems_vhcle.setChecked(b_val);
			chck_detainedItems_licence.setChecked(b_val);
			chck_detainedItems_permit.setChecked(b_val);
		}
	}

	/* SERVICE CALL TO MOBILE SPOT PAYMENT */
	public class Async_spot_challan extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String penchallans = "";
			if (ServiceHelper.pending_challans_details.length == 0 && total_amount == 0) {
				penchallans = "0@0.0";
			} else {
				penchallans = ServiceHelper.pending_challans_details.length + "@" + total_amount;
			}

			Log.i("EXTRA PASSENGET", "" + extraPassengers);
			Log.i("pendingchallans", "" + penchallans);
			Log.i("is_govt_police   ", "" + is_govt_police);
			String city = et_driver_city_iOD.getText().toString().trim();
			Log.i("city_dakota :::", "" + city);

			if (radioGroupButton_spotpaymentYes.isChecked() && !cardFLG
					&& !Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				is_it_spot_send = "1";
				Log.i("is_it_spotse:::", "" + is_it_spot_send);

			} else if (radioGroupButton_spotpaymentYes.isChecked() && cardFLG
					&& !Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				is_it_spot_send = "0";
				Log.i("is_it_spot_send 1:::", "" + is_it_spot_send);

			} else if (radioGroupButton_spotpaymentYes.isChecked()
					&& Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				is_it_spot_send = "1";
				Log.i("is_it_spot_send  2:::", "" + is_it_spot_send);

			} else if (radioGroupButton_spotpaymentNo.isChecked()) {
				is_it_spot_send = "0";
				Log.i("is_it_spot_send 3:::", "" + is_it_spot_send);
			}

			String dob_DL = null;
			if (dobFLG && !DateOfBirth_Update.dob_input.getText().toString().trim().equals("")) {
				dob_DL = DateOfBirth_Update.dob_input.getText().toString().trim();
			} else {
				dobFLG = false;
				dob_DL = null;
			}

			SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String psCd = sharedPreference.getString("PS_CODE", "");
			String psName = sharedPreference.getString("PS_NAME", "");
			String pidCd = sharedPreference.getString("PID_CODE", "");
			String pidName = sharedPreference.getString("PID_NAME", "");
			String cadre = sharedPreference.getString("CADRE_NAME", "");
			String cadreCd = sharedPreference.getString("CADRE_CODE", "");

			String pswd = sharedPreference.getString("PASS_WORD", "");

			try {
				pswd = PidSecEncrypt.encryptmd5(pswd);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String aadhar_no = et_aadharnumber_spot.getText().toString().trim() != null
					&& et_aadharnumber_spot.getText().toString().length() > 0
							? et_aadharnumber_spot.getText().toString() : "NA";
			String Licnce_no = et_driver_lcnce_num_spot.getText().toString().trim() != null
					&& et_driver_lcnce_num_spot.getText().toString().length() > 0
							? et_driver_lcnce_num_spot.getText().toString() : "NA";

			Log.i("Aadhaar No :::::", "Aaaaaaaaaaaa :::" + aadhar_no + "--LIC No" + Licnce_no);

			System.out.println("final_image_data_to_send madhusudhan \n :" + final_image_data_tosend);// 33
			ServiceHelper.mobileSpotChallanPayingNew15(
					sb_selected_penlist_send.toString() != null && sb_selected_penlist_send.toString().length() > 0
							? sb_selected_penlist_send.toString() : "NA",
					"" + regncode_send, "" + vehicle_num_send, "" + completeVehicle_num_send, "",
					"" + present_date_toSend.toUpperCase(), "" + present_time_toSend, "" + Dashboard.UNIT_CODE,
					"" + Dashboard.UNIT_NAME, "" + psCd, "" + psName, "" + bookedPScode_send_from_settings,
					"" + bookedPSname_send_from_settings, "" + point_code_send_from_settings,
					"" + point_name_send_from_settings, "" + pidCd, "" + pidName, "" + pidCd, "" + pidName,
					"" + cadreCd, "" + cadre, "" + grand_total, "" + sb_detained_items.toString().trim(),
					"" + simid_send, "" + imei_send, "" + macAddress, "" + latitude, "" + longitude,
					"" + present_date_toSend.toUpperCase(), "" + present_time_toSend, "" + ONLINE_MODE_FIX,
					"" + MODULE_CODE_FIX, "" + releasedDetained_items_list_toSend, "" + CHALLAN_NUM_FIX,
					"" + SERVICE_CODE_FIX, "", "" + Licnce_no, "" + pswd, "" + final_image_data_tosend, "", "",
					"" + exact_location_send_from_settings, "" + et_remarks_spot.getText().toString(),
					"" + pancard_to_send, "" + aadhar_no, "" + VoterId_to_send, "" + passport_to_send,
					"" + emailId_to_send, "" + et_driver_contact_spot.getText().toString(), "" + is_it_spot_send,
					"" + present_date_toSend.toUpperCase(), "" + present_time_toSend, "" + licStatus_send,
					"" + whlr_code_send, "" + Dashboard.VEH_CAT_FIX, "" + Dashboard.VEH_MAINCAT_FIX,
					"" + Dashboard.VEH_SUBCAT_FIX, "" + violations_details_send, "" + penchallans, "" + extraPassengers,
					"" + et_drivername_iOD.getText().toString().trim(),
					"" + et_driverFatherName_iOD.getText().toString().trim(),
					"" + et_driver_address_iOD.getText().toString().trim(), "" + city, "" + is_govt_police,
					"" + dob_DL);
			return null;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);

			SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			pending_challans_amount = sharedPreference.getString("PENDING_AMNT", "");

			Log.i("pennt :::", "" + pending_challans_amount);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// Log.i("**SPOT TICKET REPONSE in arr[6]**", "arr[6]---"+
			// ServiceHelper.final_spot_reponse_master[6]);

			if (ServiceHelper.spot_final_res_status != null) {
				Log.i("*arr[0]**", "arr[0]---" + ServiceHelper.final_spot_reponse_master[0]);
				Log.i("**SPOT T[1]**", "arr[1]---" + ServiceHelper.final_spot_reponse_master[1]);
				Log.i("**SPOT TICarr[2]**", "arr[2]---" + ServiceHelper.final_spot_reponse_master[2]);
				if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("0"))) {
					removeDialog(PROGRESS_DIALOG);
					showToast("Ticket Generation Failed!");
					Log.i("**SPOTrr[2]**",
							"arr[2]---" + ServiceHelper.final_spot_reponse_master[2]);

				} else if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("1"))) {
					removeDialog(PROGRESS_DIALOG);
					ShowMessage("\nTicket Generation Failed\n Already Detained :"
							+ ServiceHelper.final_spot_reponse_master[1].toString().trim() + "\n");
					Log.i("**SPOT TI arr[2]**",
							"arr[2]---" + ServiceHelper.final_spot_reponse_master[2]);

				} else if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("2"))) {
					removeDialog(PROGRESS_DIALOG);
					ShowMessage("\nTicket Generation Failed\n Already Generated :"
							+ ServiceHelper.final_spot_reponse_master[1].toString().trim() + "\n");
					Log.i("**SPOT TIC arr[2]**",
							"arr[2]---" + ServiceHelper.final_spot_reponse_master[2]);

				} else {
					Log.i("POST SE ", "" + ServiceHelper.final_spot_reponse_master.length);
					Log.i("PICTURE PATH FROM SPOT", "" + picturePath);
					/*
					 * if ((!picturePath.toString().trim().equals("")) &&
					 * (!ServiceHelper.final_spot_reponse_details[12].toString()
					 * .trim().equals("NA"))) { //uploadFile(picturePath); }
					 * else {
					 */
					removeDialog(PROGRESS_DIALOG);
					removeDialog(SECOND_SPOTSCREEN_DIALOG);

					Log.i("cardFLG ::::", "" + cardFLG);
					if (ServiceHelper.final_spot_reponse_master.length > 0) {
						Log.i("ServiceHelper.fi::::",
								"" + ServiceHelper.final_spot_reponse_master.length);

						btn_otp_flag = "0";

						if (cardFLG) {
							/*
							 * Intent popup = new
							 * Intent(getApplicationContext(),
							 * PopupDetails.class); startActivity(popup);
							 */
							// startActivity(new Intent(SpotChallan.this,
							// Respone_Print.class));
							// sb_selected_penlist_send.toString()
							String spotChallan = "";
							if (ServiceHelper.final_spot_reponse_details[3].length() > 0) {
								spotChallan = ServiceHelper.final_spot_reponse_details[3];
							}

							SharedPreferences sharedPreferences = PreferenceManager
									.getDefaultSharedPreferences(getApplicationContext());
							SharedPreferences.Editor editors = sharedPreferences.edit();

							String unit_name = "" + Dashboard.UNIT_NAME;
							String unit_code = "" + Dashboard.UNIT_CODE;

							Log.i("pending_c-->:::", "" + sb_selected_penlist_send.toString());

							String pending_challans = "" + ((sb_selected_penlist_send.toString().length() > 0
									&& sb_selected_penlist_send.toString() != null)
											? sb_selected_penlist_send.toString() : "");
							// (et_chasisNo.getText().toString().trim().length()>0
							// &&
							// et_chasisNo.getText().toString()!=null)?et_chasisNo.getText().toString():"NA";
							String pending_challans_amnt = "" + pending_challans_amount;

							/*
							 * Log.i("unit_name in Spot ---->1 ", ""+unit_name);
							 * Log.i("unit_code in Spot ---->1 ", ""+unit_code);
							 */
							Log.i("Chal---->1 ", "" + spotChallan + "$" + pending_challans);
							Log.i("pending_ct --->1:::", "" + sb_selected_penlist_send.toString());
							Log.i("pendin->1:::", "" + pending_challans);

							// Pending Challan Format
							// !@HYD00EC161000020@23@100@35@135 $
							// !@HYD00SC141021960@23@400@35@435 $
							// !@HYD00EC161000020@23@100@0@100$
							// ticketno@unitcode@acmd_amnt@usercharges@cmd_amnt

							// !@HYD00TE161000288@23@650.0@0@650.0$
							editors.putString("ALL_CHALLANS", "" + spotChallan + "$" + pending_challans);
							editors.putString("UNIT_NAME", "" + unit_name);
							editors.putString("UNIT_CODE", "" + unit_code);
							editors.putString("TICKET_DETAILS", "" + spotChallan);
							editors.putString("PENDING_CHALLAN_AMNT", "" + pending_challans_amnt);
							editors.putString("PENDING_CHALLANS", "" + pending_challans);
							editors.commit();

							Intent print = new Intent(getApplicationContext(), Respone_Print.class);
							startActivity(print);
							finish();

						} else if (!cardFLG) {

							/*
							 * Intent popup = new
							 * Intent(getApplicationContext(),
							 * PopupDetails.class);
							 * 
							 * startActivity(popup);
							 */

							// startActivity(new Intent(SpotChallan.this,
							// Respone_Print.class));

							// sb_selected_penlist_send.toString()

							String spotChallan = "";
							if (!Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
								if (ServiceHelper.final_spot_reponse_details[3].length() > 0) {
									spotChallan = ServiceHelper.final_spot_reponse_details[3];
								}

								SharedPreferences sharedPreferences = PreferenceManager
										.getDefaultSharedPreferences(getApplicationContext());
								SharedPreferences.Editor editors = sharedPreferences.edit();

								String unit_name = "" + Dashboard.UNIT_NAME;
								String unit_code = "" + Dashboard.UNIT_CODE;
								String pending_challans = "" + ((sb_selected_penlist_send.toString().length() > 0
										&& sb_selected_penlist_send.toString() != null)
												? sb_selected_penlist_send.toString() : "");
								// (et_chasisNo.getText().toString().trim().length()>0
								// &&
								// et_chasisNo.getText().toString()!=null)?et_chasisNo.getText().toString():"NA";
								String pending_challans_amnt = "" + pending_challans_amount;

								/*
								 * Log.i("unit_name in Spot ---->1 ",
								 * ""+unit_name);
								 * Log.i("unit_code in Spot ---->1 ",
								 * ""+unit_code);
								 */
								Log.i("Cha--->2 ", "" + spotChallan + "$" + pending_challans);
								Log.i("pending_--->2:::", "" + sb_selected_penlist_send.toString());
								Log.i("pe--->2:::", "" + pending_challans);

								// Pending Challan Format
								// !@HYD00EC161000020@23@100@35@135 $
								// !@HYD00SC141021960@23@400@35@435 $
								// !@HYD00EC161000020@23@100@0@100$
								// ticketno@unitcode@acmd_amnt@usercharges@cmd_amnt

								editors.putString("ALL_CHALLANS", "" + spotChallan + "$" + pending_challans);
								editors.putString("UNIT_NAME", "" + unit_name);
								editors.putString("UNIT_CODE", "" + unit_code);
								editors.putString("TICKET_DETAILS", "" + spotChallan);
								editors.putString("PENDING_CHALLAN_AMNT", "" + pending_challans_amnt);
								editors.putString("PENDING_CHALLANS", "" + pending_challans);
								editors.commit();

								Intent print = new Intent(getApplicationContext(), Respone_Print.class);
								startActivity(print);
							} else {
								Intent print = new Intent(getApplicationContext(), Respone_Print.class);
								startActivity(print);
								finish();
							}
						} /*
							 * else { startActivity(new Intent(SpotChallan.this,
							 * Respone_Print.class)); finish(); }
							 */
					}
					// }
				}
			} else {
				removeDialog(PROGRESS_DIALOG);
				showToast("Error");
			}
		}
	}

	/* SERVICE CALL TO MOBILE SPOT PAYMENT */
	public class Async_spot_challan_1_5_2 extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			String penchallans = "";
			if ((ServiceHelper.pending_challans_details.length == 0) && (total_amount == 0)) {
				penchallans = "0@0.0";
			} else {
				penchallans = ServiceHelper.pending_challans_details.length + "@" + total_amount;
			}

			/*
			 * //extraPassengers = ""; if
			 * (et_extra_people.getText().toString().equals("1")) {
			 * extraPassengers = "1"; } else { extraPassengers = "" +
			 * ExtraPassengers.passngrCount_input.getText().toString().trim(); }
			 */
			Log.i("EXTRA PASSENG", "" + extraPassengers);
			Log.i("pendingchallans", "" + penchallans);
			Log.i("is_govt_police   ", "" + is_govt_police);
			String city = et_driver_city_iOD.getText().toString().trim();
			Log.i("city_dakota :::", "" + city);

			ServiceHelper.mobileSpotChallanPayingNew1_5_2(sb_selected_penlist_send.toString(), "" + regncode_send,
					"" + vehicle_num_send, "" + completeVehicle_num_send, "", "" + present_date_toSend.toUpperCase(),
					"" + present_time_toSend, "" + Dashboard.UNIT_CODE, "" + Dashboard.UNIT_NAME,
					"" + MainActivity.arr_logindetails[2].toString().trim(),
					"" + MainActivity.arr_logindetails[3].toString().trim(), "" + bookedPScode_send_from_settings,
					"" + bookedPSname_send_from_settings, "" + point_code_send_from_settings,
					"" + point_name_send_from_settings, "" + MainActivity.arr_logindetails[0].toString().trim(),
					"" + MainActivity.arr_logindetails[1].toString().trim(),
					"" + MainActivity.arr_logindetails[0].toString().trim(),
					"" + MainActivity.arr_logindetails[1].toString().trim(),
					"" + MainActivity.arr_logindetails[4].toString().trim(),
					"" + MainActivity.arr_logindetails[5].toString().trim(), "" + grand_total,
					"" + sb_detained_items.toString().trim(), "" + simid_send, "" + imei_send, "" + macAddress,
					"" + latitude, "" + longitude, "" + present_date_toSend.toUpperCase(), "" + present_time_toSend,
					"" + ONLINE_MODE_FIX, "" + MODULE_CODE_FIX, "" + releasedDetained_items_list_toSend,
					"" + CHALLAN_NUM_FIX, "" + SERVICE_CODE_FIX, "", "" + et_driver_lcnce_num_spot.getText().toString(),
					"", "" + final_image_data_tosend, "", "", "" + exact_location_send_from_settings,
					"" + et_remarks_spot.getText().toString(), "" + pancard_to_send,
					"" + et_aadharnumber_spot.getText().toString(), "" + VoterId_to_send, "" + passport_to_send,
					"" + emailId_to_send, "" + et_driver_contact_spot.getText().toString(), "" + is_it_spot_send,
					"" + present_date_toSend.toUpperCase(), "" + present_time_toSend, "" + licStatus_send,
					"" + whlr_code_send, "" + Dashboard.VEH_CAT_FIX, "" + Dashboard.VEH_MAINCAT_FIX,
					"" + Dashboard.VEH_SUBCAT_FIX, "" + violations_details_send, "" + penchallans, "" + extraPassengers,
					"" + et_drivername_iOD.getText().toString().trim(),
					"" + et_driverFatherName_iOD.getText().toString().trim(),
					"" + et_driver_address_iOD.getText().toString().trim(), "" + city, "" + is_govt_police,
					"" + edt_prfession_name.getText().toString().trim(),
					"" + edt_prfession_Address.getText().toString().trim(),
					"" + edt_email_ID.getText().toString().trim());
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Log.i("**onPostExecuE**", "arr[0]---" + ServiceHelper.final_spot_reponse_master[0]);
			Log.i("**onPostE REPONSE**", "arr[1]---" + ServiceHelper.final_spot_reponse_master[1]);
			Log.i("**onPo**", "arr[2]---" + ServiceHelper.final_spot_reponse_master[2]);

			if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("0"))) {
				removeDialog(PROGRESS_DIALOG);
				showToast("Ticket Generation Failed!");

			} else if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("1"))) {
				removeDialog(PROGRESS_DIALOG);
				showToast("Ticket Generation Failed\n Already Detained :"
						+ ServiceHelper.final_spot_reponse_master[1].toString().trim());

			} else if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("2"))) {
				removeDialog(PROGRESS_DIALOG);
				showToast("Ticket Generation Failed\n Already Generated :"
						+ ServiceHelper.final_spot_reponse_master[1].toString().trim());

			} else {
				showToast("Ticket Successfully Generated!");
				Log.i("POST SE", "" + ServiceHelper.final_spot_reponse_details.length);
				// Log.i("SERVER FTP PATH",
				// ""+ServiceHelper.final_spot_reponse_details[12]);
				// Log.i("SERVER FTP IMAGE NAME",
				// ""+ServiceHelper.final_spot_reponse_details[13]);

				/* IMAGE UPLOAD TO FTP */
				Log.i("PICTURE PATH FROM SPOT", "" + picturePath);
				/*
				 * if ((!picturePath.toString().trim().equals("")) &&
				 * (!ServiceHelper.final_spot_reponse_details[12].toString().
				 * trim().equals("NA"))) { uploadFile(picturePath); } else {
				 */
				removeDialog(PROGRESS_DIALOG);
				removeDialog(SECOND_SPOTSCREEN_DIALOG);
				if (ServiceHelper.final_spot_reponse_details.length > 0) {
					startActivity(new Intent(SpotChallan.this, Respone_Print.class));
					finish();
					// }
				}
			}
		}
	}

	public void uploadFile(String fileName) {

		try {

			String replacing_image_name = "";

			/* GETTING ONLY IMAGE NAME FROM SERVICE AFTER SPLITTING */
			replacing_image_name = ServiceHelper.final_spot_reponse_details[13];
			Log.i("reaplcaing image name", "" + replacing_image_name);
			File file_tosend = new File(fileName);// sdcard/o/DCIM/IMG_67.jpg
			Log.i("getAbsolutePath()", "" + file_tosend.getAbsolutePath());
			Log.i("getPath()", "" + file_tosend.getPath());

			client = new FTPClient();
			client.connect(FTP_HOST_PORT_SPOT[0].toString().trim(),
					Integer.parseInt(FTP_HOST_PORT_SPOT[1].toString().trim()));
			client.login(Utils.FTP_USERNAME, Utils.FTP_PWD);
			client.setType(FTPClient.TYPE_BINARY);

			Log.i("parent file", file_tosend.getParentFile() + "");

			File oldfile = new File(new File(file_tosend.getParentFile() + ""), file_tosend.getName());
			File newfile = new File(new File(file_tosend.getParentFile() + ""), replacing_image_name);

			if (oldfile.renameTo(newfile)) {
				System.out.println("Rename succesful");
			} else {
				System.out.println("Rename failed");
			}

			Log.i("NEW PATH", "" + newfile);

			if ((file_tosend.getParentFile() != null)
					&& (!ServiceHelper.final_spot_reponse_details[12].toString().trim().equals("NA"))) {
				client.changeDirectory("" + ServiceHelper.final_spot_reponse_details[12].toString().trim());
				client.upload(newfile, new MyTransferListener());
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				client.logout();
				client.disconnect(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/******* Used to file upload and show progress **********/

	public class MyTransferListener implements FTPDataTransferListener {

		public void started() {
			// Transfer started
			showDialog(PROGRESS_DIALOG);
			System.out.println(" Upload Started ...");
		}

		public void transferred(int length) {
			// Yet other length bytes has been transferred since the last time
			// this
			// method was called
			showDialog(PROGRESS_DIALOG);
			System.out.println(" transferred ..." + length);
		}

		public void completed() {

			// Transfer completed

			removeDialog(PROGRESS_DIALOG);
			if (ServiceHelper.final_spot_reponse_details.length > 0) {
				if (!Drunk_Drive.picturePath.equals("")) {
					Drunk_Drive.picturePath = "";
				}
				/* TO LOGOUT THE FTP */
				try {
					client.logout();
					client.disconnect(true);

				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (FTPIllegalReplyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (FTPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(new Intent(SpotChallan.this, Respone_Print.class));
				finish();
			}
			System.out.println(" completed ...");
		}

		public void aborted() {
			// Transfer aborted
			removeDialog(PROGRESS_DIALOG);

			/* TO LOGOUT THE FTP */
			try {
				client.logout();
				client.disconnect(true);

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" aborted ...");
		}

		public void failed() {
			// Transfer failed
			removeDialog(PROGRESS_DIALOG);
			/* TO LOGOUT THE FTP */
			try {
				client.logout();
				client.disconnect(true);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" failed ...");
		}
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

	/* VEHICLE HISTROY ASYNC START */

	// Log.i("***PENDING SELCTED LIST**", ""
	// + sb_selected_penlist_send.toString());
	//
	// // showToast("" + sb_detained_items.toString());
	// Log.i("***VEHICLE HISTORY CLASS**", "**start***");
	// Log.i("selectedPendingChallans", ""
	// + sb_selected_penlist_send);
	// Log.i("pendingChallans", "EMPTY");
	// Log.i("complete vhle num", ""
	// + completeVehicle_num_send);
	// Log.i("gatewy", "empty");
	// Log.i("unitCode", "" + Dashboard.UNIT_CODE);
	// Log.i("pscode", ""
	// + MainActivity.arr_logindetails[2].toString()
	// .trim());
	// Log.i("psName", ""
	// + MainActivity.arr_logindetails[3].toString()
	// .trim());
	// Log.i("pidcode", ""
	// + MainActivity.arr_logindetails[0].toString()
	// .trim());
	// Log.i("pidName", ""
	// + MainActivity.arr_logindetails[1].toString()
	// .trim());
	// Log.i("total_amnt ", "EMPTY");
	// Log.i("detained_items_rc_vhle_permit", ""
	// + sb_detained_items.toString().trim());
	// Log.i("dateofPymet", "" + present_date_toSend);
	// Log.i("pmtTime", "" + present_date_toSend);
	// Log.i("simid_send", "" + simid_send);
	// Log.i("imeiNo", "" + imei_send);
	// Log.i("latitude", "" + latitude);
	// Log.i("longitude", "" + longitude);
	// Log.i("pointCode", "" + point_code_send_from_settings);
	// Log.i("pointName", "" + point_name_send_from_settings);
	// Log.i("onlinemode", "" + ONLINE_MODE_FIX);
	// Log.i("modulecode", "" + MODULE_CODE_FIX);
	// Log.i("releasedItem DEtVal", ""
	// + releasedDetained_items_list_toSend);
	// Log.i("CHALLAN_NUM_FIX", "" + CHALLAN_NUM_FIX);
	// Log.i("SERVICE_CODE_FIX", "" + SERVICE_CODE_FIX);
	// Log.i("owner_LIC_Num", "EMPTY");
	// Log.i("drvierLicNo", ""
	// + et_driver_lcnce_num_spot.getText().toString()
	// .trim());
	// Log.i("password", "EMPTY");
	// Log.i("imageEvidence", "" + imgEvidence);
	// Log.i("challanType", "EMPTY");
	// Log.i("challanCd", "EMPTY");
	// Log.i("exact_location", ""
	// + exact_location_send_from_settings);
	// Log.i("remarks", ""
	// + et_remarks_spot.getText().toString().trim());
	// Log.i("pancard", "" + pancard_to_send);
	// Log.i("aadhar", ""
	// + et_aadharnumber_spot.getText().toString()
	// .trim());
	// Log.i("voterId", "" + VoterId_to_send);
	// Log.i("passport", "" + passport_to_send);
	// Log.i("email", "" + emailId_to_send);
	// Log.i("driverContactNo", ""
	// + et_driver_contact_spot.getText().toString()
	// .trim());
	// Log.i("is_it_spot", "" + is_it_spot_send);
	// Log.i("cadre_code", "EMPTY");
	// Log.i("cadre_name", "EMPTY");
	//
	// Log.i("***VEHICLE HISTORY CLASS**", "**END***");

	public class Async_vhcleHistory extends AsyncTask<Void, Void, String> {

		@SuppressWarnings("unused")
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("Passport No ::::", "" + citizen_status);

			SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String cadreName = sharedPreference.getString("CADRE_NAME", "");
			String cadreCode = sharedPreference.getString("CADRE_CODE", "");

			/*
			 * ServiceHelper.vehicleHistory(sb_selected_penlist_send.toString(),
			 * "", "" + completeVehicle_num_send, "", "" + Dashboard.UNIT_CODE,
			 * "" + MainActivity.arr_logindetails[2].toString().trim(), "" +
			 * MainActivity.arr_logindetails[3].toString().trim(), "" +
			 * MainActivity.arr_logindetails[0].toString().trim(), "" +
			 * MainActivity.arr_logindetails[1].toString().trim(), "", "" +
			 * sb_detained_items.toString().trim(), "" + present_date_toSend, ""
			 * + present_time_toSend, "" + simid_send, "" + imei_send, "" +
			 * latitude, "" + longitude, "" + point_code_send_from_settings, ""
			 * + point_name_send_from_settings, "" + ONLINE_MODE_FIX, "" +
			 * MODULE_CODE_FIX, "" + releasedDetained_items_list_toSend, "" +
			 * CHALLAN_NUM_FIX, "" + SERVICE_CODE_FIX, "", "" +
			 * et_driver_lcnce_num_spot.getText().toString().trim(), "", "" +
			 * imgEvidence, "", "", "" + exact_location_send_from_settings, "" +
			 * et_remarks_spot.getText().toString().trim(), "" +
			 * pancard_to_send, "" +
			 * et_aadharnumber_spot.getText().toString().trim(), "" +
			 * VoterId_to_send, "" + passport_to_send, "" + emailId_to_send, ""
			 * + et_driver_contact_spot.getText().toString().trim(),
			 * is_it_spot_send, "", "");
			 */

			if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {

				if (radioGroupButton_spotpaymentYes.isChecked()
						&& Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					is_it_spot_send = "1";
					Log.i("Async_::", "" + is_it_spot_send);

				} else if (radioGroupButton_spotpaymentNo.isChecked()
						&& Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					is_it_spot_send = "0";
					Log.i("Async_v::", "" + is_it_spot_send);

				}
				Log.i("vehiclehistory No ::::", "*****ENTERED******");

				SharedPreferences sharedPreference2 = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				String psCd = sharedPreference2.getString("PS_CODE", "");
				String psName = sharedPreference2.getString("PS_NAME", "");
				String pidCd = sharedPreference2.getString("PID_CODE", "");
				String pidName = sharedPreference2.getString("PID_NAME", "");
				String cadre = sharedPreference2.getString("CADRE_NAME", "");
				String cadreCd = sharedPreference2.getString("CADRE_CODE", "");

				String pswd = sharedPreference.getString("PASS_WORD", "");

				try {
					pswd = PidSecEncrypt.encryptmd5(pswd);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ServiceHelper.vehicleHistory(
						"" + sb_selected_penlist_send.toString() != null
								&& sb_selected_penlist_send.toString().length() > 0
										? sb_selected_penlist_send.toString() : "",
						"",
						"" + completeVehicle_num_send != null && completeVehicle_num_send.length() > 0
								? completeVehicle_num_send : "",
						"", "" + Dashboard.UNIT_CODE, "" + psCd, "" + psName, "" + pidCd, "" + pidName, "",
						"" + sb_detained_items.toString().trim() != null
								&& sb_detained_items.toString().trim().length() > 0 ? sb_detained_items.toString() : "",
						"" + present_date_toSend, "" + present_time_toSend, "" + simid_send, "" + imei_send,
						"" + latitude, "" + longitude, "" + point_code_send_from_settings,
						"" + point_name_send_from_settings, "" + ONLINE_MODE_FIX, "" + MODULE_CODE_FIX,
						"" + releasedDetained_items_list_toSend,
						"" + CHALLAN_NUM_FIX != null && CHALLAN_NUM_FIX.length() > 0 ? CHALLAN_NUM_FIX : "",
						"" + SERVICE_CODE_FIX != null && SERVICE_CODE_FIX.length() > 0 ? SERVICE_CODE_FIX : "",
						"" + et_driver_lcnce_num_spot.getText().toString().trim(),
						"" + et_driver_lcnce_num_spot.getText().toString().trim(), "" + pswd,
						"" + final_image_data_tosend, "", "", "" + exact_location_send_from_settings,
						"" + et_remarks_spot.getText().toString().trim(), "" + pancard_to_send,
						"" + et_aadharnumber_spot.getText().toString().trim(), "" + VoterId_to_send,
						"" + passport_to_send, "" + emailId_to_send,
						"" + et_driver_contact_spot.getText().toString().trim(), "" + is_it_spot_send, "" + cadreCode,
						"" + cadreName);

			}

			else if (Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {

				if (radioGroupButton_spotpaymentYes.isChecked()
						&& Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					is_it_spot_send = "1";
					Log.i("Async_:", "" + is_it_spot_send);

				} else if (radioGroupButton_spotpaymentNo.isChecked()
						&& Dashboard_PC.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					is_it_spot_send = "0";
					Log.i("As:::", "" + is_it_spot_send);

				}
				Log.i("vehiclehistory No ::::", "*****ENTERED******");

				SharedPreferences sharedPreference2 = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				String psCd = sharedPreference2.getString("PS_CODE", "");
				String psName = sharedPreference2.getString("PS_NAME", "");
				String pidCd = sharedPreference2.getString("PID_CODE", "");
				String pidName = sharedPreference2.getString("PID_NAME", "");
				String cadre = sharedPreference2.getString("CADRE_NAME", "");
				String cadreCd = sharedPreference2.getString("CADRE_CODE", "");

				String pswd = sharedPreference.getString("PASS_WORD", "");

				try {
					pswd = PidSecEncrypt.encryptmd5(pswd);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ServiceHelper.vehicleHistory(
						"" + sb_selected_penlist_send.toString() != null
								&& sb_selected_penlist_send.toString().length() > 0
										? sb_selected_penlist_send.toString() : "",
						"",
						"" + completeVehicle_num_send != null && completeVehicle_num_send.length() > 0
								? completeVehicle_num_send : "",
						"", "" + Dashboard_PC.UNIT_CODE, "" + psCd, "" + psName, "" + pidCd, "" + pidName, "",
						"" + sb_detained_items.toString().trim() != null
								&& sb_detained_items.toString().trim().length() > 0 ? sb_detained_items.toString() : "",
						"" + present_date_toSend, "" + present_time_toSend, "" + simid_send, "" + imei_send,
						"" + latitude, "" + longitude, "" + point_code_send_from_settings,
						"" + point_name_send_from_settings, "" + ONLINE_MODE_FIX, "" + MODULE_CODE_FIX,
						"" + releasedDetained_items_list_toSend,
						"" + CHALLAN_NUM_FIX != null && CHALLAN_NUM_FIX.length() > 0 ? CHALLAN_NUM_FIX : "",
						"" + SERVICE_CODE_FIX != null && SERVICE_CODE_FIX.length() > 0 ? SERVICE_CODE_FIX : "",
						"" + et_driver_lcnce_num_spot.getText().toString().trim(),
						"" + et_driver_lcnce_num_spot.getText().toString().trim(), "" + pswd,
						"" + final_image_data_tosend, "", "", "" + exact_location_send_from_settings,
						"" + et_remarks_spot.getText().toString().trim(), "" + pancard_to_send,
						"" + et_aadharnumber_spot.getText().toString().trim(), "" + VoterId_to_send,
						"" + passport_to_send, "" + emailId_to_send,
						"" + et_driver_contact_spot.getText().toString().trim(), "" + is_it_spot_send, "" + cadreCode,
						"" + cadreName);

			}

			else if (Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments")) {
				Log.i("rel::", "*****ENTERED******");

				/*
				 * public String vehicleRelease15(String
				 * selectedPendingChallans,String pendingChallans, String
				 * regnNo, String gtwyCd, String unitCode, String psCode, String
				 * psName, String pidCd, String pidName, String totalAmount,
				 * String detained, String dOfPay, String pmtTime, String simId,
				 * String imeiNo, String latitude, String longitude, String
				 * pointCode, String pointName, String onlineMode, String
				 * moduleCd, String releasedDetValues, String challanNo, String
				 * serviceCode, String ownerLicNo, String driverLicNo, String
				 * password, String imageEvidence, String challanType, String
				 * challanCd, String location, String remarks, String pancardNo,
				 * String aadharNo, String voterId, String passport, String
				 * email, String driverContactNo, String isItSpotPmt, String
				 * cadreCd, String cadre,String passportNo,String engNo,String
				 * chasisNO);
				 */

				/*
				 * if (radioGroupButton_spotpaymentYes.isChecked() &&
				 * Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"
				 * )) { is_it_spot_send = "1" ; Log.i("releasedocuments  1:::",
				 * ""+is_it_spot_send);
				 * 
				 * }else if (radioGroupButton_spotpaymentNo.isChecked() &&
				 * Dashboard.check_vhleHistory_or_Spot.equals("releasedocuments"
				 * )) { is_it_spot_send = "0" ; Log.i("releasedocuments  2:::",
				 * ""+is_it_spot_send);
				 * 
				 * }
				 */

				SharedPreferences sharedPreference2 = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				String psCd = sharedPreference2.getString("PS_CODE", "");
				String psName = sharedPreference2.getString("PS_NAME", "");
				String pidCd = sharedPreference2.getString("PID_CODE", "");
				String pidName = sharedPreference2.getString("PID_NAME", "");
				String cadre = sharedPreference2.getString("CADRE_NAME", "");
				String cadreCd = sharedPreference2.getString("CADRE_CODE", "");

				String pswd = sharedPreference.getString("PASS_WORD", "");

				try {
					pswd = PidSecEncrypt.encryptmd5(pswd);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ServiceHelper.releaserDocumentsModule("" + sb_selected_penlist_send.toString(), "",
						"" + completeVehicle_num_send, "", "" + Dashboard.UNIT_CODE, "" + psCd, "" + psName, "" + pidCd,
						"" + pidName, "", "" + sb_detained_items.toString().trim(), "" + present_date_toSend,
						"" + present_time_toSend, "" + simid_send, "" + imei_send, "" + latitude, "" + longitude,
						"" + point_code_send_from_settings, "" + point_name_send_from_settings, "" + ONLINE_MODE_FIX,
						"" + MODULE_CODE_FIX, "" + releasedDetained_items_list_toSend, "" + CHALLAN_NUM_FIX,
						"" + SERVICE_CODE_FIX, "" + et_driver_lcnce_num_spot.getText().toString().trim(),
						"" + et_driver_lcnce_num_spot.getText().toString().trim(), "" + pswd,
						"" + final_image_data_tosend, "", "", "" + exact_location_send_from_settings,
						"" + et_remarks_spot.getText().toString().trim(), "" + pancard_to_send,
						"" + et_aadharnumber_spot.getText().toString().trim(), "" + VoterId_to_send,
						"" + passport_to_send, "" + emailId_to_send,
						"" + et_driver_contact_spot.getText().toString().trim(), "" + is_it_spot_send, "" + cadreCode,
						"" + cadreName, "" + passport_to_send, "" + et_engineNo.getText().toString().trim(),
						"" + et_chasisNo.getText().toString().trim());
				// String isItSpotPmt, String cadreCd, String cadre,String
				// passportNo,String engNo,String chasisNO);
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);

			if (ServiceHelper.final_spot_reponse_master != null) {

				Log.i("**onPostExecute *",
						"arr[0]---" + ServiceHelper.final_spot_reponse_master[0]);
				Log.i("**onPostExecute V**",
						"arr[1]---" + ServiceHelper.final_spot_reponse_master[1]);
				Log.i("**onPostEPONSE**",
						"arr[2]---" + ServiceHelper.final_spot_reponse_master[2]);

				if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("0"))) {
					removeDialog(PROGRESS_DIALOG);
					showToast("Failed!");

				} else if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("1"))) {
					removeDialog(PROGRESS_DIALOG);
					showToast("Failed\n Already Detained :"
							+ ServiceHelper.final_spot_reponse_master[1].toString().trim());

				} else if ((ServiceHelper.final_spot_reponse_master[0].toString().trim().equals("2"))) {
					removeDialog(PROGRESS_DIALOG);
					showToast("Failed\n Already Generated :"
							+ ServiceHelper.final_spot_reponse_master[1].toString().trim());

				} else {
					showToast("Success");
					if (ServiceHelper.final_spot_reponse_violations != null) {
						for (int i = 0; i < ServiceHelper.final_spot_reponse_violations.length; i++) {
							/*
							 * Log.i("RES TICKET NO", "" +
							 * ServiceHelper.final_spot_reponse_violations[i][0]
							 * ); Log.i("RES AMOUNT", "" +
							 * ServiceHelper.final_spot_reponse_violations[i][1]
							 * );
							 */
						}
					}
					if (ServiceHelper.final_spot_reponse_details != null
							&& ServiceHelper.final_spot_reponse_details.length > 0) {
						startActivity(new Intent(SpotChallan.this, Respone_Print.class));
						finish();
					}
				}
			} else {
				showToast("Ticket Generation Failed due bad Network!!");
			}
		}
	}

	/* VEHICLE HISTROY ASYNC END */

	public void getLocation() {

		try {
			m_locationlistner = (LocationManager) this.getSystemService(LOCATION_SERVICE);
			// getting GPS status
			isGPSEnabled = m_locationlistner.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// getting network status
			isNetworkEnabled = m_locationlistner.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				latitude = 0.0;
				longitude = 0.0;
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
					m_locationlistner.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (m_locationlistner != null) {
						location = m_locationlistner.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						} else {
							latitude = 0.0;
							longitude = 0.0;
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {

					if (location == null) {
						m_locationlistner.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (m_locationlistner != null) {
							location = m_locationlistner.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							} else {
								latitude = 0.0;
								longitude = 0.0;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei_send = telephonyManager.getDeviceId();// TO GET IMEI NUMBER
		if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
			simid_send = "" + telephonyManager.getSimSerialNumber();
		} else {
			simid_send = "";
		}

		@SuppressLint("WifiManagerLeak")
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();
		macAddress = wInfo.getMacAddress();

	}

	public void invokeLastMilePayApp(String reqString) {
		// TODO Auto-generated method stub
		Log.e("invokePOSApp", "reqString is ::" + reqString);
		cam_imag = "lastmile";
		Uri uri = Uri.parse(reqString);
		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		startActivityForResult(i, INVOKE_LASTMILE_PAY); // INVOKE_LASTMILE_PAY=100
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			latitude = (float) location.getLatitude();
			longitude = (float) location.getLongitude();
			// speed = location.getSpeed();
		} else {
			latitude = 0.0;
			longitude = 0.0;
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/* CAMERA DEVICE CHECKING */
	@SuppressWarnings("unused")
	private boolean isDeviceSupportCamera() {

		if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device

			return false;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		tv_grand_total_spot.setText("");
		showToast("Please Click on Cancel Button");
		newtimer.cancel();
	}

	public void ShowMessage(String Message) {

		/******* data and time alert start **********/
		TextView title = new TextView(SpotChallan.this);
		title.setText("ALERT");
		title.setBackgroundColor(Color.RED);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(26);
		title.setTypeface(title.getTypeface(), Typeface.BOLD);
		title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
		title.setPadding(20, 0, 20, 0);
		title.setHeight(70);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
				AlertDialog.THEME_HOLO_LIGHT);
		alertDialogBuilder.setCustomTitle(title);
		alertDialogBuilder.setIcon(R.drawable.dialog_logo);
		alertDialogBuilder.setMessage(Message);
		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

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
		/******* data and time alert end **********/

		/*
		 * TextView title2 = new TextView(this); title2.setText("ALERT");
		 * title2.setBackgroundColor(Color.RED);
		 * title2.setGravity(Gravity.CENTER); title2.setTextColor(Color.WHITE);
		 * title2.setTextSize(26); title2.setTypeface(title2.getTypeface(),
		 * Typeface.BOLD);
		 * title2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
		 * dialog_logo, 0, R.drawable.dialog_logo, 0); title2.setPadding(20, 0,
		 * 20, 0); title2.setHeight(70);
		 * 
		 * TextView Message1 = new TextView(this); Message1.setText(Message);
		 * Message1.setBackgroundColor(Color.RED);
		 * Message1.setGravity(Gravity.CENTER);
		 * Message1.setTextColor(Color.WHITE); Message1.setTextSize(26);
		 * Message1.setTypeface(title2.getTypeface(), Typeface.BOLD);
		 * Message1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.
		 * dialog_logo, 0, R.drawable.dialog_logo, 0); Message1.setPadding(20,
		 * 0, 20, 0); Message1.setHeight(70);
		 * 
		 * // String network_message = "\nPlease Check Your Network Connection
		 * // Properly. This Process Takes Some Time To Download Masters\n";
		 * 
		 * AlertDialog.Builder builder = new
		 * AlertDialog.Builder(SpotChallan.this, AlertDialog.THEME_HOLO_LIGHT);
		 * builder.setCustomTitle(title2); builder.setIcon(R.drawable.alert);
		 * builder.setMessage(Message1.getText().toString());
		 * builder.setCancelable(false); builder.setPositiveButton("Ok", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @SuppressWarnings("static-access")
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * builder.setCancelable(false); AlertDialog alertDialog =
		 * builder.create(); alertDialog.show();
		 */

		/*
		 * AlertDialog.Builder alertDialogBuilder = new
		 * AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		 * alertDialogBuilder.setTitle(Title);
		 * alertDialogBuilder.setIcon(R.drawable.dialog_logo);
		 * alertDialogBuilder.setMessage(Message);
		 * alertDialogBuilder.setPositiveButton("Ok", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface arg0, int arg1) {
		 * 
		 * } }); alertDialogBuilder.setCancelable(false); AlertDialog
		 * alertDialog = alertDialogBuilder.create(); alertDialog.show();
		 */}

	public void ShowMessageDL(String Message) {

		/******* data and time alert start **********/
		TextView title = new TextView(SpotChallan.this);
		title.setText("ALERT");
		title.setBackgroundColor(Color.RED);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(26);
		title.setTypeface(title.getTypeface(), Typeface.BOLD);
		title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
		title.setPadding(20, 0, 20, 0);
		title.setHeight(70);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
				AlertDialog.THEME_HOLO_LIGHT);
		alertDialogBuilder.setCustomTitle(title);
		alertDialogBuilder.setIcon(R.drawable.dialog_logo);
		alertDialogBuilder.setMessage(Message);
		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				extraPassengers = "1";
				tv_grand_total_spot.setText("");
				tv_violation_amnt.setText("");
				VehicleHistoryPendingChallans.total_amount_selected_challans = 0.0;

				if (isOnline()) {

					if ((!btn_wheller_code.getText().toString().trim()
							.equals("" + getString(R.string.select_wheeler_code)))
							&& (violation_offence_Code.size() > 0)) {

						/* TO CLEAR THE CHECKED & UNC-CHECKED POEISITONS */
						violation_positions.removeAll(violation_positions);
						violation_rg_ids.removeAll(violation_rg_ids);
						violation_checked_violations.removeAll(violation_checked_violations);
						grand_total = 0.0;

						/* CHECK MAP INTITALISATION */
						check_map = new LinkedHashMap<String, String>();
						check_all_ids = new HashMap<String, String>();
						check_all_ids.clear();

						vioCodeDescMap = new HashMap<String, String>();
						vioCodeDescMap.clear();

						violations_details_send = new StringBuffer();
						violations_details_send.delete(0, violations_details_send.length());

						/* TO APPEND THE SLECTED VILATIONS TO BUTTON */
						violation_desc_append = new StringBuffer();
						violation_desc_append.delete(0, violation_desc_append.length());

						removeDialog(DYNAMIC_VIOLATIONS);
						showDialog(DYNAMIC_VIOLATIONS);

					} else {
						showToast("Select Wheeler Code");
					}
				} else {
					showToast("No Internet Connection");
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
		/******* data and time alert end **********/

	}

	public void otpAlert() {
		TextView title = new TextView(SpotChallan.this);
		title.setText("ALERT");
		title.setBackgroundColor(Color.RED);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(26);
		title.setTypeface(title.getTypeface(), Typeface.BOLD);
		title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
		title.setPadding(20, 0, 20, 0);
		title.setHeight(70);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpotChallan.this,
				AlertDialog.THEME_HOLO_LIGHT);
		alertDialogBuilder.setCustomTitle(title);
		alertDialogBuilder.setIcon(R.drawable.dialog_logo);
		alertDialogBuilder.setMessage("\nPlease Click on Send OTP Button to Verify Offender Mobile Number !\n");
		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				et_driver_contact_spot.requestFocus();
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

	}

}
