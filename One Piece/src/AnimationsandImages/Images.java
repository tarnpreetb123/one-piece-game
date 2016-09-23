package AnimationsandImages;

import java.awt.image.BufferedImage;

//This class will load all the images in the game
public class Images {

	//Variable for the SpriteSheet
	SpriteSheet Player;//walking and idle
	SpriteSheet Player2;//attacks
	SpriteSheet Marine;//marine
	SpriteSheet Water;//Water blocks
	
	//Variables for the spritesheets in the game
	private BufferedImage[] player_sheet = new BufferedImage[2];//player sprite sheet
	private BufferedImage water_sheet;
	
	//Blocks and water
	public BufferedImage[] blocks = new BufferedImage[3];//This will be the dirt and grass blocks, water block, meat block;
	public BufferedImage[] water = new BufferedImage[32];//This will be the water blocks
	
	//Variables for the player
	public BufferedImage[] player = new BufferedImage[3];//idle animation frames
	public BufferedImage[] playerwalk = new BufferedImage[8];//walking/running animation frames
	public BufferedImage[] playerpistol = new BufferedImage[14];//pistol animation frames
	public BufferedImage[] playergatling = new BufferedImage[14];//gatling animation frames
	public BufferedImage[] playerhakigatling = new BufferedImage[14];//haki gatling animation frames
	
	//Variables for marine images
	public BufferedImage[] marine_sheet = new BufferedImage[2];//Marine spritesheet
	public BufferedImage[] marineidle = new BufferedImage[2];//Marine idle
	public BufferedImage[] marinewalk = new BufferedImage[4];//Marine run
	public BufferedImage[] marineattack = new BufferedImage[6];//Marine attack
	public BufferedImage[] marinedie = new BufferedImage[6];//Marine dies
	public BufferedImage[] marineflinch = new BufferedImage[3];//Marine flinchs from getting hit
	
	//Variable for the Flag and Background
	public BufferedImage flag;
	public BufferedImage background;
	public BufferedImage strawhat;
	public BufferedImage gameover;
	public BufferedImage food;
	public BufferedImage[] checkpoint = new BufferedImage[2];
	
	//Variables for images for menus
	public BufferedImage lock;
	public BufferedImage LevelSelectMenubg;
	public BufferedImage arrowkeys;
	public BufferedImage QWEkeys;
	public BufferedImage backbutton;
	public BufferedImage HelpMenubg;
	public BufferedImage PauseMenubg;
	public BufferedImage MainMenubg;
	
	//Variables for the level images
	public BufferedImage[] gamestates = new BufferedImage[4];
	
	//This method will return the image name given 
	public Images(){
		//Call the BufferedImageLoader to get the image
		BufferedImageLoader loader = new BufferedImageLoader();
		try{//try to load
			player_sheet[0] = loader.loadImage("/luffyssg1.png");//luffy basic sprite sheet
			player_sheet[1] = loader.loadImage("/luffygg12.png");//luffy attacks sprite sheet
			marine_sheet[0] = loader.loadImage("/MarineSword.png");//marine sprite sheet
			water_sheet = loader.loadImage("/water.png");
			
			//Load images that don't have sprite sheets
			
			//Blocks
			blocks[0] = loader.loadImage("/dirtblock.png");//load dirt block
			blocks[1] = loader.loadImage("/grassblock.png");//load grass block
			
			//Flag and background
			flag = loader.loadImage("/flag.png");//load flag
			background = loader.loadImage("/background.png");//load background
			strawhat = loader.loadImage("/strawhat.png");//load straw hat
			gameover = loader.loadImage("/gameover.png");//load gameover screen
			food = loader.loadImage("/food.png");//load food image
			checkpoint[0] = loader.loadImage("/checkpoint.png");//load checkpoint
			checkpoint[1] = loader.loadImage("/blankcheckpoint.png");//load checkpoint
			
			//Menus
			lock = loader.loadImage("/lock.png");//load lock image
			LevelSelectMenubg = loader.loadImage("/levelselectmenu.png");//load levelselect menu background
			arrowkeys = loader.loadImage("/arrowkeys.png");//load arrow key image
			QWEkeys = loader.loadImage("/QWEkeys.png");//load qwe key image		
			backbutton = loader.loadImage("/backbutton.png");//load back button
			HelpMenubg = loader.loadImage("/helpmenu.png");//load help menu background
			PauseMenubg = loader.loadImage("/pausemenu.png");//load pause menu background
			MainMenubg = loader.loadImage("/mainmenu.png");//load main menu background
			
			//Levels
			//gamestates[0] = 
			gamestates[1] = loader.loadImage("/level.png"); //Level 1
			gamestates[2] = loader.loadImage("/level2.png"); //Level 2
			gamestates[3] = loader.loadImage("/level3.png"); //Level 2
			
		}catch(Exception e){//catch any errors
			e.printStackTrace();
		}

		//Create a new SpriteSheet
		Player = new SpriteSheet(player_sheet[0]);
		Player2 = new SpriteSheet(player_sheet[1]);
		Marine = new SpriteSheet(marine_sheet[0]);
		Water = new SpriteSheet(water_sheet);
		
		//Get images
		getImages();
	}
	
