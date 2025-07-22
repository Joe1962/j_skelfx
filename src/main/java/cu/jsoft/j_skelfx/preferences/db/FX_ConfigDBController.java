/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.preferences.db;

import cu.jsoft.j_dbfxlite.types.TYP_ConfigDBJSON;
import cu.jsoft.j_skelfx.global.CONSTS;
import static cu.jsoft.j_skelfx.global.GLOBAL.DBCONFIG;
import cu.jsoft.j_skelfx.global.interfaces.CallbackForTabs;
import cu.jsoft.j_utilsfxlite.security.SUB_Protect;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.cmbOnScrollHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;

/**
 * FXML Controller class
 *
 * @author informaticos
 */
public class FX_ConfigDBController implements Initializable {
	private static SUB_Protect Protection = new SUB_Protect();
	private final ObservableList<String> DBDriverList = FXCollections.observableArrayList("postgresql", "derby");
	private CallbackForTabs myCallbackForTabs;
	private String MyDBNICK;
	private String MyDBNICKOLD;
	private TYP_ConfigDBJSON thisDBConfig;

	@FXML
	private TextField txfDBConfName;
	@FXML
	private ComboBox<String> cmbDBType;
	@FXML
	private TextField txfDBPath;
	@FXML
	private TextField txfDBServer;
	@FXML
	private TextField txfDBName;
	@FXML
	private TextField txfDBUser;
	@FXML
	private PasswordField pafDBPassword;
	@FXML
	private TextField txfDBPort;
	@FXML
	private Button butSave;
	@FXML
	private Button butSaveChangeTo;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
//		DBDriverList.add("postgreSQL");
//		DBDriverList.add("Derby");
		cmbDBType.setItems(DBDriverList);
		cmbDBType.getSelectionModel().selectFirst();
	}

	public void loadData(TYP_ConfigDBJSON MyDBConfig) {
		thisDBConfig = MyDBConfig;
		MyDBNICK = thisDBConfig.getDBNICK();
		MyDBNICKOLD = MyDBNICK;
		myCallbackForTabs.callbackSetTabName(MyDBNICK, MyDBConfig.getDBNICK());

		txfDBConfName.setText(thisDBConfig.getDBNICK());
		cmbDBType.getSelectionModel().select(thisDBConfig.getDBDRIVER());
		txfDBPath.setText(thisDBConfig.getDBPATH());
		txfDBServer.setText(thisDBConfig.getDBHOST());
		txfDBPort.setText(String.valueOf(thisDBConfig.getDBPORT()));
		txfDBName.setText(thisDBConfig.getDBNAME());
		txfDBUser.setText(thisDBConfig.getDBUSER());

		String DBPASSEncrypted = thisDBConfig.getDBPASS() == null ? "" : thisDBConfig.getDBPASS().trim();
		String DBPASS = Protection.getDecryptedString(DBPASSEncrypted, new StringBuffer(CONSTS.SecKeyStr).reverse().toString(), CONSTS.iv);
		pafDBPassword.setText(DBPASS);
	}

	@FXML
	private void cmbDBTypeOnScrollHandler(ScrollEvent event) {
		cmbOnScrollHandler(event, cmbDBType);
	}

	@FXML
	private void cmbDBTypeOnActionHandler(ActionEvent event) {
	}

	@FXML
	void butSaveOnActionHandler(ActionEvent event) {
		if (saveDBConfig()) {
			myCallbackForTabs.callbackSaveTabContents();
		} else {
			String title = "AVISO";
			String content = "El nombre de la BD ya existe...";

			// TODO: Convert to JavaFX...
//			SUB_Popups.MsgWarningOK(dialogsParent, title, content);

			// Restore NICKS:
			MyDBNICK = MyDBNICKOLD;
			txfDBConfName.setText(MyDBNICK);
		}
	}

	@FXML
	void butSaveChangeToOnActionHandler(ActionEvent event) {
		if (saveDBConfig()) {
			myCallbackForTabs.callbackSaveTabContents();
			// TODO: Now connect to this DB:

		} else {
			String title = "AVISO";
			String content = "El nombre de la BD ya existe...";

			// TODO: Convert to JavaFX...
//			SUB_Popups.MsgWarningOK(dialogsParent, title, content);

			// Restore NICKS:
			MyDBNICK = MyDBNICKOLD;
			txfDBConfName.setText(MyDBNICK);
		}
	}

	private Boolean isExistingDBNICK(String MyDBNICK) {
		if (DBCONFIG.isEmpty()) {
			return null;
		} else {
			for (TYP_ConfigDBJSON aDB : DBCONFIG) {
				if (MyDBNICK.equals(aDB.getDBNICK())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean saveDBConfig() {
		TYP_ConfigDBJSON MyDBConfig = new TYP_ConfigDBJSON();
		MyDBConfig.setDBNICK(txfDBConfName.getText() == null ? "default_value" : txfDBConfName.getText().trim());
		MyDBConfig.setDBCONNTYPE("jdbc");
		MyDBConfig.setDBDRIVER(cmbDBType.getSelectionModel().getSelectedItem() == null ? "PostgreSQL" : cmbDBType.getSelectionModel().getSelectedItem().trim());
		MyDBConfig.setDBPATH(txfDBPath.getText() == null ? "" : txfDBPath.getText().trim());
		MyDBConfig.setDBHOST(txfDBServer.getText() == null ? "" : txfDBServer.getText().trim());
		MyDBConfig.setDBPORT(txfDBPort.getText() == null ? 5432 : Integer.parseInt(txfDBPort.getText().trim()));
		MyDBConfig.setDBNAME(txfDBName.getText() == null ? "" : txfDBName.getText().trim());
		MyDBConfig.setDBUSER(txfDBUser.getText() == null ? "" : txfDBUser.getText().trim());

		String DBPASS = pafDBPassword.getText() == null ? "" : pafDBPassword.getText().trim();
		String DBPASSEncrypted = Protection.getEncryptedString(DBPASS, new StringBuffer(CONSTS.AESSalt).reverse().toString(), new StringBuffer(CONSTS.SecKeyStr).reverse().toString(), CONSTS.iv);
		MyDBConfig.setDBPASS(DBPASSEncrypted);

		// Set tab name to DBNICK:
		MyDBNICKOLD = MyDBNICK;
		MyDBNICK = MyDBConfig.getDBNICK();

		// Check if existing DBNICK when changed:
		boolean retUpdate;
		if (!MyDBConfig.getDBNICK().equals(MyDBNICKOLD)) {
			Boolean retBool = isExistingDBNICK(MyDBConfig.getDBNICK());

			if (retBool == null) {
				// No previous DB configs:
				retUpdate = true;
			} else if (retBool == false) {
				// DBNICK is new, append:
				retUpdate = true;
			} else {
				// DBNICK exists:
				retUpdate = false;
			}
		} else {
			retUpdate = true;
		}

		if (retUpdate) {
			thisDBConfig = MyDBConfig;
			myCallbackForTabs.callbackSetTabName(MyDBNICKOLD, MyDBNICK);
			return true;
		} else {
			return false;
		}
	}

	public void setCallbackForTabs(CallbackForTabs callMeBackForTabs) {
		myCallbackForTabs = callMeBackForTabs;
	}

	public void setup(int MyDBIndex, boolean b) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	public TYP_ConfigDBJSON getThisDBConfig() {
		return thisDBConfig;
	}

	public void setThisDBConfig(TYP_ConfigDBJSON thisDBConfig) {
		this.thisDBConfig = thisDBConfig;
	}

	public String getMyDBNICK() {
		return MyDBNICK;
	}

	public void setMyDBNICK(String MyDBNICK) {
		this.MyDBNICK = MyDBNICK;
	}

	public String getMyDBNICKOLD() {
		return MyDBNICKOLD;
	}

	public void setMyDBNICKOLD(String MyDBNICKOLD) {
		this.MyDBNICKOLD = MyDBNICKOLD;
	}

}
