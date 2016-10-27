package com.cs414.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TurnPanel extends VerticalPanel {

	Label turnLabel = new Label("");
	Label rolledDoublesLabel = new Label("");
	Button rollButton = new Button("Roll");
	Button endTurnButton = new Button("End Turn");
	
	public TurnPanel() {
		turnLabel.addStyleName("turnLabel");
		rollButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleRollClick();
			}
		});
		rollButton.addStyleName("rollButton");
		
		endTurnButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleEndTurnClick();
			}
		});
		endTurnButton.addStyleName("endTurnButton");
		
		add(turnLabel);
		add(rolledDoublesLabel);
		add(rollButton);
		add(endTurnButton);
	}
	
	public void setTurnLabelText(String text) {
		turnLabel.setText(text);
	}
	
	public void setRolledDoublesText(String text) {
		rolledDoublesLabel.setText(text);
	}
	
	public void handleRollClick() {
		
	}
	
	public void handleEndTurnClick() {
		
	}
	
	public void setRollButtonActive(boolean active) {
		rollButton.setEnabled(active);
	}
	
	public void setEndTurnButtonActive(boolean active) {
		endTurnButton.setEnabled(active);
	}
	
	
}
