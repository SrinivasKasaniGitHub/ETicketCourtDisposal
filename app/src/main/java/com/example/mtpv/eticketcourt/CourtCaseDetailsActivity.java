package com.example.mtpv.eticketcourt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.adapter.CustomRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by mtpv on 7/6/2017.
 */

public class CourtCaseDetailsActivity extends Activity {
    private static RecyclerView.Adapter custom_CourtCase_DetailsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static ArrayList<CasesDetailsPojo> arrayList_CourtCase_Detilas;

    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courtcase_details);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        bundle = getIntent().getExtras();
        String array_Value = bundle.getString("ArrayValue");
        if (array_Value.equals("DD_Bkd")){
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_Booked;

        }else if (array_Value.equals("DD_Pdng")){
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_DD_Pending;
        }else if (array_Value.equals("CHG_Bkd")){
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_Booked;
        }else if (array_Value.equals("CHG_Pdng")){
            arrayList_CourtCase_Detilas = CourtCaseStatusActivity.arrayList_CHG_Pending;
        }
        else{
            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
        }

        custom_CourtCase_DetailsAdapter = new CustomRecyclerViewAdapter(this, arrayList_CourtCase_Detilas);
        recyclerView.setAdapter(custom_CourtCase_DetailsAdapter);

    }
}
