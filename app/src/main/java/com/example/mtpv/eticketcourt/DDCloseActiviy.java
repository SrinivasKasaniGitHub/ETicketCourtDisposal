package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.ServiceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;



public class DDCloseActiviy extends Activity {
    TextView compny_Name;
    ArrayList<String> mArrayListCourtNames = new ArrayList<String>();
    ArrayList<String> mArrayListCourtDis = new ArrayList<String>();
    HashMap<String, String> paramsCourt = new HashMap<String, String>();
    HashMap<String, String> paramsCourtdis = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> maparrayCourtdis = new ArrayList<HashMap<String, String>>();
    String print_Data;


    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;
    Date date_From;
    TelephonyManager telephonyManager;
    String imei_send;
    String simid_send;
    String present_date_toSend, date_courtAtnd, date_convFRom, date_convicTo, date_soclserveFrom, date_soclserveTo;
    String netwrk_info_txt;
    EditText et_dp_regno;
    EditText edtTxt_STC_No, edtTxt_FineAmnt, edtTxtConDays, edtTxtRisDays, edtTxt_Remarks;
    Button btn_dp_date_selection, btn_courtAttenddate, btn_courtConFromdate, btn_courtConTo, btn_courtSoclServceFromdate, btn_courtSoclServceTodate;
    Button btn_dp_get_onlinedetials;
    Button btn_payment;

    final int APPTYPE_DIALOG = 0;
    final int PRESENT_DATE_PICKER = 1;
    final int PROGRESS_DIALOG = 2;
    final int PRESENT_COURT_ATTEND_DATE = 5;
    final int PRESENT_COURT_CONVI_FROM = 6;
    final int PRESENT_COURT_CONVI_TO = 7;
    final int PRESENT_COURT_SCL_SRC_FROM = 8;
    final int PRESENT_COURT_SCL_SRC_TO = 9;
    final int PRINT_DD_DIALOG=10;
    final int PRINT_DD_DIALOG_EXIT=11;
    final int REPORT_TYPE = 3;
    final int COURT_NAME_DIALOG = 4;
    String online_report_status = "";

    private static final int REQUEST_ENABLE_BT = 1;

    AnalogicsThermalPrinter actual_printer = new AnalogicsThermalPrinter();
    Bluetooth_Printer_3inch_ThermalAPI bth_printer = new Bluetooth_Printer_3inch_ThermalAPI();

    BluetoothAdapter bluetoothAdapter;
    @SuppressWarnings("unused")
    private BluetoothAdapter mBluetoothAdapter = null;

    ArrayList<String> print_respose, print_apptype;
    public static String printer_addrss, printer_name;
    int selected_type = -1;



    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String address = "";
    RelativeLayout dd_lyt;
    RelativeLayout pay_dd_lyt;
    TextView Reg_No, Mobile_No, Offender_Date, Challan_No, Aadhar_No, Dl_No;
    EditText edtTxt_Mob_No,edtTxt_Aadhar_No,edtTxt_DlNo;
    ArrayList<String> courtNames;
    MaterialSpinner courtspinner;
    ArrayList<String> courtDisNames;
    MaterialSpinner courtDisspinner;

    RadioGroup rdoGrp_VehcleRlse;
    RadioButton rdoBtnYes_VehcleRlse;
    RadioButton rdoBtnNo_VehcleRlse;

    LinearLayout lytConFrom, lytConTo, lytConDays, lytFineAmnt, lytSoclFrom, lytSclSerTo, lytRisingDays;
    DBHelper db;
    Cursor c, cursor_courtnames, cursor_court_Disnames,printer_cursor;

    String selectedCourtCode, selectedCourtDisCode;

    String vehcleRelse = "Y";

    String vEHICLE_NUMBER, chall_No, offence_Date, driver_Adhar, driver_Mobile, driver_LCNCE;

