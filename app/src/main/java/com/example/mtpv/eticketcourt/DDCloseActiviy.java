package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.analogics.thermalAPI.Bluetooth_Printer_3inch_ThermalAPI;
import com.analogics.thermalprinter.AnalogicsThermalPrinter;
import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.ServiceHelper;
import com.example.mtpv.eticketcourt.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;

public class DDCloseActiviy extends Activity {

    TextView compny_Name;
    AppCompatImageView imgView_CourtOrderCopy, imgView_DLCopy;
    ArrayList<String> mArrayListCourtNames = new ArrayList<>();
    ArrayList<String> mArrayListCourtDis = new ArrayList<>();
    HashMap<String, String> paramsCourt = new HashMap<>();
    HashMap<String, String> paramsCourtdis = new HashMap<>();
    ArrayList<HashMap<String, String>> maparrayCourtdis = new ArrayList<>();
    String print_Data;
    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;
    Date date_From;
    String present_date_toSend, date_courtAtnd, date_convFRom, date_convicTo, date_DL_SUS_FROM, date_DL_SUS_TO, date_dd_dl_dob;
    EditText et_dp_regno;
    EditText edtTxt_STC_No, edtTxt_FineAmnt, edtTxtConDays, edtTxtRisDays, edtTxt_Remarks;
    Button btn_dp_date_selection, btn_courtAttenddate, btn_courtConFromdate, btn_courtConTo, btn_courtSoclServceFromdate,
            btn_courtSoclServceTodate, btn_DLSUS_FromDate, btn_DLSUS_ToDate, btn_dateSeltion_DL_Canln;
    Button btn_dp_get_onlinedetials;
    Button btn_payment;
    AppCompatButton btn_Dl_dob;
    final int PRESENT_DATE_PICKER = 1;
    final int PROGRESS_DIALOG = 2;
    final int PRESENT_COURT_ATTEND_DATE = 5;
    final int PRESENT_COURT_CONVI_FROM = 6;
    final int PRESENT_COURT_CONVI_TO = 7;
    final int PRESENT_COURT_SCL_SRC_FROM = 8;
    final int PRESENT_COURT_SCL_SRC_TO = 9;
    final int PRESENT_DL_SUS_FROM = 16;
    final int PRESENT_DL_SUS_TO = 17;
    final int PRESENT_DD_DL_DOB = 15;
    final int PRESENT_DD_DL_CanCel = 18;
    final int PRINT_DD_DIALOG = 10;
    final int PRINT_DD_DIALOG_EXIT = 11;
    String online_report_status = "";
    private static final int REQUEST_ENABLE_BT = 1;
    AnalogicsThermalPrinter actual_printer = new AnalogicsThermalPrinter();
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
    EditText edtTxt_Mob_No, edtTxt_Aadhar_No, edtTxt_DlNo, edtTxt_DL_SUSDAYS;
    ArrayList<String> courtNames;
    MaterialSpinner courtspinner;
    ArrayList<String> courtDisNames;
    MaterialSpinner courtDisspinner;
    RadioGroup rdoGrp_VehcleRlse, rdoGrp_DLSUSCAN;
    RadioButton rdoBtnYes_VehcleRlse, rdoBtn_DLSUS;
    RadioButton rdoBtnNo_VehcleRlse, rdoBtn_DLCAN;
    LinearLayout lytConFrom, lytConTo, lytConDays, lytFineAmnt, lytSoclFrom, lytSclSerTo, lytRisingDays, lytSUSDAYS, lytDLCanDate,
            lytDLSusFrom, lytDLSusTo, lytImages;
    DBHelper db;
    Cursor c, cursor_courtnames, cursor_court_Disnames, printer_cursor;
    String selectedCourtCode, selectedCourtDisCode;
    String vehcleRelse = "Y";
    String dl_SUS = "N";
    String dl_CAN = "N";
    String dl_SUS_Status = "N";
    String dl_SusDays = "";
    String vEHICLE_NUMBER, chall_No, chall_Type, violations, offence_Date, driver_Adhar, driver_Mobile, driver_LCNCE, driver_DL_DOB,
            unit_CODE, pid_CODE, ps_CODE;
    String dayDifference;

