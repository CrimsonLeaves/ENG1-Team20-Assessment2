package Sprites;

import Ingredients.FailedIngredient;
import Ingredients.Ingredient;
import Tools.Constants;
import Recipe.Recipe;
import Recipe.CookedPizzaRecipe;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.team13.piazzapanic.MainGame;

public class Oven extends CookingStation{

    Recipe currentRecipe;
    private Texture progBarBack;
    private Texture progBarFill;
    private Texture progBarBurn;
    private float pizzaTimer=5;

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
        progBarBack = new Texture("UI/progBarBackground.png");
        progBarFill = new Texture("UI/progBarMain.png");
        progBarBurn = new Texture("UI/progBarBurn.png");
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
    public void update(float dt,float diff,boolean instantCook, boolean noBurn){
        if(currentIngredient != null && !currentIngredient.getFailed()) {
            if (instantCook && !currentIngredient.isCompleted(Constants.OVEN)){
                timer=currentIngredient.getTimer(Constants.OVEN);
            }
            timer += dt;
            if (timer > currentIngredient.getTimer(Constants.OVEN)
                    && !currentIngredient.isCompleted(Constants.OVEN)) {
                currentIngredient.setCompleted(Constants.OVEN);
                timer = 0;
            } else if (noBurn && currentIngredient.isCompleted(Constants.OVEN)){
                timer=0;
            } else if( timer > currentIngredient.getTimer(Constants.OVEN)*2*diff
                    && currentIngredient.isCompleted(Constants.OVEN)){
                currentIngredient = new FailedIngredient();
            }
        } else if(currentRecipe != null){
            if (instantCook && currentRecipe.getClass().getSimpleName().equals(Constants.UNCOOKED_PIZZA_RECIPE)){
                timer=pizzaTimer;
            }
            timer +=dt;
            //hardcoded to deal with cooking a pizza
            if (timer > pizzaTimer && (currentRecipe.getClass().getSimpleName().equals(Constants.UNCOOKED_PIZZA_RECIPE))){
                Gdx.app.log(Constants.PIZZA,"Made");
                timer = 0;
                currentRecipe = new CookedPizzaRecipe(); //cooked pizza

            } else if (noBurn && currentRecipe.getClass().getSimpleName().equals(Constants.COOKED_PIZZA_RECIPE)){
                timer=0;
            } else if (timer > pizzaTimer*2*diff && currentRecipe.getClass().getSimpleName().equals(Constants.COOKED_PIZZA_RECIPE)){
                Gdx.app.log(Constants.PIZZA,"Burnt");
                currentRecipe = null;
                currentIngredient = new FailedIngredient();
            }
        }
    }
    @Override
    public void drawProgressBar(SpriteBatch batch, String station,float diff){
        float adjX = getX()- (MainGame.TILE_SIZE-1)/2/MainGame.PPM;
        float adjY = getY()-MainGame.TILE_SIZE/2/MainGame.PPM;
        if (currentIngredient != null&& !currentIngredient.getFailed()) {
            if (!currentIngredient.isCompleted(station)){
                float progress=timer/currentIngredient.getTimer(station);
                batch.draw(progBarBack,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
            else{
                float progress=timer/(currentIngredient.getTimer(station)*2*diff);
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarBurn,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
        }
        if (currentRecipe != null){
            if (timer < pizzaTimer && currentRecipe.getClass().getSimpleName().equals(Constants.UNCOOKED_PIZZA_RECIPE)){
                float progress=timer/pizzaTimer;
                batch.draw(progBarBack,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
            else if (timer < pizzaTimer*2*diff){
                float progress=timer/(pizzaTimer*2*diff);
                batch.draw(progBarFill,adjX,adjY,(MainGame.TILE_SIZE-1)/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
                batch.draw(progBarBurn,adjX,adjY,(MainGame.TILE_SIZE-1)*progress/MainGame.PPM,MainGame.TILE_SIZE/(4*MainGame.PPM));
            }
        }

    }
}
