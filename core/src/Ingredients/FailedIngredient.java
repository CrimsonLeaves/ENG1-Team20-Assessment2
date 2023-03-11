package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FailedIngredient extends Ingredient{
    /**
     * Constructs a new Ingredient object with the specified preparation and cooking times.
     *
     */
    public FailedIngredient() {
        super(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        tex = new ArrayList<>();
        tex.add(new Texture("Food/Failed_ingredient.png"));

        setFailed(true);
    }
}
