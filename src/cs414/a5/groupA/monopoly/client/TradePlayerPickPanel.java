package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.Token;

public class TradePlayerPickPanel extends PopupPanel {
	
	private final GameServiceAsync gameService = GWT.create(GameService.class);
	private String selectedPlayer;

	public TradePlayerPickPanel(String gameId, final String currentPlayer) {
		getGameService().getAllGameTokens(gameId, new AsyncCallback<ArrayList<Token>>(){

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<Token> result) {
				init(result, currentPlayer);
			}});
	}
	
	private void init(ArrayList<Token> tokens, String currentPlayer) {
		VerticalPanel verticalPanel = new VerticalPanel();
		Label promptLabel = new Label("What player would you like to trade with?");
		verticalPanel.add(promptLabel);
		for(Token token : tokens) {
			final String tokenName = token.getPlayerName();
			if(!tokenName.equals(currentPlayer)) {
				Button button = new Button(tokenName);
				button.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						setSelectedPlayer(tokenName);
						handleTradeClick();
					}
				});
				verticalPanel.add(button);
			}
		}
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		closeButton.getElement().getStyle().setProperty("float", "right");
		verticalPanel.add(closeButton);
		setGlassEnabled(true);
		center();
		setWidget(verticalPanel);
	}
	
	protected void handleTradeClick() {
		
	}
	
	protected GameServiceAsync getGameService() {
		return gameService;
	}

	public String getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(String selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}
}
