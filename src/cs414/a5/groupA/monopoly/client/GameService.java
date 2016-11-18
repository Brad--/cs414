package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
import cs414.a5.groupA.monopoly.shared.Token;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("game")
public interface GameService extends RemoteService {
	
	ArrayList<Token> getAllGameTokens(String gameId);

	Boolean checkPlayersReady(String gameId);

	Token saveNewTokenToDatabase(Token token);

	void updateToken(Token token);

	void deleteToken(Token token);

	Boolean checkForCardSpot(String gameId, String name) throws Exception;

	String dealWithCard(String gameId, String name) throws Exception;

	String chargeTax(String gameId, String name) throws Exception;

	Boolean checkForTaxSpot(String gameId, String namer) throws Exception;

	void payRentToToken(String gameId, String name) throws Exception;

	DeedSpotOptions checkForDeedSpot(String gameId, String name) throws Exception;

	void sellProperty(String gameId, String playerName, String deedName) throws Exception;

	Boolean checkRolledDoubles(String gameId, String playerName) throws Exception;

//	Map<String, Integer> getPlayerPositions();
//	
//	void initializeGame(ArrayList<String> names);
//
	String roll(String name, String gameID);
//
//	HashMap<String, String> getPlayerPropertyList(String name);
//
//	Integer getSpeedingAmount(String name);
//	
//	HashMap<Integer, String> getAllSpacesAndOwners();
//	
//	HashMap<String, Integer> getPlayerMoneyAmounts();
//	
//	ArrayList<String> getPlayerOptionsFromSpace(String playerName, int spaceNumber);
//
//	void requestTrade(String playerRequesting, String playerRequested);
//
//	Boolean respondToTradeRequest(boolean wantsToTrade);
//
//	void addItemToTrade(Object item, String playerAdding);
//
//	void removeItemFromTrade(Object item, String playerRemoving);
//
//	void acceptTrade(String playerAccepting);
//
//	void denyTrade(String playerDenying);
//
//	String tradeCloseWithDescription();
//
//	ArrayList<Object> getOpposingTraderItems(String playerRequesting);

	void initializeFirstTurn(String gameId);

	Token getCurrentTurnToken(String gameId);

	void nextPlayersTurn(String gameId);

	HashMap<String, String> getDeedsOwnedByPlayer(String gameId, String playerName);

	String handleDeedSpotOption(String gameId, String name, String selectedOption);
}
