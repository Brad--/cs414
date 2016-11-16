//package cs414.a5.groupA.monopoly.client;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.user.client.Timer;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
//public class GamePanel extends BasePanel {
//	ViewBoard viewBoard = new ViewBoard();
//	Label countdownLabel = new Label();
//	DeedsDisplayPanel deedDisplayPanel = new DeedsDisplayPanel();
//	TurnPanel turnPanel;
//	LinkedHashMap<Integer, PlayerPiece> piecesByNumber = new LinkedHashMap<Integer, PlayerPiece>();
//	
//	Timer countdown;
//	
//	int playersTurn = 0; // Start at 0, will get incremented to 1 index first thing
//	int numOfPlayers;
//	
//	public GamePanel() {
//		
//	}
//	
//	public GamePanel(int numOfPlayers, PlayerPiece p1, PlayerPiece p2, PlayerPiece p3, PlayerPiece p4, int timeSelected) {
//		final int gameTime = timeSelected;
//		this.numOfPlayers = numOfPlayers;
//		countdown = new Timer() {
//			int minutesLeft = gameTime;
//			int secondsLeft = 0;
//			public void run() {
//				if (secondsLeft == 0) {
//					secondsLeft = 59;
//					minutesLeft--;
//				}
//				else {
//					secondsLeft--;
//				}
//				String labelText = minutesLeft + "m" + secondsLeft + "s";
//				countdownLabel.setText(labelText);
//				if (minutesLeft == 0 && secondsLeft == 0) {
//					gameOver();
//				}
//			}
//		};
//		piecesByNumber.put(1,p1);
//		piecesByNumber.put(2,p2);
//		piecesByNumber.put(3,p3);
//		piecesByNumber.put(4,p4);
//		init(p1, p2, p3, p4);
//	}
//
//	public void init(PlayerPiece p1, PlayerPiece p2, PlayerPiece p3, PlayerPiece p4) {
//		ArrayList<String> playerNames = new ArrayList<String>();
//		playerNames.add(p1.getName());
//		playerNames.add(p2.getName());
//		if (p3 != null) {
//			playerNames.add(p3.getName());
//		}
//		if (p4 != null) {
//			playerNames.add(p4.getName());
//		}
//		getGameService().initializeGame(playerNames, new AsyncCallback<Void>() {
//
//			@Override
//			public void onFailure(Throwable arg0) {
//				GWT.log("Failed to start game, abandon all hope.");
//				
//			}
//
//			@Override
//			public void onSuccess(Void result) {
//				GWT.log("Game successfully started!");
//				
//				initializeTimer();
//				HorizontalPanel boardTurnDeedsPanel = new HorizontalPanel();
//				turnPanel = new TurnPanel(){
//					@Override
//					public void handleRollClick() {
//						doTurn();
//					}
//					@Override
//					public void handleEndTurnClick() {
//						endTurn();
//					}
//				};
//				
//				VerticalPanel deedsPanel = new VerticalPanel();
//				Label ownedDeedsLabel = new Label("Owned Deeds");
//				ownedDeedsLabel.addStyleName("turnLabel");
//				deedsPanel.add(ownedDeedsLabel);
//				deedsPanel.add(deedDisplayPanel);
//				
//				boardTurnDeedsPanel.add(viewBoard);
//				boardTurnDeedsPanel.add(turnPanel);
//				boardTurnDeedsPanel.add(deedsPanel);
//				
//				setNextTurnToken();
//				setTurnPanelLabelByPlayerTurnNumber();
//				//deedDisplayPanel.displayDeeds(board.getOwnedDeeds(p1));
//				
//				updateBoard();
//				getMainVerticalPanel().add(countdownLabel);
//				getMainVerticalPanel().add(boardTurnDeedsPanel);
//			}
//			
//		});
//	}
//	
//	private void initializeTimer() {
//		
//		countdown.scheduleRepeating(1000);
//	}
//	
//	private void doTurn() {
//		getGameService().roll(getCurrentPlayerTurnName(), new AsyncCallback<String>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// no good
//			}
//
//			@Override
//			public void onSuccess(String result) {
//				updateBoard();
//				// TODO implement speeding as well
//				final String rollString = result;
//				//turnPanel.setRollLabel(result);
//				getGameService().getSpeedingAmount(getCurrentPlayerTurnName(), new AsyncCallback<Integer>() {
//
//					@Override
//					public void onFailure(Throwable arg0) {
//						GWT.log("Failed to get speeding amount");
//					}
//
//					@Override
//					public void onSuccess(Integer speedingAmount) {
//						if (speedingAmount > 0 && speedingAmount < 3) {
//							turnPanel.setRollLabel(rollString + " - You rolled doubles " + speedingAmount + " times");
//						}
//						else {
//							turnPanel.setRollLabel(rollString);
//							allowEndTurn();
//						}
//						
//					}
//				});
////				if (resultToken.getSpeeding() > 0 && resultToken.getSpeeding() < 3) {
////					turnPanel.setRollLabel(resultToken.getLastRollDieOne() + "+" + resultToken.getLastRollDieTwo() + 
////				" - You rolled doubles " + resultToken.getSpeeding() + " times");
////					turnPanel.setRollButtonActive(true);
////					turnPanel.setEndTurnButtonActive(false);
////				}
////				else {
////					turnPanel.setRollLabel(resultToken.getLastRollDieOne() + "+" + resultToken.getLastRollDieTwo());
////					turnPanel.setRollButtonActive(false);
////					turnPanel.setEndTurnButtonActive(true);
////				}
//			}});
//	}
//	
//	private void endTurn() {
//		setNextTurnToken();
//		setTurnPanelLabelByPlayerTurnNumber();
//	}
//	
//	private void setNextTurnToken() {
//		turnPanel.setRollButtonActive(true);
//		turnPanel.setEndTurnButtonActive(false);
//		if(playersTurn + 1 > numOfPlayers) {
//			playersTurn = 1;
//		} else {
//			playersTurn++;
//		}
//	}
//	
//	private void allowEndTurn() {
//		turnPanel.setRollButtonActive(false);
//		turnPanel.setEndTurnButtonActive(true);
//	}
//	
////	private void promptResponseAction(Token token, ResponseAction responseAction) {
////		if(responseAction instanceof UnownedDeedAction) {
////			//AlertPopup alert = new AlertPopup("// TODO - this is where we would display a panel with the options, and apply to token or call server again if needed");
////		}
////		allowEndTurn();
////	}
//	
//	private void setTurnPanelLabelByPlayerTurnNumber() {
//		turnPanel.setTurnLabelText(getCurrentPlayerTurnName() + "'s turn!");
//		//deedDisplayPanel.displayDeeds(board.getOwnedDeeds(getTurnToken()));
//	}
//	
//	private String getCurrentPlayerTurnName() {
//		return piecesByNumber.get(playersTurn).getName();
//	}
//	
//	private void gameOver() {
//		countdown.cancel();
//		int winner = -1;
//		int highestMoney = 0;
//		for (Entry<Integer, PlayerPiece> entry : piecesByNumber.entrySet()) {
//			Integer playerNumber = entry.getKey();
//			PlayerPiece player = entry.getValue();
//			if (player != null) {
//				if (player.getMoney() > highestMoney) {
//					highestMoney = player.getMoney();
//					winner = playerNumber;
//				}
//			}
//		}
//		AlertPopup notify = new AlertPopup(piecesByNumber.get(winner).getName() + " wins the game!");
//	}
//	
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
//				viewBoard.drawBoard(playerPositions);
//				PlayerPiece p1 = piecesByNumber.get(1);
//				PlayerPiece p2 = piecesByNumber.get(2);
//				PlayerPiece p3 = piecesByNumber.get(3);
//				PlayerPiece p4 = piecesByNumber.get(4);
//				viewBoard.drawStatsPanel(p1, p2, p3, p4);
//			}
//		});
//	}
//	
//}
