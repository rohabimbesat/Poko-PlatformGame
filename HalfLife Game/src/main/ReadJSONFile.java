package main;


import java.io.*;
import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * ReadJSONFile --- A class to help with reading game levels from a JSON file
 * -- Not currently used in the final game implementation --
 * @author Daniel
 *
 */
public class ReadJSONFile {
	//TODO remove main class and replace with constructor in calling class (Main here just gives example of usage)
	/**
	 * Main method to test this implementation
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//path to json file may be incorrect. Stored in git repo halflife/HalfLife Game/data/level1.json
			new ReadJSONFile (new FileReader(System.getProperty("user.dir") + "/data/level1.json"));
		}catch(FileNotFoundException e) {
			System.err.println("File Read Error: " + e.getMessage());
	        e.printStackTrace();
		}
	}
	/**
	 * Method to read in a json file and parse it
	 * @param fileToRead Filereader object representing the file to be read
	 */
	public ReadJSONFile(FileReader fileToRead){
		JSONParser parser = new JSONParser();
		
		try {
			//path to json file likely incorrect. Stored in git repo halflife/HalfLife Game/data/level1.json
			//Object obj = parser.parse(new FileReader("../data/level1.json"));
			Object obj = parser.parse(fileToRead);
			JSONObject jsonObject = (JSONObject) obj;

			//Print raw json file
			System.out.println(jsonObject.entrySet());
			
			//print "simple" fields
			String level = (String) jsonObject.get("level");
			long worldWidth = (long) jsonObject.get("worldWidth");
			long worldHeight = (long) jsonObject.get("worldHeight");
			System.out.println("level: " + level);
			System.out.println("worldWidth: " + worldWidth);
			System.out.println("worldHeight: " + worldHeight);
			
			//print world objects entry
			JSONArray worldObjects = (JSONArray) jsonObject.get("worldObjects");
			
			System.out.println("WorldObjects:");
			JSONObject worldObject;
			for (int i=0; i<worldObjects.size(); i++) {
				System.out.println(worldObjects.get(i));
				worldObject = (JSONObject) worldObjects.get(i);
				System.out.println(worldObject.get("type")+" - "+worldObject.get("properties"));
			}
			
			/*System.out.println("\nLinks:");
			Iterator<String> iterator = worldObjects.iterator();

			while (iterator.hasNext()) {
				JSONArray platform = (JSONArray) jsonObject.get("platform");
				//System.out.println(iterator.next());
				String imgUrl = (String) jsonObject.get("imgUrl");
				String title = (String) jsonObject.get("title");
				String url = (String) jsonObject.get("url");
				System.out.println("imgUrl: " + imgUrl);
				System.out.println("title: " + title);
				System.out.println("url: " + url);
            }*/
            
	        System.out.print("\n");
			
		}catch(FileNotFoundException e) {
			System.err.println("File Read Error: " + e.getMessage());
	        e.printStackTrace();
		}catch(IOException e){
			e.getMessage();
			e.printStackTrace();
		}catch(ParseException e){
			System.err.println("Parse Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
