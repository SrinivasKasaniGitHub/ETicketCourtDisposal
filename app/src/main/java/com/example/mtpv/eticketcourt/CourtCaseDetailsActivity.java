package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
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
    public static RecyclerView recyclerView;
    public static AppCompatButton btn_Update_Case_Details;
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
    ArrayList<String> mArrayListCourtNames = new ArrayList<String>();
    HashMap<String, String> paramsCourt = new HashMap<String, String>();
    DBHelper db;
    Cursor c, cursor_courtnames;
    ArrayList<String> courtNames;
    MaterialSpinner courtspinner;
    String selectedCourtCode;
    String selectedCourtName;
    String jsonResult;
    String spinnerAvailblity;
    String sms_key;
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
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
            }

            if (array_Value.equals("Txt_DD_CouncelngNot_Atnd")) {
                arrayList_CourtCase_Detilas = new ArrayList<>();
                arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_CouncelngNot_Atnd;
                btn_councelling_Date.setVisibility(View.VISIBLE);
                courtspinner.setVisibility(View.GONE);
                spinnerAvailblity = "0";
                sms_key = "COUNC";
            }

            if (array_Value.equals("CHG_Bkd")) {
                arrayList_CourtCase_Detilas = new ArrayList<>();
                arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_Booked;
                btn_councelling_Date.setVisibility(View.VISIBLE);
                courtspinner.setVisibility(View.VISIBLE);
                spinnerAvailblity = "1";
                sms_key = "COURT";
            }

            if (array_Value.equals("Txt_CHG_CouncelngNot_Atnd")) {
                arrayList_CourtCase_Detilas = new ArrayList<>();
                arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_CouncelngNot_Atnd;
                btn_councelling_Date.setVisibility(View.VISIBLE);
                courtspinner.setVisibility(View.GONE);
                spinnerAvailblity = "0";
                sms_key = "COUNC";
            }

            if (array_Value.equals("Txt_DD_CourtNot_Atnd")) {
                arrayList_CourtCase_Detilas = new ArrayList<>();
                arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_CourtNot_Atnd;
                btn_councelling_Date.setVisibility(View.VISIBLE);
                courtspinner.setVisibility(View.VISIBLE);
                spinnerAvailblity = "1";
                sms_key = "COURT";
            }
            if (array_Value.equals("Txt_CHG_CourtNot_Atnd")) {
                arrayList_CourtCase_Detilas = new ArrayList<>();
                arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_CourtNot_Atnd;
                btn_councelling_Date.setVisibility(View.VISIBLE);
                courtspinner.setVisibility(View.VISIBLE);
                spinnerAvailblity = "1";
                sms_key = "COURT";
            }


        custom_CourtCase_DetailsAdapter = new CustomRecyclerViewAdapter(this, arrayList_CourtCase_Detilas);
        recyclerView.setAdapter(custom_CourtCase_DetailsAdapter);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mArrayListCourtNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courtspinner.setAdapter(dataAdapter);

        courtspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCourtName = courtspinner.getSelectedItem().toString();

                Log.i("court map size : ", "" + paramsCourt.size());
                for (String mapCourtName : paramsCourt.keySet()) {
                    if (selectedCourtName.equals(mapCourtName)) {
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
        btn_councelling_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(COUNCELLING_DATE_PICKER);
            }
        });

        btn_Update_Case_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_councelling_Date.getText().toString().equals("Select Date")) {
                    showToast("Please select date!");
                } else if (spinnerAvailblity.equals("1") && selectedCourtCode == null) {
                    showToast("Please select CourtName!");
                } else {
                    JSONArray jsonArray_caseDetails = new JSONArray();
                    for (CasesDetailsPojo casesDetailsPojo : arrayList_CourtCase_Detilas) {
                        if (casesDetailsPojo.isSelected()) {
                            jsonObject = new JSONObject();
                            if (casesDetailsPojo.getDRIVER_AADHAAR().equals("")) {
                                showToast("Please Enter Aadhaar Number!");
                                jsonArray_caseDetails = new JSONArray();
                                break;
                            } else if (casesDetailsPojo.getDRIVER_AADHAAR().length() < 12) {
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

                                    jsonObject.put("VEHICLE_NUMBER", casesDetailsPojo.getVEHICLE_NUMBER());
                                    jsonObject.put("CHALLAN_NUMBER", casesDetailsPojo.getCHALLAN_NUMBER());
                                    jsonObject.put("DRIVER_LICENSE", casesDetailsPojo.getDRIVER_LICENSE());
                                    jsonObject.put("DRIVER_AADHAAR", casesDetailsPojo.getDRIVER_AADHAAR());
                                    jsonObject.put("DRIVER_MOBILE", casesDetailsPojo.getDRIVER_MOBILE());
                                    jsonObject.put("SELECTED_COUNC_DATE", councelng_Date);
                                    jsonObject.put("COURT_NAME", selectedCourtName!=null?selectedCourtName:"");
                                    jsonObject.put("COURT_CODE", selectedCourtCode);
                                    jsonObject.put("PID_CODE", MainActivity.user_id);
                                    jsonObject.put("PID_NAME", MainActivity.arr_logindetails[1]);
                                    jsonObject.put("PID_MOBILE", MainActivity.arr_logindetails[6]);
                                    jsonObject.put("SMS_KEY", sms_key);
                                    jsonArray_caseDetails.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                            new Async_sendCourtCasesInfo().execute();
//                        Toast.makeText(getApplicationContext(), caseDetailsObj.toString(), Toast.LENGTH_LONG).show();
                            Log.d("Case_Details", "" + caseDetailsObj.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
            Log.d("DD Details", "" + ServiceHelper.Opdata_Chalana);
            removeDialog(PROGRESS_DIALOG);
            showToast(ServiceHelper.Opdata_Chalana);
            Intent intent=new Intent(getApplicationContext(),CourtCaseStatusActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
            btn_councelling_Date.setText("" + councelng_Date.toUpperCase());
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case COUNCELLING_DATE_PICKER:
                DatePickerDialog dp_councelling_Date = new DatePickerDialog(this, councelling_Date_Dialog, present_year, present_month,
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
            if (casesDetailsPojo.isSelected()) {
                casesDetailsPojo.setSelected(false);
            }
        }
    }
}
