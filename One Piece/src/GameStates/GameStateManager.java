package GameStates;
import java.awt.Graphics;
import java.util.ArrayList;
//import java.awt.event.KeyListener;

//This class will "hold" all the different game states and get input from the current state
//It will also make it easier to switch between different states
public class GameStateManager {
	
	//Array of all the different states
	public static ArrayList<GameState> states;
	
	//Variable for index of current state
	public static int currentState;
	
	//Variable for the index of the previous state
	public static int previousState;
	
	//List of all the states - Index
	public static final int MenuState = 0;
	public static final int LevelSelectState = 1;
	public static final int HelpMenuState = 2;
	public static final int PauseMenuState = 3;
	
	//Variables for the levels
	public static final int Level1State = 4;
	public static final int Level2State = 5;
	public static final int Level3State = 6;
	
	//Variable for the number of levels currently available
	public static int levelsavailable = 0;//0 means 1
	
	public GameStateManager(){
		states = new ArrayList<GameState>();
		
		//The first state will be the menu state
		//Add the MenuState in
		states.add(new MenuState(this));
		
		//Set the currentState to the Menu State
		setState(MenuState);
	}
	
	//This method will return the current state
	public int getCurrentState(){
		return currentState;
	}
	
	//This method will set a state
	public static void setState(int state){
		previousState = currentState;//set the previous state
		currentState = state;//change the current state
		states.get(state).initial();//call the initializing method of the current state
	}
	
	//This method will set the state but won't restart that state
	public static void setStateResume(int state){
		previousState = currentState;//set the previous state
		currentState = state;//change the current state
	}
	
	//This method will add a state
	public void addState(GameState state)
	{
		states.add(state);//add state
	}
	//This method will call the update method in the current game state
	public void update(){
		states.get(currentState).update();
	}
	
	//This method will call the render method in the current game state
	public void render(Graphics g){
		states.get(currentState).render(g);
	}
	
	//This method will call the keyPressed method in the current game state
	public void keyPressed(int k){
		states.get(currentState).keyPressed(k);
	}
	
	//This method will call the keyReleased method in the current game state
	public void keyReleased(int k){
		states.get(currentState).keyReleased(k);
	}
	
	//This method will update the number of levels available
	public static void updatelevels(){
		levelsavailable ++;
	}
	
	//This method will return the number of total states
	public static int totalstates(){
		return states.size();//return length of arraylist
		
	}
}
