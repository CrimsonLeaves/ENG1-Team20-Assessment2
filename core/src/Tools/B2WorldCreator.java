package Tools;

import Ingredients.*;
import Recipe.Recipe;
import Recipe.*;
import Sprites.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;
import com.team13.piazzapanic.PlayScreen;

import java.util.ArrayList;

/**
 * B2WorldCreator is a class used to create Box2D World objects from a TiledMap.
 * This class uses the map objects to create various objects like worktop, plates,
 * chopperboard, bin, etc. based on the name assigned to the objects in the TiledMap.
 *
 * The class is instantiated with a World object, TiledMap object and PlayScreen object.
 * It then uses the first layer of the TiledMap to create the objects and assign their
 * positions. The objects are created as BodyDef objects and are passed to different sprite
 * classes, where they are further defined and added to the world.
 *
 */
public class B2WorldCreator {
    ArrayList<IngredientDataStore>[][] items;

/**
 * Constructor method for B2WorldCreator. It accepts a World, TiledMap and PlayScreen
 * objects. The method then iterates over the cells in the first layer of the TiledMap and
 * uses the map objects to create various objects like worktop, plates, chopperboard,
 * bin, etc. based on the name assigned to the objects in the TiledMap.
 *
 * The objects are created as BodyDef objects and are passed to different sprite classes,
 * where they are further defined and added to the world.
 *
 * @param world The Box2D World object.
 * @param map The TiledMap object.
 * */

    public B2WorldCreator(World world, TiledMap map, PlayScreen screen, ArrayList<IngredientDataStore>[][] items) {
        this.items=items;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) {
                    continue;
                }

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1)
                    continue;

                MapObject mapObject = cellObjects.get(0);
                RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                Rectangle rectangle = rectangleObject.getRectangle();

                BodyDef bdef = new BodyDef();
                float position_x = x * MainGame.TILE_SIZE + MainGame.TILE_SIZE / 2f + rectangle.getX()
                        - (MainGame.TILE_SIZE - rectangle.getWidth()) / 2f;
                float position_y = y * MainGame.TILE_SIZE + MainGame.TILE_SIZE / 2f + rectangle.getY()
                        - (MainGame.TILE_SIZE - rectangle.getHeight()) / 2f;
                bdef.position.set(position_x / MainGame.PPM, position_y / MainGame.PPM);
                bdef.type = BodyDef.BodyType.StaticBody;

                if(mapObject.getName() != null){

                    if (mapObject.getName().equals("bin")) {
                        new Bin(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("worktop")) {
                        new Worktop(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("chopping_board")) {
                        ChoppingBoard tempStation = new ChoppingBoard(world, map, bdef, rectangle);
                        addIngredient(x, y, tempStation);
                        screen.choppingBoards.add(tempStation);
                    } else if (mapObject.getName().equals("plate")) {
                        PlateStation tempPlateStat = new PlateStation(world, map, bdef, rectangle);
                        if (items != null) {
                            if (items[x][y] != null) {
                                if (items[x][y].size()> 0) {
                                    if (items[x][y].get(0).isRecipe()) {
                                        IngredientDataStore rawRecipe = items[x][y].get(0);
                                        Recipe currentRecipe;
                                        switch (rawRecipe.getName()) {
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
                                                currentRecipe = null;
                                                break;
                                        }
                                        tempPlateStat.setRecipeDone(currentRecipe);
                                    }else {
                                        for (IngredientDataStore ingredient : items[x][y]) {
                                            tempPlateStat.dropItem(generateIngredient(ingredient));
                                        }
                                    }
                                }
                            }
                        }
                        screen.plateStations.add(tempPlateStat);
                    } else if (mapObject.getName().equals("tomato")) {
                        new TomatoStation(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("lettuce")) {
                        new LettuceStation(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("buns")) {
                        new BunsStation(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("onion")) {
                        new OnionStation(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("pan1")) {
                        Pan tempStation = new Pan(world, map, bdef, rectangle);
                        addIngredient(x, y, tempStation);
                        screen.pans.add(tempStation);
                    } else if (mapObject.getName().equals("steak")) {
                        new SteakStation(world, map, bdef, rectangle);
                    } else if (mapObject.getName().equals("pan2")) {
                        Pan tempStation = new Pan(world, map, bdef, rectangle);
                        addIngredient(x, y, tempStation);
                        screen.pans.add(tempStation);
                    } else if (mapObject.getName().equals("completed_dish")) {
                        screen.cdStations.add(new CompletedDishStation(world, map, bdef, rectangle));
                    } else if (mapObject.getName().equals("order_top")) {
                        PlayScreen.trayX = rectangle.x;
                        PlayScreen.trayY = rectangle.y;
                    } else if(mapObject.getName().equals("cheese")){
                        new CheeseStation(world,map,bdef,rectangle);
                    } else if(mapObject.getName().equals("dough")){
                        new DoughStation(world,map,bdef,rectangle);
                    } else if(mapObject.getName().equals("potato")){
                        new PotatoStation(world,map,bdef,rectangle);
                    } else if(mapObject.getName().equals("beans")){
                        new BeansStation(world,map,bdef,rectangle);
                    } else if(mapObject.getName().equals(Constants.OVEN)){
                        Oven tempStation = new Oven(world, map, bdef, rectangle);
                        addIngredient(x, y, tempStation);
                        screen.ovens.add(tempStation);
                    }
                }


            }
        }
    }
    private void addIngredient(int x, int y, CookingStation tile){
        if (items == null){
            return;
        }
        if (items[x][y] == null){
            return;
        }
        Ingredient currentIngredient;
        IngredientDataStore item = items[x][y].get(0);
        currentIngredient=generateIngredient(item);
        tile.setCurrentIngredient(currentIngredient);
        tile.setTimer(item.getCurrentTimer());
    }
    private Ingredient generateIngredient(IngredientDataStore item){
        Ingredient currentIngredient;
        if (item == null){
            return null;
        }
        currentIngredient = getIngredient(item);
        return currentIngredient;
    }

    public static Ingredient getIngredient(IngredientDataStore item) {
        Ingredient currentIngredient;
        switch (item.getName()){
            case "Beans":
                currentIngredient=new Beans(item.getTimers(),item.getCompleted());
                break;
            case "Bun":
                currentIngredient=new Bun(item.getTimers(),item.getCompleted());
                break;
            case "Cheese":
                currentIngredient=new Cheese(item.getTimers(),item.getCompleted());
                break;
            case "Dough":
                currentIngredient=new Dough(item.getTimers(),item.getCompleted());
                break;
            case "Onion":
                currentIngredient=new Onion(item.getTimers(),item.getCompleted());
                break;
            case "Lettuce":
                currentIngredient=new Lettuce(item.getTimers(),item.getCompleted());
                break;
            case "Potato":
                currentIngredient=new Potato(item.getTimers(),item.getCompleted());
                break;
            case "Steak":
                currentIngredient=new Steak(item.getTimers(),item.getCompleted());
                break;
            case "Tomato":
                currentIngredient=new Tomato(item.getTimers(),item.getCompleted());
                break;
            case "FailedIngredient":
                currentIngredient=new FailedIngredient();
                break;
            default:
                currentIngredient=null;
                break;
        }
        currentIngredient.setSkin(item.getSkin());
        return currentIngredient;
    }
}
