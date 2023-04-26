package Recipe;

import Tools.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.team13.piazzapanic.MainGame;

/**
 * The `Order` class extends the `Sprite` class and represents a recipe order
 * in the game.
 */
public class Order extends Sprite {
    /** The `Recipe` object associated with this order. */
    public Recipe recipe;
    /** A flag indicating whether the order has been completed. */
    public Boolean orderComplete;
    public Boolean orderFailed;
    /** The image representing this order. */
    public Texture orderImg;
    public int startTime;
    public float totalTime;
    public float currentTime;
    private ShapeRenderer shapeRenderer;
    public float adjustedX;
    public float adjustedY;

    /**
     * Constructor for the `Order` class.
     *
     * @param recipe The `Recipe` object associated with this order.
     * @param orderImg The image representing this order.
     * @param startTime The time at which the order is created.
     * @param totalTime The time the order takes to complete
     */
    public Order(Recipe recipe, Texture orderImg, int startTime,float totalTime) {
        this.recipe = recipe;
        this.orderImg = orderImg;
        this.orderComplete = false;
        this.orderFailed = false;
        this.startTime=startTime;
        this.totalTime=totalTime;
        currentTime=0f;
        shapeRenderer = new ShapeRenderer();
        adjustedX=0;
        adjustedY=0;
    }

    /**
     * Creates the order image and adds it to the given `SpriteBatch`.
     *
     * @param x The x coordinate of the order image.
     * @param y The y coordinate of the order image.
     * @param batch The `SpriteBatch` to add the order image to.
     */
    public void create(float x, float y, SpriteBatch batch) {
        Sprite sprite = new Sprite(orderImg);
        adjustedX = x - (8 / MainGame.PPM);
        adjustedY = y + (2 / MainGame.PPM);
        if (orderImg.toString().equals(Constants.SALAD_RECIPE_PATH)) {
            sprite.setBounds(adjustedX, adjustedY, 53 / MainGame.PPM, 28 / MainGame.PPM);
            sprite.draw(batch);
        } else {
            sprite.setBounds(adjustedX, adjustedY, 33 / MainGame.PPM, 28 / MainGame.PPM);
            sprite.draw(batch);
        }
    }
}
