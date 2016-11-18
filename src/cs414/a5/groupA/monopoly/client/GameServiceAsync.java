package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cs414.a5.groupA.monopoly.shared.Token;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GameServiceAsync {
	
	void getAllGameTokens(String gameId, AsyncCallback<ArrayList<Token>> callback);
	
	void checkPlayersReady(String gameId, AsyncCallback<Boolean> callback);
	
	void saveNewTokenToDatabase(Token token, AsyncCallback<Token> callback);
	
	void updateToken(Token token, AsyncCallback<Void> callback);
	
	void deleteToken(Token token, AsyncCallback<Void> callback);
	
//	void getPlayerPositions(AsyncCallback<Map<String, Integer>> callback);
//	
//	void initializeGame(ArrayList<String> names, AsyncCallback<Void> callback);
//	
	void roll(String name, String gameId, AsyncCallback<String> callback);
	
	void initializeDeeds(String gameID, AsyncCallback<Void> callback);
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
