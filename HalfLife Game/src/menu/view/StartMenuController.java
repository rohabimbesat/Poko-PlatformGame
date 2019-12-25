package menu.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * StartMenuController --- Supplies the methods for the game start menu screen
 * @author HalfLife
 *
 */
public class StartMenuController {
	
	private Stage primaryStage;
	
	public void setStage(Stage stage) {
		primaryStage = stage;
	}

	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}
	
	/**
	 * the button "Start"
	 * directing to the main menu
	 * @throws IOException
	 */
	@FXML
	private void goStart() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
		Pane mainMenu = loader.load();
		
		MainMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(mainMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		this.primaryStage.setOnCloseRequest((WindowEvent event) -> {
	        System.exit(0);
	    });
		primaryStage.show();
	}
	
	/**
	 * the button "exit"
	 * quitting the game
	 * @throws IOException
	 */
	@FXML
	private void goExit() throws IOException {
		System.exit(0);
	}
}
