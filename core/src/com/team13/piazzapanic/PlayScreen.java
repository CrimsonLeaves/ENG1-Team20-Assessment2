package com.team13.piazzapanic;

import Ingredients.Ingredient;
import Recipe.*;
import Sprites.*;
import Recipe.Order;
import Sprites.SpeedPowerup;
import Tools.B2WorldCreator;
import Tools.CircularList;
import Tools.WorldContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The PlayScreen class is responsible for displaying the game to the user and handling the user's interactions.
 * The PlayScreen class implements the Screen interface which is part of the LibGDX framework.
 *
 * The PlayScreen class contains several important fields, including the game instance, stage instance, viewport instance,
 * and several other helper classes and variables. The game instance is used to access the global game configuration and
 * to switch between screens. The stage instance is used to display the graphics and handle user interactions, while the
 * viewport instance is used to manage the scaling and resizing of the game window.
 *
 * The PlayScreen class also contains several methods for initializing and updating the game state, including the
 * constructor, show(), render(), update(), and dispose() methods. The constructor sets up the stage, viewport, and
 * other helper classes and variables. The show() method is called when the PlayScreen becomes the active screen. The
 * render() method is called repeatedly to render the game graphics and update the game state. The update() method is
 * called to update the game state and handle user inputs. The dispose() method is called when the PlayScreen is no longer
 * needed and is used to clean up resources and prevent memory leaks.
 */


