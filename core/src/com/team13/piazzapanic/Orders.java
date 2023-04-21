package com.team13.piazzapanic;

import Recipe.Order;
import Recipe.*;
import Tools.OrderDataStore;
import Tools.Constants;
import com.badlogic.gdx.graphics.Texture;

import java.util.concurrent.ThreadLocalRandom;

public class Orders {
    int totalRecipes;
    Texture burger_recipe = new Texture(Constants.BURGER_RECIPE_PATH);
    Texture salad_recipe = new Texture(Constants.SALAD_RECIPE_PATH);
    Texture pizza_recipe = new Texture(Constants.PIZZA_RECIPE_PATH);
    Texture jacket_potato_recipe = new Texture(Constants.JACKET_POTATO_RECIPE_PATH);

    /**
     * Constructor for Orders
     */
    public Orders(){
        totalRecipes=4;
    }

    /**
     * Generates a new random order
     * @param startTime Time when order is placed
     * @return Order object of new order
     */
    public Order newOrder(int startTime, float diff){
        int randomNum = ThreadLocalRandom.current().nextInt(0, totalRecipes);

        switch (randomNum){
            case 0:
                return new Order(new BurgerRecipe(), burger_recipe,startTime,60f*diff);
            case 1:
                return new Order(new SaladRecipe(), salad_recipe,startTime,60f*diff);
            case 2:
                return new Order(new CookedPizzaRecipe(), pizza_recipe, startTime, 60f*diff);
            case 3:
                return new Order(new JacketPotatoRecipe(), jacket_potato_recipe, startTime, 60f*diff);
            default:
                return null;
        }
    }
    public Order loadOrder(OrderDataStore order){
        String orderType = order.getOrderType();
        int startTime = order.getStartTime();
        float diff = order.getDiff();
        switch (orderType){
            case Constants.BURGER_RECIPE_PATH:
                return new Order(new BurgerRecipe(), burger_recipe,startTime,60f*diff);
            case Constants.SALAD_RECIPE_PATH:
                return new Order(new SaladRecipe(), salad_recipe,startTime,60f*diff);
            case Constants.PIZZA_RECIPE_PATH:
                return new Order(new CookedPizzaRecipe(), pizza_recipe, startTime, 60f*diff);
            case "Food/jacket_potato_recipe.png":
                return new Order(new JacketPotatoRecipe(), jacket_potato_recipe, startTime, 60f*diff);
            default:
                return null;
        }
    }
}
