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
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.CycleMethod;
import com.halflife.enemies.*;
import com.halflife.entities.*;

import main.CheckCollision;
import network.*;

/**
 * Game --- A class to represent the entire game as seen in single player or the host of a multiplayer game
 * @author Halflife
 *
 */
public class Game extends Application {
	private ListofAnimations anilist= new ListofAnimations();
	public Pane root= new Pane();
	private Pane display=new Pane();
	private SpritePlayer spplayer;
	private SpritePlayer spriteNP;
	private CloudsAnimation cloudani;
	private List<SpriteEnemy> enemies = new ArrayList<SpriteEnemy>();
	private List<SupplyDrop> supplies = new ArrayList<SupplyDrop>();
	private List<Spike> spikes = new ArrayList<Spike>();
	private ArrayList<Node> platforms=new ArrayList<Node>();
	private int levelWidth;
	private String[] s = new String[ClientTable.size()];
	Server server;
	private ArrayList<RectObject> rectNodes = new ArrayList<RectObject>();
	private Message coords;
	private boolean multiplayer=false;
	private boolean paused = false;
	//private NetworkedPlayer player2;
	private StackPane DeathShow;
	private VictoryScreen VictoryShow;
	private StackPane pauseScreen;
	private boolean pauseShowing = false;
	private String[] currentLevel = Level_Info.LEVEL2;
	private Color bgcol =Color.valueOf("#333333");	
	private int levelNumber;
 	private Stage primaryStage;
	private AnimationTimer animattimer;

