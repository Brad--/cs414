package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.DatabaseDeed;


public class DeedsDisplayPanel extends VerticalPanel {
	
	
	public DeedsDisplayPanel() {

	}
	
	public void displayDeeds(ArrayList<DatabaseDeed> ownedDeedsList) {
		clear();
		String printingPlayer = null;
		for(DatabaseDeed deed : ownedDeedsList) {
			String deedName = deed.getDeedName();
			String playerName = deed.getPlayerName();
			String hexColor = deed.getHexColor();
			if(deedName != null && playerName != null) {
				Label playerNameLabel = new Label(playerName);
				playerNameLabel.getElement().getStyle().setFontSize(16, Unit.PX);
				if(printingPlayer == null) {
					add(new HTML("&nbsp"));
					add(playerNameLabel);
					printingPlayer = playerName;
				} else {
					if(printingPlayer != playerName) {
						add(new HTML("&nbsp"));
						add(playerNameLabel);
						printingPlayer = playerName;
					}
				}
				Label deedNameLabel = new Label(deedName);
				if(hexColor != null && !hexColor.isEmpty()) {
					deedNameLabel.getElement().getStyle().setColor(hexColor);
				}
				add(deedNameLabel);
			}
		}
	}
}
