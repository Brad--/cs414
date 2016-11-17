package cs414.a5.groupA.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AlertPopup extends PopupPanel {

	public AlertPopup(String message) {
		setupPopup(message, true, true);
	}
	
	public AlertPopup(String message, boolean showNow, boolean showCloseButton) {
		setupPopup(message, showNow, showCloseButton);
	}

	private void setupPopup(String message, boolean showNow, boolean showCloseButton) {
		VerticalPanel verticalPanel = new VerticalPanel();
		Label label = new Label(message);
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		verticalPanel.add(label);
		if(showCloseButton) {
			verticalPanel.add(closeButton);
		}

		setGlassEnabled(true);
		if(showNow) {
			showPopup();
		}
		setWidget(verticalPanel);
		
	}
	
	public void showPopup() {
		center();
	}
}
