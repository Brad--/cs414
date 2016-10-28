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
	Button tradeButton = new Button("Trade");
	
	public TurnPanel() {
		turnLabel.addStyleName("turnLabel");
		rollButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleRollClick();
			}
		});
		rollButton.setStyleName("turnButton");
		
		endTurnButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleEndTurnClick();
			}
		});
		endTurnButton.setStyleName("turnButton");
		tradeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleTradeClick();
			}
		});
		tradeButton.setStyleName("turnButton");
		
		add(turnLabel);
		add(rolledDoublesLabel);
		add(rollButton);
		add(tradeButton);
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
	
	public void handleTradeClick() {
		
	}
	
	public void setRollButtonActive(boolean active) {
		rollButton.setEnabled(active);
	}
	
	public void setEndTurnButtonActive(boolean active) {
		endTurnButton.setEnabled(active);
	}
	
	
}
