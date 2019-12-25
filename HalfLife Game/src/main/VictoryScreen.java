package main;

import java.io.IOException;

import com.halflife.entities.NetworkedPlayer;
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
import javafx.stage.WindowEvent;
import menu.view.LevelMenuController;
import menu.view.MainMenuController;

/**
 * VictoryScreen- Pane that shows up when the player reaches the goal state
 * @author Rohab
 *
 */
public class VictoryScreen extends StackPane {
	//SpriteAnimation sp= new SpriteAnimation();
	private int timeint;
 	private Stage primaryStage;
 	private Game game;
 	private GameClient gameC;
 	private Player player;
 	private Pane root;
 	private NetworkedPlayer netPlayer;
	
 	/** 
 	 * Constructor for the Victory Screen, if the game is singleplayer or this is the host screen
 	 * @param timeGiven
 	 * @param player
 	 * @param root
 	 * @param game
 	 * @param primaryStage
 	 */
	VictoryScreen(int timeGiven, Player player, Pane root,Game game,Stage primaryStage){
		this.primaryStage = primaryStage;
		this.game = game;
		this.timeint=timeGiven;
		this.player=player;
		this.root=root;
		setUp();
		
	}
	/**
	 * Constructor for Victory Screen, if the game is a client, and this is the clients screen 
	 * @param player
	 * @param root
	 * @param gameClient
	 * @param stage
	 */
	public VictoryScreen(NetworkedPlayer player, Pane root, GameClient gameClient, Stage stage) {
		this.netPlayer=player;
		this.root=root;
		this.gameC=gameClient;
		this.primaryStage=stage;
		setUp();		
		
	}
	/**
	 * Sets up the pane, adding buttons and background
	 */

	public void setUp() {
		 RectObject bg=new RectObject(0,0,800,600,"victoryscreen",Color.valueOf("#4CAF88"));
		 Image youwon = new Image("youwon.png");
         ImageView img= new ImageView(youwon);
         formatting(img, 0, -200, 500);
        
		 
    	 Image trophyimg = new Image("trophy.png");
         ImageView trop= new ImageView(trophyimg);
         formatting(trop,-300,0,300);
    
         
         Image timeimg = new Image("time.png");
         ImageView time= new ImageView(timeimg);
         formatting(time,-50,0,300);
         
         Label timeCount= new Label(""+timeint);
         timeCount.setTranslateX(60);
        
		 timeCount.setFont(new Font ("Courier New",75));
		 timeCount.setTextFill(Color.WHITE);

	         Image restart = new Image("restart.png");
	         ImageView resimg= new ImageView(restart);
	         formatting(resimg,-150,180,400);
	
	         RectObject resbutton=new RectObject(-150,180,250,100,"restart button",Color.TRANSPARENT);
	       
	         
	      //  resbutton.setFill(new javafx.scene.paint.ImagePattern(restart));
	         
	         resbutton.setOnMouseEntered(new EventHandler<MouseEvent>
	         () {

	             @Override
	             public void handle(MouseEvent t) {
	                resimg.setFitWidth(500);
	             }
	         });

	         resbutton.setOnMouseExited(new EventHandler<MouseEvent>
	         () {

	             @Override
	             public void handle(MouseEvent t) {
	                resimg.setFitWidth(450);
	             }
	         });
	         
	         resbutton.setOnMouseClicked((MouseEvent e) -> {
	        	 remove();
	        	 player.restartLevel();
	        	 player.loseLife(root);
	             player.addLives(3);	   
	             player.addAmmo(10);
	             player.getTimer().resetTime();
	             player.setTranslateX(200);
	             player.setTranslateY(0);
	             player.getForeground().getChildren().remove(this);
	             game.setUpLevel(game.GetCurrentLevel());
	             
	             
	         });
	         Image exit= new Image("continue.png");
		     ImageView exitimg= new ImageView(exit);
	         formatting(exitimg,160,190,400);
	 
	         
	         RectObject exitbutton=new RectObject(160,190,400,100,"exit button",Color.TRANSPARENT);
	         exitbutton.setOnMouseEntered(new EventHandler<MouseEvent>
	         () {

	             @Override
	             public void handle(MouseEvent f) {
	                exitimg.setFitWidth(450);
	             }
	         });

	         exitbutton.setOnMouseExited(new EventHandler<MouseEvent>
	         () {

	             @Override
	             public void handle(MouseEvent f) {
	                exitimg.setFitWidth(400);
	             }
	         });
	         exitbutton.setOnMouseClicked((MouseEvent e) -> {
	             System.out.println("Clicked Exit!"); // change functionality
		     		FXMLLoader loader = new FXMLLoader(getClass().getResource("../menu/view/levelmenu.fxml"));
		    		Pane mainMenu;
					try {
						mainMenu = loader.load();

		    		
		    		LevelMenuController controller = loader.getController();
		    		controller.setStage(primaryStage, game.server);
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
	         
	         this.getChildren().add(bg);
	         this.getChildren().add(trop);
	         this.getChildren().add(time);
	         this.getChildren().add(img);
	         this.getChildren().add(timeCount);
	         //this.getChildren().add(sp);
			 this.getChildren().add(resimg);
			 this.getChildren().add(resbutton);			
			 this.getChildren().add(exitimg);
			 this.getChildren().add(exitbutton);
	}
	/**
	 * Method to stop the game if continue button is presses
	 */

	private void destroyGame() {
		game.stopGame();
		game=null;
	}
	/**
	 * Method to format the images on screen
	 * @param img
	 * @param x
	 * @param y
	 * @param fitwidth
	 * @return formatted images
	 */
	
	private ImageView formatting(ImageView img ,int x,int y, int fitwidth) {
		img.setTranslateX(x);
		img.setTranslateY(y);
		img.setFitWidth(fitwidth);
		img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
		
		return img;
	}
	/**
	 * Method to remove everything on this pane
	 */
	
	public void remove() {
		 this.getChildren().clear();
		 
		// this.getChildren().remove(from, to);
	 }
}
