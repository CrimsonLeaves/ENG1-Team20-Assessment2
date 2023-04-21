package Sprites;

import Ingredients.Lettuce;
import Ingredients.Ingredient;
import Tools.Constants;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

/**
 * The `LettuceStation` class extends the `InteractiveTileObject` class.
 * It is used to represent a station for producing lettuce in the game.
 */
public class LettuceStation extends IngredientStation {
    /**
     * Constructs a LettuceStation.
     *
     * @param world the world where the LettuceStation will be located
     * @param map the TiledMap that the LettuceStation is in
     * @param bdef the BodyDef for the physics of the LettuceStation
     * @param rectangle the rectangle representing the boundaries of the LettuceStation
     */
    public LettuceStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        timers.put(Constants.CHOPPING_BOARD, (float) 2);
        completed.put(Constants.CHOPPING_BOARD, false);
        completed.put(Constants.PAN, true);
    }

    @Override
    public Ingredient getIngredient(){return new Lettuce(new HashMap<>(timers),new HashMap<>(completed));}
}


