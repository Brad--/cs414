package com.cs414.monopoly.shared;

import com.google.gwt.user.client.ui.Image;

/**
 * Created by Garrett on 10/19/2016.
 */
public class Token {
    private String name;
    private int cashMoney;
    private int currentPosition;
    private boolean isInjail;
    private Image gamePiece;
    
    public Token(String name, Image gamePiece){
        this.name = name;
        this.cashMoney = 1500;
        this.currentPosition = 1; // this is GO
        this.isInjail = false;
        this.setGamePiece(gamePiece);
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

    public String getName(){
        return this.name;
    }

    public boolean inJail(){
        return this.isInjail;
    }

    public void updatePosition(int move){
        this.currentPosition += move%40;
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

	public Image getGamePiece() {
		return gamePiece;
	}

	public void setGamePiece(Image gamePiece) {
		this.gamePiece = gamePiece;
	}
}
