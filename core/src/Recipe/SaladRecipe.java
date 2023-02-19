package Recipe;

import Ingredients.*;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 * The SaladRecipe class is a subclass of the Recipe class.
 * It holds an ArrayList of ingredients that makes up a salad dish, and the texture of the completed salad dish.
 * The salad dish consists of {@link Ingredients.Lettuce}, {@link Ingredients.Tomato} and {@link Ingredients.Onion} ingredients.
 */

public class SaladRecipe extends Recipe {
    public SaladRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Lettuce(null, null));
        ingredients.add(new Tomato(null, null));
        ingredients.add(new Onion(null, null));
        completedImg = new Texture("Food/Salad.png");
    }
}
