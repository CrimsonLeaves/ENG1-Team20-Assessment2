package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Lettuce extends Ingredient{

    /**
     * The Lettuce class represents a specific type of ingredient in the game, specifically lettuce.
     * It extends the {@link Ingredient} class and has a preparation time and cooking time.
     * The Lettuce class sets up an ArrayList of textures for its different skins.
     */

    public Lettuce(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers, completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Lettuce.png"));
        tex.add(new Texture("Food/Chopped_lettuce.png"));
    }
}