    DateUtil dateUtil;
    byte[] byteArray;
    String img_dataCourtCopy, img_dataDLCopy = null;
    String selection_Pic_Flag = "";
    String tckt_UPdated_Flag = "N";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddclosing);

        db = new DBHelper(getApplicationContext());
        getCourtDisNamesFromDB();
        getCourtNamesFromDB();
        cal = Calendar.getInstance();
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        editor = preferences.edit();
        address = preferences.getString("btaddress", "btaddr");
        lytConFrom = (LinearLayout) findViewById(R.id.lytConvtdFrom);
        lytConTo = (LinearLayout) findViewById(R.id.lytConvtdTo);
        lytConDays = (LinearLayout) findViewById(R.id.lytConvtdDays);
        lytFineAmnt = (LinearLayout) findViewById(R.id.lytFineAmnt);
        lytSoclFrom = (LinearLayout) findViewById(R.id.lytSoclFrom);
        lytSclSerTo = (LinearLayout) findViewById(R.id.lytSclSerTo);
        lytRisingDays = (LinearLayout) findViewById(R.id.lytRisingDays);
        lytSUSDAYS = (LinearLayout) findViewById(R.id.lytSUSDAYS);
        lytDLSusFrom = (LinearLayout) findViewById(R.id.lytDLSusFrom);
        lytDLCanDate = (LinearLayout) findViewById(R.id.lytDLCanDate);
        lytDLSusTo = (LinearLayout) findViewById(R.id.lytDLSusTo);
        lytImages = (LinearLayout) findViewById(R.id.lytImages);
        imgView_CourtOrderCopy = (AppCompatImageView) findViewById(R.id.img_CourtCopy);
        imgView_DLCopy = (AppCompatImageView) findViewById(R.id.img_DLCopy);
        compny_Name = (TextView) findViewById(R.id.CompanyName);
        Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
        compny_Name.startAnimation(marquee);
        et_dp_regno = (EditText) findViewById(R.id.edt_regno_dp_xml);
        edtTxt_STC_No = (EditText) findViewById(R.id.edtTxt_STC_NO);
        edtTxt_FineAmnt = (EditText) findViewById(R.id.edtTxt_FineAmnt);
        edtTxtConDays = (EditText) findViewById(R.id.edtTxt_ConDays);
        edtTxtRisDays = (EditText) findViewById(R.id.edtTxt_RisingDays);
        edtTxt_Remarks = (EditText) findViewById(R.id.edtTxt_Remarks);
        edtTxt_DL_SUSDAYS = (EditText) findViewById(R.id.edtTxt_DL_SUSDAYS);
        edtTxt_DL_SUSDAYS.setKeyListener(null);

        btn_dp_date_selection = (Button) findViewById(R.id.btn_dateselection_dp_xml);
        btn_courtAttenddate = (Button) findViewById(R.id.btn_dateselection_dp_xml1);
        btn_courtConFromdate = (Button) findViewById(R.id.btn_dateselection_dp_xml3);
        btn_courtConTo = (Button) findViewById(R.id.btn_dateselection_dp_xml4);
        btn_courtSoclServceFromdate = (Button) findViewById(R.id.btn_dateselection_dp_xmlssF);
        btn_courtSoclServceTodate = (Button) findViewById(R.id.btn_dateselection_dp_xmlSST);
        btn_dp_get_onlinedetials = (Button) findViewById(R.id.btngetdetails_dp_xml);
        btn_DLSUS_FromDate = (Button) findViewById(R.id.btn_dateSeltion_DL_SusFrom);
        btn_DLSUS_ToDate = (Button) findViewById(R.id.btn_dateSeltion_DL_SusTo);
        btn_dateSeltion_DL_Canln = (Button) findViewById(R.id.btn_dateSeltion_DL_Canln);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        btn_Dl_dob = (AppCompatButton) findViewById(R.id.btn_Dl_dob);
        dd_lyt = (RelativeLayout) findViewById(R.id.Lyt_DD_Details);
        pay_dd_lyt = (RelativeLayout) findViewById(R.id.Lyt_Pay_details);
        Reg_No = (TextView) findViewById(R.id.Txt_Regno);
        Mobile_No = (TextView) findViewById(R.id.Txt_Mobile_no);
        edtTxt_Mob_No = (EditText) findViewById(R.id.edtTxt_Mobile_no);
        edtTxt_Aadhar_No = (EditText) findViewById(R.id.edtTxt_Adhar_no);
        edtTxt_DlNo = (EditText) findViewById(R.id.edtTxt_Dl_no);
        Offender_Date = (TextView) findViewById(R.id.Txt_Offender_date);
        Challan_No = (TextView) findViewById(R.id.Txt_Chaln_No);
        Aadhar_No = (TextView) findViewById(R.id.txt_Adhar_No);
        Dl_No = (TextView) findViewById(R.id.Txt_DlNo);
        courtspinner = (MaterialSpinner) findViewById(R.id.courtSpinner);
        courtDisspinner = (MaterialSpinner) findViewById(R.id.courtDisSpinner);

        rdoGrp_VehcleRlse = (RadioGroup) findViewById(R.id.rdoGrp_VehcleRlse);
        rdoBtnYes_VehcleRlse = (RadioButton) findViewById(R.id.rdoBtnYes_VehcleRlse);
        rdoBtnNo_VehcleRlse = (RadioButton) findViewById(R.id.rdoBtnNo_VehcleRlse);
        print_respose = new ArrayList<>();
        print_apptype = new ArrayList<>();
        dateUtil = new DateUtil();
        rdoGrp_DLSUSCAN = (RadioGroup) findViewById(R.id.rdoGrpDLSUSCAN);
        rdoBtn_DLSUS = (RadioButton) findViewById(R.id.rdoBtnDLSUS);
        rdoBtn_DLCAN = (RadioButton) findViewById(R.id.rdoBtnDLCAN);


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

        rdoGrp_DLSUSCAN.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.rdoBtnDLSUS:
                        dl_SUS = "";
                        dl_SUS = "Y";
                        dl_CAN = "N";
                        lytSUSDAYS.setVisibility(View.VISIBLE);
                        lytDLSusFrom.setVisibility(View.VISIBLE);
                        lytDLSusTo.setVisibility(View.VISIBLE);
                        lytImages.setVisibility(View.VISIBLE);
                        lytDLCanDate.setVisibility(View.GONE);
                        imgView_CourtOrderCopy.setImageResource(R.drawable.courtorder);
                        imgView_DLCopy.setImageResource(R.drawable.dlcopy);
                        break;

                    case R.id.rdoBtnDLCAN:
                        dl_CAN = "";
                        dl_CAN = "Y";
                        dl_SUS = "N";
                        lytDLCanDate.setVisibility(View.VISIBLE);
                        lytSUSDAYS.setVisibility(View.GONE);
                        lytDLSusFrom.setVisibility(View.GONE);
                        lytDLSusTo.setVisibility(View.GONE);
                        lytImages.setVisibility(View.VISIBLE);
                        imgView_CourtOrderCopy.setImageResource(R.drawable.courtorder);
                        imgView_DLCopy.setImageResource(R.drawable.dlcopy);
                        break;

                    case R.id.rdoBtnNONE:
                        dl_CAN = "";
                        dl_SUS = "";
                        dl_CAN = "N";
                        dl_SUS = "N";
                        lytSUSDAYS.setVisibility(View.GONE);
                        lytDLSusFrom.setVisibility(View.GONE);
                        lytDLSusTo.setVisibility(View.GONE);
                        lytImages.setVisibility(View.GONE);
                        lytDLCanDate.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }

            }
        });

        courtNames = Dashboard.court_names_arr;
        courtDisNames = Dashboard.court_dis_names_arr;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
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
                    }

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
        db = new DBHelper(getApplicationContext());
       /* try {
            db.open();
            c = DBHelper.db.rawQuery("select * from " + DBHelper.duplicatePrint_table, null);
            if (c.getCount() == 0) {
                // showToast("No Duplicate Records Found!");
                // this.finish();
            } else {
                while (c.moveToNext()) {
                    print_respose.add(c.getString(c.getColumnIndex(DBHelper.dup_print_respnse)));
                    print_apptype.add(c.getString(c.getColumnIndex(DBHelper.dup_print_app_type)));
                }
            }
            c.close();
            db.close();
        } catch (SQLException e) {
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
        }*/

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

        btn_courtSoclServceTodate.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showDialog(PRESENT_COURT_SCL_SRC_TO);
                        //btn_courtConTo.setText("" + present_date_toSend.toUpperCase());
                    }
                });
        btn_DLSUS_FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_DL_SUS_FROM);
            }
        });
        btn_DLSUS_ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_DL_SUS_TO);
            }
        });
        btn_Dl_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_DD_DL_DOB);
            }
        });

        btn_dateSeltion_DL_Canln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PRESENT_DD_DL_CanCel);
            }
        });

        imgView_CourtOrderCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection_Pic_Flag = "1";
                selectImage();
            }
        });

        imgView_DLCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection_Pic_Flag = "2";
                selectImage();
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
                    if (isOnline()) {
                        new Async_getDD_details().execute();
                    } else {
                        showToast("Please check your network connection!");
                    }
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
                dl_SusDays = edtTxt_DL_SUSDAYS.getText().toString();
                if (dl_SUS.equals("Y")) {
                    dl_SUS_Status = "Y";
//                    dl_SUS = dl_SUS + "!" + dl_SusDays;
                } else {
                    dl_SUS_Status = "N";
                }

                String date_ConFrom = date_convFRom;
                String date_ConTo = date_convicTo;
                String con_Days = edtTxtConDays.getText().toString();
                String rising_days = edtTxtRisDays.getText().toString();
                String vhcleRelse = vehcleRelse;
                String remarks = edtTxt_Remarks.getText().toString();
                // String mob_No=edtTxt_Mob_No.getText().toString();
                // String adhar_No=edtTxt_Aadhar_No.getText().toString();
                // String dl_no=edtTxt_DlNo.getText().toString();
                String dl = driver_LCNCE;
                String ml = driver_Mobile;
                String aN = driver_Adhar;
                String dl_dob = driver_DL_DOB;
