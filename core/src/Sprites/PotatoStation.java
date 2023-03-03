package Sprites;

import Ingredients.Cheese;
import Ingredients.Ingredient;
import Ingredients.Potato;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class PotatoStation extends IngredientStation{
    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public PotatoStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        timers.put("Oven", (float) 4);
        completed.put("Chopping Board", true);
        completed.put("Pan", true);
        completed.put("Oven", false);
    }

    @Override
    public Ingredient getIngredient(){return new Potato(new HashMap<>(timers),new HashMap<>(completed));}
}
