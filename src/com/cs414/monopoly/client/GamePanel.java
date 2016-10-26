package com.cs414.monopoly.client;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GamePanel extends BasePanel {
	
	ViewBoard viewBoard = new ViewBoard();
	
	public GamePanel(Token p1, Token p2, Token p3, Token p4) {
		init(p1, p2, p3, p4);
	}

	public void init(Token p1, Token p2, Token p3, Token p4) {
		HorizontalPanel boardAndTurnPanel = new HorizontalPanel();
		
		TurnPanel turnPanel = new TurnPanel();
		turnPanel.setTurnLabelText("Player 1s turn"); // just example text
		
		boardAndTurnPanel.add(viewBoard);
		boardAndTurnPanel.add(turnPanel);
		
		viewBoard.drawBoard(p1, p2, p3, p4);
		getMainVerticalPanel().add(boardAndTurnPanel);
	}
	
}
