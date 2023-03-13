package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ExtraChefPowerup extends Powerup {
    public ExtraChefPowerup(float x, float y){
        super(new Texture(Gdx.files.internal("Powerups/chefPowerup.png")), x, y);
    }

}
