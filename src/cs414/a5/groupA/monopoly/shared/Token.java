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
	private Boolean playerTurn;
    
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

	public Boolean isReady() {
		return ready;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public Boolean isInJail() {
		return inJail;
	}

	public void setInJail(Boolean inJail) {
		this.inJail = inJail;
	}

	public int getSpeedCount() {
		return speedCount;
	}

	public void setSpeedCount(int speedCount) {
		this.speedCount = speedCount;
	}

	public int getLastRollOne() {
		return lastRollOne;
	}

	public void setLastRollOne(int lastRollOne) {
		this.lastRollOne = lastRollOne;
	}

	public int getLastRollTwo() {
		return lastRollTwo;
	}

	public void setLastRollTwo(int lastRollTwo) {
		this.lastRollTwo = lastRollTwo;
	}

	public Boolean isPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(Boolean playerTurn) {
		this.playerTurn = playerTurn;
	}

}
