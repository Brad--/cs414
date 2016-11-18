package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;

import java.util.ArrayList;

public class Trade {
    private int oneMoney, twoMoney; // How much money each player is offering to trade
    private Token oneToken, twoToken;
    private boolean oneAccepted, twoAccepted;
    ArrayList<Deed> oneDeeds, twoDeeds;

    public Trade(Token p1, Token p2) {
        oneMoney = twoMoney = 0;
        oneAccepted = twoAccepted = false;
        oneToken = p1;
        twoToken = p2;
        oneDeeds = new ArrayList<>();
        twoDeeds = new ArrayList<>();
    }

    // Token's don't get setter methods because the players should never within a trade
    public Token getPlayerOneToken() {
        return oneToken;
    }
    public Token getPlayerTwoToken() {
        return twoToken;
    }

    public int getPlayerOneMoney() {
        return oneMoney;
    }
    public int getPlayerTwoMoney() {
        return twoMoney;
    }

    public void setPlayerOneMoney(int amount) {
        oneMoney = amount;
    }
    public void setPlayerTwoMoney(int amount) {
        twoMoney = amount;
    }

    public boolean getPlayerOneAccepted() {
        return oneAccepted;
    }
    public boolean getPlayerTwoAccepted() {
        return twoAccepted;
    }
    public boolean bothAccepted() {
        return oneAccepted && twoAccepted;
    }

    public void setPlayerOneAccepted(boolean value) {
        oneAccepted = value;
    }
    public void setPlayerTwoAccepted(boolean value) {
        twoAccepted = value;
    }

    public ArrayList<Deed> getPlayerOneDeeds() {
        return oneDeeds;
    }
    public ArrayList<Deed> getPlayerTwoDeeds() {
        return twoDeeds;
    }

    public void addPlayerOneDeed(Deed d) {
        oneDeeds.add(d);
    }
    public void addPlayerTwoDeed(Deed d) {
        twoDeeds.add(d);
    }
}
