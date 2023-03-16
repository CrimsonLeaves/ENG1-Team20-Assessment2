package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Potato extends Ingredient{
    /**
     * The Potato class represents a specific type of ingredient in the game, specifically potato.
     * It extends the {@link Ingredient} class and has dictionaries for timers
     * and which preparation steps are completed.
     * The Potato class sets up an ArrayList of textures for its different skins.
     *
     * @param timers    A dictionary with keys of the cooking stations and values of their relative cooking timers
     * @param completed A dictionary with keys of the cooking stations and values representing if the ingredient
     *                  requires preparing at that station
     */
    public Potato(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers, completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Potato.png"));
        tex.add(new Texture("Food/Baked_potato.png"));
    }
}
