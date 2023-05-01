package com.team13.piazzapanic;

import Ingredients.*;
import Recipe.*;
import Sprites.*;
import Recipe.Order;
import Sprites.SpeedPowerup;
import Tools.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;
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
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
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
    public ArrayList<Oven> ovens = new ArrayList<>();
    public Boolean scenarioMode;

    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;

    private float timeSecondsCount = 0f;
    private final Stage stage;
    private float diffMulti =1;
    private boolean moneyAdded;
    private final ShapeRenderer shapeRenderer;
    public boolean loadGame;
    private ArrayList<ChefDataStore> chefData;
    ArrayList<IngredientDataStore>[][] stationItems;
    //Powerups
    private final ArrayList<Powerup> powerups;
    private float movementSpeed=1f;
    private float powerupFinish=-1f;
    private boolean instantCook=false;
    private boolean noBurn=false;

    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */

    public PlayScreen(MainGame game){
        //Initialises variables
        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        this.game = game;
        loadGame=game.loadGame;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gameCam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gameCam);
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
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        Json json = new Json();
        FileHandle file;
        //Get correct save file
        if (scenarioMode){
            file = Gdx.files.local(Constants.DATA_SCENARIO_PATH);
        }
        else{
            file = Gdx.files.local(Constants.DATA_ENDLESS_PATH);
        }
        if (!file.exists()){
            loadGame=false;
        }
        else { //If file exists get station data
            String dataRaw = file.readString();
            SaveDataStore saveData = json.fromJson(SaveDataStore.class, dataRaw);
            stationItems=saveData.getStationItems();
        }
        //Create world from map
        world = new World(new Vector2(0,0), true);
        if (loadGame){
            new B2WorldCreator(world, map, this, stationItems);
        }
        else{
            new B2WorldCreator(world, map, this, null);
            stationItems = new ArrayList[((TiledMapTileLayer) map.getLayers().get(0)).getWidth()][((TiledMapTileLayer) map.getLayers().get(0)).getHeight()];
        }
        chefList = new CircularList<>();
        if (loadGame){loadGame();}
        generateChefs(chefCount);

        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");


    }

    /**
     * Called when the screen is showed to change the stage from the previous menus.
     * This means that buttons do not click from the wrong screen
     */
    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }


    /**
     * The handleInput method is responsible for handling the input events of the game.
     * It has helper methods for handling chef switching, movement, interaction and
     * removing from a plate station.
     */

    public void handleInput(){
        handleChefSwitching();
        handleMovement();
        handleInteraction();
        handleRemovingFromPlateStation();
    }

    /**
     * Handles input for switching between controlled chefs by cycling the chefList when the required input is given
     */
    private void handleChefSwitching(){
        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) && canSwitchChefs())) {

            controlledChef.b2body.setLinearVelocity(0, 0);
            while (!chefList.peekNextItem().getUserControlChef()){
                chefList.nextItem();
            }
            controlledChef = chefList.nextItem();

        }
    }

    /**
     * Handles input for moving the controlled chef using the WASD keys
     */
    private void handleMovement(){
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
    }

    /**
     * Handles interaction with tile objects
     */
    private void handleInteraction(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            if(controlledChef.getTouchingTile() != null){
                InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                String tileName = tile.getClass().getName();


                if (tile instanceof IngredientStation){
                    IngredientStation ingredientTile = (IngredientStation) tile;
                    controlledChef.pickUp(ingredientTile.getIngredient());
                } else {
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
                            } else if (ingredient != null){
                                if(ingredient.getTimer(Constants.CHOPPING_BOARD) != null
                                        && !ingredient.isCompleted(Constants.CHOPPING_BOARD)){
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
                            } else if (ingredient != null){
                                if(ingredient.getTimer(Constants.PAN) != null
                                        && !ingredient.isCompleted(Constants.PAN)
                                        && ingredient.isCompleted(Constants.CHOPPING_BOARD)){
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
                                if(ingredient.getTimer(Constants.OVEN) != null
                                        && !ingredient.isCompleted(Constants.OVEN)){
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
                                            game.endScreen.time= String.format("%02d:%02d",(int)(timeSecondsCount / 60),
                                                                                (int)(timeSecondsCount % 60));
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


            }
        }
    }

    /**
     * Handles taking items off of the plate station
     */
    private void handleRemovingFromPlateStation(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)
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
        handleInput();

        gameCam.update();
        renderer.setView(gameCam);

        for (Chef chef : chefList.allElems()){
            chef.update(dt);
        }
        for (ChoppingBoard choppingBoard : choppingBoards) {
            choppingBoard.update(dt, diffMulti, instantCook, noBurn);
        }
        for (Pan pan : pans) {
            pan.update(dt, diffMulti, instantCook, noBurn);
        }
        for (Oven oven : ovens){
            oven.update(dt, diffMulti, instantCook, noBurn);
        }

        world.step(1/60f, 6, 2);

    }

    /**
     * Initialises chefs as circular list and creates in world space. If the game is loading data and the data exists,
     * the chefs are generated from their preexisting locations and items being held. Save data is stored in {@link ChefDataStore}
     * @param chefCount Number of chefs to create in game
     */
    public void generateChefs(int chefCount){
        if (!loadGame || chefData==null){
            float locX=31.5F;
            float locY=38;
            float spacing = 96.5F/(chefCount-1);
            for (int i=0;i<chefCount;i++){
                Chef currentChef = new Chef(this.world,locX,locY);
                chefList.addElement(currentChef);
                locX+=spacing;
            }
        }
        else{
            for (ChefDataStore chef : chefData){
                Chef currentChef = new Chef(this.world,chef.getX()*MainGame.PPM,chef.getY()*MainGame.PPM);
                Deque<Sprite> holding = loadIngredients(chef.getHolding());
                currentChef.setStack(holding);
                chefList.addElement(currentChef);
            }
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
            int totalMoney = hud.updateScore(Boolean.TRUE, currentOrder.startTime, diffMulti);
            game.endScreen.score=totalMoney;
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
                game.endScreen.score = hud.updateScore(Boolean.FALSE, currentOrder.startTime, diffMulti);
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
     * Puts game into lose game state by setting the {@link MainGame} variables for screens to corresponding values.
     * It also passes the needed data to the {@link EndScreen}, to display the data.
     */
    public void loseGame(){
        game.inGame=false;
        game.isEndScreen =true;
        game.isPlayScreen=false;
        game.endScreen.win=false;
        game.endScreen.time= String.format("%02d:%02d",(int)(timeSecondsCount / 60), (int)(timeSecondsCount % 60));
        game.endScreen.score=hud.getScore();
        if (!game.scenarioMode){
            game.addMoney(hud.getScore());
        }
    }

    /**
     * Resets all variables and objects for replaying.
     */
    public void resetGame(){
        if (loadGame){
            new B2WorldCreator(world, map, this, stationItems);
        }
        else{
            new B2WorldCreator(world, map, this, null);
            stationItems = new ArrayList[((TiledMapTileLayer) map.getLayers().get(0)).getWidth()][((TiledMapTileLayer) map.getLayers().get(0)).getHeight()];
        }
        scenarioComplete = Boolean.FALSE;
        playerRep.reset();
        for (Chef chef : chefList.allElems()){
            if (chef.getTexture() != null) {chef.getTexture().dispose();}
            world.destroyBody(chef.b2body);
        }
        chefList = new CircularList<>();
        hud.dispose();
        hud=new HUD(game.batch);
        if (loadGame){loadGame();}
        else{
            scenarioComplete = Boolean.FALSE;
            createdOrder = Boolean.FALSE;
            chefCount=game.getChefCount();
            timeSeconds = 0f;
            timeSecondsCount = 0f;
            orderNum=0;
            currentOrder = null;
            switch (game.difficulty){
                case "Easy":
                    diffMulti =1.5f;
                    break;
                case "Medium":
                    diffMulti =1;
                    break;
                case "Hard":
                    diffMulti =0.67f;
                    break;
                default:
                    break;
            }
        }
        generateChefs(chefCount);
        controlledChef.notificationSetBounds("Down");

        game.isEndScreen =false;
    }

    /**
     * Main saving method - called when the window focus is lost - e.g. when closed. It saves all data for chefs and their
     * holding in {@link ChefDataStore} and {@link IngredientDataStore} respectively. It also stores the current order in {@link OrderDataStore}
     * and stores it all in {@link SaveDataStore}
     */
    public void saveGame(){
        //Save chef locations
        //Generate chefData
        ArrayList<ChefDataStore> chefData = new ArrayList<>();
        for (Chef chef : chefList.allElems()){
            ArrayList<IngredientDataStore> holding = new ArrayList<>();
            for (Sprite item : chef.getStack()){

                holding.add(saveIngredient(item));
            }
            ChefDataStore currentChefData = new ChefDataStore(chef.b2body.getPosition().x,  chef.b2body.getPosition().y, holding);
            chefData.add(currentChefData);
        }
        OrderDataStore order = null;
        if (currentOrder!=null){
            order = new OrderDataStore(currentOrder.orderImg.toString(),currentOrder.startTime, diffMulti);
        }
        saveStations();
        //Create save object
        SaveDataStore saveData = new SaveDataStore(chefData, orderNum, diffMulti,createdOrder,timeSeconds,timeSecondsCount, chefCount, hud.getScore(), playerRep,order, stationItems);
        //Save to file
        Json json = new Json();
        String dataString = json.toJson(saveData);
        FileHandle file;
        if (scenarioMode){
            file = Gdx.files.local(Constants.DATA_SCENARIO_PATH);
        }
        else{
            file = Gdx.files.local(Constants.DATA_ENDLESS_PATH);
        }

        file.writeString(dataString, false);
    }

    /**
     * Converts an ingredient or recipe object into {@link IngredientDataStore} which can be serialized and saved and loaded.
     * @param ingredient Ingredient to be saved
     * @return Serializable ingredient object store
     */
    public IngredientDataStore saveIngredient(Sprite ingredient){
        if (ingredient instanceof Ingredient){
            Ingredient itemIngredient = (Ingredient) ingredient;
            String name=itemIngredient.getClass().getSimpleName();
            Map<String, Float> timers = itemIngredient.getTimers();
            Map<String, Boolean> completed = itemIngredient.getCompleted();
            int skin = itemIngredient.getSkin();
            return new IngredientDataStore(name, timers, completed, skin,0);
        }
        Recipe recipe = (Recipe) ingredient;
        return new IngredientDataStore(recipe.getClass().getSimpleName());

    }

    /**
     * Iterates through all {@link CookingStation} that can store an item and saves any present items to {@link ArrayList}.
     * Used for saving and further stored in {@link SaveDataStore}
     */
    public void saveStations(){
        stationItems = new ArrayList[((TiledMapTileLayer) map.getLayers().get(0)).getWidth()][((TiledMapTileLayer) map.getLayers().get(0)).getHeight()];
        for (Pan pan : pans){
            storeStationData(pan.getCurrentIngredient(), pan.getTimer(), pan.getX(), pan.getY());
        }
        for (ChoppingBoard board : choppingBoards){
            storeStationData(board.getCurrentIngredient(), board.getTimer(), board.getX(), board.getY());
        }
        for (Oven oven : ovens){
            storeStationData(oven.getCurrentIngredient(), oven.getTimer(), oven.getX(), oven.getY());
        }
        for (PlateStation plateStation : plateStations ){
            Recipe recipe = plateStation.getCompletedRecipe();
            if (recipe != null){
                IngredientDataStore item = new IngredientDataStore(recipe.getClass().getSimpleName());
                int x = (int)(plateStation.getX()*MainGame.PPM-8)/MainGame.TILE_SIZE;
                int y = (int)(plateStation.getY()*MainGame.PPM-8)/MainGame.TILE_SIZE;
                ArrayList<IngredientDataStore> ingredientData = new ArrayList<>();
                ingredientData.add(item);
                stationItems[x][y]=ingredientData;
            }
            else if (plateStation.getPlate().size() > 0){
                ArrayList<IngredientDataStore> ingredientData = new ArrayList<>();

                for (Object ingredient : plateStation.getPlate()){
                    IngredientDataStore currentIngredient = saveIngredient((Sprite) ingredient);
                    ingredientData.add(currentIngredient);
                }
                int x = (int)(plateStation.getX()*MainGame.PPM-8)/MainGame.TILE_SIZE;
                int y = (int)(plateStation.getY()*MainGame.PPM-8)/MainGame.TILE_SIZE;
                stationItems[x][y]=ingredientData;
            }
        }
    }

    /**
     * stores the stations data
     *
     * @param currentIngredient2 the current ingredient on the station
     * @param timer the current stations timer
     * @param x2 x position of the station
     * @param y2 y position of the station
     */
    private void storeStationData(Ingredient currentIngredient2, float timer, float x2, float y2) {
        if (currentIngredient2 != null){
            IngredientDataStore item = new IngredientDataStore(currentIngredient2.getClass().getSimpleName(), currentIngredient2.getTimers(), currentIngredient2.getCompleted(), currentIngredient2.getSkin(), timer);
            int x = (int)(x2 * MainGame.PPM-8)/MainGame.TILE_SIZE;
            int y = (int)(y2 *MainGame.PPM-8)/MainGame.TILE_SIZE;
            ArrayList<IngredientDataStore> ingredientData = new ArrayList<>();
            ingredientData.add(item);
            stationItems[x][y]=ingredientData;
        }
    }

    /**
     * Main loading method - called when the screen is initialised to regenerate all data if the save file exists. This data is
     * stored locally and used in other methods to fully load game.
     */
    public void loadGame(){
        Json json = new Json();
        FileHandle file;
        if (scenarioMode){
            file = Gdx.files.local(Constants.DATA_SCENARIO_PATH);
        }
        else{
            file = Gdx.files.local(Constants.DATA_ENDLESS_PATH);
        }
        String dataRaw = file.readString();
        SaveDataStore saveData = json.fromJson(SaveDataStore.class, dataRaw);
        chefData = saveData.getChefData();
        orderNum = saveData.getOrderCount();
        diffMulti = saveData.getDiffMult();
        createdOrder = saveData.getCreatedOrder();
        timeSeconds = saveData.getTimeSeconds();
        timeSecondsCount = saveData.getTimeSecondsCount();
        chefCount= saveData.getChefCount();
        playerRep=saveData.getRep();
        hud.setHud(timeSecondsCount, saveData.getScore(), playerRep.getRep());
        hud.updateOrder(scenarioComplete,orderNum);
        stationItems = saveData.getStationItems();
        OrderDataStore orderData = saveData.getOrder();
        if (orderData != null) {
            currentOrder = ordersInterface.loadOrder(saveData.getOrder());
            createdOrder = true;
        }
        else{
            createdOrder=false;
        }
    }

    /**
     * Converts an {@link ArrayList} of {@link IngredientDataStore} to their corresponding ingredient objects to be used
     * for loading chefs or station items.
     * @param ingredientData an Array of serializable ingredient objects
     * @return Deque of all serializable ingredients as corresponding object
     */
    public Deque<Sprite> loadIngredients(ArrayList<IngredientDataStore> ingredientData){
        Deque<Sprite> holding = new ArrayDeque<>();
        for (IngredientDataStore ingredient : ingredientData){

            if (ingredient.isRecipe()){
                Recipe currentRecipe;
                switch (ingredient.getName()){
                    case Constants.BURGER_RECIPE:
                        currentRecipe = new BurgerRecipe();
                        break;
                    case Constants.COOKED_PIZZA_RECIPE:
                        currentRecipe = new CookedPizzaRecipe();
                        break;
                    case Constants.JACKET_POTATO_RECIPE:
                        currentRecipe = new JacketPotatoRecipe();
                        break;
                    case Constants.SALAD_RECIPE:
                        currentRecipe = new SaladRecipe();
                        break;
                    case Constants.UNCOOKED_PIZZA_RECIPE:
                        currentRecipe = new UncookedPizzaRecipe();
                        break;
                    default:
                        currentRecipe=null;
                        break;
                }
                holding.add(currentRecipe);
            }
            else{
                Ingredient currentIngredient = B2WorldCreator.getIngredient(ingredient);
                holding.add(currentIngredient);
            }


        }
        return holding;

    }

    /**
     * Generates a new random powerup in playable area (with a random location).
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

        while (centreIsland.contains(x, y)){
            x = MainGame.TILE_SIZE/MainGame.PPM + r.nextFloat()*(xMax-min);
            y = MainGame.TILE_SIZE/MainGame.PPM + r.nextFloat()*(yMax-min);
        }



        //Random powerup
        int totalPowerups = 5;
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
            case 3:
                newPowerup = new NoBurnPowerup(x, y);
                break;
            case 4:
                newPowerup = new ExtraChefPowerup(x, y);
                break;
            default:
                newPowerup=new SpeedPowerup(x,y);
                break;
        }
        powerups.add(newPowerup);
    }

    /**
     * Checks for any overlap of controlled chef and powerups. Removes and activates if so. Uses {@link Rectangle} stored in
     * {@link Chef} and {@link Powerup}
     */
    public void checkPowerupCollisions(){
        Iterator<Powerup> itr = powerups.iterator();
        while (itr.hasNext()){
            Powerup currentPowerup = itr.next();
            if (currentPowerup.collisionRect.overlaps(controlledChef.collisionRect)){
                String powerupName = currentPowerup.getClass().getSimpleName();
                if (powerupName.equals("ExtraChefPowerup") && chefList.getCurrentSize()==chefCount+1){
                    powerupFinish=timeSecondsCount+15f;
                }
                else{
                    resetPowerups(); //So that powerups cannot be combined
                    activatePowerups(powerupName);
                }
                itr.remove();
            }
        }
    }

    /**
     * Enables the {@link Powerup}'s ability once collided
     * @param name Simple class name of powerup
     */
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
            case "NoBurnPowerup":
                noBurn=true;
                powerupFinish=timeSecondsCount+10f;
                break;
            case "ExtraChefPowerup":
                Chef powerChef = new Chef(this.world,MainGame.TILE_SIZE*7,MainGame.TILE_SIZE*7);
                powerChef.update(Gdx.graphics.getDeltaTime());
                chefList.addElement(powerChef);
                powerupFinish=timeSecondsCount+15f;
                break;
            default:
                Gdx.app.log("Error","wrong name");
        }
    }

    /**
     * Resets powerups if another powerup is picked up or timer runs out (for time based powerups)
     */
    private void resetPowerups(){
        movementSpeed=1f;
        instantCook=false;
        noBurn=false;

        if (chefList.getCurrentSize() > chefCount){ //If powerchef is active
            Chef removeChef = chefList.removeElement();
            if (controlledChef.equals(removeChef)){ //Give control to a different chef
                controlledChef = chefList.nextItem();
            }
            //Clear stack into another chef
            Deque<Sprite> holding = removeChef.getStack();
            Iterator<Sprite> holdingIterator = holding.descendingIterator();
            while (holdingIterator.hasNext()) {
                Sprite item = holdingIterator.next();
                boolean placed=false;
                int i=0;
                while (!placed){
                    Chef chef = chefList.allElems().get(i);
                    if (chef.getHoldingSize() <3){
                        chef.pickUp(item);
                        placed=true;
                    }
                    i++;
                }
            }
            world.destroyBody(removeChef.b2body);


        }
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
            currentOrder = ordersInterface.newOrder(hud.getTime(), diffMulti);
            hud.updateOrder(Boolean.FALSE, orderNum);
        }
        float period = 1f;
        if (timeSeconds > period) {
            timeSeconds -= period;
            hud.updateTime(scenarioComplete);
            if (ThreadLocalRandom.current().nextInt(0, (int)(35/ diffMulti)) == 0){
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
        game.batch.setProjectionMatrix(gameCam.combined);
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
                choppingBoard.drawProgressBar(game.batch,Constants.CHOPPING_BOARD, diffMulti);
            }
        }

        for (Pan pan : pans) {
            Ingredient currentIngredient = pan.getCurrentIngredient();
            if (currentIngredient != null) {
                currentIngredient.create(pan.getX(), pan.getY(), game.batch);
                pan.drawProgressBar(game.batch,Constants.PAN, diffMulti);
            }
        }

        for (Oven oven : ovens){
            Ingredient currentIngredient = oven.getCurrentIngredient();
            Recipe  currentRecipe = oven.getCurrentRecipe();
            if (currentIngredient != null) {
                currentIngredient.create(oven.getX(),oven.getY(),game.batch);
                oven.drawProgressBar(game.batch,Constants.OVEN, diffMulti);
            } else if (currentRecipe != null){
                currentRecipe.create(oven.getX(),oven.getY(),game.batch);
                oven.drawProgressBar(game.batch,Constants.OVEN, diffMulti);
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
        gamePort.update(width, height);
    }

    @Override
    public void pause(){
        saveGame();
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
