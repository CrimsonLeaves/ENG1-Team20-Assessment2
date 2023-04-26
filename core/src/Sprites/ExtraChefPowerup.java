package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * ExtraChefPowerup extends the Powerup class.
 * It creates an item to pickup and create an extra chef in the game.
 */
public class ExtraChefPowerup extends Powerup {
    public ExtraChefPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/chefPowerup.png")), x, y);
    }

}
