package team20.gdxtesting.ToolsUnitTests;

import Tools.Constants;
import Tools.IngredientDataStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class IngredientDataStoreTest {
    /**
     * Testing the Ingredient Store class.
     * Stores information about the Ingredient class.
     */
    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_Name(){
        //Storing the name of the ingredient/recipe.
        IngredientDataStore store = new IngredientDataStore();

        //By default, should be empty.
        assertTrue("The name by default should be empty."
                , store.getName().isEmpty());
        //Set a new name.
        store.setName("Tomato");
        assertSame("The name of the ingredient should have been set to Tomato."
                , "Tomato", store.getName());
    }
    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_Timers(){
        //Storing the timers of the ingredients.
        Map<String, Float> timers = new HashMap<>();
        timers.put(Constants.OVEN, 10f);
        IngredientDataStore ingredient = new IngredientDataStore(Constants.BURGER, timers,
                new HashMap<String, Boolean>(), 0, 5f);
        //Check the entered information.
        assertSame("The timer for the oven should be 10f.",
                ingredient.getTimers(), timers);
        assertTrue("The current timer should be 5f.",
                ingredient.getCurrentTimer()==5f);
        //Set new information.
        ingredient.setCurrentTimer(7f);
        Map<String, Float> newTimers = new HashMap<>();
        newTimers.put(Constants.PAN, 30f);
        ingredient.setTimers(newTimers);
        assertSame("The timer for the pan should be 30f.",
                ingredient.getTimers(), newTimers);
        assertTrue("The current timer should be 5f.",
                ingredient.getCurrentTimer()==7f);
    }

    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_Skin(){
        //Store and set the skin of the ingredient/recipe.
        IngredientDataStore store = new IngredientDataStore();
        store.setSkin(3);
        assertSame("The skin should be set to 3.", 3, store.getSkin());
    }
    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_Recipe(){
        //Store if the Sprite is a recipe or an ingredient.
        IngredientDataStore store = new IngredientDataStore();
        //By default, should be false
        assertFalse("The Sprite stored shouldn't be a recipe.", store.isRecipe());
        store.setRecipe(true);
        assertTrue("The Sprite should be a recipe.", store.isRecipe());

    }
    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_Completed(){
        //Store and set if ingredient is complete.
        IngredientDataStore ingredient = new IngredientDataStore();
        assertTrue("The ingredient's completeness should be empty."
                , ingredient.getCompleted().isEmpty());
        Map<String, Boolean> completed = new HashMap<>();
        completed.put(Constants.PAN, true);
        ingredient.setCompleted(completed);
        assertSame("The ingredient should have finished cooking on the pan."
                , completed, ingredient.getCompleted());
    }
}