//                driver_LCNCE=edtTxt_DlNo.getText().toString();
//                driver_Mobile=edtTxt_Mob_No.getText().toString();
//                driver_Adhar=edtTxt_Aadhar_No.getText().toString();
                if (("null").equals(dl) || ("").equals(dl)) {
                    driver_LCNCE = edtTxt_DlNo.getText().toString();
                }

                if (("null").equals(ml) || ("").equals(ml)) {
                    driver_Mobile = edtTxt_Mob_No.getText().toString();
                }

                if (("null").equals(aN) || ("").equals(aN)) {
                    driver_Adhar = edtTxt_Aadhar_No.getText().toString();
                }
                if (("null").equals(dl_dob) || ("").equals(dl_dob)) {
                    driver_DL_DOB = btn_Dl_dob.getText().toString();
                }


                if (court_Code == null) {
                    showToast("Select Court Name");
                } else if (driver_Mobile.trim().equals("")) {
                    edtTxt_Mob_No.setError(Html.fromHtml("<font color='white'>Enter Mobile Number </font>"));
                    edtTxt_Mob_No.requestFocus();
                } else if (driver_Mobile.length() <= 9 || driver_Mobile.length() >= 11) {
                    edtTxt_Mob_No.setError(Html.fromHtml("<font color='white'>Enter Valid Mobile Number </font>"));
                    edtTxt_Mob_No.requestFocus();
                } else if (driver_Adhar.trim().equals("") && driver_LCNCE.trim().equals("")) {
                    showToast("Please enter either Aadhar or Dl No");
                } else if (!driver_Adhar.equals("")) {
                    if ((driver_Adhar.length() <= 11 || driver_Adhar.length() >= 13)) {
                        edtTxt_Aadhar_No.setError(Html.fromHtml("<font color='white'>Enter valid Aadhar Number </font>"));
                        edtTxt_Aadhar_No.requestFocus();
                    }
                } else if (dl_SUS_Status.equals("Y") && (!driver_LCNCE.equals("null")) && (driver_LCNCE.length() <= 3)) {
                    edtTxt_DlNo.setError(Html.fromHtml("<font color='white'>Enter valid Dl Number </font>"));
                    edtTxt_DlNo.requestFocus();
                } else if (dl_SUS_Status.equals("Y") && (btn_Dl_dob.getText().toString().equals("Select Date"))) {
                    showToast("Select Dl Date!");
                } else if (dl_SUS.equals("Y") && btn_DLSUS_FromDate.getText().toString().equals("Select Date")) {
                    showToast("Please Select DL Suspention From Date!");
                } else if (dl_SUS.equals("Y") && btn_DLSUS_ToDate.getText().toString().equals("Select Date")) {
                    showToast("Please Select DL Suspention To Date!");
                } else if (dl_SUS.equals("Y") && img_dataCourtCopy == null) {
                    showToast("Please Select/ Capture the Megistrate Copy");
                } else if (dl_SUS.equals("Y") && img_dataDLCopy == null) {
                    showToast("Please Select/Capture DL Copy!");
                } else if (dl_CAN.equals("Y") && btn_dateSeltion_DL_Canln.getText().toString().equals("Select Date")) {
                    showToast("Please Select DL Cancellation Date!");
                } else if (dl_CAN.equals("Y") && img_dataCourtCopy == null) {
                    showToast("Please Select/ Capture the Megistrate Copy");
                } else if (dl_CAN.equals("Y") && img_dataDLCopy == null) {
                    showToast("Please Select/Capture DL Copy!");
                }

