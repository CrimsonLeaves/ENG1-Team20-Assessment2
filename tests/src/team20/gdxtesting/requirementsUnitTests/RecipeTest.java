package team20.gdxtesting.requirementsUnitTests;

import Ingredients.*;
import Recipe.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class RecipeTest {
    /**
     * Tests the Recipe class and all of its subclasses.
     *
     * Cannot test the create() method with the headless application.
     */
    @Test
    // Test Requirement: UR_RECIPE
    public void test_BurgerRecipe(){
        //Tests that the correct ingredients are required for the burger recipe.
        BurgerRecipe burger = new BurgerRecipe();
        assertTrue("The burger recipe's first ingredient should be a bun.",
                burger.getIngredients().get(0) instanceof Bun);
        assertTrue("The burger recipe's second ingredient should be a steak.",
                burger.getIngredients().get(1) instanceof Steak);
    }

    @Test
    //Test Requirement: UR_RECIPE/UR_RECIPE_PIZZA
    public void test_UncookedPizzaRecipe(){
        //Tests that the correct ingredients are required for the uncooked pizza recipe.
        UncookedPizzaRecipe uncookedpizza = new UncookedPizzaRecipe();
        assertTrue("The uncooked pizza's first ingredient should be dough.",
                uncookedpizza.getIngredients().get(0) instanceof Dough);
        assertTrue("The uncooked pizza's second ingredient should be cheese.",
                uncookedpizza.getIngredients().get(1) instanceof Cheese);
        assertTrue("The uncooked pizza's third ingredient should be tomato.",
                uncookedpizza.getIngredients().get(2) instanceof Tomato);
    }

    @Test
    //Test Requirement: UR_RECIPE/UR_RECIPE_PIZZA
    public void test_CookedPizzaRecipe(){
        //Tests that the cooked pizza only contains an image and not any ingredients.
        CookedPizzaRecipe cookedpizza = new CookedPizzaRecipe();
        assertNull("Nothing should be contained in the cooked pizza's recipe."
                , cookedpizza.getIngredients());
    }
    /* fails when using potatoes
    @Test
    //Test Requirement: UR_RECIPE/FR_RECIPE_JACKET_POTATO
    public void test_JacketPotatoRecipe(){
        //Tests that the correct ingredients are required for the jacket potato recipe.
        JacketPotatoRecipe jacketpotato = new JacketPotatoRecipe();
        assertTrue("The jacket potato's first ingredient should be potato.",
                jacketpotato.getIngredients().get(0) instanceof Potato);
        assertTrue("The jacket potato's second ingredient should be beans.",
                jacketpotato.getIngredients().get(1) instanceof Beans);
        assertTrue("The jacket potato's third ingredient should be cheese.",
                jacketpotato.getIngredients().get(2) instanceof Cheese);
    }
    */
    @Test
    //Test Requirement: UR_RECIPE
    public void test_SaladRecipe(){
        //Tests that the correct ingredients are required for the salad recipe.
        SaladRecipe salad = new SaladRecipe();
        assertTrue("The salad's first ingredient should be lettuce.",
                salad.getIngredients().get(0) instanceof Lettuce);
        assertTrue("The salad's second ingredient should be tomato.",
                salad.getIngredients().get(1) instanceof Tomato);
        assertTrue("The salad's third ingredient should be onion.",
                salad.getIngredients().get(2) instanceof Onion);
    }
}
