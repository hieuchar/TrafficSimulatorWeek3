package exercise1;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class TrafficLight extends Actor {
	private Color color;
	public static enum Color{
		RED,YELLOW,GREEN
	}
	public TrafficLight(Color initialColor){
		color = initialColor;
		GreenfootImage image = new GreenfootImage(colorImages[initialColor.ordinal()]);
		setImage(image);
	}
	private String[] colorImages = {"images/trafficLightRed.png","images/trafficLightYellow.png","images/trafficLightGreen.png"};
	public void setColor(Color c){
		color = c;
		setImage(colorImages[c.ordinal()]);
	}
	public Color getColor(){
		return color;
	}
	public String toString(){
		return ("" + color);
	}
}

