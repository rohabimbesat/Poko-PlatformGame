package com.halflife.enemies;

import com.halflife.entities.Player;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import main.GameConstants;

public class Spike extends Polygon implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -926259170349714955L;
	private final String type;
	
	public Spike(double[] points) {
		super(points);
		type = GameConstants.TYPE_SPIKE;
	}
	
	
	public void tick(Player player, Pane root) {
		if(playerCollision(player)) {
			player.loseLife(root);
		}
	}
	
	
	private boolean playerCollision(Player player) {
		if(this.getBoundsInParent().intersects(player.getBoundsInParent()))
			return true;
		return false;
	}
	
	public String getType(){
		return type;
	}

}
