package com.cs414.monopoly.shared;

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
    private String gamePiece;
    private int speeding;
    
    public Token() {
    	
    }

    public Token(String name, String imageUrl){
        this.name = name;
        this.cashMoney = 1500;
        this.setCurrentPosition(1); // this is GO
        this.isInjail = false;
        this.gamePiece = imageUrl;
        this.speeding =0;
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

	public String getGamePiece() {
		return gamePiece;
	}

	public void setGamePiece(String gamePiece) {
		this.gamePiece = gamePiece;
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
}
