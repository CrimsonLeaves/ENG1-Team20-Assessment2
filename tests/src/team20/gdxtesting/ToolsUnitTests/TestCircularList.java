package team20.gdxtesting.ToolsUnitTests;
import Sprites.Chef;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;
import Tools.CircularList;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)

public class TestCircularList {
    /**
     * Unit testing class. Tests our custom implementation of a circular list for switching chefs on rotation.
     */

    @Test
    public void testCircularListInit() {
        /**
         * Tests that a new instance of a circular list is instantiated correctly
         */
        CircularList cList = new CircularList<Chef>();
        assertEquals(cList.getCurrentSize(), 0);
        assertEquals(cList.allElems(), new ArrayList<>());
    }


    @Test
    public void testAddingItem() {
        /**
         * Tests adding a new item to the circular list
         */
        CircularList cList = new CircularList<Chef>();
        Chef testChef = new Chef(new World(new Vector2(0,0),true), 0, 0);
        cList.addElement(testChef);
        assertEquals(cList.getCurrentSize(), 1);
        assertEquals(cList.peekNextItem(), testChef);
    }

    @Test
    public void testRemovingItem() {
        /**
         * Tests removing an item from the circular list
         */
        CircularList cList = new CircularList<Chef>();
        Chef testChef = new Chef(new World(new Vector2(0,0),true), 0, 0);
        cList.addElement(testChef);
        cList.removeElement();
        assertEquals(cList.getCurrentSize(), 0);
        assertNull(cList.peekNextItem());
        assertTrue(cList.isEmpty());
    }

    @Test
    public void testRemovingItemEmptyList() {
        /**
         * Tests removing an item from an empty circular list
         */
        CircularList cList = new CircularList<Chef>();
        assertNull(cList.removeElement());
        assertEquals(cList.getCurrentSize(), 0);
    }

    @Test
    public void testIsEmpty() {
        /**
         * Tests functionality of the isEmpty method
         */
        CircularList cList = new CircularList<Chef>();
        assertTrue(cList.isEmpty());
        cList.addElement(new Chef(new World(new Vector2(0,0),true), 0, 0));
        assertFalse(cList.isEmpty());
    }

    @Test
    public void testNextItem() {
        /**
         * Tests functionality of the nextItem method
         */
        CircularList cList = new CircularList<Chef>();
        Chef testChef = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Chef testChef2 = new Chef(new World(new Vector2(1,0),true), 0, 0);

        cList.addElement(testChef);
        assertEquals(cList.peekNextItem(), testChef);
        cList.addElement(testChef2);
        assertEquals(cList.peekNextItem(),testChef);
        cList.nextItem();
        assertEquals(cList.peekNextItem(),testChef2);
        cList.nextItem();
        assertEquals(cList.peekNextItem(),testChef);
    }


    @Test
    public void testPeekNextItem() {
        /**
         * Tests functionality of the peekNextItem method
         */
        CircularList cList = new CircularList<Chef>();
        Chef testChef = new Chef(new World(new Vector2(0,0),true), 0, 0);
        Chef testChef2 = new Chef(new World(new Vector2(1,0),true), 0, 0);

        cList.addElement(testChef);
        assertEquals(cList.peekNextItem(), testChef);
        cList.addElement(testChef2);
        assertEquals(cList.peekNextItem(),testChef);
        cList.nextItem();
        assertEquals(cList.peekNextItem(),testChef2);
    }

    @Test
    public void testGetCurrentSize() {
        /**
         * Tests functionality of the getCurrentSize method
         */
        CircularList cList = new CircularList<Chef>();
        Chef testChef = new Chef(new World(new Vector2(0, 0), true), 0, 0);
        assertEquals(cList.getCurrentSize(), 0);
        cList.addElement(testChef);
        assertEquals(cList.getCurrentSize(),1);
        cList.addElement(testChef);
        assertEquals(cList.getCurrentSize(), 2);
        cList.removeElement();
        assertEquals(cList.getCurrentSize(), 1);
    }

    @Test
    public void testAllElems() {
        /**
         * Tests functionality of the allElems method
         */
        CircularList cList = new CircularList<Integer>();
        assertEquals(cList.allElems(), java.util.Collections.emptyList());
    }



}
