package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ResetOrderPowerup extends Powerup {
    public ResetOrderPowerup( float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/resetPowerup.png")), x, y);
    }

}
