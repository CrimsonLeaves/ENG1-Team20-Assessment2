package com.team13.piazzapanic;

import Recipe.Order;
import Sprites.PlateStation;
import Tools.OrderDataStore;
import com.badlogic.gdx.graphics.Texture;

import java.util.concurrent.ThreadLocalRandom;

public class Orders {
    int totalRecipes;

    /**
     * Constructor for Orders
     */
    public Orders(){
        totalRecipes=2;
    }

    /**
     * Generates a new random order
     * @param startTime Time when order is placed
     * @return Order object of new order
     */
    public Order newOrder(int startTime, float diff){
        int randomNum = ThreadLocalRandom.current().nextInt(0, totalRecipes);
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        switch (randomNum){
            case 0:
                return new Order(PlateStation.getRecipe("Burger"), burger_recipe,startTime,60f*diff);
            case 1:
                return new Order(PlateStation.getRecipe("Salad"), salad_recipe,startTime,60f*diff);
            default:
                return null;
        }
    }
    public Order loadOrder(OrderDataStore order){
        String orderType = order.getOrderType();
        int startTime = order.getStartTime();
        float diff = order.getDiff();
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        switch (orderType){
            case "Food/burger_recipe.png":
                return new Order(PlateStation.getRecipe("Burger"), burger_recipe,startTime,60f*diff);
            case "Food/salad_recipe.png":
                return new Order(PlateStation.getRecipe("Salad"), salad_recipe,startTime,60f*diff);
            default:
                return null;
        }
    }
}
