package main;

import com.halflife.entities.RectObject;

public class Mathematics {

	/**
	 * Gets the distance in X coordinates between two given objects
	 * @param obj1 Object 1 
	 * @param obj2 Object 2
	 * @return The distance on the horizontal plane between object 1 and object 2
	 */
	public static int getDistanceX(RectObject obj1, RectObject obj2) {
		
		return (int) (obj1.getTranslateX() - obj2.getTranslateX());
	}
	
	/**
	 * Gets the distance in Y coordinates between two given objects
	 * @param obj1 Object 1 
	 * @param obj2 Object 2
	 * @return The distance on the vertical plane between object 1 and object 2
	 */
	public static int getDistanceY(RectObject obj1, RectObject obj2) {
		
		return (int) Math.abs((obj1.getTranslateY() - obj2.getTranslateY()));
	}
	
	public int getSpeed(int distance) {
		return 0;
	}
}
