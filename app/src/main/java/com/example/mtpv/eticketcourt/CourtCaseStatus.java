package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
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
import com.example.mtpv.eticketcourt.service.ServiceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mtpv on 7/5/2017.
 */

public class CourtCaseStatus extends Activity {
    Button btn_offenceDate_From, btn_offenceDate_To, btn_get_details;
    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;

    final int OFFENCE_FROM_DATE_PICKER = 1;
    final int PROGRESS_DIALOG = 2;
    final int OFFENCE_TO_DATE_PICKER = 3;

    String offence_From_Date, offence_To_Date;
    CasesDetailsPojo casesDetailsPojo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtcases_status);
        btn_offenceDate_From = (Button) findViewById(R.id.btn_date_offence_Form);
        btn_offenceDate_To = (Button) findViewById(R.id.btn_date_offence_To);
        btn_get_details = (Button) findViewById(R.id.btn_CC_getdetails);
        cal = Calendar.getInstance();


		/* FOR DATE PICKER */
        present_year = cal.get(Calendar.YEAR);
        present_month = cal.get(Calendar.MONTH);
        present_day = cal.get(Calendar.DAY_OF_MONTH);

        btn_offenceDate_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(OFFENCE_FROM_DATE_PICKER);
            }
        });
        btn_offenceDate_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(OFFENCE_TO_DATE_PICKER);
            }
        });
        btn_get_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_offenceDate_From.getText().toString().equals("Select Date")) {
                    showToast("Please select Offence From date");
                } else if (btn_offenceDate_To.getText().toString().equals("Select Date")) {
                    showToast("Please select Offence To date");
                } else {
                    new Async_getCourtCasesInfo().execute();
                }
            }
        });

    }

    public class Async_getCourtCasesInfo extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {

            ServiceHelper.getCourtCasesInfo(MainActivity.user_id, offence_From_Date, offence_To_Date);


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

            removeDialog(PROGRESS_DIALOG);
            if (!ServiceHelper.Opdata_Chalana.equals("NA")) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(ServiceHelper.Opdata_Chalana);
                    JSONArray jsonArray = jsonObject.getJSONArray("Cases Details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jb = jsonArray.getJSONObject(i);
                        casesDetailsPojo=new CasesDetailsPojo();
                        casesDetailsPojo.setVEHICLE_NUMBER(jb.getString("VEHICLE_NUMBER")!=null ? jb.getString("VEHICLE_NUMBER"):"");
                        casesDetailsPojo.setCHALLAN_NUMBER(jb.getString("CHALLAN_NUMBER")!=null ? jb.getString("CHALLAN_NUMBER"): "");
                        casesDetailsPojo.setDRIVER_LICENSE(jb.getString("DRIVER_LICENSE")!=null ? jb.getString("DRIVER_LICENSE"): "") ;
                        casesDetailsPojo.setDRIVER_AADHAAR(jb.getString("DRIVER_AADHAAR")!=null ? jb.getString("DRIVER_AADHAAR"): "");
                        casesDetailsPojo.setDRIVER_MOBILE(null!=jb.getString("DRIVER_MOBILE") ? jb.getString("DRIVER_MOBILE"): "");
                        casesDetailsPojo.setCHALLAN_TYPE(null!=jb.getString("CHALLAN_TYPE") ? jb.getString("CHALLAN_TYPE"): "");
                        casesDetailsPojo.setPAYMENT_STATUS(null!=jb.getString("PAYMENT_STATUS") ? jb.getString("PAYMENT_STATUS"): "");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

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


    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener offence_FromDate_Dialog = new DatePickerDialog.OnDateSetListener() {

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
            offence_From_Date = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_offenceDate_From.setText("" + offence_From_Date.toUpperCase());

            Log.i("DAY REPORT : ", "" + offence_From_Date);


        }
    };

    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener offence_ToDate_Dialog = new DatePickerDialog.OnDateSetListener() {

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
            offence_To_Date = format.format(new Date(present_year - 1900, (present_month), present_day));

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");


            //Setting dates
            try {


                date1 = dates.parse(offence_From_Date);
                date2 = dates.parse(offence_To_Date);
                if (date2.after(date1) || date2.equals(date1)) {

                    btn_offenceDate_To.setText("" + offence_To_Date.toUpperCase());

                } else {
                    showToast("Date should be greater than From_Date ");
                    btn_offenceDate_To.setText("Select Date");

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
            case OFFENCE_FROM_DATE_PICKER:
                DatePickerDialog dp_offenceFrom_date = new DatePickerDialog(this, offence_FromDate_Dialog, present_year, present_month,
                        present_day);

                dp_offenceFrom_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offenceFrom_date;
            case OFFENCE_TO_DATE_PICKER:
                DatePickerDialog dp_offenceTo_date = new DatePickerDialog(this, offence_ToDate_Dialog, present_year, present_month,
                        present_day);

                dp_offenceTo_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offenceTo_date;

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
}
