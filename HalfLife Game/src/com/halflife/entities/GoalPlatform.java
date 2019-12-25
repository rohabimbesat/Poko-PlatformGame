package com.halflife.entities;

import javafx.scene.paint.Color;
import main.GameConstants;

/**
 * GoalPlatform --- Creates the end platform objects for, so that the
 * player can complete each level
 * @author Halflife
 */
public class GoalPlatform extends RectObject {

	/**
	 * Constructor for the goal platform object
	 * @param x X coordinate of the goal platform object
	 * @param y Y coordinate of the goal platform object
	 * @param width Width of the goal platform object
	 * @param height Height of the goal platform object
	 */
	public GoalPlatform(double x, double y, int width, int height) {
		super(x, y, width, height, GameConstants.TYPE_GOAL, Color.valueOf("#231082"));
	}
}
