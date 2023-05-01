package Sprites;

import Ingredients.Dough;
import Ingredients.Ingredient;
import Tools.Constants;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class DoughStation extends IngredientStation{
    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public DoughStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        completed.put(Constants.CHOPPING_BOARD, true);
        completed.put(Constants.PAN, true);
    }

    /**
     * creates a new dough object
     * @return new dough object
     */
    @Override
    public Ingredient getIngredient(){return new Dough(new HashMap<>(timers),new HashMap<>(completed));}
}
