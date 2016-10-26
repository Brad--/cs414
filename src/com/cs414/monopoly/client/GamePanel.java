package com.cs414.monopoly.client;

import java.util.ArrayList;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class GamePanel extends BasePanel {
	
	ViewBoard viewBoard = new ViewBoard();
	TurnPanel turnPanel = new TurnPanel();
	ArrayList<Token> tokens = new ArrayList<Token>();
	int playersTurn = 0; // Start at 0, will get incremented to player 1 first thing
	
	
	public GamePanel(Token p1, Token p2, Token p3, Token p4) {
		tokens.add(p1);
		tokens.add(p2);
		tokens.add(p3);
		tokens.add(p4);
		init(p1, p2, p3, p4);
	}

	public void init(Token p1, Token p2, Token p3, Token p4) {
		HorizontalPanel boardAndTurnPanel = new HorizontalPanel();
		
		boardAndTurnPanel.add(viewBoard);
		boardAndTurnPanel.add(turnPanel);
		
		viewBoard.drawBoard(p1, p2, p3, p4);
		getMainVerticalPanel().add(boardAndTurnPanel);
		
		doTurn();
	}
	
	private void doTurn() {
		final Token turnToken = getNextTurnToken();
	}
	
	private Token getNextTurnToken() {
		if(playersTurn + 1 > 4) {
			playersTurn = 1;
		} else {
			playersTurn++;
		}
		return tokens.get(playersTurn - 1);
	}
	
}
