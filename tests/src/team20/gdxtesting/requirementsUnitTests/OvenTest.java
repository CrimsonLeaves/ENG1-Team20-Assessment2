package team20.gdxtesting.requirementsUnitTests;

import Ingredients.Beans;
import Ingredients.FailedIngredient;
import Ingredients.Steak;
import Recipe.BurgerRecipe;
import Recipe.CookedPizzaRecipe;
import Recipe.UncookedPizzaRecipe;
import Sprites.Oven;
import Tools.Constants;
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
        //Change the current state of the oven.
        Oven oven = new Oven(new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        oven.setTimer(10f);
        //No recipe and no ingredient so nothing should change.
        oven.update(10f, 10f, false, false);
        assertTrue("No ingredient or recipe so the timer should remain unchanged."
                , oven.getTimer()==10f);
        //Failed ingredient.
        oven.setCurrentIngredient(new FailedIngredient());
        oven.update(10f, 10f, false, false);
        assertTrue("The ingredient is failed so nothing should be updated."
                , oven.getTimer()==10f);

        //Correct ingredient.
        //Branch 1 Apply the instant cook power up to the ingredient.
        HashMap<String, Float> timers = new HashMap<>();
        HashMap<String, Boolean> completed = new HashMap<>();
        timers.put(Constants.OVEN, 30f);
        completed.put(Constants.OVEN, false);
        Beans beans = new Beans(timers, completed);
        oven.setCurrentIngredient(beans);
        oven.update(10f, 10f, true, false);
        assertTrue("The beans should be instantly cooked and so the timer should be reset to 0."
                , oven.getTimer()==0f);
        assertTrue("The beans should be instantly cooked and so set as completed."
                , oven.getCurrentIngredient().isCompleted(Constants.OVEN));
        //Branch 2 The ingredient is cooked without the instant cook power up.
        completed.put(Constants.OVEN, false);
        oven.setTimer(40f);
        oven.update(10f, 10f, false, false);
        assertTrue("The beans should be cooked and so the timer should be reset to 0."
                , oven.getTimer()==0f);
        assertTrue("The beans should be cooked and so set as completed."
                , oven.getCurrentIngredient().isCompleted(Constants.OVEN));
        //Branch 3 Apply the no burn power up to the ingredient's timer.
        oven.setTimer(40f);
        oven.update(10f, 10f, false, true);
        assertTrue("The timer should be reset to 0 with the no burn power up."
                , oven.getTimer()==0f);
        //Branch 4 The ingredient is overcooked.
        oven.setTimer(100f);
        oven.update(1, 1, false, false);
        assertTrue("The ingredient should fail if it is overcooked."
                , oven.getCurrentIngredient() instanceof FailedIngredient);

        //No ingredient, there is a recipe.
        oven.setTimer(0f);
        oven.setCurrentIngredient(null);
        UncookedPizzaRecipe uncookedPizza = new UncookedPizzaRecipe();
        //Branch 1 no burn power up for the uncooked pizza.
        oven.setCurrentRecipe(uncookedPizza);
        oven.update(1, 1, true, false);
        assertTrue("The uncooked pizza should be instantly cooked and the timer set to 0."
                , oven.getTimer()==0f);
        assertTrue("The uncooked pizza should be instantly cooked and turned into a cooked pizza."
                , oven.getCurrentRecipe()instanceof CookedPizzaRecipe);
        //Branch 2 Cooking the uncooked pizza without instant cook power up.
        oven.setCurrentRecipe(uncookedPizza);
        oven.update(10, 1, false, false);
        assertTrue("The uncooked pizza should be cooked and the timer set to 0."
                , oven.getTimer()==0f);
        assertTrue("The uncooked pizza should be cooked and turned into a cooked pizza."
                , oven.getCurrentRecipe()instanceof CookedPizzaRecipe);
        //Branch 3 Applying the no burn power up to the pizza.
        oven.update(1, 1, false, true);
        assertTrue("The timer should be reset to 0 with the no burn power up.",
                oven.getTimer()==0f);
        //Branch 4 Cooked pizza is overcooked and so becomes a failed ingredient.
        oven.setTimer(1f);
        oven.update(1, 0, false, false);
        assertNull("The cooked pizza has been overcooked, turned into a failed ingredient"
                + "and so the recipe should be null.", oven.getCurrentRecipe());
        assertTrue("The cooked pizza has been overcooked and should be failed."
                , oven.getCurrentIngredient() instanceof FailedIngredient);
    }
}
