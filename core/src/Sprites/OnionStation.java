package Sprites;

import Ingredients.Bun;
import Ingredients.Ingredient;
import Ingredients.Onion;
import Tools.Constants;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

/**
  * OnionStation is a class extending InteractiveTileObject representing an Onion ingredient station.
  * It creates an OnionStation object with world, map, body definition and rectangle as parameters.
  * The fixture of the body is then set with the instance of the object as its user data.
  * The class also provides a method getIngredient() that returns a new Onion instance
 */

public class OnionStation extends IngredientStation {
    public OnionStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        timers.put(Constants.CHOPPING_BOARD, (float) 2);
        completed.put(Constants.CHOPPING_BOARD, false);
        completed.put(Constants.PAN, true);
    }

    @Override
    public Ingredient getIngredient(){return new Onion(new HashMap<>(timers),new HashMap<>(completed));}
}

