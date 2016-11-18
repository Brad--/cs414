package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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

	void initializeDeeds(String gameID);

	boolean checkForCardSpot(int position);

	void dealWithCard(Token currentPlayer);

	void chargeTax(Token currentPlayer);

	boolean checkForTaxSpot(Token currentPlayer);

	void payRentToToken(Token currentPlayer);

	void wantsToBuyProperty(Token currentPlayer);

	boolean checkForOwnedDeed(String gameId, int position);
	
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
}
