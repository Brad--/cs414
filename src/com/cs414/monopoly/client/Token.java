package com.cs414.monopoly.client;

import com.google.gwt.user.client.ui.Image;

public class Token {
	private Image image;
	private int location;
	
	public Token(Image image, int location) {
		setImage(image);
		setLocation(location);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

}
