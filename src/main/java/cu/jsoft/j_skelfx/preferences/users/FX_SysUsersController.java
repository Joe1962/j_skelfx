/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.preferences.users;

import cu.jsoft.j_dbfxlite.exception.SQLx;
import cu.jsoft.j_loginfx.users.RS_users;
import cu.jsoft.j_loginfx.users.TYP_user;
import cu.jsoft.j_skelfx.global.FLAGS;
import cu.jsoft.j_skelfx.global.GLOBAL;
import cu.jsoft.j_skelfx.global.RunTime;
import static cu.jsoft.j_skelfx.global.db2table.SUB_DB2Tables.setupTableView;
import static cu.jsoft.j_utilsfxlite.global.CONSTS.EMPTY_STRING;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.MsgErrorOKFX;
import static cu.jsoft.j_utilsfxlite.subs.SUB_PopupsFX.MsgWarningYesNoFX;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsDateTime.JavaDate2SQLDate;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsDateTime.getTodayDate;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class FX_SysUsersController implements Initializable {

	//<editor-fold defaultstate="collapsed" desc=" My class-level variables declaration ">
	RS_users RS = new RS_users();
	ObservableList<TYP_SysUsersTableRow> lstMaster = FXCollections.observableArrayList();
	boolean flagFillingInputs = false;

	@FXML
	private TitledPane tipUsers;
	@FXML
	private TableView<TYP_SysUsersTableRow> tblMaster;
	@FXML
	private TextField txfName;
	@FXML
	private CheckBox chkAdmin;
	@FXML
	private CheckBox chkActive;
	@FXML
	private TextField txfDateInactive;
	@FXML
	private Label lblConfirm;
	@FXML
	private PasswordField pwdPassword;
	@FXML
	private PasswordField pwdConfirm;
	@FXML
	private CheckBox chkPassword;
	@FXML
	private Label lblPassword;
	@FXML
	private Button butAdd;
	@FXML
	private Button butDel;
	@FXML
	private Button butSave;
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
			setupTableView(GLOBAL.DBConnHandler, tblMaster, "sys_users", RunTime.DebugMode);
			fillTableMaster();
		} catch (SQLException ex) {
			SQLx MySQLxHandler = new SQLx(GLOBAL.DBCONFIG.get(GLOBAL.DBDefaultDB), GLOBAL.MyLargeFontBold, GLOBAL.MyDefaultFont);
			boolean retBool = MySQLxHandler.handleExceptionPG(ex, FLAGS.isDEBUG());
		}
	}

	public void updateData() {
		try {
			fillTableMaster();
		} catch (SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void fillTableMaster() throws SQLException {
		// Fill the rows with lstMaster from sys_users:
		lstMaster.clear();
		tblMaster.getItems().clear();
		//GLOBAL.RSSysCostAreas.selectAll(" ORDER BY cod ", RunTime.DebugMode);
		tblMaster.setItems(lstMaster);
		RS.setDBConnHandler(GLOBAL.DBConnHandler);
		RS.selectAll(" ORDER BY name ");
		boolean retBool = RS.goFirst();
		while (retBool) {
			TYP_user MyTuple = RS.getCurrent();
			//TYP_SysUsersTableRow MyTableRow = new TYP_SysUsersTableRow(MyTuple.getUuid().toString(), MyTuple.getName(), MyTuple.isAdmin());
			TYP_SysUsersTableRow MyTableRow = new TYP_SysUsersTableRow(MyTuple.getName(), MyTuple.isAdmin());
			lstMaster.add(MyTableRow);
			retBool = RS.goNext();
		}

		// Enable changelisteners:
		setupChangeListeners();
		// Enable selectionlisteners:
		setupSelectionListeners();
	}

	private void setupChangeListeners() {
		txfName.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!flagFillingInputs) {
					checkFieldsForButtons();
				}
			}
		});

		pwdPassword.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!flagFillingInputs) {
					checkPasswordConfirmationMatch();
				}
			}
		});

		pwdConfirm.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!flagFillingInputs) {
					checkPasswordConfirmationMatch();
				}
			}
		});
	}

	private void checkFieldsForButtons() {
		// Enable save button only if txfname has content:
		if (txfName.getLength() > 0) {
			butAdd.setDisable(false);
			butSave.setDisable(false);
		} else {
			butAdd.setDisable(true);
			butSave.setDisable(true);
		}
	}

	private void setupSelectionListeners() {
		tblMaster.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TYP_SysUsersTableRow>() {
			@Override
			public void changed(ObservableValue<? extends TYP_SysUsersTableRow> observable, TYP_SysUsersTableRow oldValue, TYP_SysUsersTableRow newValue) {
				if (newValue != null) {
					// TODO: Fill input section from table row:
					flagFillingInputs = true;
					String strName = newValue.getName();
					txfName.setText(strName);
					chkAdmin.setSelected(newValue.getAdmin());
					flagFillingInputs = false;
					butAdd.setDisable(false);
					butDel.setDisable(false);
					butSave.setDisable(true);
				} else {
					butDel.setDisable(true);
				}
				// Multiselect check:
				checkPasswordFieldsEnablement();
				int selCount = tblMaster.getSelectionModel().getSelectedIndices().size();
				if (selCount == 0) {
					butDel.setDisable(true);
				}
				if (selCount == 1) {
				}
				if (selCount > 1) {
					butSave.setDisable(true);
					butDel.setDisable(false);
				}
			}
		});
	}

	private void appendRowFromInputs(String newName, boolean isAdmin, String passw) {
		// Disable Add button to stop double input:
		butAdd.setDisable(true);

		// Get next cod:
		int MyCod;
//		try {
//			MyCod = RS.getSequential(null, RunTime.DebugMode);
//		} catch (SQLException ex) {
//			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//			// TODO: popup error dialog...
//			return;
//		}

		//  Add new row to list:
//		lstMaster.add(new TYP_SysUsersTableRow(MyCod, newName, isActive, isAdmin, inactive_date == null ? "" : inactive_date.toString()));
		// Prep record:
//		TYP_SysUser MyRow = prepRecord(MyCod, newName, isActive, isAdmin, inactive_date, encryptedPass, userSalt);
//		try {
//			// Append to DB table:
//			RS.appendRow(MyRow, RunTime.DebugMode);
//		} catch (SQLException ex) {
//			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//		}

		clearInputSection();
	}

	private void updateRowFromInputs(String newName, boolean isActive, boolean isAdmin, String passw) {
//		// Get cod:
//		int MyCod = tblMaster.getSelectionModel().getSelectedIndex();
//
//		//  Get ID from list:
//		String tmpID = lstMaster.get(MyCod).getId();
//		//  Replace row in list:
//		lstMaster.set(MyCod, new TYP_SysUsersTableRow(tmpID, newName, isAdmin));
//		// Prep record:
//		TYP_user MyRow = prepRecord(tmpID, newName, isAdmin, passw);
//		// Update DB table:
//		try {
//			RS.updateRow(MyRow, RunTime.DebugMode);
//		} catch (SQLException ex) {
//			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//		}
//
//		clearInputSection();
	}

	private TYP_user prepRecord(String MyCod, String newName, boolean isAdmin, String passw) {
		TYP_user MyRow = new TYP_user();
		MyRow.setUuid(UUID.fromString(MyCod));
		MyRow.setName(newName);
		MyRow.setAdmin(isAdmin);
		MyRow.setPassword(passw);
		return MyRow;
	}

	private void clearInputSection() {
		// clear editing section:
		txfName.clear();
		chkActive.setSelected(false);
		chkAdmin.setSelected(false);
		txfDateInactive.clear();
		chkPassword.setSelected(false);
		pwdPassword.clear();
		pwdConfirm.clear();

		// Set focus to first element of edit section, for next input:
		txfName.requestFocus();
	}

	private Date TexttoDate(String strDate) {
		// TODO: temporary...
		return JavaDate2SQLDate(getTodayDate());
	}

	private void checkPasswordFieldsEnablement() {
		if (chkPassword.isSelected() && tblMaster.getSelectionModel().getSelectedIndices().size() <= 1) {
			enablePasswordInputArea(false);
		} else {
			disablePasswordInputArea(false);
		}
	}

	private void checkPasswordConfirmationMatch() {
		String bgStyle = EMPTY_STRING;
		//"-fx-border-color: red ; -fx-border-width: 2px ;"
		//"-fx-background-color: white;"
		if (pwdPassword.getText().length() > 0 || pwdConfirm.getText().length() > 0) {
			if (pwdPassword.getText().equals(pwdConfirm.getText())) {
				// passwords match = green background:
				bgStyle = "-fx-border-color: green ; -fx-border-width: 2px ;";
				butSave.setDisable(false);
			} else {
				// passwords do not match = red background:
				bgStyle = "-fx-border-color: red ; -fx-border-width: 2px ;";
				butSave.setDisable(true);
			}
		} else {
			// zero-length fields = normal background:
			//bgStyle = "-fx-border-color: black ; -fx-border-width: 2px ;";
		}
		pwdPassword.setStyle(bgStyle);
		pwdConfirm.setStyle(bgStyle);
	}

	private void disablePasswordInputArea(boolean CheckBoxToo) {
		if (CheckBoxToo) {
			chkPassword.setDisable(true);
		}
		pwdPassword.setDisable(true);
		pwdConfirm.setDisable(true);
		lblPassword.setDisable(true);
		lblConfirm.setDisable(true);
	}

	private void enablePasswordInputArea(boolean CheckBoxToo) {
		if (CheckBoxToo) {
			chkPassword.setDisable(false);
		}
		pwdPassword.setDisable(false);
		pwdConfirm.setDisable(false);
		lblPassword.setDisable(false);
		lblConfirm.setDisable(false);
	}

	private void butSaveHelper(String passw) {
		Date tmpDate = null;
		// TODO: check for user inactivation, ask for confirmation and then save inactive date...
		if (!chkActive.isSelected()) {
			boolean retBool = MsgWarningYesNoFX(GLOBAL.MainStage, null, "No ha marcado \"activo\":", "Seguro que desea inactivar este usuario?");
			if (retBool) {
				// TODO: change txfDate to datepicker and somehow decide if it was changed (set)
				// otherwise, set today's date.
				tmpDate = JavaDate2SQLDate(getTodayDate());
				txfDateInactive.setText(tmpDate.toString());
			} else {
				txfDateInactive.clear();
				return;
			}
		}
		if (tblMaster.getSelectionModel().isEmpty()) {
			appendRowFromInputs(txfName.getText(), chkAdmin.isSelected(), passw);
		} else {
			updateRowFromInputs(txfName.getText(), chkActive.isSelected(), chkAdmin.isSelected(), passw);
		}
	}

	@FXML
	private void txfNameOnActionHandler(ActionEvent event) {
	}

	@FXML
	private void chkAdminOnActionHandler(ActionEvent event) {
		checkFieldsForButtons();
	}

	@FXML
	private void chkActiveOnActionHandler(ActionEvent event) {
		checkFieldsForButtons();
	}

	@FXML
	private void chkPasswordOnActionhandler(ActionEvent event) {
		checkPasswordFieldsEnablement();
	}

	@FXML
	private void butAddOnActionHandler(ActionEvent event) {
		clearInputSection();
		tblMaster.getSelectionModel().clearSelection();
		checkPasswordFieldsEnablement();
	}

	@FXML
	private void butSaveOnActionHandler(ActionEvent event) {
		byte[] userSalt = null;
		byte[] encryptedPass = null;

		butSave.setDisable(true);

		if (pwdPassword.isDisabled()) {
			butSaveHelper(null);
		} else {
			if (pwdPassword.getText().length() > 0 && pwdConfirm.getText().length() > 0) {
				if (pwdPassword.getText().equals(pwdConfirm.getText())) {
//					encryptedPass = SUB_PasswordHandler.genUserPass(pwdPassword.getText().toCharArray(), userSalt);
//					butSaveHelper(passw);
				} else {
					// TODO: Warning, passwords don't match...
					MsgErrorOKFX(GLOBAL.MainStage, null, null, "El password no coincide con la confirmación...");
				}
			}
		}
	}

	@FXML
	private void butDelOnActionHandler(ActionEvent event) {
		ObservableList<TYP_SysUsersTableRow> listTMP = (ObservableList<TYP_SysUsersTableRow>) tblMaster.getSelectionModel().getSelectedItems();
		if (listTMP.size() > 0) {
			for (TYP_SysUsersTableRow obj : listTMP) {
				lstMaster.remove(obj);
//				try {
//					RS.setMyCod(obj.getId());
//					RS.deleteRow(RunTime.DebugMode);
//				} catch (SQLException ex) {
//					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//				}
			}
		}
	}

	@FXML
	private void txfDateInactiveOnActionHandler(ActionEvent event) {
	}

	@FXML
	private void pwdPasswordOnActionHandler(ActionEvent event) {
	}

	@FXML
	private void pwdConfirmOnActionhandler(ActionEvent event) {
	}

}
