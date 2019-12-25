package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class WriteFile {

	//private String path = System.getenv("APPDATA");
	//The above line doesn't work on Linux so is replaced with the below
	private String path = System.getProperty("user.home");
	private boolean append = false;
	
	/**
	 * 1st Constructor for the WriteFile class
	 * @param _path The path to write to 
	 * @param _append Whether or not the writer should append any data to the end of an existing file or overwrite it
	 */
	public WriteFile(String _path, boolean _append) {
		path = _path;
		append = _append;
	}
	
	/**
	 * 2nd Constructor for the WriteFile class
	 * No path needed in this one, uses the default APPDATA location
	 * @param _append Whether or not the writer should append any data to the end of an existing file or overwrite it
	 */
	public WriteFile(boolean _append) {
		append = _append;
	}
	
	public void firstTimeStart() throws FileNotFoundException {
		File fileChecker = new File(path + "/levels.txt");
		
		if (!fileChecker.exists()) {
			try {
				String[] skeletonText = new String[] {"level1=false","level2=false","level3=false","level4=false"};
				write(skeletonText);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public String getPath() {
		return path;
	}
	
	/**
	 * Writes level completion data to the given path
	 * @param text The text to write into the file
	 * @throws IOException
	 */
	public void write(String[] text) throws IOException {
		FileWriter writer = new FileWriter(path + "/levels.txt", append);
		BufferedWriter bw = new BufferedWriter(writer);
		
		for (String str : text) {
			bw.write(str);
			bw.newLine();
		}
		
		bw.close();
	}
	
}
