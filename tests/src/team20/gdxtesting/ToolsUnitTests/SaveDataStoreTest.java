package team20.gdxtesting.ToolsUnitTests;

import Tools.ChefDataStore;
import Tools.OrderDataStore;
import Tools.SaveDataStore;
import com.team13.piazzapanic.Reputation;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class SaveDataStoreTest {
    /**
     * Testing the SaveDataStore class.
     * Stores the chef, reputation, time, difficulty, score, orders and stations.
     */
    @Test
    //Test Requirement: UR_SAVE_GAME/UR_CONTINUE_GAME
    public void test_ChefDataStore(){
        //As only setters are used, the test will check if the data is correctly stored.
        ArrayList<ChefDataStore> chefStore = new ArrayList<>();
        Reputation rep = new Reputation();
        OrderDataStore orderStore = new OrderDataStore();
        SaveDataStore store = new SaveDataStore(chefStore, 5, 2f,
                false, 10f, 15f, 3, 2, rep,
                orderStore, null);

        assertSame(store.getChefData(), chefStore);
        assertTrue(store.getDiffMult()==2f);
        assertSame(store.getOrderCount(), 5);
        assertFalse(store.getCreatedOrder());
        assertTrue(store.getTimeSeconds()==10f);
        assertTrue(store.getTimeSecondsCount()==15f);
        assertSame(store.getChefCount(), 3);
        assertSame(store.getScore(), 2);
        assertSame(rep, store.getRep());
        assertSame(orderStore, store.getOrder());
        assertNull(store.getStationItems());
    }
}
