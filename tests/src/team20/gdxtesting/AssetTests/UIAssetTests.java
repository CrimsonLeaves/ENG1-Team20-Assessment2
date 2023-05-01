package team20.gdxtesting.AssetTests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import team20.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class UIAssetTests {

    @Test
    public void testBackgroundExists() {
        assertTrue("This test will only pass when the background.png asset exists.", Gdx.files.internal("UI/background.png").exists());
    }
    @Test
    public void testChefLockedExists() {
        assertTrue("This test will only pass when the chefLocked.png asset exists.", Gdx.files.internal("UI/chefLocked.png").exists());
    }
    @Test
    public void testChefNotUsedExists() {
        assertTrue("This test will only pass when the chefNotUsed.png asset exists.", Gdx.files.internal("UI/chefNotUsed.png").exists());
    }

    @Test
    public void testChefShopExists() {
        assertTrue("This test will only pass when the chefShop.png asset exists.", Gdx.files.internal("UI/chefShop.png").exists());
    }

    @Test
    public void testChefUsedExists() {
        assertTrue("This test will only pass when the chefUsed.png asset exists.", Gdx.files.internal("UI/chefUsed.png").exists());
    }

    @Test
    public void testEndlessWinImageExists() {
        assertTrue("This test will only pass when the endlessWinImage.png asset exists.", Gdx.files.internal("UI/endlessWinImage.png").exists());
    }

    @Test
    public void testHeartExists() {
        assertTrue("This test will only pass when the heart.png asset exists.", Gdx.files.internal("UI/heart.png").exists());
    }

    @Test
    public void testLossImageExists() {
        assertTrue("This test will only pass when the lossImage.png asset exists.", Gdx.files.internal("UI/lossImage.png").exists());
    }

    @Test
    public void testMinusArrowExists() {
        assertTrue("This test will only pass when the minusArrow.png asset exists.", Gdx.files.internal("UI/minusArrow.png").exists());
    }

    @Test
    public void testPlusArrowExists() {
        assertTrue("This test will only pass when the plusArrow.png asset exists.", Gdx.files.internal("UI/plusArrow.png").exists());
    }

    @Test
    public void testProgBarBackgroundExists() {
        assertTrue("This test will only pass when the progBarBackground.png asset exists.", Gdx.files.internal("UI/progBarBackground.png").exists());
    }

    @Test
    public void testProgBarBurnExists() {
        assertTrue("This test will only pass when the progBarBurn.png asset exists.", Gdx.files.internal("UI/progBarBurn.png").exists());
    }

    @Test
    public void testProgBarMainExists() {
        assertTrue("This test will only pass when the progBarMain.png asset exists.", Gdx.files.internal("UI/progBarMain.png").exists());
    }

    @Test
    public void testScenarioWinImageExists() {
        assertTrue("This test will only pass when the scenarioWinImage.png asset exists.", Gdx.files.internal("UI/scenarioWinImage.png").exists());
    }

    @Test
    public void testTitleTextExists() {
        assertTrue("This test will only pass when the TitleText.png asset exists.", Gdx.files.internal("UI/TitleText.png").exists());
    }

}
