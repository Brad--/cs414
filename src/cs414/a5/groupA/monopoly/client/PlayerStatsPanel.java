package cs414.a5.groupA.monopoly.client;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.server.Token;

public class PlayerStatsPanel extends VerticalPanel {
	
	Image gamePiece = new Image();
	Label nameLabel = new Label();
	Label moneyLabel = new Label();
	
	public PlayerStatsPanel() {
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
	
	public void setPlayer(PlayerPiece player) {
		if(player != null) {
			gamePiece.setUrl(player.getGamePiece());
			nameLabel.setText(player.getName());
			moneyLabel.setText("$" + player.getMoney());
		}
	}

}
