package AnimationsandImages;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	//Variable to store all the images in the current animation
	private BufferedImage[] frames;
	
	//Variable for the current image being displayed
	private BufferedImage currentimg;
	private int currentframe;
	
	//Variable for the delay between each frame - (speed variable)
	private long delay;
	
	//Variable for the current time and the previous time
	private long currenttime;
	private long previoustime;
	
	//Variable for the total number of frames
	private int totalframes;
	
	//Variable for if the animation has already been played or not
	private boolean played = false;
	private boolean loop = false;
	
	//Variable to hold a frame - have it play for a longer timer
	private int holdframe = -1;
		
	//Constructor variable
	public Animation(BufferedImage[] frames, long delay, boolean loop)
	{
		this.frames = frames;//set the frames
		this.delay = delay; //set the delay
		this.loop = loop;
		currentframe = 0;
		totalframes = frames.length;
		currenttime = System.currentTimeMillis()/10;//get the current time / dividing by 10 makes it easier to set the speed
		previoustime = currenttime;
	}
	
	//This method will set new frames
	public void setFrames(BufferedImage[] frames, long delay, boolean loop)
	{
		this.frames = frames;//set the frames
		this.delay = delay;//set the delay
		this.loop = loop;
		played = false;
		//currentframe = 0;
		totalframes = frames.length;
	}
	
	//This method will update the frame
	public void update(){
		//Check if the current time - previous time is greater than delay
		if((currenttime - previoustime) > delay)
		{
			//Check if the frame is being held
			if(currentframe != holdframe)
				currentframe++;//update the frame
			
			previoustime = currenttime;//update the previous time to equal current time
		}
		
		//Check if the currentframes have reached the totalframes
		if(currentframe > totalframes)
		{
			if(!loop)
				played = true;//the animation has already been played
			
			//if the animation is ment to loop - such as walking 
			if(loop)
				currentframe = 0;//reset current frame to the start
		}

		//Check if the animation should be drawn
		if(currentframe < totalframes)//if the currentframe is less then totalframes
			currentimg = frames[currentframe];//update current image
		
		//update current time
		currenttime = System.currentTimeMillis()/10;
	}

	//This method will draw the current frame
	public void draw(Graphics g, int x, int y, int width, int height, boolean FacingRight){

		if (FacingRight)//if facing right
			g.drawImage(currentimg,x ,y,null);
		else//else flip all images
			g.drawImage(currentimg,x + width ,y, -width, height, null);
	}
	
	//This method will draw the current frame scaled by width and height - in only one direction
	public void drawscaled(Graphics g, int x, int y, int width, int height){
		
			g.drawImage(currentimg,x ,y,width,height,null);
	}
		
	//This method will set which frame to hold
	public void holdframe(int holdframe)
	{
		this.holdframe = holdframe;
	}
	
	//This method will set the current frame
	public void currentframe(int currentframe){
		this.currentframe = currentframe;
	}

	//This method will get the width of the current frame
	public int getFrameWidth()
	{
		return currentimg.getWidth();
	}

	//This method will get the height of the current frame
	public int getFrameHeight()
	{
		return currentimg.getHeight();
	}
	
	//This method will return if the animation has already been played or not
	public boolean played(){
		return played;
	}
	
	//This method will reset animation
	public void reset(){
		currentframe = 0;
		played = false;
	}
	
	//This method will get the index of the currentframe
	public int getcurrentframe(){
		return currentframe;
	}
	

}
