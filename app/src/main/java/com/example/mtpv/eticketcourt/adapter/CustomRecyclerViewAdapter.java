package com.example.mtpv.eticketcourt.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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
    private ArrayList<CasesDetailsPojo> casesDetailsPojos_Checked_List=new ArrayList<>();



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
        final AppCompatEditText edt_driving_License = holder.edt_driving_License;
        final AppCompatEditText edt_adhar_Num = holder.edt_adhar_Num;
        final AppCompatEditText edt_mob_Num = holder.edt_mob_Num;

        reg_No.setText(casesDetailsPojos_List.get(listPosition).getVEHICLE_NUMBER());
        challan_No.setText(casesDetailsPojos_List.get(listPosition).getCHALLAN_NUMBER());

        appCompatCheckBox.setOnCheckedChangeListener(null);
        appCompatCheckBox.setChecked(casesDetailsPojos_List.get(listPosition).isSelected());




        appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                casesDetailsPojos_List.get(holder.getAdapterPosition()).setSelected(isChecked);

                if (isChecked) {
                    // casesDetailsPojos_List.get(listPosition).setVEHICLE_NUMBER("");
                    //casesDetailsPojos_List.remove(listPosition);

                    linearLayout.setVisibility(View.VISIBLE);
                    if (casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE().equals("")) {
                        driving_License.setVisibility(View.GONE);
                        edt_driving_License.setVisibility(View.VISIBLE);


                    } else {
                        edt_driving_License.setVisibility(View.GONE);
                        driving_License.setVisibility(View.VISIBLE);
                        driving_License.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE());

                    }

                    if (casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR().equals("")) {
                        adhar_Num.setVisibility(View.GONE);
                        edt_adhar_Num.setVisibility(View.VISIBLE);
                        //casesDetailsPojos_List.get(listPosition).setDRIVER_AADHAAR(edt_adhar_Num.getText().toString());
                    } else {
                        edt_adhar_Num.setVisibility(View.GONE);
                        adhar_Num.setVisibility(View.VISIBLE);
                        adhar_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR());
                    }

                    if (casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE().equals("")) {
                        mob_Num.setVisibility(View.GONE);
                        edt_mob_Num.setVisibility(View.VISIBLE);
                    } else {
                        edt_mob_Num.setVisibility(View.GONE);
                        mob_Num.setVisibility(View.VISIBLE);
                        mob_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE());
                    }







                } else {
                    //casesDetailsPojos_Checked_List.remove(listPosition);
                    linearLayout.setVisibility(View.GONE);
                }

            }
        });



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
        AppCompatEditText edt_driving_License, edt_adhar_Num, edt_mob_Num;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.driving_License = (AppCompatTextView) itemView.findViewById(R.id.Txt_Dl);
            this.adhar_Num = (AppCompatTextView) itemView.findViewById(R.id.Txt_Adhar);
            this.mob_Num = (AppCompatTextView) itemView.findViewById(R.id.Txt_Mobile);
            this.reg_No = (AppCompatTextView) itemView.findViewById(R.id.Txt_REGNO);
            this.challan_No = (AppCompatTextView) itemView.findViewById(R.id.Txt_CHALLNNO);
            this.lyt_DL_A_M = (LinearLayout) itemView.findViewById(R.id.lyt_DL_ADHR_MOB);
            this.compatCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.courtcase_checkBox);
            this.edt_driving_License = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_Dl);
            this.edt_adhar_Num = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_Adhar);
            this.edt_mob_Num = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_Mobile);

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
            this.edt_driving_License = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_Dl);
            this.edt_adhar_Num = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_Adhar);
            this.edt_mob_Num = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_Mobile);

        }


    }
}