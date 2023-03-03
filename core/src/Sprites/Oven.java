package Sprites;

import Ingredients.Ingredient;
import Recipe.Recipe;
import Recipe.CookedPizzaRecipe;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Oven extends CookingStation{

    Recipe currentRecipe;

    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world     The playable world.
     * @param map       The tiled map.
     * @param bdef      The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public Oven(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        currentRecipe = null;
    }

    public Recipe getCurrentRecipe(){return currentRecipe;}

    public void setCurrentRecipe(Recipe recipe){
        if(currentIngredient == null){
            currentRecipe = recipe;
        }
    }

    @Override
    public void setCurrentIngredient(Ingredient ingredient){
        if(currentRecipe == null){
            currentIngredient = ingredient;
        }
    }

    @Override
    public void update(float dt){
        if(currentIngredient != null) {
            timer += dt;
            if (timer > currentIngredient.getTimer("Oven")
                    && !currentIngredient.isCompleted("Oven")) {
                //line 37 is so skin (Ingredient.java) only gets incremented once
                currentIngredient.setCompleted("Oven");
            }
        } else if(currentRecipe != null){
            timer +=dt;
            //hardcoded to deal with cooking a pizza
            if (timer > 5){
                currentRecipe = new CookedPizzaRecipe(); //cooked pizza
            }
        }
    }

}
