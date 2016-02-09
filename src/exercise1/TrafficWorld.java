package exercise1;

import java.awt.Color;
import java.util.Random;

import greenfoot.GreenfootImage;
import greenfoot.World;

public class TrafficWorld extends World {
	public static final int ROADWIDTH = 50;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	private static final int CELL_SIZE = 1;
	private static final int NUMHROAD = 5;
	private static final int NUMVROAD = 7;
	private static final int CARWIDTH = 20;
	private static final int LIGHTWIDTH = 10;
	private static final int VGAP = (HEIGHT - (NUMHROAD * ROADWIDTH)) / (NUMHROAD - 1);
	private static final int HGAP = (WIDTH - (NUMVROAD * ROADWIDTH)) / (NUMVROAD - 1);
	private static Road[] horizRoad = new Road[NUMHROAD];
	private static Road[] vertRoad = new Road[NUMVROAD];
	public TrafficWorld()
	{
		super(WIDTH,HEIGHT,CELL_SIZE,true);
		GreenfootImage background = this.getBackground();
		background.setColor(Color.GREEN);
		background.fill();
		drawHRoads();
		drawVRoads();
		drawInter();
		//drawCar();
	}
	public void drawCar(){
		Car c = new Car(Orientation.SOUTH);
		c.setRotation(c.getOrientation().getRotation());
		this.addObject(c, (HGAP + ROADWIDTH)  + (CARWIDTH / 2) , 1 );
	}
	public void act(){
		Random rand = new Random();
		int x = rand.nextInt(5);
		int y = rand.nextInt(7);
		int r = rand.nextInt(100) + 1;
		boolean hor = rand.nextBoolean();
		boolean flip = rand.nextBoolean();
		if(r > 98){
			if(hor){
				if(flip){
					Car c = new Car(Orientation.EAST);
					c.setRotation(c.getOrientation().getRotation());
					this.addObject(c, 0, (VGAP + ROADWIDTH) * x  +  (CARWIDTH) + (CARWIDTH / 2)  + LIGHTWIDTH);
				}
				else{
					Car c = new Car(Orientation.WEST);
					c.setRotation(c.getOrientation().getRotation());
					this.addObject(c, WIDTH, (VGAP + ROADWIDTH) * x   + LIGHTWIDTH);
				}
			}
			else{
				if(flip){
					Car c = new Car(Orientation.NORTH);
					c.setRotation(c.getOrientation().getRotation());
					this.addObject(c, (HGAP + ROADWIDTH) * y  + (CARWIDTH / 2) +  (CARWIDTH) + LIGHTWIDTH, HEIGHT - 1);
				}
				else{
					Car c = new Car(Orientation.SOUTH);
					c.setRotation(c.getOrientation().getRotation());
					this.addObject(c, (HGAP + ROADWIDTH) * y  + LIGHTWIDTH, 1);
				}
			}
		}
	}
	public void drawHRoads(){
		for(int i = 0; i < NUMHROAD; i++){
			horizRoad[i] = new Road(true);
			this.addObject(horizRoad[i], WIDTH / 2, (VGAP + ROADWIDTH) * i + (ROADWIDTH / 2));
		}
	}
	public void drawVRoads(){
		for(int i = 0; i < NUMVROAD; i++){
			vertRoad[i] = new Road(false);
			this.addObject(vertRoad[i],(HGAP + ROADWIDTH) * i + (ROADWIDTH / 2),  HEIGHT / 2 );
		}
	}
	public void drawInter(){
		for (int i = 0; i < horizRoad.length; i ++){
			for(int j = 0; j < vertRoad.length; j++){
				Intersection intersection = new Intersection();
				this.addObject(intersection, vertRoad[j].getX(), horizRoad[i].getY());
				intersection.addLights();
			}
		}
	}
	
}
