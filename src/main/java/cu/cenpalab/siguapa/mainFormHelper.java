/*
 * Copyright Joe1962
 */
package cu.cenpalab.siguapa;

import static cu.cenpalab.siguapa.MainForm.butAnimals;
import static cu.cenpalab.siguapa.MainForm.butConfig;
import static cu.cenpalab.siguapa.MainForm.butDB;
import static cu.cenpalab.siguapa.MainForm.butDataMining;
import static cu.cenpalab.siguapa.MainForm.butDeliveries;
import static cu.cenpalab.siguapa.MainForm.butExit;
import static cu.cenpalab.siguapa.MainForm.butFeeds;
import static cu.cenpalab.siguapa.MainForm.butHome;
import static cu.cenpalab.siguapa.MainForm.butNorms;
import static cu.cenpalab.siguapa.MainForm.butOrders;
import static cu.cenpalab.siguapa.MainForm.butReports;
import static cu.cenpalab.siguapa.MainForm.butStock;
import cu.cenpalab.siguapa.global.CONSTS;
import cu.cenpalab.siguapa.global.GLOBAL;
import cu.cenpalab.siguapa.global.TYP_GlobalFXRef;
import cu.cenpalab.siguapa.logo.FX_LogoController;
import cu.cenpalab.siguapa.preferences.FX_ConfigController;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.toFrontHelper;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author joe1962
 */
public class mainFormHelper {

	public void setupToolBars(BorderPane MainPane, VBox tlbLeft, VBox tlbRight) {
		tlbLeft.setStyle(CONSTS.cssEtchedBorder);
		MainPane.setLeft(tlbLeft);
		BorderPane.setMargin(tlbLeft, new Insets(1, 0, 0, 1));
		tlbRight.setStyle(CONSTS.cssEtchedBorder);
		MainPane.setRight(tlbRight);
		BorderPane.setMargin(tlbRight, new Insets(1, 1, 0, 0));
	}

	public void setupStatusbar(BorderPane MainPane, StackPane StatusPane) {
		GLOBAL.StatusBar = StatusPane;
		URL location = GLOBAL.MainClass.getResource("/fxml/statusbar/FX_StatusBar.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(location);
		try {
			GLOBAL.StatusBar = loader.load();
		} catch (IOException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		}
		GLOBAL.StatusBar.setStyle(CONSTS.cssEtchedBorder);
		MainPane.setBottom(GLOBAL.StatusBar);
		GLOBAL.FXStatusBarController = loader.getController();
		BorderPane.setMargin(GLOBAL.StatusBar, new Insets(0, 1, 0, 1));
	}

	public void setInitialStatusBarState() {
//		if (FLAGS.isDBCONNECTED()) {
//			if (FLAGS.isDBGLOADED()) {
//				GLOBAL.FXStatusBarController.setDBLED(CONSTS.LEDState.GREEN);
//				GLOBAL.FXStatusBarController.setStatusText("Conectado a base de datos: " + GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB).getDBNAME(), 10, false);
//			} else {
//				GLOBAL.FXStatusBarController.setDBLED(CONSTS.LEDState.YELLOW);
//				GLOBAL.FXStatusBarController.setStatusText("Conectado a base de datos: " + GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB).getDBNAME() + " pero error cargando...", 10, false);
//			}
//		} else {
			GLOBAL.FXStatusBarController.setDBLED(CONSTS.LEDState.RED);
//			GLOBAL.FXStatusBarController.setStatusText("ERROR conectando a Base de Datos: " + GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB).getDBNAME(), 10, true);
//		}
	}

	public void prepHomeButtons(MainFormController theMainFormController, VBox tlbLeft, VBox tlbRight) {
		//this.butToolbarLeft = tlbLeft;
		//this.butToolbarRight = tlbRight;

		// Left buttons:
		butHome = new Button();
		butHome.setOnAction(theMainFormController::butHomeOnActionHandler);
		butConfig = new Button();
		butConfig.setOnAction(theMainFormController::butConfigOnActionHandler);
		butDB = new Button();
		butDB.setOnAction(theMainFormController::butDBOnActionHandler);
		butAnimals = new Button();
		//butAnimals.setOnAction(theMainFormController::butDBOnActionHandler);
		butFeeds = new Button();
		//butFeeds.setOnAction(theMainFormController::butDBOnActionHandler);
		butNorms = new Button();
		//butNorms.setOnAction(theMainFormController::butDBOnActionHandler);

		// Right buttons:
		butOrders = new Button();
		//butOrders.setOnAction(theMainFormController::butDBOnActionHandler);
		butStock = new Button();
		//butStock.setOnAction(theMainFormController::butDBOnActionHandler);
		butDeliveries = new Button();
		//butDeliveries.setOnAction(theMainFormController::butDBOnActionHandler);
		butReports = new Button();
		//butReports.setOnAction(theMainFormController::butDBOnActionHandler);
		butDataMining = new Button();
		//butDataMining.setOnAction(theMainFormController::butDBOnActionHandler);
		butExit = new Button();
		butExit.setOnAction(theMainFormController::butExitOnActionHandler);

		// Left buttons:
		butHome.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/home-64.png"))));
		butConfig.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/configuracion-64.png"))));
		butDB.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/database-64.png"))));
		butAnimals.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));
		butFeeds.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));
		butNorms.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));

		// Right buttons:
		butOrders.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));
		butStock.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));
		butDeliveries.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));
		butReports.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/user-busy-64.png"))));
		butDataMining.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/reportes-64.png"))));
		butExit.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/salir-64.png"))));

