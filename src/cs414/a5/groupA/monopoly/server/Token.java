package cs414.a5.groupA.monopoly.server;

import java.io.Serializable;

/**
 * Created by Garrett on 10/19/2016.
 */
public class Token implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 349032251412258158L;
	private String name;
    private int cashMoney;
    private int currentPosition;
    private boolean isInjail;
    private int speeding;
    private int lastRollDieOne;
    private int lastRollDieTwo;
    public Token() {
    	
    }

    public Token(String name){
        this.name = name;
        this.cashMoney = 1500;
        this.setCurrentPosition(1); // this is GO
        this.isInjail = false;
        this.speeding =0;
        this.lastRollDieOne =0;
        this.lastRollDieTwo = 0;
    }

    public void payRent(int rent){
        this.cashMoney -=rent;
    }

    public void earnRent(int rent) {
        this.cashMoney += rent;
    }

    public void buyProperty(int price){
        this.cashMoney -= price;
    }

    public int getCashMoney(){
        return this.cashMoney;
    }

    public void passGo(){
        this.cashMoney +=200;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public boolean inJail(){
        return this.isInjail;
    }

    public void updatePosition(int move){
        this.setCurrentPosition((this.getCurrentPosition() + move) % 40);
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

	public void goToJail(){
	    this.isInjail = true;
    }

    public void getOutofJail(){
        this.isInjail = false;
    }

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getSpeeding(){
	    return this.speeding;
    }

    public void incrementSpeed(int speed){
        this.speeding +=speed;
    }

    public void resetSpeed(){
        this.speeding = 0;
    }

    public void updateDieRoll(int one, int two){
        this.lastRollDieOne = one;
        this.lastRollDieTwo = two;
    }

    public int getLastRollDieOne(){
        return this.lastRollDieOne;
    }

    public int getLastRollDieTwo(){
        return this.lastRollDieTwo;
    }
}