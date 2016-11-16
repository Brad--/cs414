package cs414.a5.groupA.monopoly.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cs414.a5.groupA.monopoly.client.GameService;

public class GameServiceImpl extends RemoteServiceServlet implements GameService {

	private static final long serialVersionUID = 1L;
	
	private Board gameBoard;
	
	private  Connection getNewConnection() throws Exception {
	    Context ctx = new InitialContext();
	    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/javapolyDS");
	    Connection conn = ds.getConnection();
 
        return conn;
	}
	
	private ArrayList<Token> getGameTokens(String gameId) throws Exception {
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		String sql = "SELECT * FROM token where gameId=?";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, gameId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Token token = new Token();
			
			token.setPlayerName(rs.getString("playerName"));
			token.setGamePiece("gamePiece");
			token.setMoney(rs.getInt("money"));
			token.setPosition(rs.getInt("position"));
			token.setReady(rs.getBoolean("ready"));
			
			tokens.add(token);
		}
		
		conn.close();
		
		return tokens;
	}
	
	@Override
	public void saveNewTokenToDatabase(String gameId, String playerName, String gamePiece, int money, int position, boolean ready) {
		String sql = "INSERT INTO token (gameId, playerName, gamePiece, money, position, ready) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, gameId);
			ps.setString(2, playerName);
			ps.setString(3, gamePiece);
			ps.setInt(4, money);
			ps.setInt(5, position);
			ps.setBoolean(6, ready);
			
			ps.executeUpdate();
			
			conn.close();
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public Boolean checkPlayersReady(String gameId) {
		Boolean playersReady = false;
		
		try {
			ArrayList<Token> tokens = getGameTokens(gameId);
			
			int readyCount = 0;
			
			for(Token token : tokens) {
				if(token.getReady() != null && token.getReady()) {
					readyCount++;
				}
			}
			
			if(readyCount == tokens.size() && readyCount >= 2) {
				playersReady = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return playersReady;
	}
//
//	@Override
//	public Map<String, Integer> getPlayerPositions() {
//		HashMap<String, Integer> playerPositions = new HashMap<String, Integer>();
//		for (Entry<String, Token> entry : gameBoard.getUsers().entrySet()) {
//			String playerName = entry.getKey();
//			Token playerToken = entry.getValue();
//			int position = playerToken.getCurrentPosition();
//			playerPositions.put(playerName, position);
//		}
//		return playerPositions;
//	}
//
//
//	@Override
//	public void initializeGame(ArrayList<String> names) {
//		gameBoard = new Board();
//		for (String name : names) {
//			gameBoard.addUser(new Token(name));
//		}
//	}
//	
//	@Override
//	public String roll(String name) {
//		Token player = gameBoard.getUser(name);
//		player = gameBoard.handleRoll(player);
//		gameBoard.updateUser(player);
//		int rollOne = player.getLastRollDieOne();
//		int rollTwo = player.getLastRollDieTwo();
//		return rollOne + "+" + rollTwo;
//	}
//	
//	@Override
//	public Integer getSpeedingAmount(String name) {
//		Integer speedingAmount;
//		Token player = gameBoard.getUser(name);
//		speedingAmount = player.getSpeeding();
//		return speedingAmount;
//	}
//	
//	@Override
//	public HashMap<String, String> getPlayerPropertyList(String player) {
//		HashMap<String, String> playerPropertiesList = new HashMap<String, String>(); 
//		Token playerToken = gameBoard.getUser(player);
//		ArrayList<Deed> ownedDeeds = gameBoard.getOwnedDeeds(playerToken);
//		for (Deed deed : ownedDeeds) {
//			String name = deed.getName();
//			String color = deed.getColorHex();
//			playerPropertiesList.put(name, color);
//		}
//		return playerPropertiesList;
//	}
//	
//	@Override
//	public HashMap<Integer, String> getAllSpacesAndOwners() {
//		HashMap<Integer , String> spacesAndOwners = new HashMap<Integer, String>();
//		ArrayList<Space> spaces = gameBoard.deeds;
//		
//		for (int idx=0;idx<spaces.size();idx++) {
//			Space space = spaces.get(idx);
//			Token owner = space.getOwner();
//			String ownerName = owner.getName();
//			Integer uiSpace = idx+1; // GF might need to change this if we change it to 0 based system (currently 1 based? client side?)
//			spacesAndOwners.put(uiSpace, ownerName);
//		}
//		
//		return spacesAndOwners;
//	}
//	
//	@Override
//	public HashMap<String, Integer> getPlayerMoneyAmounts() {
//		HashMap<String, Integer> playerMoneyAmounts = new HashMap<String, Integer>();
//		HashMap<String, Token> players = gameBoard.getUsers();
//		
//		for (Entry<String, Token> entry : players.entrySet()) {
//			String playerName = entry.getKey();
//			Token playerToken = entry.getValue();
//			Integer playerMoney = playerToken.getCashMoney();
//			playerMoneyAmounts.put(playerName, playerMoney);
//		}
//		
//		return playerMoneyAmounts;
//	}
//	
//	@Override
//	public ArrayList<String> getPlayerOptionsFromSpace(String playerName, int spaceNumber) {
//		ArrayList<String> playerOptions = new ArrayList<String>();
//		
//		// TODO how do we retrieve these? Do we pass back a string listing what options they have?
//		// does client side have a list of options, and what we get returned determines what is available?
//		// give more ideas, this one is up in the air -GF
//		
//		return playerOptions;
//	}
//	
//	// TRADING BLOCK FROM HELL
//	// BEWARE ALL YE WHO ENTER HERE (HELP FINISH THESE)
//	
//	@Override
//	public void requestTrade(String playerRequesting, String playerRequested) {
//		// TODO playerRequesting wants to make a trade with playerRequested. 
//		// how do we handle this? have pop-up that both players interact with at same time if accepted?
//		// is this method going to be a simple accept/deny? -GF
//	}
//	
//	@Override
//	public Boolean respondToTradeRequest(boolean wantsToTrade) {
//		// TODO the player who was requested to be traded responds with either 'accept' or 'deny'
//		return false;
//	}
//	
//	@Override
//	public void addItemToTrade(Object item, String playerAdding) {
//		// TODO add the item to the trade object associated with these two players.
//	}
//	
//	@Override
//	public void removeItemFromTrade(Object item, String playerRemoving) {
//		// TODO remove the item from the trade object associated with this player.
//	}
//	
//	@Override
//	public void acceptTrade(String playerAccepting) {
//		// TODO set the acceptance of player to true
//	}
//	
//	@Override
//	public void denyTrade(String playerDenying) {
//		// TODO set the acceptance of player to false
//	}
//	
//	@Override
//	public ArrayList<Object> getOpposingTraderItems(String playerRequesting) {
//		// TODO return the list of items that the other trader has put up from the 
//		return null;
//	}
//	
//	@Override
//	public String tradeCloseWithDescription() {
//		// TODO close the trade, stating whether it was denied, accepted, cancelled, etc.
//		return null;
//	}

}
