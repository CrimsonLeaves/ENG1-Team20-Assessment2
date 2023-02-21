import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testTileSetAssetExists() {
        assertTrue("This test will only pass when the TileSet.png asset exists.", Gdx.files.internal("TileSet.png").exists());
    }
}
