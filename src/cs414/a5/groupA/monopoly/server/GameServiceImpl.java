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
import cs414.a5.groupA.monopoly.shared.Token;

public class GameServiceImpl extends RemoteServiceServlet implements GameService {

	private static final long serialVersionUID = 1L;
	
	private Board gameBoard;
	
	ArrayList<String> gamePiecesList = new ArrayList<String>() {{
	    add("img/token/coffee-cup.png");
	    add("img/token/coffee-mug.png");
	    add("img/token/coffee-pot.png");
	    add("img/token/thermo.png");
	}};
	
	private  Connection getNewConnection() throws Exception {
	    Context ctx = new InitialContext();
	    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/javapolyDS");
	    Connection conn = ds.getConnection();
 
        return conn;
	}
	
	@Override
	public ArrayList<Token> getAllGameTokens(String gameId) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		try {
			String sql = "SELECT * FROM token where gameId=?";
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, gameId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Token token = new Token();
				
				token.setPlayerName(rs.getString("playerName"));
				token.setGamePiece(rs.getString("gamePiece"));
				token.setMoney(rs.getInt("money"));
				token.setPosition(rs.getInt("position"));
				token.setReady(rs.getBoolean("ready"));
				token.setInJail(rs.getBoolean("inJail"));
				token.setSpeedCount(rs.getInt("speedCount"));
				
				tokens.add(token);
			}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tokens;
	}
	
	@Override
	public Token saveNewTokenToDatabase(Token token) {
		Token returnToken = null;
		String sql = "INSERT INTO token (gameId, playerName, gamePiece, money, position, ready, inJail, speedCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		token.setGamePiece(getNewAssignedGamePiece(token.getGameId()));
		
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, token.getGameId());
			ps.setString(2, token.getPlayerName());
			ps.setString(3, token.getGamePiece());
			ps.setInt(4, token.getMoney());
			ps.setInt(5, token.getPosition());
			ps.setBoolean(6, token.getReady());
			ps.setBoolean(7, token.getInJail());
			ps.setInt(8, token.getSpeedCount());
			
			
			ps.executeUpdate();
			
			conn.close();
			
			returnToken = getTokenByGameIdAndName(token.getGameId(), token.getPlayerName());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnToken;
	}
	
	public String getNewAssignedGamePiece(String gameId) {
		String returnGamePiece = null;
		
		for(String gamePiecePath : gamePiecesList) {
			try {
				String sql = "SELECT * FROM token WHERE gameId=? AND gamePiece=?";
				Connection conn = getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, gameId);
				ps.setString(2, gamePiecePath);
				ResultSet rs = ps.executeQuery();
				if(!rs.next()) {
					returnGamePiece = gamePiecePath;
					conn.close();
					break;
				} else {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return returnGamePiece;
	}
	
	public Token getTokenByGameIdAndName(String gameId, String playerName) throws Exception {
		Token token = new Token();
		
		String sql = "SELECT * FROM token WHERE gameId=? AND playerName=?";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, gameId);
		ps.setString(2, playerName);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			
			token.setTokenId(rs.getInt("tokenId"));
			token.setPlayerName(rs.getString("playerName"));
			token.setGamePiece(rs.getString("gamePiece"));
			token.setMoney(rs.getInt("money"));
			token.setPosition(rs.getInt("position"));
			token.setReady(rs.getBoolean("ready"));
			token.setInJail(rs.getBoolean("inJail"));
			token.setSpeedCount(rs.getInt("speedCount"));
			
		}
		
		conn.close();

		return token;
	}
	
	@Override
	public Boolean checkPlayersReady(String gameId) {
		Boolean playersReady = false;
		
		try {
			ArrayList<Token> tokens = getAllGameTokens(gameId);
			
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
	
	@Override
	public void updateToken(Token token) {
		String sql = "UPDATE token SET playerName=?, gamePiece=?, money=?, position=?, ready=?, inJail=?, speedCount=? WHERE tokenId=?";
		
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, token.getPlayerName());
			ps.setString(2, token.getGamePiece());
			ps.setInt(3, token.getMoney());
			ps.setInt(4, token.getPosition());
			ps.setBoolean(5, token.getReady());
			ps.setBoolean(6, token.getInJail());
			ps.setInt(7, token.getSpeedCount());
			ps.setInt(8, token.getTokenId());
			
			ps.executeUpdate();
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDeedOwner(String gameID, int position){
		String sql = "SELECT playerName FROM deed WHERE gameId=? AND position=?";
		String owner = "";
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameID);
			ps.setInt(2, position);
			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()){
				owner = rs.getString("playerName");
			}

			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return owner;
	}

	public void updateDeed(Token token){
		String sql = "UPDATE deed SET playerName=? WHERE gameId=? AND position=?";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, token.getPlayerName());
			ps.setString(2, token.getGameId());
			ps.setInt(3, token.getPosition());

			ps.executeUpdate();
			conn.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String roll(String name, String gameId) {
			Token player = null;
			System.out.println(name + " " + gameId);
		try {
			player = getTokenByGameIdAndName(gameId, name);
			player = handleRoll(player);
		}catch (Exception e) {
			e.printStackTrace();
		}

		int rollOne = player.getLastRollOne();
		int rollTwo = player.getLastRollTwo();
		updateToken(player);
		return rollOne + "+" + rollTwo;
	}

	public Token handleRoll(Token currentPlayer) { // TODO return TokenActionWrapper?
		// GD 11.15.16 Needs redone after token refactor
        Die die = new Die();
		int start = 0;
		int r1 = die.roll();
		int r2 = die.roll();
		currentPlayer.setLastRollOne(r1);
		currentPlayer.setLastRollTwo(r2);
		if (die.checkForDoubles(r1,r2)){
			if (currentPlayer.getInJail()) {
				currentPlayer.setInJail(false);
				return  currentPlayer; // you get out of jail but wait a turn tell you can move
			}
			else {
				int currentSpeed = currentPlayer.getSpeedCount();
				if (currentSpeed + 1 == 3) {
					currentPlayer.setInJail(true);
					currentPlayer.setSpeedCount(0);
				} else
					currentPlayer.setSpeedCount(currentSpeed + 1);
			}
		}
        if (!currentPlayer.getInJail()){
			start = currentPlayer.getPosition();
			int moveTo = (start + r1 + r2) % 40;
			currentPlayer.setPosition(moveTo);
            if (getDeedOwner(currentPlayer.getGameId(), currentPlayer.getPosition()) == null) {
                //TODO: display to player option to buy
                updateDeed(currentPlayer);
            }
            else if (currentPlayer.getPosition()==1){
				currentPlayer.setMoney(currentPlayer.getMoney()+200);
            }
            else if (currentPlayer.getPosition()==5){
                //tax spot pay 200
				if (currentPlayer.getMoney()-200 >= 0)
					currentPlayer.setMoney(currentPlayer.getMoney()-200);
                //@TODO show user they've lost
            }
            else if (currentPlayer.getPosition()== 39){
				if (currentPlayer.getMoney()-75 >= 0)
                	currentPlayer.setMoney(currentPlayer.getMoney()-75);
					//TODO add else to tell player they've lost
            }
            else{
				// check if they pass go before paying rent
				if(!currentPlayer.getInJail() && currentPlayer.getPosition() < start) {
					currentPlayer.setMoney(currentPlayer.getMoney()+200);
				}
                // pay rent
				Deed current = (Deed) gameBoard.deeds.get(currentPlayer.getPosition());
				int rent = current.getRent();
				if (currentPlayer.getMoney()-rent >= 0)
                	payRent(currentPlayer, rent);
				else
					payRent(currentPlayer, currentPlayer.getMoney()); // give other play rest of money

				return currentPlayer;
            }
        }

        // Pass go
        if(!currentPlayer.getInJail() && currentPlayer.getPosition() < start) {
			currentPlayer.setMoney(currentPlayer.getMoney()+200);
        }
		return currentPlayer;
	}

	private Token payRent(Token player, int rent){
		String owner = getDeedOwner(player.getGameId(), player.getPosition());
		Token player2 = null;
		try {
			player2 = getTokenByGameIdAndName(player.getGameId(), owner);
			player2.setMoney(player2.getMoney()+rent);
			player.setMoney(player.getMoney()-rent);
			updateToken(player2);
		}catch (Exception e){
			e.printStackTrace();
		}
		return player;
	}

	@Override
	public void deleteToken(Token token) {
		String sql = "DELETE FROM token WHERE tokenId=?";
		
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, token.getTokenId());
			
			ps.executeUpdate();
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
