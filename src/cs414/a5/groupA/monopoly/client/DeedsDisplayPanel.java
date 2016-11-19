package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

import cs414.a5.groupA.monopoly.shared.DatabaseDeed;


public class DeedsDisplayPanel extends FlexTable {
	
	
	public DeedsDisplayPanel() {

	}
	
	public void displayDeeds(ArrayList<DatabaseDeed> ownedDeedsList, String playerDisplaying) {
		clear();
		String printingPlayer = null;
		int row = 0;
		for(DatabaseDeed deed : ownedDeedsList) {
			final String deedName = deed.getDeedName();
			String playerName = deed.getPlayerName();
			String hexColor = deed.getHexColor();
			int numHouses = deed.getHousingCount();
			if(deedName != null && playerName != null) {
				Label playerNameLabel = new Label(playerName);
				playerNameLabel.getElement().getStyle().setFontSize(16, Unit.PX);
				if(printingPlayer == null) {
					setWidget(row, 0, playerNameLabel);
					row++;
					printingPlayer = playerName;
				} else {
					if(printingPlayer != playerName) {
						setWidget(row, 0, playerNameLabel);
						row++;
						printingPlayer = playerName;
					}
				}
				Label deedNameLabel = null;
				if (numHouses==0) {
					deedNameLabel = new Label(deedName);
				} else if (numHouses==1) {
					deedNameLabel = new Label(deedName + " (1 house)");
				}
				else if (numHouses==4) {
					deedNameLabel = new Label(deedName + " (1 hotel)");
				}
				else {
					deedNameLabel = new Label(deedName + " ("+numHouses+" houses)");
				}
				 
				if(hexColor != null && !hexColor.isEmpty()) {
					deedNameLabel.getElement().getStyle().setColor(hexColor);
				}
				setWidget(row, 0, deedNameLabel);
				if (playerName.equals(playerDisplaying)) {
					Button buyPropertyButton = new Button("Buy property");
					buyPropertyButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent arg0) {
							attemptToBuyProperty(deedName);
						}
					});
					setWidget(row, 1, buyPropertyButton);
					if (numHouses!=4) {
						buyPropertyButton.setEnabled(true);
					}
					else {
						buyPropertyButton.setEnabled(false);
					}
					Button sellPropertyButton = new Button("Sell property");
					sellPropertyButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							attemptToSellProperty(deedName);
						}
					});
					setWidget(row, 2, sellPropertyButton);
					if (numHouses!=0) {
						sellPropertyButton.setEnabled(true);
					}
					else {
						sellPropertyButton.setEnabled(false);
					}
				}
				row++;
			}
		}
	}
	
	public void attemptToBuyProperty(String deedName) {
		
	}
	
	public void attemptToSellProperty(String deedName) {
		
	}
}
