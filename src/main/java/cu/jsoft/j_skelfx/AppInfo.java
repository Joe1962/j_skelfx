/*
 * Copyright Joe1962
 * https://github.com/Joe1962
 */
package cu.jsoft.j_skelfx;

/**
 *
 * @author joe1962
 */
public class AppInfo {

	// App constants:
	private static final String TITLE = "SKELFX";
	private static final String PREFNODE = "skelfx";
	// Stable versioning:
	private static final String VERSION_MAJOR = "1";
	private static final String VERSION_MINOR = "0";
	private static final String VERSION_BUILD = "000";
	// Development versioning:
	private static final String VERSION = "1.0.0";
	private static final String BUILD = "250918.02";

	/**
	 * @return the TITLE
	 */
	public static String getTITLE() {
		return TITLE;
	}

	/**
	 * @return the PREFNODE
	 */
	public static String getPREFNODE() {
		return PREFNODE;
	}

	/**
	 * @return the VERSION_MAJOR
	 */
	public static String getVERSION_MAJOR() {
		return VERSION_MAJOR;
	}

	/**
	 * @return the VERSION_MINOR
	 */
	public static String getVERSION_MINOR() {
		return VERSION_MINOR;
	}

	/**
	 * @return the VERSION_BUILD
	 */
	public static String getVERSION_BUILD() {
		return VERSION_BUILD;
	}

	/**
	 * @return the VERSION
	 */
	public static String getVERSION() {
		return VERSION;
	}

	/**
	 * @return the BUILD
	 */
	public static String getBUILD() {
		return BUILD;
	}

	public static AppInfo getInstance() {
		return infoHolder.INSTANCE;
	}

	private AppInfo() {
	}

	private static class infoHolder {

		private static final AppInfo INSTANCE = new AppInfo();
	}

	public static String getTitleString() {
		StringBuilder strVersion = new StringBuilder();

		strVersion.append(getTITLE());
		strVersion.append(" ver. ");
		strVersion.append(getVersionString());

		if (strVersion.length() > 0) {
			return strVersion.toString();
		} else {
			return "";
		}
	}

	public static String getVersionString() {
		StringBuilder strVersion = new StringBuilder();

		if (getBUILD().isEmpty()) {
			strVersion = strVersion.append(getVERSION_MAJOR());
			strVersion = strVersion.append(".");
			strVersion = strVersion.append(getVERSION_MINOR());
			strVersion = strVersion.append(".");
			strVersion = strVersion.append(getVERSION_BUILD());
		} else {
			strVersion.append(getVERSION());
			//strVersion.append(" (build ");
			strVersion.append(" (");
			strVersion.append(getBUILD());
			strVersion.append(")");
		}
		if (strVersion.length() > 0) {
			return strVersion.toString();
		} else {
			return "";
		}
	}

}
