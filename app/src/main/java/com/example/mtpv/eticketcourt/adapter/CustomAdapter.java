package com.example.mtpv.eticketcourt.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.R;

import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srinivas on 3/6/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private Context activity;
    ArrayList<CasesDetailsPojo> casesDetailsPojos_List=new ArrayList<>();
    private static LayoutInflater inflater = null;
    private View vi;
    private ViewHolder viewHolder;

    public CustomAdapter(Context context, ArrayList<CasesDetailsPojo> items) {
        this.activity = context;
        this.casesDetailsPojos_List = items;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return casesDetailsPojos_List.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        vi = view;
        final int pos = position;
        CasesDetailsPojo items = casesDetailsPojos_List.get(pos);
        if(view == null) {
            vi = inflater.inflate(R.layout.row_customrecycle_adapter, null);
            viewHolder = new ViewHolder();

            viewHolder.driver_Name = (AppCompatTextView) vi.findViewById(R.id.Txt_DName);
            viewHolder.driver_Violation = (AppCompatTextView) vi.findViewById(R.id.Txt_Violation);
            viewHolder.driving_License = (AppCompatTextView) vi.findViewById(R.id.Txt_Dl);
            viewHolder.adhar_Num = (AppCompatTextView) vi.findViewById(R.id.Txt_Adhar);
            viewHolder.mob_Num = (AppCompatTextView) vi.findViewById(R.id.Txt_Mobile);
            viewHolder.reg_No = (AppCompatTextView) vi.findViewById(R.id.Txt_REGNO);
            viewHolder.challan_No = (AppCompatTextView) vi.findViewById(R.id.Txt_CHALLNNO);
            viewHolder.lyt_DL_A_M = (LinearLayout) vi.findViewById(R.id.lyt_DL_ADHR_MOB);
            viewHolder.compatCheckBox = (AppCompatCheckBox) vi.findViewById(R.id.courtcase_checkBox);
            viewHolder.edt_driving_License = (AppCompatEditText) vi.findViewById(R.id.edt_Txt_Dl);
            viewHolder.edt_adhar_Num = (AppCompatEditText) vi.findViewById(R.id.edt_Txt_Adhar);
            viewHolder.edt_mob_Num = (AppCompatEditText) vi.findViewById(R.id.edt_Txt_Mobile);
            viewHolder.edt_driver_Name = (AppCompatEditText) vi.findViewById(R.id.edt_Txt_DName);
            viewHolder.img_adhar_wrng = (ImageView) vi.findViewById(R.id.img_Adhar_wrng);
            viewHolder.img_Adhar_wrt = (ImageView) vi.findViewById(R.id.img_Adhar_wrt);


            vi.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.reg_No.setText(items.getVEHICLE_NUMBER());
        viewHolder.challan_No.setText(items.getCHALLAN_NUMBER());
        if(items.isSelected()){
            viewHolder.compatCheckBox.setChecked(true);
        }
        else {
            viewHolder.compatCheckBox.setChecked(false);
        }


        return vi;
    }
    public ArrayList<CasesDetailsPojo> getAllData(){
        return casesDetailsPojos_List;
    }
    public void setCheckBox(int position){
        CasesDetailsPojo items = casesDetailsPojos_List.get(position);
        items.setSelected(!items.isSelected());
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView name;
        CheckBox checkBox;


        AppCompatTextView driving_License, adhar_Num, mob_Num, reg_No, challan_No, driver_Name, driver_Violation;
        LinearLayout lyt_DL_A_M;
        AppCompatCheckBox compatCheckBox;
        public AppCompatEditText edt_driving_License, edt_adhar_Num, edt_mob_Num, edt_driver_Name;
        public ImageView img_Adhar_wrt, img_adhar_wrng;
        private Context context;
    }
}