//		setTooltipTimers(250, 5000, 500);

		// Left buttons:
		butHome.setTooltip(new Tooltip("Inicio"));
		butConfig.setTooltip(new Tooltip("Configuración"));
		butDB.setTooltip(new Tooltip("Mantenimiento a Base de Datos"));
		butAnimals.setTooltip(new Tooltip("Nomencladores de animales"));
		butFeeds.setTooltip(new Tooltip("Nomencladores de alimentos"));
		butNorms.setTooltip(new Tooltip("Normas de consumo"));

		// Right buttons:
		butOrders.setTooltip(new Tooltip("Pedidos"));
		butStock.setTooltip(new Tooltip("Existencias"));
		butDeliveries.setTooltip(new Tooltip("Entregas"));
		butReports.setTooltip(new Tooltip("Reportes"));
		butDataMining.setTooltip(new Tooltip("Minería de datos"));
		butExit.setTooltip(new Tooltip("Salir"));

		// Left buttons:
		GLOBAL.tlbLeft.setSpacing(0);
		GLOBAL.tlbLeft.getChildren().add(butHome);
		GLOBAL.tlbLeft.getChildren().add(butConfig);
		GLOBAL.tlbLeft.getChildren().add(butDB);
		GLOBAL.tlbLeft.getChildren().add(butAnimals);
		GLOBAL.tlbLeft.getChildren().add(butFeeds);
		GLOBAL.tlbLeft.getChildren().add(butNorms);

		// Right buttons:
		GLOBAL.tlbRight.setSpacing(0);
		GLOBAL.tlbRight.getChildren().add(butOrders);
		GLOBAL.tlbRight.getChildren().add(butStock);
		GLOBAL.tlbRight.getChildren().add(butDeliveries);
		GLOBAL.tlbRight.getChildren().add(butReports);
		GLOBAL.tlbRight.getChildren().add(butDataMining);
		GLOBAL.tlbRight.getChildren().add(butExit);
	}

	public void disableSensitiveButtons(boolean theState) {
		//butHome
		//butConfig
		butDB.setDisable(theState);
//		butECR.setDisable(theState);
//		butIngredients.setDisable(theState);
//		butMenu.setDisable(theState);
//		butHostal.setDisable(theState);
//		butInventory.setDisable(theState);
//		butSales.setDisable(theState);
//		butBuys.setDisable(theState);
//		butFinance.setDisable(theState);
//		butReports.setDisable(theState);
//		butSecurity.setDisable(theState);
		//butExit
	}

	public void loadCenterContentsAll(StackPane CenterPane) {
		//GLOBAL.CenterPane = CenterPane;

		loadCenterContentsLogo();
		loadCenterContentsConfig();
		loadCenterContentsDB();
//		loadCenterContentsECR();
//		loadCenterContentsIngredientes();
//		//loadCenterContentsHelper("hj.rmsngfxingredients/FX_Ingredients.fxml", GLOBAL.ccIngredients, CenterPane, GLOBAL.FXIngredientsController);
//		loadCenterContentsMenu();
//		loadCenterContentsHostal();
//		loadCenterContentsVentas();
//		loadCenterContentsCompras();
//		loadCenterContentsInventario();
//		loadCenterContentsFinanzas();
//		loadCenterContentsReportes();
//		loadCenterContentsSeguridad();
		toFrontHelper(CenterPane, "tipLogo");
	}

	private TYP_GlobalFXRef loadCenterContentsHelper(StackPane destPane, String FXResource, Parent FXNode, Initializable Controller) {
	//public static void loadCenterContentsHelper(String FXResource, StackPane destPane, TYP_GlobalFXRef FXNode_Controller) {
		URL location = GLOBAL.MainClass.getResource(FXResource);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(location);

		try {
			FXNode = loader.load();
		} catch (IOException ex) {
			Logger.getLogger(GLOBAL.MainClass.getName()).log(Level.SEVERE, null, ex);
		}

		FXNode.setOpacity(0);

		destPane.getChildren().add(FXNode);
		Controller = loader.getController();

		TYP_GlobalFXRef FXNode_Controller = new TYP_GlobalFXRef();
		FXNode_Controller.MyParent = FXNode;
		FXNode_Controller.MyController = Controller;
		return FXNode_Controller;
	}

	private void loadCenterContentsLogo() {
		GLOBAL.ccLogo = GLOBAL.CenterPane;
		GLOBAL.FXLogoController = new FX_LogoController();
		TYP_GlobalFXRef FXNode_Controller = loadCenterContentsHelper(GLOBAL.CenterPane, "/fxml/logo/FX_Logo.fxml", GLOBAL.ccLogo, GLOBAL.FXLogoController);
		GLOBAL.ccLogo = FXNode_Controller.MyParent;
		GLOBAL.FXLogoController = (FX_LogoController) FXNode_Controller.MyController;
	}

	private void loadCenterContentsConfig() {
		GLOBAL.ccConfig = GLOBAL.CenterPane;
		GLOBAL.FXConfigController = new FX_ConfigController();
		TYP_GlobalFXRef FXNode_Controller = loadCenterContentsHelper(GLOBAL.CenterPane, "/fxml/preferences/FX_Config.fxml", GLOBAL.ccConfig, GLOBAL.FXConfigController);
		GLOBAL.ccConfig = FXNode_Controller.MyParent;
		GLOBAL.FXConfigController = (FX_ConfigController) FXNode_Controller.MyController;
	}

	private void loadCenterContentsDB() {
	}

	// Load other center content FX panels here...!!!

}
