/*
 * Copyright Joe1962
 */
package cu.jsoft.j_skelfx.global;

/**
 *
 * @author joe1962
 */
public class RunTime {
	// Only @ compile time:
	public static boolean IsAdmin = false;

	// Changeable by command line parameters:
	public static boolean DebugMode = false; // backport needed by RS_currency_denominations...
	
	// Permissions for main buttons:
	public static boolean AllowConfig = true;
	public static boolean AllowDB = true;
	public static boolean AllowAnimals = true;
	public static boolean AllowFeeds = true;
	public static boolean AllowNorms = true;
	public static boolean AllowOrders = true;
	public static boolean AllowStock = true;
	public static boolean AllowDeliveries = true;
	public static boolean Allowreports = true;
	public static boolean AllowDataMining = true;
	


	public static RunTime getInstance() {
		return RunTimeHolder.INSTANCE;
	}

	private RunTime() {
	}

	private static class RunTimeHolder {

		private static final RunTime INSTANCE = new RunTime();
	}
}
