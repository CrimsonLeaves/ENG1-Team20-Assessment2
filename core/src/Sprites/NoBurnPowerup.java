package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/**
 * NoBurnPowerup extends the Powerup class.
 * It creates an item to pickup that causes all items not to burn for x seconds
 */
public class NoBurnPowerup extends Powerup {
    public NoBurnPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/noBurnPowerup.png")), x, y);
    }

}
