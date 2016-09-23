package AnimationsandImages;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	//Variable to store the image
	private BufferedImage image;
	
	//Constructor to load an image from the given path
	public BufferedImage loadImage(String path){
		//Try to load the image if possible
		try {
			image = ImageIO.read(getClass().getResource(path));//load image at the requested name
		} catch (IOException e) {//catch exception if image not loaded
			e.printStackTrace();
		}
		return image;//return the image loaded as a buffered Image
	}
}
