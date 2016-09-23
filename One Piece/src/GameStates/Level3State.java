package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import AnimationsandImages.Images;
import GameObjects.Block;
import GameObjects.CheckPoint;
import GameObjects.Flag;
import GameObjects.Food;
import GameObjects.Handler;
import GameObjects.Marine;
import GameObjects.Player;
import GameObjects.Water;
import Window.JFrameWindow;

//This is the first level of the game - it extends the GameState
public class Level3State extends GameState {

	//Variable for Handler
	Handler handler;

	//Variable for all images
	private Images img = JFrameWindow.getImages();

	//Variable for the delay for the gameover screen
	private long delay = 250;

	//Variable for the current time and the previous time
	private long currenttime;
	private long previoustime;

	//Variable for if the game over screen has been called
	private boolean called = false;
	//Variable for the level image
	private BufferedImage level;// = img.gamestates[1];//get first level;
	private int w ;//= level.getWidth();//width of image
	private int h ;//= level.getHeight();//height of image

	//Constructor
	public Level3State(GameStateManager gsm)
	{
		super(gsm);
		level = img.gamestates[3];//get first level;
		w = level.getWidth();//width of image
		h = level.getHeight();//height of image
	}

	//Initial values
	public void initial() {
		//Create new handler
		handler = new Handler();
		handler.initial();

		//Create blocks everywhere there is a pixel - each pixel in the level image scales by 32 pixels into the game

		//Loop through each x pixel
		for(int i = 0; i < w; i++)
		{
			//Loop through each y pixel
			for(int b = 0; b < h; b++)
			{
				int pixel = level.getRGB(i,b);//Get the pixel value 
				int red = (pixel >> 16) & 0xff;//Get the red value
				int green = (pixel >> 8) & 0xff;//Get the green value
				int blue = (pixel >> 0) & 0xff;//Get the blue value

				if(red == 0 && green == 0 && blue == 0)//if black
				{
					handler.addObject(new Block(i*32,b*32,0,1));//add  dirt block
				}
				
				else if(red == 0 && green == 200 && blue == 100)//if grass color
				{
					handler.addObject(new Block(i*32,b*32,1,1));//add grass block
				}
				
				else if(red == 0 && green == 0 && blue == 250)//if blue
				{
					handler.addObject(new Player(i*32,b*32,handler,0));//add player
				}

				else if(red == 0 && green == 255 && blue == 0)//if green
				{
					handler.addObject(new Marine(i*32,b*32,handler,2));//add player
				}
				
				else if(red == 255 && green == 255 && blue == 0)//if yellow
				{
					handler.addObject(new Flag(i*32,b*32,3));//add a flag
				}
				
				else if(red == 0 && green == 255 && blue == 255)//if aqua
				{
					handler.addObject(new Water(i*32,b*32,4));//add a water block
				}
				
				else if(red == 210 && green == 180 && blue == 140)//if brown
				{
					handler.addObject(new Food(i*32,b*32,5));//add a food item
				}
				
				else if(red == 160 && green == 0 && blue == 255)//if purple
				{
					handler.addObject(new CheckPoint(i*32,b*32,6));//add a check point
				}

			}//for y
		}//for x
	}//intializing method

	//This method will update the level
	public void update() {
		//Update handler
		handler.update();

		//Check if the handler is ready to update levels
		if(handler.getUpdate() == 1)
		{
			handler.clearHandler();//clear the handler to free up memory
			//GameStateManager.updatelevels();//update the number of levels available - fourth level not available 
			
			if(GameStateManager.currentState + 1 < GameStateManager.totalstates())//if there is another state
				GameStateManager.setState(GameStateManager.currentState + 1);//move to next state
			else
				GameStateManager.setState(GameStateManager.MenuState);//otherwise move to main menu
		}//if handler update is 1
		
		else if (handler.getUpdate() == 2)
		{
			//Clear the handler
			handler.clearHandler();
			currenttime = System.currentTimeMillis()/10;
			
			if(!called)//check if the gameover screen has already been called
				previoustime = currenttime;//set previous time to current time
			
			//Check if the current time - previous time is greater than delay
			if((currenttime - previoustime) > delay)
			{	
				GameStateManager.setState(0);//change state to main menu
			}
	
			called = true;//this method has now been called
		}//if handler update is 2
	}//update method

	//This method will render the level
	public void render(Graphics g) {
		//Draw the background
		g.drawImage(img.background,0,0,null);
		//Render handler
		handler.render(g);

		//Draw the string for health
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 20));//set the font
		g.drawString("Health: " + handler.Playerinfo(0), 10 ,20);
		 
		//Draw in the player health bar
		g.setColor(Color.green);
		g.fillRoundRect(10,25,(int) (handler.Playerinfo(0)*1.5),25, 10, 10);//player info 0 is amount of health

		//Draw the string for haki
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 20));//set the font
		g.drawString("Haki: " + handler.Playerinfo(2), 10 ,75);

		//Draw in the players haki bar
		g.setColor(new Color(51,58,68));
		g.fillRoundRect(10,80,(int) (handler.Playerinfo(2)*1.5),25, 10, 10);//player info 2 is amount of haki
		
		//Draw number of lives
		for(int i = 0; i < handler.Playerinfo(1);i++)//player info 1 is number of lives
		{
			g.drawImage(img.strawhat,10 + 30 * i,110,null);
		}

		//Check if the game over screen needs to be printed
		if(handler.getUpdate() == 2)
		{
			g.drawImage(img.gameover,0,0,null);
		}
	}

	//This method will get the keys pressed
	public void keyPressed(int k) {
		//Call handler
		handler.keyPressed(k);

	}

	//This method will get the keys released
	public void keyReleased(int k) {
		//Call handler
		handler.keyReleased(k);

	}

}
