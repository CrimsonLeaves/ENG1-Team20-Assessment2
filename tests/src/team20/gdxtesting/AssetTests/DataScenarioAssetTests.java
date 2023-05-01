package team20.gdxtesting.AssetTests;
import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class DataScenarioAssetTests {
    @Test
    public void testDataScenarioExists() {
        assertTrue("This test will only pass when the dataScenario.json asset exists.", Gdx.files.internal("dataScenario.json").exists());
    }
}
