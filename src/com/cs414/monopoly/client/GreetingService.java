package com.cs414.monopoly.client;

import java.util.Map;
import java.util.Set;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	Boolean testAsyncCall();

	Map<String, Integer> getTokenPositions();
	
	void initializeTokens(Set<String> names);

	Token roll(Token token);
}
