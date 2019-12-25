package com.halflife.entities;

import javafx.scene.paint.Color;
import main.GameConstants;

/**
 * Floor --- Creates the invisible floor objects in the game to
 * stop the player from passing certain boundaries
 * @author Halflife
 */
public class Floor extends RectObject{

	/**
	 * Constructor for the floor objects
	 * @param x X coordinate of the floor object
	 * @param y Y coordinate of the floor object
	 * @param width Width of the floor object
	 * @param height Height of the floor object
	 */
	public Floor(double x, double y, int width, int height) {
		super(x, y, width, height, GameConstants.TYPE_FLOOR, Color.RED);
	}
}
