package menu.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import network.Client;

/**
 * JoinMenuController --- Supplies the methods for the join game menu screen
 * @author HalfLife
 *
 */
public class JoinMenuController {
	
	private Stage primaryStage;
	@FXML private TextField ipAddrInput;

	public void setStage(Stage stage) {
		primaryStage = stage;
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}
	
	/**
	 * the button "back"
	 * directing to the multiplayer menu
	 * @throws IOException
	 */
	@FXML
	private void goBack() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("multimenu.fxml"));
		Pane joinMenu = loader.load();
		MultiMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(joinMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * the button "next"
	 * directing to the game
	 * @throws IOException
	 */
	@FXML
	private void goNext() throws IOException {
		System.out.println(ipAddrInput.getText());
		System.out.println("Network Client Connecting to: "+ipAddrInput.getText());
		//Can't use 0 - 1023, Use 1024 - 65 535
		final int port = 1035;
		System.out.println("port: "+port);
		Client client = new Client(port,"dan",ipAddrInput.getText());
		client.start();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("waitscreen.fxml"));
		Pane joinMenu = loader.load();
		WaitScreenController controller = loader.getController();
		controller.setStage(primaryStage, client);
		Scene scene = new Scene(joinMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
}