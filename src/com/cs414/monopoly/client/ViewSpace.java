package com.cs414.monopoly.client;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class ViewSpace extends FlowPanel {
	
	private int y;
	private int x;
	
	public ViewSpace(int y, int x, String backgroundImageUrl, String styleName) {
		setY(y);
		setX(x);
		addStyleName("viewSpace");
		addStyleName(styleName);
		add(new Label(backgroundImageUrl));
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
