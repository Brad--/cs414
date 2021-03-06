package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cs414.a5.groupA.monopoly.shared.BidResult;
import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.Trade;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("game")
public interface GameService extends RemoteService {

	Boolean unmortgage(String gameId, String playerName, String deedName);

	Boolean mortgageProperty(String gameId, String playerName, String deedName);

	ArrayList<Token> getAllGameTokens(String gameId);

	Boolean checkPlayersReady(String gameId);

	Token saveNewTokenToDatabase(Token token);

	void updateToken(Token token);

	void deleteToken(String gameId, Token token);

	Boolean checkForCardSpot(String gameId, String name) throws Exception;

	String dealWithCard(String gameId, String name) throws Exception;

	String chargeTax(String gameId, String name) throws Exception;

	Boolean checkForTaxSpot(String gameId, String namer) throws Exception;

	Integer payRentToToken(String gameId, String name) throws Exception;

	DeedSpotOptions checkForDeedSpot(String gameId, String name) throws Exception;

	void sellProperty(String gameId, String playerName, String deedName) throws Exception;

	Boolean checkRolledDoubles(String gameId, String playerName) throws Exception;
	
	Boolean checkInJail(String gameId, String playerName) throws Exception;

	Integer sellHouse(String playerName, String deedName, String gameId) throws Exception;

	void AddBidOnDeed(String gameId, int position, String playerName, int playerBid) throws Exception;

//	Map<String, Integer> getPlayerPositions();
//	
//	void initializeGame(ArrayList<String> names);
//
	String roll(String name, String gameID, int debug);

	void initializeFirstTurn(String gameId);

	Token getCurrentTurnToken(String gameId);

	void nextPlayersTurn(String gameId);

	HashMap<String, String> getDeedsOwnedByPlayer(String gameId, String playerName);

	String handleDeedSpotOption(String gameId, String name, String selectedOption);

	ArrayList<DatabaseDeed> getAllOwnedDeedsForGameId(String gameId);

	Boolean buyHouse(String playerName, String deedName, String gameId);

	Integer getRentOwedOnCurrentSpace(String gameId, String playerName) throws Exception;

	void getOutOfJail(String gameId, String playerName);

	Boolean checkIfAbleToBuildHouse(String playerName, String deedName, String gameId) throws Exception;

	Boolean checkForMonopoly(String playerName, String deedName, String gameId);

	Integer checkIfPlayerNeedsToBid(String gameId, String playerName);

	BidResult checkAndWaitForBiddingToEndAndRespond(String gameId, int position) throws Exception;

	void updateBidOnDeed(String gameId, int position, String playerName, int playerBid) throws Exception;

	void updateDeed(DatabaseDeed deed);

	void saveNewTradeToDatabase(Trade trade);

	void updateTrade(Trade trade);

	Trade getTrade(int tradeId);
	
	void buyPropertyFromBid(String gameId, BidResult bidResult) throws Exception;

	Token getTokenByGameIdAndName(String gameId, String playerName) throws Exception;
}
