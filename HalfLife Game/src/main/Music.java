package main;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Music --- Creates and plays background music
 * @author Rohab
 */
public class Music {
	
	private String musicFile = "data/Sample - summer.mp3"; //String representing music file
	private static MediaPlayer mediaPlay; //Tool to play music
	
	/**
	 * Plays background music if correct name is passed in
	 * @param musicType Music type to be played
	 */
	public  Music(String musicType) {
		if (musicType.equals("music")) {
			Media sound = new Media(new File(musicFile).toURI().toASCIIString());
			mediaPlay = new MediaPlayer(sound);
			mediaPlay.setVolume(0.1);
		}
		
		playMus();
		
	}
	
	/**
	 * Empty constructor for the music
	 */
	public Music() {
		
	}
	
	/**
	 * Stops the music is necessary
	 */
	public void stopMus(){
		mediaPlay.stop();
	}
	
	/**
	 * Plays the music
	 */
	public void playMus() {
		mediaPlay.play();
	}
	
}

