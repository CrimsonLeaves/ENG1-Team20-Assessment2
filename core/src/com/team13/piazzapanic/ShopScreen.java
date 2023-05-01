package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ShopScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final Image chefImage;
    private final Image currentChefSelected;
    private final Image notChefSelected;
    private final Texture chefNotUsed;
    private final Texture chefUsed;
    private final Texture chefLocked;
    private final Texture minusTexture;
    private final Texture plusTexture;
    Skin skin;

    /**
     * Constructor for LoseScreen.
     *
     * @param game the game object.
     */
    public ShopScreen(MainGame game) {
        backgroundImage = new Texture("UI/background.png");
        backgroundSprite = new Sprite(backgroundImage);
        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        chefImage = new Image(new Texture("UI/chefShop.png"));
        currentChefSelected = new Image(new Texture("UI/chefUsed.png"));
        notChefSelected = new Image(new Texture("UI/chefNotUsed.png"));
        chefNotUsed =new Texture("UI/chefNotUsed.png");
        chefUsed=new Texture("UI/chefUsed.png");
        minusTexture=new Texture("UI/minusArrow.png");
        plusTexture=new Texture("UI/plusArrow.png");
        chefLocked=new Texture("UI/chefLocked.png");

    }


    /**
     * Method called when the screen is shown.
     * Initializes the sprite and camera position.
     */
    @Override
    public void show() {
        Table table = new Table();
        Table chefTable = new Table();
        table.setFillParent(true);
        //table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        //Buttons
        TextButton exit = new TextButton("Exit", skin);
        TextButton buyChefButton = new TextButton("Buy",skin);
        ImageButton minusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(minusTexture)));
        ImageButton plusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(plusTexture)));

        //Button Sizes
        exit.setSize(250,100);
        buyChefButton.setSize(100,50);

        //Listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.isShopScreen = false;
                game.mainMenu.resetScreen();
                game.setScreen(game.mainMenu);
            }
        });
        plusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int chefCount = game.getChefCount();

                if (chefCount < game.getUnlockedChefs()){

                    game.setChefCount(chefCount + 1);
                }
            }
        });
        minusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("minus","clicked");
                int chefCount = game.getChefCount();
                if (chefCount > 1){
                    game.setChefCount(chefCount - 1);
                }
            }
        });
        buyChefButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int unlockedChefs = game.getUnlockedChefs();
                if (game.getMoney() > 500 && game.getUnlockedChefs() < game.getMaxChefs()){
                    game.setUnlockedChefs(unlockedChefs + 1);
                    game.addMoney(-500);
                }
            }
        });

        //Chef Table
        chefTable.add(chefImage).size(120,120);
        chefTable.row().padTop(20);
        //Chef quantity
        Table chefTotalTable = new Table();
        chefTotalTable.add(minusButton).size(32,32);
        //Add current amount of chefs, unlocked and locked graphics
        for (int i = 0; i< game.getChefCount(); i++){
            chefTotalTable.add(new Image(chefUsed)).size(16,16).padLeft(10).center();
        }
        for (int i = 0; i<game.getUnlockedChefs()- game.getChefCount(); i++){
            chefTotalTable.add(new Image(chefNotUsed)).size(16,16).padLeft(10).center();
        }
        for (int i=0;i<game.getMaxChefs()-game.getUnlockedChefs();i++){
            chefTotalTable.add(new Image(chefLocked)).size(16,16).padLeft(10).center();
        }
        chefTotalTable.add(plusButton).size(32,32).padLeft(10);
        //Add chef quantity to table
        chefTable.add(chefTotalTable).padBottom(20);
        chefTable.row();
        chefTable.add(buyChefButton).size(150,75);
        chefTable.row();
        //Add chef table to main table.
        table.add(chefTable).padBottom(20);
        table.row();
        table.add(exit);




    }

    /**
     * Method to render the screen.
     * Clears the screen and draws the background sprite.
     *
     * @param delta the time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();

        stage.act();
        stage.draw();
    }

    /**
     * Method called when the screen is resized.
     * Updates the viewport and camera position.
     *
     * @param width the new screen width.
     * @param height the new screen height.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().setScreenBounds((width-viewport.getScreenWidth())/2,(height-viewport.getScreenHeight())/2,viewport.getScreenWidth(),viewport.getScreenHeight());
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    /**
     * Dispose method that is called when the screen is no longer used.
     * It is used to free up resources and memory that the screen was using.
     */
    @Override
    public void dispose() {
        backgroundImage.dispose();
        stage.dispose();
        skin.dispose();
        chefImage.remove();
        notChefSelected.remove();
        currentChefSelected.remove();
    }
}
