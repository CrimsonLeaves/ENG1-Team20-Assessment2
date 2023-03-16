package Recipe;

import Ingredients.Cheese;
import Ingredients.Dough;
import Ingredients.Tomato;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 The UncookedPizzaRecipe class is a subclass of the Recipe class and represents
 an uncooked pizza dish in the kitchen game.
 It holds an ArrayList of ingredients needed to make an uncooked pizza and a Texture of the completed dish image.
 The ingredients in the ArrayList consist of {@link Ingredients.Dough} , {@link Ingredients.Cheese}
 and {@link Ingredients.Tomato}.
 */

public class UncookedPizzaRecipe extends Recipe{

    public UncookedPizzaRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Dough(null,null));
        ingredients.add(new Cheese(null,null));
        ingredients.add(new Tomato(null,null));
        completedImg = new Texture("Food/pizza.png");
    }
}
