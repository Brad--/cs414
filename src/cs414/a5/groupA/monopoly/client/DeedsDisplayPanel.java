package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.Deed;

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
