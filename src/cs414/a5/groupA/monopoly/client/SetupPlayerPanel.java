package cs414.a5.groupA.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import cs414.a5.groupA.monopoly.shared.Token;

public class SetupPlayerPanel extends BasePanel {

	private String gameId = null;
	private boolean allPlayersReady = false;
	private Token token;
	
	AlertPopup readyUpAlert = new AlertPopup("Waiting On Other Players To Ready-Up", false, false);
	
	public SetupPlayerPanel() {
		setGameId(Window.Location.getParameter("gameId"));
		Window.addWindowClosingHandler(new ClosingHandler() {
			@Override
			public void onWindowClosing(ClosingEvent event) {
				getGameService().deleteToken(getGameId(), getToken(), new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(Void result) {
						
					}});
				
			}
		});
		if(getGameId() == null) {
			AlertPopup alert = new AlertPopup("Please add on '?gameId=<IdHere>' to your URL'");
		} else {
			insertInitialToken();
		}
	}
	
	public void insertInitialToken() {
		setToken(new Token());
		getToken().setGameId(getGameId());
		getToken().setPlayerName("" + System.currentTimeMillis());
		getToken().setMoney(1500);
		getToken().setPosition(0);
		getToken().setReady(false);
		getToken().setInJail(false);
		getToken().setSpeedCount(0);
		getToken().setPlayerTurn(false);
		
		getGameService().saveNewTokenToDatabase(getToken(), new AsyncCallback<Token>() {

			@Override
			public void onFailure(Throwable caught) {
				AlertPopup alert = new AlertPopup(caught.getMessage());
			}

			@Override
			public void onSuccess(Token result) {
				setToken(result);
				init();
			}});
	}
	
	public void init() {
		Label nameLabel = new Label("Enter your name:");
		final TextBox nameField = new TextBox();
		
		Button readyButton = new Button("Ready Up");
		readyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(nameField.getValue() != null && nameField.getValue().length() > 0) {
					readyUpAlert.showPopup();
					saveTokenToDatabaseAndReadyUp(nameField.getValue());
				} else {
					AlertPopup alert = new AlertPopup("Please enter a valid name.");
				}
			}
		});
		
		add(nameLabel);
		add(nameField);
		add(readyButton);
	}
	
	public void saveTokenToDatabaseAndReadyUp(String playerName) {
		getToken().setPlayerName(playerName);
		getToken().setReady(true);

		getGameService().updateToken(getToken(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				AlertPopup alert = new AlertPopup(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				readyPhase();
			}});
	}
	
	public void readyPhase() {
		Timer timer = new Timer() { 
			@Override
		    public void run() { 
		    	if(!isAllPlayersReady()) {
		    		checkReadyAndProgress();
		    	} else {
		    		cancel();
		    	}
		    } 
		}; 
		timer.scheduleRepeating(100);
	}
	
	public void checkReadyAndProgress() {
		getGameService().checkPlayersReady(getGameId(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				AlertPopup alert = new AlertPopup(caught.getMessage());
			}

			@Override
			public void onSuccess(Boolean result) {
				if(result != null && result) {
					setAllPlayersReady(true);
					startGame();
				}
			}
		});
	}
	
	public void startGame() {
		readyUpAlert.hide();
		clear();
		GamePanel gamePanel = new GamePanel(getToken().getPlayerName(), getGameId());
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public boolean isAllPlayersReady() {
		return allPlayersReady;
	}

	public void setAllPlayersReady(boolean allPlayersReady) {
		this.allPlayersReady = allPlayersReady;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}
