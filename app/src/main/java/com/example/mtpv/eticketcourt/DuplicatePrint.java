package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.ServiceHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mtpv on 6/19/2017.
 */

public  class DuplicatePrint extends AppCompatActivity implements View.OnClickListener {

    // TextView tv_print_res;
    // TextView tv_title_display;

    // Button btn_re_print;
    // Button btn_print_type;// select date ; select app type

    int selected_type = -1;
    int pos;

    DBHelper db;
    Cursor c, printer_cursor;
    ArrayList<String> print_respose, print_apptype;

    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;
    TelephonyManager telephonyManager;
    String imei_send;
    String simid_send;
    String present_date_toSend;
    String netwrk_info_txt;

    final int APPTYPE_DIALOG = 0;
    final int PRESENT_DATE_PICKER = 1;
    final int PROGRESS_DIALOG = 2;
    final int REPORT_TYPE = 3;
    String online_report_status = "";

    private static final int REQUEST_ENABLE_BT = 1;

    AnalogicsThermalPrinter actual_printer = new AnalogicsThermalPrinter();
    Bluetooth_Printer_3inch_ThermalAPI bth_printer = new Bluetooth_Printer_3inch_ThermalAPI();

    BluetoothAdapter bluetoothAdapter;
    @SuppressWarnings("unused")
    private BluetoothAdapter mBluetoothAdapter = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String address = "";

    /*-------------------------REPORT TYPE----------------------*/
    String report_id_send = "";
    // Button btn_report_type;

    /*----------REPORT ARRAY-------*/
    String day_report = "Day Report";
    String officer_perfmnce = "Officer Performance";
    String wheeler_based = "Wheeler Based";
    String violation_based = "Violations Based Report";
    String[] report_type_arr = { day_report, officer_perfmnce, wheeler_based, violation_based };
    int selected_report_type = -1;

    /*-------------------------------------------------------------*/
	/*----REPORT----*/
    Button btn_report_type;
    Button btn_report_dateselection;
    TextView tv_report_text;
    Button btn_print_report;

    /*----DUPLICATE PRINT----*/
    EditText et_dp_regno;
    Button btn_dp_date_selection;
    Button btn_dp_get_onlinedetials;
    Button btn_dp_apptype_selection;
    TextView tv_dp_text;
    Button btn_print_dp;
    TextView compny_Name;

	/*-------------------------------------------------------------*/

    public static String printer_addrss, printer_name;

    @SuppressWarnings("deprecation")
    @SuppressLint("WorldReadableFiles")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {
            setContentView(R.layout.reports);
            loadReportComponents();
            compny_Name = (TextView) findViewById(R.id.CompanyName);
            Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
            compny_Name.startAnimation(marquee);
        } else {
            setContentView(R.layout.duplicateprint);
            loadDuplicateComponents();
            compny_Name = (TextView) findViewById(R.id.CompanyName);
            Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
            compny_Name.startAnimation(marquee);
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        cal = Calendar.getInstance();
		/* FOR DATE PICKER */
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);

        preferences = getSharedPreferences("preferences", MODE_WORLD_READABLE);
        editor = preferences.edit();
        address = preferences.getString("btaddress", "btaddr");

        Log.i("BT address :::::", "" + address);

