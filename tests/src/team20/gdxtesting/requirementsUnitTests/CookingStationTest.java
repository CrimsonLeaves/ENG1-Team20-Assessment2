package team20.gdxtesting.requirementsUnitTests;

import Ingredients.FailedIngredient;
import Ingredients.Ingredient;
import Ingredients.Tomato;
import Sprites.ChoppingBoard;
import Sprites.CookingStation;
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

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CookingStationTest {
    /**
     * Testing Cooking Station class and all of its subclasses.
     * (Excluding override methods which will be tested separately).
     * Cannot test drawProgressBar() as this isn't compatible with the headless application.
     */

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_CurrentIngredient(){
        //Testing that the Cooking station can correctly detect and change what ingredient
        //it is interacting with.
        CookingStation cookingStation = new CookingStation(new World(new Vector2(0,0),true), new TiledMap(),
                new BodyDef(), new Rectangle());
        //Initially null as no ingredient is interacting with it.
        assertNull("Cooking station has no ingredient so it should be null",
                cookingStation.getCurrentIngredient());

        //Add an ingredient to the cooking station.
        Tomato tomato = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        cookingStation.setTimer(7f);
        cookingStation.setCurrentIngredient(tomato);
        assertTrue("The tomato ingredient should be added to the cooking station",
                tomato==cookingStation.getCurrentIngredient());
        assertTrue("The timer should be set to 0, when an ingredient is added."
                , 0f==cookingStation.getTimer());

    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_Timer(){
        //Testing the timer used in the cooking station.
        CookingStation cookingStation = new CookingStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("By default the timer should equal to 0f."
                , cookingStation.getTimer()==0f);
        cookingStation.setTimer(10f);
        assertTrue("Timer should be changed to 7f"
                , cookingStation.getTimer()==10f);
    }

    @Test
    //Test Requirement: UR_FOOD_PREP
    public void test_XY(){
        //Testing the X and Y coordinates of the cooking station.
        CookingStation cookingStation = new CookingStation(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("By default the x position should be 0.0.",
                cookingStation.getX()==0f);
        assertTrue("By default the y position should be 0.0",
                cookingStation.getY()==0f);

    }

    @Test
    //Test Requirement: UR_FOOD_PREP/FR_NO_BURN_POWERUP/FR_INSTACOOK_POWERUP
    public void test_update(){
        //Testing how the state of the cooking station can change depending on:
        //time, difficulty and the instant cooking and no burn power ups.
        //Using Chopping board as it doesn't override the method.
        ChoppingBoard choppingBoard = new ChoppingBoard(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        Float timer = 100f;
        //Firstly test when current ingredient == null.
        //Nothing should change.
        choppingBoard.setTimer(timer);
        Ingredient ingredient = choppingBoard.getCurrentIngredient();
        choppingBoard.update(1f, 1f, false, false);
        assertTrue("Timer should remain the same for null ingredient for the cooking station.",
                choppingBoard.getTimer()==timer);
        assertSame("Ingredient should not be failed, if it was initially null.",
                ingredient, choppingBoard.getCurrentIngredient());

        //Ingredient isn't null but it is failed.
        //Nothing should change
        FailedIngredient failed = new FailedIngredient();
        choppingBoard.setCurrentIngredient(failed);
        choppingBoard.setTimer(timer);
        choppingBoard.update(1f, 1f, false, false);
        assertTrue("Timer should remain the same for failed ingredients for the cooking station.",
                choppingBoard.getTimer()==timer);
        assertTrue("Ingredient should remain the same, if it was initially failed.",
                failed==choppingBoard.getCurrentIngredient());

        //Ingredient isn't null and it isn't failed.

        //Testing Branch 1: (instant cook power up).
        HashMap<String, Float> timers = new HashMap<>();
        HashMap<String, Boolean> completed = new HashMap<>();
        Tomato tomato = new Tomato(timers, completed);
        timers.put(Constants.CHOPPING_BOARD, 2f);
        completed.put(Constants.CHOPPING_BOARD, false);
        choppingBoard.setCurrentIngredient(tomato);
        choppingBoard.update(0, 1, true, false);
        assertTrue("The timer should be changed to the ingredient's timer.",
                choppingBoard.getTimer()==tomato.getTimer(Constants.CHOPPING_BOARD));
        //Testing that dt is added to the timer. (The negative dt is to ensure the other
        //if statements are not called).
        Float dt = -100f;
        Float timeBefore = choppingBoard.getTimer();
        choppingBoard.update(dt, 1, false, false);
        assertTrue("The dt of -100 should be added to the current time of 2.",
                choppingBoard.getTimer()==(timeBefore+dt));

        //Testing Branch 2: (ingredient not complete)
        choppingBoard.setTimer(4f); //Greater than tomato's 2f
        choppingBoard.update(1, 1, false, false);
        assertTrue("Timer should be reset to 0.", choppingBoard.getTimer()==0);
        assertTrue("The ingredient should be completed."
                , tomato.isCompleted(Constants.CHOPPING_BOARD));

        //Testing Branch 3: (no burn power up)
        choppingBoard.setTimer(4f);
        choppingBoard.update(1, 1, false, true);
        assertTrue("", choppingBoard.getTimer()==0);

        //Testing Branch 4: (completed, over chopped and so fails)
        choppingBoard.setTimer(100f);
        choppingBoard.update(1, 1, false, false);
        System.out.print(choppingBoard.getCurrentIngredient());
        assertTrue("The ingredient should be over prepared and set to failed."
                , choppingBoard.getCurrentIngredient().getFailed());
    }

}
