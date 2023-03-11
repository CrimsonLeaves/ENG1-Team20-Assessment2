package Sprites;

import Ingredients.*;
import Recipe.Recipe;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

/**
 * Chef class extends {@link Sprite} and represents a chef in the game.
 * It has fields for the world it exists in, a Box2D body, the initial X and Y
 * positions, a wait timer, collision flag, various textures for different skins,
 * state (UP, DOWN, LEFT, RIGHT), skin needed, fixture of what it is touching, ingredient
 * and recipe in hand, control flag, circle sprite, chef notification X, Y, width and height,
 * and completed dish station.
 */

public class Chef extends Sprite {
    public World world;
    public Body b2body;

    private final float initialX;
    private final float initialY;


    public Vector2 startVector;
    private float waitTimer;

    public boolean chefOnChefCollision;


    public enum State {UP, DOWN, LEFT, RIGHT}

    public State currentState;
    private TextureRegion currentSkin;

    private final Texture skinNeeded;

    private Fixture whatTouching;

    private final Deque<Sprite> holding;

    private Boolean userControlChef;

    private final Sprite circleSprite;

    private float notificationX;
    private float notificationY;
    private float notificationWidth;
    private float notificationHeight;
    public Rectangle collisionRect;


    /**
     * Chef class constructor that initializes all the fields
     * @param world the world the chef exists in
     * @param startX starting X position
     * @param startY starting Y position
     */

    public Chef(World world, float startX, float startY) {
        initialX = startX / MainGame.PPM;
        initialY = startY / MainGame.PPM;

        skinNeeded = new Texture("Chef/Chef_normal.png");

        this.world = world;
        currentState = State.DOWN;

        defineChef();

        float chefWidth = 13 / MainGame.PPM;
        float chefHeight = 20 / MainGame.PPM;
        setBounds(0, 0, chefWidth, chefHeight);
        chefOnChefCollision = false;
        waitTimer = 0;
        startVector = new Vector2(0, 0);
        whatTouching = null;
        holding = new ArrayDeque<>();
        userControlChef = true;
        Texture circleTexture = new Texture("Chef/chefIdentifier.png");
        circleSprite = new Sprite(circleTexture);
        collisionRect = new Rectangle(initialX,initialY, circleSprite.getWidth()/MainGame.PPM, circleSprite.getHeight()/MainGame.PPM);
    }


    /**
     * Update the position and region of the chef and set the notification position based on the chef's current state.
     *
     * @param dt The delta time.
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        collisionRect.setPosition(circleSprite.getX(), circleSprite.getY());
        currentSkin = getSkin();
        setRegion(currentSkin);
        switch (currentState) {
            case UP:
                notificationX = b2body.getPosition().x - (1.75f / MainGame.PPM);
                notificationY = b2body.getPosition().y - (7.7f / MainGame.PPM);
                break;
            case DOWN:
                notificationX = b2body.getPosition().x + (0.95f / MainGame.PPM);
                notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                break;
            case LEFT:
                notificationX = b2body.getPosition().x;
                notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                break;
            case RIGHT:
                notificationX = b2body.getPosition().x + (0.5f / MainGame.PPM);
                notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                break;
        }


        if (!userControlChef && chefOnChefCollision) {
            waitTimer += dt;
            b2body.setLinearVelocity(new Vector2(startVector.x * -1, startVector.y * -1));
            if (waitTimer > 0.3f) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                chefOnChefCollision = false;
                userControlChef = true;
                waitTimer = 0;
            }
        }
    }

    public void create(SpriteBatch batch){
        this.draw(batch);
        float offset = 0;
        Iterator<Sprite> holdingIterator = holding.descendingIterator();
        while (holdingIterator.hasNext()) {
            Sprite item = holdingIterator.next();
            if(item instanceof Ingredient){
                Ingredient ingredient = (Ingredient) item;
                ingredient.create(b2body.getPosition().x, b2body.getPosition().y+(offset/MainGame.PPM),batch);
            } else if(item instanceof Recipe){
                Recipe recipe = (Recipe) item;
                recipe.create(b2body.getPosition().x, b2body.getPosition().y+(offset/MainGame.PPM),batch);
            }
            offset += 2;
        }
    }

    /**
     * This method sets the bounds for the notification based on the given direction.
     * @param direction - A string representing the direction of the notification.
     *                   Can be "Left", "Right", "Up", or "Down".
     */

