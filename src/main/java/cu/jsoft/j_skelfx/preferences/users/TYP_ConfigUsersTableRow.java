/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.preferences.users;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class TYP_ConfigUsersTableRow {
//	private SimpleStringProperty id = new SimpleStringProperty();
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleBooleanProperty admin = new SimpleBooleanProperty();

	public TYP_ConfigUsersTableRow() {
	}

	public TYP_ConfigUsersTableRow(String theName, boolean isAdmin) {
//	public TYP_ConfigUsersTableRow(String id, String name, boolean admin) {
//		this.id.set(id);
		name.set(theName);
		admin.set(isAdmin);
	}

//	public String getId() {
//		return this.id.get();
//	}

//	public void setId(String id) {
//		this.id.set(id);
//	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public boolean getAdmin() {
		return this.admin.get();
	}

	public void setAdmin(boolean admin) {
		this.admin.set(admin);
	}

}
