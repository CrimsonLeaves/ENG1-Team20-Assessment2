package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {

    private final MainGame game;
    private Stage stage;
    private SpriteBatch spriteBatch;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;


    public MainMenu(MainGame game){
        backgroundImage = new Texture("UI/background.png");
        backgroundSprite = new Sprite(backgroundImage);
        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();
    }
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        TextButton scenarioMode = new TextButton("Scenario Mode", skin);
        TextButton endlessMode = new TextButton("Endless Mode", skin);
        TextButton shop = new TextButton("Shop", skin);
        TextButton instructions = new TextButton("Instructions", skin);
        Label difficultyLabel = new Label("Difficulty:",skin);
        final SelectBox<String> difficultyBox;difficultyBox = new SelectBox<String>(skin);
        difficultyBox.setItems(new String[]{"Easy","Medium","Hard"});

        scenarioMode.getLabel().setFontScale(0.5f);
        endlessMode.getLabel().setFontScale(0.5f);
        shop.getLabel().setFontScale(0.5f);
        instructions.getLabel().setFontScale(0.5f);
        int buttonWidth=250;
        int buttonHeight=100;

        table.add(scenarioMode).width(buttonWidth).height(buttonHeight).left().padRight(10);
        table.add(endlessMode).width(buttonWidth).height(buttonHeight).right().padLeft(10);
        table.row().padTop(10);
        table.add(shop).width(buttonWidth).height(buttonHeight).left().padRight(10);
        table.add(instructions).width(buttonWidth).height(buttonHeight).right().padLeft(10);
        table.row().padTop(10);
        table.add(difficultyLabel).padRight(10);
        table.add(difficultyBox).padLeft(10).width(buttonWidth).height(buttonHeight/3);

        scenarioMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("State","Scenario "+game.difficulty);
                game.inGame = true;
                game.scenarioMode = true;
                game.playScreen.scenarioMode=true;
                game.playScreen.resetGame();
                game.setScreen(game.startScreen);
            }
        });
        endlessMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("State","Endless "+game.difficulty);
                game.inGame = true;
                game.scenarioMode = false;
                game.playScreen.scenarioMode=false;
                game.playScreen.resetGame();
                game.setScreen(game.startScreen);
            }
        });

        difficultyBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = difficultyBox.getSelected();
                game.difficulty=selected;
                Gdx.app.log("Difficulty",selected);
            }
        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

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

    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
    }
}
