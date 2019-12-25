package main;

import java.io.IOException;

import com.halflife.entities.Player;
import com.halflife.entities.RectObject;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import menu.view.MainMenuController;

/**
 * PauseScreen --- Creates a small overlay that appears when the user pauses
 * the game.
 * @author Halflife
 */
public class PauseScreen extends StackPane {
	
	private Stage primaryStage; //Game scene to be reset
	private Game game; //Game object to be removed
	
	/**
	 * Constructor for the pause screen, creates all buttons
	 * @param player player object to be paused
	 * @param primaryStage game scene to be reset
	 * @param game game object to be removed
	 */
	public PauseScreen(Player player, Stage primaryStage, Game game){
		 RectObject bg=new RectObject(265,150,250,300,"pausescreen",Color.rgb(1, 1, 1, 0.5));
		 
		 this.game = game;
		 this.primaryStage = primaryStage;
		 
		 Image resume = new Image("pauseResume.png");
         ImageView resumeImg= new ImageView(resume);
         resumeImg.setFitWidth(200);
         resumeImg.setTranslateX(270);
         resumeImg.setTranslateY(70);
         resumeImg.setPreserveRatio(true);
         resumeImg.setSmooth(true);
         resumeImg.setCache(true);
         
         Image exit = new Image("pauseExit.png");
         ImageView exitImg= new ImageView(exit);
         exitImg.setFitWidth(200);
         exitImg.setTranslateX(270);
         exitImg.setTranslateY(200);
         exitImg.setPreserveRatio(true);
         exitImg.setSmooth(true);
         exitImg.setCache(true);
         
         this.getChildren().add(bg);
         this.getChildren().add(resumeImg);
         this.getChildren().add(exitImg);
         
         RectObject resumeButton =new RectObject(270,70,200,50,"resume button",Color.TRANSPARENT);
		
         resumeButton.setOnMouseEntered(new EventHandler<MouseEvent>
         () {
             @Override
             public void handle(MouseEvent t) {
                resumeImg.setFitWidth(250);
             }
         });

         resumeButton.setOnMouseExited(new EventHandler<MouseEvent>
         () {
             @Override
             public void handle(MouseEvent t) {
                resumeImg.setFitWidth(200);
             }
         });
         
         RectObject exitButton =new RectObject(270,200,200,50,"exit button",Color.TRANSPARENT);
 		
         exitButton.setOnMouseEntered(new EventHandler<MouseEvent>
         () {
             @Override
             public void handle(MouseEvent t) {
                exitImg.setFitWidth(250);
             }
         });

         exitButton.setOnMouseExited(new EventHandler<MouseEvent>
         () {
             @Override
             public void handle(MouseEvent t) {
                exitImg.setFitWidth(200);
             }
         });
         
         resumeButton.setOnMouseClicked((MouseEvent e) -> {
             remove();
             player.setPaused(false);
         });
         
         exitButton.setOnMouseClicked((MouseEvent e) -> {
        	 FXMLLoader loader = new FXMLLoader(getClass().getResource("../menu/view/mainmenu.fxml"));
	    		Pane mainMenu;
				try {
					mainMenu = loader.load();

	    		
	    		MainMenuController controller = loader.getController();
	    		controller.setStage(primaryStage);
	    		Scene scene = new Scene(mainMenu);
	    		//setCursor(scene);
	    		primaryStage.setScene(scene);

	    		primaryStage.show();
	             remove();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				destroyGame();
         });
         
         this.getChildren().add(resumeButton);
         this.getChildren().add(exitButton);
	}
	
	/**
	 * Removes the game
	 */
	private void destroyGame() {
		game.stopGame();
		game=null;
	}
	
	/**
	 * Removes the pause screen when the user presses 'resume'
	 */
	 public void remove() {
		 this.getChildren().clear();
	 }
	
}