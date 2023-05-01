package Tools;

import java.util.HashMap;
import java.util.Map;

public class IngredientDataStore {
    private String name;
    private Map<String, Float> timers;
    private Map<String, Boolean> completed;
    private int skin;
    private boolean recipe;
    private float currentTimer;


    /**
     * No argument constructor with default files required for serialization
     */
    public IngredientDataStore(){
        name = "";
        timers = new HashMap<>();
        completed = new HashMap<>();
        skin = 0;
        recipe = false;
        currentTimer = 0;
    }

    /**
     * Constructor for ingredient storage - used for saving and loading ingredient data
     * @param name Ingredient name
     * @param timers Station timers for ingredient
     * @param completed Map of completed station
     * @param skin current skin index
     * @param currentTimer current timer if currently on station
     */
    public IngredientDataStore(String name,Map<String, Float> timers, Map<String, Boolean> completed, int skin, float currentTimer){
        this.name=name;
        this.timers=timers;
        this.completed=completed;
        this.skin=skin;
        recipe=false;
        this.currentTimer = currentTimer;
    }

    /**
     * Constructor for storing recipes within ingredient store
     * @param name name of recipe
     */
    public IngredientDataStore(String name){
        this.name=name;
        timers=null;
        completed=null;
        skin=0;
        recipe=true;
        currentTimer = 0;
    }
    //Getters and Setters

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
    //currentTimer
    public float getCurrentTimer() {
        return currentTimer;
    }
    public void setCurrentTimer(float currentTimer) {
        this.currentTimer = currentTimer;
    }
}
