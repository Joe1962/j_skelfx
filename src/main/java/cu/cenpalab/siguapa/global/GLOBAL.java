/*
 * Copyright Joe1962
 */
package cu.cenpalab.siguapa.global;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import cu.cenpalab.siguapa.AppInfo;
import cu.cenpalab.siguapa.MainFormController;
import cu.cenpalab.siguapa.logo.FX_LogoController;
import static cu.cenpalab.siguapa.preferences.CLS_Preferences.saveConfigJSON;
import cu.cenpalab.siguapa.preferences.FX_ConfigController;
import cu.cenpalab.siguapa.preferences.db.TYP_ConfigDBJSON;
import cu.cenpalab.siguapa.statusbar.FX_StatusBarController;
import cu.cenpalab.siguapa.types.TYP_user;
import static cu.jsoft.j_dbfxlite.DBActions.dbConnectServer;
import static cu.jsoft.j_dbfxlite.DBActions.getMyConn;
import static cu.jsoft.j_utilsfxlite.global.FLAGS.isDEBUG;
import cu.jsoft.j_utilsfxlite.preferences.TYP_WindowGeometry;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.MsgErrorYesNoFX;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.doFadeIn;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsNotifications.echoln;
import cu.jsoft.j_utilsfxlite.subs.SUB_UtilsOS;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author joe1962
 */
public class GLOBAL {

	// General VARS:
	public static String[] arguments;
	public static String OS;
	public static double defaultStatusBarMessageDelay = 5d;
	public static final SimpleDateFormat sqlFormatter = new SimpleDateFormat("yyyy-MM-dd");
	public static TYP_WindowGeometry WINDOWGEOM = new TYP_WindowGeometry();
	public static TYP_user MyUser = new TYP_user();
	public static String CurrUser;
	public static String LastUser;
	public static Date TheDate = new Date();

	// Global objects:
	public static Class MainClass;										// Remember to set this in startup class...
	public static Stage MainStage;
	public static MainFormController MainController = new MainFormController();
	public static BorderPane MainPane;
	public static Scene MainScene;
	public static StackPane CenterPane;
	public static StackPane StatusPane;
	public static Parent StatusBar;
	public static FX_StatusBarController FXStatusBarController;
	public static VBox tlbLeft;
	public static VBox tlbRight;

	// GLOBAL STYLE VARS:
	public static int BackGround = 2;						// options: 1 to 4.
	public static String defaultStyle = "modena";		// options: modena, caspian.
	//public static String defaultCSS = "default";
	public static String defaultCSS = "dark";
	//public static String defaultCSS = "material";
	public static String selectedStyle;

	// GLOBAL Panels (centercontents):
	public static Parent ccLogo;
	public static FX_LogoController FXLogoController;
	public static Parent ccConfig;
	public static FX_ConfigController FXConfigController;

	// DB vars:
//	public static DBConnectionHandler MyStaticDBConnHandler = new DBConnectionHandler();										// TODO: to remove...
	public static int DBErrorState = 0;				// 0: no error, 1: no DB configured, 2: exception connecting.
	public static boolean NoDB = false;
	public static int DBDefaultDB = 0;
//	public static String DBDriverType = "jdbc";
//	public static HashMap<Integer, TYP_ConfigDBJSON> Databases = new HashMap<>();												// TODO: to remove...
	public static ArrayList<TYP_ConfigDBJSON> DBCONFIG  = new ArrayList<>();



	public static String getTitleString() {
		StringBuilder strVersion = new StringBuilder();

		strVersion.append(AppInfo.getTITLE());
		strVersion.append(" ver. ");
		strVersion.append(getVersionString());

		if (strVersion.length() > 0) {
			return strVersion.toString();
		} else {
			return "";
		}
	}

	public static String getVersionString() {
		StringBuilder strVersion = new StringBuilder();

		if (AppInfo.getBUILD().length() > 0) {
			strVersion.append(AppInfo.getVERSION());
			//strVersion.append(" (build ");
			strVersion.append(" (");
			strVersion.append(AppInfo.getBUILD());
			strVersion.append(")");
		} else {
			strVersion = strVersion.append(AppInfo.getVERSION_MAJOR());
			strVersion = strVersion.append(".");
			strVersion = strVersion.append(AppInfo.getVERSION_MINOR());
			strVersion = strVersion.append(".");
			strVersion = strVersion.append(AppInfo.getVERSION_BUILD());
		}
		if (strVersion.length() > 0) {
			return strVersion.toString();
		} else {
			return "";
		}
	}

