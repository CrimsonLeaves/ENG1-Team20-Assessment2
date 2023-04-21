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
    private Texture progBarBack;
    private Texture progBarFill;
    private Texture progBarBurn;

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
    public void drawProgressBar(SpriteBatch batch, String station,float diff){
        if (currentIngredient != null&& !currentIngredient.getFailed()) {
            float adjX = getX()-(MainGame.TILE_SIZE+1)/2/MainGame.PPM;
            float adjY = getY()-MainGame.TILE_SIZE/2/MainGame.PPM;
            if (!currentIngredient.isCompleted(station)){
                float progress=timer/currentIngredient.getTimer(station);
                batch.draw(progBarBack,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
            else{
                float progress=timer/(currentIngredient.getTimer(station)*2*diff);
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarBurn,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
        }

    }

    public void setCurrentIngredient(Ingredient ingredient) {
        currentIngredient = ingredient;
        timer = 0;
    }
    public Ingredient getCurrentIngredient(){return currentIngredient;}

    public float getX(){
        return super.bdefNew.position.x;
    }

    public float getY(){
        return super.bdefNew.position.y;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }
    public float getTimer() {return timer;}
}
