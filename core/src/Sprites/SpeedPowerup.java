package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/**
 * SpeedPowerup extends the Powerup class.
 * It creates an item to pickup that gives the controlled chef increased movement speed.
 */
public class SpeedPowerup extends Powerup {
    public SpeedPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/speedPowerup.png")), x, y);
    }

}
