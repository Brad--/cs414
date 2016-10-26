package com.cs414.monopoly.client;

import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void testAsyncCall(AsyncCallback<Boolean> callback);
	
	void getTokenPositions(AsyncCallback<Map<String, Integer>> callback);
	
	void initializeTokens(Set<String> names, AsyncCallback<Void> callback);
}
