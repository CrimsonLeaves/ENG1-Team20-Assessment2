package Tools;

public class OrderDataStore {
    String orderType;
    int startTime;
    float diff;
    /**
     * No argument constructor with default files required for serialization
     */
    public OrderDataStore(){
        orderType=Constants.BURGER;
        diff=1;
        startTime=0;
    }

    /**
     * Constructor for creating store for order data used for saving and loading
     * @param orderType The recipe the order is for
     * @param startTime Time order began
     * @param diff Difficulty of game
     */
    public OrderDataStore(String orderType, int startTime, float diff){
        this.orderType=orderType;
        this.startTime=startTime;
        this.diff=diff;
    }
    //Getters and setters

    public String getOrderType() {return orderType;}
    public void setOrderType(String orderType) {this.orderType = orderType;}
    public int getStartTime() {return startTime;}
    public void setStartTime(int startTime) {this.startTime = startTime;}
    public float getDiff() {return diff;}
    public void setDiff(float diff) {this.diff = diff;}
}
