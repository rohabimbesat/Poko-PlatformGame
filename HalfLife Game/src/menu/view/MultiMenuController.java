package menu.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * MultiMenuController --- Supplies the methods for the multi player menu screen
 * @author HalfLife
 *
 */
public class MultiMenuController {
	
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
	 * the button "host game"
	 * directing to the game hosting menu
	 * @throws IOException
	 */
	@FXML
	private void goHost() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("hostmenu.fxml"));
		Pane multiMenu = loader.load();
		HostMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(multiMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * the button "join game"
	 * directing to the game joining menu
	 * @throws IOException
	 */
	@FXML
	private void goJoin() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("joinmenu.fxml"));
		Pane multiMenu = loader.load();
		JoinMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(multiMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * the button "back"
	 * directing to the main menu
	 * @throws IOException
	 */
	@FXML
	private void goBack() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
		Pane multiMenu = loader.load();
		MainMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(multiMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}