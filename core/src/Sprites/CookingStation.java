package Sprites;

import Ingredients.FailedIngredient;
import Ingredients.Ingredient;
import Tools.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.team13.piazzapanic.MainGame;

public class CookingStation extends InteractiveTileObject{


    protected Ingredient currentIngredient;

    protected float timer;
    private final Texture progBarBack;
    private final Texture progBarFill;
    private final Texture progBarBurn;

    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public CookingStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        currentIngredient = null;
        timer = 0;
        fixture.setUserData(this);
        progBarBack = new Texture("UI/progBarBackground.png");
        progBarFill = new Texture("UI/progBarMain.png");
        progBarBurn = new Texture("UI/progBarBurn.png");
    }

    /**
     * updates the state of the cooking station
     * @param dt delta time
     * @param diff difficulty
     * @param instantCook is instant cook powerup active
     * @param noBurn is no burn powerup active
     */
    public void update(float dt,float diff,boolean instantCook, boolean noBurn){
        if(currentIngredient != null && !currentIngredient.getFailed()) {
            if (instantCook && !currentIngredient.isCompleted(Constants.CHOPPING_BOARD)){
                timer=currentIngredient.getTimer(Constants.CHOPPING_BOARD);
            }
            timer += dt;
            if (timer > currentIngredient.getTimer(Constants.CHOPPING_BOARD)
                && !currentIngredient.isCompleted(Constants.CHOPPING_BOARD)) {
                currentIngredient.setCompleted(Constants.CHOPPING_BOARD);
                timer = 0;
            } else if (noBurn && currentIngredient.isCompleted(Constants.CHOPPING_BOARD)){
                timer=0;
            } else if( timer > currentIngredient.getTimer(Constants.CHOPPING_BOARD) *2*diff
                    && currentIngredient.isCompleted(Constants.CHOPPING_BOARD)){
                currentIngredient = new FailedIngredient();
            }
        }
    }

    /**
     * Draws a progress bar for the given station of the ingredient's progress
     * @param batch game's sprite batch
     * @param station current station name
     * @param diff current game difficulty
     */
    public void drawProgressBar(SpriteBatch batch, String station,float diff){
        if (currentIngredient != null&& !currentIngredient.getFailed()) { //if there is an ingredient requiring a progress bar
            float adjX = getX()-(MainGame.TILE_SIZE+1)/2f/MainGame.PPM; //get x
            float adjY = getY()-MainGame.TILE_SIZE/2f/MainGame.PPM; //get y
            if (!currentIngredient.isCompleted(station)){ //If ingredient isn't cooked (first progress bar)
                float progress=timer/currentIngredient.getTimer(station); //get progress as 0-1
                batch.draw(progBarBack,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM)); //draw background
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM)); //draw progress bar
            }
            else{ //if cooked and burning progress bar required
                float progress=timer/(currentIngredient.getTimer(station)*2*diff); //get burning progress
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM)); //draw background
                batch.draw(progBarBurn,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM)); //draw progress bar
            }
        }

    }

    /**
     * setter for the ingredient on the cooking station
     * @param ingredient ingredient to be set
     */
    public void setCurrentIngredient(Ingredient ingredient) {
        currentIngredient = ingredient;
        timer = 0;
    }

    /**
     * getter for the current ingredient
     * @return currentIngredient
     */
    public Ingredient getCurrentIngredient(){return currentIngredient;}

    /**
     * getter for the x position of the station
     * @return x coordinate
     */
    public float getX(){
        return super.bdefNew.position.x;
    }

    /**
     * getter for the y position of the station
     * @return y coordinate
     */
    public float getY(){
        return super.bdefNew.position.y;
    }

    /**
     * setter for the stations timer
     * @param timer the timer to be set
     */
    public void setTimer(float timer) {
        this.timer = timer;
    }

    /**
     * getter for the stations timer
     * @return timer
     */
    public float getTimer() {return timer;}
}
