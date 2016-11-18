package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
import cs414.a5.groupA.monopoly.shared.Token;

public class GamePanel extends BasePanel {
	ViewBoard viewBoard = new ViewBoard();
	Label countdownLabel = new Label();
	DeedsDisplayPanel deedDisplayPanel = new DeedsDisplayPanel();
	TurnPanel turnPanel;
	LinkedHashMap<Integer, PlayerPiece> piecesByNumber = new LinkedHashMap<Integer, PlayerPiece>();
	
	Timer countdown;
	Timer refreshBoard;
	
	String currentTurnPlayerName = "";
	int numOfPlayers;
	
	private String playerName;
	private String gameId;
	private boolean isMyTurn;
	
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
				checkTurnStatus();
				updateDeedsDisplay();
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
		
		getGameService().initializeFirstTurn(gameId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		GWT.log("Game successfully started!");
				
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
		initializeTimers();
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
		refreshBoard.scheduleRepeating(1000);
		countdown.scheduleRepeating(1000);
	}
	
	private void doTurn() {
		getGameService().roll(getPlayerName(), getGameId(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// cry
			}

			@Override
			public void onSuccess(String result) {
				AlertPopup alert = new AlertPopup(result);
				viewBoard.renderBoard();
				// if not speeding, allowEndTurn()
				allowEndTurn();
			}});
		checkLandedSpace();
	}
	
	private void checkLandedSpace() {
		checkTaxSpot();
		checkCardSpot();
		checkDeedSpot();
	}
	
	private void checkTaxSpot() {
		getGameService().checkForTaxSpot(gameId, playerName, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Boolean isTaxSpot) {
				if (isTaxSpot) {
					getGameService().chargeTax(gameId, playerName, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable arg0) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onSuccess(Void arg0) {
							// TODO Auto-generated method stub
						}
					});
				}
			}
		});
	}
	
	private void checkCardSpot() {
		getGameService().checkForCardSpot(gameId, playerName, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(Boolean isCardSpot) {
				if (isCardSpot) {
					getGameService().dealWithCard(gameId, playerName, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable arg0) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onSuccess(Void arg0) {
							// TODO Auto-generated method stub
						}
					});
				}
			}
		});
	}
	
	private void checkDeedSpot() {
		getGameService().checkForDeedSpot(gameId, playerName, new AsyncCallback<DeedSpotOptions>() {
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(DeedSpotOptions deedSpotOptions) {
				if(deedSpotOptions != null) {
					OptionsPanel optionsPanel = new OptionsPanel(deedSpotOptions.getOptions()) {
						@Override
						public void handleButtonClick() {
							getGameService().handleDeedSpotOption(getGameId(), getPlayerName(), getSelectedOption(), new AsyncCallback<String>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(String result) {
									AlertPopup alert = new AlertPopup(result);
								}});
						}
					};
				} else {
					// Not a deed
				}
			}
		});
	}
	
	private void endTurn() {
		turnPanel.setRollButtonActive(false);
		turnPanel.setEndTurnButtonActive(false);
		getGameService().nextPlayersTurn(gameId, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable arg0) {
				// uh oh
			}
			@Override
			public void onSuccess(Void arg0) {
				// yee haw
			}
		});
	}
	
	private void startTurn() {
		turnPanel.setRollButtonActive(true);
		turnPanel.setEndTurnButtonActive(false);
		setTurnPanelLabel();
	}
	
	private void disableButtons() {
		turnPanel.setRollButtonActive(false);
		turnPanel.setEndTurnButtonActive(false);
	}
	
	private void allowEndTurn() {
		turnPanel.setRollButtonActive(false);
		turnPanel.setEndTurnButtonActive(true);
	}
	
	private void checkTurnStatus() {
		getGameService().getCurrentTurnToken(gameId, new AsyncCallback<Token>() {

			@Override
			public void onFailure(Throwable arg0) {
				// weep
			}

			@Override
			public void onSuccess(Token currentTurn) {
				if (currentTurn != null) {
					if (currentTurn.getPlayerName().equals(playerName)) {
						currentTurnPlayerName = currentTurn.getPlayerName();
						if (!isMyTurn) { // if the database updated it to be your turn, and it wasn't known client side, update to be ready to move
							isMyTurn = true;
							startTurn();
						} // if client already knew it was their turn, do nothing
					}
					else { // if it is someone elses turn...
						isMyTurn = false;
						disableButtons();
						currentTurnPlayerName = currentTurn.getPlayerName();
						// update the label and variable to reflect whose turn it is
					}
					setTurnPanelLabel();
				}
			}
		});
	}
	
	
	private void setTurnPanelLabel() {
		turnPanel.setTurnLabelText(currentTurnPlayerName + "'s turn!");
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
	
	private void updateDeedsDisplay() {
		getGameService().getDeedsOwnedByPlayer(gameId, playerName, new AsyncCallback<HashMap<String, String>>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(HashMap<String, String> deedsAndColors) {
				deedDisplayPanel.displayDeeds(deedsAndColors);
			}
			
		});
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
	
}
