package com.halflife.entities;

import javafx.scene.paint.Color;
import main.GameConstants;

/**
 * Wall --- Creates the invisible wall objects in the game to
 * stop the player from passing certain boundaries
 * @author Halflife
 */
public class Wall extends RectObject{

	/**
	 * Constructor for the wall object
	 * @param x X coordinate of the wall object
	 * @param e Y coordinate of the wall object
	 * @param width Width of the wall object
	 * @param height Height of the wall object
	 */
	public Wall(double x, double y, int width, int height) {
		super(x, y, width, height, GameConstants.TYPE_WALL, Color.TRANSPARENT);
	}

}
