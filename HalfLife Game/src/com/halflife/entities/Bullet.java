package com.halflife.entities;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import main.CheckCollision;
import main.GameConstants;

/**
 * Bullet --- Creates the bullet objects for when the player shoots
 * @author Halflife
 */
public class Bullet extends RectObject{

	private CheckCollision collisionChecker; //Checks collisions with other objects
	
	/**
	 * Constructor for the bullet object
	 * @param x X coordinate for the bullet object
	 * @param y Y coordinate for the bullet object
	 * @param width Width of the bullet object
	 * @param height Height of the bullet object
	 * @param type Type of the bullet object
	 * @param col Colour of the bullet object
	 * @param root Contains all objects that the bullet can collide with
	 * @param bulletDir Direction that the bullet is travelling in
	 */
	public Bullet(double x, double y, int width, int height, String type, Color col, Pane root, Boolean bulletDir) {
		super(x, y + 25, width, height, type, Color.GREEN);
		collisionChecker = new CheckCollision();
		hasShot(System.currentTimeMillis(), root, bulletDir);
	}
	
	/**
	 * Controls the movement of the bullet once it has been shot
	 * @param startTime When the bullet has been shot
	 * @param root Contains all objects that the bullet can collide with
	 * @param bulletDir Direction that the bullet is travelling in
	 */
	public void hasShot(long startTime, Pane root, boolean bulletDir) {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				long secondsElapsed = (System.currentTimeMillis()-startTime)/1000;
				if (bulletDir == true ) {
					moveX(GameConstants.BULLET_SPEED);
				} else {
					moveX(-GameConstants.BULLET_SPEED);
				}
				if (secondsElapsed >= 2 || checkCollided(root)){
					setDead(true);
					stop();
				}
			}
		};
		timer.start();
	}
	
	/**
	 * Checks whether a billet has collided with an object
	 * @param root Contains all objects that the bullet can collide with
	 * @return Whether the bullet has collided with an object
	 */
	public boolean checkCollided(Pane root) {
	    collisionChecker.checkForCollision(this, root);
		return collisionChecker.getCollided();
	}
}
