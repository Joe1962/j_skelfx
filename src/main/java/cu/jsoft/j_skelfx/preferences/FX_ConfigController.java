/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.jsoft.j_skelfx.preferences;

import cu.jsoft.j_skelfx.preferences.db.FX_ConfigDBsController;
import cu.jsoft.j_skelfx.preferences.system.FX_ConfigSystemController;
import cu.jsoft.j_skelfx.preferences.users.FX_ConfigUsersController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class FX_ConfigController implements Initializable {

	private boolean flagLoadingTabs = false;
	private FX_ConfigSystemController ctlr_system;
	private FX_ConfigDBsController ctlr_db;
//	private FX_ConfigCostAreasController ctlr_cost_areas;
//	private FX_ConfigCostCentersController ctlr_cost_centers;
//	private FX_ConfigMenuGroupsController ctlr_menu_groups;
//	private FX_ConfigCurrencyController ctlr_currency;
//	private FX_ConfigUMController ctlr_units;
	private FX_ConfigUsersController ctlr_users;

	@FXML
	private BorderPane tipConfig;
	@FXML
	private Tab tabSystem;
	@FXML
	private Tab tabDB;
	@FXML
	private Tab tabUsers;
	@FXML
	private Tab tabPrinters;
	@FXML
	private Tab tabCostArea;
	@FXML
	private Tab tabCostCenter;
	@FXML
	private Tab tabMenuGroups;
	@FXML
	private Tab tabCurrency;
	@FXML
	private Tab tabUnits;

	// Declare other tabs here...!!!


	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			setupTabs();
		} catch (IOException ex) {
			Logger.getLogger(FX_ConfigController.class.getName()).log(Level.SEVERE, null, ex);
		}
//		stpConfig.setStyle(CONSTS.cssEtchedBorder);
//		vbxButtons.setStyle(CONSTS.cssEtchedBorder);
	}

	public void updateData() {
		// TODO: call updateData on all dependent controllers...
		ctlr_system.updateData();
		ctlr_db.updateData();
//		ctlr_cost_areas.updateData();
//		ctlr_cost_centers.updateData();
//		ctlr_menu_groups.updateData();
//		ctlr_currency.updateData();
//		ctlr_units.updateData();
		ctlr_users.updateData();
	}

	public void setupTabs() throws IOException {
		flagLoadingTabs = true;

		tabSystem.setContent(getSystemNode());
		tabDB.setContent(getDBNode());
		tabUsers.setContent(getUsersNode());

		// Se up other tabs here...!!!

		flagLoadingTabs = false;
	}

	public Node getSystemNode() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/system/FX_ConfigSystem.fxml"));
		Node MyNode = loader.load();
		ctlr_system = loader.getController();
		ctlr_system.updateData();
		return MyNode;
	}

	public Node getDBNode() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/db/FX_ConfigDBs.fxml"));
		Node MyNode = loader.load();
		ctlr_db = loader.getController();
		ctlr_db.updateData();
		return MyNode;
	}

	public Node getUsersNode() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/users/FX_ConfigUsers.fxml"));
		Node MyNode = loader.load();
		ctlr_users = loader.getController();
		ctlr_users.updateData();
		return MyNode;
	}


	// Handle other nodes here...!!!

	private void setFirstConfigPanel() {

	}

	/**
	 * @return the ctlr_system
	 */
	public FX_ConfigSystemController getCtlr_system() {
		return ctlr_system;
}

	/**
	 * @return the ctlr_db
	 */
	public FX_ConfigDBsController getCtlr_db() {
		return ctlr_db;
	}

	/**
	 * @return the ctlr_users
	 */
	public FX_ConfigUsersController getCtlr_users() {
		return ctlr_users;
	}

}
