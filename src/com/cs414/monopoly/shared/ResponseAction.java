package com.cs414.monopoly.shared;

import java.io.Serializable;

public class ResponseAction implements Serializable {
	
	private String response;
	
	public ResponseAction() {}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
