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
            if (listPos == 0) {
                deeds.add(new GoSpace(i));
            } else if (listPos == 4) {
                deeds.add(new TaxSpace(i));
            } else if (listPos == 5) {
                deeds.add(new Railroad(i));
            }  else if (listPos == 10) {
                deeds.add(new Jail(i));
            } else if (listPos == 12) {
                deeds.add(new UtilitySpace(i));
            } else if (listPos == 15) {
                deeds.add(new Railroad(i));
            } else if (listPos == 20) {
                deeds.add(new FreeParking(i));
            } else if (listPos == 25) {
                deeds.add(new Railroad(i));
            } else if (listPos == 28) {
                deeds.add(new UtilitySpace(i));
            } else if (listPos == 35) {
                deeds.add(new Railroad(i));
            } else if (listPos == 38) {
                deeds.add(new TaxSpace(i));
            } else if (listPos == 7 || listPos == 22
                    || listPos == 2 || listPos == 17
                    || listPos == 33 || listPos ==36) {
                deeds.add(new Card(i));
            } else if (listPos == 30) {
            	deeds.add(new GotoJail(i));
            } else {
                deeds.add(new Deed(i));
            }
        }
        //@TODO: start GUI
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


}
