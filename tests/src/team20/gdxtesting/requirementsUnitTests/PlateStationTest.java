package team20.gdxtesting.requirementsUnitTests;

import Ingredients.*;
import Recipe.BurgerRecipe;
import Recipe.JacketPotatoRecipe;
import Recipe.SaladRecipe;
import Recipe.UncookedPizzaRecipe;
import Sprites.PlateStation;
import Tools.Constants;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PlateStationTest {
    /**
     * Tests Plate Station class.
     */

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_dropItem(){
        //Add ingredients to the plate.
        PlateStation plateStation = new PlateStation(new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        Lettuce lettuce = new Lettuce(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Tomato tomato = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Onion onion = new Onion(new HashMap<String, Float>(), new HashMap<String, Boolean>());

        List<Ingredient> plate = new ArrayList<>();
        //Plate should initially be empty array list.
        assertTrue("", plateStation.getPlate().isEmpty());

        //Add one ingredient.
        plateStation.dropItem(tomato);
        plate.add(tomato);
        assertTrue("A tomato should be added to the plate."
                , plateStation.getPlate().equals(plate));
        assertNull("No recipe should be completed.", plateStation.getCompletedRecipe());

        //Add enough ingredients to create a salad.
        //(Check that the checkRecipeCreated() is being called).
        plateStation.dropItem(lettuce);
        plateStation.dropItem(onion);
        assertTrue("No ingredients should be on the plate as they should be turned into a salad.",
                plateStation.getPlate().isEmpty());
        assertTrue("A salad should be created."
                , plateStation.getCompletedRecipe() instanceof SaladRecipe);

    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_checkRecipeCreated(){
        //Check that a recipe is correctly created.
        PlateStation burgerPlate = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        PlateStation pizzaPlate = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        PlateStation jacketPotatoPlate = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        PlateStation unpreparedPlate = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        Bun bun = new Bun(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Steak steak = new Steak(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Tomato tomato = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Cheese cheese = new Cheese(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Dough dough = new Dough(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Potato potato = new Potato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Beans beans = new Beans(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        HashMap<String, Boolean> completed = new HashMap<>();
        Beans unpreparedBeans = new Beans(new HashMap<String, Float>(), completed);
        completed.put(Constants.OVEN, false);
        //Salad recipe (already tested in test_dropItem())
        //Test burger recipe
        burgerPlate.dropItem(bun);
        burgerPlate.dropItem(steak);
        assertTrue("Should have created a burger.", burgerPlate.getCompletedRecipe() instanceof BurgerRecipe);

        //Test uncooked pizza recipe
        pizzaPlate.dropItem(tomato);
        pizzaPlate.dropItem(cheese);
        pizzaPlate.dropItem(dough);
        assertTrue("Should have created a pizza.",
                pizzaPlate.getCompletedRecipe() instanceof UncookedPizzaRecipe);
        //Test jacket potato recipe
        jacketPotatoPlate.dropItem(potato);
        jacketPotatoPlate.dropItem(cheese);
        jacketPotatoPlate.dropItem(beans);
        assertTrue("Should have created a jacket potato.",
                jacketPotatoPlate.getCompletedRecipe() instanceof JacketPotatoRecipe);

        //Test not fully prepared ingredients
        unpreparedPlate.dropItem(potato);
        unpreparedPlate.dropItem(cheese);
        unpreparedPlate.dropItem(unpreparedBeans);
        assertNull("No recipe should be created as the beans ingredient isn't fully prepared."
                , unpreparedPlate.getCompletedRecipe());
    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_pickUpItem(){
        PlateStation ingredient = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        PlateStation recipe = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        Tomato tomato = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        //Pick up recipe on the plate
        recipe.setRecipeDone(new SaladRecipe());
        assertTrue("The recipe should be picked up from the plate."
                , recipe.pickUpItem() instanceof SaladRecipe);
        assertNull("The should not be a recipe on the plate."
                , recipe.getCompletedRecipe());

        //Pick up ingredients on the plate
        ingredient.dropItem(tomato);
        assertTrue("The tomato should be picked up off the plate."
                , ingredient.pickUpItem() instanceof  Tomato);
        assertTrue("There shouldn't be anything left on the plate."
                , recipe.getPlate().isEmpty());

    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_XY(){
        PlateStation plate = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        //Testing the X and Y coordinates of the plate station.
        assertTrue("By default the x position should be 0.0.",
                plate.getX()==0f);
        assertTrue("By default the y position should be 0.0",
                plate.getY()==0f);

    }

    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_setRecipeDone(){
        //Add the recipe to the plate if the game has been saved and then continued.
        PlateStation plate = new PlateStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        plate.setRecipeDone(new SaladRecipe());
        assertTrue("A salad should be put on the plate."
                , plate.getCompletedRecipe() instanceof SaladRecipe);

    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_getRecipe(){
        //Testing that a valid recipe will be returned.
        assertNull("No recipe should be returned as input is invalid."
                , PlateStation.getRecipe("invalid"));
        assertTrue("The salad recipe should be returned."
                , PlateStation.getRecipe(Constants.SALAD) instanceof SaladRecipe);
    }


}
