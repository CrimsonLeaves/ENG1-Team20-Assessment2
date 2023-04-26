package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/**
 * InstantCookPowerup extends the Powerup class.
 * It creates an item to pickup that causes all items to require no time to cook - they can still burn though.
 */
public class InstantCookPowerup extends Powerup {
    public InstantCookPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/instantCookPowerup.png")), x, y);
    }

}