	public static void setStyle(String styleType) {
		selectedStyle = styleType;
		switch (styleType) {
			case "modena light":
				MainScene.getStylesheets().clear();
				setCSS("default");
				Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
				break;
			case "modena dark":
				MainScene.getStylesheets().clear();
				setCSS("dark");
				Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
				break;
			case "caspian light":
				MainScene.getStylesheets().clear();
				setCSS("default");
				Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
				break;
			case "caspian dark":
				MainScene.getStylesheets().clear();
				setCSS("dark");
				Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
				break;
			case "primer light":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
				break;
			case "primer dark":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
				break;
			case "nord light":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
				break;
			case "nord dark":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
				break;
			case "cupertino light":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
				break;
			case "cupertino dark":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
				break;
			case "dracula":
				MainScene.getStylesheets().clear();
				Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
				break;
			default:
				Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
				break;
		}
	}

	public static void setCSS(String cssType) {
		defaultCSS = cssType;
		MainScene.getStylesheets().clear();
		MainScene.getStylesheets().add("/css/" + defaultCSS + ".css");
	}

	public static boolean queryKnownOS() {
		// Echo OS info:
		String[] MyOS = SUB_UtilsOS.getOS();
		echoln("RMS is running on " + MyOS[0], isDEBUG(), false);
		if (MyOS[0].startsWith("Linux")) {
			GLOBAL.OS = "Linux";
		} else if (MyOS[0].startsWith("Windows")) {
			GLOBAL.OS = "Windows";
		} else {
			// Other OS...
//			SUB_UtilsNotifications.doBeep();
//			//String MyCaption = "Un error requiere su intervención:";
//			String MyReqOp = "<html><b>Sistema Operativo desconocido!!!<br><br>¿Desea intentar continuar de todas formas?</b></html>";
//			boolean RetBool = MsgWarningYesNo(null, "", MyReqOp);
//			if (!RetBool) {
//				System.exit(0);
//			} else {
				return false;
//			}
		}

		return true;

	}

	public static boolean DBConnect(int MyDB) throws SQLException {
		String MyDBManager = null;

		// Set up a permanent connection to the database:
		FLAGS.setDBCONNECTED(false);
		echoln("Attempting Database connection...", RunTime.DebugMode, false);			// DEBUG...

		if (DBCONFIG.isEmpty()) {
			return false;
		} else {
			MyDBManager = DBCONFIG.get(MyDB).getDBTYPE();
			if (MyDBManager.isBlank()) {
				// TODO: continue until configuration...???
				return false;
			}
		}

		FLAGS.setDBCONNECTED(false);

		// TODO: DBCONFIG.get(MyDB).getDBPASS() has to be decrypted...!!!
		dbConnectServer(
			DBCONFIG.get(MyDB).getDBDRIVER(), 
			DBCONFIG.get(MyDB).getDBTYPE(), 
			DBCONFIG.get(MyDB).getDBHOST(), 
			DBCONFIG.get(MyDB).getDBPORT(), 
			DBCONFIG.get(MyDB).getDBNAME(), 
			DBCONFIG.get(MyDB).getDBUSER(), 
			"123");
//			DBCONFIG.get(MyDB).getDBPASS());

		FLAGS.setDBCONNECTED(true);
		return true;
	}

	public static boolean DBReconnect() throws SQLException {
		try {
			getMyConn().close();
		} catch (SQLException ex) {
//			logLogger.log(Level.SEVERE, null, ex);
		}

		return DBConnect(DBDefaultDB);
	}

	public static boolean DBValidOrReconnect() throws SQLException {
		boolean DBConnValid;
		try {
			DBConnValid = getMyConn().isValid(5);
		} catch (SQLException ex1) {
//			logLogger.log(Level.SEVERE, null, ex1);
			DBConnValid = false;
		}
		if (!DBConnValid) {
			DBConnValid = DBReconnect();
		}

		return DBConnValid;
	}

	public static boolean loadGlobalRecordSets() {
		
		return false;
	}

	public static void doLogger(Exception ex, String mesg, Level MyLogLevel) {
		Logger.getLogger(MainClass.getName()).log(MyLogLevel, mesg, ex);
	}

	public static void doExit(int ExitStatus) {
		// Fade out, then confirmation dialog...
		Node MainNode = GLOBAL.MainPane;

		FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), MainNode);
		fadeOut.setToValue(0.5);            // Fade to 50% opacity
		fadeOut.setOnFinished(e -> {
			// Use Platform.runLater to show dialog after animation completes
			Platform.runLater(() -> {
				boolean confirmExit = doExitDialog();
				if (confirmExit) {
					saveConfigJSON();

					Platform.exit();
					System.exit(ExitStatus);
				} else {
					// If cancelled, fade back in
					doFadeIn(MainNode, Duration.seconds(0), Duration.seconds(0.25), 1);
				}
			});
		});
		fadeOut.play();
	}

	public static boolean doExitDialog() {
		String title = "Salida";
		String mesg = "Desea salir de la aplicación?";
		return MsgErrorYesNoFX(GLOBAL.MainStage, title, "", mesg);
	}

}
