package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

import cs414.a5.groupA.monopoly.shared.Token;

/**
 * Created by Garrett on 11/14/2016.
 */
public class TokenBoardUtility extends Token {
    private HashMap<String, Token> users;
    private Die die;

//    public Token passGo(Token player){
//        player.getMoney() +=200;
//        return player;
//
//    public Token buyProperty(Token player, int price){
//        player.getMoney() -= price;
//        return player;
//    }
//
//    public Token payRent(Token player, int rent){
//        player.getMoney() -=rent;
//        return player;
//    }
//
//    public Token earnRent(Token player, int rent) {
//        player.getMoney() += rent;
//        return player;
//    }

    public Token handleRoll(Token currentPlayer) { // TODO return TokenActionWrapper?
        if (!users.containsKey(currentPlayer))
            addUser(currentPlayer);
//        Token updatedPlayer = die.roll(currentPlayer);
//        int move = updatedPlayer.getPosition();
        //@TODO: check for doubles
//        if (!currentPlayer.getJailStatus()){
//            if (deeds.get(currentPlayer.getPosition()).getOwner() == null) {
//                //TODO: display to player option to buy
//                delegateDeed(currentPlayer);
//            }
//            else if (currentPlayer.getPosition()==1){
//                currentPlayer = passGo(currentPlayer);
//            }
//            else if (currentPlayer.getPosition()==5){
//                //tax spot 200 or 10%
//                //@TODO show use choice to pay 200 or 10% of total
//            }
//            else if (currentPlayer.getPosition()== 39){
//                currentPlayer = payRent(currentPlayer, 75);
//            }
//            else{
//                // pay rent
//                payRent(currentPlayer);
//            }
//        }
//
//        // Pass go
//        if(!updatedPlayer.getJailStatus() && updatedPlayer.getPosition() < currentPlayer.getPosition()) {
//            currentPlayer = passGo(currentPlayer);
//        }
        return currentPlayer;
    }

    public void addUser(Token player){
        if (!users.containsKey(player.getPlayerName()))
            users.put(player.getPlayerName(), player);
    }

    public void delegateDeed(Token player) {
//        Space currSpace = deeds.get(player.getPosition());
//        if(currSpace instanceof Deed) {
//            int price = ((Deed)currSpace).getPrice();
//            if (users.get(player.getPlayerName()).getMoney() - price > 0) {
//                ((Deed)currSpace).changeOwnership(player);
//            } else {
//                System.err.println("You do not have enough money to buy this Deed");
//            }
//        }
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
