package Sprites;

import Ingredients.Ingredient;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;
import java.util.Map;

public class IngredientStation extends InteractiveTileObject{

    protected Map<String, Float> timers;
    protected Map<String, Boolean> completed;

    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */

    public IngredientStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        timers = new HashMap<>();
        completed = new HashMap<>();
    }

    public Ingredient getIngredient(){return new Ingredient(timers, completed) {};}
}
