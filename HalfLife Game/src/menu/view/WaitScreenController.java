package menu.view;

import java.io.IOException;
import java.util.concurrent.FutureTask;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.GameClient;
import network.Client;

/** 
 * WaitScreenController --- Supplies the methods for the wait screen of joining game
 * @author HalfLife
 *
 */
public class WaitScreenController {
	
	private Stage primaryStage;
	@FXML private TextField ipAddrInput;
	private FutureTask msga1;
	private Client client;
	String[] msgR;
	
	public void setStage(Stage stage, Client client){
		this.client = client;
		primaryStage = stage;
		try {
			Task<Integer> task = new Task<Integer>() {
		         @Override protected Integer call() throws Exception {
		        	 msgR = client.waitForMessage().getLevel();
		             System.out.println("msgRCeiVeDD " + msgR);
		             return 0;
		         }
		     };
			
		      Thread t = new Thread(task); 
		      t.start();
		      
		      task.setOnSucceeded(event -> {
				try {
					startGame(msgR);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		     System.out.println("m");

			System.out.println("n");
		      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@FXML
	private void initialize(){
		waitConnection();	
	}
	public void waitConnection() {	
		System.out.println("STARTGAME");
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}
	
	/**
	 * the button "disconnect"
	 * directing to the game joining menu
	 * @throws IOException
	 */
	@FXML
	private void goBack() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("joinmenu.fxml"));
		Pane joinmenu = loader.load();
		JoinMenuController controller = loader.getController();
		controller.setStage(primaryStage);
		Scene scene = new Scene(joinmenu);
		setCursor(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	private void startGame(String[] msgR2) throws Exception{
		System.out.println("message:"+msgR2);
		
		GameClient game = new GameClient(client,0);
		game.setCurrentLevel(msgR2);
		game.start(primaryStage);
				
	}
}
