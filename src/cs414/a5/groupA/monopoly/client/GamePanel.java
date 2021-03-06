package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.BidResult;
import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.Trade;

public class GamePanel extends BasePanel {
	ViewBoard viewBoard = new ViewBoard();
	Label countdownLabel = new Label();
	DeedsDisplayPanel deedsDisplayPanel;
	TurnPanel turnPanel;
	LinkedHashMap<Integer, PlayerPiece> piecesByNumber = new LinkedHashMap<Integer, PlayerPiece>();
	
	Trade tradeInstance;
	boolean inJail = false;
	int turnsInJail = 0;
	Timer countdown;
	Timer refreshBoard;
	Timer checkNeedToBid;
	
	String currentTurnPlayerName = "";
	int numOfPlayers;
	
	private String playerName;
	private String gameId;
	private boolean isMyTurn;
	
	private boolean isCurrentlyBidding = false;
	
	public GamePanel() {
		
	}
	
	public GamePanel(String playerName, String gameId) {
		setPlayerName(playerName);
		setGameId(gameId);
		initializeDeedsDisplayPanel();
		String urlTimeString = Window.Location.getParameter("time");
		int urlTimeInt = 30;
		try {
			urlTimeInt = Integer.parseInt(urlTimeString);
		} catch (Exception e) {
			
		}
		final int gameTime = urlTimeInt;
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
		
		checkNeedToBid = new Timer() {
			@Override
			public void run() {
				checkAndHandleBid();
			}
		};
		
		init();
	}
	
	private void initializeDeedsDisplayPanel() {
		deedsDisplayPanel = new DeedsDisplayPanel() {
			@Override
			public void attemptToBuyHousing(String deedName) {
				final String deed = deedName;
				getGameService().checkForMonopoly(getPlayerName(), deed, gameId, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable arg0) {}
					@Override
					public void onSuccess(Boolean hasMonopoly) {
						if (hasMonopoly) {
							getGameService().checkIfAbleToBuildHouse(getPlayerName(), deed, gameId, new AsyncCallback<Boolean>() {
								@Override
								public void onFailure(Throwable arg0) {}
								@Override
								public void onSuccess(Boolean ableToBuildOnSpot) {
									if (ableToBuildOnSpot) {
										getGameService().buyHouse(getPlayerName(), deed, gameId, new AsyncCallback<Boolean>() {
											@Override
											public void onFailure(Throwable arg0) {}
											@Override
											public void onSuccess(Boolean boughtProperty) {
												if (boughtProperty != null) {
													if (boughtProperty) {
														AlertPopup alert = new AlertPopup("You bought the housing!");
													}
													else {
														AlertPopup alert = new AlertPopup("Failed to buy housing. Insufficient funds.");
													}
												}
												else {
													AlertPopup alert = new AlertPopup("Failed to buy housing. Can't build on mortgaged property.");
												}
											}
										});
									}
									else {
										AlertPopup alert = new AlertPopup("Failed to buy housing. Need housing on other spaces first.");
									}
								}
							});
						}
						else {
							AlertPopup alert = new AlertPopup("Failed to buy housing. You need a monopoly to buy housing, or cannot build housing on this space.");
						}
					}
				});
			}
			
