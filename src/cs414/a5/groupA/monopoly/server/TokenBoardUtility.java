package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Garrett on 11/14/2016.
 */
public class TokenBoardUtility extends Token {
    private HashMap<String, Token> users;
    private Die die;

    public Token passGo(Token player){
        player.cashMoney +=200;
        return player;
    }

    public Token buyProperty(Token player, int price){
        player.cashMoney -= price;
        return player;
    }

    public Token payRent(Token player, int rent){
        player.cashMoney -=rent;
        return player;
    }

    public Token earnRent(Token player, int rent) {
        player.cashMoney += rent;
        return player;
    }

    public Token handleRoll(Token currentPlayer) { // TODO return TokenActionWrapper?
        if (!users.containsKey(currentPlayer))
            addUser(currentPlayer);
        Token updatedPlayer = die.roll(currentPlayer);
        int move = updatedPlayer.getCurrentPosition();
        //@TODO: check for doubles
        if (!currentPlayer.getJailStatus()){
            if (deeds.get(currentPlayer.getCurrentPosition()).getOwner() == null) {
                //TODO: display to player option to buy
                delegateDeed(currentPlayer);
            }
            else if (currentPlayer.getCurrentPosition()==1){
                currentPlayer = passGo(currentPlayer);
            }
            else if (currentPlayer.getCurrentPosition()==5){
                //tax spot 200 or 10%
                //@TODO show use choice to pay 200 or 10% of total
            }
            else if (currentPlayer.getCurrentPosition()== 39){
                currentPlayer = payRent(currentPlayer, 75);
            }
            else{
                // pay rent
                payRent(currentPlayer);
            }
        }

        // Pass go
        if(!updatedPlayer.getJailStatus() && updatedPlayer.getCurrentPosition() < currentPlayer.getCurrentPosition()) {
            currentPlayer = passGo(currentPlayer);
        }
        return currentPlayer;
    }

    public void addUser(Token player){
        if (!users.containsKey(player.getName()))
            users.put(player.getName(), player);
    }

    public void delegateDeed(Token player) {
        Space currSpace = deeds.get(player.getCurrentPosition());
        if(currSpace instanceof Deed) {
            int price = ((Deed)currSpace).getPrice();
            if (users.get(player.getName()).getCashMoney() - price > 0) {
                ((Deed)currSpace).changeOwnership(player);
            } else {
                System.err.println("You do not have enough money to buy this Deed");
            }
        }
    }

    public void payRent(Token player){
        if(deeds.get(player.getCurrentPosition()) instanceof Deed) {
            int rent = ((Deed)deeds.get(player.getCurrentPosition())).getRent();
            if (player.getCashMoney() - rent > 0) {
                deeds.get(player.getCurrentPosition()).action(player);
            } else {
                System.err.println("You do not have enough money to pay the rent #getEvicted!");
                //@TODO: if a player runs out of money they lose stop them from playing
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
            if (deed.getOwner() != null && deed.getOwner().equals(player.getName())){
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
        users.put(token.getName(), token);
    }

    public HashMap<String, Token> getUsers() {
        return this.users;
    }
}
