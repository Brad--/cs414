package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.Trade;

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

	void AddBidOnDeed(String gameId, int position, String playerName, int playerBid, AsyncCallback<Void> callback);

//	void getPlayerPositions(AsyncCallback<Map<String, Integer>> callback);
//	
//	void initializeGame(ArrayList<String> names, AsyncCallback<Void> callback);
//	
	void roll(String name, String gameId, int debug, AsyncCallback<String> callback);

	void checkInJail(String gameId, String playerName, AsyncCallback<Boolean> callback);

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

	void sellHouse(String playerName, String deedName, String gameId, AsyncCallback<Integer> callback);

	void checkForMonopoly(String playerName, String deedName, String gameId, AsyncCallback<Boolean> asyncCallback);

	void checkIfAbleToBuildHouse(String playerName, String deed, String gameId, AsyncCallback<Boolean> asyncCallback);
	
	void checkIfPlayerNeedsToBid(String gameId, String playerName, AsyncCallback<Integer> asyncCallback);
	
	void checkAndWaitForBiddingToEndAndRespond(String gameId, int position, AsyncCallback<String> callback);
	
	void updateBidOnDeed(String gameId, int position, String playerName, int playerBid, AsyncCallback<Void> callback);

	void updateDeed(DatabaseDeed deed, AsyncCallback<Void> callback);
	
	void saveNewTradeToDatabase(Trade trade, AsyncCallback<Void> callback);
	
	void updateTrade(Trade trade, AsyncCallback<Void> callback);
	
	void getTrade(int tradeId, AsyncCallback<Trade> callback);
	
	void mortgageProperty(String gameId, String playerName, String deedName, AsyncCallback<Boolean> callback);
}
