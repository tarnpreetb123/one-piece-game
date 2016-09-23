package GameObjects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

import AnimationsandImages.Camera;

//This class will be the parent class for all entities and blocks
//Every object in the game will extend this class
//Abstract means you can't create an instances of this class but only of classes that extend this class
public abstract class GameObject {
	
	//Variables for the location of the object
	protected float x;
	protected float y;
	
	//Variables for the speed of the object
	protected float xspeed;
	protected float yspeed;
	
	//Variables for the size of the object
	protected int width;
	protected int height;
	
	//Variables for if the object is moving
	protected boolean Left = false;
	protected boolean Right = false;
	protected boolean Jumping = false;
	protected boolean Falling = true;
	
	//Variables for the gravity
	float gravity;
	float terminalvel;//max falling speed
	
	//Variable for the type of object it is
	/*
	 * Player id = 0
	 * Block id = 1
	 * Enemy(Marine) id = 2
	 * Flag(end of game) id = 3
	 * Water id = 4
	 * Food id = 5
	 * Checkpoint id = 6
	 */
	protected int ObjectId;
	
	//Variable for the health of the object and number of lives
	protected int health = 100;
	protected int lives;
	protected int hakiamount = 100;
	
	//Variable for if the object is attacking
	protected boolean attacking = false;
	protected boolean hit = false;//if the object has been hit this becomes true
	
	//Variable for the object animation and hitbox x,y,width,height.
	protected int animewidth;
	protected int animeheight;
	protected int animex;
	protected int animey;
	
	//Variable for the location of the GameObject at any checkpoint
	protected float checkx;
	protected float checky;
	
	//Create a GameObject
	public GameObject(int x, int y, int width, int height, int ObjectId)
	{
		this.x = x;//set x
		this.y = y;//set y
		this.width = width;//set width
		this.height = height;//set height
		this.ObjectId = ObjectId;//set object id
	}
	
	//All classes that extend from GameObject need to implement these methods
	public abstract void initial(Camera cam);//This will be initializing method
	public abstract void update(ArrayList<GameObject> objects,Camera cam);//This method will do the updating - it requires an array list of all the different game objects for collisions
	public abstract void render(Graphics g,Camera cam);//This method will render
	public abstract void keyPressed(int k);//This method will check if a key is pressed
	public abstract void keyReleased(int k);//This method will check if a key is released
	public abstract Rectangle getBounds();//This method will get the bounds of the rectangle object within each GameObject
	public abstract Rectangle getBoundsanime();//This method will get the bounds of the rectangle object within each GameObject

	//Below are the getters and setters for all variables
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getXspeed() {
		return xspeed;
	}

	public void setXspeed(float xspeed) {
		this.xspeed = xspeed;
	}

	public float getYspeed() {
		return yspeed;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setYspeed(float yspeed) {
		this.yspeed = yspeed;
	}

	public boolean isLeft() {
		return Left;
	}

	public void setLeft(boolean left) {
		Left = left;
	}

	public boolean isRight() {
		return Right;
	}

	public void setRight(boolean right) {
		Right = right;
	}

	public boolean isJumping() {
		return Jumping;
	}

	public void setJumping(boolean jumping) {
		Jumping = jumping;
	}

	public boolean isFalling() {
		return Falling;
	}

	public void setFalling(boolean falling) {
		Falling = falling;
	}
	
	public int getObjectId(){
		return ObjectId;
	}

	public int getLives(){
		return lives;
	}
	public int getHealth() {
		return health;
	}

	public void damageHealth(int damage) {
		this.health -= damage;
	}

	public int getHaki() {
		return hakiamount;
	}

	public void setHaki(int hakiamount) {
		this.hakiamount = hakiamount;
	}

	public boolean isAttacking() {
		return attacking;
	}
	
	public void setHit(boolean hit){
	 this.hit = hit;
	}

	public int getAnimewidth() {
		return animewidth;
	}

	public void setAnimewidth(int animewidth) {
		this.animewidth = animewidth;
	}

	public int getAnimeheight() {
		return animeheight;
	}

	public void setAnimeheight(int animeheight) {
		this.animeheight = animeheight;
	}

	public int getAnimex() {
		return animex;
	}

	public void setAnimex(int animex) {
		this.animex = animex;
	}

	public int getAnimey() {
		return animey;
	}

	public void setAnimey(int animey) {
		this.animey = animey;
	}

	public float getCheckx() {
		return checkx;
	}

	public void setCheckx(float checkx) {
		this.checkx = checkx;
	}

	public float getChecky() {
		return checky;
	}

	public void setChecky(float checky) {
		this.checky = checky;
	}
	
	
	
}
