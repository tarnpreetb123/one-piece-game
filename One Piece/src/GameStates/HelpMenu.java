package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import AnimationsandImages.Images;
import Window.GamePanel;
import Window.JFrameWindow;

//This is the HelpMenu extends off of gamestate
public class HelpMenu extends GameState{

	//Variable for the Controls
	//private String[] controls = new String[] {"Move Right","Move Left","Jump","Attacks","Q","W","E"};
	
	//Variable for the images
	Images img = JFrameWindow.getImages();
		
	public HelpMenu(GameStateManager gsm) {
		super(gsm);
	}


	public void initial() {
	
	}


	public void update() {
	
	}


	public void render(Graphics g) {
		//Set background color
		g.drawImage(img.HelpMenubg,0,0,null); 
		
		//Draw title
		g.setColor(Color.white);
		g.setFont(new Font("Comic San", Font.PLAIN, 40));//set the font
		g.drawString("HELP MENU",GamePanel.Width()/2 - 140, 60);
		
		//Draw arrow keys
		g.drawImage(img.arrowkeys,150,140,null);
		
		//Draw controls
		g.setFont(new Font("Arial", Font.PLAIN, 20));//set the font
		g.drawString("Up Arrow Key - Jump", 400, 130);
		g.drawString("Right Arrow Key - Move Right",400,170);
		g.drawString("Left Arrow Key - Move Left",400,210);
		
		//Draw QWE keys
		g.drawImage(img.QWEkeys,150,280,null);
		
		//Draw attack keys
		g.drawString("Q - Pistol Attack",400,260);
		g.drawString("W - Gatling Attack",400,290);
		g.drawString("E - Gatling Haki Attack ",400,320);
		g.drawString("P - Pause Game",400,350);
		
		//Draw check point
		g.drawImage(img.checkpoint[0], 250,360,null);
		g.drawString("Checkpoint",350,400);//label checkpoint
		
		//Draw food
		g.drawImage(img.food,250,440,null);	
		g.drawString("Health Pack",350,460);//label food
		
		//Draw end of level -flag
		g.drawImage(img.flag,250,470,null);
		g.drawString("Flag(end of level)",350,520);
		
		//Draw back button
		g.drawImage(img.backbutton,GamePanel.Width()-200,GamePanel.Height()-150,null);
	}


	public void keyPressed(int k) {
		//If the enter key is pressed
		if(k == KeyEvent.VK_ENTER)
		{
			GameStateManager.setState(GameStateManager.MenuState);//set the state back to menu state
		}
		
	}


	public void keyReleased(int k) {
	
	}

	
}
