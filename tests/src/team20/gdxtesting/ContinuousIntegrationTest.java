package team20.gdxtesting;
import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)


public class ContinuousIntegrationTest {

    @Test
    public void testContinuousIntegration() {
        assertTrue("This test will always fail", Gdx.files.internal("nothing.jpg").exists());
    }

}
