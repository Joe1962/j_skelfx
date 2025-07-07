/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx;

import cu.jsoft.j_skelfx.global.GLOBAL;
import static cu.jsoft.j_skelfx.global.GLOBAL.WINDOWGEOM;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.doFadeIn;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.doFadeInOut;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.toFrontHelper;
import cu.jsoft.j_utilsfxlite.subs.SUB_UtilsNotifications;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class MainFormController implements Initializable {
//	Node prevNode;
//	Node currNode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		doInit();
	}

	public void doInit() {
		GLOBAL.currNode = GLOBAL.ccLogo;
		doInitFadeIn();
	}

	private void doInitFadeIn() {
		// Do initial fadeIn:
		GLOBAL.MainStage.setTitle(AppInfo.getTitleString());
		doFadeIn(GLOBAL.currNode, Duration.seconds(0.25), Duration.seconds(1), 1);
		toFrontHelper(GLOBAL.CenterPane, "tipLogo");
	}

	private void setAllTransparent() {
		for (int i = 0; i < GLOBAL.CenterPane.getChildrenUnmodifiable().size(); i++) {
			GLOBAL.CenterPane.getChildren().get(i).setOpacity(0);
		}
	}

	public void butHomeOnActionHandler(ActionEvent event) {
		if (!GLOBAL.currNode.equals(GLOBAL.ccLogo)) {
			GLOBAL.MainStage.setTitle(AppInfo.getTitleString());
			if (GLOBAL.prevNode != null) System.out.println("pre prevnode: " + GLOBAL.prevNode.getId());
			System.out.println("pre currnode: " + GLOBAL.currNode.getId());
			GLOBAL.prevNode = GLOBAL.currNode;
			GLOBAL.currNode = GLOBAL.ccLogo;
			System.out.println("pos prevnode: " + GLOBAL.prevNode.getId());
			System.out.println("pos currnode: " + GLOBAL.currNode.getId());
			System.out.println("ccLogo ID: " + GLOBAL.ccLogo.getId());

			doFadeInOut(GLOBAL.prevNode, GLOBAL.currNode, Duration.ZERO, Duration.seconds(0.5), Duration.ZERO, Duration.seconds(1));

			toFrontHelper(GLOBAL.CenterPane, "tipLogo");
		}
//		GLOBAL.FXLogoController.updateData();
	}

	public void butConfigOnActionHandler(ActionEvent event) {
		if (!GLOBAL.currNode.equals(GLOBAL.ccConfig)) {
			GLOBAL.MainStage.setTitle(AppInfo.getTitleString() + " - Configuración");
			if (GLOBAL.prevNode != null) System.out.println("pre prevnode: " + GLOBAL.prevNode.getId());
			System.out.println("pre currnode: " + GLOBAL.currNode.getId());
			GLOBAL.prevNode = GLOBAL.currNode;
			GLOBAL.currNode = GLOBAL.ccConfig;
			System.out.println("pos prevnode: " + GLOBAL.prevNode.getId());
			System.out.println("pos currnode: " + GLOBAL.currNode.getId());
			System.out.println("ccConfig ID: " + GLOBAL.ccConfig.getId());

			doFadeInOut(GLOBAL.prevNode, GLOBAL.currNode, Duration.ZERO, Duration.seconds(0.25), Duration.ZERO, Duration.seconds(0.25));

			toFrontHelper(GLOBAL.CenterPane, "tipConfig");
//			GLOBAL.ccConfig.toFront();
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
