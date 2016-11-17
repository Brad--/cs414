package cs414.a5.groupA.monopoly.shared;

import java.io.Serializable;

/**
 * Created by Garrett on 10/19/2016.
 */
public class Token implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 349032251412258158L;
	
	private int tokenId;
	private String gameId;
	private String playerName;
    private String gamePiece;
    private Integer money;
    private Integer position;
    private Boolean ready;
	private Boolean inJail;
	private int speedCount;
	private int lastRollOne;
	private int lastRollTwo;
    
    public Token() {
    	
    }
    
	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
    
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getGamePiece() {
		return gamePiece;
	}

	public void setGamePiece(String gamePiece) {
		this.gamePiece = gamePiece;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Boolean getReady() {
		return ready;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public void setInJail(Boolean jail){ this.inJail = jail;}

	public Boolean getInJail(){return inJail;}

	public void setSpeedCount(int speed){ speedCount = speed;}

	public int getSpeedCount(){return speedCount;}

	public void setLastRollOne(int roll){ lastRollOne = roll;}

	public int getLastRollOne(){ return lastRollOne;}

	public void setLastRollTwo(int roll){ lastRollTwo = roll;}

	public int getLastRollTwo(){ return lastRollTwo;}

}