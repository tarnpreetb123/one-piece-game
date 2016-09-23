package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import AnimationsandImages.Animation;
import AnimationsandImages.Camera;
import AnimationsandImages.Images;
import Window.JFrameWindow;

//A Marine is a GameObject and the main enemy 
public class Marine extends GameObject {

	//Variables for the size of the enemies - predetermined size
	public static int width = 32;
	public static int height = 64;

	//Variable for the images
	Images img = JFrameWindow.getImages();

	//Handler object to check for all collisions
	private Handler handler;
	private int animediff;//difference between heights of animation and marine box
	
	//Variables for the gravity
	float gravity = 0.2f;
	float terminalvel = 10f;//max falling speed

	//Variable for which way the Marine is facing
	private boolean facingright = true;
	private int damage = 10;
	
	//Variables for the marine animation
	private Animation marinewalk;
	private Animation marineattack;
	private Animation marineflinch;
	
	//Animation speeds
	private int walkspeed = 12;
	private int attackspeed = 10;
	private int flinchspeed = 10;
	
	//Create a new Marine
	public Marine(int x, int y, Handler handler, int ObjectId) {
		super(x, y, width, height, ObjectId);
		this.handler = handler;//set handler
		
		//Set the checkx and checky
		checkx = x;
		checky = y;
		
		setXspeed(2);//Set intial x speed
		//Marine is moving right
		Right = true;//set right true
		facingright = true;
		
		//Initial animex
		animex = (int) x;
		
		//Create animation
		marinewalk = new Animation(img.marinewalk,walkspeed,true);//will loop over
		marineattack = new Animation(img.marineattack,attackspeed,false);//wont loop over
		marineflinch = new Animation(img.marineflinch,flinchspeed,false);//wont loop over		
	}


	//Initial stats of the marine
	public void initial(Camera cam) {
	}

	//Update marine
	public void update(ArrayList<GameObject> objects, Camera cam) {
		//Update x based on camera
		x += cam.getCamxspeed();
		y -= cam.getCamyspeed()/2;//update y based on camera
		
		//Update x for movement
		x += xspeed;
		
		//Animex is the same as marine x
		animex = (int) x;
		
		//Calculate difference between animation y and player y
		animediff =((int)y  + height) - animeheight;//take marine y + height and subtract the animation height
				

		//If the marine isn't attacking
		if(!attacking)
		{
			//If marine is moving right
			if(Right || Left)
			{
				marinewalk.update();//update
				animewidth = marinewalk.getFrameWidth();
				animeheight = marinewalk.getFrameHeight();
				width = marinewalk.getFrameWidth();
				height = marinewalk.getFrameHeight();
			}
		}//if not attacking	
		
		//Calculate difference between animation y and player y
		animediff =((int)y  + height) - animeheight;//take player y + height and subtract the animation height

		//If the player is either falling or jumping then they must be affected by gravity
		if(Falling || Jumping)
		{
			yspeed += gravity;//accelerate the y speed by gravity
			y += yspeed;//change the y position by the y speed
			//If the player reachs max falling speed
			if(yspeed >= terminalvel)
				yspeed = terminalvel;

			//The animation y is the same as the player y
			animey = animediff;
		}

		//Check collision
		Collision(objects);
		
		//If the marine is attacking
		if(attacking)
		{
			//Update attack animation
			marineattack.update();
			animewidth = marineattack.getFrameWidth();
			animeheight = marineattack.getFrameHeight();
			
			//If the animation has played reset
			if(marineattack.played())
			{
				//If marine  isn't running
				if(!Right || !Left)
				 {
				}

				attacking = false;//facing left is 
			}//if animation has played
		}//if attacking
		
		//If the marine has been hit
		if(hit)
		{
			marineflinch.update();
			animewidth = marineflinch.getFrameWidth();
			animeheight = marineflinch.getFrameHeight();
			
			//If the flinch animation has played
			if(marineflinch.played())
			{
				hit = false;//the marine has recovered
				marineflinch.reset();//reset the animation for next time
			}
		}//if hit
			
		
		//If the marine is dead
		if(!alive())
		{
			handler.removeObject(this);//delete this marine
		}
	}

