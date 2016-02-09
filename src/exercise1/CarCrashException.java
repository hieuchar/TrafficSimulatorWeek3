package exercise1;

import java.util.ArrayList;

import greenfoot.GreenfootImage;

public class CarCrashException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CarCrashException(ArrayList<Car> crash){
		for(Car c : crash){
			c.getWorld().addObject(new Explosion(), c.getX(), c.getY());
			c.getWorld().removeObject(c);

		}
	}
}
