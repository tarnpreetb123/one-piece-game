package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import AnimationsandImages.Animation;
import AnimationsandImages.Camera;
import AnimationsandImages.Images;
import GameStates.GameStateManager;
import Window.GamePanel;
import Window.JFrameWindow;

public class Player extends GameObject {

	//Variables for the size of the player
	static int width = 75;//these will change with the size of the current frame animation
	static int height = 75;

	//Difference between collision height and animation height
	private int animediff;
		
	//Variables for the gravity
	float gravity = 0.2f;
	float terminalvel = 10f;//max falling speed

	//Handler object to check for all collisions
	private Handler handler;

	//Variable for the images
	Images img = JFrameWindow.getImages();

	//Variable for which way the player is facing
	private boolean facingright = true;
	private boolean idle = true;

	//Attacks and damage
	private boolean haki = false;
	private boolean pistol = false;
	private boolean gatling = false;
	private int hakidamage = 3;
	private int pistoldamage = 1;
	private int gatlingdamage = 1;
	private int hakiuse = 10;//amount of haki player uses per attack
	
	//Variables for the various animations
	private Animation playeridle;
	private Animation playerrunning;
	private Animation playerpistol;
	private Animation playergatling;
	private Animation playerhakigatling;
	
	//Variable for the animation speeds;
	private int idlespeed = 15;
	private int runspeed = 8;
	private int pistolspeed = 2;
	private int gatlingspeed = 7;
	private int hakigatlingspeed = 5;

	public Player(int x, int y, Handler handler,int ObjectId) {
		super(x, y, width, height, ObjectId);//call parent constructor

		//Set initial animation and checkpoint parameters
		animex = x;
		animey = y;
		checkx = x;
		checky = y;
		
		//Set number  of lives
		lives = 3;
		
		//Set handler
		this.handler = handler;

		//Create Animation
		playeridle = new Animation(img.player,idlespeed,true);
		playerrunning = new Animation(img.playerwalk,runspeed,true);
		playerpistol = new Animation(img.playerpistol,pistolspeed,false);
		playergatling = new Animation(img.playergatling,gatlingspeed,false);
		playerhakigatling = new Animation(img.playerhakigatling,hakigatlingspeed,false);
	}

	//Initializing method
	public void initial(Camera cam) {
	}

	//This method will update the player
	public void update(ArrayList<GameObject> objects, Camera cam) 
	{
		//Calculate difference between animation y and player y
		animediff =((int)y  + height) - animeheight;//take player y + height and subtract the animation height
		
		//If the player isn't attacking
		if(!attacking)
		{
			//If the player is moving right
			if(Right)
			{
				playerrunning.update();//Run animation
				//Set the animation width and height to the width and height of the current frame
				animewidth = playerrunning.getFrameWidth();
				animeheight = playerrunning.getFrameHeight();
				width = playerrunning.getFrameWidth();
				height = playerrunning.getFrameHeight();
				idle = false;//idle is false when walking
			}

			//If the player is moving left
			else if(Left)
			{
				playerrunning.update();//Run animation
				//Set the width and height to the width and height of the current frame
				animewidth = playerrunning.getFrameWidth();
				animeheight = playerrunning.getFrameHeight();
				width = playerrunning.getFrameWidth();
				height = playerrunning.getFrameHeight();
				idle = false;//idle is false when walking
			}
		}//if not attacking
		
		//If the player is either falling or jumping then they must be affected by gravity
		if(Falling || Jumping)
		{
			yspeed += gravity;//accelerate the y speed by gravity
			y += yspeed;//change the y position by the y speed
			//If the player reaches max falling speed
			if(yspeed >= terminalvel)
				yspeed = terminalvel;
			
			//The animation y is the same as the player y
			animey = animediff;
		}

		//Call attack method
		attack();
		
		//if player is idle
		if(idle)
		{
			playeridle.update();	//Run animation

			//Set the width and height to the width and height of the current frame
			animewidth = playeridle.getFrameWidth();
			animeheight = playeridle.getFrameHeight();
			width = playeridle.getFrameWidth();
			height = playeridle.getFrameHeight();
		}
		
		//Check if player is being pushed to left of center
		if(x < GamePanel.Width()/2 - width/2)
		{
			if(!attacking) cam.update((int)x,(int)y,1,(int)yspeed);//move camera back
			x = GamePanel.Width()/2 - width/2;//fix player position
		}
		//Check if player is being pushed to right of center
		else if(x > GamePanel.Width()/2 - width/2)
		{
			if(!attacking) cam.update((int)x,(int)y,-2,(int)yspeed);//move camera back
			x = GamePanel.Width()/2 - width/2;//fix player position
		}

		else//else normal update
		{
			if(!attacking) cam.update((int)x,(int)y,(int)xspeed,(int)yspeed);//if not attacking
			else if(attacking)cam.update((int)x,(int)y,0,(int)yspeed);//if attacking
		}

		//If player is attacking
		if(attacking)
		{
			//If player is facing right
			if(facingright)
				animex = GamePanel.Width()/2 - playeridle.getFrameWidth()/2;
			
			//If player is facing left
			if(!facingright)
			{
				if(pistol)//if the attack is pistol
					animex = ((GamePanel.Width()/2) - playerpistol.getFrameWidth()) + playeridle.getFrameWidth()/2;//half of the gamepanel - player current attack width  + half idle width 
				
				else if (gatling && haki)
					animex = ((GamePanel.Width()/2) - playerhakigatling.getFrameWidth()) + playeridle.getFrameWidth()/2;
				else if (gatling)
					animex = ((GamePanel.Width()/2) - playergatling.getFrameWidth()) + playeridle.getFrameWidth()/2;
			}
		}
		//Check for collisions
		Collision(objects);
		
		//Check if the player is still alive
		if(health <= 0)
			removelive();
		
		//Check if the player is dead
		if(lives <= 0)
			handler.setUpdate(2);//2 is gameover screen
			
	}//Update method