	/**
	 * Constructor for the Game class
	 * @param server Contains server information needed for multiplayer functionality
	 */
	public Game(Server server, int lvlNum) {
		this.server = server;
		levelNumber = lvlNum;
	}
	/**
	 * Stops the game loop and the connected server
	 */
	public void stopGame() {
		server.stopServer();
		animattimer.stop();
	}
	/**
	 * Setter for currentLevel,  
	 * @param currentLevel The string array containing details on the chosen level
	 */
	public void setCurrentLevel(String[] currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String[] GetCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Creates the visual content of the game such as the background and player
	 * if you are playing multiplayer it also adds the second player
	 * @return The constructed Pane root
	 * @throws IOException
	 */
	
	
	private Parent createContent() throws IOException {	
		 Stop[] stops = new Stop[] { new Stop(0, bgcol), new Stop(1, Color.valueOf("#8096ba"))};
	     LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
	        RectObject bg=new RectObject(0,0,800,600,GameConstants.TYPE_BACKGROUND,Color.valueOf("#333333"));
	        bg.setFill(lg1);
	        root.setPrefSize(800, 600);
	        
	       
	        
	        if(multiplayer) {
				spplayer = new SpritePlayer(levelNumber,"2player");
	        }else {
				spplayer = new SpritePlayer(levelNumber,"1player");
	        }

	        
	        
	       // root.getChildren().add(cloudani);
	        root.getChildren().add(spplayer);
	
	animattimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};
		
		animattimer.start();
		display.getChildren().addAll(bg,root,spplayer.GetPlayer().getForeground());
		
		
		if (multiplayer) {
			spriteNP = new SpritePlayer(levelNumber,"networkedPlayer");
			root.getChildren().add(spriteNP);
			spriteNP.setOpacity(0.5);
		}
		
		CloudsAnimation cloudfront= new CloudsAnimation(levelWidth);
		cloudfront.setOpacity(0.75);
		 root.getChildren().add(cloudfront);
		ImageView controls = new ImageView(new Image("controls.png"));
		controls.setX(-30);
		controls.setY(350);
		controls.setFitHeight(250);
		controls.setFitWidth(250);
		root.getChildren().add(controls);
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
	
	/**
	 * Called on each game tick to handle game functionality 
	 * Acts as a controller for all entity loops 
	 * Useful to look at as the 'heart' of the game
	 */
	private void tick() {
		paused = spplayer.GetPlayer().getPaused();
		if (!paused) {
			spplayer.GetPlayer().getClock();
			pauseShowing = false;
			spplayer.GetPlayer().tick(root);
			for (SpriteEnemy enemy : enemies) {
				enemy.GetEnemy().tick(spplayer.GetPlayer(), root);
				if (enemy.GetEnemy().isDead()) {
					enemy.getChildren().clear();
				}
			}
			for (Spike spike : spikes) {
				spike.tick(spplayer.GetPlayer(), root);
			}
			for (SupplyDrop supply : supplies) {
				supply.tick(spplayer.GetPlayer());
			}
			spplayer.GetPlayer().checkPos(this);
		} else if (!pauseShowing) {
			pauseShowing = true;
			pauseScreen = new PauseScreen(spplayer.GetPlayer(), primaryStage, this);
			spplayer.GetPlayer().getForeground().getChildren().add(pauseScreen);
		}
		
		
		
		if (multiplayer) {
			spriteNP.GetNetPlayer().tick(root);
		}
		
		
		if (spplayer.GetPlayer().isDead() && !spplayer.GetPlayer().getForeground().getChildren().contains(DeathShow)) {
			DeathShow =new DeathScreen(this, spplayer.GetPlayer(),primaryStage, multiplayer);
			spplayer.GetPlayer().getForeground().getChildren().add(DeathShow);
//			deathScreenDisplayed = true;
		}
		if (spplayer.GetPlayer().getLevelFinish() && !spplayer.GetPlayer().getForeground().getChildren().contains(VictoryShow)) {
			VictoryShow=new VictoryScreen(spplayer.GetPlayer().getTimer().getTime(), spplayer.GetPlayer(), root,this, primaryStage);
			spplayer.GetPlayer().getForeground().getChildren().add(VictoryShow);
			Message m = new Message("youLose");
			server.sendToAll(m);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(m.getType().toString());
//			deathScreenDisplayed = true;
		}
		if (multiplayer) {
			coords = new Message(spplayer.GetPlayer().getTranslateX(), spplayer.GetPlayer().getTranslateY());
			server.sendToAll(coords);
		
			Message temp = null;
			try {
				if(!server.getReceived().isEmpty()) {
					temp = server.getReceived().take();
					if (temp.getType().equals("coords")) {
						spriteNP.GetNetPlayer().setTranslateX(temp.getX());
						spriteNP.GetNetPlayer().setTranslateY(temp.getY());
					}else if(temp.getType().equals("text")) {
						if(temp.getText().equals("youLose")&&!spplayer.GetPlayer().getForeground().getChildren().contains(DeathShow)) {
							DeathShow =new DeathScreen(this, spplayer.GetPlayer(),primaryStage, multiplayer);
							spplayer.GetPlayer().getForeground().getChildren().add(DeathShow);
						}
					}
					
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}		
	}
	
	private Scene setCursor(Scene s) {
		Image cursor = new Image("cursor.png");
		s.setCursor(new ImageCursor(cursor));
		return s;
	}
	
	/**
	 * Sets up the scene for the game, the title, keyboard hooks and such JavaFX necessities
	 */
	@Override
	public void start(Stage stage) throws Exception {
//		System.out.println("Game is Starting!!!!!!!!!!");
		
		this.primaryStage = stage;
		stage.setResizable(false);
		setUpLevel(currentLevel);
		createContent();
		//stage.setTitle("Poko");
		Scene scene = new Scene(display);
		setCursor(scene);
		stage.setScene(scene);
		
		spplayer.GetPlayer().buttonPressing(this, scene,spplayer);
		spplayer.GetPlayer().buttonReleasing(scene);
		
		stage.show();
	}
	
		
	/**
	 * Responsible for converting the String array given to a playable level
	 * Each digit in the array corresponds to a different object 
	 * @param lvl The encoded level
	 */
	public void setUpLevel(String[] lvl) {
		//anilist.wipeList();
		levelWidth= lvl[0].length()*150;	
		cloudani= new CloudsAnimation(levelWidth);
		 root.getChildren().add(cloudani);
		s = network.Server.showConnected();
		if (s.length>1) {
			multiplayer=true;
		}
		Arrays.sort(s);
		
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
				Node platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.SKYBLUE);
				root.getChildren().add(platform);
				platforms.add(platform);
				rectNodes.add((RectObject)platform);
				break; 
				case '2':
					Node gPlatform =new GoalPlatform(j*150,i*100,150,15);
					root.getChildren().add(gPlatform);
					platforms.add(gPlatform);
					rectNodes.add((RectObject)gPlatform);
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
					rectNodes.add((RectObject)floor);
					break;
				case '4': 
					Node wall =new Wall(j*150,i*150,25,150);
					root.getChildren().add(wall);
					platforms.add(wall);
					rectNodes.add((RectObject)wall);
					break;
				case '5':
					platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.SKYBLUE);
					root.getChildren().add(platform);
					platforms.add(platform);
					//Node bEnemy = new BaseEnemy(j*150,i*100-30,30,30);
					SpriteEnemy spenemy=new SpriteEnemy(j*150+120,i*100-30,30,30);
				//	bEnemy.setTranslateX(bEnemy.getTranslateX()+120);
					root.getChildren().add(spenemy);
					enemies.add((SpriteEnemy) spenemy);
					rectNodes.add((RectObject)spenemy.GetEnemy());
					break;
				case '6':
					platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.SKYBLUE);
					root.getChildren().add(platform);
					platforms.add(platform);
					SpikePlatform sPlatform =new SpikePlatform(j*150,i*100,30,10);
					root.getChildren().add(sPlatform.getSpike());
					spikes.add(sPlatform.getSpike());
					rectNodes.add((RectObject)sPlatform);
					break;
				
				case '7':
					
					platform =new RectObject(j*150,i*100,150,10,GameConstants.TYPE_PLATFORM,Color.SKYBLUE);
					root.getChildren().add(platform);
					platforms.add(platform);
					if(!multiplayer) {
						SupplyDrop supply =new SupplyDrop(j*150,i*100-50,50,50);
						root.getChildren().add(supply);
						supplies.add(supply);
						rectNodes.add((RectObject)supply);
					}
					break;
				}
			}
		}	
		 
	}
	
}
