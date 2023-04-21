package Sprites;

import Ingredients.FailedIngredient;
import Tools.Constants;
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
    public void update(float dt, float diff,boolean instantCook, boolean noBurn){
        if(currentIngredient != null && !currentIngredient.getFailed()) {
            if (instantCook && !currentIngredient.isCompleted(Constants.PAN)){
                timer=currentIngredient.getTimer(Constants.PAN);
            }
            timer += dt;
            if (timer > currentIngredient.getTimer(Constants.PAN)
                && !currentIngredient.isCompleted(Constants.PAN)) {
                currentIngredient.setCompleted(Constants.PAN);
                timer = 0;
            } else if (noBurn && currentIngredient.isCompleted(Constants.PAN)){
                timer=0;
            } else if( timer > currentIngredient.getTimer(Constants.PAN)*2*diff
                        && currentIngredient.isCompleted(Constants.PAN)){
                currentIngredient = new FailedIngredient();
            }
        }
    }
}
