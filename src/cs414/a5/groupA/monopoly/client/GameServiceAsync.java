package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cs414.a5.groupA.monopoly.server.Token;
import cs414.a5.groupA.monopoly.server.TokenActionWrapper;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GameServiceAsync {
	void testAsyncCall(AsyncCallback<Boolean> callback);
	
	void getPlayerPositions(AsyncCallback<Map<String, Integer>> callback);
	
	void initializeGame(ArrayList<String> names, AsyncCallback<Void> callback);
	
	void roll(String name, AsyncCallback<String> callback);
	
	void getPlayerPropertyList(String name, AsyncCallback<HashMap<String, String>> callback);
	
	void getSpeedingAmount(String name, AsyncCallback<Integer> callback);
	
	void getAllSpacesAndOwners(AsyncCallback<HashMap<Integer, String>> callback);
	
	void getPlayerMoneyAmounts(AsyncCallback<HashMap<String, Integer>> callback);
}
