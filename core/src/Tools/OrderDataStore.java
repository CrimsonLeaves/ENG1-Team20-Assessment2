package Tools;

public class OrderDataStore {
    String orderType;
    int startTime;
    float diff;
    public OrderDataStore(){
        orderType="burger";
        diff=1;
        startTime=0;
    }
    public OrderDataStore(String orderType, int startTime, float diff){
        this.orderType=orderType;
        this.startTime=startTime;
        this.diff=diff;
    }

    public String getOrderType() {return orderType;}
    public void setOrderType(String orderType) {this.orderType = orderType;}
    public int getStartTime() {return startTime;}
    public void setStartTime(int startTime) {this.startTime = startTime;}
    public float getDiff() {return diff;}
    public void setDiff(float diff) {this.diff = diff;}
}
