package menu.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.Music;

/**
 * SettingsMenuController --- Supplies the methods for the settings menu screen
 * @author HalfLife
 *
 */
	public class SettingsMenuController {
	String musicFile= "Sample - summer.mp3";
	private Stage primaryStage;
	Music mus= new Music();
	
	public void setStage(Stage stage) {
		primaryStage = stage;
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}

	private MediaPlayer mediaplayer;
	
	@FXML
	private void soundOn() throws IOException {
		Media soundFile = new Media(new File("data/shootSound.mp3").toURI().toString());
		mediaplayer = new MediaPlayer(soundFile);
		mediaplayer.play();
	}
	
	@FXML
	private void soundOff() throws IOException {
		Media soundFile = new Media(new File("data/shootSound.mp3").toURI().toString());
		mediaplayer = new MediaPlayer(soundFile);
		mediaplayer.stop();
	}
	
	/**
	 * the button "on"
	 * turning on the background music
	 * @throws IOException
	 */
	@FXML
	private void musicOn() throws IOException {
		mus.playMus();
	}
	
	/**
	 * the button "off"
	 * turning off the background music
	 * @throws IOException
	 */
	@FXML
	private void musicOff() throws IOException {
		mus.stopMus();
	}

	/**
	 * the button "back"
	 * directing to the main menu
	 * @throws IOException
	 */
	@FXML
	private void goBack() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
		Pane settingsMenu = loader.load();
		MainMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(settingsMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}