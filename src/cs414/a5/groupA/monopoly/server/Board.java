package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    public ArrayList<Space> deeds = new ArrayList<>();
    public Board(){
        init();
    }

    public void init(){
        int listPos;
        for (int i = 1; i <= 40; i++) {
            listPos = i - 1;
            if (listPos == 0) {
                deeds.add(new GoSpace(this, i));
            } else if (listPos == 4) {
                deeds.add(new TaxSpace(this, i));
            } else if (listPos == 5) {
                deeds.add(new Railroad(this, i));
            } else if (listPos == 10) {
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
            } else {
                deeds.add(new Deed(this, i));
            }
        }
        //@TODO: start GUI
    }

}
