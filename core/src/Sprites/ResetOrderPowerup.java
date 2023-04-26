package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/**
 * ResetOrderPowerup extends the Powerup class.
 * It creates an item to pickup that resets the current time left on the order
 */
public class ResetOrderPowerup extends Powerup {
    public ResetOrderPowerup( float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/resetPowerup.png")), x, y);
    }

}
