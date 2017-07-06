package com.example.mtpv.eticketcourt.Pojos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mtpv on 7/5/2017.
 */

public class CasesDetailsPojo implements Serializable
{
    public String VEHICLE_NUMBER;
    public String CHALLAN_NUMBER;
    public String DRIVER_LICENSE;
    public String DRIVER_AADHAAR;
    public String DRIVER_MOBILE;
    public String CHALLAN_TYPE;
    public String PAYMENT_STATUS;
    public String COUNC_STATUS;
    public String COUNC_DATE;
    public String COURT_NOTICE_DT;
    public String DISPOSAL_CD;

    public ArrayList<CasesDetailsPojo> arrayList_DD_Bkd;

    public String getVEHICLE_NUMBER() {
        return VEHICLE_NUMBER;
    }

    public void setVEHICLE_NUMBER(String VEHICLE_NUMBER) {
        this.VEHICLE_NUMBER = VEHICLE_NUMBER;
    }

    public String getCHALLAN_NUMBER() {
        return CHALLAN_NUMBER;
    }

    public void setCHALLAN_NUMBER(String CHALLAN_NUMBER) {
        this.CHALLAN_NUMBER = CHALLAN_NUMBER;
    }

    public String getDRIVER_LICENSE() {
        return DRIVER_LICENSE;
    }

    public void setDRIVER_LICENSE(String DRIVER_LICENSE) {
        this.DRIVER_LICENSE = DRIVER_LICENSE;
    }

    public String getDRIVER_AADHAAR() {
        return DRIVER_AADHAAR;
    }

    public void setDRIVER_AADHAAR(String DRIVER_AADHAAR) {
        this.DRIVER_AADHAAR = DRIVER_AADHAAR;
    }

    public String getDRIVER_MOBILE() {
        return DRIVER_MOBILE;
    }

    public void setDRIVER_MOBILE(String DRIVER_MOBILE) {
        this.DRIVER_MOBILE = DRIVER_MOBILE;
    }

    public String getCHALLAN_TYPE() {
        return CHALLAN_TYPE;
    }

    public void setCHALLAN_TYPE(String CHALLAN_TYPE) {
        this.CHALLAN_TYPE = CHALLAN_TYPE;
    }

    public String getPAYMENT_STATUS() {
        return PAYMENT_STATUS;
    }

    public void setPAYMENT_STATUS(String PAYMENT_STATUS) {
        this.PAYMENT_STATUS = PAYMENT_STATUS;
    }

    public String getCOUNC_STATUS() {
        return COUNC_STATUS;
    }

    public void setCOUNC_STATUS(String COUNC_STATUS) {
        this.COUNC_STATUS = COUNC_STATUS;
    }

    public String getCOUNC_DATE() {
        return COUNC_DATE;
    }

    public void setCOUNC_DATE(String COUNC_DATE) {
        this.COUNC_DATE = COUNC_DATE;
    }

    public String getCOURT_NOTICE_DT() {
        return COURT_NOTICE_DT;
    }

    public void setCOURT_NOTICE_DT(String COURT_NOTICE_DT) {
        this.COURT_NOTICE_DT = COURT_NOTICE_DT;
    }

    public String getDISPOSAL_CD() {
        return DISPOSAL_CD;
    }

    public void setDISPOSAL_CD(String DISPOSAL_CD) {
        this.DISPOSAL_CD = DISPOSAL_CD;
    }

    public ArrayList<CasesDetailsPojo> getArrayList_DD_Bkd() {
        return arrayList_DD_Bkd;
    }

    public void setArrayList_DD_Bkd(ArrayList<CasesDetailsPojo> arrayList_DD_Bkd) {
        this.arrayList_DD_Bkd = arrayList_DD_Bkd;
    }
}
