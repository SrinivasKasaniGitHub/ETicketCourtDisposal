package com.example.mtpv.eticketcourt.service;

import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mtpv.eticketcourt.Dashboard;
import com.example.mtpv.eticketcourt.Drunk_Drive;
import com.example.mtpv.eticketcourt.GenerateDrunkDriveCase;
import com.example.mtpv.eticketcourt.MainActivity;
import com.example.mtpv.eticketcourt.Settings;
import com.example.mtpv.eticketcourt.SpotChallan;


@SuppressWarnings("unused")
public class ServiceHelper {
	public static String result = "", output = "";
	private static String METHOD_NAME = "authenticateUser";
	// LLRTS20437182016
	// our web service method name
	private static String NAMESPACE = "http://service.et.mtpv.com";
	// Here package name in web service with reverse order.
	public static String SOAP_ACTION = NAMESPACE + METHOD_NAME, Opdata_Chalana, getViolationPoint_resp,
			spot_final_res_status, transactionNo_resp, makePayment_resp, terminaldetails_resp, points_resp = null,
			aadhaarVehicle_resp, UpdateAadhaar_resp, aadhaarDetailsCheck_resp, changePswd_otp, changePSWDconfirm,
			version_response, offender_remarks;

	public static String rta_data, license_data, aadhar_data;

	// private static String URL =
	// "http://www.echallan.org/eTicketMobile/services/MobileEticketServiceImpl?wsdl";
	// private static final String URL =
	// "http://192.168.11.9:7534/eTicketMobile/services/MobileEticketServiceImpl?wsdl";

	public static String[] login_details_arr;
	public static Map<String, String> viodetMap = null;
	static String WHEELER_MEHOD_NAME = "getWheelerDetails";
	static String GET_PS_NAMES_MEHOD_NAME = "getPsNames";
	static String GET_COURT_NAMES="getCourtsMaster";
    static String GET_COURT_DIS_NAMES="getCourtsDisposalMaster";
	static String GET_POINTNAME_BY_PSNAME_MEHOD_NAME = "getPointNamesByPsName";

	public static String OCUPTN_METHOD_NAME = "getOccupations";
	public static String QLFCTION_METHOD_NAME = "getQualifications";

	public static String BAR_DETAILS_METHOD_NAME = "getWineSellerDetails";

	public static String VECHILE_CATEGORY = "getVehicleCategory";
	public static String VECHILE_MAIN_CAT_METHOD_NAME = "getVehicleMainCategory";
	public static String VECHILE_SUB_CAT_METHOD_NAME = "getVehicleSubCategory";
	public static String GENERATE_DRUNK_DRIVE_CASE = "generateDrunDriveCase";
	public static String GENERATE_DRUNK_DRIVE_CASE1_5_2 = "generateDrunDriveCase_v_1_5_2";

	public static String RTA_DETAILS_BY_REGNO_METHOD_NAME = "getRTADetailsByVehicleNO";
	public static String RTA_DETAILS_BY_REGNO_LICENSE_AADHAR_METHOD_NAME = "getOffenderRemarks";

	public static String PENDING_CHALLANS_BY_REGNO = "getPendingChallansByRegnno";
	public static String LICENCE_DETAILS_METHOD_NAME = "getRTADetailsByLicenceNo";
	// getViolationsPoints
	public static String GET_VIOLATIONS_POINTS = "getViolationsPoints";

	public static String VIOLATION_DETAILS_METHOD_NAME = "getOffenceDetailsbyWheeler";
	public static String GET_DETAINED_ITEMS_METHOD_NAME = "getDetainedItems";
	public static String AADHAR_DETAILS_METHOD_NAME = "getAADHARData";
	public static String ALL_WHEELER_VIOLATION_DETAILS_METHOD_NAME = "getViolationsDetailsbyAllWheeler";
	public static String DAY_REPORTS_METHOD_NAME = "getDayReports";
	public static String DUPLCIATE_ONLINE_PRINT_METHOD_NAME = "getDuplicatePrint";
	public static String SEND_OTP_TO_MOBILE_METHOD_NAME = "sendOTP";
	public static String VERIFY_OTP_FROM_MOBILE_METHOD_NAME = "verifyOTP";

	/**
	 * public static String MOBILE_SPOT_CHALLAN_METHOD_NAME =
	 * "mobileSpotChallanPayment"; OLD METHOD NAME
	 **/
	public static String MOBILE_SPOT_CHALLAN_METHOD_NAME = "mobileSpotChallanPaymentNew13";
	public static String TOWING_CPACT_METHOD_NAME = "mobile41CPAct13";
	public static String VEHICLE_RELEASE_METHOD_NAME = "vehicleRelease15";
	public static String VEHICLE_HISTORY_METHOD_NAME = "mobilePendingChallanPayment";

	public static String SPOT_CHALLAN_PAYMENT_NEW_15 = "mobileSpotChallanPaymentNew15";

	public static String SPOT_CHALLAN_PAYMENT_NEW_1_5_2 = "mobileSpotChallanPaymentNew_v_1_5_2";

	public static String GET_TRANSACTION_NO = "getTxnRefNo10";

	public static String MAKE_ONLINE_PAYMENT = "makeOnlinePayment10";

	public static String GET_TERMINAL_DETAILS = "getTerminalDetails";

	public static String GET_CHALLAN_DETAILS_FOR_AADHAAR = "getChallanDetailsForAadharUpdate";

	public static String GET_AADHAAR_UPDATE = "aadharUpdateForChallanGeneration";

	public static String GET_CHANGE_PSWD_OTP = "aadharUpdateForChallanGeneration";



	// public static String GET_AADHAAR_UPDATE =
	// "aadharUpdateForChallanGeneration";

	public static String GET_AADHAAR_TICKET = "checkAadharTicket";// checkAadharTicket

	/*
	 * public String getOccupations(); public String getQualifications();
	 */
	public static String OCCUPATIONS = "getOccupations";
	public static String QUALIFICATIONS = "getQualifications";

	public static String GET_DDCLOSING_DETAILS="getCourtClosingTicketInfo";

	public static String GET_COURT_CLOSING_UPDATE_TCKT_INFO="getCourtClosingUpdateTicketInfo";

	public static String[] whlr_details_master;
	public static String[] psNames_master, violation_points_masters, violation_points_masters_split;
	public static String[] court_details_master;
    public static String[] court_dis_details_master;

	public static String[] occupationlist_master;

	public static String[] PointNamesBypsNames_master;
	public static String[] occupation_master;
	public static String[] qualification_master;

	public static String[] bar_master;

	public static String[] vchle_cat_master, vchle_mainCat_master, vchle_subCat_master;
	public static String[] final_reponse_split, final_response_master;
	public static String[] pending_challans_master;
	public static String[] detained_items_list_master;
	public static String[] aadhar_details;
	public static String[][] detained_items_list_details;
	public static String[][] pending_challans_details;

	public static String[] final_spot_reponse_master, final_spot_reponse_details;// spot
																					// response
	public static String[] final_spot_reponse_violations_master;// contains data
																// like
																// 44@100@500@desc
	public static String[][] final_spot_reponse_violations;

	/* VEHICLE HISTORY PAID CHALLANS */
	public static String[] selected_paid_challans_master;// HYD123123123@100!..
	public static String[][] selected_paid_challans_details;// HYD123123123,100...

	/* VILATION DETAILS */
	public static String[] violation_details_master;
	public static String[][] violation_detailed_views;
	static StringBuffer sbuffer_allViolations;

	public static String rc_send, dl_send, adhr_send;

	public static StringBuffer onlinebuff = new StringBuffer();

