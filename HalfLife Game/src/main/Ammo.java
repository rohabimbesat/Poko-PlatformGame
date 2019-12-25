package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Ammo --- Adds the ammunition images to the player's screen, and controls
 * how much ammo the player has
 * @author Halflife
 */
public class Ammo extends Group {
	
	private int no_of_bullets; //Number of bullets the player starts with
	
	private ArrayList<ImageView>ammoList=new ArrayList<ImageView>(); //Stores the ammo images
	
	/**
	 * Constructor for displaying ammunition (leaves) on the game window
	 * @param bullets Number of bullets to display on the game window
	 */
	public Ammo(int bullets){ 	
		no_of_bullets = bullets;
		addBullets();
	}
	
	/**
	 * Sets the position of each ammo image to be displayed on the game window 
	 * The constants used ensure the images are sufficiently spaced from eachother
	 * @param ammunition A list of each ammo image displayed on the game window
	 */
	public void setAmmoImages(List<ImageView> ammunition) {
		for (int i = 0; i < no_of_bullets; i++) {
			ammunition.get(i).setRotate(90);
	    	ammunition.get(i).setX(i*15 + 625);
	    	ammunition.get(i).setY(60);
	    	ammunition.get(i).setFitHeight(75);
	    	ammunition.get(i).setFitWidth(40);
	    	ammunition.get(i).setPreserveRatio(true);
	    }
	}

	/**
	 * Removes a bullet image from the display
	 */
	public void lostBullet() {
		
		if (no_of_bullets>0) {
			this.getChildren().remove(ammoList.get(ammoList.size()-1));
			ammoList.remove(ammoList.size()-1);
			no_of_bullets --;
		}
	}
	
	/**
	 * Adds bullet images to the screen dependent on how much ammo the player has
	 * This is called when the class is instantiated to create the ImageView list
	 */
	public void addBullets() {
		this.getChildren().clear();
		no_of_bullets = getAmmo();
	    Image image = null;
	    try {
	    	image = new Image(new FileInputStream("res/ammo.png"));
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    }      

	    List<ImageView> ammunition = new ArrayList<ImageView>();
	    for (int i = 0; i < no_of_bullets; i++) {
	    	ammunition.add(new ImageView(image));
	    }

	    setAmmoImages(ammunition);
	    
	    for (int k = 0; k < no_of_bullets; k++) {
	    	this.getChildren().add(ammunition.get(k));
	    	ammoList.add(ammunition.get(k));
	    }	   	   

	}
	
	/**
	 * Setter for no_of_bullets
	 * @param i The number of bullets to assign 'no_of_bullets'
	 */
	public void setAmmo(int i) {
		no_of_bullets = i;
	}
	
	/**
	 * Getter for no_of_bullets
	 * @return The number of bullets the player has 
	 */
	public int getAmmo() {
		return no_of_bullets;
	}
}
