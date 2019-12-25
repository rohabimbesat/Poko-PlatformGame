package menu.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.Client;
import network.Server;

/**
 *  HostMenuController --- Supplies the methods for the host game menu screen
 * @author HalfLife
 */

public class HostMenuController {
	
	private Stage primaryStage;
	private String serverLocation;
	Server server;
	
    @FXML
    private Text dispIPAddr;
	
	public void setStage(Stage stage) {
		primaryStage = stage;
	}
	
	//On page load start server
	public void initialize() {
		System.out.println("Start server...");
		//Can't use 0 - 1023, Use 1024 - 65 535
		final int port = 1035;
		System.out.println("port: "+port);
		server = new Server(port);
		server.setAllowNewPlayers(true); //Allow new players to connect
		serverLocation = server.getIpAddress()+":"+server.getPort();
		dispIPAddr.setText(serverLocation);
		server.start();
				
	}
	/**
	 * set the cursor icon for a particular scene
	 * @param s scene to set cursor in
	 * @return the scene with new cursor
	 */
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
		server.stopServer();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("multimenu.fxml"));
		Pane hostMenu = loader.load();
		MultiMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(hostMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * the button "next"
	 * directing to the game level selection menu
	 * @throws IOException
	 */
	@FXML
	private void goNext() throws IOException {
		server.setAllowNewPlayers(false); //Stop any further players from connecting
		System.out.println("Network Client Connecting to: "+"localhost");
		//Can't use 0 - 1023, Use 1024 - 65 535
		Client client = new Client(1035,"dan","localhost");
		client.start();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("levelmenu.fxml"));
		Pane hostMenu = loader.load();
		LevelMenuController controller = loader.getController();
		controller.setStage(primaryStage, server);
		Scene scene = new Scene(hostMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}