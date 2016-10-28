package com.cs414.monopoly.server;

import java.util.Map;
import java.util.Set;

import com.cs414.monopoly.client.GreetingService;
import com.cs414.monopoly.shared.Token;
import com.cs414.monopoly.shared.TokenActionWrapper;
import com.cs414.monopoly.shared.UnownedDeedAction;
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
	public TokenActionWrapper roll(Token token) {
		Die die = new Die();
		Token returnToken = die.roll(token);
		return new TokenActionWrapper(token, new UnownedDeedAction());
	}

}
