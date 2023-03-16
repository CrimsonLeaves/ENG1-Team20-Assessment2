package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Tomato extends Ingredient{

    /**
     * The Tomato class represents a specific type of ingredient in the game, specifically tomato.
     * It extends the {@link Ingredient} class and has dictionaries for timers
     * and which preparation steps are completed.
     * The Tomato class sets up an ArrayList of textures for its different skins.
     *
     * @param timers    A dictionary with keys of the cooking stations and values of their relative cooking timers
     * @param completed A dictionary with keys of the cooking stations and values representing if the ingredient
     *                  requires preparing at that station
     */

    public Tomato(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers, completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Tomato.png"));
        tex.add(new Texture("Food/Chopped_tomato.png"));

    }
}
