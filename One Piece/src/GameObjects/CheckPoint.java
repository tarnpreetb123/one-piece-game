package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import AnimationsandImages.Animation;
import AnimationsandImages.Camera;
import AnimationsandImages.Images;
import Window.JFrameWindow;

//This class will be a new checkpoint extends from gameobject
public class CheckPoint extends GameObject {
	//Variables for the size of the flag - predetermined size
	public static int width = 64;
	public static int height = 64;

	//Variable for the images
	Images img = JFrameWindow.getImages();

	//Variable for the animation of the checkpoint
	private Animation checkpoint;
	private int checkspeed = 20;

		
	//Create a new flag
	public CheckPoint(int x, int y, int ObjectId) {
		super(x, y, width, height, ObjectId);

		//Set the checkx and checky
		checkx = x;
		checky = y;
		
		//Create animation
		checkpoint = new Animation(img.checkpoint,checkspeed,true);
	}


	//Initializing method
	public void initial(Camera cam) {

	}

	//Update the flag
	public void update(ArrayList<GameObject> objects, Camera cam) {
		x += cam.getCamxspeed();//update the x
		y -= cam.getCamyspeed()/2;//update the y

		//If hit - if player is touching
		if(hit)
			checkpoint.update();
	}

	//Draw the flag
	public void render(Graphics g, Camera cam) {
		
		//If not begin hit - touched
		if(!hit)
			g.drawImage(img.checkpoint[0],(int) x, (int) y, width, height,null);
		
		//If hit - if player is touching
		if(hit)
			checkpoint.drawscaled(g,(int) x, (int) y, width, height);//draw the animation
	}

	//If a key is pressed
	public void keyPressed(int k) {


	}

	//If a key is released
	public void keyReleased(int k) {


	}

	//Get the bounds of the flag
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);//create and return the new rectangle object
	}

	//Get the bounds of the animation of the flag
	public Rectangle getBoundsanime() {

		return null;
	}
}
