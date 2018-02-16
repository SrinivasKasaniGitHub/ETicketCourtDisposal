package com.example.mtpv.eticketcourt.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
    private SimpleDateFormat format;


    @SuppressLint("SimpleDateFormat")
    public String getTodaysDate() {
        String pattern = "dd-MMM-yyyy";
        format = new SimpleDateFormat(pattern);

        return format.format(new Date());
    }

    public long DaysCalucate(String dateStart, String dateStop) {
        long min = 0;
        long diffDays = 0;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date d1;
        Date d2;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffDays;

    }

    @SuppressLint("SimpleDateFormat")
    public String getPresentTime() {
        String pattern = "HH:mm";
        format = new SimpleDateFormat(pattern);

        return format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public String getPresentDateandTime() {
        String pattern = "dd-MMM-yyyy@HH:mm:ss";
        format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public String getPresentDateTime() {
        String pattern = "dd-MMM-yyyy!HH:mm";
        format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }



    @SuppressLint("SimpleDateFormat")
    public String getPresentyear() {
        String pattern = "yyyy";
        format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public String getPresentMonth() {
        String pattern = "MMM";
        format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public String getPresentDay() {
        String pattern = "dd";
        format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }


}
