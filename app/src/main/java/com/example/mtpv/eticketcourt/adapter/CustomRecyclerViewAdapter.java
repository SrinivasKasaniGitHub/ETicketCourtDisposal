package com.example.mtpv.eticketcourt.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mtpv.eticketcourt.Pojos.CasesDetailsPojo;
import com.example.mtpv.eticketcourt.R;

import org.apache.commons.validator.routines.checkdigit.VerhoeffCheckDigit;

import java.util.ArrayList;
import java.util.List;


public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.MyViewHolder> {


    Context context;
    MyViewHolder viewHolder;
    View view;
    private ArrayList<CasesDetailsPojo> casesDetailsPojos_List = new ArrayList<>();
    private ArrayList<CasesDetailsPojo> casesDetailsPojos_Checked_List = new ArrayList<>();
    VerhoeffCheckDigit ver;

    private SparseBooleanArray array = new SparseBooleanArray();


    public CustomRecyclerViewAdapter(Context context, ArrayList<CasesDetailsPojo> casesDetailsPojos_List) {

        this.context = context;
        this.casesDetailsPojos_List = casesDetailsPojos_List;

        ver = new VerhoeffCheckDigit();
        array = new SparseBooleanArray();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customrecycle_adapter, parent, false);

        viewHolder = new MyViewHolder(view);
        ver = new VerhoeffCheckDigit();


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final CasesDetailsPojo detailsPojo = casesDetailsPojos_List.get(listPosition);

        final AppCompatTextView driving_License = holder.driving_License;
        final AppCompatTextView adhar_Num = holder.adhar_Num;
        final AppCompatTextView mob_Num = holder.mob_Num;
        final AppCompatTextView reg_No = holder.reg_No;
        final AppCompatTextView challan_No = holder.challan_No;
        final AppCompatTextView driver_Name = holder.driver_Name;
        final AppCompatTextView driver_Violation = holder.driver_Violation;
        final LinearLayout linearLayout = holder.lyt_DL_A_M;
        final AppCompatCheckBox appCompatCheckBox = holder.compatCheckBox;
        final AppCompatEditText edt_driving_License = holder.edt_driving_License;
        final AppCompatEditText edt_adhar_Num = holder.edt_adhar_Num;
        final AppCompatEditText edt_mob_Num = holder.edt_mob_Num;
        final AppCompatEditText edt_driver_Name = holder.edt_driver_Name;
        final ImageView img_adhar_wrt = holder.img_Adhar_wrt;
        final ImageView img_adhar_wrng = holder.img_adhar_wrng;

        reg_No.setText(casesDetailsPojos_List.get(listPosition).getVEHICLE_NUMBER());
        challan_No.setText(casesDetailsPojos_List.get(listPosition).getCHALLAN_NUMBER());

        appCompatCheckBox.setOnCheckedChangeListener(null);
        if (detailsPojo.isSelected()) {

            appCompatCheckBox.setChecked(true);
            linearLayout.setVisibility(View.VISIBLE);
            if (null != detailsPojo.getVIOLATION()) {
                driver_Violation.setText(detailsPojo.getVIOLATION());
            } else {
                driver_Violation.setText("");
            }

            if (null != detailsPojo.getDRIVER_NAME()) {
                driver_Name.setVisibility(View.GONE);
                edt_driver_Name.setVisibility(View.VISIBLE);
                edt_driver_Name.setText(detailsPojo.getDRIVER_NAME());
                edt_driver_Name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() > 0) {
                            detailsPojo.setDRIVER_NAME(s.toString());
                        } else {
                            detailsPojo.setDRIVER_NAME("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            } else {
                edt_driver_Name.setVisibility(View.VISIBLE);
                driver_Name.setVisibility(View.GONE);
                driver_Name.setText("");
            }

            if (casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE().equals("")) {
                driving_License.setVisibility(View.GONE);
                edt_driving_License.setVisibility(View.VISIBLE);
                edt_driving_License.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() > 0) {
                            casesDetailsPojos_List.get(listPosition).setDRIVER_LICENSE(s.toString());
                        } else {
                            casesDetailsPojos_List.get(listPosition).setDRIVER_LICENSE("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            } else {
                edt_driving_License.setVisibility(View.GONE);
                driving_License.setVisibility(View.VISIBLE);
                driving_License.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE());


            }

            if (casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR().equals("")) {
                adhar_Num.setVisibility(View.GONE);
                edt_adhar_Num.setVisibility(View.VISIBLE);

                edt_adhar_Num.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() == 12 && ver.isValid(s.toString())) {
                            casesDetailsPojos_List.get(listPosition).setDRIVER_AADHAAR(s.toString());
                            img_adhar_wrt.setVisibility(View.VISIBLE);
                            img_adhar_wrng.setVisibility(View.GONE);
                        } else {
                            casesDetailsPojos_List.get(listPosition).setDRIVER_AADHAAR("");
                            img_adhar_wrt.setVisibility(View.GONE);
                            img_adhar_wrng.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            } else {
                edt_adhar_Num.setVisibility(View.GONE);
                adhar_Num.setVisibility(View.VISIBLE);
                adhar_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR());
                img_adhar_wrt.setVisibility(View.GONE);
                img_adhar_wrng.setVisibility(View.GONE);
            }

            if (casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE().equals("")) {
                mob_Num.setVisibility(View.GONE);
                edt_mob_Num.setVisibility(View.VISIBLE);

                edt_mob_Num.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().length() == 10) {
                            if (s.toString().startsWith("7") || s.toString().startsWith("8") || s.toString().startsWith("9")) {
                                casesDetailsPojos_List.get(listPosition).setDRIVER_MOBILE(s.toString());
                            }
                        } else {
                            casesDetailsPojos_List.get(listPosition).setDRIVER_MOBILE("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else {
                edt_mob_Num.setVisibility(View.GONE);
                mob_Num.setVisibility(View.VISIBLE);
                mob_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE());
            }
        } else {
            appCompatCheckBox.setChecked(false);
            linearLayout.setVisibility(View.GONE);
        }

        //  holder.compatCheckBox.setTag(casesDetailsPojos_List.get(listPosition));
        // holder.compatCheckBox.setChecked(casesDetailsPojos_List.get(listPosition).isSelected());


        appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    detailsPojo.setSelected(true);
                   // Toast.makeText(context, "checked item" + detailsPojo.getCHALLAN_NUMBER() + "Position" + listPosition, Toast.LENGTH_LONG).show();
                    linearLayout.setVisibility(View.VISIBLE);

                    if (null != detailsPojo.getVIOLATION()) {
                        driver_Violation.setText(detailsPojo.getVIOLATION());
                    } else {
                        driver_Violation.setText("");
                    }

                    if (null != detailsPojo.getDRIVER_NAME()) {
                        driver_Name.setVisibility(View.GONE);
                        edt_driver_Name.setVisibility(View.VISIBLE);
                        edt_driver_Name.setText(detailsPojo.getDRIVER_NAME());
                        edt_driver_Name.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (s.toString().length() > 0) {
                                    detailsPojo.setDRIVER_NAME(s.toString());
                                } else {
                                    detailsPojo.setDRIVER_NAME("");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                    } else {
                        edt_driver_Name.setVisibility(View.VISIBLE);
                        driver_Name.setVisibility(View.GONE);
                        driver_Name.setText("");
                    }

                    if (casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE().equals("")) {
                        driving_License.setVisibility(View.GONE);
                        edt_driving_License.setVisibility(View.VISIBLE);
                        edt_driving_License.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (s.toString().length() > 0) {
                                    casesDetailsPojos_List.get(listPosition).setDRIVER_LICENSE(s.toString());
                                } else {
                                    casesDetailsPojos_List.get(listPosition).setDRIVER_LICENSE("");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                    } else {
                        edt_driving_License.setVisibility(View.GONE);
                        driving_License.setVisibility(View.VISIBLE);
                        driving_License.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_LICENSE());


                    }

                    if (casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR().equals("")) {
                        adhar_Num.setVisibility(View.GONE);
                        edt_adhar_Num.setVisibility(View.VISIBLE);

                        edt_adhar_Num.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (s.toString().length() == 12 && ver.isValid(s.toString())) {
                                    casesDetailsPojos_List.get(listPosition).setDRIVER_AADHAAR(s.toString());
                                    img_adhar_wrt.setVisibility(View.VISIBLE);
                                    img_adhar_wrng.setVisibility(View.GONE);
                                } else {
                                    casesDetailsPojos_List.get(listPosition).setDRIVER_AADHAAR("");
                                    img_adhar_wrt.setVisibility(View.GONE);
                                    img_adhar_wrng.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                    } else {
                        edt_adhar_Num.setVisibility(View.GONE);
                        adhar_Num.setVisibility(View.VISIBLE);
                        adhar_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_AADHAAR());
                        img_adhar_wrt.setVisibility(View.GONE);
                        img_adhar_wrng.setVisibility(View.GONE);
                    }

                    if (casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE().equals("")) {
                        mob_Num.setVisibility(View.GONE);
                        edt_mob_Num.setVisibility(View.VISIBLE);

                        edt_mob_Num.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (s.toString().length() == 10) {
                                    if (s.toString().startsWith("7") || s.toString().startsWith("8") || s.toString().startsWith("9")) {
                                        casesDetailsPojos_List.get(listPosition).setDRIVER_MOBILE(s.toString());
                                    }
                                } else {
                                    casesDetailsPojos_List.get(listPosition).setDRIVER_MOBILE("");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    } else {
                        edt_mob_Num.setVisibility(View.GONE);
                        mob_Num.setVisibility(View.VISIBLE);
                        mob_Num.setText(casesDetailsPojos_List.get(listPosition).getDRIVER_MOBILE());
                    }


                } else {
                    // casesDetailsPojos_Checked_List.remove(listPosition);
                    casesDetailsPojos_List.get(listPosition).setSelected(false);
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

        AppCompatTextView driving_License, adhar_Num, mob_Num, reg_No, challan_No, driver_Name, driver_Violation;
        LinearLayout lyt_DL_A_M;
        AppCompatCheckBox compatCheckBox;
        public AppCompatEditText edt_driving_License, edt_adhar_Num, edt_mob_Num, edt_driver_Name;
        public ImageView img_Adhar_wrt, img_adhar_wrng;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.driver_Name = (AppCompatTextView) itemView.findViewById(R.id.Txt_DName);
            this.driver_Violation = (AppCompatTextView) itemView.findViewById(R.id.Txt_Violation);
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
            this.edt_driver_Name = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_DName);
            this.img_adhar_wrng = (ImageView) itemView.findViewById(R.id.img_Adhar_wrng);
            this.img_Adhar_wrt = (ImageView) itemView.findViewById(R.id.img_Adhar_wrt);
        }

        public MyViewHolder(View itemView, Context context) {
            super(itemView);
            this.driver_Name = (AppCompatTextView) itemView.findViewById(R.id.Txt_DName);
            this.driver_Violation = (AppCompatTextView) itemView.findViewById(R.id.Txt_Violation);
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
            this.edt_driver_Name = (AppCompatEditText) itemView.findViewById(R.id.edt_Txt_DName);
            this.img_adhar_wrng = (ImageView) itemView.findViewById(R.id.img_Adhar_wrng);
            this.img_Adhar_wrt = (ImageView) itemView.findViewById(R.id.img_Adhar_wrt);
        }
    }
}