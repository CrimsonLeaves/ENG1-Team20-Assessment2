package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.team13.piazzapanic.MainGame;
/**
 * Powerup class extends sprite
 * A superclass for all powerups holding main constructor, variables, and methods to create it.
 */
public class Powerup extends Sprite {
    private final float xx;
    private final float yy;
    public Rectangle collisionRect;
    public Powerup(Texture tex, float x, float y){
        super(tex);
        xx=x;
        yy=y;
        collisionRect = new Rectangle(x,y,tex.getWidth()/MainGame.PPM, tex.getHeight()/MainGame.PPM);
    }

    /**
     * draws the powerup in the world
     * @param batch the sprite of the powerup
     */
    public void create(SpriteBatch batch){
        this.setBounds(xx,yy,10/ MainGame.PPM,10/ MainGame.PPM);
        this.draw(batch);


    }

}
