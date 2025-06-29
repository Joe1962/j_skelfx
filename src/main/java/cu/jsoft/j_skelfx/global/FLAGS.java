/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.jsoft.j_skelfx.global;

/**
 *
 * @author joe1962
 */
public class FLAGS extends cu.jsoft.j_utilsfxlite.global.FLAGS {

	// DB FLAGS:
	private static boolean DBCONNECTED;
	private static boolean DBGLOADED;						// Global recordsets were loaded...
	private static boolean DBCHANGED;

	private static boolean LOGGEDIN;

	// ERROR FLAGS:
	private static boolean ERR_NO_DB = false;
	private static boolean ERR_DB_CONN = false;
	private static boolean ERR_NO_POS = false;

	// Config FLAGS:
	private static boolean WAITCONFIG;
	private static boolean TOUCHSCREEN = false;
	private static boolean WEBSERVICE = false;
	private static boolean USE_SERVER = false;
	private static boolean PARANOID = true;



	public static boolean isDBCONNECTED() {
		return DBCONNECTED;
	}

	public static void setDBCONNECTED(boolean aDBCONNECTED) {
		DBCONNECTED = aDBCONNECTED;
	}

	public static boolean isDBGLOADED() {
		return DBGLOADED;
	}

	public static void setDBGLOADED(boolean aDBGLOADED) {
		DBGLOADED = aDBGLOADED;
	}

	public static boolean isDBCHANGED() {
		return DBCHANGED;
	}

	public static void setDBCHANGED(boolean aDBCHANGED) {
		DBCHANGED = aDBCHANGED;
	}

	public static boolean isERR_NO_DB() {
		return ERR_NO_DB;
	}

	public static void setERR_NO_DB(boolean aFLAG_NO_DB_ERR) {
		ERR_NO_DB = aFLAG_NO_DB_ERR;
	}

	public static boolean isERR_DB_CONN() {
		return ERR_DB_CONN;
	}

	public static void setERR_DB_CONN(boolean aFLAG_DB_CONN_ERR) {
		ERR_DB_CONN = aFLAG_DB_CONN_ERR;
	}

	/**
	 * @return the LOGGEDIN
	 */
	public static boolean isLOGGEDIN() {
		return LOGGEDIN;
	}

	/**
	 * @param aLOGGEDIN the LOGGEDIN to set
	 */
	public static void setLOGGEDIN(boolean aLOGGEDIN) {
		LOGGEDIN = aLOGGEDIN;
	}

	public static boolean isERR_NO_POS() {
		return ERR_NO_POS;
	}

	public static void setERR_NO_POS(boolean aFLAG_NO_POS) {
		ERR_NO_POS = aFLAG_NO_POS;
	}

	public static boolean isWAITCONFIG() {
		return WAITCONFIG;
	}

	public static void setWAITCONFIG(boolean aWAITCONFIG) {
		WAITCONFIG = aWAITCONFIG;
	}

	public static boolean isTOUCHSCREEN() {
		return TOUCHSCREEN;
	}

	public static void setTOUCHSCREEN(boolean aTOUCHSCREEN) {
		TOUCHSCREEN = aTOUCHSCREEN;
	}

	public static boolean isWEBSERVICE() {
		return WEBSERVICE;
	}

	public static void setWEBSERVICE(boolean aWEBSERVICE) {
		WEBSERVICE = aWEBSERVICE;
	}

	public static boolean isUSE_SERVER() {
		return USE_SERVER;
	}

	public static void setUSE_SERVER(boolean aUSE_SERVER) {
		USE_SERVER = aUSE_SERVER;
	}

	public static boolean isPARANOID() {
		return PARANOID;
	}

	public static void setPARANOID(boolean aPARANOID) {
		PARANOID = aPARANOID;
	}

}
