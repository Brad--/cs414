package com.cs414.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AlertPopup extends PopupPanel {

	public AlertPopup(String message) {
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
		verticalPanel.add(closeButton);

		setGlassEnabled(true);
		center();
		setWidget(verticalPanel);
	}
}
