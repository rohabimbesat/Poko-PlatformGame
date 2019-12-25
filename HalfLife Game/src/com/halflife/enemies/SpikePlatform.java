package com.halflife.enemies;

import com.halflife.entities.RectObject;

import javafx.scene.paint.Color;
import main.GameConstants;

public class SpikePlatform extends RectObject {
	
	private Spike spike;
	private double[] array;
	
	public SpikePlatform(int x, int y, int width, int height) {
		super(x,y,width,height,GameConstants.TYPE_SPIKEPLATFORM,Color.LIGHTSKYBLUE);
		array = new double[] {x+(width/2), y-30, x, y, x+width, y};
		
		spike = new Spike(array);
	}

	public Spike getSpike() {
		return spike;
	}
}
