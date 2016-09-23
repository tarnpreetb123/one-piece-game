package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import AnimationsandImages.Images;
import Window.GamePanel;
import Window.JFrameWindow;

//This is the LevelSelectMenu State extends from GameState
public class LevelSelectMenu extends GameState {

	//List of all the levels on the Menu
	private String[] levels = new String[] {"1","2","3","4","5","6","7","8","9","10"};
		
	//Current level being selected
	private int currentoption = 0;
	
	//Variable for the images
	Images img = JFrameWindow.getImages();
	
	public LevelSelectMenu(GameStateManager gsm) {
		super(gsm);
	}


	public void initial() {
	
	}


	public void update() {
	
	}


	public void render(Graphics g) {
		g.drawImage(img.LevelSelectMenubg,0,0,null);//draw background image
		
		//Draw title
		g.setFont(new Font("Comic San", Font.PLAIN, 50));//set the font
		g.drawString("LEVEL SELECT",GamePanel.Width()/2 - 180, 60);
		
		//Loop through all the levels to draw them all out
		for(int i = 0; i < levels.length; i++)
		{
			//Check if the Current String is an available level or not
			if(i <= GameStateManager.levelsavailable)
				g.setColor(new Color(255,244,242));
			else 
				g.setColor(Color.white);
			
			//Draw a square
			if(i < 5)//first 5 levels
				g.fillRect(150*i + 150 ,(127 - 32),100,100);
			else if(i < 10)//next 5 levels
				g.fillRect(150*(i-5) + 150 ,(127 - 32) + 150,100,100);
			
			//If the current i is on the current option being selected
			if(i == currentoption)
				g.setColor(Color.green);//set color green

			else// 
				g.setColor(new Color(108,145,137));//set color normal
			
			//Check if the Current String is an available level or not
			if(i <= GameStateManager.levelsavailable)//draw level number
			{
				g.setFont(new Font("Arial", Font.PLAIN, 50));//set the font
				
				if(i < 5)//first 5 levels
					g.drawString(levels[i], (150*i + 200) - 15, 165);//draw the string
				else if(i < 10)//next 5 levels
					g.drawString(levels[i], (150*(i-5) + 200) - 15, 165 + 150);//draw the string
			}
			else//draw a lock image 
			{
				if(i < 5)//first 5 levels
					g.drawImage(img.lock,150*i + 150 ,(127 - 32),null);
				else if(i < 10)//next 5 levels
					g.drawImage(img.lock,150*(i-5) + 150 ,(127 - 32) + 150,null);
			}
		}//for loop
	}


	public void keyPressed(int k) {
	
		//If the key pressed is the down arrow key
		if(k == KeyEvent.VK_RIGHT)
		{
			currentoption++;//increase the current level being selected
			if(currentoption > GameStateManager.levelsavailable)//if last level
				currentoption = 0;//set to first option
		}
		
		//If the key pressed is the up arrow key
		else if(k == KeyEvent.VK_LEFT)
		{
			currentoption--;//decrease the current level selected
			if(currentoption < 0)//if left on first level
				currentoption = GameStateManager.levelsavailable;//set to last available level
		}

		//If the enter key is pressed
		if(k == KeyEvent.VK_ENTER)
		{
			//If the current option is level 1
			if(currentoption == 0)
			{
				//Go to the first level
				GameStateManager.setState(GameStateManager.Level1State);//set the state to the first level
			}
			else if(currentoption == 1)//If the current option is level 2
			{
				GameStateManager.setState(GameStateManager.Level2State);//set the state to the second level
			}
			else if(currentoption == 2)//If the current option is level 3
			{
				GameStateManager.setState(GameStateManager.Level3State);//set the state to the third level
			}
		}//If enter
		
		//If the escape key is pressed
		else if(k == KeyEvent.VK_ESCAPE)
			System.exit(0);//close window
		
	}


	public void keyReleased(int k) {
	
	}
	
}
