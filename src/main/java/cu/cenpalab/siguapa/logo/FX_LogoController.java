/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.cenpalab.siguapa.logo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import cu.cenpalab.siguapa.global.GLOBAL;

/**
 * FXML Controller class
 *
 * @author joe1962
 */
public class FX_LogoController implements Initializable {

	@FXML
	private StackPane tipLogo;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tipLogo.setOpacity(100);
		Color c = Color.DARKGREY;
//		String hex = String.format("#%02X%02X%02X",
//			(int) (c.getRed() * 255),
//			(int) (c.getGreen() * 255),
//			(int) (c.getBlue() * 255));

		StringBuilder myStyle = new StringBuilder();

		switch (GLOBAL.BackGround) {
			case 1:
				// Var 1:
				//myStyle.append("-fx-background-color: \" + hex + \"; ");
				myStyle.append("-fx-background-color: lightgrey; ");
				if ("dark".equals(GLOBAL.defaultCSS)) {
					myStyle.append("-fx-background-image: url('/images/background01_dark.png'), url('/images/logo-600x500-transparente.png'); ");
					//myStyle.append("-fx-background-image: url('/images/background01_dark.png'); ");
				} else {
					myStyle.append("-fx-background-image: url('/images/background01.jpg'), url('/images/logo-600x500-transparente.png'); ");
					//myStyle.append("-fx-background-image: url('/images/background01.jpg'); ");
				}
				myStyle.append("-fx-background-repeat: no-repeat no-repeat; ");
				//myStyle.append("-fx-background-repeat: no-repeat; ");
				myStyle.append("-fx-background-position: center, top; ");
				//myStyle.append("-fx-background-position: center; ");
				//myStyle.append("-fx-background-size: stretch, auto;");
				myStyle.append("-fx-background-size: cover, auto; ");
				//myStyle.append("-fx-background-size: cover; ");

				tipLogo.setStyle(myStyle.toString());
				break;
			case 2:
				// Var 2:
				myStyle.append("-fx-background-color: lightgrey; ");
				//myStyle.append("-fx-background-image: url('/images/background02.jpg'); ");
				//myStyle.append("-fx-background-repeat: no-repeat no-repeat; ");
				myStyle.append("-fx-background-repeat: no-repeat; ");
				//myStyle.append("-fx-background-position: center, center; ");
				myStyle.append("-fx-background-position-x: center; ");
				myStyle.append("-fx-background-position-x: center; ");
				//myStyle.append("-fx-background-size: stretch;");
				myStyle.append("-fx-background-size: stretch;");
				//myStyle.append("-fx-background-image: url('/images/total_lunar_eclipse.jpeg'); ");
				myStyle.append("-fx-background-image: url('/images/Atomic_Monster.png'); ");

				tipLogo.setStyle(myStyle.toString());
				break;
			case 3:
				// Var 3:
				myStyle.append("-fx-background-color: lightgrey; ");
				myStyle.append("-fx-background-image: url('/images/background03.jpg'), url('/images/logo-600x500-transparente.png'); ");
				myStyle.append("-fx-background-repeat: no-repeat no-repeat; ");
				myStyle.append("-fx-background-position: center, center; ");
				myStyle.append("-fx-background-size: stretch, auto;");

				tipLogo.setStyle(myStyle.toString());
				break;
			case 4:
				// Var 4:
				myStyle.append("-fx-background-color: lightgrey; ");
				myStyle.append("-fx-background-image: url('/images/background03.jpg'); ");
				myStyle.append("-fx-background-repeat: no-repeat; ");
				myStyle.append("-fx-background-position: center; ");
				myStyle.append("-fx-background-size: stretch;");

				tipLogo.setStyle(myStyle.toString());
				break;
		}
	}

	public void updateData() {
	}

}
