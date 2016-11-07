package cs414.a5.groupA.monopoly.client;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class DeedsDisplayPanel extends VerticalPanel {
	
	
	public DeedsDisplayPanel() {

	}
	
	public void displayDeeds(HashMap<String, String> ownedDeedNameAndColor) {
		clear();
		for(Entry<String, String> entry : ownedDeedNameAndColor.entrySet()) {
			String name = entry.getKey();
			String color = entry.getValue();
			Label nameLabel = new Label(name);
			if(color != null && !color.isEmpty()) {
				nameLabel.getElement().getStyle().setColor(color);
			}
			add(nameLabel);
		}
	}
}
