/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.cenpalab.siguapa.statusbar;

import static cu.jsoft.j_utilsfxlite.global.CONSTS.EMPTY_STRING;
import static cu.jsoft.j_utilsfxlite.global.CONSTS.URL_LED22Green;
import static cu.jsoft.j_utilsfxlite.global.CONSTS.URL_LED22Red;
import static cu.jsoft.j_utilsfxlite.global.CONSTS.URL_LED22Yellow;
import cu.jsoft.j_utilsfxlite.subs.SUB_UtilsNotifications;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import cu.cenpalab.siguapa.global.CONSTS;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class FX_StatusBarController implements Initializable {

	//<editor-fold defaultstate="collapsed" desc=" My class-level variables declaration ">
	@FXML
	private StackPane stkStatusBar1;
	@FXML
	private StackPane stkStatusBar2;
//	@FXML
//	private ComboBox<TYP_SysCostArea> cmbMaster;
	@FXML
	private StackPane stkStatusBar3;
	@FXML
	private Label lblStatusBarLED;
	@FXML
	private Label lblStatusBar;
	@FXML
	private GridPane grdDBLed;
	//</editor-fold>

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		stkStatusBar1.setStyle(CONSTS.cssEtchedBorder);
		stkStatusBar2.setStyle(CONSTS.cssEtchedBorder);
		grdDBLed.setStyle(CONSTS.cssEtchedBorder);
		//stkStatusBar3.setStyle(CONSTS.cssEtchedBorder);
		//lblStatusBarLED.setStyle(CONSTS.cssEtchedBorder);
//		lblStatusBarLED.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(URL_LED22Red))));

		fillComboMaster();
	}

	private void fillComboMaster() {
//		cmbMaster.setConverter(new StringConverter<TYP_SysCostArea>() {
//			@Override
//			public String toString(TYP_SysCostArea object) {
//				return object.getName();
//			}
//
//			@Override
//			public TYP_SysCostArea fromString(String string) {
//				return null;
//			}
//		});
//
//		for (Map.Entry<String, Integer> en : GLOBAL.mapAreas.ByName.entrySet()) {
//			TYP_SysCostArea MyRow = new TYP_SysCostArea();
//			MyRow.setId(en.getValue());
//			MyRow.setName(en.getKey());
//			cmbMaster.getItems().add(MyRow);
//		}
	}

	public void setStatusText(final String MyStatusText, final double delay, boolean beep) {
		lblStatusBar.setText(MyStatusText);
		if (beep) {
			SUB_UtilsNotifications.doBeep();
		}
		if (delay > 0) {
			Timeline timeline = new Timeline(new KeyFrame(
				Duration.seconds(delay),
				ae -> lblStatusBar.setText(EMPTY_STRING)));
			timeline.play();
		}
	}

	public String getStatusText() {
		return lblStatusBar.getText();
	}

	public void setDBLED(final CONSTS.LEDState MyLEDState) {
		switch (MyLEDState) {
			case RED:
				lblStatusBarLED.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(URL_LED22Red))));
				break;
			case YELLOW:
				lblStatusBarLED.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(URL_LED22Yellow))));
				break;
			case GREEN:
				lblStatusBarLED.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(URL_LED22Green))));
				break;
		}
	}

	@FXML
	private void cmbMasterOnActionhandler(ActionEvent event) {
//		GLOBAL.CurrArea.setCurrArea(GLOBAL.mapAreas.ByName.get(cmbMaster.getSelectionModel().getSelectedItem().getName()));
	}

	@FXML
	private void cmbMasterOnScrollHandler(ScrollEvent event) {
//		cmbOnScrollHandler(event, cmbMaster);
	}

}
