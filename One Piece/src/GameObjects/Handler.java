package GameObjects;

import java.awt.Graphics;
import java.util.ArrayList;

import AnimationsandImages.Camera;

//This class will "handle" all the GameObjects and update them all

public class Handler {

	//Make an array list of all the GameObjects
	public ArrayList<GameObject> objects ;
	
	//Variable for the current object being updated
	private GameObject currentobject;
	
	//Variable for the camera
	private Camera cam;
	
	//Variable for if the level needs to be updated
	private int update;
	/*
	 * update = 0 - no update
	 * update = 1 - next level
	 * update = 2 - game over screen then main menu
	 * update = 3 - pause menu
	 */
	
	//Constructor
	public Handler(){
		objects = new ArrayList<GameObject>();
		
		//Update is false
		update = 0;
	}
	
	//Initial method
	public void initial()
	{
		//Create a new camera
		cam = new Camera();
		
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//Update the current object
			currentobject.initial(cam);//requires a list of all objects for collisions
		}
	}
	
	//This method will loop through all the game objects and update them all
	public void update()
	{
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);
			
			//Update the current object
			currentobject.update(objects, cam);//requires a list of all objects for collisions
		}
	}//update method
	
	//This method will loop all the game objects and render them all
	public void render(Graphics g)
	{
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//Render the current object
			currentobject.render(g,cam);
		}
	}//render method
	
	//This method will add an object
	public void addObject(GameObject object)
	{
		this.objects.add(object);
	}
	
	//This method will remove an object
	public void removeObject(GameObject object)
	{
		this.objects.remove(object);
	}
	
	//This method will get the keyPressed
	public void keyPressed(int k) 
	{
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//keyPress the current object
			currentobject.keyPressed(k);
		}
	}//keyPressed method
	
	//This method will get the keyPressed
	public void keyReleased(int k) 
	{
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//keyReleasethe current object
			currentobject.keyReleased(k);
		}
	}//keyReleased method

	//set x
	public void setx(int x){
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			if(currentobject.getObjectId() == 1)
			//keyRelease the current object
			currentobject.setX(x);
		}
	}
	
	//Method to get the update value
	public int getUpdate(){
		return update;//return update
	}
	
	//Method to set update
	public void setUpdate(int update){
		this.update = update;
	}
	
	//Method to clear the handler
	public void clearHandler(){
		objects.clear();
	}
	
	//Method to reset handler - reset x and y off all objects to checkpoint x and y
	public void resetHandler(){
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//change the x of the current object
			currentobject.x = currentobject.getCheckx();
			
			//change the y of the current object
			currentobject.y = currentobject.getChecky();
			
			//Reset camy
			cam.setCamy(0);
			
		}
	}//reset handler
	
	//Method to set the check point
	public void setCheckpoint(){
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//change the check x of the current object to current x
			currentobject.setCheckx(currentobject.getX());

			//change the check y of the current object to current y
			currentobject.setChecky(currentobject.getY());
		}
	}
	//This method will get the current health and return it
	public int Playerinfo(int info){//depending on the value of info different stats will be return
		//Loop through each object
		for(int i = 0; i < objects.size(); i++)
		{
			//Set the current object
			currentobject = objects.get(i);

			//If info is 0 then return health
			if(info == 0)
			{
				//If the currenObject is player 
				if(currentobject.getObjectId() == 0)//player id = 0
					return currentobject.getHealth();//get the health and return it
			}
			//If info is 1 then return lives
			else if(info == 1)
			{
				//If the currentObject is player 
				if(currentobject.getObjectId() == 0)//player id = 0
					return currentobject.getLives();//get the health and return it
			}
			//If the info is 2 then return the amount of haki
			else if(info == 2)
			{
				//If the currentObject is player
				if(currentobject.getObjectId() == 0)//player id = 0
					return currentobject.getHaki();
			}
			
		}
		
		//Otherwise return negative 1 - there is no player
		return -1;
	}//get player health
	
}
