package team20.gdxtesting.AssetTests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)

public class SkinAssetTests {
    @Test
    public void testFontBigExportExists() {
        assertTrue("This test will only pass when the font-big-export.fnt asset exists.", Gdx.files.internal("skin/font-big-export.fnt").exists());
    }

    @Test
    public void testFontExportExists() {
        assertTrue("This test will only pass when the font-export.fnt asset exists.", Gdx.files.internal("skin/font-export.fnt").exists());
    }

    @Test
    public void testGlassyUIAtlasExists() {
        assertTrue("This test will only pass when the glassy-ui.atlas asset exists.", Gdx.files.internal("skin/glassy-ui.atlas").exists());
    }

    @Test
    public void testGlassyUIJsonExists() {
        assertTrue("This test will only pass when the glassy-ui.json asset exists.", Gdx.files.internal("skin/glassy-ui.json").exists());
    }

    @Test
    public void testGlassyUIPngExists() {
        assertTrue("This test will only pass when the glassy-ui.png asset exists.", Gdx.files.internal("skin/glassy-ui.png").exists());
    }



}
