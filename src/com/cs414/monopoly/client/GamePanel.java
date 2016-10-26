package com.cs414.monopoly.client;

import java.util.ArrayList;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class GamePanel extends BasePanel {
	
	ViewBoard viewBoard = new ViewBoard();
	TurnPanel turnPanel;
	ArrayList<Token> tokens = new ArrayList<Token>();
	int playersTurn = 0; // Start at 0, will get incremented to player 1 first thing
	int numOfPlayers;
	Token turnToken;
	
	
	public GamePanel(int numOfPlayers, Token p1, Token p2, Token p3, Token p4) {
		this.numOfPlayers = numOfPlayers;
		tokens.add(p1);
		tokens.add(p2);
		tokens.add(p3);
		tokens.add(p4);
		init(p1, p2, p3, p4);
	}

	public void init(Token p1, Token p2, Token p3, Token p4) {
		HorizontalPanel boardAndTurnPanel = new HorizontalPanel();
		turnPanel = new TurnPanel(){
			@Override
			public void handleRollClick() {
				doTurn();
			}
			@Override
			public void handleEndTurnClick() {
				endTurn();
			}
		};
		
		
		boardAndTurnPanel.add(viewBoard);
		boardAndTurnPanel.add(turnPanel);
		
		setNextTurnToken();
		setTurnPanelLabelByPlayerTurnNumber();
		
		viewBoard.drawBoard(p1, p2, p3, p4);
		getMainVerticalPanel().add(boardAndTurnPanel);
		
	}
	
	private void doTurn() {
		turnToken.updatePosition(1);
		viewBoard.drawBoard(tokens.get(0), tokens.get(1), tokens.get(2), tokens.get(3));
		turnPanel.setRollButtonActive(false);
		turnPanel.setEndTurnButtonActive(true);
		// TODO asnyc server call to roll, and then get back newly modified tokens and redraw
	}
	
	private void endTurn() {
		setNextTurnToken();
		setTurnPanelLabelByPlayerTurnNumber();
	}
	
	private void setNextTurnToken() {
		turnPanel.setRollButtonActive(true);
		turnPanel.setEndTurnButtonActive(false);
		if(playersTurn + 1 > numOfPlayers) {
			playersTurn = 1;
		} else {
			playersTurn++;
		}
		turnToken = tokens.get(playersTurn - 1);
	}
	
	private void setTurnPanelLabelByPlayerTurnNumber() {
		turnPanel.setTurnLabelText(turnToken.getName() + "'s turn!");
	}
	
}
