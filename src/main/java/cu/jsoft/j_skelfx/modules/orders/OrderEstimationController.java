package cu.jsoft.j_skelfx.modules.orders;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OrderEstimationController {

	@FXML
	private ComboBox<String> feedTypeCombo;
	@FXML
	private DatePicker deliveryDatePicker;
	@FXML
	private TextField currentStockField, demandField, orderField;

	public void initialize() {
		feedTypeCombo.getItems().addAll("Grower Feed", "Breeder Feed");

		feedTypeCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				updateEstimation();
			}
		});
	}

	private void updateEstimation() {
		// Mock logic (replace with DB calls)
		currentStockField.setText("85 sacks");
		demandField.setText("120 sacks");
		orderField.setText("35 sacks (⚠️ +5 safety)");
	}

	@FXML
	private void handleGenerateOrder() {
		System.out.println("Order generated for: " + feedTypeCombo.getValue());
	}

}
