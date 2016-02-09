package exercise1;

import java.awt.Color;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Road extends Actor {
	public Road(boolean horizontal){
		GreenfootImage road;
		if (horizontal){
			road = new GreenfootImage(TrafficWorld.WIDTH,TrafficWorld.ROADWIDTH);
		}
		else{
			road = new GreenfootImage(TrafficWorld.ROADWIDTH,TrafficWorld.WIDTH);
		}
		road.setColor(Color.gray);
		road.fill();
		this.setImage(road);
	}
}
