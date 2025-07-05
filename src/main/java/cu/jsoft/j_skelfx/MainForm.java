/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx;

import cu.jsoft.j_dbfxlite.exception.SQLx;
import cu.jsoft.j_loginfx.LoginMain;
import cu.jsoft.j_skelfx.global.CONSTS;
import cu.jsoft.j_skelfx.global.FLAGS;
import cu.jsoft.j_skelfx.global.GLOBAL;
import static cu.jsoft.j_skelfx.global.GLOBAL.WINDOWGEOM;
import static cu.jsoft.j_skelfx.global.GLOBAL.arguments;
import static cu.jsoft.j_skelfx.global.GLOBAL.doExit;
import static cu.jsoft.j_skelfx.global.GLOBAL.queryKnownOS;
import cu.jsoft.j_skelfx.global.RunTime;
import static cu.jsoft.j_skelfx.preferences.CLS_Preferences.loadConfigJSON;
import static cu.jsoft.j_skelfx.preferences.CLS_Preferences.saveConfigJSON;
import cu.jsoft.j_skelfx.preferences.db.CLS_DBPrefs;
import static cu.jsoft.j_utilsfxlite.global.CONSTS.NEW_LINE;
import static cu.jsoft.j_utilsfxlite.global.FLAGS.isDEBUG;
import static cu.jsoft.j_utilsfxlite.global.FLAGS.setBEEPAVAILABLE;
import cu.jsoft.j_utilsfxlite.networking.SingleInstance;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.MsgErrorYesNoFX;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.MsgWarningOKFX;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.MsgWarningYesNoFX;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.SimpleDialog;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsNotifications.echoln;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsNotifications.setupBeep;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author joe1962
 */
public class MainForm extends Application {
	private static final SingleInstance MySingleInstanceHandler = new SingleInstance();
	public static Button butHome, butConfig, butDB, butAnimals, butFeeds, butNorms;
	public static Button butOrders, butStock, butDeliveries, butReports, butDataMining, butExit;
	//private SQLx MySQLxHandler = new SQLx(GLOBAL.MyLargeFontBold, GLOBAL.MyDefaultFont);
	MainFormHelper GUIHelper = new MainFormHelper();



	public static void main(String[] args) {
		GLOBAL.arguments = args;
		GLOBAL.MainClass = MainForm.class;

		System.setProperty("javafx.preloader", preloaderSplashScreen.class.getCanonicalName());

		//launch(HJRMSFX.class, args);
		launch(args);
	}

