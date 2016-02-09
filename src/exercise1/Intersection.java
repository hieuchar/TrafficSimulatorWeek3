package exercise1;

import java.awt.Color;
import java.util.ArrayList;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Intersection extends Actor{
	private static final int GREEN_COUNT = 100;
	private static final int YELLOW_COUNT = 25;
	private static final int RED_COUNT = GREEN_COUNT + YELLOW_COUNT;
	private ArrayList<IntersectionListener> curO = new ArrayList<IntersectionListener>(); 
	private ArrayList<IntersectionListener> prevO = new ArrayList<IntersectionListener>();
	private ArrayList<IntersectionListener> curI = new ArrayList<IntersectionListener>();
	private ArrayList<IntersectionListener> prevI = new ArrayList<IntersectionListener>(); 
	private ArrayList<IntersectionListener> exit =  new ArrayList<IntersectionListener>();
	private TrafficLight tf1 = null;
	private TrafficLight tf2 = null;
	private TrafficLight tf3 = null;
	private TrafficLight tf4 = null;
	private int vLightCounter = 0;
	private int hLightCounter = 0;
	TrafficLight.Color verticalColor;
	TrafficLight.Color horizColor;
	public Intersection(){
		GreenfootImage intersection = new GreenfootImage(TrafficWorld.ROADWIDTH, TrafficWorld.ROADWIDTH);
		intersection.setColor(Color.BLACK);
		intersection.fill();
		setImage(intersection);
		
	}
	public void addLights(){
		verticalColor = TrafficLight.Color.RED;
		horizColor = TrafficLight.Color.GREEN;
		tf1 = new TrafficLight(verticalColor);
		tf2 = new TrafficLight(verticalColor);
		tf2.setRotation(Orientation.WEST.getRotation());
		tf3 = new TrafficLight(horizColor);
		tf3.setRotation(Orientation.SOUTH.getRotation());
		tf4 = new TrafficLight(horizColor);
		tf4.setRotation(Orientation.NORTH.getRotation());
		getWorld().addObject(tf1, this.getX(), getY() + TrafficWorld.ROADWIDTH/2 + tf1.getImage().getHeight()/2);
		getWorld().addObject(tf2, this.getX(), getY() - TrafficWorld.ROADWIDTH/2 - tf2.getImage().getHeight()/2);
		getWorld().addObject(tf3, this.getX() + TrafficWorld.ROADWIDTH/2 + tf3.getImage().getWidth(), getY());
		getWorld().addObject(tf4, this.getX() - TrafficWorld.ROADWIDTH/2 - tf4.getImage().getWidth(), getY());
	}
	public void act(){
		vLightCounter++;
		hLightCounter++;
		switch(verticalColor){
			case GREEN:
				if(vLightCounter == GREEN_COUNT){
					verticalColor = TrafficLight.Color.YELLOW;
					tf1.setColor(verticalColor);
					tf2.setColor(verticalColor);
					vLightCounter = 0;
				}
				break;
			case YELLOW:
				if(vLightCounter == YELLOW_COUNT){
					verticalColor = TrafficLight.Color.RED;
					tf1.setColor(verticalColor);
					tf2.setColor(verticalColor);
					vLightCounter = 0;
				}
				break;
			case RED:
				if(vLightCounter == RED_COUNT){
					verticalColor = TrafficLight.Color.GREEN;
					tf1.setColor(verticalColor);
					tf2.setColor(verticalColor);
					vLightCounter = 0;
				}
				break;
		}
		switch(horizColor){
			case GREEN:
				if(hLightCounter == GREEN_COUNT){
					horizColor = TrafficLight.Color.YELLOW;
					tf3.setColor(horizColor);
					tf4.setColor(horizColor);
					hLightCounter = 0;
				}
				break;
			case YELLOW:
				if(hLightCounter == YELLOW_COUNT){
					horizColor = TrafficLight.Color.RED;
					tf3.setColor(horizColor);
					tf4.setColor(horizColor);
					hLightCounter = 0;
				}
				break;
			case RED:
				if(hLightCounter == RED_COUNT){
					horizColor = TrafficLight.Color.GREEN;
					tf3.setColor(horizColor);
					tf4.setColor(horizColor);
					hLightCounter = 0;
				}
				break;
		}
		notifyInCars();
		notifyApproachingCars();
	//	notifyExitingCars();
		
		
	}
	public void notifyApproachingCars(){
		curO = (ArrayList<IntersectionListener>) (this.getObjectsInRange(TrafficWorld.ROADWIDTH,IntersectionListener.class));
		for(IntersectionListener c : curO){
			if(prevO.contains(c)){
				c.approachingIntersection(this);
			}
		}
		prevO = curO;
	}
	public void notifyInCars(){
		curI = (ArrayList<IntersectionListener>) (this.getIntersectingObjects(IntersectionListener.class));
		for(IntersectionListener c: curI){
			if(prevI.contains(c)){
				c.inIntersection(this);
			}
		}
		prevI = curI;
	}
}