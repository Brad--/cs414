package com.cs414.monopoly.client;

import java.util.ArrayList;

import com.cs414.monopoly.shared.Deed;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DeedsDisplayPanel extends FlexTable {
	
	
	public DeedsDisplayPanel() {

	}
	
	public void displayDeeds(ArrayList<Deed> ownedDeeds) {
		clear();
		int row = 0;
		for(Deed deed : ownedDeeds) {
			Label colorLabel = new Label(" ");
			if(deed.getColorHex() != null) {
				colorLabel.getElement().getStyle().setBackgroundColor(deed.getColorHex());
			}
			Label nameLabel = new Label(deed.getName());
			setWidget(row, 0, colorLabel);
			setWidget(row, 1, nameLabel);
			row++;
		}
	}
}
