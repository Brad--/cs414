package com.cs414.monopoly.client;

import java.util.ArrayList;

import com.cs414.monopoly.shared.Deed;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DeedsDisplayPanel extends VerticalPanel {
	
	
	public DeedsDisplayPanel() {

	}
	
	public void displayDeeds(ArrayList<Deed> ownedDeeds) {
		clear();
		for(Deed deed : ownedDeeds) {
			Label nameLabel = new Label(deed.getName());
			if(deed.getColorHex() != null) {
				nameLabel.getElement().getStyle().setColor(deed.getColorHex());
			}
			add(nameLabel);
		}
	}
}
