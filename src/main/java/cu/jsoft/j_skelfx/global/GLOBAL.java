/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.global;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import cu.jsoft.j_dbfx.DBConnectionHandler;
import cu.jsoft.j_dbfx.types.TYP_ConfigDBJSON;
import cu.jsoft.j_loginfx.users.RS_users;
import cu.jsoft.j_loginfx.users.TYP_user;
import cu.jsoft.j_skelfx.AppInfo;
import cu.jsoft.j_skelfx.MainFormController;
import cu.jsoft.j_skelfx.logo.FX_LogoController;
import static cu.jsoft.j_skelfx.preferences.CLS_Preferences.saveConfigJSON;
import cu.jsoft.j_skelfx.preferences.FX_ConfigController;
import cu.jsoft.j_skelfx.statusbar.FX_StatusBarController;
import static cu.jsoft.j_utilsfx.global.FLAGS.isDEBUG;
import cu.jsoft.j_utilsfx.preferences.TYP_WindowGeometry;
import cu.jsoft.j_utilsfx.security.SUB_Protect;
import static cu.jsoft.j_utilsfx.subs.SUB_PopupsFX.MsgErrorYesNoFX;
import static cu.jsoft.j_utilsfx.subs.SUB_UtilsFX.doFadeIn;
import static cu.jsoft.j_utilsfx.subs.SUB_UtilsNotifications.echoln;
import cu.jsoft.j_utilsfx.subs.SUB_UtilsOS;
import java.io.InputStream;
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
import javafx.scene.text.Font;
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
	public static boolean AdminUser = false;
	public static String LastUser;
	public static Date TheDate = new Date();

	// Global objects:
	public static Class MainClass;										// Remember to set this in startup class...
	public static Stage MainStage;
	public static MainFormController MainController = new MainFormController();
	public static BorderPane MainPane = null;
	public static Scene MainScene;
	public static StackPane CenterPane = null;
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

	// GLOBAL Fonts:
	public static Font MyLargeFont;
	public static Font MyDefaultFont;
	public static Font MySmallFont;
	public static Font MyCondensedFont;
	public static Font MyCondensedFontSmall;
	public static Font MyLargeFontBold;
	public static Font MyDefaultFontBold;
	public static Font MySmallFontBold;
	public static Font MyCondensedFontBold;

	// GLOBAL Panels (centercontents):
	public static Node prevNode;
	public static Node currNode;
	public static Node ccLogo;
	public static FX_LogoController FXLogoController;
	public static Node ccConfig;
	public static FX_ConfigController FXConfigController;

	// DB vars:
	public static DBConnectionHandler DBConnHandler;
	public static int DBErrorState = 0;				// 0: no error, 1: no DB configured, 2: exception connecting.
	public static String DBErrorDesc;
	public static SQLException DBException;
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

	public static void loadFonts() {
		String MyFontTypeNormal = "roboto/Roboto-Regular.ttf";
		String MyFontTypeBold = "roboto/Roboto-Bold.ttf";
		String MyFontTypeCondensed = "roboto/RobotoCondensed-Light.ttf";
		String MyFontTypeCondensedBold = "roboto/RobotoCondensed-Bold.ttf";

		// MyFontTypeNormal:
		InputStream streamMyLargeFont = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeNormal);
		GLOBAL.MyLargeFont = Font.loadFont(streamMyLargeFont, 16);
		InputStream streamMyDefaultFont = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeNormal);
		GLOBAL.MyDefaultFont = Font.loadFont(streamMyDefaultFont, 14);
		InputStream streamMySmallFont = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeNormal);
		GLOBAL.MySmallFont = Font.loadFont(streamMySmallFont, 12);

		// MyFontTypeBold:
		InputStream streamLargeFontBold = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeBold);
		GLOBAL.MyLargeFontBold = Font.loadFont(streamLargeFontBold, 16);
		InputStream streamDefaultFontBold = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeBold);
		GLOBAL.MyDefaultFontBold = Font.loadFont(streamDefaultFontBold, 14);
		InputStream streamSmallFontBold = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeBold);
		GLOBAL.MySmallFontBold = Font.loadFont(streamSmallFontBold, 12);

		// MyFontTypeCondensed:
		InputStream streamCondensedFont = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeCondensed);
		GLOBAL.MyCondensedFont = Font.loadFont(streamCondensedFont, 14);
		InputStream streamCondensedFontSmall = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeCondensed);
		GLOBAL.MyCondensedFontSmall = Font.loadFont(streamCondensedFontSmall, 12);

		// MyFontTypeCondensedBold:
		InputStream streamCondensedFontBold = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/" + MyFontTypeCondensedBold);
		GLOBAL.MyCondensedFontBold = Font.loadFont(streamCondensedFontBold, 11);
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
		SUB_Protect Protection = new SUB_Protect();
		DBConnHandler = new DBConnectionHandler();
		String MyDBManager = null;

		// Set up a permanent connection to the database:
		FLAGS.setDBCONNECTED(false);
		echoln("Attempting Database connection...", RunTime.DebugMode, false);			// DEBUG...

		if (DBCONFIG.isEmpty()) {
			return false;
		} else {
			MyDBManager = DBCONFIG.get(MyDB).getDBDRIVER();
			if (MyDBManager.isBlank()) {
				// TODO: continue until configuration...???
				return false;
			}
		}

		FLAGS.setDBCONNECTED(false);

		// TODO: DBCONFIG.get(MyDB).getDBPASS() has to be decrypted...!!!
		DBConnHandler.dbConnectServer(
			DBCONFIG.get(MyDB).getDBCONNTYPE(), 
			DBCONFIG.get(MyDB).getDBDRIVER(), 
			DBCONFIG.get(MyDB).getDBPATH(), 
			DBCONFIG.get(MyDB).getDBHOST(), 
			DBCONFIG.get(MyDB).getDBPORT(), 
			DBCONFIG.get(MyDB).getDBNAME(), 
			DBCONFIG.get(MyDB).getDBUSER(), 
			Protection.getDecryptedString(DBCONFIG.get(MyDB).getDBPASS(), new StringBuffer(CONSTS.SecKeyStr).reverse().toString(), CONSTS.iv));

		FLAGS.setDBCONNECTED(true);
		return true;
	}

	public static boolean DBReconnect() throws SQLException {
		try {
			DBConnHandler.getMyConn().close();
		} catch (SQLException ex) {
//			logLogger.log(Level.SEVERE, null, ex);
		}

		return DBConnect(DBDefaultDB);
	}

	public static boolean DBValidOrReconnect() throws SQLException {
		boolean DBConnValid;
		try {
			DBConnValid = DBConnHandler.getMyConn().isValid(5);
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

	public static void updateAdminStatus() {
		RS_users RS = new RS_users();
		RS.setDBConnHandler(DBConnHandler);
		try {
			 AdminUser = RS.isAdminByname(CurrUser);
		} catch (SQLException ex) {
			System.getLogger(GLOBAL.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
		}
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
