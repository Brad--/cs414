package cs414.a5.groupA.monopoly.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cs414.a5.groupA.monopoly.client.GameService;

public class GameServiceImpl extends RemoteServiceServlet implements GameService {

	private static final long serialVersionUID = 1L;
	
	private Board gameBoard;

	@Override
	public Map<String, Integer> getPlayerPositions() {
		HashMap<String, Integer> playerPositions = new HashMap<String, Integer>();
		for (Entry<String, Token> entry : gameBoard.getUsers().entrySet()) {
			String playerName = entry.getKey();
			Token playerToken = entry.getValue();
			int position = playerToken.getCurrentPosition();
			playerPositions.put(playerName, position);
		}
		return playerPositions;
	}

	@Override
	public Boolean testAsyncCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeGame(ArrayList<String> names) {
		gameBoard = new Board();
		for (String name : names) {
			gameBoard.addUser(new Token(name));
		}
	}
	
	@Override
	public String roll(String name) {
		Token player = gameBoard.getUser(name);
		player = gameBoard.handleRoll(player);
		gameBoard.updateUser(player);
		int rollOne = player.getLastRollDieOne();
		int rollTwo = player.getLastRollDieTwo();
		return rollOne + "+" + rollTwo;
	}
	
	@Override
	public Integer getSpeedingAmount(String name) {
		Integer speedingAmount;
		Token player = gameBoard.getUser(name);
		speedingAmount = player.getSpeeding();
		return speedingAmount;
	}
	
	@Override
	public HashMap<String, String> getPlayerPropertyList(String player) {
		HashMap<String, String> playerPropertiesList = new HashMap<String, String>(); 
		Token playerToken = gameBoard.getUser(player);
		ArrayList<Deed> ownedDeeds = gameBoard.getOwnedDeeds(playerToken);
		for (Deed deed : ownedDeeds) {
			String name = deed.getName();
			String color = deed.getColorHex();
			playerPropertiesList.put(name, color);
		}
		return playerPropertiesList;
	}
	
	@Override
	public HashMap<Integer, String> getAllSpacesAndOwners() {
		HashMap<Integer , String> spacesAndOwners = new HashMap<Integer, String>();
		ArrayList<Space> spaces = gameBoard.deeds;
		
		for (int idx=0;idx<spaces.size();idx++) {
			Space space = spaces.get(idx);
			Token owner = space.getOwner();
			String ownerName = owner.getName();
			Integer uiSpace = idx+1; // GF might need to change this if we change it to 0 based system (currently 1 based? client side?)
			spacesAndOwners.put(uiSpace, ownerName);
		}
		
		return spacesAndOwners;
	}
	
	@Override
	public HashMap<String, Integer> getPlayerMoneyAmounts() {
		HashMap<String, Integer> playerMoneyAmounts = new HashMap<String, Integer>();
		HashMap<String, Token> players = gameBoard.getUsers();
		
		for (Entry<String, Token> entry : players.entrySet()) {
			String playerName = entry.getKey();
			Token playerToken = entry.getValue();
			Integer playerMoney = playerToken.getCashMoney();
			playerMoneyAmounts.put(playerName, playerMoney);
		}
		
		return playerMoneyAmounts;
	}

}
