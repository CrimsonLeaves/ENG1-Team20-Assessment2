package team20.gdxtesting.AssetTests;
import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PowerupsAssetTests {

    @Test
    public void testChefPowerupExists() {
        assertTrue("This test will only pass when the chefPowerup.png asset exists.", Gdx.files.internal("Powerups/chefPowerup.png").exists());
    }
    @Test
    public void testInstantCookPowerupExists() {
        assertTrue("This test will only pass when the instantCookPowerup.png asset exists.", Gdx.files.internal("Powerups/instantCookPowerup.png").exists());
    }

    @Test
    public void testNoBurnPowerupExists() {
        assertTrue("This test will only pass when the noBurnPowerup.png asset exists.", Gdx.files.internal("Powerups/noBurnPowerup.png").exists());
    }

    @Test
    public void testResetPowerupExists() {
        assertTrue("This test will only pass when the resetPowerup.png asset exists.", Gdx.files.internal("Powerups/resetPowerup.png").exists());
    }

    @Test
    public void testSpeedPowerupExists() {
        assertTrue("This test will only pass when the speedPowerup.png asset exists.", Gdx.files.internal("Powerups/speedPowerup.png").exists());
    }


}



