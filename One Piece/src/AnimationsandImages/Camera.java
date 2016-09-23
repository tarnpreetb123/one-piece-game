package AnimationsandImages;

import Window.GamePanel;


//This class will act like a camera to draw only certain parts
//This class will follow the player
public class Camera {

	//Variables
	public int camx;
	public int camy;
	
	public int camwidth;
	public int camheight;
	
	public int camxspeed;
	public int camyspeed;
	
	//Constructor to create a new camera
	public Camera()
	{
		camx = 0;
		camy = 0;
		camwidth = 800;
		camheight = 800;
	}
	
	
	//Variable to update camera
	public void update(int playerx,int playery, int playerxspeed, int playeryspeed){
		camx = playerx;
		camy = (camy += camyspeed/2);//update camy location
		camxspeed = playerxspeed;
		
		//If player y is off the screen
		if(playery < 0)//at the top
			camyspeed = -10;
		if(playery + 65 > GamePanel.Height())// at the bottom
			camyspeed = 20;
		else
			camyspeed = (int) ((playery - camy) * 0.025);//update camera y speed based on player y and camy -tween algorithm
	}

	public int getCamxspeed() {
		return camxspeed;
	}


	public void setCamxspeed(int camxspeed) {
		this.camxspeed = camxspeed;
	}


	public int getCamyspeed() {
		return camyspeed;
	}


	public void setCamyspeed(int camyspeed) {
		this.camyspeed = camyspeed;
	}


	public int getCamx() {
		return camx;
	}


	public void setCamx(int camx) {
		this.camx = camx;
	}


	public int getCamy() {
		return camy;
	}


	public void setCamy(int camy) {
		this.camy = camy;
	}

	
	
}
