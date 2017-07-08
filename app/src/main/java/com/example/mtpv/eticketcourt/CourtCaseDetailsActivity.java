package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.adapter.CustomRecyclerViewAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CourtCaseDetailsActivity extends Activity {
    private static RecyclerView.Adapter custom_CourtCase_DetailsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static RecyclerView recyclerView;
    public static AppCompatButton btn_Update_Case_Details;
    public static ArrayList<CasesDetailsPojo> arrayList_CourtCase_Detilas;
    JSONArray jsonArray_caseDetails;
    JSONObject jsonObject;
    Bundle bundle;

    Button btn_councelling_Date;
    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;

    final int COUNCELLING_DATE_PICKER = 1;

    String councelng_Date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtcase_details);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        btn_Update_Case_Details = (AppCompatButton) findViewById(R.id.btn_Update_CasesDetails);
        btn_councelling_Date=(Button)findViewById(R.id.btn_Councelling_Date);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cal = Calendar.getInstance();
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);

        bundle = getIntent().getExtras();
        String array_Value = bundle.getString("ArrayValue");

        if (array_Value.equals("DD_Bkd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_Booked;
        }
        if (array_Value.equals("Txt_DD_CouncelngNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_CouncelngNot_Atnd;
        }
        if (array_Value.equals("CHG_Bkd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_Booked;
        }
        if (array_Value.equals("Txt_CHG_CouncelngNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_CouncelngNot_Atnd;
        }
        if (array_Value.equals("Txt_DD_CourtNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_CourtNot_Atnd;
        }
        if (array_Value.equals("Txt_CHG_CourtNot_Atnd")) {
            arrayList_CourtCase_Detilas = new ArrayList<>();
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_CourtNot_Atnd;
        }

        custom_CourtCase_DetailsAdapter = new CustomRecyclerViewAdapter(this, arrayList_CourtCase_Detilas);
        recyclerView.setAdapter(custom_CourtCase_DetailsAdapter);
        btn_councelling_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(COUNCELLING_DATE_PICKER);
            }
        });

        btn_Update_Case_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonArray_caseDetails = new JSONArray();
                jsonObject = new JSONObject();
                for (CasesDetailsPojo casesDetailsPojo : arrayList_CourtCase_Detilas) {
                    if (casesDetailsPojo.isSelected()) {

                        try {

                            jsonObject.put("VEHICLE_NUMBER", casesDetailsPojo.getVEHICLE_NUMBER());
                            jsonObject.put("CHALLAN_NUMBER", casesDetailsPojo.getCHALLAN_NUMBER());
                            jsonObject.put("DRIVER_LICENSE", casesDetailsPojo.getDRIVER_LICENSE());
                            jsonObject.put("DRIVER_AADHAAR", casesDetailsPojo.getDRIVER_AADHAAR());
                            jsonObject.put("DRIVER_MOBILE", casesDetailsPojo.getDRIVER_MOBILE());
                            jsonObject.put("CHALLAN_TYPE", casesDetailsPojo.getCHALLAN_TYPE());
                            jsonObject.put("PAYMENT_STATUS", casesDetailsPojo.getPAYMENT_STATUS());
                            jsonObject.put("COUNC_STATUS", casesDetailsPojo.getCOUNC_STATUS());
                            jsonObject.put("COUNC_DATE", casesDetailsPojo.getCOUNC_DATE());
                            jsonObject.put("COURT_NOTICE_DT", casesDetailsPojo.getCOURT_NOTICE_DT());
                            jsonObject.put("DISPOSAL_CD", casesDetailsPojo.getDISPOSAL_CD());
                            jsonObject.put("SELECTED_COUNC_DATE",councelng_Date);

                            jsonArray_caseDetails.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        if (stringBuilder.length() > 0)
//                            stringBuilder.append(", ");
//                        stringBuilder.append(casesDetailsPojo.getCHALLAN_NUMBER());
                    }
                }
                JSONObject caseDetailsObj = new JSONObject();
                try {
                    caseDetailsObj.put("Case_Updation_Details", jsonArray_caseDetails);
                    Log.d("Case_Details", "" + caseDetailsObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), caseDetailsObj.toString(), Toast.LENGTH_LONG).show();

            }
        });

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


    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener councelling_Date_Dialog = new DatePickerDialog.OnDateSetListener() {

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
            councelng_Date = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_councelling_Date.setText("" + councelng_Date.toUpperCase());



        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch (id) {
            case COUNCELLING_DATE_PICKER:
                DatePickerDialog dp_councelling_Date = new DatePickerDialog(this, councelling_Date_Dialog, present_year, present_month,
                        present_day);

                dp_councelling_Date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_councelling_Date;


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
