package cs414.a5.groupA.monopoly.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

import cs414.a5.groupA.monopoly.shared.Token;

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
		add(new Image(token.getGamePiece()));
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
