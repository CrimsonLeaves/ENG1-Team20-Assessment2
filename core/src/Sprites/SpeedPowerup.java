package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpeedPowerup extends Powerup {
    public SpeedPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/speedPowerup.png")), x, y);
    }

}
