package menu.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.SpriteAnimation;
import main.SpriteFollower;
import network.Client;
import network.Server;

/**
 * MainMenuController --- Supplies the methods for the main menu screen
 * @author HalfLife
 *
 */
public class MainMenuController {
	
	private Stage primaryStage;
	private SpriteFollower sf;
	
	private String serverLocation;
	public Server server;
	
	
	public void setStage(Stage stage) {
		primaryStage = stage;
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(600);
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}

	/**
	 * the button "single player"
	 * directing to the game level selection menu
	 * @throws IOException
	 */
	@FXML
	private void goSingle() throws IOException {
		System.out.println("Start server...");
		//Can't use 0 - 1023, Use 1024 - 65 535
		final int port = 1035;
		System.out.println("port: "+port);
		server = new Server(port);
		server.setAllowNewPlayers(true); //Allow new players to connect
		serverLocation = server.getIpAddress()+":"+server.getPort();
		server.start();
		server.setAllowNewPlayers(false); //Stop any further players from connecting
		System.out.println("Network Client Connecting to: "+"localhost");
		//Can't use 0 - 1023, Use 1024 - 65 535
		Client client = new Client(1035,"dan","localhost");
		client.start();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("levelmenu.fxml"));
		Pane mainMenu = loader.load();
		sf= new SpriteFollower(mainMenu);
		sf.getSpriteAnimation().keepitself();
		mainMenu.getChildren().add(sf);
		LevelMenuController controller = loader.getController();
		controller.setStage(primaryStage, server);
		Scene scene = new Scene(mainMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * the button "multi player"
	 * directing to the multi player menu
	 * @throws IOException
	 */
	@FXML
	private void goMulti() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("multimenu.fxml"));
		Pane mainMenu = loader.load();
		MultiMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(mainMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * the button "settings"
	 * directing to the settings menu
	 * @throws IOException
	 */
	@FXML
	private void goSettings() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("settingsmenu.fxml"));
		Pane mainMenu = loader.load();
		SettingsMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(mainMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * the button "back"
	 * directing to the start menu
	 * @throws IOException
	 */
	@FXML
	private void goBack() throws IOException {
		SpriteAnimation sp1 = new SpriteAnimation("player");
		SpriteAnimation sp2 = new SpriteAnimation("player");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("startmenu.fxml"));
		Pane mainMenu = loader.load();
		sp1.keepAllbut(2);
		sp1.resizeView(-10, -135, 300);
		sp2.flip();
		sp2.resizeView(470, -220, 300);
		mainMenu.getChildren().add(sp1);
		mainMenu.getChildren().add(sp2);
		StartMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(mainMenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}