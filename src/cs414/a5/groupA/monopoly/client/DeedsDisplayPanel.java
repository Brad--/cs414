package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
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
				else if (numHouses==5) {
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
					String mortgageButtonString;
					final Boolean mortgaged = deed.isMortgaged();
					if (mortgaged) {
						mortgageButtonString = "Un-mortgage property";
					}
					else {
						mortgageButtonString = "Mortgage property";
					}
					Button mortgageButton = new Button(mortgageButtonString);
					mortgageButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent arg0) {
							if (mortgaged) {
								attemptToDeMortgage(deedName);
							}
							else {
								attemptToMortgage(deedName);
							}
						}						
					});
					setWidget(row, 1, mortgageButton);
					
					Button buyHousingButton = new Button("Buy housing");
					buyHousingButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent arg0) {
							attemptToBuyHousing(deedName);
						}
					});
					setWidget(row, 2, buyHousingButton);
					if (numHouses!=5) {
						buyHousingButton.setEnabled(true);
					}
					else {
						buyHousingButton.setEnabled(false);
					}
					
					Button sellHousingButton = new Button("Sell housing");
					sellHousingButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							attemptToSellHousing(deedName);
						}
					});
					setWidget(row, 3, sellHousingButton);
					if (numHouses!=0) {
						sellHousingButton.setEnabled(true);
					}
					else {
						sellHousingButton.setEnabled(false);
					}
				}
				row++;
			}
		}
	}
	
	public void attemptToBuyHousing(String deedName) {
		
	}
	
	public void attemptToSellHousing(String deedName) {
		
	}
	
	public void attemptToMortgage(String deedName) {
		
	}
	
	public void attemptToDeMortgage(String deedName) {
		
	}
}
