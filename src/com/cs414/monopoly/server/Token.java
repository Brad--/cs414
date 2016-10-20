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
    private boolean jail;
    public Token(String name){
        this.name = name;
        this.cashMoney = 1500;
        this.token = null;
        this.currentPosition = 1; // this is GO
        this.jail = false;
    }

    public void pickToken(PickToken choice){
        // not really sure how to show the enum object to the user ill let Gabe handel this
        this.token = choice;
    }

    public void payRent(int rent){
        this.cashMoney -=rent;

    }

    public void trade(String otherPlayersName){
        gameBoard.delegateDeed(this);

    }
    public void roll(){
        gameBoard.handleRoll(this);
    }

    public void buyProperty(){

    }

    public String getName(){
        return this.name;
    }

    public boolean inJail(){
        return this.jail;
    }

    public void updatePosition(int move){
        this.currentPosition += move;
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }
}
