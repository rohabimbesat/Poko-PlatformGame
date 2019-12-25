package com.halflife.entities;

import java.io.File;
import java.io.IOException;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.CheckCollision;
import main.CountdownTimer;
import main.GameClient;
import main.GameConstants;
import main.WriteFile;

/**
 * NetworkedPlayer --- Creates the player that is used in multiplayer when
 * a client joins the server
 * @author Halflife
 */
public class NetworkedPlayer extends Player {
	
	private double velX = 0; //Velocity of the player in X direction
	private double velY = 0; //Velocity of the player in Y direction
	private double gravity = 0; //Gravity value for the player
	private boolean completedLevel; //Whether the player has completed the level
	private Pane foreground = new Pane(); //Objects on the screen that are independent to the player

	/**
	 * Constructor of the networked player object
	 * @param x X coordinate of the player object
	 * @param y Y coordinate of the player object
	 * @param width Width of the player object
	 * @param height Height of the player object
	 * @param col Colour of the player object
	 * @param lives Number of lives the player object has
	 */
	public NetworkedPlayer(double x, double y, int width, int height, Color col, int lives, int lvlNum, boolean multiplayer) {
		super(x,y,width,height, col, 3, lvlNum, multiplayer);
		collisionChecker = new CheckCollision();
		movement(x, y);		
		completedLevel = false;
	}
	
	/**
	 * Returns the foreground objects to display on the screen
	 * for the player
	 * @return Returns the foreground pane
	 */
	public Pane getForeground() {
		return foreground;
	}

	/**
	 * Constantly runs, from the Game class, to check how the player
	 * object is interacting with other objects
	 * @param root Contains all objects that the player could interact with
	 */
	public void tick(Pane root) {
		moveX((int)velX);
		moveY((int)velY);	
		
		setVelY(10);
		
		RectObject collidedObj = collisionChecker.checkForCollision(this, root);
		if (collisionChecker.getCollided()) {
			if (collidedObj.getType().equals(GameConstants.TYPE_PLATFORM)) {
				setVelY(0);
				setTranslateY(collidedObj.getTranslateY() - 50);
		    }
			else if (collidedObj.getType().equals(GameConstants.TYPE_GOAL)) {
				setVelY(0);
				if (!completedLevel) {
					completedLevel = true;
				}
				WriteFile wr = new WriteFile(false);
//				try {
//					wr.write("level1=true");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}	
			else if (collidedObj.getType().equals(GameConstants.TYPE_FLOOR)) {
				loseLife(root);
			}
			else if (collidedObj.getType().equals(GameConstants.TYPE_WALL)) {
				this.setTranslateX(getTranslateX() + 20);
			}
		}
	}
	
	/**
	 * Determines what happens when the player loses a life
	 * @param root Contains all objects that the player could interact with
	 */
	@Override
	public void loseLife(Pane root) {
		Media sound = new Media(new File("data/death.mp3").toURI().toASCIIString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		this.Fade();
		this.setTranslateX(200);
		this.setTranslateY(0);
		root.setLayoutX(0);
	}

	
	/**
	 * Sets the velocity of the player in the X direction
	 * @param v Velocity value to change by
	 */
	public void setVelX(double v) {
		this.velX = v;
	}
	
	/**
	 * Sets the velocity of the player in the Y direction
	 * @param v Velocity value to change by
	 */
	public void setVelY(double v) {
		this.velY = v;
	}
	
	/**
	 * Returns the velocity of the player in the X direction
	 * @return Velocity of the player in the X direction
	 */
	public double getVelX() {
		return velX;
	}
	
	/**
	 * Returns the velocity of the player in the Y direction
	 * @return Velocity of the player in the Y direction
	 */
	public double getVelY() {
		return velY;
	}

	/**
	 * Determines what happens when the player presses the Jump button
	 */
	public void jump() {
		double startingY = this.getTranslateY();
		AnimationTimer jTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				setVelY(-10+gravity);
				gravity += .5;
				if (startingY < getYLocation() || collisionChecker.getCollided()) {
					stop();
                    gravity = 0;
                    setVelY(0);
                    setTranslateY(getTranslateY() - 10);
				}
			}
		};
		Media sound = new Media(new File("data/jump.mp3").toURI().toASCIIString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		jTimer.start();
	}
	
	/**
	 * Uses the gravity variable to make the player fall
	 * @param y Y coordinate of the player object
	 */
	public void movement(double x, double y) {
		if (isFalling() || isJumping()) {
			y += gravity;
		}
	}

	/**
	 * Checks whether the player object has collided with another object
	 * @param root Contains all objects that the player could interact with
	 * @return Whether the player has collided or not
	 */
	public boolean hasCollided(Pane root) {
		collisionChecker.checkForCollision(this, root);
		return collisionChecker.getCollided();
	}
	
	/**
	 * Creates a flashing animation for certain objects, such as when
	 * the player loses a life
	 */
	public void Fade() {
		if (getType().equals(GameConstants.TYPE_PLAYER)) {
				
			 ft = new FadeTransition(Duration.millis(500), this);
			
			 ft.setFromValue(1.0);
			 ft.setToValue(0.0);
			 ft.setCycleCount(10);
			 ft.setAutoReverse(true);
			    
			
			 Double opa = this.getOpacity();
	         if (opa.intValue() == 0) {
	             return;
	         } 
	         Animation.Status as = ft.getStatus();
	         if (as == Animation.Status.RUNNING) {
	             return;
	         }
	         if (as == Animation.Status.STOPPED) {
	             ft.play();
	         }           
		}
	}
	
	/**
	 * Returns the gravity value for the player
	 * @return Gravity value for the player
	 */
	public double getGravity() {
		return gravity;
	}

	/**
	 * Detects when a user presses a button and decides what to do
	 * in each case
	 * @param game Main game object
	 * @param s Scene object to display all the objects
	 */
	public void buttonPressing(GameClient game, Scene s) {
	
		s.setOnKeyPressed(e-> {
			switch (e.getCode()) {
			case A:
				setVelX(-5);
				game.root.setLayoutX(game.root.getLayoutX()+10);
				break;
			case D: 
				setVelX(5);
				game.root.setLayoutX(game.root.getLayoutX()-10);
				break;
			case S: 
				break;
			case W:
				if (getGravity() == 0 && hasCollided(game.root)) {
					setTranslateY(getTranslateY() - 10);
					jump();
				}
				break;
			default:
				break;
			}
			
		});
		
	}

	/**
	 * Detects when a user releases a button and decides what to do
	 * @param s Scene object to display all the objects
	 */
	public void buttonReleasing(Scene s) {
		
		s.setOnKeyReleased(e-> {
			switch (e.getCode()) {
			case A:
				setVelX(0);
				break;
			case D: 
				setVelX(0);
				break;
			case S: 
				setVelY(0);
				break;
			case W:
				break;
			default:
				break;
			}
			
		});
		
	}
	
	/**
	 * Returns whether the player has finished the current level
	 * @return Whether the player has finished the current level
	 */
	public boolean getLevelFinish() {
		return completedLevel;
	}

	/**
	 * Moves the screen so that the player can always be seen
	 * @param game Main game object
	 */
	public void checkPos(GameClient gameClient) {
		double x =getXLocation();
		if (x>400) {
			gameClient.root.setLayoutX(gameClient.root.getTranslateX()-(x-400));
		}
	}
}
