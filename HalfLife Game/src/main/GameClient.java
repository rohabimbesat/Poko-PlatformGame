  package main;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.halflife.enemies.*;
import com.halflife.entities.*;

import main.CheckCollision;
import network.*;
//IF YOU WANT TO TEST WITH THE SPRITE go to the start method and comment out where necessary
/**
 * GameClient --- A class to represent the entire game as seen on the multiplayer client
 * @author Halflife
 *
 */
public class GameClient extends Application {
	public Pane root= new Pane();
	private Pane display=new Pane();
	//private RectObject player=new RectObject(500,300,40,50,"player",Color.WHITE);
	private CloudsAnimation cloudani;
	private SpritePlayer spriteNP;
	private SpritePlayer spplayer;
	private List<SpriteEnemy> enemies = new ArrayList<SpriteEnemy>();
	private DeathScreen YouLose ;
	private List<SupplyDrop> supplies = new ArrayList<SupplyDrop>();
	private List<Spike> spikes = new ArrayList<Spike>();
	private List<NetworkedPlayer> netPlayers = new ArrayList<NetworkedPlayer>();
	private ArrayList<Node> platforms=new ArrayList<Node>();
	private int levelWidth;
	private String[] s = new String[ClientTable.size()];
	private Client client;
	private Message coords;
	//private NetworkedPlayer tempNP;
	private Color bgcol =Color.valueOf("#333333");
	private int levelNumber;
	private Stage stage;
	private VictoryScreen VictoryShow;


	private String[] currentLevel = Level_Info.LEVEL2;

	/**
	 * Constructor for the GameClient class
	 * @param client The client object 
	 */
	public GameClient(Client client, int lvlNum) {
		this.client=client;
		levelNumber = lvlNum;
		spplayer= new SpritePlayer(levelNumber,"networkedPlayer");
		//player= new NetworkedPlayer(200,0,40,50,Color.WHITE,3, levelNumber);
	}

	/**
	 * Setter for currentLevel,  
	 * @param currentLevel The string array containing details on the chosen level
	 */
	public void setCurrentLevel(String[] currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	/**
	 * Creates the visual content of the game such as the background and player
	 * @return The constructed Pane root
	 * @throws IOException
	 */
	private Parent createContent() throws IOException {
		Stop[] stops = new Stop[] { new Stop(0, bgcol), new Stop(1, Color.valueOf("#8096ba"))};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        RectObject bg=new RectObject(0,0,800,600,GameConstants.TYPE_BACKGROUND,Color.valueOf("#333333"));
        bg.setFill(lg1);

		root.setPrefSize(800, 600);
		root.getChildren().add(spplayer);
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};
		
		timer.start();
		CloudsAnimation cloudfront= new CloudsAnimation(levelWidth);
		cloudfront.setOpacity(0.75);
		 root.getChildren().add(cloudfront);
		display.getChildren().addAll(bg,root,spplayer.GetNetPlayer().getForeground());

		spriteNP=new SpritePlayer(levelNumber, "networkedPlayer");
		spriteNP.setOpacity(0.5);
		//tempNP = new NetworkedPlayer(200, 0, 40, 50, Color.BLACK, 3, levelNumber);
//		for (NetworkedPlayer np : netPlayers) {
			root.getChildren().add(spriteNP);
			
//		}
		
		//root.getChildren().add(player);
		return root;	
	}
	
	/**
	 * Function to retrieve all nodes from a parent node including the parent node
	 * @param root The root node
	 * @return All nodes in the ArrayList including the parent
	 */
	public static ArrayList<Node> getAllNodes(Parent root) {
	    ArrayList<Node> nodes = new ArrayList<Node>();
	    addAllDescendents(root, nodes);
	    return nodes;
	}
	
