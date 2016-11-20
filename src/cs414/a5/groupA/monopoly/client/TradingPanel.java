package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.Trade;

public class TradingPanel extends PopupPanel {
	
	private final GameServiceAsync gameService = GWT.create(GameService.class);
	
	public TradingPanel(Trade trade, String playerName) {
		final String gameId = trade.getGameId();
		final String displayPlayer = playerName;
		getGameService().getTokenByGameIdAndName(gameId, displayPlayer, new AsyncCallback<Token>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(Token playerToken) {
				VerticalPanel playerItemsPanel = new VerticalPanel();
				VerticalPanel playerOfferPanel = new VerticalPanel();
				VerticalPanel opposingOfferPanel = new VerticalPanel();
				HorizontalPanel offerPanel = new HorizontalPanel();
				HorizontalPanel buttonsPanel = new HorizontalPanel();
				Button acceptButton = new Button("Accept trade");
				acceptButton.getElement().getStyle().setProperty("float", "right");
				Button cancelButton = new Button("Cancel");
				cancelButton.getElement().getStyle().setProperty("float", "right");
				
				getGameService().getDeedsOwnedByPlayer(gameId, displayPlayer, new AsyncCallback<HashMap<String, String>>() {
					@Override
					public void onFailure(Throwable arg0) {
					}
					@Override
					public void onSuccess(HashMap<String, String> deedsList) {
						
					}
				});
			}
		});
	}
	
	protected GameServiceAsync getGameService() {
		return gameService;
	}

}
