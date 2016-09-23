package GameStates;

//import java.awt.Graphics2D;
import java.awt.Graphics;

//Game state object
//Abstract means that gamestate can't be initialized but subclasses of game state can be initialized
//All game states will extend from this class
public abstract class GameState {
	
	//Variable for the GameStateManager
	protected GameStateManager gsm;
	
	//Constructor
	protected GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
		initial();
	}
	
	public abstract void initial();//This will be initializing method
	public abstract void update();//This method will do the updating
	public abstract void render(Graphics g);//This method will render
	public abstract void keyPressed(int k);//This method will check if a key is pressed
	public abstract void keyReleased(int k);//This method will check if a key is released
}
