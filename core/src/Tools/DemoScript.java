package Tools;

import Ingredients.Ingredient;
import Ingredients.Steak;
import Recipe.*;
import Sprites.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.team13.piazzapanic.MainGame;

import java.util.List;

public class DemoScript {
    Chef mainChef;
    int instruction;
    float x;
    float y;
    String[] instructionList;
    float movementSpeed;
    int instructionType;
    float idleTime;
    public DemoScript(World world, float movementSpeed){
        float x= 32f;
        float y = 32f;
        this.movementSpeed=movementSpeed;
        instructionType=0; //0=idle, 1=move, 2=interact
        mainChef = new Chef(world, x, y);
        instruction=0;
        idleTime=0;
        FileHandle file = new FileHandle(Gdx.files.local("demoInstructions.txt").file());
        String rawInstructions = file.readString();
        rawInstructions = rawInstructions.replaceAll("\r","");
        instructionList = rawInstructions.split("\n");
        nextInstruction();

    }
    public void update(float dt, SpriteBatch batch){
        if (instructionType==0){
            idleTime-=Gdx.graphics.getDeltaTime();
            if (idleTime <=0){
                idleTime = 0;
                nextInstruction();
            }
        }
        else if  (instructionType==1){
            moveChef();
        }

        mainChef.update(dt);
        mainChef.create(batch);
    }
    public void moveChef(){
        if (Math.abs((x-mainChef.b2body.getPosition().x)+Math.abs(y-mainChef.b2body.getPosition().y)) <= 0.01){
            mainChef.b2body.setLinearVelocity(0, 0);
            nextInstruction();
            return;
        }
        Vector2 movement = new Vector2(x,y);
        movement.sub(mainChef.b2body.getPosition());
        float mag = (float)Math.sqrt(movement.x* movement.x + movement.y* movement.y);
        if (mag != 0){
            movement.x = movement.x/mag;
            movement.y = movement.y/mag;
        }
        else{
            movement.x = 0;
            movement.y = 0;
        }

        mainChef.b2body.setLinearVelocity(movement.x*movementSpeed, movement.y*movementSpeed);
    }
    public void nextInstruction(){
        if (instruction >= instructionList.length){
            instruction=0;
        }
        String instr = instructionList[instruction];
        if (instr.startsWith("#")){
            //Comment
            instruction++;
            nextInstruction();
            return;
        }
        if (instr.startsWith("~")){
            String[] dat = instr.substring(1).split(" ");
            x= Float.parseFloat(dat[0]) /MainGame.PPM;
            y= Float.parseFloat(dat[1]) /MainGame.PPM;
            instructionType=1;
        }
        else if (instr.startsWith("@")){
            idleTime = Float.parseFloat(instr.substring(1));
            instructionType=0;
        }
        else if (instr.length() > 0){
            String[] dat = instr.split(" ");
            InteractiveTileObject tile = null;
            String tileName = null;
            if (mainChef.getTouchingTile() != null){
                tile = (InteractiveTileObject) mainChef.getTouchingTile().getUserData();
                tileName = tile.getClass().getSimpleName();
                if (dat[0] == "pickup"){
                    if (tile instanceof IngredientStation){
                        IngredientStation ingredientTile = (IngredientStation) tile;
                        mainChef.pickUp(ingredientTile.getIngredient());
                    }
                }
                else if (dat[0] == "putdown"){
                    switch (dat[1]){
                        case "chopping":
                            ChoppingBoard choppingBoardTile = (ChoppingBoard) tile;
                            if(choppingBoardTile.getCurrentIngredient() == null){

                            }
                            break;
                    }
                }

            }

        }
        else{
            x=0;
            y=0;
            instructionType=0;
        }
        instruction++;
    }

    private void handleInteraction(){
        if(mainChef.getTouchingTile() != null){
            InteractiveTileObject tile = (InteractiveTileObject) mainChef.getTouchingTile().getUserData();
            String tileName = tile.getClass().getName();
            if (tile instanceof IngredientStation){
                IngredientStation ingredientTile = (IngredientStation) tile;
                mainChef.pickUp(ingredientTile.getIngredient());
            } else {
                    //cast top of the stack to the correct object
                    Sprite item = mainChef.peekStack();
                    Ingredient ingredient = null;
                    Recipe recipe = null;
                    if(item instanceof Ingredient){
                        ingredient = (Ingredient) item;
                    } else if(item instanceof Recipe){
                        recipe = (Recipe) item;
                    }

                    switch (tileName) {
                        case "Sprites.PlateStation":
                            PlateStation plateTile = (PlateStation) tile;
                            if(plateTile.getCompletedRecipe() != null){
                                mainChef.pickUp(plateTile.pickUpItem());
                            } else if (recipe == null && ingredient != null){
                                plateTile.dropItem(ingredient);
                                mainChef.putDown();
                            }
                            break;
                        case "Sprites.ChoppingBoard":
                            ChoppingBoard choppingBoardTile = (ChoppingBoard) tile;
                            if(choppingBoardTile.getCurrentIngredient() != null){
                                mainChef.pickUp(choppingBoardTile.getCurrentIngredient());
                                choppingBoardTile.setCurrentIngredient(null);
                            } else if (ingredient != null){
                                if(ingredient.getTimer(Constants.CHOPPING_BOARD) != null
                                        && !ingredient.isCompleted(Constants.CHOPPING_BOARD)){
                                    choppingBoardTile.setCurrentIngredient(ingredient);
                                    mainChef.putDown();
                                }
                            }
                            break;
                        case "Sprites.Pan":
                            Pan panTile = (Pan) tile;
                            if(panTile.getCurrentIngredient() != null){
                                mainChef.pickUp(panTile.getCurrentIngredient());
                                panTile.setCurrentIngredient(null);
                            } else if (ingredient != null){
                                if(ingredient.getTimer(Constants.PAN) != null
                                        && !ingredient.isCompleted(Constants.PAN)){
                                    if (ingredient instanceof Steak){
                                        if (!ingredient.isCompleted(Constants.CHOPPING_BOARD)){
                                            return;
                                        }
                                    }
                                    panTile.setCurrentIngredient(ingredient);
                                    mainChef.putDown();
                                }
                            }
                            break;
                        case "Sprites.Oven":
                            Oven ovenTile = (Oven) tile;
                            if(ovenTile.getCurrentIngredient() != null){
                                mainChef.pickUp(ovenTile.getCurrentIngredient());
                                ovenTile.setCurrentIngredient(null);
                            } else if (ovenTile.getCurrentRecipe() != null){
                                mainChef.pickUp(ovenTile.getCurrentRecipe());
                                ovenTile.setCurrentRecipe(null);
                            } else if (ingredient != null){
                                if(ingredient.getTimer(Constants.OVEN) != null
                                        && !ingredient.isCompleted(Constants.OVEN)){
                                    ovenTile.setCurrentIngredient(ingredient);
                                    mainChef.putDown();
                                }
                            } else if (recipe != null){
                                if(recipe instanceof UncookedPizzaRecipe){
                                    ovenTile.setCurrentRecipe(recipe);
                                    mainChef.putDown();
                                }
                            }
                            break;
                        case "Sprites.Bin":
                            if(ingredient != null || recipe != null){
                                mainChef.putDown();
                            }
                            break;
                        case "Sprites.CompletedDishStation":
                            CompletedDishStation cds = (CompletedDishStation) tile;
                            cds.setRecipe(recipe);
                            mainChef.putDown();
                    }
                }


        }
    }
}
