package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class NoBurnPowerup extends Powerup {
    public NoBurnPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/noBurnPowerup.png")), x, y);
    }

}