	/** 
	 * Adds all nodes that can be defined as a RectObject to an ArrayList recursively
	 * @param parent The parent node
	 * @param nodes The ArrayList of descendant nodes that's added to recursively 
	 */
	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
	    for (Node node : parent.getChildrenUnmodifiable()) {
	    	
	    	if (node instanceof RectObject) {
	        nodes.add((RectObject) node);
	        if (node instanceof Parent)
	            addAllDescendents((Parent)node, nodes);
	    	}
	    }
	}
	
	
	
	// Game loop variables
	long lastTime = System.nanoTime();
	final double numberOfTicks = 60.0;
	double ns = 1000000000 / numberOfTicks;
	double delta = 0;
	int updates = 0;
	int frames = 0;
	long timer = System.currentTimeMillis();
	
	/**
	 * Game loop function that calculates tick and frame rates 
	 * Also detects objects marked as 'dead' and removes them from the game window
	 */
	private void update() {
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
		
		if (delta >= 1) {
			tick();
			updates++;
			delta--;
		}
		
		frames++;
		
		if (System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			System.out.println(updates + " Ticks, Fps " + frames);
			updates = 0;
			frames = 0;
		}
		

		for (Node object : getAllNodes(root)) {
			RectObject newObj = (RectObject) object;
			if (newObj.isDead())
				root.getChildren().remove(newObj);
		}	
	}
	
	Message temp = null;
	/**
	 * Called on each game tick to handle game functionality 
	 * Acts as a controller for all entity loops 
	 * Handles incoming messages from the server
	 * Useful to look at as the 'heart' of the game
	 */
	private void tick() {
		
		spplayer.GetNetPlayer().tick(root);
		
		for (SpriteEnemy enemy : enemies) {
			enemy.GetEnemy().tick(spplayer.GetNetPlayer(), root);
			if (enemy.GetEnemy().isDead()) {
				enemy.getChildren().clear();
			}
		}
		for (Spike spike : spikes) {
			spike.tick(spplayer.GetNetPlayer(), root);
		}
		for (SupplyDrop supply : supplies) {
			supply.tick(spplayer.GetNetPlayer());
		}
		if (spplayer.GetNetPlayer().getLevelFinish()&&!spplayer.GetNetPlayer().getForeground().getChildren().contains(VictoryShow)) {
			VictoryShow=new VictoryScreen(spplayer.GetNetPlayer(), root,this, stage);
			spplayer.GetNetPlayer().getForeground().getChildren().add(VictoryShow);
			Message m = new Message("youLose");
			client.sendToServer(m);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

		spplayer.GetNetPlayer().checkPos(this);
		coords = new Message(spplayer.GetNetPlayer().getTranslateX(), spplayer.GetNetPlayer().getTranslateY());
		client.sendToServer(coords);
		
		
		
		Task<Integer> task = new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				temp = client.waitForMessage();
				return 0;
			}
		};
		
		Thread t = new Thread(task); 
		t.start();
	      
		task.setOnSucceeded(event -> {				
				
			if(temp.getType().equals("coords")) {
				spriteNP.GetNetPlayer().setTranslateX(temp.getX());
				spriteNP.GetNetPlayer().setTranslateY(temp.getY());	
			}else if (temp.getType().equals("text")) {
				if (temp.getText().equals("youLose")&&!spplayer.GetNetPlayer().getForeground().getChildren().contains(YouLose)) {
					YouLose =new DeathScreen(this, spplayer.GetNetPlayer(),stage, true);
					spplayer.GetNetPlayer().getForeground().getChildren().add(YouLose);				
				}
			}
					
		});
		}
	

	/**
	 * Sets up the scene for the game, the title, keyboard hooks and such JavaFX necessities
	 */
	@Override
	public void start(Stage stage) throws Exception {
//		System.out.println("Game is Starting!!!!!!!!!!");
		stage.setResizable(false);
		setUpLevel(currentLevel);
		createContent();
//		Message nodes = new Message(rectNodes);
//		server.sendToAll(nodes);
		stage.setTitle("POKO");
		Scene scene = new Scene(display);
		stage.setScene(scene);
//		spplayer.buttonPressing(this, scene);
//		spplayer.buttonReleasing(scene);
		
		//IF YOU WANT THE SPRITE UNCOMMENT THE ABOVE AND COMMENT OUT THE BELOW
		spplayer.GetNetPlayer().buttonPressing(this, scene); 
		spplayer.GetNetPlayer().buttonReleasing(scene);
		
//		temp2.buttonPressing(this, scene);
//		temp2.buttonReleasing(scene);
		
		stage.show();
		
		
	}
	
	/**
	 * Responsible for converting the String array given to a playable level
	 * Each digit in the array corresponds to a different object 
	 * @param lvl The encoded level
	 */
	private void setUpLevel(String[] lvl) {
		levelWidth= lvl[0].length()*150;	
		cloudani= new CloudsAnimation(levelWidth);
		for (int i = 0; i < lvl.length; i++) {
			String line=lvl[i];
			for (int j = 0; j < line.length(); j++) {
				switch(line.charAt(j)) {
				case '0':
					Node edgePlatR =new RectObject(j*150,i*100,1,1,GameConstants.TYPE_EDGE_PLATFORM_RIGHT,Color.TRANSPARENT);
					edgePlatR.setTranslateX(edgePlatR.getTranslateX()+1);
					root.getChildren().add(edgePlatR);
					platforms.add(edgePlatR);
					Node edgePlatL =new RectObject(j*150,i*100,1,1,GameConstants.TYPE_EDGE_PLATFORM_LEFT,Color.TRANSPARENT);
					edgePlatL.setTranslateX(edgePlatL.getTranslateX()+148);
					root.getChildren().add(edgePlatL);
					platforms.add(edgePlatL);
					break;
				case '1':
				Node platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.LIGHTSKYBLUE);
				root.getChildren().add(platform);
				platforms.add(platform);
				break; 
				case '2':
					Node gPlatform =new GoalPlatform(j*150,i*100,150,30);
					root.getChildren().add(gPlatform);
					platforms.add(gPlatform);
					break;
				case '3': 
					Node floor;
					if (j==1) {
						 floor =new Floor(j*150,i*120,400,10);
						 floor.setTranslateX(0);
					}
					else {
						 floor =new Floor(j*150,i*120,150,10);
					}					
					
					root.getChildren().add(floor);
					platforms.add(floor);
					break;
				case '4': 
					Node wall =new Wall(j*150,i*150,25,150);
					root.getChildren().add(wall);
					platforms.add(wall);
					break;
				case '5':
					platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.LIGHTSKYBLUE);
					root.getChildren().add(platform);
					platforms.add(platform);
					Node bEnemy = new BaseEnemy(j*150,i*100-30,30,30);
					SpriteEnemy spenemy=new SpriteEnemy(j*150+120,i*100-30,30,30);
					//	bEnemy.setTranslateX(bEnemy.getTranslateX()+120);
						root.getChildren().add(spenemy);
						enemies.add((SpriteEnemy) spenemy);
						
					break;
				case '6':
					platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.LIGHTSKYBLUE);
					root.getChildren().add(platform);
					platforms.add(platform);
					SpikePlatform sPlatform =new SpikePlatform(j*150,i*100,30,10);
					root.getChildren().add(sPlatform.getSpike());
					spikes.add(sPlatform.getSpike());
					break;
				
				case '7':
					platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.LIGHTSKYBLUE);
					root.getChildren().add(platform);
					platforms.add(platform);					
					break;
				}
			}
		}		
	}
}
