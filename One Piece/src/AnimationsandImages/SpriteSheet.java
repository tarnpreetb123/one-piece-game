package AnimationsandImages;

import java.awt.image.BufferedImage;

//This class will get images from a sprite sheet
public class SpriteSheet {
	
	//Variable for SpriteSheet Image
	private BufferedImage image;
	
	//Constructor
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	//This method will get each individual image from within the Sprite sheet and return them
	//Get 250 by 250 pixel images
	public BufferedImage getImage(int x,int y, int width, int height){
		//Make a temporary image
		BufferedImage img = image.getSubimage((x*250) - 250 ,(y*250)-250,width,height);//Get Sub Image makes a new image from the x,y,width,height given
		return img; //return the image	
	}
	
	//Get 75 by 75 pixel images
	public BufferedImage getImage2(int x,int y, int width, int height){
		//Make a temporary image
		BufferedImage img = image.getSubimage((x*75) - 75 ,(y*75)-75,width,height);//Get Sub Image makes a new image from the x,y,width,height given
		return img; //return the image	
	}
	
	//Get 32 by 32 pixel images
	public BufferedImage getImage3(int x,int y, int width, int height){
		//Make a temporary image
		BufferedImage img = image.getSubimage((x*32) - 32 ,(y*32)-32,width,height);//Get Sub Image makes a new image from the x,y,width,height given
		return img; //return the image	
	}

}
