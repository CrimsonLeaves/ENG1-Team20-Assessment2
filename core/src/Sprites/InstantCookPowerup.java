package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class InstantCookPowerup extends Powerup {
    public InstantCookPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/instantCookPowerup.png")), x, y);
    }

}
