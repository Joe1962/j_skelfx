/*
 * Copyright Joe1962
 */
package cu.cenpalab.siguapa.global;

/**
 *
 * @author joe1962
 */
public class CONSTS {
	
	// GLOBAL DEFAULT CONSTANTS:
	public static final String DEFAULTDBDRIVER = "jdbc";
	public static final String DEFAULTDBTYPE = "postgresql";
	public static final String DEFAULTDBHOST = "localhost";
	public static final int DEFAULTDBPORT = 5432;
	public static final String DEFAULTDBNAME = "caja";
	public static final String DEFAULTDBUSER = "herbiesoft";



//	public static final String cssEtchedBorder = "-fx-border-base: gray; -fx-border-shadow: white; "
//		+ "-fx-light-border: derive(-fx-border-base, 25%); -fx-border-color: -fx-light-border -fx-border-base "
//		+ "-fx-border-base -fx-light-border; -fx-border-insets: 0 1 1 0; -fx-background-color: -fx-border-shadow, "
//		+ "-fx-background; -fx-background-insets: 1 0 0 1, 2; -fx-padding: 2;";

	public static final String cssEtchedBorder = "-fx-border-base: gray; -fx-border-shadow: white; "
		+ "-fx-light-border: derive(-fx-border-base, 25%); -fx-border-color: -fx-light-border -fx-border-base "
		+ "-fx-border-base -fx-light-border; -fx-border-insets: 0 1 1 0; -fx-background-insets: 1 0 0 1, 2; -fx-padding: 2;";

	public static enum LEDState {
		RED, YELLOW, GREEN
	}

}
