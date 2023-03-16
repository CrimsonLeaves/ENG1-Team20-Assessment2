package Recipe;

import Ingredients.Beans;
import Ingredients.Cheese;
import Ingredients.Potato;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**

 The JacketPotatoRecipe class is a subclass of the Recipe class and represents a jacket potato dish in the kitchen game.
 It holds an ArrayList of ingredients needed to make a jacket potato and a Texture of the completed dish image.
 The ingredients in the ArrayList consist of a {@link Ingredients.Potato}, {@link Ingredients.Beans}
 and {@link Ingredients.Cheese}.
 */

public class JacketPotatoRecipe extends Recipe{

    public JacketPotatoRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Potato(null,null));
        ingredients.add(new Beans(null,null));
        ingredients.add(new Cheese(null,null));
        completedImg = new Texture("Food/Jacket_Potato.png");
    }

}