public class PlayScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera gamecam;
    private final Viewport gameport;
    private HUD hud;
    public Reputation playerRep;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final World world;
    private CircularList<Chef> chefList;
    private int chefCount = 3;
    private Chef controlledChef;
    private final Orders ordersInterface = new Orders();
    public Order currentOrder;
    public int orderNum=0;
    public int totalOrders=5;
    public ArrayList<PlateStation> plateStations = new ArrayList<>();
    public ArrayList<ChoppingBoard> choppingBoards = new ArrayList<>();
    public ArrayList<Pan> pans = new ArrayList<>();
    public ArrayList<CompletedDishStation> cdStations = new ArrayList<>();
    public Boolean scenarioMode;
    public ArrayList<Oven> ovens = new ArrayList<>();


    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;

    private float timeSecondsCount = 0f;
    private Stage stage;
    private float diffMult=1;
    private boolean moneyAdded;
    private final ShapeRenderer shapeRenderer;
    private ArrayList<Powerup> powerups;
    private final int totalPowerups=3;
    private float movementSpeed=1f;
    private float powerupFinish=-1f;
    private boolean instantCook=false;

    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */

    public PlayScreen(MainGame game){
        shapeRenderer = new ShapeRenderer();
        this.game = game;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD for score & time
        hud = new HUD(game.batch);
        playerRep=new Reputation(3);
        scenarioMode = game.scenarioMode;
        Gdx.input.setInputProcessor(stage);
        moneyAdded=false;
        powerups = new ArrayList<>();
        // create orders hud
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
        new B2WorldCreator(world, map, this);
        chefList = new CircularList<>(chefCount);
        generateChefs(chefCount);

        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");
        createPowerup();


    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);

    }


    /**
     * The handleInput method is responsible for handling the input events of the game such as movement and interaction with objects.
     *
     * It checks if the 'R' key is just pressed and both chefs have the user control, if so,
     * it switches the control between the two chefs.
     *
     * If the controlled chef does not have the user control,
     * the method checks if chef1 or chef2 have the user control and sets the control to that chef.
     *
     * If the controlled chef has the user control,
     * it checks if the 'W', 'A', 'S', or 'D' keys are pressed and sets the velocity of the chef accordingly.
     *
     * If the 'E' key is just pressed and the chef is touching a tile,
     * it checks the type of tile and sets the chef's in-hands ingredient accordingly.
     *
     * The method also sets the direction of the chef based on its linear velocity.
     *
     * @param dt is the time delta between the current and previous frame.
     */

    public void handleInput(float dt){
        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) && canSwitchChefs())) {

            controlledChef.b2body.setLinearVelocity(0, 0);
            //Chef tempChef=controlledChef;

            while (!chefList.peekNextItem().getUserControlChef()){
                chefList.nextItem();
                //Stop infinite loop here
            }
            controlledChef = chefList.nextItem();

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)){
            if (currentOrder==null){
                if (playerRep.loseRep()){
                    hud.updateLives(playerRep.getRep());
                    loseGame();
                }
                hud.updateLives(playerRep.getRep());
                return;
            }
            currentOrder.orderFailed=Boolean.TRUE;

        }

        if (controlledChef.getUserControlChef()) {
            float xVelocity = 0;
            float yVelocity = 0;

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                yVelocity += 0.5f;
                controlledChef.notificationSetBounds("Up");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                xVelocity -= 0.5f;
                controlledChef.notificationSetBounds("Left");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                yVelocity -= 0.5f;
                controlledChef.notificationSetBounds("Down");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                xVelocity += 0.5f;
                controlledChef.notificationSetBounds("Right");
            }
            controlledChef.b2body.setLinearVelocity(xVelocity*movementSpeed, yVelocity*movementSpeed);
        } else {
            controlledChef.b2body.setLinearVelocity(0, 0);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            if(controlledChef.getTouchingTile() != null){
                InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                String tileName = tile.getClass().getName();

                //cast top of the stack to the correct object
                Sprite item = controlledChef.peekStack();
                Ingredient ingredient = null;
                Recipe recipe = null;
                if(item instanceof Ingredient){
                    ingredient = (Ingredient) item;
                } else if(item instanceof Recipe){
                    recipe = (Recipe) item;
                }

                switch (tileName) {
                    case "Sprites.TomatoStation":
                        TomatoStation tomatoTile = (TomatoStation) tile;
                        controlledChef.pickUp(tomatoTile.getIngredient());
                        break;
                    case "Sprites.BunsStation":
                        BunsStation bunTile = (BunsStation) tile;
                        controlledChef.pickUp(bunTile.getIngredient());
                        break;
                    case "Sprites.OnionStation":
                        OnionStation onionTile = (OnionStation) tile;
                        controlledChef.pickUp(onionTile.getIngredient());
                        break;
                    case "Sprites.SteakStation":
                        SteakStation steakTile = (SteakStation) tile;
                        controlledChef.pickUp(steakTile.getIngredient());
                        break;
                    case "Sprites.LettuceStation":
                        LettuceStation lettuceTile = (LettuceStation) tile;
                        controlledChef.pickUp(lettuceTile.getIngredient());
                        break;
                    case "Sprites.CheeseStation":
                        CheeseStation cheeseStation = (CheeseStation) tile;
                        controlledChef.pickUp(cheeseStation.getIngredient());
                        break;
                    case "Sprites.DoughStation":
                        DoughStation doughStation = (DoughStation) tile;
                        controlledChef.pickUp(doughStation.getIngredient());
                        break;
                    case "Sprites.PotatoStation":
                        PotatoStation potatoStation = (PotatoStation) tile;
                        controlledChef.pickUp(potatoStation.getIngredient());
                        break;
                    case "Sprites.BeansStation":
                        BeansStation beansStation = (BeansStation) tile;
                        controlledChef.pickUp(beansStation.getIngredient());
                        break;
                    case "Sprites.PlateStation":
                        PlateStation plateTile = (PlateStation) tile;
                        if(plateTile.getCompletedRecipe() != null){
                            controlledChef.pickUp(plateTile.pickUpItem());
                        } else if (recipe == null && ingredient != null){
                            plateTile.dropItem(ingredient);
                            controlledChef.putDown();
                        }
                        break;
                    case "Sprites.ChoppingBoard":
                        ChoppingBoard choppingBoardTile = (ChoppingBoard) tile;
                        if(choppingBoardTile.getCurrentIngredient() != null){
                            controlledChef.pickUp(choppingBoardTile.getCurrentIngredient());
                            choppingBoardTile.setCurrentIngredient(null);
                        } else {
                            assert ingredient != null;
                            if(ingredient.getTimer("Chopping Board") != null
                                      && !ingredient.isCompleted("Chopping Board")){
                                choppingBoardTile.setCurrentIngredient(ingredient);
                                controlledChef.putDown();
                            }
                        }
                        break;
                    case "Sprites.Pan":
                        Pan panTile = (Pan) tile;
                        if(panTile.getCurrentIngredient() != null){
                            controlledChef.pickUp(panTile.getCurrentIngredient());
                            panTile.setCurrentIngredient(null);
                        } else {
                            assert ingredient != null;
                            if(ingredient.getTimer("Pan") != null
                                      && !ingredient.isCompleted("Pan")){
                                panTile.setCurrentIngredient(ingredient);
                                controlledChef.putDown();
                            }
                        }
                        break;
                    case "Sprites.Oven":
                        Oven ovenTile = (Oven) tile;
                        if(ovenTile.getCurrentIngredient() != null){
                            controlledChef.pickUp(ovenTile.getCurrentIngredient());
                            ovenTile.setCurrentIngredient(null);
                        } else if (ovenTile.getCurrentRecipe() != null){
                            controlledChef.pickUp(ovenTile.getCurrentRecipe());
                            ovenTile.setCurrentRecipe(null);
                        } else if (ingredient != null){
                            if(ingredient.getTimer("Oven") != null
                                && !ingredient.isCompleted("Oven")){
                                ovenTile.setCurrentIngredient(ingredient);
                                controlledChef.putDown();
                            }
                        } else if (recipe != null){
                            if(recipe instanceof UncookedPizzaRecipe){
                                ovenTile.setCurrentRecipe(recipe);
                                controlledChef.putDown();
                            }
                        }
                        break;
                    case "Sprites.Bin":
                        if(ingredient != null || recipe != null){
                            controlledChef.putDown();
                        }
                        break;
                    case "Sprites.CompletedDishStation":
                        if (recipe != null && currentOrder != null){
                            if(recipe.getClass().equals(currentOrder.recipe.getClass())){
                                CompletedDishStation cds = (CompletedDishStation) tile;
                                cds.setRecipe(recipe);
                                controlledChef.putDown();
                                currentOrder.orderComplete = true;
                                if(scenarioMode){
                                    if(orderNum >= totalOrders){
                                        scenarioComplete = Boolean.TRUE;
                                        game.endScreen.win=true;
                                        String stringTime = String.format("%02d:%02d",(int)(timeSecondsCount / 60),(int)(timeSecondsCount % 60));
                                        game.endScreen.time=stringTime;
                                    }
                                }
                            }
                            else {
                                controlledChef.putDown();
                                currentOrder.orderFailed=true;
                            }
                        }
                }

            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)
                    && controlledChef.getTouchingTile() != null){
            InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
            String tileName = tile.getClass().getName();
            if(tileName.equals("Sprites.PlateStation")){
                PlateStation plateTile = (PlateStation) tile;
                if(plateTile.getPlate().size() > 0){
                    controlledChef.pickUp(plateTile.pickUpItem());
                }
            }
        }
    }

    /**
     * The update method updates the game elements, such as camera and characters,
     * based on a specified time interval "dt".
     * @param dt time interval for the update
    */
    public void update(float dt){
        handleInput(dt);

        gamecam.update();
        renderer.setView(gamecam);

        for (Chef chef : chefList.allElems()){
            chef.update(dt);
        }
        for (ChoppingBoard choppingBoard : choppingBoards) {
            choppingBoard.update(dt, diffMult, instantCook);
        }
        for (Pan pan : pans) {
            pan.update(dt, diffMult, instantCook);
        }
        for (Oven oven : ovens){
            oven.update(dt, diffMult, instantCook);
        }

        world.step(1/60f, 6, 2);

    }

    /**
     * Initialises chefs as circular list and creates in world space
     * @param chefCount Number of chefs to create in game
     */
    public void generateChefs(int chefCount){
        float locX=31.5F;
        float locY=38;
        float spacing = 96.5F/(chefCount-1);
        for (int i=0;i<chefCount;i++){
            Chef currentChef = new Chef(this.world,locX,locY);
            chefList.addElement(currentChef);
            locX+=spacing;
        }
        controlledChef=chefList.nextItem();
    }

    /**
     * Checks if chefs can be switched or not if locked out
     * @return Boolean Can chef be controlled and switched to
     */
    public boolean canSwitchChefs() {
        for (Chef chef : chefList.allElems()) {
            if (!chef.getUserControlChef()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * Called each frame to check if order has been completed and update HUD
     */
    public void checkOrder(){
        if (scenarioComplete==Boolean.TRUE){
            int totalMoney = hud.updateScore(Boolean.TRUE, currentOrder.startTime,diffMult);
            hud.updateOrder(Boolean.TRUE, 0);
            if (!moneyAdded){
                moneyAdded=true;
                game.addMoney(totalMoney);
            }
            game.inGame=false;
            game.isEndScreen =true;
            game.isPlayScreen=false;
            return;
        }
        if (currentOrder != null){
            if (currentOrder.orderComplete == Boolean.TRUE){
                //orderNum++;
                hud.updateScore(Boolean.FALSE, currentOrder.startTime,diffMult);
                currentOrder = null;
                createdOrder = Boolean.FALSE;
                hud.updateOrder(Boolean.FALSE, orderNum);
                return;
            }

            if (currentOrder.totalTime-currentOrder.currentTime <=0){
                currentOrder.orderFailed=Boolean.TRUE;
            }
            else {
                currentOrder.currentTime+=Gdx.graphics.getDeltaTime();
            }
            if (currentOrder.orderFailed == Boolean.TRUE){
                currentOrder = null;
                if (playerRep.loseRep()){
                    hud.updateLives(playerRep.getRep());
                    loseGame();
                    return;
                }
                hud.updateLives(playerRep.getRep());
                createdOrder = Boolean.FALSE;
                hud.updateOrder(Boolean.FALSE, orderNum);
                return;
            }
            currentOrder.create(112/MainGame.PPM,128/MainGame.PPM,game.batch);
        }


    }

    /**
     * Puts game into lose game state
     */
    public void loseGame(){
        Gdx.app.log("State","The game is in loss state");
        game.inGame=false;
        game.isEndScreen =true;
        game.isPlayScreen=false;
        game.endScreen.win=false;
        String stringTime = String.format("%02d:%02d",(int)(timeSecondsCount / 60), (int)(timeSecondsCount % 60));
        game.endScreen.time=stringTime;
        game.endScreen.score=hud.getScore();
        if (!game.scenarioMode){
            game.addMoney(hud.getScore());
        }
    }

    /**
     * Resets all variables and objects for replaying.
     */
    public void resetGame(){
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        playerRep.reset();
        for (Chef chef : chefList.allElems()){
            if (chef.getTexture() != null) {chef.getTexture().dispose();}
            world.destroyBody(chef.b2body);
        }
        chefList = new CircularList<Chef>(chefCount);
        generateChefs(chefCount);
        controlledChef.notificationSetBounds("Down");
        timeSeconds = 0f;
        timeSecondsCount = 0f;
        orderNum=0;
        game.isEndScreen =false;
        currentOrder=null;
        //hud.reset();
        hud.dispose();
        hud=new HUD(game.batch);
        switch (game.difficulty){
            case "Easy":
                diffMult=1.5f;
                break;
            case "Medium":
                diffMult=1;
                break;
            case "Hard":
                diffMult=0.67f;
                break;
            default:
                break;
        }
    }

    /**
     * Generates a new powerup in playable area.
     */
    public void createPowerup(){
        //Generate random location that is accessible
        Random r = new Random();
        float min=MainGame.TILE_SIZE/MainGame.PPM;
        float xMax = MainGame.TILE_SIZE*8/MainGame.PPM;
        float yMax = MainGame.TILE_SIZE*6/MainGame.PPM;
        Rectangle centreIsland = new Rectangle(MainGame.TILE_SIZE*3/MainGame.PPM,MainGame.TILE_SIZE*3/MainGame.PPM,MainGame.TILE_SIZE*4/MainGame.PPM,MainGame.TILE_SIZE*4/MainGame.PPM);
        float x = MainGame.TILE_SIZE/MainGame.PPM + r.nextFloat()*(xMax-min);
        float y = MainGame.TILE_SIZE/MainGame.PPM + r.nextFloat()*(yMax-min);

        while (centreIsland.contains(x,y)){
            x = MainGame.TILE_SIZE/MainGame.PPM + r.nextFloat()*(xMax-min);
            y = MainGame.TILE_SIZE/MainGame.PPM + r.nextFloat()*(yMax-min);
        }



        //Random powerup
        int randomNum = ThreadLocalRandom.current().nextInt(0, totalPowerups);
        Powerup newPowerup;
        switch (randomNum){
            case 0: //Speed Powerup
                newPowerup = new SpeedPowerup(x, y);
                break;
            case 1:
                newPowerup = new ResetOrderPowerup(x, y);
                break;
            case 2:
                newPowerup = new InstantCookPowerup(x, y);
                break;
            default:
                newPowerup=new SpeedPowerup(x,y);
                break;
        }
        powerups.add(newPowerup);
    }

    /**
     * Checks for any overlap of controlled chef and powerups. Removes and activates if so.
     */
    public void checkPowerupCollisions(){
        Iterator itr = powerups.iterator();
        while (itr.hasNext()){
            Powerup currentPowerup = (Powerup) itr.next();
            if (currentPowerup.collisionRect.overlaps(controlledChef.collisionRect)){
                resetPowerups(); //So that powerups cannot be combined
                activatePowerups(currentPowerup.getClass().getSimpleName());
                itr.remove();
            }
        }
    }
    private void activatePowerups(String name){
        switch (name){
            case "SpeedPowerup":
                movementSpeed=1.5f; //Increase speed by 50%
                powerupFinish=timeSecondsCount+10f; //Set powerup active for 10 seconds
                break;
            case "ResetOrderPowerup":
                if (currentOrder != null) {
                    currentOrder.currentTime = 0f;
                    powerupFinish = -1;
                }
                break;
            case "InstantCookPowerup":
                instantCook=true;
                powerupFinish=timeSecondsCount+10f;
                break;
            default:
                Gdx.app.log("Error","wrong name");
        }
    }
    private void resetPowerups(){
        movementSpeed=1f;
        instantCook=false;
    }
    /**

     The render method updates the screen by calling the update method with the given delta time, and rendering the graphics of the game.

     It updates the HUD time, clears the screen, and renders the renderer and the hud.

     Additionally, it checks the state of the game and draws the ingredients, completed recipes, and notifications on the screen.

     @param delta The time in seconds since the last frame.
     */
    @Override
    public void render(float delta){
        update(delta);

        //Execute handleEvent each 1 second
        timeSeconds += Gdx.graphics.getDeltaTime();
        timeSecondsCount += Gdx.graphics.getDeltaTime();

        if (Math.round(timeSecondsCount % 10) == 5 && createdOrder == Boolean.FALSE){
            createdOrder = Boolean.TRUE;
            orderNum++;
            currentOrder=ordersInterface.newOrder(hud.getTime(),diffMult);
        }
        float period = 1f;
        if (timeSeconds > period) {
            timeSeconds -= period;
            hud.updateTime(scenarioComplete);
            if (ThreadLocalRandom.current().nextInt(0, (int)(15/diffMult)) == 0){
                createPowerup();
            }
        }

        if (timeSecondsCount > powerupFinish && powerupFinish > 0){
            powerupFinish=-1;
            resetPowerups();
        }

        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        checkOrder();
        checkPowerupCollisions();

        for (Powerup powerup : powerups){
            powerup.create(game.batch);
        }
        for (Chef chef : chefList.allElems()){
            chef.create(game.batch);
        }

        controlledChef.drawNotification(game.batch);
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

        // make a function
        for (ChoppingBoard choppingBoard : choppingBoards) {
            Ingredient currentIngredient = choppingBoard.getCurrentIngredient();
            if (currentIngredient != null) {
                currentIngredient.create(choppingBoard.getX(), choppingBoard.getY(), game.batch);
                choppingBoard.drawProgressBar(game.batch,"Chopping Board", diffMult);
            }
        }

        for (Pan pan : pans) {
            Ingredient currentIngredient = pan.getCurrentIngredient();
            if (currentIngredient != null) {
                currentIngredient.create(pan.getX(), pan.getY(), game.batch);
                pan.drawProgressBar(game.batch,"Pan", diffMult);
            }
        }

        for (Oven oven : ovens){
            Ingredient currentIngredient = oven.getCurrentIngredient();
            Recipe  currentRecipe = oven.getCurrentRecipe();
            if (currentIngredient != null) {
                currentIngredient.create(oven.getX(),oven.getY(),game.batch);
                oven.drawProgressBar(game.batch,"Oven", diffMult);
            } else if (currentRecipe != null){
                currentRecipe.create(oven.getX(),oven.getY(),game.batch);
                oven.drawProgressBar(game.batch,"Oven", diffMult);
            }
        }

        for (CompletedDishStation cdStation : cdStations) {
            if(cdStation.getRecipe() != null){
                cdStation.draw(game.batch);
            }
        }

        game.batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        if (currentOrder!=null){
            float orderX=435;
            float orderY=450;
            float percent=(currentOrder.totalTime- currentOrder.currentTime)/ currentOrder.totalTime;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Gdx.gl20.glLineWidth(3f);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.arc(orderX, orderY, 10f,90,360 * percent);
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if (percent > 0.75) {shapeRenderer.setColor(new Color(0,0.4f,0,1));}
            else if (percent > 0.50) {shapeRenderer.setColor(Color.YELLOW);}
            else if (percent > 0.25) {shapeRenderer.setColor(Color.ORANGE);}
            else {shapeRenderer.setColor(Color.RED);}

            shapeRenderer.arc(orderX, orderY, 10f,90,360 * percent);
            shapeRenderer.end();


        }
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height){
        gameport.update(width, height);
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){
        
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
        stage.dispose();
        shapeRenderer.dispose();

    }
}
