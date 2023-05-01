package team20.gdxtesting.ToolsUnitTests;

import Tools.Constants;
import Tools.IngredientDataStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ChefDataStore {
    /**
     * Testing the ChefDataStore class, which stores the chef's data when the game is saved.
     */
    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_ChefDataStore(){
        //Store the x and y coordinates of the chef and what it is holding.
        ArrayList<IngredientDataStore> ingredientDataStores = new ArrayList<>();
        ingredientDataStores.add(new IngredientDataStore(Constants.BURGER));
        Tools.ChefDataStore store = new Tools.ChefDataStore(10, 20, ingredientDataStores);
        assertTrue("The x coordinate should be stored as 10.",
                store.getX()==10);
        assertTrue("The y coordinate should be stored as 20.",
                store.getY()==20);
        assertSame("The ingredient stack should hold a burger.",
                store.getHolding(), ingredientDataStores);
    }

    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_setXYHolding(){
        //Set x and y coordinates and what the chef is holding.
        Tools.ChefDataStore store = new Tools.ChefDataStore();
        ArrayList<IngredientDataStore> ingredientDataStores = new ArrayList<>();
        ingredientDataStores.add(new IngredientDataStore(Constants.JACKET_POTATO));
        store.setX(100);
        store.setY(200);
        store.setHolding(ingredientDataStores);
        assertTrue("The x coordinate should be set to 100.", store.getX()==100);
        assertTrue("The y coordinate should be set to 200.", store.getY()==200);
        assertSame("The chef store should be storing a jacket potato.",
                ingredientDataStores, store.getHolding());
    }

}
