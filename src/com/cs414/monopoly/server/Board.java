package com.cs414.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.cs414.monopoly.shared.Token;

public class Board {
    private Token currToken; // Change this to Token when that exists
    private Die die;
    private HashMap<String, Token> users;
    private ArrayList<Deed> deeds;
    public Board(){
        init();
    }

    public void init(){
        users = new HashMap<>();
        die = new Die();
        deeds = new ArrayList<>();
        for (int i = 0; i < 40; i++){
            deeds.add(i, new Deed(this,i));
        }
        //@TODO: start GUI
    }

    public void handleRoll(Token currentPlayer) {
        int move = die.roll(currentPlayer);
        //@TODO: check for doubles
        if (!currentPlayer.inJail()){
            updatePostion(currentPlayer, move);
            if (deeds.get(currentPlayer.getCurrentPosition()).getOwner() == null) {
                //TODO: display to player option to buy
                delegateDeed(currentPlayer);
            }
            else if (currentPlayer.getCurrentPosition()==1){
                currentPlayer.passGo();
            }
            else if (currentPlayer.getCurrentPosition()==5){
                //tax spot 200 or 10%
                //@TODO show use choice to pay 200 or 10% of total
            }
            else if (currentPlayer.getCurrentPosition()== 39){
                currentPlayer.payRent(75);
            }
            else{
                // pay rent
                payRent(currentPlayer);
            }
        }
    }

    public void addUser(Token player){
        if (!users.containsKey(player.getName()))
            users.put(player.getName(), player);
    }

    public void payRent(Token player){
        Token owner = deeds.get(player.getCurrentPosition()).getOwner();
        int rent = deeds.get(player.getCurrentPosition()).houseRent;
        users.get(player.getName()).payRent(rent);
        users.get(owner.getName()).earnRent(rent);

    }

    public void delegateDeed(Token player) {
        int price = deeds.get(player.getCurrentPosition()).houseRent;
        if (users.get(player.getName()).getCashMoney()-price >0) {
            deeds.get(player.getCurrentPosition()).changeOwnership(player);
        }
        else {
            System.err.println("You do not have enough money to buy this Deed");
        }
    }

    // @param: playerA is the player receiving the property
    // @param: position the position of the property being traded
    public void trade(Token playerA, int position){
        deeds.get(position).changeOwnership(playerA);
    }

    public ArrayList<Deed> getOwnedDeeds(Token player) {
        ArrayList<Deed > ownedDeeds = new ArrayList<>();
        for (Deed deed : deeds) {
            if (deed.getOwner().equals(player.getName())){
                ownedDeeds.add(deed);
            }
        }
        return ownedDeeds;
    }

    public void updatePostion(Token player, int move){
        users.get(player.getName()).updatePosition(move);
    }

}
