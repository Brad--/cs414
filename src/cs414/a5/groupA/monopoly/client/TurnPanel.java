package cs414.a5.groupA.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TurnPanel extends VerticalPanel {

	Label turnLabel = new Label("");
	Label rollLabel = new Label("");
	Button rollButton = new Button("Roll");
	Button endTurnButton = new Button("End Turn");
	Button tradeButton = new Button("Trade");
	Button rollOneButton = new Button("Advance 1 (DEBUG)");
	Button rollDoublesButton = new Button("Roll Doubles (DEBUG)");
	
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
		rollOneButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleRollOneClick();
			}
		});
		rollOneButton.setStyleName("turnButton");
		rollDoublesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handleRollDoublesClick();
			}
		});
		rollDoublesButton.setStyleName("turnButton");
		
		add(turnLabel);
		add(rollLabel);
		add(rollButton);
		add(tradeButton);
		add(endTurnButton);
		add(rollOneButton);
		add(rollDoublesButton);
	}
	
	public void setTurnLabelText(String text) {
		turnLabel.setText(text);
	}
	
	public void setRollLabel(String text) {
		rollLabel.setText(text);
	}
	
	public void handleRollClick() {
		
	}
	
	public void handleEndTurnClick() {
		
	}
	
	public void handleTradeClick() {
		
	}
	
	public void handleRollOneClick() {
		
	}
	
	public void handleRollDoublesClick() {
		
	}
	
	public void setRollButtonActive(boolean active) {
		rollButton.setEnabled(active);
	}
	
	public void setEndTurnButtonActive(boolean active) {
		endTurnButton.setEnabled(active);
	}
	
	
}
