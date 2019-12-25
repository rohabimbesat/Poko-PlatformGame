package com.halflife.entities;


import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import main.GameConstants;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * RectObject --- Creates all objects in the game that the
 * player can collide with
 * @author Halflife
 */
public class RectObject extends Rectangle{
	
	protected FadeTransition ft; //Fading effect for certain objects 
    protected Rectangle rect; //Rectangular shape of the object
    public float gravity = 0.5f; //Gravity affecting objects
	private boolean falling = true; //Whether the object is falling
	private boolean jumping = false; //Whether the object is jumping
	protected boolean dead= false; //Whether the object is dead
	private final String type; //Type of the object
	private double x,y,w,h; //Coordinates and dimensions of the object
	private Color col; //Colour of the object

	/**
	 * Constructor of each rectangular object
	 * @param x X coordinate of the object
	 * @param y Y coordinate of the object
	 * @param width Width of the object
	 * @param height Height of the object
	 * @param type Type of the object
	 * @param col Colour of the object
	 */
	public RectObject(double x, double y,int width, int height, String type, Color col){
		super (width,height,col);
		this.type =type;
		this.x=x;
		this.y=y;
		this.w=width;
		this.h=height;
		this.col=col;

	 	setTranslateX(x);
		setTranslateY(y);
	
	}
	
	/**
	 * Movement of the object in the X direction
	 * @param i Amount to move the object by
	 */
	public void moveX(int i) {
		setTranslateX(getTranslateX()+i);
	}
	
	/**
	 * Movement of the object in the Y direction
	 * @param i Amount to move the object by
	 */
	public void moveY(int i) {
		setTranslateY(getTranslateY()+i);	
	}
	

	/**
	 * Creates the bullet object for when the player shoots
	 * @param shooter Player object where the bullet comes from
	 * @param colour Colour of the bullet
	 * @param root Contains all objects that the bullet could interact with
	 * @param bulletDir Determines which direction the bullet travels in
	 * @return Bullet object
	 */
	public Bullet getBullet(RectObject shooter, Color colour, Pane root, boolean bulletDir) {
		int moveBulletX = 0;
		if (bulletDir == true) {
			moveBulletX = 50;
		} else {
			moveBulletX = -30;
		}
		Image img = new Image("bulletColour.png");
		Bullet bullet = new Bullet(shooter.getTranslateX() + moveBulletX, shooter.getTranslateY(), 20, 5, shooter.getType() + "bullet", col, root, bulletDir);
		bullet.setFill(new ImagePattern(img));
		return bullet;
	}
	
	/**
	 * Returns the type of the current object
	 * @return Type of the current object
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns whether the current object is dead or not
	 * @return Whether the current object is dead or not
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Sets the state of deadness for the current object
	 * @param dead Whether the current object is dead or not
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	/**
	 * Returns whether the current object is falling or not
	 * @return Whether the current object is falling or not
	 */
	public boolean isFalling() {
		return falling;
	}
	
	/**
	 * Sets the state of falling for the current object
	 * @param falling Whether the current object is falling or not
	 */
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	
	/**
	 * Returns whether the current object is jumping or not
	 * @return Whether the current object is jumping or not
	 */
	public boolean isJumping() {
		return jumping;
	}
	
	/**
	 * Sets the state of jumping for the current object
	 * @param jumping Whether the current object is jumping or not
	 */
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	/**
	 * Returns the Y location of the current object
	 * @return Y location of the current object
	 */
	public double getYLocation() {
		return this.getTranslateY();
	}
	
	/**
	 * Returns the X location of the current object
	 * @return X location of the current object
	 */
	public double getXLocation() {
		return this.getTranslateX();
	}
	
	/**
	 * Returns a string variation of the current object
	 * @return String variation of the current object
	 */
	@Override
	public String toString() {
		return "RectObject x: "+x+ " y: "+ y +" w: "+ w +" h: " + h;
	}
}
