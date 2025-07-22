/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.preferences.db;

import cu.jsoft.j_dbfxlite.types.TYP_ConfigDBJSON;
import cu.jsoft.j_skelfx.global.CONSTS;
import cu.jsoft.j_skelfx.global.GLOBAL;
import cu.jsoft.j_skelfx.global.interfaces.CallbackForTabs;
import static cu.jsoft.j_skelfx.preferences.CLS_Preferences.saveConfigJSON;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class FX_ConfigDBsController implements Initializable, CallbackForTabs {
	HashMap<String, FX_ConfigDBController> nestedControllers = new HashMap<>();
	HashMap<String, TYP_ConfigDBJSON> DBConfigs = new HashMap<>();

	@FXML
	private TitledPane tipDB;
	@FXML
	private TabPane tabConfigDBs;

	@Override
	public void callbackSetTabName(String oldName, String newName) {
		for (Tab thisTab : tabConfigDBs.getTabs()) {
			if (thisTab.getText().equals(oldName)) {
				thisTab.setText(newName);
				thisTab.setUserData(oldName);
			}
		}
	}

	@Override
	public void callbackSaveTabContents() {
		SaveTabContentsHelper();
	}



	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			setupTabConfigDBs();
		} catch (IOException ex) {
			Logger.getLogger(FX_ConfigDBsController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void updateData() {
	}

	public void setupTabConfigDBs() throws IOException {
		// TODO: Load DB configs...
		if (!GLOBAL.DBCONFIG.isEmpty()) {
			for (TYP_ConfigDBJSON aDB : GLOBAL.DBCONFIG) {
				AddDB(aDB);
			}
		}
	}

	private void SaveTabContentsHelper() {
		GLOBAL.DBCONFIG.clear();

		for (Tab thisTab : tabConfigDBs.getTabs()) {
			// DBNICK could have changed, so use DBNICKOLD stored in tab's user data:
			String tabname = String.valueOf(thisTab.getUserData());

			TYP_ConfigDBJSON MyDBConfig = nestedControllers.get(tabname).getThisDBConfig();

			GLOBAL.DBCONFIG.add(new TYP_ConfigDBJSON(
				MyDBConfig.getDBNICK(),
				MyDBConfig.getDBCONNTYPE(),
				MyDBConfig.getDBDRIVER(),
				MyDBConfig.getDBPATH(),
				MyDBConfig.getDBHOST(),
				MyDBConfig.getDBPORT(),
				MyDBConfig.getDBNAME(),
				MyDBConfig.getDBUSER(),
				MyDBConfig.getDBPASS()));
		}

		saveConfigJSON();
	}

	private void AddDB(TYP_ConfigDBJSON aDB) throws IOException {
		Tab tabConfigDB = new Tab();
		tabConfigDB.setText(aDB.getDBNICK());

		// Save aDB to map:
		DBConfigs.put(aDB.getDBNICK(), aDB);

		// Load the nested FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preferences/db/FX_ConfigDB.fxml"));

		// Load fxml and call loadData:
		Parent content = loader.load();

		// Get the nested controller
		FX_ConfigDBController nestedController = loader.getController();

		// Save nestedController to map:
		nestedControllers.put(aDB.getDBNICK(), nestedController);

		// Set tab content:
		tabConfigDB.setContent(content);

		// Add tab to container:
		tabConfigDBs.getTabs().add(tabConfigDB);

		// Set this as callback for tabs stuff:
		nestedController.setCallbackForTabs(this);

		// Load the data for this DB:
		nestedController.loadData(aDB);
	}

	private void DelCurrDB() {
		//First get current tab:
//		int currTabIndex = tabConfigDBs.getSelectionModel().getSelectedIndex();
		Tab currTab = tabConfigDBs.getSelectionModel().getSelectedItem();

		// TODO: Then get DB nick:
		String DBNICK = currTab.getText();

		// Delete controller from map...
		nestedControllers.remove(DBNICK);

		// Delete current DB tab...
//		tabConfigDBs.getTabs().remove(currTabIndex);
		tabConfigDBs.getTabs().remove(currTab);

		// TODO: now delete DB from GLOBAL DB config list...
		// Actually, just do the whole save all thingy again...
		SaveTabContentsHelper();
		// TODO: now save updated GLOBAL DB config list...
		// Should be already done in previous step...
	}

	@FXML
	private void butSystemDBsAddOnActionHandler(ActionEvent event) throws IOException {
		TYP_ConfigDBJSON aDB = new TYP_ConfigDBJSON();
		// TODO: set defaults...
		aDB.setDBNICK("Nueva BD");
		aDB.setDBCONNTYPE(CONSTS.DEFAULTDBDRIVER);
		aDB.setDBDRIVER(CONSTS.DEFAULTDBTYPE);
		aDB.setDBHOST(CONSTS.DEFAULTDBHOST);
		aDB.setDBPORT(CONSTS.DEFAULTDBPORT);
		aDB.setDBNAME(CONSTS.DEFAULTDBNAME);
		aDB.setDBUSER(CONSTS.DEFAULTDBUSER);

		AddDB(aDB);
		// TODO: select new tab:
		tabConfigDBs.getSelectionModel().selectLast();
	}

	@FXML
	private void butSystemDBsDelOnActionHandler(ActionEvent event) throws IOException {
		//DelPrinter(tabConfigPRNs.getSelectionModel().getSelectedIndex());
		DelCurrDB();
	}

}
