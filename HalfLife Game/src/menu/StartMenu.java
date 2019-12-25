package menu;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaException;
import javafx.stage.Stage;
import main.CloudsAnimation;
import main.Music;
import main.SpriteAnimation;
import main.WriteFile;
import menu.view.*;

/**
 * StartMenu --- Starts the first menu
 * @author HalfLife
 *
 */
public class StartMenu extends Application {
	
	private static Stage primaryStage;
	private static Pane mainLayout;
	private SpriteAnimation sp1 = new SpriteAnimation("player");
	private SpriteAnimation sp2 = new SpriteAnimation("player");
	private CloudsAnimation cloud;
	
	/**
	 * General layout settings
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("POKO");
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(600);
		this.primaryStage.setMinWidth(250);
		this.primaryStage.setMinHeight(250);
		this.primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("icon.png"));
		String musicFile = "data/Sample - summer.mp3";

		try {
			Music mus= new Music("music");
		} catch(MediaException e) {
			System.out.println("Unable to play audio: "+e.getMessage());
		}
		showMainView();
	}
	
	private void setAnis() {
		sp1.resizeView(10, -135, 300);
		sp2.flip();
		sp2.resizeView(470, -220, 300);
		cloud=new CloudsAnimation(200);
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}
	
	/**
	 * Loading the first "POKO" menu
	 * @throws IOException
	 */
	public void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(StartMenu.class.getResource("view/startmenu.fxml"));
		setAnis();
		mainLayout = loader.load();
		StackPane cloudpane= new StackPane();
		
		cloudpane.getChildren().add(cloud);
		cloudpane.setPickOnBounds(false);
		cloudpane.isMouseTransparent();
		mainLayout.getChildren().add(sp1);

		mainLayout.getChildren().add(cloudpane);
		mainLayout.setPickOnBounds(false);
		StartMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(mainLayout);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Main Class to start the game
	 * @param args Command line arguments passed - None needed
	 */
	public static void main(String[] args) {
		WriteFile firstRunCheck = new WriteFile(true);
		try {
			firstRunCheck.firstTimeStart();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		launch(args);
		
	}
}
