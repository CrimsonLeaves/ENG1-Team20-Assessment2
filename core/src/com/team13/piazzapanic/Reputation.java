package com.team13.piazzapanic;

public class Reputation {
    int rep;
    int maxRep;

    /**
     * Constructor for reputation class
     * @param maxRep the maximum reputation allowed
     */
    public Reputation(int maxRep){
        this.maxRep=maxRep;
        rep=maxRep;
    }
    public Reputation(){
        maxRep=3;
        rep=3;
    }

    /**
     * Gets current reputation
     * @return current reputation
     */
    public int getRep(){
        return  rep;
    }
    public int getMaxRep() {return maxRep;}
    public void setMaxRep(int maxRep) {this.maxRep = maxRep;}
    public void setRep(int rep) {this.rep = rep;}

    /**
     * Removes 1 reputation point and checks if game should end
     * @return True if game should lose
     */
    public boolean loseRep(){
        if (rep > 0) {
            rep--;
        }

        return loseGame();
    }

    /**
     * Checks if reputation is 0
     * @return True if game should lose
     */
    private boolean loseGame(){
        if (rep <= 0){
            return true;
        }
        return false;
    }

    /**
     * Resets reputation
     */
    public void reset(){
        rep=maxRep;
    }
}
