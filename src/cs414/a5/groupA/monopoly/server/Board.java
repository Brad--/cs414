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
            listPos = i;
            if (listPos == 1) {
                deeds.add(new GoSpace(i));
            } else if (listPos == 5) {
                deeds.add(new TaxSpace(i));
            } else if (listPos == 6) {
                deeds.add(new Railroad(i));
            }  else if (listPos == 11) {
                deeds.add(new Jail(i));
            } else if (listPos == 13) {
                deeds.add(new UtilitySpace(i));
            } else if (listPos == 14) {
                deeds.add(new Railroad(i));
            } else if (listPos == 21) {
                deeds.add(new FreeParking(i));
            } else if (listPos == 26) {
                deeds.add(new Railroad(i));
            } else if (listPos == 29) {
                deeds.add(new UtilitySpace(i));
            } else if (listPos == 36) {
                deeds.add(new Railroad(i));
            } else if (listPos == 39) {
                deeds.add(new TaxSpace(i));
            } else if (listPos == 8 || listPos == 23
                    || listPos == 3 || listPos == 18
                    || listPos == 34 || listPos ==37) {
                deeds.add(new Card(i));
            } else {
                deeds.add(new Deed(i));
            }
        }
        //@TODO: start GUI
    }


    public void addUser(Token player){
//        if (!users.containsKey(player.getPlayerName()))
//            users.put(player.getPlayerName(), player);
    }

    public void payRent(Token player){
//        if(deeds.get(player.getPosition()) instanceof Deed) {
//            int rent = ((Deed)deeds.get(player.getPosition())).getRent();
//            if (player.getMoney() - rent > 0) {
//                deeds.get(player.getPosition()).action(player);
//            } else {
//                System.err.println("You do not have enough money to pay the rent #getEvicted!");
//                //@TODO: if a player runs out of money they lose stop them from playing
//            }
//        }
    }

    public void delegateDeed(Token player) {
        Space currSpace = deeds.get(player.getPosition());
//        if(currSpace instanceof Deed) {
//            int price = ((Deed)currSpace).getPrice();
//            if (users.get(player.getPlayerName()).getMoney() - price > 0) {
//                ((Deed)currSpace).changeOwnership(player);
//            } else {
//                System.err.println("You do not have enough money to buy this Deed");
//            }
//        }
    }

    // @param: playerA is the player receiving the property
    // @param: position the position of the property being traded
    public void trade(Token playerA, int position){
//        if(deeds.get(position) instanceof Deed)
//            ((Deed)deeds.get(position)).changeOwnership(playerA);
    }

    public ArrayList<Deed> getOwnedDeeds(Token player) {
        ArrayList<Deed> ownedDeeds = new ArrayList<>();
//        for (Space deed : deeds) {
//            if (deed.getOwner() != null && deed.getOwner().equals(player.getPlayerName())){
//                if(deed instanceof Deed)
//                    ownedDeeds.add((Deed)deed);
//            }
//        }
        return ownedDeeds;
    }

//    public boolean containsUser(String name){
//        return users.containsKey(name);
//    }
//    
//    public Token getUser(String name) {
//    	return users.get(name);
//    }
//    
//    public void updateUser(Token token) {
//    	users.put(token.getPlayerName(), token);
//    }
//    
//    public HashMap<String, Token> getUsers() {
//    	return this.users;
//    }

}
