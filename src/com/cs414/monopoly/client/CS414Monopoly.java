package com.cs414.monopoly.client;

import com.google.gwt.core.client.EntryPoint;

public class CS414Monopoly implements EntryPoint {

	public void onModuleLoad() {
		GamePanel gamePanel = new GamePanel();
		gamePanel.init();
	}
}
