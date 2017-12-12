package com.example.mtpv.eticketcourt;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.service.ServiceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CourtCaseStatusActivity extends Activity {
    Button btn_offenceDate_From, btn_offenceDate_To, btn_get_details;
    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;
    TextView compny_Name;
    final int OFFENCE_FROM_DATE_PICKER = 1;
    final int PROGRESS_DIALOG = 2;
    final int OFFENCE_TO_DATE_PICKER = 3;
    String offence_From_Date, offence_To_Date;
    CasesDetailsPojo casesDetailsPojo;
    RelativeLayout layout_TbleData;
    TextView Txt_DD_Bkd, Txt_DD_CouncelngNot_Atnd, Txt_DD_CourtNot_Atnd,Txt_CHG_Bkd, Txt_CHG_CouncelngNot_Atnd, Txt_CHG_CourtNot_Atnd,
            Txt_TV_Bkd, Txt_TV_CouncelngNot_Atnd, Txt_TV_CourtNot_Atnd;

    public static ArrayList<CasesDetailsPojo> arrayList_DD_Booked;
    public static ArrayList<CasesDetailsPojo> arrayList_DD_CouncelngNot_Atnd;
    public static ArrayList<CasesDetailsPojo> arrayList_DD_CourtNot_Atnd;
    public static ArrayList<CasesDetailsPojo> arrayList_CHG_Booked;
    public static ArrayList<CasesDetailsPojo> arrayList_CHG_CouncelngNot_Atnd;
    public static ArrayList<CasesDetailsPojo> arrayList_CHG_CourtNot_Atnd;
   /* public static ArrayList<CasesDetailsPojo> arrayList_TV_Booked;
    public static ArrayList<CasesDetailsPojo> arrayList_TV_CouncelngNot_Atnd;
    public static ArrayList<CasesDetailsPojo> arrayList_TV_CourtNot_Atnd;*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtcases_status);
        btn_offenceDate_From = (Button) findViewById(R.id.btn_date_offence_Form);
        btn_offenceDate_To = (Button) findViewById(R.id.btn_date_offence_To);
        btn_get_details = (Button) findViewById(R.id.btn_CC_getdetails);
        layout_TbleData = (RelativeLayout) findViewById(R.id.lyt_TableData);
        Txt_DD_Bkd = (TextView) findViewById(R.id.Txt_DD_Booked);
        Txt_DD_CouncelngNot_Atnd = (TextView) findViewById(R.id.Txt_DD_CouncelngNot_Atnd);
        Txt_DD_CourtNot_Atnd = (TextView) findViewById(R.id.Txt_DD_CourtNot_Atndg);
        Txt_CHG_Bkd = (TextView) findViewById(R.id.Txt_ChgBkd);
        Txt_CHG_CouncelngNot_Atnd = (TextView) findViewById(R.id.Txt_CHG_CouncelngNot_Atnd);
        Txt_CHG_CourtNot_Atnd = (TextView) findViewById(R.id.Txt_CHG_CourtNot_Atndg);
       /* Txt_TV_Bkd = (TextView) findViewById(R.id.Txt_TopVltnBkd);
        Txt_TV_CouncelngNot_Atnd = (TextView) findViewById(R.id.Txt_TV_CouncelngNot_Atnd);
        Txt_TV_CourtNot_Atnd = (TextView) findViewById(R.id.Txt_TV_CourtNot_Atndg);*/

        compny_Name = (TextView) findViewById(R.id.CompanyName);
        Animation marquee = AnimationUtils.loadAnimation(this, R.anim.marquee);
        compny_Name.startAnimation(marquee);
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
                    if (isOnline()) {
                        new Async_getCourtCasesInfo().execute();
                    }else{
                        showToast("Please check your internet Connection");
                    }
                }
            }
        });

        Txt_DD_Bkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_DD_Booked.size()>0) {
                    Intent intent_DD_Bkd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "DD_Bkd");
                    intent_DD_Bkd.putExtras(bundle);
                    startActivity(intent_DD_Bkd);
                }else{
                    showToast("No reports Found");
                }
            }
        });
        Txt_DD_CouncelngNot_Atnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_DD_CouncelngNot_Atnd.size()>0) {
                    Intent intent_DD_CouncelngNot_Atnd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "Txt_DD_CouncelngNot_Atnd");
                    intent_DD_CouncelngNot_Atnd.putExtras(bundle);
                    startActivity(intent_DD_CouncelngNot_Atnd);
                }else{
                    showToast("No reports Found");
                }
            }
        });

        Txt_DD_CourtNot_Atnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_DD_CourtNot_Atnd.size()>0) {
                    Intent intent_DD_CourtNot_Atnd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "Txt_DD_CourtNot_Atnd");
                    intent_DD_CourtNot_Atnd.putExtras(bundle);
                    startActivity(intent_DD_CourtNot_Atnd);
                }else{
                    showToast("No reports Found");
                }
            }
        });
        Txt_CHG_Bkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList_CHG_Booked.size()>0) {
                    Intent intent_CHG_Bkd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "CHG_Bkd");
                    intent_CHG_Bkd.putExtras(bundle);
                    startActivity(intent_CHG_Bkd);
                }else{
                    showToast("No reports Found");
                }
            }
        });

        Txt_CHG_CouncelngNot_Atnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_CHG_CouncelngNot_Atnd.size()>0) {
                    Intent intent_CHG_CouncelngNot_Atnd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "Txt_CHG_CouncelngNot_Atnd");
                    intent_CHG_CouncelngNot_Atnd.putExtras(bundle);
                    startActivity(intent_CHG_CouncelngNot_Atnd);
                }else{
                    showToast("No reports Found");
                }
            }
        });


        Txt_CHG_CourtNot_Atnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList_CHG_CourtNot_Atnd.size()>0) {
                    Intent intent_CHG_CourtNot_Atnd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "Txt_CHG_CourtNot_Atnd");
                    intent_CHG_CourtNot_Atnd.putExtras(bundle);
                    startActivity(intent_CHG_CourtNot_Atnd);
                }else{
                    showToast("No reports Found");
                }
            }
        });

       /* Txt_TV_Bkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_TV_Booked.size()>0) {
                    Intent intent_TV_Bkd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "TV_Bkd");
                    intent_TV_Bkd.putExtras(bundle);
                    startActivity(intent_TV_Bkd);
                }else{
                    showToast("No reports Found");
                }
            }
        });
        Txt_TV_CouncelngNot_Atnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_TV_CouncelngNot_Atnd.size()>0) {
                    Intent intent_TV_CouncelngNot_Atnd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "Txt_TV_CouncelngNot_Atnd");
                    intent_TV_CouncelngNot_Atnd.putExtras(bundle);
                    startActivity(intent_TV_CouncelngNot_Atnd);
                }else{
                    showToast("No reports Found");
                }
            }
        });

        Txt_TV_CourtNot_Atnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList_TV_CourtNot_Atnd.size()>0) {
                    Intent intent_TV_CourtNot_Atnd = new Intent(getApplicationContext(), CourtCaseDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ArrayValue", "Txt_TV_CourtNot_Atnd");
                    intent_TV_CourtNot_Atnd.putExtras(bundle);
                    startActivity(intent_TV_CourtNot_Atnd);
                }else{
                    showToast("No reports Found");
                }
            }
        });*/


    }
    public Boolean isOnline() {
        ConnectivityManager conManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = conManager.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private class Async_getCourtCasesInfo extends AsyncTask<Void, Void, String> {
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
            super.onPreExecute();
            showDialog(PROGRESS_DIALOG);
            layout_TbleData.setVisibility(View.GONE);

        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DD Details", "" + ServiceHelper.Opdata_Chalana);
            removeDialog(PROGRESS_DIALOG);
            if (!ServiceHelper.Opdata_Chalana.equals("NA")&&null!=ServiceHelper.Opdata_Chalana) {

                arrayList_DD_Booked = new ArrayList<>();
                arrayList_DD_CouncelngNot_Atnd = new ArrayList<>();
                arrayList_DD_CourtNot_Atnd = new ArrayList<>();
                arrayList_CHG_Booked = new ArrayList<>();
                arrayList_CHG_CouncelngNot_Atnd = new ArrayList<>();
                arrayList_CHG_CourtNot_Atnd = new ArrayList<>();
               /* arrayList_TV_Booked = new ArrayList<>();
                arrayList_TV_CouncelngNot_Atnd = new ArrayList<>();
                arrayList_TV_CourtNot_Atnd = new ArrayList<>();*/
                JSONObject jsonObject=null;
                try {
                    jsonObject = new JSONObject(ServiceHelper.Opdata_Chalana);
                    JSONArray jsonArray = jsonObject.getJSONArray("Cases Details");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jb = jsonArray.getJSONObject(i);
                        casesDetailsPojo = new CasesDetailsPojo();
                        casesDetailsPojo.setVEHICLE_NUMBER(jb.getString("VEHICLE_NUMBER") != null ? jb.getString("VEHICLE_NUMBER") : "");
                        casesDetailsPojo.setCHALLAN_NUMBER(jb.getString("CHALLAN_NUMBER") != null ? jb.getString("CHALLAN_NUMBER") : "");
                        casesDetailsPojo.setDRIVER_LICENSE(jb.getString("DRIVER_LICENSE") != null ? jb.getString("DRIVER_LICENSE") : "");
                        casesDetailsPojo.setDRIVER_AADHAAR(jb.getString("DRIVER_AADHAAR") != null ? jb.getString("DRIVER_AADHAAR") : "");
                        casesDetailsPojo.setDRIVER_NAME(null!=jb.getString("DRIVER_NAME") ?  jb.getString("DRIVER_NAME"):"");
                        casesDetailsPojo.setVIOLATION(null!=jb.getString("VIOLATION") ?  jb.getString("VIOLATION"):"");
                        casesDetailsPojo.setDISTRICT_NAME(null!=jb.getString("DISTRICT_NAME") ?  jb.getString("DISTRICT_NAME"):"");
                        casesDetailsPojo.setDRIVER_MOBILE(null != jb.getString("DRIVER_MOBILE") ? jb.getString("DRIVER_MOBILE") : "");
                        casesDetailsPojo.setCHALLAN_TYPE(null != jb.getString("CHALLAN_TYPE") ? jb.getString("CHALLAN_TYPE") : "");
                        casesDetailsPojo.setPAYMENT_STATUS(null != jb.getString("PAYMENT_STATUS") ? jb.getString("PAYMENT_STATUS") : "");
                        casesDetailsPojo.setCOUNC_STATUS(null != jb.getString("COUNC_STATUS") ? jb.getString("COUNC_STATUS") : "");
                        casesDetailsPojo.setCOUNC_DATE(null != jb.getString("COUNC_DATE") ? jb.getString("COUNC_DATE") : "");
                        casesDetailsPojo.setCOURT_NOTICE_DT(null != jb.getString("COURT_NOTICE_DT") ? jb.getString("COURT_NOTICE_DT") : "");
                        casesDetailsPojo.setDISPOSAL_CD(null != jb.getString("DISPOSAL_CD") ? jb.getString("DISPOSAL_CD") : "");

                        if (jb.getString("CHALLAN_TYPE").equals("12") || jb.getString("CHALLAN_TYPE").equals("23")) {
                            arrayList_DD_Booked.add(casesDetailsPojo);
                        }
                        if ((jb.getString("CHALLAN_TYPE").equals("12") || jb.getString("CHALLAN_TYPE").equals("23")) && jb.getString("PAYMENT_STATUS").equals("U") && jb.getString("COUNC_STATUS").equals("0")) {
                            arrayList_DD_CouncelngNot_Atnd.add(casesDetailsPojo);
                        }
                        if ((jb.getString("CHALLAN_TYPE").equals("12") || jb.getString("CHALLAN_TYPE").equals("23")) && jb.getString("PAYMENT_STATUS").equals("U") && jb.getString("COURT_NOTICE_DT").equals("")) {
                            arrayList_DD_CourtNot_Atnd.add(casesDetailsPojo);
                        }

                        if (jb.getString("CHALLAN_TYPE").equals("26")) {
                            arrayList_CHG_Booked.add(casesDetailsPojo);
                        }
                        if ((jb.getString("CHALLAN_TYPE").equals("26")) && jb.getString("PAYMENT_STATUS").equals("U") && jb.getString("COUNC_STATUS").equals("0")) {
                            arrayList_CHG_CouncelngNot_Atnd.add(casesDetailsPojo);
                        }

                        if ((jb.getString("CHALLAN_TYPE").equals("26")) && jb.getString("PAYMENT_STATUS").equals("U") && jb.getString("COURT_NOTICE_DT").equals("")) {
                            arrayList_CHG_CourtNot_Atnd.add(casesDetailsPojo);
                        }

                      /*  if (jb.getString("CHALLAN_TYPE").equals("99")) {
                            arrayList_TV_Booked.add(casesDetailsPojo);
                        }
                        if ((jb.getString("CHALLAN_TYPE").equals("99")) && jb.getString("PAYMENT_STATUS").equals("U") && jb.getString("COUNC_STATUS").equals("0")) {
                            arrayList_TV_CouncelngNot_Atnd.add(casesDetailsPojo);
                        }
                        if ((jb.getString("CHALLAN_TYPE").equals("99")) && jb.getString("PAYMENT_STATUS").equals("U") && jb.getString("COURT_NOTICE_DT").equals("")) {
                            arrayList_TV_CourtNot_Atnd.add(casesDetailsPojo);
                        }*/
                    }
                    layout_TbleData.setVisibility(View.VISIBLE);

                    Txt_DD_Bkd.setText(Html.fromHtml("<u>" + arrayList_DD_Booked.size() + "</u>"));
                    Txt_DD_CouncelngNot_Atnd.setText(Html.fromHtml("<u>" + arrayList_DD_CouncelngNot_Atnd.size() + "</u>"));
                    Txt_DD_CourtNot_Atnd.setText(Html.fromHtml("<u>" + arrayList_DD_CourtNot_Atnd.size() + "</u>"));
                    Txt_CHG_Bkd.setText(Html.fromHtml("<u>" + arrayList_CHG_Booked.size() + "</u>"));
                    Txt_CHG_CouncelngNot_Atnd.setText(Html.fromHtml("<u>" + arrayList_CHG_CouncelngNot_Atnd.size() + "</u>"));
                    Txt_CHG_CourtNot_Atnd.setText(Html.fromHtml("<u>" + arrayList_CHG_CourtNot_Atnd.size() + "</u>"));
                   /* Txt_TV_Bkd.setText(Html.fromHtml("<u>" + arrayList_TV_Booked.size() + "</u>"));
                    Txt_TV_CouncelngNot_Atnd.setText(Html.fromHtml("<u>" + arrayList_TV_CouncelngNot_Atnd.size() + "</u>"));
                    Txt_TV_CourtNot_Atnd.setText(Html.fromHtml("<u>" + arrayList_TV_CourtNot_Atnd.size() + "</u>"));*/

                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast("No data found");
                }

            } else {
                showToast("No data found");
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
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            offence_From_Date = format.format(new Date(present_year - 1900, (present_month), present_day));
            btn_offenceDate_From.setText(offence_From_Date.toUpperCase());



        }
    };


    /* FOR OFFENSE DATE */
    DatePickerDialog.OnDateSetListener offence_ToDate_Dialog = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
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

                if (null != offence_From_Date) {
                    date1 = dates.parse(offence_From_Date);
                    date2 = dates.parse(offence_To_Date);
                    if (date2.after(date1) || date2.equals(date1)) {

                        btn_offenceDate_To.setText(offence_To_Date.toUpperCase());

                    } else {
                        showToast("Date should be greater than From_Date ");
                        btn_offenceDate_To.setText("Select Date");

                    }
                } else {
                    showToast("Please select From date");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    };


    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        //10/02/2017
        switch (id) {
            case OFFENCE_FROM_DATE_PICKER:
                DatePickerDialog dp_offenceFrom_date;
                dp_offenceFrom_date = new DatePickerDialog(this, offence_FromDate_Dialog, present_year, present_month,
                        present_day);
//                dp_offenceFrom_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offenceFrom_date;
            case OFFENCE_TO_DATE_PICKER:
                DatePickerDialog dp_offenceTo_date;
                dp_offenceTo_date = new DatePickerDialog(this, offence_ToDate_Dialog, present_year, present_month,
                        present_day);
//                dp_offenceTo_date.getDatePicker().setMaxDate(System.currentTimeMillis());
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
