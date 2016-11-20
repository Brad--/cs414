package cs414.a5.groupA.monopoly.shared;

import java.io.Serializable;

public class BidResult implements Serializable {

	private String bidWinnerName;
	private int position;
	private int bidAmount;
	private String message;
	
	public String getBidWinnerName() {
		return bidWinnerName;
	}
	public void setBidWinnerName(String bidWinnerName) {
		this.bidWinnerName = bidWinnerName;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
