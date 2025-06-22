/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.cenpalab.siguapa.preferences.system;

import cu.cenpalab.siguapa.global.GLOBAL;
import cu.jsoft.j_utilsfxlite.global.FLAGS;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ScrollEvent;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class FX_SysSystemController implements Initializable {

	//<editor-fold defaultstate="collapsed" desc=" My class-level variables declaration ">
	@FXML
	private TitledPane tipSystem;
	@FXML
	private CheckBox chkGeneralBeep;
	@FXML
	private CheckBox chkGeneralTouch;
	@FXML
	private CheckBox chkGeneralWebservice;
	@FXML
//	private TabPane tabConfigPRNs;
	private TabPane tabConfigDBs;
	@FXML
	private Button butSystemPrintersAdd;
	@FXML
	private Button butSystemPrintersDel;
	@FXML
	private ChoiceBox<String> chbThemes;
	@FXML
	private ChoiceBox<String> chbStyles;
	//</editor-fold>

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setupStylesChoiceBox();
		loadSystemTabGeneral();
//		loadSystemTabPrinters();
	}

	public void updateData() {
	}

	private void loadSystemTabGeneral() {
//		LoadSettingsApplication();
		chkGeneralBeep.setSelected(FLAGS.isBEEP());
//		chkGeneralTouch.setSelected(FLAGS.isTOUCHSCREEN());
//		chkGeneralWebservice.setSelected(FLAGS.isWEBSERVICE());
	}

	private void setupStylesChoiceBox() {
		ObservableList choiceBoxItems = FXCollections.observableArrayList(
			"modena light",
			"modena dark",
			"caspian light",
			"caspian dark",
			"cupertino light",
			"cupertino dark",
			"nord light",
			"nord dark",
			"dracula"
		);
		chbStyles.setItems(choiceBoxItems);

		chbStyles.setValue(GLOBAL.selectedStyle);
		//chbStyles.getSelectionModel().(GLOBAL.selectedStyle);

		chbStyles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			GLOBAL.setStyle(newValue.toString());
			//SaveSettingsApplication();
		});
		chbStyles.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				// TODO...
				//chbOnScrollHandler(event, chbStyles);
			}
		});
//		chbStyles.getSelectionModel().select(choiceBoxItems.indexOf(GLOBAL.defaultStyle));
	}

	@FXML
	private void chkGeneralBeepOnActionHandler(ActionEvent event) {
		FLAGS.setBEEP(chkGeneralBeep.isSelected());
//		SaveSettingsApplication();
	}

	@FXML
	private void chkGeneralTouchOnActionHandler(ActionEvent event) {
//		FLAGS.setTOUCHSCREEN(chkGeneralTouch.isSelected());
//		SaveSettingsApplication();
	}

	@FXML
	private void chkGeneralWebserviceOnActionHandler(ActionEvent event) {
//		FLAGS.setWEBSERVICE(chkGeneralWebservice.isSelected());
//		SaveSettingsApplication();
	}

}
