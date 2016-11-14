package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cs414.a5.groupA.monopoly.server.Token;
import cs414.a5.groupA.monopoly.server.TokenActionWrapper;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("game")
public interface GameService extends RemoteService {
	Boolean testAsyncCall();

	Map<String, Integer> getPlayerPositions();
	
	void initializeGame(ArrayList<String> names);

	String roll(String name);

	HashMap<String, String> getPlayerPropertyList(String name);

	Integer getSpeedingAmount(String name);
	
	HashMap<Integer, String> getAllSpacesAndOwners();
}
