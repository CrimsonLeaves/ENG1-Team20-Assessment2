package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EndScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImageLose;
    private final Texture backgroundImageScenarioWin;
    private final Texture backgroundImageEndlessWin;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Stage stage;
    public Boolean win;
    public String time;
    public int score;
    Skin skin;

    /**
     * Constructor for LoseScreen.
     *
     * @param game the game object.
     */
    public EndScreen(MainGame game) {
        this.game = game;
        backgroundImageLose = new Texture("UI/lossImage.png");
        backgroundImageScenarioWin = new Texture("UI/scenarioWinImage.png");
        backgroundImageEndlessWin = new Texture("UI/endlessWinImage.png");
        backgroundSprite = new Sprite(backgroundImageLose);
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        win=false;
        time="00:00";
        score=0;
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }


    /**
     * Method called when the screen is shown.
     * Initializes the sprite and camera position.
     */
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.debug();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        //Buttons
        TextButton exit = new TextButton("Exit", skin);
        TextButton replay = new TextButton("Replay", skin);

        //Button Sizes
        exit.setSize(250,100);
        replay.setSize(250,100);
        //Text
        Label winLabel = new Label("Well Done!",skin);
        Label scoreLabel = new Label ("Score: "+score,skin);
        //Scenario Win
        Label scenarioTimeLabel = new Label("Completion Time: "+time,skin);
        //Endless Win
        Label EndlessTimeLabel = new Label("Survival Time: "+time,skin);
        //Resizing Fonts
        float fontscale= 2f;
        winLabel.setFontScale(fontscale);
        scoreLabel.setFontScale(fontscale);
        scenarioTimeLabel.setFontScale(fontscale);
        scenarioTimeLabel.setFontScale(fontscale);
        if (win){
            backgroundSprite.setTexture(backgroundImageScenarioWin);
            table.add(winLabel);
            table.row().padTop(50);
            table.add(scenarioTimeLabel);
            table.row().padTop(50);
            table.add(scoreLabel);
        }
        else if (!game.scenarioMode){
            backgroundSprite.setTexture(backgroundImageEndlessWin);
            table.add(winLabel);
            table.row().padTop(50);
            table.add(EndlessTimeLabel);
            table.row().padTop(25);
            table.add(scoreLabel);
        }
        else{
            backgroundSprite.setTexture(backgroundImageLose);
        }

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.isEndScreen =false;
                game.mainMenu.resetScreen();
                game.setScreen(game.mainMenu);
            }
        });

        replay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.playScreen.resetGame();
                game.inGame=true;
                game.setScreen(game.playScreen);
            }
        });
        table.addActor(replay);
        replay.setPosition(40,40);
        table.addActor(exit);
        exit.setPosition(stage.getWidth()-exit.getWidth()-40, 40);


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
        backgroundImageEndlessWin.dispose();
        backgroundImageScenarioWin.dispose();
        backgroundImageLose.dispose();
        stage.dispose();
        skin.dispose();
    }
}
