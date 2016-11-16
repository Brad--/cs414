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
	
	void checkPlayersReady(String gameId, AsyncCallback<Boolean> callback);
	
	void saveNewTokenToDatabase(String gameId, String playerName, String gamePiece, int money, int position,
			boolean ready, AsyncCallback<Void> callback);
	
//	void getPlayerPositions(AsyncCallback<Map<String, Integer>> callback);
//	
//	void initializeGame(ArrayList<String> names, AsyncCallback<Void> callback);
//	
//	void roll(String name, AsyncCallback<String> callback);
//	
//	void getPlayerPropertyList(String name, AsyncCallback<HashMap<String, String>> callback);
//	
//	void getSpeedingAmount(String name, AsyncCallback<Integer> callback);
//	
//	void getAllSpacesAndOwners(AsyncCallback<HashMap<Integer, String>> callback);
//	
//	void getPlayerMoneyAmounts(AsyncCallback<HashMap<String, Integer>> callback);
//	
//	void getPlayerOptionsFromSpace(String playerName, int spaceNumber, AsyncCallback<ArrayList<String>> callback);
//	
//	void requestTrade(String playerRequesting, String playerRequested, AsyncCallback<Void> callback);
//	
//	void respondToTradeRequest(boolean wantsToTrade, AsyncCallback<Boolean> callback);
//	
//	void addItemToTrade(Object item, String playerAdding, AsyncCallback<Void> callback);
//	
//	void removeItemFromTrade(Object item, String playerRemoving, AsyncCallback<Void> callback);
//	
//	void acceptTrade(String playerAccepting, AsyncCallback<Void> callback);
//	
//	void denyTrade(String playerDenying, AsyncCallback<Void> callback);
//	
//	void tradeCloseWithDescription(AsyncCallback<String> callback);
//	
//	void getOpposingTraderItems(String playerRequesting, AsyncCallback<ArrayList<Object>> callback);
}
