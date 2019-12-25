package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReadLevel {
	private String path = System.getenv("APPDATA");
	private String[] userLevel;
	
	private boolean CHECK_LENGTH = true;
	private boolean CHECK_ROWLENGTH = true;
	private boolean CHECK_CEILING = true;
	private boolean CHECK_FLOOR = true;
	private boolean CHECK_WALL = true;
	private boolean CHECK_GOAL = true;
	private boolean CHECK_STARTPLAT = true;

	/**
	 * Constructor for ReadLevel allowing user to specify path of file
	 * @param _path The path of the file to read in from
	 */
	public ReadLevel(String _path) {
		path = _path;
	}

	/**
	 * Constructor for ReadLevel where the default path is used
	 */
	public ReadLevel() {
	}
	
	/**
	 * @return The userLevel string array
	 */
	public String[] getValidatedLevel() {
		return userLevel;
	}
	
	/**
	 * Reads a user submitted level 
	 * @throws IOException
	 */
	public String[] read() throws IOException {
		FileReader reader = new FileReader(path);
		BufferedReader r =  new BufferedReader(reader);
		
		String[] level = new String[6];
		for (int i = 0; i < 6; i++) {
			level[i] = r.readLine();
		}
		
		return level;
	}
	
	/**
	 * @return The user defined level if valid or the checks which failed if not
	 */
	public String[] getLevel() {
		try {
			setLevel(read());
			if (isValid())
				return userLevel;
			else {
				userLevel = returnErrors();
				return returnErrors();
			}
				
		} catch (IOException e) {
			return returnErrors();
		}
	}
	
	/**
	 * Sets the boolean flags for validating the level, ensures it fits the given criteria
	 * @param level The user defined level
	 */
	public void setLevel(String[] level) {
		if (level.length !=6)
			CHECK_LENGTH = false;
		
		//Row Length
		int rowLength = level[0].length();
		for (int i = 1; i < level.length; i++) {
			if (level[i].length() != rowLength)
				CHECK_ROWLENGTH = false;
		}
		
		String topRow = level[0].substring(1);
		for (char c : topRow.toCharArray()) {
			if (c != '0')
				CHECK_CEILING = false; // Air in top row
		}		
		System.out.println("CHECKING CEILING: " + CHECK_CEILING);

		String bottomRow = level[5].substring(1);
		for (char c : bottomRow.toCharArray()) {
			if (c != '3')
				CHECK_FLOOR = false; // No floor
		}
		
		boolean hasStartPlat = false;
		for (int i = 1; i < 5; i++) {
			if (level[i].charAt(1) == '1' )
				hasStartPlat = true;
		}
		if (!hasStartPlat)
			CHECK_STARTPLAT = false;
		
		boolean hasGoal = false;
		for (int i = 1; i < 5; i++) {
			if (level[i].substring(1).contains("2")){
				hasGoal = true;
			}
		}
		if (!hasGoal)
			CHECK_GOAL = false;
		
		//Checking all 4 at start of each string
		for (int i = 0; i < level.length; i++) {
			if (level[i].charAt(0) != '4')
				CHECK_WALL = false;
		}
		
		userLevel = level;
	}
	
	/**
	 * Checks whether any boolean flags have failed the checks
	 * @return Whether or not the given level is valid
	 */
	public boolean isValid() {
		if  (CHECK_CEILING&&CHECK_FLOOR&&CHECK_GOAL&&CHECK_LENGTH&&CHECK_ROWLENGTH&&CHECK_STARTPLAT&&CHECK_WALL)
			return true;
		else
			return false;
	}
	
	/**
	 * Constructs a string of all the checks and which ones failed
	 * @return A string with all boolean flags and their values
	 */
	public String[] returnErrors() {
		return new String[] {"Errors in user defined level: \n" + "LENGTH CHECK: " + CHECK_LENGTH + "\n ROW LENGTH CHECK: " + CHECK_ROWLENGTH + "\n CEILING CHECK: " + CHECK_CEILING
				+ "\n FLOOR CHECK: " + CHECK_FLOOR + "\n WALL CHECK: " + CHECK_WALL + "\n START PLATFORM CHECK: " + CHECK_STARTPLAT + "\n GOAL STATE CHECK: " + CHECK_GOAL};
	}
}
