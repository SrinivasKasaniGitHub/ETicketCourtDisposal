package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mtpv.eticketcourt.service.ServiceHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class VehicleHistoryPendingChallans extends Activity {

	LinearLayout ll_vhle_hstry_pending_challans;
	TextView tv_total_challans;
	TextView tv_total_amnt;
	TextView tv_sub_header;// spot or vehicle history

	final int PROGRESS_DIALOG = 0;
	final int PENDING_CHALLAN_DETAILS = 1;

	ImageLoader imageLoader;

	int pos;
	int total_amount = 0;
	CheckBox[] cb;
	LinearLayout[] ll;
	ArrayList<Boolean> detained_items_status;
	public static double total_amount_selected_challans = 0.0;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	public static StringBuffer sb_selected_penlist_send;
	public static ArrayList<String> sb_selected_penlist;
	public static ArrayList<String> sb_selected_penlist_positions;
	private StringBuffer completeSplit;

	public static String pending_challans_to_send;

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vhcle_hstry_pchallans);
		LoadUIComponents();

		sb_selected_penlist = new ArrayList<String>();
		/*
		 * //sb_selected_penlist.clear();
		 * //sb_selected_penlist_positions.clear();
		 * //Log.i("**sb_selected_penlist_positions**", ""+
		 * sb_selected_penlist_positions.size());
		 * sb_selected_penlist_send.delete(0,sb_selected_penlist_send.length());
		 */

		sb_selected_penlist_positions = new ArrayList<String>();
		sb_selected_penlist_send = new StringBuffer("");
		sb_selected_penlist_send.setLength(0);
		// sb_selected_penlist_send = new StringBuffer("");
		completeSplit = new StringBuffer("");
		completeSplit.setLength(0);

		total_amount_selected_challans = 0.0;

		if (android.os.Build.VERSION.SDK_INT > 11) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		if (ServiceHelper.pending_challans_details.length > 0) {
			detained_items_status = new ArrayList<Boolean>();

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			StringBuffer myId = new StringBuffer();

			params.setMargins(0, 0, 0, 10);
			myId.delete(0, myId.length());
			cb = new CheckBox[ServiceHelper.pending_challans_details.length];

			ll = new LinearLayout[ServiceHelper.pending_challans_details.length];
			Log.i("LL LENGHT", "" + ll.length);

			for (int i = 0; i < (ServiceHelper.pending_challans_details.length); i++) {

				ll[i] = new LinearLayout(getApplicationContext());
				ll[i].setId(i);
				ll[i].setLayoutParams(params);
				ll[i].setOrientation(LinearLayout.HORIZONTAL);

				cb[i] = new CheckBox(getApplicationContext());
				android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
				int identifier = getResources().getIdentifier(
						getApplicationContext().getPackageName() + ":drawable/custom_chec_box", null, null);
				cb[i].setText("   " + ServiceHelper.pending_challans_details[i][1].toString().trim() + "       "
						+ ServiceHelper.pending_challans_details[i][7].toString().trim());

				cb[i].setButtonDrawable(identifier);
				cb[i].setTextAppearance(getApplicationContext(), R.style.navi_text_style);
				cb[i].setId(i);
				// cb[i].setOnClickListener(onRadioButtonClick(cb[i].getId()));

				ll[i].addView(cb[i]);
				detained_items_status.add(true);

				if (SpotChallan.cardFLG) {
					Log.i("VehicleHistor:", "" + SpotChallan.cardFLG);
					SpotChallan.sb_selected_penlist
							.add("" + ServiceHelper.pending_challans_details[i][0].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][1].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][2].toString().trim()
									+ ServiceHelper.pending_challans_details[i][3].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][4].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][5].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][6].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][7].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][8].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][9].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][10].toString().trim() + "!");

					sb_selected_penlist.add("" + ServiceHelper.pending_challans_details[i][0].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][1].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][2].toString().trim()
							+ ServiceHelper.pending_challans_details[i][3].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][4].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][5].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][6].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][7].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][8].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][9].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][10].toString().trim() + "!");
				} else {
					Log.i("VehicleHis:::", "" + SpotChallan.cardFLG);
					SpotChallan.sb_selected_penlist
							.add("" + ServiceHelper.pending_challans_details[i][1].toString().trim() + "@"
									+ ServiceHelper.pending_challans_details[i][7].toString().trim() + "!");
					
					sb_selected_penlist.add("" + ServiceHelper.pending_challans_details[i][1].toString().trim() + "@"
							+ ServiceHelper.pending_challans_details[i][7].toString().trim() + "!");
				}

				Log.i("0. VEHICLE NO   	:::::", "" + ServiceHelper.pending_challans_details[i][0]);
				Log.i("1. TICKET NO    	:::::", "" + ServiceHelper.pending_challans_details[i][1]);
				Log.i("2. OFFENCE DATE 	:::::", "" + ServiceHelper.pending_challans_details[i][2]);
				Log.i("3. OFFENCE TIME 	:::::", "" + ServiceHelper.pending_challans_details[i][3]);
				Log.i("4. POINT NAME   	:::::", "" + ServiceHelper.pending_challans_details[i][4]);
				Log.i("5. PS NAME      	:::::", "" + ServiceHelper.pending_challans_details[i][5]);
				Log.i("6. OFFENCE DESC 	:::::", "" + ServiceHelper.pending_challans_details[i][6]);
				Log.i("7. CMD AMOUNT   	:::::", "" + ServiceHelper.pending_challans_details[i][7]);
				Log.i("8. IMG_EVIDENCE 	:::::", "" + ServiceHelper.pending_challans_details[i][8]);
				Log.i("9. ACMD AMOUNT 	:::::", "" + ServiceHelper.pending_challans_details[i][9]);
				Log.i("10.USER CHARGES  :::::", "" + ServiceHelper.pending_challans_details[i][10]);
				Log.i("11.UNIT CODE		:::::", "" + ServiceHelper.pending_challans_details[i][11]);

				cb[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						CheckBox check = (CheckBox) v;

						if (detained_items_status.get(check.getId()) == true) {

							detained_items_status.set(check.getId(), false);

							SpotChallan.sb_selected_penlist_positions.add("" + (cb[check.getId()].getId()));
							sb_selected_penlist_positions.add("" + (cb[check.getId()].getId()));

							total_amount_selected_challans = total_amount_selected_challans + (Double.parseDouble(
									ServiceHelper.pending_challans_details[check.getId()][7].toString().trim()));

							Log.i("total_amou::", "" + total_amount_selected_challans);
							Log.i("SpotChallan.sb_s :::",
									"" + SpotChallan.sb_selected_penlist_positions);

							tv_total_amnt.setText("Selected Total Amount : " + total_amount_selected_challans);

							SharedPreferences sharedPreferences = PreferenceManager
									.getDefaultSharedPreferences(getApplicationContext());
							SharedPreferences.Editor editors = sharedPreferences.edit();

							String pending_challanAmnt = "" + total_amount_selected_challans;
							editors.putString("PENDING_AMNT", "" + pending_challanAmnt);
							editors.putString("PENDING_DISPLAY", "" + detained_items_status);
							editors.commit();

							if (!SpotChallan.btn_violation.getText().toString()
									.equals("" + getResources().getString(R.string.select_violation))) {
								SpotChallan.tv_grand_total_spot
										.setText("Rs. " + (VehicleHistoryPendingChallans.total_amount_selected_challans
												+ SpotChallan.grand_total));
							} else {
								SpotChallan.tv_grand_total_spot.setText("");
							}
							// SpotChallan.tv_toal_amount_pending_challans.setText(""+VehicleHistoryPendingChallans.total_amount_selected_challans);
							// SpotChallan.tv_total_pending_challans.setText(""+SpotChallan.sb_selected_penlist_positions.size());

						} else if (detained_items_status.get(check.getId()) == false) {

							detained_items_status.set(check.getId(), true);

							total_amount_selected_challans = total_amount_selected_challans - (Double.parseDouble(
									ServiceHelper.pending_challans_details[check.getId()][7].toString().trim()));
							tv_total_amnt.setText("Selected Amount : " + total_amount_selected_challans);
							// sb_selected_penlist_send.append("");
							// completeSplit.append("");
							sb_selected_penlist_positions.remove("" + (cb[check.getId()].getId()));

							sb_selected_penlist_send.delete(0, sb_selected_penlist_send.length());
							sb_selected_penlist_send.setLength(0);
							completeSplit.setLength(0);

							SharedPreferences sharedPreferences = PreferenceManager
									.getDefaultSharedPreferences(getApplicationContext());
							SharedPreferences.Editor editors = sharedPreferences.edit();
							String pending_challanAmnt = "" + total_amount_selected_challans;
							editors.putString("PENDING_AMNT", "" + pending_challanAmnt);
							editors.putString("PENDING_DISPLAY", "" + detained_items_status);
							editors.commit();
						}
					}
				});

				/* IMAGE BUTTON */
				final ImageButton[] tv_detaineditem = new ImageButton[ServiceHelper.pending_challans_details.length];
				tv_detaineditem[i] = new ImageButton(getApplicationContext());
				tv_detaineditem[i].setId(i + 1);
				tv_detaineditem[i].setImageResource(R.drawable.right_arrow);
				tv_detaineditem[i].setBackgroundDrawable(null);
				params1.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
				tv_detaineditem[i].setLayoutParams(params1);
				ll[i].addView(tv_detaineditem[i]);

				ll_vhle_hstry_pending_challans.addView(ll[i]);

				tv_detaineditem[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ImageButton ibtn = (ImageButton) v;
						pos = ibtn.getId();

						removeDialog(PENDING_CHALLAN_DETAILS);
						showDialog(PENDING_CHALLAN_DETAILS);
					}
				});
			}
		}
	}

	private void LoadUIComponents() {
		// TODO Auto-generated method stub
		ll_vhle_hstry_pending_challans = (LinearLayout) findViewById(R.id.ll_vhle_hstry_pchallans_xml);
		// tv_total_challans = (TextView)
		// findViewById(R.id.tv_totalnochallans_pen_challans_xml);
		tv_total_amnt = (TextView) findViewById(R.id.tv_totalamount_pen_challans_xml);
		tv_total_amnt.setText("");

		tv_sub_header = (TextView) findViewById(R.id.textView_subheader_vechl_hstry_xml);

		if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
			tv_sub_header.setText("" + getResources().getString(R.string.vehicle_history));
		} else if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
			tv_sub_header.setText("" + getResources().getString(R.string.spot_challan));
		} else if (Dashboard.check_vhleHistory_or_Spot.equals("drunkdrive")) {
			tv_sub_header.setText("" + getResources().getString(R.string.drunk_drive));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case PROGRESS_DIALOG:
			ProgressDialog pd = ProgressDialog.show(this, "", "", true);
			pd.setContentView(R.layout.custom_progress_dialog);
			pd.setCancelable(false);

			return pd;
		case PENDING_CHALLAN_DETAILS:
			Dialog dg_details = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
			dg_details.setContentView(R.layout.pending_challans_details);

			TextView tv_detailed_pen_challan_header = (TextView) dg_details
					.findViewById(R.id.textView_penchallandetails_header);
			tv_detailed_pen_challan_header.setText("" + getResources().getString(R.string.vehicle_history));
			TextView tv_vhle_no_details = (TextView) dg_details.findViewById(R.id.tvregno_pchallans_details_xml);
			TextView tv_etckt_no_details = (TextView) dg_details.findViewById(R.id.tveticketno_pchallans_details_xml);
			TextView tv_offencedate_details = (TextView) dg_details
					.findViewById(R.id.tv_offencedate_pchallans_details_xml);
			TextView tv_offcencetime_details = (TextView) dg_details
					.findViewById(R.id.tv_offencetime_pchallans_details_xml);
			TextView tv_pointname_details = (TextView) dg_details.findViewById(R.id.tv_pointname_pchallans_details_xml);
			TextView tv_psname_details = (TextView) dg_details.findViewById(R.id.tv_psname_pchallans_details_xml);
			TextView tv_offence_desc_details = (TextView) dg_details
					.findViewById(R.id.tv_offencedesc_pchallans_details_xml);
			TextView tv_amnt_details = (TextView) dg_details.findViewById(R.id.tv_amnt_pchallans_details_xml);
			Button btn_ok_details = (Button) dg_details.findViewById(R.id.btn_ok_pchallans_details_xml);
			ImageView img_display = (ImageView) dg_details.findViewById(R.id.imgv_pchallans_details_xml);
			int newpos = pos - 1;

			tv_vhle_no_details.setText("" + (ServiceHelper.pending_challans_details[newpos][0].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][0]));
			tv_etckt_no_details.setText("" + (ServiceHelper.pending_challans_details[newpos][1].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][1]));
			tv_offencedate_details.setText("" + (ServiceHelper.pending_challans_details[newpos][2].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][2]));
			tv_offcencetime_details.setText("" + (ServiceHelper.pending_challans_details[newpos][3].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][3]));
			tv_pointname_details.setText("" + (ServiceHelper.pending_challans_details[newpos][4].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][4]));
			tv_psname_details.setText("" + (ServiceHelper.pending_challans_details[newpos][5].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][5]));
			tv_offence_desc_details.setText("" + (ServiceHelper.pending_challans_details[newpos][6].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][6]));
			tv_amnt_details.setText("Rs : " + (ServiceHelper.pending_challans_details[newpos][7].equals("0") ? " "
					: ServiceHelper.pending_challans_details[newpos][7]));

			if (!ServiceHelper.pending_challans_details[newpos][8].toString().trim().equals("0")) {
				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
						.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
						.discCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO).build();
				ImageLoader.getInstance().init(config);

				imageLoader = ImageLoader.getInstance();

				imageLoader.displayImage(ServiceHelper.pending_challans_details[newpos][8].toString().trim(),
						img_display);
			}

			btn_ok_details.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					removeDialog(PENDING_CHALLAN_DETAILS);
				}
			});

			return dg_details;

		}
		return super.onCreateDialog(id);
	}

	public Boolean isOnline() {
		ConnectivityManager conManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
		return nwInfo != null;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		TextView title = new TextView(this);
		title.setText("Challans Selected");
		title.setBackgroundColor(Color.RED);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(26);
		title.setTypeface(title.getTypeface(), Typeface.BOLD);
		title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
		title.setPadding(20, 0, 20, 0);
		title.setHeight(70);

		if (sb_selected_penlist_positions.size() > 0) {
			for (int i = 0; i < sb_selected_penlist_positions.size(); i++) {
				sb_selected_penlist_send
						.append("" + sb_selected_penlist.get(Integer.parseInt(sb_selected_penlist_positions.get(i))));
			}
		}
		if (sb_selected_penlist_send == null && sb_selected_penlist_send.toString().equals("")) {
			sb_selected_penlist_send.append("");
			sb_selected_penlist_send.setLength(0);
		}

		String to_splitData = "" + sb_selected_penlist_send.toString();
		completeSplit.setLength(0);

		String[] split1 = to_splitData.split("\\!");
		String ticket = null;
		String amount = null;
		int count = 0;

		/*
		 * for (int i = 0; i < split1.length; i++) { Log.i("**split1 MASTER***",
		 * ""+ split1[i]); count++; String[] challanSelected =
		 * split1[i].split("\\@");
		 * 
		 * for (int j = 0; j < challanSelected.length; j++) { if (j==1) { ticket
		 * = challanSelected[1]; }else if (j==9) { amount = challanSelected[8];
		 * } }
		 * completeSplit.append("\t\t"+count+". "+ticket+"\t\t"+amount+"\n");
		 * Log.i("**completeSplit MASTER***", ""+ completeSplit.toString()); }
		 */
		try {

			for (int i = 0; i < split1.length; i++) {
				Log.i("**split1 MASTER***", "" + split1[i]);
				// HYD00TE171000076@100@HYD00TE171000077@100@HYD17TE171001924@100@HYD00TE171000080@100@

				Log.i("count", "" + count);
				String[] challanSelected = split1[i].split("\\@");

				for (int j = 0, k = 1; j < challanSelected.length; j++, k++) {
					count++;
					/*
					 * if (j == 1) { ticket = challanSelected[0]; amount =
					 * challanSelected[1]; Log.i("ticket", ""+ticket);
					 * Log.i("amount", ""+amount); } else if (j == 9) { amount =
					 * challanSelected[8]; Log.i("amount", ""+amount); }
					 */
					Log.i("i value ", "" + i);
					Log.i("j value ", "" + j);
					Log.i("k value", "" + k);
					ticket = challanSelected[j];
					amount = challanSelected[k];
					Log.i("ticket", "" + ticket);
					Log.i("amount", "" + amount);
					j++;
					k++;

					completeSplit.append("\t\t" + count + "." + ticket + "\t\t" + amount + "\n");
					Log.i("**comple*", "" + completeSplit.toString());
					pending_challans_to_send = completeSplit.toString();

					// ticketno@anount!ticketno@anount
				}
				// completeSplit.append("\t\t" + count + "." + ticket + "\t\t" +
				// amount + "\n");
				/*
				 * completeSplit.append("\t\t" + count + "." + ticket + "\t\t" +
				 * amount + "\n"); Log.i("**completeSplit MASTER***", "" +
				 * completeSplit.toString());
				 */
			}

		} catch (Exception e) {
			// TODO: handle exception
			completeSplit.setLength(0);
		}

		if (completeSplit != null && ticket != null && amount != null) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VehicleHistoryPendingChallans.this,
					AlertDialog.THEME_HOLO_LIGHT);
			alertDialogBuilder.setCustomTitle(title);
			alertDialogBuilder.setIcon(R.drawable.dialog_logo);
			alertDialogBuilder.setMessage(completeSplit.toString());
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					sb_selected_penlist_send.setLength(0);
					completeSplit.setLength(0);
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			alertDialog.getWindow().getAttributes();

			TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
			textView.setTextSize(28);
			textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
			textView.setGravity(Gravity.LEFT);

			Button btn1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
			btn1.setTextSize(22);
			btn1.setTextColor(Color.WHITE);
			btn1.setTypeface(btn1.getTypeface(), Typeface.BOLD);
			btn1.setBackgroundColor(Color.RED);

			Button btn2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
			btn2.setTextSize(22);
			btn2.setTextColor(Color.WHITE);
			btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
			btn2.setBackgroundColor(Color.RED);
		} else {
			finish();
		}
	}
}
