package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * CountdownTimer --- Counts the amount of time the player has been alive for,
 * and displays when the level is completed.
 * @author Rohab
 */
public class CountdownTimer extends Pane{
	
	private Timeline ticker; //Timer that does the counting
	private int tmp=0; //Timer as an integer
	private String emp=""; //Text for the timer
	private Label label=new Label("0"); //Label for the timer
	
	/**
	 * Constructor for the timer, starts it
	 */
	public CountdownTimer() {
		label.setFont(javafx.scene.text.Font.font(50 ));
		label.setTextFill(Color.valueOf("#C2C1BB"));
		label.setTranslateX(0 ); 
		label.setTranslateY(0);
		getChildren().add(label);
		ticker= new Timeline(new KeyFrame(Duration.seconds(1), e-> timelabel()));
		ticker.setCycleCount(Timeline.INDEFINITE); //change the clock
		ticker.play();  
	}
	
	/**
	 * Increments the timer and displays it on the screen
	 */
	private void timelabel() {
		if (tmp>-1) {
			tmp++;
		}
		emp=tmp+"";
		label.setText(emp);
		}
	
	/**
	 * Gets the time of the timer
	 * @return Time of the timer
	 */
	public int getTime() {
		return tmp;
	}
	
	/**
	 * Resets the time of the timer
	 */
	public void resetTime() {
		tmp=0;
	}
	
	/**
	 * Pauses the timer
	 */
	public void pauseTime() {
		ticker.pause();
	}
	
	/**
	 * Continues the timer
	 */
	public void continueTime() {
		ticker.play();
	}
}