	//This method will check if this GameObject(Player) is colliding with/intersecting with any of the other game objects
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
				//Check if the player bottom is touching the block
				if(getBoundsbot().intersects(currentobject.getBounds()))//.intersects is a built in method that checks if two rectangles are touching
				{
					//Stop the player from falling and jumping
					setFalling(false);
					setJumping(false);

					//Reset the yspeed to zero
					setYspeed(0);

					//Make sure the player is not sinking into the block
					y = currentobject.getY() - height;//player is located at the top of the block minus their own height

				}//If the bottom of player is intersecting with block

				//If the bottom is not touching a block then the player must be falling
				else
				{
					setFalling(true);//set the falling to be true
				}//else not touching bottom

				//Check if the player top is touching a block
				if(getBoundsTop().intersects(currentobject.getBounds()))
				{
					//Stop the player from moving up
					setYspeed(0);

					//Set the player position underneath the block - (block y + block height)
					y = currentobject.getY() + currentobject.getHeight();

				}//if top touching block

				//Check if the player right is touching a block
				if(getBoundsRight().intersects(currentobject.getBounds()))
				{
					//Stop the player from moving right
					//setRight(false);

					//Set the player location right next to the left of the block - (block x - player width)
					x = currentobject.getX() - width;
				}//if right touching block

