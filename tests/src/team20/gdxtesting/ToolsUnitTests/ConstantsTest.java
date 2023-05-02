package team20.gdxtesting.ToolsUnitTests;


import Tools.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ConstantsTest {

    @Test
    public void testChoppingBoardConstant(){
        assertEquals(Constants.CHOPPING_BOARD, "Chopping Board");
    }

    @Test
    public void testPanConstant(){
        assertEquals(Constants.PAN, "Pan");
    }

    @Test
    public void testOvenConstant(){
        assertEquals(Constants.OVEN, "Oven");
    }

    @Test
    public void testBurgerConstant(){
        assertEquals(Constants.BURGER, "Burger");
    }

    @Test
    public void testSaladConstant(){
        assertEquals(Constants.SALAD, "Salad");
    }

    @Test
    public void testJacket_PotatoConstant(){
        assertEquals(Constants.JACKET_POTATO, "Jacket Potato");
    }

    @Test
    public void testPizzaConstant(){
        assertEquals(Constants.PIZZA, "Pizza");
    }

    @Test
    public void testBurger_RecipeConstant(){
        assertEquals(Constants.BURGER_RECIPE, "BurgerRecipe");
    }

    @Test
    public void testSalad_RecipeConstant(){
        assertEquals(Constants.SALAD_RECIPE, "SaladRecipe");
    }

    @Test
    public void testCooked_Pizza_RecipeConstant(){
        assertEquals(Constants.COOKED_PIZZA_RECIPE, "CookedPizzaRecipe");
    }

    @Test
    public void testUncooked_Pizza_RecipeConstant(){
        assertEquals(Constants.UNCOOKED_PIZZA_RECIPE, "UncookedPizzaRecipe");
    }

    @Test
    public void testJacket_Potato_RecipeConstant(){
        assertEquals(Constants.JACKET_POTATO_RECIPE, "JacketPotatoRecipe");
    }

    @Test
    public void testBurger_Recipe_PathConstant(){
        assertEquals(Constants.BURGER_RECIPE_PATH, "Food/burger_recipe.png");
    }

    @Test
    public void testSalad_Recipe_PathConstant(){
        assertEquals(Constants.SALAD_RECIPE_PATH, "Food/salad_recipe.png");
    }
    @Test
    public void testPizza_Recipe_PathConstant(){
        assertEquals(Constants.PIZZA_RECIPE_PATH, "Food/pizza_recipe.png");
    }

    @Test
    public void testJacket_Potato_Recipe_PathConstant(){
        assertEquals(Constants.JACKET_POTATO_RECIPE_PATH, "Food/jacket_potato_recipe.png");
    }

    @Test
    public void testData_Scenario_PathConstant(){
        assertEquals(Constants.DATA_SCENARIO_PATH, "dataScenario.json");
    }

    @Test
    public void testData_Endless_PathConstant(){
        assertEquals(Constants.DATA_ENDLESS_PATH, "dataEndless.json");
    }
}
