package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import AnimationsandImages.Images;
import Window.GamePanel;
import Window.JFrameWindow;

//This is the  main menu of the game - it extends the GameState
public class MenuState extends GameState {

	//List of all the options on the Main Menu
	private String[] options = new String[] {"PLAY","HELP","EXIT"};
	
	//Current option being selected
	private int currentoption = 0;
	
	//Variable to keep track if the states have already been added
	private boolean statesloaded = false;
	
	//Variable for the images
	Images img = JFrameWindow.getImages();
	
	//Constructor 
	public MenuState(GameStateManager gsm){
		super(gsm);//the given gsm is equal to the gsm in the parent class
	}
	
	public void initial() {
	
	}

	//This method will update the menu
	public void update() {
	
	}

	//This method will display everthing on the screen
	public void render(Graphics g) {
	
		//Set background color
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.Width(), GamePanel.Height());
		
		g.setColor(Color.blue);//set color of title
		g.setFont(new Font("Comic San", Font.PLAIN, 80));//set the font
		g.drawString("One Piece",GamePanel.Width()/2 - 180, 75);//draw the title
	
		//Loop through all the options to draw them all out
		for(int i = 0; i < options.length; i++)
		{
			//If the for loop is on the current option being selected
			if(i == currentoption)
				g.setColor(Color.green);
			
			else
				g.setColor(Color.white);
		
			g.setFont(new Font("Arial", Font.PLAIN, 72));//set the font
			g.drawString(options[i], GamePanel.Width()/2 - 90, 220 + i*150);//draw options
		}//for loop
	}//render

	public void keyPressed(int k) {
		
		//Check if the states have been loaded in or not
		if(!statesloaded)//not loaded
		{
			//Add in all the states
			addstate();
			statesloaded = true;//states have now been loaded
		}
		
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
			//If the current option is play
			if(currentoption == 0)
			{
				//Go to the level select menu
				GameStateManager.setState(GameStateManager.LevelSelectState);//set the state to the level select menu
			}
			else if(currentoption == 1)//If the current option is help
			{
				GameStateManager.setState(GameStateManager.HelpMenuState);
			}
			else if(currentoption == 2)//If the currentoption is exit
				System.exit(0);//close window
		}//If enter

	}//if a key is pressed

	//Key released
	public void keyReleased(int k) {
		
	}

	//This method will the add states
	public void addstate() {
		GameStateManager.states.add(new LevelSelectMenu(gsm));//add the level select menu - 1
		GameStateManager.states.add(new HelpMenu(gsm));//add the help menu state - 2
		GameStateManager.states.add(new PauseMenu(gsm));//add the pause menu state - 3
		GameStateManager.states.add(new Level1State(gsm));//add the Level1State - 4
		GameStateManager.states.add(new Level2State(gsm));//add the Level2State - 5
		GameStateManager.states.add(new Level3State(gsm));//add the Level2State - 6
	}

}
