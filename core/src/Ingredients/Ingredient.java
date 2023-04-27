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

    /**
     * A dictionary with keys of the cooking stations and values of their relative cooking timers
     */
    private final Map<String, Float> timers;
    /**
     * A dictionary with keys of the cooking stations and values representing if the ingredient
     * requires preparing at that station
     */
    private final Map<String, Boolean> completed;

    /**
     * the current index of tex -- represents the current skin to be displayed
     */
    private int skin;
    /**
     * a value representing if the ingredient is an instance of FailedIngredient
     */
    private boolean failed;

    /**
     * Constructs a new Ingredient object with the specified timers and completed dictionaries.
     *
     * @param timers A dictionary with keys of the cooking stations and values of their relative cooking timers
     * @param completed A dictionary with keys of the cooking stations and values representing if the ingredient
     *                  requires preparing at that station
     */
    public Ingredient(Map<String, Float> timers, Map<String, Boolean> completed) {
        this.tex = null;
        this.timers = timers;
        this.completed = completed;
        this.skin = 0;
        this.failed = false;
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

    /**
     * Checks if all values of completed are true - meaning the ingredient is fully prepared.
     *
     * @return true if all values are true, false if not.
     */
    public Boolean isAllCompleted(){
        for (boolean complete : completed.values()) {
            if(!complete){return false;}
        }
        return true;
    }

    /**
     * Returns the timer of the input station
     * @param station the station corresponding to the desired timer
     * @return the timer value as a Float
     */
    public Float getTimer(String station){
        return timers.get(station);
    }

    /**
     * Returns the all the timers for the ingrdient - used for saving
     * @return the Map of each station and their timer
     */
    public Map<String, Float> getTimers(){
        return timers;
    }

    /**
     * Gets the ingredient's current skin index
     * @return the skin index
     */
    public int getSkin() {
        return skin;
    }

    /**
     * Sets the ingredient's current skin index
     * @param skin ingredient skin index
     */
    public void setSkin(int skin) {
        if (skin != 0 || skin != 1){
            return;
        }
        this.skin = skin;
    }

    /**
     * Returns the all the completed state for the ingredient in each station
     * @return the Map of each station and if they have been completed
     */
    public Map<String, Boolean> getCompleted() {
        return completed;
    }

    /**
     * Returns the boolean value of the input station
     * @param station the station corresponding to the desired boolean value
     * @return the Boolean value of the station
     */
    public Boolean isCompleted(String station){
        return completed.get(station);
    }

    /**
     * Sets the stations value to true and increments skin to correspond to the prepared sprite
     * @param station the station which preparation has been completed for
     */
    public void setCompleted(String station){
        completed.put(station, true);
        skin += 1;
    }

    /**
     * Sets the value of failed to be true in FailedIngredient.java
     */
    protected void setFailed(){this.failed = true;}

    /**
     * Gets the value of failed to determine if an ingredient is of class FailedIngredient
     * @return failed - boolean value representing if the ingredient has failed a preparation step
     */
    public Boolean getFailed(){return this.failed;}

}