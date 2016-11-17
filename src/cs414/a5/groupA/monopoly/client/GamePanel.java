package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GamePanel extends BasePanel {
	ViewBoard viewBoard = new ViewBoard();
	Label countdownLabel = new Label();
	DeedsDisplayPanel deedDisplayPanel = new DeedsDisplayPanel();
	TurnPanel turnPanel;
	LinkedHashMap<Integer, PlayerPiece> piecesByNumber = new LinkedHashMap<Integer, PlayerPiece>();
	
	Timer countdown;
	Timer refreshBoard;
	
	int playersTurn = 0; // Start at 0, will get incremented to 1 index first thing
	int numOfPlayers;
	
	private String playerName;
	private String gameId;
	
	public GamePanel() {
		
	}
	
	public GamePanel(String playerName, String gameId, int timeSelected) {
		setPlayerName(playerName);
		setGameId(gameId);
		final int gameTime = timeSelected;
		countdown = new Timer() {
			int minutesLeft = gameTime;
			int secondsLeft = 0;
			public void run() {
				if (secondsLeft == 0) {
					secondsLeft = 59;
					minutesLeft--;
				}
				else {
					secondsLeft--;
				}
				String labelText = minutesLeft + "m" + secondsLeft + "s";
				countdownLabel.setText(labelText);
				if (minutesLeft == 0 && secondsLeft == 0) {
					gameOver();
				}
			}
		};
		
		refreshBoard = new Timer() {
			@Override
			public void run() {
				viewBoard.renderBoard();
			}
		};
//		piecesByNumber.put(1,p1);
//		piecesByNumber.put(2,p2);
//		piecesByNumber.put(3,p3);
//		piecesByNumber.put(4,p4);
		init();
	}

	public void init() {
		ArrayList<String> playerNames = new ArrayList<String>();
		getGameService().initializeDeeds(gameId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
//		playerNames.add(p1.getName());
//		playerNames.add(p2.getName());
//		if (p3 != null) {
//			playerNames.add(p3.getName());
//		}
//		if (p4 != null) {
//			playerNames.add(p4.getName());
//		}

				GWT.log("Game successfully started!");
				
				initializeTimers();
				HorizontalPanel boardTurnDeedsPanel = new HorizontalPanel();
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
				
				VerticalPanel deedsPanel = new VerticalPanel();
				Label ownedDeedsLabel = new Label("Owned Deeds");
				ownedDeedsLabel.addStyleName("turnLabel");
				deedsPanel.add(ownedDeedsLabel);
				deedsPanel.add(deedDisplayPanel);
				
				boardTurnDeedsPanel.add(viewBoard);
				boardTurnDeedsPanel.add(turnPanel);
				boardTurnDeedsPanel.add(deedsPanel);
				
				viewBoard.renderBoard();
				getMainVerticalPanel().add(countdownLabel);
				getMainVerticalPanel().add(boardTurnDeedsPanel);
			
	}
	
	private void initializeTimers() {
		refreshBoard.scheduleRepeating(100);
		countdown.scheduleRepeating(1000);
	}
	
	private void doTurn() {
		getGameService().roll(getPlayerName(), getGameId(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				AlertPopup alert = new AlertPopup(result);
				viewBoard.renderBoard();
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
	
	
	private void setTurnPanelLabelByPlayerTurnNumber() {
		turnPanel.setTurnLabelText(getCurrentPlayerTurnName() + "'s turn!");
	}
	
	private String getCurrentPlayerTurnName() {
		return piecesByNumber.get(playersTurn).getName();
	}
	
	private void gameOver() {
		countdown.cancel();
		int winner = -1;
		int highestMoney = 0;
		for (Entry<Integer, PlayerPiece> entry : piecesByNumber.entrySet()) {
			Integer playerNumber = entry.getKey();
			PlayerPiece player = entry.getValue();
			if (player != null) {
				if (player.getMoney() > highestMoney) {
					highestMoney = player.getMoney();
					winner = playerNumber;
				}
			}
		}
		AlertPopup notify = new AlertPopup(piecesByNumber.get(winner).getName() + " wins the game!");
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
//	private void updateBoard() {
//		final HashMap<PlayerPiece, Integer> playerPositions = new HashMap<PlayerPiece, Integer>();
//		getGameService().getPlayerPositions(new AsyncCallback<Map<String,Integer>>() {
//
//			@Override
//			public void onFailure(Throwable arg0) {
//				GWT.log("Failed to get player positions");
//				
//			}
//
//			@Override
//			public void onSuccess(Map<String, Integer> value) {
//				for (Entry<String, Integer> entry : value.entrySet()) {
//					String playerName = entry.getKey();
//					Integer playerPosition = entry.getValue();
//					for (Entry<Integer, PlayerPiece> player : piecesByNumber.entrySet()) {
//						PlayerPiece storedPiece = player.getValue();
//						if (storedPiece != null) {
//							if (storedPiece.getName().equals(playerName)) { // if names are the same, put piece and new position to be redrawn
//								playerPositions.put(player.getValue(), playerPosition);
//								GWT.log(playerName + " is at position " + playerPosition);
//							}
//						}
//					}
//				}
//				viewBoard.renderBoard();
//			}
//		});
//	}
	
}
