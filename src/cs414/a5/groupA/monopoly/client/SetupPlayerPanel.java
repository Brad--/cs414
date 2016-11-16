package cs414.a5.groupA.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class SetupPlayerPanel extends BasePanel {

	private String gameId = null;
	private boolean allPlayersReady = false;
	
	public SetupPlayerPanel() {
		setGameId(Window.Location.getParameter("gameId"));
		if(getGameId() == null) {
			AlertPopup alert = new AlertPopup("Please add on '?gameId=<IdHere>' to your URL'");
		} else {
			init();
		}
	}
	
	public void init() {
		Label nameLabel = new Label("Enter your name:");
		final TextBox nameField = new TextBox();
		
		Button readyButton = new Button("Ready Up");
		readyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(nameField.getValue() != null && nameField.getValue().length() > 0) {
					saveTokenToDatabaseAndReadyUp(nameField.getValue());
					AlertPopup alert = new AlertPopup("Waiting On Other Players To Ready-Up");
				}
			}
		});
		
		add(nameLabel);
		add(nameField);
		add(readyButton);
	}
	
	public void saveTokenToDatabaseAndReadyUp(String playerName) {
		getGameService().saveNewTokenToDatabase(getGameId(), playerName, null, 1500, 0, true, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				
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
		timer.scheduleRepeating(10000);
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
		AlertPopup alert = new AlertPopup("GAME WILL START NOW");
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
}
