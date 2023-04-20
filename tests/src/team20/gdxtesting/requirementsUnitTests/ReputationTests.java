package team20.gdxtesting.requirementsUnitTests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;
import com.team13.piazzapanic.Reputation;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ReputationTests {

    @Test
    public void testReputationInit() {
        int maxRep = 3;
        Reputation reput = new Reputation(maxRep);
        assertEquals("This test will pass when the reputation method has been initialised correctly", reput.getRep(), maxRep);
    }


    @Test
    public void testReputationLoss() {
        int maxRep = 3;
        Reputation reput = new Reputation(maxRep);
        reput.loseRep();
        assertEquals("This test will pass when reputation is lost correctly", reput.getRep(), maxRep - 1);
    }

    @Test
    public void testReputationReset() {
        int maxRep = 3;
        Reputation reput = new Reputation(maxRep);
        reput.loseRep();
        reput.reset();
        assertEquals("This test will pass when reputation is reset correctly", reput.getRep(), maxRep);
    }


    @Test
    public void testReputationGameLoss() {
        int maxRep = 3;
        Reputation reput = new Reputation(maxRep);
        reput.loseRep();
        reput.loseRep();
        assertTrue("This test will pass if the game ends correctly when reputation reaches 0", reput.loseRep());
    }

}
