package Tools;

import java.util.ArrayList;

public class ChefDataStore {
    private float x;
    private float y;
    private ArrayList<IngredientDataStore> holding;

    /**
     * No argument constructor with default files required for serialization
     */
    public ChefDataStore(){
        this.x=31.5F;
        this.y=38F;
        holding = new ArrayList<>();
    }

    /**
     * Constructor for creating new chef storage
     * @param x chef's x location
     * @param y chef's y location
     * @param holding List of converted holding items
     */
    public ChefDataStore(float x, float y, ArrayList<IngredientDataStore> holding){
        this.x=x;
        this.y=y;
        this.holding=holding;
    }
    //Getters and setters
    public float getX() {return x;}
    public void setX(float x) {this.x=x;}
    public float getY() {return y;}
    public void setY(float y) {this.y=y;}
    public void setHolding(ArrayList<IngredientDataStore> holding) {this.holding = holding;}
    public ArrayList<IngredientDataStore> getHolding() {return holding;}
}
