package cs414.a5.groupA.monopoly.shared;

import java.io.Serializable;

public class DatabaseDeed implements Serializable {

	private int deedId;
	private String gameId;
	private String deedName;
	private int position;
	private String playerName;
	private int housingCount;
	private String propertyGroup;
	private String hexColor;
	
	public int getDeedId() {
		return deedId;
	}
	public void setDeedId(int deedId) {
		this.deedId = deedId;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getDeedName() {
		return deedName;
	}
	public void setDeedName(String deedName) {
		this.deedName = deedName;
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
	public int getHousingCount() {
		return housingCount;
	}
	public void setHousingCount(int housingCount) {
		this.housingCount = housingCount;
	}
	public String getPropertyGroup() {
		return propertyGroup;
	}
	public void setPropertyGroup(String propertyGroup) {
		this.propertyGroup = propertyGroup;
	}
	public String getHexColor() {
		return hexColor;
	}
	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
	
}
