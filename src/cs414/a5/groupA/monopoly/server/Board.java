package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

import cs414.a5.groupA.monopoly.shared.Token;

public class Board {
    public ArrayList<Space> deeds = new ArrayList<>();
    public Board(){
        init();
    }

    public void init(){
        int listPos;
        for (int i = 0; i <= 39; i++) {
            listPos = i - 1;
            if (listPos == 0) {
                deeds.add(new GoSpace(this, i));
            } else if (listPos == 4) {
                deeds.add(new TaxSpace(this, i));
            } else if (listPos == 5) {
                deeds.add(new Railroad(this, i));
            }  else if (listPos == 10) {
                deeds.add(new Jail(this, i));
            } else if (listPos == 12) {
                deeds.add(new UtilitySpace(this, i));
            } else if (listPos == 15) {
                deeds.add(new Railroad(this, i));
            } else if (listPos == 20) {
                deeds.add(new FreeParking(this, i));
            } else if (listPos == 25) {
                deeds.add(new Railroad(this, i));
            } else if (listPos == 28) {
                deeds.add(new UtilitySpace(this, i));
            } else if (listPos == 35) {
                deeds.add(new Railroad(this, i));
            } else if (listPos == 38) {
                deeds.add(new TaxSpace(this, i));
            } else if (listPos == 7 || listPos == 22
                    || listPos == 2 || listPos == 17
                    || listPos == 33 || listPos ==36) {
                deeds.add(new Card(this, i));
            } else {
                deeds.add(new Deed(this, i));
            }
        }
        //@TODO: start GUI
    }

    public Token handleRoll(Token currentPlayer) { // TODO return TokenActionWrapper?
    	// GD 11.15.16 Needs redone after token refactor
//        if (!users.containsKey(currentPlayer))
//            addUser(currentPlayer);
//    	Token updatedPlayer = die.roll(currentPlayer);
//        int move = updatedPlayer.getPosition();
//        //@TODO: check for doubles
//        if (!currentPlayer.inJail()){
//            if (deeds.get(currentPlayer.getPosition()).getOwner() == null) {
//                //TODO: display to player option to buy
//                delegateDeed(currentPlayer);
//            }
//            else if (currentPlayer.getPosition()==1){
//                currentPlayer.passGo(); // TODO needs to also account for PASSING of go as well
//            }
//            else if (currentPlayer.getPosition()==5){
//                //tax spot 200 or 10%
//                //@TODO show use choice to pay 200 or 10% of total
//            }
//            else if (currentPlayer.getPosition()== 39){
//                currentPlayer.payRent(75);
//            }
//            else{
//                // pay rent
//                payRent(currentPlayer);
//            }
//        }
//
//        // Pass go
//        if(!updatedPlayer.inJail() && updatedPlayer.getPosition() < currentPlayer.getPosition()) {
//            updatedPlayer.earnRent(200);
//        }
        return currentPlayer;
    }

    public void addUser(Token player){
        if (!users.containsKey(player.getPlayerName()))
            users.put(player.getPlayerName(), player);
    }

    public void payRent(Token player){
        if(deeds.get(player.getPosition()) instanceof Deed) {
            int rent = ((Deed)deeds.get(player.getPosition())).getRent();
            if (player.getMoney() - rent > 0) {
                deeds.get(player.getPosition()).action(player);
            } else {
                System.err.println("You do not have enough money to pay the rent #getEvicted!");
                //@TODO: if a player runs out of money they lose stop them from playing
            }
        }
    }

    public void delegateDeed(Token player) {
        Space currSpace = deeds.get(player.getPosition());
        if(currSpace instanceof Deed) {
            int price = ((Deed)currSpace).getPrice();
            if (users.get(player.getPlayerName()).getMoney() - price > 0) {
                ((Deed)currSpace).changeOwnership(player);
            } else {
                System.err.println("You do not have enough money to buy this Deed");
            }
        }
    }

    // @param: playerA is the player receiving the property
    // @param: position the position of the property being traded
    public void trade(Token playerA, int position){
        if(deeds.get(position) instanceof Deed)
            ((Deed)deeds.get(position)).changeOwnership(playerA);
    }

    public ArrayList<Deed> getOwnedDeeds(Token player) {
        ArrayList<Deed> ownedDeeds = new ArrayList<>();
        for (Space deed : deeds) {
            if (deed.getOwner() != null && deed.getOwner().equals(player.getPlayerName())){
                if(deed instanceof Deed)
                    ownedDeeds.add((Deed)deed);
            }
        }
        return ownedDeeds;
    }

    public boolean containsUser(String name){
        return users.containsKey(name);
    }
    
    public Token getUser(String name) {
    	return users.get(name);
    }
    
    public void updateUser(Token token) {
    	users.put(token.getPlayerName(), token);
    }
    
    public HashMap<String, Token> getUsers() {
    	return this.users;
    }

}
