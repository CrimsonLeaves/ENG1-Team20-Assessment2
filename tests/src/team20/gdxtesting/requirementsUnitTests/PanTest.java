package team20.gdxtesting.requirementsUnitTests;

import Ingredients.FailedIngredient;
import Ingredients.Tomato;
import Sprites.Pan;
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

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PanTest {
    /**
     * Testing the update class for Pan as it overrides its parent class Cooking Station.
     */
    @Test
    //Test Requirement: UR_FOOD_PREP/FR_NO_BURN_POWERUP/FR_INSTACOOK_POWERUP
    public void test_update(){
        //Testing how the state of the pan can change depending on:
        //time, difficulty and the instant cooking and no burn power ups.

        Pan pan = new Pan(new World(new Vector2(0,0),true)
                , new TiledMap(), new BodyDef(), new Rectangle());
        Float timer = 100f;

        //Current ingredient is null, nothing should change.
        pan.setTimer(timer);
        pan.update(1, 1, false, false);
        //Failure cannot be tested as ingredient is null.
        assertTrue("Timer should remain the same for null ingredient for the pan.",
                pan.getTimer()==timer);

        //Ingredient isn't null but it is failed; nothing should change.
        FailedIngredient failed = new FailedIngredient();
        pan.setCurrentIngredient(failed);
        pan.setTimer(timer);
        pan.update(1f, 1f, false, false);
        assertTrue("Timer should remain the same for failed ingredients for the pan.",
                pan.getTimer()==timer);
        assertTrue("Ingredient should remain the same, if it was initially failed.",
                failed==pan.getCurrentIngredient());

        //Ingredient isn't null and it isn't failed.

        //Testing Branch 1: (instant cook power up).
        HashMap<String, Float> timers = new HashMap<>();
        HashMap<String, Boolean> completed = new HashMap<>();
        Tomato tomato = new Tomato(timers, completed);
        timers.put(Constants.PAN, 2f);
        completed.put(Constants.PAN, false);
        pan.setCurrentIngredient(tomato);
        pan.update(0, 1, true, false);
        assertTrue("The timer should be changed to the ingredient's timer.",
                pan.getTimer()==tomato.getTimer(Constants.PAN));
        //Testing that dt is added to the timer. (The negative dt is to ensure the other
        //if statements are not called).
        Float dt = -100f;
        Float timeBefore = pan.getTimer();
        pan.update(dt, 1, false, false);
        assertTrue("The dt of -100 should be added to the current time of 2.",
                pan.getTimer()==(timeBefore+dt));

        //Testing Branch 2: (ingredient not complete)
        pan.setTimer(4f); //Greater than tomato's 2f
        pan.update(1, 1, false, false);
        assertTrue("Timer should be reset to 0.", pan.getTimer()==0);
        assertTrue("The ingredient should be completed."
                , tomato.isCompleted(Constants.PAN));

        //Testing Branch 3: (no burn power up)
        pan.setTimer(4f);
        pan.update(1, 1, false, true);
        assertTrue("", pan.getTimer()==0);

        //Testing Branch 4: (completed, over chopped and so fails)
        pan.setTimer(100f);
        pan.update(1, 1, false, false);
        assertTrue("The ingredient should be over prepared and set to failed."
                , pan.getCurrentIngredient().getFailed());

    }

}
