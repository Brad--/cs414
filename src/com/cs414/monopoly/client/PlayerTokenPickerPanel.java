package com.cs414.monopoly.client;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PlayerTokenPickerPanel extends VerticalPanel {
	
	private boolean active = true;
	
	final TextBox nameField = new TextBox();
	
	private final RadioButton coffeeCupRB = new RadioButton("cup");
	private final RadioButton coffeeMugRB = new RadioButton("mug");
	private final RadioButton coffeePotRB = new RadioButton("pot");
	private final RadioButton thermoRB = new RadioButton("thermo");
	
	private final Image coffeeCupImage = new Image("img/token/coffee-cup.png");
	private final Image coffeeMugImage = new Image("img/token/coffee-mug.png");
	private final Image coffeePotImage = new Image("img/token/coffee-pot.png");
	private final Image thermoImage = new Image("img/token/thermo.png");
	
	public PlayerTokenPickerPanel(int playerNumber) {
		init(playerNumber);
	}
	
	public void init(int playerNumber)
	{
		
		Label lblPlayer = new Label("Player " + playerNumber + " options");
		nameField.getElement().setPropertyString("placeholder", "Player Name");
		
		Label lblToken = new Label("Choose a player token:");
		
		FlexTable flexTable = new FlexTable();
		
		coffeeCupRB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deselectAll();
				coffeeCupRB.setValue(true);
			}
		});
		coffeeMugRB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deselectAll();
				coffeeMugRB.setValue(true);
			}
		});
		coffeePotRB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deselectAll();
				coffeePotRB.setValue(true);
			}
		});
		thermoRB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deselectAll();
				thermoRB.setValue(true);
			}
		});
		
		flexTable.setWidget(0,0, coffeeCupRB);
		flexTable.setWidget(0,1, coffeeCupImage);
		flexTable.setWidget(1,0, coffeeMugRB);
		flexTable.setWidget(1,1, coffeeMugImage);
		flexTable.setWidget(2,0, coffeePotRB);
		flexTable.setWidget(2,1, coffeePotImage);
		flexTable.setWidget(3,0, thermoRB);
		flexTable.setWidget(3,1, thermoImage);
		
		add(lblPlayer);
		add(nameField);
		add(lblToken);
		add(flexTable);

	}

	public Image getSelectedImage() {
		Image returnImage = null;
		if(coffeeCupRB.getValue()) {
			returnImage = coffeeCupImage;
		} else if(coffeeMugRB.getValue()) {
			returnImage = coffeeMugImage;
		} else if (coffeePotRB.getValue()) {
			returnImage = coffeePotImage;
		} else if (thermoRB.getValue()) {
			returnImage = thermoImage;
		}
		
		return returnImage;
	}

	
	private void deselectAll() {
		coffeeCupRB.setValue(false);
		coffeeMugRB.setValue(false);
		coffeePotRB.setValue(false);
		thermoRB.setValue(false);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		setVisible(active);
	}
	
	public boolean isValidToken() {
		boolean returnValue = false;
		if(getName() != null && !getName().isEmpty() && getSelectedImage() != null) {
			returnValue = true;
		}
		
		return returnValue;
	}
	
	public String getName() {
		return nameField.getValue();
	}
	
	public Token getPlayerToken() {
		return new Token(getName(), getSelectedImage());
	}
	
	
}
