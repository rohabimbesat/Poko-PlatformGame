package com.halflife.enemies;

import com.halflife.entities.Bullet;

import com.halflife.entities.Player;
import com.halflife.entities.RectObject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Game;
import main.GameConstants;
import main.Mathematics;

public class BaseEnemy extends RectObject {
	
	private boolean startMoving;

	private double velX = 0;
	private double velY = 0;
	
	public BaseEnemy(int x, int y, int width, int height) {
		
		super(x, y, width, height, GameConstants.TYPE_ENEMY, Color.RED);		
		// TODO Auto-generated constructor stub
		startMoving = false;
	}
	
	public void tick(Player player, Pane root) {
		velX = 0;
		if (isCollidedWithBullet(root)) {
			this.setDead(true);
		}
		if (isCollidedWithPlayer(player) && !isDead()) {
			player.loseLife(root);
		}
		if (isNear(player) && !isAtEdgeR(root) && !isAtEdgeL(root)){
			if (Mathematics.getDistanceX(this, player)< 0)
				velX = 2;
			else if (Mathematics.getDistanceX(this, player)> 0)
				velX = -2;
		}
		if (isAtEdgeR(root)) {
			this.setTranslateX(this.getTranslateX() - 1);
		}
		if (isAtEdgeL(root)) {
			this.setTranslateX(this.getTranslateX() + 1);
		}
		moveX((int)velX);
		//moveY((int)velY);	
	}
	
	public void PlayerNear(Player player) {
		// detection for if the player comes within x pixels
		
		startMoving = true;
		while(isNear(player) && !isDead()) {
			this.setVelX(5);
		}
	}
	
	public boolean isNear(Player player) {
		if (Mathematics.getDistanceX(this, player) < 400 && Mathematics.getDistanceY(this, player) < 30) {
			return true;
		}
		return false;
	}

	public boolean isCollidedWithPlayer(Player player)
	{
		if(this.getBoundsInParent().intersects(player.getBoundsInParent()))
			return true;
		return false;
	}
	
	public boolean isAtEdgeR(Pane root)
	{
		 for (Node static_bloc : Game.getAllNodes(root)) {
			 RectObject b = (RectObject) static_bloc;
			    if (b != this && b.getType().equals(GameConstants.TYPE_EDGE_PLATFORM_RIGHT))
			      if (this.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
			    	 	 return true;
			      }
			    }
		 return false;
	}
	
	public boolean isAtEdgeL(Pane root)
	{
		 for (Node static_bloc : Game.getAllNodes(root)) {
			 RectObject b = (RectObject) static_bloc;
			    if (b != this && b.getType().equals(GameConstants.TYPE_EDGE_PLATFORM_LEFT))
			      if (this.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
			    	 	 return true;
			      }
			    }
		 return false;
	}
	
	public boolean isCollidedWithBullet(Pane root)
	{
		 for (Node static_bloc : Game.getAllNodes(root)) {
			 RectObject b = (RectObject) static_bloc;
			    if (b != this && b.getType().equals(GameConstants.TYPE_PLAYER_BULLET))
			      if (this.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
			    	 	 return true;
			      }
			    }
		 return false;
	}
	
	public void setVelX(double v) {
		this.velX = v;
	}
	public void setVelY(double v) {
		this.velY = v;
	}
	
	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}
	
}
