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
    public void testKitchenTSXExists() {
        assertTrue("This test will only pass when the Kitchen.tsx asset exists.", Gdx.files.internal("Kitchen.tsx").exists());
    }

    @Test
    public void testKitchenTileSetExists() {
        assertTrue("This test will only pass when the KitchenTileSet.tsx asset exists.", Gdx.files.internal("KitchenTileSet.tsx").exists());
    }

    @Test
    public void testStartImageExists() {
        assertTrue("This test will only pass when the startImage.png asset exists.", Gdx.files.internal("startImage.png").exists());
    }

    @Test
    public void testTile_setAssetExists() {
        assertTrue("This test will only pass when the Tile_set.png asset exists.", Gdx.files.internal("Tile_set.png").exists());
    }
    @Test
    public void testTileSetAssetExists() {
        assertTrue("This test will only pass when the TileSet.png asset exists.", Gdx.files.internal("TileSet.png").exists());
    }

}
