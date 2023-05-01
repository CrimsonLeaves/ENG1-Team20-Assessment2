package Sprites;

import Ingredients.*;
import Recipe.*;
import Tools.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.*;

/**
 * PlateStation class represents a Plate Station in the game where the player can drop or pick up ingredients.
 *
 * It extends InteractiveTileObject and provides the functionality of checking if a recipe is complete,
 * and getting the completed recipe or individual ingredients from the plate.
 *
 * It also has a List of ingredients placed on the plate and two static recipes (burger and salad).
 * The checkRecipeCreated method checks if the ingredients on the plate match any of the two recipes.
 */

public class PlateStation extends InteractiveTileObject {

    /** List of ingredients placed on the plate */
    private final List<Ingredient> plate;

    private static Map<String, Recipe> recipes;

    /** Recipe that has been completed on the plate */
    private Recipe recipeDone;

    /**
     * Constructor for the PlateStation class
     *
     * @param world the world object representing the game world
     * @param map the TiledMap object representing the game map
     * @param bdef the BodyDef object representing the plate's physical body
     * @param rectangle the Rectangle object representing the plate's hitbox
     */
    public PlateStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        this.plate = new ArrayList<>();
        recipes = new HashMap<>();
        recipes.put(Constants.SALAD,new SaladRecipe());
        recipes.put(Constants.BURGER, new BurgerRecipe());
        recipes.put(Constants.PIZZA, new UncookedPizzaRecipe());
        recipes.put(Constants.JACKET_POTATO, new JacketPotatoRecipe());
        this.recipeDone = null;
    }

    /**
     * Adds an ingredient to the plate.
     *
     * @param ing the Ingredient object to be added to the plate
     */
    public void dropItem(Ingredient ing) {
        plate.add(ing);
        checkRecipeCreated();
    }

    /**
     * Check if the ingredients on the plate match any of the two recipes (burger or salad) then clear the plate
     * if a recipe is found and set the recipeDone
     */
    public void checkRecipeCreated(){
        for (Recipe recipe: recipes.values()) {
            ArrayList<Ingredient> recipeIngredients = new ArrayList<>(recipe.getIngredients());
            if(plate.size() == recipeIngredients.size()){
                for(Ingredient ing : plate){
                    if(!ing.isAllCompleted()){
                        return;
                    }
                    for(int i = 0; i < recipeIngredients.size(); i++) {
                      if(ing.getClass().equals(recipeIngredients.get(i).getClass())){
                          recipeIngredients.remove(i);
                          break;
                      }
                    }
                }
                if(recipeIngredients.isEmpty()){
                    plate.clear();
                    recipeDone = recipe;
                    return;
                }
            }
        }
    }

    /**
     * getter for the recipes in the plate station
     * @param recipe the recipe to be returned
     * @return the corresponding recipe
     */
    public static Recipe getRecipe(String recipe){
        return recipes.get(recipe);
    }

    /**
     * Gets the ingredients on the plate.
     *
     * @return An ArrayList of ingredients on the plate.
     */

    public ArrayList<Ingredient> getPlate(){
        return (ArrayList<Ingredient>) this.plate;
    }

    /**
     * Gets the completed recipe, if any, on the plate.
     *
     * @return The completed recipe on the plate, or null if no recipe is completed.
     */
    public Recipe getCompletedRecipe(){
        return recipeDone;
    }

    /**
     * Sets the completed recipe on the plate - used for loading
     * @param recipeDone The completed recipe to be put on plate
     */
    public void setRecipeDone(Recipe recipeDone) {
        this.recipeDone = recipeDone;
    }

    /**
     * Gets the x-coordinate of the plate station.
     *
     * @return The x-coordinate of the plate station.
     */
    public float getX(){
        return super.bdefNew.position.x;
    }

    /**
     * Gets the y-coordinate of the plate station.
     *
     * @return The y-coordinate of the plate station.
     */
    public float getY(){
        return super.bdefNew.position.y;
    }

    /**
     * Picks up an item from the plate. If a recipe is completed on the plate, the completed recipe is returned.
     * If no recipe is completed, the last ingredient on the plate is removed and returned.
     *
     * @return A Recipe object if a recipe is completed, or an Ingredient object if no recipe is completed.
     */
    public Sprite pickUpItem() {
        if (recipeDone != null){
            Recipe temp = recipeDone;
            recipeDone = null;
            return temp;
        } else {
            Ingredient item = plate.get(plate.size()-1);
            plate.remove(plate.size()-1);
            return item;
        }
    }
}