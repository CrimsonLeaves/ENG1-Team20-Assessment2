package com.team13.piazzapanic;

import Ingredients.Ingredient;
import Recipe.Recipe;
import Sprites.*;
import Recipe.Order;
import Tools.B2WorldCreator;
import Tools.CircularList;
import Tools.WorldContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
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
    private final HUD hud;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private final World world;
    private CircularList<Chef> chefList;
    private int chefCount = 3;
    private Chef controlledChef;

    public ArrayList<Order> ordersArray;
    private Orders ordersInterface = new Orders();
    public Order currentOrder;
    public int orderNum=0;
    public int totalOrders=5;

    public PlateStation plateStation;

    public ArrayList<ChoppingBoard> choppingBoards = new ArrayList<>();

    public ArrayList<Pan> pans = new ArrayList<>();


    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;

    private float timeSecondsCount = 0f;

    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */

    public PlayScreen(MainGame game){
        this.game = game;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD for score & time
        hud = new HUD(game.batch);
        //currentOrder =ordersInterface.newOrder(0);
        // create orders hud
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
        new B2WorldCreator(world, map, this);
        chefList = new CircularList<Chef>(chefCount);
        generateChefs(chefCount);
        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");

        ordersArray = new ArrayList<>();

    }

    @Override
    public void show(){

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
            controlledChef.b2body.setLinearVelocity(xVelocity, yVelocity);
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
                    case "Sprites.PlateStation":
                        if(plateStation.getCompletedRecipe() != null){
                            controlledChef.pickUpItemFrom(tile);
                        } else if (recipe == null && ingredient != null){
                            controlledChef.dropItemOn(tile, ingredient);
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
                    case "Sprites.Bin":
                        if(ingredient != null || recipe != null){
                            controlledChef.putDown();
                        }
                        break;
                    case "Sprites.CompletedDishStation":
                        if (recipe != null){
                            if(recipe.getClass().equals(currentOrder.recipe.getClass())){
                                controlledChef.dropItemOn(tile, recipe);
                                currentOrder.orderComplete = true;
                                if(orderNum >= totalOrders){
                                    scenarioComplete = Boolean.TRUE;
                                }
                            }
                        }
                }

            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)
                    && controlledChef.getTouchingTile() != null){ //make instance of plate station when refactoring that
            InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
            String tileName = tile.getClass().getName();
            if(tileName.equals("Sprites.PlateStation")){
                if(plateStation.getPlate().size() > 0){
                    controlledChef.pickUpItemFrom(tile);
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
            choppingBoard.update(dt);
        }
        for (Pan pan : pans) {
            pan.update(dt);
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
    public boolean canSwitchChefs(){
        for (Chef chef : chefList.allElems()){
            if (!chef.getUserControlChef()){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * Creates the orders randomly and adds to an array, updates the HUD.
     */
    public void createOrder() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        Order order;

        for(int i = 0; i<5; i++){
            if(randomNum==1) {
                order = new Order(PlateStation.burgerRecipe, burger_recipe);
            }
            else {
                order = new Order(PlateStation.saladRecipe, salad_recipe);
            }
            ordersArray.add(order);
            randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        }
        hud.updateOrder(Boolean.FALSE, 1);
    }

    /**
     * Updates the orders as they are completed, or if the game scenario has been completed.
     */
    public void updateOrder(){
        if(scenarioComplete==Boolean.TRUE) {
            hud.updateScore(Boolean.TRUE, (6 - ordersArray.size()) * 35);
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if(ordersArray.size() != 0) {
            if (ordersArray.get(0).orderComplete) {
                hud.updateScore(Boolean.FALSE, (6 - ordersArray.size()) * 35);
                ordersArray.remove(0);
                hud.updateOrder(Boolean.FALSE, 6 - ordersArray.size());
                return;
            }
            ordersArray.get(0).create(trayX, trayY, game.batch);
        }
    }

    /**
     * Called each frame to check if order has been completed and update HUD
     */
    public void checkOrder(){
        if (scenarioComplete==Boolean.TRUE){
            hud.updateScore(Boolean.TRUE, currentOrder.startTime);
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if (currentOrder != null){
            if (currentOrder.orderComplete == Boolean.TRUE){
                //orderNum++;
                hud.updateScore(Boolean.FALSE, currentOrder.startTime);
                currentOrder = null;
                createdOrder = Boolean.FALSE;
                hud.updateOrder(Boolean.FALSE, orderNum);
                return;
            }
            currentOrder.create(trayX,trayY,game.batch);
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

        if(Math.round(timeSecondsCount % 10) == 5 && createdOrder == Boolean.FALSE){
            createdOrder = Boolean.TRUE;
            orderNum++;
            currentOrder=ordersInterface.newOrder(hud.getTime());
        }
        float period = 1f;
        if(timeSeconds > period) {
            timeSeconds -= period;
            hud.updateTime(scenarioComplete);
        }

        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        //updateOrder();
        checkOrder();

        for (Chef chef : chefList.allElems()){
            chef.create(game.batch);
        }
        controlledChef.drawNotification(game.batch);
        if (plateStation.getPlate().size() > 0){
            for(Object ing : plateStation.getPlate()){
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(plateStation.getX(), plateStation.getY(),game.batch);
            }
        } else if (plateStation.getCompletedRecipe() != null){
            Recipe recipeNew = plateStation.getCompletedRecipe();
            recipeNew.create(plateStation.getX(), plateStation.getY(), game.batch);
        }

        // make a function
        for (ChoppingBoard choppingBoard : choppingBoards) {
            Ingredient currentingredient = choppingBoard.getCurrentIngredient();
            if (currentingredient != null) {
                currentingredient.create(choppingBoard.getX(), choppingBoard.getY(), game.batch);
            }
        }

        for (Pan pan : pans) {
            Ingredient currentingredient = pan.getCurrentIngredient();
            if (currentingredient != null) {
                currentingredient.create(pan.getX(), pan.getY(), game.batch);
            }
        }
        for (Chef chef : chefList.allElems()) {
            if (chef.previousInHandRecipe != null){
                chef.displayIngDynamic(game.batch);
            }
        }
        game.batch.end();
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
    }
}
