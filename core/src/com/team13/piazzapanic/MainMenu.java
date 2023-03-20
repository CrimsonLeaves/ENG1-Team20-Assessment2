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
import com.badlogic.gdx.utils.ScreenUtils;
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
    Skin skin;
    Image titleImage;
    Label moneyLabel;
    Table table;


    public MainMenu(MainGame game){
        backgroundImage = new Texture("UI/background.png");
        backgroundSprite = new Sprite(backgroundImage);
        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        titleImage= new Image(new Texture("UI/TitleText.png"));

    }
    public void resetScreen(){
        table.clear();
        table.remove();
        stage.clear();
    }
    @Override
    public void show() {
        table = new Table();
        Table buttonTable = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        table.setDebug(false);


        stage.addActor(table);


        //Buttons
        TextButton scenarioMode = new TextButton("Scenario Mode", skin);
        TextButton endlessMode = new TextButton("Endless Mode", skin);
        TextButton shop = new TextButton("Shop", skin);
        TextButton instructions = new TextButton("Instructions", skin);
        //Difficulty
        Label difficultyLabel = new Label("Difficulty:",skin);
        final SelectBox<String> difficultyBox= new SelectBox<String>(skin);
        difficultyBox.setItems(new String[]{"Easy","Medium","Hard"});
        //Money
        table.removeActor(moneyLabel);
        moneyLabel = new Label("Money: "+game.getMoney(),skin);

        scenarioMode.getLabel().setFontScale(0.5f);
        endlessMode.getLabel().setFontScale(0.5f);
        shop.getLabel().setFontScale(0.5f);
        instructions.getLabel().setFontScale(0.5f);
        int buttonWidth=250;
        int buttonHeight=100;

        buttonTable.add(scenarioMode).width(buttonWidth).height(buttonHeight).left().padRight(10);
        buttonTable.add(endlessMode).width(buttonWidth).height(buttonHeight).right().padLeft(10);
        buttonTable.row().padTop(10);
        buttonTable.add(shop).width(buttonWidth).height(buttonHeight).left().padRight(10);
        buttonTable.add(instructions).width(buttonWidth).height(buttonHeight).right().padLeft(10);
        buttonTable.row().padTop(10);
        buttonTable.add(difficultyLabel).padRight(10);
        buttonTable.add(difficultyBox).padLeft(10).width(buttonWidth).height(buttonHeight/3);

        table.add(titleImage).size(320,100).padBottom(20);
        table.row();
        table.add(buttonTable).center().center();
        table.row();
        table.add(moneyLabel).bottom().left();


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
        shop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.isShopScreen=true;
                game.setScreen(game.shopScreen);
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
        ScreenUtils.clear(0, 0.5f, 0, 1);
        moneyLabel.setText("Money: "+game.getMoney());
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
        if (viewport.getScreenHeight()+viewport.getLeftGutterWidth()+ viewport.getRightGutterWidth() != width || viewport.getScreenWidth()+ viewport.getBottomGutterHeight()+ viewport.getTopGutterHeight()  != height){
            resetScreen();
        }
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

    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
        skin.dispose();
    }
}
