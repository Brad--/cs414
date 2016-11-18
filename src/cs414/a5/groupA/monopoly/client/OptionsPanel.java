package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OptionsPanel extends PopupPanel {
	
	private String selectedOption;

	public OptionsPanel(ArrayList<String> options) {
		VerticalPanel verticalPanel = new VerticalPanel();
		
		verticalPanel.add(new Label("What would you like to do on this space?"));

		for(final String option : options) {
			Button button = new Button(option);
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					setSelectedOption(option);
					handleButtonClick();
					hide();
				}
			});
			
			verticalPanel.add(button);
		}
		setWidget(verticalPanel);
		
		setGlassEnabled(true);
		center();
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	public void handleButtonClick() {
		
	}
}
