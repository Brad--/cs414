package com.cs414.monopoly.server;

/**
 * Created by Garrett on 10/19/2016.
 */
public class Token {
    private String name;
    private int cashMoney;
    private PickToken token;
    private int currentPosition;
    private Board gameBoard;
    private boolean isInjail;
    public Token(String name){
        this.name = name;
        this.cashMoney = 1500;
        this.token = null;
        this.currentPosition = 1; // this is GO
        this.isInjail = false;
    }

    public void setToken(PickToken choice){
        // not really sure how to show the enum object to the user ill let Gabe handel this
        this.token = choice;
    }

    public void payRent(int rent){
        this.cashMoney -=rent;

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
}
