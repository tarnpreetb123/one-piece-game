package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import AnimationsandImages.Camera;
import AnimationsandImages.Images;
import Window.JFrameWindow;

//This class will create a food object that will give the player more health
public class Food extends GameObject{

	//Variables for the size of the flag - predetermined size
	public static int width = 69;
	public static int height = 30;

	//Variable for the images
	Images img = JFrameWindow.getImages();

	//Create a new flag
	public Food(int x, int y, int ObjectId) {
		super(x, y, width, height, ObjectId);

		//Set the check x and check y
		checkx = x;
		checky = y;
	}


	//Initializing method
	public void initial(Camera cam) {
	}

	//Update the flag
	public void update(ArrayList<GameObject> objects, Camera cam) {
		x += cam.getCamxspeed();//update the x
		y -= cam.getCamyspeed()/2;//update the y

	}

	//Draw the flag
	public void render(Graphics g, Camera cam) {
		g.drawImage(img.food,(int) x, (int) y, width, height,null);
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

