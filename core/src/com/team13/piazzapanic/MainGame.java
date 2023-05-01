package com.team13.piazzapanic;

import Tools.DemoScript;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class MainGame extends Game {

	/**
	 * MainGame class is the central class of the game that creates and manages the two screens, PlayScreen and StartScreen.
	 * Class Members:
	 *     V_WIDTH (int): Width of the view.
	 *     V_HEIGHT (int): Height of the view.
	 *     TILE_SIZE (int): Size of the tile.
	 *     PPM (float): Pixels per meter.
	 *     batch (SpriteBatch): Instance of SpriteBatch.
	 *     isPlayScreen (bool): Flag indicating whether the PlayScreen is displayed or not.
	 *     playScreen (PlayScreen): Instance of PlayScreen.
	 *     startScreen (StartScreen): Instance of StartScreen.
	 * Methods:
	 *     __init__: Initializes the MainGame class.
	 *     create: Creates the instances of StartScreen and PlayScreen and initializes the SpriteBatch instance.
	 *     render: Renders the StartScreen or PlayScreen based on the value of isPlayScreen flag.
	 * 	   dispose: Releases resources used by the MainGame class.
	 */
	public static final int V_WIDTH = 160;
	public static final int V_HEIGHT = 160;
	public static final int TILE_SIZE = 16;

	public static final float PPM = 100;
	public SpriteBatch batch;
	public boolean isPlayScreen;
	public boolean isEndScreen;
	public boolean isShopScreen;
	public boolean isInstructionsScreen;
	public boolean inGame;
	public boolean idle;
	public boolean scenarioMode;
	public boolean loadGame;
	public String difficulty;
	private int money;
	//Chef counts
	private int chefCount;
	private int unlockedChefs;
	private final int maxChefs;
	//Screens
	public PlayScreen playScreen;
	public StartScreen startScreen;
	public EndScreen endScreen;
	public MainMenu mainMenu;
	public InstructionsScreen instructionsScreen;
	public DemoScreen demoScreen;
	public ShopScreen shopScreen;
	private float idleTime;
	Preferences prefs;


	/**
	 * Constructor for MainGame, instantiates all variables that are used between classes
	 */
	public MainGame(){
		isInstructionsScreen=false;
		idleTime=0;
		idle=false;
		isShopScreen = false;
		isPlayScreen = false;
		isEndScreen = false;
		inGame = false;
		scenarioMode = true;
		difficulty = "Easy";
		loadGame=true;
		money = 0;
		chefCount = 3;
		unlockedChefs = 3;
		maxChefs = 5;
	}

	/**
	 * Initialises auxiliary screens and loads game data from preferences
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		startScreen = new StartScreen(this);
		endScreen = new EndScreen(this);
		mainMenu = new MainMenu(this);
		shopScreen = new ShopScreen(this);
		instructionsScreen = new InstructionsScreen(this);
		//demoScreen = new DemoScreen(this);
		prefs = Gdx.app.getPreferences("gameData");
		money = prefs.getInteger("money", 0);
		chefCount = prefs.getInteger("chefCount", 3);
		unlockedChefs = prefs.getInteger("unlockedChefs", 3);

	}

	/**
	 * Gets money from the preference file
	 * @return The overall money earned
	 */
	public int getMoney() {
		prefs.getInteger("money", 0);
		return money;
	}

	/**
	 * Alters the overall money saved in the preference file, either adds or subtracts money
	 * @param money money to be added to overall total
	 */
	public void addMoney(int money) {
		prefs = Gdx.app.getPreferences("gameData");
		this.money+=money;
		prefs.putInteger("money",this.money);
		prefs.flush();
	}
	//ChefCount getter and setter
	public int getChefCount() {
		chefCount = prefs.getInteger("chefCount", 3);
		return chefCount;
	}
	public void setChefCount(int chefCount) {
		prefs = Gdx.app.getPreferences("gameData");
		this.chefCount=chefCount;
		prefs.putInteger("chefCount",this.chefCount);
		prefs.flush();
	}
	//UnlockedChefs getter and setter
	public int getUnlockedChefs() {
		unlockedChefs = prefs.getInteger("unlockedChefs", 3);
		return unlockedChefs;
	}
	public void setUnlockedChefs(int unlockedChefs) {
		prefs = Gdx.app.getPreferences("gameData");
		this.unlockedChefs=unlockedChefs;
		prefs.putInteger("unlockedChefs",this.unlockedChefs);
		prefs.flush();
	}
	//MaxChefs getter
	public int getMaxChefs() {return maxChefs;}


	/**
	 * Controls what screen should be present through different booleans
	 */
	@Override
	public void render() {
		if (!inGame){
			idleTime+=Gdx.graphics.getDeltaTime();
		}
		if (idleTime > 60 && !idle){
			idle=true;
			demoScreen=new DemoScreen(this);
		}
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
			idleTime=0;
			idle=false;

		}

		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB) && inGame) {
			isPlayScreen = !isPlayScreen;
		}
		if (isEndScreen) {
			setScreen(endScreen);
		} else if (inGame) {
			if (isPlayScreen) {
				setScreen(playScreen);
			} else {
				setScreen(startScreen);
			}
		} else if (isShopScreen) {
			setScreen(shopScreen);
		} else if (idle) {
			setScreen(demoScreen);
		} else if (isInstructionsScreen){
			setScreen(instructionsScreen);
		}else {
			setScreen(mainMenu);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}