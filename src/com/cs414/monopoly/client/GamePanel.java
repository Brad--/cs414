package com.cs414.monopoly.client;

import java.util.LinkedHashMap;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class GamePanel extends BasePanel {
	
	ViewBoard viewBoard = new ViewBoard();
	TurnPanel turnPanel;
	LinkedHashMap<Integer, Token> tokens = new LinkedHashMap<Integer, Token>();
	int playersTurn = 0; // Start at 0, will get incremented to 1 index first thing
	int numOfPlayers;
	
	public GamePanel() {
		
	}
	
	public GamePanel(int numOfPlayers, Token p1, Token p2, Token p3, Token p4) {
		this.numOfPlayers = numOfPlayers;
		tokens.put(1,p1);
		tokens.put(2,p2);
		tokens.put(3,p3);
		tokens.put(4,p4);
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
		getGreetingService().roll(getTurnToken(), new AsyncCallback<Token>() {

			@Override
			public void onFailure(Throwable caught) {
				// no good
			}

			@Override
			public void onSuccess(Token result) {
				tokens.put(playersTurn, result);
				viewBoard.drawBoard(tokens.get(0), tokens.get(1), tokens.get(2), tokens.get(3));
				turnPanel.setRollButtonActive(false);
				turnPanel.setEndTurnButtonActive(true);
			}});
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
	}
	
	private void setTurnPanelLabelByPlayerTurnNumber() {
		turnPanel.setTurnLabelText(getTurnToken().getName() + "'s turn!");
	}
	
	private Token getTurnToken() {
		return tokens.get(playersTurn);
	}
	
}
