package com.cs414.monopoly.client;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.FlowPanel;

public class ViewSpace extends FlowPanel {
	
	private int y;
	private int x;
	
	public ViewSpace(int y, int x, String backgroundImage, String styleName) {
		setY(y);
		setX(x);
		addStyleName("viewSpace");
		addStyleName(styleName);
		addStyleName(backgroundImage);
	}
	
	public void add(Token token) {
		add(token.getGamePiece());
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

}
