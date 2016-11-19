package cs414.a5.groupA.monopoly.shared;

public class DeedBid {

	private String gameId;
	private int position;
	private String playerName;
	private int playerBid;
	
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getPlayerBid() {
		return playerBid;
	}
	public void setPlayerBid(int playerBid) {
		this.playerBid = playerBid;
	}
	
}
