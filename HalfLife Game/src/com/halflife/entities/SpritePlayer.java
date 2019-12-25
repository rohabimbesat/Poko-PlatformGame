package com.halflife.entities;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.SpriteAnimation;

/**
 * Adds a sprite animation on top of a player object
 * @author Rohab
 */
public class SpritePlayer extends Pane {
	
	private Player pl; //Player object that is covered with the sprite
	private SpriteAnimation ani; //Sprite animation
	private boolean forward; //Direction the sprite is facing
	private NetworkedPlayer np;
	
	/**
	 * Constructor of the sprite player
	 */
	public SpritePlayer(int lvlNum,String type) {
		this.forward=true;
		if (type.equals("1player")) {
		this.pl=  new Player(200,0,40,50,Color.PINK,3, lvlNum,false);
		
		this.ani=new SpriteAnimation("player");
		ani.resizeView(-38, -45, 120);
		ani.translateXProperty().bindBidirectional(pl.translateXProperty());
		ani.translateYProperty().bindBidirectional(pl.translateYProperty());
		}
		if(type.equals("2player")) {
			this.pl=  new Player(200,0,40,50,Color.PINK,3, lvlNum,true);
			
			this.ani=new SpriteAnimation("player");
			ani.resizeView(-38, -45, 120);
			ani.translateXProperty().bindBidirectional(pl.translateXProperty());
			ani.translateYProperty().bindBidirectional(pl.translateYProperty());
		}
		if (type.equals("networkedPlayer")) {
		
		this.np= new NetworkedPlayer(200,0,40,50,Color.PINK,3, lvlNum,true);
		this.ani=new SpriteAnimation("player");
		ani.resizeView(-38, -45, 120);
		ani.translateXProperty().bindBidirectional(np.translateXProperty());
		ani.translateYProperty().bindBidirectional(np.translateYProperty());
		}
		
		
		Pane PlayerSprite= new Pane();
		PlayerSprite.getChildren().addAll(ani);
		this.getChildren().add(PlayerSprite);
	}
	
	/**
	 * Returns the player object that the sprite animation is on top of
	 * @return Player object that the sprite animation is on top of
	 */
	public Player GetPlayer() {
		return pl;
	}
	public NetworkedPlayer GetNetPlayer() {
		return np;
	}
	
	/**
	 * Flips the animation for when moving in the opposite direction
	 */
	public void flipbackwards() {
		if (forward) {
			ani.flip();
		}
		forward=false;
	}
	
	/**
	 * Flips the animation back to it's original when moving in the normal direction
	 */
	public void flipforwards() {
		if (!forward) {
			ani.flipnorm();
		}
		forward=true;
	}
	public SpriteAnimation GetAnimation() {
		return ani;
	}
}

