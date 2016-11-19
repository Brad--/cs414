package cs414.a5.groupA.monopoly.shared;


import java.io.Serializable;
import java.util.ArrayList;

public class Trade implements Serializable {
    private int tradeId, oneMoney, twoMoney; // How much money each player is offering to trade
    private String oneName, twoName, gameId;
    private boolean oneAccepted, twoAccepted, finalized;
    ArrayList<String> oneDeeds, twoDeeds;

    public Trade(String p1, String p2) {
        oneMoney = twoMoney = 0;
        finalized = oneAccepted = twoAccepted = false;
        oneName = p1;
        twoName = p2;
        oneDeeds = new ArrayList<>();
        twoDeeds = new ArrayList<>();
    }

    public Trade() {
		// TODO Auto-generated constructor stub
	}

	public String getGameId() {
        return gameId;
    }
    public int getTradeId() {
        return tradeId;
    }

    public void setGameId(String id) {
        gameId = id;
    }
    public void setTradeId(int id) {
        tradeId = id;
    }

    // Token's don't get setter methods because the players should never within a trade
    public String getPlayerOneName() {
        return oneName;
    }
    
    public void setPlayerOneName(String playerOneName) {
    	oneName = playerOneName;
    }
    
    public String getPlayerTwoName() {
        return twoName;
    }
    
    public void setPlayerTwoName(String playerTwoName) {
    	twoName = playerTwoName;
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
    
    public void setPlayerOneDeeds(ArrayList<String> oneDeeds) {
    	this.oneDeeds = oneDeeds;
    }
    
    public void setPlayerTwoDeeds(ArrayList<String> twoDeeds) {
    	this.twoDeeds = twoDeeds;
    }

    public ArrayList<String> getPlayerOneDeeds() {
        return oneDeeds;
    }
    public ArrayList<String> getPlayerTwoDeeds() {
        return twoDeeds;
    }

    public void addPlayerOneDeed(String s) {
        oneDeeds.add(s);
    }
    public void addPlayerTwoDeed(String s) {
        twoDeeds.add(s);
    }

    public boolean isFinalized() { return finalized; }
    public void setFinalized(boolean f) { finalized = f; }
}
