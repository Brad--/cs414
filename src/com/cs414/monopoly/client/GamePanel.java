package com.cs414.monopoly.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class GamePanel extends BasePanel {
	
	
	
	public GamePanel(HashMap<String, Image> playerTokens) {
		init(playerTokens);
	}

	public void init(HashMap<String, Image> playerTokens) {
		getGreetingService().initializeTokens(playerTokens.keySet(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable arg0) {
				//cry
			}

			@Override
			public void onSuccess(Void arg0) {
				// yay
				
			}
			
		});
		ViewBoard viewBoard = new ViewBoard(playerTokens);

		getGreetingService().getTokenPositions(new AsyncCallback<Map<String, Integer>>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Map<String, Integer> arg0) {
				viewBoard.drawBoard((HashMap<String, Integer>)arg0); //yay!
				
			}
			
		});
		
		getMainVerticalPanel().add(viewBoard);
	}
	
}
