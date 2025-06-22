/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.cenpalab.siguapa.preferences;

import cu.cenpalab.siguapa.AppInfo;
import cu.cenpalab.siguapa.global.FLAGS;
import cu.cenpalab.siguapa.global.GLOBAL;
import static cu.cenpalab.siguapa.global.GLOBAL.WINDOWGEOM;
import cu.cenpalab.siguapa.preferences.db.TYP_ConfigDBJSON;
import cu.jsoft.j_utilsfxlite.interfaces.SettingsHandlerJSON;
import cu.jsoft.j_utilsfxlite.preferences.SUB_SettingsHandlerJSON;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Servicell
 */
public class CLS_Preferences {
	private static SettingsHandlerJSON MyConfig = new SUB_SettingsHandlerJSON();



	private static Path getConfigPath() {
		return MyConfig.getDefaultConfigPathJSON(AppInfo.getPREFNODE());
	}

	private static boolean checkForConfigJSON() throws IOException {
		if (!getConfigPath().toFile().canWrite()) {
			if (!createDefaultConfigJSON()) {
				// TODO: popup some error and exit...
				System.exit(12);
			}
		}
		GLOBAL.setStyle(GLOBAL.defaultStyle);
		return true;
	}

	private static boolean createDefaultConfigJSON() throws IOException {
		MyConfig.setValue("GENERAL","DefaultDB", 0);
		MyConfig.setValue("GENERAL","Beep", false);
		MyConfig.setValue("GENERAL","LastUser", "");
		MyConfig.setValue("WINDOW","Style", GLOBAL.defaultStyle);
		MyConfig.setValue("APP", "Reg", "");

		MyConfig.save(getConfigPath());
		return true;
	}

	public static void loadConfigJSON() throws IOException {
		checkForConfigJSON();
		MyConfig.load(getConfigPath());

		GLOBAL.DBDefaultDB = MyConfig.getValue("GENERAL","DefaultDB", Integer.class, 0);
		FLAGS.setBEEP(MyConfig.getValue("GENERAL","Beep", Boolean.class, false));
		GLOBAL.LastUser = MyConfig.getValue("GENERAL","LastUser", String.class, "");
		WINDOWGEOM.setWindowLeft(MyConfig.getValue("WINDOW","Left", Double.class, (double) 0));
		WINDOWGEOM.setWindowTop(MyConfig.getValue("WINDOW","Top", Double.class, (double) 0));
		WINDOWGEOM.setWindowWidth(MyConfig.getValue("WINDOW","Width", Double.class, (double) 1024));
		WINDOWGEOM.setWindowHeight(MyConfig.getValue("WINDOW","Height", Double.class, (double) 768));
		WINDOWGEOM.setWindowMaximized(MyConfig.getValue("WINDOW","Maximized", Boolean.class, true));
		GLOBAL.selectedStyle = MyConfig.getValue("WINDOW","Style", String.class, "");
		//GLOBAL.REGISTRATION = MyConfig.getValue("APP", "Reg", String.class, "");

		LoadConfigDBsJSON();
	}

	public static void LoadConfigDBsJSON() {
		// Clear GLOBAL DB preferences list:
		GLOBAL.DBCONFIG.clear();

		List<TYP_ConfigDBJSON> dbConfigs = MyConfig.getArray("DBs", TYP_ConfigDBJSON.class);

		if (!dbConfigs.isEmpty()) {
			for (TYP_ConfigDBJSON dbConfig : dbConfigs) {
				LoadConfigDBJSON(dbConfig);
			}
		} else {
			// Set FLAG for future notification of no DB configured:
			FLAGS.setERR_NO_DB(true);
		}
	}

	private static void LoadConfigDBJSON(TYP_ConfigDBJSON MyDBConfig) {
		String DBNICK = MyDBConfig.getDBNICK();
		String DBDRIVER = MyDBConfig.getDBDRIVER();
		String DBTYPE = MyDBConfig.getDBTYPE();
		String DBPATH = MyDBConfig.getDBPATH();
		String DBHOST = MyDBConfig.getDBHOST();
		int DBPORT = MyDBConfig.getDBPORT();
		String DBNAME = MyDBConfig.getDBNAME();
		String DBUSER = MyDBConfig.getDBUSER();
		String DBPASS = MyDBConfig.getDBPASS();

		GLOBAL.DBCONFIG.add(new TYP_ConfigDBJSON(DBNICK, DBDRIVER, DBTYPE, DBPATH, DBHOST, DBPORT, DBNAME, DBUSER, DBPASS));
	}

	public static void saveConfigJSON() {
		// TODO: first check inputs for correctness...

		// Clear MyConfig:
		//MyConfig.clear();
		MyConfig = new SUB_SettingsHandlerJSON();

		MyConfig.setValue("GENERAL","DefaultDB", GLOBAL.DBDefaultDB);
		MyConfig.setValue("GENERAL","Beep", FLAGS.isBEEP());
		MyConfig.setValue("GENERAL","LastUser", GLOBAL.LastUser);
//		MyConfig.setValue("APP", "Reg", GLOBAL.REGISTRATION);
		MyConfig.setValue("WINDOW", "Left", WINDOWGEOM.getWindowLeft());
		MyConfig.setValue("WINDOW", "Top", WINDOWGEOM.getWindowTop());
		MyConfig.setValue("WINDOW", "Width", WINDOWGEOM.getWindowWidth());
		MyConfig.setValue("WINDOW", "Height", WINDOWGEOM.getWindowHeight());
		MyConfig.setValue("WINDOW","Style", GLOBAL.selectedStyle);
		MyConfig.setValue("WINDOW", "Maximized", WINDOWGEOM.isWindowMaximized());

		saveConfigDBsJSON();

		try {
			MyConfig.save(getConfigPath());
		} catch (IOException ex) {
			Logger.getLogger(CLS_Preferences.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void saveConfigDBsJSON() {
		ArrayList<Map<String, Object>> currentDbs = new ArrayList<Map<String, Object>>();

		if (!GLOBAL.DBCONFIG.isEmpty()) {
			// TODO: Load GLOBAL.DBCONFIG into currentDBs...
			for (TYP_ConfigDBJSON aDB : GLOBAL.DBCONFIG) {
				saveConfigDBJSON(currentDbs, aDB);
			}
		}

		MyConfig.setArray("DBs", currentDbs);
	}

	private static void saveConfigDBJSON(List<Map<String, Object>> currentDbs, TYP_ConfigDBJSON MyDBConfig) {
		// Add new DB configuration
		Map<String, Object> newDb = new LinkedHashMap<>();

		newDb.put("DBNICK", MyDBConfig.getDBNICK());
		newDb.put("DBDRIVER", MyDBConfig.getDBDRIVER());
		newDb.put("DBTYPE", MyDBConfig.getDBTYPE());
		newDb.put("DBPATH", MyDBConfig.getDBPATH());
		newDb.put("DBHOST", MyDBConfig.getDBHOST());
		newDb.put("DBPORT", MyDBConfig.getDBPORT());
		newDb.put("DBNAME", MyDBConfig.getDBNAME());
		newDb.put("DBUSER", MyDBConfig.getDBUSER());
		newDb.put("DBPASS", MyDBConfig.getDBPASS());

		currentDbs.add(newDb);
	}

}
