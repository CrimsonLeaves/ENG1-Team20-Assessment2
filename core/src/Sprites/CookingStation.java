package Sprites;

import Ingredients.FailedIngredient;
import Ingredients.Ingredient;
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

    public void update(float dt,float diff){
        if(currentIngredient != null && !currentIngredient.getFailed()) {
            timer += dt;
            if (timer > currentIngredient.getTimer("Chopping Board")
                && !currentIngredient.isCompleted("Chopping Board")) {
                currentIngredient.setCompleted("Chopping Board");
                timer = 0;
            } else if( timer > currentIngredient.getTimer("Chopping Board") *2*diff
                    && currentIngredient.isCompleted("Chopping Board")){
                currentIngredient = new FailedIngredient();
            }
        }
    }
    public void drawProgressBar(SpriteBatch batch, String station,float diff){
        if (currentIngredient != null&& !currentIngredient.getFailed()) {
            float adjX = getX()-MainGame.TILE_SIZE/2/MainGame.PPM;
            float adjY = getY()-MainGame.TILE_SIZE/2/MainGame.PPM;
            if (!currentIngredient.isCompleted(station)){
                float progress=timer/currentIngredient.getTimer(station);
                batch.draw(progBarBack,adjX,adjY,MainGame.TILE_SIZE/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarFill,adjX,adjY,MainGame.TILE_SIZE*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
            else{
                float progress=timer/(currentIngredient.getTimer(station)*2*diff);
                batch.draw(progBarFill,adjX,adjY,MainGame.TILE_SIZE/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarBurn,adjX,adjY,MainGame.TILE_SIZE*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
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
}