			@Override
			public void attemptToSellHousing(String deedName) {
				getGameService().sellHouse(getPlayerName(), deedName, getGameId(), new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable arg0) {}
					@Override
					public void onSuccess(Integer amountSoldFor) {
						if (amountSoldFor!=null) {
							AlertPopup alert = new AlertPopup("Sold housing for $"+amountSoldFor);
						}
					}
				});
			}
			
			@Override
			public void attemptToMortgage(String deedName) {
				getGameService().mortgageProperty(getGameId(), getPlayerName(), deedName, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Boolean mortgageSuccessful) {
						if (mortgageSuccessful) {
							AlertPopup alert = new AlertPopup("Mortgaged the property to the bank");
						}
						else {
							AlertPopup alert = new AlertPopup("Unable to mortgage. Make sure all properties owned of that color do not have houses on them.");
						}
					}
				});
			}
			
			@Override
			public void attemptToDeMortgage(String deedName) {
				getGameService().unmortgage(getGameId(), getPlayerName(), deedName, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Boolean mortgageSuccessful) {
						if (mortgageSuccessful) {
							AlertPopup alert = new AlertPopup("Bought the property back from the bank!");
						}
						else {
							AlertPopup alert = new AlertPopup("Couldn't buy property back from the bank, insufficient funds.");
						}
					}
				});
			}
		};
	}

	public void init() {		
		getGameService().initializeFirstTurn(gameId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable arg0) {}

			@Override
			public void onSuccess(Void arg0) {}
			
		});
				
		HorizontalPanel boardTurnDeedsPanel = new HorizontalPanel();
		turnPanel = new TurnPanel(){
			@Override
			public void handleRollClick() {
				doTurn(0);
			}
			@Override
			public void handleEndTurnClick() {
				endTurn();
			}
			@Override
			public void handleRollOneClick() {
				doTurn(1);
			}
			@Override
			public void handleRollDoublesClick() {
				doTurn(2);
			}
			@Override
			public void handleTradeClick() {
				TradePlayerPickPanel tradePlayerPickPanel = new TradePlayerPickPanel(getGameId(), getPlayerName()){
					@Override
					protected void handleTradeClick() {
						Trade trade = new Trade();
						trade.setGameId(gameId);
						trade.setPlayerOneName(getPlayerName());
						trade.setPlayerOneDeeds(new ArrayList<String>());
						trade.setPlayerOneMoney(0);
						trade.setPlayerOneAccepted(false);
						trade.setFinalized(false);
						trade.setPlayerTwoName(getSelectedPlayer());
						trade.setPlayerOneDeeds(new ArrayList<String>());
						trade.setPlayerOneMoney(0);
						trade.setPlayerOneAccepted(false);
						
						getGameService().saveNewTradeToDatabase(trade, new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable arg0) {}
							@Override
							public void onSuccess(Void arg0) {
								hide();
							}
						});
					}
				};
			}
		};
		initializeTimers();
		VerticalPanel deedsPanel = new VerticalPanel();
		Label ownedDeedsLabel = new Label("Owned Deeds");
		ownedDeedsLabel.addStyleName("turnLabel");
		deedsPanel.add(ownedDeedsLabel);
		deedsPanel.add(deedsDisplayPanel);
		
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
		checkNeedToBid.scheduleRepeating(1000);
	}
	
	private void doTurn(int debug) {
		getGameService().roll(getPlayerName(), getGameId(), debug, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {}
			@Override
			public void onSuccess(String result) {
				turnPanel.setRollLabel("Dice Roll: " + result);
				viewBoard.renderBoard();
				checkLandedSpace();
				getGameService().checkRolledDoubles(getGameId(), getPlayerName(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable arg0) {}
					@Override
					public void onSuccess(Boolean rolledDoubles) {
						if (!rolledDoubles) {
							allowEndTurn();
						}
						else if (rolledDoubles && getInJail()) {
							allowEndTurn();
						}
					}
				});
			}});
		getGameService().checkInJail(getGameId(), getPlayerName(), new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(Boolean inJail) {
				if (inJail) {
					if (getInJail()) {
						turnsInJail++;
					}
					else {
						setInJail(true);
					}
				}
				else {
					setInJail(false);
					turnsInJail=0;
				}
			}
		});
		if (turnsInJail == 2) {
			setInJail(false);
			turnsInJail=0;
			getGameService().getOutOfJail(getGameId(), getPlayerName(), new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable arg0) {}
				@Override
				public void onSuccess(Void arg0) {
					AlertPopup alert = new AlertPopup("You did your time in the slammer, you are set free next turn.");
				}
			});
		}
	}
	
	private void checkLandedSpace() {
		checkTaxSpot();
		checkCardSpot();
		checkDeedSpot();
	}
	
	private void checkTaxSpot() {
		getGameService().checkForTaxSpot(gameId, playerName, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(Boolean isTaxSpot) {
				if (isTaxSpot) {
					getGameService().chargeTax(gameId, playerName, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable arg0) {}
						@Override
						public void onSuccess(String message) {
							AlertPopup alert = new AlertPopup(message);
						}
					});
				}
			}
		});
	}
	
	private void checkCardSpot() {
		getGameService().checkForCardSpot(gameId, playerName, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(Boolean isCardSpot) {
				if (isCardSpot) {
					getGameService().dealWithCard(gameId, playerName, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable arg0) {}
						@Override
						public void onSuccess(String message) {
							AlertPopup alert = new AlertPopup("Drew Card: " + message);
						}
					});
				}
			}
		});
	}
	
	private void checkDeedSpot() {
		getGameService().checkForDeedSpot(gameId, playerName, new AsyncCallback<DeedSpotOptions>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(DeedSpotOptions deedSpotOptions) {
				if(deedSpotOptions != null) {
					final String deedOwner = deedSpotOptions.getOwner();
					if (deedOwner == null) {
						OptionsPanel optionsPanel = new OptionsPanel(deedSpotOptions.getOptions()) {
							@Override
							public void handleButtonClick() {
								getGameService().handleDeedSpotOption(getGameId(), getPlayerName(), getSelectedOption(), new AsyncCallback<String>() {

									@Override
									public void onFailure(Throwable caught) {}

									@Override
									public void onSuccess(String result) {
										if(!result.equals(DeedSpotOptions.DO_NOT_BUY)) {
											AlertPopup alert = new AlertPopup(result);
										}
									}});
							}
						};
					}
					else if (!deedOwner.equals(getPlayerName())) {
						getGameService().payRentToToken(gameId, playerName, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable arg0) {}
							@Override
							public void onSuccess(Integer rent) {
								if (rent != null) {
									if (rent == 0) {
										// do nothing
									}
									else {
										AlertPopup alert = new AlertPopup("Paid $" + rent + " to " + deedOwner + " by landing on their property.");
									}
								}
								else {
									AlertPopup alert = new AlertPopup("You ran out of money and lost the game. Game over");
								}
							}
						});
					}
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
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(Void arg0) {}
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
			public void onFailure(Throwable arg0) {}
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
		getGameService().getAllGameTokens(getGameId(), new AsyncCallback<ArrayList<Token>>(){

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<Token> result) {
				int highestMoney = -1;
				String winningPlayer = null;
				boolean tie = false;
				for(Token token : result) {
					if(token.getMoney() > highestMoney) {
						highestMoney = token.getMoney();
						winningPlayer = token.getPlayerName();
						tie = false;
					} else if (token.getMoney() == highestMoney) {
						tie = true;
					}
				}
				if(tie) {
					AlertPopup alert = new AlertPopup("Times up! A tie occured, so there is no true winner.", true, false);
				} else {
					AlertPopup alert = new AlertPopup("Times up! " + winningPlayer + " has won the game!", true, false);
				}
			}});
	}
	
	private void updateDeedsDisplay() {
		getGameService().getAllOwnedDeedsForGameId(getGameId(), new AsyncCallback<ArrayList<DatabaseDeed>>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(ArrayList<DatabaseDeed> result) {
				deedsDisplayPanel.displayDeeds(result, getPlayerName());
			}
		});
	}
	
	private void checkAndHandleBid() {
		if(!isCurrentlyBidding()) {
			getGameService().checkIfPlayerNeedsToBid(getGameId(), getPlayerName(), new AsyncCallback<Integer>() {
	
				@Override
				public void onFailure(Throwable caught) {
					AlertPopup alert = new AlertPopup(caught.getMessage());
				}
	
				@Override
				public void onSuccess(final Integer positionResult) {
					if(positionResult != null) {
						setCurrentlyBidding(true);
						BiddingPanel biddingPanel = new BiddingPanel(positionResult){
							@Override
							public void handleClick() {
								hide();
								final AlertPopup waitingAlert = new AlertPopup("Bid sent, waiting on other players' bids", true, false);
								getGameService().updateBidOnDeed(getGameId(), Integer.valueOf(positionResult), getPlayerName(), getBidAmount(), new AsyncCallback<Void>(){

									@Override
									public void onFailure(Throwable caught) {
										
									}

									@Override
									public void onSuccess(Void voidResult) {
										final Timer timer = new Timer() {
											@Override
											public void run() {
												getGameService().checkAndWaitForBiddingToEndAndRespond(getGameId(), positionResult, new AsyncCallback<BidResult>() {
		
													@Override
													public void onFailure(Throwable caught) {
														// TODO Auto-generated method stub
														
													}
		
													@Override
													public void onSuccess(BidResult bidResult) {
														if(bidResult != null) {
															if(bidResult.getBidWinnerName() != null && getPlayerName().equals(bidResult.getBidWinnerName())) {
															getGameService().buyPropertyFromBid(gameId, bidResult, new AsyncCallback<Void>(){

																@Override
																public void onFailure(Throwable caught) {
																	// TODO Auto-generated method stub
																	
																}

																@Override
																public void onSuccess(Void result) {
																	// TODO Auto-generated method stub
																	
																}});
															}
															waitingAlert.hide();
															AlertPopup resultAlert = new AlertPopup(bidResult.getMessage());
															setCurrentlyBidding(false);
															cancel();
														}
													}});
											}
										};
										timer.scheduleRepeating(1000);
									}});
							}
						};
					}
				}});
		}
	}
	
	public boolean getInJail() {
		return inJail;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
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

	public boolean isCurrentlyBidding() {
		return isCurrentlyBidding;
	}

	public void setCurrentlyBidding(boolean isCurrentlyBidding) {
		this.isCurrentlyBidding = isCurrentlyBidding;
	}
	
}
