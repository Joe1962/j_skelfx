/*
 * Copyright Joe1962
 */
package cu.cenpalab.siguapa;

import cu.cenpalab.siguapa.global.GLOBAL;
import static cu.cenpalab.siguapa.global.GLOBAL.WINDOWGEOM;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.doFadeIn;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.doFadeInOut;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.toFrontHelper;
import cu.jsoft.j_utilsfxlite.subs.SUB_UtilsNotifications;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class MainFormController implements Initializable {
	Node prevNode;
	Node currNode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		doInit();
	}

	public void doInit() {
		currNode = GLOBAL.ccLogo;
		doInitFadeIn();
	}

	private void doInitFadeIn() {
		// Do initial fadeIn:
//		HJRMSFX.setWindowTitle(AppInfo.getTitleString());
		toFrontHelper(GLOBAL.CenterPane, "tipLogo");
		currNode = GLOBAL.ccLogo;
		doFadeIn(currNode, Duration.seconds(1), Duration.seconds(1), 1);
	}

	public void butHomeOnActionHandler(ActionEvent event) {
		if (!currNode.equals(GLOBAL.ccLogo)) {
//			HJRMSFX.setWindowTitle(AppInfo.getTitleString());
			toFrontHelper(GLOBAL.CenterPane, "tipLogo");
			prevNode = currNode;
			currNode = GLOBAL.ccLogo;
			doFadeInOut(prevNode, currNode, Duration.ZERO, Duration.seconds(0.5), Duration.ZERO, Duration.seconds(1));
		}
//		GLOBAL.FXLogoController.updateData();
	}

	public void butConfigOnActionHandler(ActionEvent event) {
		if (!currNode.equals(GLOBAL.ccConfig)) {
//			HJRMSFX.setWindowTitle(AppInfo.getTitleString() + " - Configuración");
			toFrontHelper(GLOBAL.CenterPane, "tipConfig");
			prevNode = currNode;
			currNode = GLOBAL.ccConfig;
			doFadeInOut(prevNode, currNode, Duration.ZERO, Duration.seconds(0.25), Duration.ZERO, Duration.seconds(0.25));
		}
		//GLOBAL.FXConfigController.updateData();
	}

	public void butDBOnActionHandler(ActionEvent event) {
//		HJRMSFX.setWindowTitle(AppInfo.getTitleString() + " - Mantenimiento a BD");

		//run in background thread (REMOVE WHEN FUNCTIONAL):
		new Thread() {

			@Override
			public void run() {
				SUB_UtilsNotifications.doBeep();
			}
		;

	}

	.start();

//		HJRMSFX.CenterPane.getChildren().clear();
//		HJRMSFX.CenterPane.getChildren().add(GLOBAL.ccDB);
	}

	// Handlers for other buttons go here...!!!

	public void butExitOnActionHandler(ActionEvent event) {
		WINDOWGEOM.setWindowLeft(GLOBAL.MainStage.getX());
		WINDOWGEOM.setWindowTop(GLOBAL.MainStage.getY());
		WINDOWGEOM.setWindowWidth(GLOBAL.MainStage.getWidth());
		WINDOWGEOM.setWindowHeight(GLOBAL.MainStage.getHeight());
		WINDOWGEOM.setWindowMaximized(GLOBAL.MainStage.isMaximized());

		GLOBAL.doExit(0);
	}

}
