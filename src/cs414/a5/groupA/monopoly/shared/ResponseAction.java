package cs414.a5.groupA.monopoly.shared;

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
