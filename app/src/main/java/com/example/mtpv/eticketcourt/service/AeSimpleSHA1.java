package com.example.mtpv.eticketcourt.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class AeSimpleSHA1 {
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text, String text2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md_usr = MessageDigest.getInstance("SHA-1");
        MessageDigest md_temp = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        byte[] textBytes2 = text2.getBytes("iso-8859-1");
        
        md_usr.update(textBytes, 0, textBytes.length);
        md_temp.update(textBytes2, 0, textBytes2.length);
        
        Log.i("md_usr ::::", ""+md_usr);
        Log.i("md_temp ::::", ""+md_temp);
        
        byte[] sha1_usr = md_usr.digest();
        byte[] sha1_temp = md_temp.digest();
        
        String usr = convertToHex(sha1_usr);
        String tmp = convertToHex(sha1_temp);
        
        Log.i("usr", ""+usr);
        Log.i("tmp ::::", ""+tmp);
        
        return convertToHex(sha1_usr);
    }
}