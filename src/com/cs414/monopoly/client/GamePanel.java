package com.cs414.monopoly.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class GamePanel extends BasePanel {
	
	Board board = new Board();
	
	public GamePanel() {
		loadData();
	}
	
	public void loadData() {
		init();
	}

	public void init() {
		Token P1 = new Token(new Image("https://screenshots.en.sftcdn.net/en/scrn/76000/76818/microsoft-small-basic-22.jpg"), 25);
		board.drawBoard(P1, null, null, null);
		getMainVerticalPanel().add(board);
	}
	
}
