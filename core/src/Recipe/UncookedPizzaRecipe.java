package Recipe;

import Ingredients.Cheese;
import Ingredients.Dough;
import Ingredients.Tomato;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class UncookedPizzaRecipe extends Recipe{

    public UncookedPizzaRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Dough(null,null));
        ingredients.add(new Cheese(null,null));
        ingredients.add(new Tomato(null,null));
        completedImg = new Texture("Food/pizza.png");
    }
}
