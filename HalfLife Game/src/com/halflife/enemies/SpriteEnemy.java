package com.halflife.enemies;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.SpriteAnimation;



/**
 * Adds a sprite animation on top of a Enemy object
 * @author Rohab
 */
public class SpriteEnemy extends Pane {
	
	private BaseEnemy enemy; //Player object that is covered with the sprite
	private SpriteAnimation ani; //Sprite animation
	
	/**
	 * Constructor of the sprite enemy
	 * @param l 
	 * @param k 
	 * @param j 
	 * @param i 
	 */
	public SpriteEnemy(int x, int y, int width, int height) {
		
		this.enemy=  new BaseEnemy(x,y,width,height);
		this.ani=new SpriteAnimation("enemy");
		System.out.println("created animation");
		ani.resizeView(-38, -45, 100);
		ani.translateXProperty().bindBidirectional(enemy.translateXProperty());
		ani.translateYProperty().bindBidirectional(enemy.translateYProperty());
		
		Pane EnemySprite= new Pane();
		EnemySprite.getChildren().addAll(ani);
		this.getChildren().add(EnemySprite);
	}
	
	/**
	 * Returns the enemy object that the sprite animation is on top of
	 * @return Enemy object that the sprite animation is on top of
	 */
	public BaseEnemy GetEnemy() {
		return enemy;
	}
	

}
