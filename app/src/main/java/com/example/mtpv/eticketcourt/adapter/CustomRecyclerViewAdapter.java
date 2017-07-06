package com.example.mtpv.eticketcourt.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.R;

import java.util.ArrayList;


/**
 * Created by mtpv on 7/6/2017.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.MyViewHolder> {


    Context context;
    MyViewHolder viewHolder;
    View view;
    private ArrayList<CasesDetailsPojo> casesDetailsPojos_List;


    public CustomRecyclerViewAdapter(Context context, ArrayList<CasesDetailsPojo> casesDetailsPojos_List) {

        this.context = context;
        this.casesDetailsPojos_List = casesDetailsPojos_List;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customrecycle_adapter, parent, false);
        viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final AppCompatTextView driving_License = holder.driving_License;
        final AppCompatTextView adhar_Num = holder.adhar_Num;
        final AppCompatTextView mob_Num = holder.mob_Num;
        final AppCompatTextView reg_No = holder.reg_No;
        final AppCompatTextView challan_No = holder.challan_No;
        final LinearLayout linearLayout = holder.lyt_DL_A_M;
        final AppCompatCheckBox appCompatCheckBox = holder.compatCheckBox;

        reg_No.setText(casesDetailsPojos_List.get(listPosition).getVEHICLE_NUMBER());
        challan_No.setText(casesDetailsPojos_List.get(listPosition).getCHALLAN_NUMBER());


        appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout.setVisibility(View.VISIBLE);
                    driving_License.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE());
                    adhar_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR());
                    mob_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE());
//                    driving_License.setText(" hfjsdhfjhj");
//                    adhar_Num.setText("gjhjdhjf");
//                    mob_Num.setText("ghjdhjfhjd");
                } else {
                    linearLayout.setVisibility(View.GONE);
                }

            }
        });
//        driving_License.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE());
//        adhar_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR());
//        mob_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE());


    }

    @Override
    public int getItemCount() {
        try {
            return casesDetailsPojos_List.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView driving_License, adhar_Num, mob_Num, reg_No, challan_No;
        LinearLayout lyt_DL_A_M;
        AppCompatCheckBox compatCheckBox;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.driving_License = (AppCompatTextView) itemView.findViewById(R.id.Txt_Dl);
            this.adhar_Num = (AppCompatTextView) itemView.findViewById(R.id.Txt_Adhar);
            this.mob_Num = (AppCompatTextView) itemView.findViewById(R.id.Txt_Mobile);
            this.reg_No = (AppCompatTextView) itemView.findViewById(R.id.Txt_REGNO);
            this.challan_No = (AppCompatTextView) itemView.findViewById(R.id.Txt_CHALLNNO);
            lyt_DL_A_M = (LinearLayout) itemView.findViewById(R.id.lyt_DL_ADHR_MOB);
            compatCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.courtcase_checkBox);
        }


        public MyViewHolder(View itemView, Context context) {
            super(itemView);
            this.driving_License = (AppCompatTextView) itemView.findViewById(R.id.Txt_Dl);
            this.adhar_Num = (AppCompatTextView) itemView.findViewById(R.id.Txt_Adhar);
            this.mob_Num = (AppCompatTextView) itemView.findViewById(R.id.Txt_Mobile);
            this.reg_No = (AppCompatTextView) itemView.findViewById(R.id.Txt_REGNO);
            this.challan_No = (AppCompatTextView) itemView.findViewById(R.id.Txt_CHALLNNO);
            lyt_DL_A_M = (LinearLayout) itemView.findViewById(R.id.lyt_DL_ADHR_MOB);
            compatCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.courtcase_checkBox);

        }


    }
}