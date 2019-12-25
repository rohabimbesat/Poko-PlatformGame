package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.halflife.entities.Player;
import com.halflife.entities.RectObject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/** Message --- A serialisable object containing the data to be sent across the network.
 * All sent objects need to also be serialisable
 * 
 * @author Daniel
 *
 */
public class Message implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sender =null;
	private String text=null;
	private String[] level=null;
	private Double x=null;
	private Double y=null;
	private String type;

	  /** Constructor for Message
	   * 
	   * @param text String of text to be sent
	   */
	public Message(String text) {
		//this.sender = sender;
		this.text = text;
		setType("text");
	}	  
	/** Constructor for Message
	 * 
	 * @param text String of text to be sent
	 */
	public Message(String[] level) {
		this.setLevel(level);
		setType("level");
	}
	/** Constructor for Message (For sending coordinates)
	 * 
	 * @param x Double, x coordinate
	 * @param y Double, y coordinate
	 */
	public Message(Double x, Double y) {
		this.setX(x);
		this.setY(y);
		setType("coords");
	}
	/** Sets the sender attribute of the message
	 * 
	 * @param sender String of player name
	 */
	void setSender(String sender) {
		this.sender = sender;
	}
	/** Get the sender attribute of the message
	 * (Nickname of the client that sent the message)
	 * @return sender as a String
	 */
	public String getSender() {
		return sender;
	}
	/** Get the text content of the message
	 * 
	 * @return text as a String
	 */
	public String getText() {
		return text;
	}
	
	public String toString() {
		return "From " + sender + ": " + text;
	}
	/**
	 * Get the level attribute of the message (when sending a level)
	 * @return level as a string array
	 */
	public String[] getLevel() {
		return level;
	}

	/**
	 * Set the level attribute of the message
	 * @param level String array of level in the standard format
	 */
	private void setLevel(String[] level) {
		this.level = level;
	}
	
	/**
	 * get X coordinate attribute  of the message
	 * @return x coordinate as Double
	 */
	public Double getX() {
		return x;
	}
	
	/**
	 * set X coordinate attribute  of the message
	 * @param x coordinate as Double
	 */
	private void setX(Double x) {
		this.x = x;
	}
	
	/**
	 * get Y coordinate attribute  of the message
	 * @return y coordinate as Double
	 */
	public Double getY() {
		return y;
	}
	/**
	 * set Y coordinate attribute  of the message
	 * @param y coordinate as Double
	 */
	private void setY(Double y) {
		this.y = y;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
