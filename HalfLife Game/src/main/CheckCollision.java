package main;


import com.halflife.entities.RectObject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.Game;
import com.halflife.entities.*;

/**
 * CheckCollision --- Checks whether two given objects have collided
 * @author Halflife
 */
public class CheckCollision {

	private  boolean collided; // Determines whether the given object has collided with another

	/**
	 * Given an object checks whether it has collided with another
	 * @param block The object to check collision with 
	 * @param root The main game Pane, holds details on all current objects within the game
	 * @return The object of which the given object has collided with 
	 */
	public  RectObject checkForCollision(Shape block, Pane root) {
		collided = false;
		  for (Node static_bloc : Game.getAllNodes(root)) {
		     RectObject b = (RectObject) static_bloc;
		     if (!b.getType().equals(GameConstants.TYPE_PLAYER)) {
		    	 if (static_bloc != block && b.getType() != GameConstants.TYPE_EDGE_PLATFORM_RIGHT && b.getType() != GameConstants.TYPE_EDGE_PLATFORM_LEFT) {

				      if (block.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
				    	  
				    	  collided = true;
				    	  return (RectObject) static_bloc;
				      }
				    }
				  }

				  if (getCollided()) {
				    block.setFill(Color.RED);
				  } else {
				    block.setFill(Color.WHITE);
				  }
		     }
		return null;
		    
	}

	/**
	 * Getter for collided 
	 * @return Whether the associated object is collided with anything
	 */
	public  boolean getCollided() {
		return collided;
	}
}