	@Override
	public void init() throws Exception {
		// Do non-gui loading tasks
		doLoadingTasks();

		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Check and report DB connection errors:
		if (FLAGS.isDBCONNECTED()) {
			// TODO: do we need to do something here...???
		} else {
			switch (GLOBAL.DBErrorState) {
				case 0:
					// This is not supposed to be possible...!!!
					break;
				case 1:
					FLAGS.setERR_NO_DB(true);
					break;
				case 2:
					FLAGS.setERR_DB_CONN(true);
					break;
			}
		}

		primaryStage.setOnCloseRequest(event -> {
			echoln("Caught a stage close request...", RunTime.DebugMode, false);

			doExit(0);

			// Eat the event if we made it this far:
			event.consume();
		});

		GLOBAL.MainStage = primaryStage;

		try {
			showMainView(primaryStage);
		} catch (Exception ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void showMainView(Stage primaryStage) throws Exception {
		// Notify unknown OS:
		if (FLAGS.isFLAG_UNKNOWN_OS()) {
			String header = "Sistema Operativo desconocido!!!";
			String content = "¿Desea intentar continuar de todas formas?";
			if (MsgWarningYesNoFX(null, null, header, content)) {
				// Do nothing...
			} else {
				// Exit:
				System.exit(66);
			}
		}

		// Set up panes (internal panels):
		ScrollPane CenterpaneScroller = new ScrollPane();
		CenterpaneScroller.setFitToWidth(true);
		CenterpaneScroller.setFitToHeight(true);
		GLOBAL.CenterPane = new StackPane();
		GLOBAL.CenterPane.setStyle(CONSTS.cssEtchedBorder);
		GLOBAL.MainPane = new BorderPane();
		GLOBAL.MainPane.setCenter(CenterpaneScroller);
		CenterpaneScroller.setContent(GLOBAL.CenterPane);
		BorderPane.setMargin(GLOBAL.CenterPane, new Insets(1, 0, 0, 0));
		GLOBAL.MainPane.setBottom(GLOBAL.StatusPane);

		// Set up main scene:
		double MyWidth = 1024;
		double MyHeight = 600;
//		mainScene = new Scene(MainPane, MyWidth, MyHeight);
		GLOBAL.MainScene = new Scene(GLOBAL.MainPane);
		GLOBAL.setStyle(GLOBAL.selectedStyle);

		// Set up primary stage (main window):
		primaryStage.setScene(GLOBAL.MainScene);
//		primaryStage.setTitle(AppInfo.getTitleString());
		setWindowTitle(AppInfo.getTitleString());
		////		primaryStage.getIcons().add(new Image(GLOBAL.MainClass.getResourceAsStream("/images/menu-64.png")));
//		primaryStage.centerOnScreen();
		primaryStage.setX(WINDOWGEOM.getWindowLeft());
		primaryStage.setY(WINDOWGEOM.getWindowTop());
		primaryStage.setWidth(WINDOWGEOM.getWindowWidth());
		primaryStage.setHeight(WINDOWGEOM.getWindowHeight());
		primaryStage.setMinWidth(MyWidth);
		primaryStage.setMinHeight(MyHeight);
		primaryStage.setMaximized(WINDOWGEOM.isWindowMaximized());

		// Set up main toolbars, module buttons, center panel contents (module panels):
		GLOBAL.tlbLeft = new VBox();
		GLOBAL.tlbRight = new VBox();
		GUIHelper.prepHomeButtons(GLOBAL.MainController, GLOBAL.tlbLeft, GLOBAL.tlbRight);
		GUIHelper.disableSensitiveButtons(true);
		GUIHelper.setupToolBars(GLOBAL.MainPane, GLOBAL.tlbLeft, GLOBAL.tlbRight);
		GUIHelper.loadCenterContentsAll(GLOBAL.CenterPane);

		// Set up status bar:
		GUIHelper.setupStatusbar(GLOBAL.MainPane, GLOBAL.StatusPane);

		// React to single instance check:
		if (FLAGS.getSingleInstance() == null) {
			// Unable to determine if another instance is running;
			WTF();
		} else {
			if (FLAGS.getSingleInstance()) {
				// All is well...
			} else {
				MsgWarningOKFX(null, "AVISO", "", "Se está ejecutando otra instancia de la aplicación...");
				Platform.exit();
				System.exit(55);
			}
		}

		// React to DB connection errors:
		if (FLAGS.isERR_NO_DB()) {
			String title = "AVISO";
			String header = "Revise la configuración del " + AppInfo.getTITLE() + ":";
			String content = "No hay base de datos configurada..." + "\n" + "Se mostrará el diálogo de configuración de la BD," + "\n" + "posteriormente debe reiniciar el sistema " + AppInfo.getTITLE();
			MsgWarningOKFX(null, title, header, content);
			// Go to DB config...
			doDBConfig();
		}
		if (FLAGS.isERR_DB_CONN()) {
			SQLx MySQLxHandler = new SQLx(GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB), GLOBAL.MyLargeFontBold, GLOBAL.MyDefaultFont);
			boolean retBool = MySQLxHandler.handleExceptionPG(GLOBAL.DBException, FLAGS.isDEBUG());

			// Go to DB config...
			doDBConfig();
		}

		// Show login dialog:
		try {
		Login();
		} catch (IOException ex) {
			GLOBAL.doLogger(ex, "Login IOException", Level.SEVERE);
		}

		// Enable sensitive (module) buttons on successful login:
		// TODO: implement granular user permissions:
		if (!FLAGS.isDBCONNECTED() | !FLAGS.isDBGLOADED() | !FLAGS.isLOGGEDIN()) {
			// NOTE: Guest mode...
			GUIHelper.disableSensitiveButtons(true);
		} else {
			GUIHelper.disableSensitiveButtons(false);
			if (FLAGS.isERR_NO_POS()) {
				String myMsg = "No hay POS definidos, debe crear al menos uno \npara utilizar las funcionalidades del RMS-ng...";
				String header = null;
				String content = myMsg;
				MsgWarningOKFX(GLOBAL.MainStage, null, header, content);
			}
		}

		// Set up status bar initial state:
		GUIHelper.setInitialStatusBarState();

		// Show main window:
		GLOBAL.MainController.doInit();			// Need this as the controller is not initialized because we are not using fxml for main window...
		primaryStage.show();

	}

	/**
	 * Do all non-GUI application startup tasks here...
	 */
	private void doLoadingTasks() {
		// DEBUG:
		echoln("javafx.runtime.version: " + System.getProperty("javafx.runtime.version"), isDEBUG(), true);
		echoln("Java version: " + System.getProperty("java.version"), isDEBUG(), true);
		echoln("JAVA_HOME: " + System.getenv("JAVA_HOME"), isDEBUG(), true);
		echoln("JRE_HOME: " + System.getenv("JRE_HOME"), isDEBUG(), true);
		echoln("JDK_HOME: " + System.getenv("JDK_HOME"), isDEBUG(), true);

		// Check for multiple instances:
		if (!checkSingleInstanceFX()) {
			return;
		}

		// Check and process command line arguments:
		try {
			checkArgsFX(arguments);
		} catch (InterruptedException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		}

		// Is OS supported?:
		if (queryKnownOS()) {
			FLAGS.setFLAG_UNKNOWN_OS(false);
		} else {
			FLAGS.setFLAG_UNKNOWN_OS(true);
		}

		// Set up the error beep:
		setUpTheBeeper();

		GLOBAL.loadFonts();

		// Load basic configuration from file:
		try {
			loadConfigJSON();
		} catch (IOException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		}

		// Try and connect to the DB:
		int retInt = DBConnect();
		switch (retInt) {
			case 0:
				GLOBAL.DBErrorState = 0;
				break;
			case 1:
				GLOBAL.DBErrorState = 1;
				break;
			case 2:
				GLOBAL.DBErrorState = 2;
				break;
		}
	}

	private void checkArgsFX(String[] args) throws InterruptedException {
		for (String string : args) {
			if (string.contains("--debug")) {
				RunTime.DebugMode = true;
				echoln("Running in debug mode...", RunTime.DebugMode, true);
			}

			if (string.contains("--runtime")) {
				Field[] MyRunTimeFields = RunTime.getInstance().getClass().getFields();
				for (Field MyRunTimeField : MyRunTimeFields) {
					try {
						echoln("RunTime." + MyRunTimeField.getName() + ": " + MyRunTimeField.get(null), RunTime.DebugMode, true);
					} catch (IllegalArgumentException | IllegalAccessException ex) {
						GLOBAL.doLogger(ex, string, Level.SEVERE);
					}
				}
				//System.exit(0);
			}

//			if (string.contains("--reg")) {
//				if (!isRegistered(AppInfo.getTITLE(), GLOBAL.MainClass.getPackage().getName(), 0)) {
//					echoln("Registered: False", RunTime.DebugMode, true);
//				} else {
//					echoln("Registered: True", RunTime.DebugMode, true);
//				}
//				//System.exit(0);
//			}
//
//			if (string.contains("--novalidatedb")) {
//				RunTime.ValidateDBStruct = false;
//			}

			if (string.contains("--version")) {
				echoln(GLOBAL.getTitleString(), RunTime.DebugMode, true);
				//System.exit(0);
			}

			if (string.contains("--exit")) {
				// MUST ALWAYS BE THE LAST IF...
				System.exit(0);
			}
		}

		echoln("RunTime.DebugMode: " + RunTime.DebugMode, RunTime.DebugMode, false);

	}

	private boolean checkSingleInstanceFX() {
		int retInt;

		if (RunTime.IsAdmin) {
			retInt = MySingleInstanceHandler.getInstances(6666);
		} else {
			retInt = MySingleInstanceHandler.getInstances(6667);
		}
		switch (retInt) {
			case -1:
				// Unable to determine if another instance is running:
				//WTF();
				FLAGS.setSingleInstance(null);
				break;
			case 0:
				// No other instance is running:
				echoln("No other instance is running... RUNNING NOW...", RunTime.DebugMode, false);
				FLAGS.setSingleInstance(true);
				break;
			case 1:
				// Another instance is running:
				echoln("Another instance is running... EXITING NOW...", RunTime.DebugMode, false);
				FLAGS.setSingleInstance(false);
				return false;
			//break;
			default:
				// Unable to determine if another instance is running:
				//WTF();
				FLAGS.setSingleInstance(null);
				break;
		}
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (RunTime.IsAdmin) {
					try {
						MySingleInstanceHandler.StartServer(6666);
					} catch (IOException ex) {
						Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
					}
				} else {
					try {
						MySingleInstanceHandler.StartServer(6667);
					} catch (IOException ex) {
						Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		};
		thread.start();
		return true;
	}

	private void WTF() {
		echoln("Unable to determine if another instance is running... ASKING NOW...", RunTime.DebugMode, false);
		boolean response = MsgErrorYesNoFX(null, "AVISO", null, "Imposible determinar si otra instancia de la aplicación se está ejecutando..." + NEW_LINE + "¿Ejecutar de todas formas?");
		if (!response) {
			System.exit(56);
		}
	}

	private static void setUpTheBeeper() {
		setBEEPAVAILABLE(false);
		try {
			setupBeep(isDEBUG());
			setBEEPAVAILABLE(true);
		} catch (UnsupportedAudioFileException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		} catch (LineUnavailableException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private int DBConnect() {
		// Connect to database:
		try {
			if (GLOBAL.DBConnect(GLOBAL.DBDefaultDB)) {
				FLAGS.setDBCONNECTED(true);

				boolean IsSuccess = GLOBAL.loadGlobalRecordSets();
				if (IsSuccess) {
					FLAGS.setDBGLOADED(true);
				} else {
					FLAGS.setDBGLOADED(false);
				}
			} else {
				FLAGS.setDBCONNECTED(false);
				return 1;
			}
		} catch (SQLException ex) {
			FLAGS.setDBCONNECTED(false);
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
			GLOBAL.DBErrorDesc = ex.getLocalizedMessage();
			GLOBAL.DBException = ex;
			return 2;
		}
		return 0;
	}

	private void doDBConfig() throws IOException {
		CLS_DBPrefs MyDBPrefs = new CLS_DBPrefs();
		SimpleDialog("SkelFX", MyDBPrefs.getDBNode(), "TERMINAR", 640, 480);
		Platform.exit();
		System.exit(0);
	}

	private void Login() throws IOException, SQLException {
		LoginMain Loginhandler = new LoginMain(GLOBAL.MainClass, GLOBAL.DBConnHandler, GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB).getDBNAME(), GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB).getDBUSER(), GLOBAL.MyLargeFontBold, GLOBAL.MyDefaultFont);

		GLOBAL.CurrUser = Loginhandler.doLogin(null, "Login: " + AppInfo.getTitleString(), new StringBuffer(CONSTS.AESSalt).reverse().toString(), new StringBuffer(CONSTS.SecKeyStr).reverse().toString(), CONSTS.iv);

		if (GLOBAL.CurrUser.isEmpty()) {
			FLAGS.setLOGGEDIN(false);
			// TODO: BE CAREFUL TO IMPLEMENT GUEST USE SAFELY...!!!
			// Comment or remove next 2 lines to allow guest use:
			Platform.exit();
			System.exit(11);			// TODO: change to proper number
		} else {
			FLAGS.setLOGGEDIN(true);
			GLOBAL.LastUser = GLOBAL.CurrUser;
			// TODO: Save LastUser to preferences...
			saveConfigJSON();
		}
	}

	public void setupMainButtons(boolean DBConnected) {
//		butHome.setDisable(false);
//		butExit.setDisable(false);
//
//		if (DBConnected) {
//			// Set up buttons for DBConnected connect SUCCESS:
//			butConfig.setDisable(!RunTime.AllowConfig);
//			butDB.setDisable(!RunTime.AllowDB);
//			butAnimals.setDisable(!RunTime.AllowAnimals);
//			butFeeds.setDisable(!RunTime.AllowFeeds);
//			butNorms.setDisable(!RunTime.AllowNorms);
//			butOrders.setDisable(!RunTime.AllowOrders);
//			butStock.setDisable(!RunTime.AllowStock);
//			butDeliveries.setDisable(!RunTime.AllowDeliveries);
//			butReports .setDisable(!RunTime.Allowreports);
//			butDataMining.setDisable(!RunTime.AllowDataMining);
//		} else {
//			// Set up buttons for DBConnected connect FAILED:
//			butDB.setDisable(true);
//			butConfig.setDisable(false);
//			butAnimals.setDisable(true);
//			butFeeds.setDisable(true);
//			butNorms.setDisable(true);
//			butOrders.setDisable(true);
//			butStock.setDisable(true);
//			butDeliveries.setDisable(true);
//			butReports .setDisable(true);
//			butDataMining.setDisable(true);
//		}
	}

	public void setWindowTitle(String MyTitle) {
		GLOBAL.MainStage.setTitle(MyTitle);
	}

}
