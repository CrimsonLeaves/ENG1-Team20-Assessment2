package team20.gdxtesting.requirementsUnitTests;
import Ingredients.Cheese;
import Ingredients.Tomato;
import Sprites.*;
import Tools.CircularList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.Deque;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ChefTest {

    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_initialEmpty(){
        //The stack should initially be empty.
        Chef c = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Integer initial = c.getHoldingSize();
        assertTrue("Chef should initially be holding nothing.", initial.equals(0));
    }
    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_pickUp_addStackValid(){
        //Add a valid item to the stack.
        Tomato t = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Chef c = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Integer initial = c.getHoldingSize();
        c.pickUp(t);
        Integer size = c.getHoldingSize();
        assertTrue("One valid item should be added to the stack.", size.equals(1));

    }
    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_pickUp_addStackInValid(){
        //Try to add an invalid item to the stack (should produce an error).
        //Allows invalid Sprites to be added.
        //Should this be corrected?
        Chef c = new Chef(new World(new Vector2(0,0),true), 0, 0);
        c.pickUp(c);
    }
    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_pickUp_addFront(){
        //Check that the ingredient is added to the front (top) of the stack.
        Chef c = new Chef(new World(new Vector2(0,0),true), 0, 0);
        c.pickUp(new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>()));
        Cheese cheese = new Cheese(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        c.pickUp(cheese);
        //Cheese should be at the top.
        Deque<Sprite> stack = c.getStack();
        assertTrue("The last item to be added should be at the top.", cheese.equals(c.peekStack()));

    }
    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_pickUp_fullStack(){
        //Tests if nothing will be added to the stack if it is already full.
        //Create a new stack and fill it (so it contains 3 items).
        Chef c = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Tomato t = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        for (int i = 0; i<3; i++){
            c.pickUp(t);
        }
        Integer size = c.getHoldingSize();
        c.pickUp(t);
        assertTrue("Once the stack is full another item shouldn't be added.", size.equals(3));
    }
    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_putDown(){
        //Tests that it is the top item of the stack that is removed.
        Chef c = new Chef(new World(new Vector2(0,0),true), 0, 0);
        c.pickUp(new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>()));
        Sprite s = c.peekStack();
        c.pickUp(new Cheese(new HashMap<String, Float>(), new HashMap<String, Boolean>()));
        c.putDown();
        assertTrue("The top item of the stack should be removed.", s.equals(c.peekStack()));
    }
    @Test
    //Test Requirement: FR_SWITCH
    public void test_getUserControlChef(){
        //Tests if the systems knows which chef is being controlled.
        Chef c1 = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Chef c2 = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Chef c3 = new Chef(new World(new Vector2(0,0),true), 0, 0);
        CircularList<Chef> chefList = new CircularList<>(4);
        chefList.addElement(c1);
        chefList.addElement(c2);
        chefList.addElement(c3);
        assertTrue("The last chef in the list by default should be initially controlled.",
                c3.getUserControlChef());


       // assertFalse("As the last chef in the list is being controlled, the other chefs shouldn't be controlled.",
       //         c2.getUserControlChef());
    }

    @Test
    //Test Requirement: FR_INTERACT
    public void test_TouchingTile() {
        //If the chef is touching nothing it should return null.
        Chef c1 = new Chef(new World(new Vector2(0,0),true), 0, 0);
        assertTrue("If the chef isn't touching anything it should return null.",
                c1.getTouchingTile()==null);
        //If the chef is touching a tile, it should return that tile.
        IngredientStation i = new IngredientStation(new World(new Vector2(1,1),true), new TiledMap(), new BodyDef(), new Rectangle());
        c1.setX(1);
        c1.setY(1);
        c1.update(0);
        assertTrue(c1.getTouchingTile()==null);
        //Should be failing, obviously not colliding properly.
    }
    //Need a test to deal with Chefs colliding with each other.
}