    String dayDifference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddclosing);
        db = new DBHelper(getApplicationContext());
        getCourtDisNamesFromDB();
        getCourtNamesFromDB();
        cal = Calendar.getInstance();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		/* FOR DATE PICKER */
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);

        preferences = getSharedPreferences("preferences", MODE_WORLD_READABLE);
        editor = preferences.edit();
        address = preferences.getString("btaddress", "btaddr");

        Log.i("BT address :::::", "" + address);

        lytConFrom = (LinearLayout) findViewById(R.id.lytConvtdFrom);
        lytConTo = (LinearLayout) findViewById(R.id.lytConvtdTo);
        lytConDays = (LinearLayout) findViewById(R.id.lytConvtdDays);
        lytFineAmnt = (LinearLayout) findViewById(R.id.lytFineAmnt);

        lytSoclFrom = (LinearLayout) findViewById(R.id.lytSoclFrom);
        lytSclSerTo = (LinearLayout) findViewById(R.id.lytSclSerTo);
        lytRisingDays = (LinearLayout) findViewById(R.id.lytRisingDays);

        compny_Name = (TextView) findViewById(R.id.CompanyName);
        Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
        compny_Name.startAnimation(marquee);

        et_dp_regno = (EditText) findViewById(R.id.edt_regno_dp_xml);
        edtTxt_STC_No = (EditText) findViewById(R.id.edtTxt_STC_NO);
        edtTxt_FineAmnt = (EditText) findViewById(R.id.edtTxt_FineAmnt);
        edtTxtConDays = (EditText) findViewById(R.id.edtTxt_ConDays);
        edtTxtRisDays = (EditText) findViewById(R.id.edtTxt_RisingDays);
        edtTxt_Remarks = (EditText) findViewById(R.id.edtTxt_Remarks);

        btn_dp_date_selection = (Button) findViewById(R.id.btn_dateselection_dp_xml);
        btn_courtAttenddate = (Button) findViewById(R.id.btn_dateselection_dp_xml1);
        btn_courtConFromdate = (Button) findViewById(R.id.btn_dateselection_dp_xml3);
        btn_courtConTo = (Button) findViewById(R.id.btn_dateselection_dp_xml4);
        btn_courtSoclServceFromdate = (Button) findViewById(R.id.btn_dateselection_dp_xmlssF);
        btn_courtSoclServceTodate = (Button) findViewById(R.id.btn_dateselection_dp_xmlSST);
        btn_dp_get_onlinedetials = (Button) findViewById(R.id.btngetdetails_dp_xml);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        dd_lyt = (RelativeLayout) findViewById(R.id.Lyt_DD_Details);
        pay_dd_lyt = (RelativeLayout) findViewById(R.id.Lyt_Pay_details);
        Reg_No = (TextView) findViewById(R.id.Txt_Regno);
        Mobile_No = (TextView) findViewById(R.id.Txt_Mobile_no);
        edtTxt_Mob_No=(EditText)findViewById(R.id.edtTxt_Mobile_no);
        edtTxt_Aadhar_No=(EditText)findViewById(R.id.edtTxt_Adhar_no);
        edtTxt_DlNo=(EditText)findViewById(R.id.edtTxt_Dl_no);
        Offender_Date = (TextView) findViewById(R.id.Txt_Offender_date);
        Challan_No = (TextView) findViewById(R.id.Txt_Chaln_No);
        Aadhar_No = (TextView) findViewById(R.id.txt_Adhar_No);
        Dl_No = (TextView) findViewById(R.id.Txt_DlNo);
        courtspinner = (MaterialSpinner) findViewById(R.id.courtSpinner);
        courtDisspinner = (MaterialSpinner) findViewById(R.id.courtDisSpinner);

        rdoGrp_VehcleRlse = (RadioGroup) findViewById(R.id.rdoGrp_VehcleRlse);
        rdoBtnYes_VehcleRlse = (RadioButton) findViewById(R.id.rdoBtnYes_VehcleRlse);
        rdoBtnNo_VehcleRlse = (RadioButton) findViewById(R.id.rdoBtnNo_VehcleRlse);
        print_respose = new ArrayList<String>();
        print_apptype = new ArrayList<String>();

        rdoGrp_VehcleRlse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rdoBtnYes_VehcleRlse:
                        vehcleRelse = "";
                        vehcleRelse = "Y";
                        break;

                    case R.id.rdoBtnNo_VehcleRlse:
                        vehcleRelse = "";
                        vehcleRelse = "N";
                        break;

                    default:
                        break;
                }
            }
        });


        courtNames = Dashboard.court_names_arr;
        courtDisNames = Dashboard.court_dis_names_arr;


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mArrayListCourtNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courtspinner.setAdapter(dataAdapter);

        courtspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String courtDis = courtspinner.getSelectedItem().toString();

                Log.i("court map size : ", "" + paramsCourt.size());
                for (String mapCourtName : paramsCourt.keySet()) {
                    if (courtDis.equals(mapCourtName)) {
                        selectedCourtCode = paramsCourt.get(mapCourtName);
                        //Toast.makeText(getApplicationContext(), "COURT NAME : " + mapCourtName + " COURT CODE : " + selectedCourtCode, Toast.LENGTH_LONG).show();
                        break;
                    }// int longitude = Integer.parseInt(menuItem.get(KEY_LON));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        ArrayAdapter<String> courtDisAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item_court_disposal, R.id.spincourtdis, mArrayListCourtDis);
//        courtDisAdapter.setDropDownViewResource(R.layout.simple_spinner_item_court_disposal);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mArrayListCourtDis);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courtDisspinner.setAdapter(dataAdapter1);
        courtDisspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String courtDis = courtDisspinner.getSelectedItem().toString();

                Log.i("disposal map size : ", "" + paramsCourtdis.size());
                for (String mapCourtName : paramsCourtdis.keySet()) {
                    if (courtDis.equals(mapCourtName)) {
                        selectedCourtDisCode = paramsCourtdis.get(mapCourtName);
                        // Toast.makeText(getApplicationContext(), "COURT NAME : " + mapCourtName + " COURT CODE : " + selectedCourtCode, Toast.LENGTH_LONG).show();
                        break;
                    }

                }
                if (courtDis.contains("ONLY FINE")) {

                    lytConFrom.setVisibility(View.GONE);
                    lytConTo.setVisibility(View.GONE);
                    lytConDays.setVisibility(View.GONE);
                    lytSoclFrom.setVisibility(View.GONE);
                    lytSclSerTo.setVisibility(View.GONE);
                    lytRisingDays.setVisibility(View.GONE);
                    lytFineAmnt.setVisibility(View.VISIBLE);
                } else if (courtDis.contains("FINE AND RISING")) {
                    lytConFrom.setVisibility(View.GONE);
                    lytConTo.setVisibility(View.GONE);
                    lytSoclFrom.setVisibility(View.GONE);
                    lytSclSerTo.setVisibility(View.GONE);
                    lytRisingDays.setVisibility(View.VISIBLE);
                    lytConDays.setVisibility(View.GONE);
                    lytFineAmnt.setVisibility(View.VISIBLE);
                } else if (courtDis.contains("FINE, RISING, SOCIAL SERVIC")) {
                    lytConFrom.setVisibility(View.GONE);
                    lytConTo.setVisibility(View.GONE);
                    lytConDays.setVisibility(View.GONE);
                    lytFineAmnt.setVisibility(View.VISIBLE);
                    lytSoclFrom.setVisibility(View.VISIBLE);
                    lytSclSerTo.setVisibility(View.VISIBLE);
                    lytRisingDays.setVisibility(View.VISIBLE);

                } else if (courtDis.contains("FINE AND IMPRISONMENT")) {
                    lytConFrom.setVisibility(View.VISIBLE);
                    lytConTo.setVisibility(View.VISIBLE);
                    lytConDays.setVisibility(View.VISIBLE);
                    lytFineAmnt.setVisibility(View.VISIBLE);
                    lytSoclFrom.setVisibility(View.GONE);
                    lytSclSerTo.setVisibility(View.GONE);
                    lytRisingDays.setVisibility(View.GONE);

                } else if (courtDis.contains("ONLY IMPRISONMENT")) {
                    lytConFrom.setVisibility(View.VISIBLE);
                    lytConTo.setVisibility(View.VISIBLE);
                    lytConDays.setVisibility(View.VISIBLE);
                    lytFineAmnt.setVisibility(View.GONE);
                    lytSoclFrom.setVisibility(View.GONE);
                    lytSclSerTo.setVisibility(View.GONE);
                    lytRisingDays.setVisibility(View.GONE);
                } else if (courtDis.contains("ONLY RISING")) {
                    lytConFrom.setVisibility(View.GONE);
                    lytConTo.setVisibility(View.GONE);
                    lytConDays.setVisibility(View.GONE);
                    lytFineAmnt.setVisibility(View.GONE);
                    lytSoclFrom.setVisibility(View.GONE);
                    lytSclSerTo.setVisibility(View.GONE);
                    lytRisingDays.setVisibility(View.VISIBLE);
                } else {
                    lytConFrom.setVisibility(View.GONE);
                    lytConTo.setVisibility(View.GONE);

                    lytConDays.setVisibility(View.GONE);
                    lytFineAmnt.setVisibility(View.GONE);
                    lytSoclFrom.setVisibility(View.GONE);
                    lytSclSerTo.setVisibility(View.GONE);
                    lytRisingDays.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //PRINTER Data

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

                    Log.i("Duplicate Records",
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


        btn_dp_date_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_DATE_PICKER);
                // btn_dp_date_selection.setText("" + present_date_toSend.toUpperCase());
            }
        });

        btn_courtAttenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_COURT_ATTEND_DATE);
                // btn_courtAttenddate.setText("" + present_date_toSend.toUpperCase());
            }
        });

        btn_courtConFromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_COURT_CONVI_FROM);
                // btn_courtConFromdate.setText("" + present_date_toSend.toUpperCase());
            }
        });
        btn_courtConTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_COURT_CONVI_TO);
                //btn_courtConTo.setText("" + present_date_toSend.toUpperCase());
            }
        });

        btn_courtSoclServceFromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_COURT_SCL_SRC_FROM);
                //btn_courtConTo.setText("" + present_date_toSend.toUpperCase());
            }
        });
        btn_courtSoclServceTodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_COURT_SCL_SRC_TO);
                //btn_courtConTo.setText("" + present_date_toSend.toUpperCase());
            }
        });

        btn_dp_get_onlinedetials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_dp_regno.getText().toString().trim().equals("")) {

                    et_dp_regno.setError(Html.fromHtml("<font color='black'>Enter Vehicle No</font>"));
                } else if (btn_dp_date_selection.getText().toString().equals("Select Date")) {
                    showToast("Select Date");
                } else {
                    new Async_getDD_details().execute();
                }

            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String court_Code = selectedCourtCode;
                String court_Attnd_Date = date_courtAtnd;
                String sTS_No = edtTxt_STC_No.getText().toString();
                String court_Disposal_code = selectedCourtDisCode;
                String fineAmnt = edtTxt_FineAmnt.getText().toString();
                String date_ConFrom = date_convFRom;
                String date_ConTo = date_convicTo;

                String con_Days = edtTxtConDays.getText().toString();
                String rising_days=edtTxtRisDays.getText().toString();
                String vhcleRelse = vehcleRelse;
                String remarks = edtTxt_Remarks.getText().toString();
                //String mob_No=edtTxt_Mob_No.getText().toString();
               // String adhar_No=edtTxt_Aadhar_No.getText().toString();
               // String dl_no=edtTxt_DlNo.getText().toString();
                String dl=driver_LCNCE;
                String ml=driver_Mobile;
                String aN=driver_Adhar;
