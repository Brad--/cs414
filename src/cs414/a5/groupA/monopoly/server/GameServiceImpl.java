package cs414.a5.groupA.monopoly.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import java.util.Set;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cs414.a5.groupA.monopoly.client.GameService;
import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.DeedSpotOptions;
import cs414.a5.groupA.monopoly.shared.Token;

import static cs414.a5.groupA.monopoly.server.PropertyGroup.*;

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

	private String getNewAssignedGamePiece(String gameId) {
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

	@Override
	public Boolean buyHouse(String playerName, String deedName, String gameId) {
		Boolean buyingSuccessful = false;
        try {
            Token player = getTokenByGameIdAndName(gameId, playerName);
            Deed deed = getDeedByName(gameId, playerName, deedName);

            PropertyGroup color = deed.getPropertyGroup();
            int cost = 0;
            if (color == BROWN || color == LIGHTBLUE) {
                cost = 50;
            }
            else if (color == PURPLE || color == ORANGE) {
                cost = 100;
            }
            else if (color == RED || color == YELLOW) {
                cost = 150;
            }
            else if (color == GREEN || color == BLUE) {
                cost = 200;
            }
            else {
              	return false; // case when trying to buy railroad/utility houses. will need to fail
            }

            int resultFunds = player.getMoney() - cost;
            if(resultFunds > 0) {
                player.setMoney(player.getMoney() - cost);
                updateToken(player);
                if (deed.getHousingCount() < 5 && !deed.hasHotel()) {
                	deed.setHousingCount(deed.getHousingCount() + 1);
                	updateDeedHousingCount(deed.getHousingCount(), gameId, deedName);
                }
            	buyingSuccessful = true;
            }
        } catch(Exception e) {
            System.out.println("Error fetching Token or Deed from db.");
        }
        return buyingSuccessful;
    }
	
	@Override
	public Boolean checkIfAbleToBuildHouse(String playerName, String deedName, String gameId) throws Exception {
		Deed deedToBuildOn = getDeedByName(gameId, playerName, deedName);
		boolean ableToBuildHouse = true;
		int numHouses = deedToBuildOn.getHousingCount();
		String propertyGroup = deedToBuildOn.getPropertyGroup().toString();
		List<DatabaseDeed> allDeedsInGroup = getDeedsByPropertyGroup(propertyGroup, gameId);
		for (DatabaseDeed deed : allDeedsInGroup) {
			if (numHouses > deed.getHousingCount()) {
				ableToBuildHouse = false;
			}
		}
		return ableToBuildHouse;
	}
	
	private List<DatabaseDeed> getDeedsByPropertyGroup(String propertyGroup, String gameId) throws Exception {
		List<DatabaseDeed> deeds = new ArrayList<DatabaseDeed>();
		
		String sql = "SELECT * FROM `deed` WHERE `gameId`=? AND `propertyGroup`=?";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, gameId);
		ps.setString(2, propertyGroup);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			DatabaseDeed deed = getDatabaseDeedFromResultSet(rs);
			deeds.add(deed);
		}
		conn.close();
		
		return deeds;
	}

    @Override
    public Integer sellHouse(String playerName, String deedName, String gameId) throws SQLException{
    	Integer sellAmount = null;
		try{
			Token player = getTokenByGameIdAndName(gameId, playerName);
			Deed deed = getDeedByName(gameId, playerName, deedName);
			int currentHouseCount = deed.getHousingCount();
			int newCount = currentHouseCount-1;
			if (newCount >=0){
				PropertyGroup color = deed.getPropertyGroup();
				int cost;
				if (color == BROWN || color == LIGHTBLUE) {
					cost = 50;
				}
				else if (color == PURPLE || color == ORANGE) {
					cost = 100;
				}
				else if (color == RED || color == YELLOW) {
					cost = 150;
				}
				else if (color == GREEN || color == BLUE) {
					cost = 200;
				}
				else {
					// silently fail
					return null;
				}
				sellAmount = cost/2;
				player.setMoney(player.getMoney() + sellAmount);
				updateDeedHousingCount(newCount,gameId,deedName);
				updateToken(player);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sellAmount;
	}

    @Override
    public Boolean checkForMonopoly(String playerName, String deedName, String gameId) {
        HashMap<String, String> nameColorMap = getDeedsOwnedByPlayer(gameId, playerName);
        if (nameColorMap.containsKey(deedName)) {
            String color = nameColorMap.get(deedName);
            int ownedCount = 0;

            for (Map.Entry<String, String> pair : nameColorMap.entrySet()) {
                if(pair.getValue().equals(color)) {
                    ownedCount++;
                }
            }

            boolean isTwoSpace = color.equals("BROWN") || color.equals("BLUE") || color.equals("UTILITY");
            if(ownedCount == 2 && isTwoSpace ) {
                return true;
            }
            else if(ownedCount == 3 && !isTwoSpace) {
                return true;
            }
        }
        return false;
    }

	private Token getTokenByGameIdAndName(String gameId, String playerName) throws Exception {
		Token token = null;

		String sql = "SELECT * FROM `token` WHERE `gameId`=? AND `playerName`=?";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, gameId);
		ps.setString(2, playerName);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			token = new Token();
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
		String sql = "UPDATE `token` SET `playerName`=?, `gamePiece`=?, `money`=?, `position`=?, `ready`=?, `inJail`=?, `speedCount`=?, `playerTurn`=?, `lastRollOne`=?, `lastRollTwo`=? WHERE `tokenId`=?";

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
			ps.setInt(9, token.getLastRollOne());
			ps.setInt(10, token.getLastRollTwo());
			ps.setInt(11, token.getTokenId());

			ps.executeUpdate();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateDeed(DatabaseDeed deed) {
		String sql = "UPDATE `deed` SET `gameId`=?, `deedName`=?, `position`=?, `playerName`=?, `housingCount`=?, `propertyGroup`=?, `isMortgaged`=? WHERE `deedId`=?";
	
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, deed.getGameId());
			ps.setString(2, deed.getDeedName());
			ps.setInt(3, deed.getPosition());
			ps.setString(4, deed.getPlayerName());
			ps.setInt(5, deed.getHousingCount());
			ps.setString(6, deed.getPropertyGroup());
			ps.setBoolean(7, deed.isMortgaged());
			ps.setInt(8, deed.getDeedId());

			ps.executeUpdate();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DeedSpotOptions getDeedSpotOptionsByGameIdAndPosition(String gameId, int position) {
		String sql = "SELECT * FROM `deed` WHERE `gameId`=? AND `position`=?";
		DeedSpotOptions deedSpotOptions = null;
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameId);
			ps.setInt(2, position);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				deedSpotOptions = new DeedSpotOptions();
				deedSpotOptions.setOwner(rs.getString("playerName"));
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deedSpotOptions;
	}

	private String getDeedOwner(String gameId, int position) {
		String owner = null;
		DeedSpotOptions deedSpotOptions = getDeedSpotOptionsByGameIdAndPosition(gameId, position);
		if(deedSpotOptions != null) {
			owner = deedSpotOptions.getOwner();
		}
		
		return owner;
	}

	private void updateDeedHousingCount(int housingCount, String gameId, String deedName) {
		String sql = "UPDATE `deed` SET `housingCount`=? where `gameId`=? AND `deedName`=?";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1,housingCount);
			ps.setString(2, gameId);
			ps.setString(3, deedName);

			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Deed getDeedByName(String gameID, String playerName, String deedName) {
		String sql = "SELECT * FROM `deed` WHERE `gameId`=? AND `deedName`=? AND `playerName`=?";
		String owner = null;
		Deed deed = null;
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameID);
			ps.setString(2, deedName);
			ps.setString(3, playerName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				deed = new Deed(rs.getInt("position"));
				deed.setName(rs.getString("deedName"));
				Token t = new Token();
				t.setPlayerName(rs.getString("playerName"));
				deed.setOwner(t);
				deed.setHousingCount(rs.getInt("housingCount"));

			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deed;
	}

	private Deed getDeedByPosition(String gameID, int position) {
		String sql = "SELECT * FROM `deed` WHERE `gameId`=? AND `position`=?";
		String owner = null;
		Deed deed = null;
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameID);
			ps.setInt(2, position);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				deed = new Deed(rs.getInt("position"));
				deed.setName(rs.getString("deedName"));
				Token t = new Token();
				t.setPlayerName(rs.getString("playerName"));
				deed.setOwner(t);
				deed.setHousingCount(rs.getInt("housingCount"));

			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deed;
	}

	private void updateDeedOwner(Token token, int position) {
		String sql = "UPDATE `deed` SET `playerName`=? WHERE `gameId`=? AND `position`=?";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, token.getPlayerName());
			ps.setString(2, token.getGameId());
			ps.setInt(3, position);

			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateDeedByToken(Token token) {
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

	private void initializeDeeds(String gameId){
		boolean deedsInitialized = getDeedsAlreadyInitialized(gameId);
		if(!deedsInitialized) {
			Board gameBoard = new Board();
			for (Space deed: gameBoard.deeds){
				if (deed instanceof Railroad){
					initializeRailroad(gameId, (Railroad) deed);
				} else if (deed instanceof Deed){
					initializeDeed(gameId, (Deed) deed);
				}
			}
		}
	}

	private void initializeDeed(String gameId, Deed d) {
		String sql = "INSERT into `deed` (`gameId`, `deedName`, `position`, `playerName`, `housingCount`, `propertyGroup`) VALUES" +
				" (?,?,?,?,?,?)";
		
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameId);
			ps.setString(2, d.getName());
			ps.setInt(3, d.getPosition());
			ps.setString(4, null);
			ps.setInt(5, 0);
			ps.setString(6, d.getPropertyGroup().toString());

			ps.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeRailroad(String gameId, Railroad railroad) {
		String sql = "INSERT into `deed` (`gameId`, `deedName`, `position`, `playerName`, `housingCount`, `propertyGroup`) VALUES" +
				" (?,?,?,?,?,?)";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, gameId);
			ps.setString(2, railroad.getName());
			ps.setInt(3, railroad.getPosition());
			ps.setString(4, null);
			ps.setInt(5, 0);
			ps.setString(6, railroad.getPropertyGroup().toString());

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

	// Debug is 0: Roll as normal
	//          1: Roll a one
	//          2: Roll doubles
	@Override
	public String roll(String name, String gameId, int debug) {
		Token player = null;
		try {
			player = getTokenByGameIdAndName(gameId, name);
			player = handleRoll(player, debug);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int rollOne = player.getLastRollOne();
		int rollTwo = player.getLastRollTwo();
		updateToken(player);
		return rollOne + "+" + rollTwo;
	}

	private Token handleRoll(Token currentPlayer, int debug) { // TODO return TokenActionWrapper?
		// GD 11.15.16 Needs redone after token refactor
		Die die = new Die();
		int start = 0;
		int r1, r2;
		if(debug == 1) {
			r1 = 1;
			r2 = 0;
		}
		else if(debug == 2) {
			r1 = die.roll();
			r2 = r1;
		}
		// Else catches the normal case. If debug is 0 or some random number it'll use the normal roll
		else {
			r1 = die.roll();
			r2 = die.roll();
		}

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
		else
			currentPlayer.setSpeedCount(0);
		if (moveTo == 30) {
			currentPlayer.setPosition(10);
			currentPlayer.setInJail(true);
			currentPlayer.setSpeedCount(0);
		} else {
			if (!currentPlayer.isInJail()) {
				currentPlayer.setPosition(moveTo);
				if (currentPlayer.getPosition() < start)
					currentPlayer.setMoney(currentPlayer.getMoney() + 200);
			}
		}

		return currentPlayer;
	}

	@Override
	public Boolean checkInJail(String gameId, String playerName) throws SQLException{
		Token player = new Token();
		try{
			player = getTokenByGameIdAndName(gameId, playerName);
		}catch (Exception e){
			e.printStackTrace();
		}
		return player.isInJail();
	}
	
	@Override
	public void getOutOfJail(String gameId, String playerName) {
		Token player = new Token();
		try{
			player = getTokenByGameIdAndName(gameId, playerName);
		}catch (Exception e){
			e.printStackTrace();
		}
		player.setInJail(false);
		updateToken(player);
	}

	@Override
	public Boolean checkRolledDoubles(String gameId, String playerName) throws SQLException{
		Token player = new Token();
		try{
			player = getTokenByGameIdAndName(gameId, playerName);
		}catch (Exception e){
			e.printStackTrace();
		}
		return player.getLastRollOne() == player.getLastRollTwo();
	}

	@Override
	public DeedSpotOptions checkForDeedSpot(String gameId, String name) throws Exception {
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		int position = currentPlayer.getPosition();
		DeedSpotOptions deedSpotOptions = getDeedSpotOptionsByGameIdAndPosition(gameId, position);

		return deedSpotOptions;
	}

	private String wantsToBuyProperty(String gameId, String name) throws Exception {
		String response = "Not enough money to buy property";
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		Deed tempDeed = new Deed(currentPlayer.getPosition());
		if (currentPlayer.getMoney() > tempDeed.getPrice()) {
			updateDeedByToken(currentPlayer);
			currentPlayer.setMoney(currentPlayer.getMoney() - tempDeed.getPrice());
			response = "Property purchased";
		}
		updateToken(currentPlayer);
		
		return response;
	}

	@Override
	public void AddBidOnDeed(String gameId, int position, String playerName, int playerBid) throws Exception{
		String sql = "INSERT INTO `deedBid` (`gameId`,`position`,`playerName`,`playerBid`) VALUES (?,?,?,?)";
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, gameId);
			ps.setInt(2, position);
			ps.setString(3, playerName);
			ps.setInt(4, playerBid);

			ps.execute();
			conn.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String getWinningBidOnDeed(String gameId, int position) throws Exception{
		String sql = "SELECT `playerName`, MAX(`playerBid`) FROM `deedBid` WHERE `gameId`=? AND `position`=?";
		String winner = "";
		String deedName = "";
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,gameId);
			ps.setInt(2, position);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				winner = rs.getString("playerName");
				Token player = getTokenByGameIdAndName(gameId, winner);
				Deed deed = getDeedByPosition(gameId, position);
				deedName = deed.getName();
				player.setMoney(player.getMoney()-deed.getPrice());
				deed.setOwner(player);
				updateDeedOwner(player, position);
				updateToken(player);
			}
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return winner + " won " + deedName;
	}

	@Override
	public Integer payRentToToken(String gameId, String name) throws Exception {
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		int position = currentPlayer.getPosition();
		DatabaseDeed deed = getDatabaseDeedFromPosition(gameId, position);
		int rent = DeedUtil.calcRent(deed);
		int amountPaid;
		if (currentPlayer.getMoney() - rent >= 0) {
			amountPaid = rent;
			payRent(currentPlayer, amountPaid);
		}
		else {
			amountPaid = currentPlayer.getMoney();
			payRent(currentPlayer, amountPaid); // give other play rest of money
		}
		return amountPaid;
	}
	
	private DatabaseDeed getDatabaseDeedFromPosition(String gameId, int position) {
		DatabaseDeed deed = new DatabaseDeed();
		String sql = "SELECT * FROM `deed` WHERE `gameId`=? AND `position`=?";
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,gameId);
			ps.setInt(2, position);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				deed = getDatabaseDeedFromResultSet(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deed;
	}

	@Override
	public Boolean checkForTaxSpot(String gameId, String name) throws Exception {
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		return currentPlayer.getPosition() == 4 || currentPlayer.getPosition() == 38;
	}

	@Override
	public String chargeTax(String gameId, String name) throws Exception {
		String descriptionMessage = "";
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		if (currentPlayer.getPosition() == 4) {
			descriptionMessage = "You hear Tony Frank chuckle as he adds $200 to your tuition fee.";
			if (currentPlayer.getMoney() - 200 >= 0)
				currentPlayer.setMoney(currentPlayer.getMoney() - 200);
		} else if (currentPlayer.getPosition() == 38) {
			descriptionMessage = "You buy a nice piece of jewelry for $100";
			if (currentPlayer.getMoney() - 100 >= 0)
				currentPlayer.setMoney(currentPlayer.getMoney() - 100);
		}
		updateToken(currentPlayer);
		return descriptionMessage;
	}

	@Override
	public String dealWithCard(String gameId, String name) throws Exception{
		String message = "";
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		Card c = getCard();
		message = c.getDescription();
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
				} else {
					if (c.getAmount() < currentPlayer.getPosition())
						currentPlayer.setMoney(currentPlayer.getMoney() + 200);
					currentPlayer.setPosition(c.getAmount());
				}
				break;
			default:
				System.err.println("Unknown card type something is wrong");
		}
		updateToken(currentPlayer);
		return message;
	}

	@Override
	public Boolean checkForCardSpot(String gameId, String name) throws Exception{
		Token currentPlayer = getTokenByGameIdAndName(gameId, name);
		int position = currentPlayer.getPosition();
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
			updateToken(player);
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

	private Card getCard(){
		String sql = "SELECT * FROM card Where cardId=?";
		int position = (int) (20.0*Math.random());
		Card c = new Card(position);
		try{
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, position);

			ResultSet rs = ps.executeQuery();
			
			if (rs.next()){
				c.setType(rs.getInt("type"));
				c.setDescription(rs.getString("cardText"));
				c.setAmount(rs.getInt("cardReward"));
			}
			conn.close();
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
		token.setLastRollOne(rs.getInt("lastRollOne"));
		token.setLastRollTwo(rs.getInt("lastRollTwo"));
		return token;
	}
	
	private DatabaseDeed getDatabaseDeedFromResultSet(ResultSet rs) throws SQLException {
		DatabaseDeed deed = new DatabaseDeed();
		deed.setDeedId(rs.getInt("deedId"));
		deed.setGameId(rs.getString("gameId"));
		deed.setDeedName(rs.getString("deedName"));
		deed.setPosition(rs.getInt("position"));
		deed.setPlayerName(rs.getString("playerName"));
		deed.setHousingCount(rs.getInt("housingCount"));
		deed.setPropertyGroup(rs.getString("propertyGroup"));
		deed.setMortgaged(rs.getBoolean("isMortgaged"));
		return deed;
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
	
	@Override
	public String handleDeedSpotOption(String gameId, String name, String selectedOption) {
		String response = null;
		
		switch(selectedOption) {
			case(DeedSpotOptions.BUY):
			try {
				response = wantsToBuyProperty(gameId, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
				break;
			case(DeedSpotOptions.DO_NOT_BUY):
				response = "Did not buy property";
				break;
		}
		return response;
	}

	@Override
	public void sellProperty(String gameId, String playerName, String deedName) throws SQLException{
		Board gameBoard = new Board();
		Token player = new Token();
		try{
			player = getTokenByGameIdAndName(gameId, playerName);
			Deed d = getDeedByName(gameId, playerName, deedName);
			d = (Deed) gameBoard.deeds.get(d.getPosition());
			player.setMoney(d.getPrice()/2);
		}catch(Exception e){
			e.printStackTrace();
		}
		updateToken(player);
	}
	
	@Override
	public ArrayList<DatabaseDeed> getAllOwnedDeedsForGameId(String gameId) {
		ArrayList<DatabaseDeed> ownedDeedsList = new ArrayList<DatabaseDeed>();
		
		String sql = "SELECT * FROM `deed` WHERE gameId=? AND playerName IS NOT NULL order by playerName, propertyGroup, deedName";
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;
			ps.setString(1, gameId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				DatabaseDeed deed = new DatabaseDeed();
				deed.setDeedId(rs.getInt("deedId"));
				deed.setGameId(rs.getString("gameId"));
				deed.setDeedName(rs.getString("deedName"));
				deed.setPosition(rs.getInt("position"));
				deed.setPlayerName(rs.getString("playerName"));
				deed.setHousingCount(rs.getInt("housingCount"));
				deed.setPropertyGroup(rs.getString("propertyGroup"));
				deed.setMortgaged(rs.getBoolean("isMortgaged"));
				deed.setHexColor(DeedUtil.getDeedHexColorByStringPropertyGroup(deed.getPropertyGroup()));
				
				ownedDeedsList.add(deed);
			}
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ownedDeedsList;
	}
	
	@Override
	public Integer getRentOwedOnCurrentSpace(String gameId, String playerName) throws Exception {
		Token player = getTokenByGameIdAndName(gameId, playerName);
		int position = player.getPosition();
		Deed deed = new Deed(position);
		Integer rent = deed.getRent();
		return rent;
	}
	
	@Override
	public void saveNewTradeToDatabase(Trade trade) {
		String sql = "INSERT INTO `trade` (`gameId`, `playerOneName`, `playerOneMoneyOffered`, `playerOneAccepted`, "
				+ "`playerTwoName`, `playerTwoMoneyOffered`, `playerTwoAccepted`, `isFinalized`"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		
		try {
			Connection conn = getNewConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;
			ps.setString(1, trade.getGameId());
			ps.setString(2, trade.getPlayerOneName());
			ps.setInt(3, trade.getPlayerOneMoney());
			ps.setBoolean(4, trade.getPlayerOneAccepted());
			ps.setString(5, trade.getPlayerTwoName());
			ps.setInt(6, trade.getPlayerTwoMoney());
			ps.setBoolean(7, trade.getPlayerTwoAccepted());
			ps.setBoolean(8, trade.getI)
			;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
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