    public void notificationSetBounds(String direction) {
        switch (direction) {
            case "Left":
            case "Right":
                notificationWidth = 1.5f / MainGame.PPM;
                notificationHeight = 1.5f / MainGame.PPM;
                break;
            case "Up":
                notificationWidth = 4 / MainGame.PPM;
                notificationHeight = 4 / MainGame.PPM;
                break;
            case "Down":
                notificationWidth = 2 / MainGame.PPM;
                notificationHeight = 2 / MainGame.PPM;
                break;
        }
    }

    /**
     Draws a notification to help the user understand what chef they are controlling.
     The notification is a sprite that looks like at "C" on the controlled chef.
     @param batch The sprite batch that the notification should be drawn with.
     */
    public void drawNotification(SpriteBatch batch) {
        if (this.getUserControlChef()) {
            circleSprite.setBounds(notificationX, notificationY, notificationWidth, notificationHeight);
            circleSprite.draw(batch);
        }
    }

    /**
     * Get the texture region for the current state of the player.
     *
     * @return the texture region for the player's current state
     */

    private TextureRegion getSkin() {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case UP:
                region = new TextureRegion(skinNeeded, 0, 0, 33, 46);
                break;
            case DOWN:
                region = new TextureRegion(skinNeeded, 33, 0, 33, 46);
                break;
            case LEFT:
                region = new TextureRegion(skinNeeded, 64, 0, 33, 46);
                break;
            case RIGHT:
                region = new TextureRegion(skinNeeded, 96, 0, 33, 46);
                break;
            default:
                region = currentSkin;
        }
        return region;
    }


    /**
     Returns the current state of the player based on the controlled chefs velocity.
     @return current state of the player - UP, DOWN, LEFT, or RIGHT
     */
    private State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.UP;
        if (b2body.getLinearVelocity().y < 0)
            return State.DOWN;
        if (b2body.getLinearVelocity().x > 0)
            return State.RIGHT;
        if (b2body.getLinearVelocity().x < 0)
            return State.LEFT;
        else
            return currentState;
    }

    /**
     * Define the body and fixture of the chef object.
     *
     * This method creates a dynamic body definition and sets its position with the `initialX` and `initialY`
     * variables, then creates the body in the physics world. A fixture definition is also created and a
     * circle shape is set with a radius of `4.5f / MainGame.PPM` and a position shifted by `(0.5f / MainGame.PPM)`
     * in the x-axis and `-(5.5f / MainGame.PPM)` in the y-axis. The created fixture is then set as the user data
     * of the chef object.
     */

    private void defineChef() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(initialX, initialY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4.5f / MainGame.PPM);
        shape.setPosition(new Vector2(shape.getPosition().x + (0.5f / MainGame.PPM), shape.getPosition().y - (5.5f / MainGame.PPM)));


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**

      * This method updates the state of the chef when it is in a collision with another chef.
      * The method sets the userControlChef to false, meaning the user cannot control the chef while it's in collision.
      * It also sets the chefOnChefCollision to true, indicating that the chef is in collision with another chef.
      * Finally, it calls the setStartVector method to update the position of the chef.
     */
        public void chefsColliding () {
            userControlChef = false;
            chefOnChefCollision = true;
            setStartVector();
        }

    /**
     * Set the starting velocity vector of the chef
     * when the chef collides with another chef
     *
     */
    public void setStartVector () {
        startVector = new Vector2(b2body.getLinearVelocity().x, b2body.getLinearVelocity().y);
    }

    /**
     * Set the touching tile fixture
     *
     * @param obj fixture that the chef is touching
     */
    public void setTouchingTile (Fixture obj){
        this.whatTouching = obj;
    }

    /**
     * Get the fixture that the chef is touching
     *
     * @return the fixture that the chef is touching
     */
    public Fixture getTouchingTile () {
        if (whatTouching == null) {
            return null;
        } else {
            return whatTouching;
        }
    }

    public void pickUp(Sprite item){
        if(holding.size() < 3){
            holding.addFirst(item);
        }
    }

    public void putDown(){
        holding.removeFirst();
    }

    public Sprite peekStack(){
        return holding.peekFirst();
    }

    /**

     * Returns a boolean value indicating whether the chef is under user control.
     * If not specified, returns false.
     *
     * @return userControlChef The boolean value indicating chef's control.
     */
    public boolean getUserControlChef () {
            return Objects.requireNonNullElse(userControlChef, false);
        }

}



