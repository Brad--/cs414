package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private Die die;
    private HashMap<String, Token> users;
    public ArrayList<Space> deeds = new ArrayList<>();
    public Board(){
        init();
    }

    public void init(){
        users = new HashMap<>();
        die = new Die();
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
        if (!users.containsKey(currentPlayer))
            addUser(currentPlayer);
    	Token updatedPlayer = die.roll(currentPlayer);
        int move = updatedPlayer.getCurrentPosition();
        //@TODO: check for doubles
        if (!currentPlayer.inJail()){
            if (deeds.get(currentPlayer.getCurrentPosition()).getOwner() == null) {
                //TODO: display to player option to buy
                delegateDeed(currentPlayer);
            }
            else if (currentPlayer.getCurrentPosition()==0){
                currentPlayer.passGo(); // TODO needs to also account for PASSING of go as well
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

        // Pass go
        if(!updatedPlayer.inJail() && updatedPlayer.getCurrentPosition() < currentPlayer.getCurrentPosition()) {
            updatedPlayer.earnRent(200);
        }
        return currentPlayer;
    }

    public void addUser(Token player){
        if (!users.containsKey(player.getName()))
            users.put(player.getName(), player);
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
