package com.example.mtpv.eticketcourt.service;

import java.sql.SQLException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {

    Context context;
    Databasehelpers myDatabase;
    public static final String DATABASE_NAME = "MobileeTicket";
    final int DATABASE_VERSION = 1;
    public static SQLiteDatabase db;
    public static ArrayList<String> crtnmaes;
    /* VARIABLE NAMES */

    // wheeler code
    public static String wheeler_code = "WHEELER_CODE";
    public static String wheeler_name = "WHEELER_NAME";

    // occupation
    public static String occuptn_code = "OCCUPATION_CODE";
    public static String occuptn_name = "OCCUPATION_NAME";

    // Bar
    public static String bar_code = "BAR_CODE";
    public static String bar_name = "BAR_NAME";

    // qualification
    public static String qlfctn_code = "QLFCTN_CODE";
    public static String qlfctn_name = "QLFCTN_NAME";

    // vehicle category
    public static String vhcle_cat_code = "VHCLE_CAT_CODE";
    public static String vhcle_cat_name = "VHCLE_CAT_NAME";

    // vehicle main category
    public static String vhcle_maincat_code = "VHCLE_MAINCAT_CODE";
    public static String vhcle_maincat_name = "VHCLE_MAINCAT_NAME";

    // PS NAMES
    public static String ps_code_settings = "PS_CODE";
    public static String ps_name_settings = "PS_NAME";

    public static String court_code_settings = "COURT_CODE";
    public static String court_name_settings = "COURT_NAME";
    public static String court_address_settings = "COURT_ADDRESS";

    public static String court_dis_code_settings = "COURT_DIS_CODE";
    public static String court_dis_name_settings = "COURT_DIS_NAME";


    // VIOLATION POINTS DETAILS
    public static String offenceCode = "OFFENCE_CD";
    public static String wheelerCode = "WHEELER_CD";
    public static String penalityPoints = "PENALITY_POINTS";

    // OCCUPATION
    public static String occupation_desc = "OCCUPATION_DESC";
    public static String occupation_name = "OCCUPATION_NAME";

    // DUPLICATE PRINT
    public static String dup_print_respnse = "DUP_PRINT_RES";
    public static String dup_print_app_type = "DUP_PRINT_APP_TYPE";

    /* VIOLATION CATEGORY */
    public static String vltn_wheeler_code = "VLTN_WHEELER_CODE";
    public static String vltn_offence_code = "VLTN_OFFENCE_CODE";
    public static String vltn_section = "VLTN_SECTION";
    public static String vltn_violation_desc = "VLTN_VIOLATION_DESC";
    public static String vltn_min_fine = "VLTN_MINI_FINE";
    public static String vltn_max_fine = "VLTN_MAX_FINE";
    public static String vltn_display = "VLTN_DISPLAY";

    /* TABLE NAMES */
    public static String wheelercode_table = "WHEELER_CODE_TABLE";
    public static String occupation_table = "OCCUPATION_TABLE";
    public static String qualification_table = "QUALIFICATION_TABLE";
    public static String vehicleCat_table = "VEHICLE_CAT_TABLE";
    public static String vehicleMainCat_table = "VEHICLE_MAINCAT_TABLE";
    public static String psName_table = "PSNAME_TABLE";
    public static String courtName_table = "COURTNMAE_TABLE";
    public static String court_disName_table = "COURTDISNMAE_TABLE";
    public static String violationPointsTable = "VIOLATION_POINT_TABLE";//
    public static String duplicatePrint_table = "DUPLICATE_PRINT_TABLE";
    public static String vioalation_table = "VIOLATION_TABLE";
    public static String bar_table = "BAR_TABLE";
    public static String TERMINAL_DETAILS_TABLE = "TERMINAL_DETAILS_TABLE";
    public static String TERMINAL_ID = "TERMINAL_ID";
    public static String BT_NAME = "BT_NAME";
    public static String BT_ADDRESS = "BT_ADDRESS";
    public static String Occupation_table = "OCCUPATION_TABLE";

    /* TABLE CREATIONS QUERIES */
    public static String wheelerCodeCreation = "create table " + wheelercode_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + wheeler_code + " varchar2(20), " + wheeler_name
            + " varchar2(40));";

    public static String occupationCreation = "create table " + occupation_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + occuptn_code + " varchar2(20), " + occuptn_name
            + " varchar2(40));";

    public static String barTableCreation = "create table " + bar_table + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , "
            + bar_code + " varchar2(20), " + bar_name + " varchar2(40)) ";

    public static String termailDetailsCreation = "CREATE TABLE IF NOT EXISTS  " + TERMINAL_DETAILS_TABLE
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + TERMINAL_ID + " varchar2(40), " + BT_NAME
            + " varchar2(40), " + BT_ADDRESS + " varchar2(40)) ";

    static String qualificationCreation = "create table " + qualification_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + qlfctn_code + " varchar2(20), " + qlfctn_name
            + " varchar2(40));";

    static String vehicleCatCreation = "create table " + vehicleCat_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + vhcle_cat_code + " varchar2(20), " + vhcle_cat_name
            + " varchar2(40));";

    static String vehicleMainCatCreation = "create table " + vehicleMainCat_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + vhcle_maincat_code + " varchar2(20), "
            + vhcle_maincat_name + " varchar2(40));";

    public static String psNamesCreation = "create table " + psName_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + ps_code_settings + " varchar2(20), " + ps_name_settings
            + " varchar2(40));";

    public static String courtNamesCreation = "create table " + courtName_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + court_code_settings + " varchar2(20), " + court_name_settings
            + " varchar2(40)," + court_address_settings + " varchar2(50));";
    public static String court_disNamesCreation = "create table " + court_disName_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + court_dis_code_settings + " varchar2(20), " + court_dis_name_settings
            + " varchar2(40));";

    public static String violatioPointsDetaisCreation = "create table " + violationPointsTable
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + offenceCode + " varchar2(20), " + wheelerCode
            + " varchar2(20), " + penalityPoints + " varchar2(20));";

    static String duplicatePrintCreation = "create table " + duplicatePrint_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + dup_print_respnse + " varchar2(5000), "
            + dup_print_app_type + " varchar2(30));";

    /* VIOLATION CODE */
    static String violationsCreation = "create table " + vioalation_table
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + vltn_wheeler_code + " varchar2(20), " + vltn_offence_code
            + " varchar2(10), " + vltn_section + " varchar2(40), " + vltn_violation_desc + " varchar2(50), "
            + vltn_min_fine + " varchar2(40), " + vltn_max_fine + " varchar2(40));";

    public DBHelper(Context context) {
        this.context = context;
        myDatabase = new Databasehelpers(context);
    }

    public static String USER_TABLE = "USER_TABLE";
    public static String COLUMN_ID = "COLUMN_ID";
    public static String PIDCODE = "PIDCODE";
    public static String PASSWORD = "PASSWORD";
    public static String PIDNAME = "PIDNAME";
    // UserData
    public static String Pid_code = "PID_CODE";
    public static String Pid_pswrd = "PID_PSWRD";
    public static String PID_NAME = "PID_NAME";
    public static String PS_CODE = "PS_CODE";
    public static String PS_NAME = "PS_NAME";
    public static String CADRE_CODE = "CADRE_CODE";
    public static String CADRE_NAME = "CADRE_NAME";
    public static String UNIT_CODE = "UNIT_CODE";
    public static String UNIT_NAME = "UNIT_NAME";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + PIDCODE + " varchar2(30), " + PASSWORD + " varchar2(10), "
            + PIDNAME + " varchar(30)," + PID_NAME + " varchar2(30), " + PS_CODE + " varchar2(30)," + PS_NAME
            + " varchar2(30), " + CADRE_CODE + " varchar2(30)," + CADRE_NAME + " varchar2(30), " + UNIT_CODE
            + " varchar2(30),  " + UNIT_NAME + " varchar2(30));";

    public static String PINPAD_TABLE = "PINPAD_TABLE";
    public static String PINPAD_ID = "PINPAD_ID";
    public static String PINPAD_NAME = "PINPAD_NAME";

    public static final String CREATE_PINPAD_TABLE = "CREATE TABLE IF NOT EXISTS " + PINPAD_TABLE
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + PINPAD_ID + " varchar2(30), " + PINPAD_NAME
            + " varchar2(30));";

    public static final String BT_PRINTER_TABLE = "BT_PRINTER_TABLE";
    public static final String PRINTER_NAME = "PRINTER_NAME";
    public static final String PRINTER_ADDRESS = "PRINTER_ADDRESS";

    public static final String CREATE_BT_PRINTER = "CREATE TABLE IF NOT EXISTS " + BT_PRINTER_TABLE
            + " ( SNO INTEGER PRIMARY KEY AUTOINCREMENT , " + PRINTER_ADDRESS + " varchar2(30), " + PRINTER_NAME
            + " varchar2(30));";

    class Databasehelpers extends SQLiteOpenHelper {

        public Databasehelpers(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL(wheelerCodeCreation);
            Log.i("wheelerCodeCion Table", "Created");

            db.execSQL(occupationCreation);
            Log.i("occupationtion Table", "Created");

            db.execSQL(barTableCreation);
            Log.i("barTableCreation Table", "Created");

            db.execSQL(qualificationCreation);
            Log.i("qualificationion Table", "Created");

            db.execSQL(vehicleCatCreation);
            Log.i("vehicleCatCreon Table", "Created");

            db.execSQL(vehicleMainCatCreation);
            Log.i("vehicleMainCatCtn Table", "Created");

            db.execSQL(psNamesCreation);
            Log.i("psNamesCreation Table", "Created");

            db.execSQL(courtNamesCreation);
            Log.i("courtNamesion Table", "Created");

            db.execSQL(court_disNamesCreation);
            Log.i("courtdisNamesion Table", "Created");


            db.execSQL(violatioPointsDetaisCreation);
            Log.i("violatioPointion Table", "Created");

            db.execSQL(duplicatePrintCreation);
            Log.i("duplicatePrinton Table", "Created");

            db.execSQL(violationsCreation);
            Log.i("violationsCron Table", "Created");

            db.execSQL(termailDetailsCreation);
            Log.i("termailDetail Table", "Created");
        }

		/*
		 * @Override public void onUpgrade(SQLiteDatabase db, int oldVersion,
		 * int newVersion) { // TODO Auto-generated method stub
		 * //db.execSQL("DROP TABLE IF EXISTS " + bar_table);
		 * db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME); onCreate(db); }
		 */

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + termailDetailsCreation);
            db.execSQL("DROP TABLE IF EXISTS " + barTableCreation);
            db.execSQL("DROP TABLE IF EXISTS " + occupation_table);
            db.execSQL("DROP TABLE IF EXISTS " + wheelercode_table);
            db.execSQL("DROP TABLE IF EXISTS " + qualification_table);
            db.execSQL("DROP TABLE IF EXISTS " + vehicleCat_table);
            db.execSQL("DROP TABLE IF EXISTS " + vehicleMainCat_table);
            db.execSQL("DROP TABLE IF EXISTS " + psName_table);
            db.execSQL("DROP TABLE IF EXISTS " + courtName_table);
            db.execSQL("DROP TABLE IF EXISTS " + court_disName_table);
            db.execSQL("DROP TABLE IF EXISTS " + violationPointsTable);
            db.execSQL("DROP TABLE IF EXISTS " + duplicatePrint_table);
            db.execSQL("DROP TABLE IF EXISTS " + vioalation_table);
            onCreate(db);

        }
    }

    public DBHelper open() throws SQLException {
        db = myDatabase.getWritableDatabase();
        return this;
    }

    public void close() {
        myDatabase.close();
    }

    public static void insertWheelerDetails(String w_code, String w_name) {
        ContentValues con = new ContentValues();
        con.put(wheeler_code, w_code);
        con.put(wheeler_name, w_name);
        db.insert(wheelercode_table, null, con);
    }

    public static void insertOccupation(String oc_code, String oc_name) {
        ContentValues con = new ContentValues();
        con.put(occuptn_code, oc_code);
        con.put(occuptn_name, oc_name);
        db.insert(occupation_table, null, con);
    }

    public static void insertPinPadBT(String pinpad_ID, String pinpad_name) {
        ContentValues con = new ContentValues();
        con.put(PINPAD_ID, pinpad_ID);
        con.put(PINPAD_NAME, pinpad_name);
        db.insert(PINPAD_TABLE, null, con);
    }

    public static void insertBTprintet(String BT_ID, String bT_name) {
        ContentValues con = new ContentValues();
        con.put(PRINTER_ADDRESS, BT_ID);
        con.put(PRINTER_NAME, bT_name);
        db.insert(BT_PRINTER_TABLE, null, con);
    }

    public static void insertBarDetails(String barCode, String barName) {
        ContentValues con = new ContentValues();
        con.put(bar_code, barCode);
        con.put(bar_name, barName);
        // Log.i("Bar Table ::::::",
        // ""+DBHelper.bar_table.trim().toUpperCase());
        db.insert(DBHelper.bar_table.trim().toUpperCase(), null, con);
    }

    public static void insertTerminalDetails(String term_id, String bTname, String bTaddress) {
        ContentValues con = new ContentValues();
        con.put(TERMINAL_ID, term_id);
        con.put(BT_NAME, bTname);
        con.put(BT_ADDRESS, bTaddress);
        // Log.i("Bar Table ::::::",
        // ""+DBHelper.bar_table.trim().toUpperCase());
        db.insert(DBHelper.TERMINAL_DETAILS_TABLE.trim(), null, con);
    }

    public static void insertQualificationDetails(String q_code, String q_name) {
        ContentValues con = new ContentValues();
        con.put(qlfctn_code, q_code);
        con.put(qlfctn_name, q_name);
        db.insert(qualification_table, null, con);
    }

    public static void insertVehicleCatDetails(String v_code, String c_name) {
        ContentValues con = new ContentValues();
        con.put(vhcle_cat_code, v_code);
        con.put(vhcle_cat_name, c_name);
        db.insert(vehicleCat_table, null, con);
    }

    public static void insertVehicleMainCatDetails(String vmain_code, String vmainc_name) {
        ContentValues con = new ContentValues();
        con.put(vhcle_maincat_code, vmain_code);
        con.put(vhcle_maincat_name, vmainc_name);
        db.insert(vehicleMainCat_table, null, con);
    }

    public static void insertPsNameDetails(String ps_code, String ps_name) {
        ContentValues con = new ContentValues();
        con.put(ps_code_settings, ps_code);
        con.put(ps_name_settings, ps_name);
        db.insert(psName_table, null, con);
    }

    public static void insertCourtNameDetails(String court_code, String court_name,String court_address) {
        ContentValues con = new ContentValues();
        con.put(court_code_settings, court_code);
        con.put(court_name_settings, court_name);
        con.put(court_address_settings,court_address);
        db.insert(courtName_table, null, con);
    }

    public static void insertCourtDisNameDetails(String court_dis_code, String court_dis_name) {
        ContentValues con = new ContentValues();
        con.put(court_dis_code_settings, court_dis_code);
        con.put(court_dis_name_settings, court_dis_name);
        db.insert(court_disName_table, null, con);
    }

    public static void insertViolationPointDetails(String offence_code, String wheeler_code, String penality_points) {
        ContentValues con = new ContentValues();
        con.put(offenceCode, offence_code);
        con.put(wheelerCode, wheeler_code);
        con.put(penalityPoints, penality_points);
        db.insert(violationPointsTable, null, con);
    }

	/*
	 * public static void insertOccupationDetails(String occup_code, String
	 * occup_desc) { ContentValues con = new ContentValues();
	 * con.put(occupation_name, occup_code); con.put(occupation_desc,
	 * occup_desc); db.insert(occupation_table, null, con); }
	 */

    public static void insertDuplicatePrintDetails(String dup_res, String app_type) {
        ContentValues con = new ContentValues();
        con.put(dup_print_respnse, dup_res);
        con.put(dup_print_app_type, app_type);
        db.insert(duplicatePrint_table, null, con);

    }

    public static void Delete_ReadingData(String tblename) {
        db.execSQL("delete from " + tblename);

    }



    public static void deleteDuplicateRecords(String tablename, String apptype) {
        Log.i("**DB_HELPER**", "started deleting from");
        Log.i("**DB_HELPER**", "tablename : " + tablename + "\napptype : " + apptype);
        Log.i("**DB_HELPER**", "Successfully deleted");
        db.execSQL("delete from " + tablename + " WHERE " + dup_print_app_type + " = " + "'" + apptype + "'");
    }

    public static void insertViolationDetails(String whlr_code, String ofnce_cde, String sec, String vltn_desc,
                                              String min_fine, String mx_fine) {
        ContentValues con = new ContentValues();
        con.put(vltn_wheeler_code, whlr_code);
        con.put(vltn_offence_code, ofnce_cde);
        con.put(vltn_section, sec);
        con.put(vltn_violation_desc, vltn_desc);
        con.put(vltn_min_fine, min_fine);
        con.put(vltn_max_fine, mx_fine);
        db.insert(vioalation_table, null, con);

    }

    public void execSQL(String string) {
        // TODO Auto-generated method stub

    }

    public Cursor rawQuery(String query, Object object) {
        // TODO Auto-generated method stub
        return null;
    }

}
