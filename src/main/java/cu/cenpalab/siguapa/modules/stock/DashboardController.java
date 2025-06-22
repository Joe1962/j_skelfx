package cu.cenpalab.siguapa.modules.stock;

import cu.cenpalab.siguapa.global.GLOBAL;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class DashboardController {

	@FXML
	private TableView<stock_model> stockTable;
	@FXML
	private ListView<String> alertList;

	public void initialize() {
		// Load data from database
		stockTable.getItems().addAll(
			new stock_model("Grower Feed", 85, "Normal"),
			new stock_model("Breeder Feed", 3, "Critical")
		);

		alertList.getItems().addAll(
			"⚠️ Low: Grower Feed (12 sacks left)",
			"⚠️ Critical: Breeder Feed (3 sacks left)"
		);
	}

	@FXML
	private void openConsumptionScreen() {
		// Switch to consumption.fxml
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/consumption.fxml"));
		} catch (IOException ex) {
			System.getLogger(DashboardController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
		}
		GLOBAL.MainStage.setScene(new Scene(root));
	}

	@FXML
	private void openOrderEstimationScreen() {
		// Switch to order_estimation.fxml
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/order_estimation.fxml"));
		} catch (IOException ex) {
			System.getLogger(DashboardController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
		}
		GLOBAL.MainStage.setScene(new Scene(root));
	}

}
