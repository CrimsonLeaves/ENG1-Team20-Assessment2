package team20.gdxtesting.requirementsUnitTests;

import Ingredients.*;
import Tools.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class IngredientTest {
    /**
     * Testing the ingredient class and all of its subclasses.
     *
     * Won't be able to test create() as it isn't compatible with the headless application.
     */

    @Test
    //Test requirement: FR_PREP_FAIL/UR_INGREDIENT_DOUGH/UR_INGREDIENT_BEANS/UR_INGREDIENT_CHEESE
    //                  UR_INGREDIENT_POTATO
    public void test_Failed(){
        //Tests if ingredients are failing when they should.

        //Failed Ingredient should be initially set to failed=true.
        FailedIngredient fail = new FailedIngredient();
        assertTrue("The failed ingredient should be initially set to failed=true.", fail.getFailed());

        //All other ingredients should be initially set to failed=false.
        Beans beans = new Beans(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The beans ingredient should be initially set to failed=false",
                beans.getFailed());
        Bun bun = new Bun(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The bun ingredient should be initially set to failed=false",
                bun.getFailed());
        Cheese cheese = new Cheese(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The cheese ingredient should be initially set to failed=false",
                cheese.getFailed());
        Dough dough = new Dough(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The dough ingredient should be initially set to failed=false",
                dough.getFailed());
        Lettuce lettuce = new Lettuce(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The lettuce ingredient should be initially set to failed=false",
                lettuce.getFailed());
        Onion onion = new Onion(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The onion ingredient should be initially set to failed=false",
                onion.getFailed());
        Potato potato = new Potato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The potato ingredient should be initially set to failed=false",
                potato.getFailed());
        Steak steak = new Steak(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The steak ingredient should be initially set to failed=false",
                steak.getFailed());
        Tomato tomato = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        assertFalse("The tomato ingredient should be initially set to failed=false",
                tomato.getFailed());
    }

    @Test
    //Test Requirement: UR_FOOD_PREP/UR_INGREDIENT_POTATO
    public void test_Skin(){
        //Tests if the food ingredient is correctly displayed as prepared or unprepared.
        Potato potato = new Potato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        //Tests if the ingredient is initially displayed as unprepared.
        assertTrue("The potato ingredient should be initially displayed as unprepared " +
                "(skin=0).",potato.getSkin()==0);
        //Tests if the ingredient can instead be displayed as prepared by changing the image.
        potato.setSkin(1);
        assertTrue("The potato skin should be set to 1", potato.getSkin()==1);
    }
    @Test
    //Test Requirement: UR_FOOD_PREP/UR_INGREDIENT_BEANS


    //need to add empty or incorrect station
    public void test_Completed(){
        //Testing that a food ingredient can change from unprepared to prepared.
        HashMap<String, Boolean> completed = new HashMap<>();
        Beans beans = new Beans(new HashMap<String, Float>(), completed);

        //Testing when completed doesn't contain any stations.
        assertNull("Pan isn't in the dictionary so should return null",
                beans.isCompleted(Constants.PAN));

        assertTrue("There should be no stations so an empty dictionary returned",
                beans.getCompleted()==completed);
        assertTrue("All are completed as there aren't any stations to be incomplete.",
                beans.isAllCompleted());

        completed.put(Constants.PAN, true);
        completed.put(Constants.OVEN, false);
        completed.put(Constants.CHOPPING_BOARD, false);

        //Tests a station's state of completeness.
        assertFalse("The oven should be incomplete as it was set to false.",
                beans.isCompleted(Constants.OVEN));
        assertTrue("The pan should be complete as it was set to true.",
                beans.isCompleted(Constants.PAN));

        //Tests if a station and ingredient can be set to complete,
        //as the ingredient has been prepared.
        beans.setCompleted(Constants.OVEN);
        assertTrue("The beans ingredient is prepared so it should be displayed as cooked " +
                "(skin=1).", beans.getSkin()==1);
        assertTrue("The station should now be considered completed (station = true)",
                beans.isCompleted(Constants.OVEN));

        //Tests if the game knows which stations are completed.
        assertTrue("The pan and oven should be complete but the chopping board incomplete"
                , beans.getCompleted()==completed);

        //Test if all stations are complete, meaning that the ingredient is fully prepared.
        assertFalse("The chopping board isn't complete so not all are completed",
                beans.isAllCompleted());
        beans.setCompleted(Constants.CHOPPING_BOARD);
        assertTrue("All of the stations should be set to complete (true)",
                beans.isAllCompleted());
    }

    @Test
    //Test Requirement: UR_TIME/UR_INGREDIENT_CHEESE
    public void test_Timer(){
        //Testing that the time taken for the ingredient to be prepared can be stored.
        HashMap<String, Float> timers = new HashMap<>();
        Cheese cheese = new Cheese(timers, new HashMap<String, Boolean>());

        //Test when timers doesn't contain the station, null values are returned.
        assertNull("Pan isn't in the dictionary so must return null"
                , cheese.getTimer(Constants.PAN));
        //Test empty dictionary is returned.
        assertTrue("An empty HashMap should be returned.",
                cheese.getTimers()==timers);

        timers.put(Constants.PAN, 0f);
        timers.put(Constants.CHOPPING_BOARD, 5f);
        //Test that the timer is returned when there is a station.
        assertTrue("Pan's timer should be 0f.", cheese.getTimer(Constants.PAN)==0f);
        //Test that all the timers are returned.
        assertTrue("Should return HashMap timers containing pan and chopping board.",
                cheese.getTimers()==timers);
    }

}