	//This method will load the images
	public void getImages(){
		//Water
		water[0] = Water.getImage3(1, 1, 32, 32);
		water[1] = Water.getImage3(2, 1, 32, 32);
		water[2] = Water.getImage3(3, 1, 32, 32);
		water[3] = Water.getImage3(4, 1, 32, 32);
		water[4] = Water.getImage3(5, 1, 32, 32);
		water[5] = Water.getImage3(6, 1, 32, 32);
		water[6] = Water.getImage3(7, 1, 32, 32);
		water[7] = Water.getImage3(8, 1, 32, 32);
		water[8] = Water.getImage3(9, 1, 32, 32);
		water[9] = Water.getImage3(10, 1, 32, 32);
		water[10] = Water.getImage3(11, 1, 32, 32);
		water[11] = Water.getImage3(12, 1, 32, 32);
		water[12] = Water.getImage3(13, 1, 32, 32);
		water[13] = Water.getImage3(14, 1, 32, 32);	
		water[14] = Water.getImage3(15, 1, 32, 32);
		water[15] = Water.getImage3(1, 2, 32, 32);
		water[16] = Water.getImage3(2, 2, 32, 32);
		water[17] = Water.getImage3(3, 2, 32, 32);
		water[18] = Water.getImage3(4, 2, 32, 32);
		water[19] = Water.getImage3(5, 2, 32, 32);
		water[20] = Water.getImage3(6, 2, 32, 32);
		water[21] = Water.getImage3(7, 2, 32, 32);
		water[22] = Water.getImage3(8, 2, 32, 32);
		water[23] = Water.getImage3(9, 2, 32, 32);
		water[24] = Water.getImage3(10, 2, 32, 32);
		water[25] = Water.getImage3(11, 2, 32, 32);
		water[26] = Water.getImage3(12, 2, 32, 32);
		water[27] = Water.getImage3(13, 2, 32, 32);
		water[28] = Water.getImage3(14, 2, 32, 32);
		water[29] = Water.getImage3(15, 2, 32, 32);
		water[30] = Water.getImage3(1, 3, 32, 32);
		water[31] = Water.getImage3(2, 3, 32, 32);
		
		//Idle player stances
		player[0] = Player.getImage2(1,1 ,41,63);//idle player stance 1
		player[1] = Player.getImage2(2,1 ,41,64);//idle player stance 1
		player[2] = Player.getImage2(3,1 ,41,65);//idle player stance 1
		
		//Walk frames
		playerwalk[0] = Player.getImage2(1, 3, 47, 55);//First Frame
		playerwalk[1] = Player.getImage2(2, 3, 47, 55);//Second Frame
		playerwalk[2] = Player.getImage2(3, 3, 55, 52);//Third Frame
		playerwalk[3] = Player.getImage2(4, 3, 48, 56);//Forth Frame
		playerwalk[4] = Player.getImage2(5, 3, 48, 55);//Fifth Frame
		playerwalk[5] = Player.getImage2(6, 3, 49, 57);//Sixth Frame
		playerwalk[6] = Player.getImage2(7, 3, 56, 52);//Seventh Frame
		playerwalk[7] = Player.getImage2(8, 3, 46, 57);//Eight Frame
		
		//Pistol
		playerpistol[0] = Player2.getImage(1, 1, 35, 63);
		playerpistol[1] = Player2.getImage(2, 1, 32, 64);
		playerpistol[2] = Player2.getImage(3, 1, 40, 64);
		playerpistol[3] = Player2.getImage(4, 1, 21, 55);
		playerpistol[4] = Player2.getImage(5, 1, 56, 50);
		playerpistol[5] = Player2.getImage(6, 1, 53, 50);
		playerpistol[6] = Player2.getImage(7, 1, 53, 52);
		playerpistol[7] = Player2.getImage(8, 1, 87, 54);
		playerpistol[8] = Player2.getImage(9, 1, 112, 55);
		playerpistol[9] = Player2.getImage(10, 1, 128, 54);
		playerpistol[10] = Player2.getImage(11, 1, 148, 59);
		playerpistol[11] = Player2.getImage(12, 1, 165, 67);
		playerpistol[12] = Player2.getImage(13, 1, 208, 69);
		playerpistol[13] = Player2.getImage(14, 1, 41, 56);
		
		//Gatling 
		playergatling[0] = Player2.getImage(1,2,40,60);
		playergatling[1] = Player2.getImage(2,2,40,56);
		playergatling[2] = Player2.getImage(3,2,45,58);
		playergatling[3] = Player2.getImage(4,2,46,52);
		playergatling[4] = Player2.getImage(5,2,165,58);
		playergatling[5] = Player2.getImage(6,2,155,64);
		playergatling[6] = Player2.getImage(7,2,161,71);
		playergatling[7] = Player2.getImage(8,2,164,76);
		playergatling[8] = Player2.getImage(9,2,158,71);
		playergatling[9] = Player2.getImage(10,2,165,59);
		playergatling[10] = Player2.getImage(11,2,60,47);
		playergatling[11] = Player2.getImage(12,2,56,45);
		playergatling[12] = Player2.getImage(13,2,45,53);
		playergatling[13] = Player2.getImage(14,2,33,61);
		
		//Haki Gatling 
		playerhakigatling[0] = Player2.getImage(1,3,40,60);
		playerhakigatling[1] = Player2.getImage(2,3,40,56);
		playerhakigatling[2] = Player2.getImage(3,3,45,58);
		playerhakigatling[3] = Player2.getImage(4,3,46,52);
		playerhakigatling[4] = Player2.getImage(5,3,165,58);
		playerhakigatling[5] = Player2.getImage(6,3,155,64);
		playerhakigatling[6] = Player2.getImage(7,3,161,71);
		playerhakigatling[7] = Player2.getImage(8,3,164,76);
		playerhakigatling[8] = Player2.getImage(9,3,158,71);
		playerhakigatling[9] = Player2.getImage(10,3,165,59);
		playerhakigatling[10] = Player2.getImage(11,3,60,47);
		playerhakigatling[11] = Player2.getImage(12,3,56,45);
		playerhakigatling[12] = Player2.getImage(13,3,45,53);
		playerhakigatling[13] = Player2.getImage(14,3,33,61);

		//Marine idle
		marineidle[0] = Marine.getImage2(1, 1, 43, 48);
		marineidle[1] = Marine.getImage2(2, 1, 43, 48);
		
		//Marine run
		marinewalk[0] = Marine.getImage2(1, 2, 29, 57);
		marinewalk[1] = Marine.getImage2(2, 2, 36, 55);
		marinewalk[2] = Marine.getImage2(3, 2, 30, 57);
		marinewalk[3] = Marine.getImage2(4, 2, 33, 55);
		
		//Marine attack
		marineattack[0] = Marine.getImage2(1, 3, 43, 49);
		marineattack[1] = Marine.getImage2(2, 3, 45, 52);
		marineattack[2] = Marine.getImage2(3, 3, 60, 57);
		marineattack[3] = Marine.getImage2(4, 3, 55, 44);
		marineattack[4] = Marine.getImage2(5, 3, 51, 39);
		marineattack[5] = Marine.getImage2(6, 3, 48, 42);
		
		//Marine die
		marinedie[0] = Marine.getImage2(1, 4, 43, 42);
		marinedie[1] = Marine.getImage2(2, 4, 45, 44);
		marinedie[2] = Marine.getImage2(3, 4, 51, 17);
		marinedie[3] = Marine.getImage2(4, 4, 50, 19);
		marinedie[4] = Marine.getImage2(5, 4, 47, 22);
		marinedie[5] = Marine.getImage2(6, 4, 51, 17);
		
		//Marineflinch
		marineflinch[0] = Marine.getImage2(1, 5, 38, 57);
		marineflinch[1] = Marine.getImage2(2, 5, 38, 57);
		marineflinch[2] = Marine.getImage2(3, 5, 41, 54);
	}
}