				//Check if the player left is touching a block
				if(getBoundsLeft().intersects(currentobject.getBounds()))
				{
					//Stop the player from moving left
					//setLeft(false);

					//Set the player location right next to the to the right of the block - (block x + player width)
					x = currentobject.getX() + currentobject.getWidth();
				}//if left touching block
			}//if the object is a block

			//Check if the current object is an enemy
			else if(currentobject.getObjectId() == 2)//Enemies have the id 2
			{
				//If the player is attacking
				if(attacking)
				{
					//If the player is touching the enemy
					if(getBoundsanime().intersects(currentobject.getBounds()))
					{
						//If pistol
						if(pistol)
							currentobject.damageHealth(pistoldamage);
						//If player is using gatling attack
						else if(gatling)
						{
							//If not using haki
							if(!haki)
								currentobject.damageHealth(gatlingdamage);//normal  gatling damage
							else if(haki)//else if haki
								currentobject.damageHealth((gatlingdamage + hakidamage));//add haki damage
						}

						//The enemy has been hit
						currentobject.setHit(true);
					}//if touching enemy
				}//if attacking
			}//If enemy 

			//Check if the current object is a flag
			else if(currentobject.getObjectId() == 3)//Flags have the id 3
			{
				//Check if the player is touching the flag
				if(getBounds().intersects(currentobject.getBounds()))
				{
					//Move to the next level
					handler.setUpdate(1);
				}//if touching
			}//if flag

			//Check if the current object is a waterblock
			else if(currentobject.getObjectId() == 4)//Water blocks have the id 4
			{
				//Check if the player is touching the waterblock
				if(getBounds().intersects(currentobject.getBounds()))
				{
					removelive();//call remove live
				}//if touching
			}//if water
			
			//Check if the current object is a food item
			else if(currentobject.getObjectId() == 5)//Food items have the id 5
			{
				//Check if the player is touching the food
				if(getBounds().intersects(currentobject.getBounds()))
				{
					//Give the player more health
					health += 100;//100 health
					hakiamount += 50;//50 haki
					handler.removeObject(currentobject);//remove the food item
				}//if touching
			}//if water
			
			//Check if the current object is a checkpoint
			else if(currentobject.getObjectId() == 6)//Checkpoints have the id 6
			{
				//Check if the player is touching the checkpoint
				if(getBounds().intersects(currentobject.getBounds()))
				{
					handler.setCheckpoint();//set this checkpoint
					
					//The checkpoint is being touched
					currentobject.setHit(true);
				}//if touching
				else//not touching
					currentobject.setHit(false);//not being touched
			}//if checkpoint

		}//For loop through all objects

	}//Collision method

	//This method will display the player
	public void render(Graphics g,Camera cam) 
	{
		//Draw the player animation
		if(!attacking)//if not attacking
		{
			if(idle)//if idle
				playeridle.draw(g,(int) x,(int) y, width, height, facingright);

			else if(Right || Left)// if moving left or right
				playerrunning.draw(g,(int) x,(int) y, width, height, facingright);

			else if(Jumping || Falling)//if falling or jumping
				playeridle.draw(g,(int) x,(int) y, width, height, facingright);
		}//if not attacking
		
		if(attacking)//if attacking
		{
			if(pistol)//pistol attack
				playerpistol.draw(g,animex,animey, animewidth, animeheight, facingright);
			
			else if(gatling){//if gatling attack
				//If using haki
				if(haki){playerhakigatling.draw(g,animex,animey, animewidth, animeheight, facingright);}
				//If not using haki
				else{playergatling.draw(g,animex,animey, animewidth, animeheight, facingright);}
			}//if gatling
		}//if attacking
	}//Render method
	
	public void keyPressed(int k) //if a key is pressed
	{
		//If the right arrow key is pressed
		if(k == KeyEvent.VK_RIGHT)
		{
			setXspeed(-5);//set the xspeed 

			setRight(true);//set Right true
			idle = false;
			facingright = true;
			//set attacking to false
			attacking = false;//attacking is false
		}//if right
		
		//If the left arrow key is pressed
		if(k == KeyEvent.VK_LEFT)
		{
			setXspeed(5);//set the xspeed 
			setLeft(true);//set Left true
			idle = false;
			facingright = false;
			attacking = false;//attacking is false
		}//if left

		//If the space bar is pressed and the player is not already jumping  or attacking
		if(k == KeyEvent.VK_UP && !Jumping)
		{
			setYspeed(-7);//set the yspeed 
			setJumping(true);//set Jumping true
			attacking = false;//attacking is false
		}//if up
		
		//If q is pressed use pistol attack
		if(k == KeyEvent.VK_Q)
		{
			attacking = true;//player is attacking
			pistol = true; //player is using pistol
			playerpistol.reset();//reset animation
			
			//idle is false
			idle = false;
		}//if q
		
		//If w is pressed use gatling attack
		else if(k == KeyEvent.VK_W)
		{
			attacking = true;//player is attacking
			gatling = true; //player is using gatling
			playergatling.reset();//reset animation
			
			//idle is false
			idle = false;
		}//if w
		
		//If e is pressed use haki gatling attack and the player has haki
		else if(k == KeyEvent.VK_E && hakiamount >= hakiuse)
		{
			attacking = true;//player is attacking
			gatling = true; //player is using gatling
			haki = true;//player is using haki
			playerhakigatling.reset();//reset animation
			hakiamount -= hakiuse;//reduce the players haki
			//idle is false
			idle = false;
		}//if w and space

		//If the escape key is pressed exit the game
		else if(k == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		
		//If p is pressed switch to pause menu state
		if(k == KeyEvent.VK_P)
		{
			GameStateManager.setState(GameStateManager.PauseMenuState);//set the state to pause menu state
		}
		
	}//key pressed method

	public void keyReleased(int k) 
	{
		//If the right arrow key is released
		if(k == KeyEvent.VK_RIGHT)
		{
			setXspeed(0);//set the xspeed zero
			setRight(false);//set Right false
			
			//If the player isn't attacking
			if(!attacking)
				idle = true;
		}

		//If the left arrow key is released
		if(k == KeyEvent.VK_LEFT)
		{
			setXspeed(0);//set the xspeed zero
			setLeft(false);//set Left false
			
			//If the player isn't attacking
			if(!attacking)
				idle = true;
		}
	}//key released method

	//This method will create a Rectangle object within the player at the base
	public Rectangle getBoundsbot(){
		return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int) ((int) y+(height/2)), (int) width/2,(int) height/2);
	}

	//This method will create a Rectangle object within the player at the top
	public Rectangle getBoundsTop(){
		return new Rectangle((int)((int) x+(width/2)-((width/2)/2)), (int) y, (int) width/2,(int) height/2);
	}

	//This method will create a Rectangle object within the player on the right
	public Rectangle getBoundsRight(){
		return new Rectangle((int)((int)x+width - 5), (int) y+5, (int) 5,(int) height-10);
	}

	//This method will create a Rectangle object within the player on the left
	public Rectangle getBoundsLeft(){
		return new Rectangle((int) x, (int) y+5, (int) 5,(int) height-10);
	}
	
	//This method will create a Rectangle where the player collision box is
	public Rectangle getBounds(){
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	//This method will create a Rectangle where the animation is
	public Rectangle getBoundsanime() {
		return new Rectangle(animex, animey, animewidth, animeheight);
	}
	
	//This method will check if player is using an attack and correctly output the attack
	private void attack(){
		//If player is using pistol attack
		if(pistol)
		{
			playerpistol.update();//Run animation
			//Set the width and height to the width and height of the current frame
			animewidth = playerpistol.getFrameWidth();//set animation width
			animeheight = playerpistol.getFrameHeight();//set animation height

			if(playerpistol.played())//if the animation already played
			{
				//If player isn't running
				if(!Right || !Left)
					idle = true;//idle is true

				attacking = false;//attacking is false 
				pistol = false;
			}//if animation has played
		}//if pistol
		
		//If player is using gatling attack
		else if(gatling)
		{
			//If player is using haki
			if(haki)
			{
				playerhakigatling.update();//Update Animation
				//Set the width and height to the width and height of the current frame
				animewidth = playerhakigatling.getFrameWidth();//set animation width
				animeheight = playerhakigatling.getFrameHeight();//set animation height
				
				if(playerhakigatling.played())//if haki gatling animation played
				{
					//If player isn't running
					if(!Right || !Left)
						idle = true;//idle is true

					attacking = false;//attacking is false
					haki = false;
					gatling = false;
				}//if animation has played
			}//if haki
			else
			{
				playergatling.update();//Run animation

				//Set the width and height to the width and height of the current frame
				animewidth = playergatling.getFrameWidth();//set animation width
				animeheight = playergatling.getFrameHeight();//set animation height
				
				if(playergatling.played())//if gatling animation played
				{
					//If player isn't running
					if(!Right || !Left)
						idle = true;//idle is true

					attacking = false;//facing left is 
					gatling = false;
				}//if animation has played
			}//else
		}//if gatling
	}

	//This method will be called when player health reachs 0 or player falls in water
	private void removelive(){
		//Remove a live
		lives--;
		
		//Reset the player health
		health = 100;
		
		//Call handler reset
		handler.resetHandler();
	}
	
	//Get the players health
	public int getHealth(){
		return health;
	}
	
	//Get the players lives
	public int getLives(){
		return lives;
	}
}
