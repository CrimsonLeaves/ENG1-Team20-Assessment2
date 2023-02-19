package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Map;

public class Bun extends Ingredient{

    /**

     The Bun class represents a specific type of ingredient in the game, specifically the burger buns.
     It extends the {@link Ingredient} class and has a preparation time and cooking time.
     The Bun class sets the prepared flag to true in the constructor, and sets up an ArrayList of textures for its different skins.

     */

    public Bun(Map<String, Float> timers, Map<String, Boolean> completed) {
        super(timers,completed);
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Burger_buns.png"));
        tex.add(new Texture("Food/Toasted_burger_buns.png"));
    }
}
