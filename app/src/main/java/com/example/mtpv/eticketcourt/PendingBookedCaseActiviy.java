package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.service.ServiceHelper;

import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by mtpv on 7/5/2017.
 */

public class PendingBookedCaseActiviy extends Activity {

    MaterialSpinner year_spinner, month_spinner, day_spinner;
    Button btn_get_cases;
    ArrayList<String> years = new ArrayList<String>();
    static final String[] months = new String[]{"JAN", "FEB",
            "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP",
            "OCT", "NOV", "DEC"};
    ArrayList<String> days = new ArrayList<String>();

    String selected_year,selected_month,selected_day;
    String month_position;
    String month_NameorNum;
    String offence_Date;

    final int PROGRESS_DIALOG = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_booked);

        year_spinner = (MaterialSpinner) findViewById(R.id.yearSpinner);
        month_spinner = (MaterialSpinner) findViewById(R.id.monthSpinner);
        day_spinner = (MaterialSpinner) findViewById(R.id.daySpinner);
        btn_get_cases = (Button) findViewById(R.id.btn_getpending_bookedcases);

        int present_Year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = present_Year; i >= 2000; i--) {
            years.add(Integer.toString(i));
        }

        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(yearAdapter);
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_year=year_spinner.getSelectedItem().toString();
                String sd=selected_year;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> month_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, months);
        month_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(month_Adapter);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_month = month_spinner.getSelectedItem().toString();
                    month_position = String.valueOf(month_spinner.getSelectedItemPosition());

                    String sdf = selected_month;
                    // int sd=month_position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> day_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, days);
        day_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_spinner.setAdapter(day_Adapter);
        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_day=day_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_get_cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_year.equals("Year") && selected_month.equals("Month")&& selected_day.equals("Day")){
                    showToast("Please select atleast Year");
                }else if (!selected_year.equals("Year") && !selected_month.equals("Month")&& !selected_day.equals("Day")){
                    offence_Date=selected_day+"-"+selected_month+"-"+selected_year;
                    selected_year="";
                    month_NameorNum="";
                    new Async_getCourtCasesInfo().execute();
                }else if (selected_year.equals("Year") && !selected_month.equals("Month")){
                    showToast("Please select the Year");
                }else if (!selected_year.equals("Year") && !selected_month.equals("Month")){
                    month_NameorNum=month_position;
                    offence_Date="";
                    new Async_getCourtCasesInfo().execute();
                }else if (!selected_year.equals("Year") && selected_month.equals("Month")&& selected_day.equals("Day")){
                    offence_Date="";
                    month_NameorNum="";
                    new Async_getCourtCasesInfo().execute();
                }else if (selected_year.equals("Year") && selected_month.equals("Month")&& !selected_day.equals("Day")){
                    showToast("Please select Year");

                }




            }
        });



    }

    public class Async_getCourtCasesInfo extends AsyncTask<Void, Void, String> {
        @SuppressLint("DefaultLocale")
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... params) {

            ServiceHelper.getCourtCasesInfo( MainActivity.user_id, offence_Date, month_NameorNum);


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
            if (ServiceHelper.Opdata_Chalana.equals("0")) {
               // showToast("Updated failled ! ");

//                Intent intent_dashboard = new Intent(getApplicationContext(), Dashboard.class);
//                startActivity(intent_dashboard);
            }else {

            }



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


    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch (id) {

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
