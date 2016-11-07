package cs414.a5.groupA.monopoly.client;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.Token;

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
