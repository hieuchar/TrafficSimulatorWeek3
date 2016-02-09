package exercise1;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Explosion extends Actor{
	private static int numImages = 3;
    private static GreenfootImage[] images = new GreenfootImage[numImages];   
    private int counter = 0;
    public Explosion() {
        initialiseImages();
        this.setImage(images[0]);        
    }       
    public static void initialiseImages() {
    	images[0] = new GreenfootImage("images/explosion1.png");
		images[1] = new GreenfootImage("images/explosion2.png");
		images[2] = new GreenfootImage("images/explosion3.png");
    }
    public void act()
    { 
    	 if(counter > 20) {
             setImage(images[1]);
         }
         if(counter > 40) {
            setImage(images[2]);
         }
         if(counter > 60) {
             this.getWorld().removeObject(this);
         }
         counter++;
    }
}
