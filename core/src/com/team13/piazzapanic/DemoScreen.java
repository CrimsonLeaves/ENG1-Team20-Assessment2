package com.team13.piazzapanic;

import Ingredients.Ingredient;
import Recipe.Recipe;
import Sprites.*;
import Tools.B2WorldCreator;
import Tools.Constants;
import Tools.DemoScript;
import Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * This class implements the `Screen` interface and represents the start screen of the game.
 */
public class DemoScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Stage stage;
    private DemoScript demoMode;
    private World world;
    public ArrayList<PlateStation> plateStations = new ArrayList<>();
    public ArrayList<ChoppingBoard> choppingBoards = new ArrayList<>();
    public ArrayList<Pan> pans = new ArrayList<>();
    public ArrayList<CompletedDishStation> cdStations = new ArrayList<>();

    public ArrayList<Oven> ovens = new ArrayList<>();
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    /**
     * Constructor for StartScreen.
     *
     * @param game the game object.
     */
    public DemoScreen(MainGame game) {
        this.game = game;
        backgroundImage = new Texture("startImage.png");
        backgroundSprite = new Sprite(backgroundImage);
        camera = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        viewport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, camera);
        Gdx.input.setInputProcessor(stage);
        world = new World(new Vector2(0,0), true);
        demoMode = new DemoScript(world,0.5f);
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        new B2WorldCreator(world, map, this);
        world.setContactListener(new WorldContactListener());
    }

    /**
     * Method called when the screen is shown.
     * Initializes the sprite and camera position.
     */
    @Override
    public void show() {
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        Gdx.input.setInputProcessor(stage);
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
        renderer.setView(camera);
        renderer.render();
        for (ChoppingBoard choppingBoard : choppingBoards) {
            choppingBoard.update(delta, 1f, false, false);
        }
        for (Pan pan : pans) {
            pan.update(delta, 1f, false, false);
        }
        for (Oven oven : ovens){
            oven.update(delta, 1f, false, false);
        }
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        //backgroundSprite.draw(game.batch);
        demoMode.update(delta, game.batch);

        for (ChoppingBoard choppingBoard : choppingBoards) {
            Ingredient currentIngredient = choppingBoard.getCurrentIngredient();
            if (currentIngredient != null) {
                currentIngredient.create(choppingBoard.getX(), choppingBoard.getY(), game.batch);
                choppingBoard.drawProgressBar(game.batch, Constants.CHOPPING_BOARD, 1f);
            }
        }

        for (Pan pan : pans) {
            Ingredient currentIngredient = pan.getCurrentIngredient();
            if (currentIngredient != null) {
                currentIngredient.create(pan.getX(), pan.getY(), game.batch);
                pan.drawProgressBar(game.batch,Constants.PAN, 1f);
            }
        }

        for (Oven oven : ovens){
            Ingredient currentIngredient = oven.getCurrentIngredient();
            Recipe currentRecipe = oven.getCurrentRecipe();
            if (currentIngredient != null) {
                currentIngredient.create(oven.getX(),oven.getY(),game.batch);
                oven.drawProgressBar(game.batch,Constants.OVEN, 1f);
            } else if (currentRecipe != null){
                currentRecipe.create(oven.getX(),oven.getY(),game.batch);
                oven.drawProgressBar(game.batch,Constants.OVEN, 1f);
            }
        }
        for (CompletedDishStation cdStation : cdStations) {
            if(cdStation.getRecipe() != null){
                cdStation.draw(game.batch);
            }
        }
        for (PlateStation plateStation : plateStations) {
            if (plateStation.getPlate().size() > 0){
                for(Object ing : plateStation.getPlate()){
                    Ingredient ingNew = (Ingredient) ing;
                    ingNew.create(plateStation.getX(), plateStation.getY(),game.batch);
                }
            } else if (plateStation.getCompletedRecipe() != null){
                Recipe recipeNew = plateStation.getCompletedRecipe();
                recipeNew.create(plateStation.getX(), plateStation.getY(), game.batch);
            }
        }
        game.batch.end();
        world.step(1/60f, 6, 2);


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
        //stage.dispose();
        world.dispose();
        renderer.dispose();
        map.dispose();
    }
}
