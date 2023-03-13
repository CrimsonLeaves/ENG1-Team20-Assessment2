package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class HUD implements Disposable {
    public Stage stage;
    private Boolean scenarioComplete;

    private Integer worldTimerM;
    private Integer worldTimerS;

    private Integer score;

    public String timeStr;

    public Table table;

    Label timeLabelT;
    Label timeLabel;

    Label scoreLabel;
    Label scoreLabelT;
    Label orderNumL;
    Label orderNumLT;
    Image[] lives = new Image[3];

    public HUD(SpriteBatch sb){
        this.scenarioComplete = Boolean.FALSE;
        worldTimerM = 0;
        worldTimerS = 0;
        score = 0;
        timeStr = String.format("%d", worldTimerM) + " : " + String.format("%d", worldTimerS);
        float fontX = 0.5F;
        float fontY = 0.3F;

        BitmapFont font = new BitmapFont();
        font.getData().setScale(fontX, fontY);
        Viewport viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table = new Table();
        table.left().top();
        table.setFillParent(true);

        timeLabel = new Label(String.format("%d", worldTimerM, ":", "%i", worldTimerS), new Label.LabelStyle(font, Color.WHITE));
        timeLabelT = new Label("TIME", new Label.LabelStyle(font, Color.BLACK));
        orderNumLT = new Label("ORDER", new Label.LabelStyle(font, Color.BLACK));
        orderNumL = new Label(String.format("%d", 0), new Label.LabelStyle(font, Color.WHITE));

        scoreLabel = new Label(String.format("%d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabelT = new Label("MONEY", new Label.LabelStyle(font, Color.BLACK));


        table.add(timeLabelT).padTop(2).padLeft(2);
        table.add(scoreLabelT).padTop(2).padLeft(2);
        table.add(orderNumLT).padTop(2).padLeft(2);
        table.row();
        table.add(timeLabel).padTop(2).padLeft(2);
        table.add(scoreLabel).padTop(2).padLeft(2);
        table.add(orderNumL).padTop(2).padLeft(2);
        table.row();

        for (int i=0; i<3;i++){
            Image heart = new Image(new Texture(Gdx.files.internal("UI/heart.png")));
            table.add(heart);
            lives[i]=heart;
        }
        table.left().top();
        stage.addActor(table);
    }

    /**
     * Updates the player's lives.
     *
     * @param currentLives The players current lives.
     */
    public void updateLives(int currentLives){
        lives[currentLives].remove();
    }
    /**
     * Updates the time label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     */
    public void updateTime(Boolean scenarioComplete){
        if(scenarioComplete){
            timeLabel.setColor(Color.GREEN);
            timeStr = String.format("%d", worldTimerM) + ":" + String.format("%d", worldTimerS);
            timeLabel.setText(String.format("TIME: " + timeStr + " MONEY: %d", score));
            timeLabelT.setText("SCENARIO COMPLETE");
            table.center().top();
            stage.addActor(table);
            return;
        }
        else {
            if (worldTimerS == 59) {
                worldTimerM += 1;
                worldTimerS = 0;
            } else {
                worldTimerS += 1;
            }
        }
        table.left().top();
        if(worldTimerS < 10){
            timeStr = String.format("%d", worldTimerM) + ":0" + String.format("%d", worldTimerS);
        }
        else {
            timeStr = String.format("%d", worldTimerM) + ":" + String.format("%d", worldTimerS);
        }
        timeLabel.setText(timeStr);
        stage.addActor(table);

    }

    /**
     * Calculates the user's score per order and updates the label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     * @param startTime The time at which the order is placed
     */

    public int updateScore(Boolean scenarioComplete, Integer startTime,float diff){
        int addScore;
        int currentTime;

        if(this.scenarioComplete == Boolean.FALSE){
            currentTime = (worldTimerM * 60) + worldTimerS;
            if (currentTime - startTime <= 30*diff) {
                addScore = 100;
            }
            else{
                addScore = 100 - (int)(5 * (currentTime -startTime- 30*diff));
                if(addScore < 0){
                    addScore = 0;
                }
            }
            score += addScore;
        }


        if(scenarioComplete==Boolean.TRUE){
            scoreLabel.setColor(Color.GREEN);
            scoreLabel.setText("");
            scoreLabelT.setText("");
            scoreLabelT.remove();
            scoreLabel.remove();
            table.center().top();
            stage.addActor(table);
            this.scenarioComplete = Boolean.TRUE;
            return score;
        }

        table.left().top();
        scoreLabel.setText(String.format("%d", score));
        stage.addActor(table);
        return score;

    }

    /**
     * Updates the order label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     * @param orderNum The index number of the order.
     */
    public void updateOrder(Boolean scenarioComplete, Integer orderNum){
        if(scenarioComplete==Boolean.TRUE){
            orderNumL.remove();
            orderNumLT.remove();
            table.center().top();
            stage.addActor(table);
            return;
        }

        table.left().top();
        orderNumL.setText(String.format("%d", orderNum));
        orderNumLT.setText("ORDER");
        stage.addActor(table);

    }
    /**
     * Used to get the time from HUD
     * @return Time elapsed in seconds
     */
    public int getTime(){
        return worldTimerM*60+worldTimerS;
    }
    public int getScore() {return score;}
    public void reset(){
        //Reset lives
        lives = new Image[3];
        for (int i=0; i<3;i++){
            Image heart = new Image(new Texture(Gdx.files.internal("UI/heart.png")));
            table.add(heart);
            lives[i]=heart;
        }
        //Reset Time
        worldTimerS=0;
        worldTimerM=0;
        //Reset Score
        score=0;
        updateOrder(false,0);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
