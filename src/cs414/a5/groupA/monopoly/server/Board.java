package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

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
        for (int i = 1; i <= 40; i++){
            listPos = i - 1;
            if(listPos == 0) {
                deeds.add(new GoSpace(this, i));
            }
            else if(listPos == 4){
                deeds.add(new TaxSpace(this, i));
            }
            else if(listPos == 5) {
                deeds.add(new Railroad(this, i));
            }
            else if(listPos == 10) {
                deeds.add(new Jail(this, i));
            }
            else if(listPos == 12) {
                deeds.add(new UtilitySpace(this, i));
            }
            else if(listPos == 15) {
                deeds.add(new Railroad(this, i));
            }
            else if(listPos == 20) {
                deeds.add(new FreeParking(this, i));
            }
            else if(listPos == 25) {
                deeds.add(new Railroad(this, i));
            }
            else if(listPos == 28) {
                deeds.add(new UtilitySpace(this, i));
            }
            else if(listPos == 35) {
                deeds.add(new Railroad(this, i));
            }
            else if(listPos == 38) {
                deeds.add(new TaxSpace(this, i));
            }
            else {
                deeds.add(new Deed(this, i));
            }
        }

        deeds.get(0).setName("Go");
        deeds.get(1).setName("Bean Trees");
        deeds.get(2).setName("Community Chest");
        deeds.get(3).setName("Blackbird");
        deeds.get(4).setName("CSU Stadium Tax");
        deeds.get(5).setName("Laurel Station");
        deeds.get(6).setName("Cuppy's");
        deeds.get(7).setName("Chance");
        deeds.get(8).setName("Little Bird");
        deeds.get(9).setName("Bindle");
        deeds.get(10).setName("Jail");
        deeds.get(11).setName("Momo Lolo");
        deeds.get(12).setName("Electric");
        deeds.get(13).setName("Morgan's Grind");
        deeds.get(14).setName("Sweet Sinsations");
        deeds.get(15).setName("Mulberry Station");
        deeds.get(16).setName("Fort Collins Coffee House");
        deeds.get(17).setName("Community Chest");
        deeds.get(18).setName("Wild Boar");
        deeds.get(19).setName("Alley Cat");
        deeds.get(20).setName("Free Parking");
        deeds.get(21).setName("Bean Cycle");
        deeds.get(22).setName("Chance");
        deeds.get(23).setName("Human Bean");
        deeds.get(24).setName("Daz Bog");
        deeds.get(25).setName("Olive Station");
        deeds.get(26).setName("Cups Coffee");
        deeds.get(27).setName("Everyday Joe's");
        deeds.get(28).setName("Utilities");
        deeds.get(29).setName("Harbinger Coffee");
        deeds.get(30).setName("Goto Jail");
        deeds.get(31).setName("Crooked Cup");
        deeds.get(32).setName("Starry Night");
        deeds.get(33).setName("Community Chest");
        deeds.get(34).setName("Mugs");
        deeds.get(35).setName("Mountain Station");
        deeds.get(36).setName("Chance");
        deeds.get(37).setName("Dunkin Donuts");
        deeds.get(38).setName("Luxury Tax");
        deeds.get(39).setName("Starbucks");


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
            else if (currentPlayer.getCurrentPosition()==1){
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
