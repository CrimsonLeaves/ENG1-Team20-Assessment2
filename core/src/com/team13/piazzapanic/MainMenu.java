package com.team13.piazzapanic;

import Tools.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.tools.javac.util.Context;

public class MainMenu implements Screen {

    private final MainGame game;
    private Stage stage, stage2;
    private SpriteBatch spriteBatch;
    private final Texture backgroundImage, loadImage;
    private final Sprite backgroundSprite, loadSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    Skin skin;
    Image titleImage;
    Label moneyLabel;
    Table table, promptTable;
    boolean loadPrompt;


    public MainMenu(MainGame game){
        backgroundImage = new Texture("UI/background.png");
        backgroundSprite = new Sprite(backgroundImage);
        loadImage  =new Texture("UI/background.png");
        loadSprite = new Sprite(loadImage);
        this.game=game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        stage = new Stage(new ScreenViewport());
        stage2 = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        titleImage= new Image(new Texture("UI/TitleText.png"));
        loadPrompt=false;
        promptTable=new Table();

    }
    public void resetScreen(){
        table.clear();
        table.remove();
        promptTable.clear();
        promptTable.remove();
        stage.clear();
        stage2.clear();
    }
    @Override
    public void show() {
        if (loadPrompt){
            Gdx.input.setInputProcessor(stage2);
        }
        else{
            Gdx.input.setInputProcessor(stage);
        }
        table = new Table();
        promptTable = new Table();
        Table buttonTable = new Table();
        table.setFillParent(true);
        promptTable.setFillParent(true);
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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


        Table promptTableButtons = new Table();
        //promptTable.setDebug(true);

        TextButton loadButton = new TextButton("Load Game", skin);
        loadButton.getLabel().setFontScale(0.5f);
        TextButton newButton = new TextButton("New Game", skin);
        newButton.getLabel().setFontScale(0.5f);
        Label loadLabel = new Label("Would you like to load or start a new game?",skin);
        loadLabel.setAlignment(Align.center);
        promptTable.add(loadLabel).expandX().padBottom(20).row();
        promptTableButtons.add(newButton).width(buttonWidth).height(buttonHeight).padRight(10);
        promptTableButtons.add(loadButton).width(buttonWidth).height(buttonHeight).padLeft(10);
        promptTable.add(promptTableButtons).center().center();
        promptTable.center().center();
        //promptTable.setBackground((Drawable) Color.WHITE);
        stage2.addActor(promptTable);
        int windowWidth = 500;
        int windowHeight = 250;
        loadSprite.setSize(windowWidth, windowHeight);
        loadSprite.setPosition(MainGame.V_WIDTH/2-windowWidth/2, MainGame.V_HEIGHT/2-windowHeight/2);

        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.inGame = true;
                game.loadGame = true;
                loadPrompt = false;
                game.playScreen = new PlayScreen(game);
                game.playScreen.resetGame();
                game.setScreen(game.startScreen);
            }
        });
        newButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.inGame = true;
                game.loadGame = false;
                loadPrompt = false;
                game.playScreen = new PlayScreen(game);
                game.playScreen.resetGame();
                game.setScreen(game.startScreen);
            }
        });
        scenarioMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!Gdx.files.local(Constants.DATA_SCENARIO_PATH).exists()) {
                    Gdx.app.log("State", "Scenario " + game.difficulty);
                    game.inGame = true;
                    game.scenarioMode = true;
                    //game.playScreen.scenarioMode = true;
                    game.loadGame = false;
                    game.playScreen = new PlayScreen(game);
                    game.playScreen.resetGame();
                    game.setScreen(game.startScreen);
                }
                else{
                    game.scenarioMode = true;
                    //game.playScreen.scenarioMode = true;
                    loadPrompt=true;
                }
            }
        });
        endlessMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!Gdx.files.local(Constants.DATA_ENDLESS_PATH).exists()) {
                    Gdx.app.log("State", "Scenario " + game.difficulty);
                    game.inGame = true;
                    game.scenarioMode = false;

                    game.loadGame = false;
                    game.playScreen = new PlayScreen(game);
                    //game.playScreen.scenarioMode = false;
                    game.playScreen.resetGame();

                    game.setScreen(game.startScreen);
                }
                else{
                    game.scenarioMode = false;
                    //game.playScreen.scenarioMode = false;
                    loadPrompt=true;
                }
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
        stage2.act();
        if (loadPrompt){
            //game.batch.begin();
            //backgroundSprite.draw(game.batch);
            //loadSprite.draw(game.batch);
            //game.batch.end();
            stage2.draw();
        } else {
            stage.draw();
        }
        if (loadPrompt && Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            loadPrompt=false;
        }
    }

    @Override
    public void resize(int width, int height) {
        if (viewport.getScreenHeight()+viewport.getLeftGutterWidth()+ viewport.getRightGutterWidth() != width || viewport.getScreenWidth()+ viewport.getBottomGutterHeight()+ viewport.getTopGutterHeight()  != height){
            resetScreen();
        }
        viewport.update(width, height);
        stage.getViewport().setScreenBounds((width-viewport.getScreenWidth())/2,(height-viewport.getScreenHeight())/2,viewport.getScreenWidth(),viewport.getScreenHeight());
        stage2.getViewport().setScreenBounds((width-viewport.getScreenWidth())/2,(height-viewport.getScreenHeight())/2,viewport.getScreenWidth(),viewport.getScreenHeight());
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
        stage2.dispose();
        spriteBatch.dispose();
        skin.dispose();
    }
}
