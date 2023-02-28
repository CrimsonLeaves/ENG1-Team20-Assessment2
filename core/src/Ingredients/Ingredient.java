package Ingredients;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team13.piazzapanic.MainGame;

import java.util.ArrayList;
import java.util.Map;

public abstract class Ingredient extends Sprite {
    /**
     * An array of textures representing different states of the ingredient.
     */
    protected ArrayList<Texture> tex;

    private final Map<String, Float> timers;

    private final Map<String, Boolean> completed;

    private int skin;

    /**
     * Constructs a new Ingredient object with the specified preparation and cooking times.
     *
     * @param timers A dictionary with keys of the cooking stations and values of their relative cooking timers
     * @param completed A dictionary with keys of the cooking stations and values (idk how to word it)
     */
    public Ingredient( Map<String, Float> timers, Map<String, Boolean> completed) {
        this.tex = null;
        this.timers = timers;
        this.completed = completed;
        this.skin = 0;
    }

    /**
     * Creates and draws a new Sprite object representing the ingredient.
     *
     * @param x The x coordinate of the ingredient.
     * @param y The y coordinate of the ingredient.
     * @param batch The SpriteBatch object used to draw the ingredient.
     */
    public void create(float x, float y, SpriteBatch batch){
        Sprite sprite = new Sprite(tex.get(skin));
        float adjustedX =  x - (5/MainGame.PPM);
        float adjustedY =  y - (4.95f / MainGame.PPM);
        sprite.setBounds(adjustedX,adjustedY,10/ MainGame.PPM,10/ MainGame.PPM);
        sprite.draw(batch);
    }


    //getOrDefault exists try using that
    public Float getTimer(String station){
        return timers.get(station);
    }

    public Boolean isCompleted(String station){
        return completed.get(station);
    }

    public void setCompleted(String station){
        completed.put(station, true);
        skin += 1;
    }

}