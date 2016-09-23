package Window;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import AnimationsandImages.Images;

//This class will create a new Jframe, add the game panel, and run the game
public class JFrameWindow {

	//Variable for the images
	static Images img;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("One Piece");//create a new jframe and give it a ttiel
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//make the close button close the window
		frame.setResizable(false);//make the jframe not resizeable
		frame.setLayout(new BorderLayout());//determine size to allow components to be layed out well
		frame.add(new GamePanel(1000,600,60),BorderLayout.CENTER);// add the gamepanel
		frame.pack();//pack frame to make sure everything is at the right size
		frame.setLocationRelativeTo(null);//make it centered
		frame.setVisible(true);//make it visible
		
		//Create the textures for the game
		img = new Images();
		}
	
	//Create a method to return the images
	public static Images getImages(){
		//Return the images
		return img;
	}
}