	//This method will check if this GameObject(Marine) is colliding with/intersecting with any of the other game objects
	private void Collision(ArrayList<GameObject> objects)
	{
		//Loop through each object
		for(int i = 0; i < handler.objects.size(); i ++)
		{
			//Take the current object and temporarily store it
			GameObject currentobject = handler.objects.get(i);

			//Check if the currentObject is a block
			if(currentobject.getObjectId() == 1)//Blocks have the id 1
			{
				//Check if the marine bottom is touching the block
				if(getBoundsbot().intersects(currentobject.getBounds()))//.intersects is a built in method that checks if two rectangles are touching
				{
					//Stop the marine from falling and jumping
					setFalling(false);
					setJumping(false);

					//Reset the yspeed to zero
					setYspeed(0);

					//Make sure the Marine is not sinking into the block
					y = currentobject.getY() - height;//Marine is located at the top of the block minus their own height

				}//If the bottom of Marine is intersecting with block

				//If the bottom is not touching a block then the Marine must be falling
				else
				{
					setFalling(true);//set the falling to be true
				}//else not touching bottom

				//Check if the Marine top is touching a block
				if(getBoundsTop().intersects(currentobject.getBounds()))
				{
					//Stop the Marine from moving up
					setYspeed(0);

					//Set the Marine position underneath the block - (block y + block height)
					y = currentobject.getY() + currentobject.getHeight();
				}//if top touching block

				//Check right collision
				RightCollision(currentobject);

				//Check left collision
				LeftCollision(currentobject);
			}//if the object is a block
			
			//Check if the currentObject is a player
			if(currentobject.getObjectId() == 0)//Player has the id 0
			{
				//Call attack method if the player isn't already attacking and hasn't been hit
				if(!attacking && !hit)
				{
					//If the marine is touching player - attack
					if(getBounds().intersects(currentobject.getBounds()))
						attackchoice(currentobject);
				}//if not attacking
			}//if player
		}//For loop through all objects

	}//Collision method

	//This method will check if this GameObject left is colliding
	private void LeftCollision(GameObject object)
	{
		//Check if the Marine left is touching a block
		if(getBoundsLeft().intersects(object.getBounds()))
		{
			//Stop the Marine from moving left and change direction
			setXspeed(2);
			facingright = true;
			Left = false;
			Right = true;//marine is now moving right
			
			//Set the Marine location right next to the to the right of the block - (block x + Marine width)
			x = object.getX() + object.getWidth();
		}//if left touching block
	}
	
	//This method will check if this GameObject Right is colliding
	private void RightCollision(GameObject object)
	{
		//Check if the Marine right is touching a block
		if(getBoundsRight().intersects(object.getBounds()))
		{
			//Stop the Marine from moving right and change direction
			setXspeed(-2);
			facingright = false;
			Right = false;
			Left = true;//marine is not moving left

			//Set the Marine location right next to the left of the block - (block x - Marine width)
			x = object.getX() - width;
		}//if right touching block
	}

	//Render the marine
	public void render(Graphics g, Camera cam) {
		//Check if the marine has been hit
		if(!hit)//not hit
		{
			if(!attacking)
			{
				marinewalk.draw(g,animex,animey, animewidth, animeheight,!facingright);//not facing right because image is naturally facing left
			}

			else if(attacking)
				marineattack.draw(g,animex,animey, animewidth, animeheight,!facingright);
		}
		else if(hit)//marine has been hit
		{
			marineflinch.draw(g,animex,animey, animewidth, animeheight,!facingright);
		}
	}

	//If a key is pressed
	public void keyPressed(int k) {

	}

	//If a key is released
	public void keyReleased(int k) {

	}

	//This method will create a Rectangle object within the Marine at the base
	public Rectangle getBoundsbot(){
		return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int) ((int) y+(height/2)), (int) width/2,(int) height/2);
	}

	//This method will create a Rectangle object within the Marine at the top
	public Rectangle getBoundsTop(){
		return new Rectangle((int)((int) x+(width/2)-((width/2)/2)), (int) y, (int) width/2,(int) height/2);
	}

	//This method will create a Rectangle object within the Marine on the right
	public Rectangle getBoundsRight(){
		return new Rectangle((int)((int)x+width - 5), (int) y+5, (int) 5,(int) height-10);
	}

	//This method will create a Rectangle object within the Marine on the left
	public Rectangle getBoundsLeft(){
		return new Rectangle((int) x, (int) y+5, (int) 5,(int) height-10);
	}
	
	//This method will create a Rectangle where the marine collision box is
	public Rectangle getBounds(){
		return new Rectangle((int) x, (int) y, width, height);
	}
		
	//This method will create a Rectangle where the animation is
	public Rectangle getBoundsanime() {
		return new Rectangle(animex, animey, animewidth, animeheight);
	}
		
	//This method will decide when the marine will attack
	private void attackchoice(GameObject object){
			attacking = true;//set attack true
			marineattack.reset();
			
			//Damage the gameobject
			object.damageHealth(damage);
	}

	//This method will check if the marine is still alive
	private boolean alive(){
		//If health is less than or equal to zero
		if(health <= 0)
			return false;//marine is dead
		
		//Return true - marine is alive
		return true;
	}
	
}
