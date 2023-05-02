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

import java.util.ArrayList;

public class InstructionsScreen implements Screen {
    private final MainGame game;
    private final Texture instructionsControlsImage;
    private final Texture instructionsStationsImage;
    private final Texture instructionsPowerupsImage;
    private ArrayList<Sprite> instructionBackgrounds;
    private Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Stage stage;
    private Image selectedImage;
    private Image notSelectedImage;
    private Texture notSelected;
    private Texture currentSelected;
    private Texture minusTexture;
    private Texture plusTexture;
    private int currentScreen;
    private int maxScreen;
    Skin skin;

    /**
     * Constructor for LoseScreen.
     *
     * @param game the game object.
     */
    public InstructionsScreen(MainGame game) {
        currentScreen=0;

        instructionsControlsImage = new Texture("UI/instructionsControls.png");
        instructionsStationsImage = new Texture("UI/instructionsStations.png");
        instructionsPowerupsImage = new Texture("UI/instructionsPowerups.png");

        instructionBackgrounds = new ArrayList<>();
        instructionBackgrounds.add(new Sprite(instructionsControlsImage));
        instructionBackgrounds.add(new Sprite(instructionsStationsImage));
        instructionBackgrounds.add(new Sprite(instructionsPowerupsImage));
        backgroundSprite= instructionBackgrounds.get(0);
        maxScreen=instructionBackgrounds.size()-1;


        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        notSelected=new Texture("UI/chefNotUsed.png");
        currentSelected=new Texture("UI/chefUsed.png");
        selectedImage = new Image(currentSelected);
        notSelectedImage = new Image(notSelected);

        minusTexture=new Texture("UI/minusArrow.png");
        plusTexture=new Texture("UI/plusArrow.png");

    }


    /**
     * Method called when the screen is shown.
     * Initializes the sprite and camera position.
     */
    @Override
    public void show() {
        Table table = new Table();
        Table controlTable = new Table();
        table.setFillParent(true);
        //table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        backgroundSprite=instructionBackgrounds.get(currentScreen);
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        //Buttons
        TextButton exit = new TextButton("Exit", skin);
        exit.getLabel().setFontScale(0.4f);
        ImageButton minusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(minusTexture)));
        ImageButton plusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(plusTexture)));

        //Button Sizes
        exit.setSize(100,40);

        //Listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.isInstructionsScreen = false;
                game.mainMenu.resetScreen();
                game.setScreen(game.mainMenu);
            }
        });
        plusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentScreen+=1;
                if (currentScreen > maxScreen){
                    currentScreen=0;
                }
            }
        });
        minusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentScreen-=1;
                if (currentScreen < 0){
                    currentScreen=maxScreen;
                }
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(minusButton).size(32,32);
        for (int i = 0; i<= maxScreen; i++){
            if (i==currentScreen){
                buttonTable.add(new Image(currentSelected)).size(16,16).padLeft(10).center();

            }
            else{
                buttonTable.add(new Image(notSelected)).size(16,16).padLeft(10).center();
            }

        }
        buttonTable.add(plusButton).size(32,32).padLeft(10);
        //Add chef quantity to table
        controlTable.add(buttonTable);
        //Add chef table to main table.
        table.add(controlTable).padBottom(5);
        table.row();
        table.add(exit).size(100,50);
        table.bottom().padBottom(30);




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
        instructionsPowerupsImage.dispose();
        instructionsStationsImage.dispose();
        instructionsControlsImage.dispose();
        stage.dispose();
        skin.dispose();
        currentSelected.dispose();
        notSelected.dispose();
    }
}
