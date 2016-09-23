package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import AnimationsandImages.Images;
import Window.GamePanel;
import Window.JFrameWindow;

//This is the pause menu state extends from game state
public class PauseMenu extends GameState {

	//Options in the pausemenu
	private String[] options = new String[] {"RESUME","RESTART","MAIN MENU","EXIT"};

	//Current option being selected
	private int currentoption = 0;

	//Variable for the images
	Images img = JFrameWindow.getImages();

	protected PauseMenu(GameStateManager gsm) {
		super(gsm);
	}


	//Initalzing method
	public void initial() {


	}

	//Update method
	public void update() {


	}


	//Render method
	public void render(Graphics g) {		
		//Draw background
		g.drawImage(img.PauseMenubg,0,0,null);

		//Draw title
		g.setColor(Color.red);
		g.setFont(new Font("Comic San", Font.PLAIN, 64));//set the font
		g.drawString("PAUSE MENU",GamePanel.Width()/2 - 180, 60);

		//Loop through all the options to draw them all out
		for(int i = 0; i < options.length; i++)
		{
			//If the for loop is on the current option being selected
			if(i == currentoption)
				g.setColor(new Color(255,62,150));

			else
				g.setColor(Color.white);

			g.setFont(new Font("Arial", Font.PLAIN, 64));//set the font
			g.drawString(options[i], GamePanel.Width()/2 - 140, 200 + i*100);//draw options
		}//draw options
		
	}//if key pressed

	//Listen for key pressed
	public void keyPressed(int k) {

		//If the key pressed is the down arrow key
		if(k == KeyEvent.VK_DOWN)
		{
			currentoption++;//increase the current option
			if(currentoption >= options.length)//if last option
				currentoption = 0;//set to first option
		}

		//If the key pressed is the up arrow key
		else if(k == KeyEvent.VK_UP)
		{
			currentoption--;//decrease the current option
			if(currentoption < 0)//if first option
				currentoption = options.length - 1;//set to last option
		}

		//If the enter key is pressed
		if(k == KeyEvent.VK_ENTER)
		{
			//If the current option is RESUME
			if(currentoption == 0)
			{
				//Continue the first level
				GameStateManager.setStateResume(GameStateManager.previousState);//set the state to the LEVEL 1 STATE
			}
			else if(currentoption == 1)//If the current option is RESTART
			{
				GameStateManager.setState(GameStateManager.previousState);//go to the first level
			}
			else if(currentoption == 2)//If the current option is MAIN MENU
			{
				GameStateManager.setState(GameStateManager.MenuState);//Set state to main menu
			}
			
			else if(currentoption == 3)//If the current option is exit
				System.exit(0);//close window
			
		}//If enter
	}

	//Listen for key released
	public void keyReleased(int k) {


	}

}
