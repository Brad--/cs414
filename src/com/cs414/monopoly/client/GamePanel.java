package com.cs414.monopoly.client;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;

import com.cs414.monopoly.shared.ResponseAction;
import com.cs414.monopoly.shared.Token;
import com.cs414.monopoly.shared.TokenActionWrapper;
import com.cs414.monopoly.shared.UnownedDeedAction;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class GamePanel extends BasePanel {
	ViewBoard viewBoard = new ViewBoard();
	Label countdownLabel = new Label();

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
		initializeTimer();
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
		getMainVerticalPanel().add(countdownLabel);
		getMainVerticalPanel().add(boardAndTurnPanel);
		
	}
	
	private void initializeTimer() {
		Timer countdown = new Timer() {
			int minutesLeft = 30;
			int secondsLeft = 0;
			public void run() {
				String labelText = minutesLeft + "m" + secondsLeft + "s";
				countdownLabel.setText(labelText);
				if (secondsLeft == 0) {
					secondsLeft = 59;
					minutesLeft--;
				}
				else {
					secondsLeft--;
				}
				if (minutesLeft == 0 && secondsLeft == 0) {
					gameOver();
				}
			}
		};
		countdown.scheduleRepeating(1000);
	}
	
	private void doTurn() {
		getGreetingService().roll(getTurnToken(), new AsyncCallback<TokenActionWrapper>() {

			@Override
			public void onFailure(Throwable caught) {
				// no good
			}

			@Override
			public void onSuccess(TokenActionWrapper result) {
				Token resultToken = result.getToken();
				ResponseAction resultResponseAction = result.getResponseAction();
				tokens.put(playersTurn, resultToken);
				viewBoard.drawBoard(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4));
				if(resultResponseAction != null) {
					promptResponseAction(resultToken, resultResponseAction);
				} else {
					allowEndTurn();
				}
				if (resultToken.getSpeeding() > 0 && resultToken.getSpeeding() < 3) {
					turnPanel.setRolledDoublesText("You rolled doubles " + resultToken.getSpeeding() + " times");
					turnPanel.setRollButtonActive(true);
					turnPanel.setEndTurnButtonActive(false);
				}
				else {
					turnPanel.setRolledDoublesText("");
					turnPanel.setRollButtonActive(false);
					turnPanel.setEndTurnButtonActive(true);
				}
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
	
	private void allowEndTurn() {
		turnPanel.setRollButtonActive(false);
		turnPanel.setEndTurnButtonActive(true);
	}
	
	private void promptResponseAction(Token token, ResponseAction responseAction) {
		if(responseAction instanceof UnownedDeedAction) {
			AlertPopup alert = new AlertPopup("// TODO - this is where we would display a panel with the options, and apply to token or call server again if needed");
		}
		allowEndTurn();
	}
	
	private void setTurnPanelLabelByPlayerTurnNumber() {
		turnPanel.setTurnLabelText(getTurnToken().getName() + "'s turn!");
	}
	
	private Token getTurnToken() {
		return tokens.get(playersTurn);
	}
	
	private void gameOver() {
		// TODO end game
	}
	
}
