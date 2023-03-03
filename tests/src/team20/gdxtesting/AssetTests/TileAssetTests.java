package team20.gdxtesting.AssetTests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TileAssetTests {

    @Test
    public void testKitchenTMXExists() {
        assertTrue("This test will only pass when the Kitchen.tmx asset exists.", Gdx.files.internal("Kitchen.tmx").exists());
    }

    @Test
    public void testKitchenTilesTSXExists() {
        assertTrue("This test will only pass when the Kitchen.tsx asset exists.", Gdx.files.internal("kitchenTiles.tsx").exists());
    }

    @Test
    public void testStartImageExists() {
        assertTrue("This test will only pass when the startImage.png asset exists.", Gdx.files.internal("startImage.png").exists());
    }

    @Test
    public void testTilesAssetExists() {
        assertTrue("This test will only pass when the Tiles.png asset exists.", Gdx.files.internal("Tiles.png").exists());
    }

}
