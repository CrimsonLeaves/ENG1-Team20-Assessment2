package team20.gdxtesting.requirementsUnitTests;

import Recipe.SaladRecipe;
import Sprites.CompletedDishStation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class CompletedDishStationTest {
    /**
     * Testing the CompletedDishStation.
     *
     * Cannot test draw() as it isn't compatible with the headless application.
     */

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_Recipe(){
        //Add a recipe to the plate
        SaladRecipe saladRecipe = new SaladRecipe();
        CompletedDishStation completedDishStation = new CompletedDishStation(
                new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        //Initially null
        assertNull("No recipe has been added so the recipe should be null.",
                completedDishStation.getRecipe());

        //Add to plate.
        completedDishStation.setRecipe(saladRecipe);
        assertTrue("The salad recipe should be added to the plate."
                , completedDishStation.getRecipe()==saladRecipe);
    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public  void test_XY(){
        //Get the coordinates of the completed dish station.
        CompletedDishStation completedDishStation = new CompletedDishStation(
                new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        //The default should be (0, 0).
        assertTrue("X coordinate should be 0.", completedDishStation.getX()==0f);
        assertTrue("Y coordinate should be 0.", completedDishStation.getY()==0f);
    }
}
