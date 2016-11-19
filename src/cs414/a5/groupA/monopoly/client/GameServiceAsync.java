package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
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

	void sellProperty(String gameId, String playerName, String deedName, AsyncCallback<Void> callback);

	void checkRolledDoubles(String gameId, String playerName, AsyncCallback<Boolean> callback);

//	void getPlayerPositions(AsyncCallback<Map<String, Integer>> callback);
//	
//	void initializeGame(ArrayList<String> names, AsyncCallback<Void> callback);
//	
	void roll(String name, String gameId, int debug, AsyncCallback<String> callback);

	void checkInJail(String gameId, String playerName, AsyncCallback<Boolean> callback);
	
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

	void initializeFirstTurn(String gameId, AsyncCallback<Void> asyncCallback);

	void getCurrentTurnToken(String gameId, AsyncCallback<Token> asyncCallback);

	void nextPlayersTurn(String gameId, AsyncCallback<Void> asyncCallback);

	void getDeedsOwnedByPlayer(String gameId, String playerName, AsyncCallback<HashMap<String, String>> asyncCallback);
	
	void checkForCardSpot(String gameId, String name, AsyncCallback<Boolean> callback);
	
	void dealWithCard(String gameId, String name, AsyncCallback<String> callback);
	
	void chargeTax(String gameId, String name, AsyncCallback<String> callback);
	
	void checkForTaxSpot(String gameId, String name, AsyncCallback<Boolean> callback);
	
	void payRentToToken(String gameId, String name, AsyncCallback<Integer> callback);
	
	void checkForDeedSpot(String gameId, String name, AsyncCallback<DeedSpotOptions> callback);
	
	void handleDeedSpotOption(String gameId, String name, String selectedOption, AsyncCallback<String> callback);
	
	void getAllOwnedDeedsForGameId(String gameId, AsyncCallback<ArrayList<DatabaseDeed>> callback);

	void buyHouse(String playerName, String deedName, String gameId, AsyncCallback<Boolean> asyncCallback);

	void getRentOwedOnCurrentSpace(String gameId, String playerName, AsyncCallback<Integer> asyncCallback);

	void getOutOfJail(String gameId, String playerName, AsyncCallback<Void> asyncCallback);

	void sellHouse(String playerName, String deedName, String gameId, int numHouseToSell, AsyncCallback<Void> callback);
}
