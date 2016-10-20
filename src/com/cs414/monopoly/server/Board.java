package com.cs414.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private Token currToken; // Change this to Token when that exists
    private Die die;
    private HashMap<String, Token> users;
    private ArrayList<Deed> deeds;
    public Board(){}

    public void init(){
        users = new HashMap<>();
        Board B = new Board();
        die = new Die();
        deeds = new ArrayList<>();
        for (int i=0; i<40; i++){
            deeds.add(i,new Deed(B,i));
        }
    }

    public void handleRoll(Token currentPlayer) {
        int move = die.roll();
        if (!currentPlayer.inJail()){
            updatePostion(currentPlayer, move);
            if (deeds.get(currentPlayer.getCurrentPosition()).getOwner() == null) {
                //TODO: display to player option to buy
                delegateDeed(currentPlayer);
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
        users.get(player.getName()).payRent(deeds.get(player.getCurrentPosition()).houseRent);
    }

    public void delegateDeed(Token player) {
        deeds.get(player.getCurrentPosition()).changeOwnership(player);
    }

    public ArrayList<Integer> getOwnedDeeds(Token player) {
        ArrayList<Integer > ownedDeeds = new ArrayList<>();
        for (Deed deed : deeds) {
            if (deed.getOwner().equals(player.getName())){
                ownedDeeds.add(deed.position);
            }
        }
        return ownedDeeds;

    }

    public void updatePostion(Token player, int move){
        users.get(player.getName()).updatePosition(move);
    }

//    public Space getPosition(String token) {
//        return new Space();
//    }
}
