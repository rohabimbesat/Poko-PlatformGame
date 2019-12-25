package network;
import java.io.*;

/**
 * Standard class to show user output from the networking package
 * @author Unknown
 *
 */
public class Report {

	/** Standard output. Logs standard behaviour of the application (to console)
	 * @param message String to print
	 */
	public static void behaviour(String message) {
		System.out.println(message);
	}
	/**
	 * Error Output. Log an error in the application (to console)
	 * @param message String to print
	 */
	public static void error(String message) {
		System.err.println(message);
	}
	/**
	 * Fatal error Output. Log an error in the application (to console) and quit application
	 * @param message String to print
	 */
	public static void errorAndGiveUp(String message) {
		Report.error(message);
		System.exit(1);
	}
}

