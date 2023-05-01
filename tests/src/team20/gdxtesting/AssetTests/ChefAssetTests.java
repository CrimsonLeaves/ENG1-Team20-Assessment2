package team20.gdxtesting.AssetTests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)

public class ChefAssetTests {

    @Test
    public void testChef_normalPngExists() {
        assertTrue("This test will only pass when the Chef_normal.png asset exists.", Gdx.files.internal("Chef/Chef_normal.png").exists());
    }
    @Test
    public void testChefIdentifierPngExists() {
        assertTrue("This test will only pass when the chefIdentifier.png asset exists.", Gdx.files.internal("Chef/chefIdentifier.png").exists());
    }
}
