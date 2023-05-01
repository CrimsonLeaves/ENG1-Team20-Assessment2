package team20.gdxtesting.requirementsUnitTests;
import Ingredients.Cheese;
import Ingredients.Tomato;
import Sprites.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ChefTest {
    /**
     * Testing the Chef class.
     */
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
        assertTrue("The last item to be added should be at the top.", cheese.equals(c.peekStack()));

    }
    @Test
    //Test requirement: UR_CARRYING_STACK
    public void test_pickUp_fullStack(){
        //Tests if nothing will be added to the stack if it is already full.
        //Create a new stack and fill it (so it contains 3 items).
        Chef chef = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Tomato t = new Tomato(new HashMap<String, Float>(), new HashMap<String, Boolean>());
        Deque<Sprite> stack = new ArrayDeque<>();
        for (int i = 0; i<3; i++){
            stack.add(t);
        }
        chef.setStack(stack);
        Integer size = chef.getHoldingSize();
        chef.pickUp(t);
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
    //Test Requirement: FR_INTERACT
    public void test_chefsColliding(){
        //Tests that the chef updates its self when colliding with another chef.
        Chef chef = new Chef(new World(new Vector2(0,0),true), 0, 0);
        //Before colliding
        assertTrue("The user should be able to control the chef if it isn't colliding."
                , chef.getUserControlChef());
        assertFalse("The chef shouldn't be set as colliding."
                , chef.chefOnChefCollision);
        //After colliding.
        chef.chefsColliding();
        assertFalse("The user shouldn't be able to control the chef if it is colliding."
                , chef.getUserControlChef());
        assertTrue("The chef should be set as colliding."
                , chef.chefOnChefCollision);

    }

    @Test
    //Test Requirement: FR_INTERACT
    public void test_TouchingTile() {
        //If the chef is touching nothing it should return null.
        Chef c1 = new Chef(new World(new Vector2(0,0),true), 0, 0);
        assertTrue("If the chef isn't touching anything it should return null.",
                c1.getTouchingTile()==null);

    }
}
