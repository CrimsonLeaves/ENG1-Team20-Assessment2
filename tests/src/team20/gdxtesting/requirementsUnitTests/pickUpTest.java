package team20.gdxtesting.requirementsUnitTests;
import Sprites.TomatoStation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.ArrayDeque;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
//Test requirement: CARRYING_STACK
//Specifically testing if the ingredients can be added to the stack.
public class pickUpTest {
    @Test
    public void test_fullStack(){
        //Tests if nothing will be added to the stack if it is already full.
        //Create a new stack and fill it (so it contains 3 items).
        Chef c = new Chef(new World(new Vector2(0, 0), true), 0, 0);


    }
    @Test
    public void test_addStack(){
        //Add a valid item to the stack.
    }
    @Test
    public void test_invalidAddStack(){
        //Try to add an invalid item to the stack (should produce an error).
    }
}
