package com.example.mtpv.eticketcourt.service;

import android.annotation.SuppressLint;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

@SuppressLint("DefaultLocale")
public class PidSecEncrypt {
 
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec myKeySpec;
    private SecretKeyFactory mySecretKeyFactory;
    private Cipher cipher;
    byte[] keyAsBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
 
    public PidSecEncrypt() throws Exception
    {
        myEncryptionKey = "Qa21WsEd4Fhkj53T7fhg65UyT";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        myKeySpec = new DESedeKeySpec(keyAsBytes);
        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = mySecretKeyFactory.generateSecret(myKeySpec);
    }
 
   public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            BASE64Encoder base64encoder = new BASE64Encoder();
            encryptedString = base64encoder.encode(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    
    public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
          
            byte[] plainText = cipher.doFinal(encryptedText);
           // System.out.println("plainText     "+plainText);
            decryptedText= bytes2String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
   
    private static String bytes2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }
 
  
    @SuppressWarnings("unused")
	public static void main(String args []) throws Exception
    {
    	
    	Connection con=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	try {
    		
    		PidSecEncrypt myEncryptor= new PidSecEncrypt();
    		System.out.println("HOST :"+myEncryptor.encrypt("192.168.11.1"));
    		System.out.println("PORT :"+myEncryptor.encrypt("1522"));
    		System.out.println("NAME :"+myEncryptor.encrypt("eChDBPrm"));
    		System.out.println("USER :"+myEncryptor.encrypt("eticket_mother"));
    		System.out.println("PWD :"+myEncryptor.encrypt("Les$K34s9Hp"));
    		
    		/*con=DataService.getInstance().createConnection();

    		String mapidQuery="SELECT PID_CD,SECURITY_CD FROM MA_PID WHERE PID_CD='23001004'";
    		ps=con.prepareStatement(mapidQuery);
    		rs=ps.executeQuery();
    		
    		String stringToEncrypt="";
    		
    		if(rs.next()){
    			
    			stringToEncrypt=rs.getString("SECURITY_CD");
    		    
    	      //  String decrypted=myEncryptor.decrypt(encrypted);
    	       // String decrypted=myEncryptor.decrypt("7M7q/rW+h53JtWdzdPMLDli+/ULHBMdEKBUbspHUTdw=");
    	        System.out.println("PID_CD :"+rs.getString("pid_cd")+" Security_cd:"+ stringToEncrypt);

    	        String encrypted=myEncryptor.encrypt(stringToEncrypt);
    	        System.out.println("Base64 Converted:" + encrypted);
    			
    	        String shaConverted=myEncryptor.encryptSHA(stringToEncrypt);
    	        System.out.println("SHA-1 Converted:"+shaConverted);
    	        
    	        String md5Converted=myEncryptor.encryptmd5(stringToEncrypt);
    	        System.out.println("MD5 Converted  :"+md5Converted);
    	        
    		}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public static String encryptSHA(String generatedKey)
    {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(generatedKey.getBytes("UTF-8"));
                byte digest[] = md.digest();
                return (new BASE64Encoder()).encode(digest);
            }
            catch (Exception e) {
                return null;
            }

    }
    
    public static  String encryptmd5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }
    }
}


