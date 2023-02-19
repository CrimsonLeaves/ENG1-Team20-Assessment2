package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Steak extends Ingredient{

    /**
     * The Steak class represents a specific type of ingredient in the game, specifically steak.
     * It extends the {@link Ingredient} class and has a preparation time and cooking time.
     * The Steak class sets up an ArrayList of textures for its different skins.
     */

    public Steak(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers, completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Meat.png"));
        tex.add(new Texture("Food/Patty.png"));
        tex.add(new Texture("Food/Cooked_patty.png"));
    }
}