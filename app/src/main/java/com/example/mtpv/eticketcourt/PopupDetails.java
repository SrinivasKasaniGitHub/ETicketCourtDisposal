package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.ServiceHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

//import com.agsindia.mpos_integration.ActivitySwipe

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class PopupDetails extends Activity implements LocationListener {

	public static String Signature, reqString = "";
	public static String merchantId = "800026";
	public static String merchantKey = "57Y5LEJFUK88CCDSB89D6JI4";
	public static final int INVOKE_LASTMILE_PAY = 100;
	
	public static final int PROGRESS_DIALOG = 0;
	
	
	public static EditText MobileNo, EmailID ;
	public static ImageView submit, cancel ;
	private boolean isValidEmaillId(String email){

		    return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
		              + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
		              + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
		              + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
		              + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
		              + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
	  }
	  
	DBHelper db;
    String address_BT = null , term_bt_id = null, term_id = null, term_bt_name = null;
	String name_BT ;
	Cursor cursor, terminal_cursor ;
	
	/* DATE & TIME START */
	SimpleDateFormat date_format;
	Calendar calendar;
	int present_date;
	int present_month;
	int present_year;

	int present_hour;
	int present_minutes;

	String present_date_toSend = "", current_date =null,  current_time =null;
	StringBuffer present_time_toSend;
	  
	
	LocationManager m_locationlistner;
	android.location.Location location;

	double latitude = 0.0;
	double longitude = 0.0;
	String provider = "";
	String IMEI = "", sim_No = null;
	public static String user_id = "";
	public static String user_pwd = "";
	/* GPS VALUES */
	// flag for GPS status
	boolean isGPSEnabled = false;
	// flag for network status
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	String all_challans ="", unitName ="", unitCode ="", ticket_no = "", complete_challan_tosend ="" ;
	String[] split_ticket ;
	public static String eticket_no = "", unit_cd = "", acmnd_amnt="", user_charges = "", cmd_amnt = ""	, pending_chaallanAmnt ="", pending_challans = "",
			pidCode = "", pidName = "";
	//eticket no@unitcode@acmd_amnt@usercharges@cmd_amnt

	public static String auth_code, rrn, invoice_no, response_code, transaction_No, card_holder, card_type, batch_no ;

	public static String result, gps_Date ;

	public static String[] txn_no ;

	public static long time = 0;
	public static java.util.Date date ;

	LinearLayout ll_vhle_hstry_pending_challans, full_pending_layout;
	TextView tv_total_challans;
	TextView tv_total_amnt;
	TextView tv_sub_header;

	int pos;
	int total_amount = 0;
	CheckBox[] cb;
	LinearLayout[] ll;
	ArrayList<Boolean> detained_items_status;
	public static double total_amount_selected_challans = 0.0;


	public static ArrayList<String> sb_selected_penlist;

	public static ArrayList<String> ALL_selected_penlist;
	public static StringBuffer ALL_selected_penlist_toSend;


	public static ArrayList<String> sb_selected_penlist_positions;

	public static StringBuffer sb_selected_penlist_send  ;

	RelativeLayout cuurentDetails_layout ;
	TextView current_ticketNo, currentDate, currentAmount ;
	public static String Amount, add_PendingSelected ;

	int total_pendingChallan=0, total_pendingAmount=0;

	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_popup_details);
		 this.setFinishOnTouchOutside(false);

		 getLocation();

		 getDateAndTime();

		 sb_selected_penlist_positions = new ArrayList<String>();
		 sb_selected_penlist_send = new StringBuffer("");
		 ALL_selected_penlist_toSend = new StringBuffer("");

		 ll_vhle_hstry_pending_challans = (LinearLayout) findViewById(R.id.ll_vhle_hstry_pchallans_xml);
		 full_pending_layout = (LinearLayout)findViewById(R.id.full_pending_layout);

		 tv_total_amnt = (TextView) findViewById(R.id.tv_totalamount_pen_challans_xml);
		 tv_total_amnt.setText("");

		 cuurentDetails_layout = (RelativeLayout)findViewById(R.id.current_details_layout);
		 currentAmount = (TextView)findViewById(R.id.current_amount);
		 currentDate = (TextView)findViewById(R.id.curerent_date);
		 current_ticketNo = (TextView)findViewById(R.id.curent_tcket_No);

		 full_pending_layout.setVisibility(View.GONE);

		 total_amount_selected_challans = 0.0;

		 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 all_challans = sharedPreferences.getString("ALL_CHALLANS", "");
    	 unitName = sharedPreferences.getString("UNIT_NAME", "");
    	 unitCode = sharedPreferences.getString("UNIT_CODE", "");
    	 ticket_no = sharedPreferences.getString("TICKET_DETAILS", "");
    	 pending_chaallanAmnt = sharedPreferences.getString("PENDING_CHALLAN_AMNT", "");
    	 pending_challans = sharedPreferences.getString("PENDING_CHALLANS", "");
    	 pidCode = sharedPreferences.getString("PID_CODE", "");
    	 pidName = sharedPreferences.getString("PID_NAME", "");

    	Log.i("Chal--->1 ", ""+all_challans);
		Log.i("unit_name--->1 ", ""+unitName);
		Log.i("unit_c--->1 ", ""+unitCode);
		Log.i("ticket deup ----->1",""+ticket_no);
		Log.i("pendingn Popup ----->1",""+	pending_chaallanAmnt);
		Log.i("pendinopup ----->1",""+	pending_challans);

		Log.i("pidC--->1",""+pidCode);
		Log.i("pidNam---->1",""+pidName);


		/*if (all_challans!=null) {
			String[] split_challans = ServiceHelper.final_spot_reponse_master[2].split("\\!");
//			!@HYD00TE161000361@23@1@0@1
			//!@HYD00TE161000368@23@1@0@1
			Log.i("split_challans[0] :::", ""+split_challans[0]);
			Log.i("split_challans[1] :::", ""+split_challans[1]);

			String[] split_challans2 = split_challans[1].split("\\@");

			for (int i = 0; i < split_challans2.length; i++) {

				Log.i("split_challans2[i] :::", ""+split_challans2[i]);
				Log.i("split_challans2[i] Length :::", ""+split_challans2[i].length());

				current_ticketNo.setText(""+split_challans2[1]);
				currentDate.setText(""+present_date_toSend);
				currentAmount.setText(""+split_challans2[5]);

				10-03 15:15:03.437: I/split_challans[0] :::(2521): 42@100@100@Restriction on plying of vehicles ( S 119/177 )
				10-03 15:15:03.437: I/split_challans[1] :::(2521): 118@500@500@Refusal to Ply ( 179(1) )
				10-03 15:15:03.437: I/split_challans2[i] :::(2521): 118
				10-03 15:15:03.437: I/split_challans2[i] Length :::(2521): 3

			}
		}*/

		/************COMMENTED ON 26-10-2016****************/

		if (all_challans!=null) {

			//!@HYD00TE161000368@23@1@0@1$
			String[] splitChallans = all_challans.split("\\!") ;
			//@HYD00TE161000368@23@1@0@1$ at index 1
			String[] splitChallans2 = splitChallans[1].split("\\$") ;
			//@HYD00TE161000368@23@1@0@1 at index 0
			String[] split_challans = splitChallans2[0].split("\\@") ;


			Log.i("split_challans[] ::::", ""+splitChallans[0]);
			Log.i("split_challans[] ::::", ""+splitChallans[1]);

			Log.i("split_challans[0] ::::", ""+split_challans[0]);
			Log.i("split_challans[1] ::::", ""+split_challans[1]);
			Log.i("split_challans[2] ::::", ""+split_challans[2]);
			Log.i("split_challans[3] ::::", ""+split_challans[3]);
			Log.i("split_challans[4] ::::", ""+split_challans[4]);
			Log.i("split_challans[5] ::::", ""+split_challans[5]);


	/*		10-03 15:51:59.578: I/split_challans[] ::::(6402): @HYD00TE161000375@23@1@0@1$
			10-03 15:51:59.578: I/split_challans[1] ::::(6402): HYD00TE161000375
			10-03 15:51:59.578: I/split_challans[2] ::::(6402): 23
			10-03 15:51:59.578: I/split_challans[3] ::::(6402): 1
			10-03 15:51:59.579: I/split_challans[4] ::::(6402): 0
			10-03 15:51:59.579: I/split_challans[5] ::::(6402): 1
	*/

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
			//print(dateFormat.format(new Date()); // will print like 2014-02-20
			//print(dateFormat2.format(new Date());
			Log.i("dateFormat ::::", ""+dateFormat.format(new Date()));
			Log.i("dateFormat2 ::::", ""+dateFormat2.format(new Date()));


			current_ticketNo.setText(""+split_challans[1]);
			currentDate.setText(""+dateFormat2.format(new Date()));
			currentAmount.setText(""+split_challans[5]);

			if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
				currentAmount.setText("0");
				tv_total_amnt.setText("Total Payable Amount : Rs. "+(Integer.parseInt(currentAmount.getText().toString().trim())));

			}else{
				tv_total_amnt.setText("Total Payable Amount : Rs. "+(Integer.parseInt(currentAmount.getText().toString().trim())));

			}
		}


		Amount ="";

		//all_challans = all_challans ;


			if (android.os.Build.VERSION.SDK_INT > 11) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			Log.i("**ServiceHh**", ""+ ServiceHelper.pending_challans_details.length);

			if (ServiceHelper.pending_challans_details.length > 0) {
				        full_pending_layout.setVisibility(View.VISIBLE);
					 	sb_selected_penlist = new ArrayList<String>();
					 	ALL_selected_penlist = new ArrayList<String>();

						sb_selected_penlist.clear();
						ALL_selected_penlist.clear();
						sb_selected_penlist_positions.clear();
						Log.i("**sb_sel*", ""+ sb_selected_penlist_positions.size());
						sb_selected_penlist_send.delete(0,sb_selected_penlist_send.length());
						ALL_selected_penlist_toSend.delete(0,ALL_selected_penlist_toSend.length());


				detained_items_status = new ArrayList<Boolean>();

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				StringBuffer myId = new StringBuffer();

				params.setMargins(0, 0, 0, 10);
				myId.delete(0, myId.length());
				cb = new CheckBox[ServiceHelper.pending_challans_details.length];


				ll = new LinearLayout[ServiceHelper.pending_challans_details.length];
				Log.i("LL LENGHT", "" + ll.length);

				for (int i = 0; i < (ServiceHelper.pending_challans_details.length); i++) {
					total_pendingChallan++;

					ll[i] = new LinearLayout(getApplicationContext());
					ll[i].setId(i);
					ll[i].setLayoutParams(params);
					ll[i].setOrientation(LinearLayout.HORIZONTAL);

					cb[i] = new CheckBox(getApplicationContext());
					android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1f);
					int identifier = getResources().getIdentifier(getApplicationContext().getPackageName()+ ":drawable/custom_chec_box", null, null);
					cb[i].setText("   "+ ServiceHelper.pending_challans_details[i][1].toString().trim()+ "       "+ ServiceHelper.pending_challans_details[i][2].toString().trim()
							+ "       "+ ServiceHelper.pending_challans_details[i][7].toString().trim());

					total_pendingAmount = total_pendingAmount+ Integer.parseInt(ServiceHelper.pending_challans_details[i][7].toString().trim());
					
					cb[i].setButtonDrawable(identifier);
					cb[i].setTextAppearance(getApplicationContext(),R.style.navi_text_style);
					cb[i].setId(i);
					/*cb[i].setScaleX((float) 0.6);
					cb[i].setScaleY((float) 0.6);*/
					// cb[i].setOnClickListener(onRadioButtonClick(cb[i].getId()));

					ll[i].addView(cb[i]);
					detained_items_status.add(true);
					
					sb_selected_penlist.add(""+ ServiceHelper.pending_challans_details[i][0].toString().trim()+ "@"+ ServiceHelper.pending_challans_details[i][1].toString().trim()+ "@"
							+ ServiceHelper.pending_challans_details[i][2].toString().trim() + ServiceHelper.pending_challans_details[i][3].toString().trim()+ "@"+ ServiceHelper.pending_challans_details[i][4].toString().trim()+ "@"
							+ ServiceHelper.pending_challans_details[i][5].toString().trim()+ "@"+ ServiceHelper.pending_challans_details[i][6].toString().trim()+ "@"
							+ ServiceHelper.pending_challans_details[i][7].toString().trim()+ "@"+ ServiceHelper.pending_challans_details[i][8].toString().trim()+ "@"
							+ ServiceHelper.pending_challans_details[i][9].toString().trim()+ "@"+ ServiceHelper.pending_challans_details[i][10].toString().trim()+"!");

							Log.i("0. VEHICLE NO   	:::::", ""+ServiceHelper.pending_challans_details[i][0]);
							Log.i("1. TICKET NO    	:::::", ""+ServiceHelper.pending_challans_details[i][1]);
							Log.i("2. OFFENCE DATE 	:::::", ""+ServiceHelper.pending_challans_details[i][2]);
							Log.i("3. OFFENCE TIME 	:::::", ""+ServiceHelper.pending_challans_details[i][3]);
							Log.i("4. POINT NAME   	:::::", ""+ServiceHelper.pending_challans_details[i][4]);
							Log.i("5. PS NAME      	:::::", ""+ServiceHelper.pending_challans_details[i][5]);
							Log.i("6. OFFENCE DESC 	:::::", ""+ServiceHelper.pending_challans_details[i][6]);
							Log.i("7. CMD AMOUNT   	:::::", ""+ServiceHelper.pending_challans_details[i][7]);
							Log.i("8. IMG_EVIDENCE 	:::::", ""+ServiceHelper.pending_challans_details[i][8]);
							Log.i("9. ACMD AMOUNT 	:::::", ""+ServiceHelper.pending_challans_details[i][9]);
							Log.i("10.USER CHARGES  :::::", ""+ServiceHelper.pending_challans_details[i][10]);
							Log.i("11.UNIT CODE		:::::", ""+ServiceHelper.pending_challans_details[i][11]);
							
					//123@CYB20162345@22@1@0@1 $
							//123@CYB20162345@22@1@0@1$	
					//@HYD20162345@23@1@0@1
							
					//eticket no@unitcode@acmd_amnt@usercharges@cmd_amnt$eticket no@unitcode@acmd_amnt@usercharges@cmd_amnt$
					ALL_selected_penlist.add(""+ServiceHelper.pending_challans_details[i][1].toString().trim()+ "@"+ 
							ServiceHelper.pending_challans_details[i][11].toString().trim()+"@"+
							ServiceHelper.pending_challans_details[i][9].toString().trim()+"@"+
							ServiceHelper.pending_challans_details[i][10].toString().trim()+"@"+
							ServiceHelper.pending_challans_details[i][7].toString().trim()+"$");		
							
							
					
					cb[i].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							
							CheckBox check = (CheckBox) v;
							
							if (detained_items_status.get(check.getId()) == true) {

								detained_items_status.set(check.getId(), false);

								sb_selected_penlist_positions.add(""+ (cb[check.getId()].getId()));
								
								total_amount_selected_challans = total_amount_selected_challans + (Double.parseDouble(ServiceHelper.pending_challans_details[check.getId()][7].toString().trim()));
								
								Log.i("total_amount_se:::", ""+total_amount_selected_challans);
								Log.i("SpotChositions :::", ""+sb_selected_penlist_positions);
								
								tv_total_amnt.setText("Total Payable Amount : \t Rs. "+ (total_amount_selected_challans + Integer.parseInt(currentAmount.getText().toString().trim())));
								
								Amount =""+ (total_amount_selected_challans + Integer.parseInt(currentAmount.getText().toString().trim()));
								
								
								SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								SharedPreferences.Editor editors = sharedPreferences.edit();
								
								String pending_challanAmnt = ""+total_amount_selected_challans ;
								editors.putString("PENDING_AMNT", ""+pending_challanAmnt);
								editors.putString("PENDING_DISPLAY", ""+detained_items_status);
								editors.commit();
								
							} else if (detained_items_status.get(check.getId()) == false) {

								detained_items_status.set(check.getId(), true);
								
								sb_selected_penlist_positions.remove(""+ (cb[check.getId()].getId()));
								
								total_amount_selected_challans = total_amount_selected_challans - (Double.parseDouble(ServiceHelper.pending_challans_details[check.getId()][7].toString().trim()));

								if (total_amount_selected_challans!= 0.0) {
									tv_total_amnt.setText("Total Payable Amount : \t Rs. "+ (total_amount_selected_challans + Integer.parseInt(currentAmount.getText().toString().trim())));
									Amount =""+ (total_amount_selected_challans + Integer.parseInt(currentAmount.getText().toString().trim()));
								}else {
									tv_total_amnt.setText("Total Payable Amount : Rs. "+(Integer.parseInt(currentAmount.getText().toString().trim())));
									Amount =""+(Integer.parseInt(currentAmount.getText().toString().trim())) ;
								}
								
								
								SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								SharedPreferences.Editor editors = sharedPreferences.edit();
								
								String pending_challanAmnt = ""+total_amount_selected_challans ;
								editors.putString("PENDING_AMNT", ""+pending_challanAmnt);
								editors.putString("PENDING_DISPLAY", ""+detained_items_status);
								
								editors.commit();
							}
						}
					});
					
					ll_vhle_hstry_pending_challans.addView(ll[i]);
				}
			
				Log.i("total_pen::", ""+total_pendingChallan+" and "+total_pendingAmount);
			}
		 
		 
			
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			Log.i("****", "LocationAndIMEIValues");
			Log.i("**imei**", "" + getDeviceID(telephonyManager)+"SIM :::"+telephonyManager.getLine1Number() + "\nlat :"+ latitude + "\nlong :" + longitude);
			IMEI = getDeviceID(telephonyManager);
			
			//sim_No = telephonyManager.getLine1Number();
			if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
				sim_No = "" + telephonyManager.getSimSerialNumber();
				Log.i("SIM Number", ""+sim_No);
			} else {
				sim_No = "";
			}
		
		 
		 db = new DBHelper(getApplicationContext());
		 
		 try {
				db.open();
				android.database.sqlite.SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME,MODE_PRIVATE, null);
				String query = "SELECT  * FROM " + DBHelper.TERMINAL_DETAILS_TABLE;
				cursor = db.rawQuery(query, null);
			    
		        if (cursor.moveToFirst()) {
		            do {
			            	address_BT = cursor.getString(3);
			            	name_BT = cursor.getString(2);
			            	
			            	Log.i("address_BT :",""+ cursor.getString(3));
			            	Log.i("name_BT :",""+ cursor.getString(2));
			           	 	
	            		} while (cursor.moveToNext());
		        		}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if(db!=null)
					db.close();
				if(cursor!=null)
					cursor.close();
			}
		 
		 
		MobileNo = (EditText)findViewById(R.id.et_mobileNo);
		EmailID = (EditText)findViewById(R.id.et_emailid);
		
		submit = (ImageView)findViewById(R.id.submit);
		cancel = (ImageView)findViewById(R.id.cancel);
		
		
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (MobileNo.getText().toString().trim().equals("")) {
					MobileNo.setError(Html.fromHtml("<font color='red'>Please Enter Mobile Number!</font>"));
					MobileNo.requestFocus();
				}else if (MobileNo.getText().toString().trim().length()>1 && !validateMobileNo(MobileNo.getText().toString().trim())) {
					MobileNo.setError(Html.fromHtml("<font color='red'>Please Enter Valid Mobile Number!</font>"));
					MobileNo.requestFocus();
				}else if (EmailID.getText().toString().trim().equals("")) {
					EmailID.setError(Html.fromHtml("<font color='red'>Please Enter Email Id!</font>"));
					EmailID.requestFocus();
				}else if (EmailID.getText().toString().trim()!=null && EmailID.getText().toString().trim().length()>1
						&&!isValidEmaillId(EmailID.getText().toString().trim())) {
					EmailID.setError(Html.fromHtml("<font color='red'>Please Enter Valid Email Id!</font>"));
					EmailID.requestFocus();
				}
				else{
					if (isOnline()) {
						
						try {
							db.open();
							android.database.sqlite.SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME,MODE_PRIVATE, null);
							String query2 = "SELECT TERMINAL_ID,BT_NAME,BT_ADDRESS FROM " + DBHelper.TERMINAL_DETAILS_TABLE +" WHERE BT_ADDRESS= '"+address_BT+"'";
							terminal_cursor = db.rawQuery(query2, null);
							
							Log.i("query2 :::", ""+query2);
						    
					        if (terminal_cursor.moveToFirst()) {
					            do {
					            	 term_id = terminal_cursor.getString(0);
					            	 term_bt_name = terminal_cursor.getString(1);
					            	 term_bt_id = terminal_cursor.getString(2);
					            	
					            	Log.i("--","--------------------");
					            	Log.i("1 :",""+ terminal_cursor.getString(0));
					            	Log.i("2 :",""+ terminal_cursor.getString(1));
					            	Log.i("3 :",""+ terminal_cursor.getString(2));
					            	
			         		} while (terminal_cursor.moveToNext());
					            
					            if(term_id!=null && term_bt_name!=null){
					            	Log.i("If Log ","Entered ");
					            	
					            	SharedPreferences myref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
									SharedPreferences.Editor editr = myref.edit();
									
									editr.putString("TERMINAL_ID", ""+term_id);
									
									editr.commit();
									
									Log.i("TERMINAL_ID :::::: ","Entered "+term_id);
					            	if (term_bt_id.equals(""+address_BT)) {
					            		Log.i("term_bt_addr :::::: ","Entered "+term_bt_id);
					            		Log.i("addre:: ","Entered "+address_BT);
					            		
					            		new Async_getTransactionNo().execute();
										
									}else {
										Log.i("If Log 1 ","Entered ");
										showToast("Invalid PIN pad Address Please Scan Again !!!");
									}
					            			
					            }else{
					            	Log.i("If Log 2 ","Entered ");
					            	showToast("Invalid PIN pad Address Please Scan Again !!!");
					            	
					            }
				        	}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}finally{
							if(db!=null)
								db.close();
							if(terminal_cursor!=null)
								terminal_cursor.close();
						}

						/*****LAST MILE INVOCATION STARTS*****/
  						reqString = "";
				       /* RequestCreator requestCreator = new RequestCreator();
				        try {
				            Signature = com.lmg.Signature.generateSignature("800026", "57Y5LEJFUK88CCDSB89D6JI4");
				        }catch (Exception e){}
				         reqString  = "pos://lastmile.cc?MID=800026&RETURL=&URLTYPE=1&SIG="+Signature+""
				         		+ "^REFID=123456&KF1=HYD161205412155&KF2=HYDERBAD&KF3=35&AMT=120.00&DESC=''"
				         		+ "^REFID=123456&KF1=CYB161205412156&KF2=CYBERABAD&KF3=35&AMT=100.00&DESC=''";
				    
				         callOtherApp(reqString); */
						/*****LAST MILE INVOCATION ENDS*****/
						
						
						
					}else {
						showToast("Please Check Internet Connection!");
					}
				}
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	private void getDateAndTime() {
		// TODO Auto-generated method stub
		calendar = Calendar.getInstance();

		present_date = calendar.get(Calendar.DAY_OF_MONTH);
		present_month = calendar.get(Calendar.MONTH);
		present_year = calendar.get(Calendar.YEAR);
		
		date_format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ssZ");//yyyy-MM-dd HH:mm:ssZ

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

		Log.i("**PRESENT TIME****", ""+ present_time_toSend.toString().trim().toUpperCase());

	}

	private void getLocation() {
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
					m_locationlistner.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (m_locationlistner != null) {
						location = m_locationlistner.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							
							time = location.getTime();
							date = new Date(time);
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							gps_Date = sdf.format(date);
							System.out.println(gps_Date);
							
							Log.i("Current Date :::", "Date :::"+ gps_Date);
							
						} else {
							latitude = 0.0;
							longitude = 0.0;
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						m_locationlistner.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (m_locationlistner != null) {
							location = m_locationlistner.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								
								time = location.getTime();
								date = new Date(time);
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								gps_Date = sdf.format(date);
								System.out.println(gps_Date);
								
								Log.i("Current Date :::", "Date :::"+ gps_Date);
								
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
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			latitude = (float) location.getLatitude();
			longitude = (float) location.getLongitude();
			
			time = location.getTime();
			date = new Date(time);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			gps_Date = sdf.format(date);
			System.out.println(gps_Date);
			
			Log.i("Current Date :::", "Date :::"+ gps_Date);
			// speed = location.getSpeed();
		} else {
			latitude = 0.0;
			longitude = 0.0;
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}

	String getDeviceID(TelephonyManager phonyManager) {

		String id = phonyManager.getDeviceId();
		if (id == null) {
			id = "not available";
		}

		int phoneType = phonyManager.getPhoneType();
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_NONE:
			return id;

		case TelephonyManager.PHONE_TYPE_GSM:
			return id;

		case TelephonyManager.PHONE_TYPE_CDMA:
			return id;

		default:
			return "UNKNOWN:ID=" + id;
		}

	}
	class Async_getTransactionNo extends AsyncTask<Void, Void, String> {
		
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			String email_id = EmailID.getText().toString().trim() ;
			String contactNo = MobileNo.getText().toString().trim();
			//eticket_no@unit_code@acmd_amt@uch_amt@cmd_amt
			Log.i("all_challans  :::", ""+all_challans);
			Log.i("IMEI  :::", ""+IMEI);
			Log.i("email_id  :::", ""+email_id);
			Log.i("contactNo  :::", ""+contactNo);
			Log.i("term_id  :::", ""+term_id);
			Log.i("address_BT  :::", ""+address_BT);
			Log.i("name_BT  :::", ""+name_BT);
			Log.i("sim_No  :::", ""+sim_No);
			Log.i("latitude  :::", ""+latitude);
			Log.i("longitude  :::", ""+longitude);
			Log.i("date  :::", ""+gps_Date);
			Log.i("ALL_selecte:", ""+ALL_selected_penlist_toSend.toString());//
			
			//total_amount_selected_challans
			ServiceHelper.getTransactionNo(""+all_challans, ""+IMEI, email_id, contactNo, ""+term_id, ""+address_BT, ""+name_BT, ""+sim_No, ""+latitude, ""+longitude, ""+gps_Date);
		  //public String getTxnRefNo10(String challans,String imei,String email,String contactNo,String terID,String btID,String btName);
			return null;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			removeDialog(PROGRESS_DIALOG);
			
			if (ServiceHelper.transactionNo_resp.equals("")
					|| ServiceHelper.transactionNo_resp.equals("NA")
					|| ServiceHelper.transactionNo_resp == null  
					|| ServiceHelper.transactionNo_resp.equals("N") 
					|| ServiceHelper.transactionNo_resp.equals("0") ) {
				showToast("Transaction Failed !");
				finish();
			}else {
				try {
					
				String transaction_resp = ServiceHelper.transactionNo_resp ;
				
				txn_no = transaction_resp.split("\\|");
				
				if (!"NA".equals(txn_no[0])) {
					Log.i("transaction_resp ::::", ""+transaction_resp);
					
					Log.i("transaction_No ::::", ""+txn_no[0]);
					Log.i("merchantId ::::", ""+txn_no[1]);
					Log.i("merchantKey ::::", ""+txn_no[2]);
					
					transaction_No = ""+txn_no[0];
					merchantId = txn_no[1];
					merchantKey = txn_no[2];	
					
					split_ticket = ticket_no.split("\\@");
					//eticket no@unitcode@acmd_amnt@usercharges@cmd_amnt$eticket no@unitcode@acmd_amnt@usercharges@cmd_amnt$
					// '$' DIVIDES EACH CHALLAN
					//HYD00CS161000037@23@0.0@0@0.0

					
					eticket_no = ""+split_ticket[1] ;
					unit_cd = ""+split_ticket[2] ;
					acmnd_amnt = ""+split_ticket[3] ;
					user_charges = ""+split_ticket[4] ;
					cmd_amnt = ""+split_ticket[5] ;
					//all_challans
					//!@HYD20162345@23@1@0@1$!@CYB20162345@22@1@0@1$
					
					for (int i = 0; i < sb_selected_penlist_positions.size(); i++) {
						ALL_selected_penlist_toSend.append(transaction_No+"@"+ ALL_selected_penlist.get(Integer.parseInt(sb_selected_penlist_positions.get(i))));
						
						Log.i("ALL_selected_Entered:::", ""+ ALL_selected_penlist_toSend);
					}
					
					complete_challan_tosend = all_challans.replace("!", transaction_No) ;
					
					Log.i("complete_ch:::", ""+complete_challan_tosend+ALL_selected_penlist_toSend);
					
	
					complete_challan_tosend = complete_challan_tosend + ALL_selected_penlist_toSend;
					
					
					int totalSelectedAmt=0;
					//"123@HYD20162345@23@1@0@1 $ 123@CYB20162345@22@1@0@1$"

					//70736301@HYD00TE161000403@23@1@0@1 $ 70736301@HYD00TE161000402@23@100@0@100$
					if(complete_challan_tosend!=null){
						String[]selectedChallans=complete_challan_tosend.split("\\$");
						
						if(selectedChallans!=null&&complete_challan_tosend.length()>0){
							for(String challan:selectedChallans){
								//"123@HYD20162345@23@1@0@1
								String challans[]=challan.split("\\@");
								
								Log.i("size :", challans.length+"");
								Log.i("amount :"," 0"+challans[0]);
								Log.i("amount :"," 1"+challans[1]);
								Log.i("amount :"," 2"+challans[2]);
								Log.i("amount :"," 3"+challans[3]);
								Log.i("amount :"," 4"+challans[4]);
								Log.i("amount :"," 5"+challans[5]);
								
								if(challans!=null &&  challans.length==6){
									totalSelectedAmt+= Integer.parseInt(challans[5]);
								}
							}
						}
					}
				
					//total_amount_selected_challans
					
					if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
						totalSelectedAmt = (int)total_amount_selected_challans ;
						
						Log.i("totalSelectedAmt ::::::", ""+totalSelectedAmt);
					}
					
					/*****INDUS INVOCATION STARTS*****/
					//com.agsindia.mpos_integration.ActivitySwipe
					//finish();
					Intent intent = new Intent("com.agsindia.mpos_integration.ActivitySwipe");
					intent.putExtra("serviceType","sale");
					intent.putExtra("mer_id","130000000001015");//130000000001015
					//intent.putExtra("ter_id","RB000022");
					//intent.putExtra("ter_id","IB001167");
					intent.putExtra("ter_id",""+term_id);
					//intent.putExtra("bt_address","00:01:90:C4:92:5B");  //00:01:90:C1:53:2A NAME= PP1116018071
					intent.putExtra("bt_address",""+address_BT); 
					intent.putExtra("bt_name",""+name_BT);
					intent.putExtra("amount",""+totalSelectedAmt);
					//intent.putExtra("challan_info","123@HYD20162345@23@1@0@1$123@CYB20162345@22@1@0@1$");  //trnxid@challanNo@UnitCode@AcmdAmnt@userCharges@cmdAMnt
					intent.putExtra("challan_info",""+complete_challan_tosend);
					//startActivity(intent);
					startActivityForResult(intent, 200);
					//finish();
				/*****INDUS INVOCATION ENDS*****/
				}else {
					showToast("Transaction Failed !!!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
		
	}
	
	class Async_makeOnlinePayment10 extends AsyncTask<Void, Void, String> {
		
		
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDialog(PROGRESS_DIALOG);
			
			//showToast("Async_makeOnlinePayment10 :::: is Called ");	
			
			Log.i("Async_ma:::", "**** Async_makeOnlinePayment10 Called *****");
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			//showToast("Async_makeOnlinePayment10 :::: Entered DoInBackground() ");
			split_ticket = ticket_no.split("\\@");
			//eticket no@unitcode@acmd_amnt@usercharges@cmd_amnt
			//HYD00CS161000037@23@0.0@0@0.0


			
			Log.i("result ::::", ""+result);
			
			eticket_no = ""+split_ticket[1] ;
			unit_cd = ""+split_ticket[2] ;
			acmnd_amnt = ""+split_ticket[3] ;
			user_charges = ""+split_ticket[4] ;
			cmd_amnt = ""+split_ticket[5] ;
			
			 /*auth_code = data.getStringExtra("Auth_Code");
             rrn = data.getStringExtra("RRN");
             invoice_no = data.getStringExtra("Stan");
             response_code = data.getStringExtra("Response_Code");
             result = data.getStringExtra("Result") ;*/
			
			pending_challans = ""+((ALL_selected_penlist_toSend.toString().length()>0 && ALL_selected_penlist_toSend.toString()!=null)?ALL_selected_penlist_toSend.toString():"");
			//pending_challans = Amount;
			
			Log.v("Complete ::", ""+complete_challan_tosend +
					"\n Transaction No ::"+transaction_No + 
					"\n Date ::"+present_date_toSend + 
					"\n Time ::"+present_time_toSend +
					"\n Resp Code ::"+response_code + 
					"\n Invoice No ::"+invoice_no +
					"\n RRN ::"+rrn +
					"\n AuthCode ::"+auth_code + 
					"\n PID_CD ::"+pidCode +
					"\n PID_NAME ::"+pidName +
					"\n UNIT_CD ::"+unit_cd + 
					"\n UNIT_NAME ::"+unitName +
					"\n PENDING_CHALLANS ::"+pending_challans +
					"\n PENDING CHALLAN AMNT ::"+pending_chaallanAmnt);	
			
			Log.v("pending_::::", ""+pending_challans);
		
			pending_chaallanAmnt = ""+ALL_selected_penlist_toSend.toString();
			
			if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
				ServiceHelper.make_OnlinePayment10(""+complete_challan_tosend, ""+transaction_No, ""+present_date_toSend, ""+present_time_toSend, ""+response_code, 
						""+invoice_no, ""+rrn, ""+auth_code, 
						""+pidCode, ""+pidName, ""+Dashboard.UNIT_CODE, 
						""+Dashboard.UNIT_NAME, ""+total_pendingChallan, ""+total_pendingAmount, "", ""+result, ""+merchantId, ""+merchantKey,
						""+term_id, ""+ batch_no, ""+card_holder, ""+card_type);
				
			}else{
				ServiceHelper.make_OnlinePayment10(""+complete_challan_tosend, ""+transaction_No, ""+present_date_toSend, ""+present_time_toSend, ""+response_code, 
						""+invoice_no, ""+rrn, ""+auth_code, 
						""+pidCode, ""+pidName, ""+Dashboard.UNIT_CODE, 
						""+Dashboard.UNIT_NAME, ""+total_pendingChallan, ""+total_pendingAmount, "", ""+result, ""+merchantId, ""+merchantKey,
						""+term_id, ""+ batch_no, ""+card_holder, ""+card_type);
				
			}
			
			//eTicket_Info = transctnid@HYD00CS161000037@23@0.0@0@0.0
			//pmt_TrId = transctnid
			//mercntResp_code =
			//mercntInvoice_no =
			//mercntRrn =
			//mercntAuth_code =
			//amt_collectedPid_cd =
			//noOf_PendingChallans =
			//pending_Amount =
			
			/*public String makeOnlinePayment10(String eTicketInfo,String pmtTrId,String date, String time,
            String mercnt_resp_code, String mercnt_invoice_no, String mercnt_Rrn, String mercnt_auth_code,
            String amt_collected_pid_cd,String amt_collected_pid_name,
            String pmt_unit_cd,String pmt_unit_name,String noOfPendingChallans,String pendingAmount,String cardDetails
            ,String pmtResult,String merchantId,String merchantKey,String terminalId,String batchNo,String cardHolderName,String cardType);
			*/
			return null;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//showToast("Async_makeOnlinePayment10 :::: Entered PostExecute() ");
			removeDialog(PROGRESS_DIALOG);
			
			if (!ServiceHelper.makePayment_resp.equals("N")) {
				finish();
				
				showToast("Transaction Successfull!!!");
				
			    Respone_Print.text_to_print.setText(ServiceHelper.makePayment_resp);
			    Respone_Print.make_paymnt.setVisibility(View.GONE);
			    
	
			}else {
				TextView title = new TextView(PopupDetails.this);
				title.setText("Hyderabad E-Ticket");
				title.setBackgroundColor(Color.RED);
				title.setGravity(Gravity.CENTER);
				title.setTextColor(Color.WHITE);
				title.setTextSize(26);
				title.setTypeface(title.getTypeface(), Typeface.BOLD);
				title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
				title.setPadding(20, 0, 20, 0);
				title.setHeight(70);

			      
			      String otp_message = "\n Transaction Failed...! \n" ;
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PopupDetails.this, AlertDialog.THEME_HOLO_LIGHT);
					alertDialogBuilder.setCustomTitle(title);
					alertDialogBuilder.setIcon(R.drawable.dialog_logo);
					alertDialogBuilder.setMessage(otp_message);
					alertDialogBuilder.setCancelable(false);
					alertDialogBuilder.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									 finish();
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
			      
			}
						
		}
		
	}
	
	
	/*@SuppressWarnings("static-access")
	protected void InvokeLastMile() {
		reqString = "";
        RequestCreator requestCreator = new RequestCreator();
        try {
            Signature = com.lmg.Signature.generateSignature("800026", "57Y5LEJFUK88CCDSB89D6JI4");
        }catch (Exception e){}
         reqString  = "pos://lastmile.cc?MID=800026&RETURL=&URLTYPE=1&SIG="+Signature+"^REFID=HYD161205412155&KF1=HYD161205412155=120.00&KF2=Hyderabad&KF3=''&AMT=120.00&DESC=''^REFID=CYB161205412156&KF1=CYB161205412156=100.00&KF2=Hyderabad&KF3=''&AMT=100.00&DESC=''";
    
         callOtherApp(reqString);
	}*/

	@SuppressWarnings({ "rawtypes", "static-access", "unused" })
	private void callOtherApp(String reqString2) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_RUN);
        intent.putExtra("URI", reqString);
        intent.setComponent(new ComponentName("com.digitseucre.lastmilepay", "com.digitseucre.lastmilepay.NotificationClient"));
        List list = getPackageManager().queryIntentActivities(intent, getPackageManager().COMPONENT_ENABLED_STATE_DEFAULT);

        if (list.size() > 0) {
            Log.e("Log", "Have application" + list.size());
            startActivityForResult(intent, 100);
        } else {
            Log.e("Log", "None application");

        }
	}

	public Boolean isOnline() {
		ConnectivityManager conManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
		return nwInfo != null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog pd = ProgressDialog.show(this, "", "",	true);
			pd.setContentView(R.layout.custom_progress_dialog);
			pd.setCancelable(false); 
			
			return pd;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}
	
	
	public void invokeLastMilePayApp(String reqString2) {
		// TODO Auto-generated method stub
		Log.i("Request String :::::", "reqString is ::" + reqString);
		Uri uri = Uri.parse(reqString);
		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		startActivityForResult(i, INVOKE_LASTMILE_PAY); // INVOKE_LASTMILE_PAY=100
	}
	
	@SuppressWarnings("static-access")
	public String getSignature(String merchantId2, String merchantKey2) {
		Log.i("called getSignature:", ""+merchantId2+" key "+merchantKey2);
		String signature="";
		try {
			//Signature sign=new Signature();
			//signature=(String)sign.generateSignature(merchantKey2, merchantKey2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("sign in getSignature:", ""+signature);
		return signature;
	}

	/***********************Mobile Number Validation Method***************************/
	protected boolean validateMobileNo(String mobileNo) {
		boolean flg=false;
		try {
			if(mobileNo!=null &&  mobileNo.trim().length()==10
					
				&&( "7".equals(mobileNo.trim().substring(0,1)) 
				|| "8".equals(mobileNo.trim().substring(0,1))
				|| "9".equals(mobileNo.trim().substring(0,1)))){
				flg=true;
			}else if(mobileNo!=null &&  mobileNo.trim().length()==11 && "0".equals(mobileNo.trim().substring(0,1)) ){
				flg=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flg=false;
			
		}
		return flg;
	}
	/***********************Mobile Number Validation Method Ends***************************/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
		case 100 :
			if(resultCode==RESULT_OK){
                String reqString = data.getStringExtra("RESULT_DATA");
                String resultFlag = data.getStringExtra("FLAG");
                Log.i("** resultFlag **", "**Before Entering Log**" + resultFlag);
                if(resultFlag!=null && resultFlag.equalsIgnoreCase("SUCCESS")) {
                    showToast("Ticket Generated Successfully!");
                    finish();
                    startActivity(new Intent(PopupDetails.this, Respone_Print.class));

                    Log.i("** resultFlag **", "**If condition Entered Flag**" + resultFlag);
                    Log.i("** TO APP **", "**************  RESULT SUCCESS ::" + reqString);
                }else{
                    Log.i("** TO APP **", "**************  RESULT FAIL ::" + reqString);
                    Log.i("** resultFlag **", "**Else condition Entered Flag**" + resultFlag);
                    showToast("Ticket Generated Failed!");
                	}
                }
            if(resultCode==RESULT_CANCELED){
//                String reqString = data.getStringExtra("data");
                	Log.i("TRANSACTION","***** CANCELED **** ");
                	Log.i("*****IF CANCELED **** ", "******IF RESULT FAIL ::" + reqString);
            	}
			break;
		case 200 :
			 try {
				 //showToast("result:"+data.getStringExtra("Result"));
				 //showToast("IS_SUCCESS:"+data.getStringExtra("IS_SUCCESS"));
				 
				// showToast(""+data.getStringExtra("Response_Code"));
				 Log.i("Response_Code :::::::", ""+data.getStringExtra("Response_Code"));
				 Log.i("IS_SUCCESS :::::::", ""+data.getStringExtra("IS_SUCCESS"));
				 Log.i("Result :::::::", ""+data.getStringExtra("Result"));
				 
                 if (data.getStringExtra("Result").equals("") || data.getStringExtra("Result").isEmpty()) {
                	 System.out.println(" PMT_RESP_Result_EMPTY");
                    showToast("Transaction failed");

                 } else if (data.getStringExtra("Result").equals("Transaction Aborted")) {
                	 System.out.println(" PMT_RESP_Result_Transaction Aborted");
                   //showMessageDialog(ClsMainSpareFormActivity.this, "Transaction Aborted");
                   showToast("Transaction Aborted");
                   Intent abort = new Intent(PopupDetails.this, PopupDetails.class);
                   startActivity(abort);
                    sb_selected_penlist = new ArrayList<String>();
				 	ALL_selected_penlist = new ArrayList<String>();
				 	
					sb_selected_penlist.clear();
					ALL_selected_penlist.clear();
					sb_selected_penlist_positions.clear();
					Log.i("**sb_selec*", ""+ sb_selected_penlist_positions.size());
					sb_selected_penlist_send.delete(0,sb_selected_penlist_send.length());
					ALL_selected_penlist_toSend.delete(0,ALL_selected_penlist_toSend.length());
			

                 } else if (data.getStringExtra("IS_SUCCESS").equals("TRUE")) {
                	 System.out.println(" PMT_RESP_IS_SUCCESS_TRUE");
                    auth_code = data.getStringExtra("Auth_Code");
                    rrn = data.getStringExtra("RRN");
                    invoice_no = data.getStringExtra("Stan");
                    response_code = data.getStringExtra("Response_Code");
                    result = data.getStringExtra("Result") ;
                    
                    card_holder = data.getStringExtra("Card_Holder_Name") ;
                    card_type = data.getStringExtra("Card_Type") ;
                    batch_no = data.getStringExtra("Batch_Number") ;

                    Log.v("auth_code :::", ""+auth_code);
                    Log.v("rrn :::", ""+rrn);
                    Log.v("invoice_no :::", ""+invoice_no);
                    Log.v("response_code :::", ""+response_code);
                    Log.v("result :::", ""+result);
                    
                    Log.v("card_holder :::", ""+card_holder);
                    Log.v("card_type :::", ""+card_type);
                    Log.v("batch_no :::", ""+batch_no);

                    new Async_makeOnlinePayment10().execute();
                    
                    showToast(""+result);
                    Log.v("RESPONSE ::::::", ""+result);

                 } else if (data.getStringExtra("IS_SUCCESS").equals("FALSE")) {
                	 System.out.println(" PMT_RESP_IS_SUCCESS_FAILURE");
                	 result = data.getStringExtra("Result") ;
                  //showMessageDialog(ClsMainSpareFormActivity.this, data.getStringExtra("Result"));
                	 showToast(""+result);
                  

                 } else {
                	 System.out.println(" PMT_RESP_ELSE");
                	 result = data.getStringExtra("Result") ;
                    //showMessageDialog(ClsMainSpareFormActivity.this, data.getStringExtra("Result"));

                	 showToast(""+result);
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
			
			break;	
			}
		}
		
	private void showToast(String msg) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		View toastView = toast.getView();
		
		ViewGroup group = (ViewGroup) toast.getView();
	    TextView messageTextView = (TextView) group.getChildAt(0);
	    messageTextView.setTextSize(24);
		
    	//toastView.setBackgroundResource(R.drawable.toast_background);
	    toast.show();
	}
}
