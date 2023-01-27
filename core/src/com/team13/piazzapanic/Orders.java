package com.team13.piazzapanic;

import Recipe.Order;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Orders implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Order[] orders[];

    Label timeLabelT;
    Label timeLabel;

    public Orders(SpriteBatch sb){
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}