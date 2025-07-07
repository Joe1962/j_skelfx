/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.preferences.db;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * @author joe1962
 */
public class CLS_DBPrefs {
	
	public Node getDBNode() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/preferences/db/FX_SysDBs.fxml"));
		Node MyNode = loader.load();
		FX_SysDBsController c = loader.getController();
		return MyNode;
	}

}
