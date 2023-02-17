package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Pan is a concrete class that extends the {@link InteractiveTileObject} class.
 * Pan is a class extending InteractiveTileObject representing a Pan in the game where the chef can cook steaks
 * and toast buns
 */

public class Pan extends CookingStation {
    public Pan(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);

    }

    @Override
    public void update(float dt){
        if(currentIngredient != null) {
            timer += dt;
            if (timer > currentIngredient.cookTime) {
                currentIngredient.setCooked();
            }
        }
    }
}
