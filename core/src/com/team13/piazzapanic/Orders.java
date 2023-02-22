package com.team13.piazzapanic;

import Recipe.Order;
import Sprites.PlateStation;
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
    public Order newOrder(int startTime){
        int randomNum = ThreadLocalRandom.current().nextInt(0, totalRecipes);
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        switch (randomNum){
            case 0:
                return new Order(PlateStation.burgerRecipe, burger_recipe,startTime);
            case 1:
                return new Order(PlateStation.saladRecipe, salad_recipe,startTime);
            default:
                return null;
        }
    }
}
