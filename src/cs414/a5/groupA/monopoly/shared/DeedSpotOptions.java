package cs414.a5.groupA.monopoly.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class DeedSpotOptions implements Serializable {

	private String owner;
	
	public static final String BUY = "Buy";
	public static final String DO_NOT_BUY = "Do Not Buy";
	
	public ArrayList<String> getOptions() {
		ArrayList<String> options = new ArrayList<String>();
		if(getOwner() == null) {
			options.add(BUY);
			options.add(DO_NOT_BUY);
		} else {
			
		}
		return options;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
