/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.jsoft.j_skelfx.preferences;

import cu.jsoft.j_skelfx.preferences.db.FX_SysDBsController;
import cu.jsoft.j_skelfx.preferences.system.FX_SysSystemController;
import cu.jsoft.j_skelfx.preferences.users.FX_SysUsersController;
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

	//<editor-fold defaultstate="collapsed" desc=" My class-level variables declaration ">
	boolean flagLoadingTabs = false;

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

	//</editor-fold>

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
//		stpConfig.setStyle(CONSTS.cssEtchedBorder);
//		vbxButtons.setStyle(CONSTS.cssEtchedBorder);
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
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/system/FX_SysSystem.fxml"));
		Node MyNode = loader.load();
		FX_SysSystemController c = loader.getController();
		c.updateData();
		return MyNode;
	}

	public Node getDBNode() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/db/FX_SysDBs.fxml"));
		Node MyNode = loader.load();
		FX_SysDBsController c = loader.getController();
		return MyNode;
	}

	// Handle other nodes here...!!!
	public Node getUsersNode() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/users/FX_SysUsers.fxml"));
		Node MyNode = loader.load();
		FX_SysUsersController c = loader.getController();
		return MyNode;
	}


	private void setFirstConfigPanel() {

	}

}
