package team20.gdxtesting.ToolsUnitTests;

import Tools.SaveDataStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

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
        SaveDataStore store = new SaveDataStore();
        assertTrue(store.getChefData().isEmpty());
        assertTrue(store.getDiffMult()==1f);
        assertSame(store.getOrderCount(), 0);
        assertFalse(store.getCreatedOrder());
        assertTrue(store.getTimeSeconds()==0f);
        assertTrue(store.getTimeSecondsCount()==0f);
        assertSame(store.getChefCount(), 3);
        assertSame(store.getScore(), 0);


        assertNull(store.getStationItems());
    }
}