//                else if ((!driver_LCNCE.equals("")) && (driver_LCNCE.length() <= 3) && driver_Adhar.equals("")) {
//                    edtTxt_DlNo.setError(Html.fromHtml("<font color='white'>Enter valid Dl Number </font>"));
//                    edtTxt_DlNo.requestFocus();
//                }

                else if (btn_courtAttenddate.getText().toString().equals("Select Date")) {
                    showToast("Select Court Attended Date");
                } else if (sTS_No.trim().equals("")) {
                    edtTxt_STC_No.setError(Html.fromHtml("<font color='white'>Enter STC No</font>"));
                    edtTxt_STC_No.requestFocus();
                } else if (dl_SusDays.trim().equals("") && dl_SUS_Status.equals("Y")) {
                    edtTxt_DL_SUSDAYS.setError(Html.fromHtml("<font color='white'>Enter Suspention Days</font>"));
                    edtTxt_DL_SUSDAYS.requestFocus();
                } else if (court_Disposal_code == null) {
                    showToast("Select court Disposal Name");
                } else if (court_Disposal_code.equals("3")) {

                    if (btn_courtConFromdate.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted From date");
                    } else if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='black'>Enter Fine Amount </font>"));
                    } else if (btn_courtConTo.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted To date");
                    } else if (con_Days.trim().equals("")) {
                        edtTxtConDays.setError(Html.fromHtml("<font color='black'>Enter Convicted Days </font>"));
                    } else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();
                    }


                } else if (court_Disposal_code.equals("1")) {

                    if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='white'>Enter Fine Amount </font>"));
                        edtTxt_FineAmnt.requestFocus();
                    } else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                } else if (court_Disposal_code.equals("5")) {

                    if (fineAmnt.trim().equals("")) {
                        edtTxt_FineAmnt.setError(Html.fromHtml("<font color='white'>Enter Fine Amount </font>"));
                    } else if (rising_days.trim().equals("")) {
                        edtTxtRisDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                        edtTxtRisDays.requestFocus();
                    } else {
                        if (isOnline()) {
                            new Async_getCourtClosingUpdateTicketInfo().execute();
                        } else {
                            showToast("Please check your network connection");
                        }

                    }

                } else if (court_Disposal_code.equals("7")) {

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
                    } else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();

                    }

                } else if (court_Disposal_code.equals("2")) {

                    if (btn_courtConFromdate.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted From date");
                    } else if (btn_courtConTo.getText().toString().equals("Select Date")) {
                        showToast("Please select Court Convicted To date");
                    } else if (con_Days.trim().equals("")) {
                        edtTxtConDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                        edtTxtConDays.requestFocus();
                    } else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();
                    }

                } else if (court_Disposal_code.equals("4")) {

                    if (rising_days.trim().equals("")) {
                        edtTxtRisDays.setError(Html.fromHtml("<font color='white'>Enter Convicted Days </font>"));
                        edtTxtRisDays.requestFocus();
                    } else {
                        new Async_getCourtClosingUpdateTicketInfo().execute();
                    }

                }

            }
        });


    }

    public Boolean isOnline() {
        ConnectivityManager conManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
        return nwInfo != null;
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
            e.printStackTrace();
        }
        cursor_courtnames.close();
        db.close();

    }

    private class Async_getCourtClosingUpdateTicketInfo extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {

            try {
                if (dl_SUS.equals("Y") || dl_CAN.equals("Y")) {
                    if ((!driver_LCNCE.equals("null")) && (driver_LCNCE.length() <= 3)) {
                        edtTxt_DlNo.setError(Html.fromHtml("<font color='white'>Enter valid Dl Number </font>"));
                        edtTxt_DlNo.requestFocus();
                    } else if (btn_Dl_dob.getText().toString().equals("Select Date")) {
                        showToast("Select Dl Date!");
                    }
                }


                ServiceHelper.getCourtClosingUpdateTicketInfo(chall_No, vEHICLE_NUMBER, driver_LCNCE.toUpperCase(), btn_Dl_dob.getText().toString(), driver_Adhar, edtTxt_STC_No.getText().toString(),
                        selectedCourtDisCode, edtTxtConDays.getText().toString(), date_convFRom, date_convicTo, edtTxt_FineAmnt.getText().toString(),
                        edtTxtRisDays.getText().toString(), selectedCourtCode, date_courtAtnd, vehcleRelse, "Y", edtTxt_Remarks.getText().toString(),
                        MainActivity.user_id, MainActivity.arr_logindetails[1], "", driver_Mobile, dl_SUS, dl_CAN, date_DL_SUS_FROM, date_DL_SUS_TO,
                        img_dataCourtCopy, img_dataDLCopy, dl_SusDays, chall_Type, violations, unit_CODE, ps_CODE, pid_CODE, offence_Date);
            } catch (Exception e) {
                e.printStackTrace();
                showToast("Please check network and try again!");
            }
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(PROGRESS_DIALOG);
//            dd_lyt.setVisibility(View.GONE);
//            pay_dd_lyt.setVisibility(View.GONE);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            removeDialog(PROGRESS_DIALOG);
            try {
                if (null != ServiceHelper.Opdata_Chalana && !ServiceHelper.Opdata_Chalana.equals("NA") && !"0".equals(ServiceHelper.Opdata_Chalana)) {
                    sucessFull_DialogMSG(ServiceHelper.Opdata_Chalana);
                    tckt_UPdated_Flag = "Y";
                } else {
                    sucessFull_DialogMSG("Updation Failed \n Please try again");
                    tckt_UPdated_Flag = "N";
                }
            } catch (Exception e) {
                e.printStackTrace();
                sucessFull_DialogMSG("Updation Failed \n Please try again");
                tckt_UPdated_Flag = "N";
            }


        }
    }

    public void sucessFull_DialogMSG(String msg) {
        TextView title = new TextView(this);
        title.setText("Hyderabad E-Ticket");
        title.setBackgroundColor(Color.BLUE);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(26);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dialog_logo, 0, R.drawable.dialog_logo, 0);
        title.setPadding(20, 0, 20, 0);
        title.setHeight(70);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DDCloseActiviy.this, AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setCustomTitle(title);
        alertDialogBuilder.setIcon(R.drawable.dialog_logo);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//                dd_lyt.setVisibility(View.GONE);
