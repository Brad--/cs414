package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.Trade;

public class TradingPanel extends PopupPanel {
	
	private final GameServiceAsync gameService = GWT.create(GameService.class);
	
	public TradingPanel(final Trade trade, String playerName) {
		final String gameId = trade.getGameId();
		final String displayPlayer = playerName;
		final boolean isPlayerOne = displayPlayer.equals(trade.getPlayerOneName());
		getGameService().getTokenByGameIdAndName(gameId, displayPlayer, new AsyncCallback<Token>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(final Token playerToken) {
				
				final VerticalPanel playerItemsPanel = new VerticalPanel();
				final VerticalPanel playerOfferPanel = new VerticalPanel();
				final VerticalPanel opposingOfferPanel = new VerticalPanel();
				final HorizontalPanel offerPanel = new HorizontalPanel();
				final HorizontalPanel buttonsPanel = new HorizontalPanel();
				Button acceptButton = new Button("Accept trade");
				acceptButton.getElement().getStyle().setProperty("float", "right");
				Button cancelButton = new Button("Cancel");
				cancelButton.getElement().getStyle().setProperty("float", "right");
				
				final int currentMoney = playerToken.getMoney();
				FlexTable moneyAdder = new FlexTable();
				final TextBox moneyBox = new TextBox();
				moneyBox.setName("/"+currentMoney);
				Button moneyButton = new Button("Add money");
				moneyButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						int amountAdding = Integer.parseInt(moneyBox.getText());
						if (amountAdding>currentMoney) {
							AlertPopup alert = new AlertPopup("You don't have enough money to do that.");
						}
						else {
							playerToken.setMoney(currentMoney-amountAdding);
							if (isPlayerOne) {
								trade.setPlayerOneMoney(trade.getPlayerOneMoney()+amountAdding);
							}
							else {
								trade.setPlayerTwoMoney(trade.getPlayerOneMoney()+amountAdding);
							}
							updateTrade(trade);
						}
					}
				});
				moneyAdder.setWidget(0, 0, moneyBox);
				moneyAdder.setWidget(0, 1, new Button("Add money"));
				
				getGameService().getDeedsOwnedByPlayer(gameId, displayPlayer, new AsyncCallback<HashMap<String, String>>() {
					@Override
					public void onFailure(Throwable arg0) {
					}
					@Override
					public void onSuccess(HashMap<String, String> deedsList) {
						for (Entry<String, String> entry : deedsList.entrySet()) {
							String deedName = entry.getKey();
							playerItemsPanel.add(new Label(deedName));
						}
					}
				});
			}
		});
	}
	
	protected GameServiceAsync getGameService() {
		return gameService;
	}
	
	private void updateTrade(Trade trade) {
		getGameService().updateTrade(trade, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable arg0) {}
			@Override
			public void onSuccess(Void arg0) {}
		});
	}

}
