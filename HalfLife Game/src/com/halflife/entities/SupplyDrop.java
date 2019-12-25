package com.halflife.entities;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import main.CheckCollision;
import main.GameConstants;

/**
 * SupplyDrop --- Creates supply drop object inside the game
 * @author Halflife
 */
public class SupplyDrop extends RectObject{

	private CheckCollision collisionChecker; //Checks collisions with other objects
	private int ammoContents; //Amount of ammo in the supply drop
	private int livesContents;//Amount of lives in the supply drop
	
	/**
	 * Constructor for the supply drop object
	 * @param x X coordinate of the supply drop
	 * @param y Y coordinate of the supply drop
	 * @param width Width of the supply drop
	 * @param height Height of the supply drop
	 */
	public SupplyDrop(double x, double y, int width, int height) {
		
		super(x, y, width, height, GameConstants.TYPE_SUPPLY_DROP, Color.INDIGO);
		Image supply= new Image("supplycrate.png",height*2,width*2,false,true);
		
		ImageView img=new ImageView(supply);
		ImagePattern texture=new ImagePattern(supply,0,0,height,width,false);
		
	
		
		
		this.setFill(texture);
		collisionChecker = new CheckCollision();
		generateContents();
	}
	
	/**
	 * Randomly generates the number of lives and bullets inside the supply drop
	 */
	public void generateContents() {
		Random r = new Random();
		ammoContents = r.nextInt(10);
		livesContents = r.nextInt(2);
		if (ammoContents == 0)
			ammoContents = 1;
		if (livesContents == 0)
			livesContents = 1;
	}

	/**
	 * Constantly runs to check whether a player object has collided with it
	 * @param player Player object that can collide with it
	 */
	public void tick(Player player) {
		if (isCollidedWithPlayer(player) && ammoContents != 0 && livesContents != 0) {
			player.addAmmo(ammoContents);
			player.addLives(livesContents);
			System.out.println(ammoContents + " - " + livesContents);
			this.setDead(true);
		} else {
			this.dead = false;
		}
	}
	
	/**
	 * Checks whether the supply drop has collided with the player
	 * @param player Player object that can collide with it
	 * @return Whether the player has collided with the supply drop
	 */
	public boolean isCollidedWithPlayer(Player player)
	{
		if(this.getBoundsInParent().intersects(player.getBoundsInParent()))
			return true;
		return false;
	}
	
	/**
	 * Returns whether the supply drop has collided with any object
	 * @param root Contains all of the objects that the supply drop could collide with
	 * @return Whether the supply drop has been collided with
	 */
	public boolean checkCollided(Pane root) {
	    collisionChecker.checkForCollision(this, root);
		return collisionChecker.getCollided();
	}
	
	/**
	 * Sets the state of 'deadness' for the supply drop
	 * @param dead Whether the supply drop is 'dead' or not
	 */
	@Override
	public void setDead(boolean dead) {
		this.dead = dead;
		ammoContents = 0;
		livesContents = 0;
	}
}
