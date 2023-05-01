package Sprites;

import Ingredients.Beans;
import Ingredients.Ingredient;
import Tools.Constants;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class BeansStation extends IngredientStation{
    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public BeansStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        timers.put(Constants.PAN, (float) 3);
        completed.put(Constants.CHOPPING_BOARD, true);
        completed.put(Constants.PAN, false);
    }

    /**
     * creates a new beans object
     * @return new beans object
     */
    @Override
    public Ingredient getIngredient(){return new Beans(new HashMap<>(timers),new HashMap<>(completed));}
}
