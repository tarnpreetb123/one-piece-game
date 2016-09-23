package Window;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import GameStates.GameStateManager;

//This class will call the game state manager for the current state and update/render/keylisten that state
public class GamePanel extends LASSJPanel {

	//Variables for width and height
	static int Width;
	static int Height;
	
	//Variables for the game state
	private GameStateManager gsm;
		
	public GamePanel(int fps) {
		super(fps);
	}
	
	public GamePanel(int width, int height, int fps ) {
		super(width,height,fps);//call parent constructor 
		Width = width;//set width
		Height = height;//set height
	}
	
	//Initializing method
	public void initial(){
		//Create new GameStateManager
		gsm = new GameStateManager();
	}

	public void paintComponent(Graphics g) {
		//Clear screen
		g.clearRect(0, 0, Width, Height);

		//Call the GameStateManager to render
		gsm.render(g);	
	}

	public void updateFrame() {
		 //Call the GameStateManager for update
		gsm.update();
	}
	
	//If a key is pressed
	public void keyPressed(KeyEvent e) {
		//Call GameStateManager
		gsm.keyPressed(e.getKeyCode());
	}

	//If a key is released
	public void keyReleased(KeyEvent e) {
		//Call GameStateManager
		gsm.keyReleased(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	//Get width method
	public static int Width(){
		return Width;
	}
	
	//Get height method
	public static int Height(){
		return Height;
	}

}
