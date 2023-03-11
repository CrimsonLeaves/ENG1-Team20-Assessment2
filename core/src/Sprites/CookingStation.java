package Sprites;

import Ingredients.FailedIngredient;
import Ingredients.Ingredient;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class CookingStation extends InteractiveTileObject{


    protected Ingredient currentIngredient;

    protected float timer;

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
    }

    public void update(float dt){
        if(currentIngredient != null && !currentIngredient.getFailed()) {
            timer += dt;
            if (timer > currentIngredient.getTimer("Chopping Board")
                && !currentIngredient.isCompleted("Chopping Board")) {
                currentIngredient.setCompleted("Chopping Board");
                timer = 0;
            } else if( timer > currentIngredient.getTimer("Chopping Board") *2
                    && currentIngredient.isCompleted("Chopping Board")){
                currentIngredient = new FailedIngredient();
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
