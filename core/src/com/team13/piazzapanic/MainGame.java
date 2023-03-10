package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class MainGame extends Game {

	/**
	 * MainGame class is the central class of the game that creates and manages the two screens, PlayScreen and StartScreen.
	 *
	 * Class Members:
	 *     V_WIDTH (int): Width of the view.
	 *     V_HEIGHT (int): Height of the view.
	 *     TILE_SIZE (int): Size of the tile.
	 *     PPM (float): Pixels per meter.
	 *     batch (SpriteBatch): Instance of SpriteBatch.
	 *     isPlayScreen (bool): Flag indicating whether the PlayScreen is displayed or not.
	 *     playScreen (PlayScreen): Instance of PlayScreen.
	 *     startScreen (StartScreen): Instance of StartScreen.
	 *
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
	public boolean inGame;
	public boolean scenarioMode;
	public String difficulty;
	public PlayScreen playScreen;
	public StartScreen startScreen;
	public EndScreen endScreen;
	public MainMenu mainMenu;
	private int money;

	public MainGame(){

		isPlayScreen = false;
		isEndScreen = false;
		inGame = false;
		scenarioMode = false;
		difficulty="Easy";
		money=0;
	}
	@Override
	public void create() {
		batch = new SpriteBatch();
		startScreen = new StartScreen(this);
		playScreen = new PlayScreen(this);
		endScreen = new EndScreen(this);
		mainMenu = new MainMenu(this);

	}
	public int getMoney() {return money;}
	public void addMoney(int money) {this.money+=money;}

	@Override
	public void render() {
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB) && inGame){
			isPlayScreen = !isPlayScreen;
		}
		if (isEndScreen) {
			setScreen(endScreen);
		}
		else if (inGame){
			if (isPlayScreen ) {
				setScreen(playScreen);
			} else {
				setScreen(startScreen);
			}
		}
		else {
			setScreen(mainMenu);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}