package com.cs414.monopoly.client;

import com.google.gwt.user.client.ui.Image;

public class Token {
	
	private String name;
	private Image image;
	private int location = 0;
	
	public Token(String name, Image image) {
		setName(name);
		setImage(image);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