//                driver_LCNCE=edtTxt_DlNo.getText().toString();
//                driver_Mobile=edtTxt_Mob_No.getText().toString();
//                driver_Adhar=edtTxt_Aadhar_No.getText().toString();
                if (("null")==dl){

                    driver_LCNCE=edtTxt_DlNo.getText().toString();
                }

                if (("null")==ml){
                    driver_Mobile=edtTxt_Mob_No.getText().toString();
                }
                if (("null")==aN){
                    driver_Adhar=edtTxt_Aadhar_No.getText().toString();
                }

//                if (!adhar_No.equals("")){
//
//                    if ((adhar_No.length()<=11 || adhar_No.length()>=13)) {
//                        edtTxt_Aadhar_No.setError(Html.fromHtml("<font color='white'>Enter valid Aadhar Number </font>"));
//                        edtTxt_Aadhar_No.requestFocus();
//                    }
//                }



                if (court_Code == null) {
                    showToast("Select Court Name");
                } else if (driver_Mobile.trim().equals("")) {
                    edtTxt_Mob_No.setError(Html.fromHtml("<font color='white'>Enter Mobile Number </font>"));
                    edtTxt_Mob_No.requestFocus();
                }else if (driver_Mobile.length()<=9 || driver_Mobile.length()>=11) {
                    edtTxt_Mob_No.setError(Html.fromHtml("<font color='white'>Enter Valid Mobile Number </font>"));
                    edtTxt_Mob_No.requestFocus();
                }else if (driver_Adhar.trim().equals("")&& driver_LCNCE.trim().equals("") ) {
                    showToast("Please enter either Aadhar or Dl No");
                }else if ((!driver_Adhar.equals(""))&&(driver_Adhar.length()<=11 || driver_Adhar.length()>=13)) {
                    edtTxt_Aadhar_No.setError(Html.fromHtml("<font color='white'>Enter valid Aadhar Number </font>"));
                    edtTxt_Aadhar_No.requestFocus();
                }else if ((!driver_LCNCE.equals(""))&&(driver_LCNCE.length()<=3)) {
                    edtTxt_DlNo.setError(Html.fromHtml("<font color='white'>Enter valid Dl Number </font>"));
                    edtTxt_DlNo.requestFocus();
                }else if (btn_courtAttenddate.getText().toString().equals("Select Date")) {
                    showToast("Select Court Attended Date");
                } else if (sTS_No.trim().equals("")) {
                    edtTxt_STC_No.setError(Html.fromHtml("<font color='white'>Enter STC No</font>"));
                    edtTxt_STC_No.requestFocus();
                } else if (court_Disposal_code == null) {
                    showToast("Select court Disposal Name");
                } else if (remarks.trim().equals("")) {
                    edtTxt_Remarks.setError(Html.fromHtml("<font color='white'>Enter Remarks </font>"));
                    edtTxt_Remarks.requestFocus();
                }else if (court_Disposal_code.equals("3")) {

                    if (btn_courtConFromdate.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted From date");
                    } else if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='black'>Enter Fine Amount </font>"));
                    } else if (btn_courtConTo.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted To date");
                    } else if (con_Days.trim().equals("")) {
                        edtTxtConDays.setError(Html.fromHtml("<font color='black'>Enter Convicted Days </font>"));
                    }  else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                   // 6@CONTEST!3@FINE AND IMPRISONMENT!5@FINE AND RISING!7@FINE, RISING, SOCIAL SERVIC!1@ONLY FINE!2@ONLY IMPRISONMENT!4@ONLY RISING

                }else if (court_Disposal_code.equals("1")) {

                   if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='white'>Enter Fine Amount </font>"));
                       edtTxt_FineAmnt.requestFocus();
                    }   else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                }else if (court_Disposal_code.equals("5")) {

                     if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='white'>Enter Fine Amount </font>"));
                    }  else if (rising_days.trim().equals("")) {
                         edtTxtRisDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                         edtTxtRisDays.requestFocus();
                    }  else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                }else if (court_Disposal_code.equals("7")) {

                    if (btn_courtSoclServceFromdate.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted From date");
                    } else if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='white'>Enter Fine Amount </font>"));
                        edtTxt_FineAmnt.requestFocus();
                    } else if (btn_courtSoclServceTodate.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted To date");
                    } else if (rising_days.trim().equals("")) {
                        edtTxtRisDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                        edtTxtRisDays.requestFocus();
                    }  else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                }else if (court_Disposal_code.equals("2")) {

                    if (btn_courtConFromdate.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted From date");
                    } else if (btn_courtConTo.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted To date");
                    } else if (con_Days.trim().equals("")) {
                        edtTxtConDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                        edtTxtConDays.requestFocus();
                    }  else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                }else if (court_Disposal_code.equals("4")) {

                     if (rising_days.trim().equals("")) {
                        edtTxtRisDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                        edtTxtRisDays.requestFocus();
                    }  else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                }

            }
        });






    }


    public void getCourtDisNamesFromDB() {
        try {
            db.open();
            cursor_court_Disnames = DBHelper.db.rawQuery("select * from " + db.court_disName_table, null);


//            if (cursor_court_Disnames.getCount() == 0) {
//                showToast("Please download master's !");
//            } else {

            if (cursor_court_Disnames.moveToFirst()) {
                paramsCourtdis = new HashMap<String, String>();
                while (!cursor_court_Disnames.isAfterLast()) {
                    mArrayListCourtDis.add(cursor_court_Disnames.getString(cursor_court_Disnames.getColumnIndex(db.court_dis_name_settings))); //add the item

                    paramsCourtdis.put(cursor_court_Disnames.getString(cursor_court_Disnames.getColumnIndex(db.court_dis_name_settings)), cursor_court_Disnames.getString(cursor_court_Disnames.getColumnIndex(db.court_dis_code_settings)));
                    cursor_court_Disnames.moveToNext();
                    maparrayCourtdis.add(paramsCourtdis);
                }

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cursor_court_Disnames.close();
        db.close();

    }

    public void getCourtNamesFromDB() {
        try {
            db.open();
            cursor_courtnames = DBHelper.db.rawQuery("select * from " + db.courtName_table, null);


//            if (cursor_court_Disnames.getCount() == 0) {
//                showToast("Please download master's !");
//            } else {

            if (cursor_courtnames.moveToFirst()) {
                paramsCourt = new HashMap<String, String>();
                while (!cursor_courtnames.isAfterLast()) {
                    mArrayListCourtNames.add(cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_name_settings)));

                    paramsCourt.put(cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_name_settings)), cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_code_settings)));
                    cursor_courtnames.moveToNext();
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cursor_courtnames.close();
        db.close();

    }

    public class Async_getCourtClosingUpdateTicketInfo extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {

            ServiceHelper.getCourtClosingUpdateTicketInfo(chall_No, vEHICLE_NUMBER, driver_LCNCE.toUpperCase(), driver_Adhar, edtTxt_STC_No.getText().toString(),
                    selectedCourtDisCode, edtTxtConDays.getText().toString(), date_convFRom, date_convicTo, edtTxt_FineAmnt.getText().toString(),
                    edtTxtRisDays.getText().toString(), selectedCourtCode, date_courtAtnd, vehcleRelse, "Y", edtTxt_Remarks.getText().toString(), MainActivity.user_id, MainActivity.arr_logindetails[1], "", driver_Mobile);


            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showDialog(PROGRESS_DIALOG);
//            dd_lyt.setVisibility(View.GONE);
//            pay_dd_lyt.setVisibility(View.GONE);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            Log.d("DD Details", "" + ServiceHelper.Opdata_Chalana);
            print_Data=ServiceHelper.Opdata_Chalana;
            removeDialog(PROGRESS_DIALOG);
            if (ServiceHelper.Opdata_Chalana.equals("0")) {
                showToast("Updated failled ! ");

//                Intent intent_dashboard = new Intent(getApplicationContext(), Dashboard.class);
//                startActivity(intent_dashboard);
            }else {
                showDialog(PRINT_DD_DIALOG);
            }
            online_report_status = "";


        }
    }


    public class Async_getDD_details extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {

            ServiceHelper.getCourtClosingTicketInfo("" + et_dp_regno.getText().toString().trim().toUpperCase(), "" + present_date_toSend);

            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showDialog(PROGRESS_DIALOG);
            dd_lyt.setVisibility(View.GONE);
            pay_dd_lyt.setVisibility(View.GONE);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            Log.d("DD Details", "" + ServiceHelper.Opdata_Chalana);
            removeDialog(PROGRESS_DIALOG);

            online_report_status = "";
            if (!ServiceHelper.Opdata_Chalana.equals("NA")) {

                try {
                    JSONObject jsonObject = new JSONObject(ServiceHelper.Opdata_Chalana);
                    JSONArray jsonArray = jsonObject.getJSONArray("Challan Details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.toString().isEmpty()||jsonObject1.length()==0){
                            showToast("No data Found");
                        }else{
                            dd_lyt.setVisibility(View.VISIBLE);
                            pay_dd_lyt.setVisibility(View.VISIBLE);
                            lytConFrom.setVisibility(View.GONE);
                            lytConTo.setVisibility(View.GONE);
                            lytConDays.setVisibility(View.GONE);
                            lytFineAmnt.setVisibility(View.GONE);
                            vEHICLE_NUMBER = jsonObject1.getString("VEHICLE NUMBER");
                            chall_No = jsonObject1.getString("CHALLAN NUMBER");
                            offence_Date = jsonObject1.getString("OFFENCE DATE");
                            driver_Adhar = jsonObject1.getString("DRIVER AADHAAR");
                            driver_Mobile = jsonObject1.getString("DRIVER MOBILE");
                            driver_LCNCE = jsonObject1.getString("DRIVING LICENSE");

                            Reg_No.setText(vEHICLE_NUMBER);
                            Challan_No.setText(chall_No);
                            Offender_Date.setText(offence_Date);
                            if (("null")!=driver_Mobile){
                                edtTxt_Mob_No.setVisibility(View.GONE);
                                Mobile_No.setVisibility(View.VISIBLE);
                                Mobile_No.setText(driver_Mobile);

                            }else {
                                edtTxt_Mob_No.setVisibility(View.VISIBLE);
                                Mobile_No.setVisibility(View.GONE);

                            }

                            if (("null")!=driver_Adhar){
                                edtTxt_Aadhar_No.setVisibility(View.GONE);
                                Aadhar_No.setVisibility(View.VISIBLE);
                                Aadhar_No.setText(driver_Adhar);

                            }else {
                                edtTxt_Aadhar_No.setVisibility(View.VISIBLE);
                                Aadhar_No.setVisibility(View.GONE);

                            }

                            if (("null")!=driver_LCNCE){
                                edtTxt_DlNo.setVisibility(View.GONE);
                                Dl_No.setVisibility(View.VISIBLE);
                                Dl_No.setText(driver_LCNCE);

                            }else {
                                edtTxt_DlNo.setVisibility(View.VISIBLE);
                                Dl_No.setVisibility(View.GONE);

                            }


                        }




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                online_report_status = "";
                showToast("" + getResources().getString(R.string.no_day_report));
            }
        }
    }

    public class Aysnc_Print_Data extends AsyncTask<Void, Void, String> {

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

                        if (!print_Data.equals("")) {


                            try {
									/*
									 * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 */

                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                String print_data = printer.font_Courier_41("" + print_Data);
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

                                        String print_data = printer.font_Courier_41("" + print_Data);
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

                        if (!print_Data.equals("")) {


                            try {
									/*
									 * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 */
                                Bluetooth_Printer_3inch_ThermalAPI printer = new Bluetooth_Printer_3inch_ThermalAPI();

                                String print_data = printer.font_Courier_41("" + print_Data);
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

                                    String print_data = printer.font_Courier_41("" + print_Data);
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


    private void showToast(String msg) {
        // TODO Auto-generated method stub
        // Toast.makeText(getApplicationContext(), "" + msg,
        // Toast.LENGTH_SHORT).show();
//        Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 10, 10);
//
//
//        View toastView = toast.getView();
//
//        ViewGroup group = (ViewGroup) toast.getView();
//        TextView messageTextView = (TextView) group.getChildAt(0);
//        messageTextView.setTextSize(22);
//
//        toastView.setBackgroundResource(R.drawable.toast_background);
//        toast.show();


        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));

        // set a message
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        // Toast...
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener md1 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
//pre
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            present_date_toSend = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_dp_date_selection.setText("" + present_date_toSend.toUpperCase());

            Log.i("DAY REPORT : ", "" + present_date_toSend);



        }
    };
    DatePickerDialog.OnDateSetListener md2 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub

            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_courtAtnd = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_courtAttenddate.setText("" + date_courtAtnd.toUpperCase());



        }
    };
    DatePickerDialog.OnDateSetListener md3 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub

            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convFRom="";
            date_convFRom = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_courtConFromdate.setText("" + date_convFRom.toUpperCase());
            btn_courtConTo.setText("Select Date");
            edtTxtConDays.setText("");
            try {
                date_From=null;
                date_From=format.parse(date_convFRom);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    };

    DatePickerDialog.OnDateSetListener md4 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub

            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convicTo = format.format(new Date(present_year - 1900, (present_month), present_day));
            dayDifference="";



            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");


            //Setting dates
            try {


                date1 = dates.parse(date_convFRom);
                date2 = dates.parse(date_convicTo);
                if (date2.after(date1) || date2.equals(date1)){

                    btn_courtConTo.setText("" + date_convicTo.toUpperCase());
                    //Comparing dates
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);

                    //Convert long to String
                    dayDifference = Long.toString(differenceDates);
                    edtTxtConDays.setText(dayDifference);
                }else {
                    showToast("Date should be greater than From_Date ");
                    btn_courtConTo.setText("Select Date");
                    edtTxtConDays.setText("");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }




        }
    };

    DatePickerDialog.OnDateSetListener md_scl_servceFrom = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub

            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convFRom = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_courtSoclServceFromdate.setText("" + date_convFRom.toUpperCase());


        }
    };

    DatePickerDialog.OnDateSetListener md_scl_servceTo = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub

            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convicTo = format.format(new Date(present_year - 1900, (present_month), present_day));
            //btn_courtSoclServceTodate.setText("" + date_convicTo.toUpperCase());
            dayDifference="";

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");


            //Setting dates
            try {


                date1 = dates.parse(date_convFRom);
                date2 = dates.parse(date_convicTo);
                if (date2.after(date1) || date2.equals(date1)){

                    btn_courtSoclServceTodate.setText("" + date_convicTo.toUpperCase());
                    //Comparing dates
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);

                    //Convert long to String
                    dayDifference = Long.toString(differenceDates);
                    edtTxtRisDays.setText(dayDifference);
                }else {
                    showToast("Date should be greater than From_Date ");
                    btn_courtSoclServceTodate.setText("Select Date");
                    edtTxtRisDays.setText("");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }





        }
    };


    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch (id) {
            case PRESENT_DATE_PICKER:
                DatePickerDialog dp_offence_date = new DatePickerDialog(this, md1, present_year, present_month,
                        present_day);

                dp_offence_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offence_date;
            case PRESENT_COURT_ATTEND_DATE:
                DatePickerDialog dp_courtAtnd_date = new DatePickerDialog(this, md2, present_year, present_month,
                        present_day);

                dp_courtAtnd_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_courtAtnd_date;
            case PRESENT_COURT_CONVI_FROM:
                DatePickerDialog dp_courtConFrom_date = new DatePickerDialog(this, md3, present_year, present_month,
                        present_day);

                //dp_courtConFrom_date.getDatePicker().setMaxDate(System.currentTimeMillis());
               // dp_courtConFrom_date.getDatePicker().setMinDate(Long.parseLong(date_convFRom));
                return dp_courtConFrom_date;
            case PRESENT_COURT_CONVI_TO:
                DatePickerDialog dp_courtConTo_date = new DatePickerDialog(this, md4, present_year, present_month,
                        present_day);

                //dp_courtConTo_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_courtConTo_date;
            case PROGRESS_DIALOG:
                ProgressDialog pd = ProgressDialog.show(this, "", "Please Wait...", true);
                pd.setContentView(R.layout.custom_progress_dialog);
                pd.setCancelable(false);
                return pd;

            case PRESENT_COURT_SCL_SRC_FROM:
                DatePickerDialog dp_SCL_SRC_FROM = new DatePickerDialog(this, md_scl_servceFrom, present_year, present_month,
                        present_day);

                dp_SCL_SRC_FROM.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_SCL_SRC_FROM;

            case PRESENT_COURT_SCL_SRC_TO:
                DatePickerDialog dp_SCL_SRC_TO = new DatePickerDialog(this, md_scl_servceTo, present_year, present_month,
                        present_day);

                dp_SCL_SRC_TO.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_SCL_SRC_TO;

            case PRINT_DD_DIALOG:
                TextView title = new TextView(this);
                title.setText("COURT CLOSING");
                title.setBackgroundColor(Color.BLUE);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(26);
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
                title.setPadding(20, 0, 20, 0);
                title.setHeight(70);

                //String otp_message = "Print INfo \n";

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DDCloseActiviy.this,
                        AlertDialog.THEME_HOLO_LIGHT);
                alertDialogBuilder.setCustomTitle(title);
                alertDialogBuilder.setIcon(R.drawable.dialog_logo);
                alertDialogBuilder.setMessage(print_Data);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("PRINT", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        new Aysnc_Print_Data().execute();
                        Toast.makeText(getApplicationContext(),"Printing",Toast.LENGTH_LONG).show();

                    }
                });

                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dd_lyt.setVisibility(View.GONE);
                        pay_dd_lyt.setVisibility(View.GONE);
                        removeDialog(PRINT_DD_DIALOG_EXIT);

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
                btn.setBackgroundColor(Color.BLUE);

                Button btn2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                btn2.setTextSize(22);
                btn2.setTextColor(Color.WHITE);
                btn2.setTypeface(btn2.getTypeface(), Typeface.BOLD);
                btn2.setBackgroundColor(Color.BLUE);
                return alertDialog;


            default:
                break;
        }
        return super.onCreateDialog(id);
    }





}
