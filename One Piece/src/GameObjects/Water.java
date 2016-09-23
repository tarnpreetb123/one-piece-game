package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import AnimationsandImages.Animation;
import AnimationsandImages.Camera;
import AnimationsandImages.Images;
import Window.JFrameWindow;

public class Water extends GameObject{

	//Variables for the size of the blocks - predetermined size
	public static int width = 32;
	public static int height = 32;

	//Variable for the animation of the block
	private Animation water;
	private int waterspeed = 5;

	//Variable for the images
	Images img = JFrameWindow.getImages();

	//Create a new block - Can't make it any size
	public Water(int x, int y, int ObjectId) {
		super(x, y, width, height, ObjectId);//GameObject needs to know size

		//Set the checkx and checky
		checkx = x;
		checky = y;
		
		//Create animation
		water = new Animation(img.water,waterspeed,true);
	}

	public void initial(Camera cam) {
	}

	//This method will update the block - non moving blocks won't using it - blocks will move in the background to create an illusion of movement
	public void update(ArrayList<GameObject> objects, Camera cam) {
		x += cam.getCamxspeed();//update the x
		y -= cam.getCamyspeed()/2;//update the y

		water.update();
	}

	//This method will draw the water blocks
	public void render(Graphics g,Camera cam) {
		water.draw(g,(int) x,(int) y, width, height,true);

	}

	//Check if key is pressed - not used
	public void keyPressed(int k) {

	}

	//Check if key is released - not used
	public void keyReleased(int k) {

	}

	//This method will create a rectangle object were the water block is
	public Rectangle getBounds()
	{
		return new Rectangle((int) x, (int) y, width, height);//create and return the new rectangle object
	}

	//Won't be used but required for other game objects - only enemy and player
	public Rectangle getBoundsanime() {
		return null;
	}
}