//                pay_dd_lyt.setVisibility(View.GONE);
//                et_dp_regno.setText("");
//                btn_dp_date_selection.setText("Select Date");
                if (tckt_UPdated_Flag.equals("Y")) {
                    Intent intent = new Intent(getApplicationContext(), DDCloseActiviy.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
//                    finish();
                    alertDialogBuilder.create().dismiss();
                }

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().getAttributes();

        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(22);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);

        Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btn.setTextSize(22);
        btn.setTextColor(Color.WHITE);
        btn.setTypeface(btn.getTypeface(), Typeface.BOLD);
        btn.setBackgroundColor(Color.BLUE);

    }

    private class Async_getDD_details extends AsyncTask<Void, Void, String> {
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
            removeDialog(PROGRESS_DIALOG);

            online_report_status = "";
            if (null != ServiceHelper.Opdata_Chalana && !ServiceHelper.Opdata_Chalana.equals("NA")) {

                try {
                    JSONObject jsonObject = new JSONObject(ServiceHelper.Opdata_Chalana);
                    JSONArray jsonArray = jsonObject.getJSONArray("Challan Details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.toString().isEmpty() || jsonObject1.length() == 0) {
                            showToast("No data Found");
                        } else {
                            dd_lyt.setVisibility(View.VISIBLE);
                            pay_dd_lyt.setVisibility(View.VISIBLE);
                            lytConFrom.setVisibility(View.GONE);
                            lytConTo.setVisibility(View.GONE);
                            lytConDays.setVisibility(View.GONE);
                            lytFineAmnt.setVisibility(View.GONE);
                            vEHICLE_NUMBER = jsonObject1.getString("VEHICLE NUMBER");
                            chall_No = jsonObject1.getString("CHALLAN NUMBER");
                            chall_Type = jsonObject1.getString("CHALLAN_TYPE");
                            violations = jsonObject1.getString("VIOLATIONS");
                            offence_Date = jsonObject1.getString("OFFENCE DATE");
                            driver_Adhar = jsonObject1.getString("DRIVER AADHAAR");
                            driver_Mobile = jsonObject1.getString("DRIVER MOBILE");
                            driver_LCNCE = jsonObject1.getString("DRIVING LICENSE");
                            driver_DL_DOB = jsonObject1.getString("DRIVER_LICENSE_DOB");
                            unit_CODE = jsonObject1.getString("UNIT_CODE");
                            pid_CODE = jsonObject1.getString("PID_CODE");
                            ps_CODE = jsonObject1.getString("PS_CODE");
                            Reg_No.setText(vEHICLE_NUMBER);
                            Challan_No.setText(chall_No);
                            Offender_Date.setText(offence_Date);
                            if (!Objects.equals("null", driver_Mobile)) {
                                edtTxt_Mob_No.setVisibility(View.GONE);
                                Mobile_No.setVisibility(View.VISIBLE);
                                Mobile_No.setText(driver_Mobile);
                            } else {
                                edtTxt_Mob_No.setVisibility(View.VISIBLE);
                                Mobile_No.setVisibility(View.GONE);
                            }
                            if (!Objects.equals("null", driver_Adhar)) {
                                edtTxt_Aadhar_No.setVisibility(View.GONE);
                                Aadhar_No.setVisibility(View.VISIBLE);
                                Aadhar_No.setText(driver_Adhar);
                            } else {
                                edtTxt_Aadhar_No.setVisibility(View.VISIBLE);
                                Aadhar_No.setVisibility(View.GONE);
                            }
                            if (!Objects.equals("null", driver_LCNCE)) {
                                edtTxt_DlNo.setVisibility(View.GONE);
                                Dl_No.setVisibility(View.VISIBLE);
                                Dl_No.setText(driver_LCNCE);

                            } else {
                                edtTxt_DlNo.setVisibility(View.VISIBLE);
                                Dl_No.setVisibility(View.GONE);
                            }

                            if (!Objects.equals("null", driver_DL_DOB) && (!Objects.equals("null", driver_LCNCE))) {
                                btn_Dl_dob.setText(driver_DL_DOB);

                            } else {
                                btn_Dl_dob.setText("Select Date");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast("" + getResources().getString(R.string.no_day_report));
                    dd_lyt.setVisibility(View.GONE);
                    pay_dd_lyt.setVisibility(View.GONE);
                }

            } else {
                online_report_status = "";
                showToast("" + getResources().getString(R.string.no_day_report));
                dd_lyt.setVisibility(View.GONE);
                pay_dd_lyt.setVisibility(View.GONE);
            }
        }
    }

    /*private class Aysnc_Print_Data extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // printResponse();
            if (bluetoothAdapter == null) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showToast("Bluetooth NOT support");
                    }
                });
            } else {
                if (bluetoothAdapter.isEnabled()) {
                    if (bluetoothAdapter.isDiscovering()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Bluetooth is currently in device discovery process.");
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Bluetooth is Enabled");
                            }
                        });

                        if (!print_Data.equals("")) {


                            try {

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
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }

                                        } else if ((print_apptype.get(selected_type).toString().trim()
                                                .equals("" + getResources().getString(R.string.dup_spot_challan)))
                                                && (!print_respose.get(selected_type).equals(""))) {
                                            try {

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
                                                        showToast("Check Your Device is Working Condition!");
                                                    }
                                                });
                                            }
                                        } else if ((print_apptype.get(selected_type).toString().trim()
                                                .equals("" + getResources().getString(R.string.dup_vhcle_hstry)))
                                                && (!print_respose.get(selected_type).equals(""))) {

                                            try {
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
                                    *//*
                                     * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 *//*
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
                                    *//*
                                     * String printdata =
									 * bth_printer.font_Courier_41(""+
									 * ServiceHelper.Opdata_Chalana);
									 * actual_printer.Call_PrintertoPrint("" +
									 * address, "" + printdata);
									 *//*

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
                                            *//*
                                             * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 *//*

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
                                            *//*
                                             * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 *//*

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
                                            *//*
                                             * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 *//*
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
                                            *//*
                                             * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 *//*

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
                                            *//*
                                             * String printdata =
											 * bth_printer.font_Courier_41(""+
											 * print_respose.get(selected_type))
											 * ; actual_printer.
											 * Call_PrintertoPrint(""+ address,
											 * "" + printdata);
											 *//*
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
*/
    protected void selectImage() {
        // TODO Auto-generated method stub
        if (selection_Pic_Flag.equals("1")) {
            final CharSequence[] options = {"Open Camera", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(DDCloseActiviy.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {
                   /* FileProvider.getUriForFile(SpotChallan.this,
                            BuildConfig.APPLICATION_ID + ".fileProvider"*/

                    if (options[item].equals("Open Camera")) {
                        if (Build.VERSION.SDK_INT <= 23) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            startActivityForResult(intent, 1);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(DDCloseActiviy.this,
                                    BuildConfig.APPLICATION_ID + ".provider", f));
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 1);
                        }
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();

                    }
                }
            });
            builder.show();
        } else if (selection_Pic_Flag.equals("2")) {
            final CharSequence[] options = {"Open Camera", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(DDCloseActiviy.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Open Camera")) {
                        if (Build.VERSION.SDK_INT <= 23) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            startActivityForResult(intent, 1);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(DDCloseActiviy.this,
                                    BuildConfig.APPLICATION_ID + ".provider", f));
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 1);
                        }
                    } else if (options[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();

                    }
                }
            });
            builder.show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
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
                    String current_date = dateUtil.getTodaysDate();
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "COURTAPP"
                            + File.separator + current_date;
                    File camerapath = new File(path);

                    if (!camerapath.exists()) {
                        camerapath.mkdirs();
                    }
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {
                        if ("1".equals(selection_Pic_Flag) && bitmap != null) {
                            outFile = new FileOutputStream(file);
                            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                            Display d = getWindowManager().getDefaultDisplay();
                            int x = d.getWidth();
                            int y = d.getHeight();
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mutableBitmap, y, x, true);
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            mutableBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                            outFile.flush();
                            outFile.close();
                            new SingleMediaScanner(this, file);
                            imgView_CourtOrderCopy.setRotation(0);
                            imgView_CourtOrderCopy.setImageBitmap(mutableBitmap);
                            // imgView_CourtOrderCopy.setRotation(-90);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
                            byteArray = bytes.toByteArray();
                            img_dataCourtCopy = Base64.encodeToString(byteArray, Base64.NO_WRAP);

                        } else if ("2".equals(selection_Pic_Flag) && bitmap != null) {
                            outFile = new FileOutputStream(file);
                            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                            Display d = getWindowManager().getDefaultDisplay();
                            int x = d.getWidth();
                            int y = d.getHeight();
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mutableBitmap, y, x, true);
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90);
                            mutableBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                            outFile.flush();
                            outFile.close();
                            new SingleMediaScanner(this, file);
                            imgView_DLCopy.setRotation(0);
                            imgView_DLCopy.setImageBitmap(mutableBitmap);
                            //imgView_DLCopy.setRotation(-90);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
                            byteArray = bytes.toByteArray();
                            img_dataDLCopy = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        } else {
                            showToast("Image Cannot be Loaded !");
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        img_dataCourtCopy = "";


                    } catch (IOException e) {
                        e.printStackTrace();
                        img_dataCourtCopy = "";


                    } catch (Exception e) {
                        e.printStackTrace();
                        img_dataCourtCopy = "";

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    img_dataCourtCopy = "";
                }

            } else if (requestCode == 2) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    if (null != c) {
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        picturePath = c.getString(columnIndex);
                        c.close();
                        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                        if ("1".equals(selection_Pic_Flag) && thumbnail != null) {
                            Bitmap mutableBitmap = thumbnail.copy(Bitmap.Config.ARGB_8888, true);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            imgView_CourtOrderCopy.setRotation(0);
                            imgView_CourtOrderCopy.setImageBitmap(mutableBitmap);

                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
                            byteArray = bytes.toByteArray();
                            img_dataCourtCopy = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        } else if ("2".equals(selection_Pic_Flag) && thumbnail != null) {
                            Bitmap mutableBitmap = thumbnail.copy(Bitmap.Config.ARGB_8888, true);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            imgView_DLCopy.setRotation(0);
                            imgView_DLCopy.setImageBitmap(mutableBitmap);
                            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
                            byteArray = bytes.toByteArray();
                            img_dataDLCopy = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        } else {
                            showToast("Image Cannot be Loaded !");
                        }
                    } else {
                        img_dataCourtCopy = "";
                        showToast("Image Cannot be Loaded !");
                    }
                } catch (Exception e) {
                    img_dataCourtCopy = "";
                    img_dataDLCopy = "";

                }
            }
        }
    }

    private void showToast(String msg) {
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

    public void dateReset() {
        cal = Calendar.getInstance();
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);
    }

    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener md1 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
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
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_courtAtnd = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_courtAttenddate.setText(date_courtAtnd.toUpperCase());

        }

    };
    DatePickerDialog.OnDateSetListener md3 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;
            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convFRom = "";
            date_convFRom = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_courtConFromdate.setText(date_convFRom.toUpperCase());
            btn_courtConTo.setText("Select Date");
            edtTxtConDays.setText("");
            try {
                date_From = null;
                date_From = format.parse(date_convFRom);
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
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convicTo = format.format(new Date(present_year - 1900, (present_month), present_day));
            dayDifference = "";
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");

            //Setting dates
            try {
                date1 = dates.parse(date_convFRom);
                date2 = dates.parse(date_convicTo);
                if (date2.after(date1) || date2.equals(date1)) {
                    btn_courtConTo.setText(date_convicTo.toUpperCase());

                    //Comparing dates
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);

                    //Convert long to String
                    dayDifference = Long.toString(differenceDates);
                    edtTxtConDays.setText(dayDifference);
                } else {
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
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_convicTo = format.format(new Date(present_year - 1900, (present_month), present_day));
            //btn_courtSoclServceTodate.setText("" + date_convicTo.toUpperCase());
            dayDifference = "";

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");


            //Setting dates
            try {


                date1 = dates.parse(date_convFRom);
                date2 = dates.parse(date_convicTo);
                if (date2.after(date1) || date2.equals(date1)) {

                    btn_courtSoclServceTodate.setText("" + date_convicTo.toUpperCase());
                    //Comparing dates
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);

                    //Convert long to String
                    dayDifference = Long.toString(differenceDates);
                    edtTxtRisDays.setText(dayDifference);
                } else {
                    showToast("Date should be greater than From_Date ");
                    btn_courtSoclServceTodate.setText("Select Date");
                    edtTxtRisDays.setText("");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    };


    DatePickerDialog.OnDateSetListener md_DL_SUS_FROM = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_DL_SUS_FROM = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_DLSUS_FromDate.setText("" + date_DL_SUS_FROM.toUpperCase());


        }
    };

    DatePickerDialog.OnDateSetListener md_DL_SUS_TO = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_DL_SUS_TO = format.format(new Date(present_year - 1900, (present_month), present_day));
            //btn_courtSoclServceTodate.setText("" + date_convicTo.toUpperCase());
            dayDifference = "";

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");


            //Setting dates
            try {


                date1 = dates.parse(date_DL_SUS_FROM);
                date2 = dates.parse(date_DL_SUS_TO);
                if (date2.after(date1) || date2.equals(date1)) {

                    btn_DLSUS_ToDate.setText("" + date_DL_SUS_TO.toUpperCase());
                    //Comparing dates
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);

                    //Convert long to String
                    dayDifference = Long.toString(differenceDates);
                    edtTxt_DL_SUSDAYS.setText(dayDifference);
                } else {
                    showToast("Date should be greater than From_Date ");
                    btn_DLSUS_ToDate.setText("Select Date");
                    edtTxt_DL_SUSDAYS.setText("");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    };

    DatePickerDialog.OnDateSetListener md1_DD_DL_DOB = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;
            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_dd_dl_dob = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_Dl_dob.setText("" + date_dd_dl_dob.toUpperCase());
            Log.i("DAY REPORT : ", "" + date_dd_dl_dob);

        }
    };

    DatePickerDialog.OnDateSetListener md_DL_CANCEL = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            date_DL_SUS_FROM = "";
            date_DL_SUS_FROM = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_dateSeltion_DL_Canln.setText("" + date_DL_SUS_FROM.toUpperCase());
        }
    };


    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PRESENT_DATE_PICKER:
                dateReset();
                DatePickerDialog dp_offence_date = new DatePickerDialog(this, md1, present_year, present_month,
                        present_day);

                dp_offence_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offence_date;
            case PRESENT_COURT_ATTEND_DATE:
                dateReset();
                DatePickerDialog dp_courtAtnd_date = new DatePickerDialog(this, md2, present_year, present_month,
                        present_day);

                dp_courtAtnd_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_courtAtnd_date;
            case PRESENT_COURT_CONVI_FROM:
                dateReset();
                DatePickerDialog dp_courtConFrom_date = new DatePickerDialog(this, md3, present_year, present_month,
                        present_day);

                //dp_courtConFrom_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                // dp_courtConFrom_date.getDatePicker().setMinDate(Long.parseLong(date_convFRom));
                return dp_courtConFrom_date;
            case PRESENT_COURT_CONVI_TO:
                dateReset();
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
                dateReset();
                DatePickerDialog dp_SCL_SRC_FROM = new DatePickerDialog(this, md_scl_servceFrom, present_year, present_month,
                        present_day);

                dp_SCL_SRC_FROM.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_SCL_SRC_FROM;

            case PRESENT_COURT_SCL_SRC_TO:
                dateReset();
                DatePickerDialog dp_SCL_SRC_TO = new DatePickerDialog(this, md_scl_servceTo, present_year, present_month,
                        present_day);

                dp_SCL_SRC_TO.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_SCL_SRC_TO;


            case PRESENT_DL_SUS_FROM:
                dateReset();
                DatePickerDialog dp_DL_SUS_FROM = new DatePickerDialog(this, md_DL_SUS_FROM, present_year, present_month,
                        present_day);

                // dp_DL_SUS_FROM.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_DL_SUS_FROM;

            case PRESENT_DL_SUS_TO:
                dateReset();
                DatePickerDialog dp_DL_SUS_TO = new DatePickerDialog(this, md_DL_SUS_TO, present_year, present_month,
                        present_day);

                // dp_DL_SUS_TO.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_DL_SUS_TO;
            case PRESENT_DD_DL_DOB:
                dateReset();
                DatePickerDialog dp_DD_DL_DOB = new DatePickerDialog(this, md1_DD_DL_DOB, present_year, present_month,
                        present_day);

                dp_DD_DL_DOB.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_DD_DL_DOB;

            case PRESENT_DD_DL_CanCel:
                dateReset();
                DatePickerDialog dp_DL_CANCEL = new DatePickerDialog(this, md_DL_CANCEL, present_year, present_month,
                        present_day);

                // dp_DL_SUS_FROM.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_DL_CANCEL;

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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DDCloseActiviy.this,
                        AlertDialog.THEME_HOLO_LIGHT);
                alertDialogBuilder.setCustomTitle(title);
                alertDialogBuilder.setIcon(R.drawable.dialog_logo);
                alertDialogBuilder.setMessage(print_Data);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("PRINT", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Printing", Toast.LENGTH_LONG).show();

                    }
                });

                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
