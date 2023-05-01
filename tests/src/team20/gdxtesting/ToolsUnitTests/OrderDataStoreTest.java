package team20.gdxtesting.ToolsUnitTests;

import Tools.Constants;
import Tools.OrderDataStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class OrderDataStoreTest {
    /**
     * Tests the OrderDataStore Class.
     * Stores all the orders.
     */

    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_OrderDataStore(){
        //Testing all the getters as the setters are never used.
        OrderDataStore orderStore = new OrderDataStore(Constants.SALAD, 10, 3f);
        assertSame(orderStore.getOrderType(), Constants.SALAD);
        assertTrue(orderStore.getDiff()==3f);
        assertSame(orderStore.getStartTime(), 10);

    }
}
