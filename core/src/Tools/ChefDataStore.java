package Tools;

public class ChefDataStore {
    private float x;
    private float y;
    public ChefDataStore(){
        this.x=31.5F;
        this.y=38F;
    }
    public ChefDataStore(float x, float y){
        this.x=x;
        this.y=y;
    }
    public float getX() {return x;}
    public void setX(float x) {this.x=x;}
    public float getY() {return y;}
    public void setY(float y) {this.y=y;}

}
