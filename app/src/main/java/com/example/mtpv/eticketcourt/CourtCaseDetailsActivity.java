package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.adapter.CustomAdapter;
import com.example.mtpv.eticketcourt.adapter.CustomRecyclerViewAdapter;
import com.example.mtpv.eticketcourt.service.DBHelper;
import com.example.mtpv.eticketcourt.service.ServiceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CourtCaseDetailsActivity extends Activity {

    private static RecyclerView.Adapter custom_CourtCase_DetailsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerView;
    AppCompatButton btn_Update_Case_Details;
    public static ArrayList<CasesDetailsPojo> arrayList_CourtCase_Detilas;
    JSONObject jsonObject;
    Bundle bundle;
    Button btn_councelling_Date;
    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;
    final int COUNCELLING_DATE_PICKER = 1;
    final int PROGRESS_DIALOG = 2;
    String councelng_Date;
    ArrayList<String> mArrayListCourtNames = new ArrayList<>();
    HashMap<String, String> paramsCourt = new HashMap<>();
    DBHelper db;
    Cursor c, cursor_courtnames;
    ArrayList<String> courtNames;
    MaterialSpinner courtspinner;
    String selectedCourtCode, selectedCourtAddress;
    String selectedCourtName;
    String jsonResult;
    String spinnerAvailblity;
    String sms_key, btn_Txt;
    TextView compny_Name;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtcase_details);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        btn_Update_Case_Details = (AppCompatButton) findViewById(R.id.btn_Update_CasesDetails);
        btn_councelling_Date = (Button) findViewById(R.id.btn_Councelling_Date);
        courtspinner = (MaterialSpinner) findViewById(R.id.courtSpinner);
        compny_Name = (TextView) findViewById(R.id.CompanyName);
        Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
        compny_Name.startAnimation(marquee);
        layoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this) );
        recyclerView.setHasFixedSize(true);
        db = new DBHelper(getApplicationContext());
        getCourtNamesFromDB();
        cal = Calendar.getInstance();
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);
        bundle = getIntent().getExtras();
        String array_Value = bundle.getString("ArrayValue");

        if (array_Value.equals("DD_Bkd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_Booked;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.VISIBLE);
            spinnerAvailblity = "1";
            sms_key = "COURT";
            btn_Txt = "COURT ATTEND";
        }

        if (array_Value.equals("Txt_DD_CouncelngNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_CouncelngNot_Atnd;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.GONE);
            spinnerAvailblity = "0";
            sms_key = "COUNC";
            btn_Txt = "COUNCELLING";
        }

        if (array_Value.equals("Txt_DD_CourtNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_CourtNot_Atnd;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.VISIBLE);
            spinnerAvailblity = "1";
            sms_key = "COURT";
            btn_Txt = "COURT ATTEND";
        }

        if (array_Value.equals("CHG_Bkd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_Booked;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.VISIBLE);
            spinnerAvailblity = "1";
            sms_key = "COURT";
            btn_Txt = "COURT ATTEND";
        }

        if (array_Value.equals("Txt_CHG_CouncelngNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_CouncelngNot_Atnd;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.GONE);
            spinnerAvailblity = "0";
            sms_key = "COUNC";
            btn_Txt = "COUNCELLING";
        }

        if (array_Value.equals("Txt_CHG_CourtNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_CourtNot_Atnd;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.VISIBLE);
            spinnerAvailblity = "1";
            sms_key = "COURT";
            btn_Txt = "COURT ATTEND";
        }

        btn_councelling_Date.setText("Select " + btn_Txt + " Date");

        //Top Violator

       /* if (array_Value.equals("TV_Bkd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_TV_Booked;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.VISIBLE);
            spinnerAvailblity = "1";
            sms_key = "COURT";
        }

        if (array_Value.equals("Txt_TV_CouncelngNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_TV_CouncelngNot_Atnd;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.GONE);
            spinnerAvailblity = "0";
            sms_key = "COUNC";
        }

        if (array_Value.equals("Txt_TV_CourtNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_TV_CourtNot_Atnd;
            btn_councelling_Date.setVisibility(View.VISIBLE);
            courtspinner.setVisibility(View.VISIBLE);
            spinnerAvailblity = "1";
            sms_key = "COURT";
        }*/


        CustomRecyclerViewAdapter custom_CourtCase_DetailsAdapter = new CustomRecyclerViewAdapter(this,arrayList_CourtCase_Detilas);
        recyclerView.setAdapter(custom_CourtCase_DetailsAdapter);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mArrayListCourtNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courtspinner.setAdapter(dataAdapter);

        courtspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCourtName = courtspinner.getSelectedItem().toString();
                for (String mapCourtName : paramsCourt.keySet()) {
                    if (selectedCourtName.equals(mapCourtName)) {
                        selectedCourtCode = paramsCourt.get(mapCourtName);
                        break;
                    }
                }
                /*for (String mapCourt_Name : paramsCourt_Address.keySet()) {
                    if (selectedCourtName.equals(mapCourt_Name)) {
                        selectedCourtAddress = paramsCourt_Address.get(mapCourt_Name);
                        break;
                    }
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_councelling_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(COUNCELLING_DATE_PICKER);
            }
        });

        btn_Update_Case_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_councelling_Date.getText().toString().equals("Select " + btn_Txt + " Date")) {
                    showToast("Please select date!");
                } else if (spinnerAvailblity.equals("1") && selectedCourtCode == null) {
                    showToast("Please select CourtName!");
                } else {
                    JSONArray jsonArray_caseDetails = new JSONArray();
                    for (CasesDetailsPojo casesDetailsPojo : arrayList_CourtCase_Detilas) {
                        if (casesDetailsPojo.isSelected()) {
                            jsonObject = new JSONObject();
                            if (sms_key.equals("COURT") && casesDetailsPojo.getDRIVER_AADHAAR().equals("")) {
                                showToast("Please Enter Aadhaar Number!");
                                jsonArray_caseDetails = new JSONArray();
                                break;
                            } else if (sms_key.equals("COURT") && casesDetailsPojo.getDRIVER_AADHAAR().length() < 12) {
                                showToast("Please Enter valid Aadhaar Number!");
                                jsonArray_caseDetails = new JSONArray();
                                break;
                            } else if (casesDetailsPojo.getDRIVER_MOBILE().equals("")) {
                                showToast("Please enter Mobile Number!");
                                jsonArray_caseDetails = new JSONArray();
                                break;
                            } else if (casesDetailsPojo.getDRIVER_MOBILE().length() < 10) {
                                showToast("Please enter valid Mobile Number!");
                                jsonArray_caseDetails = new JSONArray();
                                break;
                            } else {
                                try {
                                    if ("null".equals(selectedCourtAddress)) {
                                        selectedCourtAddress = "";
                                    }

                                    jsonObject.put("VEHICLE_NUMBER", casesDetailsPojo.getVEHICLE_NUMBER());
                                    jsonObject.put("CHALLAN_NUMBER", casesDetailsPojo.getCHALLAN_NUMBER());
                                    jsonObject.put("DRIVER_LICENSE", casesDetailsPojo.getDRIVER_LICENSE());
                                    jsonObject.put("DRIVER_AADHAAR", casesDetailsPojo.getDRIVER_AADHAAR());
                                    jsonObject.put("DRIVER_MOBILE", casesDetailsPojo.getDRIVER_MOBILE());
                                    jsonObject.put("DRIVER_NAME", casesDetailsPojo.getDRIVER_NAME());
                                    jsonObject.put("VIOLATION", casesDetailsPojo.getVIOLATION());
                                    jsonObject.put("DISTRICT_NAME", casesDetailsPojo.getDISTRICT_NAME());
                                    jsonObject.put("SELECTED_COUNC_DATE", councelng_Date);
                                    jsonObject.put("COURT_NAME", selectedCourtName != null ? selectedCourtName : "");
                                    jsonObject.put("COURT_CODE", selectedCourtCode);
                                    jsonObject.put("COURT_ADDRESS", selectedCourtAddress);
                                    jsonObject.put("PID_CODE", MainActivity.user_id);
                                    jsonObject.put("PID_NAME", MainActivity.arr_logindetails[1]);
                                    jsonObject.put("PID_MOBILE", MainActivity.arr_logindetails[6]);
                                    jsonObject.put("SMS_KEY", sms_key);
                                    jsonArray_caseDetails.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    jsonObject = new JSONObject();
                                    jsonArray_caseDetails = new JSONArray();

                                }
                            }
                        }
                    }
                    JSONObject caseDetailsObj = new JSONObject();
                    try {
                        caseDetailsObj.put("Case_Updation_Details", jsonArray_caseDetails);
                        if (jsonArray_caseDetails.length() == 0) {
                            showToast("Please fill the details");
                        } else {
                            jsonResult = caseDetailsObj.toString();
                            if (isOnline()) {
                                new Async_sendCourtCasesInfo().execute();
                            } else {
                                showToast("Please check your net work connection");
                            }
//                        Toast.makeText(getApplicationContext(), caseDetailsObj.toString(), Toast.LENGTH_LONG).show();
                            //Log.d("Case_Details", "" + caseDetailsObj.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        jsonResult = "";
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

    private class Async_sendCourtCasesInfo extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {

            ServiceHelper.sendCourtCasesInfo("" + jsonResult);
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(PROGRESS_DIALOG);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            removeDialog(PROGRESS_DIALOG);
            if (null != ServiceHelper.Opdata_Chalana && !ServiceHelper.Opdata_Chalana.equals("NA")) {
                try {
                    showToast(ServiceHelper.Opdata_Chalana);
                    Intent intent = new Intent(getApplicationContext(), CourtCaseStatusActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    showToast("Updation Failed Please try again");
                }
            } else {
                showToast("Updation Failed Please try again");
            }
        }
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
//                paramsCourt_Address=new HashMap<>();
                while (!cursor_courtnames.isAfterLast()) {
                    try {

                        mArrayListCourtNames.add(cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_name_settings)));

                        paramsCourt.put(cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_name_settings)), cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_code_settings)));
//                        paramsCourt_Address.put(cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_name_settings)), cursor_courtnames.getString(cursor_courtnames.getColumnIndex(db.court_address_settings)));
                        cursor_courtnames.moveToNext();
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("Please download the Masters");
                    }
                }
            }
        } catch (SQLException e) {
            paramsCourt = new HashMap<>();
//            paramsCourt_Address=new HashMap<>();
            showToast("Please download the Masters");
            e.printStackTrace();
        }
        cursor_courtnames.close();
        db.close();

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

    DatePickerDialog.OnDateSetListener councelling_Date_Dialog = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;
            format = new SimpleDateFormat("dd-MMM-yyyy");
            councelng_Date = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_councelling_Date.setText(councelng_Date.toUpperCase());
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case COUNCELLING_DATE_PICKER:
                DatePickerDialog dp_councelling_Date;
                dp_councelling_Date = new DatePickerDialog(this, councelling_Date_Dialog, present_year, present_month,
                        present_day);

//                dp_councelling_Date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_councelling_Date;
            case PROGRESS_DIALOG:
                ProgressDialog pd = ProgressDialog.show(this, "", "Please Wait...", true);
                pd.setContentView(R.layout.custom_progress_dialog);
                pd.setCancelable(false);
                return pd;
            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (CasesDetailsPojo casesDetailsPojo : arrayList_CourtCase_Detilas) {
            // if (casesDetailsPojo.isSelected()) {
            casesDetailsPojo.setSelected(false);
            // }
        }
    }

}
