package com.cs414.monopoly.client;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TokenStatsPanel extends VerticalPanel {
	
	Image gamePiece = new Image();
	Label nameLabel = new Label();
	Label moneyLabel = new Label();
	
	public TokenStatsPanel() {
		init();
	}
	
	public void init() {
		addStyleName("tokenStatsPanel");
		gamePiece.addStyleName("statsGamePiece");
		nameLabel.addStyleName("statsNameLabel");
		moneyLabel.addStyleName("statsMoneyLabel");
		add(gamePiece);
		add(nameLabel);
		add(moneyLabel);
	}
	
	public void setToken(Token token) {
		if(token != null) {
			gamePiece.setUrl(token.getGamePiece());
			nameLabel.setText(token.getName());
			moneyLabel.setText("$" + token.getCashMoney());
		}
	}

}
