package Recipe;

import Ingredients.Beans;
import Ingredients.Cheese;
import Ingredients.Potato;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class JacketPotatoRecipe extends Recipe{

    public JacketPotatoRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Potato(null,null));
        ingredients.add(new Beans(null,null));
        ingredients.add(new Cheese(null,null));
        completedImg = new Texture("Food/Jacket_Potato.png");
    }

}
