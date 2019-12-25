package main;

import java.awt.MouseInfo;
import java.awt.Point;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * SpriteFollower --- A class to create the level menu sprite that follows the mouse on the screen
 * @author Rohab
 *
 */
public class SpriteFollower extends Pane {
	SpriteAnimation sp= new SpriteAnimation("player");
	double x;
	double y;
	/** 
	 * Constructor for Spritefollower, requires the pane to access its size and mouse movements on the pane,
	 * it translates the sprite animation according to mouse movements
	 * @param p
	 */
	
	public SpriteFollower(Pane p){
	
	sp.resizeView(0, 0, 120);
		AnimationTimer timer = new AnimationTimer() {
	        @Override
	        
	        public void handle(long now) {
	            TranslateTransition tt = new TranslateTransition(Duration.millis(200), sp);
	           // Point mouse = MouseInfo.getPointerInfo().getLocation();
	          
	            
	        	p.setOnMouseMoved(new EventHandler<MouseEvent>() 
	    		{
	    		  @Override
	    		  public void handle(MouseEvent event) {
	    		   x=event.getSceneX();
	    		  x=x-50;
	    		  if (sp.getTranslateX()>x) {
	    			  sp.flip();
	    		  }
	    		  else {
	    			  sp.flipnorm();
	    				 
	    			  
	    		  }
	    		  //  System.out.println(event.getScreenY());
	    		  }
	    		});
	           // x = mouse.getX();
	          //  y = mouse.getY();
	           // System.out.println(x);
	        	 tt.setToX(x);
	    		   tt.play();

	           
	           // tt.setToY(y);

	            
	        }
	    };

	    timer.start();
	    getChildren().add(sp);
	}
	/**
	 * Get SpriteAnimation
	 * @return SpriteAnimation
	 */
	public SpriteAnimation getSpriteAnimation(){
		return sp;
	}
	
}
