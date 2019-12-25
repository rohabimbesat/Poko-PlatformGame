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
 * Lives --- Adds the hearts to the screen, and controls what happens with them
 * @author Halflife
 */
public class Lives extends Group {
	
	private int lives = 3; //Initial number of lives
	private boolean isDead; //Whether the player is dead
	private ArrayList<ImageView> heartlist = new ArrayList<ImageView>(); //Image list of heart images

	/**
	 * Constructor for displaying hearts on the game window corresponding to lives remaining
	 * @param _lives The number of lives to draw on the game window
	 */
	public Lives(int _lives) {

		super();
		lives = _lives;
		addLives();
	}

	/**
	 * Adds heart images to the screen dependent on how many lives the player has
	 * This is called when the class is instantiated to create the ImageView list
	 */
	public void addLives() {
		this.getChildren().clear();
		Image image = null;
		try {
			image = new Image(new FileInputStream("res/heart-1.png.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setting the image view
		// this.setImage(image);

		List<ImageView> hearts = new ArrayList<ImageView>();
		for (int i = 0; i < lives; i++) {
			hearts.add(new ImageView(image));
		}

		for (int i = 0; i < lives; i++) {
			hearts.get(i).setX(i * 50 + 610);
			hearts.get(i).setY(-10);
			hearts.get(i).setFitHeight(100);
			hearts.get(i).setFitWidth(100);
			hearts.get(i).setPreserveRatio(true);
			this.getChildren().add(hearts.get(i));
			heartlist.add(hearts.get(i));
		}

		isDead = false;
	}
	
	/**
	 * Removes heart image from display
	 */
	public void lostlife() {

		if (lives > 0) {
			this.getChildren().remove(heartlist.get(heartlist.size() - 1));
			heartlist.remove(heartlist.size() - 1);
			lives = lives - 1;
		} else {
			isDead = true;
		}
	}

	/**
	 * Getter for isDead variable
	 * @return Whether the player is dead
	 */
	public boolean isDead() {
		return isDead;
	}
	
	/**
	 * Setter for the lives variable
	 * @param _lives The number of lives to assign
	 */
	public void setLives(int _lives) {
		lives = _lives;
	}
}
