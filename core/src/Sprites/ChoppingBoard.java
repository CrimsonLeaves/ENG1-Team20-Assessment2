package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The ChoppingBoard class extends the InteractiveTileObject class.
 *
 * This class is used to define a chopping board object in the game that can chop up steak into a patty and
 * to cut up fruit and vegetables
 */
public class ChoppingBoard extends CookingStation {

    public ChoppingBoard(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);

    }

}
