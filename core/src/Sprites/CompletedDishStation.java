package Sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Recipe.Recipe;
import com.team13.piazzapanic.MainGame;

/**
 * Represents a station where completed dishes can be placed.
 */
public class CompletedDishStation extends InteractiveTileObject {

    private Recipe recipe;

    private float timer;

    /**
     * Creates a new instance of CompletedDishStation
     *
     * @param world The world where the object is located
     * @param map The TiledMap the object belongs to
     * @param bdef Body definition for the object
     * @param rectangle The rectangle that defines the object's size and location
     */
    public CompletedDishStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
        recipe = null;
    }

    public void draw(SpriteBatch batch){
        timer += 1/60f;
        recipe.create(this.getX(), this.getY() - (0.01f / MainGame.PPM), batch);
        if (timer > 3) {
            recipe = null;
            timer = 0;
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }



    /**
     * Gets the x position of the object.
     *
     * @return The x position of the object
     */
    public float getX(){
        return super.bdefNew.position.x;
    }

    /**
     * Gets the y position of the object.
     *
     * @return The y position of the object
     */
    public float getY(){
        return super.bdefNew.position.y;
    }
}

