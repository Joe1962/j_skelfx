/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.global.db2table;

import cu.jsoft.j_dbfx.RS;
import cu.jsoft.j_utilsfx.global.FLAGS;
import static cu.jsoft.j_utilsfx.subs.SUB_UtilsNotifications.echoClassMethodComment;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joe1962
 */
public class RS_SysFieldTitles extends RS {
	private final String SQLSelectByTableName;

	public RS_SysFieldTitles() {
		super();
		SQLSelectAll = "SELECT table_name, field_name, field_title, field_prefwidth, field_datatype "
			+ "FROM sys_field_titles ";//"ORDER BY sys_um.cod ASC"
		SQLSelectByID = "SELECT field_name, field_title, field_prefwidth, field_datatype "
			+ "FROM sys_field_titles WHERE table_name = ? AND field_order = ? ORDER BY field_order ";
		SQLSelectByTableName = "SELECT field_name, field_title, field_prefwidth, field_datatype "
			+ "FROM sys_field_titles WHERE table_name = ? ORDER BY field_order ";
	}

	public void selectByTableName(String TableName) throws SQLException {
		String QuerySQL = SQLSelectByTableName;
		PreparedStatement pstmt;
		pstmt = getMyConn().prepareStatement(QuerySQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		int n = 1;
		pstmt.setString(n++, TableName);
		setRST(getDBConnHandler().doQuery(pstmt));
		getRST().first();
		//QuerySQL = getRST().getStatement().toString();									// DEBUG...
		echoClassMethodComment(pstmt.toString(), FLAGS.isDEBUG(), false);			// DEBUG...
	}

	@Override
	public Object getCurrent() throws SQLException {
		// Remember to call a select* method first...
		TYP_SysFieldTitle MyRow = new TYP_SysFieldTitle();

		if (getRST().getRow() > 0) {
			MyRow.setField_name(getRST().getString("field_name"));
			MyRow.setField_title(getRST().getString("field_title"));
			MyRow.setField_prefwidth(getRST().getInt("field_prefwidth"));
			MyRow.setField_datatype(getRST().getString("field_datatype"));
			return MyRow;
		} else {
			return null;
		}
	}

	@Override
	public boolean appendRow(Object MyRow) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public boolean updateRow(Object MyRow, Object WhereParam) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}


}
