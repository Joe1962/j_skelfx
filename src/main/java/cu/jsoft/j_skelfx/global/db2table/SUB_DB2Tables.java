/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.global.db2table;

import cu.jsoft.j_dbfxlite.DBConnectionHandler;
import cu.jsoft.j_skelfx.global.CONSTS;
import static cu.jsoft.j_utilsfxlite.subs.SUB_UtilsFX.getTableColumnTextAlignment;
import java.sql.SQLException;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author joe1962
 */
public class SUB_DB2Tables {

	public static void setupTableView(DBConnectionHandler DBConnHandler, TableView theTableView, String dbtable, boolean debugMode) throws SQLException {
		// Generate the columns from sys_field_titles:
		RS_SysFieldTitles RSSysFieldTitles = new RS_SysFieldTitles();
		RSSysFieldTitles.setDBConnHandler(DBConnHandler);
		RSSysFieldTitles.selectByTableName(dbtable);
		boolean retBool = true;
		theTableView.getColumns().clear();
		while (retBool) {
			TYP_SysFieldTitle MySysFieldTitle = (TYP_SysFieldTitle) RSSysFieldTitles.getCurrent();
			String fieldTitle = MySysFieldTitle.getField_title();
			if (fieldTitle != null && !fieldTitle.equals(CONSTS.EMPTY_STRING)) {
				TableColumn col = new TableColumn(MySysFieldTitle.getField_title());
				col.setPrefWidth(MySysFieldTitle.getField_prefwidth());
				col.setCellValueFactory(new PropertyValueFactory(MySysFieldTitle.getField_name()));
				col.setStyle(getTableColumnTextAlignment(MySysFieldTitle.getField_datatype()));
				col.setEditable(false);
				theTableView.getColumns().add(col);
			}
			retBool = RSSysFieldTitles.goNext();
		}
		theTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

}
