package cs414.a5.groupA.monopoly.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	ArrayList<String> gamePiecesList = new ArrayList<String>() {{
		add("img/token/coffee-cup.png");
		add("img/token/coffee-mug.png");
		add("img/token/coffee-pot.png");
		add("img/token/thermo.png");
	}};

	private Connection getNewConnection() throws Exception {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/javapolyDS");
		Connection conn = ds.getConnection();

		return conn;
	}

	@Override
	public ArrayList<Token> getAllGameTokens(String gameId) {
		ArrayList<Token> tokens = new ArrayList<Token>();

		try {
			String sql = "SELECT * FROM `token` where `gameId`=?";
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, gameId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Token token = getTokenFromResultSet(rs);

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
		String sql = "INSERT INTO `token` (`gameId`, `playerName`, `gamePiece`, `money`, `position`, `ready`, `inJail`, `speedCount`, `playerTurn`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		token.setGamePiece(getNewAssignedGamePiece(token.getGameId()));

		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, token.getGameId());
			ps.setString(2, token.getPlayerName());
			ps.setString(3, token.getGamePiece());
			ps.setInt(4, token.getMoney());
			ps.setInt(5, token.getPosition());
			ps.setBoolean(6, token.isReady());
			ps.setBoolean(7, token.isInJail());
			ps.setInt(8, token.getSpeedCount());
			ps.setBoolean(9, token.isPlayerTurn());


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

		for (String gamePiecePath : gamePiecesList) {
			try {
				String sql = "SELECT * FROM `token` WHERE `gameId`=? AND `gamePiece`=?";
				Connection conn = getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, gameId);
				ps.setString(2, gamePiecePath);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
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

		String sql = "SELECT * FROM `token` WHERE `gameId`=? AND `playerName`=?";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, gameId);
		ps.setString(2, playerName);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			token = getTokenFromResultSet(rs);
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

			for (Token token : tokens) {
				if (token.isReady() != null && token.isReady()) {
					readyCount++;
				}
			}

			if (readyCount == tokens.size() && readyCount >= 2) {
				playersReady = true;
				initializeDeeds(gameId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return playersReady;
	}

	@Override
	public void updateToken(Token token) {
		String sql = "UPDATE `token` SET `playerName`=?, `gamePiece`=?, `money`=?, `position`=?, `ready`=?, `inJail`=?, `speedCount`=?, `playerTurn`=? WHERE `tokenId`=?";

		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, token.getPlayerName());
			ps.setString(2, token.getGamePiece());
			ps.setInt(3, token.getMoney());
			ps.setInt(4, token.getPosition());
			ps.setBoolean(5, token.isReady());
			ps.setBoolean(6, token.isInJail());
			ps.setInt(7, token.getSpeedCount());
			ps.setBoolean(8, token.isPlayerTurn());
			ps.setInt(9, token.getTokenId());

			ps.executeUpdate();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDeedOwner(String gameID, int position) {
		String sql = "SELECT `playerName` FROM `deed` WHERE `gameId`=? AND `position`=?";
		String owner = null;
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameID);
			ps.setInt(2, position);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				owner = rs.getString("playerName");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return owner;
	}

	public void updateDeed(Token token) {
		String sql = "UPDATE `deed` SET `playerName`=? WHERE `gameId`=? AND `position`=?";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, token.getPlayerName());
			ps.setString(2, token.getGameId());
			ps.setInt(3, token.getPosition());

			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initializeDeeds(String gameId){
		boolean deedsInitialized = getDeedsAlreadyInitialized(gameId);
		if(!deedsInitialized) {
			Board gameBoard = new Board();
			for (Space deed: gameBoard.deeds){
				if (deed instanceof Deed){
					initializeDeed(gameId, (Deed) deed);
				}
			}
		}
	}

	public void initializeDeed(String gameId, Deed d) {
		String sql = "INSERT into `deed` (`gameId`, `position`, `playerName`, `housingCount`) VALUES" +
				" (?,?,?,?)";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameId);
			ps.setInt(2, d.getPosition());
			ps.setString(3, null);
			ps.setInt(4, 0);

			ps.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean getDeedsAlreadyInitialized(String gameId) {
		boolean alreadyInitialized = false;
		
		String sql = "SELECT * FROM `deed` WHERE `gameId`=?";
		
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				alreadyInitialized = true;
			}

			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return alreadyInitialized;
	}

	@Override
	public String roll(String name, String gameId) {
		Token player = null;
		System.out.println(name + " " + gameId);
		try {
			player = getTokenByGameIdAndName(gameId, name);
			player = handleRoll(player);
		} catch (Exception e) {
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
		start = currentPlayer.getPosition();
		int moveTo = (start + r1 + r2) % 40;

		if (die.checkForDoubles(r1, r2)) {
			if (currentPlayer.isInJail()) {
				currentPlayer.setInJail(false);
				return currentPlayer; // you get out of jail but wait a turn tell you can move
			} else {
				int currentSpeed = currentPlayer.getSpeedCount();
				if (currentSpeed + 1 == 3) {
					currentPlayer.setInJail(true);
					currentPlayer.setPosition(10);
					currentPlayer.setSpeedCount(0);
				} else
					currentPlayer.setSpeedCount(currentSpeed + 1);
			}
		}
		if (moveTo == 30) {
			currentPlayer.setPosition(11);
			currentPlayer.setInJail(true);
			currentPlayer.setSpeedCount(0);
		} else {
			if (!currentPlayer.isInJail() && currentPlayer.getPosition() < start)
				currentPlayer.setMoney(currentPlayer.getMoney() + 200);
			currentPlayer.setPosition(moveTo);
		}

		return currentPlayer;
	}

	public boolean checkForOwnedDeed(String gameId, int position) {
		return getDeedOwner(gameId, position) == null;
	}

	public void wantsToBuyProperty(Token currentPlayer) {
		Deed tempDeed = new Deed(currentPlayer.getPosition());
		if (currentPlayer.getMoney() > tempDeed.getPrice()) {
			updateDeed(currentPlayer);
			currentPlayer.setMoney(currentPlayer.getMoney() - tempDeed.getPrice());
		}
		updateToken(currentPlayer);
	}

	public void payRentToToken(Token currentPlayer) {
		Deed current = new Deed(currentPlayer.getPosition());
		int rent = current.getRent();
		if (currentPlayer.getMoney() - rent >= 0)
			payRent(currentPlayer, rent);
		else
			payRent(currentPlayer, currentPlayer.getMoney()); // give other play rest of money
	}

	public boolean checkForTaxSpot(Token currentPlayer) {
		return currentPlayer.getPosition() == 4 || currentPlayer.getPosition() == 38;
	}

	public void chargeTax(Token currentPlayer) {
		if (currentPlayer.getPosition() == 4) {
			//tax spot pay 200
			if (currentPlayer.getMoney() - 200 >= 0)
				currentPlayer.setMoney(currentPlayer.getMoney() - 200);
			//@TODO show user they've lost
		} else if (currentPlayer.getPosition() == 38) {
			if (currentPlayer.getMoney() - 100 >= 0)
				currentPlayer.setMoney(currentPlayer.getMoney() - 100);
			//TODO add else to tell player they've lost
		}
		updateToken(currentPlayer);
	}

	public void dealWithCard(Token currentPlayer){
		Card c = getCard();
		int type = c.getType();
		switch (type) {
			case 1: // get paid
				currentPlayer.setMoney(currentPlayer.getMoney() + c.getAmount());
			break;
			case 2: // have to pay
				if (currentPlayer.getMoney() - c.getAmount() >= 0)
					currentPlayer.setMoney(currentPlayer.getMoney() - c.getAmount());
			break;
			case 3: // move to
				if (c.getAmount() == 10) {
					currentPlayer.setPosition(c.getAmount());
					currentPlayer.setInJail(true);
				} else
					currentPlayer.setPosition(c.getAmount());
				break;
			default:
				System.err.println("UnKnow card type something is wrong");
		}
		updateToken(currentPlayer);
	}

	public boolean checkForCardSpot(int position){
		return position == 2 || position == 7 || position == 17 || position == 22 || position == 33 || position ==36;
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

	public Card getCard(){
		String sql = "SELECT * FROM card Where position=?";
		int position = (int) (21.0*Math.random()+1);
		Card c = new Card(position);
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, position);

			ResultSet rs = ps.executeQuery(sql);
			conn.close();
			if (rs.next()){
				c.setType(rs.getInt("type"));
				c.setDiscription(rs.getString("cardText"));
				c.setAmount(rs.getInt("cardReward"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return c;
	}
	
	
	@Override
	public void initializeFirstTurn(String gameId) {
		String sql = "UPDATE `token` SET `playerTurn`=1 WHERE `gameId`=? LIMIT 1";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, gameId);
			ps.executeUpdate();
			conn.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Token getCurrentTurnToken(String gameId) {
		String sql = "SELECT * FROM `token` WHERE gameId=? AND playerTurn=1";
		Token token = new Token();
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;
			ps.setString(1, gameId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				token = getTokenFromResultSet(rs);
			}
			conn.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	@Override
	public void nextPlayersTurn(String gameId) {
		Token currentToken = getCurrentTurnToken(gameId);
		currentToken.setPlayerTurn(false);
		int currentTurnTokenId = currentToken.getTokenId();
		updateToken(currentToken);
		String sql = "SELECT * FROM `token` WHERE gameId=? AND tokenId>? LIMIT 1";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;
			ps.setString(1, gameId);
			ps.setInt(2, currentTurnTokenId);
			rs = ps.executeQuery();
			
			if (rs.next()) { // if there is someone next by tokenId, update them
				Token token = getTokenFromResultSet(rs);
				token.setPlayerTurn(true);
				updateToken(token);
			}
			else { // otherwise, grab the very first token that has the gameId
				Connection c = getNewConnection();
				PreparedStatement p = c.prepareStatement(sql);
				ResultSet r;
				p.setString(1, gameId);
				p.setInt(2, 0);
				r = p.executeQuery();
				
				if (r.next()) {
					Token token = getTokenFromResultSet(r);
					token.setPlayerTurn(true);
					updateToken(token);
				}
				c.close();
			}
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Token getTokenFromResultSet(ResultSet rs) throws SQLException {
		Token token = new Token();
		token.setTokenId(rs.getInt("tokenId"));
		token.setGameId(rs.getString("gameId"));
		token.setPlayerName(rs.getString("playerName"));
		token.setGamePiece(rs.getString("gamePiece"));
		token.setMoney(rs.getInt("money"));
		token.setPosition(rs.getInt("position"));
		token.setReady(rs.getBoolean("ready"));
		token.setInJail(rs.getBoolean("inJail"));
		token.setSpeedCount(rs.getInt("speedCount"));
		token.setPlayerTurn(rs.getBoolean("playerTurn"));
		return token;
	}
	
	@Override
	public HashMap<String, String> getDeedsOwnedByPlayer(String gameId, String playerName) {
		HashMap<String, String> deedAndColor = new HashMap<String, String>();
		
		String sql = "SELECT * FROM `deed` WHERE gameId=? AND playerName=?";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;
			ps.setString(1, gameId);
			ps.setString(2, playerName);
			rs = ps.executeQuery();
			
			while (rs.next()) { // if there is someone next by tokenId, update them
				int deedPosition = rs.getInt("position");
				Deed deed = new Deed(deedPosition);
				deedAndColor.put(deed.getName(), deed.getPropertyGroup().name());
			}
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return deedAndColor;
	}
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
