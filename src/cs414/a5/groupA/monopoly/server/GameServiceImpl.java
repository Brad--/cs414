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

}