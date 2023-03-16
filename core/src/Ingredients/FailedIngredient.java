package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class FailedIngredient extends Ingredient{
    /**
     * The FailedIngredient class represents an Ingredient that has failed a preparation step in the game.
     * It extends the {@link Ingredient} class and has the failed value set to true.
     * The FailedIngredient class sets up an ArrayList of textures for its different skins.
     */
    public FailedIngredient() {
        super(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Failed_ingredient.png"));

        setFailed();
    }
}
