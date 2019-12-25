package menu.view;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import javafx.scene.layout.*;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Game;
import main.Level_Info;
import main.ReadLevel;
import network.*;

/**
 * LevelMenuController --- Supplies the methods for the game level selection menu screen
 * @author HalfLife
 *
 */
public class LevelMenuController {
	
	private Stage primaryStage;
	private Server server;
	
	public void setStage(Stage stage, Server server) {
		primaryStage = stage;
		this.server = server;
	}
	
	/**
	 * the button "1"
	 * directing to the game - level 1
	 * @throws Exception
	 */
	@FXML
	private void go1() throws Exception {
			Game game = new Game(server,1);
			game.setCurrentLevel(Level_Info.LEVEL1);
			Message m = new Message(Level_Info.LEVEL1);
			server.sendToAll(m);
			game.start(primaryStage);
	}
	
	/**
	 * the button "2"
	 * directing to the game - level 2
	 * @throws Exception
	 */
	@FXML
	private void go2() throws Exception {
		Game game = new Game(server,2);
		game.setCurrentLevel(Level_Info.LEVEL2);
		Message m = new Message(Level_Info.LEVEL2);
		server.sendToAll(m);
		game.start(primaryStage);
	}
	
	/**
	 * the button "3"
	 * directing to the game - level 3
	 * @throws Exception
	 */
	@FXML
	private void go3() throws Exception {
		Game game = new Game(server,3);
		game.setCurrentLevel(Level_Info.LEVEL3);
		Message m = new Message(Level_Info.LEVEL3);
		server.sendToAll(m);
		game.start(primaryStage);
	}
	
	/**
	 * the button "4"
	 * directing to the game - level 4
	 * @throws Exception
	 */
	@FXML
	private void go4() throws Exception {
		Game game = new Game(server, 4);
		game.setCurrentLevel(Level_Info.LEVEL4);
		Message m = new Message(Level_Info.LEVEL4);
		server.sendToAll(m);
		game.start(primaryStage);
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}
	
	/**
	 * the button "upload"
	 * upload the customized level
	 */
	@FXML
	private ListView listview;
	
	public void goUpload(ActionEvent event) throws Exception {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(primaryStage);
		if (selectedFile != null) {
			String fileName=fc.getInitialFileName();
		} else {
			System.out.println("File is not valid");
		}
		System.out.println(selectedFile.getPath());
		ReadLevel readLevel = new ReadLevel(selectedFile.getPath());
		readLevel.getLevel();
		
		if (readLevel.isValid()) {
			Game game = new Game(server,0);
			game.setCurrentLevel(readLevel.getValidatedLevel());
			System.out.println("LVL TO LOAD: " + readLevel.getValidatedLevel());
			Message m = new Message(readLevel.getValidatedLevel());
			server.sendToAll(m);
			game.start(primaryStage);
		 }else {
			 System.out.println(readLevel.returnErrors()[0]);
		 }
	}

	/**
	 * the button "back"
	 * directing to the main menu
	 * @throws IOException
	 */
	@FXML
	private void goBack() throws IOException {
		server.stopServer();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
		Pane levelMenu = loader.load();
		MainMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(levelMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void sendLevel() {
		String[] s = Server.showConnected();
		if (s.length>1) {
			//send level
		}
		//else single player no code required
	}

}