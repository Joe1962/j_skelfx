/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx.global;

import jakarta.xml.bind.DatatypeConverter;

/**
 *
 * @author joe1962
 */
public class CONSTS extends cu.jsoft.j_utilsfxlite.global.CONSTS {
	
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

	// NOTE: remember to reverse AESSalt and SecKeyStr before using...!!!
	public static final String AESSalt = "TAOGehtsiCFloopreviL";
	public static final String SecKeyStr = "=oMYdujpP8lMvu91WdJPWlSg4kh69KCZt9RcYD3Iufd4";
	public static final byte[] iv = DatatypeConverter.parseHexBinary("2478081054223D4DB4DEE5A82749E90F");

	public static enum LEDState {
		RED, YELLOW, GREEN
	}

}
