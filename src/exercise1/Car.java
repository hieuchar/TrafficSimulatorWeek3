package exercise1;

import java.util.ArrayList;
import java.util.Random;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Car extends Actor implements IntersectionListener {
	private GreenfootImage image;
	private Orientation dir;
	private Orientation turnDir;
	private Speed speed = Speed.GO;
	private boolean passed = false;
	private Intersection current = null;
	private Random rand = new Random();
	private boolean turning = false;
	private int currentX,targetX, currentY, targetY;
	private String[] cars = {"images/topCarBlue.png","images/topCarRed.png","images/topCarPurple.png","images/topCarYellow.png"};
	public Car(Orientation dir){
		Random rand = new Random();
		this.dir = dir;
		int x = rand.nextInt(4);
		image = new GreenfootImage(cars[x]);
		this.setImage(image);
	}
	public static enum Speed{
		STOP,
		SLOW,
		GO;		
	}
	@Override
	public void act(){
		currentX = this.getX();
		currentY = this.getY();
		if(this.isTouching(Car.class)){
			ArrayList<Car> crash = new ArrayList<Car>();
			crash.add(this);
			crash.addAll(this.getIntersectingObjects(Car.class));
			try { 
				throw new CarCrashException(crash);
			} catch (CarCrashException e) {
				
			}
		}
		else {
			this.move(speed.ordinal());
			if(this.isAtEdge()){
				this.getWorld().removeObject(this);
			}
		}		
	}
	public Orientation getOrientation(){
		return dir;
	}
	public int getWidth(){
		return image.getWidth();
	}
	public int getHeight(){
		return image.getHeight();
	}
	public String toString(){
		return ("" + dir);
	}
	public void setSpeed(Speed speed){
		this.speed = speed;
	}
	public void rightTurn(){
		turning = true;
		if(dir.equals(Orientation.SOUTH)){
			targetY = currentY + (TrafficWorld.ROADWIDTH / 2 );
			turnDir = Orientation.WEST;
		}
		else if(dir.equals(Orientation.NORTH)){
			targetY = currentY - (TrafficWorld.ROADWIDTH/ 2);
			turnDir = Orientation.EAST;
		}
		else if(dir.equals(Orientation.EAST)){
			targetX = currentX + ( ( TrafficWorld.ROADWIDTH / 2));
			turnDir = Orientation.SOUTH;
		}
		else if(dir.equals(Orientation.WEST)){
			targetX = currentX - (TrafficWorld.ROADWIDTH / 2);
			turnDir = Orientation.NORTH;
		}
	}
	public void makeTurn(){
		if(dir.equals(Orientation.EAST)){
			if(currentX > targetX){
				this.setRotation(turnDir.getRotation());
				dir = turnDir;
				turning = false;
			}
		}
		else if (dir.equals(Orientation.WEST)){
			if(currentX < targetX){
				this.setRotation(turnDir.getRotation());
				dir = turnDir;
				turning = false;
			}
		}
		else if(dir.equals(Orientation.SOUTH) ){
			if(currentY > targetY){
				this.setRotation(turnDir.getRotation());
				dir = turnDir;
				turning = false;
			}
		}
		else if(dir.equals(Orientation.NORTH)){
			if(currentY < targetY){
				this.setRotation(turnDir.getRotation());
				dir = turnDir;
				turning = false;
			}
		}
	}
	public void leftTurn(){
		turning = true;
		if(dir.equals(Orientation.SOUTH)){
			currentY = this.getY();
			targetY = currentY + (TrafficWorld.ROADWIDTH + TrafficWorld.ROADWIDTH / 4 );
			turnDir = Orientation.EAST;
		}
		else if(dir.equals(Orientation.NORTH)){
			currentY = this.getY();
			targetY = currentY - (TrafficWorld.ROADWIDTH + TrafficWorld.ROADWIDTH / 4);
			turnDir = Orientation.WEST;
		}
		else if(dir.equals(Orientation.EAST)){
			currentX = this.getX();
			targetX = currentX + (TrafficWorld.ROADWIDTH + TrafficWorld.ROADWIDTH / 4);
			turnDir = Orientation.NORTH;
		}
		else if(dir.equals(Orientation.WEST)){
			currentX = this.getX();
			targetX = currentX - (TrafficWorld.ROADWIDTH + TrafficWorld.ROADWIDTH / 4);
			turnDir = Orientation.SOUTH;
		}
	}
	public void calculateTurn(){
		int r = rand.nextInt(100);
		if(r >= 90){
			rightTurn();
		}
		else if(r > 80 && r < 90){
			leftTurn();
		}
	}
	@Override
	public void approachingIntersection(Intersection in){
		if(passed && in.equals(current) ){
			passedIntersection(in);
		}
		else{
			if(!turning){
				calculateTurn();
			}
			else {
				makeTurn();
			}
			current = in;
			passed = false;
			if(dir.equals(Orientation.NORTH) || dir.equals(Orientation.SOUTH)){
				if(in.verticalColor.equals(TrafficLight.Color.RED)){
					speed = Speed.STOP;
				}
				else if(in.verticalColor.equals(TrafficLight.Color.YELLOW)){
					speed = Speed.SLOW;
				}
				else {
					if(turning){
						speed = Speed.SLOW;
					}
					else {
						speed = Speed.GO;
					}
				}
			}
			if(dir.equals(Orientation.EAST) || dir.equals(Orientation.WEST)){
				if(in.horizColor.equals(TrafficLight.Color.RED)){
					speed = Speed.STOP;
				}
				else if(in.horizColor.equals(TrafficLight.Color.YELLOW)){
					speed = Speed.SLOW;
				}
				else {
					if(turning){
						speed = Speed.SLOW;
					}
					else {
						speed = Speed.GO;
					}
				}
			}
		}
	}
	@Override
	public void inIntersection(Intersection in){
		assert in != null;
		if(turning){
			makeTurn();
		}
		current = in;
		passed = true;
		if(dir.equals(Orientation.NORTH) || dir.equals(Orientation.SOUTH)){
			if(in.verticalColor.equals(TrafficLight.Color.RED)){
					speed = Speed.STOP;
			}
			else if(in.verticalColor.equals(TrafficLight.Color.YELLOW)){
				if(turning){
					speed = Speed.SLOW;
				}
				else {
					speed = Speed.GO;
				}
			}
			else {
				if(turning){
					speed = Speed.SLOW;
				}
				else {
					speed = Speed.GO;
				}
			}
		}
		if(dir.equals(Orientation.EAST) || dir.equals(Orientation.WEST)){
			if(in.horizColor.equals(TrafficLight.Color.RED)){
				
					speed = Speed.STOP;
				
			}
			else if(in.horizColor.equals(TrafficLight.Color.YELLOW)){
				if(turning){
					speed = Speed.SLOW;
				}
				else {
					speed = Speed.GO;
				}
			}
			else {
				if(turning){
					speed = Speed.SLOW;
				}
				else {
					speed = Speed.GO;
				}
			}
		}		
	}
	@Override
	public void passedIntersection(Intersection in){
		assert in != null;
		if(turning){
			speed = Speed.SLOW;
		}
		else{
		speed = Speed.GO;
		}
		
	}
}
