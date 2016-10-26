package com.cs414.monopoly.server;

import java.util.Map;
import java.util.Set;

import com.cs414.monopoly.client.GreetingService;
import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	private static final long serialVersionUID = 1L;

	@Override
	public Map<String, Integer> getTokenPositions() {
		// TODO get the token positions for all tokens. Return them as Integers
		return null;
	}

	@Override
	public Boolean testAsyncCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeTokens(Set<String> names) {
		// TODO save to database
		
	}
	
	@Override
	public Token roll(Token token) {
		boolean value = true;
		Die die = new Die();
		int distance = die.roll(token);
		token.updatePosition(distance);
		return token;
	}

}
