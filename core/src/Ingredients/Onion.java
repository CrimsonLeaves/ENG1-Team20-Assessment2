package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Onion extends Ingredient{

    /**
     * The Onion class represents a specific type of ingredient in the game, specifically onions.
     * It extends the {@link Ingredient} class and has a preparation time and cooking time.
     * The Onion class sets up an ArrayList of textures for its different skins.
     */

    public Onion(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers, completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Onion.png"));
        tex.add(new Texture("Food/Chopped_onion.png"));
    }
}
