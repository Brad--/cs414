package cs414.a5.groupA.monopoly.shared;

import java.io.Serializable;

public class TokenActionWrapper implements Serializable {
	
	private Token token;
	private ResponseAction responseAction;
	
	public TokenActionWrapper() {}
	
	public TokenActionWrapper(Token token, ResponseAction responseAction) {
		this.setToken(token);
		this.setResponseAction(responseAction);
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public ResponseAction getResponseAction() {
		return responseAction;
	}

	public void setResponseAction(ResponseAction responseAction) {
		this.responseAction = responseAction;
	}

}
