package Sprites;

import Ingredients.Cheese;
import Ingredients.Ingredient;
import Ingredients.Lettuce;
import Tools.Constants;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class CheeseStation  extends IngredientStation{
    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public CheeseStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        timers.put(Constants.CHOPPING_BOARD, (float) 2);
        completed.put(Constants.CHOPPING_BOARD, false);
        completed.put(Constants.PAN, true);
    }

    @Override
    public Ingredient getIngredient(){return new Cheese(new HashMap<>(timers),new HashMap<>(completed));}
}
