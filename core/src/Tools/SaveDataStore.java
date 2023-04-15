package Tools;

import com.team13.piazzapanic.Reputation;

import java.util.ArrayList;

public class SaveDataStore {
    //Variables
    private ArrayList<ChefDataStore> chefData;
    private int orderCount;
    private float diffMult;
    private boolean createdOrder;
    private float timeSeconds;
    private float timeSecondsCount;
    private int chefCount;
    private int score;
    private Reputation rep;
    private OrderDataStore order;

    //Contructors

    public SaveDataStore(){
        chefData = new ArrayList<ChefDataStore>();
        diffMult = 1f;
        orderCount = 0;
        createdOrder = false;
        timeSeconds = 0f;
        timeSecondsCount = 0f;
        chefCount = 3;
        score = 0;
        rep=new Reputation(3);
        order=new OrderDataStore();
    }
    public SaveDataStore(ArrayList<ChefDataStore> chefData, int orderCount, float diffMult, boolean createdOrder,float timeSeconds, float timeSecondsCount, int chefCount, int score, Reputation rep, OrderDataStore order){
        this.chefData = chefData;
        this.orderCount = orderCount;
        this.diffMult = diffMult;
        this.createdOrder = createdOrder;
        this.timeSecondsCount = timeSecondsCount;
        this.timeSeconds = timeSeconds;
        this.chefCount = chefCount;
        this.score=score;
        this.rep=rep;
        this.order=order;
    }

    //Getters and Setters

    //chefData
    public void setChefData(ArrayList<ChefDataStore> chefData) {this.chefData=chefData;}
    public ArrayList<ChefDataStore> getChefData(){return chefData;}
    //orderCount
    public void setOrderCount(int orderCount){ this.orderCount=orderCount; }
    public int getOrderCount() {return orderCount;}
    //diffMult
    public void setDiffMult(int diffMult){ this.diffMult=diffMult; }
    public float getDiffMult() {return diffMult;}
    //createdOrder
    public void setCreatedOrder(boolean createdOrder) {this.createdOrder=createdOrder;}
    public boolean getCreatedOrder() {return createdOrder;}
    //timeSeconds
    public float getTimeSeconds() {return timeSeconds;}
    public void setTimeSeconds(float timeSeconds) {this.timeSeconds = timeSeconds;}
    //timeSecondsCount
    public float getTimeSecondsCount() {return timeSecondsCount;}
    public void setTimeSecondsCount(float timeSecondsCount) {this.timeSecondsCount = timeSecondsCount;}
    //chefCount
    public int getChefCount() {return chefCount; }
    public void setChefCount(int chefCount) {this.chefCount = chefCount;}
    //score
    public int getScore() {return score;}
    public void setScore(int score) {this.score = score;}
    //rep
    public void setRep(Reputation rep) {this.rep = rep;}
    public Reputation getRep() {return rep;}
    //order
    public void setOrder(OrderDataStore order) {this.order = order;}
    public OrderDataStore getOrder() {return order;}
}
