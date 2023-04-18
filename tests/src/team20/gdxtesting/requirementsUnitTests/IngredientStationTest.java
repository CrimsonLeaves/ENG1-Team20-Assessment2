package team20.gdxtesting.requirementsUnitTests;

import Ingredients.*;
import Sprites.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class IngredientStationTest {
    /**
     * Tests the IngredientStation Class and all of its subclasses.
     */
    
    
    @Test
    // Test Requirement: UR_STATION
    public void test_IngredientStation(){
        //Create a general ingredient station and see if it can return an ingredient.
        IngredientStation s = new IngredientStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("An ingredient station should return an ingredient.", s.getIngredient() instanceof Ingredient);

    }
    
    @Test
    // Test Requirement: UR_INGREDIENT_DOUGH/UR_STATION
    public void test_DoughStation(){
        //Create a dough station and return the dough ingredient.
        DoughStation d = new DoughStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A dough station should return dough.", d.getIngredient() instanceof Dough);
        //Test an invalid input.
        assertFalse("A dough station shouldn't return Tomatoes.", d.getIngredient() instanceof Tomato);
    }
    
    @Test
    // Test Requirement: UR_STATION
    public void test_BunsStation(){
        //Create a buns station and return the bun ingredient.
        BunsStation b = new BunsStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A buns station should return a bun.", b.getIngredient() instanceof Bun);
        assertFalse("A bun station shouldn't return dough.", b.getIngredient() instanceof Dough);
    }
    
    @Test
    // Test Requirement: UR_STATION/UR_INGREDIENT_BEANS
    public void test_BeansStation(){
        //Creates a beans station and returns the beans ingredient.
        BeansStation b = new BeansStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A beans station should return beans.", b.getIngredient() instanceof Beans);
        assertFalse("A beans station shouldn't return Buns.", b.getIngredient() instanceof Bun);
    }

    @Test
    //Test Requirement: UR_STATION/UR_INGREDIENT_CHEESE/UR_PIZZA_TOPPINGS
    public void test_CheeseStation(){
        //Creates a cheese station and returns the cheese ingredient.
        CheeseStation c = new CheeseStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A cheese station should return cheese.", c.getIngredient() instanceof  Cheese);
        assertFalse("A cheese station shouldn't return beans.", c.getIngredient() instanceof Beans);
    }
    
    @Test
    //Test Requirement: UR_STATION
    public void test_LettuceStation(){
        //Creates a lettuce station and returns the lettuce ingredient.
        LettuceStation l = new LettuceStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A lettuce station should return lettuce.", l.getIngredient() instanceof  Lettuce);
        assertFalse("A lettuce station shouldn't return cheese.", l.getIngredient() instanceof Cheese);
    }
    
    @Test
    //Test Requirement: UR_STATION
    public void test_OnionStation(){
        //Creates an onion station and returns the onion ingredient.
        OnionStation o = new OnionStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("An onion station should return onions.", o.getIngredient() instanceof  Onion);
        assertFalse("An onion station shouldn't return lettuce.", o.getIngredient() instanceof Lettuce);
    }
    /*
    @Test
    //Test Requirement: UR_STATION/UR_INGREDIENT_POTATO
    public void test_PotatoStation(){
        //Creates a potato station and returns the potato ingredient.
        PotatoStation p = new PotatoStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A potato station should return potatoes.", p.getIngredient() instanceof  Potato);
        assertFalse("A potato station shouldn't return onions.", p.getIngredient() instanceof Onion);
    }
    */
    @Test
    //Test Requirement: UR_STATION
    public void test_SteakStation(){
        //Creates a steak station and returns the steak ingredient.
        SteakStation s = new SteakStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A steak station should return steaks.", s.getIngredient() instanceof  Steak);
        assertFalse("A steak station shouldn't return potatoes.", s.getIngredient() instanceof Potato);
    }

    @Test
    //Test Requirement: UR_STATION/UR_INGREDIENT_SAUCE
    public void test_TomatoStation(){
        //Creates a tomato station and returns the tomato ingredient.

        TomatoStation t = new TomatoStation(new World(new Vector2(0, 0), true),
                new TiledMap(), new BodyDef(), new Rectangle());
        assertTrue("A tomato station should return tomatoes.", t.getIngredient() instanceof  Tomato);
        assertFalse("A tomato station shouldn't return steaks.", t.getIngredient() instanceof Potato);
    }
    
}

