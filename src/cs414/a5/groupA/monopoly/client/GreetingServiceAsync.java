package cs414.a5.groupA.monopoly.client;

import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.TokenActionWrapper;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void testAsyncCall(AsyncCallback<Boolean> callback);
	
	void getTokenPositions(AsyncCallback<Map<String, Integer>> callback);
	
	void initializeTokens(Set<String> names, AsyncCallback<Void> callback);
	
	void roll(Token token, AsyncCallback<TokenActionWrapper> callback);
}
