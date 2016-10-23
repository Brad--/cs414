package com.cs414.monopoly.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class GamePanel extends BasePanel {
	
	Board board = new Board();
	
	public GamePanel(Token p1, Token p2, Token p3, Token p4) {
		init(p1, p2, p3, p4);
	}

	public void init(Token p1, Token p2, Token p3, Token p4) {
		board.drawBoard(p1, p2, p3, p4);
		getMainVerticalPanel().add(board);
	}
	
}
