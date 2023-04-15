package Tools;

import java.util.HashMap;
import java.util.Map;

public class IngredientDataStore {
    String name;
    Map<String, Float> timers;
    Map<String, Boolean> completed;
    int skin;
    boolean recipe;


    public IngredientDataStore(){
        name="";
        timers=new HashMap<>();
        completed=new HashMap<>();
        skin=0;
        recipe=false;
    }

    public IngredientDataStore(String name,Map<String, Float> timers, Map<String, Boolean> completed, int skin){
        this.name=name;
        this.timers=timers;
        this.completed=completed;
        this.skin=skin;
        recipe=false;
    }
    public IngredientDataStore(String name){
        this.name=name;
        timers=null;
        completed=null;
        skin=0;
        recipe=true;
    }
    //name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    //timers
    public Map<String, Float> getTimers() {return timers;}
    public void setTimers(Map<String, Float> timers) {this.timers = timers;}
    //completed
    public Map<String, Boolean> getCompleted() {return completed;}
    public void setCompleted(Map<String, Boolean> completed) {this.completed = completed;}
    //skin
    public int getSkin() {return skin;}
    public void setSkin(int skin) {this.skin = skin;}
    //failed
    public boolean isRecipe() {return recipe;}
    public void setRecipe(boolean recipe) {this.recipe = recipe;}
}
