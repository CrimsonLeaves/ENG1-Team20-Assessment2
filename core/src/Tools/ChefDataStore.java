package Tools;

import java.util.ArrayList;

public class ChefDataStore {
    private float x;
    private float y;
    private ArrayList<IngredientDataStore> holding;
    public ChefDataStore(){
        this.x=31.5F;
        this.y=38F;
        holding = new ArrayList<>();
    }
    public ChefDataStore(float x, float y, ArrayList<IngredientDataStore> holding){
        this.x=x;
        this.y=y;
        this.holding=holding;
    }
    public float getX() {return x;}
    public void setX(float x) {this.x=x;}
    public float getY() {return y;}
    public void setY(float y) {this.y=y;}
    public void setHolding(ArrayList<IngredientDataStore> holding) {this.holding = holding;}
    public ArrayList<IngredientDataStore> getHolding() {return holding;}
}
