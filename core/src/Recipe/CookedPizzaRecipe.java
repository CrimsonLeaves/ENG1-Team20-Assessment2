package Recipe;

import com.badlogic.gdx.graphics.Texture;

/**
 * The CookedPizzaRecipe Class is a subclass of the Recipe class and represents a cooked pizza dish in the kitchen game.
 * It has a Texture of the completed dish
 */
public class CookedPizzaRecipe extends Recipe{


    public CookedPizzaRecipe(){
        completedImg = new Texture("Food/Cooked_pizza.png");
    }

}
