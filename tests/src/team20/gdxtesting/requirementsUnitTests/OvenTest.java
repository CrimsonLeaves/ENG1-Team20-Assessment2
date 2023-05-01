package team20.gdxtesting.requirementsUnitTests;

import Ingredients.Steak;
import Recipe.BurgerRecipe;
import Sprites.Oven;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.HashMap;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class OvenTest {

    /**
     * Testing the methods exclusive to Oven (not inherited from cooking station).
     */
    @Test
    //Test Requirement: UR_FOOD_PREP/UR_RECIPE
    public void test_CurrentRecipe(){
        //Retrieve and change the current recipe.
        Oven oven = new Oven(new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        BurgerRecipe burgerRecipe = new BurgerRecipe();
        Steak steak = new Steak(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        //Initially the recipe should be null.
        assertNull("No recipe set so current recipe should be null."
                , oven.getCurrentRecipe());

        //Set the recipe of the oven.
        oven.setCurrentRecipe(burgerRecipe);
        assertTrue("The recipe should be set to burger."
                , burgerRecipe==oven.getCurrentRecipe());

        //Check that a new recipe can't be set if there is currently an ingredient.
        oven.setCurrentRecipe(null);
        oven.setCurrentIngredient(steak);
        oven.setCurrentRecipe(burgerRecipe);
        assertNull("There is already an ingredient so a new recipe cannot be set."
                , oven.getCurrentRecipe());

    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_setCurrentIngredient(){
        //Test that ingredients are set correctly.
        Oven oven = new Oven(new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        BurgerRecipe burgerRecipe = new BurgerRecipe();
        Steak steak = new Steak(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        //Can set with no recipe
        oven.setCurrentIngredient(steak);
        assertTrue("Ingredient should be set to steak."
                , oven.getCurrentIngredient()==steak);
        //Can't set with a recipe
        oven.setCurrentIngredient(null);
        oven.setCurrentRecipe(burgerRecipe);
        oven.setCurrentIngredient(steak);
        assertNull("A recipe has been set so no ingredient can be."
                , oven.getCurrentIngredient());

    }

    @Test
    //Test Requirement: UR_FOOD_PREP/UR_RECIPE/FR_NO_BURN_POWERUP/FR_INSTACOOK_POWERUP
    public void test_update(){
        //
    }
}