        netwrk_info_txt = "" + getResources().getString(R.string.newtork_txt);
    }

    /*------------FOR REPORTS-----------------*/
    private void loadReportComponents() {
        // TODO Auto-generated method stub
        btn_report_type = (Button) findViewById(R.id.btnselect_report_type_xml);
        btn_report_dateselection = (Button) findViewById(R.id.btnselect_dateselection_report_xml);
        tv_report_text = (TextView) findViewById(R.id.tvreport_res_xml);
        btn_print_report = (Button) findViewById(R.id.btnprint_report_xml);

        btn_report_type.setOnClickListener(this);
        btn_report_dateselection.setOnClickListener(this);
        btn_print_report.setOnClickListener(this);

        tv_report_text.setVisibility(View.GONE);
        btn_print_report.setVisibility(View.GONE);
    }

    /*------------FOR DUPLCAITE PRINT-----------------*/
    private void loadDuplicateComponents() {
        // TODO Auto-generated method stub
        et_dp_regno = (EditText) findViewById(R.id.edt_regno_dp_xml);
        btn_dp_date_selection = (Button) findViewById(R.id.btn_dateselection_dp_xml);
        btn_dp_get_onlinedetials = (Button) findViewById(R.id.btngetdetails_dp_xml);
        btn_dp_apptype_selection = (Button) findViewById(R.id.btnselect_app_type_dup_xml);
        tv_dp_text = (TextView) findViewById(R.id.tvdup_res_xml);
        btn_print_dp = (Button) findViewById(R.id.btnprint_dup_res_xml);

        btn_dp_date_selection.setOnClickListener(this);
        btn_dp_get_onlinedetials.setOnClickListener(this);
        btn_dp_apptype_selection.setOnClickListener(this);
        btn_print_dp.setOnClickListener(this);

        tv_dp_text.setVisibility(View.GONE);
        btn_print_dp.setVisibility(View.GONE);

        print_respose = new ArrayList<String>();
        print_apptype = new ArrayList<String>();

		/* FOR DUPLICATE PRINT APP TYPE IS TAKEN FROM DATABASE */
        db = new DBHelper(getApplicationContext());
        try {
            db.open();
            c = DBHelper.db.rawQuery("select * from " + DBHelper.duplicatePrint_table, null);
            Log.i("**DUP PRINT***", "" + c.getCount());

            if (c.getCount() == 0) {
                // showToast("No Duplicate Records Found!");
                // this.finish();
            } else {
                Log.i("Duplicate Records Len", "" + c.getCount());

                while (c.moveToNext()) {
                    Log.i("Duplicate Records count", "" + c.getCount());
                    print_respose.add(c.getString(c.getColumnIndex(DBHelper.dup_print_respnse)));
                    print_apptype.add(c.getString(c.getColumnIndex(DBHelper.dup_print_app_type)));

                    Log.i("Duplicate Rec",
                            "" + c.getString(c.getColumnIndex(DBHelper.dup_print_app_type)));

                }

            }
            c.close();
            db.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            c.close();
            db.close();
        }

        try {
            android.database.sqlite.SQLiteDatabase db = openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE,
                    null);
            String selectQuery = "SELECT  * FROM " + DBHelper.BT_PRINTER_TABLE;
            // SQLiteDatabase db = this.getWritableDatabase();
            printer_cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (printer_cursor.moveToFirst()) {
                do {
                    printer_addrss = printer_cursor.getString(1);
                    printer_name = printer_cursor.getString(2);

                    Log.i("printer_addrss :", "" + printer_cursor.getString(1));
                    Log.i("printer_name :", "" + printer_cursor.getString(2));

                    address = printer_addrss;

                } while (printer_cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (printer_cursor != null) {
                printer_cursor.close();
            }
        }

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

		/* REPORT PRINT */
            case R.id.btnprint_report_xml:
                new Aysnc_Print().execute();
                // printResponse();
                break;

		/* DUPLICATE PRINT */
            case R.id.btnprint_dup_res_xml:
                new Aysnc_Print().execute();
                // printResponse();
                break;

            case R.id.btnselect_dateselection_report_xml:
			/* FOR REPORTS WE NEED TO SELECT DATE SELECTION */
                if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {
                    showDialog(PRESENT_DATE_PICKER);
                }
                break;

            case R.id.btnselect_report_type_xml:
                report_id_send = "";
                showDialog(REPORT_TYPE);
                break;

		/*------DUPLICATE PRINT------------*/
            case R.id.btn_dateselection_dp_xml:
                showDialog(PRESENT_DATE_PICKER);
                break;

            case R.id.btnselect_app_type_dup_xml:
			/*----FOR DUPLICATE RECORDS NEED TO SELECT APP TYPE----*/
                if (print_respose.size() > 0) {
                    online_report_status = "";
                    showDialog(APPTYPE_DIALOG);
                } else {
                    showToast("No Latest Records");
                }
                break;

            case R.id.btngetdetails_dp_xml:
                tv_dp_text.setVisibility(View.GONE);
                btn_print_dp.setVisibility(View.GONE);

                if (et_dp_regno.getText().toString().trim().equals("")) {
                    // et_dp_regno.setError("Enter Regno.");
                    et_dp_regno.setError(Html.fromHtml("<font color='black'>Enter Vehicle No</font>"));
                } else if (btn_dp_date_selection.getText().toString().equals("Select Date")) {
                    showToast("Select Report Date");
                } else {
                    if (isOnline()) {
                        new Async_getOnlineDuplicatePrint().execute();
                    } else {
                        showToast("OffLineMeassage" + netwrk_info_txt);
                    }

                }
                break;

            default:
                break;
        }
    }

    public class Aysnc_Print extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // printResponse();
            if (bluetoothAdapter == null) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        showToast("Bluetooth NOT support");
                    }
                });
            } else {
                if (bluetoothAdapter.isEnabled()) {
                    if (bluetoothAdapter.isDiscovering()) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                showToast("Bluetooth is currently in device discovery process.");
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                showToast("Bluetooth is Enabled");
                            }
                        });

                        Log.i("PRINT FROM", "" + Dashboard.check_vhleHistory_or_Spot);

                        if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {
                            if ((Dashboard.check_vhleHistory_or_Spot.equals("reports"))
                                    && (!ServiceHelper.Opdata_Chalana.equals("NA"))) {

                                try {
									/*
									 * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 */

                                    Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                    String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                    actual_printer.openBT(address);

                                    actual_printer.printData(print_data);
                                    Thread.sleep(5000);
                                    actual_printer.closeBT();
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            showToast("Check Your Device is Working Condition!");
                                        }
                                    });

                                }
                            }
                        } else {

                            if (address.equals("btaddr")) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        showToast("Check Bluetooth Details!");
                                    }
                                });

                            } else if (bluetoothAdapter.isEnabled()) {

                                if (online_report_status.equals("yes")) {
                                    Log.i("ONLINE PRINT", "ONLINE PRINT");

                                    try {
                                        Log.i("ONLINE PRINT", "ONLINE PRINT");
										/*
										 * String printdata =
										 * bth_printer.font_Courier_41(""+
										 * ServiceHelper.Opdata_Chalana);
										 * actual_printer.Call_PrintertoPrint(
										 * ""+ address, "" + printdata);
										 */

                                        Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                        String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                        actual_printer.openBT(address);

                                        actual_printer.printData(print_data);
                                        Thread.sleep(5000);
                                        actual_printer.closeBT();
                                    } catch (Exception e) {
                                        runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method
                                                // stub
                                                showToast("Check Your Device is Working Condition!");
                                            }
                                        });

                                    }
                                } else {
                                    if (print_apptype.size() > 0) {
                                        if ((print_apptype.get(selected_type).toString().trim()
                                                .equals("" + getResources().getString(R.string.dup_drunk_drive)))
                                                && (!print_respose.get(selected_type).equals(""))) {
                                            try {
												/*
												 * String printdata =
												 * bth_printer.font_Courier_41(
												 * ""+ print_respose.get(
												 * selected_type));
												 * actual_printer.
												 * Call_PrintertoPrint("" +
												 * address, "" + printdata);
												 */

                                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                                String print_data = printer
                                                        .font_Courier_41("" + print_respose.get(selected_type));
                                                actual_printer.openBT(address);

                                                actual_printer.printData(print_data);
                                                Thread.sleep(5000);
                                                actual_printer.closeBT();
                                            } catch (Exception e) {
                                                runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated
                                                        // method stub
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }

                                        } else if ((print_apptype.get(selected_type).toString().trim()
                                                .equals("" + getResources().getString(R.string.dup_spot_challan)))
                                                && (!print_respose.get(selected_type).equals(""))) {
                                            try {
												/*
												 * String printdata =
												 * bth_printer.font_Courier_41(
												 * ""+ print_respose.get(
												 * selected_type));
												 * actual_printer.
												 * Call_PrintertoPrint("" +
												 * address, ""+ printdata);
												 */

                                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                                String print_data = printer
                                                        .font_Courier_41("" + print_respose.get(selected_type));
                                                actual_printer.openBT(address);

                                                actual_printer.printData(print_data);
                                                Thread.sleep(5000);
                                                actual_printer.closeBT();
                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated
                                                        // method stub
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }
                                        } else if ((print_apptype.get(selected_type).toString().trim()
                                                .equals("" + getResources().getString(R.string.dup_vhcle_hstry)))
                                                && (!print_respose.get(selected_type).equals(""))) {

                                            try {
												/*
												 * String printdata =
												 * bth_printer.font_Courier_41(
												 * ""+ print_respose.get(
												 * selected_type));
												 * actual_printer.
												 * Call_PrintertoPrint("" +
												 * address, ""+ printdata);
												 */
                                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                                String print_data = printer
                                                        .font_Courier_41("" + print_respose.get(selected_type));
                                                actual_printer.openBT(address);

                                                actual_printer.printData(print_data);
                                                Thread.sleep(5000);
                                                actual_printer.closeBT();
                                            } catch (Exception e) {
                                                runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated
                                                        // method stub
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }
                                        } else if ((print_apptype.get(selected_type).toString().trim()
                                                .equals("" + getResources().getString(R.string.towing_one_line)))
                                                && (!print_respose.get(selected_type).equals(""))) {

                                            try {
												/*
												 * String printdata =
												 * bth_printer.font_Courier_41(
												 * ""+ print_respose.get(
												 * selected_type));
												 * actual_printer.
												 * Call_PrintertoPrint("" +
												 * address, ""+ printdata);
												 */

                                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                                String print_data = printer
                                                        .font_Courier_41("" + print_respose.get(selected_type));
                                                actual_printer.openBT(address);

                                                actual_printer.printData(print_data);
                                                Thread.sleep(5000);
                                                actual_printer.closeBT();
                                            } catch (Exception e) {
                                                runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated
                                                        // method stub
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }
                                        } else if ((print_apptype.get(selected_type).toString().trim().equals(
                                                "" + getResources().getString(R.string.release_documents_one_line)))
                                                && (!print_respose.get(selected_type).equals(""))) {

                                            try {
												/*
												 * String printdata =
												 * bth_printer.font_Courier_41(
												 * ""+ print_respose.get(
												 * selected_type));
												 * actual_printer.
												 * Call_PrintertoPrint("" +
												 * address, ""+ printdata);
												 */

                                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                                String print_data = printer
                                                        .font_Courier_41("" + print_respose.get(selected_type));
                                                actual_printer.openBT(address);

                                                actual_printer.printData(print_data);
                                                Thread.sleep(5000);
                                                actual_printer.closeBT();
                                            } catch (Exception e) {
                                                runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated
                                                        // method stub
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }
                } else {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                    if (address.equals("btaddr")) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                showToast("Check Bluetooth Details!");
                            }
                        });
                    } else if (bluetoothAdapter.isEnabled()) {

                        Log.i("SECOND  CASE", print_apptype.get(selected_type).toString().trim() + ""
                                + getResources().getString(R.string.dup_drunk_drive));

                        if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {
                            if ((Dashboard.check_vhleHistory_or_Spot.equals("reports"))
                                    && (!ServiceHelper.Opdata_Chalana.equals("NA"))) {

                                try {
									/*
									 * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 */
                                    Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                    String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                    actual_printer.openBT(address);

                                    actual_printer.printData(print_data);
                                    Thread.sleep(5000);
                                    actual_printer.closeBT();
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            showToast("Check Your Device is Working Condition!");
                                        }
                                    });
                                }
                            }
                        } else {

                            if (online_report_status.equals("yes")) {
                                Log.i("ONLINE PRINT", "ONLINE PRINT");

                                try {
									/*
									 * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 */

                                    Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                    String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                    actual_printer.openBT(address);

                                    actual_printer.printData(print_data);
                                    Thread.sleep(5000);
                                    actual_printer.closeBT();
                                } catch (Exception e) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            showToast("Check Your Device is Working Condition!");
                                        }
                                    });
                                }
                            } else {
                                if (print_apptype.size() > 0) {
                                    if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.dup_drunk_drive)))
                                            && (!print_respose.get(selected_type).equals(""))) {
                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    showToast("Check Your Device is Working Condition!");
                                                }
                                            });
                                        }

                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.dup_spot_challan)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    showToast("Check Your Device is Working Condition!");
                                                }
                                            });
                                        }

                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.dup_vhcle_hstry)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 */
                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    showToast("Check Your Device is Working Condition!");
                                                }
                                            });
                                        }
                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.towing_one_line)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    showToast("Check Your Device is Working Condition!");
                                                }
                                            });
                                        }
                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.release_documents_one_line)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 */
                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    // TODO Auto-generated
                                                    // method stub
                                                    showToast("Check Your Device is Working Condition!");
                                                }
                                            });
                                        }
                                    }
                                }

                            }

                        }
                    }
                }

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

        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch (id) {
            case APPTYPE_DIALOG:
                TextView title2 = new TextView(this);
                title2.setText("Select Application");
                title2.setBackgroundColor(Color.parseColor("#007300"));
                title2.setGravity(Gravity.CENTER);
                title2.setTextColor(Color.WHITE);
                title2.setTextSize(26);
                title2.setTypeface(title2.getTypeface(), Typeface.BOLD);
                title2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
                title2.setPadding(20, 0, 20, 0);
                title2.setHeight(70);

                AlertDialog.Builder ad_app_type = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
                ad_app_type.setCustomTitle(title2);
                ad_app_type.setSingleChoiceItems(print_apptype.toArray(new String[print_apptype.size()]), selected_type,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                selected_type = which;

                                removeDialog(APPTYPE_DIALOG);
                                if ((print_apptype.get(which).toString().trim()
                                        .equals("" + getResources().getString(R.string.dup_drunk_drive)))
                                        && (!print_respose.get(which).equals(""))) {
                                    tv_dp_text.setText("" + print_respose.get(which));
                                } else if ((print_apptype.get(which).toString().trim()
                                        .equals("" + getResources().getString(R.string.dup_spot_challan)))
                                        && (!print_respose.get(which).equals(""))) {
                                    tv_dp_text.setText("" + print_respose.get(which));

                                } else if ((print_apptype.get(which).toString().trim()
                                        .equals("" + getResources().getString(R.string.dup_vhcle_hstry)))
                                        && (!print_respose.get(which).equals(""))) {
                                    tv_dp_text.setText("" + print_respose.get(which));
                                } else if ((print_apptype.get(which).toString().trim()
                                        .equals("" + getResources().getString(R.string.towing_one_line)))
                                        && (!print_respose.get(which).equals(""))) {
                                    tv_dp_text.setText("" + print_respose.get(which));

                                } else if ((print_apptype.get(which).toString().trim()
                                        .equals("" + getResources().getString(R.string.release_documents_one_line)))
                                        && (!print_respose.get(which).equals(""))) {
                                    tv_dp_text.setText("" + print_respose.get(which));

                                }
                                tv_dp_text.setVisibility(View.VISIBLE);
                                btn_print_dp.setVisibility(View.VISIBLE);
                            }
                        });

                Dialog dg_app = ad_app_type.create();
                return dg_app;
            case PRESENT_DATE_PICKER:
                DatePickerDialog dp_offence_date = new DatePickerDialog(this, md1, present_year, present_month,
                        present_day);

                dp_offence_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offence_date;
            case PROGRESS_DIALOG:
                ProgressDialog pd = ProgressDialog.show(this, "", "Please Wait...", true);
                pd.setContentView(R.layout.custom_progress_dialog);
                pd.setCancelable(false);
                return pd;
            case REPORT_TYPE:
                TextView title = new TextView(this);
                title.setText("Report Type");
                title.setBackgroundColor(Color.parseColor("#007300"));
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(26);
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
                title.setPadding(20, 0, 20, 0);
                title.setHeight(70);

                AlertDialog.Builder ad_rp_type = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
                ad_rp_type.setCustomTitle(title);
                ad_rp_type.setSingleChoiceItems(report_type_arr, selected_report_type,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                selected_report_type = which;
                                btn_report_type.setText("" + report_type_arr[which].toString());
                                removeDialog(REPORT_TYPE);
                                btn_report_dateselection.setText("" + getResources().getString(R.string.select_date));
                                if (report_type_arr[which].equals(day_report)) {
                                    report_id_send = "1";

                                } else if (report_type_arr[which].equals(officer_perfmnce)) {
                                    report_id_send = "2";

                                } else if (report_type_arr[which].equals(wheeler_based)) {
                                    report_id_send = "3";

                                } else if (report_type_arr[which].equals(violation_based)) {
                                    report_id_send = "4";

                                }

                                tv_report_text.setVisibility(View.GONE);
                                btn_print_report.setVisibility(View.GONE);
                            }
                        });

                Dialog rp_type = ad_rp_type.create();
                return rp_type;

            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    // /*------------TO SEND TO officer_perfmnce,wheeler_based,
    // violation_based----------------*/
    // // TODO : REMOVED : 29-06-2015 by Madhusudhan
    // @SuppressWarnings("deprecation")
    // @SuppressLint("SimpleDateFormat")
    // protected void getPresentDateToSend() {
    // // TODO Auto-generated method stub
    // format = new SimpleDateFormat("dd-MMM-yyyy");
    // present_date_toSend = "";
    // present_date_toSend = format.format(new Date(present_year - 1900,
    // (present_month), present_day));
    //
    // }

    /*--------COMMOON METHOD FOR REPORT AND DUPLICATE METHOD-----------*/
    public void printResponse() {
        if (bluetoothAdapter == null) {
            showToast("Bluetooth NOT support");
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    showToast("Bluetooth is currently in device discovery process.");
                } else {
                    showToast("Bluetooth is Enabled");
                    Log.i("PRINT FROM", "" + Dashboard.check_vhleHistory_or_Spot);

                    if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {
                        if ((Dashboard.check_vhleHistory_or_Spot.equals("reports"))
                                && (!ServiceHelper.Opdata_Chalana.equals("NA"))) {

                            try {
								/*
								 * String printdata =
								 * bth_printer.font_Courier_41(""+
								 * ServiceHelper.Opdata_Chalana);
								 * actual_printer.Call_PrintertoPrint("" +
								 * address, "" + printdata);
								 */
                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                actual_printer.openBT(address);

                                actual_printer.printData(print_data);
                                Thread.sleep(5000);
                                actual_printer.closeBT();
                            } catch (Exception e) {
                                showToast("Check Your Device is Working!");
                            }
                        }
                    } else {

                        if (address.equals("btaddr")) {
                            showToast("Check Bluetooth Details!");
                        } else if (bluetoothAdapter.isEnabled()) {

                            if (online_report_status.equals("yes")) {
                                Log.i("ONLINE PRINT", "ONLINE PRINT");

                                try {
                                    Log.i("ONLINE PRINT", "ONLINE PRINT");
									/*
									 * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint(""+
									 * address, "" + printdata);
									 */

                                    Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                    String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                    actual_printer.openBT(address);

                                    actual_printer.printData(print_data);
                                    Thread.sleep(5000);
                                    actual_printer.closeBT();
                                } catch (Exception e) {
                                    showToast("Check Your Device is Working!");
                                }
                            } else {
                                if (print_apptype.size() > 0) {
                                    if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.dup_drunk_drive)))
                                            && (!print_respose.get(selected_type).equals(""))) {
                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint("" + address,
											 * "" + printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            showToast("Check Your Device is Working!");
                                        }

                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.dup_spot_challan)))
                                            && (!print_respose.get(selected_type).equals(""))) {
                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint("" + address,
											 * ""+ printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            showToast("Check Your Device is Working!");
                                        }
                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.dup_vhcle_hstry)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint("" + address,
											 * ""+ printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            showToast("Check Your Device is Working!");
                                        }
                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.towing_one_line)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint("" + address,
											 * ""+ printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            showToast("Check Your Device is Working!");
                                        }
                                    } else if ((print_apptype.get(selected_type).toString().trim()
                                            .equals("" + getResources().getString(R.string.release_documents_one_line)))
                                            && (!print_respose.get(selected_type).equals(""))) {

                                        try {
											/*
											 * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint("" + address,
											 * ""+ printdata);
											 */

                                            Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                            String print_data = printer
                                                    .font_Courier_41("" + print_respose.get(selected_type));
                                            actual_printer.openBT(address);

                                            actual_printer.printData(print_data);
                                            Thread.sleep(5000);
                                            actual_printer.closeBT();
                                        } catch (Exception e) {
                                            showToast("Check Your Device is Working!");
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                if (address.equals("btaddr")) {
                    showToast("Check Bluetooth Details!");
                } else if (bluetoothAdapter.isEnabled()) {

                    Log.i("SECOND  CASE", print_apptype.get(selected_type).toString().trim() + ""
                            + getResources().getString(R.string.dup_drunk_drive));

                    if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {
                        if ((Dashboard.check_vhleHistory_or_Spot.equals("reports"))
                                && (!ServiceHelper.Opdata_Chalana.equals("NA"))) {

                            try {
								/*
								 * String printdata =
								 * bth_printer.font_Courier_41(""+
								 * ServiceHelper.Opdata_Chalana);
								 * actual_printer.Call_PrintertoPrint("" +
								 * address, "" + printdata);
								 */

                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                actual_printer.openBT(address);

                                actual_printer.printData(print_data);
                                Thread.sleep(5000);
                                actual_printer.closeBT();
                            } catch (Exception e) {
                                showToast("Check Your Device is Working!");
                            }
                        }
                    } else {

                        if (online_report_status.equals("yes")) {
                            Log.i("ONLINE PRINT", "ONLINE PRINT");

                            try {
								/*
								 * String printdata =
								 * bth_printer.font_Courier_41(""+
								 * ServiceHelper.Opdata_Chalana);
								 * actual_printer.Call_PrintertoPrint("" +
								 * address, "" + printdata);
								 */

                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                String print_data = printer.font_Courier_41("" + ServiceHelper.Opdata_Chalana);
                                actual_printer.openBT(address);

                                actual_printer.printData(print_data);
                                Thread.sleep(5000);
                                actual_printer.closeBT();
                            } catch (Exception e) {
                                showToast("Check Your Device is Working!");
                            }
                        } else {
                            if (print_apptype.size() > 0) {
                                if ((print_apptype.get(selected_type).toString().trim()
                                        .equals("" + getResources().getString(R.string.dup_drunk_drive)))
                                        && (!print_respose.get(selected_type).equals(""))) {
                                    try {
										/*
										 * String printdata =
										 * bth_printer.font_Courier_41(""+
										 * print_respose.get(selected_type));
										 * actual_printer.Call_PrintertoPrint(
										 * ""+ address, "" + printdata);
										 */

                                        Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                        String print_data = printer
                                                .font_Courier_41("" + print_respose.get(selected_type));
                                        actual_printer.openBT(address);

                                        actual_printer.printData(print_data);
                                        Thread.sleep(5000);
                                        actual_printer.closeBT();
                                    } catch (Exception e) {
                                        showToast("Check Your Device is Working!");
                                    }

                                } else if ((print_apptype.get(selected_type).toString().trim()
                                        .equals("" + getResources().getString(R.string.dup_spot_challan)))
                                        && (!print_respose.get(selected_type).equals(""))) {

                                    try {
										/*
										 * String printdata =
										 * bth_printer.font_Courier_41(""+
										 * print_respose.get(selected_type));
										 * actual_printer.Call_PrintertoPrint(
										 * ""+ address, "" + printdata);
										 */

                                        Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                        String print_data = printer
                                                .font_Courier_41("" + print_respose.get(selected_type));
                                        actual_printer.openBT(address);

                                        actual_printer.printData(print_data);
                                        Thread.sleep(5000);
                                        actual_printer.closeBT();
                                    } catch (Exception e) {
                                        showToast("else VH catch");
                                        showToast("Check Your Device is Working!");
                                    }

                                } else if ((print_apptype.get(selected_type).toString().trim()
                                        .equals("" + getResources().getString(R.string.dup_vhcle_hstry)))
                                        && (!print_respose.get(selected_type).equals(""))) {

                                    try {
										/*
										 * String printdata =
										 * bth_printer.font_Courier_41(""+
										 * print_respose.get(selected_type));
										 * actual_printer.Call_PrintertoPrint(
										 * ""+ address, "" + printdata);
										 */

                                        Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                        String print_data = printer
                                                .font_Courier_41("" + print_respose.get(selected_type));
                                        actual_printer.openBT(address);

                                        actual_printer.printData(print_data);
                                        Thread.sleep(5000);
                                        actual_printer.closeBT();
                                    } catch (Exception e) {
                                        showToast("Check Your Device is Working!");
                                    }
                                } else if ((print_apptype.get(selected_type).toString().trim()
                                        .equals("" + getResources().getString(R.string.towing_one_line)))
                                        && (!print_respose.get(selected_type).equals(""))) {

                                    try {
										/*
										 * String printdata =
										 * bth_printer.font_Courier_41(""+
										 * print_respose.get(selected_type));
										 * actual_printer.Call_PrintertoPrint(
										 * ""+ address, "" + printdata);
										 */

                                        Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                        String print_data = printer
                                                .font_Courier_41("" + print_respose.get(selected_type));
                                        actual_printer.openBT(address);

                                        actual_printer.printData(print_data);
                                        Thread.sleep(5000);
                                        actual_printer.closeBT();
                                    } catch (Exception e) {
                                        showToast("Check Your Device is Working!");
                                    }
                                } else if ((print_apptype.get(selected_type).toString().trim()
                                        .equals("" + getResources().getString(R.string.release_documents_one_line)))
                                        && (!print_respose.get(selected_type).equals(""))) {

                                    try {
										/*
										 * String printdata =
										 * bth_printer.font_Courier_41(""+
										 * print_respose.get(selected_type));
										 * actual_printer.Call_PrintertoPrint(
										 * ""+ address, "" + printdata);
										 */

                                        Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                        String print_data = printer
                                                .font_Courier_41("" + print_respose.get(selected_type));
                                        actual_printer.openBT(address);

                                        actual_printer.printData(print_data);
                                        Thread.sleep(5000);
                                        actual_printer.closeBT();
                                    } catch (Exception e) {
                                        showToast("Check Your Device is Working!");
                                    }
                                }
                            }

                        }

                    }
                }
            }

        }
    }

    private void getSimImeiNo() {
        // TODO Auto-generated method stub

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei_send = telephonyManager.getDeviceId();// TO GET IMEI NUMBER

        if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
            simid_send = "" + telephonyManager.getSimSerialNumber();
        } else {
            simid_send = "";
        }
    }

    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener md1 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub

            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            present_date_toSend = format.format(new Date(present_year - 1900, (present_month), present_day));

            Log.i("DAY REPORT : ", "" + present_date_toSend);

			/* ONLY IN REPORT ON DATE SELECTION SERVICE NEED TO CALL */
			/* IN DUPLIATE PRINT ON PRESS OF GO BUTTON SERVICE NEED TO CALL */
            if (Dashboard.check_vhleHistory_or_Spot.equals("reports")) {

                btn_report_dateselection.setText("" + present_date_toSend.toUpperCase());

                if (isOnline()) {
                    new Async_reports().execute();
                } else {
                    showToast("" + netwrk_info_txt);
                }
            } else {
                btn_dp_date_selection.setText("" + present_date_toSend.toUpperCase());
            }

        }
    };

    /* REPORTS START */
    public class Async_reports extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            getSimImeiNo();
            SharedPreferences sharedPreference2 = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            String psCd = sharedPreference2.getString("PS_CODE", "");
            String psName = sharedPreference2.getString("PS_NAME", "");
            String pidCd = sharedPreference2.getString("PID_CODE", "");
            String pidName = sharedPreference2.getString("PID_NAME", "");
            String cadre = sharedPreference2.getString("CADRE_NAME", "");
            String cadreCd = sharedPreference2.getString("CADRE_CODE", "");

            ServiceHelper.getDayReports("" + Dashboard.UNIT_CODE, "" + pidCd, "" + pidName, "" + present_date_toSend,
                    "" + simid_send, "" + imei_send, "" + report_id_send);
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
            if (!ServiceHelper.Opdata_Chalana.equals("NA")) {
                tv_report_text.setText("" + ServiceHelper.Opdata_Chalana);
                tv_report_text.setVisibility(View.VISIBLE);
                btn_print_report.setVisibility(View.VISIBLE);
            } else {
                showToast("" + getResources().getString(R.string.no_day_report));
                tv_report_text.setVisibility(View.GONE);
                btn_print_report.setVisibility(View.GONE);
            }
        }
    }

	/* REPORTS END */

    /*-------------------------------------------------------------------------*/
	/* ONLINE REPORTS START */
    public class Async_getOnlineDuplicatePrint extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            getSimImeiNo();
            SharedPreferences sharedPreference2 = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            String psCd = sharedPreference2.getString("PS_CODE", "");
            String psName = sharedPreference2.getString("PS_NAME", "");
            String pidCd = sharedPreference2.getString("PID_CODE", "");
            String pidName = sharedPreference2.getString("PID_NAME", "");
            String cadre = sharedPreference2.getString("CADRE_NAME", "");
            String cadreCd = sharedPreference2.getString("CADRE_CODE", "");

            ServiceHelper.getOnlineDuplicatePrint("" + Dashboard.UNIT_CODE, "" + pidCd, "" + pidName,
                    "" + et_dp_regno.getText().toString().trim().toUpperCase(), "" + present_date_toSend,
                    "" + simid_send, "" + imei_send);

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
            online_report_status = "";
            if (!ServiceHelper.Opdata_Chalana.equals("NA")) {
                online_report_status = "yes";
                tv_dp_text.setText("" + ServiceHelper.Opdata_Chalana);
                tv_dp_text.setVisibility(View.VISIBLE);
                btn_print_dp.setVisibility(View.VISIBLE);
            } else {
                online_report_status = "";
                showToast("" + getResources().getString(R.string.no_day_report));
                tv_dp_text.setVisibility(View.GONE);
                btn_print_dp.setVisibility(View.GONE);
            }
        }
    }

	/* ONLINE REPORTS END */

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