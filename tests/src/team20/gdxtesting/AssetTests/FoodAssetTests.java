package team20.gdxtesting.AssetTests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class FoodAssetTests {

    @Test
    public void testBaked_PotatoPngExists() {
        assertTrue("This test will only pass when the Baked_Potato.png asset exists.", Gdx.files.internal("Food/Baked_Potato.png").exists());
    }

    @Test
    public void testBeansPngExists() {
        assertTrue("This test will only pass when the beans.png asset exists.", Gdx.files.internal("Food/beans.png").exists());
    }

    @Test
    public void testBurgerPngExists() {
        assertTrue("This test will only pass when the Burger.png asset exists.", Gdx.files.internal("Food/Burger.png").exists());
    }

    @Test
    public void testBurger_bunsPngExists() {
        assertTrue("This test will only pass when the Burger_buns.png asset exists.", Gdx.files.internal("Food/Burger_buns.png").exists());
    }

    @Test
    public void testBurger_recipePngExists() {
        assertTrue("This test will only pass when the burger_recipe.png asset exists.", Gdx.files.internal("Food/burger_recipe.png").exists());
    }

    @Test
    public void testCheesePngExists() {
        assertTrue("This test will only pass when the cheese.png asset exists.", Gdx.files.internal("Food/cheese.png").exists());
    }

    @Test
    public void testChopped_cheesePngExists() {
        assertTrue("This test will only pass when the Chopped_cheese.png asset exists.", Gdx.files.internal("Food/Chopped_cheese.png").exists());
    }

    @Test
    public void testChopped_lettucePngExists() {
        assertTrue("This test will only pass when the Chopped_lettuce.png asset exists.", Gdx.files.internal("Food/Chopped_lettuce.png").exists());
    }

    @Test
    public void testChopped_onionPngExists() {
        assertTrue("This test will only pass when the Chopped_onion.png asset exists.", Gdx.files.internal("Food/Chopped_onion.png").exists());
    }

    @Test
    public void testChopped_tomatoPngExists() {
        assertTrue("This test will only pass when the Chopped_tomato.png asset exists.", Gdx.files.internal("Food/Chopped_tomato.png").exists());
    }

    @Test
    public void testCooked_beansPngExists() {
        assertTrue("This test will only pass when the Cooked_beans.png asset exists.", Gdx.files.internal("Food/Cooked_beans.png").exists());
    }

    @Test
    public void testCooked_pattyPngExists() {
        assertTrue("This test will only pass when the Cooked_patty.png asset exists.", Gdx.files.internal("Food/Cooked_patty.png").exists());
    }

    @Test
    public void testCooked_pizzaPngExists() {
        assertTrue("This test will only pass when the Cooked_pizza.png asset exists.", Gdx.files.internal("Food/Cooked_pizza.png").exists());
    }

    @Test
    public void testDoughPngExists() {
        assertTrue("This test will only pass when the dough.png asset exists.", Gdx.files.internal("Food/dough.png").exists());
    }

    @Test
    public void testFailed_ingredientPngExists() {
        assertTrue("This test will only pass when the Failed_ingredient.png asset exists.", Gdx.files.internal("Food/Failed_ingredient.png").exists());
    }

    @Test
    public void testJacket_PotatoPngExists() {
        assertTrue("This test will only pass when the Jacket_Potato.png asset exists.", Gdx.files.internal("Food/Jacket_Potato.png").exists());
    }

    @Test
    public void testLettucePngExists() {
        assertTrue("This test will only pass when the Lettuce.png asset exists.", Gdx.files.internal("Food/Lettuce.png").exists());
    }

    @Test
    public void testMeatPngExists() {
        assertTrue("This test will only pass when the Meat.png asset exists.", Gdx.files.internal("Food/Meat.png").exists());
    }

    @Test
    public void testOnionPngExists() {
        assertTrue("This test will only pass when the Onion.png asset exists.", Gdx.files.internal("Food/Onion.png").exists());
    }

    @Test
    public void testPattyPngExists() {
        assertTrue("This test will only pass when the Patty.png asset exists.", Gdx.files.internal("Food/Patty.png").exists());
    }

    @Test
    public void testPizzaPngExists() {
        assertTrue("This test will only pass when the pizza.png asset exists.", Gdx.files.internal("Food/pizza.png").exists());
    }

    @Test
    public void testPizzaRecipePngExists() {
        assertTrue("This test will only pass when the pizza_recipe.png asset exists.", Gdx.files.internal("Food/pizza_recipe.png").exists());
    }

    @Test
    public void testPotatoPngExists() {
        assertTrue("This test will only pass when the Potato.png asset exists.", Gdx.files.internal("Food/Potato.png").exists());
    }

    @Test
    public void testSaladPngExists() {
        assertTrue("This test will only pass when the Salad.png asset exists.", Gdx.files.internal("Food/Salad.png").exists());
    }

    @Test
    public void testSalad_recipePngExists() {
        assertTrue("This test will only pass when the salad_recipe.png asset exists.", Gdx.files.internal("Food/salad_recipe.png").exists());
    }

    @Test
    public void testToasted_burger_bunsPngExists() {
        assertTrue("This test will only pass when the Toasted_burger_buns.png asset exists.", Gdx.files.internal("Food/Toasted_burger_buns.png").exists());
    }

    @Test
    public void testTomatoPngExists() {
        assertTrue("This test will only pass when the Tomato.png asset exists.", Gdx.files.internal("Food/Tomato.png").exists());
    }
}