	public static void login(String pid, String pidpwd, String mob_imei, String sim_No, String lat, String log,
			String appVersion) {

		Log.i("**login**",
				"PID : " + pid + "\nPID_PWD : " + pidpwd + "\nIMEI: " + mob_imei + "\nLAT : " + lat + "\nLOG: " + log);

		try {
			// public String authenticateDeviceNPID(String pidCd, String
			// password,String imei,String simNo,String gpsLattitude,String
			// gpsLongitude);
			SoapObject request = new SoapObject(NAMESPACE, "authenticateDeviceNPID");
			request.addProperty("pidCd", pid);
			request.addProperty("password", pidpwd);
			request.addProperty("imei", mob_imei);
			request.addProperty("simNo", sim_No);
			request.addProperty("gpsLattitude", lat);
			request.addProperty("gpsLongitude", log);
			request.addProperty("appVersion", appVersion);
			Log.i("login_request--->", "" + request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Toast.makeText(getApplicationContext(), "" + result,
			// Toast.LENGTH_LONG).show();
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("**LOGIN_RESPONSE***", "" + Opdata_Chalana);
			if (Opdata_Chalana == "0") {
				Log.i("NO_DATA_FOUND", "NO_DATA_FOUND");
			} else {
				// 09-21 20:12:32.715: I/**LOGIN***(10383): 23001004|TESTING
				// PURPOSE|2300|TRAFFIC CELL|00|DEVP|12345
				Opdata_Chalana = Opdata_Chalana.replace("|", ":");
				/*
				 * 09-21 20:12:32.715: I/LOGIN REPONSE(10383): 23001004:TESTING
				 * PURPOSE:2300:TRAFFIC CELL:00:DEVP:12345
				 */
				Log.i("LOGIN_RESPONSE_AFTER", "" + Opdata_Chalana);
				MainActivity.arr_logindetails = Opdata_Chalana.split(":");
				// Log.i("pid_code", MainActivity.arr_logindetails[0]);
			}

		} catch (SoapFault fault) {
			Log.i("****SOAP F", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			Opdata_Chalana = "0";
			Log.e("Error", "" + E.toString());
		}
	}

	/*--------SENDING OTP TO MOBILE START-----*/
	/*---first : send / second: confirm----*/
	public static void sendOTPtoMobile(String regn_no, String mobileNo, String date) {
		try {

			SoapObject request = new SoapObject(NAMESPACE, SEND_OTP_TO_MOBILE_METHOD_NAME);
			/*
			 * if (methodname.equals("" + SEND_OTP_TO_MOBILE_METHOD_NAME)) {
			 * ----sendOTP--- request.addProperty(parm_regno_mob, regno);
			 * request.addProperty(parm_mob_date, mob);
			 * request.addProperty(parm_date_otp, otpDate); } else {
			 * ----verifyOTP---- request.addProperty(parm_regno_mob, regno);
			 * request.addProperty(parm_mob_date, mob);
			 * request.addProperty(parm_date_otp, otpDate);
			 * request.addProperty(parm_otp, otp);
			 * request.addProperty(parm_verify_status, vstatus); }
			 */
			request.addProperty("regn_no", regn_no);
			request.addProperty("mobileNo", mobileNo);
			request.addProperty("date", date);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("sendOTPtoMobile URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Toast.makeText(getApplicationContext(), "" + result,
			// Toast.LENGTH_LONG).show();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("**OTP TO MOBILE RESUL", "" + Opdata_Chalana);

		} catch (SoapFault fault) {
			Log.i("****SOAP FAULT ERROR*:", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			Opdata_Chalana = "NA";
			Log.e("Error", "" + E.toString());
		}
	}

	public static void verifyOTP(String regn_no, String mobileNo, String date, String otp, String verify_status) {
		try {

			SoapObject request = new SoapObject(NAMESPACE, VERIFY_OTP_FROM_MOBILE_METHOD_NAME);
			request.addProperty("regn_no", regn_no);
			request.addProperty("mobileNo", mobileNo);
			request.addProperty("date", date);
			request.addProperty("otp", otp);
			request.addProperty("verify_status", verify_status);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("sendOTPtoMobile URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Toast.makeText(getApplicationContext(), "" + result,
			// Toast.LENGTH_LONG).show();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("**OTP Verificat", "" + Opdata_Chalana);

		} catch (SoapFault fault) {
			Log.i("****SOAP F", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			Opdata_Chalana = "NA";
			Log.e("Error", "" + E.toString());
		}
	}

	/*-------------------------------------------*/

	public static void getWheeler() {

		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + WHEELER_MEHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request--->", "" + request);
			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			Log.i("**WHEELER_RESPONSE***", "" + Opdata_Chalana);

			if (Opdata_Chalana == null) {
				Log.i("NO_WHEELER_DATA_FOUND", "NO_WHEELER_DATA_FOUND");
			} else {

				whlr_details_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.whlr_details_master.length; i++) {



					Log.i("**WHEELER MASTER***", "" + ServiceHelper.whlr_details_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i(" soap", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "0";
			whlr_details_master = new String[0];
		}
	}

	public static void getViolationPoint_SystemMasterData() {

		try {
			// Log.i("getViolationPoint_SystemMasterData Details ::::",
			// "***Called ***");
			SoapObject request = new SoapObject(NAMESPACE, "getVioPSyst");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// getViolationPoint_resp = "";
			// getViolationPoint_resp = result.toString();
			Log.i("get", "" + getViolationPoint_resp);
			try {
				getViolationPoint_resp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt()
						.decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// OFFENCE_CD|WHEELER_CD|PENALTY_POINTS@
			// OFFENCE_CD|WHEELER_CD|PENALTY_POINTS

			// 01|2|1@
			// 02|2|2@

			violation_points_masters = new String[0];
			violation_points_masters = getViolationPoint_resp.split("\\@");
			for (int i = 0; i < ServiceHelper.violation_points_masters.length; i++) {
			}

		} catch (SoapFault fault) {
			Log.i("**getViolationPo", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			bar_master = new String[0];
		}
	}

	/*
	 * public static void getViolationPoint_SystemMasterData() { try {
	 * SoapObject request = new SoapObject(NAMESPACE, "getVioPSyst");
	 * SoapSerializationEnvelope envelope = new
	 * SoapSerializationEnvelope(SoapEnvelope.VER11); envelope.dotNet = true;
	 * envelope.setOutputSoapObject(request);
	 *
	 * HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
	 * httpTransportSE.call(SOAP_ACTION, envelope); Object result =
	 * envelope.getResponse(); getViolationPoint_resp = "";
	 * getViolationPoint_resp = result.toString();
	 *
	 * Log.i("*getViolationPoint_resp**  :::", " "+getViolationPoint_resp); if
	 * (getViolationPoint_resp == null) { //
	 * OFFENCE_CD|WHEELER_CD|PENALTY_POINTS@OFFENCE_CD|WHEELER_CD|PENALTY_POINTS
	 * } else {
	 *
	 * }
	 *
	 * } catch (SoapFault fault) { Log.i("****SOAP FAULT ERROR****:::",
	 * "soapfault = "+fault.getMessage());
	 *
	 * } catch (Exception e) { // TODO: handle exception getViolationPoint_resp
	 * = "0"; } }
	 */

	/* TO GET PS_NAMES FOR SETTINGS */
	public static void getPsNames() {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_PS_NAMES_MEHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("PS_NAMES_RESPONSE :::", "" + Opdata_Chalana);
			/*
			 * !2399@COURT!2302@MAHANKALI TRPS!2301@GOPALPURAM
			 * TRPS!2304@TRIMULGHERRY TRPS! 2310@BEGUMPET TRPS!2306@PUNJAGUTTA
			 * TRPS!2309@BANJARA HILLS TRPS!2307@S.R.NAGAR TRPS!
			 * 2312@CHIKKADPALLY TRPS!2315@SAIFABAD TRPS!2313@ABIDS
			 * TRPS!2330@GOSHAMAHAL TRPS! 2329@NAMPALLY TRPS!2328@ASIF NAGAR
			 * TRPS!2319@CHARMINAR TRPS!2318@FALAKNUMA TRPS! 2317@MIRCHOWK
			 * TRPS!2322@KACHIGUDA TRPS!2324@SULTAN BAZAR TRPS!2325@MALAKPET
			 * TRPS! 2300@TRAFFIC CELL!2333@TTI GOSHAMAHAL!2303@MARREDPALLY
			 * TRPS!2308@JUBLEE HILLS TRPS! 2323@NALLAKUNTA TRPS!2327@TOLICHOWKI
			 * TRPS!2314@NARAYANAGUDA TRPS!2320@BAHADHURPURA TRPS!
			 * 2398@UNDEFINED!2334@TTI BEGUMPET
			 */

			if (Opdata_Chalana == null) {
				Log.i("NO_PS_DATA_FOUND", "NO_PS_DATA_FOUND");
			} else {
				psNames_master = new String[0];
				psNames_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.psNames_master.length; i++) {
					Log.i("**PSNAMES MASTER***", "" + ServiceHelper.psNames_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("soaps", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			psNames_master = new String[0];
		}
	}

	public static void getCourtNames() {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_COURT_NAMES);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(NAMESPACE+GET_COURT_NAMES, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("COURT_NAMES_REONSE :::", "" + Opdata_Chalana);
			/*
			 * !2399@COURT!2302@MAHANKALI TRPS!2301@GOPALPURAM
			 * TRPS!2304@TRIMULGHERRY TRPS! 2310@BEGUMPET TRPS!2306@PUNJAGUTTA
			 * TRPS!2309@BANJARA HILLS TRPS!2307@S.R.NAGAR TRPS!
			 * 2312@CHIKKADPALLY TRPS!2315@SAIFABAD TRPS!2313@ABIDS
			 * TRPS!2330@GOSHAMAHAL TRPS! 2329@NAMPALLY TRPS!2328@ASIF NAGAR
			 * TRPS!2319@CHARMINAR TRPS!2318@FALAKNUMA TRPS! 2317@MIRCHOWK
			 * TRPS!2322@KACHIGUDA TRPS!2324@SULTAN BAZAR TRPS!2325@MALAKPET
			 * TRPS! 2300@TRAFFIC CELL!2333@TTI GOSHAMAHAL!2303@MARREDPALLY
			 * TRPS!2308@JUBLEE HILLS TRPS! 2323@NALLAKUNTA TRPS!2327@TOLICHOWKI
			 * TRPS!2314@NARAYANAGUDA TRPS!2320@BAHADHURPURA TRPS!
			 * 2398@UNDEFINED!2334@TTI BEGUMPET
			 */

			if (Opdata_Chalana == null) {
				Log.i("NO_PS_DATA_FOUND", "NO_PS_DATA_FOUND");
			} else {
				court_details_master = new String[0];
				court_details_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.court_details_master.length; i++) {
					Log.i("**PSNAMES MASTER***", "" + ServiceHelper.court_details_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("soaps", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			court_details_master = new String[0];
		}
	}
    public static void getCourtDisNames() {
        try {
            SoapObject request = new SoapObject(NAMESPACE, "" + GET_COURT_DIS_NAMES);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
            httpTransportSE.call(NAMESPACE+GET_COURT_DIS_NAMES, envelope);
            Object result = envelope.getResponse();
            // Opdata_Chalana = "";
            // Opdata_Chalana = result.toString();

            try {
                Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.i("COURT_DIS_NAMREONSE :::", "" + Opdata_Chalana);
			/*
			 * !2399@COURT!2302@MAHANKALI TRPS!2301@GOPALPURAM
			 * TRPS!2304@TRIMULGHERRY TRPS! 2310@BEGUMPET TRPS!2306@PUNJAGUTTA
			 * TRPS!2309@BANJARA HILLS TRPS!2307@S.R.NAGAR TRPS!
			 * 2312@CHIKKADPALLY TRPS!2315@SAIFABAD TRPS!2313@ABIDS
			 * TRPS!2330@GOSHAMAHAL TRPS! 2329@NAMPALLY TRPS!2328@ASIF NAGAR
			 * TRPS!2319@CHARMINAR TRPS!2318@FALAKNUMA TRPS! 2317@MIRCHOWK
			 * TRPS!2322@KACHIGUDA TRPS!2324@SULTAN BAZAR TRPS!2325@MALAKPET
			 * TRPS! 2300@TRAFFIC CELL!2333@TTI GOSHAMAHAL!2303@MARREDPALLY
			 * TRPS!2308@JUBLEE HILLS TRPS! 2323@NALLAKUNTA TRPS!2327@TOLICHOWKI
			 * TRPS!2314@NARAYANAGUDA TRPS!2320@BAHADHURPURA TRPS!
			 * 2398@UNDEFINED!2334@TTI BEGUMPET
			 */

            if (Opdata_Chalana == null) {
                Log.i("NO_PS_DATA_FOUND", "NO_PS_DATA_FOUND");
            } else {
                court_dis_details_master = new String[0];
                court_dis_details_master = Opdata_Chalana.split("!");
                for (int i = 0; i < ServiceHelper.court_dis_details_master.length; i++) {
                    Log.i("**NAMES MASTER***", "" + ServiceHelper.court_dis_details_master[i]);
                }
            }
        } catch (SoapFault fault) {
            Log.i("soaps", "soapfault = " + fault.getMessage());
        } catch (Exception e) {
            // TODO: handle exception
            court_dis_details_master = new String[0];
        }
    }



    /* TO GET OCCUPATION NAMES */
	public static void getOccupationdetails() {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + OCCUPATIONS);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("Response ::::", "" + Opdata_Chalana);
			if (Opdata_Chalana == null) {

			} else {
				occupationlist_master = new String[0];
				occupationlist_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.occupationlist_master.length; i++) {
					Log.i("**OCCUPATIONS MASTER***", "" + ServiceHelper.occupationlist_master[i]);

				}
			}

		} catch (SoapFault fault) {
			Log.i("soapss", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			occupationlist_master = new String[0];
		}
	}

	public static void getversiondetails(String unit_code, String app_type, String version) {

		try {
			SoapObject request = new SoapObject(NAMESPACE, "getNewUpdationDoc");
			request.addProperty("unit_code", unit_code);
			request.addProperty("app_type", app_type);
			request.addProperty("version_code", version);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("sendOTPtoMobile URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// changePswd_otp = result.toString();
			Log.i("version_response", result.toString());
			try {
				Log.i("try Block  :", "" + result.toString());

				version_response = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());

				Log.i("version_res", version_response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SoapFault fault) {
			Log.i("****SOAP FAULT ", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			version_response = "NA";
			Log.e("Error", "" + E.toString());
		}
	}

	public static void getAllWheelerViolations() {
		try {
			Log.i("please wait for data..", "getAllWheelerViolations---");
			SoapObject request = new SoapObject(NAMESPACE, "" + ALL_WHEELER_VIOLATION_DETAILS_METHOD_NAME);
			Log.i("**getAllWheelerVi", "" + request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sbuffer_allViolations = new StringBuffer();
			// sbuffer_allViolations.delete(0, sbuffer_allViolations.length());
			sbuffer_allViolations.append(Opdata_Chalana);

			Log.i("**getAllWheelerVi", "" + envelope.getResponse());

			if (Opdata_Chalana.toString().trim().equals("0")) {

			} else {
				// aadhar_details = new String[0];
				// aadhar_details = Opdata_Chalana.split("@");
				// for (int i = 0; i < ServiceHelper.aadhar_details.length; i++)
				// {
				// Log.i("**AADHAR DETAILS***", ""
				// + ServiceHelper.aadhar_details[i]);
				//
				// }
			}
			// Log.i("**getAadharDetails LENGTH***", ""
			// + ServiceHelper.aadhar_details.length);

		} catch (SoapFault fault) {
			Log.i("****SOAP ", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "0";
			// aadhar_details = new String[0];
		}
	}

	public static void getOccupation_Details() {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + OCUPTN_METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("OCCUPATION_D:::", "" + Opdata_Chalana);
			/*
			 * !6@PRIVATE EMPLOYEE!3@BUSINESS!18@SELF EMPLOYEE!7@GOVT
			 * EMPLOYEE!1@STUDENT!11@OTHERS
			 */
			if (Opdata_Chalana == null) {
				Log.i("NO_OND", "NO_OCCUPATION_DATA_FOUND");
			} else {
				occupation_master = new String[0];
				occupation_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.occupation_master.length; i++) {
					Log.i("**OCCUPATION_MASTER***", "" + ServiceHelper.occupation_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("****getOccup::", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			qualification_master = new String[0];
		}
	}
	public static void getBar_Details() {

		try {
			Log.i("Get Bar Details ::::", "***Called ***");
			SoapObject request = new SoapObject(NAMESPACE, "" + BAR_DETAILS_METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("BAR_DETAIL", "" + Opdata_Chalana);
			/*
			 * !1@BAR!2@WINES PERMIT ROOM!3@PUB!4@OTHERS!5@REFUSAL TO GIVE INFO
			 */
			if (Opdata_Chalana == null) {
				Log.i("NO_BAR_DETAILS_FOUND", "NO_BAR_DETAILS_FOUND");
			} else {
				ServiceHelper.bar_master = new String[0];
				ServiceHelper.bar_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.bar_master.length; i++) {
					Log.i("**BAR MASTER***", "" + ServiceHelper.bar_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("****getBar_Detail::", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			bar_master = new String[0];
		}
	}

	public static void getQualifications_Details() {

		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + QLFCTION_METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("QUALIFICATION_DE", "" + Opdata_Chalana);
			/*
			 * !1@BELOW
			 * SSC!2@SSC!3@INTERMEDIATE!4@DEGREE!5@DIPLOMA/POLYTECHNIC!6@B.TECH!
			 * 7@MCA!8@POST GRADUATE!9@OTHERS
			 */
			if (Opdata_Chalana == null) {
				qualification_master = new String[0];
			} else {
				qualification_master = new String[0];
				qualification_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.qualification_master.length; i++) {
					Log.i("**QLFCTN MASTER***", "" + ServiceHelper.qualification_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("****Async_qlfct::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			qualification_master = new String[0];
		}
	}

	/* TO GET VEHCILE CATEGORY & VECHILE MAIN CATEOGRY DETAILS */
	public static void getVechileCategory(String mName) {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + mName);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("*****", "" + Opdata_Chalana);
			if (Opdata_Chalana == null) {

			} else {
				Log.i("*****", "" + mName);
				if (mName.equals("" + VECHILE_CATEGORY)) {
					vchle_cat_master = new String[0];

					vchle_cat_master = Opdata_Chalana.split("!");
					for (int i = 0; i < ServiceHelper.vchle_cat_master.length; i++) {
						Log.i("**VCHLE CAT MASTER***", "" + ServiceHelper.vchle_cat_master[i]);

					}
				} else if (mName.equals("" + VECHILE_MAIN_CAT_METHOD_NAME)) {
					vchle_mainCat_master = new String[0];

					vchle_mainCat_master = Opdata_Chalana.split("!");
					for (int i = 0; i < ServiceHelper.vchle_mainCat_master.length; i++) {
						Log.i("**VCHLE MIA*", "" + ServiceHelper.vchle_mainCat_master[i]);

					}
				}

			}

		} catch (SoapFault fault) {
			Log.i("****SOAP F:", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception

			if (mName.equals("" + VECHILE_CATEGORY)) {

				vchle_cat_master = new String[0];

			} else if (mName.equals("" + VECHILE_MAIN_CAT_METHOD_NAME)) {

				vchle_mainCat_master = new String[0];
			}

		}
	}

	/* TO GET VEHCILE SUB CATEOGRY DETAILS */
	public static void getVechileSubCategory(String mainCCode) {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + VECHILE_SUB_CAT_METHOD_NAME);
			request.addProperty("vehicleMainCategoryCode", "" + mainCCode);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("*****", "" + Opdata_Chalana);
			if (Opdata_Chalana == null) {

			} else {
				vchle_subCat_master = new String[0];

				vchle_subCat_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.vchle_subCat_master.length; i++) {
					Log.i("**VCHLE SUB C*", "" + ServiceHelper.vchle_subCat_master[i]);

				}

			}

		} catch (SoapFault fault) {
			Log.i("****S***:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			vchle_subCat_master = new String[0];
		}
	}
	public static void getTerminalID(String unitCode) {

		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_TERMINAL_DETAILS);
			request.addProperty("unitCode", "" + unitCode);
			Log.i("request", "" + request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			terminaldetails_resp = "";
			// terminaldetails_resp = result.toString();
			Log.i("termibal response ", "" + result.toString());

			try {
				if (result != null && result.toString() != null && result.toString().trim().length() > 10) {
					terminaldetails_resp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt()
							.decrypt(result.toString().trim());
				} else {
					Log.i("terminals :::", "no response");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("**DECRYPTED_T", "" + terminaldetails_resp);
			/*
			 * {"TERMINAL_DETAILS":[{"UNIT_CODE":"23","TERMINAL_ID":"IB001168",
			 * "BLUETOOTH_NAME":"PP1116018072",
			 * "BLUETOOTH_ADDRESS":"00:01:90:C1:53:3A"},{"UNIT_CODE":"23",
			 * "TERMINAL_ID":"IB001169",
			 * "BLUETOOTH_NAME":"PP1116018073","BLUETOOTH_ADDRESS":
			 * "00:01:90:C1:53:4A"},
			 * {"UNIT_CODE":"23","TERMINAL_ID":"IB001167","BLUETOOTH_NAME":
			 * "PP1116018071", "BLUETOOTH_ADDRESS":"00:01:90:C1:53:2A"}]}
			 */
		} catch (SoapFault fault) {
			Log.i("**getTerminalI***", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			terminaldetails_resp = "0";
		}
	}


	public static void getDayReports(String unitcode, String pidcd, String pidname, String reportdate, String simid,
									 String imeino, String reprt_id) {
		Utils utils = new Utils();
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + DAY_REPORTS_METHOD_NAME);
			request.addProperty("" + utils.SPOT_UNIT_CODE, "" + unitcode);
			request.addProperty("" + utils.SPOT_PID_CODE, "" + pidcd);
			request.addProperty("" + utils.SPOT_PID_NAME, "" + pidname);
			request.addProperty("" + utils.REPORT_DATE, "" + reportdate);
			request.addProperty("" + utils.SIM_ID, "" + simid);
			request.addProperty("" + utils.SPOT_IMEI, "" + imeino);
			request.addProperty("" + utils.REPORT_ID, "" + reprt_id);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("***SERVICE DAYNSE**", "" + Opdata_Chalana);

		} catch (SoapFault fault) {
			Log.i("****SOA**:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "NA";
		}
	}


	/* ONLINE DUPLICATE PRINT */
	public static void getOnlineDuplicatePrint(String unitcode, String pidcd, String pidname, String regno,
											   String reportdate, String simid, String imeino) {
		Utils utils = new Utils();
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + DUPLCIATE_ONLINE_PRINT_METHOD_NAME);
			request.addProperty("" + utils.SPOT_UNIT_CODE, "" + unitcode);
			request.addProperty("" + utils.SPOT_PID_CODE, "" + pidcd);
			request.addProperty("" + utils.SPOT_PID_NAME, "" + pidname);
			request.addProperty("" + utils.REG_NO, "" + regno);
			request.addProperty("" + utils.REPORT_DATE, "" + reportdate);
			request.addProperty("" + utils.SIM_ID, "" + simid);
			request.addProperty("" + utils.SPOT_IMEI, "" + imeino);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
 			Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("***ONLIN MAIN REPONSE**", "" + Opdata_Chalana);

		} catch (SoapFault fault) {
			Log.i("****SOAP****:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "NA";
		}
	}


	public static void getCourtClosingTicketInfo(String eticketRegNo, String offenceDT){

		Utils utils = new Utils();
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_DDCLOSING_DETAILS);
			request.addProperty("" + utils.ETICKET_REG_NO, "" + eticketRegNo);
			request.addProperty("" + utils.OFFENCE_DT, "" + offenceDT);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(NAMESPACE+GET_DDCLOSING_DETAILS, envelope);
			Object result = envelope.getResponse();
			Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = result.toString();

				//Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString().trim());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("***SERVICE DAYNSE**", "" + Opdata_Chalana);

		} catch (SoapFault fault) {
			Log.i("****SOA**:::", "soapfault = " + fault.getMessage());
			Opdata_Chalana = "NA";

		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "NA";
		}


	}

	public static void getCourtClosingUpdateTicketInfo(String eticketNo, String regnNo, String dlNO, String aadhaarNO,
													   String stcNO, String courtDispCD, String imprisDays,
													   String imprisFrom, String imprisTo, String courtFine, String risingDetails,
													   String courtCD, String coutrAttnDT, String vehRelease, String dlRelease,
													   String ddRemarks, String pidCD, String pidName, String releaseDT,
													   String mobileNo)
	{

		Utils utils = new Utils();
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_COURT_CLOSING_UPDATE_TCKT_INFO);
			if (null!=eticketNo)request.addProperty("" + utils.TICKET_NO, "" + eticketNo);
			if (null!=regnNo)request.addProperty("" + utils.E_REG_NO, "" + regnNo);
			if (null!=dlNO)request.addProperty("" + utils.DL_NO, "" + dlNO);
			if (null!=aadhaarNO)request.addProperty("" + utils.ADHAR_NO, "" + aadhaarNO);
			if (null!=stcNO)request.addProperty("" + utils.STC_NO, "" + stcNO);
			if (null!=courtDispCD)request.addProperty("" + utils.COURT_DIS_CODE, "" + courtDispCD);
			if (null!=imprisDays)request.addProperty("" + utils.IMPRSN_DAYS, "" + imprisDays);
			if (null!=imprisFrom)request.addProperty("" + utils.IMPRSN_FROM, "" + imprisFrom);
			if (null!=imprisTo)request.addProperty("" + utils.IMPRSN_TO, "" + imprisTo);
			if (null!=courtFine)request.addProperty("" + utils.COURT_FINE, "" + courtFine);
			if (null!=risingDetails)request.addProperty("" + utils.RISING_DETAILS, "" + risingDetails);
			if (null!=courtCD)request.addProperty("" + utils.COURT_CODE, "" + courtCD);
			if (null!=coutrAttnDT)request.addProperty("" + utils.COURT_ATND_DATE, "" + coutrAttnDT);
			if (null!=vehRelease)request.addProperty("" + utils.VEHCLE_RELSE, "" + vehRelease);
			if (null!=dlRelease)request.addProperty("" + utils.DL_RELSE, "" + dlRelease);
			if (null!=ddRemarks)request.addProperty("" + utils.DD_REMARKS, "" + ddRemarks);
			if (null!=pidCD)request.addProperty("" + utils.P_ID, "" + pidCD);
			if (null!=pidName)request.addProperty("" + utils.P_NAME, "" + pidName);
			if (null!=releaseDT)request.addProperty("" + utils.RELSE_DATE, "" + releaseDT);
			if (null!=mobileNo)request.addProperty("" + utils.RELSE_ITEMS, "" + mobileNo);



			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(NAMESPACE+GET_COURT_CLOSING_UPDATE_TCKT_INFO, envelope);
			Object result = envelope.getResponse();
			Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = result.toString();

				//Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString().trim());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("***SERVICE DAYNSE**", "" + Opdata_Chalana);

		} catch (SoapFault fault) {
			Log.i("****SOA**:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "NA";
		}


	}



	public static void getRTADetails(String vhle_num) {
		try {
			Log.i("getRTADetails(Strd  ", "YES");
			SoapObject request = new SoapObject(NAMESPACE, "" + RTA_DETAILS_BY_REGNO_METHOD_NAME);
			request.addProperty("regn_no", "" + vhle_num);
			Log.i("request---->", "" + request);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// rta_data = "";
			// rta_data = result.toString();

			try {
				rta_data = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
				// rc_send = "1|"+vhle_num + "^" + rta_data;//v
				rc_send = "1|" + rta_data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("getRTADetail::", "" + rta_data);
			if (rta_data == "0") {
				Log.i("NO_DATA_FOUND", "NO_DATA_FOUND");
			} else {
				Log.i("**SERVICE cla*", "" + Dashboard.rta_details_request_from);
				if (Dashboard.rta_details_request_from.equals("RTACLASS")) {
					Drunk_Drive.rta_details_master = new String[0];
					Drunk_Drive.rta_details_master = rta_data.split("!");
					Log.i("**getRTADetails***", "" + rta_data);
					Log.i("**getR*", "" + Drunk_Drive.rta_details_master.length);

				} else if (Dashboard.rta_details_request_from.equals("SPOT")) {
					SpotChallan.rta_details_spot_master = new String[0];
					SpotChallan.rta_details_spot_master = rta_data.split("!");
					Log.i("**getRTADetails SPOT***", "" + rta_data);
					Log.i("**getR*", "" + SpotChallan.rta_details_spot_master.length);
				}
			}
		} catch (SoapFault fault) {
			Log.i("****SOAP*:::", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "0";
			if (Dashboard.rta_details_request_from.equals("RTACLASS")) {
				Drunk_Drive.rta_details_master = new String[0];
			} else if (Dashboard.rta_details_request_from.equals("RTACLASS")) {
				SpotChallan.rta_details_spot_master = new String[0];
			}
		}
	}
	public static void getLicenceDetails(String lcnce_num) {

		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + LICENCE_DETAILS_METHOD_NAME);
			request.addProperty("licenceNo", "" + (lcnce_num.replace(" ", "")));

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// license_data = "";
			// license_data = result.toString();

			// if (license_data.toString().trim().equals("anyType{}"))

			/*try {
				license_data = new com.mtpv.mobilee_ticket_services.PidSecEncrypt().decrypt(result.toString());
				Log.i("**getLiceneDetailsDecryptedResponse***", "" + license_data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (license_data.toString().trim().equals("0")) {
				if (Dashboard.licence_details_request_from.equals("RTACLASS")) {
					license_data = "0";
					Drunk_Drive.licene_details_master = new String[0];
				} else if (Dashboard.licence_details_request_from.equals("SPOT")) {
					license_data = "0";
					SpotChallan.licence_details_spot_master = new String[0];
				}
			} else {
				Log.i("**SERVICE LICENCE REQ FROM***", "" + Dashboard.licence_details_request_from);
				if (Dashboard.licence_details_request_from.equals("RTACLASS")) {
					// Log.i("**getLiceneDetails***", "else--" +
					// license_data);
					Drunk_Drive.licene_details_master = new String[0];
					Drunk_Drive.licene_details_master = license_data.split("!");
					Log.i("**getLiceneDetails***", "" + license_data);

				} else if (Dashboard.licence_details_request_from.equals("SPOT")) {
					// Log.i("**getLiceneDetails***", "else--" +
					// license_data);
					SpotChallan.licence_details_spot_master = new String[0];
					SpotChallan.licence_details_spot_master = license_data.split("!");
					Log.i("**getLiceneDetails***", "" + license_data);
				}
			}*/


			try {
				license_data = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
				Log.i("License_data_challan", "==="+license_data);
				if (license_data.trim()!=null || license_data.trim().length()>4) {
					dl_send = lcnce_num + "|" + license_data;
					onlinebuff = onlinebuff.append("2" + "|" + license_data + "^");
					SpotChallan.licence_details_spot_master = new String[0];
					SpotChallan.licence_details_spot_master = license_data.split("!");
					Log.i("SpotChallan", "======"+SpotChallan.licence_details_spot_master.toString());
				} else {
					license_data="0";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} catch (SoapFault fault) {
			Log.i("****SOA:", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			license_data = "0";
			if (Dashboard.licence_details_request_from.equals("RTACLASS")) {
				Drunk_Drive.licene_details_master = new String[0];
			} else if (Dashboard.licence_details_request_from.equals("SPOT")) {
				SpotChallan.licence_details_spot_master = new String[0];
			}
		}
	}

	public static void checkAadhar_Ticket(String unit_Code, String regn_No, String eng_No, String chasis_No) {
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_AADHAAR_TICKET);
			request.addProperty("unitCode", "" + unit_Code);
			request.addProperty("regnNo", "" + regn_No);
			request.addProperty("engNo", "" + eng_No);
			request.addProperty("chasisNo", "" + chasis_No);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("**request RESULT***", "" + request);
			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();

			aadhaarDetailsCheck_resp = "";
			// aadhaarDetailsCheck_resp = result.toString();

			try {
				aadhaarDetailsCheck_resp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt()
						.decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**aadhaar***", "" + aadhaarDetailsCheck_resp);

		} catch (SoapFault fault) {
			Log.i("****S:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			aadhaarDetailsCheck_resp = "0";
		}
	}
	public static void getViolations_Points(String unit_Code, String aadhar_No, String driving_LicenseNo) {
		// public String getViolationsPoints(String unitCode,String
		// aadharNo,String drivingLicenseNo);
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_VIOLATIONS_POINTS);
			request.addProperty("unitCode", "" + unit_Code);
			request.addProperty("aadharNo", "" + aadhar_No);
			request.addProperty("drivingLicenseNo", "" + driving_LicenseNo);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();

			// points_resp = result.toString();

			try {
				points_resp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("points_resp  ::::", "" + points_resp);

		} catch (SoapFault fault) {
			Log.i("****SOAP **:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* TO GET VIOLATION DETAILS */
	public static void getViolationDetails(String whlr_code) {
		try {
			Log.i("Violatio:::", "Yes");
			SoapObject request = new SoapObject(NAMESPACE, VIOLATION_DETAILS_METHOD_NAME);
			request.addProperty("wheelercode", whlr_code);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			System.out.println(" getviolations by wheeler response :" + result.toString());
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if (Opdata_Chalana.toString().trim().equals("anyType{}"))

			if (Opdata_Chalana.toString().trim().equals("0")) {
				Opdata_Chalana = "0";
				violation_details_master = new String[0];
				violation_detailed_views = new String[0][0];
			} else {

				violation_details_master = new String[0];
				violation_detailed_views = new String[0][0];

				violation_details_master = (Opdata_Chalana.substring(1, Opdata_Chalana.length())).split("!");

				Log.i("**getV**", "" + Opdata_Chalana);
				Log.i("**getVio***", "" + violation_details_master.length);

				if (violation_details_master.length > 0) {

					violation_detailed_views = new String[violation_details_master.length][6];

					for (int i = 0; i < violation_details_master.length; i++) {

						violation_detailed_views[i] = violation_details_master[i].toString().trim().split("@");

						Log.i("**violcode**", "" + violation_detailed_views[i][0].toString().trim()
								+ "---" + violation_detailed_views[i][2].toString().trim());
					}
				} else {
					violation_detailed_views = new String[0][0];
				}

			}

		} catch (SoapFault fault) {
			Log.i("***:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("**getVon***", "" + e.getMessage());
			Opdata_Chalana = "0";
			violation_details_master = new String[0];
			violation_detailed_views = new String[0][0];
		}
	}

	/* TO GET DETAINED ITEMS LIST */
	public static void getDetainedItemsList(String v_num, String unt_code) {
		Log.i("**gt**", "Please wait...");
		try {
			Log.i("v_num", "" + v_num);
			Log.i("unt_code", "" + unt_code);
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_DETAINED_ITEMS_METHOD_NAME);
			request.addProperty("regnNo", "" + (v_num));
			request.addProperty("unitCode", "" + (unt_code));
			Log.i("request", "" + request);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**getDetai**", "" + Opdata_Chalana);
			if (Opdata_Chalana.toString().trim().equals("0")) {
				Opdata_Chalana = "0";
				detained_items_list_master = new String[0];
				detained_items_list_details = new String[0][0];
			} else {
				detained_items_list_master = new String[0];
				detained_items_list_details = new String[0][0];

				detained_items_list_master = Opdata_Chalana.split("@");
				if (detained_items_list_master.length > 0) {
					Log.i("**", "" + detained_items_list_master.length);
					detained_items_list_details = new String[detained_items_list_master.length][3];

					for (int i = 0; i < detained_items_list_master.length; i++) {
						detained_items_list_details[i] = detained_items_list_master[i].toString().trim().split(":");
						Log.i("**getDeta*", "" + detained_items_list_details[i][0].toString().trim());
						Log.i("**gst 1**", "" + detained_items_list_details[i][1].toString().trim());
						Log.i("**getDst 2**", "" + detained_items_list_details[i][2].toString().trim());
						Log.i("**gist 3**", "" + detained_items_list_details[i][3].toString().trim());
						Log.i("**st 4**", "" + detained_items_list_details[i][4].toString().trim());
						Log.i("**getDeist 5**", "" + detained_items_list_details[i][5].toString().trim());
						Log.i("**getDetast 6**", "" + detained_items_list_details[i][6].toString().trim());
						Log.i("**getDett 7**", "" + detained_items_list_details[i][7].toString().trim());
					}
				}
			}
		} catch (SoapFault fault) {
			Log.i("****SO***:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "0";
			detained_items_list_master = new String[0];
			detained_items_list_details = new String[0][0];
		}
	}

	/* TO GET AADHAR DETAILS */
	public static void getAadharDetails(String uid, String eid) {
		Log.i("**getAadharDetails ***", "Entered");
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + AADHAR_DETAILS_METHOD_NAME);
			request.addProperty("uid", "" + (uid.toString().trim()));
			request.addProperty("eid", "" + (eid.toString().trim()));

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// aadhar_data = "";
			// aadhar_data = result.toString();
			Log.i("AA******", "" + result);
			try {
				aadhar_data = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// String aadhar_data =result.toString();
			Log.i("**getAadhar*", "" + aadhar_data);

			if ("NA".equals(aadhar_data.toString().trim())|| ("0".equals(aadhar_data.toString().trim()))) {
				aadhar_details = new String[0];
			} else {
				aadhar_details = new String[0];
				aadhar_details = aadhar_data.split("@");
				// for (int i = 0; i < ServiceHelper.aadhar_details.length; i++)
				// {
				// Log.i("**AADHAR DETAILS***", ""
				// + ServiceHelper.aadhar_details[i]);
				//
				// }
			}
			Log.i("**getAa**", "" + ServiceHelper.aadhar_details.length);

		} catch (SoapFault fault) {
			Log.i("****SOA::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			aadhar_data = "0";
			aadhar_details = new String[0];
		}
	}

	public static void getpendingChallansByRegNo(String regno, String drvr_lcno, String ownr_lcnce_no, String pid,
												 String pidname, String pwd, String simid, String imei, String lat, String logn, String ip_val,
												 String unit_code) {

		Log.i("getpendingChalod", "called");
		Log.i("regno", "" + regno);
		Log.i("drvr_lcno", "" + drvr_lcno);
		Log.i("ownr_lcnce_no", "" + ownr_lcnce_no);
		Log.i("pid", "" + pid);
		Log.i("pidname", pidname);
		Log.i("pwd", pwd);
		Log.i("simid", simid);
		Log.i("imei", imei);
		Log.i("lat", lat);
		Log.i("logn", logn);
		Log.i("ip_val", ip_val);
		Log.i("unit_code", unit_code);

		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + PENDING_CHALLANS_BY_REGNO);
			request.addProperty("regnNo", "" + regno);
			request.addProperty("drvierLicNo", "" + drvr_lcno);
			request.addProperty("ownerLicNo", "" + ownr_lcnce_no);
			request.addProperty("pidCd", "" + pid);
			request.addProperty("pidName", "" + pidname);
			request.addProperty("password", "" + pwd);
			request.addProperty("simId", "" + simid);
			request.addProperty("imeiNo", "" + imei);
			request.addProperty("latitude", "" + lat);
			request.addProperty("longitude", "" + logn);
			request.addProperty("ip", "" + ip_val);
			request.addProperty("unitCode", "" + Dashboard.UNIT_CODE);
			Log.i("getpendinst--->", "" + request);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("**PENDING_CHAL*", "" + Opdata_Chalana);
			if (Opdata_Chalana.toString().equals("0")) {
				Opdata_Chalana = "0";
				pending_challans_master = new String[0];
				pending_challans_details = new String[0][0];
			} else {
				Log.i("**PENDI", "" + Opdata_Chalana);
				/*
				 * AP29BS7402! HYD00TE171000076! 2017-03-02! 12:09! COMPOUNDING
				 * BOOTH! TRAFFIC CELL! CARRYING GOODS DANGEROUSLY! 100! 0! 100!
				 * 0! 23@
				 *
				 * AP29BS7402! HYD00TE171000077! 2017-03-02! 12:33! COMPOUNDING
				 * BOOTH! TRAFFIC CELL! NO ENTRY! 100! 0! 100! 0! 23@
				 *
				 * AP29BS7402! HYD17TE171001924! 2017-02-24! 17:06! I S SADAN
				 * JUNCTION! MIRCHOWK TRPS! WITHOUT HELMET / NOT WEARING HELMET!
				 * 100! 0! 100! 0! 23@
				 */

				pending_challans_master = new String[0];
				pending_challans_master = Opdata_Chalana.split("@");

				pending_challans_details = new String[pending_challans_master.length][8];
				Log.i("**PENDING C**", "" + pending_challans_master.length);
				for (int i = 0; i < pending_challans_master.length; i++) {
					pending_challans_details[i] = pending_challans_master[i].split("!");
					Log.i("**PENDI**", "" + pending_challans_details[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("****S*:::", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			Opdata_Chalana = "nodata";
			pending_challans_master = new String[0];
			pending_challans_details = new String[0][0];
			Log.i("**P**", "" + e.toString());
		}
	}
	public static void getOffenderRemarks(String vhle_num, String license, String aadhar) {

		try {
			Log.i("getOffed  ", "YES");
			SoapObject request = new SoapObject(NAMESPACE, "" + RTA_DETAILS_BY_REGNO_LICENSE_AADHAR_METHOD_NAME);
			request.addProperty("regn_no", vhle_num + "^" + rta_data);
			request.addProperty("licenceNo", "" + license);
			request.addProperty("aadharNo", "" + aadhar);
			Log.i("getOffe--->", "" + request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			offender_remarks = "";
			offender_remarks = result.toString();

			try {
				offender_remarks = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(offender_remarks);
				Log.i("getOffenderR", "" + offender_remarks);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SoapFault fault) {
			Log.i("****SOAP:", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			offender_remarks = "0";
		}
	}

	public static void mobileSpotChallanPayingNew15(String selectedPendingChallans, String regnCd, String vehicleNo,
													String regnNo, String gtwyCd, String dOfPay, String pmtTime, String unitCode, String unitName,
													String psCode, String psName, String bookedPsCd, String bookedPsName, String pointCode, String pointName,
													String operaterCd, String operaterName, String pidCd, String pidName, String cadreCD, String cadre,
													String totalAmount, String detained, String simId, String imeiNo, String macId, String latitude,
													String longitude, String gpsDt, String gpsTime, String onlineMode, String moduleCd, String releasedItem,
													String challanNo, String serviceCode, String ownerLicNo, String drvierLicNo, String password,
													String imageEvidence, String challanType, String challanCd, String location, String remarks,
													String pancardNo, String aadharNo, String voterId, String passport, String email, String driverContactNo,
													String isItSpotPmt, String offenceDt, String offenceTime, String licStatus, String wheelerCd,
													String vehCategory, String obMkrCD, String vehicleMainType, String violations, String pendingChallans,
													String noOfExtraPassengers, String driverName, String driverFatherName, String driverAddress,
													String driverCity, String typeOfVeh, String dl_dob) { // v

		Log.i("****mobileSotCh**", "Please wait...");
		Log.i("****mobileSotChall", "PARAMETERS PASSED START");

		Log.i("****spot_select**", "" + selectedPendingChallans);
		Log.i("****regn_Cd***", "" + regnCd);
		Log.i("***vehicle_No****", "" + vehicleNo);
		Log.i("**regn_No*****", "" + regnNo);
		Log.i("***gtwy_cd****", "" + gtwyCd);
		Log.i("****dateof_pymnt***", "" + dOfPay);
		Log.i("****pymnt_time***", "" + pmtTime);
		Log.i("**unit_cd*****", "" + unitCode);
		Log.i("****unit_name***", "" + unitName);
		Log.i("***ps_code****", "" + psCode);
		Log.i("****ps_name***", "" + psName);
		Log.i("****booked_cd***", "" + bookedPsCd);
		Log.i("**booked_name*****", "" + bookedPsName);
		Log.i("**point_cd*****", "" + pointCode);
		Log.i("****point_name***", "" + pointName);
		Log.i("**operator_cd*****", "" + operaterCd);
		Log.i("***operator_name****", "" + operaterName);
		Log.i("****pid_cd***", "" + pidCd);
		Log.i("****pid_name***", "" + pidName);
		Log.i("*cadre_cd******", "" + cadreCD);
		Log.i("*****cdre**", "" + cadre);
		Log.i("***total_amnt****", "" + totalAmount);
		Log.i("****detained_items***", "" + detained);
		Log.i("****simid***", "" + simId);
		Log.i("****imei_no***", "" + imeiNo);
		Log.i("*****mac_id**", "" + macId);
		Log.i("*****lat_val**", "" + latitude);
		Log.i("***long_val****", "" + longitude);
		Log.i("****gps_date***", "" + gpsDt);
		Log.i("*****gps_time**", "" + gpsTime);
		Log.i("*onlinemode******", "" + onlineMode);
		Log.i("***module_code****", "" + moduleCd);
		Log.i("***released_items****", "" + releasedItem);
		Log.i("***challan_num****", "" + challanNo);
		Log.i("****service_code***", "" + serviceCode);
		Log.i("***ownr_lic_no****", "" + ownerLicNo);
		Log.i("**drvr_lic_no*****", "" + drvierLicNo);
		Log.i("****pwd***", "" + password);
		Log.i("***image_data****", "" + imageEvidence);
		Log.i("***challan_type****", "" + challanType);
		Log.i("*****challan_code**", "" + challanCd);
		Log.i("*****location**", "" + location);
		Log.i("****remarks***", "" + remarks);
		Log.i("******pan_num*", "" + pancardNo);
		Log.i("****aadhar_num***", "-----------" + aadharNo != null && aadharNo.length() > 1 ? aadharNo : "NA");
		Log.i("**voter_id*****", "" + voterId);
		Log.i("*****pasprt**", "" + passport);
		Log.i("***mailid****", "" + email);
		Log.i("**drvr_cntct_num*****", "" + driverContactNo);
		Log.i("*****is_spot**", "" + isItSpotPmt);
		Log.i("*****ofnce_date**", "" + offenceDt);
		Log.i("*****ofence_time**", "" + offenceTime);
		Log.i("****lic_status***", "" + licStatus);
		Log.i("*****whlr_cd**", "" + wheelerCd);
		Log.i("****veh_cat***", "" + vehCategory);
		Log.i("****vhle_main_cat***", "" + obMkrCD);
		Log.i("****vhle_sub_cat***", "" + vehicleMainType);
		Log.i("****vilatns***", "" + violations);
		Log.i("****pnding_challans***", "" + pendingChallans);
		Log.i("****extra_psngrs***", "" + noOfExtraPassengers);
		Log.i("****dname***", "" + driverName);
		Log.i("****dfname***", "" + driverFatherName);
		Log.i("****daddress***", "" + driverAddress);
		Log.i("****dcity***", "" + driverCity);
		Log.i("is_govt_police   ", "" + SpotChallan.is_govt_police);
		Log.i("****mobile**", "PARAMETERS PASSED ENDED");
		Log.i("****isItSpotPmt  is ***", "" + isItSpotPmt);
		Log.i("****dl_dob  is ***", "" + dl_dob);

		/*
		 * if (!(onlinebuff==null)) { onlinebuff = new StringBuffer("");
		 * onlinebuff.delete(0, onlinebuff.length()); //v }
		 */

		try {

			if (!(onlinebuff == null)) {
				onlinebuff = new StringBuffer("");
				onlinebuff.delete(0, onlinebuff.length());
				onlinebuff.setLength(0); // v
			}

			// 1|regnInfo^2|dlInfo^3|AadhaarInfo

			// rc_send = "1|"+vhle_num + "!" + rta_data + "^" + "2" + "|" +
			// license_data + "^" + "3" + "|" + aadhar_data

			if ((drvierLicNo != null && !"NA".equals(drvierLicNo)) && (aadharNo != null && !"NA".equals(aadharNo))) {
				Log.i("onlinebuff----------->", "= 1 =");
				onlinebuff = onlinebuff
						.append(rc_send + "^" + "2" + "|" + license_data + "^" + "3" + "|" + aadhar_data);
			} else if ((drvierLicNo != null && !"NA".equals(drvierLicNo))
					&& (aadharNo == null || "NA".equals(aadharNo))) {
				Log.i("onlinebuff----------->", "= 2 =");
				onlinebuff = onlinebuff.append(rc_send + "^" + "2" + "|" + license_data + "^" + "3" + "|" + "NA");
			} else if ((drvierLicNo == null || "NA".equals(drvierLicNo))
					&& (aadharNo != null && !"NA".equals(aadharNo))) {
				Log.i("onlinebuff----------->", "= 3 =");
				onlinebuff = onlinebuff.append(rc_send + "^" + "2" + "|" + "NA" + "^" + "3" + "|" + aadhar_data);
			} else {
				Log.i("onlinebuff----------->", "= 4 =");
				onlinebuff = onlinebuff.append(rc_send + "^" + "2" + "|" + "NA" + "^" + "3" + "|" + "NA");
			}
			Log.i("onlinebuff----------->", "===>" + onlinebuff.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Utils utils = new Utils();
		try {
			Log.i("SERVICEHELT", "" + Dashboard.check_vhleHistory_or_Spot);
			SoapObject request = null;
			if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
				request = new SoapObject(NAMESPACE, "" + SPOT_CHALLAN_PAYMENT_NEW_15);
				request.addProperty(utils.SPOT_VEHICLE_TYPE, "" + typeOfVeh);
			} else if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				request = new SoapObject(NAMESPACE, "" + TOWING_CPACT_METHOD_NAME);
			}
			request.addProperty(utils.VHLE_HIST_SELECTED_PEN_CHALLANS, "" + selectedPendingChallans);
			request.addProperty(utils.SPOT_REGNCD, "" + regnCd);
			request.addProperty(utils.SPOT_VHLE_NO, "" + vehicleNo);
			request.addProperty(utils.SPOT_REG_NO, "" + regnNo);
			request.addProperty(utils.SPOT_GTWY_CD, "" + gtwyCd);
			request.addProperty(utils.SPOT_DT_OF_PAY, "" + dOfPay);
			request.addProperty(utils.SPOT_PYMNT_TIME, "" + pmtTime);
			request.addProperty(utils.SPOT_UNIT_CODE, "" + unitCode);
			request.addProperty(utils.SPOT_UNIT_NAME, "" + unitName);
			request.addProperty(utils.SPOT_PS_CODE, "" + psCode);
			request.addProperty(utils.SPOT_PS_NAME, "" + psName);
			request.addProperty(utils.SPOT_BOOKED_PS_CODE, "" + bookedPsCd);
			request.addProperty(utils.SPOT_BOOBKED_PS_NAME, "" + bookedPsName);
			request.addProperty(utils.SPOT_POINT_CODE, "" + pointCode);
			request.addProperty(utils.SPOT_POINT_NAME, "" + pointName);
			request.addProperty(utils.SPOT_OPERATOR_CODE, "" + operaterCd);
			request.addProperty(utils.SPOT_OPERATOR_NAME, "" + operaterName);
			request.addProperty(utils.SPOT_PID_CODE, "" + pidCd);
			request.addProperty(utils.SPOT_PID_NAME, "" + pidName);
			request.addProperty(utils.SPOT_CADRE_CODE, "" + cadreCD);
			request.addProperty(utils.SPOT_CADRE, "" + cadre);
			request.addProperty(utils.SPOT_TOTAL_AMNT, "" + totalAmount);
			request.addProperty(utils.SPOT_DETAINED, "" + detained);
			request.addProperty(utils.SPOT_SIMID, "" + simId);
			request.addProperty(utils.SPOT_IMEI, "" + imeiNo);
			request.addProperty(utils.SPOT_MACID, "" + macId);
			request.addProperty(utils.SPOT_LAT, "" + latitude);
			request.addProperty(utils.SPOT_LONG, "" + longitude);
			request.addProperty(utils.SPOT_GPS_DATE, "" + gpsDt);
			request.addProperty(utils.SPOT_GPS_TIME, "" + gpsTime);
			request.addProperty(utils.SPOT_ONLINE_MODE, "" + onlinebuff); // v
			request.addProperty(utils.SPOT_MODULE_CODE, "" + moduleCd);
			request.addProperty(utils.SPOT_RELEASE_ITEM, "" + releasedItem);
			request.addProperty(utils.SPOT_CHALLAN_NO, "" + challanNo);
			request.addProperty(utils.SPOT_SERVICE_CODE, "" + serviceCode);
			request.addProperty(utils.SPOT_OWNER_LIC_NO, "" + ownerLicNo);
			request.addProperty(utils.SPOT_DRVR_LIC_NO, "" + drvierLicNo);
			request.addProperty(utils.SPOT_PWD, "" + password);
			request.addProperty(utils.SPOT_IMAGE, "" + imageEvidence);
			request.addProperty(utils.SPOT_CHALLAN_TYPE, "" + challanType);
			request.addProperty(utils.SPOT_CHALLAN_CODE, "" + challanCd);
			request.addProperty(utils.SPOT_LOCATION, "" + location);
			request.addProperty(utils.SPOT_REMARKS, "" + remarks);
			request.addProperty(utils.SPOT_PANCARD_NO, "" + pancardNo);
			request.addProperty(utils.SPOT_AADHAR_NO, "" + aadharNo);
			request.addProperty(utils.SPOT_VOTERID, "" + voterId);
			request.addProperty(utils.SPOT_PASSPORT, "" + passport);
			request.addProperty(utils.SPOT_EMAIL, "" + email);
			request.addProperty(utils.SPOT_DRIVER_CNTCT_NO, "" + driverContactNo);
			request.addProperty(utils.SPOT_IS_IT_SPOT, "" + isItSpotPmt);
			request.addProperty(utils.SPOT_OFFENCE_DATE, "" + offenceDt);
			request.addProperty(utils.SPOT_OFFENCE_TIME, "" + offenceTime);
			request.addProperty(utils.SPOT_LIC_STATUS, "" + licStatus);
			request.addProperty(utils.SPOT_WHEELER_CODE, "" + wheelerCd);
			request.addProperty(utils.SPOT_VEH_CAT, "" + vehCategory);
			request.addProperty(utils.SPOT_VEH_MAIN_TYPE, "" + obMkrCD);
			request.addProperty(utils.SPOT_VEH_SUB, "" + vehicleMainType);
			request.addProperty(utils.SPOT_VIOLATIONS, "" + violations);
			request.addProperty(utils.SPOT_PENDING_CHALLANS, "" + pendingChallans);
			request.addProperty(utils.EXTRA_PASSENGERS, "" + noOfExtraPassengers);
			request.addProperty(utils.SPOT_DRIVERNAME, "" + driverName);
			request.addProperty(utils.SPOT_DRIVER_FNAME, "" + driverFatherName);
			request.addProperty(utils.SPOT_DRIVER_ADDRESS, "" + driverAddress);
			request.addProperty(utils.SPOT_DRIVER_CITY, "" + driverCity);
			if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
				request.addProperty("dl_dob", "" + dl_dob);
			}

			Log.i("**Request :::***", "" + request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			/*
			 * spot_final_res_status = ""; spot_final_res_status =
			 * result.toString();
			 */

			try {
				spot_final_res_status = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**mobileSpotCha**", "" + spot_final_res_status);

			final_spot_reponse_master = new String[0];// arr[0]-print,arr[1]-display,arr[2]-violations
			final_spot_reponse_violations_master = new String[0];// violations :
			// 01@100@500@description,01@100@500@description
			final_spot_reponse_details = new String[0];// display data
			final_spot_reponse_violations = new String[0][0];// violations :
			// 01,100,500,description

			if (spot_final_res_status.toString().trim().equals("0")) {
				if (final_spot_reponse_master.length == 0) {
					final_spot_reponse_master = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations_master = new String[0];
				}

				if (final_spot_reponse_details.length == 0) {
					final_spot_reponse_details = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations = new String[0][0];
				}

			} else {

				final_spot_reponse_master = spot_final_res_status.split("\\^");

				/*
				 * Log.i("SPOT PRINT REPONSE", "" +
				 * final_spot_reponse_master[0]);
				 *
				 * Log.i("SPOT DISPLAY REPONSE", "" +
				 * final_spot_reponse_master[1]);
				 *
				 * Log.i("SPOT VIOLATIONS REPONSE", "" +
				 * final_spot_reponse_master[6]);
				 */

				for (int i = 0; i < final_spot_reponse_master.length; i++) {

					Log.i("final_spot_re::", "" + final_spot_reponse_master[i]);

				}

				/* DISPLAY REPONSE */
				if (final_spot_reponse_master[1].toString().trim().length() > 0) {
					final_spot_reponse_details = final_spot_reponse_master[1].split("!");

					for (int i = 0; i < final_spot_reponse_details.length; i++) {
						Log.i("final_spot_::", "" + final_spot_reponse_details[i]);
					}
				}

				/* VIOLATIONS DISPLAY */
				if (final_spot_reponse_master[2].toString().trim().length() > 0) {

					/*
					 * THIS SPLITTING IS FOR GETTING RECORDS LIKE THIS EX:
					 * 01@100@500@description
					 */
					final_spot_reponse_violations_master = final_spot_reponse_master[2].split("!");
					Log.i("final_spr", "" + final_spot_reponse_violations_master.length);
					final_spot_reponse_violations = new String[final_spot_reponse_violations_master.length][3];

					for (int i = 0; i < final_spot_reponse_violations_master.length; i++) {
						final_spot_reponse_violations[i] = final_spot_reponse_violations_master[i].split("\\@");
					}

				}

			}

		} catch (SoapFault fault) {
			Log.i("****SOAP FAU", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("SPOT EXCPTN", "" + e.getStackTrace());
			if (final_spot_reponse_master.length == 0) {
				final_spot_reponse_master = new String[0];
			}

			if (final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations_master = new String[0];
			}

			if (final_spot_reponse_details.length == 0) {
				final_spot_reponse_details = new String[0];
			}

			if (final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations = new String[0][0];
			}
		}
	}

	public static void mobileSpotChallanPaying(String spot_selected_penchallans, String regn_Cd, String vehicle_No,
											   String regn_No, String gtwy_cd, String dateof_pymnt, String pymnt_time, String unit_cd, String unit_name,
											   String ps_code, String ps_name, String booked_cd, String booked_name, String point_cd, String point_name,
											   String operator_cd, String operator_name, String pid_cd, String pid_name, String cadre_cd, String cdre,
											   String total_amnt, String detained_items, String simid, String imei_no, String mac_id, String lat_val,
											   String long_val, String gps_date, String gps_time, String onlinemode, String module_code,
											   String released_items, String challan_num, String service_code, String ownr_lic_no, String drvr_lic_no,
											   String pwd, String image_data, String challan_type, String challan_code, String location, String remarks,
											   String pan_num, String aadhar_num, String voter_id, String pasprt, String mailid, String drvr_cntct_num,
											   String is_spot, String ofnce_date, String ofence_time, String lic_status, String whlr_cd, String veh_cat,
											   String vhle_main_cat, String vhle_sub_cat, String vilatns, String pnding_challans, String epeople,
											   String dname, String dfname, String daddress, String dcity) {
		Log.i("****mobileSo***", "Please wait...");
		Log.i("****mobileS***", "PARAMETERS PASSED START");
		Log.i("****spot_seles***", "" + spot_selected_penchallans);
		Log.i("****regn_Cd***", "" + regn_Cd);
		Log.i("***vehicle_No****", "" + vehicle_No);
		Log.i("**regn_No*****", "" + regn_No);
		Log.i("***gtwy_cd****", "" + gtwy_cd);
		Log.i("****dateof_pymnt***", "" + dateof_pymnt);
		Log.i("****pymnt_time***", "" + pymnt_time);
		Log.i("**unit_cd*****", "" + unit_cd);
		Log.i("****unit_name***", "" + unit_name);
		Log.i("***ps_code****", "" + ps_code);
		Log.i("****ps_name***", "" + ps_name);
		Log.i("****booked_cd***", "" + booked_cd);
		Log.i("**booked_name*****", "" + booked_name);
		Log.i("**point_cd*****", "" + point_cd);
		Log.i("****point_name***", "" + point_name);
		Log.i("**operator_cd*****", "" + operator_cd);
		Log.i("***operator_name****", "" + operator_name);
		Log.i("****pid_cd***", "" + pid_cd);
		Log.i("****pid_name***", "" + pid_name);
		Log.i("*cadre_cd******", "" + cadre_cd);
		Log.i("*****cdre**", "" + cdre);
		Log.i("***total_amnt****", "" + total_amnt);
		Log.i("****detained_items***", "" + detained_items);
		Log.i("****simid***", "" + simid);
		Log.i("****imei_no***", "" + imei_no);
		Log.i("*****mac_id**", "" + mac_id);
		Log.i("*****lat_val**", "" + lat_val);
		Log.i("***long_val****", "" + long_val);
		Log.i("****gps_date***", "" + gps_date);
		Log.i("*****gps_time**", "" + gps_time);
		Log.i("*onlinemode******", "" + onlinemode);
		Log.i("***module_code****", "" + module_code);
		Log.i("***released_items****", "" + released_items);
		Log.i("***challan_num****", "" + challan_num);
		Log.i("****service_code***", "" + service_code);
		Log.i("***ownr_lic_no****", "" + ownr_lic_no);
		Log.i("**drvr_lic_no*****", "" + drvr_lic_no);
		Log.i("****pwd***", "" + pwd);
		Log.i("***image_data****", "" + image_data);
		Log.i("***challan_type****", "" + challan_type);
		Log.i("*****challan_code**", "" + challan_code);
		Log.i("*****location**", "" + location);
		Log.i("****remarks***", "" + remarks);
		Log.i("******pan_num*", "" + pan_num);
		Log.i("****aadhar_num***", "" + aadhar_num);
		Log.i("**voter_id*****", "" + voter_id);
		Log.i("*****pasprt**", "" + pasprt);
		Log.i("***mailid****", "" + mailid);
		Log.i("**drvr_cntct_num*****", "" + drvr_cntct_num);
		Log.i("*****is_spot**", "" + is_spot);
		Log.i("*****ofnce_date**", "" + ofnce_date);
		Log.i("*****ofence_time**", "" + ofence_time);
		Log.i("****lic_status***", "" + lic_status);
		Log.i("*****whlr_cd**", "" + whlr_cd);
		Log.i("****veh_cat***", "" + veh_cat);
		Log.i("****vhle_main_cat***", "" + vhle_main_cat);
		Log.i("****vhle_sub_cat***", "" + vhle_sub_cat);
		Log.i("****vilatns***", "" + vilatns);
		Log.i("****pnding_challans***", "" + pnding_challans);
		Log.i("****extra_psngrs***", "" + epeople);
		Log.i("****dname***", "" + dname);
		Log.i("****dfname***", "" + dfname);
		Log.i("****daddress***", "" + daddress);
		Log.i("****dcity***", "" + dcity);
		Log.i("****mobileS*", "PARAMETERS PASSED ENDED");

		Utils utils = new Utils();
		try {
			Log.i("SERVICEHELPERT", "" + Dashboard.check_vhleHistory_or_Spot);
			SoapObject request = null;
			if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
				request = new SoapObject(NAMESPACE, "" + MOBILE_SPOT_CHALLAN_METHOD_NAME);
			} else if (Dashboard.check_vhleHistory_or_Spot.equals("towing")) {
				request = new SoapObject(NAMESPACE, "" + TOWING_CPACT_METHOD_NAME);
			}

			request.addProperty(utils.VHLE_HIST_SELECTED_PEN_CHALLANS, "" + spot_selected_penchallans);
			request.addProperty(utils.SPOT_REGNCD, "" + regn_Cd);
			request.addProperty(utils.SPOT_VHLE_NO, "" + vehicle_No);
			request.addProperty(utils.SPOT_REG_NO, "" + regn_No);
			request.addProperty(utils.SPOT_GTWY_CD, "" + gtwy_cd);
			request.addProperty(utils.SPOT_DT_OF_PAY, "" + dateof_pymnt);
			request.addProperty(utils.SPOT_PYMNT_TIME, "" + pymnt_time);
			request.addProperty(utils.SPOT_UNIT_CODE, "" + unit_cd);
			request.addProperty(utils.SPOT_UNIT_NAME, "" + unit_name);
			request.addProperty(utils.SPOT_PS_CODE, "" + ps_code);
			request.addProperty(utils.SPOT_PS_NAME, "" + ps_name);
			request.addProperty(utils.SPOT_BOOKED_PS_CODE, "" + booked_cd);
			request.addProperty(utils.SPOT_BOOBKED_PS_NAME, "" + booked_name);
			request.addProperty(utils.SPOT_POINT_CODE, "" + point_cd);
			request.addProperty(utils.SPOT_POINT_NAME, "" + point_name);
			request.addProperty(utils.SPOT_OPERATOR_CODE, "" + operator_cd);
			request.addProperty(utils.SPOT_OPERATOR_NAME, "" + operator_name);
			request.addProperty(utils.SPOT_PID_CODE, "" + pid_cd);
			request.addProperty(utils.SPOT_PID_NAME, "" + pid_name);
			request.addProperty(utils.SPOT_CADRE_CODE, "" + cadre_cd);
			request.addProperty(utils.SPOT_CADRE, "" + cdre);
			request.addProperty(utils.SPOT_TOTAL_AMNT, "" + total_amnt);
			request.addProperty(utils.SPOT_DETAINED, "" + detained_items);
			request.addProperty(utils.SPOT_SIMID, "" + simid);
			request.addProperty(utils.SPOT_IMEI, "" + imei_no);
			request.addProperty(utils.SPOT_MACID, "" + mac_id);
			request.addProperty(utils.SPOT_LAT, "" + lat_val);
			request.addProperty(utils.SPOT_LONG, "" + long_val);
			request.addProperty(utils.SPOT_GPS_DATE, "" + gps_date);
			request.addProperty(utils.SPOT_ONLINE_MODE, "" + onlinemode);
			request.addProperty(utils.SPOT_MODULE_CODE, "" + module_code);
			request.addProperty(utils.SPOT_RELEASE_ITEM, "" + released_items);
			request.addProperty(utils.SPOT_CHALLAN_NO, "" + challan_num);
			request.addProperty(utils.SPOT_SERVICE_CODE, "" + service_code);
			request.addProperty(utils.SPOT_OWNER_LIC_NO, "" + ownr_lic_no);
			request.addProperty(utils.SPOT_DRVR_LIC_NO, "" + drvr_lic_no);
			request.addProperty(utils.SPOT_PWD, "" + pwd);
			request.addProperty(utils.SPOT_IMAGE, "" + image_data);
			request.addProperty(utils.SPOT_CHALLAN_TYPE, "" + challan_type);
			request.addProperty(utils.SPOT_CHALLAN_CODE, "" + challan_code);
			request.addProperty(utils.SPOT_LOCATION, "" + location);
			request.addProperty(utils.SPOT_REMARKS, "" + remarks);
			request.addProperty(utils.SPOT_PANCARD_NO, "" + pan_num);
			request.addProperty(utils.SPOT_AADHAR_NO, "" + aadhar_num);
			request.addProperty(utils.SPOT_VOTERID, "" + voter_id);
			request.addProperty(utils.SPOT_PASSPORT, "" + pasprt);
			request.addProperty(utils.SPOT_EMAIL, "" + mailid);
			request.addProperty(utils.SPOT_DRIVER_CNTCT_NO, "" + drvr_cntct_num);
			request.addProperty(utils.SPOT_IS_IT_SPOT, "" + is_spot);
			request.addProperty(utils.SPOT_OFFENCE_DATE, "" + ofnce_date);
			request.addProperty(utils.SPOT_OFFENCE_TIME, "" + ofence_time);
			request.addProperty(utils.SPOT_LIC_STATUS, "" + lic_status);
			request.addProperty(utils.SPOT_WHEELER_CODE, "" + whlr_cd);
			request.addProperty(utils.SPOT_VEH_CAT, "" + veh_cat);
			request.addProperty(utils.SPOT_VEH_MAIN_TYPE, "" + vhle_main_cat);
			request.addProperty(utils.SPOT_VEH_SUB, "" + vhle_sub_cat);
			request.addProperty(utils.SPOT_VIOLATIONS, "" + vilatns);
			request.addProperty(utils.SPOT_PENDING_CHALLANS, "" + pnding_challans);
			request.addProperty(utils.EXTRA_PASSENGERS, "" + epeople);
			request.addProperty(utils.SPOT_DRIVERNAME, "" + dname);
			request.addProperty(utils.SPOT_DRIVER_FNAME, "" + dfname);
			request.addProperty(utils.SPOT_DRIVER_ADDRESS, "" + daddress);
			request.addProperty(utils.SPOT_DRIVER_CITY, "" + dcity);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// spot_final_res_status = "";
			// spot_final_res_status = result.toString();

			try {
				spot_final_res_status = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**mobileSpotChall", "" + spot_final_res_status);
			/* SPOT REPONSE STAART */
			/* arr[0]-print */
			/* arr[1]-display */
			/* arr[2]-violations */
			/* arr[3]-pending challans--buffer displayy */
			/* arr[4]-detained items --buffer displayy */
			/* arr[5]-Released detained items --buffer displayy */
			/* SPOT REPONSE END */

			final_spot_reponse_master = new String[0];// arr[0]-print,arr[1]-display,arr[2]-violations
			final_spot_reponse_violations_master = new String[0];// violations :
			// 01@100@500@description,01@100@500@description
			final_spot_reponse_details = new String[0];// display data
			final_spot_reponse_violations = new String[0][0];// violations :
			// 01,100,500,description

			if (spot_final_res_status.toString().trim().equals("0")) {
				if (final_spot_reponse_master.length == 0) {
					final_spot_reponse_master = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations_master = new String[0];
				}

				if (final_spot_reponse_details.length == 0) {
					final_spot_reponse_details = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations = new String[0][0];
				}

			} else {

				final_spot_reponse_master = spot_final_res_status.split("\\^");

				Log.i("SPOT PRINT REPONSE", "" + final_spot_reponse_master[0]);

				Log.i("SPOT DISPLAY REPONSE", "" + final_spot_reponse_master[1]);

				Log.i("SPOT VIOLATIONS REPONSE", "" + final_spot_reponse_master[2]);

				/* DISPLAY REPONSE */
				if (final_spot_reponse_master[1].toString().trim().length() > 0) {
					final_spot_reponse_details = final_spot_reponse_master[1].split("!");
				}

				/* VIOLATIONS DISPLAY */
				if (final_spot_reponse_master[2].toString().trim().length() > 0) {

					/*
					 * THIS SPLITTING IS FOR GETTING RECORDS LIKE THIS EX:
					 * 01@100@500@description
					 */
					final_spot_reponse_violations_master = final_spot_reponse_master[2].split("!");
					Log.i("final_spot_rr", "" + final_spot_reponse_violations_master.length);
					final_spot_reponse_violations = new String[final_spot_reponse_violations_master.length][3];

					for (int i = 0; i < final_spot_reponse_violations_master.length; i++) {
						final_spot_reponse_violations[i] = final_spot_reponse_violations_master[i].split("\\@");
					}

				}

			}

		} catch (SoapFault fault) {
			Log.i("****SOAP :", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("SPOT EXCPTN", "" + e.getStackTrace());
			if (final_spot_reponse_master.length == 0) {
				final_spot_reponse_master = new String[0];
			}

			if (final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations_master = new String[0];
			}

			if (final_spot_reponse_details.length == 0) {
				final_spot_reponse_details = new String[0];
			}

			if (final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations = new String[0][0];
			}

		}
	}

	public static void mobileSpotChallanPayingNew1_5_2(String selectedPendingChallans, String regnCd, String vehicleNo,
													   String regnNo, String gtwyCd, String dOfPay, String pmtTime, String unitCode, String unitName,
													   String psCode, String psName, String bookedPsCd, String bookedPsName, String pointCode, String pointName,
													   String operaterCd, String operaterName, String pidCd, String pidName, String cadreCD, String cadre,
													   String totalAmount, String detained, String simId, String imeiNo, String macId, String latitude,
													   String longitude, String gpsDt, String gpsTime, String onlineMode, String moduleCd, String releasedItem,
													   String challanNo, String serviceCode, String ownerLicNo, String drvierLicNo, String password,
													   String imageEvidence, String challanType, String challanCd, String location, String remarks,
													   String pancardNo, String aadharNo, String voterId, String passport, String email, String driverContactNo,
													   String isItSpotPmt, String offenceDt, String offenceTime, String licStatus, String wheelerCd,
													   String vehCategory, String obMkrCD, String vehicleMainType, String violations, String pendingChallans,
													   String noOfExtraPassengers, String driverName, String driverFatherName, String driverAddress,
													   String driverCity, String typeOfVeh, String occupation_Name, String occupation_Address,
													   String occupation_EmailId) {

		Log.i("****selectedP*", "" + selectedPendingChallans);
		Log.i("****regnCd***", "" + regnCd);
		Log.i("****vehicleNo***", "" + vehicleNo);
		Log.i("****regnNo***", "" + regnNo);
		Log.i("****gtwyCd***", "" + gtwyCd);
		Log.i("****dOfPay***", "" + dOfPay);
		Log.i("****pmtTime***", "" + pmtTime);
		Log.i("***unitCode****", "" + unitCode);
		Log.i("**unitName*****", "" + unitName);
		Log.i("***psCode****", "" + psCode);
		Log.i("****psName***", "" + psName);
		Log.i("****pymnt_time***", "" + pmtTime);
		Log.i("**unit_cd*****", "" + unitCode);
		Log.i("****unit_name***", "" + unitName);
		Log.i("***ps_code****", "" + psCode);
		Log.i("****ps_name***", "" + psName);
		Log.i("****booked_cd***", "" + bookedPsCd);
		Log.i("**booked_name*****", "" + bookedPsName);
		Log.i("**point_cd*****", "" + pointCode);
		Log.i("****point_name***", "" + pointName);
		Log.i("**operator_cd*****", "" + operaterCd);
		Log.i("***operator_name****", "" + operaterName);
		Log.i("****pid_cd***", "" + pidCd);
		Log.i("****pid_name***", "" + pidName);
		Log.i("*cadre_cd******", "" + cadreCD);
		Log.i("*****cdre**", "" + cadre);
		Log.i("***total_amnt****", "" + totalAmount);
		Log.i("****detained_items***", "" + detained);
		Log.i("****simid***", "" + simId);
		Log.i("****imei_no***", "" + imeiNo);
		Log.i("*****mac_id**", "" + macId);
		Log.i("*****lat_val**", "" + latitude);
		Log.i("***long_val****", "" + longitude);
		Log.i("****gps_date***", "" + gpsDt);
		Log.i("*****gps_time**", "" + gpsTime);
		Log.i("*onlinemode******", "" + onlineMode);
		Log.i("***module_code****", "" + moduleCd);
		Log.i("***released_items****", "" + releasedItem);
		Log.i("***challan_num****", "" + challanNo);
		Log.i("****service_code***", "" + serviceCode);
		Log.i("***ownr_lic_no****", "" + ownerLicNo);
		Log.i("**drvr_lic_no*****", "" + drvierLicNo);
		Log.i("****pwd***", "" + password);
		Log.i("***image_data****", "" + imageEvidence);
		Log.i("***challan_type****", "" + challanType);
		Log.i("*****challan_code**", "" + challanCd);
		Log.i("*****location**", "" + location);
		Log.i("****remarks***", "" + remarks);
		Log.i("******pan_num*", "" + pancardNo);
		Log.i("****aadhar_num***", "" + aadharNo);
		Log.i("**voter_id*****", "" + voterId);
		Log.i("*****pasprt**", "" + passport);
		Log.i("***mailid****", "" + email);
		Log.i("**drvr_cntct_num*****", "" + driverContactNo);
		Log.i("*****is_spot**", "" + isItSpotPmt);
		Log.i("*****ofnce_date**", "" + offenceDt);
		Log.i("*****ofence_time**", "" + offenceTime);
		Log.i("****lic_status***", "" + licStatus);
		Log.i("*****whlr_cd**", "" + wheelerCd);
		Log.i("****veh_cat***", "" + vehCategory);
		Log.i("****vhle_main_cat***", "" + obMkrCD);
		Log.i("****vhle_sub_cat***", "" + vehicleMainType);
		Log.i("****vilatns***", "" + violations);
		Log.i("****pnding_challans***", "" + pendingChallans);
		Log.i("****extra_psngrs***", "" + noOfExtraPassengers);
		Log.i("****dname***", "" + driverName);
		Log.i("****dfname***", "" + driverFatherName);
		Log.i("****daddress***", "" + driverAddress);
		Log.i("****dcity***", "" + driverCity);
		Log.i("is_govt_police   ", "" + SpotChallan.is_govt_police);
		Log.i("****mobileS**", "PARAMETERS PASSED ENDED");

		Utils utils = new Utils();
		try {
			Log.i("SERVICEHELPER ST", "" + Dashboard.check_vhleHistory_or_Spot);
			SoapObject request = null;
			if (Dashboard.check_vhleHistory_or_Spot.equals("spot")) {
				request = new SoapObject(NAMESPACE, "" + SPOT_CHALLAN_PAYMENT_NEW_1_5_2);
				request.addProperty(utils.SPOT_VEHICLE_TYPE, "" + typeOfVeh);
			}
			/*
			 * } else if (Dashboard.check_vhleHistory_or_Spot.equals("towing"))
			 * { request = new SoapObject(NAMESPACE, ""+
			 * TOWING_CPACT_METHOD_NAME); }
			 */

			request.addProperty(utils.VHLE_HIST_SELECTED_PEN_CHALLANS, "" + selectedPendingChallans);
			request.addProperty(utils.SPOT_REGNCD, "" + regnCd);
			request.addProperty(utils.SPOT_VHLE_NO, "" + vehicleNo);
			request.addProperty(utils.SPOT_REG_NO, "" + regnNo);
			request.addProperty(utils.SPOT_GTWY_CD, "" + gtwyCd);
			request.addProperty(utils.SPOT_DT_OF_PAY, "" + dOfPay);
			request.addProperty(utils.SPOT_PYMNT_TIME, "" + pmtTime);
			request.addProperty(utils.SPOT_UNIT_CODE, "" + unitCode);
			request.addProperty(utils.SPOT_UNIT_NAME, "" + unitName);
			request.addProperty(utils.SPOT_PS_CODE, "" + psCode);
			request.addProperty(utils.SPOT_PS_NAME, "" + psName);
			request.addProperty(utils.SPOT_BOOKED_PS_CODE, "" + bookedPsCd);
			request.addProperty(utils.SPOT_BOOBKED_PS_NAME, "" + bookedPsName);
			request.addProperty(utils.SPOT_POINT_CODE, "" + pointCode);
			request.addProperty(utils.SPOT_POINT_NAME, "" + pointName);
			request.addProperty(utils.SPOT_OPERATOR_CODE, "" + operaterCd);
			request.addProperty(utils.SPOT_OPERATOR_NAME, "" + operaterName);
			request.addProperty(utils.SPOT_PID_CODE, "" + pidCd);
			request.addProperty(utils.SPOT_PID_NAME, "" + pidName);
			request.addProperty(utils.SPOT_CADRE_CODE, "" + cadreCD);
			request.addProperty(utils.SPOT_CADRE, "" + cadre);
			request.addProperty(utils.SPOT_TOTAL_AMNT, "" + totalAmount);
			request.addProperty(utils.SPOT_DETAINED, "" + detained);
			request.addProperty(utils.SPOT_SIMID, "" + simId);
			request.addProperty(utils.SPOT_IMEI, "" + imeiNo);
			request.addProperty(utils.SPOT_MACID, "" + macId);
			request.addProperty(utils.SPOT_LAT, "" + latitude);
			request.addProperty(utils.SPOT_LONG, "" + longitude);
			request.addProperty(utils.SPOT_GPS_DATE, "" + gpsDt);
			request.addProperty(utils.SPOT_GPS_TIME, "" + gpsTime);
			request.addProperty(utils.SPOT_ONLINE_MODE, "" + onlineMode);
			request.addProperty(utils.SPOT_MODULE_CODE, "" + moduleCd);
			request.addProperty(utils.SPOT_RELEASE_ITEM, "" + releasedItem);
			request.addProperty(utils.SPOT_CHALLAN_NO, "" + challanNo);
			request.addProperty(utils.SPOT_SERVICE_CODE, "" + serviceCode);
			request.addProperty(utils.SPOT_OWNER_LIC_NO, "" + ownerLicNo);
			request.addProperty(utils.SPOT_DRVR_LIC_NO, "" + drvierLicNo);
			request.addProperty(utils.SPOT_PWD, "" + password);
			request.addProperty(utils.SPOT_IMAGE, "" + imageEvidence);
			request.addProperty(utils.SPOT_CHALLAN_TYPE, "" + challanType);
			request.addProperty(utils.SPOT_CHALLAN_CODE, "" + challanCd);
			request.addProperty(utils.SPOT_LOCATION, "" + location);
			request.addProperty(utils.SPOT_REMARKS, "" + remarks);
			request.addProperty(utils.SPOT_PANCARD_NO, "" + pancardNo);
			request.addProperty(utils.SPOT_AADHAR_NO, "" + aadharNo);
			request.addProperty(utils.SPOT_VOTERID, "" + voterId);
			request.addProperty(utils.SPOT_PASSPORT, "" + passport);
			request.addProperty(utils.SPOT_EMAIL, "" + email);
			request.addProperty(utils.SPOT_DRIVER_CNTCT_NO, "" + driverContactNo);
			request.addProperty(utils.SPOT_IS_IT_SPOT, "" + isItSpotPmt);
			request.addProperty(utils.SPOT_OFFENCE_DATE, "" + offenceDt);
			request.addProperty(utils.SPOT_OFFENCE_TIME, "" + offenceTime);
			request.addProperty(utils.SPOT_LIC_STATUS, "" + licStatus);
			request.addProperty(utils.SPOT_WHEELER_CODE, "" + wheelerCd);
			request.addProperty(utils.SPOT_VEH_CAT, "" + vehCategory);
			request.addProperty(utils.SPOT_VEH_MAIN_TYPE, "" + obMkrCD);
			request.addProperty(utils.SPOT_VEH_SUB, "" + vehicleMainType);
			request.addProperty(utils.SPOT_VIOLATIONS, "" + violations);
			request.addProperty(utils.SPOT_PENDING_CHALLANS, "" + pendingChallans);
			request.addProperty(utils.EXTRA_PASSENGERS, "" + noOfExtraPassengers);
			request.addProperty(utils.SPOT_DRIVERNAME, "" + driverName);
			request.addProperty(utils.SPOT_DRIVER_FNAME, "" + driverFatherName);
			request.addProperty(utils.SPOT_DRIVER_ADDRESS, "" + driverAddress);
			request.addProperty(utils.SPOT_DRIVER_CITY, "" + driverCity);

			request.addProperty(utils.DD_OCCUPATION, occupation_Name);
			request.addProperty(utils.DD_OCCUP_ADDRSS, occupation_Address);
			request.addProperty(utils.DD_OCCUP_MAIL, occupation_EmailId);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			spot_final_res_status = "";
			// spot_final_res_status = result.toString();
			try {
				spot_final_res_status = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**mobileSpoSE***", "" + spot_final_res_status);

			final_spot_reponse_master = new String[0];// arr[0]-print,arr[1]-display,arr[2]-violations
			final_spot_reponse_violations_master = new String[0];// violations :
			// 01@100@500@description,01@100@500@description
			final_spot_reponse_details = new String[0];// display data
			final_spot_reponse_violations = new String[0][0];// violations :
			// 01,100,500,description

			if (spot_final_res_status.toString().trim().equals("0")) {
				if (final_spot_reponse_master.length == 0) {
					final_spot_reponse_master = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations_master = new String[0];
				}

				if (final_spot_reponse_details.length == 0) {
					final_spot_reponse_details = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations = new String[0][0];
				}

			} else {

				final_spot_reponse_master = spot_final_res_status.split("\\^");

				Log.i("SPOT PRINT REPONSE", "" + final_spot_reponse_master[0]);

				Log.i("SPOT DISPLAY REPONSE", "" + final_spot_reponse_master[1]);

				Log.i("SPOT VIOLATIONS REPONSE", "" + final_spot_reponse_master[2]);

				/* DISPLAY REPONSE */
				if (final_spot_reponse_master[1].toString().trim().length() > 0) {
					final_spot_reponse_details = final_spot_reponse_master[1].split("!");
				}

				/* VIOLATIONS DISPLAY */
				if (final_spot_reponse_master[2].toString().trim().length() > 0) {
					/*
					 * THIS SPLITTING IS FOR GETTING RECORDS LIKE THIS EX:
					 * 01@100@500@description
					 */
					final_spot_reponse_violations_master = final_spot_reponse_master[2].split("!");
					Log.i("final_spot_reponser", "" + final_spot_reponse_violations_master.length);
					Log.i("final_spot_reponsr 0", "" + final_spot_reponse_violations_master[0]);
					Log.i("final_spot_repoer 1", "" + final_spot_reponse_violations_master[1]);
					final_spot_reponse_violations = new String[final_spot_reponse_violations_master.length][3];

					for (int i = 0; i < final_spot_reponse_violations_master.length; i++) {
						final_spot_reponse_violations[i] = final_spot_reponse_violations_master[i].split("\\@");
						Log.i("final_spot_s[i] 1", "" + final_spot_reponse_violations[i]);
					}

				}

			}

		} catch (SoapFault fault) {
			Log.i("****S***:::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("SPOT EXCPTN", "" + e.getStackTrace());
			if (final_spot_reponse_master.length == 0) {
				final_spot_reponse_master = new String[0];
			}

			if (final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations_master = new String[0];
			}

			if (final_spot_reponse_details.length == 0) {
				final_spot_reponse_details = new String[0];
			}

			if (final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations = new String[0][0];
			}
		}
	}
	public static void vehicleHistory(String vhle_hist_penchallans, String pen_challans, String regn_No, String gtwy_cd,
									  String unit_cd, String ps_code, String ps_name, String pid_cd, String pid_name, String total_amnt,
									  String detained_items, String dateof_pymnt, String pymnt_time, String simid, String imei_no, String lat_val,
									  String long_val, String point_cd, String point_name, String onlinemode, String module_code,
									  String released_detValues, String challan_num, String service_code, String ownr_lic_no, String drvr_lic_no,
									  String pwd, String image_data, String challan_type, String challan_code, String location, String remarks,
									  String pan_num, String aadhar_num, String voter_id, String pasprt, String mailid, String drvr_cntct_num,
									  String is_spot, String cadre_cd, String cdre) {

		Log.i("****sele*", "" + vhle_hist_penchallans != null && vhle_hist_penchallans.length() > 0
				? vhle_hist_penchallans : "NA");
		Log.i("****pen_challans***", "" + pen_challans != null && pen_challans.length() > 0 ? pen_challans : "NA");
		Log.i("**regn_No*****", "" + regn_No != null && regn_No.length() > 0 ? regn_No : "NA");
		Log.i("***gtwy_cd****", "" + gtwy_cd != null && gtwy_cd.length() > 0 ? gtwy_cd : "NA");
		Log.i("**unit_cd*****", "" + unit_cd != null && unit_cd.length() > 0 ? unit_cd : "NA");
		Log.i("***ps_code****", "" + ps_code != null && ps_code.length() > 0 ? ps_code : "NA");
		Log.i("****ps_name***", "" + ps_name != null && ps_name.length() > 0 ? ps_name : "NA");
		Log.i("****pid_cd***", "" + pid_cd != null && pid_cd.length() > 0 ? pid_cd : "NA");
		Log.i("****pid_name***", "" + pid_name != null && pid_name.length() > 0 ? pid_name : "NA");
		Log.i("***total_amnt****", "" + total_amnt != null && total_amnt.length() > 0 ? total_amnt : "NA");
		Log.i("****detained_items***",
				"" + detained_items != null && detained_items.length() > 0 ? detained_items : "");
		Log.i("****dateof_pymnt***", "" + dateof_pymnt != null && dateof_pymnt.length() > 0 ? dateof_pymnt : "NA");
		Log.i("****pymnt_time***", "" + pymnt_time != null && pymnt_time.length() > 0 ? pymnt_time : "NA");
		Log.i("****simid***", "" + simid);
		Log.i("****imei_no***", "" + imei_no);
		Log.i("*****lat_val**", "" + lat_val);
		Log.i("***long_val****", "" + long_val);
		Log.i("***point_cd****", "" + point_cd != null && point_cd.length() > 0 ? point_cd : "NA");
		Log.i("***point_name****", "" + point_name != null && point_name.length() > 0 ? point_name : "NA");
		Log.i("*onlinemode******", "" + onlinemode != null && onlinemode.length() > 0 ? onlinemode : "NA");
		Log.i("***module_code****", "" + module_code != null && module_code.length() > 0 ? module_code : "NA");
		Log.i("***re*",
				"" + released_detValues != null && released_detValues.length() > 0 ? released_detValues : "NA");
		Log.i("***challan_num****", "" + challan_num != null && challan_num.length() > 0 ? challan_num : "NA");
		Log.i("****service_code***", "" + service_code != null && service_code.length() > 0 ? service_code : "NA");
		Log.i("***ownr_lic_no****", "" + ownr_lic_no != null && ownr_lic_no.length() > 0 ? ownr_lic_no : "NA");
		Log.i("**drvr_lic_no*****", "" + drvr_lic_no != null && drvr_lic_no.length() > 0 ? drvr_lic_no : "NA");
		Log.i("****pwd***", "" + pwd != null && pwd.length() > 0 ? pwd : "NA");
		Log.i("***image_data****", "" + image_data != null && image_data.length() > 0 ? image_data : "NA");
		Log.i("***challan_type****", "" + challan_type != null && challan_type.length() > 0 ? challan_type : "NA");
		Log.i("*****challan_code**", "" + challan_code != null && challan_code.length() > 0 ? challan_code : "NA");
		Log.i("*****location**", "" + location != null && location.length() > 0 ? location : "NA");
		Log.i("****remarks***", "" + remarks != null && remarks.length() > 0 ? remarks : "NA");
		Log.i("******pan_num*", "" + pan_num != null && pan_num.length() > 0 ? pan_num : "NA");
		Log.i("****aadhar_num***", "" + aadhar_num != null && aadhar_num.length() > 0 ? aadhar_num : "NA");
		Log.i("**voter_id*****", "" + voter_id != null && voter_id.length() > 0 ? voter_id : "NA");
		Log.i("*****pasprt**", "" + pasprt != null && pasprt.length() > 0 ? pasprt : "NA");
		Log.i("***mailid****", "" + mailid != null && mailid.length() > 0 ? mailid : "NA");
		Log.i("**drvr_cntct_num*****", "" + drvr_cntct_num);
		Log.i("*****is_spot**", "" + is_spot);
		Log.i("*cadre_cd******", "" + cadre_cd);

		try {
			SoapObject request = new SoapObject(NAMESPACE, "mobilePendingChallanPayment");
			request.addProperty("selectedPendingChallans",
					"" + vhle_hist_penchallans != null && vhle_hist_penchallans.length() > 0 ? vhle_hist_penchallans
							: "");
			request.addProperty("pendingChallans",
					"" + pen_challans != null && pen_challans.length() > 0 ? pen_challans : "");
			request.addProperty("regnNo", "" + regn_No != null && regn_No.length() > 0 ? regn_No : "");
			request.addProperty("gtwyCd", "" + gtwy_cd != null && gtwy_cd.length() > 0 ? gtwy_cd : "");
			request.addProperty("unitCode", "" + unit_cd != null && unit_cd.length() > 0 ? unit_cd : "");
			request.addProperty("psCode", "" + ps_code != null && ps_code.length() > 0 ? ps_code : "");
			request.addProperty("psName", "" + ps_name != null && ps_name.length() > 0 ? ps_name : "");
			request.addProperty("pidCd", "" + pid_cd != null && pid_cd.length() > 0 ? pid_cd : "");
			request.addProperty("pidName", "" + pid_name != null && pid_name.length() > 0 ? pid_name : "");
			request.addProperty("totalAmount", "" + total_amnt != null && total_amnt.length() > 0 ? total_amnt : "");
			request.addProperty("detained",
					"" + detained_items != null && detained_items.length() > 0 ? detained_items : "");
			request.addProperty("dOfPay", "" + dateof_pymnt != null && dateof_pymnt.length() > 0 ? dateof_pymnt : "");
			request.addProperty("pmtTime", "" + pymnt_time != null && pymnt_time.length() > 0 ? pymnt_time : "");
			request.addProperty("simId", "" + simid);
			request.addProperty("imeiNo", "" + imei_no);
			request.addProperty("latitude", "" + lat_val);
			request.addProperty("longitude", "" + long_val);
			request.addProperty("pointCode", "" + point_cd != null && point_cd.length() > 0 ? point_cd : "");
			request.addProperty("pointName", "" + point_name != null && point_name.length() > 0 ? point_name : "");
			request.addProperty("onlineMode", "" + onlinemode != null && onlinemode.length() > 0 ? onlinemode : "");
			request.addProperty("moduleCd", "" + module_code != null && module_code.length() > 0 ? module_code : "");
			request.addProperty("releasedDetValues",
					"" + released_detValues != null && released_detValues.length() > 0 ? released_detValues : "");
			request.addProperty("challanNo", "" + challan_num != null && challan_num.length() > 0 ? challan_num : "");
			request.addProperty("serviceCode",
					"" + service_code != null && service_code.length() > 0 ? service_code : "");
			request.addProperty("ownerLicNo", "" + ownr_lic_no != null && ownr_lic_no.length() > 0 ? ownr_lic_no : "");
			request.addProperty("driverLicNo", "" + drvr_lic_no != null && drvr_lic_no.length() > 0 ? drvr_lic_no : "");
			request.addProperty("password", "" + pwd != null && pwd.length() > 0 ? pwd : "");
			request.addProperty("imageEvidence", "" + image_data != null && image_data.length() > 0 ? image_data : "");
			request.addProperty("challanType",
					"" + challan_type != null && challan_type.length() > 0 ? challan_type : "");
			request.addProperty("challanCd",
					"" + challan_code != null && challan_code.length() > 0 ? challan_code : "");
			request.addProperty("location", "" + location != null && location.length() > 0 ? location : "");
			request.addProperty("remarks", "" + remarks != null && remarks.length() > 0 ? remarks : "");
			request.addProperty("pancardNo", "" + pan_num != null && pan_num.length() > 0 ? pan_num : "");
			request.addProperty("aadharNo", "" + aadhar_num != null && aadhar_num.length() > 0 ? aadhar_num : "");
			request.addProperty("voterId", "" + voter_id != null && voter_id.length() > 0 ? voter_id : "");
			request.addProperty("passport", "" + pasprt != null && pasprt.length() > 0 ? pasprt : "");
			request.addProperty("email", "" + mailid != null && mailid.length() > 0 ? mailid : "");
			request.addProperty("driverContactNo", "" + drvr_cntct_num);
			request.addProperty("isItSpotPmt", "" + is_spot);
			request.addProperty("cadreCd", "" + cadre_cd);
			request.addProperty("cadre", "" + cdre);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("URL", "" + MainActivity.URL);
			// HttpTransportSE androidHttpTransport = new
			// HttpTransportSE("http://192.168.11.23:8080/eTicketMobileCyb/services/MobileEticketServiceImpl?wsdl");
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			Object result = envelope.getResponse();

			// spot_final_res_status = result.toString();
			Log.i("VehicleHis) :::::", "" + result.toString());
			try {
				spot_final_res_status = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**spot_f**", "" + spot_final_res_status);

			final_spot_reponse_master = new String[0];// arr[0]-print,arr[1]-display,arr[2]-violations
			final_spot_reponse_violations_master = new String[0];// violations :
			// 01@100@500@description,01@100@500@description
			final_spot_reponse_details = new String[0];// display data

			selected_paid_challans_master = new String[0];
			selected_paid_challans_details = new String[0][0];

			if (spot_final_res_status.toString().trim().equals("0")) {
				if (final_spot_reponse_master.length == 0) {
					final_spot_reponse_master = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations_master = new String[0];
				}

				if (final_spot_reponse_details.length == 0) {
					final_spot_reponse_details = new String[0];
				}

				// HERE IT IS FOR PENDING CHALLANS arr[2]
				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations = new String[0][0];
				}

				// PAID CHALLANS
				if (selected_paid_challans_master.length == 0) {
					selected_paid_challans_master = new String[0];
				}

				if (selected_paid_challans_details.length == 0) {
					selected_paid_challans_details = new String[0][0];
				}

			} else {

				final_spot_reponse_master = spot_final_res_status.split("\\^");

				Log.i("VHCLE HISTRY ", "" + final_spot_reponse_master[0]);

				Log.i("VHCLE HI", "" + final_spot_reponse_master[1]);

				// DISPLAY REPONSE
				if (final_spot_reponse_master[1].toString().trim().length() > 0) {
					final_spot_reponse_details = final_spot_reponse_master[1].split("!");
				}

				if ((!final_spot_reponse_master[2].equals("NA"))) {
					Log.i("VHCLE HISTRY PENNSE", "" + final_spot_reponse_master[2]);
				}

				if (!final_spot_reponse_master[3].equals("NA")) {
					Log.i("VHCLE HISTRY PONSE", "" + final_spot_reponse_master[3]);
				}

				if ((!final_spot_reponse_master[2].toString().trim().equals("NA"))
						&& (final_spot_reponse_master[2].toString().trim().length() > 0)) {

					/*
					 * * THIS SPLITTING IS FOR GETTING RECORDS LIKE THIS EX:
					 * HYD12346556564OCY@100
					 */

					// above pending challans reponse modified like
					// HYD12346556564OCY@DATE@100 : 01-07-2015 by Madhusudhan
					final_spot_reponse_violations_master = final_spot_reponse_master[2].split("!");
					Log.i("fins_master", "" + final_spot_reponse_violations_master.length);

					final_spot_reponse_violations = new String[final_spot_reponse_violations_master.length][3];

					for (int i = 0; i < final_spot_reponse_violations_master.length; i++) {
						final_spot_reponse_violations[i] = final_spot_reponse_violations_master[i].split("\\@");

					}

				}

				// in vechicle history display:PENDING CHALLANS & PAID CHALLANS
				if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					if ((!final_spot_reponse_master[3].toString().trim().equals("NA"))
							&& (final_spot_reponse_master[3].toString().trim().length() > 0)) {

						selected_paid_challans_master = final_spot_reponse_master[3].split("!");

						selected_paid_challans_details = new String[selected_paid_challans_master.length][2];

						for (int j = 0; j < selected_paid_challans_master.length; j++) {
							selected_paid_challans_details[j] = selected_paid_challans_master[j].split("\\@");

						}

					}
				}

			}

		} catch (SoapFault fault) {
			Log.i("******:::", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			Log.i("VHCLE HISTRY EXCPTN", "" + E.getStackTrace().toString());
			if (final_spot_reponse_master == null && final_spot_reponse_master.length == 0) {
				final_spot_reponse_master = new String[0];
			}

			if (final_spot_reponse_violations_master == null && final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations_master = new String[0];
			}

			if (final_spot_reponse_details == null && final_spot_reponse_details.length == 0) {
				final_spot_reponse_details = new String[0];
			}

			// HERE IT IS FOR PENDING CHALLANS arr[2]
			if (final_spot_reponse_violations_master == null && final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations = new String[0][0];
			}

			// PAID CHALLANS
			if (selected_paid_challans_master == null && selected_paid_challans_master.length == 0) {
				selected_paid_challans_master = new String[0];
			}

			if (selected_paid_challans_details == null && selected_paid_challans_details.length == 0) {
				selected_paid_challans_details = new String[0][0];
			}

		}
	}

	public static void releaserDocumentsModule(String vhle_hist_penchallans, String pen_challans, String regn_No,
											   String gtwy_cd, String unit_cd, String ps_code, String ps_name, String pid_cd, String pid_name,
											   String total_amnt, String detained_items, String dateof_pymnt, String pymnt_time, String simid,
											   String imei_no, String lat_val, String long_val, String point_cd, String point_name, String onlinemode,
											   String module_code, String released_detValues, String challan_num, String service_code, String ownr_lic_no,
											   String drvr_lic_no, String pwd, String image_data, String challan_type, String challan_code,
											   String location, String remarks, String pan_num, String aadhar_num, String voter_id, String pasprt,
											   String mailid, String drvr_cntct_num, String is_spot, String cadre_cd, String cdre, String passport,
											   String engNo, String chasisNo) {

		Log.i("****VehicleH***", "Please wait...");
		Log.i("****Vehnt***", "PARAMETERS PASSED START");
		Log.i("****vans***", "" + vhle_hist_penchallans);
		Log.i("****pen_challans***", "" + pen_challans);
		Log.i("**regn_No*****", "" + regn_No);
		Log.i("***gtwy_cd****", "" + gtwy_cd);
		Log.i("**unit_cd*****", "" + unit_cd);
		Log.i("***ps_code****", "" + ps_code);
		Log.i("****ps_name***", "" + ps_name);
		Log.i("****pid_cd***", "" + pid_cd);
		Log.i("****pid_name***", "" + pid_name);
		Log.i("***total_amnt****", "" + total_amnt);
		Log.i("****detained_items***", "" + detained_items);
		Log.i("****dateof_pymnt***", "" + dateof_pymnt);
		Log.i("****pymnt_time***", "" + pymnt_time);
		Log.i("****simid***", "" + simid);
		Log.i("****imei_no***", "" + imei_no);
		Log.i("*****lat_val**", "" + lat_val);
		Log.i("***long_val****", "" + long_val);
		Log.i("***point_cd****", "" + point_cd);
		Log.i("***point_name****", "" + point_name);
		Log.i("*onlinemode******", "" + onlinemode);
		Log.i("***module_code****", "" + module_code);
		Log.i("***re***", "" + released_detValues);
		Log.i("***challan_num****", "" + challan_num);
		Log.i("****service_code***", "" + service_code);
		Log.i("***ownr_lic_no****", "" + ownr_lic_no);
		Log.i("**drvr_lic_no*****", "" + drvr_lic_no);
		Log.i("****pwd***", "" + pwd);
		Log.i("***image_data****", "" + image_data);
		Log.i("***challan_type****", "" + challan_type);
		Log.i("*****challan_code**", "" + challan_code);
		Log.i("*****location**", "" + location);
		Log.i("****remarks***", "" + remarks);
		Log.i("******pan_num*", "" + pan_num);
		Log.i("****aadhar_num***", "" + aadhar_num);
		Log.i("**voter_id*****", "" + voter_id);
		Log.i("*****pasprt**", "" + pasprt);
		Log.i("***mailid****", "" + mailid);
		Log.i("**drvr_cntct_num*****", "" + drvr_cntct_num);
		Log.i("*****is_spot**", "" + is_spot);
		Log.i("*cadre_cd******", "" + cadre_cd);
		Log.i("*****cdre**", "" + cdre);

		Log.i("**passport No ::**", "" + passport);

		Log.i("****Vehint***", "PARAMETERS PASSED ENDED");

		Utils utils = new Utils();
		try {
			SoapObject request = null;

			String passPort_No = SpotChallan.et_passport != null ? SpotChallan.et_passport.getText().toString().trim()
					: "";
			Log.i("PassPort No ::::", "" + passPort_No);

			request = new SoapObject(NAMESPACE, "" + VEHICLE_RELEASE_METHOD_NAME);

			request.addProperty(utils.VHLE_HIST_SELECTED_PEN_CHALLANS, "" + vhle_hist_penchallans);
			request.addProperty(utils.SPOT_PENDING_CHALLANS, "" + pen_challans);
			request.addProperty(utils.SPOT_REG_NO, "" + regn_No);
			request.addProperty(utils.SPOT_GTWY_CD, "" + gtwy_cd);
			request.addProperty(utils.SPOT_UNIT_CODE, "" + unit_cd);
			request.addProperty(utils.SPOT_PS_CODE, "" + ps_code);
			request.addProperty(utils.SPOT_PS_NAME, "" + ps_name);
			request.addProperty(utils.SPOT_PID_CODE, "" + pid_cd);
			request.addProperty(utils.SPOT_PID_NAME, "" + pid_name);
			request.addProperty(utils.SPOT_TOTAL_AMNT, "" + total_amnt);
			request.addProperty(utils.SPOT_DETAINED, "" + detained_items);
			request.addProperty(utils.SPOT_DT_OF_PAY, "" + dateof_pymnt);
			request.addProperty(utils.SPOT_PYMNT_TIME, "" + pymnt_time);
			request.addProperty(utils.SPOT_SIMID, "" + simid);
			request.addProperty(utils.SPOT_IMEI, "" + imei_no);
			request.addProperty(utils.SPOT_LAT, "" + lat_val);
			request.addProperty(utils.SPOT_LONG, "" + long_val);
			request.addProperty(utils.SPOT_POINT_CODE, "" + point_cd);
			request.addProperty(utils.SPOT_POINT_NAME, "" + point_name);
			request.addProperty(utils.SPOT_ONLINE_MODE, "" + onlinemode);
			request.addProperty(utils.SPOT_MODULE_CODE, "" + module_code);
			request.addProperty(utils.VHLE_RELEASED_DET_VALUES, "" + released_detValues);
			request.addProperty(utils.SPOT_CHALLAN_NO, "" + challan_num);
			request.addProperty(utils.SPOT_SERVICE_CODE, "" + service_code);
			request.addProperty(utils.SPOT_OWNER_LIC_NO, "" + ownr_lic_no);
			request.addProperty(utils.VHLE_DRVR_LIC_NO, "" + drvr_lic_no);
			request.addProperty(utils.SPOT_PWD, "" + pwd);
			request.addProperty(utils.SPOT_IMAGE, "" + image_data);
			request.addProperty(utils.SPOT_CHALLAN_TYPE, "" + challan_type);
			request.addProperty(utils.SPOT_CHALLAN_CODE, "" + challan_code);
			request.addProperty(utils.SPOT_LOCATION, "" + location);
			request.addProperty(utils.SPOT_REMARKS, "" + remarks);
			request.addProperty(utils.SPOT_PANCARD_NO, "" + pan_num);
			request.addProperty(utils.SPOT_AADHAR_NO, "" + aadhar_num);
			request.addProperty(utils.SPOT_VOTERID, "" + voter_id);
			request.addProperty(utils.SPOT_PASSPORT, "" + pasprt);
			request.addProperty(utils.SPOT_EMAIL, "" + mailid);
			request.addProperty(utils.SPOT_DRIVER_CNTCT_NO, "" + drvr_cntct_num);
			request.addProperty(utils.SPOT_IS_IT_SPOT, "" + is_spot);
			request.addProperty(utils.VHLE_CADRE_CODE, "" + cadre_cd);
			request.addProperty(utils.SPOT_CADRE, "" + cdre);

			request.addProperty(utils.SPOT_PASSPORT_NO, "" + passPort_No);
			request.addProperty(utils.SPOT_ENGINE, "" + engNo);
			request.addProperty(utils.SPOT_CHASIS, "" + chasisNo);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request ::::", "" + request);
			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// spot_final_res_status = "";
			// spot_final_res_status = result.toString();

			try {
				spot_final_res_status = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**RELEASE D***", "" + spot_final_res_status);

			final_spot_reponse_master = new String[0];// arr[0]-print,arr[1]-display,arr[2]-violations
			final_spot_reponse_violations_master = new String[0];// violations :
			// 01@100@500@description,01@100@500@description
			final_spot_reponse_details = new String[0];// display data

			selected_paid_challans_master = new String[0];
			selected_paid_challans_details = new String[0][0];

			if (spot_final_res_status.toString().trim().equals("0")) {
				if (final_spot_reponse_master.length == 0) {
					final_spot_reponse_master = new String[0];
				}

				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations_master = new String[0];
				}

				if (final_spot_reponse_details.length == 0) {
					final_spot_reponse_details = new String[0];
				}

				/* HERE IT IS FOR PENDING CHALLANS arr[2] */
				if (final_spot_reponse_violations_master.length == 0) {
					final_spot_reponse_violations = new String[0][0];
				}

				/* PAID CHALLANS */
				if (selected_paid_challans_master.length == 0) {
					selected_paid_challans_master = new String[0];
				}

				if (selected_paid_challans_details.length == 0) {
					selected_paid_challans_details = new String[0][0];
				}

			} else {

				final_spot_reponse_master = spot_final_res_status.split("\\^");

				Log.i("VHCLE HE", "" + final_spot_reponse_master[0]);

				Log.i("VHCLE HI", "" + final_spot_reponse_master[1]);

				/* IN VEHICLE HISTORY START */
				/* final_spot_reponse_master[0]--PRINT */
				/* final_spot_reponse_master[1]--DISPLAY */
				/* final_spot_reponse_master[2]--PENING CHALLANS */
				/* final_spot_reponse_master[3]--PAID CHALLANS */
				/* final_spot_reponse_master[4]--DETAINED ITEMS */
				/* final_spot_reponse_master[5]--RELEASED DETAINED ITEMS */
				/* IN VEHICLE HISTORY END */

				/*----------------------------------*/
				/* IN RELEASE DOCUMENTS START */
				/* final_spot_reponse_master[0]--PRINT */
				/* final_spot_reponse_master[1]--DISPLAY */
				/* final_spot_reponse_master[2]--PENING CHALLANS */
				/* final_spot_reponse_master[3]--RELEASED DETAINED ITEMS */
				/* IN RELEASE DOCUMENTS END */

				/* DISPLAY REPONSE */
				if (final_spot_reponse_master[1].toString().trim().length() > 0) {
					final_spot_reponse_details = final_spot_reponse_master[1].split("!");
				}

				if ((final_spot_reponse_master[2] != null) && (!final_spot_reponse_master[2].equals("NA"))) {
					Log.i("VHCLE HISTRYONSE", "" + final_spot_reponse_master[2]);
				}

				if (!final_spot_reponse_master[3].equals("NA")) {
					Log.i("VHCLES REPONSE", "" + final_spot_reponse_master[3]);
				}

				if ((final_spot_reponse_master[2] != null)
						&& (!final_spot_reponse_master[2].toString().trim().equals("NA"))
						&& (final_spot_reponse_master[2].toString().trim().length() > 0)) {

					/*
					 * THIS SPLITTING IS FOR GETTING RECORDS LIKE THIS EX:
					 * HYD12346556564OCY@100
					 */

					// above pending challans reponse modified like
					// HYD12346556564OCY@DATE@100 : 01-07-2015 by Madhusudhan
					final_spot_reponse_violations_master = final_spot_reponse_master[2].split("!");
					Log.i("final_sposter", "" + final_spot_reponse_violations_master.length);

					final_spot_reponse_violations = new String[final_spot_reponse_violations_master.length][3];

					for (int i = 0; i < final_spot_reponse_violations_master.length; i++) {
						final_spot_reponse_violations[i] = final_spot_reponse_violations_master[i].split("\\@");

					}

				}

				/*
				 * in vechicle history display:PENDING CHALLANS & PAID CHALLANS
				 */
				if (Dashboard.check_vhleHistory_or_Spot.equals("vehiclehistory")) {
					if ((final_spot_reponse_master[3] != null)
							&& (!final_spot_reponse_master[3].toString().trim().equals("NA"))
							&& (final_spot_reponse_master[3].toString().trim().length() > 0)) {

						selected_paid_challans_master = final_spot_reponse_master[3].split("!");

						selected_paid_challans_details = new String[selected_paid_challans_master.length][2];

						for (int j = 0; j < selected_paid_challans_master.length; j++) {
							selected_paid_challans_details[j] = selected_paid_challans_master[j].split("\\@");

						}

					}
				}

			}

		} catch (SoapFault fault) {
			Log.i("****S::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception

			Log.i("VHCLE HISTRY EXCPTN", "" + e.getStackTrace().toString());
			if (final_spot_reponse_master == null && final_spot_reponse_master.length == 0) {
				final_spot_reponse_master = new String[0];
			}

			if (final_spot_reponse_violations_master == null && final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations_master = new String[0];
			}

			if (final_spot_reponse_details == null && final_spot_reponse_details.length == 0) {
				final_spot_reponse_details = new String[0];
			}

			// HERE IT IS FOR PENDING CHALLANS arr[2]
			if (final_spot_reponse_violations_master == null && final_spot_reponse_violations_master.length == 0) {
				final_spot_reponse_violations = new String[0][0];
			}

			// PAID CHALLANS
			if (selected_paid_challans_master == null && selected_paid_challans_master.length == 0) {
				selected_paid_challans_master = new String[0];
			}

			if (selected_paid_challans_details == null && selected_paid_challans_details.length == 0) {
				selected_paid_challans_details = new String[0][0];
			}

		}
	}


	public static void getTransactionNo(String all_challans, String imei_No, String email_ID, String contact_No,
										String ter_ID, String bt_ID, String bt_Name, String sim_no, String gps_Latti, String gps_Longi,
										String gps_Date) {

		try {

			SoapObject request = new SoapObject(NAMESPACE, "" + GET_TRANSACTION_NO);
			request.addProperty("challans", "" + all_challans);
			request.addProperty("imei", "" + imei_No);
			request.addProperty("email", "" + email_ID);
			request.addProperty("contactNo", "" + contact_No);
			request.addProperty("terID", "" + ter_ID);
			request.addProperty("btID", "" + bt_ID);
			request.addProperty("btName", "" + bt_Name);
			request.addProperty("simNo", "" + sim_no);
			request.addProperty("gpsLatti", "" + gps_Latti);
			request.addProperty("gpsLongi", "" + gps_Longi);
			request.addProperty("gpsDate", "" + gps_Date);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Toast.makeText(getApplicationContext(), "" + result,
			// Toast.LENGTH_LONG).show();

			// transactionNo_resp = result.toString();

			try {
				transactionNo_resp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Opdata_Chalana = result.toString();
			Log.i("**transactionNo_resp***", "" + Opdata_Chalana);
			if (transactionNo_resp == "0") {

			} else {
				// transactionNo_resp = Opdata_Chalana.replace("|", ":");

				Log.i("transaction", transactionNo_resp);

				/*
				 * MainActivity.arr_logindetails = Opdata_Chalana.split(":");
				 * Log.i("output after replace",
				 * MainActivity.arr_logindetails[0]);
				 */
			}

		} catch (SoapFault fault) {
			Log.i("****SOAP::", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			transactionNo_resp = "0";
			Log.e("Error", "" + E.toString());
		}
	}

	public static void make_OnlinePayment10(String eTicket_Info, String pmt_TrId, String current_date,
											String current_time, String mercntResp_code, String mercntInvoice_no, String mercntRrn,
											String mercntAuth_code, String amt_collectedPid_cd, String amtCollected_pidName, String pmt_unitCd,
											String pmt_unitName, String noOf_PendingChallans, String pending_Amount, String card_Details,
											String pmnt_result, String merchent_id, String merchent_key, String terminal_id, String batchNo,
											String card_holder, String card_type) {

		/*
		 * public String makeOnlinePayment10(String eTicketInfo,String
		 * pmtTrId,String date, String time, String mercnt_resp_code, String
		 * mercnt_invoice_no, String mercnt_Rrn, String mercnt_auth_code, String
		 * amt_collected_pid_cd,String amt_collected_pid_name, String
		 * pmt_unit_cd,String pmt_unit_name,String noOfPendingChallans,String
		 * pendingAmount,String cardDetails ,String pmtResult,String
		 * merchantId,String merchantKey,String terminalId,String batchNo,String
		 * cardHolderName,String cardType);
		 */

		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + MAKE_ONLINE_PAYMENT);
			request.addProperty("eTicketInfo", "" + eTicket_Info);
			request.addProperty("pmtTrId", "" + pmt_TrId);
			request.addProperty("date", "" + current_date);
			request.addProperty("time", "" + current_time);
			request.addProperty("mercnt_resp_code", "" + mercntResp_code);
			request.addProperty("mercnt_invoice_no", "" + mercntInvoice_no);
			request.addProperty("mercnt_Rrn", "" + mercntRrn);
			request.addProperty("mercnt_auth_code", "" + mercntAuth_code);
			request.addProperty("amt_collected_pid_cd", "" + amt_collectedPid_cd);
			request.addProperty("amt_collected_pid_name", "" + amtCollected_pidName);
			request.addProperty("pmt_unit_cd", "" + pmt_unitCd);
			request.addProperty("pmt_unit_name", "" + pmt_unitName);
			request.addProperty("noOfPendingChallans", "" + noOf_PendingChallans);
			request.addProperty("pendingAmount", "" + pending_Amount);
			request.addProperty("cardDetails", "" + card_Details);
			request.addProperty("pmtResult", "" + pmnt_result);
			request.addProperty("merchantId", "" + merchent_id);
			request.addProperty("merchantKey", "" + merchent_key);

			request.addProperty("terminalId", "" + terminal_id);
			request.addProperty("batchNo", "" + batchNo);
			request.addProperty("cardHolderName", "" + card_holder);
			request.addProperty("cardType", "" + card_type);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			Log.v("pmt response :", result.toString());
			makePayment_resp = "";
			// makePayment_resp = result.toString();

			try {
				makePayment_resp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("", "" + makePayment_resp);

		} catch (SoapFault fault) {
			Log.i("****SOAP F:", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			makePayment_resp = "0";
		}
	}

	public static void generateDrunDriveCase_1_5_2(String regn_Cd, String vehicle_No, String regn_No,
												   String operater_Cd, String operater_Name, String pid_Cd, String pid_Name, String ps_Code, String ps_Name,
												   String booked_PsCode, String booked_PsName, String point_Code, String poin_tName, String exact_Location,
												   String offence_Date, String offence_Time, String fined_By, String owner_DLNo, String driver_DLNo,
												   String driver_Name, String father_Name, String aadhar_No, String panCard_No, String passport_No,
												   String voter_Id, String contact_No, String offence_Code, String offence_Amount, String detained_Items,
												   String councelling_Date, String alcohol_Reading, String user_age, String user_occupation,
												   String user_qualification, String user_gendor, String check_SlNo, String wheeler_Cd, String maker_Cd,
												   String vehMain_Category, String vehSub_Category, String user_address, String user_city, String user_unitCd,
												   String user_unitName, String cadre_Cd, String cadre_only, String imgEncoded_String, String device_imei,
												   String gps_Lattitude, String gps_Longitude, String mob_macId, String sim_Id, String breath_AnalyserId,
												   String lcnce_status, String nameOfBar_WineShop, String addressOfBar_WineShop, String occupation_Name,
												   String occupation_Address) {

		Utils utils = new Utils();

		Log.i(utils.REG_CD, "-->" + regn_Cd);
		Log.i(utils.VEH_NO, "-->" + vehicle_No);
		Log.i(utils.REG_NO, "-->" + regn_No);
		Log.i(utils.OPTR_CD, "-->" + operater_Cd);
		Log.i(utils.OPTR_NAME, "-->" + operater_Name);
		Log.i(utils.PID_CD, "-->" + pid_Cd);
		Log.i(utils.PID_NAME, "-->" + pid_Name);
		Log.i(utils.PS_CODE, "-->" + ps_Code);
		Log.i(utils.PS_NAME, "-->" + ps_Name);
		Log.i(utils.BKD_PS_CODE, "-->" + booked_PsCode);
		Log.i(utils.BKD_PS_NAME, "-->" + booked_PsName);
		Log.i(utils.POINT_CODE, "-->" + point_Code);
		Log.i(utils.POINT_NAME, "-->" + poin_tName);
		Log.i(utils.EXCT_LOCATION, "-->" + exact_Location);
		Log.i(utils.OFFNCE_DATE, "-->" + offence_Date);
		Log.i(utils.OFFENCE_TIME, "-->" + offence_Time);
		Log.i(utils.FINED_BY, "-->" + fined_By);
		Log.i(utils.OWNR_DL_NO, "-->" + owner_DLNo);
		Log.i(utils.DRIVER_DL_NO, "-->" + driver_DLNo);
		Log.i(utils.DRIVER_NAME, "-->" + driver_Name);
		Log.i(utils.DRIVER_FNAME, "-->" + father_Name);
		Log.i(utils.AADHAR_NO, "-->" + aadhar_No);
		Log.i(utils.PAN_NO, "-->" + panCard_No);
		Log.i(utils.PSSPRT_NO, "-->" + passport_No);
		Log.i(utils.VOTER_ID, "-->" + voter_Id);
		Log.i(utils.CONTACT_NO, "-->" + contact_No);
		Log.i(utils.OFENCE_CODE, "-->" + offence_Code);
		Log.i(utils.OFENCE_AMNT, "-->");
		Log.i(utils.DETAINED_ITEMS, "-->" + detained_Items);
		Log.i(utils.COUNCELLING_DATE, "-->" + councelling_Date);
		Log.i(utils.ALCHL_READING, "-->" + alcohol_Reading);
		Log.i(utils.AGE, "-->" + user_age);
		Log.i(utils.OCUPTN, "-->" + user_occupation);
		Log.i(utils.QLFCTN, "-->" + user_qualification);
		Log.i(utils.GENDER, "-->" + user_gendor);
		Log.i(utils.CHECK_SI_NO, "-->" + check_SlNo);
		Log.i(utils.WHLR_CODE, "-->" + wheeler_Cd);
		Log.i(utils.MAKER_CODE, "-->" + maker_Cd);
		Log.i(utils.VEH_MAIN_CAT, "-->" + vehMain_Category);
		Log.i(utils.VEH_SUB_CAT, "-->" + vehSub_Category);
		Log.i(utils.ADDRESS, "-->" + user_address);
		Log.i(utils.CITY, "-->" + user_city);
		Log.i(utils.UNIT_CD, "-->" + user_unitCd);
		Log.i(utils.UNIT_NAME, "-->" + user_unitName);
		Log.i(utils.CADRE_CODE, "-->" + cadre_Cd);
		Log.i(utils.CADRE, "-->" + cadre_only);
		Log.i(utils.BASE_64_IMG, "-->" + imgEncoded_String);
		Log.i(utils.MOB_IMEI, "-->" + device_imei);
		Log.i(utils.GPS_LAT, "-->" + gps_Lattitude);
		Log.i(utils.GPS_LOG, "-->" + gps_Longitude);
		Log.i(utils.MAC_ID, "-->" + mob_macId);
		Log.i(utils.SIM_ID, "-->" + sim_Id);
		Log.i(utils.BREATHE_ANYLSR_ID, "-->" + breath_AnalyserId);
		Log.i(utils.LICENCE_STATUS, "-->" + lcnce_status);
		Log.i(utils.DD_NAME_OF_BAR, "-->" + nameOfBar_WineShop);
		Log.i(utils.DD_ADDRESS_OF_BAR, "-->" + addressOfBar_WineShop);
		Log.i(utils.DD_OCCUPATION, "-->" + occupation_Name);
		Log.i(utils.DD_OCCUP_ADDRSS, "-->" + occupation_Address);
		try {
			SoapObject request = new SoapObject(NAMESPACE, "" + GENERATE_DRUNK_DRIVE_CASE1_5_2);

			request.addProperty(utils.REG_CD, "" + regn_Cd);
			request.addProperty(utils.VEH_NO, "" + vehicle_No);
			request.addProperty(utils.REG_NO, "" + regn_No);
			request.addProperty(utils.OPTR_CD, "" + operater_Cd);
			request.addProperty(utils.OPTR_NAME, "" + operater_Name);
			request.addProperty(utils.PID_CD, "" + pid_Cd);
			request.addProperty(utils.PID_NAME, "" + pid_Name);
			request.addProperty(utils.PS_CODE, "" + ps_Code);
			request.addProperty(utils.PS_NAME, "" + ps_Name);
			request.addProperty(utils.BKD_PS_CODE, "" + booked_PsCode);
			request.addProperty(utils.BKD_PS_NAME, "" + booked_PsName);
			request.addProperty(utils.POINT_CODE, "" + point_Code);
			request.addProperty(utils.POINT_NAME, "" + poin_tName);
			request.addProperty(utils.EXCT_LOCATION, "" + exact_Location);
			request.addProperty(utils.OFFNCE_DATE, "" + offence_Date);
			request.addProperty(utils.OFFENCE_TIME, "" + offence_Time);
			request.addProperty(utils.FINED_BY, "" + fined_By);
			request.addProperty(utils.OWNR_DL_NO, "" + owner_DLNo);
			request.addProperty(utils.DRIVER_DL_NO, "" + driver_DLNo);
			request.addProperty(utils.DRIVER_NAME, "" + driver_Name);
			request.addProperty(utils.DRIVER_FNAME, "" + father_Name);
			request.addProperty(utils.AADHAR_NO, "" + aadhar_No);
			request.addProperty(utils.PAN_NO, "" + panCard_No);
			request.addProperty(utils.PSSPRT_NO, "" + passport_No);
			request.addProperty(utils.VOTER_ID, "" + voter_Id);
			request.addProperty(utils.CONTACT_NO, "" + contact_No);
			request.addProperty(utils.OFENCE_CODE, "" + offence_Code);
			request.addProperty(utils.OFENCE_AMNT, "");
			request.addProperty(utils.DETAINED_ITEMS, "" + detained_Items);
			request.addProperty(utils.COUNCELLING_DATE, "" + councelling_Date);
			request.addProperty(utils.ALCHL_READING, "" + alcohol_Reading);
			request.addProperty(utils.AGE, "" + user_age);
			request.addProperty(utils.OCUPTN, "" + user_occupation);
			request.addProperty(utils.QLFCTN, "" + user_qualification);
			request.addProperty(utils.GENDER, "" + user_gendor);
			request.addProperty(utils.CHECK_SI_NO, "" + check_SlNo);
			request.addProperty(utils.WHLR_CODE, "" + wheeler_Cd);
			request.addProperty(utils.MAKER_CODE, "" + maker_Cd);
			request.addProperty(utils.VEH_MAIN_CAT, "" + vehMain_Category);
			request.addProperty(utils.VEH_SUB_CAT, "" + vehSub_Category);
			request.addProperty(utils.ADDRESS, "" + user_address);
			request.addProperty(utils.CITY, "" + user_city);
			request.addProperty(utils.UNIT_CD, "" + user_unitCd);
			request.addProperty(utils.UNIT_NAME, "" + user_unitName);
			request.addProperty(utils.CADRE_CODE, "" + cadre_Cd);
			request.addProperty(utils.CADRE, "" + cadre_only);
			request.addProperty(utils.BASE_64_IMG, "" + imgEncoded_String);
			request.addProperty(utils.MOB_IMEI, "" + device_imei);
			request.addProperty(utils.GPS_LAT, "" + gps_Lattitude);
			request.addProperty(utils.GPS_LOG, "" + gps_Longitude);
			request.addProperty(utils.MAC_ID, "" + mob_macId);
			request.addProperty(utils.SIM_ID, "" + sim_Id);
			request.addProperty(utils.BREATHE_ANYLSR_ID, "" + breath_AnalyserId);
			request.addProperty(utils.LICENCE_STATUS, "" + lcnce_status);

			request.addProperty(utils.DD_NAME_OF_BAR, "" + nameOfBar_WineShop);
			request.addProperty(utils.DD_ADDRESS_OF_BAR, "" + addressOfBar_WineShop);
			request.addProperty(utils.DD_OCCUPATION, "" + occupation_Name);
			request.addProperty(utils.DD_OCCUP_ADDRSS, "" + occupation_Address);
			// request.addProperty(utils.DD_OCCUP_MAIL, "" +
			// occupation_EmailId);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();

			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("**generat*", "" + Opdata_Chalana);
			if (Opdata_Chalana.toString().trim().equals("0")) {
				Opdata_Chalana = "0";
				final_reponse_split = new String[0];
				final_response_master = new String[0];

				GenerateDrunkDriveCase.otpStatus = null;

			} else if (Opdata_Chalana.toString().trim().equals("1")) {
				Opdata_Chalana = "1";
				final_reponse_split = new String[0];
				final_response_master = new String[0];
				GenerateDrunkDriveCase.otpStatus = null;
			} else {
				final_response_master = new String[0];
				// final_response_master = Opdata_Chalana.split("!");
				final_reponse_split = new String[0];// TO STORE SPLETTED DATA

				final_reponse_split = Opdata_Chalana.split("@");

				if (final_reponse_split[1].toString().trim().length() > 0) {
					final_response_master = (final_reponse_split[1].split("!"));
				}

			}

		} catch (SoapFault fault) {
			Log.i("***::", "soapfault = " + fault.getMessage());

		} catch (Exception e) {
			// TODO: handle exception
			if (final_reponse_split.length == 0) {
				final_reponse_split = new String[0];
			}

			if (final_response_master.length == 0) {
				final_response_master = new String[0];
			}

			if ((final_reponse_split.length == 0) && (final_response_master.length == 0)) {
				Opdata_Chalana = "0";
			}

		}
	}


	public static void getChange_PWDotp(String pid_cd, String security_Code, String contact_No) {

		try {

			SoapObject request = new SoapObject(NAMESPACE, "getChangePWDotp");
			request.addProperty("pidcd", pid_cd);
			request.addProperty("securityCode", security_Code);
			request.addProperty("contactNo", contact_No);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("sendOTPtoMobile URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// changePswd_otp = result.toString();
			Log.i("otp ense :", result.toString());
			try {
				changePswd_otp = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
				Settings.changepwdotpresp = changePswd_otp;

				Log.i("otsponse :", changePswd_otp);
				;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Log.i("**changePswd_otp RESULT***", "" + changePswd_otp);

		} catch (SoapFault fault) {
			Log.i("****SOAP::", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			changePswd_otp = "NA";
			Log.e("Error", "" + E.toString());
		}
	}

	// public String updateChangePWD(String pidcd,String securityCode,String
	// contactNo,String otp,String newPwd);
	public static void updateChange_PWD(String pid_cd, String security_Code, String contact_No, String otpflg,
										String new_Pwd) {
		try {

			SoapObject request = new SoapObject(NAMESPACE, "updateChangePWD");
			request.addProperty("pidcd", pid_cd);
			request.addProperty("oldPwd", security_Code);
			request.addProperty("contactNo", contact_No);
			request.addProperty("otpFlag", otpflg);// if otp match otpFlag Y / N
			request.addProperty("newPwd", new_Pwd);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			Log.i("request", "" + request);
			Log.i("sendOTPtoMobile URL", "" + MainActivity.URL);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(MainActivity.URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();

			changePSWDconfirm = result.toString();
			try {
				changePSWDconfirm = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(changePSWDconfirm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("**changePSWDco*", "" + changePSWDconfirm);

		} catch (SoapFault fault) {
			Log.i("****SOAP*:::", "soapfault = " + fault.getMessage());

		} catch (Exception E) {
			E.printStackTrace();
			changePSWDconfirm = "NA";
			Log.e("Error", "" + E.toString());
		}
	}

	public static void getPointNameByPsNames(String ps_code) {
		try {
			Log.i("***getPointName", "" + ps_code);
			SoapObject request = new SoapObject(NAMESPACE, "" + GET_POINTNAME_BY_PSNAME_MEHOD_NAME);
			request.addProperty("psCode", "" + ps_code);
			Log.i("Point::", "" + request);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransportSE = new HttpTransportSE(MainActivity.URL);
			httpTransportSE.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			// Opdata_Chalana = "";
			// Opdata_Chalana = result.toString();
			try {
				Opdata_Chalana = new com.example.mtpv.eticketcourt.service.PidSecEncrypt().decrypt(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("***PointNa**", "" + Opdata_Chalana);
			if (Opdata_Chalana == null) {
				Log.i("D", "NO POINT_NAMES_DATA_FOUND");
			} else {
				PointNamesBypsNames_master = new String[0];
				PointNamesBypsNames_master = Opdata_Chalana.split("!");
				for (int i = 0; i < ServiceHelper.PointNamesBypsNames_master.length; i++) {
					Log.i("", "" + ServiceHelper.PointNamesBypsNames_master[i]);
				}
			}
		} catch (SoapFault fault) {
			Log.i("****get****:::", "soapfault = " + fault.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			PointNamesBypsNames_master = new String[0];
		}
	}

}