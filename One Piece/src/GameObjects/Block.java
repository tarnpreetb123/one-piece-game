package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import AnimationsandImages.Camera;
import AnimationsandImages.Images;
import Window.JFrameWindow;

//This class will create a block GameObject
public class Block extends GameObject {

	//Variables for the size of the blocks - predetermined size
	public static int width = 32;
	public static int height = 32;
	
	//Variable for what type of block it is
	private int type;
	
	//Variable for the images
	Images img = JFrameWindow.getImages();
	
	//Create a new block - Can't make it any size
	public Block(int x, int y,int type, int ObjectId) {
		super(x, y, width, height, ObjectId);//GameObject needs to know size
		this.type = type;//set the block type
		
		//Set the check x and check y
		checkx = x;
		checky = y;
	}

	public void initial(Camera cam) {
	}

	//This method will update the block - non moving blocks won't using it - blocks will move in the background to create an illusion of movement
	public void update(ArrayList<GameObject> objects, Camera cam) {
		x += cam.getCamxspeed();//update the x
		y -= cam.getCamyspeed()/2;//update the y
	}

	//This method will draw the blocks
	public void render(Graphics g,Camera cam) {
		g.drawImage(img.blocks[type],(int) x,(int) y, null);//draw block image at x and y
		
	}

	//Check if key is pressed - not used
	public void keyPressed(int k) {

	}

	//Check if key is released - not used
	public void keyReleased(int k) {
		
	}
	
	//This method will create a rectangle object were the block is
	public Rectangle getBounds()
	{
		return new Rectangle((int) x, (int) y, width, height);//create and return the new rectangle object
	}

	//Won't be used but required for other game objects
	public Rectangle getBoundsanime() {
		return null;
	}
}
