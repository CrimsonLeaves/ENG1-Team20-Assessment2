package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Cheese extends Ingredient{
    /**
     * Constructs a new Ingredient object with the specified preparation and cooking times.
     *
     * @param timers    A dictionary with keys of the cooking stations and values of their relative cooking timers
     * @param completed A dictionary with keys of the cooking stations and values (idk how to word it)
     */
    public Cheese(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers, completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/cheese.png"));
        tex.add(new Texture("Food/Chopped_cheese.png"));
    }
}
